package com.fa.BlueHouse.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.fa.BlueHouse.entities.VehicleRegistration;

public interface VehicleRegistrationRepository extends JpaRepository<VehicleRegistration, String>{

	@Query("SELECT ve FROM VehicleRegistration ve where ve.idVehicle LIKE %:seacrch% or ve.vehicleNumber LIKE %:seacrch% ")
	Page<VehicleRegistration> searchVehicleRegistration(@Param("seacrch")String seacrch, Pageable pageable);
	

	@Query("SELECT ve FROM VehicleRegistration ve where ve.idVehicle LIKE %:seacrch% or ve.vehicleNumber LIKE %:seacrch% ")
	List<VehicleRegistration> searchVeRegistration(@Param("seacrch")String seacrch);

	@Query("FROM VehicleRegistration where apartmentVE.idApartment = :idApartment and status is null")
	public List<VehicleRegistration> findRegisApartment(String idApartment);

	@Query("FROM VehicleRegistration ve WHERE ve.apartmentVE.idApartment = :apartmentVE")
	Page<VehicleRegistration> findByapartmentVE(@Param("apartmentVE") String apartmentVE, Pageable pageable);
	
}
