package com.sanshengshui.server.common.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.id.EventId;
import com.sanshengshui.server.common.data.id.TenantId;
import lombok.Data;

/**
 * @author james mu
 * @date 19-1-8 上午11:04
 */
@Data
public class Event extends BaseData<EventId> {

    private TenantId tenantId;
    private String type;
    private String uid;
    private EntityId entityId;
    private transient JsonNode body;

    public Event(){
        super();
    }

    public Event(EventId id) {
        super(id);
    }

    public Event(Event event) {
        super(event);
    }
}
