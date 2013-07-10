/**
 * Copyright (c) 2012 North Carolina State University
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.extensiblecatalog.ncip.v2.symphony.json;

import org.codehaus.jackson.annotate.JsonProperty;
import org.extensiblecatalog.ncip.v2.service.*;
import org.extensiblecatalog.ncip.v2.symphony.ConversionUtils;
import org.extensiblecatalog.ncip.v2.symphony.ItemTypeMapper;

import java.util.*;

/**
 * Represents a Bill (e.g. a service charge, overdue fine), including zero or more payments made on the bill.
 * @author adam_constabaris@ncsu.edu, $LastChangedBy$
 * @since 1.2
 * @version $LastChangedRevision$
 */
public class Bill {
	
	@JsonProperty("item")
	private Item item;

	@JsonProperty("numNotices")
	private int notices;

	
	@JsonProperty("reason")
	private String reason;
	
	@JsonProperty("reasonDisplay")
	private String reasonDisplay;
	
	@JsonProperty("dateBilled")
	private Date dateBilled;

	@JsonProperty("amountBilled")
	private Amount amount;
	
	@JsonProperty("total")
	private Amount outstandingAmount;

	@JsonProperty("payments")
	private List<Payment> payments = new ArrayList<Payment>();

    private static final AgencyId agencyId = new AgencyId("NCSU");

	/**
     * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return the notices
	 */
	public int getNotices() {
		return notices;
	}

	/**
	 * @param notices the notices to set
	 */
	public void setNotices(int notices) {
		this.notices = notices;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the amount
	 */
	public Amount getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	/**
	 * @return the payments
	 */
	public List<Payment> getPayments() {
		return payments;
	}

	/**
	 * @param payments the payments to set
	 */
	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	/**
	 * @return the reasonDisplay
	 */
	public String getReasonDisplay() {
		return reasonDisplay;
	}

	/**
	 * @param reasonDisplay the reasonDisplay to set
	 */
	public void setReasonDisplay(String reasonDisplay) {
		this.reasonDisplay = reasonDisplay;
	}

	/**
	 * @return the dateBilled
	 */
	public Date getDateBilled() {
		return dateBilled;
	}

	/**
	 * @param dateBilled the dateBilled to set
	 */
	public void setDateBilled(Date dateBilled) {
		this.dateBilled = dateBilled;
	}

	/**
	 * @return the outstandingAmount
	 */
	public Amount getOutstandingAmount() {
		return outstandingAmount;
	}

	/**
	 * @param outstandingAmount the outstandingAmount to set
	 */
	public void setOutstandingAmount(Amount outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

    private final static Map<String,FiscalTransactionType> transTypes = new HashMap<String,FiscalTransactionType>()
    {/**
		 * 
		 */
		private static final long	serialVersionUID	= 1L;

	{
            put("DAMAGE", Version1FiscalTransactionType.BOOK_REPLACEMENT_CHARGE);
            put("LONGOVRDUE", Version1FiscalTransactionType.FINE);
            put("OVERDUE", Version1FiscalTransactionType.FINE);
            put("PROCESSFEE", Version1FiscalTransactionType.SERVICE_CHARGE);
            put("LOST", Version1FiscalTransactionType.BOOK_REPLACEMENT_CHARGE);
            put("TRANPROC", Version1FiscalTransactionType.SERVICE_CHARGE);
            put("TRANOVD", Version1FiscalTransactionType.FINE);
            put("RECALLOVD", Version1FiscalTransactionType.FINE);
    }};

    public FiscalTransactionType getTransactionType() {
        FiscalTransactionType ft = transTypes.get(getReason());
        return ft != null ? ft : Version1FiscalTransactionType.SERVICE_CHARGE;
    }

    /**
     * Converts this bill into a collection of objects that can be supplied in an 
     * NCIP response.
     * @param billNumber the ordinal number of the bill in the set of bills associated with 
     * a single user lookup; this is used to create internal references between bills and 
     * payments.
     * @return
     */
    public List<AccountDetails> getAccountDetails(int billNumber) {
        // my precious!
        List<AccountDetails> accountDetailses = new ArrayList<AccountDetails>();
        AccountDetails details = new AccountDetails();
        details.setAccrualDate(ConversionUtils.toGregorianCalendar(getDateBilled()));
        FiscalTransactionInformation info = new FiscalTransactionInformation();
        info.setFiscalTransactionDescription(getReasonDisplay());
        info.setFiscalTransactionType(getTransactionType());
        info.setFiscalActionType(Version1FiscalActionType.PENALTY);
        info.setAmount(getAmount().toNCIPAmount());


        FiscalTransactionReferenceId billId = new FiscalTransactionReferenceId();
        String billIdentifier = "bill-" + billNumber;
        billId.setFiscalTransactionIdentifierValue(billIdentifier);
        billId.setAgencyId(agencyId);
        info.setFiscalTransactionReferenceId(billId);

        if (getItem() != null) {
            ItemDetails itemDetails = new ItemDetails();
            itemDetails.setItemId(getItem().getNCIPItemId());
            BibliographicDescription desc = new BibliographicDescription();
            desc.setAuthor(getItem().getAuthorName());
            desc.setTitle(getItem().getTitle());
            desc.setMediumType(ItemTypeMapper.getMediumType(getItem().getItemType()));
            itemDetails.setBibliographicDescription(desc);
            info.setItemDetails(itemDetails);
        }

        details.setFiscalTransactionInformation(info);

        accountDetailses.add(details);
        if (!getPayments().isEmpty()) {
            info.setRelatedFiscalTransactionReferenceIds(new ArrayList<RelatedFiscalTransactionReferenceId>());

            int paymentNumber = 1;
            for (Payment p : getPayments()) {
                AccountDetails pd = new AccountDetails();
                String paymentIdentifier = billIdentifier + "-payment-" + paymentNumber;
                pd.setAccrualDate(ConversionUtils.toGregorianCalendar(p.getDatePaid()));
                FiscalTransactionInformation pTrans = new FiscalTransactionInformation();
                pTrans.setFiscalActionType(Version1FiscalActionType.PAYMENT);
                pTrans.setFiscalTransactionType(Version1FiscalTransactionType.PURCHASE);

                pTrans.setAmount(p.getAmount().toNCIPAmount());

                FiscalTransactionReferenceId refId = new FiscalTransactionReferenceId();
                refId.setFiscalTransactionIdentifierValue(paymentIdentifier);
                refId.setAgencyId(agencyId);

                pTrans.setFiscalTransactionReferenceId(refId);

                RelatedFiscalTransactionReferenceId relatedId = new RelatedFiscalTransactionReferenceId();
                relatedId.setFiscalTransactionIdentifierValue(paymentIdentifier);
                relatedId.setAgencyId(agencyId);
                info.getRelatedFiscalTransactionReferenceIds().add(relatedId);
                paymentNumber++;
                pd.setFiscalTransactionInformation(pTrans);
                accountDetailses.add(pd);
            }
        }
        return accountDetailses;
    }




}
