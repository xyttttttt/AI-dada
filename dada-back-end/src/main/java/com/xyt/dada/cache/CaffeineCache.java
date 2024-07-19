package com.xyt.dada.cache;

import cn.hutool.crypto.digest.DigestUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class CaffeineCache {


    /**
     * AI评分结果缓存
     */
    public static final Cache<String, String> answerCacheMap =
            Caffeine.newBuilder().initialCapacity(1024)
                    //缓存五分钟移除
                    .expireAfterWrite(5L, TimeUnit.MINUTES)
                    .build();

    public static String buildCacheKey(Long appId, String value) {
        return DigestUtil.md5Hex(appId + ":" + value);
    }

    public static String buildAnswerCache(String key, String value) {
        answerCacheMap.put(key, value);
        return value;
    }
}
