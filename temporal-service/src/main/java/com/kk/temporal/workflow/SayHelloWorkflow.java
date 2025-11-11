package com.kk.temporal.workflow;

import com.kk.temporal.request.TravelReq;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface SayHelloWorkflow {

    @WorkflowMethod
    String sayHello(TravelReq name);

    @SignalMethod
    void confirm(String user);
}
