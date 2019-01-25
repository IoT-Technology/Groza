package com.sanshengshui.server.dao.customer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ListenableFuture;
import com.sanshengshui.server.common.data.Customer;
import com.sanshengshui.server.common.data.Tenant;
import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.dao.exception.DataValidationException;
import com.sanshengshui.server.dao.exception.IncorrectParameterException;
import com.sanshengshui.server.dao.service.DataValidator;
import com.sanshengshui.server.dao.service.Validator;
import com.sanshengshui.server.dao.tenant.TenantDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static com.sanshengshui.server.dao.service.Validator.validateId;

/**
 * @author james
 * @date 2019/1/7 6:01
 */
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private static final String PUBLIC_CUSTOMER_TITLE = "Public";
    public static final String INCORRECT_CUSTOMER_ID = "Incorrect customerId ";
    public static final String INCORRECT_TENANT_ID = "Incorrect tenantId ";

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private TenantDao tenantDao;

    @Override
    public Customer findCustomerById(CustomerId customerId) {
        log.trace("Executing findCustomerById [{}]", customerId);
        validateId(customerId, INCORRECT_CUSTOMER_ID + customerId);
        return customerDao.findById(customerId.getId());
    }

    @Override
    public Optional<Customer> findCustomerByTenantIdAndTitle(TenantId tenantId, String title) {
        log.trace("Executing findCustomerByTenantIdAndTitle [{}] [{}]", tenantId, title);
        validateId(tenantId, INCORRECT_TENANT_ID + tenantId);
        return customerDao.findCustomersByTenantIdAndTitle(tenantId.getId(), title);
    }

    @Override
    public ListenableFuture<Customer> findCustomerByIdAsync(CustomerId customerId) {
        log.trace("Executing findCustomerByIdAsync [{}]", customerId);
        validateId(customerId, INCORRECT_CUSTOMER_ID + customerId);
        return customerDao.findByIdAsync(customerId.getId());
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        log.trace("Executing saveCustomer [{}]", customer);
        customerValidator.validate(customer);
        Customer savedCustomer = customerDao.save(customer);
        return savedCustomer;
    }

    @Override
    public void deleteCustomer(CustomerId customerId) {
        //TODO customer need something to do
    }

    @Override
    public Customer findOrCreatePublicCustomer(TenantId tenantId) {
        log.trace("Executing findOrCreatePublicCustomer, tenantId [{}]", tenantId);
        Validator.validateId(tenantId, INCORRECT_CUSTOMER_ID + tenantId);
        Optional<Customer> publicCustomerOpt = customerDao.findCustomersByTenantIdAndTitle(tenantId.getId(), PUBLIC_CUSTOMER_TITLE);
        if (publicCustomerOpt.isPresent()){
            return publicCustomerOpt.get();
        } else {
            Customer publicCustomer = new Customer();
            publicCustomer.setTenantId(tenantId);
            publicCustomer.setTitle(PUBLIC_CUSTOMER_TITLE);
            try {
                publicCustomer.setAdditionalInfo(new ObjectMapper().readValue("{ \"isPublic\": true }", JsonNode.class));
            } catch (IOException e) {
                throw new IncorrectParameterException("Unable to create public customer.", e);
            }
            return customerDao.save(publicCustomer);
        }
    }

    @Override
    public void deleteCustomersByTenantId(TenantId tenantId) {

    }

    private DataValidator<Customer> customerValidator =
            new DataValidator<Customer>() {

                @Override
                protected void validateCreate(Customer customer) {
                    customerDao.findCustomersByTenantIdAndTitle(customer.getTenantId().getId(), customer.getTitle()).ifPresent(
                            c -> {
                                throw new DataValidationException("Customer with such title already exists!");
                            }
                    );
                }

                @Override
                protected void validateUpdate(Customer customer) {
                    customerDao.findCustomersByTenantIdAndTitle(customer.getTenantId().getId(), customer.getTitle()).ifPresent(
                            c -> {
                                if (!c.getId().equals(customer.getId())) {
                                    throw new DataValidationException("Customer with such title already exists!");
                                }
                            }
                    );
                }

                @Override
                protected void validateDataImpl(Customer customer) {
                    if (StringUtils.isEmpty(customer.getTitle())) {
                        throw new DataValidationException("Customer title should be specified!");
                    }
                    if (customer.getTitle().equals(PUBLIC_CUSTOMER_TITLE)) {
                        throw new DataValidationException("'Public' title for customer is system reserved!");
                    }
                    if (!StringUtils.isEmpty(customer.getEmail())) {
                        validateEmail(customer.getEmail());
                    }
                    if (customer.getTenantId() == null) {
                        throw new DataValidationException("Customer should be assigned to tenant!");
                    } else {
                        Tenant tenant = tenantDao.findById(customer.getTenantId().getId());
                        if (tenant == null) {
                            throw new DataValidationException("Customer is referencing to non-existent tenant!");
                        }
                    }
                }
            };
}
