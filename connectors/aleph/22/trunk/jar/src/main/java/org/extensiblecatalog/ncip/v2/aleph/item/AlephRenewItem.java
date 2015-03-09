package org.extensiblecatalog.ncip.v2.aleph.item;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

import org.extensiblecatalog.ncip.v2.binding.ncipv2_02.jaxb.elements.RenewItem;
import org.extensiblecatalog.ncip.v2.service.FiscalTransactionInformation;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType;
import org.extensiblecatalog.ncip.v2.service.Pending;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.RequiredFeeAmount;
import org.extensiblecatalog.ncip.v2.service.UserOptionalFields;

public class AlephRenewItem extends RenewItem {

	private Problem problem;
	private Pending pending;
	private BigDecimal renewalCount;
	private GregorianCalendar dateDue;
	private GregorianCalendar dateForReturn;
	private RequiredFeeAmount requiredFeeAmount;
	private ItemOptionalFields itemOptionalFields;
	private UserOptionalFields userOptionalFields;
	private FiscalTransactionInformation fiscalTransactionInfo;
	private List<ItemUseRestrictionType> requiredItemUseRestrictionTypes;

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
	 * @return dateDue
	 */
	public GregorianCalendar getDateDue() {
		return dateDue;
	}

	/**
	 * @param dateDue
	 */
	public void setDateDue(GregorianCalendar dateDue) {
		this.dateDue = dateDue;
	}

	public List<ItemUseRestrictionType> getRequiredItemUseRestrictionTypes() {
		return requiredItemUseRestrictionTypes;
	}

	public void setRequiredItemUseRestrictionTypes(List<ItemUseRestrictionType> requiredItemUseRestrictionTypes) {
		this.requiredItemUseRestrictionTypes = requiredItemUseRestrictionTypes;
	}

	public RequiredFeeAmount getRequiredFeeAmount() {
		return requiredFeeAmount;
	}

	public void setRequiredFeeAmount(RequiredFeeAmount requiredFeeAmount) {
		this.requiredFeeAmount = requiredFeeAmount;
	}

	public BigDecimal getRenewalCount() {
		return renewalCount;
	}

	public void setRenewalCount(BigDecimal renewalCount) {
		this.renewalCount = renewalCount;
	}

	public Pending getPending() {
		return pending;
	}

	public void setPending(Pending pending) {
		this.pending = pending;
	}

	public GregorianCalendar getDateForReturn() {
		return dateForReturn;
	}

	public void setDateForReturn(GregorianCalendar dateForReturn) {
		this.dateForReturn = dateForReturn;
	}
}
