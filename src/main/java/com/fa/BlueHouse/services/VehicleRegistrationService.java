package com.fa.BlueHouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fa.BlueHouse.entities.Apartment;
import com.fa.BlueHouse.entities.FeeType;
import com.fa.BlueHouse.entities.VehicleRegistration;
import com.fa.BlueHouse.repositories.ApartmentRepository;
import com.fa.BlueHouse.repositories.FeetypeRepositories;
import com.fa.BlueHouse.repositories.VehicleRegistrationRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class VehicleRegistrationService {

	@Autowired
	private VehicleRegistrationRepository vehiRegiRepository;

	@Autowired
	private ApartmentRepository apartmentRepository;

	@Autowired
	private FeetypeRepositories feeTypeRepository;

	public List<VehicleRegistration> findaVehicleRegi() {
		return vehiRegiRepository.findAll();
	}

	public List<Apartment> findaApartment() {

		return apartmentRepository.findAll();
	}

	public List<FeeType> findaFeeType() {
		return feeTypeRepository.findAll();
	}

	public void saveVehicleRegistration(VehicleRegistration vehicleRegistration) {
		vehiRegiRepository.save(vehicleRegistration);
	}

	public VehicleRegistration findaById(String id) {
		return vehiRegiRepository.findById(id).orElse(null);
	}

	public void deleteVehicleRegistration(String id) {
		vehiRegiRepository.deleteById(id);
	}

	public void updateVehicleRegistration(VehicleRegistration vehicleRegistration) {
		vehiRegiRepository.save(vehicleRegistration);
	}
	
	public List<VehicleRegistration> findByKeyword(String keyword) {
		return vehiRegiRepository.searchVeRegistration(keyword);
	}
	
	public Page<VehicleRegistration> allVehicleRegistration(Pageable pageable){
		return vehiRegiRepository.findAll(pageable);
	}
	public Page<VehicleRegistration> seachVehicleRegistration(Pageable pageable, String search){
		return vehiRegiRepository.searchVehicleRegistration(search, pageable);
	}
	
	public List<VehicleRegistration> findRegisApartment(String idApartment){
		return vehiRegiRepository.findRegisApartment(idApartment);
	}
	public Page<VehicleRegistration> allVehicleRegistration(String apartmentVE ,Pageable pageable){
		return vehiRegiRepository.findByapartmentVE(apartmentVE, pageable);
	}
	public List<Apartment> allEmployee(String idApartment){
		return apartmentRepository.findByapartment(idApartment);
	}
}
