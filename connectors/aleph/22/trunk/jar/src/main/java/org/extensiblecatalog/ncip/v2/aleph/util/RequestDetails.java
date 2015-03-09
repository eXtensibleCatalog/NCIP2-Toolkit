package org.extensiblecatalog.ncip.v2.aleph.util;

import java.util.GregorianCalendar;

import org.extensiblecatalog.ncip.v2.service.AcknowledgedFeeAmount;
import org.extensiblecatalog.ncip.v2.service.PaidFeeAmount;
import org.extensiblecatalog.ncip.v2.service.PickupLocation;
import org.extensiblecatalog.ncip.v2.service.RequestStatusType;

public class RequestDetails {
	private AcknowledgedFeeAmount acknowledgedFeeAmount;
	private GregorianCalendar dateOfUserRequest;
	private GregorianCalendar earliestDateNeeded;
	private GregorianCalendar needBeforeDate;
	private PaidFeeAmount paidFeeAmount;
	private GregorianCalendar pickupDate;
	private GregorianCalendar pickupExpiryDate;
	private PickupLocation pickupLocation;
	private RequestStatusType requestStatusType;

	public AcknowledgedFeeAmount getAcknowledgedFeeAmout() {
		return acknowledgedFeeAmount;
	}

	public void setAcknowledgedFeeAmount(AcknowledgedFeeAmount acknowledgedFeeAmount) {
		this.acknowledgedFeeAmount = acknowledgedFeeAmount;
	}

	public GregorianCalendar getDatePlaced() {
		return dateOfUserRequest;
	}

	public void setDatePlaced(GregorianCalendar dateOfUserRequest) {
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

	public void setPickupDate(GregorianCalendar pickupDate) {
		this.pickupDate = pickupDate;
	}

	public GregorianCalendar getPickupDate() {
		return pickupDate;
	}
}
