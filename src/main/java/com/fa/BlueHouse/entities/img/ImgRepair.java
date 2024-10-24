package com.fa.BlueHouse.entities.img;

import com.fa.BlueHouse.entities.Repair;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ImgRepair {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String imagePath;

	@ManyToOne
	@JoinColumn(name = "Repair_id")
	private Repair repair;

	public ImgRepair() {
		super();
	}

	public ImgRepair(Long id, String imagePath, Repair repair) {
		super();
		this.id = id;
		this.imagePath = imagePath;
		this.repair = repair;
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

	public Repair getRepair() {
		return repair;
	}

	public void setRepair(Repair repair) {
		this.repair = repair;
	}

}
