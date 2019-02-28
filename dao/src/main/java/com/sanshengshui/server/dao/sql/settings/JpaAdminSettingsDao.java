package com.sanshengshui.server.dao.sql.settings;

import com.sanshengshui.server.common.data.AdminSettings;
import com.sanshengshui.server.dao.DaoUtil;
import com.sanshengshui.server.dao.model.sql.AdminSettingsEntity;
import com.sanshengshui.server.dao.settings.AdminSettingsDao;
import com.sanshengshui.server.dao.sql.JpaAbstractDao;
import com.sanshengshui.server.dao.util.SqlDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * @author james mu
 * @date 19-2-28 下午1:25
 * @description
 */
@Component
@Slf4j
@SqlDao
public class JpaAdminSettingsDao extends JpaAbstractDao<AdminSettingsEntity, AdminSettings> implements AdminSettingsDao {

    @Autowired
    private AdminSettingsRepository adminSettingsRepository;

    @Override
    public AdminSettings findByKey(String key) {
        return DaoUtil.getData(adminSettingsRepository.findByKey(key));
    }

    @Override
    protected Class<AdminSettingsEntity> getEntityClass() {
        return AdminSettingsEntity.class;
    }

    @Override
    protected CrudRepository<AdminSettingsEntity, String> getCrudRepository() {
        return adminSettingsRepository;
    }
}
