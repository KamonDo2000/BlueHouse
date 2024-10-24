package com.fa.BlueHouse.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fa.BlueHouse.authen.model.AccountDTO;
import com.fa.BlueHouse.entities.Resident;
import com.fa.BlueHouse.entities.form.Report;
import com.fa.BlueHouse.entities.img.ImgReport;
import com.fa.BlueHouse.services.ReportService;
import com.fa.BlueHouse.services.ResidentService;

@Controller
@RequestMapping(path = "/Form/Report/")
public class ReportController {
	@Autowired
	ReportService reportService;
	@Autowired
	ResidentService residentService;
	private final String uploadDirReport = "E:\\TaiLieu\\Mock project\\report\\img";
	@GetMapping("list")
	public String showAll(@RequestParam(name = "page", defaultValue = "1") int page, Model model, Principal principal) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Report> listAll = null;
		if("ROLE_ADMIN".equalsIgnoreCase(userDetails.getRole()) || "ROLE_MANAGE".equalsIgnoreCase(userDetails.getRole())) {
			listAll = reportService.showAll(pageRequest);
		}else if("ROLE_RESIDENT".equalsIgnoreCase(userDetails.getRole())) {
			listAll = reportService.findRPByResidentId(userDetails.getId(), pageRequest);
		}else {
			return "redirect:/";
		}
		model.addAttribute("currentPage", page);
		int totalPages ;
		if(listAll.getTotalPages() < 1) {
			totalPages = 1 ;
		}else {
			totalPages = listAll.getTotalPages();
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listAll", listAll.getContent());
		return "Form/Report/list";
	}
	@GetMapping("showAdd")
	public String showAdd(Principal principal, Model model) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String id = userDetails.getId();
		Resident resident = residentService.findById(id);
		Report form = new Report();	
		form.setResident(resident);
		model.addAttribute("form", form);
		return "Form/Report/add";
	}
	
	@PostMapping("add")
	public String save(
			@ModelAttribute(name = "form") Report form,
			BindingResult bindingResult,
			 @RequestParam("files") MultipartFile[] files,
			 RedirectAttributes redirectAttributes) {
		List<ImgReport> images = new ArrayList<>();
		for (MultipartFile file : files) {
	        if (file != null && !file.isEmpty()) {
	            try {
	            	String fileName = file.getOriginalFilename();
	                Path path = Paths.get(uploadDirReport + File.separator + fileName);
	                Files.write(path, file.getBytes());

	                ImgReport image = new ImgReport();
	                image.setImagePath(fileName);
	                image.setReport(form);
	                images.add(image);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		form.setImgReports(images);
		form.setIdForm(reportService.generateNewId());
		form.setStatus("Send");
		form.setDateSent(new Date());
		reportService.save(form);
		return "redirect:list";
		
	}
	@GetMapping("showDetail")
	public String detail(Model model, Principal principal,@RequestParam(name ="id") String id) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String role = userDetails.getRole();
		Report form = reportService.findById(id);
		model.addAttribute("form", form);
		model.addAttribute("role", role);
		return "Form/Report/detail";
	}
	@PostMapping("opinion")
	public String opinion(Model model, Principal principal,@RequestParam(name ="idForm") String id,@RequestParam(name ="opinion") String opinion) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String role = userDetails.getRole();
		Report form = reportService.findById(id);
		Resident admin = residentService.findById(userDetails.getId());
		form.setAdmin(admin);
		form.setDateAccept(new Date());
		form.setOpinion(opinion);
		form.setStatus("Accept");
		reportService.save(form);
		model.addAttribute("form", form);
		model.addAttribute("role", role);
		return "Form/Report/detail";
	}
	@GetMapping("search")
	public String search( @RequestParam(name = "searchKeyword", defaultValue = "") String keyword ,@RequestParam(name = "page", defaultValue = "1") int page, Model model, Principal principal) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Report> listAll = null;
		if("ROLE_ADMIN".equalsIgnoreCase(userDetails.getRole()) || "ROLE_MANAGE".equalsIgnoreCase(userDetails.getRole())) {
			listAll = reportService.findByKeyword(pageRequest,keyword);
		}else if("ROLE_RESIDENT".equalsIgnoreCase(userDetails.getRole())) {
			listAll = reportService.findRPByResidentIdAndKeyword(userDetails.getId(), pageRequest,keyword);
		}else {
			return "redirect:/";
		}
		model.addAttribute("currentPage", page);
		int totalPages ;
		if(listAll.getTotalPages() < 1) {
			totalPages = 1 ;
		}else {
			totalPages = listAll.getTotalPages();
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listAll", listAll.getContent());
		return "Form/Report/list";
	}
	
}
