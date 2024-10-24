package com.fa.BlueHouse.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FinancialSupportfee {

	@Id
	private String billCode;

	private String nameFeeType;

	private String sponsorName;

	private LocalDate datefee;

	private float price;

	public FinancialSupportfee() {
		super();
	}

	public FinancialSupportfee(String billCode, String nameFeeType, String sponsorName, LocalDate datefee,
			float price) {
		super();
		this.billCode = billCode;
		this.nameFeeType = nameFeeType;
		this.sponsorName = sponsorName;
		this.datefee = datefee;
		this.price = price;
	}

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getNameFeeType() {
		return nameFeeType;
	}

	public void setNameFeeType(String nameFeeType) {
		this.nameFeeType = nameFeeType;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public LocalDate getDatefee() {
		return datefee;
	}

	public void setDatefee(LocalDate datefee) {
		this.datefee = datefee;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
