package com.sanshengshui.server.dao.model.sql;

import com.sanshengshui.server.common.data.EntityType;
import com.sanshengshui.server.common.data.kv.*;
import com.sanshengshui.server.dao.model.ToData;
import lombok.Data;

import javax.persistence.*;

import static com.sanshengshui.server.dao.model.ModelConstants.*;

/**
 * @author james mu
 * @date 19-1-30 下午4:47
 * @description
 */
@Data
@Entity
@Table(name = "ts_kv")
@IdClass(TsKvCompositeKey.class)
public final class TsKvEntity implements ToData<TsKvEntry> {

    public TsKvEntity() {
    }

    public TsKvEntity(Double avgLongValue, Double avgDoubleValue) {
        if(avgLongValue != null) {
            this.longValue = avgLongValue.longValue();
        }
        this.doubleValue = avgDoubleValue;
    }

    public TsKvEntity(Long sumLongValue, Double sumDoubleValue) {
        this.longValue = sumLongValue;
        this.doubleValue = sumDoubleValue;
    }

    public TsKvEntity(String strValue, Long longValue, Double doubleValue) {
        this.strValue = strValue;
        this.longValue = longValue;
        this.doubleValue = doubleValue;
    }

    public TsKvEntity(Long booleanValueCount, Long strValueCount, Long longValueCount, Long doubleValueCount) {
        if (booleanValueCount != 0) {
            this.longValue = booleanValueCount;
        } else if (strValueCount != 0) {
            this.longValue = strValueCount;
        } else if (longValueCount != 0) {
            this.longValue = longValueCount;
        } else if (doubleValueCount != 0) {
            this.longValue = doubleValueCount;
        }
    }

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = ENTITY_TYPE_COLUMN)
    private EntityType entityType;

    @Id
    @Column(name = ENTITY_ID_COLUMN)
    private String entityId;

    @Id
    @Column(name = KEY_COLUMN)
    private String key;

    @Id
    @Column(name = TS_COLUMN)
    private long ts;

    @Column(name = BOOLEAN_VALUE_COLUMN)
    private Boolean booleanValue;

    @Column(name = STRING_VALUE_COLUMN)
    private String strValue;

    @Column(name = LONG_VALUE_COLUMN)
    private Long longValue;

    @Column(name = DOUBLE_VALUE_COLUMN)
    private Double doubleValue;

    @Override
    public TsKvEntry toData() {
        KvEntry kvEntry = null;
        if (strValue != null) {
            kvEntry = new StringDataEntry(key, strValue);
        } else if (longValue != null) {
            kvEntry = new LongDataEntry(key, longValue);
        } else if (doubleValue != null) {
            kvEntry = new DoubleDataEntry(key, doubleValue);
        } else if (booleanValue != null) {
            kvEntry = new BooleanDataEntry(key, booleanValue);
        }
        return new BasicTsKvEntry(ts, kvEntry);
    }

    public boolean isNotEmpty() {
        return strValue != null || longValue != null || doubleValue != null || booleanValue != null;
    }
}
