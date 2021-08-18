package com.payslipProgram.monthlyPayslip.domain;

public class Employee {

	private String firstName;
	private String lastName;
	private int annualSalary;
	private double superRate;
	private int paymentMonth;
	
	public Employee(String firstName, String lastName, int annualSalary, double superRate, int paymentMonth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.annualSalary = annualSalary;
		this.superRate = superRate;
		this.paymentMonth = paymentMonth;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAnnualSalary() {
		return annualSalary;
	}

	public void setAnnualSalary(int annualSalary) {
		this.annualSalary = annualSalary;
	}

	public double getSuperRate() {
		return superRate;
	}

	public void setSuperRate(double superRate) {
		this.superRate = superRate;
	}

	public int getPaymentMonth() {
		return paymentMonth;
	}

	public void setPaymentMonth(int paymentMonth) {
		this.paymentMonth = paymentMonth;
	}
	
}
