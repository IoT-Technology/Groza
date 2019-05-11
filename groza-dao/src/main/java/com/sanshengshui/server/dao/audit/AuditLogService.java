package com.sanshengshui.server.dao.audit;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.BaseData;
import com.sanshengshui.server.common.data.HasName;
import com.sanshengshui.server.common.data.audit.ActionType;
import com.sanshengshui.server.common.data.audit.AuditLog;
import com.sanshengshui.server.common.data.id.*;
import com.sanshengshui.server.common.data.page.TimePageData;
import com.sanshengshui.server.common.data.page.TimePageLink;

import java.util.List;

/**
 * @author james mu
 * @date 19-2-1 下午1:32
 * @description
 */
public interface AuditLogService {

    TimePageData<AuditLog> findAuditLogsByTenantIdAndCustomerId(TenantId tenantId, CustomerId customerId, TimePageLink pageLink);

    TimePageData<AuditLog> findAuditLogsByTenantIdAndUserId(TenantId tenantId, UserId userId, TimePageLink pageLink);

    TimePageData<AuditLog> findAuditLogsByTenantIdAndEntityId(TenantId tenantId, EntityId entityId, TimePageLink pageLink);

    TimePageData<AuditLog> findAuditLogsByTenantId(TenantId tenantId, TimePageLink pageLink);

    <E extends BaseData<I> & HasName,
            I extends UUIDBased & EntityId> ListenableFuture<List<Void>> logEntityAction(TenantId tenantId,
                                                                                         CustomerId customerId,
                                                                                         UserId userId,
                                                                                         String userName,
                                                                                         I entityId,
                                                                                         E entity,
                                                                                         ActionType actionType,
                                                                                         Exception e, Object... additionalInfo);
}
