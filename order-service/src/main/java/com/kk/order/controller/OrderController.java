package com.kk.order.controller;

import com.kk.order.model.InputFieldError;
import com.kk.order.model.OrderReq;
import com.kk.order.service.KafkaMessagePublisher;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private final KafkaMessagePublisher publisher;
    static String TOPIC = "logs";

    public OrderController(KafkaMessagePublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping("publish")
    public ResponseEntity<?> publishMessage() {
        try {
            publisher.sendMessageToTopic("order publish", TOPIC);
            return ResponseEntity.ok("Message published successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error occurred while publishing message: " + ex.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> order(@Valid @RequestBody OrderReq order, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            List<InputFieldError> fieldErrorList = bindingResult.getFieldErrors()
                    .stream()
                    .map(err -> new InputFieldError(err.getField(), err.getDefaultMessage()))
                    .toList();
            this.publisher.sendMessageToTopic("order", TOPIC);
            return ResponseEntity.badRequest().body(fieldErrorList);
        }
        return ResponseEntity.ok(order);
    }

    @PostMapping(value = "valid",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validOrder(@Valid @RequestBody OrderReq order, BindingResult bindingResult) {
        this.publisher.sendMessageToTopic("order", TOPIC);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<?> getOrder() {
        this.publisher.sendMessageToTopic("order", TOPIC);
        return ResponseEntity.ok("order success");
    }
}
