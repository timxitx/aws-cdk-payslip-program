package com.payslipProgram.monthlyPayslip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.payslipProgram.monthlyPayslip.domain.Employee;
import com.payslipProgram.monthlyPayslip.domain.PaySlip;
import com.payslipProgram.monthlyPayslip.domain.Threshold;
import com.payslipProgram.monthlyPayslip.service.EmployeeService;

@RestController
public class MainController {
	
	private EmployeeService employeeService;
	
	@Autowired
	public MainController(EmployeeService es) {
		this.employeeService = es;
	}
	
	@GetMapping
	public String home() {
		return "This is the monthly payslip application.";
	}

	@PostMapping("/input")
	public List<PaySlip> input(@RequestBody List<Employee> e) {
		return employeeService.output(e);
	}
	
	@PostMapping("/import-tax-threshold")
	public String getTaxThreshold(@RequestBody List<Threshold> tt) {
		try {
			employeeService.assignTaxThreshold(tt);
			return "New Tax Threshold has been set!";
		} catch(Exception e) {
			e.printStackTrace();
			return "something went wrong.";
		}
	}
	
	
	
}
