package com.fa.BlueHouse.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fa.BlueHouse.authen.model.AccountDTO;
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.entities.Resident;
import com.fa.BlueHouse.services.EmployeeService;
import com.fa.BlueHouse.services.ResidentService;

@Controller
public class HomePageController {
	@Autowired
	EmployeeService employeeService;
	@Autowired
	ResidentService residentService;
	@GetMapping("/")
	public String showLogin(Principal principal, Model model) {

		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		if("ROLE_RESIDENT".equalsIgnoreCase(userDetails.getRole()) || "ROLE_ADMIN".equalsIgnoreCase(userDetails.getRole())) {
			Resident resident = residentService.findById(userDetails.getId());
			model.addAttribute("userDetails", resident);
		}
		if("ROLE_MANAGE".equalsIgnoreCase(userDetails.getRole()) || "ROLE_EMPLOYEE".equalsIgnoreCase(userDetails.getRole())) {
			Employee employee = employeeService.findById(userDetails.getId());
			model.addAttribute("userDetails", employee);
		}
		System.err.println(userDetails.getRole());
		model.addAttribute("role", userDetails.getRole());
		return "HomePage";
	}

}
