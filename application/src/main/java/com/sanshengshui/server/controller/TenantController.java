package com.sanshengshui.server.controller;

import com.sanshengshui.server.common.data.Tenant;
import com.sanshengshui.server.common.data.exception.GrozaException;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.dao.tenant.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author james mu
 * @date 19-1-24 下午4:21
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class TenantController extends BaseController{

    @Autowired
    private TenantService tenantService;

    @RequestMapping(value = "/tenant/{tenantId}", method = RequestMethod.GET)
    @ResponseBody
    public Tenant getTenantById(@PathVariable("tenantId") String strTenantId) throws GrozaException {
        checkParameter("tenantId", strTenantId);
        TenantId tenantId = new TenantId(toUUID(strTenantId));

    }

    @ResponseBody
    @RequestMapping(value = "/tenant", method = RequestMethod.POST)
    public Tenant saveTenant(@RequestBody Tenant tenant) throws GrozaException {
        try {
            boolean newTenant = tenant.getId() == null;
            tenant = checkNotNull(tenantService.saveTenant(tenant));
            if (newTenant){
                //TODO
            }
            return tenant;
        } catch (Exception e) {
            throw handleException(e);
        }
    }
}
