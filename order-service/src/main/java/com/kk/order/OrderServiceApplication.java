package com.kk.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderServiceApplication {

	public static void main(String[] args) {
		var env = SpringApplication.run(OrderServiceApplication.class, args).getEnvironment();
		String appName = env.getProperty("spring.application.name").toUpperCase();
		String port = env.getProperty("server.port");
		System.out.println("-------------------------START " + appName + " Application------------------------------");
		System.out.println("   Application         : " + appName);
		System.out.println("   Url swagger-ui      : http://localhost:" + port + "/swagger-ui.html");
		System.out.println("-------------------------START SUCCESS " + appName + " Application------------------------------");
	}

}
