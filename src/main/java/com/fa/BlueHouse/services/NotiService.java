package com.fa.BlueHouse.services;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.authen.model.AccountDTO;
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.entities.Notification;
import com.fa.BlueHouse.entities.Receiver;
import com.fa.BlueHouse.entities.Resident;
import com.fa.BlueHouse.repositories.NotiRepositories;
import com.fa.BlueHouse.repositories.ReceiverRepositories;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NotiService {

	@Autowired
	private NotiRepositories notiRepo;

	@Autowired
	private ReceiverRepositories receiRepo;

	@Autowired
	private EmployeeService eService;

	@Autowired
	private ResidentService rService;

	public void saveNoti(Notification noti) {
		notiRepo.save(noti);
	}

	public void saveRecei(Receiver recei) {
		receiRepo.save(recei);
	}

	public Page<Notification> findByIDSend(Pageable pageable, String id) {
		return receiRepo.findByIDSend(id, pageable);
	}
	
	public Page<Notification> findByIDSendKey(Pageable pageable, String id, String keyword) {
		return receiRepo.findByIDSendKey(keyword, id, pageable);
	}

	public Page<Receiver> findByIDSeen(Pageable pageable, String id) {
		return receiRepo.findByIDSeen(id, pageable);
	}
	
	public Page<Receiver> findByIDSeenKey(Pageable pageable, String id, String keyword) {
		return receiRepo.findByIDSeenKey(keyword, id, pageable);
	}

	public List<Receiver> findNotiUnSeen(String id) {
		return receiRepo.findNotiUnSeen(id);
	}

	public Notification findNotiByID(String id) {
		return notiRepo.findById(id).orElse(null);
	}

	public Receiver findReceiByID(Integer id) {
		return receiRepo.findById(id).orElse(null);
	}

	/**
	 * 
	 * @param choose       "All" || "AllEmployee" || "AllResident" || "Choosen"
	 * @param Notification notification Không cần set Notification Code
	 * @param String[]     list ID Receiver
	 * @param Principal    principal
	 * 
	 * @return tùy vào choose lưu dữ liệu xuống database nếu "Choosen" cần truyền
	 *         vào list id của đối tượng nhận thông báo
	 *
	 */
	public void saveNotificationAndReceiver(String choose, Notification noti, Principal principal,
			String[] listIDReceiver) {
		AccountDTO thongTin = (AccountDTO) ((Authentication) principal).getPrincipal();

		Employee senderEmp = eService.findById(thongTin.getId());
		Resident senderResi = rService.findById(thongTin.getId());

		if (senderEmp != null) {
			noti.setNotificationCode(senderEmp.getEmployeeID() + noti.getDate() + noti.getTime());
		} else if (senderResi != null) {
			noti.setNotificationCode(senderResi.getIdResident() + noti.getDate() + noti.getTime());
		}

		if (choose.equalsIgnoreCase("All")) {
			saveNoti(noti);
			saveAll(noti, senderEmp, senderResi, eService.allEmployee(), rService.findallResident());
		}
		if (choose.equalsIgnoreCase("AllEmployee")) {
			saveNoti(noti);
			saveAllEmp(noti, senderEmp, senderResi, eService.allEmployee());
		}
		if (choose.equalsIgnoreCase("AllResident")) {
			saveNoti(noti);
			saveAllResi(noti, senderEmp, senderResi, rService.findallResident());
		}
		if (choose.equalsIgnoreCase("Choosen")) {
			saveNoti(noti);
			saveChoosen(noti, senderEmp, senderResi, listIDReceiver);
		}
	}

	private void saveAll(Notification noti, Employee senderEmp, Resident senderResi, List<Employee> listEmp,
			List<Resident> listResi) {
		for (Employee e : listEmp) {
			saveRecei(new Receiver(noti, senderEmp, senderResi, e, null, 0));
		}
		for (Resident e : listResi) {
			saveRecei(new Receiver(noti, senderEmp, senderResi, null, e, 0));
		}
	}

	private void saveAllEmp(Notification noti, Employee senderEmp, Resident senderResi, List<Employee> listEmp) {
		for (Employee e : listEmp) {
			saveRecei(new Receiver(noti, senderEmp, senderResi, e, null, 0));
		}
	}

	private void saveAllResi(Notification noti, Employee senderEmp, Resident senderResi, List<Resident> listResi) {
		for (Resident e : listResi) {
			saveRecei(new Receiver(noti, senderEmp, senderResi, null, e, 0));
		}
	}

	private void saveChoosen(Notification noti, Employee senderEmp, Resident senderResi, String[] listID) {
		for (String e : listID) {
			Employee emp = eService.findById(e);
			Resident resi = rService.findById(e);
			saveRecei(new Receiver(noti, senderEmp, senderResi, emp, resi, 0));
		}
	}

}
