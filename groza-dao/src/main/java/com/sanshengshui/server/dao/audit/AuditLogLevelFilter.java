package com.sanshengshui.server.dao.audit;

import com.sanshengshui.server.common.data.EntityType;
import com.sanshengshui.server.common.data.audit.ActionType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author james mu
 * @date 19-2-1 下午2:18
 * @description
 */
public class AuditLogLevelFilter {

    private Map<EntityType, AuditLogLevelMask> entityTypeMask = new HashMap<>();

    public AuditLogLevelFilter(Map<String, String> mask) {
        entityTypeMask.clear();
        mask.forEach((entityTypeStr, logLevelMaskStr) -> {
            EntityType entityType = EntityType.valueOf(entityTypeStr.toUpperCase());
            AuditLogLevelMask logLevelMask = AuditLogLevelMask.valueOf(logLevelMaskStr.toUpperCase());
            entityTypeMask.put(entityType, logLevelMask);
        });
    }

    public boolean logEnabled(EntityType entityType, ActionType actionType){
        AuditLogLevelMask logLevelMask = entityTypeMask.get(entityType);
        if (logLevelMask != null){
            return actionType.isRead() ? logLevelMask.isRead() : logLevelMask.isWrite();
        } else {
            return false;
        }
    }
}
