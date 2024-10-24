package com.fa.BlueHouse.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fa.BlueHouse.entities.Resident;

public interface ResidentRepositories extends JpaRepository<Resident, String> {
	
	@Query("FROM Resident where nameResident LIKE %:seacrch% or gender LIKE %:seacrch% or relationshipHousehold LIKE %:seacrch% or phonenumber LIKE %:seacrch% or workplace LIKE %:seacrch% or identificationCard LIKE %:seacrch%")
	public Page<Resident> searchResident(String seacrch , Pageable pageable);

	public List<Resident> findByIdApartment_idApartment(String idApartment) ;
	
	@Query("SELECT MAX(i.idIncomeBill) FROM IncomeBill i")
    String findMaxId();

}
