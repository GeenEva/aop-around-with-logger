package com.luv2code.aopdemo;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.luv2code.aopdemo.dao.AccountDAO;

public class AfterThrowingDemoApp {

	public static void main(String[] args) {

	
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(DemoConfig.class);
		
	
		AccountDAO theAccountDAO = context.getBean("accountDAO", AccountDAO.class);

		List<Account> theAccounts = null;
		
		try {
			boolean tripWire = true;	
			theAccounts = theAccountDAO.findAccounts(tripWire);	
		}
		
		catch(Exception exc){
			System.out.println("\n\nMain program... caught exception: " + exc);
		}
		
		System.out.println("\nThese are the accounts: " + theAccounts);
		
		context.close();
	}

}
