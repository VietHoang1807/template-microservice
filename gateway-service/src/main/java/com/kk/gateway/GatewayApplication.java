package com.kk.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		var env = SpringApplication.run(GatewayApplication.class, args).getEnvironment();
		String appName = env.getProperty("spring.application.name").toUpperCase();
		String port = env.getProperty("server.port");
		String zipkin = env.getProperty("management.zipkin.tracing.endpoint");
		System.out.println("-------------------------START " + appName + " Application------------------------------");
		System.out.println("   Application         : " + appName);
		System.out.println("   Port         : " + port);
		System.out.println("   Zipkin         : " + zipkin);
		System.out.println("-------------------------START SUCCESS " + appName + " Application------------------------------");
	}
}
