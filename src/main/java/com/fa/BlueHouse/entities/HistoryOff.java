package com.fa.BlueHouse.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "HISTORYOFF")
public class HistoryOff {

	@Id
	@Column(name = "ID_HISTORYOFF")
	private String historyOffID;

	@Column(name = "DayOff", columnDefinition = "DATE")
	private LocalDate dayOff;

	@Column(name = "StatusOff")
	private String statusOff;

	@Column(name = "Reason")
	private String reason;

	@Column(name = "NOTE")
	private String note;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMPLOYEE")
	private Employee employeeID;

	public String getHistoryOffID() {
		return historyOffID;
	}

	public void setHistoryOffID(String historyOffID) {
		this.historyOffID = historyOffID;
	}

	public LocalDate getDayOff() {
		return dayOff;
	}

	public void setDayOff(LocalDate dayOff) {
		this.dayOff = dayOff;
	}

	public String getStatusOff() {
		return statusOff;
	}

	public void setStatusOff(String statusOff) {
		this.statusOff = statusOff;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public HistoryOff(String historyOffID, LocalDate dayOff, String statusOff, String reason, String note,
			Employee employeeID) {
		super();
		this.historyOffID = historyOffID;
		this.dayOff = dayOff;
		this.statusOff = statusOff;
		this.reason = reason;
		this.note = note;
		this.employeeID = employeeID;
	}

	public HistoryOff() {
		super();
	}

}
