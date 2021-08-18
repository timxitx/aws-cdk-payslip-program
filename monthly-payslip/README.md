# monthly-payslip

This is a monthly payslip application

It has been published to 3.21.40.161:8080


How to run it:
- Open postman, select POST method and select application.json.
- (Optional, it has default value) put "3.21.40.161:8080/import-tax-threshold" and the JSON like the following
- [
  {
    "fromIncome":0,
    "toIncome":18200,
    "taxRate":0
  },
  {
    "fromIncome":18201,
    "toIncome":37000,
    "taxRate":0.19
  },
  {
    "fromIncome":37001,
    "toIncome":87000,
    "taxRate":0.325
  },
  {
    "fromIncome":87001,
    "toIncome":180000,
    "taxRate":0.37
  },
  {
    "fromIncome":180001,
    "toIncome":999999999,
    "taxRate":0.45
  }
]
- put "3.21.40.161:8080/input" and the JSON like the following
- [
  {
  	"firstName":"David",
    "lastName":"Rudd",
    "annualSalary":60050,
	"superRate":0.09,
	"paymentMonth":1
  },
  {
    "firstName":"Ryan",
    "lastName":"Chen",
    "annualSalary":120000,
    "superRate":0.1,
    "paymentMonth":2
  }
]

Assumption:
- The threshold json import should have income from low to high, fromIncome should be less than toIncome for each element and there is no gap between different thresholds
- The tax threshold json import should have the large enough amount for the last toIncome and the first fromIncome should be 0


About Deploy: 
- I used docker to create a docker image for this application and linked the image to aws ECS service for deploying.(https://hub.docker.com/r/timxii/monthlypayslip/tags?page=1&ordering=last_updated)

About Testing: 
- Already included the Junit Test.
