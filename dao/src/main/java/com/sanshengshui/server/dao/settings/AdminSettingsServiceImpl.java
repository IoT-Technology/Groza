package com.sanshengshui.server.dao.settings;

import com.sanshengshui.server.common.data.AdminSettings;
import com.sanshengshui.server.common.data.id.AdminSettingsId;
import com.sanshengshui.server.dao.exception.DataValidationException;
import com.sanshengshui.server.dao.service.DataValidator;
import com.sanshengshui.server.dao.service.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author james mu
 * @date 19-2-28 下午1:40
 * @description
 */
@Service
@Slf4j
public class AdminSettingsServiceImpl implements AdminSettingsService{

    @Autowired
    private AdminSettingsDao adminSettingsDao;

    @Override
    public AdminSettings findAdminSettingsById(AdminSettingsId adminSettingsId) {
        log.trace("Executing findAdminSettingsById [{}]", adminSettingsId);
        Validator.validateId(adminSettingsId, "Incorrect adminSettingsId " + adminSettingsId);
        return  adminSettingsDao.findById(adminSettingsId.getId());
    }

    @Override
    public AdminSettings findAdminSettingsByKey(String key) {
        log.trace("Executing findAdminSettingsByKey [{}]", key);
        Validator.validateString(key, "Incorrect key " + key);
        return adminSettingsDao.findByKey(key);
    }

    @Override
    public AdminSettings saveAdminSettings(AdminSettings adminSettings) {
        log.trace("Executing saveAdminSettings [{}]", adminSettings);
        adminSettingsValidator.validate(adminSettings);
        return adminSettingsDao.save(adminSettings);
    }

    private DataValidator<AdminSettings> adminSettingsValidator =
            new DataValidator<AdminSettings>() {

                @Override
                protected void validateCreate(AdminSettings adminSettings) {
                    AdminSettings existentAdminSettingsWithKey = findAdminSettingsByKey(adminSettings.getKey());
                    if (existentAdminSettingsWithKey != null) {
                        throw new DataValidationException("Admin settings with such name already exists!");
                    }
                }

                @Override
                protected void validateUpdate(AdminSettings adminSettings) {
                    AdminSettings existentAdminSettings = findAdminSettingsById(adminSettings.getId());
                    if (existentAdminSettings != null) {
                        if (!existentAdminSettings.getKey().equals(adminSettings.getKey())) {
                            throw new DataValidationException("Changing key of admin settings entry is prohibited!");
                        }
                        validateJsonStructure(existentAdminSettings.getJsonValue(), adminSettings.getJsonValue());
                    }
                }


                @Override
                protected void validateDataImpl(AdminSettings adminSettings) {
                    if (StringUtils.isEmpty(adminSettings.getKey())) {
                        throw new DataValidationException("Key should be specified!");
                    }
                    if (adminSettings.getJsonValue() == null) {
                        throw new DataValidationException("Json value should be specified!");
                    }
                }
            };
}
