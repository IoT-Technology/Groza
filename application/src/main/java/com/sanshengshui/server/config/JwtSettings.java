package com.sanshengshui.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author james mu
 * @date 19-3-18 下午2:12
 * @description
 */
@Configuration
@EnableConfigurationProperties(JwtSettings.class)
@ConfigurationProperties(prefix = "security.jwt")
public class JwtSettings {
    /**
     * {@link com.sanshengshui.server.service.security.model.token.JwtToken} will expire after this time.
     */
    private Integer tokenExpirationTime;

    /**
     * Token issuer.
     */
    private String tokenIssuer;

    /**
     * key is used to sign {@link com.sanshengshui.server.service.security.model.token.JwtToken}
     */
    private String tokenSigningKey;

    /**
     * {@link com.sanshengshui.server.service.security.model.token.JwtToken} can be refreshed during this timeframe
     */
    private String refreshTokenExpTime;

    public Integer getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public void setTokenExpirationTime(Integer tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    public String getTokenSigningKey() {
        return tokenSigningKey;
    }

    public void setTokenSigningKey(String tokenSigningKey) {
        this.tokenSigningKey = tokenSigningKey;
    }

    public String getRefreshTokenExpTime() {
        return refreshTokenExpTime;
    }

    public void setRefreshTokenExpTime(String refreshTokenExpTime) {
        this.refreshTokenExpTime = refreshTokenExpTime;
    }
}
