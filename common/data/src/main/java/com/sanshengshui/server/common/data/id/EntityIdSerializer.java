package com.sanshengshui.server.common.data.id;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author james mu
 * @date 18-12-6 下午3:32
 */
public class EntityIdSerializer extends JsonSerializer<EntityId> {

    @Override
    public void serialize(EntityId entityId, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("entityType",entityId.getEntityType().name());
        jsonGenerator.writeStringField("id",entityId.getId().toString());
        jsonGenerator.writeEndObject();
    }
}
