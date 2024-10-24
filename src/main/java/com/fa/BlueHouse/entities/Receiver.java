package com.fa.BlueHouse.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Receiver")
public class Receiver {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDReceiver")
	private int idReceiver;

	@ManyToOne
	@JoinColumn(name = "ID_ThongBao")
	private Notification notification;

	@ManyToOne
	@JoinColumn(name = "sender_Emp")
	private Employee senderEmp;

	@ManyToOne
	@JoinColumn(name = "receiver_Emp")
	private Employee receiverEmp;

	@ManyToOne
	@JoinColumn(name = "sender_Resi")
	private Resident senderResi;

	@ManyToOne
	@JoinColumn(name = "receiver_Resi")
	private Resident receiverResi;

	private int status;

	public int getIdReceiver() {
		return idReceiver;
	}

	public void setIdReceiver(int idReceiver) {
		this.idReceiver = idReceiver;
	}

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public Employee getSenderEmp() {
		return senderEmp;
	}

	public void setSenderEmp(Employee senderEmp) {
		this.senderEmp = senderEmp;
	}

	public Employee getReceiverEmp() {
		return receiverEmp;
	}

	public void setReceiverEmp(Employee receiverEmp) {
		this.receiverEmp = receiverEmp;
	}

	public Resident getSenderResi() {
		return senderResi;
	}

	public void setSenderResi(Resident senderResi) {
		this.senderResi = senderResi;
	}

	public Resident getReceiverResi() {
		return receiverResi;
	}

	public void setReceiverResi(Resident receiverResi) {
		this.receiverResi = receiverResi;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Receiver(Notification notification, Employee senderEmp, Resident senderResi, Employee receiverEmp,
			Resident receiverResi, int status) {
		super();
		this.notification = notification;
		this.senderEmp = senderEmp;
		this.receiverEmp = receiverEmp;
		this.senderResi = senderResi;
		this.receiverResi = receiverResi;
		this.status = status;
	}

	public Receiver() {
		super();
	}

	@Override
	public String toString() {
		return "Receiver [idReceiver=" + idReceiver + ", notification=" + notification + ", senderEmp=" + senderEmp
				+ ", receiverEmp=" + receiverEmp + ", senderResi=" + senderResi + ", receiverResi=" + receiverResi
				+ "]";
	}

}