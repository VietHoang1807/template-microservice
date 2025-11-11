package com.kk.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentServiceApplication {

	public static void main(String[] args) {
		var env = SpringApplication.run(PaymentServiceApplication.class, args).getEnvironment();
		String appName = env.getProperty("spring.application.name").toUpperCase();
		String port = env.getProperty("server.port");
		System.out.println("-------------------------START " + appName + " Application------------------------------");
		System.out.println("   Application         : " + appName);
		System.out.println("   Url swagger-ui      : http://localhost:" + port + "/swagger-ui.html");
		System.out.println("-------------------------START SUCCESS " + appName + " Application------------------------------");
	}

}
