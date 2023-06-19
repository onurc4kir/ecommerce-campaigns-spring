package dev.onurcakir.ecommerce.cache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean("authKeyGenerator")
    public KeyGenerator authKeyGenerator() {
        return new AuthKeyGenerator();
    }
    @Bean("campaignGetKeyGenerator")
    public KeyGenerator campaignGetKeyGenerator() {
        return new CampaignGetKeyGenerator();
    }
}
