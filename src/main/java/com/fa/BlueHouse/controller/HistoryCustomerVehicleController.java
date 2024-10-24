package com.fa.BlueHouse.controller;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fa.BlueHouse.entities.HistoryCustomerVehicle;
import com.fa.BlueHouse.entities.HistoryCustomerVehicleID;
import com.fa.BlueHouse.services.HistoryCustomerVehicleServices;

import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "historyCustomerVehicle")
public class HistoryCustomerVehicleController {

	@Autowired
	private HistoryCustomerVehicleServices hisCusVehiServices;
	
	@GetMapping({"/", "/list"})
	public String showListHisCusVehi(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<HistoryCustomerVehicle> allhiscusvehi = hisCusVehiServices.allHistoryCustomerVehicle(pageRequest);
		
		int totalPages;
		if (allhiscusvehi.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = allhiscusvehi.getTotalPages();
		}
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listhiscusvehi", allhiscusvehi.getContent());
		return "/HistoryCustomerVehicle/listHistoryCustomerVehicle";
	}
	
	@GetMapping("/search")
	public String searchHisCusVehi(Model model, @RequestParam(name = "searchKeyword", defaultValue = "") String search, @RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<HistoryCustomerVehicle> allHisCusVehi = hisCusVehiServices.seachHistoryCustomerVehicle(pageRequest, search);
		
		int totalPages ;
		if(allHisCusVehi.getTotalPages() < 1) {
			totalPages = 1 ;
		}else {
			totalPages = allHisCusVehi.getTotalPages();
		}
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", search);
		model.addAttribute("listhiscusvehiPA", allHisCusVehi.getContent());
		
		if (search.equalsIgnoreCase("")) {
			model.addAttribute("listFinancialSupportfee", hisCusVehiServices.findaHisCusVehi());
		} else {
			model.addAttribute("listFinancialSupportfee", hisCusVehiServices.findByKeyword(search));
		}
		return "/HistoryCustomerVehicle/listHistoryCustomerVehicle";
	}
	
	@GetMapping("/add")
	public String createHisCusVehi(Model model) {
		model.addAttribute("hiscusvehi", new HistoryCustomerVehicle ());
		model.addAttribute("apartments", hisCusVehiServices.findaApa());
		return "/HistoryCustomerVehicle/createHistoryCustomerVehicle";
	}
	
	@PostMapping("/save")
	public String saveHisCusVehi(Model model,@Valid @ModelAttribute("hiscusvehi") HistoryCustomerVehicle hitoryscusvehi, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("apartments", hisCusVehiServices.findaApa());
			return "/HistoryCustomerVehicle/createHistoryCustomerVehicle";
		}
		hisCusVehiServices.saveHistoryCustomerVehicle(hitoryscusvehi);
		return "redirect:/historyCustomerVehicle/list";
	}

	@GetMapping("/xera")
	public String xeRa(@RequestParam("idhiscusvehi") String vehicleNumber, @RequestParam("idmoveInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime moveInDate) {
		HistoryCustomerVehicleID id = new HistoryCustomerVehicleID(vehicleNumber, moveInDate);
        hisCusVehiServices.updateMoveOutDate(id);
        return "redirect:/historyCustomerVehicle/list";
    }
	
//	@GetMapping("/xevao")
//    public String xeVao(@RequestParam("idhiscusvehi") String vehicleNumber) {
//        hisCusVehiServices.updateMoveInDate(vehicleNumber, LocalDate.now());
//        return "redirect:/showhiscusvehi";
//    }
	
}
