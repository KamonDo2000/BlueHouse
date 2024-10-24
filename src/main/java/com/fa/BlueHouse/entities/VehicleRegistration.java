package com.fa.BlueHouse.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "VEHICLE_REGISTRATION")
public class VehicleRegistration {

	@Id
	private String idVehicle;

	@ManyToOne
	@JoinColumn(name = "Fee_type_code")
	private FeeType feeTypeCode;

	private LocalDate registrationDate;

	private LocalDate expirationDate;

	private String vehicleNumber;
	
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_APARTMENT")
	private Apartment apartmentVE;

	public VehicleRegistration() {
		super();
	}

	public VehicleRegistration(String idVehicle, FeeType feeTypeCode, LocalDate registrationDate,
			LocalDate expirationDate, String vehicleNumber, Apartment apartmentVE) {
		super();
		this.idVehicle = idVehicle;
		this.feeTypeCode = feeTypeCode;
		this.registrationDate = registrationDate;
		this.expirationDate = expirationDate;
		this.vehicleNumber = vehicleNumber;
		this.apartmentVE = apartmentVE;
	}

	public String getIdVehicle() {
		return idVehicle;
	}

	public void setIdVehicle(String idVehicle) {
		this.idVehicle = idVehicle;
	}

	public FeeType getFeeTypeCode() {
		return feeTypeCode;
	}

	public void setFeeTypeCode(FeeType feeTypeCode) {
		this.feeTypeCode = feeTypeCode;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Apartment getApartmentVE() {
		return apartmentVE;
	}

	public void setApartmentVE(Apartment apartmentVE) {
		this.apartmentVE = apartmentVE;
	}

}
