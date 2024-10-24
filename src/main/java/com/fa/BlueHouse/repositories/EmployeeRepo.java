package com.fa.BlueHouse.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.BlueHouse.entities.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, String> {

	@Query("SELECT emp FROM Employee emp WHERE emp.fullName LIKE %:keyword% ")
	Page<Employee> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
	
	@Query("SELECT emp FROM Employee emp WHERE emp.employeeID LIKE %:keyword% ")
	List<Employee> findByKeyword(@Param("keyword") String keyword);
	
	@Query("SELECT emp FROM Employee emp WHERE emp.office = :office AND emp.duty = 'Manager'")
	List<Employee> getManagerByOffice(@Param("office") String office);

	@Query("SELECT e FROM Employee e WHERE e.duty = 'Manager'")
	List<Employee> findAdministrator();
	
	@Query("SELECT MAX(e.employeeID) FROM Employee e")
    String findMaxId();

}
