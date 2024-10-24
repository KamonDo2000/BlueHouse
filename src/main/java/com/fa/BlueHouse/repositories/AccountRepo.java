package com.fa.BlueHouse.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.BlueHouse.authen.model.Account;
import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.entities.Resident;

public interface AccountRepo extends JpaRepository<Account, String> {

	@Query("SELECT a FROM Account a WHERE a.username LIKE %:keyword% ")
	Page<Account> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

	@Query("SELECT e FROM Employee e LEFT JOIN Account a ON e.employeeID = a.employee.employeeID WHERE a.employee.employeeID IS NULL")
	List<Employee> getEmpNotInAccount();
	
	@Query("SELECT r FROM Resident r LEFT JOIN Account a ON r.idResident = a.resident.idResident WHERE a.resident.idResident IS NULL")
	List<Resident> getReciNotInAccount();
	
	@Query("SELECT a FROM Account a WHERE a.employee.employeeID = :idEmp ")
	List<Account> getAccByEmp(@Param("idEmp") String idEmp);

	@Query("SELECT a FROM Account a WHERE a.resident.idResident = :idResi ")
	List<Account> getAccByResi(@Param("idResi") String idResi);

	@Query("SELECT a FROM Account a WHERE a.role = 2 ")
	List<Account> findRole2();

}
