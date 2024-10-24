package com.fa.BlueHouse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TIMESHEET")
public class Timesheet {

	@Id
	@Column(name = "ID_Timesheet")
	private String timesheetID;

	@Column(name = "TOTALDAYWORK")
	private int totalDayWork;

	@Column(name = "TOTALOFFPERMIT")
	private int totalOffPermitt;

	@Column(name = "TOTALOFF")
	private int totalOff;

	@Column(name = "NOTE")
	private String note;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMPLOYEE")
	private Employee employeeID;

	public String getTimesheetID() {
		return timesheetID;
	}

	public void setTimesheetID(String timesheetID) {
		this.timesheetID = timesheetID;
	}

	public int getTotalDayWork() {
		return totalDayWork;
	}

	public void setTotalDayWork(int totalDayWork) {
		this.totalDayWork = totalDayWork;
	}

	public int getTotalOffPermitt() {
		return totalOffPermitt;
	}

	public void setTotalOffPermitt(int totalOffPermitt) {
		this.totalOffPermitt = totalOffPermitt;
	}

	public int getTotalOff() {
		return totalOff;
	}

	public void setTotalOff(int totalOff) {
		this.totalOff = totalOff;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Employee getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(Employee employeeID) {
		this.employeeID = employeeID;
	}

	public Timesheet(String timesheetID, int totalDayWork, int totalOffPermitt, int totalOff, String note,
			Employee employeeID) {
		super();
		this.timesheetID = timesheetID;
		this.totalDayWork = totalDayWork;
		this.totalOffPermitt = totalOffPermitt;
		this.totalOff = totalOff;
		this.note = note;
		this.employeeID = employeeID;
	}

	public Timesheet() {
		super();
	}

}
