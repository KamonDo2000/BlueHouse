package com.fa.BlueHouse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Administrators {
	@Id
	@Column(name = "ID_BQT")
	@NotBlank(message = "* ID Admin không được để trống")
	private String idBQT;
	@ManyToOne
	@JoinColumn(name = "ID_Resident")
	@NotNull(message ="* ID Position không được để trống" )
	private Resident idResident;
	@ManyToOne
	@JoinColumn(name = "ID_Position")
	@NotNull(message ="* ID Position không được để trống" )
	private Position idPosition;

	public String getIdBQT() {
		return idBQT;
	}

	public void setIdBQT(String idBQT) {
		this.idBQT = idBQT;
	}

	public Resident getIdResident() {
		return idResident;
	}

	public void setIdResident(Resident idResident) {
		this.idResident = idResident;
	}

	public Position getIdPosition() {
		return idPosition;
	}

	public void setIdPosition(Position idPosition) {
		this.idPosition = idPosition;
	}

	public Administrators(String idBQT, Resident idResident, Position idPosition) {
		super();
		this.idBQT = idBQT;
		this.idResident = idResident;
		this.idPosition = idPosition;
	}
	

	public Administrators(@NotBlank(message = "* ID Admin không được để trống") String idBQT) {
		super();
		this.idBQT = idBQT;
	}

	public Administrators() {
		super();
	}

}
