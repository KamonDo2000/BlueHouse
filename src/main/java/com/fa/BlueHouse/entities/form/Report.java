package com.fa.BlueHouse.entities.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fa.BlueHouse.entities.Resident;
import com.fa.BlueHouse.entities.img.ImgReport;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Report extends Form implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name = "Id_Admin")
	Resident admin;
	@OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    private List<ImgReport> imgReports = new ArrayList<>();
	String opinion;
	Date dateAccept;
	public Report() {
		super();
	}


	public Resident getAdmin() {
		return admin;
	}

	public void setAdmin(Resident admin) {
		this.admin = admin;
	}


	public List<ImgReport> getImgReports() {
		return imgReports;
	}


	public void setImgReports(List<ImgReport> imgReports) {
		this.imgReports = imgReports;
	}


	public Report(Resident admin, List<ImgReport> imgReports, String opinion,Date dateAccept) {
		super();
		this.admin = admin;
		this.imgReports = imgReports;
		this.opinion = opinion;
		this.dateAccept = dateAccept;
	}


	public String getOpinion() {
		return opinion;
	}


	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}


	public Date getDateAccept() {
		return dateAccept;
	}


	public void setDateAccept(Date dateAccept) {
		this.dateAccept = dateAccept;
	}
	
}
