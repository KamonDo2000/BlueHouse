package com.fa.BlueHouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fa.BlueHouse.entities.FeeType;
import com.fa.BlueHouse.entities.IncomeBill;
import com.fa.BlueHouse.entities.IncomeBillDetail;
import com.fa.BlueHouse.entities.VehicleRegistration;
import com.fa.BlueHouse.services.ApartmentService;
import com.fa.BlueHouse.services.FeetypeService;
import com.fa.BlueHouse.services.IncomeBillDetailService;
import com.fa.BlueHouse.services.IncomeBillService;
import com.fa.BlueHouse.services.VehicleRegistrationService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/IncomeBillDetail")
public class IncomeBillDetailController {

	@Autowired
	private IncomeBillDetailService indetail;
	@Autowired
	private IncomeBillService inbill;
	@Autowired
	private FeetypeService feetype;
	@Autowired
	private ApartmentService apart;
	@Autowired
	private VehicleRegistrationService vehicle;

	@GetMapping("/showlishtdetail")
	public String findallDetail(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "idbill", defaultValue = "#{null}") String idbill) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		if (idbill != null) {
			IncomeBill bill = inbill.findById(idbill);
			Page<IncomeBillDetail> listdetail = indetail.findAllbill(idbill, pageRequest);
			model.addAttribute("IncomeBill", bill);
			model.addAttribute("currentPage", page);
			model.addAttribute("apartment", apart.findById(bill.getIdApartment().getIdApartment()));
			model.addAttribute("totalPages", listdetail.getTotalPages());
			model.addAttribute("listDetail", listdetail.getContent());
			return "/IncomeBillDetail/listInDetail";
		} else {
			Page<IncomeBillDetail> listdetail = indetail.findAll(pageRequest);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", listdetail.getTotalPages());
			model.addAttribute("listDetail", listdetail.getContent());
			return "/IncomeBillDetail/listInDetail";
		}
	}

	@GetMapping("/createBillDetail")
	public String createBillDetail(Model model, @RequestParam(name = "idinbill", defaultValue = "#{null}") String id) {
		String iddetail = indetail.generateNewId();
		if (id != null) {
			model.addAttribute("billDetail", new IncomeBillDetail(iddetail,inbill.findById(id)));
			model.addAttribute("listfee", feetype.findallFeetype());
			return "/IncomeBillDetail/createBillDetail";
		} else {
			model.addAttribute("billDetail", new IncomeBillDetail(iddetail));
			model.addAttribute("listincomebill", inbill.finall());
			model.addAttribute("listfee", feetype.findallFeetype());
			return "/IncomeBillDetail/createDetail";
		}

	}

	@PostMapping("/saveDetail")
	public String saveDeatail(Model model, @Valid @ModelAttribute("billDetail") IncomeBillDetail billdetail,
			BindingResult result) {
		if (result.hasErrors()) {
			model.addAttribute("listfee", feetype.findallFeetype());
			return "/IncomeBillDetail/createBillDetail";
		}
		FeeType fee = feetype.findById(billdetail.getIdfeetype().getIdFeetype());
		billdetail.setPrice(fee.getPrice() * billdetail.getQuantity());
		indetail.saveIncobillDetail(billdetail);
		model.addAttribute("idbill", billdetail.getIdIncomeBill().getIdIncomeBill());
		return "redirect:/IncomeBillDetail/showlishtdetail?idbill=" + billdetail.getIdIncomeBill().getIdIncomeBill();

	}

	@GetMapping("/paymentvehicle")
	public String createdetailVehicle(Model model, @RequestParam("idinbill") String idbill) {
		IncomeBill incobill = inbill.findById(idbill);
		model.addAttribute("IncomeBill", incobill);
		model.addAttribute("listregis", vehicle.findRegisApartment(incobill.getIdApartment().getIdApartment()));
		return "/IncomeBillDetail/listRegisVehicle";
	}

	@GetMapping("saveregisdetail")
	public String saveRegisdetail(@RequestParam("idbill") String idbill, @RequestParam("idvehicle") String idVehicle) {
		IncomeBill bill = inbill.findById(idbill);
		VehicleRegistration regi = vehicle.findaById(idVehicle);
		FeeType fee = feetype.findById(regi.getFeeTypeCode().getIdFeetype());
		String id = indetail.generateNewId();
		IncomeBillDetail billdetail = new IncomeBillDetail(id, bill, fee, 1, fee.getPrice());
		indetail.saveIncobillDetail(billdetail);
		regi.setStatus("DTT");
		vehicle.saveVehicleRegistration(regi);
		return "redirect:/IncomeBillDetail/showlishtdetail?idbill=" + idbill;
	}

	@GetMapping("/deletedetail")
	public String deleteDetail(@RequestParam("iddetail") String id) {
		String idbill = indetail.findById(id).getIdIncomeBill().getIdIncomeBill();
		indetail.deleteDetail(id);
		return "redirect:/IncomeBillDetail/showlishtdetail?idbill=" + idbill;
	}

	@GetMapping("/editdetail")
	public String editDetail(Model model, @RequestParam("iddetail") String id) {
		model.addAttribute("billDetail", indetail.findById(id));
		model.addAttribute("listfee", feetype.findallFeetype());
		return "/IncomeBillDetail/updateDetail";
	}
	@GetMapping("/searchIncobilldetail")
	public String searchAlldetail(@RequestParam(name = "searchKeyword", defaultValue = "") String keyword, Model model,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<IncomeBillDetail> listApartmentBill = indetail.searchDetail(keyword, pageRequest);
		model.addAttribute("currentPage", page);
		int totalPages ;
		if(listApartmentBill.getTotalPages() < 1) {
			totalPages = 1 ;
		}else {
			totalPages = listApartmentBill.getTotalPages();
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listDetail", listApartmentBill.getContent());
		return "/IncomeBillDetail/listInDetail";
	}
	@GetMapping("/searchbilldetail")
	public String searchBilldetail(@RequestParam(name = "searchKeyword", defaultValue = "") String keyword, Model model,
			@RequestParam("idbill")String idbill, @RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<IncomeBillDetail> listApartmentBill = indetail.searchDetail(keyword,idbill, pageRequest);
		model.addAttribute("currentPage", page);
		int totalPages ;
		if(listApartmentBill.getTotalPages() < 1) {
			totalPages = 1 ;
		}else {
			totalPages = listApartmentBill.getTotalPages();
		}
		model.addAttribute("apartment", apart.findById(inbill.findById(idbill).getIdApartment().getIdApartment()));
		model.addAttribute("IncomeBill", inbill.findById(idbill));
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listDetail", listApartmentBill.getContent());
		return "/IncomeBillDetail/listInDetail";
	}
}
