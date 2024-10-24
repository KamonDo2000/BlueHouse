package com.fa.BlueHouse.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Assets {
	@Id
	String idAsset;

	@NotEmpty(message = "Please enter location.")
	String location;
	@NotEmpty(message = "Please enter name assets")
	String name;
	@Min(value = 1, message = "Invalid number of assets. Minimum should be 1.")
	int quantityOfAssets;
	@Min(value = 1000, message = "Invalid price of assets. Minimum should be 1000.")
	long priceOfAssets;

	@ManyToOne
	Employee employ;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Assets_Repair", joinColumns = @JoinColumn(name = "idAsset"), inverseJoinColumns = @JoinColumn(name = "id"))
	private List<Repair> repair;

	@OneToMany(mappedBy = "asset")
	List<Schedules> schedules;

	public Assets() {
		super();
	}

	public Assets(String idAsset, String location, String name, int quantityOfAssets, long priceOfAssets,
			Employee employ, List<Schedules> schedules) {
		super();
		this.idAsset = idAsset;
		this.name = name;
		this.quantityOfAssets = quantityOfAssets;
		this.priceOfAssets = priceOfAssets;
		this.employ = employ;
		this.schedules = schedules;
	}

	public String getIdAsset() {
		return idAsset;
	}

	public void setIdAsset(String idAsset) {
		this.idAsset = idAsset;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantityOfAssets() {
		return quantityOfAssets;
	}

	public void setQuantityOfAssets(int quantityOfAssets) {
		this.quantityOfAssets = quantityOfAssets;
	}

	public long getPriceOfAssets() {
		return priceOfAssets;
	}

	public void setPriceOfAssets(long priceOfAssets) {
		this.priceOfAssets = priceOfAssets;
	}

	public Employee getEmploy() {
		return employ;
	}

	public void setEmploy(Employee employ) {
		this.employ = employ;
	}

	public List<Schedules> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedules> schedules) {
		this.schedules = schedules;
	}

	public List<Repair> getRepair() {
		return repair;
	}

	public void setRepair(List<Repair> repair) {
		this.repair = repair;
	}

}