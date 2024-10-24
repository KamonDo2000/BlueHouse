package com.fa.BlueHouse.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Schedules {
	@Id
	String idSchedules;

	@ManyToOne
	Assets asset;

	@ManyToOne
	Employee employ;

	Date date;

	@ManyToOne
	Employee employee;

	public Schedules() {
		super();
	}

	public String getIdSchedules() {
		return idSchedules;
	}

	public void setIdSchedules(String idSchedules) {
		this.idSchedules = idSchedules;
	}

	public Assets getAsset() {
		return asset;
	}

	public void setAsset(Assets asset) {
		this.asset = asset;
	}

	public Employee getEmploy() {
		return employ;
	}

	public void setEmploy(Employee employ) {
		this.employ = employ;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
}