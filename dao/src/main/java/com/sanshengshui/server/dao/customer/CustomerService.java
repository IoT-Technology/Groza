package com.sanshengshui.server.dao.customer;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.Customer;
import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.TenantId;

import java.util.Optional;

/**
 * @author james mu
 * @date 19-1-3 下午4:12
 */
public interface CustomerService {

    Customer findCustomerById(CustomerId customerId);

    Optional<Customer> findCustomerByTenantIdAndTitle(TenantId tenantId, String title);

    ListenableFuture<Customer> findCustomerByIdAsync(CustomerId customerId);

    Customer saveCustomer(Customer customer);

    void deleteCustomer(CustomerId customerId);

    Customer findOrCreatePublicCustomer(TenantId tenantId);

    void deleteCustomersByTenantId(TenantId tenantId);
}
