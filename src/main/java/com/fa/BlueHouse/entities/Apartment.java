package com.fa.BlueHouse.entities;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "APARTMENT")
public class Apartment {

	@Id
	@Column(name = "ID_APARTMENT")
	private String idApartment;

	@Column(name = "ID_HOMEOWNER")
	private String idHomeowner;

	@Column(name = "FLOOR")
	private String floor;

	@Column(name = "NUMBER_OF_ROOMS")
	private String numberOfRooms;

	@Column(name = "AREA")
	private double area;

	@Column(name = "APARTMENTNUMBER")
	private String apartmentNumber;

	@Column(name = "TYPEAPARTMENT")
	private String typeApartment;

	@OneToMany(mappedBy = "idApartment")
	List<Resident> listCuDan;

	@OneToMany(mappedBy = "apartmentVE")
	List<VehicleRegistration> vehicleRegistrations;

	@OneToOne(mappedBy = "idApa")
	private RentalSpaceContract ren;

	@OneToMany(mappedBy = "apartmentHIS")
	List<HistoryCustomerVehicle> listHistoryCustomer;

	@OneToMany(mappedBy = "apartmentTransfer")
	List<ApartmentTransferHistory> listapartmentHistories;

	public Apartment() {
		super();
	}

	public Apartment(String idApartment, String idHomeowner, String floor, String numberOfRooms, double area,
			String apartmentNumber, String typeApartment, List<VehicleRegistration> vehicleRegistrations,
			RentalSpaceContract ren) {
		super();
		this.idApartment = idApartment;
		this.idHomeowner = idHomeowner;
		this.floor = floor;
		this.numberOfRooms = numberOfRooms;
		this.area = area;
		this.apartmentNumber = apartmentNumber;
		this.typeApartment = typeApartment;
		this.vehicleRegistrations = vehicleRegistrations;
		this.ren = ren;
	}

	public List<VehicleRegistration> getVehicleRegistrations() {
		return vehicleRegistrations;
	}

	public void setVehicleRegistrations(List<VehicleRegistration> vehicleRegistrations) {
		this.vehicleRegistrations = vehicleRegistrations;
	}

	public List<HistoryCustomerVehicle> getListHistoryCustomer() {
		return listHistoryCustomer;
	}

	public void setListHistoryCustomer(List<HistoryCustomerVehicle> listHistoryCustomer) {
		this.listHistoryCustomer = listHistoryCustomer;
	}

	public List<ApartmentTransferHistory> getListapartmentHistories() {
		return listapartmentHistories;
	}

	public void setListapartmentHistories(List<ApartmentTransferHistory> listapartmentHistories) {
		this.listapartmentHistories = listapartmentHistories;
	}

	public RentalSpaceContract getRen() {
		return ren;
	}

	public void setRen(RentalSpaceContract ren) {
		this.ren = ren;
	}

	public String getIdApartment() {
		return idApartment;
	}

	public void setIdApartment(String idApartment) {
		this.idApartment = idApartment;
	}

	public String getIdHomeowner() {
		return idHomeowner;
	}

	public void setIdHomeowner(String idHomeowner) {
		this.idHomeowner = idHomeowner;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(String numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public String getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(String apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public String getTypeApartment() {
		return typeApartment;
	}

	public void setTypeApartment(String typeApartment) {
		this.typeApartment = typeApartment;
	}

	public List<Resident> getListCuDan() {
		return listCuDan;
	}

	public void setListCuDan(List<Resident> listCuDan) {
		this.listCuDan = listCuDan;
	}

}
