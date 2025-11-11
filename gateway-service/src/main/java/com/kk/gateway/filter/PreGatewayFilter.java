package com.kk.gateway.filter;

import com.kk.gateway.service.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class PreGatewayFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(PreGatewayFilter.class);
    private static final List<String> WHITE_LIST = List.of(
            "/ping",
            "/api/sso/token/check",
            "/api/sso/auth/login",
            "/api/sso/auth/signup",
            "/api/sso/password/resetPasswordByUser",
            "/api/sso/verify",
            "/api/quanlyan/configsystem/detail",
            "/api/sso/user/passwordRetrieval"
    );

    private final CacheService cacheService;
    private final DiscoveryClient discoveryClient;

    public PreGatewayFilter(CacheService cacheService, DiscoveryClient discoveryClient) {
        this.cacheService = cacheService;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        setCorsHeaders(response);
        discoveryClient.getServices().forEach(System.out::println);
        discoveryClient.getInstances("sso-service").forEach(System.out::println);
        // ✅ Bỏ qua preflight request
        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            response.setStatusCode(HttpStatus.OK);
            return response.setComplete();
        }

        // ✅ White-list endpoint (public)
        String path = request.getURI().getPath().toLowerCase();
        if (WHITE_LIST.stream().anyMatch(path::contains)) {
            log.debug("[Gateway] Allow public endpoint: {}", path);
            return chain.filter(exchange);
        }

        // ✅ Kiểm tra JWT Token
//        String accessToken = extractJwtFromRequest(request);
//        if (!StringUtils.hasText(accessToken)) {
//            log.warn("[Gateway] Missing Authorization token for {}", path);
//            return writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token không được để trống");
//        }

        // 👉 Bạn có thể validate token từ Redis/Cache ở đây (ví dụ gọi service xác thực)
//        boolean valid = tokenValidate(accessToken);
//        if (!valid) {
//            if (!tokenValidate(extractJwtFromRequestRefresh(request))) {
//                return writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token không hợp lệ hoặc đã hết hạn");
//            }
//        }

        // ✅ Add username header (ví dụ) để gửi qua service Order/Payment
        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("X-Username", "demo_user")
                .build();

        log.info("[Gateway] Forwarding request: {} -> {}", request.getMethod(), path);
        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    private void setCorsHeaders(ServerHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        headers.setAccessControlAllowOrigin("*");
        headers.setAccessControlAllowMethods(List.of(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.OPTIONS));
        headers.setAccessControlAllowHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        headers.setAccessControlExposeHeaders(List.of("Authorization"));
        headers.setAccessControlMaxAge(3600L);
    }

    private String extractJwtFromRequest(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String extractJwtFromRequestRefresh(ServerHttpRequest request) {
        String tokenRefresh = request.getHeaders().getFirst("refresh_token");
        if (StringUtils.hasText(tokenRefresh)) {
            return tokenRefresh;
        }
        return null;
    }

    private boolean tokenValidate(String token) {
        // 👉 Ở đây bạn thay bằng gọi cacheService.getTokenFromCache(token)
        return cacheService.isToken(token);
    }

    private Mono<Void> writeErrorResponse(ServerHttpResponse response, HttpStatus status, String message) {
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String body = String.format("{\"status\":%d,\"error\":\"%s\"}", status.value(), message);
        return response.writeWith(Mono.just(
                response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8))
        ));
    }

    @Override
    public int getOrder() {
        // Đảm bảo chạy trước các filter khác
        return -1;
    }
}
