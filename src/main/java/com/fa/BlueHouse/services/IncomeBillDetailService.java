package com.fa.BlueHouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.IncomeBillDetail;
import com.fa.BlueHouse.repositories.IncomeBillDetailRepositories;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class IncomeBillDetailService {

	
	@Autowired
	private IncomeBillDetailRepositories incodetail;
	
	public List<IncomeBillDetail> findallIncomeDetail(){
		return incodetail.findAll();
	}
	public IncomeBillDetail findById(String id) {
		return incodetail.findById(id).orElse(null);
	}

	public void deleteDetail(String id) {
		incodetail.deleteById(id);
	}
	public void saveIncobillDetail(IncomeBillDetail inco) {
		incodetail.save(inco);
	}
	public Page<IncomeBillDetail> findAllbill(String idbill, Pageable page){
		return incodetail.findByIdBill(idbill, page);
	}
	public Page<IncomeBillDetail> findAll( Pageable page){
		return incodetail.findAll(page);
	}
//	public Page<IncomeBillDetail> findByIdbill(String id, Pageable page){
//		return incodetail.findByIdBill(id, page);
//	}
	public Page<IncomeBillDetail> searchDetail(String search, Pageable page){
		return incodetail.searchDetail(search, page);
	}
	public Page<IncomeBillDetail> searchDetail(String search,String idbill, Pageable page){
		return incodetail.searchDetail(search,idbill, page);
	}
	public String generateNewId() {
		String maxId = incodetail.findMaxId();
		
		if(maxId == null) return "DT001";
		
		int numberic = Integer.parseInt(maxId.substring(2));
		
		numberic++;
		
		return String.format("DT%03d", numberic);
	}
}
