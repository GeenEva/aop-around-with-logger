package com.luv2code.aopdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.luv2code.aopdemo.service.TrafficFortuneService;

public class AroundDemoApp {

	public static void main(String[] args) {

	
		AnnotationConfigApplicationContext context =
				new AnnotationConfigApplicationContext(DemoConfig.class);
		
	
		TrafficFortuneService theFortuneService = context.getBean("trafficFortuneService", TrafficFortuneService.class);
		
		System.out.println("\nMain Program: Around Demo App");
		System.out.println("\n ... Calling getFortune()");
		
		String data = theFortuneService.getFortune();
		
		System.out.println("\nMy fortune is: " + data);
		System.out.println("\nFinished");
		
		context.close();
	}

}
