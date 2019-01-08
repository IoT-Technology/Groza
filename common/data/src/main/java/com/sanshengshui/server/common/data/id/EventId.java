package com.sanshengshui.server.common.data.id;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * @author james mu
 * @date 19-1-8 上午11:04
 */
public class EventId extends UUIDBased {

    public static final long serialVersionUID = 1L;

    public EventId(@JsonProperty("id") UUID id){
        super(id);
    }

    public static EventId fromString(String eventId){
        return new EventId(UUID.fromString(eventId));
    }
}
