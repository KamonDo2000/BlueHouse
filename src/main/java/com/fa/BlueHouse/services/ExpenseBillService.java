package com.fa.BlueHouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.ExpenseBill;
import com.fa.BlueHouse.repositories.ExpenseBillRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ExpenseBillService {

	@Autowired
	private ExpenseBillRepository expenbill;
	
	public void saveExpenseBill(ExpenseBill exbill) {
		expenbill.save(exbill);
	}
	public void deleteExpenseBill(String id) {
		expenbill.deleteById(id);
	}
	public ExpenseBill findExpenBillById(String id) {
		return expenbill.findById(id).orElse(null);
	}
	
	public List<ExpenseBill> findAllExpenseBill(){
		return expenbill.findAll();
	}
	public Page<ExpenseBill> findAllPageExpenseBill(Pageable page){
		return expenbill.findAll(page);
	}
	
	public Page<ExpenseBill> searchExpenseBill(String search, Pageable page){
		return expenbill.searchInBill(search, page);
	}
	public String generateNewId() {
		String maxId = expenbill.findMaxId();
		
		if(maxId == null) return "EX001";
		
		int numberic = Integer.parseInt(maxId.substring(2));
		
		numberic++;
		
		return String.format("EX%03d", numberic);
	}
}
