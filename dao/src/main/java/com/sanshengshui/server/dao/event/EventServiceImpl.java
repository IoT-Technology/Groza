package com.sanshengshui.server.dao.event;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.Event;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.common.data.page.TimePageData;
import com.sanshengshui.server.common.data.page.TimePageLink;
import com.sanshengshui.server.dao.exception.DataValidationException;
import com.sanshengshui.server.dao.service.DataValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author james mu
 * @date 19-2-20 下午4:35
 * @description
 */
@Slf4j
@Service
public class EventServiceImpl implements EventService{

    @Autowired
    public EventDao eventDao;

    @Override
    public Event save(Event event) {
        eventValidator.validate(event);
        return eventDao.save(event);
    }

    @Override
    public ListenableFuture<Event> saveAsync(Event event) {
        eventValidator.validate(event);
        return eventDao.saveAsync(event);
    }

    @Override
    public Optional<Event> saveIfNotExists(Event event) {
        eventValidator.validate(event);
        if (StringUtils.isEmpty(event.getUid())) {
            throw new DataValidationException("Event uid should be specified!.");
        }
        return eventDao.saveIfNotExists(event);
    }

    @Override
    public Optional<Event> findEvent(TenantId tenantId, EntityId entityId, String eventType, String eventUid) {
        if (tenantId == null) {
            throw new DataValidationException("Tenant id should be specified!.");
        }
        if (entityId == null) {
            throw new DataValidationException("Entity id should be specified!.");
        }
        if (StringUtils.isEmpty(eventType)) {
            throw new DataValidationException("Event type should be specified!.");
        }
        if (StringUtils.isEmpty(eventUid)) {
            throw new DataValidationException("Event uid should be specified!.");
        }
        Event event = eventDao.findEvent(tenantId.getId(), entityId, eventType, eventUid);
        return event != null ? Optional.of(event) : Optional.empty();
    }

    @Override
    public TimePageData<Event> findEvents(TenantId tenantId, EntityId entityId, TimePageLink pageLink) {
        List<Event> events = eventDao.findEvents(tenantId.getId(), entityId, pageLink);
        return new TimePageData<>(events, pageLink);
    }

    @Override
    public TimePageData<Event> findEvents(TenantId tenantId, EntityId entityId, String eventType, TimePageLink pageLink) {
        List<Event> events = eventDao.findEvents(tenantId.getId(), entityId, eventType, pageLink);
        return new TimePageData<>(events, pageLink);
    }

    @Override
    public List<Event> findLatestEvents(TenantId tenantId, EntityId entityId, String eventType, int limit) {
        return eventDao.findLatestEvents(tenantId.getId(), entityId, eventType, limit);
    }

    private DataValidator<Event> eventValidator =
            new DataValidator<Event>() {
                @Override
                protected void validateDataImpl(Event event) {
                    if (event.getEntityId() == null) {
                        throw new DataValidationException("Entity id should be specified!.");
                    }
                    if (StringUtils.isEmpty(event.getType())) {
                        throw new DataValidationException("Event type should be specified!.");
                    }
                    if (event.getBody() == null) {
                        throw new DataValidationException("Event body should be specified!.");
                    }
                }
            };
}
