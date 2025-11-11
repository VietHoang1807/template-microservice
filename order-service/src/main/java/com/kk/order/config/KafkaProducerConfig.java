package com.kk.order.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${config.kafka.server}")
    private String kafkaServer;


//    @Bean
//    public KafkaAdmin admin() {
//         Map<String, Object> props = new HashMap<>();
//         props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
//         props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 10000);
//         return new KafkaAdmin(props);
//    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // === Các cấu hình nâng cao ===
        /*Xác định khi nào producer coi message là “đã gửi thành công”.
        🔸 acks=0 → không chờ phản hồi (hiệu năng cao, rủi ro mất dữ liệu).
        🔸 acks=1 → chờ leader ghi log (cân bằng giữa tốc độ và an toàn).
        🔸 acks=all → chờ tất cả replica xác nhận (đảm bảo nhất, nhưng chậm hơn).*/
        props.put(ProducerConfig.ACKS_CONFIG, "all"); // Đảm bảo dữ liệu không mất mát (mặc định 1)
        /*Số lần thử gửi lại nếu lỗi tạm thời (ví dụ: network glitch).
        🔸 Có thể tăng lên 3, 5 hoặc 10 tuỳ mức chịu lỗi mong muốn.
        🔸 Kết hợp với delivery.timeout.ms để tránh retry vô tận.*/
        props.put(ProducerConfig.RETRIES_CONFIG, 5);  // Thử gửi lại tối đa 5 lần
        /*Thời gian tối đa cho một lần gửi (bao gồm cả retry)
        🔸 Dùng cùng retries để kiểm soát vòng đời message.
        🔸 Nếu retries cao, nên tăng delivery.timeout.ms tương ứng.*/
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 120000); // Timeout gửi (2 phút)
        /*Bật chế độ “chống trùng lặp” để tránh gửi message nhiều lần nếu retry.
        🔸 Đặt true khi yêu cầu đảm bảo chính xác 1 lần (exactly-once delivery).
        🔸 Khi bật, Kafka sẽ tự thiết lập acks=all và retries=Integer.MAX_VALUE*/
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); // Tránh gửi trùng message
        /*Giới hạn số request chưa được ACK trên mỗi kết nối.
        🔸 Nếu bật idempotence, nên giới hạn ≤ 5 để tránh lỗi reorder message*/
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        /*Độ trễ tối đa trước khi gửi batch message (ms).
        🔸 Tăng nhẹ (5–20ms) để tối ưu hiệu năng khi gửi nhiều message nhỏ.*/
        props.put(ProducerConfig.LINGER_MS_CONFIG, 5); // Trễ nhẹ để batch nhiều message
        /*Kích thước tối đa của batch message.
        🔸 Tăng lên 32KB hoặc 64KB nếu hệ thống gửi message thường xuyên*/
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 32768); // Batch 32KB
        /*Bộ nhớ đệm cục bộ cho producer.
        🔸 Cần tăng nếu gửi dữ liệu lớn hoặc tốc độ cao.*/
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 67108864); // Bộ nhớ đệm 64MB
        return props;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> props = producerConfigs();
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }
    @Bean
    public ProducerFactory<String, String> strProducerFactory() {
        Map<String, Object> props = producerConfigs();
        return new DefaultKafkaProducerFactory<>(props);
    }
    @Bean
    public ProducerFactory<String, Byte[]> byteProducerFactory() {
        Map<String, Object> props = producerConfigs();
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(
                producerFactory(),
                Collections.singletonMap(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class)
        );
    }
    @Bean
    public KafkaTemplate<String, Byte[]> byteKafkaTemplate() {
        return new KafkaTemplate<>(byteProducerFactory(),
                Collections.singletonMap(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class));
    }
    @Bean
    public KafkaTemplate<String, String> strKafkaTemplate() {
        return new KafkaTemplate<>(strProducerFactory());
    }

    @Bean
    public KafkaAdmin.NewTopics createTopics() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("payment-topic")
                        .partitions(4)
                        .build(),
                TopicBuilder.name("j_payment-topic")
                        .partitions(2)
                        .build(),
                TopicBuilder.name("kafka-topic-3")
                        .partitions(3)
                        .build());
    }
}
