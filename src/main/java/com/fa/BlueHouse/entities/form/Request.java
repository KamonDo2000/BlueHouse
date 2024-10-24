package com.fa.BlueHouse.entities.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fa.BlueHouse.entities.Employee;
import com.fa.BlueHouse.entities.Repair;
import com.fa.BlueHouse.entities.img.ImgRequest;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Request extends Form implements Serializable{
	

	private static final long serialVersionUID = 1L;
	private String rate;
	@ManyToOne
	Employee employee;
	Date dateAccept;
	@OneToOne
	Repair repair;
	@OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
	    private List<ImgRequest> imgRequests = new ArrayList<>();
	
	public Request() {
		super();
	}

	public Request(String rate, Employee employee, Repair repair, List<ImgRequest> imgRequests,Date dateAccept) {
		super();
		this.rate = rate;
		this.employee = employee;
		this.repair = repair;
		this.imgRequests = imgRequests;
		this.dateAccept = dateAccept;
	}

	
	public List<ImgRequest> getImgRequests() {
		return imgRequests;
	}

	public void setImgRequests(List<ImgRequest> imgRequests) {
		this.imgRequests = imgRequests;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Repair getRepair() {
		return repair;
	}

	public void setRepair(Repair repair) {
		this.repair = repair;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Date getDateAccept() {
		return dateAccept;
	}

	public void setDateAccept(Date dateAccept) {
		this.dateAccept = dateAccept;
	}

}
