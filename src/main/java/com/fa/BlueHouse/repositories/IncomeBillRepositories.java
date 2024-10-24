package com.fa.BlueHouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.fa.BlueHouse.entities.IncomeBill;

public interface IncomeBillRepositories extends JpaRepository<IncomeBill, String> {

	@Query("FROM IncomeBill Where idApartment.idApartment =:idApartment")
	public Page<IncomeBill> findApartmentBill(String idApartment, Pageable page);
	@Query("FROM IncomeBill ")
	public Page<IncomeBill> findBill( Pageable page);
	@Query("FROM IncomeBill where idIncomeBill LIKE %:seacrch% or status LIKE %:seacrch% or idApartment.idApartment LIKE %:seacrch%")
	public Page<IncomeBill> searchInBill(String seacrch , Pageable pageable);
	@Query("FROM IncomeBill where (idIncomeBill LIKE %:seacrch% or status LIKE %:seacrch% or idApartment.idApartment LIKE %:seacrch% ) and  idApartment.idApartment = :idapart")
	public Page<IncomeBill> searchApartBill(String seacrch , String idapart, Pageable pageable);
	@Query("SELECT MAX(i.idIncomeBill) FROM IncomeBill i")
    String findMaxId();
	
	@Query("FROM IncomeBill b WHERE b.status = :status")
	Page<IncomeBill> findByStatus(@Param("status") String status, Pageable page);

}
