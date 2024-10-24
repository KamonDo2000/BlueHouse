package com.fa.BlueHouse.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.fa.BlueHouse.entities.Apartment;

public interface ApartmentRepository extends JpaRepository<Apartment, String> {

	@Query("SELECT a FROM Apartment a WHERE a.idApartment LIKE %:keyword% ")
	Page<Apartment> findApartmentByKeyword(@Param("keyword") String keyword , Pageable pageable);
	
	@Query("SELECT a FROM Apartment a WHERE a.typeApartment = 'Regular Apartment'")
	List<Apartment> regularApartment();
	

	@Query("FROM Apartment ap WHERE ap.idApartment = :idApartment")
	List<Apartment> findByapartment(@Param("idApartment") String idApartment);
	
	
	@Query("SELECT a FROM Apartment a WHERE a.typeApartment = 'For Rent'")
	List<Apartment> searchapa();
}
