package com.kk.order.service;

import com.kk.common_lib.model.ProducerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class KafkaMessagePublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessageToTopic(String message, String TOPICS) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPICS,null, message);
//        kafkaTemplate.send(TOPICS, 1, null, message);
//        kafkaTemplate.send(TOPICS, 2, null, message);
//        kafkaTemplate.send(TOPICS, 3, null, message);
        future.whenComplete((result, ex) -> {
            result.getProducerRecord();
            if (ex == null) {
                System.out.println("send message=[" + message + "] to topic:kapishop-dm1 with offset=["
                        + result.getRecordMetadata().offset() + "]");
                System.out.println("send message=[" + message + "] to topic:kapishop-dm1 with parttion=["
                        + result.getRecordMetadata().partition() + "]");
                System.out.println("send message=[" + message + "] to topic:kapishop-dm1 with serializedKeySize=["
                        + result.getRecordMetadata().serializedKeySize() + "]");
                System.out.println("send message=[" + message + "] to topic:kapishop-dm1 with serializedValueSize=["
                        + result.getRecordMetadata().serializedValueSize() + "]");
                System.out.println("send message=[" + message + "] to topic:kapishop-dm1 with timestamp=["
                        + result.getRecordMetadata().timestamp() + "]");
            } else {
                System.out.println("unable to send message: due to:" + ex.getMessage() + "\n");
            }

        });
    }

    public void sendMessageToTopic(ProducerDTO customer, String TOPICS) {
        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPICS, customer);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + customer.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                            customer.toString() + "] due to : " + ex.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("Handle exception ::" + e.getMessage());
        }
    }

    public void sendMessageToTopic(byte[] customer, String TOPICS) {
        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPICS, customer);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + customer.toString() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                            customer.toString() + "] due to : " + ex.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("Handle exception ::" + e.getMessage());
        }
    }
}
