package com.sanshengshui.server.dao.settings;

import com.sanshengshui.server.common.data.AdminSettings;
import com.sanshengshui.server.dao.Dao;

/**
 * @author james mu
 * @date 19-2-28 下午1:26
 * @description
 */
public interface AdminSettingsDao extends Dao<AdminSettings> {
    /**
     * Save or update admin settings object
     *
     * @param adminSettings the admin settings object
     * @return saved admin settings object
     */
    AdminSettings save(AdminSettings adminSettings);

    /**
     * Find admin settings by key
     *
     * @param key the key
     * @return the admin settings object
     */
    AdminSettings findByKey(String key);
}
