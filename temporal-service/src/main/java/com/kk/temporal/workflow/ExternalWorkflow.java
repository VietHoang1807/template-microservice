package com.kk.temporal.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface ExternalWorkflow {

    @WorkflowMethod
    void handlePython();
}
