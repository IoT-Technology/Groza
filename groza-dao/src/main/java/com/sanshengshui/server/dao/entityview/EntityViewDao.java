package com.sanshengshui.server.dao.entityview;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.EntitySubtype;
import com.sanshengshui.server.common.data.EntityView;
import com.sanshengshui.server.common.data.page.TextPageLink;
import com.sanshengshui.server.dao.Dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author james mu
 * @date 19-1-24 下午1:38
 */
public interface EntityViewDao extends Dao<EntityView> {

    /**
     * Save or update device object
     *
     * @param entityView the entity-view object
     * @return saved entity-view object
     */
    EntityView save(EntityView entityView);

    /**
     * Find entity views by tenantId and page link.
     *
     * @param tenantId the tenantId
     * @param pageLink the page link
     * @return the list of entity view objects
     */
    List<EntityView> findEntityViewsByTenantId(UUID tenantId, TextPageLink pageLink);

    /**
     * Find entity views by tenantId, type and page link.
     *
     * @param tenantId the tenantId
     * @param type the type
     * @param pageLink the page link
     * @return the list of entity view objects
     */
    List<EntityView> findEntityViewsByTenantIdAndType(UUID tenantId, String type, TextPageLink pageLink);

    /**
     * Find entity views by tenantId and entity view name.
     *
     * @param tenantId the tenantId
     * @param name the entity view name
     * @return the optional entity view object
     */
    Optional<EntityView> findEntityViewByTenantIdAndName(UUID tenantId, String name);

    /**
     * Find entity views by tenantId, customerId and page link.
     *
     * @param tenantId the tenantId
     * @param customerId the customerId
     * @param pageLink the page link
     * @return the list of entity view objects
     */
    List<EntityView> findEntityViewsByTenantIdAndCustomerId(UUID tenantId,
                                                            UUID customerId,
                                                            TextPageLink pageLink);

    /**
     * Find entity views by tenantId, customerId, type and page link.
     *
     * @param tenantId the tenantId
     * @param customerId the customerId
     * @param type the type
     * @param pageLink the page link
     * @return the list of entity view objects
     */
    List<EntityView> findEntityViewsByTenantIdAndCustomerIdAndType(UUID tenantId,
                                                                   UUID customerId,
                                                                   String type,
                                                                   TextPageLink pageLink);

    ListenableFuture<List<EntityView>> findEntityViewsByTenantIdAndEntityIdAsync(UUID tenantId, UUID entityId);

    /**
     * Find tenants entity view types.
     *
     * @return the list of tenant entity view type objects
     */
    ListenableFuture<List<EntitySubtype>> findTenantEntityViewTypesAsync(UUID tenantId);
}
