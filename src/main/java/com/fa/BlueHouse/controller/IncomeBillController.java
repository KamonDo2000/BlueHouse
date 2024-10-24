package com.fa.BlueHouse.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fa.BlueHouse.authen.model.AccountDTO;
import com.fa.BlueHouse.entities.FeeType;
import com.fa.BlueHouse.entities.FinancialSupportfee;
import com.fa.BlueHouse.entities.IncomeBill;
import com.fa.BlueHouse.entities.RentalSpaceContract;
import com.fa.BlueHouse.entities.Resident;
import com.fa.BlueHouse.entities.VehicleRegistration;
import com.fa.BlueHouse.services.ApartmentService;
import com.fa.BlueHouse.services.EmployeeService;
import com.fa.BlueHouse.services.FeetypeService;
import com.fa.BlueHouse.services.FinancialSupportfeeServices;
import com.fa.BlueHouse.services.IncomeBillService;
import com.fa.BlueHouse.services.RentalSpaceContractService;
import com.fa.BlueHouse.services.ResidentService;
import com.fa.BlueHouse.services.VehicleRegistrationService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/IncomeBill")
public class IncomeBillController {
	@Autowired
	private ResidentService residen;
	@Autowired
	private FeetypeService feetype;
	@Autowired
	private IncomeBillService inbill;
	@Autowired
	private ApartmentService apart;
	@Autowired
	private EmployeeService emp;
	@Autowired
	private FinancialSupportfeeServices fin;
	@Autowired 
	private VehicleRegistrationService vehic;
	@Autowired 
	private RentalSpaceContractService spa;
	@RequestMapping("/showfeetype")
	public String showlistFeetype(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<FeeType> listfeetype = feetype.findallpageFeetype(pageRequest);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", listfeetype.getTotalPages());
		model.addAttribute("listfeetype", listfeetype.getContent());
		return "Feetype/listFeetype";
	}
	@GetMapping("/createFeetype")
	public String createFeetype(Model model) {
		String id = feetype.generateNewId();
		model.addAttribute("feetype", new FeeType(id));
		return "Feetype/createFeetype";
	}
	
	@PostMapping("/savefeetype")
	public String saveFeetype(Model model,@Valid @ModelAttribute("feetype") FeeType fee, BindingResult result) {
		if(result.hasErrors()) {
			return "Feetype/listFeetype";
		}else {
        feetype.saveFeetype(fee);
			return "redirect:/IncomeBill/showfeetype";
		}
	}

	@GetMapping("/showinvoiceapratmentbill")
	public String showInvoiceApartmentBill(Model model, @RequestParam(name = "page", defaultValue = "1") int page, Principal principal) {
		AccountDTO thongTin = (AccountDTO) ((Authentication) principal).getPrincipal();
		Resident res = residen.findById(thongTin.getId());
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<IncomeBill> listApartmentBill = inbill.findApartmentBill(res.getIdApartment().getIdApartment(), pageRequest);
		model.addAttribute("apartment", apart.findById(res.getIdApartment().getIdApartment()));
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", listApartmentBill.getTotalPages());
		model.addAttribute("listapartmentbill", listApartmentBill.getContent());
		return "/IncomeBill/listIncomebill";
	}
	
	@GetMapping("/showlistapratmentbill")
	public String showlistApartmentBill(Model model, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam("idApartment") String idApartment) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<IncomeBill> listApartmentBill = inbill.findApartmentBill(idApartment, pageRequest);
		model.addAttribute("apartment", apart.findById(idApartment));
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", listApartmentBill.getTotalPages());
		model.addAttribute("listapartmentbill", listApartmentBill.getContent());
		return "/IncomeBill/listIncomebill";
	}
	@GetMapping("/showlistbill")
	public String showlistBill(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<IncomeBill> listApartmentBill = inbill.findAllbill( pageRequest);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", listApartmentBill.getTotalPages());
		model.addAttribute("listapartmentbill", listApartmentBill.getContent());
		return "/IncomeBill/listAllBill";
	}
	@GetMapping("/createapartmentbill")
	public String createApartBill(Model model,@RequestParam(name = "idapartment", defaultValue = "#{null}" )String id ) {
		String idbill = inbill.generateNewId();
		if(id != null) {
			
			model.addAttribute("incomebill", new IncomeBill(idbill,apart.findById(id)));
			return "/IncomeBill/createApartmentBill";
		}else {
		
			model.addAttribute("incomebill", new IncomeBill(idbill));
			model.addAttribute("listapart", apart.allApartments());
			return  "/IncomeBill/createBill";
		}
		
	}
	
	@PostMapping("/saveapartmentbill")
	public String saveApartmentBill(Model model,@ModelAttribute("incomebill") IncomeBill incobill ) {
		inbill.saveIncobill(incobill);
		return "redirect:/IncomeBill/showlistapratmentbill?idApartment=" + incobill.getIdApartment().getIdApartment();
	} 
	@GetMapping("/searchIncobill")
	public String searchIncomeBill(@RequestParam(name = "searchKeyword", defaultValue = "") String keyword, Model model,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<IncomeBill> listApartmentBill = inbill.searchIncomeBill(keyword, pageRequest);
		model.addAttribute("currentPage", page);
		int totalPages ;
		if(listApartmentBill.getTotalPages() < 1) {
			totalPages = 1 ;
		}else {
			totalPages = listApartmentBill.getTotalPages();
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listapartmentbill", listApartmentBill.getContent());
		return "/IncomeBill/listAllBill";
	}
	@GetMapping("/searchapartmentbill")
	public String searchApartmentBill(@RequestParam(name = "searchKeyword", defaultValue = "") String keyword, Model model,
			@RequestParam("idapartment")String idapartment,@RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<IncomeBill> listApartmentBill = inbill.searchApartmentBill(keyword, idapartment, pageRequest);
		model.addAttribute("currentPage", page);
		int totalPages ;
		if(listApartmentBill.getTotalPages() < 1) {
			totalPages = 1 ;
		}else {
			totalPages = listApartmentBill.getTotalPages();
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("apartment", apart.findById(idapartment));
		model.addAttribute("listapartmentbill", listApartmentBill.getContent());
		return "/IncomeBill/listIncomebill";
	}
	
	@GetMapping("/paybill")
	public String payBill(Principal principal, Model model, @RequestParam("idbill")String idbill) {
		AccountDTO thongTin = (AccountDTO) ((Authentication) principal).getPrincipal();
		IncomeBill incomebill = inbill.findById(idbill);
		incomebill.setStatus("Bill Paid");
		incomebill.setIdEmployee(emp.findById(thongTin.getId()));
		incomebill.setPaymentDate(LocalDate.now());
		inbill.saveIncobill(incomebill);
		return "redirect:/IncomeBill/showlistapratmentbill?idApartment=" + incomebill.getIdApartment().getIdApartment();

	}
	
	@GetMapping("/statistics")
    public ResponseEntity<Map<String, Double>> getStatistics() {
        Map<String, Double> stats = inbill.getTotalAmountByStatus();
        return ResponseEntity.ok(stats);
    }

	@GetMapping("/showbill")
	public String showlist(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<IncomeBill> listApartmentBill = inbill.AmountByStatus("Bill Not Paid", pageRequest);
		Page<IncomeBill> listApartmentBillpaid = inbill.AmountByStatus("Bill Paid", pageRequest);
		Page<IncomeBill> listApartment = inbill.findAllbill(pageRequest);
		Page<FinancialSupportfee> listFinan = fin.allFinsupfee(pageRequest);
		Page<VehicleRegistration> listVehi = vehic.allVehicleRegistration(pageRequest);
		Page<RentalSpaceContract> listspa = spa.allRentalSpaceContract(pageRequest);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", listApartmentBill.getTotalPages());
		model.addAttribute("listapartmentbill", listApartmentBill.getContent());
		model.addAttribute("listApartmentBillpaid", listApartmentBillpaid.getContent());
		model.addAttribute("listApartment", listApartment.getContent());
		model.addAttribute("listFinan", listFinan.getContent());
		model.addAttribute("listVehi", listVehi.getContent());
		model.addAttribute("listspa", listspa.getContent());
		return "PaymentStatistics/paymentstatistics";
	}
	
}
