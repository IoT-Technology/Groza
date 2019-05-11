package com.sanshengshui.server.dao.sql.audit;

import com.sanshengshui.server.dao.model.sql.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author james mu
 * @date 19-2-1 上午10:44
 * @description
 */

public interface AuditLogRepository extends CrudRepository<AuditLogEntity, String>, JpaSpecificationExecutor<AuditLogEntity> {

}
