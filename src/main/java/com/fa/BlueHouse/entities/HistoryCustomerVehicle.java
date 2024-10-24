package com.fa.BlueHouse.entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "HISTORY_CUSTOMER_VEHICLE")
public class HistoryCustomerVehicle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private HistoryCustomerVehicleID id;

	private String type;

	private LocalDate moveOutDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_APARTMENT")
	private Apartment apartmentHIS;

	public HistoryCustomerVehicle() {
		super();
	}

	public HistoryCustomerVehicle(HistoryCustomerVehicleID id, String type, LocalDate moveOutDate,
			Apartment apartmentHIS) {
		super();
		this.id = id;
		this.type = type;
		this.moveOutDate = moveOutDate;
		this.apartmentHIS = apartmentHIS;
	}

	public HistoryCustomerVehicleID getId() {
		return id;
	}

	public void setId(HistoryCustomerVehicleID id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDate getMoveOutDate() {
		return moveOutDate;
	}

	public void setMoveOutDate(LocalDate moveOutDate) {
		this.moveOutDate = moveOutDate;
	}

	public Apartment getApartmentHIS() {
		return apartmentHIS;
	}

	public void setApartmentHIS(Apartment apartmentHIS) {
		this.apartmentHIS = apartmentHIS;
	}

}
