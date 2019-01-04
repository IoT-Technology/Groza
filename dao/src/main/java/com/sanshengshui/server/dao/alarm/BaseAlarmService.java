package com.sanshengshui.server.dao.alarm;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.alarm.*;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.id.TenantId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author james mu
 * @date 19-1-4 上午9:32
 */
@Service
@Slf4j
public class BaseAlarmService implements AlarmService{

    @Override
    public Alarm createOrUpdateAlarm(Alarm alarm) {
        return null;
    }

    @Override
    public ListenableFuture<Boolean> ackAlarm(AlarmId alarmId, long ackTs) {
        return null;
    }

    @Override
    public ListenableFuture<Boolean> clearAlarm(AlarmId alarmId, JsonNode details, long ackTs) {
        return null;
    }

    @Override
    public ListenableFuture<Alarm> findAlarmByIdAsync(AlarmId alarmId) {
        return null;
    }

    @Override
    public ListenableFuture<AlarmInfo> findAlarmInfoByIdAsync(AlarmId alarmId) {
        return null;
    }

    @Override
    public AlarmSeverity findHighestAlarmSeverity(EntityId entityId, AlarmSearchStatus alarmSearchStatus, AlarmStatus alarmStatus) {
        return null;
    }

    @Override
    public ListenableFuture<Alarm> findLatestByOriginatorAndType(TenantId tenantId, EntityId originator, String type) {
        return null;
    }
}
