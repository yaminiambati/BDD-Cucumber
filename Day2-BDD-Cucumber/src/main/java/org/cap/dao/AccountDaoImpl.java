package org.cap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.cap.model.Account;

public class AccountDaoImpl implements IAccountDao{

	@Override
	public boolean addAccount(Account account) {
	
		String sql="insert into account values(?,?,?)";
		try {
			
			Connection conn=getMySQlConnection();
			//System.out.println(conn);
			
			PreparedStatement pst=conn.prepareStatement(sql);
			
			pst.setInt(1, account.getAccountNo());
			pst.setDouble(2, account.getOpeningBalance());
			pst.setString(3, account.getCustomer().getFirstName());
			
			int count=pst.executeUpdate();
			if(count>0)
				return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	
	private Connection getMySQlConnection() {
		Connection connection=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/capsql", 
					"root", "admin");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return connection;
	}


	@Override
	public Account findAccountById(int accountNo) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Account updateBalance(int accountNo, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

}
