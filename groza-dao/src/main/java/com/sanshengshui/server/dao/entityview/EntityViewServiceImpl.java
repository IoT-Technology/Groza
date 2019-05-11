package com.sanshengshui.server.dao.entityview;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.EntitySubtype;
import com.sanshengshui.server.common.data.EntityView;
import com.sanshengshui.server.common.data.entityview.EntityViewSearchQuery;
import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.EntityId;
import com.sanshengshui.server.common.data.id.EntityViewId;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.common.data.page.TextPageData;
import com.sanshengshui.server.common.data.page.TextPageLink;
import com.sanshengshui.server.dao.entity.AbstractEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author james mu
 * @date 19-1-24 下午2:22
 */
@Slf4j
@Service
public class EntityViewServiceImpl extends AbstractEntityService implements EntityViewService{

    public static final String INCORRECT_TENANT_ID = "Incorrect tenantId ";
    public static final String INCORRECT_PAGE_LINK = "Incorrect page link ";
    public static final String INCORRECT_CUSTOMER_ID = "Incorrect customerId ";
    public static final String INCORRECT_ENTITY_VIEW_ID = "Incorrect entityViewId ";

    @Override
    public EntityView saveEntityView(EntityView entityView) {
        return null;
    }

    @Override
    public EntityView assignEntityViewToCustomer(EntityViewId entityViewId, CustomerId customerId) {
        return null;
    }

    @Override
    public EntityView unassignEntityViewFromCustomer(EntityViewId entityViewId) {
        return null;
    }

    @Override
    public void unassignCustomerEntityViews(TenantId tenantId, CustomerId customerId) {

    }

    @Override
    public EntityView findEntityViewById(EntityViewId entityViewId) {
        return null;
    }

    @Override
    public TextPageData<EntityView> findEntityViewByTenantId(TenantId tenantId, TextPageLink pageLink) {
        return null;
    }

    @Override
    public TextPageData<EntityView> findEntityViewByTenantIdAndType(TenantId tenantId, TextPageLink pageLink, String type) {
        return null;
    }

    @Override
    public TextPageData<EntityView> findEntityViewsByTenantIdAndCustomerId(TenantId tenantId, CustomerId customerId, TextPageLink pageLink) {
        return null;
    }

    @Override
    public TextPageData<EntityView> findEntityViewsByTenantIdAndCustomerIdAndType(TenantId tenantId, CustomerId customerId, TextPageLink pageLink, String type) {
        return null;
    }

    @Override
    public ListenableFuture<List<EntityView>> findEntityViewsByQuery(EntityViewSearchQuery query) {
        return null;
    }

    @Override
    public ListenableFuture<EntityView> findEntityViewByIdAsync(EntityViewId entityViewId) {
        return null;
    }

    @Override
    public ListenableFuture<List<EntityView>> findEntityViewsByTenantIdAndEntityIdAsync(TenantId tenantId, EntityId entityId) {
        return null;
    }

    @Override
    public void deleteEntityView(EntityViewId entityViewId) {

    }

    @Override
    public void deleteEntityViewsByTenantId(TenantId tenantId) {

    }

    @Override
    public ListenableFuture<List<EntitySubtype>> findEntityViewTypesByTenantId(TenantId tenantId) {
        return null;
    }
}
