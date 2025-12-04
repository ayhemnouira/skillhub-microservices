package com.skillhub.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        String correlationId = UUID.randomUUID().toString();

        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-Correlation-ID", correlationId)
                .build();

        String method = request.getMethod().toString();
        String path = request.getURI().getPath();
        String ipAddress = getClientIp(request);
        String userId = request.getHeaders().getFirst("X-User-Id");

        long startTime = System.currentTimeMillis();
        String timestamp = LocalDateTime.now().format(formatter);

        logger.info("═══════════════════════════════════════════════════════════");
        logger.info("[{}] [CORRELATION: {}] ➡️  INCOMING REQUEST", timestamp, correlationId);
        logger.info("   Method: {}", method);
        logger.info("   Path: {}", path);
        logger.info("   User ID: {}", userId != null ? userId : "Anonymous");
        logger.info("   IP Address: {}", ipAddress);
        logger.info("───────────────────────────────────────────────────────────");

        return chain.filter(exchange.mutate().request(modifiedRequest).build())
                .then(Mono.fromRunnable(() -> {

                    ServerHttpResponse response = exchange.getResponse();

                    long duration = System.currentTimeMillis() - startTime;

                    int statusCode = response.getStatusCode() != null
                            ? response.getStatusCode().value()
                            : 0;

                    String responseTimestamp = LocalDateTime.now().format(formatter);
                    logger.info("[{}] [CORRELATION: {}] ⬅️  RESPONSE", responseTimestamp, correlationId);
                    logger.info("   Status: {} {}", statusCode, getStatusDescription(statusCode));
                    logger.info("   Duration: {}ms", duration);

                    if (duration > 1000) {
                        logger.warn("⚠️  SLOW REQUEST DETECTED - {} ms for {} {}",
                                duration, method, path);
                    }

                    logger.info("═══════════════════════════════════════════════════════════\n");
                }));
    }

    private String getClientIp(ServerHttpRequest request) {
        String forwardedFor = request.getHeaders().getFirst("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isEmpty()) {
            return forwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddress() != null
                ? request.getRemoteAddress().getAddress().getHostAddress()
                : "Unknown";
    }

    private String getStatusDescription(int statusCode) {
        if (statusCode >= 200 && statusCode < 300) return "SUCCESS";
        if (statusCode >= 300 && statusCode < 400) return "REDIRECT";
        if (statusCode >= 400 && statusCode < 500) return "CLIENT ERROR";
        if (statusCode >= 500) return "SERVER ERROR";
        return "UNKNOWN";
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
