package org.cap.service;

import org.cap.dao.AccountDaoImpl;
import org.cap.dao.IAccountDao;
import org.cap.exception.AccountNotFound;
import org.cap.exception.InsufficientBalance;
import org.cap.exception.InvalidCustomer;
import org.cap.exception.InvalidOpeningBalance;
import org.cap.model.Account;
import org.cap.model.Customer;
import org.cap.util.AccountUtil;

public class AccountServiceImpl implements IAccountService{

	private IAccountDao accountDao;
	
	public AccountServiceImpl() {
		
	}
	
	public AccountServiceImpl(IAccountDao accountDao) {
		this.accountDao=accountDao;
	}
	
	@Override
	public Account createAccount(Customer customer, double amount) throws InvalidCustomer, InvalidOpeningBalance {
		if(customer==null) 
			throw new InvalidCustomer("Invalid Customer!");
		if(amount<500) 
			throw new InvalidOpeningBalance("Sorry! Invalid Opening Balance given.");
		
				Account account=new Account();
				account.setCustomer(customer);
				account.setOpeningBalance(amount);
				account.setAccountNo(AccountUtil.generateAccountNo());
				
				System.out.println(account);
				
				//Calling DAo layer
				boolean flag=accountDao.addAccount(account);
				
				System.out.println(flag);
				
				if(flag)
					return account;
				else
					return null;
				
			
	}

	@Override
	public Account findAccountById(int accountNo) {
		
		return accountDao.findAccountById(accountNo);
	}

	@Override
	public Account withdraw(int accountNo, double amount_withdraw) 
			throws AccountNotFound, InsufficientBalance {
		
		//Dao interaction
		Account account=accountDao.findAccountById(accountNo);
		
		if(account==null)
			throw new AccountNotFound("Sorry! account id does not exits!");
		if(amount_withdraw>account.getOpeningBalance())
			throw new InsufficientBalance("Insufficient Balance Exception");
			account.setOpeningBalance(
					account.getOpeningBalance()-amount_withdraw);
			
			updateBalance(accountNo,account.getOpeningBalance());
			
			
		return account;
	}

	@Override
	public Account updateBalance(int accountNo, double amount) {
		
		return accountDao.updateBalance(accountNo, amount);
	}

	@Override
	public Account deposit(int accountNo, double amount_deposit) throws AccountNotFound {
		
		//Dao interaction
		Account account=accountDao.findAccountById(accountNo);
		
		if(account==null)
			throw new AccountNotFound("Sorry! account id does not exits!");
		
			account.setOpeningBalance(
					account.getOpeningBalance()+amount_deposit);
			
			updateBalance(accountNo,account.getOpeningBalance());
			
			
		return account;
	}

}
