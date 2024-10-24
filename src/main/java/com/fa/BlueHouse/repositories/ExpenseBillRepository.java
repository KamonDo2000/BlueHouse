package com.fa.BlueHouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fa.BlueHouse.entities.ExpenseBill;

public interface ExpenseBillRepository extends JpaRepository<ExpenseBill, String> {

	@Query("FROM ExpenseBill ")
	public Page<ExpenseBill> findAllBill( Pageable page);
	@Query("FROM ExpenseBill where idExpenseBill LIKE %:seacrch% or idEmployee.fullName LIKE %:seacrch%")
	public Page<ExpenseBill> searchInBill(String seacrch , Pageable pageable);
	@Query("SELECT MAX(i.idExpenseBill) FROM ExpenseBill i")
    String findMaxId();
}
