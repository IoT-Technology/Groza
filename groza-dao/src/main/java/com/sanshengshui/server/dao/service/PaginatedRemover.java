package com.sanshengshui.server.dao.service;

import com.sanshengshui.server.common.data.id.IdBased;
import com.sanshengshui.server.common.data.page.TextPageLink;

import java.util.List;
import java.util.UUID;

/**
 * @author james mu
 * @date 19-1-24 下午3:57
 */
public abstract class PaginatedRemover<I, D extends IdBased<?>> {

    private static final int DEFAULT_LIMIT = 100;

    public void removeEntities(I id) {
        TextPageLink pageLink = new TextPageLink(DEFAULT_LIMIT);
        boolean hasNext = true;
        while (hasNext) {
            List<D> entities = findEntities(id, pageLink);
            for (D entity : entities) {
                removeEntity(entity);
            }
            hasNext = entities.size() == pageLink.getLimit();
            if (hasNext) {
                int index = entities.size() - 1;
                UUID idOffset = entities.get(index).getUuidId();
                pageLink.setIdOffset(idOffset);
            }
        }
    }

    protected abstract List<D> findEntities(I id, TextPageLink pageLink);

    protected abstract void removeEntity(D entity);

}
