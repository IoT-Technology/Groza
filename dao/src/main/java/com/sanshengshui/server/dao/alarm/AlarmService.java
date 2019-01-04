package com.sanshengshui.server.dao.alarm;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.alarm.*;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.id.TenantId;

/**
 * @author james mu
 * @date 19-1-3 下午5:23
 */
public interface AlarmService {

    Alarm createOrUpdateAlarm(Alarm alarm);

    ListenableFuture<Boolean> ackAlarm(AlarmId alarmId, long ackTs);

    ListenableFuture<Boolean> clearAlarm(AlarmId alarmId, JsonNode details, long ackTs);

    ListenableFuture<Alarm> findAlarmByIdAsync(AlarmId alarmId);

    ListenableFuture<AlarmInfo> findAlarmInfoByIdAsync(AlarmId alarmId);


    AlarmSeverity findHighestAlarmSeverity(EntityId entityId, AlarmSearchStatus alarmSearchStatus,
                                           AlarmStatus alarmStatus);

    ListenableFuture<Alarm> findLatestByOriginatorAndType(TenantId tenantId, EntityId originator, String type);
}
