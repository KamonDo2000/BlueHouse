package com.fa.BlueHouse.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class HistoryCustomerVehicleID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String vehicleNumber;

	private LocalDateTime moveInDate;

	public HistoryCustomerVehicleID() {
		super();
	}

	public HistoryCustomerVehicleID(String vehicleNumber, LocalDateTime moveInDate) {
		super();
		this.vehicleNumber = vehicleNumber;
		this.moveInDate = moveInDate;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public LocalDateTime getMoveInDate() {
		return moveInDate;
	}

	public void setMoveInDate(LocalDateTime moveInDate) {
		this.moveInDate = moveInDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(moveInDate, vehicleNumber);
	}

}
