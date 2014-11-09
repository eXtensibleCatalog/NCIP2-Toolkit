package org.extensiblecatalog.ncip.v2.aleph.restdlf.item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephConstants;
import org.extensiblecatalog.ncip.v2.service.*;

public class AlephRequestItem implements Serializable {

	private static final long serialVersionUID = 8760934932234833600L;

	private Problem problem;
	private UserId userId;
	private ItemId itemId;
	private RequestId requestId;
	private RequestType requestType;
	private BigDecimal holdQueueLength;
	private BigDecimal holdQueuePosition;
	private GregorianCalendar dateAvailable;
	private GregorianCalendar holdPickupDate;
	private RequestScopeType requestScopeType;
	private RequiredFeeAmount requiredFeeAmount;
	private UserOptionalFields userOptionalFields;
	private ItemOptionalFields itemOptionalFields;
	private ShippingInformation shippingInformation;
	private FiscalTransactionInformation fiscalTransactionInfo;
	private List<ItemUseRestrictionType> itemUseRestrictionTypes;

	private AcknowledgedFeeAmount acknowledgedFeeAmount;

	private GregorianCalendar dateOfUserRequest;

	private RequestStatusType requestStatusType;

	private GregorianCalendar earliestDateNeeded;

	private GregorianCalendar needBeforeDate;

	private PaidFeeAmount paidFeeAmount;

	private GregorianCalendar pickupExpiryDate;

	private PickupLocation pickupLocation;

	/**
	 * @return the problem
	 */
	public Problem getProblem() {
		return problem;
	}

	/**
	 * @param problem
	 *            the problem to set
	 */
	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	/**
	 * @return the userId
	 */
	public UserId getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(UserId userId) {
		this.userId = userId;
	}

	/**
	 * @return the itemId
	 */
	public ItemId getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(ItemId itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the requestId
	 */
	public RequestId getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId
	 *            the requestId to set
	 */
	public void setRequestId(RequestId requestId) {
		this.requestId = requestId;
	}

	public void addRequestId(String requestId) {
		if (this.requestId != null) {
			// This appends REQUEST_ID_DELIMITER = ";" to current identifier value & adds another request id to the end
			this.requestId.setRequestIdentifierValue(this.requestId.getRequestIdentifierValue() + AlephConstants.REQUEST_ID_DELIMITER + requestId);
		} else {
			RequestId reqId = new RequestId();
			reqId.setRequestIdentifierValue(requestId);
			this.requestId = reqId;
		}
	}

	/**
	 * @return the requestType
	 */
	public RequestType getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType
	 *            the requestType to set
	 */
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the holdQueueLength
	 */
	public BigDecimal getHoldQueueLength() {
		return holdQueueLength;
	}

	/**
	 * @param holdQueueLength
	 *            the holdQueueLength to set
	 */
	public void setHoldQueueLength(BigDecimal holdQueueLength) {
		this.holdQueueLength = holdQueueLength;
	}

	/**
	 * @return the holdQueuePosition
	 */
	public BigDecimal getHoldQueuePosition() {
		return holdQueuePosition;
	}

	/**
	 * @param holdQueuePosition
	 *            the holdQueuePosition to set
	 */
	public void setHoldQueuePosition(BigDecimal holdQueuePosition) {
		this.holdQueuePosition = holdQueuePosition;
	}

	/**
	 * @return the dateAvailable
	 */
	public GregorianCalendar getDateAvailable() {
		return dateAvailable;
	}

	/**
	 * @param dateAvailable
	 *            the dateAvailable to set
	 */
	public void setDateAvailable(GregorianCalendar dateAvailable) {
		this.dateAvailable = dateAvailable;
	}

	/**
	 * @return the holdPickupDate
	 */
	public GregorianCalendar getHoldPickupDate() {
		return holdPickupDate;
	}

	/**
	 * @param holdPickupDate
	 *            the holdPickupDate to set
	 */
	public void setHoldPickupDate(GregorianCalendar holdPickupDate) {
		this.holdPickupDate = holdPickupDate;
	}

	/**
	 * requestItem.set
	 * 
	 * @return the requestScopeType
	 */
	public RequestScopeType getRequestScopeType() {
		return requestScopeType;
	}

	/**
	 * @param requestScopeType
	 *            the requestScopeType to set
	 */
	public void setRequestScopeType(RequestScopeType requestScopeType) {
		this.requestScopeType = requestScopeType;
	}

	/**
	 * @return the requiredFeeAmount
	 */
	public RequiredFeeAmount getRequiredFeeAmount() {
		return requiredFeeAmount;
	}

	/**
	 * @param requiredFeeAmount
	 *            the requiredFeeAmount to set
	 */
	public void setRequiredFeeAmount(RequiredFeeAmount requiredFeeAmount) {
		this.requiredFeeAmount = requiredFeeAmount;
	}

	/**
	 * @return the userOptionalFields
	 */
	public UserOptionalFields getUserOptionalFields() {
		return userOptionalFields;
	}

	/**
	 * @param userOptionalFields
	 *            the userOptionalFields to set
	 */
	public void setUserOptionalFields(UserOptionalFields userOptionalFields) {
		this.userOptionalFields = userOptionalFields;
	}

	/**
	 * @return the itemOptionalFields
	 */
	public ItemOptionalFields getItemOptionalFields() {
		return itemOptionalFields;
	}

	/**
	 * @param itemOptionalFields
	 *            the itemOptionalFields to set
	 */
	public void setItemOptionalFields(ItemOptionalFields itemOptionalFields) {
		this.itemOptionalFields = itemOptionalFields;
	}

	/**
	 * @return the fiscalTransactionInfo
	 */
	public FiscalTransactionInformation getFiscalTransactionInfo() {
		return fiscalTransactionInfo;
	}

	/**
	 * @param fiscalTransactionInfo
	 *            the fiscalTransactionInfo to set
	 */
	public void setFiscalTransactionInfo(FiscalTransactionInformation fiscalTransactionInfo) {
		this.fiscalTransactionInfo = fiscalTransactionInfo;
	}

	/**
	 * @return the itemUseRestrictionTypes
	 */
	public List<ItemUseRestrictionType> getItemUseRestrictionTypes() {
		return itemUseRestrictionTypes;
	}

	/**
	 * @param itemUseRestrictionTypes
	 *            the itemUseRestrictionTypes to set
	 */
	public void setItemUseRestrictionTypes(List<ItemUseRestrictionType> itemUseRestrictionTypes) {
		this.itemUseRestrictionTypes = itemUseRestrictionTypes;
	}

	/**
	 * @return the shippingInformation
	 */
	public ShippingInformation getShippingInformation() {
		return shippingInformation;
	}

	/**
	 * @param shippingInformation
	 *            the shippingInformation to set
	 */
	public void setShippingInformation(ShippingInformation shippingInformation) {
		this.shippingInformation = shippingInformation;
	}

	public AcknowledgedFeeAmount getAcknowledgedFeeAmout() {
		return acknowledgedFeeAmount;
	}

	public void setAcknowledgedFeeAmount(AcknowledgedFeeAmount acknowledgedFeeAmount) {
		this.acknowledgedFeeAmount = acknowledgedFeeAmount;
	}

	public GregorianCalendar getDateOfUserRequest() {
		return dateOfUserRequest;
	}

	public void setDateOfUserRequest(GregorianCalendar dateOfUserRequest) {
		this.dateOfUserRequest = dateOfUserRequest;
	}

	public GregorianCalendar getEarliestDateNeeded() {
		return earliestDateNeeded;
	}

	public void setEarliestDateNeeded(GregorianCalendar earliestDateNeeded) {
		this.earliestDateNeeded = earliestDateNeeded;
	}

	public GregorianCalendar getNeedBeforeDate() {
		return needBeforeDate;
	}

	public void setNeedBeforeDate(GregorianCalendar needBeforeDate) {
		this.needBeforeDate = needBeforeDate;
	}

	public PaidFeeAmount getPaidFeeAmount() {
		return paidFeeAmount;
	}

	public void setPaidFeeAmount(PaidFeeAmount paidFeeAmount) {
		this.paidFeeAmount = paidFeeAmount;
	}

	public GregorianCalendar getPickupExpiryDate() {
		return pickupExpiryDate;
	}

	public void setPickupExpiryDate(GregorianCalendar pickupExpiryDate) {
		this.pickupExpiryDate = pickupExpiryDate;
	}

	public PickupLocation getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(PickupLocation pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public RequestStatusType getRequestStatusType() {
		return requestStatusType;
	}

	public void setRequestStatusType(RequestStatusType requestStatusType) {
		this.requestStatusType = requestStatusType;
	}

}
