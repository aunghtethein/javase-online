package com.solt.jdc.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Invoice {
	private int id;
	private double subTotal;
	private double tax;
	private double total;
	private LocalDate saleDate;
	
	private int user_id;
	private String userName;
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public void setSubTotal(int subTotal) {
		this.subTotal = subTotal;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public LocalDate getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	public String getSaleDateStr() {
		String str = "";
		if(saleDate != null) {
			str = saleDate.format(DateTimeFormatter.ofPattern("dd//MM//yyyy"));
		}
		return str;
	}
}
