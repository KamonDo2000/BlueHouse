package com.fa.BlueHouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fa.BlueHouse.entities.Administrators;

public interface AdministratorsRepositories extends JpaRepository<Administrators, String>{

	@Query("FROM Administrators ad where ad.idBQT Like %:search% or ad.idPosition.namePosition Like %:search% or ad.idResident.nameResident LIKE %:search%")
	public Page<Administrators> searchAdminis(String search, Pageable pageable);

	@Query("SELECT MAX(i.idIncomeBill) FROM IncomeBill i")
    String findMaxId();
}
