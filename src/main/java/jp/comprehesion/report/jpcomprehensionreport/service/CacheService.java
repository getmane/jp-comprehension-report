package jp.comprehesion.report.jpcomprehensionreport.service;

import jakarta.annotation.PostConstruct;
import jp.comprehesion.report.jpcomprehensionreport.constant.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final CacheManager cacheManager;

    @PostConstruct
    public void init() {
        Cache defaultCache = cacheManager.getCache(Constants.CACHE_DEFAULT_REPOSITORY);
        if (defaultCache != null) {
            defaultCache.put(Constants.CACHE_KNOWN_WORDS_REPOSITORY, new ArrayList<String>());
            defaultCache.put(Constants.CACHE_UNKNOWN_WORDS_REPOSITORY, new ArrayList<String>());
            defaultCache.put(Constants.CACHE_JPDB_WORDS_REPOSITORY, new ArrayList<String>());
            defaultCache.put(Constants.CACHE_PAGE_WORDS_REPOSITORY, new ArrayList<String>());
        }
    }

    public <T> List<T> getValues(String key) {
        Cache defaultCache = cacheManager.getCache(Constants.CACHE_DEFAULT_REPOSITORY);
        return defaultCache != null ? defaultCache.get(key, List.class) : new ArrayList<>();
    }

    public <T> void putValues(String key, List<T> values) {
        Cache defaultCache = cacheManager.getCache(Constants.CACHE_DEFAULT_REPOSITORY);
        if (defaultCache != null) {
            defaultCache.get(key, List.class).addAll(values);
        }
    }

    public void reset(String key) {
        Cache defaultCache = cacheManager.getCache(Constants.CACHE_DEFAULT_REPOSITORY);
        if (defaultCache != null) {
            defaultCache.put(key, new ArrayList<String>());
        }
    }
}
