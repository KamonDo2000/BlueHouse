package com.fa.BlueHouse.entities;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class IncomeBill {
	@Id
	@Column(name = "ID_IncomeBill")
	private String idIncomeBill;
	@ManyToOne
	@JoinColumn(name = "ID_Apartment")
	private Apartment idApartment;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "Bill_Date")
	private LocalDate billDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "Paymen_Date")
	private LocalDate paymentDate;
	@Column(name = "Status")
	private String status;
	@ManyToOne
	@JoinColumn(name = "employeeID")
	private Employee idEmployee;
	@OneToMany(mappedBy = "idIncomeBill")
	private List<IncomeBillDetail> listdetail;
	public IncomeBill(String idIncomeBill, Apartment idApartment, LocalDate billDate, LocalDate paymentDate,
			String status) {
		super();
		this.idIncomeBill = idIncomeBill;
		this.idApartment = idApartment;
		this.billDate = billDate;
		this.paymentDate = paymentDate;
		this.status = status;
		
	}
	
	public Employee getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Employee idEmployee) {
		this.idEmployee = idEmployee;
	}

	public IncomeBill(String id,Apartment idApartment) {
		super();
		this.idIncomeBill = id;
		this.idApartment = idApartment;
	}
	public IncomeBill(String id) {
		super();
		this.idIncomeBill = id;
		
	}

	public IncomeBill() {
		super();
	}
	
	public List<IncomeBillDetail> getListdetail() {
		return listdetail;
	}
	public void setListdetail(List<IncomeBillDetail> listdetail) {
		this.listdetail = listdetail;
	}
	public String getIdIncomeBill() {
		return idIncomeBill;
	}
	public void setIdIncomeBill(String idIncomeBill) {
		this.idIncomeBill = idIncomeBill;
	}
	public Apartment getIdApartment() {
		return idApartment;
	}
	public void setIdApartment(Apartment idApartment) {
		this.idApartment = idApartment;
	}
	public LocalDate getBillDate() {
		return billDate;
	}
	public void setBillDate(LocalDate billDate) {
		this.billDate = billDate;
	}
	public LocalDate getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public double getTotalbill() {
        return listdetail.stream()
                      .mapToDouble(detail -> detail.getQuantity() * detail.getPrice())
                      .sum();
    }
	@Override
	public String toString() {
		return "IncomeBill [getIdIncomeBill()=" + getIdIncomeBill() + ", getBillDate()=" + getBillDate()
				+ ", getPaymentDate()=" + getPaymentDate() + ", getStatus()=" + getStatus() + "]";
	}
	
	
}
