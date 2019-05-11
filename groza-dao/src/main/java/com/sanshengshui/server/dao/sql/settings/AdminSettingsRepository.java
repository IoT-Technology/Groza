package com.sanshengshui.server.dao.sql.settings;

import com.sanshengshui.server.dao.model.sql.AdminSettingsEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author james mu
 * @date 19-2-28 下午1:21
 * @description
 */
public interface AdminSettingsRepository extends CrudRepository<AdminSettingsEntity, String> {

    AdminSettingsEntity findByKey(String key);
}
