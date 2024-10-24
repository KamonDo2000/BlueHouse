package com.fa.BlueHouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.BlueHouse.entities.Assets;

public interface AssetRepository extends JpaRepository<Assets, String> {
	   @Query("SELECT MAX(a.idAsset) FROM Assets a")
	    String findMaxId();
	   @Query("SELECT a FROM Assets a WHERE a.name LIKE %:keyword% OR a.idAsset LIKE %:keyword% OR a.location LIKE %:keyword% ")
	   Page<Assets> findByKeyword(@Param("keyword") String keyword , Pageable pageable);
}
