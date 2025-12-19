package com.example.shortener.service;

import org.springframework.stereotype.Service;

@Service
public class Base62Service {

    // The character set: 26 lowercase + 26 uppercase + 10 digits = 62 characters
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = CHARACTERS.length();

    /**
     * Converts a base-10 long integer into a base-62 string.
     */
    public String encode(long input) {
        if (input == 0) {
            return String.valueOf(CHARACTERS.charAt(0));
        }

        StringBuilder encodedString = new StringBuilder();

        // Standard base conversion logic
        while (input > 0) {
            int remainder = (int) (input % BASE);
            encodedString.append(CHARACTERS.charAt(remainder));
            input /= BASE;
        }

        // Reverse the string because we calculate digits from right to left
        return encodedString.reverse().toString();
    }
}