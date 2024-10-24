package com.fa.BlueHouse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.BlueHouse.entities.IncomeBillDetail;

public interface IncomeBillDetailRepositories extends JpaRepository<IncomeBillDetail, String>{
	@Query("FROM IncomeBillDetail Where idIncomeBill.idIncomeBill = :idBill")
	public Page<IncomeBillDetail> findByIdBill(String idBill, Pageable page);
	@Query("FROM IncomeBillDetail ")
	public Page<IncomeBillDetail> findBill( Pageable page);
	@Query("FROM IncomeBillDetail where idbilldetail LIKE %:seacrch% or idIncomeBill.idIncomeBill LIKE %:seacrch% or idfeetype.nameFeetype LIKE %:seacrch%")
	public Page<IncomeBillDetail> searchDetail(String seacrch , Pageable pageable);
	@Query("FROM IncomeBillDetail where (idbilldetail LIKE %:seacrch%  or idfeetype.nameFeetype LIKE %:seacrch%) and idIncomeBill.idIncomeBill = :idbill")
	public Page<IncomeBillDetail> searchDetail(String seacrch ,String idbill, Pageable pageable);
	@Query("SELECT MAX(i.idbilldetail) FROM IncomeBillDetail i")
    String findMaxId();
	
	@Query("SELECT COALESCE(SUM(bd.price * bd.quantity), 0.0) FROM IncomeBillDetail bd JOIN IncomeBill b ON bd.idIncomeBill.idIncomeBill = b.idIncomeBill WHERE b.status = :status")
	double findTotalByStatus(@Param("status") String status);
	
	@Query("SELECT COALESCE(SUM(bd.price * bd.quantity), 0.0) FROM IncomeBillDetail bd")
    double findTotalAmount();
	
	@Query("SELECT COALESCE(SUM(bd.price * bd.quantity), 0.0) FROM IncomeBillDetail bd JOIN FeeType fe ON bd.idfeetype.idFeetype = fe.idFeetype WHERE fe.idFeetype = :feeTypes")
    double findTotalAmountByFeeTypes(@Param("feeTypes") String feeTypes);
}
