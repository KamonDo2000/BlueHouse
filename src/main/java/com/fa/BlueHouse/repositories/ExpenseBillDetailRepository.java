package com.fa.BlueHouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fa.BlueHouse.entities.ExpenseBillDetail;

public interface ExpenseBillDetailRepository extends JpaRepository<ExpenseBillDetail, String>{

	@Query("FROM ExpenseBillDetail ")
	public Page<ExpenseBillDetail> findAllBill( Pageable page);
	@Query("FROM ExpenseBillDetail where idExpenseBill.idExpenseBill = :id")
	public Page<ExpenseBillDetail> findByIDExpensedetail(String id , Pageable pageable);
	@Query("FROM ExpenseBillDetail where idexpenDetail LIKE %:seacrch% or idExpenseBill.idExpenseBill LIKE %:seacrch% or nameExpense LIKE %:seacrch%")
	public Page<ExpenseBillDetail> searchExpenseBillDetail(String seacrch , Pageable pageable);
	@Query("FROM ExpenseBillDetail where (idexpenDetail LIKE %:seacrch% or nameExpense LIKE %:seacrch%) and idExpenseBill.idExpenseBill = :idbill ")
	public Page<ExpenseBillDetail> searchExpenseBillDetail(String seacrch ,String idbill, Pageable pageable);
	@Query("SELECT MAX(i.idexpenDetail) FROM ExpenseBillDetail i")
    String findMaxId();
}
