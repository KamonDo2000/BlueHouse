package com.fa.BlueHouse.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fa.BlueHouse.entities.FinancialSupportfee;
import com.fa.BlueHouse.repositories.FinancialSupportfeeRepositories;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class FinancialSupportfeeServices {

	@Autowired
	private FinancialSupportfeeRepositories finsupfee;
	
	public void savefinansupfee(FinancialSupportfee financialSupportfee) {
		finsupfee.save(financialSupportfee);
	}
	
	public FinancialSupportfee findaById(String id) {
		return finsupfee.findById(id).orElse(null);
	}
	
	public List<FinancialSupportfee> findafinansuppfee(){
		return finsupfee.findAll();
	}
	
	public List<FinancialSupportfee> findByKeyword(String keyword) {
		return finsupfee.searchFinancialSupportfee(keyword);
	}
	
	public void deletefinsupFee(String id) {
		finsupfee.deleteById(id);
	}
	
	public void  updateFinsupFee(FinancialSupportfee financiasupfee) {
		finsupfee.save(financiasupfee);
	}
	
	public Page<FinancialSupportfee> allFinsupfee(Pageable pageable){
		return finsupfee.findAll(pageable);
	}
	
	public Page<FinancialSupportfee> seachFinsupfee(Pageable pageable, String search){
		return finsupfee.searchFinancialSupportfee(search, pageable);
	}
}
