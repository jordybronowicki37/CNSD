package com.jb_cnsd.opdracht_2_3.application;

import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CacheService {
    private final CacheManager cacheManager;

    @Scheduled(fixedRate = 60000)
    public void clearCache() {
        var allCaches = cacheManager.getCacheNames();
        for (String cacheName : allCaches) {
            var cache = cacheManager.getCache(cacheName);
            if (cache != null) cache.clear();
        }
    }
}
