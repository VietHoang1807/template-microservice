package com.kk.temporal.config;

import io.temporal.serviceclient.WorkflowServiceStubs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporalConfig {

//    @Bean
//    public WorkerFactory workerFactory(WorkflowServiceStubs serviceStubs) {
//        WorkflowClient client = WorkflowClient.newInstance(serviceStubs);
//        WorkerFactory factory = WorkerFactory.newInstance(client);
//        Worker worker = factory.newWorker("TRAVEL_TASK_QUEUE");
//        worker.registerWorkflowImplementationTypes(SayHelloWorkflowImpl.class);
//        worker.registerActivitiesImplementations(new GreetActivitiesImpl());
//        return factory;
//    }
//
    @Bean WorkflowServiceStubs serviceStubs() {
        return WorkflowServiceStubs.newLocalServiceStubs();
    }
//
//    @PostConstruct
//    public void startWorker() {
//        workerFactory(serviceStubs()).start();
//    }
}
