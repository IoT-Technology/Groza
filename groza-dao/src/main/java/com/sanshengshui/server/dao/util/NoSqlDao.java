package com.sanshengshui.server.dao.util;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

/**
 * @author james mu
 * @date 18-12-11 下午2:12
 */
@ConditionalOnProperty(prefix = "database",value = "type",havingValue = "mongodb")
public @interface NoSqlDao {
}
