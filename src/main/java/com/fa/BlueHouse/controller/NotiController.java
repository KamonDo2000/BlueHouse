package com.fa.BlueHouse.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fa.BlueHouse.authen.model.AccountDTO;
import com.fa.BlueHouse.entities.Notification;
import com.fa.BlueHouse.entities.Receiver;
import com.fa.BlueHouse.services.EmployeeService;
import com.fa.BlueHouse.services.EventService;
import com.fa.BlueHouse.services.NotiService;
import com.fa.BlueHouse.services.ParticipantService;
import com.fa.BlueHouse.services.ResidentService;

@Controller
@RequestMapping(path = "notification")
public class NotiController {
	private final String uploadDirRequest = "E:\\imgNoti";
	private Notification notificationSend;

	@Autowired
	private NotiService notiService;

	@Autowired
	private EmployeeService eService;

	@Autowired
	private ResidentService rService;

	@Autowired
	private ParticipantService partiService;

	@Autowired
	private EventService eventService;

	@GetMapping("viewSendDetail")
	public String viewSendDetail(@RequestParam(name = "idNoti", defaultValue = "") String idNoti, Model model,
			@RequestParam(name = "lastPath") String lastPath) {
		Notification noti = notiService.findNotiByID(idNoti);

		model.addAttribute("lastPath", lastPath);
		model.addAttribute("post", noti);

		return "Notifications/viewSendingNoti";
	}

	@GetMapping("viewDetail")
	public String viewDetail(@RequestParam(name = "idNoti", defaultValue = "") String idNoti, Model model,
			@RequestParam(name = "lastPath") String lastPath,
			@RequestParam(name = "idReceiver", defaultValue = "") Integer idReceiver, Principal principal) {
		AccountDTO auth = (AccountDTO) ((Authentication) principal).getPrincipal();
		Receiver recei = notiService.findReceiByID(idReceiver);
		recei.setStatus(1);
		notiService.saveRecei(recei);

		Notification noti = notiService.findNotiByID(idNoti);

		String eventID = null;
		try {
			eventID = noti.getTypeNote().replace("KeyEvent~", "");
		} catch (Exception e) {
			eventID = "";
		}

		boolean isExist = false;
		if (noti.getTypeNote() != null && eventService.findById(eventID) != null) {
			isExist = partiService.isExist(eventID, auth.getId());
		}

		model.addAttribute("lastPath", lastPath);
		model.addAttribute("post", noti);
		model.addAttribute("eventID", eventID);
		model.addAttribute("isExist", isExist);

		return "Notifications/viewNoti";
	}

//	==================================================

	@GetMapping("listSeen")
	public String listSeen(Principal principal, Model model,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		AccountDTO auth = (AccountDTO) ((Authentication) principal).getPrincipal();

		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);

		Page<Receiver> listNoti = notiService.findByIDSeen(pageRequest, auth.getId());

		int totalPages;
		if (listNoti.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listNoti.getTotalPages();
		}

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listReceiver", listNoti);
		return "Notifications/listSeenNoti";
	}

	@GetMapping("/searchSeen")
	public String searchAll(@RequestParam(name = "searchKeyword", defaultValue = "") String keyword,
			@RequestParam(name = "page", defaultValue = "1") int page, Model model, Principal principal) {
		AccountDTO auth = (AccountDTO) ((Authentication) principal).getPrincipal();

		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Receiver> listNoti = notiService.findByIDSeenKey(pageRequest, auth.getId(), keyword);

		int totalPages;
		if (listNoti.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listNoti.getTotalPages();
		}

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listReceiver", listNoti.getContent());

		if (keyword.equalsIgnoreCase("")) {
			model.addAttribute("listReceiver", notiService.findByIDSeen(pageRequest, auth.getId()));
		} else {
			model.addAttribute("listReceiver", notiService.findByIDSeenKey(pageRequest, auth.getId(), keyword));
		}

		return "Notifications/listSeenNoti";

	}

//	==================================================================

	@GetMapping("listSend")
	public String listSend(Principal principal, Model model,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		AccountDTO auth = (AccountDTO) ((Authentication) principal).getPrincipal();

		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Notification> listNoti = notiService.findByIDSend(pageRequest, auth.getId());

		int totalPages;
		if (listNoti.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listNoti.getTotalPages();
		}

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listNoti", listNoti);

		return "Notifications/listSendNoti";
	}

	@GetMapping("/searchSend")
	public String searchSend(@RequestParam(name = "searchKeyword", defaultValue = "") String keyword,
			@RequestParam(name = "page", defaultValue = "1") int page, Model model, Principal principal) {
		AccountDTO auth = (AccountDTO) ((Authentication) principal).getPrincipal();

		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Notification> listNoti = notiService.findByIDSendKey(pageRequest, auth.getId(), keyword);

		int totalPages;
		if (listNoti.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listNoti.getTotalPages();
		}

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("searchKeyword", keyword);
		model.addAttribute("listNoti", listNoti);

		if (keyword.equalsIgnoreCase("")) {
			model.addAttribute("listNoti", notiService.findByIDSend(pageRequest, auth.getId()));
		} else {
			model.addAttribute("listNoti", notiService.findByIDSendKey(pageRequest, auth.getId(), keyword));
		}

		return "Notifications/listSendNoti";

	}

//	=======================================================================

	/**
	 * Điều hướng tới trang tạo noti
	 * 
	 * @return model.addAttribute("Notification", new Notification());
	 */
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("Notification", new Notification());
		return "Notifications/createNoti";
	}

	/**
	 * Chuẩn bị một noti để lưu
	 * 
	 * @return notificationSend
	 */
	@PostMapping("save")
	public String save(Model model, @ModelAttribute("noti") Notification noti,
			@RequestParam("imgNotification") MultipartFile file, RedirectAttributes redirectAttributes) {

		if (file != null) {
			try {
				String fileName = file.getOriginalFilename();
				Path path = Paths.get(uploadDirRequest + File.separator + fileName);
				Files.write(path, file.getBytes());
				noti.setAttachment(fileName);
			} catch (IOException e) {
				noti.setAttachment(null);
			}
		}

		noti.setDate(LocalDate.now());
		noti.setTime(LocalTime.now());

		notificationSend = noti;

		return "redirect:sending";
	}

	/**
	 * Điều hướng tới trang thêm người nhận
	 * 
	 * @return
	 */
	@GetMapping("sending")
	public String sending(Model model) {
		model.addAttribute("employee", eService.allEmployee());
		model.addAttribute("resident", rService.findallResident());
		return "Notifications/choosenSelect";
	}

	/**
	 * Chuẩn bị đối tượng để lưu Lưu vào database
	 * 
	 * @return
	 */
	@GetMapping("saveReceiver")
	public String saveReceiver(Principal principal,
			@RequestParam(name = "ListValue", defaultValue = "") String listRecei,
			@RequestParam(name = "choose", defaultValue = "0") String choose) {

		String[] listReceiver = listRecei.split(",");

		notiService.saveNotificationAndReceiver(choose, notificationSend, principal, listReceiver);

		return "redirect:add";
	}

}
