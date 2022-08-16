package dev.edurevsky.contacts.utils;

import org.springframework.stereotype.Component;

@Component
public class JWTUtils {

    private final String applicationSecret = "secret";
    private final Integer expirationLimit = 60 * 60 * 2 * 1000; // 2 Hours
}
