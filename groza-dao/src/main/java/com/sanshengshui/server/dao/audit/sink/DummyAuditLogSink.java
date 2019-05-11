package com.sanshengshui.server.dao.audit.sink;

import com.sanshengshui.server.common.data.audit.AuditLog;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author james mu
 * @date 19-2-1 下午3:40
 * @description
 */
@Component
@ConditionalOnProperty(prefix = "audit_log.sink", value = "type", havingValue = "none")
public class DummyAuditLogSink implements AuditLogSink{

    @Override
    public void logAction(AuditLog auditLogEntry) {
    }
}
