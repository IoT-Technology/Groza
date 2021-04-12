package iot.technology.server.dao.settings;

import iot.technology.server.common.data.AdminSettings;
import iot.technology.server.common.data.id.AdminSettingsId;

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
