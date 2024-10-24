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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fa.BlueHouse.authen.model.Account;
import com.fa.BlueHouse.authen.model.AccountDTO;
import com.fa.BlueHouse.entities.Assets;
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.entities.Notification;
import com.fa.BlueHouse.entities.Repair;
import com.fa.BlueHouse.entities.Resident;
import com.fa.BlueHouse.entities.form.Report;
import com.fa.BlueHouse.entities.form.Request;
import com.fa.BlueHouse.entities.img.ImgRepair;
import com.fa.BlueHouse.entities.img.ImgRequest;
import com.fa.BlueHouse.services.AccountService;
import com.fa.BlueHouse.services.AssetService;
import com.fa.BlueHouse.services.EmployeeService;
import com.fa.BlueHouse.services.NotiService;
import com.fa.BlueHouse.services.RepairService;
import com.fa.BlueHouse.services.RequestService;
import com.fa.BlueHouse.services.ResidentService;

@Controller
@RequestMapping(path = "/Form/Request/")
public class RequestController {
	private final String uploadDirRequest = "E:\\TaiLieu\\Mock project\\request\\img";
	private final String uploadDirRepair = "E:\\TaiLieu\\Mock project\\repair\\img";
	@Autowired
	RequestService requestService;
	@Autowired
	ResidentService residentService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	RepairService repairService;
	@Autowired
	AssetService assetService;
	@Autowired
	NotiService notiService;
	@Autowired
	AccountService accountService;

	@GetMapping("list")
	public String showAll(@RequestParam(name = "page", defaultValue = "1") int page, Model model, Principal principal) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "dateSent"));
		Page<Request> listAll = null;
		if ("ROLE_MANAGE".equalsIgnoreCase(userDetails.getRole())) {
			listAll = requestService.showAll(pageRequest);
		} else if ("ROLE_EMPLOYEE".equalsIgnoreCase(userDetails.getRole())) {
			listAll = requestService.showAllForEmployee(userDetails.getId(), pageRequest);
		} else {
			listAll = requestService.showAllForResident(userDetails.getId(), pageRequest);
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
		return "Form/Request/list";
	}

	@GetMapping("showAdd")
	public String showAdd(Principal principal, Model model) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String id = userDetails.getId();
		Resident resident = residentService.findById(id);
		Request form = new Request();
		form.setResident(resident);
		model.addAttribute("form", form);
		return "Form/Request/add";
	}

	@PostMapping("add")
	public String save(@ModelAttribute(name = "form") Request form, BindingResult bindingResult, Principal principal,
			@RequestParam("files") MultipartFile[] files, RedirectAttributes redirectAttributes) {
		List<ImgRequest> images = new ArrayList<>();
		for (MultipartFile file : files) {
			if (file != null && !file.isEmpty()) {
				try {
					String fileName = file.getOriginalFilename();
					Path path = Paths.get(uploadDirRequest + File.separator + fileName);
					Files.write(path, file.getBytes());

					ImgRequest image = new ImgRequest();
					image.setImagePath(fileName);
					image.setRequest(form);
					images.add(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		form.setImgRequests(images);
		form.setIdForm(requestService.generateNewId());
		form.setStatus("Send");
		form.setDateSent(new Date());

		requestService.save(form);

		Notification noti = new Notification();
		noti.setTitle("Request from resident");
		noti.setContentNoti( "You have 1 request from resident: " 
	               + form.getResident().getIdResident() 
	               + " - " + form.getResident().getNameResident() 
	               + " <a href=\"/Form/Request/showDetail?id=" + form.getIdForm() + "\">More</a>");
		noti.setDate(LocalDate.now());
		noti.setTime(LocalTime.now());
		List<String> name = new ArrayList<>();
		for(Account acc : accountService.findByRole2()) {
			name.add(acc.getEmployee().getEmployeeID());
		}
		String[] nguoiNhan = name.toArray(new String[0]);
		notiService.saveNotificationAndReceiver("Choosen", noti, principal, nguoiNhan);

		return "redirect:list";
	}

	@GetMapping("showDetail")
	public String showDetail(@RequestParam(name = "id") String id, Model model, Principal principal) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String role = userDetails.getRole();
		Request form = requestService.findById(id);
		List<Employee> employees = employeeService.allEmployee();
		List<Assets> assets = assetService.findAll();
		model.addAttribute("assets", assets);
		model.addAttribute("employees", employees);
		model.addAttribute("form", form);
		model.addAttribute("role", role);
		return "Form/detail";
	}

	@PostMapping("deny")
	public String denyRequest(@RequestParam(name = "idForm") String idForm,
			@RequestParam(name = "reasonDeny") String reason, Model model, Principal principal) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String id = userDetails.getId();
		Employee employee = employeeService.findById(id);
		Request form = requestService.findById(idForm);
		form.setReason(reason);
		form.setStatus("Denied");
		form.setEmployee(employee);
		form.setDateAccept(new Date());
		requestService.save(form);
		model.addAttribute("form", form);
		Notification noti = new Notification();
		noti.setTitle("Your request is denied");
		noti.setContentNoti( "Your request is denied by" + form.getEmployee().getFullName() + " because: "  + form.getReason() 
	               + " <a href=\"/Form/Request/showDetail?id=" + form.getIdForm() + "\">See more</a>");
		noti.setDate(LocalDate.now());
		noti.setTime(LocalTime.now());
		String[] nguoiNhan = {form.getResident().getIdResident()};
		notiService.saveNotificationAndReceiver("Choosen", noti, principal, nguoiNhan);
		return "Form/detail";
	}

	@GetMapping("accept")
	public String accpetRequest(@RequestParam(name = "idForm") String idForm, Model model, Principal principal) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String role = userDetails.getRole();
		String id = userDetails.getId();
		Employee employee = employeeService.findById(id);
		Request form = requestService.findById(idForm);
		form.setStatus("Accept");
		form.setDateAccept(new Date());
		form.setEmployee(employee);
		requestService.save(form);
		List<Employee> employees = employeeService.allEmployee();

		model.addAttribute("employees", employees);
		model.addAttribute("form", form);
		model.addAttribute("role", role);
		return "Form/detail";
	}

	@PostMapping("addEmployee")
	public String addEmployee(@RequestParam(name = "idForm") String idForm,
			@RequestParam(name = "selectedEmployee") String idEmployee, Model model , Principal principal) {
		Employee employee = employeeService.findById(idEmployee);
		System.out.println(idEmployee);
		Request form = requestService.findById(idForm);
		Repair repair = new Repair();
		repair.setId(repairService.generateNewId());
		repair.setEmployee(employee);
		repair.setDateAssign(new Date());
		repairService.save(repair);
		form.setRepair(repair);
		requestService.save(form);
		Notification noti = new Notification();
		noti.setTitle("You have a new repair request");
		noti.setContentNoti( "You have a new repair request from " + form.getEmployee().getFullName()
	               + " <a href=\"/Form/Request/showDetail?id=" + form.getIdForm() + "\">See more</a>");
		noti.setDate(LocalDate.now());
		noti.setTime(LocalTime.now());
		String[] nguoiNhan = {idEmployee};
		notiService.saveNotificationAndReceiver("Choosen", noti, principal, nguoiNhan);
		model.addAttribute("form", form);
		
		return "Form/detail";
	}

	@GetMapping("showListAsset")
	public String showListAsset(Model model, @RequestParam(name = "id") String idForm) {
		List<Assets> assets = assetService.findAll();
		model.addAttribute("idForm", idForm);
		model.addAttribute("assets", assets);
		return "Form/Request/addAsset";
	}

	@GetMapping("addListAssets")
	public String employeeAccept(@RequestParam(name = "ListValue", defaultValue = "") String idAsset,
			@RequestParam(name = "idForm") String idForm, Model model, Principal principal) {
		String[] arrayAssets = idAsset.split(",");
		System.out.println(arrayAssets[0] + "cccc");
		if ("".equalsIgnoreCase(arrayAssets[0])) {
			return "redirect:showListAsset?id=" + idForm;
		}
		List<String> listAssets = new ArrayList<>(Arrays.asList(arrayAssets));
		Request form = requestService.findById(idForm);
		List<Assets> assets = assetService.findByIds(listAssets);
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String role = userDetails.getRole();
		form.setStatus("Active");
		Repair repair = form.getRepair();
		for (Assets assets2 : assets) {
			assets2.getRepair().add(repair);
		}
		repair.setAssets(assets);
		repair.setDateRepair(new Date());
		repairService.save(repair);
		requestService.save(form);
		model.addAttribute("repair", repair);
		model.addAttribute("messT", "Add asset success");
		model.addAttribute("role", role);
		model.addAttribute("form", form);
		return "Form/detail";
	}

	@PostMapping("completed")
	public String completed(@RequestParam("files") MultipartFile[] files, RedirectAttributes redirectAttributes,
			@RequestParam(name = "idForm") String idForm, Model model, Principal principal) {
		Request form = requestService.findById(idForm);
		Repair repair = form.getRepair();
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
					image.setRepair(repair);
					images.add(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		form.setStatus("Completed");
		repair.setDateCompleted(new Date());
		repair.setImgRepairs(images);
		repairService.save(repair);
		requestService.save(form);
		model.addAttribute("role", role);
		model.addAttribute("form", form);
		Notification noti = new Notification();
		noti.setTitle("Your request is completed");
		noti.setContentNoti( "Your request is completed" 
	               + " <a href=\"/Form/Request/showDetail?id=" + form.getIdForm() + "\">See more</a>");
		noti.setDate(LocalDate.now());
		noti.setTime(LocalTime.now());
		String[] nguoiNhan = {form.getResident().getIdResident()};
		notiService.saveNotificationAndReceiver("Choosen", noti, principal, nguoiNhan);
		return "Form/detail";

	}

	@PostMapping("rate")
	public String rate(@RequestParam(name = "idForm") String idForm, @RequestParam(name = "rating") String rate,
			Model model, Principal principal) {
		Request form = requestService.findById(idForm);
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		String role = userDetails.getRole();
		model.addAttribute("role", role);
		switch (rate) {
		case "1": {
			form.setRate("Very bad");
			break;
		}
		case "2": {
			form.setRate("Bad");
			break;
		}
		case "3": {
			form.setRate("Normal");
			break;
		}
		case "4": {
			form.setRate("Good");
			break;
		}
		default:
			form.setRate("Very good");
		}
		requestService.save(form);
		model.addAttribute("form", form);
		return "Form/detail";
	}
	@GetMapping("search")
	public String search( @RequestParam(name = "searchKeyword", defaultValue = "") String keyword ,@RequestParam(name = "page", defaultValue = "1") int page, Model model, Principal principal) {
		AccountDTO userDetails = (AccountDTO) ((Authentication) principal).getPrincipal();
		int pageSize = 6;
		PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
		Page<Request> listAll = null;
		if ("ROLE_MANAGE".equalsIgnoreCase(userDetails.getRole())) {
			listAll = requestService.showAllByKeyword(pageRequest, keyword);
		} else if ("ROLE_EMPLOYEE".equalsIgnoreCase(userDetails.getRole())) {
			listAll = requestService.showAllForEmployeeByKeyword(userDetails.getId(), pageRequest, keyword);
		} else {
			listAll = requestService.showAllForResidentByKeyWord(userDetails.getId(), pageRequest, keyword);
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
		return "Form/Request/list";
	}
}
