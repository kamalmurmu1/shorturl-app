package com.example.shortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.TimeZone;

@SpringBootApplication
@EnableCaching // Enables the @Cacheable logic we used in the Controller
public class ShortenerApplication {
    public static void main(String[] args) {
        // Force the application to run in UTC to avoid database timezone mismatch issues
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(ShortenerApplication.class, args);
    }
}
