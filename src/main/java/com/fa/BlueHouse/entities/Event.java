package com.fa.BlueHouse.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Event {
	@Id
	@Column(name = "ID_Event")
	String idEvent;

	@Column(name = "name_Event")
	String nameEvent;

	@Column(name = "start_Date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate startDate;

	@Column(name = "end_Date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate endDate;

	@Column(name = "Attachment")
	private String attachment;

	@Column(name = "start_Time")
	@DateTimeFormat(pattern = "HH:mm:ss")
	LocalTime startTime;

	@Column(name = "end_Time")
	@DateTimeFormat(pattern = "HH:mm:ss")
	LocalTime endTime;

	@Column(name = "location")
	String location;

	@Column(name = "Count_Participants")
	int numberOfParticipants;

	@OneToOne
	@JoinColumn(name = "Bill")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ExpenseBill bill;

	@ManyToOne
	@JoinColumn(name = "ID_Oganizer")
	private Resident IDOganizer;

	@OneToMany(mappedBy = "IDEvent")
	private List<Participants> listParticipants;

	public String getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(String idEvent) {
		this.idEvent = idEvent;
	}

	public String getNameEvent() {
		return nameEvent;
	}

	public void setNameEvent(String nameEvent) {
		this.nameEvent = nameEvent;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getNumberOfParticipants() {
		return numberOfParticipants;
	}

	public void setNumberOfParticipants(int numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}

	public ExpenseBill getBill() {
		return bill;
	}

	public void setBill(ExpenseBill bill) {
		this.bill = bill;
	}

	public Resident getIDOganizer() {
		return IDOganizer;
	}

	public void setIDOganizer(Resident iDOganizer) {
		IDOganizer = iDOganizer;
	}

	public List<Participants> getListParticipants() {
		return listParticipants;
	}

	public void setListParticipants(List<Participants> listParticipants) {
		this.listParticipants = listParticipants;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public Event() {
		super();
	}

	public Event(String idEvent, String nameEvent, LocalDate startDate, LocalDate endDate, LocalTime startTime,
			LocalTime endTime, String location, int numberOfParticipants) {
		super();
		this.idEvent = idEvent;
		this.nameEvent = nameEvent;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location;
		this.numberOfParticipants = numberOfParticipants;
	}

	public Event(String idEvent, String nameEvent, LocalDate startDate, LocalDate endDate, String attachment,
			LocalTime startTime, LocalTime endTime, String location, int numberOfParticipants, ExpenseBill bill,
			Resident iDOganizer) {
		super();
		this.idEvent = idEvent;
		this.nameEvent = nameEvent;
		this.startDate = startDate;
		this.endDate = endDate;
		this.attachment = attachment;
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location;
		this.numberOfParticipants = numberOfParticipants;
		this.bill = bill;
		IDOganizer = iDOganizer;
	}

	@Override
	public String toString() {
		return "Event [idEvent=" + idEvent + ", nameEvent=" + nameEvent + ", startDate=" + startDate + ", endDate="
				+ endDate + ", startTime=" + startTime + ", endTime=" + endTime + ", location=" + location
				+ ", numberOfParticipants=" + numberOfParticipants + ", bill=" + bill + ", IDOganizer=" + IDOganizer
				+ ", listParticipants=" + listParticipants + "]";
	}

}
