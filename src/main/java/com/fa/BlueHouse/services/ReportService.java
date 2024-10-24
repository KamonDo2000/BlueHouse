package com.fa.BlueHouse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.BlueHouse.entities.form.Report;
import com.fa.BlueHouse.repositories.ReportRepository;

@Service
public class ReportService {
	@Autowired
	ReportRepository reportRepository;
	public Page<Report> showAll(Pageable pageable){
		return reportRepository.findAll(pageable);
	}
	public void save(Report form) {
		reportRepository.save(form);
	}
	public String generateNewId() {
		String maxId = reportRepository.findMaxId();
		
		if(maxId == null) return "FR001";
		
		int numberic = Integer.parseInt(maxId.substring(2));
		
		numberic++;
		
		return String.format("FR%03d", numberic);
	}
	public Page<Report> findRPByResidentId(String id, Pageable pageable){
		return reportRepository.findReportByResidentId(id, pageable);
	}
	public Report findById(String id) {
		return reportRepository.findById(id).orElse(null);
	}
	public Page<Report> findByKeyword(Pageable pageable , String keyword){
		return reportRepository.findReportByKeyword( keyword ,pageable );
	}
	public Page<Report> findRPByResidentIdAndKeyword(String id, Pageable pageable, String keyword){
		return reportRepository.findReportByResidentIdAndKeyword(id, pageable, keyword);
	}
}
