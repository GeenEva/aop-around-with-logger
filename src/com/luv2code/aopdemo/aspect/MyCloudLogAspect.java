package com.luv2code.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class MyCloudLogAspect {

	@Before("com.luv2code.aopdemo.aspect.LuvAOPExpressions.setter()")
	public void logToCloudAdvice() {
		System.out.println("\n=====>>> Log it alllll to the CLOUDDDDD"  + getClass() + " ORDER = 1");
	}
}
