package com.sanshengshui.server.dao.sql;

import com.sanshengshui.server.dao.model.BaseEntity;
import com.sanshengshui.server.dao.model.SearchTextEntity;

/**
 * @author james mu
 * @date 19-1-4 下午3:48
 */
public abstract class JpaAbstractSearchTextDao <E extends BaseEntity<D>,D> extends JpaAbstractDao<E,D> {

    @Override
    protected void setSearchText(E entity){
        ((SearchTextEntity) entity).setSearchText(((SearchTextEntity) entity).getSearchTextSource().toLowerCase());
    }

}
