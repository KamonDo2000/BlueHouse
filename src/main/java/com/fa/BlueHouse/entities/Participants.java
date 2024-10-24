package com.fa.BlueHouse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Participants {

	@Id
	@Column(name = "ID_Participants")
	private String idParticipants;

	@ManyToOne
	@JoinColumn(name = "ID_event")
	private Event IDEvent;

	@ManyToOne
	@JoinColumn(name = "participant_Emp")
	private Employee participantEmp;

	@ManyToOne
	@JoinColumn(name = "participant_Resi")
	private Resident participantResi;

	@Column(name = "Mission")
	private String mission;

	@Column(name = "Note")
	private String note;

	public String getIdParticipants() {
		return idParticipants;
	}

	public void setIdParticipants(String idParticipants) {
		this.idParticipants = idParticipants;
	}

	public Event getIDEvent() {
		return IDEvent;
	}

	public void setIDEvent(Event iDEvent) {
		IDEvent = iDEvent;
	}

	public Employee getParticipantEmp() {
		return participantEmp;
	}

	public void setParticipantEmp(Employee participantEmp) {
		this.participantEmp = participantEmp;
	}

	public Resident getParticipantResi() {
		return participantResi;
	}

	public void setParticipantResi(Resident participantResi) {
		this.participantResi = participantResi;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getMission() {
		return mission;
	}

	public void setMission(String mission) {
		this.mission = mission;
	}

	public Participants() {
		super();
	}

	public Participants(String idParticipants, Event iDEvent, Employee participantEmp, Resident participantResi,
			String mission, String note) {
		super();
		this.idParticipants = idParticipants;
		IDEvent = iDEvent;
		this.participantEmp = participantEmp;
		this.participantResi = participantResi;
		this.mission = mission;
		this.note = note;
	}

	@Override
	public String toString() {
		return "Participants [idParticipants=" + idParticipants + ", mission=" + mission + ", note=" + note + "]";
	}

}
