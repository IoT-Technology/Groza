package com.sanshengshui.server.dao.customer;

import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.Customer;
import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.TenantId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author james
 * @date 2019/1/7 6:01
 */
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Override
    public Customer findCustomerById(CustomerId customerId) {
        return null;
    }

    @Override
    public Optional<Customer> findCustomerByTenantIdAndTitle(TenantId tenantId, String title) {
        return Optional.empty();
    }

    @Override
    public ListenableFuture<Customer> findCustomerByIdAsync(CustomerId customerId) {
        return null;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return null;
    }

    @Override
    public void deleteCustomer(CustomerId customerId) {

    }

    @Override
    public Customer findOrCreatePublicCustomer(TenantId tenantId) {
        return null;
    }

    @Override
    public void deleteCustomersByTenantId(TenantId tenantId) {

    }
}
