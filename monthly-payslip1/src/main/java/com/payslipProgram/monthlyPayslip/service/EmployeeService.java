package com.payslipProgram.monthlyPayslip.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.payslipProgram.monthlyPayslip.domain.Employee;
import com.payslipProgram.monthlyPayslip.domain.PaySlip;
import com.payslipProgram.monthlyPayslip.domain.Threshold;

@Service
public class EmployeeService {
	
	List<Threshold> taxThresholds = new ArrayList<Threshold>();
	
	public EmployeeService() {
		//set default tax thresholds
		Threshold tt1 = new Threshold(0,18200, 0); //(fromIncome, toIncome, taxRate)
		Threshold tt2 = new Threshold(18201,37000, 0.19);
		Threshold tt3 = new Threshold(37001,87000, 0.325);
		Threshold tt4 = new Threshold(87001, 180000, 0.37);
		Threshold tt5 = new Threshold(180001, Integer.MAX_VALUE, 0.45);
		taxThresholds.add(tt1);
		taxThresholds.add(tt2);
		taxThresholds.add(tt3);
		taxThresholds.add(tt4);
		taxThresholds.add(tt5);
		
	}

	public List<PaySlip> output(List<Employee> employees) {
		List<PaySlip> payslips = new ArrayList<PaySlip>();
		for(Employee e: employees) {
			PaySlip payslip = new PaySlip();
			payslip.setEmployee(e);
			payslip.setGrossIncome(e.getAnnualSalary());
			payslip.setIncomeTax(e.getAnnualSalary(), taxThresholds);
			payslip.setNetIncome();
			payslip.setSuperannuation(e.getSuperRate());
			payslip.setFromDate(e.getPaymentMonth());
			payslip.setToDate(e.getPaymentMonth());
			payslips.add(payslip);
		}
	
		return payslips;
	}
	
	public void assignTaxThreshold(List<Threshold> tts) {
		taxThresholds.clear();
		taxThresholds.addAll(tts);
	}
}
