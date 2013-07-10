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
import java.util.GregorianCalendar;

import org.codehaus.jackson.annotate.JsonProperty;
import org.extensiblecatalog.ncip.v2.service.*;

public class Hold {

	@JsonProperty("item")
	private Item item;
	
	@JsonProperty("position")
	private int position;
	
	
	@JsonProperty("datePlaced")
	private Date datePlaced;

	@JsonProperty("dateExpires")
	private Date dateExpires;
	
	@JsonProperty("pickupLibrary")
	private String pickupLibrary;
	
	@JsonProperty("holdNumber")
	private String holdNumber;

    @JsonProperty("availableFlag")
    private boolean available = false;

	/**
	 * @return the holdNumber
	 */
	public String getHoldNumber() {
		return holdNumber;
	}

	/**
	 * @param holdNumber the holdNumber to set
	 */
	public void setHoldNumber(String holdNumber) {
		this.holdNumber = holdNumber;
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

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the datePlaced
	 */
	public Date getDatePlaced() {
		return datePlaced;
	}

	/**
	 * @param datePlaced the datePlaced to set
	 */
	public void setDatePlaced(Date datePlaced) {
		this.datePlaced = datePlaced;
	}

	/**
	 * @return the dateExpires
	 */
	public Date getDateExpires() {
		return dateExpires;
	}

	/**
	 * @param dateExpires the dateExpires to set
	 */
	public void setDateExpires(Date dateExpires) {
		this.dateExpires = dateExpires;
	}

	/**
	 * @return the pickupLibrary
	 */
	public String getPickupLibrary() {
		return pickupLibrary;
	}

	/**
	 * @param pickupLibrary the pickupLibrary to set
	 */
	public void setPickupLibrary(String pickupLibrary) {
		this.pickupLibrary = pickupLibrary;
	}
	
	public String toString() {
		return String.format("Hold [%s] %s (%s); pick up @ %s.  Placed %s, expires %s: ",
				holdNumber, item.getTitle(), item.getItemId(), pickupLibrary, datePlaced, dateExpires);
	}


    public RequestedItem toRequestedItem() {
        RequestedItem ritem = new RequestedItem();
        ritem.setItemId( item.getNCIPItemId() );
        ritem.setTitle(item.getTitle());

        GregorianCalendar placed = new GregorianCalendar();
        placed.setTime(getDatePlaced());
        ritem.setDatePlaced(placed);

        GregorianCalendar expires = new GregorianCalendar();
        expires.setTime(getDateExpires());
        ritem.setPickupExpiryDate(expires);

        /**
         * This checks the static "cache" of locations first,
         * creating the location if the library name has not
         * yet been seen -- as a side effect, new PickupLocation
         * adds the value to the cache.
         */
        PickupLocation location = null;
        try {
            location = PickupLocation.find(null, getPickupLibrary());
        } catch( ServiceException sx ) {
            location = new PickupLocation(getPickupLibrary());
        }

        ritem.setPickupLocation( location );

        ritem.setHoldQueuePosition(new BigDecimal(position));

        RequestId reqId = new RequestId();
        reqId.setRequestIdentifierValue(holdNumber);
        ritem.setRequestId(reqId);

        ritem.setRequestType( Version1RequestType.HOLD );
        if ( available ) {
            ritem.setRequestStatusType(Version1RequestStatusType.AVAILABLE_FOR_PICKUP);
        } else {
            ritem.setRequestStatusType(Version1RequestStatusType.IN_PROCESS);
        }

        return ritem;

    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
