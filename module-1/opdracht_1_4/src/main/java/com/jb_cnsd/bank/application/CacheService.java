package com.jb_cnsd.bank.application;

import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    private final CacheManager cacheManager;

    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Scheduled(fixedRate = 60000)
    public void clearCache() {
        var allCaches = cacheManager.getCacheNames();
        for (String cacheName : allCaches) {
            var cache = cacheManager.getCache(cacheName);
            if (cache != null) cache.clear();
        }
    }
}
