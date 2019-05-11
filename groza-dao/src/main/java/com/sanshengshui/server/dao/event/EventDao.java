package com.sanshengshui.server.dao.event;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.Event;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.page.TimePageLink;
import com.sanshengshui.server.dao.Dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author james mu
 * @date 19-1-8 上午11:03
 */
public interface EventDao extends Dao<Event> {

    /**
     * Save or update event object
     *
     * @param event the event object
     * @return saved event object
     */
    Event save(Event event);

    /**
     * Save or update event object async
     *
     * @param event the event object
     * @return saved event object future
     */
    ListenableFuture<Event> saveAsync(Event event);

    /**
     * Save event object if it is not yet saved
     *
     * @param event the event object
     * @return saved event object
     */
    Optional<Event> saveIfNotExists(Event event);

    /**
     * Find event by tenantId, entityId and eventUid.
     *
     * @param tenantId the tenantId
     * @param entityId the entityId
     * @param eventType the eventType
     * @param eventUid the eventUid
     * @return the event
     */
    Event findEvent(UUID tenantId, EntityId entityId, String eventType, String eventUid);

    /**
     * Find events by tenantId, entityId and pageLink.
     *
     * @param tenantId the tenantId
     * @param entityId the entityId
     * @param pageLink the pageLink
     * @return the event list
     */
    List<Event> findEvents(UUID tenantId, EntityId entityId, TimePageLink pageLink);

    /**
     * Find events by tenantId, entityId, eventType and pageLink.
     *
     * @param tenantId the tenantId
     * @param entityId the entityId
     * @param eventType the eventType
     * @param pageLink the pageLink
     * @return the event list
     */
    List<Event> findEvents(UUID tenantId, EntityId entityId, String eventType, TimePageLink pageLink);

    /**
     * Find latest events by tenantId, entityId and eventType.
     *
     * @param tenantId the tenantId
     * @param entityId the entityId
     * @param eventType the eventType
     * @param limit the limit
     * @return the event list
     */
    List<Event> findLatestEvents(UUID tenantId, EntityId entityId, String eventType, int limit);
}
