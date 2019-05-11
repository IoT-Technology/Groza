package com.sanshengshui.server.common.data.page;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanshengshui.server.common.data.BaseData;
import com.sanshengshui.server.common.data.id.UUIDBased;

import java.util.List;
import java.util.UUID;

/**
 * @author james mu
 * @date 19-1-8 上午11:20
 */
public class TimePageData<T extends BaseData<? extends UUIDBased>> {

    private final List<T> data;
    private final TimePageLink nextPageLink;
    private final boolean hasNext;

    public TimePageData(List<T> data, TimePageLink pageLink) {
        super();
        this.data = data;
        int limit = pageLink.getLimit();
        if (data != null && data.size() == limit) {
            int index = data.size() - 1;
            UUID idOffset = data.get(index).getId().getId();
            nextPageLink = new TimePageLink(limit, pageLink.getStartTime(), pageLink.getEndTime(), pageLink.isAscOrder(), idOffset);
            hasNext = true;
        } else {
            nextPageLink = null;
            hasNext = false;
        }
    }

    @JsonCreator
    public TimePageData(@JsonProperty("data") List<T> data,
                        @JsonProperty("nextPageLink") TimePageLink nextPageLink,
                        @JsonProperty("hasNext") boolean hasNext) {
        this.data = data;
        this.nextPageLink = nextPageLink;
        this.hasNext = hasNext;
    }

    public List<T> getData() {
        return data;
    }

    @JsonProperty("hasNext")
    public boolean hasNext() {
        return hasNext;
    }

    public TimePageLink getNextPageLink() {
        return nextPageLink;
    }
}
