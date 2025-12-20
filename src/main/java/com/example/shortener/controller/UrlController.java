package com.example.shortener.controller;

import com.example.shortener.dto.UrlRequest;
import com.example.shortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@RestController
public class UrlController {

    @Autowired private UrlService urlService;
    @Autowired private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@RequestBody UrlRequest request) {
        String code = urlService.shortenUrl(request.getUrl());
        return ResponseEntity.ok("http://localhost:8080/" + code);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String longUrl = redisTemplate.opsForValue().get(code);

        if (longUrl == null) {
            longUrl = urlService.getOriginalUrl(code);
            redisTemplate.opsForValue().set(code, longUrl, 1, TimeUnit.DAYS);
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }
}