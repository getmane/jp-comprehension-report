package jp.comprehesion.report.jpcomprehensionreport.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheService {

    private static final String DEFAULT_CACHE = "default";

    private final CacheManager cacheManager;

    @PostConstruct
    public void init() {
        Cache defaultCache = cacheManager.getCache(DEFAULT_CACHE);
        if (defaultCache != null) {
            defaultCache.put("knownWords", new ArrayList<String>());
            defaultCache.put("unknownWords", new ArrayList<String>());
            defaultCache.put("jpdbWords", new ArrayList<String>());
            defaultCache.put("pageWords", new ArrayList<String>());
        }
    }

    public <T> List<T> getValues(String key) {
        Cache defaultCache = cacheManager.getCache(DEFAULT_CACHE);
        return defaultCache != null ? defaultCache.get(key, List.class) : new ArrayList<>();
    }

    public <T> void putValues(String key, List<T> values) {
        Cache defaultCache = cacheManager.getCache(DEFAULT_CACHE);
        if (defaultCache != null) {
            defaultCache.get(key, List.class).addAll(values);
        }
    }

    public void reset(String key) {
        Cache defaultCache = cacheManager.getCache(DEFAULT_CACHE);
        if (defaultCache != null) {
            defaultCache.put(key, new ArrayList<String>());
        }
    }
}
