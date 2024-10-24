package com.fa.BlueHouse.entities.img;

import com.fa.BlueHouse.entities.form.Report;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class ImgReport {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "Report_id")
    private Report report;

    
    
	public ImgReport() {
		super();
	}

	public ImgReport(Long id, String imagePath, Report report) {
		super();
		this.id = id;
		this.imagePath = imagePath;
		this.report = report;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

}
