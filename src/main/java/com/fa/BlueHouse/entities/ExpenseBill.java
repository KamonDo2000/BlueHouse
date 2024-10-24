package com.fa.BlueHouse.entities;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class ExpenseBill {
	@Id
	@Column(name = "ID_ExpenseBill")
	private String idExpenseBill;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "Expense_Date")
	private LocalDate expenseDate;
	@ManyToOne
	@JoinColumn(name = "idResident")
	private Resident idResident;
	@ManyToOne
	@JoinColumn(name = "employeeID")
	private Employee idEmployee;
	@OneToMany(mappedBy = "idExpenseBill")
	private List<ExpenseBillDetail> listExpenDetail;

	public String getIdExpenseBill() {
		return idExpenseBill;
	}

	public void setIdExpenseBill(String idExpenseBill) {
		this.idExpenseBill = idExpenseBill;
	}

	public LocalDate getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(LocalDate expenseDate) {
		this.expenseDate = expenseDate;
	}

	public Employee getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Employee idEmployee) {
		this.idEmployee = idEmployee;
	}

	public List<ExpenseBillDetail> getListExpenDetail() {
		return listExpenDetail;
	}

	public void setListExpenDetail(List<ExpenseBillDetail> listExpenDetail) {
		this.listExpenDetail = listExpenDetail;
	}

	public Resident getIdResident() {
		return idResident;
	}

	public void setIdResident(Resident idResident) {
		this.idResident = idResident;
	}

	public ExpenseBill(String idExpenseBill, LocalDate expenseDate, Resident idResident) {
		super();
		this.idExpenseBill = idExpenseBill;
		this.expenseDate = expenseDate;
		this.idResident = idResident;
	}

	public ExpenseBill(String idExpenseBill, LocalDate expenseDate, Employee idEmployee) {
		super();
		this.idExpenseBill = idExpenseBill;
		this.expenseDate = expenseDate;
		this.idEmployee = idEmployee;
	}

	public ExpenseBill(String idExpenseBill, LocalDate expenseDate, Administrators idAdministrators,
			List<ExpenseBillDetail> listExpenDetail) {
		super();
		this.idExpenseBill = idExpenseBill;
		this.expenseDate = expenseDate;
		this.listExpenDetail = listExpenDetail;
	}

	public ExpenseBill() {
		super();
	}
	public double getTotalbill() {
        return listExpenDetail.stream()
                      .mapToDouble(detail -> detail.getQuantity() * detail.getPrice())
                      .sum();
    }

	@Override
	public String toString() {
		return "ExpenseBill [getIdExpenseBill()=" + getIdExpenseBill() + ", getExpenseDate()=" + getExpenseDate() + "]";
	}

}
