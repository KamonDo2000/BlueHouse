package com.fa.BlueHouse.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {

	@Id
	@Column(name = "ID_EMPLOYEE")
	@Pattern(regexp = "^EMP\\d{3}$", message = "Must start with 'EMP' and be followed by 3 digits.")
	private String employeeID;

	@Column(name = "Name")
	@Pattern(regexp = "^[a-zA-Z\\s]{3,30}$", message = "Must be between 3 and 30 characters.")
	private String fullName;

	@Column(name = "Gender")
	@NotBlank(message = "* Empty values ​​are not allowed")
	private String gender;

	@Column(name = "PhoneNumber")
	@Pattern(regexp = "^09\\d{8,9}$", message = "The input must be a string of 10 to 11 digits and start with '09'.")
	private String phoneNumber;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DateOfBirth", columnDefinition = "DATE")
	@NotNull(message = "* Null is not allowed")
	private LocalDate dateOfBirth;

	@Column(name = "National_ID")
	@Pattern(regexp = "^\\d{12}$", message = "The input must be a string of 12 digits.")
	private String nationalID;

	@Column(name = "Country")
	@Size(max = 50, min = 5, message = "Size should be between 5 to 50 Digits.")
	private String country;

	@Column(name = "Office")
	@NotBlank(message = "* Empty values ​​are not allowed")
	private String office;

	@Column(name = "Duty")
	@NotBlank(message = "* Empty values ​​are not allowed")
	private String duty;
	
	private String profile;
	
//	======================One To Many=========================

	@OneToMany(mappedBy = "employeeID")
	private List<HistoryOff> HistoryOffID;

	@OneToMany(mappedBy = "senderEmp", cascade = CascadeType.REMOVE)
	private List<Receiver> senderEmp;

	@OneToMany(mappedBy = "receiverEmp", cascade = CascadeType.REMOVE)
	private List<Receiver> receiverEmp;

	@OneToMany(mappedBy = "participantEmp", cascade = CascadeType.REMOVE)
	private List<Participants> participantEmp;

//	======================Many To Many==============================

	@OneToMany(mappedBy = "employeeID")
	private Set<EmployeeWork> employeeWork;

//	====================================================
	
	
	public String getEmployeeID() {
		return employeeID;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getNationalID() {
		return nationalID;
	}

	public void setNationalID(String nationalID) {
		this.nationalID = nationalID;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public List<HistoryOff> getHistoryOffID() {
		return HistoryOffID;
	}

	public void setHistoryOffID(List<HistoryOff> historyOffID) {
		HistoryOffID = historyOffID;
	}

	public Set<EmployeeWork> getEmployeeWork() {
		return employeeWork;
	}

	public void setEmployeeWork(Set<EmployeeWork> employeeWork) {
		this.employeeWork = employeeWork;
	}

	public List<Receiver> getSenderEmp() {
		return senderEmp;
	}

	public void setSenderEmp(List<Receiver> senderEmp) {
		this.senderEmp = senderEmp;
	}

	public List<Receiver> getReceiverEmp() {
		return receiverEmp;
	}

	public void setReceiverEmp(List<Receiver> receiverEmp) {
		this.receiverEmp = receiverEmp;
	}

	public List<Participants> getParticipantEmp() {
		return participantEmp;
	}

	public void setParticipantEmp(List<Participants> participantEmp) {
		this.participantEmp = participantEmp;
	}

	// ============================================================
	public Employee() {
		super();
	}

	public Employee(String employeeID, String fullName, String gender, String phoneNumber, LocalDate dateOfBirth,
			String nationalID, String country, String office, String duty, List<HistoryOff> historyOffID,
			Employee manager, List<Employee> under, Set<EmployeeWork> employeeWork) {
		super();
		this.employeeID = employeeID;
		this.fullName = fullName;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.nationalID = nationalID;
		this.country = country;
		this.office = office;
		this.duty = duty;
		HistoryOffID = historyOffID;
		this.employeeWork = employeeWork;
	}

	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", fullName=" + fullName + ", gender=" + gender + ", phoneNumber="
				+ phoneNumber + ", dateOfBirth=" + dateOfBirth + ", nationalID=" + nationalID + ", country=" + country
				+ ", office=" + office + ", duty=" + duty + ", HistoryOffID=" + HistoryOffID + ", manager=" + ", under="
				+ ", employeeWork=" + employeeWork + "]";
	}

}