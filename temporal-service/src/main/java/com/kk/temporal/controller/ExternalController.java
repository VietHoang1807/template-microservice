package com.kk.temporal.controller;

import com.kk.temporal.starter.ExternalStarting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("external")
public class ExternalController {

    @Autowired
    private ExternalStarting externalStarting;

    @GetMapping
    String handlePython() {
        try {
            externalStarting.startWorkflow();
            return "000";
        } catch (Exception ignored) {}
        return "999";
    }

}
