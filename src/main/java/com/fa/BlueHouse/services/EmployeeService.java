package com.fa.BlueHouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.repositories.EmployeeRepo;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepo eRepo;

	public List<Employee> listAdministrator() {
		return eRepo.findAdministrator();
	}

	public List<Employee> allEmployee() {
		return eRepo.findAll();
	}

	public Page<Employee> allEmployee(Pageable pageable) {
		return eRepo.findAll(pageable);
	}

	public Page<Employee> findByKeyword(Pageable pageable, String keyword) {
		return eRepo.findByKeyword(keyword, pageable);
	}

	public List<Employee> findByKeyword(String keyword) {
		return eRepo.findByKeyword(keyword);
	}

	/**
	 * @param Services
	 * @param Engineering
	 * @param Environment
	 * 
	 * @return
	 */
	public List<Employee> getManagerByOffice(String office) {
		return eRepo.getManagerByOffice(office);
	}

	public void saveEmployee(Employee emp) {
		eRepo.save(emp);
	}

	public void deleteByID(String id) {
		eRepo.deleteById(id);
	}

	public Employee findById(String id) {
		return eRepo.findById(id).orElse(null);
	}

	public String generateNewId() {
		String maxId = eRepo.findMaxId();

		if (maxId == null)
			return "EMP001";

		int numberic = Integer.parseInt(maxId.substring(3));

		numberic++;

		return String.format("EMP%03d", numberic);
	}

}
