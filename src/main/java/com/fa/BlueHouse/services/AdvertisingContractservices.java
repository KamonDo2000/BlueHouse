package com.fa.BlueHouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.AdvertisingContract;
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.entities.FeeType;
import com.fa.BlueHouse.repositories.AdvertisingContractRepositories;
import com.fa.BlueHouse.repositories.EmployeeRepo;
import com.fa.BlueHouse.repositories.FeetypeRepositories;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdvertisingContractservices {

	@Autowired
	private AdvertisingContractRepositories advRepositories;
	
	@Autowired
	private FeetypeRepositories feeTypeRepository;
	
	@Autowired 
	private EmployeeRepo employeeRepo;
	
	public List<AdvertisingContract> findaAdv(){
		return advRepositories.findAll();
	}
	
	public List<Employee> findaEmp(){
		return employeeRepo.findAll();
	}
	
	public List<FeeType> findaFee(){
		return feeTypeRepository.findAll();
	}
	
	public AdvertisingContract findaById(String id) {
		return advRepositories.findById(id).orElse(null);
	}
	
	public Employee findaByIdemp(String id) {
		return employeeRepo.findById(id).orElse(null);
	}
	
	public void saveAdv(AdvertisingContract adv) {
		advRepositories.save(adv);
	}
	
	public void deleteadv(String id) {
		advRepositories.deleteById(id);
	}
	
	public List<AdvertisingContract> findByKeyword(String keyword) {
		return advRepositories.searchAdvertisingContract(keyword);
	}
	
	public void  updateadv(AdvertisingContract adv) {
		advRepositories.save(adv);
	}
	
	public Page<AdvertisingContract> allAdvertisingContract(Pageable pageable){
		return advRepositories.findAll(pageable);
	}
	public Page<AdvertisingContract> seachAdvertisingContract(Pageable pageable, String search){
		return advRepositories.searchAdvertisingContract(search, pageable);
	}
}

