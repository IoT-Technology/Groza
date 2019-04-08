package com.sanshengshui.server.controller;

import com.sanshengshui.server.common.data.Device;
import com.sanshengshui.server.common.data.audit.ActionType;
import com.sanshengshui.server.common.data.exception.GrozaErrorCode;
import com.sanshengshui.server.common.data.exception.GrozaException;
import com.sanshengshui.server.common.data.id.DeviceId;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.common.data.page.TextPageData;
import com.sanshengshui.server.common.data.page.TextPageLink;
import com.sanshengshui.server.common.data.security.Authority;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DeviceController extends BaseController {

    public static final String DEVICE_ID = "deviceId";

    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN','CUSTOMER_USER')")
    @RequestMapping(value = "/device/{deviceId}", method = RequestMethod.GET)
    @ResponseBody
    public Device getDeviceById(@PathVariable(DEVICE_ID) String strDeviceId) throws GrozaException {
        checkParameter(DEVICE_ID, strDeviceId);
        try {
            DeviceId deviceId = new DeviceId(toUUID(strDeviceId));
            return checkDeviceId(deviceId);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'CUSTOMER_USER')")
    @RequestMapping(value = "/device", method = RequestMethod.POST)
    @ResponseBody
    public Device saveDevice(@RequestBody Device device) throws GrozaException{
        device.setTenantId(getCurrentUser().getTenantId());
        if (getCurrentUser().getAuthority() == Authority.CUSTOMER_USER) {
            if (device.getId() == null || device.getId().isNullUid() ||
            device.getCustomerId() == null || device.getCustomerId().isNullUid()) {
                throw new GrozaException("You don't have permission to perform this operation!",
                        GrozaErrorCode.PERMISSION_DENIED);
            } else {
                checkCustomerId(device.getCustomerId());
            }
        }
        Device savedDevice = checkNotNull(deviceService.saveDevice(device));

        if (device.getId() == null) {
            deviceStateService.onDeviceAdded(savedDevice);
        } else {
            deviceStateService.onDeviceUpdated(savedDevice);
        }
        return savedDevice;
    }

    @PreAuthorize("hasAuthority('TENANT_ADMIN')")
    @RequestMapping(value = "/tenant/devices", params = {"limit"}, method = RequestMethod.GET)
    @ResponseBody
    public TextPageData<Device> getTenantDevices(
            @RequestParam int limit,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String textSearch,
            @RequestParam(required = false) String idOffset,
            @RequestParam(required = false) String textOffset) throws GrozaException {
        try {
            TenantId tenantId = getCurrentUser().getTenantId();
            TextPageLink pageLink = createPageLink(limit, textSearch, idOffset, textOffset);
            if (type != null && type.trim().length() > 0) {
                return checkNotNull(deviceService.findDevicesByTenantIdAndType(tenantId, type, pageLink));
            } else {
                return checkNotNull(deviceService.findDevicesByTenantId(tenantId, pageLink));
            }
        } catch (Exception e) {
            throw handleException(e);
        }

    }

}
