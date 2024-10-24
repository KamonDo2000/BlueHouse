package com.fa.BlueHouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.BlueHouse.entities.Repair;

public interface RepairRepository extends JpaRepository<Repair, String> {
	@Query("SELECT MAX(r.id) FROM Repair r")
    String findMaxId();
	 @Query("FROM Repair r WHERE r.employee.employeeID = :id")
	 Page<Repair> findRepairByEmployeeId(@Param("id") String id , Pageable pageable );
}



