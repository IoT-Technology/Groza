package com.sanshengshui.server.dao.customer;

import com.sanshengshui.server.common.data.Customer;
import com.sanshengshui.server.common.data.page.TextPageLink;
import com.sanshengshui.server.dao.Dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author james mu
 * @date 19-1-3 下午3:56
 */
public interface CustomerDao extends Dao<Customer> {

    /**
     * Save or update customer object
     *
     * @param customer the customer object
     * @return saved customer object
     */
    Customer save(Customer customer);

    /**
     * Find customers by tenant id
     *
     * @param tenantId the tenant id
     * @return the list of customer objects
     */
    List<Customer> findCustomersByTenantId(UUID tenantId, TextPageLink pageLink);

    /**
     * Find customers by tenantId and customer title.
     *
     * @param tenantId the tenant id
     * @param title the customer title
     * @return the optional customer object
     */
    Optional<Customer> findCustomersByTenantIdAndTitle(UUID tenantId, String title);
}
