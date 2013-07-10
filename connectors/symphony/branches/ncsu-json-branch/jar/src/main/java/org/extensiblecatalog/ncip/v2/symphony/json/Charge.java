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

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.extensiblecatalog.ncip.v2.service.LoanedItem;
import org.extensiblecatalog.ncip.v2.symphony.ConversionUtils;

/**
 * DTO for a checked-out item.
 * @author adam_constabaris@ncsu.edu
 */
public class Charge {
	
	@JsonProperty("dateCharged")
	private Date dateCharged;
	
	@JsonProperty("dateDueDisplay")
	private Date dateDueDisplay;
	
	private String circRule;

	@JsonProperty("item")
	private Item item;
	
	@JsonProperty("numRenewalsRemaining")
	private int renewalsRemaining;

    @JsonProperty("recallNoticeCount")
    private int recallNoticeCount = 0;

	/**
	 * @return the dateCharged
	 */
	public Date getDateCharged() {
		return dateCharged;
	}

	/**
	 * @param dateCharged the dateCharged to set
	 */
	public void setDateCharged(Date dateCharged) {
		this.dateCharged = dateCharged;
	}

	/**
	 * @return the dateDueDisplay
	 */
	public Date getDateDueDisplay() {
		return dateDueDisplay;
	}

	/**
	 * @param dateDueDisplay the dateDueDisplay to set
	 */
	public void setDateDueDisplay(Date dateDueDisplay) {
		this.dateDueDisplay = dateDueDisplay;
	}

	/**
	 * @return the circRule
	 */
	public String getCircRule() {
		return circRule;
	}

	/**
	 * @param circRule the circRule to set
	 */
	public void setCircRule(String circRule) {
		this.circRule = circRule;
	}

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
	
	public String toString() {
		return String.format("%s : checked out %s, due %s", item.getTitle(), getDateCharged(), getDateDueDisplay());
	}

	/**
	 * @return the renewalsRemaining
	 */
	public int getRenewalsRemaining() {
		return renewalsRemaining;
	}

	/**
	 * @param renewalsRemaining the renewalsRemaining to set
	 */
	public void setRenewalsRemaining(int renewalsRemaining) {
		this.renewalsRemaining = renewalsRemaining;
	}

    public LoanedItem toLoanedItem()
    {
        LoanedItem litem = new LoanedItem();
        litem.setItemId( this.getItem().getNCIPItemId() );
        litem.setDateDue(ConversionUtils.toGregorianCalendar(getDateDueDisplay()));
        // this value has to be present, and has to be a positive integer; which seems
        // odd because maybe you haven't got any recall notices?  So let's just add one
        // to the value because that seems least ambiguous
        litem.setReminderLevel( new BigDecimal(getRecallNoticeCount()+1) );
        litem.setAmount( new Amount().toNCIPAmount() );
        litem.setTitle(item.getTitle());
        litem.setMediumType( item.getMediumType() );
        return litem;
    }

    public int getRecallNoticeCount() {
        return recallNoticeCount;
    }

    public void setRecallNoticeCount(int recallNoticeCount) {
        this.recallNoticeCount = recallNoticeCount;
    }
}
