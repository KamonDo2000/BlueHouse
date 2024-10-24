package com.fa.BlueHouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.Apartment;
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.entities.RentalSpaceContract;
import com.fa.BlueHouse.repositories.ApartmentRepository;
import com.fa.BlueHouse.repositories.EmployeeRepo;

import com.fa.BlueHouse.repositories.RentalSpaceContractRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RentalSpaceContractService {

	@Autowired
	private RentalSpaceContractRepository renSpaCon;
	
	@Autowired
	private ApartmentRepository apartment;
	
	@Autowired 
	private EmployeeRepo employee;
	
	public void saveRentalSpaceContractDao(RentalSpaceContract renspa) {
		renSpaCon.save(renspa);
	}
	
	public List<Employee> findalEmploy(){
		return employee.findAll();
	}
	
	public List<RentalSpaceContract> findalRenSpaCon(){
		return renSpaCon.findAll();
	}
	
	public List<Apartment> findalApa(){
		return apartment.searchapa();
	}
	
	public RentalSpaceContract findByID(String id) {
		return renSpaCon.findById(id).orElse(null);
	}
	
	public Employee findByempID(String id) {
		return employee.findById(id).orElse(null);
	}
	
	public void deleteRenSapCon(String id) {
		renSpaCon.deleteById(id);
	}
	
	public void updateRenSpaCon(RentalSpaceContract rentalSpaCon) {
		renSpaCon.save(rentalSpaCon);
	}
	
	public List<RentalSpaceContract> findByKeyword(String keyword){
		return renSpaCon.searchRentalSpaceContract(keyword);
	}
	public Page<RentalSpaceContract> allRentalSpaceContract(Pageable pageable){
		return renSpaCon.findAll(pageable);
	}
	public Page<RentalSpaceContract> seachRentalSpaceContract(Pageable pageable, String search){
		return renSpaCon.searchRentalSpaceContract(search, pageable);
	}
}
