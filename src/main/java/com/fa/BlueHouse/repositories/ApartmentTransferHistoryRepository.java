package com.fa.BlueHouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.BlueHouse.entities.ApartmentTransferHistory;

public interface ApartmentTransferHistoryRepository extends JpaRepository<ApartmentTransferHistory, String> {
	@Query("SELECT a FROM ApartmentTransferHistory a WHERE a.idContract LIKE %:keyword% OR a.idResident.idResident LIKE %:keyword% OR a.idResident.nameResident LIKE %:keyword% OR a.newHomeowner LIKE %:keyword% OR a.apartmentTransfer.apartmentNumber LIKE %:keyword% OR a.managerCodeTransfer.employeeID LIKE %:keyword%")
	Page<ApartmentTransferHistory> findApartmentTransferHistoryByKeyword(@Param("keyword") String keyword , Pageable pageable);
	@Query("SELECT MAX(a.idContract) FROM ApartmentTransferHistory a")
	String findMaxId();
}
