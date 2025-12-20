package com.example.shortener.controller;

import com.example.shortener.dto.UrlRequest;
import com.example.shortener.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@RestController
public class UrlController {

    private static final Logger logger = LoggerFactory.getLogger(UrlController.class);
    private final UrlService urlService;
    private final RedisTemplate<String, String> redisTemplate;

    public UrlController(UrlService urlService, RedisTemplate<String, String> redisTemplate) {
        this.urlService = urlService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@RequestBody UrlRequest request) {
        String code = urlService.shortenUrl(request.getUrl());
        return ResponseEntity.ok("http://localhost:8080/" + code);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String longUrl = null;

        // Try to get from Redis, but don't fail if Redis is down
        try {
            longUrl = redisTemplate.opsForValue().get(code);
        } catch (Exception e) {
            logger.error("Redis is unavailable: {}", e.getMessage());
        }

        if (longUrl == null) {
            try {
                longUrl = urlService.getOriginalUrl(code);
                
                // Try to save to Redis, but don't fail if Redis is down
                try {
                    redisTemplate.opsForValue().set(code, longUrl, 1, TimeUnit.DAYS);
                } catch (Exception e) {
                    logger.error("Failed to cache URL in Redis: {}", e.getMessage());
                }
            } catch (RuntimeException e) {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }
}
