package com.fa.BlueHouse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.Repair;
import com.fa.BlueHouse.repositories.RepairRepository;

@Service
public class RepairService {
	@Autowired
	RepairRepository repairRepository;
	public String generateNewId() {
		String maxId = repairRepository.findMaxId();
		
		if(maxId == null) return "RP001";
		
		int numberic = Integer.parseInt(maxId.substring(2));
		
		numberic++;
		
		return String.format("RP%03d", numberic);
	}
	public void save(Repair repair) {
		repairRepository.save(repair);
	}
	public Repair findById(String id) {
		return repairRepository.findById(id).orElse(null);
	}
	public Page<Repair> showAll(Pageable pageable){
		return repairRepository.findAll(pageable);
	}
	public Page<Repair> showAllForEmployee(String id ,Pageable pageable){
		return repairRepository.findRepairByEmployeeId(id, pageable);
	}
}
