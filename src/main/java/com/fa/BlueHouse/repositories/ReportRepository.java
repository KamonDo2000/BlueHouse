package com.fa.BlueHouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.BlueHouse.entities.form.Report;

public interface ReportRepository extends JpaRepository<Report, String> {

	@Query("SELECT MAX(r.id) FROM Report r")
	String findMaxId();

	@Query("FROM Report r WHERE r.resident.idResident = :id")
	Page<Report> findReportByResidentId(@Param("id") String id, Pageable pageable);

	@Query(" FROM Report r WHERE r.idForm LIKE %:keyword% OR r.status LIKE %:keyword% OR r.resident.nameResident LIKE %:keyword% ")
	Page<Report> findReportByKeyword(@Param("keyword") String keyword, Pageable pageable);
	
	@Query("FROM Report r WHERE r.resident.idResident = :id AND (r.idForm LIKE %:keyword% OR r.status LIKE %:keyword% OR r.resident.nameResident LIKE %:keyword% )")
	Page<Report> findReportByResidentIdAndKeyword(@Param("id") String id, Pageable pageable ,@Param("keyword") String keyword);
}
