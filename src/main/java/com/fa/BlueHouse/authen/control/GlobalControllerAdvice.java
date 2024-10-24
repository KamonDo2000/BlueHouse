package com.fa.BlueHouse.authen.control;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fa.BlueHouse.authen.model.AccountDTO;
import com.fa.BlueHouse.services.NotiService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Autowired
	private NotiService notiService;

	@ModelAttribute
	public void addAttributes(Principal principal, Model model) {
		try {
			AccountDTO auth = (AccountDTO) ((Authentication) principal).getPrincipal();

			model.addAttribute("numberKey", notiService.findNotiUnSeen(auth.getId()).size());
			model.addAttribute("userName", auth.getUsername());
		} catch (Exception e) {
			model.addAttribute("numberKey", 0);
			model.addAttribute("userName", "");
		}

	}
}