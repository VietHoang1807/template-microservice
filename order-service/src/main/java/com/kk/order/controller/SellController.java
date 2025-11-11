package com.kk.order.controller;

import com.kk.common_lib.model.ProducerDTO;
import com.kk.common_lib.util.SerializationUtil;
import com.kk.order.service.KafkaMessagePublisher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/sell")
@RequiredArgsConstructor
public class SellController {

    private static Logger LOGGER = LoggerFactory.getLogger(SellController.class);
    static String TOPIC = "payment-topic";
    static String TOPIC2 = "j_payment-topic";
    final String TOPIC_3 = "kafka-topic-3";
    private final KafkaMessagePublisher publisher;
    static ProducerDTO req = new ProducerDTO("1", "publish object", "json", "test contact");

    @GetMapping("publish")
    public ResponseEntity<?> publishMessage() {
        LOGGER.info("start publishMessage");
        try {
            for (int i = 0; i < 10; i++) {
                publisher.sendMessageToTopic("test publish" + ":::" + i, TOPIC);
            }
            return ResponseEntity.ok("Message published successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error occurred while publishing message: " + ex.getMessage());
        }
    }

    @GetMapping("publish/{message}")
    public ResponseEntity<?> publishMessage(@PathVariable String message) {
        LOGGER.info("start publishMessage");
        try {
            for (int i = 0; i < 10; i++) {
                publisher.sendMessageToTopic(message + ":::" + i, TOPIC);
            }
            return ResponseEntity.ok("Message published successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error occurred while publishing message: " + ex.getMessage());
        }
    }

    @PostMapping("publish")
    public void publishObject() {
        LOGGER.info("start publishObject");
        publisher.sendMessageToTopic(req, TOPIC2);
    }

    @PostMapping("publish/byte")
    public void publishByte(@RequestBody ProducerDTO req) throws IOException {
        LOGGER.info("start publishByte");
        publisher.sendMessageToTopic(SerializationUtil.toJsonByteArray(req), TOPIC_3);
    }

//    @PostMapping("publish/mutil")
//    public void publishMutil() throws IOException {
//        for (int i = 0; i < 100; i++) {
//            req.setContact(req.getContact() + " :: " + i);
//            publisher.sendMessageToTopic(req.toString() + ":::" + i, TOPIC_4);
//            publisher.sendMessageToTopic(req, TOPIC_4);
//            publisher.sendMessageToTopic(SerializationUtil.toJsonByteArray(req), TOPIC_4);
//
//        }
//    }
}
