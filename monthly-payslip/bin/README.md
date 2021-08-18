# monthly-payslip

This is a monthly payslip application

The assumptions:
- The number of working days each month is the same, and there is no any sick leave or annual leave.
- The start date is a range of current month like the example shows.
- In csv file, you should only put numbers in super column. The salary and super can be "double" type.
- In inputFile.csv, the first row is heaadings, the read function starts from the second row. Left aligned as well.(The sample inputFile.csv is located in src folder)


To run it:
- you need to install java in the computer.


How to run it:
- Option 1. Open Eclipse, select "GUI.java" and click run
- Option 2. Open terminal, go to src/payslipProgram, run "javac Calculation.java Employee.java, GUI.java, IO.java". Go back to src folder and run "java payslipProgram.GUI".
- (You can choose your csv file use GUI and the output csv file will be generated into monthly-payslip/src folder).


Unit Test covers:
- the calculation of gross income and its "Math.round()".
- the calculation of income tax and related to different tax thresholds.
- the calculation of super and its "Math.round()".
