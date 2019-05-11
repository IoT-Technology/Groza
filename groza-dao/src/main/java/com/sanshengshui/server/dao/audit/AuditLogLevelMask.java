package com.sanshengshui.server.dao.audit;

import lombok.Getter;

/**
 * @author james mu
 * @date 19-2-1 下午2:19
 * @description
 */
@Getter
public enum  AuditLogLevelMask {

    OFF(false, false),
    W(true, false),
    RW(true, true);

    private final boolean write;
    private final boolean read;

    AuditLogLevelMask(boolean write, boolean read){
        this.write = write;
        this.read = read;
    }
}
