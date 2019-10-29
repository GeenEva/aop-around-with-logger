package com.luv2code.aopdemo.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.luv2code.aopdemo.Account;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {
	
	@Around("execution(* com.luv2code.aopdemo.service.*.getFortune(..))")
	public Object aroundGetFortune(ProceedingJoinPoint theProcedingJoinPoint) throws Throwable {
		
		String method = theProcedingJoinPoint.getSignature().toShortString();
		
		System.out.println("\n =======>>> Executing @Around advice on method: " + method);
		
		long beginStamp = System.currentTimeMillis();
		
			//Execute the target method
			//The joinpoint is the target object, so here it is the method of the fortune service.
			//The method gives back the String, so that is put into the result.
		Object result = theProcedingJoinPoint.proceed();
		
		long endStamp = System.currentTimeMillis();
		
		long duration = endStamp - beginStamp;
		
		System.out.println("\n====>> Duration: " + duration / 1000.0 + " seconds");
		
		System.out.println("\nThe result is: " + result);
		
		return result;
	}
	
	
	@After("execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))")
	public void afterFinallyFindAccountsAdvice(JoinPoint theJoinPoint) {
		
		String method = theJoinPoint.getSignature().toShortString();		
		System.out.println("\n =======>>> Executing @After (Finally) on method: " + method);
	}

	
	@AfterThrowing(
			pointcut="execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
			throwing="theExc")
	public void afterThrowingFindAccountAdvice(JoinPoint theJoinPoint, Throwable theExc) {
		
		String method = theJoinPoint.getSignature().toShortString();
		
		System.out.println("\n =======>>> Executing @AfterThrowing on method: " + method);
		
		System.out.println("\n =======>>> The exception is: " + theExc);
		
		System.out.println();
	}
	
	//Executes only with success, so not in case of exception
	@AfterReturning(
			pointcut="execution(* com.luv2code.aopdemo.dao.AccountDAO.findAccounts(..))",
			returning="result")
	public void afterReturningFindAccountAdvice(JoinPoint theJoinPoint, List<Account> result) {
	
		//print out which method we're advising on
		String method = theJoinPoint.getSignature().toShortString();
		
		System.out.println("\n =======>>> Executing @AfterReturning on method: " + method);
		
		System.out.println("\n =======>>> The result is: " + result);
		
		convertAccountNamesIntoUpperCase(result);
		
		System.out.println("\n =======>>> The Uppercase result is: " + result);
	}
	
	
	private void convertAccountNamesIntoUpperCase(List<Account> result) {

		for(Account tempAccount: result) {
			String theUpperName = tempAccount.getName().toUpperCase();
			tempAccount.setName(theUpperName);
		}
		
	}


	@Before("com.luv2code.aopdemo.aspect.LuvAOPExpressions.forDaoPackageNoGetterSetter()")
	public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {	
		
		MethodSignature methodSig = (MethodSignature) theJoinPoint.getSignature();
		
		System.out.println("\n=====>>> Executing @Before advice on method " + getClass() + " ORDER = 2");		
		
		System.out.println("\n Method: " + methodSig);
		
		for(Object arg: theJoinPoint.getArgs()) {
		
			System.out.println("\n Method arguments: " + arg);
		
			if(arg instanceof Account) {
				
				Account theAccount = (Account) arg;
				
				System.out.println("\n Account name: " + theAccount.getName());
				System.out.println("\n Account level: " + theAccount.getLevel());
				
			}
		}
	}
	
}
