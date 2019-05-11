package com.sanshengshui.server.common.data;

import com.sanshengshui.server.common.data.id.CustomerId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author james mu
 * @date 19-1-3 下午3:53
 */
@AllArgsConstructor
public class ShortCustomerInfo {

    @Getter @Setter
    private CustomerId customerId;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private boolean isPublic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShortCustomerInfo that = (ShortCustomerInfo) o;

        return customerId.equals(that.customerId);

    }

    @Override
    public int hashCode() {
        return customerId.hashCode();
    }
}
