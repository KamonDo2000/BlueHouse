package com.fa.BlueHouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.Administrators;
import com.fa.BlueHouse.repositories.AdministratorsRepositories;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdministratorsService {

	@Autowired
	private AdministratorsRepositories adminis;

	public void saveAdminis(Administrators admin) {
		adminis.save(admin);
	}

	public void deleteAdminis(String id) {
		adminis.deleteById(id);
	}

	public List<Administrators> findall() {
		return adminis.findAll();
	}

	public Administrators findById(String id) {
		return adminis.findById(id).orElse(null);
	}

	public Page<Administrators> findpageAdmin(Pageable page) {
		return adminis.findAll(page);
	}

	public Page<Administrators> findsearchAdmin(String search, Pageable page) {
		return adminis.searchAdminis(search, page);
	}

	public boolean isExits(String id) {
		for (Administrators e : findall()) {
			if (e.getIdResident().getIdResident().equalsIgnoreCase(id)) {
				return true;
			}
		}
		return false;
	}
	public String generateNewId() {
		String maxId = adminis.findMaxId();
		
		if(maxId == null) return "AD001";
		
		int numberic = Integer.parseInt(maxId.substring(2));
		
		numberic++;
		
		return String.format("AD%03d", numberic);
	}
}
