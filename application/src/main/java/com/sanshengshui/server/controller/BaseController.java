package com.sanshengshui.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanshengshui.server.common.data.Customer;
import com.sanshengshui.server.common.data.asset.Asset;
import com.sanshengshui.server.common.data.exception.GrozaErrorCode;
import com.sanshengshui.server.common.data.exception.GrozaException;
import com.sanshengshui.server.common.data.id.AssetId;
import com.sanshengshui.server.common.data.id.CustomerId;
import com.sanshengshui.server.common.data.id.TenantId;
import com.sanshengshui.server.common.data.page.TextPageLink;
import com.sanshengshui.server.common.data.security.Authority;
import com.sanshengshui.server.dao.asset.AssetService;
import com.sanshengshui.server.dao.customer.CustomerService;
import com.sanshengshui.server.dao.exception.DataValidationException;
import com.sanshengshui.server.dao.exception.IncorrectParameterException;
import com.sanshengshui.server.dao.model.ModelConstants;
import com.sanshengshui.server.dao.tenant.TenantService;
import com.sanshengshui.server.service.security.model.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.mail.MessagingException;
import java.util.UUID;

import static com.sanshengshui.server.dao.service.Validator.validateId;


/**
 * @author james mu
 * @date 19-1-24 下午4:23
 */
@Slf4j
public abstract class BaseController {

    public static final String INCORRECT_TENANT_ID = "Incorrect tenantId ";
    public static final String YOU_DON_T_HAVE_PERMISSION_TO_PERFORM_THIS_OPERATION = "You don't have permission to perform this operation!";

    private static final ObjectMapper json = new ObjectMapper();

    @Autowired
    protected TenantService tenantService;

    @Autowired
    protected AssetService assetService;

    @Autowired
    protected CustomerService customerService;

    <T> T checkNotNull(T reference) throws GrozaException {
        if (reference == null) {
            throw new GrozaException("Requested item wasn't found!", GrozaErrorCode.ITEM_NOT_FOUND);
        }
        return reference;
    }

    void checkParameter(String name, String param) throws GrozaException {
        if (StringUtils.isEmpty(param)){
            throw new GrozaException("Parameter '" + name + "' can't be empty!", GrozaErrorCode.BAD_REQUEST_PARAMS);
        }
    }

    Asset checkAssetId(AssetId assetId) throws GrozaException {
        try {
            validateId(assetId, "Incorrect assetId " + assetId);
            Asset asset = assetService.findAssetById(assetId);
            checkAsset(asset);
            return asset;
        } catch (Exception e) {
            throw handleException(e, false);
        }
    }

    protected void checkAsset(Asset asset) throws GrozaException {
        checkNotNull(asset);
        checkTenantId(asset.getTenantId());
        if (asset.getCustomerId() != null && !asset.getCustomerId().getId().equals(ModelConstants.NULL_UUID)) {
            checkCustomerId(asset.getCustomerId());
        }
    }

    Customer checkCustomerId(CustomerId customerId) throws GrozaException {
        try {
            validateId(customerId, "Incorrect customerId " + customerId);
            SecurityUser authUser = getCurrentUser();
            if (authUser.getAuthority() == Authority.SYS_ADMIN ||
                    (authUser.getAuthority() != Authority.TENANT_ADMIN &&
                            (authUser.getCustomerId() == null || !authUser.getCustomerId().equals(customerId)))) {
                throw new GrozaException(YOU_DON_T_HAVE_PERMISSION_TO_PERFORM_THIS_OPERATION,
                        GrozaErrorCode.PERMISSION_DENIED);
            }
            Customer customer = customerService.findCustomerById(customerId);
            checkCustomer(customer);
            return customer;
        } catch (Exception e) {
            throw handleException(e, false);
        }
    }

    private void checkCustomer(Customer customer) throws GrozaException {
        checkNotNull(customer);
        checkTenantId(customer.getTenantId());
    }

    void checkTenantId(TenantId tenantId) throws GrozaException {
        validateId(tenantId, INCORRECT_TENANT_ID + tenantId);
        SecurityUser authUser = getCurrentUser();
        if (authUser.getAuthority() != Authority.SYS_ADMIN &&
                (authUser.getTenantId() == null || !authUser.getTenantId().equals(tenantId))) {
            throw new GrozaException(YOU_DON_T_HAVE_PERMISSION_TO_PERFORM_THIS_OPERATION,
                    GrozaErrorCode.PERMISSION_DENIED);
        }
    }

    protected SecurityUser getCurrentUser() throws GrozaException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof SecurityUser) {
            return (SecurityUser) authentication.getPrincipal();
        } else {
            throw new GrozaException("You aren't authorized to perform this operation!", GrozaErrorCode.AUTHENTICATION);
        }
    }

    TextPageLink createPageLink(int limit, String textSearch, String idOffset, String textOffset) {
        UUID idOffsetUuid = null;
        if (StringUtils.isNotEmpty(idOffset)) {
            idOffsetUuid = toUUID(idOffset);
        }
        return new TextPageLink(limit, textSearch, idOffsetUuid, textOffset);
    }




    UUID toUUID(String id) {
        return UUID.fromString(id);
    }

    GrozaException handleException(Exception exception) {
        return handleException(exception, true);
    }

    private GrozaException handleException(Exception exception, boolean logException) {
        if (logException) {
            log.error("Error [{}]", exception.getMessage(), exception);
        }

        String cause = "";
        if (exception.getCause() != null) {
            cause = exception.getCause().getClass().getCanonicalName();
        }

        if (exception instanceof GrozaException) {
            return (GrozaException) exception;
        } else if (exception instanceof IllegalArgumentException || exception instanceof IncorrectParameterException
                || exception instanceof DataValidationException || cause.contains("IncorrectParameterException")) {
            return new GrozaException(exception.getMessage(), GrozaErrorCode.BAD_REQUEST_PARAMS);
        } else if (exception instanceof MessagingException) {
            return new GrozaException("Unable to send mail: " + exception.getMessage(), GrozaErrorCode.GENERAL);
        } else {
            return new GrozaException(exception.getMessage(), GrozaErrorCode.GENERAL);
        }
    }
}
