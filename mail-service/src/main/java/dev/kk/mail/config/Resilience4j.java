package dev.kk.mail.config;

import dev.kk.mail.constant.CircuitConstant;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import io.github.resilience4j.common.ratelimiter.configuration.RateLimiterConfigCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Resilience4j {

    @Bean
    public CircuitBreakerConfigCustomizer circuitCustomizer() {
        return CircuitBreakerConfigCustomizer
                .of(CircuitConstant.SEND_MAIL, builder ->
                        builder.slidingWindowSize(1000).build());
    }

    @Bean
    public RateLimiterConfigCustomizer rateLimiterCustomizer() {
        return RateLimiterConfigCustomizer
                .of(CircuitConstant.SEND_MAIL, rate ->
                        rate.limitForPeriod(1).build());
    }
}
