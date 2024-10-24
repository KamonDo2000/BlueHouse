package com.fa.BlueHouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fa.BlueHouse.entities.FeeType;

public interface FeetypeRepositories extends JpaRepository<FeeType, String>{
	@Query("SELECT MAX(i.idFeetype) FROM FeeType i")
    String findMaxId();
}
