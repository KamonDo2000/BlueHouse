package com.fa.BlueHouse.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Notification")
public class Notification {

	@Id
	@Column(name = "Notification_code")
	private String notificationCode;

	@Column(name = "Title")
	private String title;

	@Column(name = "ContentNoti", columnDefinition = "TEXT")
	private String contentNoti;

	@Column(name = "Attachment")
	private String attachment;

	@Column(name = "Date")
	private LocalDate date;

	@Column(name = "Time")
	private LocalTime time;
	
	@Column(name = "Type_Note")
	private String typeNote;

	@OneToMany(mappedBy = "notification")
	private Set<Receiver> receiver;

	public String getNotificationCode() {
		return notificationCode;
	}

	public void setNotificationCode(String notificationCode) {
		this.notificationCode = notificationCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentNoti() {
		return contentNoti;
	}

	public void setContentNoti(String content) {
		this.contentNoti = content;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public Set<Receiver> getReceiver() {
		return receiver;
	}

	public void setReceiver(Set<Receiver> receiver) {
		this.receiver = receiver;
	}

	public String getTypeNote() {
		return typeNote;
	}

	public void setTypeNote(String typeNote) {
		this.typeNote = typeNote;
	}

	public Notification() {
		super();
	}

	public Notification(String notificationCode, String title, String content, String attachment, LocalDate date,
			LocalTime time) {
		super();
		this.notificationCode = notificationCode;
		this.title = title;
		this.contentNoti = content;
		this.attachment = attachment;
		this.date = date;
		this.time = time;
	}

	@Override
	public String toString() {
		return "Notification [notificationCode=" + notificationCode + ", title=" + title + ", contentNoti="
				+ contentNoti + ", attachment=" + attachment + ", date=" + date + ", time=" + time + ", receiver="
				+ receiver + "]";
	}

}