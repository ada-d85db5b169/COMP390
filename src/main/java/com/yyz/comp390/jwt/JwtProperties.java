package com.yyz.comp390.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "comp390.jwt")
@Data
public class JwtProperties {

    /**
     * All user jwt properties
     */
    private String SecretKey;
    private long ttl;
    private String tokenName;
}
