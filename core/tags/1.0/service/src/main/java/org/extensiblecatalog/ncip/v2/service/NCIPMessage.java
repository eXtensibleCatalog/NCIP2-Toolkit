/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class NCIPMessage {

    protected AcceptItemInitiationData acceptItem;
    protected AcceptItemResponseData acceptItemResponse;
    protected AgencyCreatedInitiationData agencyCreated;
    protected AgencyCreatedResponseData agencyCreatedResponse;
    protected AgencyUpdatedInitiationData agencyUpdated;
    protected AgencyUpdatedResponseData agencyUpdatedResponse;
    protected CancelRecallItemInitiationData cancelRecallItem;
    protected CancelRecallItemResponseData cancelRecallItemResponse;
    protected CancelRequestItemInitiationData cancelRequestItem;
    protected CancelRequestItemResponseData cancelRequestItemResponse;
    protected CheckInItemInitiationData checkInItem;
    protected CheckInItemResponseData checkInItemResponse;
    protected CheckOutItemInitiationData checkOutItem;
    protected CheckOutItemResponseData checkOutItemResponse;
    protected CirculationStatusChangeReportedInitiationData circulationStatusChangeReported;
    protected CirculationStatusChangeReportedResponseData circulationStatusChangeReportedResponse;
    protected CirculationStatusUpdatedInitiationData circulationStatusUpdated;
    protected CirculationStatusUpdatedResponseData circulationStatusUpdatedResponse;
    protected CreateAgencyInitiationData createAgency;
    protected CreateAgencyResponseData createAgencyResponse;
    protected CreateItemInitiationData createItem;
    protected CreateItemResponseData createItemResponse;
    protected CreateUserInitiationData createUser;
    protected CreateUserResponseData createUserResponse;
    protected CreateUserFiscalTransactionInitiationData createUserFiscalTransaction;
    protected CreateUserFiscalTransactionResponseData createUserFiscalTransactionResponse;
    protected DeleteItemInitiationData deleteItem;
    protected DeleteItemResponseData deleteItemResponse;
    protected DeleteUserInitiationData deleteUser;
    protected DeleteUserResponseData deleteUserResponse;
    protected ItemCheckedInInitiationData itemCheckedIn;
    protected ItemCheckedInResponseData itemCheckedInResponse;
    protected ItemCheckedOutInitiationData itemCheckedOut;
    protected ItemCheckedOutResponseData itemCheckedOutResponse;
    protected ItemCreatedInitiationData itemCreated;
    protected ItemCreatedResponseData itemCreatedResponse;
    protected ItemRecallCancelledInitiationData itemRecallCancelled;
    protected ItemRecallCancelledResponseData itemRecallCancelledResponse;
    protected ItemRecalledInitiationData itemRecalled;
    protected ItemRecalledResponseData itemRecalledResponse;
    protected ItemReceivedInitiationData itemReceived;
    protected ItemReceivedResponseData itemReceivedResponse;
    protected ItemRenewedInitiationData itemRenewed;
    protected ItemRenewedResponseData itemRenewedResponse;
    protected ItemRequestCancelledInitiationData itemRequestCancelled;
    protected ItemRequestCancelledResponseData itemRequestCancelledResponse;
    protected ItemRequestUpdatedInitiationData itemRequestUpdated;
    protected ItemRequestUpdatedResponseData itemRequestUpdatedResponse;
    protected ItemRequestedInitiationData itemRequested;
    protected ItemRequestedResponseData itemRequestedResponse;
    protected ItemShippedInitiationData itemShipped;
    protected ItemShippedResponseData itemShippedResponse;
    protected ItemUpdatedInitiationData itemUpdated;
    protected ItemUpdatedResponseData itemUpdatedResponse;
    protected LookupAgencyInitiationData lookupAgency;
    protected LookupAgencyResponseData lookupAgencyResponse;
    protected LookupItemSetInitiationData lookupItemSet;
    protected LookupItemSetResponseData lookupItemSetResponse;
    protected LookupItemInitiationData lookupItem;
    protected LookupItemResponseData lookupItemResponse;
    protected LookupRequestInitiationData lookupRequest;
    protected LookupRequestResponseData lookupRequestResponse;
    protected LookupUserInitiationData lookupUser;
    protected LookupUserResponseData lookupUserResponse;
    protected RecallItemInitiationData recallItem;
    protected RecallItemResponseData recallItemResponse;
    protected RenewItemInitiationData renewItem;
    protected RenewItemResponseData renewItemResponse;
    protected ReportCirculationStatusChangeInitiationData reportCirculationStatusChange;
    protected ReportCirculationStatusChangeResponseData reportCirculationStatusChangeResponse;
    protected RequestItemInitiationData requestItem;
    protected RequestItemResponseData requestItemResponse;
    protected SendUserNoticeInitiationData sendUserNotice;
    protected SendUserNoticeResponseData sendUserNoticeResponse;
    protected UndoCheckOutItemInitiationData undoCheckOutItem;
    protected UndoCheckOutItemResponseData undoCheckOutItemResponse;
    protected UpdateAgencyInitiationData updateAgency;
    protected UpdateAgencyResponseData updateAgencyResponse;
    protected UpdateCirculationStatusInitiationData updateCirculationStatus;
    protected UpdateCirculationStatusResponseData updateCirculationStatusResponse;
    protected UpdateItemInitiationData updateItem;
    protected UpdateItemResponseData updateItemResponse;
    protected UpdateRequestItemInitiationData updateRequestItem;
    protected UpdateRequestItemResponseData updateRequestItemResponse;
    protected UpdateUserInitiationData updateUser;
    protected UpdateUserResponseData updateUserResponse;
    protected UserCreatedInitiationData userCreated;
    protected UserCreatedResponseData userCreatedResponse;
    protected UserFiscalTransactionCreatedInitiationData userFiscalTransactionCreated;
    protected UserFiscalTransactionCreatedResponseData userFiscalTransactionCreatedResponse;
    protected UserNoticeSentInitiationData userNoticeSent;
    protected UserNoticeSentResponseData userNoticeSentResponse;
    protected UserUpdatedInitiationData userUpdated;
    protected UserUpdatedResponseData userUpdatedResponse;
    protected ProblemResponseData problemResponse;


    public AcceptItemInitiationData getAcceptItem() {
        return acceptItem;
    }

    public void setAcceptItem(AcceptItemInitiationData acceptItem) {
        this.acceptItem = acceptItem;
    }

    public AcceptItemResponseData getAcceptItemResponse() {
        return acceptItemResponse;
    }

    public void setAcceptItemResponse(AcceptItemResponseData acceptItemResponse) {
        this.acceptItemResponse = acceptItemResponse;
    }

    public AgencyCreatedInitiationData getAgencyCreated() {
        return agencyCreated;
    }

    public void setAgencyCreated(AgencyCreatedInitiationData agencyCreated) {
        this.agencyCreated = agencyCreated;
    }

    public AgencyCreatedResponseData getAgencyCreatedResponse() {
        return agencyCreatedResponse;
    }

    public void setAgencyCreatedResponse(AgencyCreatedResponseData agencyCreatedResponse) {
        this.agencyCreatedResponse = agencyCreatedResponse;
    }

    public AgencyUpdatedInitiationData getAgencyUpdated() {
        return agencyUpdated;
    }

    public void setAgencyUpdated(AgencyUpdatedInitiationData agencyUpdated) {
        this.agencyUpdated = agencyUpdated;
    }

    public AgencyUpdatedResponseData getAgencyUpdatedResponse() {
        return agencyUpdatedResponse;
    }

    public void setAgencyUpdatedResponse(AgencyUpdatedResponseData agencyUpdatedResponse) {
        this.agencyUpdatedResponse = agencyUpdatedResponse;
    }

    public CancelRecallItemInitiationData getCancelRecallItem() {
        return cancelRecallItem;
    }

    public void setCancelRecallItem(CancelRecallItemInitiationData cancelRecallItem) {
        this.cancelRecallItem = cancelRecallItem;
    }

    public CancelRecallItemResponseData getCancelRecallItemResponse() {
        return cancelRecallItemResponse;
    }

    public void setCancelRecallItemResponse(CancelRecallItemResponseData cancelRecallItemResponse) {
        this.cancelRecallItemResponse = cancelRecallItemResponse;
    }

    public CancelRequestItemInitiationData getCancelRequestItem() {
        return cancelRequestItem;
    }

    public void setCancelRequestItem(CancelRequestItemInitiationData cancelRequestItem) {
        this.cancelRequestItem = cancelRequestItem;
    }

    public CancelRequestItemResponseData getCancelRequestItemResponse() {
        return cancelRequestItemResponse;
    }

    public void setCancelRequestItemResponse(CancelRequestItemResponseData cancelRequestItemResponse) {
        this.cancelRequestItemResponse = cancelRequestItemResponse;
    }

    public CheckInItemInitiationData getCheckInItem() {
        return checkInItem;
    }

    public void setCheckInItem(CheckInItemInitiationData checkInItem) {
        this.checkInItem = checkInItem;
    }

    public CheckInItemResponseData getCheckInItemResponse() {
        return checkInItemResponse;
    }

    public void setCheckInItemResponse(CheckInItemResponseData checkInItemResponse) {
        this.checkInItemResponse = checkInItemResponse;
    }

    public CheckOutItemInitiationData getCheckOutItem() {
        return checkOutItem;
    }

    public void setCheckOutItem(CheckOutItemInitiationData checkOutItem) {
        this.checkOutItem = checkOutItem;
    }

    public CheckOutItemResponseData getCheckOutItemResponse() {
        return checkOutItemResponse;
    }

    public void setCheckOutItemResponse(CheckOutItemResponseData checkOutItemResponse) {
        this.checkOutItemResponse = checkOutItemResponse;
    }

    public CirculationStatusChangeReportedInitiationData getCirculationStatusChangeReported() {
        return circulationStatusChangeReported;
    }

    public void setCirculationStatusChangeReported(CirculationStatusChangeReportedInitiationData circulationStatusChangeReported) {
        this.circulationStatusChangeReported = circulationStatusChangeReported;
    }

    public CirculationStatusChangeReportedResponseData getCirculationStatusChangeReportedResponse() {
        return circulationStatusChangeReportedResponse;
    }

    public void setCirculationStatusChangeReportedResponse(CirculationStatusChangeReportedResponseData circulationStatusChangeReportedResponse) {
        this.circulationStatusChangeReportedResponse = circulationStatusChangeReportedResponse;
    }

    public CirculationStatusUpdatedInitiationData getCirculationStatusUpdated() {
        return circulationStatusUpdated;
    }

    public void setCirculationStatusUpdated(CirculationStatusUpdatedInitiationData circulationStatusUpdated) {
        this.circulationStatusUpdated = circulationStatusUpdated;
    }

    public CirculationStatusUpdatedResponseData getCirculationStatusUpdatedResponse() {
        return circulationStatusUpdatedResponse;
    }

    public void setCirculationStatusUpdatedResponse(CirculationStatusUpdatedResponseData circulationStatusUpdatedResponse) {
        this.circulationStatusUpdatedResponse = circulationStatusUpdatedResponse;
    }

    public CreateAgencyInitiationData getCreateAgency() {
        return createAgency;
    }

    public void setCreateAgency(CreateAgencyInitiationData createAgency) {
        this.createAgency = createAgency;
    }

    public CreateAgencyResponseData getCreateAgencyResponse() {
        return createAgencyResponse;
    }

    public void setCreateAgencyResponse(CreateAgencyResponseData createAgencyResponse) {
        this.createAgencyResponse = createAgencyResponse;
    }

    public CreateItemInitiationData getCreateItem() {
        return createItem;
    }

    public void setCreateItem(CreateItemInitiationData createItem) {
        this.createItem = createItem;
    }

    public CreateItemResponseData getCreateItemResponse() {
        return createItemResponse;
    }

    public void setCreateItemResponse(CreateItemResponseData createItemResponse) {
        this.createItemResponse = createItemResponse;
    }

    public CreateUserInitiationData getCreateUser() {
        return createUser;
    }

    public void setCreateUser(CreateUserInitiationData createUser) {
        this.createUser = createUser;
    }

    public CreateUserResponseData getCreateUserResponse() {
        return createUserResponse;
    }

    public void setCreateUserResponse(CreateUserResponseData createUserResponse) {
        this.createUserResponse = createUserResponse;
    }

    public CreateUserFiscalTransactionInitiationData getCreateUserFiscalTransaction() {
        return createUserFiscalTransaction;
    }

    public void setCreateUserFiscalTransaction(CreateUserFiscalTransactionInitiationData createUserFiscalTransaction) {
        this.createUserFiscalTransaction = createUserFiscalTransaction;
    }

    public CreateUserFiscalTransactionResponseData getCreateUserFiscalTransactionResponse() {
        return createUserFiscalTransactionResponse;
    }

    public void setCreateUserFiscalTransactionResponse(CreateUserFiscalTransactionResponseData createUserFiscalTransactionResponse) {
        this.createUserFiscalTransactionResponse = createUserFiscalTransactionResponse;
    }

    public DeleteItemInitiationData getDeleteItem() {
        return deleteItem;
    }

    public void setDeleteItem(DeleteItemInitiationData deleteItem) {
        this.deleteItem = deleteItem;
    }

    public DeleteItemResponseData getDeleteItemResponse() {
        return deleteItemResponse;
    }

    public void setDeleteItemResponse(DeleteItemResponseData deleteItemResponse) {
        this.deleteItemResponse = deleteItemResponse;
    }

    public DeleteUserInitiationData getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(DeleteUserInitiationData deleteUser) {
        this.deleteUser = deleteUser;
    }

    public DeleteUserResponseData getDeleteUserResponse() {
        return deleteUserResponse;
    }

    public void setDeleteUserResponse(DeleteUserResponseData deleteUserResponse) {
        this.deleteUserResponse = deleteUserResponse;
    }

    public ItemCheckedInInitiationData getItemCheckedIn() {
        return itemCheckedIn;
    }

    public void setItemCheckedIn(ItemCheckedInInitiationData itemCheckedIn) {
        this.itemCheckedIn = itemCheckedIn;
    }

    public ItemCheckedInResponseData getItemCheckedInResponse() {
        return itemCheckedInResponse;
    }

    public void setItemCheckedInResponse(ItemCheckedInResponseData itemCheckedInResponse) {
        this.itemCheckedInResponse = itemCheckedInResponse;
    }

    public ItemCheckedOutInitiationData getItemCheckedOut() {
        return itemCheckedOut;
    }

    public void setItemCheckedOut(ItemCheckedOutInitiationData itemCheckedOut) {
        this.itemCheckedOut = itemCheckedOut;
    }

    public ItemCheckedOutResponseData getItemCheckedOutResponse() {
        return itemCheckedOutResponse;
    }

    public void setItemCheckedOutResponse(ItemCheckedOutResponseData itemCheckedOutResponse) {
        this.itemCheckedOutResponse = itemCheckedOutResponse;
    }

    public ItemCreatedInitiationData getItemCreated() {
        return itemCreated;
    }

    public void setItemCreated(ItemCreatedInitiationData itemCreated) {
        this.itemCreated = itemCreated;
    }

    public ItemCreatedResponseData getItemCreatedResponse() {
        return itemCreatedResponse;
    }

    public void setItemCreatedResponse(ItemCreatedResponseData itemCreatedResponse) {
        this.itemCreatedResponse = itemCreatedResponse;
    }

    public ItemRecallCancelledInitiationData getItemRecallCancelled() {
        return itemRecallCancelled;
    }

    public void setItemRecallCancelled(ItemRecallCancelledInitiationData itemRecallCancelled) {
        this.itemRecallCancelled = itemRecallCancelled;
    }

    public ItemRecallCancelledResponseData getItemRecallCancelledResponse() {
        return itemRecallCancelledResponse;
    }

    public void setItemRecallCancelledResponse(ItemRecallCancelledResponseData itemRecallCancelledResponse) {
        this.itemRecallCancelledResponse = itemRecallCancelledResponse;
    }

    public ItemRecalledInitiationData getItemRecalled() {
        return itemRecalled;
    }

    public void setItemRecalled(ItemRecalledInitiationData itemRecalled) {
        this.itemRecalled = itemRecalled;
    }

    public ItemRecalledResponseData getItemRecalledResponse() {
        return itemRecalledResponse;
    }

    public void setItemRecalledResponse(ItemRecalledResponseData itemRecalledResponse) {
        this.itemRecalledResponse = itemRecalledResponse;
    }

    public ItemReceivedInitiationData getItemReceived() {
        return itemReceived;
    }

    public void setItemReceived(ItemReceivedInitiationData itemReceived) {
        this.itemReceived = itemReceived;
    }

    public ItemReceivedResponseData getItemReceivedResponse() {
        return itemReceivedResponse;
    }

    public void setItemReceivedResponse(ItemReceivedResponseData itemReceivedResponse) {
        this.itemReceivedResponse = itemReceivedResponse;
    }

    public ItemRenewedInitiationData getItemRenewed() {
        return itemRenewed;
    }

    public void setItemRenewed(ItemRenewedInitiationData itemRenewed) {
        this.itemRenewed = itemRenewed;
    }

    public ItemRenewedResponseData getItemRenewedResponse() {
        return itemRenewedResponse;
    }

    public void setItemRenewedResponse(ItemRenewedResponseData itemRenewedResponse) {
        this.itemRenewedResponse = itemRenewedResponse;
    }

    public ItemRequestCancelledInitiationData getItemRequestCancelled() {
        return itemRequestCancelled;
    }

    public void setItemRequestCancelled(ItemRequestCancelledInitiationData itemRequestCancelled) {
        this.itemRequestCancelled = itemRequestCancelled;
    }

    public ItemRequestCancelledResponseData getItemRequestCancelledResponse() {
        return itemRequestCancelledResponse;
    }

    public void setItemRequestCancelledResponse(ItemRequestCancelledResponseData itemRequestCancelledResponse) {
        this.itemRequestCancelledResponse = itemRequestCancelledResponse;
    }

    public ItemRequestUpdatedInitiationData getItemRequestUpdated() {
        return itemRequestUpdated;
    }

    public void setItemRequestUpdated(ItemRequestUpdatedInitiationData itemRequestUpdated) {
        this.itemRequestUpdated = itemRequestUpdated;
    }

    public ItemRequestUpdatedResponseData getItemRequestUpdatedResponse() {
        return itemRequestUpdatedResponse;
    }

    public void setItemRequestUpdatedResponse(ItemRequestUpdatedResponseData itemRequestUpdatedResponse) {
        this.itemRequestUpdatedResponse = itemRequestUpdatedResponse;
    }

    public ItemRequestedInitiationData getItemRequested() {
        return itemRequested;
    }

    public void setItemRequested(ItemRequestedInitiationData itemRequested) {
        this.itemRequested = itemRequested;
    }

    public ItemRequestedResponseData getItemRequestedResponse() {
        return itemRequestedResponse;
    }

    public void setItemRequestedResponse(ItemRequestedResponseData itemRequestedResponse) {
        this.itemRequestedResponse = itemRequestedResponse;
    }

    public ItemShippedInitiationData getItemShipped() {
        return itemShipped;
    }

    public void setItemShipped(ItemShippedInitiationData itemShipped) {
        this.itemShipped = itemShipped;
    }

    public ItemShippedResponseData getItemShippedResponse() {
        return itemShippedResponse;
    }

    public void setItemShippedResponse(ItemShippedResponseData itemShippedResponse) {
        this.itemShippedResponse = itemShippedResponse;
    }

    public ItemUpdatedInitiationData getItemUpdated() {
        return itemUpdated;
    }

    public void setItemUpdated(ItemUpdatedInitiationData itemUpdated) {
        this.itemUpdated = itemUpdated;
    }

    public ItemUpdatedResponseData getItemUpdatedResponse() {
        return itemUpdatedResponse;
    }

    public void setItemUpdatedResponse(ItemUpdatedResponseData itemUpdatedResponse) {
        this.itemUpdatedResponse = itemUpdatedResponse;
    }

    public LookupAgencyInitiationData getLookupAgency() {
        return lookupAgency;
    }

    public void setLookupAgency(LookupAgencyInitiationData lookupAgency) {
        this.lookupAgency = lookupAgency;
    }

    public LookupAgencyResponseData getLookupAgencyResponse() {
        return lookupAgencyResponse;
    }

    public void setLookupAgencyResponse(LookupAgencyResponseData lookupAgencyResponse) {
        this.lookupAgencyResponse = lookupAgencyResponse;
    }

    public LookupItemInitiationData getLookupItem() {
        return lookupItem;
    }

    public void setLookupItem(LookupItemInitiationData lookupItem) {
        this.lookupItem = lookupItem;
    }

    public void setLookupItemSet(LookupItemSetInitiationData lookupItemSet) {
        this.lookupItemSet = lookupItemSet;
    }

    public LookupItemSetInitiationData getLookupItemSet() {
        return lookupItemSet;
    }

    public LookupItemSetResponseData getLookupItemSetResponse() {
        return lookupItemSetResponse;
    }

    public void setLookupItemSetResponse(LookupItemSetResponseData lookupItemSetResponse) {
        this.lookupItemSetResponse = lookupItemSetResponse;
    }

    public LookupItemResponseData getLookupItemResponse() {
        return lookupItemResponse;
    }

    public void setLookupItemResponse(LookupItemResponseData lookupItemResponse) {
        this.lookupItemResponse = lookupItemResponse;
    }

    public LookupRequestInitiationData getLookupRequest() {
        return lookupRequest;
    }

    public void setLookupRequest(LookupRequestInitiationData lookupRequest) {
        this.lookupRequest = lookupRequest;
    }

    public LookupRequestResponseData getLookupRequestResponse() {
        return lookupRequestResponse;
    }

    public void setLookupRequestResponse(LookupRequestResponseData lookupRequestResponse) {
        this.lookupRequestResponse = lookupRequestResponse;
    }

    public LookupUserInitiationData getLookupUser() {
        return lookupUser;
    }

    public void setLookupUser(LookupUserInitiationData lookupUser) {
        this.lookupUser = lookupUser;
    }

    public LookupUserResponseData getLookupUserResponse() {
        return lookupUserResponse;
    }

    public void setLookupUserResponse(LookupUserResponseData lookupUserResponse) {
        this.lookupUserResponse = lookupUserResponse;
    }

    public RecallItemInitiationData getRecallItem() {
        return recallItem;
    }

    public void setRecallItem(RecallItemInitiationData recallItem) {
        this.recallItem = recallItem;
    }

    public RecallItemResponseData getRecallItemResponse() {
        return recallItemResponse;
    }

    public void setRecallItemResponse(RecallItemResponseData recallItemResponse) {
        this.recallItemResponse = recallItemResponse;
    }

    public RenewItemInitiationData getRenewItem() {
        return renewItem;
    }

    public void setRenewItem(RenewItemInitiationData renewItem) {
        this.renewItem = renewItem;
    }

    public RenewItemResponseData getRenewItemResponse() {
        return renewItemResponse;
    }

    public void setRenewItemResponse(RenewItemResponseData renewItemResponse) {
        this.renewItemResponse = renewItemResponse;
    }

    public ReportCirculationStatusChangeInitiationData getReportCirculationStatusChange() {
        return reportCirculationStatusChange;
    }

    public void setReportCirculationStatusChange(ReportCirculationStatusChangeInitiationData reportCirculationStatusChange) {
        this.reportCirculationStatusChange = reportCirculationStatusChange;
    }

    public ReportCirculationStatusChangeResponseData getReportCirculationStatusChangeResponse() {
        return reportCirculationStatusChangeResponse;
    }

    public void setReportCirculationStatusChangeResponse(ReportCirculationStatusChangeResponseData reportCirculationStatusChangeResponse) {
        this.reportCirculationStatusChangeResponse = reportCirculationStatusChangeResponse;
    }

    public RequestItemInitiationData getRequestItem() {
        return requestItem;
    }

    public void setRequestItem(RequestItemInitiationData requestItem) {
        this.requestItem = requestItem;
    }

    public RequestItemResponseData getRequestItemResponse() {
        return requestItemResponse;
    }

    public void setRequestItemResponse(RequestItemResponseData requestItemResponse) {
        this.requestItemResponse = requestItemResponse;
    }

    public SendUserNoticeInitiationData getSendUserNotice() {
        return sendUserNotice;
    }

    public void setSendUserNotice(SendUserNoticeInitiationData sendUserNotice) {
        this.sendUserNotice = sendUserNotice;
    }

    public SendUserNoticeResponseData getSendUserNoticeResponse() {
        return sendUserNoticeResponse;
    }

    public void setSendUserNoticeResponse(SendUserNoticeResponseData sendUserNoticeResponse) {
        this.sendUserNoticeResponse = sendUserNoticeResponse;
    }

    public UndoCheckOutItemInitiationData getUndoCheckOutItem() {
        return undoCheckOutItem;
    }

    public void setUndoCheckOutItem(UndoCheckOutItemInitiationData undoCheckOutItem) {
        this.undoCheckOutItem = undoCheckOutItem;
    }

    public UndoCheckOutItemResponseData getUndoCheckOutItemResponse() {
        return undoCheckOutItemResponse;
    }

    public void setUndoCheckOutItemResponse(UndoCheckOutItemResponseData undoCheckOutItemResponse) {
        this.undoCheckOutItemResponse = undoCheckOutItemResponse;
    }

    public UpdateAgencyInitiationData getUpdateAgency() {
        return updateAgency;
    }

    public void setUpdateAgency(UpdateAgencyInitiationData updateAgency) {
        this.updateAgency = updateAgency;
    }

    public UpdateAgencyResponseData getUpdateAgencyResponse() {
        return updateAgencyResponse;
    }

    public void setUpdateAgencyResponse(UpdateAgencyResponseData updateAgencyResponse) {
        this.updateAgencyResponse = updateAgencyResponse;
    }

    public UpdateCirculationStatusInitiationData getUpdateCirculationStatus() {
        return updateCirculationStatus;
    }

    public void setUpdateCirculationStatus(UpdateCirculationStatusInitiationData updateCirculationStatus) {
        this.updateCirculationStatus = updateCirculationStatus;
    }

    public UpdateCirculationStatusResponseData getUpdateCirculationStatusResponse() {
        return updateCirculationStatusResponse;
    }

    public void setUpdateCirculationStatusResponse(UpdateCirculationStatusResponseData updateCirculationStatusResponse) {
        this.updateCirculationStatusResponse = updateCirculationStatusResponse;
    }

    public UpdateItemInitiationData getUpdateItem() {
        return updateItem;
    }

    public void setUpdateItem(UpdateItemInitiationData updateItem) {
        this.updateItem = updateItem;
    }

    public UpdateItemResponseData getUpdateItemResponse() {
        return updateItemResponse;
    }

    public void setUpdateItemResponse(UpdateItemResponseData updateItemResponse) {
        this.updateItemResponse = updateItemResponse;
    }

    public UpdateRequestItemInitiationData getUpdateRequestItem() {
        return updateRequestItem;
    }

    public void setUpdateRequestItem(UpdateRequestItemInitiationData updateRequestItem) {
        this.updateRequestItem = updateRequestItem;
    }

    public UpdateRequestItemResponseData getUpdateRequestItemResponse() {
        return updateRequestItemResponse;
    }

    public void setUpdateRequestItemResponse(UpdateRequestItemResponseData updateRequestItemResponse) {
        this.updateRequestItemResponse = updateRequestItemResponse;
    }

    public UpdateUserInitiationData getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(UpdateUserInitiationData updateUser) {
        this.updateUser = updateUser;
    }

    public UpdateUserResponseData getUpdateUserResponse() {
        return updateUserResponse;
    }

    public void setUpdateUserResponse(UpdateUserResponseData updateUserResponse) {
        this.updateUserResponse = updateUserResponse;
    }

    public UserCreatedInitiationData getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(UserCreatedInitiationData userCreated) {
        this.userCreated = userCreated;
    }

    public UserCreatedResponseData getUserCreatedResponse() {
        return userCreatedResponse;
    }

    public void setUserCreatedResponse(UserCreatedResponseData userCreatedResponse) {
        this.userCreatedResponse = userCreatedResponse;
    }

    public UserFiscalTransactionCreatedInitiationData getUserFiscalTransactionCreated() {
        return userFiscalTransactionCreated;
    }

    public void setUserFiscalTransactionCreated(UserFiscalTransactionCreatedInitiationData userFiscalTransactionCreated) {
        this.userFiscalTransactionCreated = userFiscalTransactionCreated;
    }

    public UserFiscalTransactionCreatedResponseData getUserFiscalTransactionCreatedResponse() {
        return userFiscalTransactionCreatedResponse;
    }

    public void setUserFiscalTransactionCreatedResponse(UserFiscalTransactionCreatedResponseData userFiscalTransactionCreatedResponse) {
        this.userFiscalTransactionCreatedResponse = userFiscalTransactionCreatedResponse;
    }

    public UserNoticeSentInitiationData getUserNoticeSent() {
        return userNoticeSent;
    }

    public void setUserNoticeSent(UserNoticeSentInitiationData userNoticeSent) {
        this.userNoticeSent = userNoticeSent;
    }

    public UserNoticeSentResponseData getUserNoticeSentResponse() {
        return userNoticeSentResponse;
    }

    public void setUserNoticeSentResponse(UserNoticeSentResponseData userNoticeSentResponse) {
        this.userNoticeSentResponse = userNoticeSentResponse;
    }

    public UserUpdatedInitiationData getUserUpdated() {
        return userUpdated;
    }

    public void setUserUpdated(UserUpdatedInitiationData userUpdated) {
        this.userUpdated = userUpdated;
    }

    public UserUpdatedResponseData getUserUpdatedResponse() {
        return userUpdatedResponse;
    }

    public void setUserUpdatedResponse(UserUpdatedResponseData userUpdatedResponse) {
        this.userUpdatedResponse = userUpdatedResponse;
    }

    public ProblemResponseData getProblemResponse() {
        return problemResponse;
    }

    public void setProblemResponse(ProblemResponseData problemResponse) {
        this.problemResponse = problemResponse;
    }

    /**
     * Generic toString() implementation.
     *
     * @return String
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
