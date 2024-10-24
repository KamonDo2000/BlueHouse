package com.fa.BlueHouse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fa.BlueHouse.entities.ApartmentTransferHistory;
import com.fa.BlueHouse.repositories.ApartmentTransferHistoryRepository;

@Service
public class ApartmentTransferHistoryService {
	@Autowired
	ApartmentTransferHistoryRepository apartmentTransferHistoryRepository;
	
	public Page<ApartmentTransferHistory> showAll(Pageable pageable){
		return apartmentTransferHistoryRepository.findAll(pageable);
	}
	public void save(ApartmentTransferHistory apartmentTransferHistory) {
		apartmentTransferHistoryRepository.save(apartmentTransferHistory);
	}
	public Page<ApartmentTransferHistory> findByKeyword(Pageable pageable , String keyword){
		return apartmentTransferHistoryRepository.findApartmentTransferHistoryByKeyword( keyword ,pageable );
	}
	public String generateNewId() {
		String maxId = apartmentTransferHistoryRepository.findMaxId();
		
		if(maxId == null) return "C001";
		
		int numberic = Integer.parseInt(maxId.substring(1));
		
		numberic++;
		
		return String.format("C%03d", numberic);
	}
}
