package com.sanshengshui.server.dao.sql.customer;

import com.sanshengshui.server.common.data.Customer;
import com.sanshengshui.server.common.data.UUIDConverter;
import com.sanshengshui.server.common.data.page.TextPageLink;
import com.sanshengshui.server.dao.DaoUtil;
import com.sanshengshui.server.dao.customer.CustomerDao;
import com.sanshengshui.server.dao.model.sql.CustomerEntity;
import com.sanshengshui.server.dao.sql.JpaAbstractSearchTextDao;
import com.sanshengshui.server.dao.util.SqlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.sanshengshui.server.dao.model.ModelConstants.NULL_UUID_STR;

/**
 * @author james
 * @date 2019/1/7 6:46
 */
@Component
@SqlDao
public class JpaCustomerDao extends JpaAbstractSearchTextDao<CustomerEntity, Customer> implements CustomerDao {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findCustomersByTenantId(UUID tenantId, TextPageLink pageLink) {
        return DaoUtil.convertDataList(customerRepository.findByTenantId(
                UUIDConverter.fromTimeUUID(tenantId),
                Objects.toString(pageLink.getTextSearch(), ""),
                pageLink.getIdOffset() == null ? NULL_UUID_STR : UUIDConverter.fromTimeUUID(pageLink.getIdOffset()),
                new PageRequest(0, pageLink.getLimit())));
    }

    @Override
    public Optional<Customer> findCustomersByTenantIdAndTitle(UUID tenantId, String title) {
        Customer customer = DaoUtil.getData(customerRepository.findByTenantIdAndTitle(UUIDConverter.fromTimeUUID(tenantId), title));
        return Optional.ofNullable(customer);
    }

    @Override
    protected Class<CustomerEntity> getEntityClass() {
        return CustomerEntity.class;
    }

    @Override
    protected CrudRepository<CustomerEntity, String> getCrudRepository() {
        return customerRepository;
    }
}
