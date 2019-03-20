package com.sanshengshui.server.service.security.model.token;

import com.sanshengshui.server.service.security.exception.JwtExpiredTokenException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.Serializable;

/**
 * @author james mu
 * @date 19-3-20 下午2:16
 * @description
 */
public class RawAccessJwtToken implements JwtToken, Serializable {

    private static final long serialVersionUID = -797397445703066079L;

    private static Logger logger = LoggerFactory.getLogger(RawAccessJwtToken.class);

    private String token;

    public RawAccessJwtToken(String token){
        this.token = token;
    }

    public Jws<Claims> parseClaims(String signingKey){
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            logger.error("Invalid JWT Token", ex);
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            logger.info("JWT Token is expired", expiredEx);
            throw new JwtExpiredTokenException(this, "JWT Token expired", expiredEx);
        }

    }

    @Override
    public String getToken() {
        return token;
    }
}
