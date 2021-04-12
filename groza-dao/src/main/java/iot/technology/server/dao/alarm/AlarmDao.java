package iot.technology.server.dao.alarm;

import com.google.common.util.concurrent.ListenableFuture;
import iot.technology.server.common.data.alarm.Alarm;
import iot.technology.server.common.data.alarm.AlarmInfo;
import iot.technology.server.common.data.alarm.AlarmQuery;
import iot.technology.server.common.data.id.EntityId;
import iot.technology.server.common.data.id.TenantId;
import iot.technology.server.dao.Dao;

import java.util.List;
import java.util.UUID;

/**
 * @author james mu
 * @date 19-1-3 下午5:15
 */
public interface AlarmDao extends Dao<Alarm> {

    ListenableFuture<Alarm> findLatestByOriginatorAndType(TenantId tenantId, EntityId originator, String type);

    ListenableFuture<Alarm> findAlarmByIdAsync(UUID key);

    Alarm save(Alarm alarm);

    ListenableFuture<List<AlarmInfo>> findAlarms(AlarmQuery query);
}
