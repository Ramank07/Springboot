//package com.example.AuthenticationProject.service;
//
//import io.github.bucket4j.Bandwidth;
//import io.github.bucket4j.Bucket;
//import io.github.bucket4j.BucketConfiguration;
//import io.github.bucket4j.Refill;
//import io.github.bucket4j.distributed.proxy.ProxyManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.util.function.Supplier;
//
////@Service
////public class RateLimitService {
////
////    private final ProxyManager<String> proxyManager;
////
////    @Autowired
////    public RateLimitService(ProxyManager<String> proxyManager) {
////        this.proxyManager = proxyManager;
////    }
////
////    public Bucket resolveBucket(String key) {
////        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUser();
////        return proxyManager.builder().build(key, configSupplier);
////    }
////
////    private Supplier<BucketConfiguration> getConfigSupplierForUser() {
////        return () -> BucketConfiguration.builder()
////                .addLimit(Bandwidth.classic(5, Refill.intervally(5, Duration.ofMinutes(1))))
////                .build();
////    }
////}
//
//
////package com.example.AuthenticationProject.service;
////
////import com.example.AuthenticationProject.config.RateLimitProperties;
////import io.github.bucket4j.Bandwidth;
////import io.github.bucket4j.Bucket;
////import io.github.bucket4j.BucketConfiguration;
////import io.github.bucket4j.Refill;
////import io.github.bucket4j.distributed.proxy.ProxyManager;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.context.properties.EnableConfigurationProperties;
////import org.springframework.stereotype.Service;
////
////import java.time.Duration;
////import java.util.function.Supplier;
////
////@Service
////@EnableConfigurationProperties(RateLimitProperties.class)
////public class RateLimitService {
////
////    private final ProxyManager<String> proxyManager;
////    private final RateLimitProperties properties;
////
////    @Autowired
////    public RateLimitService(ProxyManager<String> proxyManager, RateLimitProperties properties) {
////        this.proxyManager = proxyManager;
////        this.properties = properties;
////    }
////
////    public Bucket resolveBucket(String key) {
////        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUser();
////        return proxyManager.builder().build(key, configSupplier);
////    }
////
////    private Supplier<BucketConfiguration> getConfigSupplierForUser() {
////        return () -> BucketConfiguration.builder()
////                .addLimit(Bandwidth.classic(
////                                properties.getCapacity(),
////                                Refill.intervally(properties.getCapacity(),
////                                        Duration.ofMinutes(properties.getDuration()))
////                        )
////                        .build();
////    }
////}
//
//
//
//
//
//
//
//
////package com.example.AuthenticationProject.service;
//
//import com.example.AuthenticationProject.config.RateLimitProperties;
//import io.github.bucket4j.Bandwidth;
//import io.github.bucket4j.Bucket;
//import io.github.bucket4j.BucketConfiguration;
//import io.github.bucket4j.Refill;
//import io.github.bucket4j.distributed.proxy.ProxyManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.util.function.Supplier;
//
//@Service
//@EnableConfigurationProperties(RateLimitProperties.class)
//public class RateLimitService {
//
//    private final ProxyManager<String> proxyManager;
//    private final RateLimitProperties properties;
//
//    @Autowired
//    public RateLimitService(ProxyManager<String> proxyManager, RateLimitProperties properties) {
//        this.proxyManager = proxyManager;
//        this.properties = properties;
//    }
//
//    public Bucket resolveBucket(String key) {
//        Supplier<BucketConfiguration> configSupplier = getConfigSupplierForUser();
//        return proxyManager.builder().build(key, configSupplier);
//    }
//
//    private Supplier<BucketConfiguration> getConfigSupplierForUser() {
//        return () -> {
//            Bandwidth limit = Bandwidth.classic(
//                    properties.getCapacity(),
//                    Refill.intervally(properties.getCapacity(),
//                            Duration.ofMinutes(properties.getDuration()))
//            );
//            return BucketConfiguration.builder()
//                    .addLimit(limit)
//                    .build();
//        };
//    }
//}




//for attempt only

package com.example.AuthenticationProject.service;

import com.example.AuthenticationProject.config.RateLimitProperties;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.function.Supplier;

@Service
@EnableConfigurationProperties(RateLimitProperties.class)
public class RateLimitService {

    private final ProxyManager<String> proxyManager;

    @Autowired
    public RateLimitService(ProxyManager<String> proxyManager) {
        this.proxyManager = proxyManager;
    }

    public Bucket resolveBucket(String key) {
        Supplier<BucketConfiguration> configSupplier = getFailedLoginConfig();
        return proxyManager.builder().build(key, configSupplier);
    }

    /**
     * Limit failed login attempts to 5 per 2 minutes
     */
    private Supplier<BucketConfiguration> getFailedLoginConfig() {
        return () -> {
            Bandwidth limit = Bandwidth.classic(
                    5, // max 5 attempts
                    Refill.intervally(5, Duration.ofMinutes(2)) // refill all 5 tokens after 2 minutes
            );
            return BucketConfiguration.builder()
                    .addLimit(limit)
                    .build();
        };
    }
}
