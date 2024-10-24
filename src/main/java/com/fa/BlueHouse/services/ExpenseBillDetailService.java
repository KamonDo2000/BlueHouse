package com.fa.BlueHouse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.ExpenseBillDetail;
import com.fa.BlueHouse.repositories.ExpenseBillDetailRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ExpenseBillDetailService {

	@Autowired
	private ExpenseBillDetailRepository expendetail;
	
	public ExpenseBillDetail findByIDDetail(String id) {
		return expendetail.findById(id).orElse(null);
	}
	public void saveExpenDetail(ExpenseBillDetail expense) {
		expendetail.save(expense);
	}
	public void deleteExpenseBillDetail(String id) {
		expendetail.deleteById(id);
	}
	public Page<ExpenseBillDetail> findAllPageExpenseBillDetail(Pageable page){
		return expendetail.findAll(page);
	}
	public Page<ExpenseBillDetail> findByIdExpenseDetail(String id, Pageable page){
		return expendetail.findByIDExpensedetail(id, page);
	}
	
	public Page<ExpenseBillDetail> searchExpenseBillDetail(String search, Pageable page){
		return expendetail.searchExpenseBillDetail(search, page);
	}
	public Page<ExpenseBillDetail> searchExpenseBillDetail(String search,String idbill, Pageable page){
		return expendetail.searchExpenseBillDetail(search,idbill, page);
	}
	public String generateNewId() {
		String maxId = expendetail.findMaxId();
		
		if(maxId == null) return "ED001";
		
		int numberic = Integer.parseInt(maxId.substring(2));
		
		numberic++;
		
		return String.format("ED%03d", numberic);
	}
}
