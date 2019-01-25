package com.sanshengshui.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanshengshui.server.common.data.exception.GrozaErrorCode;
import com.sanshengshui.server.common.data.exception.GrozaException;
import com.sanshengshui.server.common.data.page.TextPageLink;
import com.sanshengshui.server.dao.exception.DataValidationException;
import com.sanshengshui.server.dao.exception.IncorrectParameterException;
import com.sanshengshui.server.dao.tenant.TenantService;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


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
