package com.shrt.shrt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String longUrl;

    @Column(unique = true)
    private String shortUrl;

    private LocalDateTime createdAt = LocalDateTime.now();
}
