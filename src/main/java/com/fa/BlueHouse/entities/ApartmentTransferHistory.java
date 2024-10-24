package com.fa.BlueHouse.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "APARTMENT_TRANSFER_HISTORY")
public class ApartmentTransferHistory {

	@Id
	private String idContract;

	@ManyToOne
	@JoinColumn(name = "ID_Resident")
	private Resident idResident;

	private String newHomeowner;

	@ManyToOne
	@JoinColumn(name = "ID_APARTMENT")
	private Apartment apartmentTransfer;

	private LocalDate transferDate;

	@ManyToOne
	@JoinColumn(name = "Manager_codeTransfer")
	private Employee managerCodeTransfer;

	public ApartmentTransferHistory() {
		super();
	}

	public ApartmentTransferHistory(String idContract, Resident idResident, String newHomeowner,
			Apartment apartmentTransfer, LocalDate transferDate, Employee managerCodeTransfer) {
		super();
		this.idContract = idContract;
		this.idResident = idResident;
		this.newHomeowner = newHomeowner;
		this.apartmentTransfer = apartmentTransfer;
		this.transferDate = transferDate;
		this.managerCodeTransfer = managerCodeTransfer;
	}

	public String getIdContract() {
		return idContract;
	}

	public void setIdContract(String idContract) {
		this.idContract = idContract;
	}

	public Resident getIdResident() {
		return idResident;
	}

	public void setIdResident(Resident idResident) {
		this.idResident = idResident;
	}

	public String getNewHomeowner() {
		return newHomeowner;
	}

	public void setNewHomeowner(String newHomeowner) {
		this.newHomeowner = newHomeowner;
	}

	public Apartment getApartmentTransfer() {
		return apartmentTransfer;
	}

	public void setApartmentTransfer(Apartment apartmentTransfer) {
		this.apartmentTransfer = apartmentTransfer;
	}

	public LocalDate getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(LocalDate transferDate) {
		this.transferDate = transferDate;
	}

	public Employee getManagerCodeTransfer() {
		return managerCodeTransfer;
	}

	public void setManagerCodeTransfer(Employee managerCodeTransfer) {
		this.managerCodeTransfer = managerCodeTransfer;
	}

}
