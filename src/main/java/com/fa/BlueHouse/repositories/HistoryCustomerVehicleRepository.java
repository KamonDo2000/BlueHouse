package com.fa.BlueHouse.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.BlueHouse.entities.HistoryCustomerVehicle;
import com.fa.BlueHouse.entities.HistoryCustomerVehicleID;

public interface HistoryCustomerVehicleRepository extends JpaRepository<HistoryCustomerVehicle, HistoryCustomerVehicleID>{

//	Li<HistoryCustomerVehicle> findByIdVehicleNumber(String vehicleNumber, LocalDate moveInDate);
	
	@Query("SELECT hi FROM HistoryCustomerVehicle hi where hi.id.vehicleNumber LIKE %:seacrch% or hi.type LIKE %:seacrch%  or CAST(hi.id.moveInDate AS string) LIKE %:seacrch%  ")
	Page<HistoryCustomerVehicle> searchHistoryCustomerVehicle(@Param("seacrch")String seacrch, Pageable pageable);
	
	@Query("SELECT hi FROM HistoryCustomerVehicle hi where hi.id.vehicleNumber LIKE %:seacrch% or hi.type LIKE %:seacrch%  or CAST(hi.id.moveInDate AS string) LIKE %:seacrch%  ")
	List<HistoryCustomerVehicle> searchHistoryCustomerVehicle(@Param("seacrch")String seacrch);
}
