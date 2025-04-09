package com.example.AuthenticationProject.config;

//package com.example.AuthenticationProject.config;

import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.grid.jcache.JCacheProxyManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();

        CacheConfiguration<String, byte[]> config = CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        String.class,
                        byte[].class,
                        ResourcePoolsBuilder.heap(1000))
                .build();

        cacheManager.createCache("rate-limit-buckets",
                Eh107Configuration.fromEhcacheCacheConfiguration(config));

        return cacheManager;
    }

    @Bean
    public ProxyManager<String> proxyManager(CacheManager cacheManager) {
        return new JCacheProxyManager<>(
                cacheManager.getCache("rate-limit-buckets", String.class, byte[].class)
        );
    }
}
