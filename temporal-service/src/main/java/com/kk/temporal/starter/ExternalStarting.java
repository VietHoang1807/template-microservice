package com.kk.temporal.starter;

import com.kk.temporal.activities.impl.ExternalActivitiesImpl;
import com.kk.temporal.workflow.ExternalWorkflow;
import com.kk.temporal.workflow.impl.ExternalWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExternalStarting {

    private final WorkflowServiceStubs serviceStubs;

    public ExternalStarting(WorkflowServiceStubs serviceStubs) {
        this.serviceStubs = serviceStubs;
        WorkflowClient client = WorkflowClient.newInstance(serviceStubs);
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker("EXTERNAL_TASK_QUEUE");
        worker.registerWorkflowImplementationTypes(ExternalWorkflowImpl.class);
        worker.registerActivitiesImplementations(new ExternalActivitiesImpl());
        factory.start();
    }

    public void startWorkflow() {
        WorkflowClient client = WorkflowClient.newInstance(serviceStubs);
        ExternalWorkflow fWorkflow = client.newWorkflowStub(
                ExternalWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("EXTERNAL_TASK_QUEUE")
                        .setWorkflowId("EXTERNAL_WORKFLOW_ID")
                        .build()
        );
        WorkflowClient.start(fWorkflow::handlePython);
    }
}
