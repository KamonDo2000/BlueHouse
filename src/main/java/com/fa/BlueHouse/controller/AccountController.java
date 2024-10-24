package com.fa.BlueHouse.controller;

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

import com.fa.BlueHouse.authen.model.Account;
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.entities.Resident;
import com.fa.BlueHouse.services.AccountService;
import com.fa.BlueHouse.services.AdministratorsService;
import com.fa.BlueHouse.services.EmployeeService;
import com.fa.BlueHouse.services.ResidentService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(path = "/account")
public class AccountController {

	@Autowired
	private AccountService aService;

	@Autowired
	private EmployeeService eService;

	@Autowired
	private ResidentService rService;

	@Autowired
	private AdministratorsService adminService;

	@GetMapping({ "/", "/list" })
	public String showAll(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {

		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Account> listAccount = aService.allAccount(pageRequest);

		int totalPages;
		if (listAccount.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listAccount.getTotalPages();
		}

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listPaggin", listAccount.getContent());
		return "Account/list";
	}

	@GetMapping("/search")
	public String searchAll(@RequestParam(name = "searchKeyword", defaultValue = "") String keyword,
			@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Account> listAccount = aService.findByKeyword(pageRequest, keyword);

		int totalPages;
		if (listAccount.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listAccount.getTotalPages();
		}

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listPaggin", listAccount.getContent());

		return "Account/list";
	}

	@GetMapping("/delete")
	public String deleteAcc(@RequestParam(name = "userName") String userName) {
		aService.deleteByUserName(userName);
		return "redirect:/account/list";
	}

	@GetMapping("/add")
	public String addAcc(Model model) {

		model.addAttribute("listEmp", aService.getEmpNotInAccount());
		model.addAttribute("listReci", aService.getReciNotInAccount());
		model.addAttribute("account", new Account());
		model.addAttribute("resident", new Resident());
		model.addAttribute("employee", new Employee());
		return "Account/addEdit";
	}

	@PostMapping("/save")
	public String saveAcc(@Valid @ModelAttribute("account") Account account, BindingResult result,
			@ModelAttribute("employee") Employee emp, @ModelAttribute("resident") Resident resi, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("listEmp", aService.getEmpNotInAccount());
			model.addAttribute("listReci", aService.getReciNotInAccount());
			model.addAttribute("resident", new Resident());
			model.addAttribute("employee", new Employee());
			return "Account/addEdit";
		}

		if (account.getRole() == 1 || account.getRole() == 3) {
			for (Resident e : aService.getReciNotInAccount()) {
				if (e.getIdResident().equals(resi.getIdResident())) {
					resi = e;
					emp = null;
					break;
				}
			}
		} else {
			for (Employee e : aService.getEmpNotInAccount()) {
				if (e.getEmployeeID().equals(emp.getEmployeeID())) {
					emp = e;
					resi = null;
					break;
				}
			}
		}

		account.setEmployee(emp);
		account.setResident(resi);

		if (emp != null && emp.getDuty().equalsIgnoreCase("Manager")) {
			account.setRole(2);
		} else if (emp != null && emp.getDuty().equalsIgnoreCase("Employee")) {
			account.setRole(4);
		} else if (resi != null && adminService.isExits(resi.getIdResident())) {
			account.setRole(1);
		} else {
			account.setRole(3);
		}

		aService.saveAccount(account);
		return "redirect:/account/list";
	}

	private Account accountEdit;
	private String idResi;
	private String idEmp;

	@GetMapping("/edit")
	public String editAcc(Model model, @RequestParam(name = "userName") String userName) {
		Account acc = aService.findByUserName(userName);

		model.addAttribute("account", acc);
		if (acc.getResident() != null) {
			idResi = acc.getResident().getIdResident();
			idEmp = "";
		} else {
			idEmp = acc.getEmployee().getEmployeeID();
			idResi = "";
		}

		accountEdit = acc;

		return "Account/update";
	}

	@PostMapping("/update")
	public String updateAcc(Model model, @Valid @ModelAttribute("account") Account account, BindingResult result) {

		if (result.hasErrors()) {
			model.addAttribute("resident", account.getResident());
			model.addAttribute("employee", account.getEmployee());
			return "Account/update";
		}

		account.setUsername(accountEdit.getUsername());
		account.setRole(accountEdit.getRole());
		account.setPassword(account.getPassword());
		account.setActive(account.getActive());
		account.setEmployee(eService.findById(idEmp));
		account.setResident(rService.findById(idResi));

		aService.saveAccount(account);
		return "redirect:/account/list";
	}

	private Account changePassAccount = new Account();

	@GetMapping("/changePass")
	public String changePass(Model model, @RequestParam(name = "userName") String userName) {
		Account acc = aService.findByUserName(userName);
		changePassAccount = acc;
		model.addAttribute("account", acc);
		return "Account/changePass";
	}

	@PostMapping("/updatePass")
	public String updatePass(@Valid @ModelAttribute("account") Account account, BindingResult result) {
		changePassAccount.setPassword(account.getPassword());

		if (result.hasErrors()) {
			account.setUsername(changePassAccount.getUsername());
			return "Account/changePass";
		}

		aService.saveAccount(changePassAccount);
		return "redirect:/";
	}

}
