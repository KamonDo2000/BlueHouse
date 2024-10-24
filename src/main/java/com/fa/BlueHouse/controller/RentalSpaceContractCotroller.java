package com.fa.BlueHouse.controller;

import java.security.Principal;
import java.util.List;
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
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.entities.RentalSpaceContract;
import com.fa.BlueHouse.services.RentalSpaceContractService;

import jakarta.validation.Valid;


@Controller
@RequestMapping(path = "rentalSpaceContract")
public class RentalSpaceContractCotroller {

	@Autowired
	private RentalSpaceContractService rentalSpaContrac;
	
	@GetMapping({"/", "/list"})
	public String showListRentalSpaCon(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<RentalSpaceContract> allRentalSpaceContract = rentalSpaContrac.allRentalSpaceContract(pageRequest);
		
		int totalPages;
		if (allRentalSpaceContract.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = allRentalSpaceContract.getTotalPages();
		}
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listRentalSpa", allRentalSpaceContract.getContent());
		return "/RentalSpaceContract/listRentalSpaContrac";
	}
	
	@GetMapping("/search")
	public String searchRentalSpaceContract(Model model, @RequestParam(name = "searchKeyword", defaultValue = "") String search, @RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<RentalSpaceContract> allRentalSpaceContract = rentalSpaContrac.seachRentalSpaceContract(pageRequest, search);
		
		int totalPages ;
		if(allRentalSpaceContract.getTotalPages() < 1) {
			totalPages = 1 ;
		}else {
			totalPages = allRentalSpaceContract.getTotalPages();
		}
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", search);
		model.addAttribute("listRentalSpa", allRentalSpaceContract.getContent());
		
		if (search.equalsIgnoreCase("")) {
			model.addAttribute("listRentalSpa", rentalSpaContrac.findalRenSpaCon());
		} else {
			model.addAttribute("listRentalSpa", rentalSpaContrac.findByKeyword(search));
		}
		return "/RentalSpaceContract/listRentalSpaContrac";
	}
	
	@GetMapping("/add")
	public String createRentalSpaContrac(Model model) {
		model.addAttribute("RentalSpa", new RentalSpaceContract ());
		model.addAttribute("apartments", rentalSpaContrac.findalApa());
//		model.addAttribute("employees", rentalSpaContrac.findalEmploy());
		return "/RentalSpaceContract/AddRentalSpaceContract";
	}
	
	@PostMapping("/save")
	public String saveRentalSpaContrac(Model model,@Valid @ModelAttribute("RentalSpa") RentalSpaceContract rentalSpaCon, BindingResult bindingResult, Principal principal) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("apartments", rentalSpaContrac.findalApa());
//			model.addAttribute("employees", rentalSpaContrac.findalEmploy());
			return "/RentalSpaceContract/AddRentalSpaceContract";
		}
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String id = userDetails.getId();
		Employee emp = rentalSpaContrac.findByempID(id);
		rentalSpaCon.setManagerCodeContract(emp);
		rentalSpaContrac.saveRentalSpaceContractDao(rentalSpaCon);
		return "redirect:/rentalSpaceContract/list";
	}
	
	@GetMapping("/delete")
	public String deleteRenSpaCon(@RequestParam("idrenspacon") String id) {
		rentalSpaContrac.deleteRenSapCon(id);
		return "redirect:/rentalSpaceContract/list";
	}

	@GetMapping("/edit")
	public String editRenSpaCon(Model model, @RequestParam("idrenspacon") String id) {
		List<RentalSpaceContract> listresi = rentalSpaContrac.findalRenSpaCon();
		model.addAttribute("listapartment", listresi);
		model.addAttribute("apartments", rentalSpaContrac.findalApa());
//		model.addAttribute("employees", rentalSpaContrac.findalEmploy());
		model.addAttribute("RentalSpa", rentalSpaContrac.findByID(id));
		return "/RentalSpaceContract/AddRentalSpaceContract";
	}
	@PostMapping("/update")
	public String saveupdateRenSpaCon(@ModelAttribute("idrenspacon") RentalSpaceContract renspa, Principal principal) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String id = userDetails.getId();
		Employee emp = rentalSpaContrac.findByempID(id);
		renspa.setManagerCodeContract(emp);
		rentalSpaContrac.updateRenSpaCon(renspa);
		return "redirect:/rentalSpaceContract/list";
	}
	
}
