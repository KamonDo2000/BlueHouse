package com.fa.BlueHouse.controller;

import java.security.Principal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fa.BlueHouse.authen.model.AccountDTO;
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.entities.ExpenseBill;
import com.fa.BlueHouse.entities.ExpenseBillDetail;
import com.fa.BlueHouse.entities.Resident;
import com.fa.BlueHouse.services.EmployeeService;
import com.fa.BlueHouse.services.ExpenseBillDetailService;
import com.fa.BlueHouse.services.ExpenseBillService;
import com.fa.BlueHouse.services.ResidentService;

@Controller
@RequestMapping("/ExpenseBill")
public class ExpenseBillController {

	@Autowired
	private ExpenseBillService expenbill;
	@Autowired
	private EmployeeService emp;
	@Autowired
	private ExpenseBillDetailService expendetail;
	@Autowired
	private ResidentService resident;
	
	
	@GetMapping("/create")
	public String createExpenseBill(Principal principal) {
		AccountDTO thongTin = (AccountDTO) ((Authentication) principal).getPrincipal();
		String id = expenbill.generateNewId();
		Employee employy = new Employee();
		employy = emp.findById(thongTin.getId());
		ExpenseBill expen = new ExpenseBill(id, LocalDate.now(),employy );
		expenbill.saveExpenseBill(expen);
		return "redirect:/ExpenseBill/show";
	}
	@GetMapping("/createbillevent")
	public String createExpenseBillEvent(Principal principal) {
		AccountDTO thongTin = (AccountDTO) ((Authentication) principal).getPrincipal();
		String id = expenbill.generateNewId();
		Resident resi = new Resident();
		resi = resident.findById(thongTin.getId());
		ExpenseBill expen = new ExpenseBill(id, LocalDate.now(),resi);
		expenbill.saveExpenseBill(expen);
		return "redirect:/ExpenseBill/show";
	}
	@PostMapping("/save")
	public String saveExpensebill(@ModelAttribute ExpenseBill expen) {
		expenbill.saveExpenseBill(expen);
		return "redirect:/ExpenseBill/show";
	}
	
	@GetMapping("/show")
	public String showlistExpensebill(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<ExpenseBill> listExpenseBill = expenbill.findAllPageExpenseBill(pageRequest);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", listExpenseBill.getTotalPages());
		model.addAttribute("listexpen", listExpenseBill.getContent());
		return "ExpenseBill/listExpenseBill";
	}
	@GetMapping("listdetail")
	public String showlistDetail(Model model, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name="idbill", defaultValue = "#{null}" ) String id) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		if(id != null) {
		Page<ExpenseBillDetail> listExpenseBillDetail = expendetail.findByIdExpenseDetail(id, pageRequest);
		model.addAttribute("expensebill", expenbill.findExpenBillById(id));
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", listExpenseBillDetail.getTotalPages());
		model.addAttribute("listexpendetail", listExpenseBillDetail.getContent());
		return "/ExpenseBillDetail/listExpenseBillDetail";
		}else {
			Page<ExpenseBillDetail> listExpenseBillDetail = expendetail.findAllPageExpenseBillDetail( pageRequest);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", listExpenseBillDetail.getTotalPages());
			model.addAttribute("listexpendetail", listExpenseBillDetail.getContent());
			return "/ExpenseBillDetail/listAllDetail";
		}
	}
	@GetMapping("createdetail")
	public String createDetail(Model model, @RequestParam(name="idbill", defaultValue = "#{null}" )String idbill) {
		String id;
		id = expendetail.generateNewId();
		if(idbill != null) {
			model.addAttribute("expensedetail", new ExpenseBillDetail(id,expenbill.findExpenBillById(idbill)));
			return "/ExpenseBillDetail/createExpenseBillDetail";
		}else {
			model.addAttribute("listexpensebill", expenbill.findAllExpenseBill());
			model.addAttribute("expensedetail", new ExpenseBillDetail(id, null));
			return "/ExpenseBillDetail/createDetail";
		}
		}
		
		@PostMapping("saveDetail")
		public String saveDetail(@ModelAttribute ExpenseBillDetail detail) {
			expendetail.saveExpenDetail(detail);
			return "redirect:/ExpenseBill/listdetail?idbill=" + detail.getIdExpenseBill().getIdExpenseBill();
		}
		@GetMapping("deletedetail")
		public String deleteDetail(@RequestParam("iddetail") String id) {
		    String idbill = expendetail.findByIDDetail(id).getIdExpenseBill().getIdExpenseBill();
			expendetail.deleteExpenseBillDetail(id);
			return "redirect:/ExpenseBill/listdetail?idbill=" + idbill;
		}
		@GetMapping("editdetail")
		public String editDetail(Model model, @RequestParam("iddetail") String id) {
			model.addAttribute("expensedetail", expendetail.findByIDDetail(id));
			return "/ExpenseBillDetail/updateExpenseBillDeatail";
		}
		@GetMapping("searchexpensebill")
		public String searchExpenseBill(@RequestParam(name = "searchKeyword", defaultValue = "") String keyword, Model model,
			@RequestParam(name = "page", defaultValue = "1") int page) {
			int pageSize = 6;
			PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
			Page<ExpenseBill> listExpenseBill = expenbill.searchExpenseBill(keyword, pageRequest);
			model.addAttribute("currentPage", page);
			int totalPages ;
			if(listExpenseBill.getTotalPages() < 1) {
				totalPages = 1 ;
			}else {
				totalPages = listExpenseBill.getTotalPages();
			}
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("searchKeyword", keyword);
			model.addAttribute("listexpen", listExpenseBill.getContent());
			return "ExpenseBill/listExpenseBill";
		}
		@GetMapping("searchexpensedetail")
		public String searchExpensedetail(@RequestParam(name = "searchKeyword", defaultValue = "") String keyword, Model model,
			@RequestParam(name = "page", defaultValue = "1") int page) {
			int pageSize = 6;
			PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
			Page<ExpenseBillDetail> listExpenseDetail = expendetail.searchExpenseBillDetail(keyword, pageRequest);
			model.addAttribute("currentPage", page);
			int totalPages ;
			if(listExpenseDetail.getTotalPages() < 1) {
				totalPages = 1 ;
			}else {
				totalPages = listExpenseDetail.getTotalPages();
			}
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("searchKeyword", keyword);
			model.addAttribute("listexpendetail", listExpenseDetail.getContent());
			return "/ExpenseBillDetail/listAllDetail";
		}
		@GetMapping("searchexpensebilldetail")
		public String searchExpenseBilldetail(@RequestParam(name = "searchKeyword", defaultValue = "") String keyword, Model model,
			@RequestParam("idbill")String idbill, @RequestParam(name = "page", defaultValue = "1") int page) {
			int pageSize = 6;
			PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
			Page<ExpenseBillDetail> listExpenseDetail = expendetail.searchExpenseBillDetail(keyword,idbill, pageRequest);
			model.addAttribute("currentPage", page);
			int totalPages ;
			if(listExpenseDetail.getTotalPages() < 1) {
				totalPages = 1 ;
			}else {
				totalPages = listExpenseDetail.getTotalPages();
			}
			model.addAttribute("expensebill", expenbill.findExpenBillById(idbill));
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("searchKeyword", keyword);
			model.addAttribute("listexpendetail", listExpenseDetail.getContent());
			return "/ExpenseBillDetail/listExpenseBillDetail";
		}
		
		
}
