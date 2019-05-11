package com.sanshengshui.server.service.security.auth;

import com.sanshengshui.server.service.security.model.SecurityUser;
import com.sanshengshui.server.service.security.model.token.RawAccessJwtToken;

/**
 * @author james mu
 * @date 19-3-20 下午2:35
 * @description
 */
public class RefreshAuthenticationToken extends AbstractJwtAuthenticationToken{

    private static final long serialVersionUID = -1311042791508924523L;

    public RefreshAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(unsafeToken);
    }

    public RefreshAuthenticationToken(SecurityUser securityUser) {
        super(securityUser);
    }
}
