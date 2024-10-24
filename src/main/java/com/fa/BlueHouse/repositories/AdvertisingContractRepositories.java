package com.fa.BlueHouse.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.fa.BlueHouse.entities.AdvertisingContract;

public interface AdvertisingContractRepositories extends JpaRepository<AdvertisingContract, String>{

	@Query("SELECT adv FROM AdvertisingContract adv where adv.contractCode LIKE %:seacrch% or adv.contractName LIKE %:seacrch%  or adv.company LIKE %:seacrch% ")
	Page<AdvertisingContract> searchAdvertisingContract(@Param("seacrch")String seacrch, Pageable pageable);
	
	@Query("SELECT adv FROM AdvertisingContract adv where adv.contractCode LIKE %:seacrch% or adv.contractName LIKE %:seacrch%  or adv.company LIKE %:seacrch% ")
	List<AdvertisingContract> searchAdvertisingContract(@Param("seacrch")String seacrch);
}
