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

import com.fa.BlueHouse.entities.Position;
import com.fa.BlueHouse.services.PositionService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/Position")
public class PositionController {

	@Autowired
	private PositionService position;
	
	@RequestMapping("/createposition")
	public String createPosition(Model model) {
		String id = position.generateNewId();
		model.addAttribute("position", new Position(id));
		return "Position/createPosition";
	}
	@PostMapping("/saveposition")
	public String savePosition(Model model,@Valid  @ModelAttribute("position") Position posi,BindingResult result) {
		if(result.hasErrors()) {
			return "/Position/createPosition";
		}else {
			position.savePosition(posi);
			return "redirect:/Position/showlistposition";
		}
	}
	@GetMapping("/showlistposition")
	public String showlistPosition(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Position> listpositon = position.findpagePosition(pageRequest);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", listpositon.getTotalPages());
		model.addAttribute("listPosition", listpositon.getContent());
		return "Position/listPosition";
	}
	@GetMapping("/editposition")
	public String editPosition(Model model, @RequestParam("idPosition") String id) {
	   model.addAttribute("position", position.findById(id));
	   return "Position/updatePosition";
	}
	
	@GetMapping("/deleteposition")
	public String deletePosition(Model model, @RequestParam("idPosition") String id) {
		position.deletePosition(id);
		return "redirect:/Position/showlistposition";
	}
	
	@GetMapping("/searchposition")
	public String searchPosition(Model model, @RequestParam(name = "page", defaultValue = "1") int page ,@RequestParam(name = "searchKeyword", defaultValue = "") String keyword) {
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Position> listpositon = position.searchPosition(keyword, pageRequest);
		model.addAttribute("currentPage", page);
		int totalPages ;
		if(listpositon.getTotalPages() < 1) {
			totalPages = 1 ;
		}else {
			totalPages = listpositon.getTotalPages();
		}
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listPosition", listpositon.getContent());
		return "Position/listPosition";
	}
}
