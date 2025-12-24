package com.example.shortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This DTO (Data Transfer Object) captures the incoming
 * JSON request from the user.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlRequest {

    // This matches the "url" key in your JSON body
    // Example JSON: { "url": "https://www.example.com" }
    private String uhrl;
}