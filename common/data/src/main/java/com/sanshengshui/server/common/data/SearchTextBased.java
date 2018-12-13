package com.sanshengshui.server.common.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sanshengshui.server.common.data.id.UUIDBased;

/**
 * @author james mu
 * @date 18-12-13 下午4:38
 */
public abstract class SearchTextBased<I extends UUIDBased> extends BaseData<I> {

    private static final long serialVersionUID = -539812997348227609L;

    public SearchTextBased() {
        super();
    }

    public SearchTextBased(I id) {
        super(id);
    }

    public SearchTextBased(SearchTextBased<I> searchTextBased) {
        super(searchTextBased);
    }

    @JsonIgnore
    public abstract String getSearchText();
}
