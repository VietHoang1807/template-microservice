package com.kk.temporal.workflow.impl;

import com.kk.temporal.activities.ExternalActivities;
import com.kk.temporal.workflow.ExternalWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
public class ExternalWorkflowImpl implements ExternalWorkflow {

    private static ExternalActivities activities = null;

    @Override
    public void handlePython() {
        activities = Workflow
                .newActivityStub(ExternalActivities.class,
                        ActivityOptions.newBuilder()
//                                .setRetryOptions(
//                                        RetryOptions
//                                                .newBuilder()
//                                                .setMaximumAttempts(5)
//                                                .build())
                                .setStartToCloseTimeout(Duration.ofSeconds(5))
                                .build());
        try {
            activities.myActivityFunc("Test external");
            log.info("✅ User call  the external");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
