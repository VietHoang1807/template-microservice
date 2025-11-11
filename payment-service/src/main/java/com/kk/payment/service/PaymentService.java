package com.kk.payment.service;

import com.kk.common_lib.model.ProducerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    static Logger logger = LoggerFactory.getLogger(PaymentService.class);
    static final String TOPICS = "payment-topic";
    static final String TOPICS_2 = "j_payment-topic";
    static final String TOPICS_3 = "logs";
    static final String GROUPID = "payment-group-1";
    static final String GROUPID_3 = "logstash-consumer";

    @RetryableTopic(attempts = "3", exclude = {RuntimeException.class})
    @KafkaListener(topics = TOPICS, groupId = GROUPID, topicPartitions = {
            @TopicPartition(topic = TOPICS, partitions = {"0"}),
    })
    public void consumer(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) String partition) {
        try {
            writeMsg(message, partition);
            throw new RuntimeException("Invalid IP Address received !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @KafkaListener(topics = TOPICS, groupId = GROUPID, topicPartitions = {
            @TopicPartition(topic = TOPICS, partitions = { "1" }) })
    public void consumer2(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) String partition) {
        writeMsg(message, partition);
    }
    @KafkaListener(topics = TOPICS, groupId = GROUPID, topicPartitions = {
            @TopicPartition(topic = TOPICS, partitions = { "2" }) })
    public void consumer3(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) String partition) {
        writeMsg(message, partition);
    }
    @KafkaListener(topics = TOPICS, groupId = GROUPID, topicPartitions = {
            @TopicPartition(topic = TOPICS, partitions = { "3" }) })
    public void consumer4(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) String partition) {
        writeMsg(message, partition);
    }
    static void writeMsg(String message, String partition) {
        logger.info("Received message: {}  -- Partition: {}", message, partition);
    }

    @RetryableTopic(exclude = {RuntimeException.class})
    @KafkaListener(topics= TOPICS_3, groupId = GROUPID_3)
    public void logstashELK(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) String partition) {
        writeMsg(message + " with logstash ", partition);
    }

    /**
     * Logger dữ liệu trường hợp xảy ra lỗi
     * @param message
     * @param partition
     * @param offset
     */
    @DltHandler
    public void DltHandler(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) String partition,
                           @Header(KafkaHeaders.OFFSET) String offset) {
        logger.info("DLTMessage message: {} ---- offset: {}", message, offset);
    }

    @KafkaListener(topics = TOPICS_2, groupId = GROUPID, containerFactory = "kafkaJsonListenerContainerFactory")
    @Transactional
    public void consumerJson(@Payload ProducerDTO payload) {
        logger.info("consumerJson event: {} ---- id: {}", payload.event(), payload.id());
    }
}
