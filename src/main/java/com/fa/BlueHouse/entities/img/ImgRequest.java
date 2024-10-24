package com.fa.BlueHouse.entities.img;

import com.fa.BlueHouse.entities.form.Request;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ImgRequest {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

	public ImgRequest() {
		super();
	}

	public ImgRequest(Long id, String imagePath, Request request) {
		super();
		this.id = id;
		this.imagePath = imagePath;
		this.request = request;
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

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}
    
}
