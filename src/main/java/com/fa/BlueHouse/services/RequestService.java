package com.fa.BlueHouse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.form.Request;
import com.fa.BlueHouse.repositories.RequestRepository;
@Service				
public class RequestService {
	@Autowired
	RequestRepository requestRepository;
	public Page<Request> showAll(Pageable pageable){
		return requestRepository.findAll(pageable);
	}
	public void save(Request form) {
		requestRepository.save(form);
	}
	public String generateNewId() {
		String maxId = requestRepository.findMaxId();
		
		if(maxId == null) return "FR001";
		
		int numberic = Integer.parseInt(maxId.substring(2));
		
		numberic++;
		
		return String.format("FR%03d", numberic);
	}
	public Request findById(String id) {
		return requestRepository.findById(id).orElse(null);
	}
	public Page<Request> showAllForEmployee(String id ,Pageable pageable){
		return requestRepository.findRequestByEmployeeId(id, pageable);
	}
	public Page<Request> showAllForResident(String id ,Pageable pageable){
		return requestRepository.findRequestByResidentId(id, pageable);
	}
	public Page<Request> showAllByKeyword(Pageable pageable,String keyword){
		return requestRepository.findAllByKeyword(keyword, pageable);
	}
	public Page<Request> showAllForEmployeeByKeyword(String id ,Pageable pageable,String keyword){
		return requestRepository.findRequestByEmployeeIdAndKeyword(id, pageable , keyword);
	}
	public Page<Request> showAllForResidentByKeyWord(String id ,Pageable pageable,String keyword){
		return requestRepository.findRequestByResidentIdAndKeyword(id, pageable, keyword);
	}

}
