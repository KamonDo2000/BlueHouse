package com.fa.BlueHouse.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Feedback {
	@Id
	String idFeedback;
	
	@ManyToOne
	@JoinColumn(name = "resident_id")
	Resident resident;
	
	String typeFeedback;
	Date sentDate;
	String reason;
	String note;
	
	@ManyToOne
	@JoinColumn(name = "employ_id")
	Employee employee;

	public Feedback() {
		super();
	}

	public Feedback(String idFeedback, Resident resident, String typeFeedback, Date sentDate, String reason,
			String note, Employee employee) {
		super();
		this.idFeedback = idFeedback;
		this.resident = resident;
		this.typeFeedback = typeFeedback;
		this.sentDate = sentDate;
		this.reason = reason;
		this.note = note;
		this.employee = employee;
	}

	public String getIdFeedback() {
		return idFeedback;
	}

	public void setIdFeedback(String idFeedback) {
		this.idFeedback = idFeedback;
	}

	public Resident getResident() {
		return resident;
	}

	public void setResident(Resident resident) {
		this.resident = resident;
	}

	public String getTypeFeedback() {
		return typeFeedback;
	}

	public void setTypeFeedback(String typeFeedback) {
		this.typeFeedback = typeFeedback;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
}
