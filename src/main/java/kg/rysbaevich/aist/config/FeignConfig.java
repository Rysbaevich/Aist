package kg.rysbaevich.aist.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Value("${yookassa.shop.id}")
    private String shopId;

    @Value("${yookassa.shop.secret}")
    private String secret;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(shopId, secret);
    }
}
