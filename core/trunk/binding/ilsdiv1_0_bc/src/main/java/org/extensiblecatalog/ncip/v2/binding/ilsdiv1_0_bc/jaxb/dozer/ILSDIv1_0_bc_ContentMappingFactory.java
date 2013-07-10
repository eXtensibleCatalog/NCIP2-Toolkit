/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.ilsdiv1_0_bc.jaxb.dozer;

import org.extensiblecatalog.ncip.v2.binding.ilsdiv1_0_bc.jaxb.elements.SchemeValuePair;
import org.extensiblecatalog.ncip.v2.binding.jaxb.dozer.BaseContentConverter;
import org.extensiblecatalog.ncip.v2.binding.ilsdiv1_0_bc.jaxb.elements.*;
import org.extensiblecatalog.ncip.v2.service.*;

import java.util.HashMap;
import java.util.Map;

public class ILSDIv1_0_bc_ContentMappingFactory extends BaseContentConverter<SchemeValuePair, Ext> {

    /**
     * Maps element names (derived in this case from JAXB field names) to service-package class names.
     */
    protected static Map<String, Class<?>> elementNamesToServiceClassMap = new HashMap<String, Class<?>>();
    static {

        elementNamesToServiceClassMap.put("AcceptItem", AcceptItemInitiationData.class);
        elementNamesToServiceClassMap.put("AcceptItemResponse", AcceptItemResponseData.class);
        elementNamesToServiceClassMap.put("AgencyCreated", AgencyCreatedInitiationData.class);
        elementNamesToServiceClassMap.put("AgencyCreatedResponse", AgencyCreatedResponseData.class);
        elementNamesToServiceClassMap.put("AgencyUpdated", AgencyUpdatedInitiationData.class);
        elementNamesToServiceClassMap.put("AgencyUpdatedResponse", AgencyUpdatedResponseData.class);
        elementNamesToServiceClassMap.put("CancelRecallItem", CancelRecallItemInitiationData.class);
        elementNamesToServiceClassMap.put("CancelRecallItemResponse", CancelRecallItemResponseData.class);
        elementNamesToServiceClassMap.put("CancelRequestItem", CancelRequestItemInitiationData.class);
        elementNamesToServiceClassMap.put("CancelRequestItemResponse", CancelRequestItemResponseData.class);
        elementNamesToServiceClassMap.put("CheckInItem", CheckInItemInitiationData.class);
        elementNamesToServiceClassMap.put("CheckInItemResponse", CheckInItemResponseData.class);
        elementNamesToServiceClassMap.put("CheckOutItem", CheckOutItemInitiationData.class);
        elementNamesToServiceClassMap.put("CheckOutItemResponse", CheckOutItemResponseData.class);
        elementNamesToServiceClassMap.put("CirculationStatusChangeReported", CirculationStatusChangeReportedInitiationData.class);
        elementNamesToServiceClassMap.put("CirculationStatusChangeReportedResponse", CirculationStatusChangeReportedResponseData.class);
        elementNamesToServiceClassMap.put("CirculationStatusUpdated", CirculationStatusUpdatedInitiationData.class);
        elementNamesToServiceClassMap.put("CirculationStatusUpdatedResponse", CirculationStatusUpdatedResponseData.class);
        elementNamesToServiceClassMap.put("CreateAgency", CreateAgencyInitiationData.class);
        elementNamesToServiceClassMap.put("CreateAgencyResponse", CreateAgencyResponseData.class);
        elementNamesToServiceClassMap.put("CreateItem", CreateItemInitiationData.class);
        elementNamesToServiceClassMap.put("CreateItemResponse", CreateItemResponseData.class);
        elementNamesToServiceClassMap.put("CreateUser", CreateUserInitiationData.class);
        elementNamesToServiceClassMap.put("CreateUserResponse", CreateUserResponseData.class);
        elementNamesToServiceClassMap.put("CreateUserFiscalTransaction", CreateUserFiscalTransactionInitiationData.class);
        elementNamesToServiceClassMap.put("CreateUserFiscalTransactionResponse", CreateUserFiscalTransactionResponseData.class);
        elementNamesToServiceClassMap.put("DeleteItem", DeleteItemInitiationData.class);
        elementNamesToServiceClassMap.put("DeleteItemResponse", DeleteItemResponseData.class);
        elementNamesToServiceClassMap.put("DeleteUser", DeleteUserInitiationData.class);
        elementNamesToServiceClassMap.put("DeleteUserResponse", DeleteUserResponseData.class);
        elementNamesToServiceClassMap.put("ItemCheckedIn", ItemCheckedInInitiationData.class);
        elementNamesToServiceClassMap.put("ItemCheckedInResponse", ItemCheckedInResponseData.class);
        elementNamesToServiceClassMap.put("ItemCheckedOut", ItemCheckedOutInitiationData.class);
        elementNamesToServiceClassMap.put("ItemCheckedOutResponse", ItemCheckedOutResponseData.class);
        elementNamesToServiceClassMap.put("ItemCreated", ItemCreatedInitiationData.class);
        elementNamesToServiceClassMap.put("ItemCreatedResponse", ItemCreatedResponseData.class);
        elementNamesToServiceClassMap.put("ItemRecallCancelled", ItemRecallCancelledInitiationData.class);
        elementNamesToServiceClassMap.put("ItemRecallCancelledResponse", ItemRecallCancelledResponseData.class);
        elementNamesToServiceClassMap.put("ItemRecalled", ItemRecalledInitiationData.class);
        elementNamesToServiceClassMap.put("ItemRecalledResponse", ItemRecalledResponseData.class);
        elementNamesToServiceClassMap.put("ItemReceived", ItemReceivedInitiationData.class);
        elementNamesToServiceClassMap.put("ItemReceivedResponse", ItemReceivedResponseData.class);
        elementNamesToServiceClassMap.put("ItemRenewed", ItemRenewedInitiationData.class);
        elementNamesToServiceClassMap.put("ItemRenewedResponse", ItemRenewedResponseData.class);
        elementNamesToServiceClassMap.put("ItemRequestCancelled", ItemRequestCancelledInitiationData.class);
        elementNamesToServiceClassMap.put("ItemRequestCancelledResponse", ItemRequestCancelledResponseData.class);
        elementNamesToServiceClassMap.put("ItemRequestUpdated", ItemRequestUpdatedInitiationData.class);
        elementNamesToServiceClassMap.put("ItemRequestUpdatedResponse", ItemRequestUpdatedResponseData.class);
        elementNamesToServiceClassMap.put("ItemRequested", ItemRequestedInitiationData.class);
        elementNamesToServiceClassMap.put("ItemRequestedResponse", ItemRequestedResponseData.class);
        elementNamesToServiceClassMap.put("ItemShipped", ItemShippedInitiationData.class);
        elementNamesToServiceClassMap.put("ItemShippedResponse", ItemShippedResponseData.class);
        elementNamesToServiceClassMap.put("ItemUpdated", ItemUpdatedInitiationData.class);
        elementNamesToServiceClassMap.put("ItemUpdatedResponse", ItemUpdatedResponseData.class);
        elementNamesToServiceClassMap.put("LookupAgency", LookupAgencyInitiationData.class);
        elementNamesToServiceClassMap.put("LookupAgencyResponse", LookupAgencyResponseData.class);
        elementNamesToServiceClassMap.put("LookupItemSet", LookupItemSetInitiationData.class);
        elementNamesToServiceClassMap.put("LookupItemSetResponse", LookupItemSetResponseData.class);
        elementNamesToServiceClassMap.put("LookupItem", LookupItemInitiationData.class);
        elementNamesToServiceClassMap.put("LookupItemResponse", LookupItemResponseData.class);
        elementNamesToServiceClassMap.put("LookupRequest", LookupRequestInitiationData.class);
        elementNamesToServiceClassMap.put("LookupRequestResponse", LookupRequestResponseData.class);
        elementNamesToServiceClassMap.put("LookupUser", LookupUserInitiationData.class);
        elementNamesToServiceClassMap.put("LookupUserResponse", LookupUserResponseData.class);
        elementNamesToServiceClassMap.put("RecallItem", RecallItemInitiationData.class);
        elementNamesToServiceClassMap.put("RecallItemResponse", RecallItemResponseData.class);
        elementNamesToServiceClassMap.put("RenewItem", RenewItemInitiationData.class);
        elementNamesToServiceClassMap.put("RenewItemResponse", RenewItemResponseData.class);
        elementNamesToServiceClassMap.put("ReportCirculationStatusChange", ReportCirculationStatusChangeInitiationData.class);
        elementNamesToServiceClassMap.put("ReportCirculationStatusChangeResponse", ReportCirculationStatusChangeResponseData.class);
        elementNamesToServiceClassMap.put("RequestItem", RequestItemInitiationData.class);
        elementNamesToServiceClassMap.put("RequestItemResponse", RequestItemResponseData.class);
        elementNamesToServiceClassMap.put("SendUserNotice", SendUserNoticeInitiationData.class);
        elementNamesToServiceClassMap.put("SendUserNoticeResponse", SendUserNoticeResponseData.class);
        elementNamesToServiceClassMap.put("UndoCheckOutItem", UndoCheckOutItemInitiationData.class);
        elementNamesToServiceClassMap.put("UndoCheckOutItemResponse", UndoCheckOutItemResponseData.class);
        elementNamesToServiceClassMap.put("UpdateAgency", UpdateAgencyInitiationData.class);
        elementNamesToServiceClassMap.put("UpdateAgencyResponse", UpdateAgencyResponseData.class);
        elementNamesToServiceClassMap.put("UpdateCirculationStatus", UpdateCirculationStatusInitiationData.class);
        elementNamesToServiceClassMap.put("UpdateCirculationStatusResponse", UpdateCirculationStatusResponseData.class);
        elementNamesToServiceClassMap.put("UpdateItem", UpdateItemInitiationData.class);
        elementNamesToServiceClassMap.put("UpdateItemResponse", UpdateItemResponseData.class);
        elementNamesToServiceClassMap.put("UpdateRequestItem", UpdateRequestItemInitiationData.class);
        elementNamesToServiceClassMap.put("UpdateRequestItemResponse", UpdateRequestItemResponseData.class);
        elementNamesToServiceClassMap.put("UpdateUser", UpdateUserInitiationData.class);
        elementNamesToServiceClassMap.put("UpdateUserResponse", UpdateUserResponseData.class);
        elementNamesToServiceClassMap.put("UserCreated", UserCreatedInitiationData.class);
        elementNamesToServiceClassMap.put("UserCreatedResponse", UserCreatedResponseData.class);
        elementNamesToServiceClassMap.put("UserFiscalTransactionCreated", UserFiscalTransactionCreatedInitiationData.class);
        elementNamesToServiceClassMap.put("UserFiscalTransactionCreatedResponse", UserFiscalTransactionCreatedResponseData.class);
        elementNamesToServiceClassMap.put("UserNoticeSent", UserNoticeSentInitiationData.class);
        elementNamesToServiceClassMap.put("UserNoticeSentResponse", UserNoticeSentResponseData.class);
        elementNamesToServiceClassMap.put("UserUpdated", UserUpdatedInitiationData.class);
        elementNamesToServiceClassMap.put("UserUpdatedResponse", UserUpdatedResponseData.class);
        elementNamesToServiceClassMap.put("ProblemResponse", ProblemResponseData.class);


    }

    /**
     * Maps element names (derived in this case from names of fields in service classes) to JAXB package class names.
     */
    protected static Map<String, Class<?>> elementNamesToJAXBClassMap = new HashMap<String, Class<?>>();
    static {

        // There are no element names (as yet) which don't match their JAXB class' simple name,
        // so this map can be empty.
    }

    public ILSDIv1_0_bc_ContentMappingFactory() {

        super(SchemeValuePair.class, Ext.class);

    }

    @Override
    protected Ext createExtension() {

        return new Ext();

    }

    @Override
    protected Object getObjectFactory() {

        return new ObjectFactory();

    }

    @Override
    protected Map<String, Class<?>> getElementNamesToServiceClassMap() {

        return elementNamesToServiceClassMap;

    }

    @Override
    protected Map<String, Class<?>> getElementNamesToJAXBClassMap() {

        return elementNamesToJAXBClassMap;

    }
}
