package iot.technology.server.service.state;

import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import iot.technology.server.common.data.Device;
import iot.technology.server.common.data.id.DeviceId;
import iot.technology.server.dao.device.DeviceService;
import iot.technology.server.dao.tenant.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author: 穆书伟
 * @Date: 19-4-8 下午1:32
 * @Version 1.0
 */
@Service
@Slf4j
public class DefaultDeviceStateService implements DeviceStateService{

    private ListeningScheduledExecutorService queueExecutor;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private DeviceService deviceService;

    @PostConstruct
    public void init() {
    }

    @PreDestroy
    public void stop() {
        if (queueExecutor != null) {
            queueExecutor.shutdownNow();
        }
    }

    @Override
    public void onDeviceAdded(Device device) {

    }

    @Override
    public void onDeviceUpdated(Device device) {

    }

    @Override
    public void onDeviceDeleted(Device device) {

    }

    @Override
    public void onDeviceConnect(DeviceId deviceId) {

    }

    @Override
    public void onDeviceActivity(DeviceId deviceId) {

    }

    @Override
    public void onDeviceDisConnect(DeviceId deviceId) {

    }

    @Override
    public void onDeviceInactivityTimeoutUpdate(DeviceId deviceId, long inactivityTimeout) {

    }
}
