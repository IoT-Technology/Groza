package com.sanshengshui.server.common.data.alarm;

import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.page.TimePageLink;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author james mu
 * @date 19-1-3 下午5:18
 */
@Data
@Builder
@AllArgsConstructor
public class AlarmQuery {

    private EntityId affectedEntityId;
    private TimePageLink pageLink;
    private AlarmSearchStatus searchStatus;
    private AlarmStatus status;
    private Boolean fetchOriginator;
}
