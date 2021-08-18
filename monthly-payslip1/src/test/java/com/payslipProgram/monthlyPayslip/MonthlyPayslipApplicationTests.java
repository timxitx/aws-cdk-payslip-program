package com.payslipProgram.monthlyPayslip;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.payslipProgram.monthlyPayslip.domain.PaySlip;
import com.payslipProgram.monthlyPayslip.domain.Threshold;

@SpringBootTest
class MonthlyPayslipApplicationTests {
	
	List<Threshold> taxThresholds = new ArrayList<Threshold>();
	
	PaySlip payslip1 = new PaySlip();
	PaySlip payslip2 = new PaySlip();
	PaySlip payslip3 = new PaySlip();
	PaySlip payslip4 = new PaySlip();
	PaySlip payslip5 = new PaySlip();
	
	@Test
	void grossIncomeTest() {
		payslip1.setGrossIncome(60050);
		payslip2.setGrossIncome(60050.4);
		payslip3.setGrossIncome(60049.5);
		assertEquals(5004, payslip1.getGrossIncome());
		assertEquals(5004, payslip2.getGrossIncome());
		assertEquals(5004, payslip3.getGrossIncome());
		
	}
	
	@Test
	void incomeTaxTest() {
		Threshold tt1 = new Threshold(0, 20000, 0);
		Threshold tt2 = new Threshold(20001, 30000, 0.21);
		Threshold tt3 = new Threshold(30001, 50000, 0.382);
		Threshold tt4 = new Threshold(50001, 120000, 0.44);
		Threshold tt5 = new Threshold(120001, Integer.MAX_VALUE, 0.57);
		taxThresholds.add(tt1);
		taxThresholds.add(tt2);
		taxThresholds.add(tt3);
		taxThresholds.add(tt4);
		taxThresholds.add(tt5);
		
		payslip1.setIncomeTax(10000, taxThresholds);
		payslip2.setIncomeTax(25000, taxThresholds);
		payslip3.setIncomeTax(42000, taxThresholds);
		payslip4.setIncomeTax(110000, taxThresholds);
		payslip5.setIncomeTax(300000, taxThresholds);
		
		assertEquals(0, payslip1.getIncomeTax());
		assertEquals(87, payslip2.getIncomeTax());
		assertEquals(557, payslip3.getIncomeTax());
		assertEquals(3012, payslip4.getIncomeTax());
		assertEquals(11928, payslip5.getIncomeTax());
	}
	
	@Test
	void superAmountTest() {
		//grossincome = 5004
		payslip1.setGrossIncome(60050);
		//grossincome = 6020
		payslip2.setGrossIncome(72240);
		//grossincome = 7119
		payslip3.setGrossIncome(85428);
		//grossincome = 8332
		payslip4.setGrossIncome(99984);
		
		payslip1.setSuperannuation(0.09);
		payslip2.setSuperannuation(0.11);
		payslip3.setSuperannuation(0.1);
		payslip4.setSuperannuation(0.093);
		
		assertEquals(450, payslip1.getSuperannuation());
		assertEquals(662, payslip2.getSuperannuation());
		assertEquals(712, payslip3.getSuperannuation());
		assertEquals(775, payslip4.getSuperannuation());
	}
	
	@Test
	void fromDateTest() {
		payslip1.setFromDate(1);
		payslip2.setFromDate(2);
		payslip3.setFromDate(6);
		payslip4.setFromDate(12);
		//current year
		assertEquals("01 February", payslip1.getFromDate());
		assertEquals("01 March", payslip2.getFromDate());
		assertEquals("01 July", payslip3.getFromDate());
		assertEquals("01 January", payslip4.getFromDate());
	}
	
	@Test
	void toDateTest() {
		payslip1.setToDate(1);
		payslip2.setToDate(2);
		payslip3.setToDate(3);
		payslip4.setToDate(12);
		//current year
		assertEquals("28 February", payslip1.getToDate());
		assertEquals("31 March", payslip2.getToDate());
		assertEquals("30 April", payslip3.getToDate());
		assertEquals("31 January", payslip4.getToDate());
	}

}
