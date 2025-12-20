package com.example.shortener.service;

import com.example.shortener.entity.UrlMapping;
import com.example.shortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UrlService {
    @Autowired private UrlRepository repository;
    @Autowired private Base62Service base62;

    @Transactional
    public String shortenUrl(String longUrl) {
        UrlMapping mapping = new UrlMapping();
        mapping.setLongUrl(longUrl);
        mapping = repository.save(mapping); // First save to generate the ID

        String code = base62.encode(mapping.getId());
        mapping.setShortCode(code);
        repository.save(mapping); // Update with the code
        return code;
    }

    public String getOriginalUrl(String shortCode) {
        return repository.findByShortCode(shortCode)
                .map(UrlMapping::getLongUrl)
                .orElseThrow(() -> new RuntimeException("URL Not Found"));
    }
}