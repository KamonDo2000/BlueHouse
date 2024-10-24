package com.fa.BlueHouse.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "SHIFTCHEDULE")
public class ShiftChedule {

	@Id
	@Column(name = "ShiftCheduleID")
	private String shiftCheduleID;

	@Column(name = "STARTTIME", columnDefinition = "TIME")
	private LocalTime startTime;

	@Column(name = "ENDTIME", columnDefinition = "TIME")
	private LocalTime endTime;

	@Column(name = "DATE", columnDefinition = "DATE")
	private LocalDate date;

	@Column(name = "TASK")
	private String task;

	@Column(name = "Mission")
	private String mission;

	@OneToMany(mappedBy = "shiftCheduleID", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<EmployeeWork> employeeWork;

	public String getShiftCheduleID() {
		return shiftCheduleID;
	}

	public void setShiftCheduleID(String shiftCheduleID) {
		this.shiftCheduleID = shiftCheduleID;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getMission() {
		return mission;
	}

	public void setMission(String mission) {
		this.mission = mission;
	}

	public Set<EmployeeWork> getEmployeeWork() {
		return employeeWork;
	}

	public void setEmployeeWork(Set<EmployeeWork> employeeWork) {
		this.employeeWork = employeeWork;
	}

	public ShiftChedule(String shiftCheduleID, LocalTime startTime, LocalTime endTime, LocalDate date, String task,
			String mission, Set<EmployeeWork> employeeWork) {
		super();
		this.shiftCheduleID = shiftCheduleID;
		this.startTime = startTime;
		this.endTime = endTime;
		this.date = date;
		this.task = task;
		this.mission = mission;
		this.employeeWork = employeeWork;
	}

	public ShiftChedule() {
		super();
	}

}
