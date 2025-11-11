package com.kk.temporal.workflow.impl;

import com.kk.temporal.activities.GreetActivities;
import com.kk.temporal.request.TravelReq;
import com.kk.temporal.workflow.SayHelloWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
public class SayHelloWorkflowImpl implements SayHelloWorkflow {

    private static boolean isConfirm = false;

    private static GreetActivities activities = null;

    @Override
    public String sayHello(TravelReq req) {
        activities = Workflow
                .newActivityStub(GreetActivities.class,
                        ActivityOptions.newBuilder()
                                .setRetryOptions(
                                        RetryOptions
                                                .newBuilder()
                                                .setMaximumAttempts(5)
                                                .build())
                                .setStartToCloseTimeout(Duration.ofSeconds(5))
                                .build());
        log.info("SayHelloWorkflowImpl sayHello");
        Saga.Options sagaOptions = new Saga.Options.Builder()
                .setParallelCompensation(false)
                .build();
        Saga saga = new Saga(sagaOptions);
        try {
            activities.greet(req.user());
            activities.travel(req);
            saga.addCompensation(() -> activities.cancelTravel(req));
            activities.bookHotel(req);
            saga.addCompensation(() -> activities.cancelBookHotel(req));
            boolean isConfirmed = Workflow.await(
                    Duration.ofMinutes(3),
                    () -> isConfirm);
            if (!isConfirmed) {
                log.info("🛑 User did not confirm within 2 minutes, cancelling the booking for user: {}", req.user());
                //cancel the booking
                activities.cancelBook(req.user());
            } else {
                log.info("✅ User confirmed the booking: {}", req.user());
                activities.confirmBook(req.user());
            }
        }  catch (Exception e) {
            log.error("❌ Error during travel booking for user: {}. Initiating compensation.", req.user());
            saga.compensate();
        }

        return "000";
    }

    @Override
    public void confirm(String user) {
        log.info("Confirm user: {}", user);
        isConfirm = true;
    }
}
