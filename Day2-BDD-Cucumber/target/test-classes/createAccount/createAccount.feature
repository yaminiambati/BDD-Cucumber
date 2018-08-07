@account
Feature: Create New Account
 Create new Account for the valid customer details
 Scenario: For valid customer create new Account
  Given customer details
  When  Valid Customer
  And valid opening balance
  Then create new Account

Scenario: For Invalid customer 
  For invalid customer details throw error message
   Given Customer details
   When Invalid Customer
   Then throw 'Invalid Customer' error message

Scenario: For Invalid Opening Balance 
   Given Customer details and opening balance
   When Invalid opening balance
   Then throw 'Invalid Balance' error message
Scenario: Find account details
	Find account details for the given account number
	Given Account number
	When Valid account Number
	Then find account details
	
Scenario: Withdraw Amount from Account
	Find account details and withdraw amount
	Given Account number 1001 and amount 1000
	When Find account and do withdrawal
	Then Update the account details.
	
Scenario: Deposit Amount to Account
	Find account details to deposit amount
	Given Account number 1001 and amount is 1000
	When Find account and do deposit
	Then Update account details.	
	
	
	
	
	
	
	
	
	
	

    