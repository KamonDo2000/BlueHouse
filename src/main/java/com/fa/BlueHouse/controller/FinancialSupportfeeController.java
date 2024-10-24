package com.fa.BlueHouse.controller;

import java.security.Principal;
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
import com.fa.BlueHouse.entities.FinancialSupportfee;
import com.fa.BlueHouse.services.FinancialSupportfeeServices;
import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "financialSupportfee")
public class FinancialSupportfeeController {

	@Autowired
	private FinancialSupportfeeServices finSupfeeServices;
	
	@GetMapping({"/", "/list"})
	public String showFinancialSupportfee(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<FinancialSupportfee> listFinancialSupportfee = finSupfeeServices.allFinsupfee(pageRequest);
		
		int totalPages;
		if (listFinancialSupportfee.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listFinancialSupportfee.getTotalPages();
		}
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listFinancialSupportfee", listFinancialSupportfee.getContent());
		return "/FinancialSupportfee/listFinancialSupportfee";
	}
	
	@GetMapping("/search")
	public String searchfinSupfeeServices(Model model, @RequestParam(name = "searchKeyword", defaultValue = "") String search, @RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<FinancialSupportfee> allFinancialSupportfee = finSupfeeServices.seachFinsupfee(pageRequest, search);
		
		int totalPages;
		if(allFinancialSupportfee.getTotalPages() < 1) {
			totalPages = 1;
		}else {
			totalPages = allFinancialSupportfee.getTotalPages();
		}
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", search);
		model.addAttribute("listFinancialSupportfeePA", allFinancialSupportfee.getContent());
		
		if (search.equalsIgnoreCase("")) {
			model.addAttribute("listFinancialSupportfee", finSupfeeServices.findafinansuppfee());
		} else {
			model.addAttribute("listFinancialSupportfee", finSupfeeServices.findByKeyword(search));
		}
		
		return "/FinancialSupportfee/listFinancialSupportfee";
	}
	
	@GetMapping("/add")
	public String addRegiForResi(Model model) {
		model.addAttribute("finansuppfee", new FinancialSupportfee());
		return "/FinancialSupportfee/addFinancialSupportfee";
	}
	
	@PostMapping("/save")
	public String saveRegiForResi(Model model,@Valid @ModelAttribute("finansuppfee") FinancialSupportfee financiasuppfee, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "/FinancialSupportfee/addFinancialSupportfee";
		}
//		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
//		String id = userDetails.getId();
//		Employee emp = finSupfeeServices.findaByIdemp(id);
//		financiasuppfee.setManagerCodeRegi(emp);
		finSupfeeServices.savefinansupfee(financiasuppfee);
		return "redirect:/financialSupportfee/list";
	} 
	
	@GetMapping("/delete")
	public String deletefinansuppfee(@RequestParam("idfinSupfee") String id) {
		finSupfeeServices.deletefinsupFee(id);
		return "redirect:/financialSupportfee/list";
	}

	@GetMapping("/edit")
	public String editRegiForResi(Model model, @RequestParam("idfinSupfee") String id) {
		model.addAttribute("finansuppfee", finSupfeeServices.findaById(id));
		return "/FinancialSupportfee/addFinancialSupportfee";
	}
	
	@PostMapping("/update")
	public String updateRegiForResi(@ModelAttribute("finansuppfee") FinancialSupportfee financiasuppfee) {
		finSupfeeServices.updateFinsupFee(financiasuppfee);
		return "redirect:/financialSupportfee/list";
	}
}
