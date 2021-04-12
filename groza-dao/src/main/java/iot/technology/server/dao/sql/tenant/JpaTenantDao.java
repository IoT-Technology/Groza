package iot.technology.server.dao.sql.tenant;

import iot.technology.server.common.data.Tenant;
import iot.technology.server.common.data.UUIDConverter;
import iot.technology.server.common.data.page.TextPageLink;
import iot.technology.server.dao.DaoUtil;
import iot.technology.server.dao.model.sql.TenantEntity;
import iot.technology.server.dao.sql.JpaAbstractSearchTextDao;
import iot.technology.server.dao.tenant.TenantDao;
import iot.technology.server.dao.util.SqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static iot.technology.server.dao.model.ModelConstants.NULL_UUID_STR;

/**
 * @author james mu
 * @date 19-1-4 下午5:14
 */
@Component
@SqlDao
public class JpaTenantDao extends JpaAbstractSearchTextDao<TenantEntity, Tenant> implements TenantDao {

    @Autowired
    private TenantRepository tenantRepository;

    @Override
    protected Class<TenantEntity> getEntityClass() {
        return TenantEntity.class;
    }

    @Override
    protected CrudRepository<TenantEntity, String> getCrudRepository() {
        return tenantRepository;
    }

    @Override
    public List<Tenant> findTenantsByRegion(String region, TextPageLink pageLink) {
        return DaoUtil.convertDataList(tenantRepository
                .findByRegionNextPage(
                        region,
                        Objects.toString(pageLink.getTextSearch(), ""),
                        pageLink.getIdOffset() == null ? NULL_UUID_STR : UUIDConverter.fromTimeUUID(pageLink.getIdOffset()),
                        new PageRequest(0, pageLink.getLimit())));
    }
}
