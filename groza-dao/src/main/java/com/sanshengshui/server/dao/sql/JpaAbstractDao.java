package com.sanshengshui.server.dao.sql;

import com.datastax.driver.core.utils.UUIDs;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.dao.Dao;
import com.sanshengshui.server.dao.DaoUtil;
import com.sanshengshui.server.dao.model.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.sanshengshui.server.common.data.UUIDConverter.fromTimeUUID;

/**
 * @author james mu
 * @date 18-12-24 上午11:06
 */
@Slf4j
public abstract class JpaAbstractDao<E extends BaseEntity<D>,D>
        extends JpaAbstractDaoListeningExecutorService
        implements Dao<D> {

    protected abstract Class<E> getEntityClass();

    protected abstract CrudRepository<E,String> getCrudRepository();

    protected void setSearchText(E entity){}

    @Override
    public List<D> find() {
        List<E> entities = Lists.newArrayList(getCrudRepository().findAll());
        return DaoUtil.convertDataList(entities);
    }

    @Override
    public D findById(UUID key) {
        log.debug("Get entity by key {}", key);
        E entity = getCrudRepository().findById(fromTimeUUID(key)).get();
        return DaoUtil.getData(entity);
    }

    @Override
    public ListenableFuture<D> findByIdAsync(UUID id) {
        log.debug("Get entity by key async {}", id);
        return service.submit(()-> DaoUtil.getData(getCrudRepository().findById(fromTimeUUID(id)).get()));
    }

    @Override
    @Transactional
    public D save(D domain) {
        E entity;
        try {
            entity = getEntityClass().getConstructor(domain.getClass()).newInstance(domain);
        }catch (Exception e){
            log.error("Can't create entity for domain object {}",domain,e);
            throw new IllegalArgumentException("Can't create entity for domain object {" + domain + "}", e);
        }
        setSearchText(entity);
        log.debug("Saving entity {}", entity);
        if (entity.getId() == null) {
            entity.setId(UUIDs.timeBased());
        }
        entity = getCrudRepository().save(entity);
        return DaoUtil.getData(entity);
    }

    @Override
    @Transactional
    public boolean removeById(UUID id) {
        String key = fromTimeUUID(id);
        getCrudRepository().deleteById(key);
        log.debug("Remove request: {}", key);
        return getCrudRepository().findById(key).isPresent();
    }
}
