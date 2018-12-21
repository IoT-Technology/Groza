package com.sanshengshui.server.dao.model;

import com.datastax.driver.core.utils.UUIDs;
import com.sanshengshui.server.common.data.UUIDConverter;

import java.util.UUID;

/**
 * @author james mu
 * @date 18-12-21 下午3:17
 */
public class ModelConstants {

    private ModelConstants(){
    }

    public static final UUID NULL_UUID = UUIDs.startOf(0);
    public static final String NULL_UUID_STR = UUIDConverter.fromTimeUUID(NULL_UUID);

    /**
     * Generic constants.
     */
    public static final String ID_PROPERTY = "id";
    public static final String USER_ID_PROPERTY = "user_id";
    public static final String TENANT_ID_PROPERTY = "tenant_id";
    public static final String CUSTOMER_ID_PROPERTY = "customer_id";
    public static final String DEVICE_ID_PROPERTY = "device_id";
    public static final String TITLE_PROPERTY = "title";
    public static final String ALIAS_PROPERTY = "alias";
    public static final String SEARCH_TEXT_PROPERTY = "search_text";
    public static final String ADDITIONAL_INFO_PROPERTY = "additional_info";
    public static final String ENTITY_TYPE_PROPERTY = "entity_type";

    public static final String ENTITY_TYPE_COLUMN = ENTITY_TYPE_PROPERTY;
    public static final String ENTITY_ID_COLUMN = "entity_id";
    public static final String ATTRIBUTE_TYPE_COLUMN = "attribute_type";
    public static final String ATTRIBUTE_KEY_COLUMN = "attribute_key";
    public static final String LAST_UPDATE_TS_COLUMN = "last_update_ts";

    /**
     * Main names of cassandra key-value columns storage.
     */
    public static final String BOOLEAN_VALUE_COLUMN = "bool_v";
    public static final String STRING_VALUE_COLUMN = "str_v";
    public static final String LONG_VALUE_COLUMN = "long_v";
    public static final String DOUBLE_VALUE_COLUMN = "dbl_v";

    /**
     * Cassandra device constants.
     */
    public static final String DEVICE_COLUMN_FAMILY_NAME = "device";
    public static final String DEVICE_TENANT_ID_PROPERTY = TENANT_ID_PROPERTY;
    public static final String DEVICE_CUSTOMER_ID_PROPERTY = CUSTOMER_ID_PROPERTY;
    public static final String DEVICE_NAME_PROPERTY = "name";
    public static final String DEVICE_TYPE_PROPERTY = "type";
    public static final String DEVICE_ADDITIONAL_INFO_PROPERTY = ADDITIONAL_INFO_PROPERTY;

    public static final String DEVICE_BY_TENANT_AND_SEARCH_TEXT_COLUMN_FAMILY_NAME = "device_by_tenant_and_search_text";
    public static final String DEVICE_BY_TENANT_BY_TYPE_AND_SEARCH_TEXT_COLUMN_FAMILY_NAME = "device_by_tenant_by_type_and_search_text";
    public static final String DEVICE_BY_CUSTOMER_AND_SEARCH_TEXT_COLUMN_FAMILY_NAME = "device_by_customer_and_search_text";
    public static final String DEVICE_BY_CUSTOMER_BY_TYPE_AND_SEARCH_TEXT_COLUMN_FAMILY_NAME = "device_by_customer_by_type_and_search_text";
    public static final String DEVICE_BY_TENANT_AND_NAME_VIEW_NAME = "device_by_tenant_and_name";
    public static final String DEVICE_TYPES_BY_TENANT_VIEW_NAME = "device_types_by_tenant";

    /**
     * Cassandra device_credentials constants.
     */
    public static final String DEVICE_CREDENTIALS_COLUMN_FAMILY_NAME = "device_credentials";
    public static final String DEVICE_CREDENTIALS_DEVICE_ID_PROPERTY = DEVICE_ID_PROPERTY;
    public static final String DEVICE_CREDENTIALS_CREDENTIALS_TYPE_PROPERTY = "credentials_type";
    public static final String DEVICE_CREDENTIALS_CREDENTIALS_ID_PROPERTY = "credentials_id";
    public static final String DEVICE_CREDENTIALS_CREDENTIALS_VALUE_PROPERTY = "credentials_value";

    public static final String DEVICE_CREDENTIALS_BY_DEVICE_COLUMN_FAMILY_NAME = "device_credentials_by_device";
    public static final String DEVICE_CREDENTIALS_BY_CREDENTIALS_ID_COLUMN_FAMILY_NAME = "device_credentials_by_credentials_id";
}
