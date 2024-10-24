package com.fa.BlueHouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.Apartment;
import com.fa.BlueHouse.entities.Resident;
import com.fa.BlueHouse.repositories.ApartmentRepository;
import com.fa.BlueHouse.repositories.ResidentRepositories;

import jakarta.transaction.Transactional;
@Service
@Transactional
public class ResidentService {
	@Autowired
	private ResidentRepositories repositori;
	@Autowired
	private ApartmentRepository apart;
	
	public void saveResident(Resident resi) {
		repositori.save(resi);
	}
	
	public List<Resident> findallResident(){
		return repositori.findAll();
	}

    public Page<Resident> searchResident(String search, Pageable pageable){
    	return repositori.searchResident(search, pageable);
    }
    
    public List<Apartment> findallapart(){
    	return apart.findAll();
    }
    
    public void deleteResident(String id) {
    	repositori.deleteById(id);
    }
    public void updateResident(Resident resi) {
    	repositori.save(resi);
    }
    
    public Resident findById(String id) {
    	return repositori.findById(id).orElse(null);
    }
    public List<Resident> findByIdApartment(String id){
    	return repositori.findByIdApartment_idApartment(id);
    }
    
    public Page<Resident> findpageResident(Pageable page){
    	return repositori.findAll(page);
    }
    public String generateNewId() {
		String maxId = repositori.findMaxId();
		
		if(maxId == null) return "RS001";
		
		int numberic = Integer.parseInt(maxId.substring(2));
		
		numberic++;
		
		return String.format("RS%03d", numberic);
	}
    
}
