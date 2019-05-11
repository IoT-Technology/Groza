package com.sanshengshui.server.dao.audit.sink;

import com.sanshengshui.server.common.data.audit.AuditLog;

/**
 * @author james mu
 * @date 19-2-1 下午3:23
 * @description
 */
public interface AuditLogSink {

    void logAction(AuditLog auditLogEntry);
}
