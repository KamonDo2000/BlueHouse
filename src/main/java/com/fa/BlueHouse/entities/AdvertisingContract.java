package com.fa.BlueHouse.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class AdvertisingContract {

	@Id
	private String contractCode;
	
	private String contractName;
	
	private String company;
	
	private LocalDate daySign;
	
	private int numberOfAdv;
	
	@ManyToOne
	@JoinColumn(name = "nameEmp")
	private Employee empadv;
	
	@ManyToOne
	@JoinColumn(name = "nameFeeType")
	private FeeType typeadv;
	
	public AdvertisingContract() {
		super();
	}

	public AdvertisingContract(String contractCode, String contractName, String company, LocalDate daySign,
			int numberOfAdv, Employee empadv, FeeType typeadv) {
		super();
		this.contractCode = contractCode;
		this.contractName = contractName;
		this.company = company;
		this.daySign = daySign;
		this.numberOfAdv = numberOfAdv;
		this.empadv = empadv;
		this.typeadv = typeadv;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public LocalDate getDaySign() {
		return daySign;
	}

	public void setDaySign(LocalDate daySign) {
		this.daySign = daySign;
	}

	public int getNumberOfAdv() {
		return numberOfAdv;
	}

	public void setNumberOfAdv(int numberOfAdv) {
		this.numberOfAdv = numberOfAdv;
	}

	public Employee getEmpadv() {
		return empadv;
	}

	public void setEmpadv(Employee empadv) {
		this.empadv = empadv;
	}

	public FeeType getTypeadv() {
		return typeadv;
	}

	public void setTypeadv(FeeType typeadv) {
		this.typeadv = typeadv;
	}
	
	
}
