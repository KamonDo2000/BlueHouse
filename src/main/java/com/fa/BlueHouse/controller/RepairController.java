package com.fa.BlueHouse.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fa.BlueHouse.authen.model.AccountDTO;
import com.fa.BlueHouse.entities.Assets;
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.entities.Repair;
import com.fa.BlueHouse.entities.form.Request;
import com.fa.BlueHouse.entities.img.ImgRepair;
import com.fa.BlueHouse.services.AssetService;
import com.fa.BlueHouse.services.EmployeeService;
import com.fa.BlueHouse.services.RepairService;
import com.fa.BlueHouse.services.RequestService;

@Controller
@RequestMapping(path = "/Repair/")
public class RepairController {
	private final String uploadDirRepair = "E:\\TaiLieu\\Mock project\\repair\\img";
	@Autowired
	RepairService repairService;
	@Autowired
	AssetService assetService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	RequestService requestService;

	@GetMapping("list")
	public String showAll(@RequestParam(name = "page", defaultValue = "1") int page, Model model, Principal principal) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize,Sort.by(Sort.Direction.DESC, "id"));
		Page<Repair> listAll = repairService.showAll(pageRequest);
		if ("ROLE_MANAGE".equalsIgnoreCase(userDetails.getRole())) {
			listAll = repairService.showAll(pageRequest);
		} else if ("ROLE_EMPLOYEE".equalsIgnoreCase(userDetails.getRole())) {
			listAll = repairService.showAllForEmployee(userDetails.getId(), pageRequest);
		} else {
			return "redirect:/";
		}
		model.addAttribute("currentPage", page);
		int totalPages;
		if (listAll.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listAll.getTotalPages();
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listAll", listAll.getContent());
		return "Repair/list";
	}

	@GetMapping("showListAsset")
	public String showListAsset(Model model) {
		List<Assets> assets = assetService.findAll();
		model.addAttribute("assets", assets);
		return "Form/Request/addAsset";
	}

	@GetMapping("addListAssets")
	public String employeeAccept(@RequestParam(name = "ListValue", defaultValue = "") String idAsset, Model model,
			Principal principal) {
		String[] arrayAssets = idAsset.split(",");
		if ("".equalsIgnoreCase(arrayAssets[0])) {
			return "redirect:showListAsset";
		}
		List<String> listAssets = new ArrayList<>(Arrays.asList(arrayAssets));
		List<Assets> assets = assetService.findByIds(listAssets);
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		Employee employee = employeeService.findById(userDetails.getId());
		Repair repair = new Repair();
		repair.setId(repairService.generateNewId());
		repair.setAssets(assets);
		repair.setDateRepair(new Date());
		repair.setEmployee(employee);
		repairService.save(repair);
		model.addAttribute("repair", repair);
		return "redirect:list";
	}
	@GetMapping("showDetail")
	public String showDetail(@RequestParam(name = "idRepair") String id, Model model, Principal principal) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String role = userDetails.getRole();
		Repair repair = repairService.findById(id);
		model.addAttribute("repair", repair);
		model.addAttribute("role", role);
		return "Repair/detail";
	}
	@GetMapping("accept")
	public String accept(@RequestParam(name = "idRepair") String id, Model model, Principal principal) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String role = userDetails.getRole();
		Repair repair = repairService.findById(id);
		repair.setDateRepair(new Date());
		repairService.save(repair);
		model.addAttribute("repair", repair);
		model.addAttribute("role", role);
		return "Repair/detail";
	}
	@PostMapping("completed")
	public String completed(@RequestParam("files") MultipartFile[] files, RedirectAttributes redirectAttributes,
			@RequestParam(name = "idForm") String idForm, Model model, Principal principal) {
		Repair form = repairService.findById(idForm);
		Request request = form.getRequest();
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String role = userDetails.getRole();
		List<ImgRepair> images = new ArrayList<>();
		  for (MultipartFile file : files) {
		        if (file != null && !file.isEmpty()) {
		            try {
		            	String fileName = file.getOriginalFilename();
		                Path path = Paths.get(uploadDirRepair + File.separator + fileName);
		                Files.write(path, file.getBytes());
		                ImgRepair image = new ImgRepair();
		                image.setImagePath(fileName);
		                image.setRepair(form);
		                images.add(image);
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		    }
		  if(request != null) {
			  request.setStatus("Completed");
			  requestService.save(request);
		  }
		
		form.setDateCompleted(new Date());
		form.setImgRepairs(images);
		repairService.save(form);
		
		model.addAttribute("role", role);
		model.addAttribute("repair", form);
		return "Repair/detail";

	}
}
