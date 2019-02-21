package com.sanshengshui.server.dao.sql.alarm;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.alarm.Alarm;
import com.sanshengshui.server.common.data.alarm.AlarmInfo;
import com.sanshengshui.server.common.data.alarm.AlarmQuery;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.dao.alarm.AlarmDao;
import com.sanshengshui.server.dao.model.sql.AlarmEntity;
import com.sanshengshui.server.dao.sql.JpaAbstractDao;
import com.sanshengshui.server.dao.util.SqlDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * @author james mu
 * @date 19-2-21 上午9:52
 * @description
 */
@Slf4j
@Component
@SqlDao
public class JpaAlarmDao extends JpaAbstractDao<AlarmEntity, Alarm> implements AlarmDao {

    @Autowired
    private AlarmRepository alarmRepository;

    @Override
    protected Class<AlarmEntity> getEntityClass() {
        return AlarmEntity.class;
    }

    @Override
    protected CrudRepository<AlarmEntity, String> getCrudRepository() {
        return alarmRepository;
    }

    @Override
    public ListenableFuture<Alarm> findLatestByOriginatorAndType(TenantId tenantId, EntityId originator, String type) {
        return null;
    }

    @Override
    public ListenableFuture<Alarm> findAlarmByIdAsync(UUID key) {
        return findByIdAsync(key);
    }

    @Override
    public ListenableFuture<List<AlarmInfo>> findAlarms(AlarmQuery query) {
        return null;
    }
}
