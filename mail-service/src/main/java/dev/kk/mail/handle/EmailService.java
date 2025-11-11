package dev.kk.mail.handle;

import dev.kk.mail.constant.CircuitConstant;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface EmailService {


    @CircuitBreaker(name = CircuitConstant.SEND_MAIL, fallbackMethod = CircuitConstant.FALLBACK)
    @RateLimiter(name = CircuitConstant.SEND_MAIL)
    @Bulkhead(name = CircuitConstant.SEND_MAIL, fallbackMethod = CircuitConstant.FALLBACK)
    @Retry(name = CircuitConstant.SEND_MAIL)
    @TimeLimiter(name = CircuitConstant.SEND_MAIL)
    void sendEmail(ContentRecord content, String to) throws MessagingException, IllegalAccessException, URISyntaxException, IOException;

    void fallback(ContentRecord content, String to, CallNotPermittedException e);

    void fallback(ContentRecord content, String to, NumberFormatException e);

    void fallback(ContentRecord content, String to, Exception e);
}
