package iot.technology.server.controller;

import iot.technology.server.common.data.Tenant;
import iot.technology.server.common.data.exception.GrozaException;
import iot.technology.server.common.data.id.TenantId;
import iot.technology.server.common.data.page.TextPageData;
import iot.technology.server.common.data.page.TextPageLink;
import iot.technology.server.dao.tenant.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'TENANT_ADMIN')")
    @RequestMapping(value = "/tenant/{tenantId}", method = RequestMethod.GET)
    @ResponseBody
    public Tenant getTenantById(@PathVariable("tenantId") String strTenantId) throws GrozaException {
        checkParameter("tenantId", strTenantId);
        try {
            TenantId tenantId = new TenantId(toUUID(strTenantId));
            return checkNotNull(tenantService.findTenantById(tenantId));
        } catch (Exception e) {
            throw handleException(e);
        }
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

    @RequestMapping(value = "/tenants", params = {"limit"}, method = RequestMethod.GET)
    @ResponseBody
    public TextPageData<Tenant> getTenants(@RequestParam int limit,
                                           @RequestParam(required = false) String textSearch,
                                           @RequestParam(required = false) String idOffset,
                                           @RequestParam(required = false) String textOffset) throws GrozaException {
        try {
            TextPageLink pageLink = createPageLink(limit, textSearch, idOffset, textOffset);
            return checkNotNull(tenantService.findTenants(pageLink));
        } catch (Exception e) {
            throw handleException(e);
        }
    }
}
