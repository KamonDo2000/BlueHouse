package com.fa.BlueHouse.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.fa.BlueHouse.entities.FinancialSupportfee;

public interface FinancialSupportfeeRepositories extends JpaRepository<FinancialSupportfee, String>{
	
	@Query("SELECT re FROM FinancialSupportfee re where re.billCode LIKE %:seacrch% or re.nameFeeType LIKE %:seacrch%  or re.sponsorName LIKE %:seacrch% ")
	Page<FinancialSupportfee> searchFinancialSupportfee(@Param("seacrch")String seacrch, Pageable pageable);
	
	@Query("SELECT re FROM FinancialSupportfee re where re.billCode LIKE %:seacrch% or re.nameFeeType LIKE %:seacrch%  or re.sponsorName LIKE %:seacrch% ")
	List<FinancialSupportfee> searchFinancialSupportfee(@Param("seacrch")String seacrch);
	
	@Query("SELECT COALESCE(SUM(fi.price), 0.0) FROM FinancialSupportfee fi")
    double findTotalAmount();
}
