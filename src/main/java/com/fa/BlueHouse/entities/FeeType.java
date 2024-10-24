package com.fa.BlueHouse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FeeType {
	@Id
	@Column(name = "ID_FeeType")
	private String idFeetype;
	
	@Column(name = "Name_FeeType")
	private String nameFeetype;
	
	@Column(name = "Price")
	private float price;
	
	

	public String getIdFeetype() {
		return idFeetype;
	}

	public void setIdFeetype(String idFeetype) {
		this.idFeetype = idFeetype;
	}

	public String getNameFeetype() {
		return nameFeetype;
	}

	public void setNameFeetype(String nameFeetype) {
		this.nameFeetype = nameFeetype;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public FeeType(String idFeetype, String nameFeetype, float price) {
		super();
		this.idFeetype = idFeetype;
		this.nameFeetype = nameFeetype;
		this.price = price;
	}
	public FeeType(String idFeetype) {
		super();
		this.idFeetype = idFeetype;
	}

	public FeeType() {
		super();
	}


}
