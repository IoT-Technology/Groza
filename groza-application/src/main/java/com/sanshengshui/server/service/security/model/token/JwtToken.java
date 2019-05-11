package com.sanshengshui.server.service.security.model.token;

import java.io.Serializable;

/**
 * @author james mu
 * @date 19-3-18 下午2:23
 * @description
 */
public interface JwtToken extends Serializable {
    String getToken();
}
