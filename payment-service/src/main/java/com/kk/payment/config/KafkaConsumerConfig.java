package com.kk.payment.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

/**
 * Quy trình thực hiện các cấu hình cho consumer
 * Tạo config: Cấu hình sự kiện
 * Tạo factory: Bộ chuyển đổi sự kiện
 * Tạo Listener: Lắng nghe sự kiện
 */

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${config.kafka.server}")
    private String kafkaServer;

    /**
     * thực hiện set properties the consumer config
     *
     * @return Map
     */
    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }
    /**
     * Thực hiện tạo 1 factory ConsumerFactory<Key,Value>
     *
     * @return ConsumerFactory
     */
    @Bean
    public ConsumerFactory<String, String> strConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }
    @Bean
    public ConsumerFactory<String, byte[]> byteConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new ByteArrayDeserializer());
    }
    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new JsonDeserializer<>());
    }

    /**
     * Thực hiện những cấu hình listener global
     *
     * @param <T>
     * @param <K>
     * @param classKey
     * @param classValue
     * @return ConcurrentKafkaListenerContainerFactory
     */
    public <T,K> ConcurrentKafkaListenerContainerFactory<T, K> genericFactory(Class<T> classKey, Class<K> classValue) {
        ConcurrentKafkaListenerContainerFactory<T, K> factory = new ConcurrentKafkaListenerContainerFactory<>();
//         factory.getContainerProperties().setAckMode(AckMode.RECORD); //
        // https://docs.spring.io/spring-kafka/reference/kafka/receiving-messages/ooo-commits.html#page-title
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(1000L, 1L)));
        return factory;
    }

    /**
     * Thực hiện tạo 1 listener container factory
     * ConcurrentMessageListenerContainer<Key,Value>
     *
     * @return KafkaListenerContainerFactory
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = genericFactory(String.class, Object.class);
        // Set bộ chuyển đổi
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaJsonListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = genericFactory(String.class, String.class);
        factory.setConsumerFactory(strConsumerFactory());
        factory.setRecordMessageConverter(new JsonMessageConverter());
        return factory;
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, byte[]>> kafkaByteListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory = genericFactory(String.class, byte[].class);
        factory.setConsumerFactory(byteConsumerFactory());
        factory.setRecordMessageConverter(new ByteArrayJsonMessageConverter());
        return factory;
    }
}
