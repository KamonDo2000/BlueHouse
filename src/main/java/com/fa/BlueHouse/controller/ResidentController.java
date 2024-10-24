package com.fa.BlueHouse.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fa.BlueHouse.authen.model.Account;
import com.fa.BlueHouse.entities.Apartment;
import com.fa.BlueHouse.entities.Resident;
import com.fa.BlueHouse.services.AccountService;
import com.fa.BlueHouse.services.ResidentService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/Resident")
public class ResidentController {
	private final String uploadResident = "C:\\img\\resident";
	@Autowired
	private ResidentService resident;

	@Autowired
	private AccountService accService;

	@GetMapping("/createresident")
	public String createResident(Model model) {
		String id = resident.generateNewId();
		model.addAttribute("resident", new Resident(id));
		model.addAttribute("listapartment", resident.findallapart());
		return "/Resident/createResident";
	}

	@PostMapping("/saveresident")
	public String saveResident(Model model,@Valid @ModelAttribute Resident resi, BindingResult result, @RequestParam("file")MultipartFile file,
            RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.addAttribute("listapartment", resident.findallapart());
			return "/Resident/createResident";
		}
		if(file != null) {
			  try {
		            // Lưu file xuống ổ D
		            String fileName = file.getOriginalFilename();
		            Path path = Paths.get(uploadResident + File.separator + fileName);
		            Files.write(path, file.getBytes());

		           resi.setProfile(fileName);

		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		}
		resident.saveResident(resi);
		return "redirect:/Resident/showlistresident";
	}

	@GetMapping("/showlistresident")
	public String showlistResident(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Resident> listresi = resident.findpageResident(pageRequest);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", listresi.getTotalPages());
		model.addAttribute("listResident", listresi.getContent());
		return "/Resident/listResident";
	}

	@GetMapping("/searchrisedent")
	public String searchResident(@RequestParam(name = "searchKeyword", defaultValue = "") String keyword, Model model,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Resident> listresi = resident.searchResident(keyword, pageRequest);
		model.addAttribute("currentPage", page);
		int totalPages;
		if (listresi.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listresi.getTotalPages();
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listResident", listresi.getContent());
		return "/Resident/listResident";
	}

	@GetMapping("/deleterisedent")
	public String deleteResident(@RequestParam("idresident") String id) {

		for (Account acc : accService.getAccByResi(id)) {
			accService.deleteByUserName(acc.getUsername());
		}

		resident.deleteResident(id);
		return "redirect:/Resident/showlistresident";
	}

	@GetMapping("/editresident")
	public String editResident(Model model, @RequestParam("idresident") String id) {
		List<Apartment> listresi = resident.findallapart();
		model.addAttribute("listapartment", listresi);
		model.addAttribute("resident", resident.findById(id));
		return "/Resident/updateResident";
	}

	@PostMapping("/saveupdateresident")
	public String saveupdateresident(@ModelAttribute("resident") Resident resi) {
		resident.updateResident(resi);
		return "redirect:/Resident/showlistresident";
	}

}
