package com.sanshengshui.server.service.security.auth;

import com.sanshengshui.server.service.security.model.SecurityUser;
import com.sanshengshui.server.service.security.model.token.RawAccessJwtToken;

/**
 * @author james mu
 * @date 19-3-20 下午2:34
 * @description
 */
public class JwtAuthenticationToken extends AbstractJwtAuthenticationToken{

    private static final long serialVersionUID = -8487219769037942225L;

    public JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(unsafeToken);
    }

    public JwtAuthenticationToken(SecurityUser securityUser) {
        super(securityUser);
    }
}
