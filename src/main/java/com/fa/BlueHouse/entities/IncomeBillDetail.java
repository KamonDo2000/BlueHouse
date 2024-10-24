package com.fa.BlueHouse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class IncomeBillDetail {
	@Id
	@Column(name = "ID_BillDetail")
	@NotBlank(message = "* ID BillDetail không được bỏ trống")
	private String idbilldetail;
	@ManyToOne
	@JoinColumn(name = "ID_IconmeBill")
	private IncomeBill idIncomeBill;
	@ManyToOne
	@JoinColumn(name = "ID_FeeType")
	private FeeType idfeetype;
	@Column(name = "Quantity")
	@Min(value = 1, message = "Quantity Phải lớn hơn 0")
	private float quantity;
	@Column(name = "Price")
	private float price;

	public String getIdbilldetail() {
		return idbilldetail;
	}

	public void setIdbilldetail(String idbilldetail) {
		this.idbilldetail = idbilldetail;
	}

	public IncomeBill getIdIncomeBill() {
		return idIncomeBill;
	}

	public void setIdIncomeBill(IncomeBill idIncomeBill) {
		this.idIncomeBill = idIncomeBill;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public FeeType getIdfeetype() {
		return idfeetype;
	}

	public void setIdfeetype(FeeType idfeetype) {
		this.idfeetype = idfeetype;
	}
	

	public IncomeBillDetail(String idbilldetail, IncomeBill idIncomeBill, FeeType idfeetype, float quantity,
			float price) {
		super();
		this.idbilldetail = idbilldetail;
		this.idIncomeBill = idIncomeBill;
		this.idfeetype = idfeetype;
		this.quantity = quantity;
		this.price = price;
	}
	

	public IncomeBillDetail(String id,IncomeBill idIncomeBill) {
		super();
		this.idbilldetail = id;
		this.idIncomeBill = idIncomeBill;
	}
	public IncomeBillDetail(String id) {
		super();
		this.idbilldetail = id;
		
	}

	public IncomeBillDetail() {
		super();
	}

	@Override
	public String toString() {
		return "BillDetail [getIdbilldetail()=" + getIdbilldetail() + ", getQuantity()=" + getQuantity()
				+ ", getPrice()=" + getPrice() + "]";
	}

}
