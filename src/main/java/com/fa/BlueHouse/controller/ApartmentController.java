package com.fa.BlueHouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fa.BlueHouse.entities.Apartment;
import com.fa.BlueHouse.services.ApartmentService;

@Controller
@RequestMapping(path = "/Apartment/")
public class ApartmentController {
	@Autowired
	ApartmentService apartmentService;

	@GetMapping("list")
	public String showAll(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Apartment> allApartments = apartmentService.allApartments(pageRequest);
		model.addAttribute("currentPage", page);
		int totalPages ;
		if(allApartments.getTotalPages() < 1) {
			totalPages = 1 ;
		}else {
			totalPages = allApartments.getTotalPages();
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listApartment", allApartments.getContent());
		return "Apartment/list";
	}

	@GetMapping("search")
	public String searchApartment(@RequestParam(name = "searchKeyword", defaultValue = "") String keyword, Model model,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Apartment> allApartments = apartmentService.findApartmentsByKeyword(pageRequest, keyword);
		model.addAttribute("currentPage", page);
		int totalPages ;
		if(allApartments.getTotalPages() < 1) {
			totalPages = 1 ;
		}else {
			totalPages = allApartments.getTotalPages();
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listApartment", allApartments.getContent());
		return "Apartment/list";

	}
	
}
