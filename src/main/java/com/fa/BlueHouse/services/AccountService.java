package com.fa.BlueHouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.authen.model.Account;
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.entities.Resident;
import com.fa.BlueHouse.repositories.AccountRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountService {

	@Autowired
	private AccountRepo aRepo;
	
	public List<Account> allAccount() {
		return aRepo.findAll();
	}

	public Page<Account> allAccount(Pageable pageable) {
		return aRepo.findAll(pageable);
	}

	public Page<Account> findByKeyword(Pageable pageable, String keyword) {
		return aRepo.findByKeyword(keyword, pageable);
	}

	public void saveAccount(Account emp) {
		aRepo.save(emp);
	}

	public void deleteByUserName(String userName) {
		aRepo.deleteById(userName);
	}
	
	public Account findByUserName(String userName) {
    	return aRepo.findById(userName).orElse(null);
    }

	public List<Employee> getEmpNotInAccount() {
		return aRepo.getEmpNotInAccount();
	}
	
	public List<Resident> getReciNotInAccount() {
		return aRepo.getReciNotInAccount();
	}
	
	public List<Account> getAccByEmp(String empID) {
		return aRepo.getAccByEmp(empID);
	}
	
	public List<Account> getAccByResi(String resiID) {
		return aRepo.getAccByResi(resiID);
	}

	public List<Account> findByRole2(){
		return aRepo.findRole2();
	}
}
