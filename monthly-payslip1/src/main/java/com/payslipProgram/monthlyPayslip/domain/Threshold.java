package com.payslipProgram.monthlyPayslip.domain;

public class Threshold {
	
	private int fromIncome;
	private int toIncome;
	private double taxRate;
	
	public Threshold(int fromIncome, int toIncome, double taxRate) {
		super();
		this.fromIncome = fromIncome;
		this.toIncome = toIncome;
		this.taxRate = taxRate;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public int getFromIncome() {
		return fromIncome;
	}
	
	public void setFromIncome(int fromIncome) {
		this.fromIncome = fromIncome;
	}
	
	public int getToIncome() {
		return toIncome;
	}
	
	public void setToIncome(int toIncome) {
		this.toIncome = toIncome;
	}
	
	public double getTaxInRange(double income) {
		return (income-this.fromIncome)*this.taxRate;
	}
	
	

}
