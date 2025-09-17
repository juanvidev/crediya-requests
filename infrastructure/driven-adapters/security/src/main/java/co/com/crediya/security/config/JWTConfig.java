package co.com.crediya.security.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@EnableConfigurationProperties(JWTProperties.class)
public class JWTConfig {

    private final JWTProperties properties;

    public JWTConfig(JWTProperties properties) {
        this.properties = properties;
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(properties.getSecret()));
    }
}
