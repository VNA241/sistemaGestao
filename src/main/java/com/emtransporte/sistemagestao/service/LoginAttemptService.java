package com.emtransporte.sistemagestao.service;

import org.springframework.stereotype.Service;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private final int MAX_ATTEMPTS = 3;
    private final long BLOCK_TIME = TimeUnit.MINUTES.toMillis(5);

    private final ConcurrentHashMap<String, Attempt> attemptsCache = new ConcurrentHashMap<>();

    public void loginFailed(String key) {
        Attempt attempt = attemptsCache.getOrDefault(key, new Attempt());
        attempt.increment();
        attemptsCache.put(key, attempt);
    }

    public void loginSucceeded(String key) {
        attemptsCache.remove(key);
    }

    public boolean isBlocked(String key) {
        Attempt attempt = attemptsCache.get(key);
        if (attempt == null) {
            return false;
        }

        if (attempt.count > MAX_ATTEMPTS) {
            if (System.currentTimeMillis() - attempt.lastAttempt < BLOCK_TIME) {
                return true;
            } else {
                attemptsCache.remove(key);
            }
        }

        return false;
    }

    public void desbloquearSeExpirado(String key) {
        Attempt attempt = attemptsCache.get(key);
        if (attempt != null && System.currentTimeMillis() - attempt.lastAttempt >= BLOCK_TIME) {
            attemptsCache.remove(key);
        }
    }

    static class Attempt {
        int count = 1;
        long lastAttempt = System.currentTimeMillis();

        void increment() {
            count++;
            lastAttempt = System.currentTimeMillis();
        }
    }
}
