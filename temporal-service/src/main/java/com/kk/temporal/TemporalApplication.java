package com.kk.temporal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TemporalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemporalApplication.class, args);
//		WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
//		WorkflowClient client = WorkflowClient.newInstance(service);
//		WorkerFactory factory = WorkerFactory.newInstance(client);
//
//		Worker worker = factory.newWorker("my-task-queue");
//		worker.registerWorkflowImplementationTypes(SayHelloWorkflowImpl.class);
//		worker.registerActivitiesImplementations(new GreetActivitiesImpl());
//
//		System.out.println("Starting sayhello for task");
//		factory.start();
	}
}
