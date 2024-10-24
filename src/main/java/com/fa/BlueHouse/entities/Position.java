package com.fa.BlueHouse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Position {
	@Id
	@Column(name = "ID_Position")
	@NotBlank(message = "* ID Position không được để trống")
	private String idPosition;
	@Column(name = "Name_Position")
	@NotBlank(message = "* Name Position không được để trống")
	private String namePosition;
	@Column(name = "Prorogue")
	@NotBlank(message = "* Prorogue không được để trống")
	private String prorogue;

	public String getIdPosition() {
		return idPosition;
	}

	public void setIdPosition(String idPosition) {
		this.idPosition = idPosition;
	}

	public String getNamePosition() {
		return namePosition;
	}

	public void setNamePosition(String namePosition) {
		this.namePosition = namePosition;
	}

	public String getProrogue() {
		return prorogue;
	}

	public void setProrogue(String prorogue) {
		this.prorogue = prorogue;
	}

	public Position(String idPosition, String namePosition, String prorogue) {
		super();
		this.idPosition = idPosition;
		this.namePosition = namePosition;
		this.prorogue = prorogue;
	}

	public Position() {
		super();
	}

	
	public Position(@NotBlank(message = "* ID Position không được để trống") String idPosition) {
		super();
		this.idPosition = idPosition;
	}

	@Override
	public String toString() {
		return "Position [getIdPosition()=" + getIdPosition() + ", getNamePosition()=" + getNamePosition()
				+ ", getProrogue()=" + getProrogue() + "]";
	}

}
