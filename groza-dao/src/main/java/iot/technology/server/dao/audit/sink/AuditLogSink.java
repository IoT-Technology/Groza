package iot.technology.server.dao.audit.sink;

import iot.technology.server.common.data.audit.AuditLog;

/**
 * @author james mu
 * @date 19-2-1 下午3:23
 * @description
 */
public interface AuditLogSink {

    void logAction(AuditLog auditLogEntry);
}
