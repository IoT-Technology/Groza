package com.sanshengshui.server.dao.sql.rule;

import com.sanshengshui.server.dao.model.sql.RuleChainEntity;
import com.sanshengshui.server.dao.util.SqlDao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author james mu
 * @date 19-2-25 上午11:03
 * @description
 */
@SqlDao
public interface RuleChainRepository extends CrudRepository<RuleChainEntity, String> {

    @Query("SELECT rc FROM RuleChainEntity rc WHERE rc.tenantId = :tenantId " +
            "AND LOWER(rc.searchText) LIKE LOWER(CONCAT(:searchText, '%')) " +
            "AND rc.id > :idOffset ORDER BY rc.id")
    List<RuleChainEntity> findByTenantId(@Param("tenantId") String tenantId,
                                         @Param("searchText") String searchText,
                                         @Param("idOffset") String idOffset,
                                         Pageable pageable);

}
