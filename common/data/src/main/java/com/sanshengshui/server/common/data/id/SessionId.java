package com.sanshengshui.server.common.data.id;

import java.io.Serializable;

/**
 * @author james mu
 * @date 19-1-2 上午11:20
 */
public interface SessionId extends Serializable {

    String toUidStr();
}
