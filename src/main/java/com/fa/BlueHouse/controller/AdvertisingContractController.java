package com.fa.BlueHouse.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.fa.BlueHouse.entities.AdvertisingContract;
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.services.AdvertisingContractservices;
import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "advertisingContract")
public class AdvertisingContractController {

	@Autowired
	private AdvertisingContractservices adConservices;
	
	@GetMapping({"/", "/list"})
	public String showAdvertisingContract(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<AdvertisingContract> listAdvertisingContract = adConservices.allAdvertisingContract(pageRequest);
		
		int totalPages;
		if (listAdvertisingContract.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listAdvertisingContract.getTotalPages();
		}
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listAdvertisingContract", listAdvertisingContract.getContent());
		return "/AdvertisingContract/listAdvertisingContract";
	}
	
	@GetMapping("/search")
	public String searchAdvertisingContract(Model model, @RequestParam(name = "searchKeyword", defaultValue = "") String search, @RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<AdvertisingContract> allAdvertisingContract = adConservices.seachAdvertisingContract(pageRequest, search);
		
		int totalPages;
		if(allAdvertisingContract.getTotalPages() < 1) {
			totalPages = 1;
		}else {
			totalPages = allAdvertisingContract.getTotalPages();
		}
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", search);
		model.addAttribute("listAdvertisingContract", allAdvertisingContract.getContent());
		
		if (search.equalsIgnoreCase("")) {
			model.addAttribute("listAdvertisingContract", adConservices.findaAdv());
		} else {
			model.addAttribute("listAdvertisingContract", adConservices.findByKeyword(search));
		}
		
		return "/AdvertisingContract/listAdvertisingContract";
	}
	
	
	@GetMapping("/add")
	public String addAdConservices(Model model) {
		model.addAttribute("advcontract", new AdvertisingContract());
//		model.addAttribute("listemp", adConservices.findaEmp());
		model.addAttribute("listfee", adConservices.findaFee());
		return "/AdvertisingContract/addAdvertisingContract";
	}
	
	@PostMapping("/save")
	public String saveAdConservices(Model model,@Valid @ModelAttribute("advcontract") AdvertisingContract advertisingContract, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
//			model.addAttribute("listemp", adConservices.findaEmp());
			model.addAttribute("listfee", adConservices.findaFee());
			return "/AdvertisingContract/addAdvertisingContract";
		}
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String id = userDetails.getId();
		Employee emp = adConservices.findaByIdemp(id);
		advertisingContract.setEmpadv(emp);
		adConservices.saveAdv(advertisingContract);
		return "redirect:/advertisingContract/list";
	} 
	
	@GetMapping("/delete")
	public String deleteAdConservices(@RequestParam("idcontrac") String id) {
		adConservices.deleteadv(id);
		return "redirect:/advertisingContract/list";
	}

	@GetMapping("/edit")
	public String editAdConservices(Model model, @RequestParam("idcontrac") String id) {
//		model.addAttribute("listemp", adConservices.findaEmp());
		model.addAttribute("listfee", adConservices.findaFee());
		model.addAttribute("advcontract", adConservices.findaById(id));
		return "/advertisingContract/addAdvertisingContract";
	}
	
	@PostMapping("/update")
	public String updateAdConservices(@ModelAttribute("advcontract") AdvertisingContract advertisingContract, Principal principal) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String id = userDetails.getId();
		Employee emp = adConservices.findaByIdemp(id);
		advertisingContract.setEmpadv(emp);
		adConservices.updateadv(advertisingContract);
		return "redirect:/advertisingContract/list";
	}
}
