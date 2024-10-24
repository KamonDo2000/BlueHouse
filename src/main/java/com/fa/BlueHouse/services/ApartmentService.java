package com.fa.BlueHouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.Apartment;
import com.fa.BlueHouse.repositories.ApartmentRepository;

@Service

public class ApartmentService {
	@Autowired
	ApartmentRepository apartmentRepository;
	
	public List<Apartment> allApartments(){
		return apartmentRepository.findAll();
	}
	public Page<Apartment> allApartments(Pageable pageable){
		return apartmentRepository.findAll(pageable);
	}
	public Page<Apartment> findApartmentsByKeyword(Pageable pageable , String keyword){
		return apartmentRepository.findApartmentByKeyword( keyword ,pageable );
	}

	public List<Apartment> regularApartments(){
		return apartmentRepository.regularApartment();
	}
	public void save(Apartment apartment) {
		apartmentRepository.save(apartment);
	}
	public Apartment findById(String id) {
		return apartmentRepository.findById(id).orElse(null);
	}
}
