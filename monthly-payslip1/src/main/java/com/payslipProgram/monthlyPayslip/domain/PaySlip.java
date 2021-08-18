package com.payslipProgram.monthlyPayslip.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PaySlip {

	private Employee employee;
	
	private Integer grossIncome;
	private Integer incomeTax;
	private Integer netIncome;
	private Integer superannuation;
	private String fromDate;
	private String toDate;
	
	double taxBase;


	public Integer getGrossIncome() {
		return grossIncome;
	}

	public void setGrossIncome(double salary) {
		this.grossIncome = (int) Math.round(salary/12);
	}

	public Integer getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(double salary, List<Threshold> tts) {
		taxBase = 0;
		for(Threshold tt: tts) {
			if(salary >= tt.getFromIncome() && salary<tt.getToIncome()) {
				this.incomeTax = (int) Math.round((taxBase + tt.getTaxInRange(salary))/12);
				break;
			}
			taxBase += (tt.getToIncome() - tt.getFromIncome())*tt.getTaxRate();
		}
	}

	public Integer getNetIncome() {
		return netIncome;
	}

	public void setNetIncome() {
		this.netIncome = this.grossIncome - this.incomeTax;
	}

	public Integer getSuperannuation() {
		return superannuation;
	}

	public void setSuperannuation(double superRate) {
		this.superannuation = (int) Math.round(this.grossIncome * superRate);
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(int paymentMonth) {
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.MONTH, paymentMonth);
	    int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
	    cal.set(Calendar.DAY_OF_MONTH, firstDay);
	    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM");
	    String firstDayOfMonth = sdf.format(cal.getTime());
		this.fromDate = firstDayOfMonth;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(int paymentMonth) {
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.MONTH, paymentMonth);
	    int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    cal.set(Calendar.DAY_OF_MONTH, lastDay);
	    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM");
	    String lastDayOfMonth = sdf.format(cal.getTime());
		this.toDate = lastDayOfMonth;
	}
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	

}
