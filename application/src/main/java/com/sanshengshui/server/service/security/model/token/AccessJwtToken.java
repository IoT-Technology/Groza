package com.sanshengshui.server.service.security.model.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;

/**
 * @author james mu
 * @date 19-3-20 下午2:23
 * @description
 */
public final class AccessJwtToken implements JwtToken{
    private final String rawToken;
    @JsonIgnore
    private transient Claims claims;

    protected AccessJwtToken(final String token, Claims claims){
        this.rawToken = token;
        this.claims = claims;
    }

    public String getToken(){
        return this.rawToken;
    }

    public Claims getClaims(){
        return claims;
    }
}
