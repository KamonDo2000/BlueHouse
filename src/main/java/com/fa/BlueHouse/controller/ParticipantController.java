package com.fa.BlueHouse.controller;

import java.security.Principal;
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

import com.fa.BlueHouse.authen.model.AccountDTO;
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.entities.Event;
import com.fa.BlueHouse.entities.Participants;
import com.fa.BlueHouse.entities.Resident;
import com.fa.BlueHouse.services.EmployeeService;
import com.fa.BlueHouse.services.EventService;
import com.fa.BlueHouse.services.ParticipantService;
import com.fa.BlueHouse.services.ResidentService;

@Controller
@RequestMapping(path = "/event")
public class ParticipantController {

	@Autowired
	private ParticipantService partiService;

	@Autowired
	private EventService eventService;

	@Autowired
	private ResidentService rService;

	@Autowired
	private EmployeeService eService;

	@GetMapping("/confirm")
	public String confirmJoin(Model model, @RequestParam("eventID") String eventID, Principal principal) {
		AccountDTO auth = (AccountDTO) ((Authentication) principal).getPrincipal();
		Employee partiEmp = eService.findById(auth.getId());
		Resident partiResi = rService.findById(auth.getId());

		Event event = eventService.findById(eventID);
		event.setNumberOfParticipants(event.getNumberOfParticipants() + 1);

		String idParti = event.getIdEvent() + "_parti_" + LocalTime.now();
		Participants parti = new Participants(idParti, event, partiEmp, partiResi, "participants", null);
		partiService.saveParticipant(parti);

		return "redirect:/notification/listSeen";
	}

	@GetMapping("/detail")
	public String detail(Model model, @RequestParam("eventID") String eventID,
			@RequestParam(name = "page", defaultValue = "1") int page, Principal principal) {
		AccountDTO auth = (AccountDTO) ((Authentication) principal).getPrincipal();

		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Participants> listParticipants = partiService.findAllPartiByEvent(eventID, pageRequest);

		int totalPages;
		if (listParticipants.getTotalPages() < 1) {
			totalPages = 1;
		} else {
			totalPages = listParticipants.getTotalPages();
		}

		int author = 0;
		for (Event e : eventService.getAllEventMyCreate(auth.getId())) {
			if (e.getIdEvent().equals(eventID)) {
				author = 1;
				break;
			}
		}

		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listParticipants", listParticipants.getContent());
		model.addAttribute("author", author);

		return "Event/viewDetailEvent";
	}

	@GetMapping("/Participants/edit")
	public String editEmp(Model model, @RequestParam(name = "id") String id) {

		Participants participants = partiService.findByID(id);

		model.addAttribute("participants", participants);

		return "Event/editParticipants";
	}

	@PostMapping("/Participants/update")
	public String update(@ModelAttribute("participants") Participants partiNew) {
		Participants partiOld = partiService.findByID(partiNew.getIdParticipants());

		if (partiNew.getMission() != "") {
			partiOld.setMission(partiNew.getMission());
		}else {
			partiOld.setMission("participant");
		}

		partiOld.setNote(partiNew.getNote());

		partiService.saveParticipant(partiOld);
		String path = "/event/detail?eventID=" + partiOld.getIDEvent().getIdEvent();
		return "redirect:" + path;
	}

	@GetMapping("/Participants/delete")
	public String delete(@RequestParam(name = "id") String id) {
		Participants participants = partiService.findByID(id);

		Event event = participants.getIDEvent();
		event.setNumberOfParticipants(event.getNumberOfParticipants() - 1);
		eventService.saveEvent(event);

		partiService.deleteByID(id);
		String path = "/event/detail?eventID=" + event.getIdEvent();
		return "redirect:" + path;
	}

}
