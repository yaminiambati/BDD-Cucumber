package createAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.cap.dao.AccountDaoImpl;
import org.cap.dao.IAccountDao;
import org.cap.exception.InvalidCustomer;
import org.cap.exception.InvalidOpeningBalance;
import org.cap.model.Account;
import org.cap.model.Address;
import org.cap.model.Customer;
import org.cap.service.AccountServiceImpl;
import org.cap.service.IAccountService;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdef {
	
	private Customer customer;
	private double openingBalance;
	private int accountNo;
	private double amount_withdraw;
	private double amount_deposit;
	private IAccountService accountService;
	
	@Mock
	private IAccountDao accountDao;
	
	
	@Before
	public void setUp() {
		
		MockitoAnnotations.initMocks(this);
		
		customer=new Customer();
		openingBalance=1000;
		
		//accountDao=new AccountDaoImpl();
		accountService=new AccountServiceImpl(accountDao);
	}
	
	@Given("^customer details$")
	public void customer_details() throws Throwable {
		customer.setFirstName("Tom");
		customer.setLastName("Jerry");
		Address address=new Address();
		address.setDoorNo("12/B");
		address.setCity("Chennai");
		customer.setAddress(address);
	}

	@When("^Valid Customer$")
	public void valid_Customer() throws Throwable {
	  assertNotNull(customer);
	}

	@When("^valid opening balance$")
	public void valid_opening_balance() throws Throwable {
	    assertTrue(openingBalance>=500);
	}

	@Then("^create new Account$")
	public void create_new_Account() throws Throwable {
		
		//Fake declaration
		Account account=new Account();
		account.setAccountNo(2);
		account.setOpeningBalance(1000);
		account.setCustomer(customer);
		
		Mockito.when(accountDao.addAccount(account)).thenReturn(true);
		
		//Business Logic
		Account account2=accountService.createAccount(customer, openingBalance);
		
		//System.out.println(account2);
		
		Mockito.verify(accountDao).addAccount(account);
		
		
		assertNotNull(account2);
		assertEquals(openingBalance, account2.getOpeningBalance(),0.0);
		assertEquals(2, account2.getAccountNo());
	}

	
	
	@Given("^Customer details$")
	public void customer_details1() throws Throwable {
	   customer=null;
	}

	@When("^Invalid Customer$")
	public void invalid_Customer() throws Throwable {
	  assertNull(customer);
	   
	}


	@Then("^throw 'Invalid Customer' error message$")
	public void throw_Invalid_Customer_error_message() throws InvalidCustomer, InvalidOpeningBalance {
	
		try {
		accountService.createAccount(customer, 3000);
		}catch (InvalidCustomer e) {
			// TODO: handle exception
		}
	}

	
	@Given("^Customer details and opening balance$")
	public void customer_details_and_opening_balance() throws Throwable {
	 
		
		openingBalance=100;
	}

	@When("^Invalid opening balance$")
	public void invalid_opening_balance() throws Throwable {
	 
		assertTrue(openingBalance<1000);
		
	}

	@Then("^throw 'Invalid Balance' error message$")
	public void throw_Invalid_Balance_error_message() throws Throwable {
	   try {
		accountService.createAccount(customer, openingBalance);
	   }catch (Exception e) {
		// TODO: handle exception
	}
		
	}
	
	@Given("^Account number$")
	public void account_number() throws Throwable {
		accountNo=1001;
	}

	@When("^Valid account Number$")
	public void valid_account_Number() throws Throwable {
	    assertTrue(accountNo>0);
	}

	@Then("^find account details$")
	public void find_account_details() throws Throwable {
	    
		Account account=new Account();
		account.setAccountNo(1001);
		account.setCustomer(customer);
		account.setOpeningBalance(10000);
		
		
		Mockito.when(accountDao.findAccountById(1001)).thenReturn(account);
		
		//Actual Business Logic
		Account found_account=accountService.findAccountById(accountNo);
		
		Mockito.verify(accountDao).findAccountById(1001);
		
		
	}

	@Given("^Account number (\\d+) and amount (\\d+)$")
	public void account_number_and_amount(int accNo, int amount) throws Throwable {
		this.accountNo=accNo;
		this.amount_withdraw=amount;
	}


@When("^Find account and do withdrawal$")
public void find_account_and_do_withdrawal() throws Throwable {
  
	Account account=new Account();
		account.setAccountNo(1001);
		account.setCustomer(customer);
		account.setOpeningBalance(10000);
		
		
		
		Mockito.when(accountDao.findAccountById(1001)).thenReturn(account);
		
		
		
		Mockito.when(accountDao.updateBalance(accountNo, 9000)).thenReturn(account);
		
		Account updatedaccount=accountService.withdraw(accountNo,amount_withdraw);
		
		Mockito.verify(accountDao).findAccountById(1001);
		
		Mockito.verify(accountDao).updateBalance(accountNo, 9000);
		
		
		assertEquals(9000, updatedaccount.getOpeningBalance(),0.0);
		
	}


	@Then("^Update the account details\\.$")
	public void update_the_account_details() throws Throwable {
	   
		
		Account account=new Account();
		account.setAccountNo(1001);
		account.setCustomer(customer);
		//account.setOpeningBalance(10000);
		//Mockito.times(2);	
		//Actual Business Logic
		Account updated_account=accountService.updateBalance(accountNo, 9000);	
	//	Mockito.verify(accountDao).updateBalance(accountNo, 9000);
		assertEquals(9000, updated_account.getOpeningBalance(),0.0);
		
		
	}
	
	@Given("^Account number (\\d+) and amount is (\\d+)$")
	public void account_number_and_amount_is(int accNo, int amount) throws Throwable {
		this.accountNo=accNo;
		this.amount_withdraw=amount;
	}


@When("^Find account and do deposit$")
public void find_account_and_do_deposit() throws Throwable {
	Account account=new Account();
	account.setAccountNo(1001);
	account.setCustomer(customer);
	account.setOpeningBalance(8000);
	Mockito.when(accountDao.findAccountById(1001)).thenReturn(account);
	Mockito.when(accountDao.updateBalance(accountNo, 9000)).thenReturn(account);
	Account updatedaccount=accountService.deposit(accountNo,amount_deposit);
	Mockito.verify(accountDao).findAccountById(1001);
	Mockito.verify(accountDao).updateBalance(accountNo, 8000);
	assertEquals(8000, updatedaccount.getOpeningBalance(),0.0);
}

@Then("^Update account details\\.$")
public void update_account_details1() throws Throwable {
	Account account=new Account();
	account.setAccountNo(1001);
	account.setCustomer(customer);
	//account.setOpeningBalance(10000);
	//Mockito.times(2);	
	//Actual Business Logic
	Account updated_account=accountService.updateBalance(accountNo, 9000);	
//	Mockito.verify(accountDao).updateBalance(accountNo, 9000);
	assertEquals(8000, updated_account.getOpeningBalance(),0.0);
	
}



}
