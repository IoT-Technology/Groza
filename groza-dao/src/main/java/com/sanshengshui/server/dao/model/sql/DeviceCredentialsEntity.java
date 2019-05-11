package com.sanshengshui.server.dao.model.sql;

import com.datastax.driver.core.utils.UUIDs;
import com.sanshengshui.server.common.data.id.DeviceCredentialsId;
import com.sanshengshui.server.common.data.id.DeviceId;
import com.sanshengshui.server.common.data.security.DeviceCredentials;
import com.sanshengshui.server.common.data.security.DeviceCredentialsType;
import com.sanshengshui.server.dao.model.BaseEntity;
import com.sanshengshui.server.dao.model.BaseSqlEntity;
import com.sanshengshui.server.dao.model.ModelConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author james
 * @date 2018/12/22 2:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = ModelConstants.DEVICE_CREDENTIALS_COLUMN_FAMILY_NAME)
public final class DeviceCredentialsEntity extends BaseSqlEntity<DeviceCredentials> implements BaseEntity<DeviceCredentials> {

    @Column(name = ModelConstants.DEVICE_CREDENTIALS_DEVICE_ID_PROPERTY)
    private String deviceId;

    @Enumerated(EnumType.STRING)
    @Column(name = ModelConstants.DEVICE_CREDENTIALS_CREDENTIALS_TYPE_PROPERTY)
    private DeviceCredentialsType credentialsType;

    @Column(name = ModelConstants.DEVICE_CREDENTIALS_CREDENTIALS_ID_PROPERTY)
    private String credentialsId;

    @Column(name = ModelConstants.DEVICE_CREDENTIALS_CREDENTIALS_VALUE_PROPERTY)
    private String credentialsValue;

    public DeviceCredentialsEntity() {
        super();
    }

    public DeviceCredentialsEntity(DeviceCredentials deviceCredentials) {
        if (deviceCredentials.getId() != null) {
            this.setId(deviceCredentials.getId().getId());
        }
        if (deviceCredentials.getDeviceId() != null) {
            this.deviceId = toString(deviceCredentials.getDeviceId().getId());
        }
        this.credentialsType = deviceCredentials.getCredentialsType();
        this.credentialsId = deviceCredentials.getCredentialsId();
        this.credentialsValue = deviceCredentials.getCredentialsValue();
    }

    @Override
    public DeviceCredentials toData() {
        DeviceCredentials deviceCredentials = new DeviceCredentials(new DeviceCredentialsId(getId()));
        deviceCredentials.setCreatedTime(UUIDs.unixTimestamp(getId()));
        if (deviceId != null) {
            deviceCredentials.setDeviceId(new DeviceId(toUUID(deviceId)));
        }
        deviceCredentials.setCredentialsType(credentialsType);
        deviceCredentials.setCredentialsId(credentialsId);
        deviceCredentials.setCredentialsValue(credentialsValue);
        return deviceCredentials;
    }
}
