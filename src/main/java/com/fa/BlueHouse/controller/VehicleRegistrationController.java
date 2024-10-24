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
import com.fa.BlueHouse.entities.Resident;
import com.fa.BlueHouse.entities.VehicleRegistration;
import com.fa.BlueHouse.services.ResidentService;
import com.fa.BlueHouse.services.VehicleRegistrationService;
import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "vehicleRegistration")
public class VehicleRegistrationController {

	@Autowired
	private VehicleRegistrationService vehicleRegistrationService;
	
	@Autowired
	private ResidentService residentService;
	
	@GetMapping({"/", "/list"})
	public String showVehicleRegistration(Model model, @RequestParam(name = "page", defaultValue = "1") int page, Principal principal) {
		
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String role = userDetails.getRole();
		String id = userDetails.getId();
		Resident ri = residentService.findById(id);
		Page<VehicleRegistration> listVehicleRegistration = null;
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		if("ROLE_RESIDENT".equalsIgnoreCase(role)) {
			listVehicleRegistration = vehicleRegistrationService.allVehicleRegistration(ri.getIdApartment().getIdApartment(), pageRequest);
		}else {
		 listVehicleRegistration = vehicleRegistrationService.allVehicleRegistration(pageRequest);
		}
	
		
		int totalPages;
		if (listVehicleRegistration.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listVehicleRegistration.getTotalPages();
		}
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listVehicleRegistration", listVehicleRegistration.getContent());
		return "/VehicleRegistration/listVehicleRegistration";
	}
	
	@GetMapping("/search")
	public String searchVehicleRegistration(Model model, @RequestParam(name = "searchKeyword", defaultValue = "") String search, @RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<VehicleRegistration> allVehicleRegistration = vehicleRegistrationService.seachVehicleRegistration(pageRequest, search);
		
		int totalPages;
		if(allVehicleRegistration.getTotalPages() < 1) {
			totalPages = 1;
		}else {
			totalPages = allVehicleRegistration.getTotalPages();
		}
		
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", search);
		model.addAttribute("listVehicleRegistrationPA", allVehicleRegistration.getContent());
		
		if (search.equalsIgnoreCase("")) {
			model.addAttribute("listVehicleRegistration", vehicleRegistrationService.findaVehicleRegi());
		} else {
			model.addAttribute("listVehicleRegistration", vehicleRegistrationService.findByKeyword(search));
		}
		
		return "/VehicleRegistration/listVehicleRegistration";
	}
	
	
	@GetMapping("/add")
	public String createVehicleRegistration(Model model, Principal principal) {
		
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String role = userDetails.getRole();
		String id = userDetails.getId();
		Resident ri = residentService.findById(id);
		if("ROLE_RESIDENT".equalsIgnoreCase(role)) {
			model.addAttribute("vehicleregi", new VehicleRegistration());
			model.addAttribute("listapa", vehicleRegistrationService.allEmployee(ri.getIdApartment().getIdApartment()));
			model.addAttribute("listfee", vehicleRegistrationService.findaFeeType());
		}else {
			model.addAttribute("vehicleregi", new VehicleRegistration());
			model.addAttribute("listapa", vehicleRegistrationService.findaApartment());
			model.addAttribute("listfee", vehicleRegistrationService.findaFeeType());
		}
		return "/VehicleRegistration/CreateVehicleRegistration";
	}
	
	@PostMapping("/save")
	public String saveVehicleRegistration(Model model,@Valid @ModelAttribute("vehicleregi") VehicleRegistration vehicleRegistration, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("listapa", vehicleRegistrationService.findaApartment());
			model.addAttribute("listfee", vehicleRegistrationService.findaFeeType());
			return "/VehicleRegistration/CreateVehicleRegistration";
		}
		vehicleRegistrationService.saveVehicleRegistration(vehicleRegistration);
		return "redirect:/vehicleRegistration/list";
	}
	
	@GetMapping("/delete")
	public String deleteVehicleRegistration(@RequestParam("idvehicle") String id) {
		vehicleRegistrationService.deleteVehicleRegistration(id);
		return "redirect:/vehicleRegistration/list";
	}

	@GetMapping("/edit")
	public String editVehicleRegistration(Model model, @RequestParam("idvehicle") String id, Principal principal) {
		model.addAttribute("listapa", vehicleRegistrationService.findaApartment());
		model.addAttribute("listfee", vehicleRegistrationService.findaFeeType());
		model.addAttribute("vehicleregi", vehicleRegistrationService.findaById(id));
		return "/VehicleRegistration/CreateVehicleRegistration";
	}
	
	@PostMapping("/update")
	public String updateVehicleRegistration(@ModelAttribute("idregiresi") VehicleRegistration vehicleRegistration) {
		vehicleRegistrationService.updateVehicleRegistration(vehicleRegistration);
		return "redirect:/vehicleRegistration/list";
	}
	
}
