package com.fa.BlueHouse.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "EMPLOYEEWORK")
public class EmployeeWork implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name = "ID_shiftChedule")
	private ShiftChedule shiftCheduleID;

	@Id
	@ManyToOne
	@JoinColumn(name = "ID_EMPLOYEE")
	private Employee employeeID;

	public ShiftChedule getShiftCheduleID() {
		return shiftCheduleID;
	}

	public void setShiftCheduleID(ShiftChedule shiftCheduleID) {
		this.shiftCheduleID = shiftCheduleID;
	}

	public Employee getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(Employee employeeID) {
		this.employeeID = employeeID;
	}

	public EmployeeWork(ShiftChedule shiftCheduleID, Employee employeeID) {
		super();
		this.shiftCheduleID = shiftCheduleID;
		this.employeeID = employeeID;
	}

	public EmployeeWork() {
		super();
	}

}
