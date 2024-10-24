package com.fa.BlueHouse.services;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.Apartment;
import com.fa.BlueHouse.entities.HistoryCustomerVehicle;
import com.fa.BlueHouse.entities.HistoryCustomerVehicleID;
import com.fa.BlueHouse.repositories.ApartmentRepository;
import com.fa.BlueHouse.repositories.HistoryCustomerVehicleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HistoryCustomerVehicleServices {

	@Autowired
	private HistoryCustomerVehicleRepository hisCusVehiRepository;
	
	@Autowired
	private ApartmentRepository apartmentRepository;
	
	public List<HistoryCustomerVehicle> findaHisCusVehi(){
		return hisCusVehiRepository.findAll();
	}
	
	public List<Apartment> findaApa(){
		return apartmentRepository.findAll();
	}
	
	public void saveHistoryCustomerVehicle(HistoryCustomerVehicle hisCusVehi) {
		hisCusVehiRepository.save(hisCusVehi);
	}
	
	public HistoryCustomerVehicle findaById(HistoryCustomerVehicleID id) {
		return hisCusVehiRepository.findById(id).orElse(null);
	}
	
	public void deleteHistoryCustomerVehicle(HistoryCustomerVehicleID id) {
		hisCusVehiRepository.deleteById(id);
	}
	
	public void updateMoveOutDate(HistoryCustomerVehicleID id) {
		HistoryCustomerVehicle historyCustomerVehicle = hisCusVehiRepository.findById(id).orElse(null);
		if(historyCustomerVehicle == null) return;
		
        if(historyCustomerVehicle != null && historyCustomerVehicle.getMoveOutDate() == null) {
        	historyCustomerVehicle.setMoveOutDate(LocalDate.now());
        	hisCusVehiRepository.save(historyCustomerVehicle);
        }
    }
	
	
//	public void updateMoveInDate(String vehicleNumber, LocalDate moveInDate) {
//        HistoryCustomerVehicle historyCustomerVehicle = hisCusVehiRepository.findByIdVehicleNumber(vehicleNumber);
//        if (historyCustomerVehicle != null ) {
//        	HistoryCustomerVehicle newVehicleEntry = new HistoryCustomerVehicle();
//            HistoryCustomerVehicleID newId = new HistoryCustomerVehicleID();
//            newId.setVehicleNumber(vehicleNumber);
//            newId.setMoveInDate(moveInDate);
//            newVehicleEntry.setId(newId);
//            newVehicleEntry.setType(historyCustomerVehicle.getType());
//            newVehicleEntry.setMoveOutDate(null);
//            newVehicleEntry.setApartmentHIS(historyCustomerVehicle.getApartmentHIS());
//            hisCusVehiRepository.save(newVehicleEntry);
//        }
//    }
	
	public List<HistoryCustomerVehicle> findByKeyword(String keyword){
		return hisCusVehiRepository.searchHistoryCustomerVehicle(keyword);
	}
	
	public Page<HistoryCustomerVehicle> allHistoryCustomerVehicle(Pageable pageable){
		return hisCusVehiRepository.findAll(pageable);
	}
	
	public Page<HistoryCustomerVehicle> seachHistoryCustomerVehicle(Pageable pageable, String search){
		return hisCusVehiRepository.searchHistoryCustomerVehicle(search, pageable);
	}
}

