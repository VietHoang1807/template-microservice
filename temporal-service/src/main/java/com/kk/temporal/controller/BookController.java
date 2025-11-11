package com.kk.temporal.controller;

import com.kk.temporal.request.TravelReq;
import com.kk.temporal.starter.TravelBookingWorkflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private TravelBookingWorkflow travelBookingWorkflow;

    @PostMapping
    public String bookTravel(@RequestBody TravelReq req) {
        travelBookingWorkflow.startWorkFlow(req);
        return "000";
    }

    @PostMapping("confirm")
    public String confirm(@RequestParam String user) {
        travelBookingWorkflow.sendConfirmationSignal(user);
        return "000";
    }
}
