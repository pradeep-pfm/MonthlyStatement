package com.galaxy.entity;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="monthly_statement")
public class MonthlyStmt {
	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	@Column
	private Integer accountId;
	@Column
	private String  customerName;
	@Column
	private String customerEmail;
	@Column
	private Date customerDOB;
	public MonthlyStmt() {

	}

	public MonthlyStmt(Integer id, Integer accountId, String customerName, String customerEmail, Date customerDOB) {
		this.id = id;
		this.accountId = accountId;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerDOB = customerDOB;
	}

	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public Date getCustomerDOB() {
		return customerDOB;
	}
	public void setCustomerDOB(Date customerDOB) {
		this.customerDOB = customerDOB;
	}

	@Override
	public String toString() {
		return "MonthlyStmt{" +
				"id=" + id +
				", accountId=" + accountId +
				", customerName='" + customerName + '\'' +
				", customerEmail='" + customerEmail + '\'' +
				", customerDOB=" + customerDOB +
				'}';
	}
}
