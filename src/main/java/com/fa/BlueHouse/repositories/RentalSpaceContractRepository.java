package com.fa.BlueHouse.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.BlueHouse.entities.RentalSpaceContract;


public interface RentalSpaceContractRepository extends JpaRepository<RentalSpaceContract, String>{

	@Query("SELECT a FROM RentalSpaceContract a where a.contractCode LIKE %:seacrch% or a.tenantCode LIKE %:seacrch% ")
	Page<RentalSpaceContract> searchRentalSpaceContract(@Param("seacrch")String seacrch, Pageable pageable);
	
	@Query("SELECT a FROM RentalSpaceContract a where a.contractCode LIKE %:seacrch% or a.tenantCode LIKE %:seacrch% ")
	List<RentalSpaceContract> searchRentalSpaceContract(@Param("seacrch")String seacrch);
}
