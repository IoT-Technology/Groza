package com.sanshengshui.server.dao.settings;

import com.sanshengshui.server.common.data.AdminSettings;
import com.sanshengshui.server.common.data.id.AdminSettingsId;

/**
 * @author james mu
 * @date 19-2-28 下午1:38
 * @description
 */
public interface AdminSettingsService {

    AdminSettings findAdminSettingsById(AdminSettingsId adminSettingsId);

    AdminSettings findAdminSettingsByKey(String key);

    AdminSettings saveAdminSettings(AdminSettings adminSettings);
}
