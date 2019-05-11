package com.sanshengshui.server.service.state;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.sanshengshui.server.common.data.Device;
import com.sanshengshui.server.common.data.Tenant;
import com.sanshengshui.server.common.data.id.DeviceId;
import com.sanshengshui.server.common.data.page.TextPageLink;
import com.sanshengshui.server.dao.device.DeviceService;
import com.sanshengshui.server.dao.tenant.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
