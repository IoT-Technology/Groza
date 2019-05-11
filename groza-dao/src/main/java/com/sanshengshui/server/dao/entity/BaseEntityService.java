package com.sanshengshui.server.dao.entity;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.id.EntityId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author james mu
 * @date 19-1-22 上午11:02
 */
@Slf4j
@Service
public class BaseEntityService extends AbstractEntityService implements EntityService{

    @Override
    public ListenableFuture<String> fetchEntityNameAsync(EntityId entityId) {
        return null;
    }

    @Override
    public void deleteEntityRelations(EntityId entityId) {

    }
}
