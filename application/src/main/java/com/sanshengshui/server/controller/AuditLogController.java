package com.sanshengshui.server.controller;

import com.sanshengshui.server.common.data.audit.AuditLog;
import com.sanshengshui.server.common.data.exception.GrozaException;
import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.common.data.page.TimePageData;
import com.sanshengshui.server.common.data.page.TimePageLink;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author james mu
 * @date 19-3-25 下午4:41
 * @description
 */
@RestController
@RequestMapping("/api")
public class AuditLogController extends BaseController{

    @PreAuthorize("hasAuthority('TENANT_ADMIN')")
    public TimePageData<AuditLog> getAuditLogsByCustomerId(
            @PathVariable("customerId") String strCustomerId,
            @RequestParam int limit,
            @RequestParam(required = false) long startTime,
            @RequestParam(required = false) long endTime,
            @RequestParam(required = false, defaultValue = "false") boolean ascOrder,
            @RequestParam(required = false) String offset) throws GrozaException {
        try {
            checkParameter("CustomerId", strCustomerId);
            TenantId tenantId = getCurrentUser().getTenantId();
            TimePageLink pageLink = createPageLink(limit, startTime, endTime, ascOrder, offset);
            return checkNotNull(auditLogService.findAuditLogsByTenantIdAndCustomerId(tenantId, new CustomerId(UUID.fromString(strCustomerId)),pageLink));
        } catch (Exception e) {
            throw handleException(e);
        }

    }
}
