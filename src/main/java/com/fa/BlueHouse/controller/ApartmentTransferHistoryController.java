package com.fa.BlueHouse.controller;

import java.util.List;

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

import com.fa.BlueHouse.entities.Apartment;
import com.fa.BlueHouse.entities.ApartmentTransferHistory;
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.entities.Resident;
import com.fa.BlueHouse.services.ApartmentService;
import com.fa.BlueHouse.services.ApartmentTransferHistoryService;
import com.fa.BlueHouse.services.EmployeeService;
import com.fa.BlueHouse.services.ResidentService;

@Controller
@RequestMapping(path = "/ApartmentTransferHistory/")
public class ApartmentTransferHistoryController {
	@Autowired
	ApartmentTransferHistoryService apartmentTransferHistoryService;

	@Autowired
	ApartmentService apartmentService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	ResidentService residentService;

	@GetMapping("list")
	public String showAll(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<ApartmentTransferHistory> allApartmentTransferHistory = apartmentTransferHistoryService
				.showAll(pageRequest);
		model.addAttribute("currentPage", page);
		int totalPages;
		if (allApartmentTransferHistory.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = allApartmentTransferHistory.getTotalPages();
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listAll", allApartmentTransferHistory.getContent());
		return "ApartmentTransferHistory/list";
	}

	@GetMapping("showAdd")
	public String showAdd(Model model) {
		List<Apartment> apartments = apartmentService.regularApartments();
		List<Employee> employees = employeeService.listAdministrator();
		model.addAttribute("listAdministrator", employees);
		model.addAttribute("apartments", apartments);
		model.addAttribute("formApartmentTransfer", new ApartmentTransferHistory());
		return "ApartmentTransferHistory/add";
	}

	@PostMapping("add")
	public String save(
			@ModelAttribute(name = "formApartmentTransfer") ApartmentTransferHistory apartmentTransferHistory,
			BindingResult bindingResult) {
		String idHomeowner = apartmentTransferHistory.getNewHomeowner();
		String idApartment = apartmentTransferHistory.getApartmentTransfer().getIdApartment();
		Apartment apartment = apartmentService.findById(idApartment);
		for (Resident resident : residentService.findByIdApartment(idApartment)) {
			resident.setIdApartment(null);
			residentService.saveResident(resident);
		}
		apartmentTransferHistory.setIdContract(apartmentTransferHistoryService.generateNewId());
		apartment.setIdHomeowner(idHomeowner);
		apartmentService.save(apartment);
		apartmentTransferHistoryService.save(apartmentTransferHistory);
		return "redirect:list";
	}
	@GetMapping("search")
	public String search(@RequestParam(name = "searchKeyword", defaultValue = "") String keyword, Model model,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<ApartmentTransferHistory> all = apartmentTransferHistoryService.findByKeyword(pageRequest, keyword);
		model.addAttribute("currentPage", page);
		int totalPages ;
		if(all.getTotalPages() < 1) {
			totalPages = 1 ;
		}else {
			totalPages = all.getTotalPages();
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listAll", all.getContent());
		return "ApartmentTransferHistory/list";

	}
}
