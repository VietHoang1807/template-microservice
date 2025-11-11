package com.kk.temporal.starter;

import com.kk.temporal.activities.impl.GreetActivitiesImpl;
import com.kk.temporal.request.TravelReq;
import com.kk.temporal.workflow.SayHelloWorkflow;
import com.kk.temporal.workflow.impl.SayHelloWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.stereotype.Component;

@Component
public class TravelBookingWorkflow {

    private final WorkflowServiceStubs serviceStubs;

    public TravelBookingWorkflow(WorkflowServiceStubs serviceStubs) {
        this.serviceStubs = serviceStubs;
        WorkflowClient client = WorkflowClient.newInstance(serviceStubs);
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker("TRAVEL_TASK_QUEUE");
        worker.registerWorkflowImplementationTypes(SayHelloWorkflowImpl.class);
        worker.registerActivitiesImplementations(new GreetActivitiesImpl());
        factory.start();
    }

    public void startWorkFlow(TravelReq request) {
        WorkflowClient client = WorkflowClient.newInstance(serviceStubs);

        SayHelloWorkflow workflow = client.newWorkflowStub(
                SayHelloWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("TRAVEL_TASK_QUEUE")
                        .setWorkflowId("TRAVEL_WORKFLOW_" + request.user())
                        .build()
        );

        WorkflowClient.start(workflow::sayHello, request);
    }

    public void sendConfirmationSignal(String userId) {
        WorkflowClient client = WorkflowClient.newInstance(serviceStubs);
        String workflowId = "TRAVEL_WORKFLOW_" + userId;
        SayHelloWorkflow workflow = client.newWorkflowStub(
                SayHelloWorkflow.class,
                workflowId
        );
        workflow.confirm(userId);
    }
}
