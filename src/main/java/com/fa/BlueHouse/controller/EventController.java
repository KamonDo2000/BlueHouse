package com.fa.BlueHouse.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
import com.fa.BlueHouse.entities.Event;
import com.fa.BlueHouse.entities.Notification;
import com.fa.BlueHouse.entities.Participants;
import com.fa.BlueHouse.services.EmployeeService;
import com.fa.BlueHouse.services.EventService;
import com.fa.BlueHouse.services.NotiService;
import com.fa.BlueHouse.services.ParticipantService;
import com.fa.BlueHouse.services.ResidentService;

@Controller
@RequestMapping(path = "/event")
public class EventController {
	private final String uploadDirRequest = "E:\\imgEvent";

	@Autowired
	private EventService eventService;

	@Autowired
	private ResidentService rService;

	@Autowired
	private EmployeeService eService;

	@Autowired
	private NotiService notiService;

	@Autowired
	private ParticipantService partiService;

	@GetMapping({ "/", "/list" })
	public String eventList(@RequestParam(name = "page", defaultValue = "1") int page, Model model,
			Principal principal) {
		AccountDTO auth = (AccountDTO) ((Authentication) principal).getPrincipal();

		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Event> listEvent = eventService.getAllEventMyCreate(auth.getId(), pageRequest);

		int totalPages;
		if (listEvent.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listEvent.getTotalPages();
		}

		for (Event e : listEvent.getContent()) {
			e.setNumberOfParticipants(partiService.findListPartiByEvent(e.getIdEvent()).size());
		}

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listEvent", listEvent.getContent());

		return "Event/list";
	}

	@GetMapping("/listJoin")
	public String eventListMyJoin(@RequestParam(name = "page", defaultValue = "1") int page, Model model,
			Principal principal) {
		AccountDTO auth = (AccountDTO) ((Authentication) principal).getPrincipal();

		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Event> listEvent = partiService.findEventMyJoin(auth.getId(), pageRequest);

		int totalPages;
		if (listEvent.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listEvent.getTotalPages();
		}

		for (Event e : listEvent.getContent()) {
			e.setNumberOfParticipants(partiService.findListPartiByEvent(e.getIdEvent()).size());
		}

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listEvent", listEvent.getContent());

		return "Event/listMyJoin";
	}

	@GetMapping("/add")
	public String newEvent(Model model) {
		model.addAttribute("employee", eService.allEmployee());
		model.addAttribute("resident", rService.findallResident());
		model.addAttribute("event", new Event());
		return "Event/newEvent";
	}

	@PostMapping("/save")
	public String eventSave(@ModelAttribute("employee") Event event, Principal principal, Model model,
			@RequestParam("Choose") String Choose, @RequestParam("valueSend") String valueSend,
			@RequestParam("imgEvent") MultipartFile file, RedirectAttributes redirectAttributes) {

		if (file != null) {
			try {
				String fileName = file.getOriginalFilename();
				Path path = Paths.get(uploadDirRequest + File.separator + fileName);
				Files.write(path, file.getBytes());
				event.setAttachment(fileName);
			} catch (IOException e) {
				event.setAttachment(null);
			}
		}

		AccountDTO auth = (AccountDTO) ((Authentication) principal).getPrincipal();
		event.setIDOganizer(rService.findById(auth.getId()));

		String idEvent = LocalTime.now() + auth.getId() + event.getNameEvent().trim().replace(" ", "");
		event.setIdEvent(idEvent);

		eventService.saveEvent(event);

		Notification noti = new Notification();
		String[] listReceiver = valueSend.split(",");

		noti.setTitle("Event: " + event.getNameEvent());
		noti.setContentNoti("<figure class=\"table\"><table><tbody><tr><td><p><strong>Notice about event organization: "
				+ event.getNameEvent()
				+ "</strong></p></td></tr></tbody></table></figure><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<strong> BLUE HOUSE PREMIUM APARTMENTS</strong><br>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<strong>Name: </strong>"
				+ auth.getName()
				+ "<br>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<strong> Phone: </strong>"
				+ auth.getPhoneNumber() + "<br> Please Announce the upcoming <strong>" + event.getNameEvent()
				+ "</strong> event. At <strong>" + event.getStartTime() + "</strong> on <strong>" + event.getStartDate()
				+ "</strong> Venue at <strong>" + event.getLocation()
				+ "</strong>. The Event takes place until <strong>" + event.getEndTime() + "</strong> on <strong>"
				+ event.getEndDate()
				+ ".</strong><br>&nbsp;</p><p>Looking forward to your presence. Please confirm your participation soon and stay tuned for information.</p><p><br>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Thank you and Warmest Regards.</p><p>&nbsp;</p>");
		noti.setDate(LocalDate.now());
		noti.setTime(LocalTime.now());
		noti.setAttachment(event.getAttachment());
		noti.setTypeNote("KeyEvent~" + idEvent);

		notiService.saveNotificationAndReceiver(Choose, noti, principal, listReceiver);

		return "redirect:list";
	}

	@GetMapping("/delete")
	public String deleteEvent(@RequestParam("eventID") String eventID, Principal principal) {

		List<String> listID = new ArrayList<>();

		for (Participants e : partiService.findListPartiByEvent(eventID)) {
			if (e.getParticipantEmp() != null) {
				listID.add(e.getParticipantEmp().getEmployeeID());
			} else {
				listID.add(e.getParticipantResi().getIdResident());
			}
		}

		String[] listReceiver = listID.toArray(new String[0]);

		Notification noti = new Notification();

		noti.setTitle("Event: " + eventService.findById(eventID).getNameEvent());
		noti.setContentNoti("<p><b>Please note that this event has been canceled and will no longer be held</b></p>");
		noti.setDate(LocalDate.now());
		noti.setTime(LocalTime.now());

		notiService.saveNotificationAndReceiver("Choosen", noti, principal, listReceiver);

		for (Participants e : partiService.findListPartiByEvent(eventID)) {
			partiService.deleteByID(e.getIdParticipants());
		}
		eventService.deleteById(eventID);
		return "redirect:list";
	}

	@GetMapping("/edit")
	public String editEvent(@RequestParam("eventID") String eventID, Model model) {
		model.addAttribute("employee", eService.allEmployee());
		model.addAttribute("resident", rService.findallResident());
		model.addAttribute("event", eventService.findById(eventID));

		return "Event/editEvent";
	}

	@PostMapping("/saveEdit")
	public String eventSaveEdit(@ModelAttribute("employee") Event event, Principal principal, Model model,
			@RequestParam("imgEvent") MultipartFile file, RedirectAttributes redirectAttributes) {

		if (file != null) {
			try {
				String fileName = file.getOriginalFilename();
				Path path = Paths.get(uploadDirRequest + File.separator + fileName);
				Files.write(path, file.getBytes());
				event.setAttachment(fileName);
			} catch (IOException e) {
				event.setAttachment(null);
			}
		}

		AccountDTO auth = (AccountDTO) ((Authentication) principal).getPrincipal();
		event.setIDOganizer(rService.findById(auth.getId()));

		List<String> listID = new ArrayList<>();

		for (Participants e : partiService.findListPartiByEvent(event.getIdEvent())) {
			if (e.getParticipantEmp() != null) {
				listID.add(e.getParticipantEmp().getEmployeeID());
			} else {
				listID.add(e.getParticipantResi().getIdResident());
			}
		}
		String[] listReceiver = listID.toArray(new String[0]);

		event.setNumberOfParticipants(listReceiver.length);

		Notification noti = new Notification();
		noti.setTitle("Change Event: " + event.getNameEvent());
		noti.setContentNoti("<figure class=\"table\"><table><tbody><tr><td><p><strong>Notice about event organization: "
				+ event.getNameEvent()
				+ "</strong></p></td></tr></tbody></table></figure><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<strong> BLUE HOUSE PREMIUM APARTMENTS</strong><br>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<strong>Name: </strong>"
				+ auth.getName()
				+ "<br>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<strong> Phone: </strong>"
				+ auth.getPhoneNumber() + "<br> Please Announce the upcoming <strong>" + event.getNameEvent()
				+ "</strong> event. At <strong>" + event.getStartTime() + "</strong> on <strong>" + event.getStartDate()
				+ "</strong> Venue at <strong>" + event.getLocation()
				+ "</strong>. The Event takes place until <strong>" + event.getEndTime() + "</strong> on <strong>"
				+ event.getEndDate()
				+ ".</strong><br>&nbsp;</p><p>Looking forward to your presence. Please confirm your participation soon and stay tuned for information.</p><p><br>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Thank you and Warmest Regards.</p><p>&nbsp;</p>");
		noti.setDate(LocalDate.now());
		noti.setTime(LocalTime.now());
		noti.setAttachment(event.getAttachment());
		noti.setTypeNote("KeyEvent~" + event.getIdEvent());

		eventService.saveEvent(event);
		notiService.saveNotificationAndReceiver("Choosen", noti, principal, listReceiver);

		return "redirect:list";
	}
}
