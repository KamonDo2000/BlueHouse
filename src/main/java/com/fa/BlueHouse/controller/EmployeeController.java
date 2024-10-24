package com.fa.BlueHouse.controller;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fa.BlueHouse.authen.model.Account;
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.services.AccountService;
import com.fa.BlueHouse.services.EmployeeService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "/employee")
public class EmployeeController {

	private final String uploadEmployee = "D:/Spring-Boot/imgdate";
	@Autowired
	private EmployeeService eService;

	@Autowired
	private AccountService accService;

	@GetMapping({ "/", "/list" })
	public String showAll(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {

		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Employee> listEmployee = eService.allEmployee(pageRequest);

		int totalPages;
		if (listEmployee.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listEmployee.getTotalPages();
		}

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listEmpPaggin", listEmployee.getContent());
		model.addAttribute("listEmp", eService.allEmployee());
		return "Employee/list";
	}

	@GetMapping("/search")
	public String searchAll(@RequestParam(name = "searchKeyword", defaultValue = "") String keyword,
			@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Employee> listEmployee = eService.findByKeyword(pageRequest, keyword);

		int totalPages;
		if (listEmployee.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listEmployee.getTotalPages();
		}

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listEmpPaggin", listEmployee.getContent());

		if (keyword.equalsIgnoreCase("")) {
			model.addAttribute("listEmp", eService.allEmployee());
		} else {
			model.addAttribute("listEmp", eService.findByKeyword(keyword));
		}

		return "Employee/list";

	}

	@GetMapping("/add")
	public String addEmp(Model model) {

		List<Employee> listMService = eService.getManagerByOffice("Services");
		List<Employee> listMEngineering = eService.getManagerByOffice("Engineering ");
		List<Employee> listMEnvironment = eService.getManagerByOffice("Environment");

		model.addAttribute("listMService", listMService);
		model.addAttribute("listMEngineering", listMEngineering);
		model.addAttribute("listMEnvironment", listMEnvironment);

		String id = eService.generateNewId();
		Employee emp = new Employee();
		emp.setEmployeeID(id);

		model.addAttribute("employee", emp);
		return "Employee/addEditEmployee";
	}

	@PostMapping("/save")
	public String saveEmp(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model,
			@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			List<Employee> listMService = eService.getManagerByOffice("Services");
			List<Employee> listMEngineering = eService.getManagerByOffice("Engineering ");
			List<Employee> listMEnvironment = eService.getManagerByOffice("Environment");

			model.addAttribute("listMService", listMService);
			model.addAttribute("listMEngineering", listMEngineering);
			model.addAttribute("listMEnvironment", listMEnvironment);
			return "Employee/addEditEmployee";
		}

		if (file != null) {
			try {
				// Lưu file xuống ổ D
				String fileName = file.getOriginalFilename();
				Path path = Paths.get(uploadEmployee + File.separator + fileName);
				Files.write(path, file.getBytes());

				employee.setProfile(fileName);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		eService.saveEmployee(employee);
		return "redirect:/employee/list";
	}

	@GetMapping("/delete")
	public String deleteEmp(@RequestParam(name = "employeeID") String id) {

		for (Account acc : accService.getAccByEmp(id)) {
			accService.deleteByUserName(acc.getUsername());
		}

		eService.deleteByID(id);
		return "redirect:/employee/list";
	}

	@GetMapping("/edit")
	public String editEmp(Model model, @RequestParam(name = "employeeID") String id) {
		model.addAttribute("employee", eService.findById(id));
		return "Employee/EditEmployee";
	}

	@PostMapping("/update")
	public String updateEmp(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "Employee/EditEmployee";
		}

		eService.saveEmployee(employee);

		for (Account acc : accService.getAccByEmp(employee.getEmployeeID())) {
			if (employee.getDuty().equals("Manager")) {
				acc.setRole(2);
			} else {
				acc.setRole(4);
			}
			accService.saveAccount(acc);
		}

		return "redirect:/employee/list";
	}

}
