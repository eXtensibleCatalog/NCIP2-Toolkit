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

import java.util.Date;

/**
 * DTO for representing information returned by operations that deal with items (renewals, request cancellations)
 * @author adam_constabaris@ncsu.edu
 *
 */
public class ItemOperationInfo {

    private static final Date _DEFAULT_DUEDATE = new Date(Long.MIN_VALUE);

    @JsonProperty("charge")
    private Charge charge;


    @JsonProperty("item")
    private Item item;


    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Gets the due date, if any, associated with the operation.
     * It seems that sometimes, the API wrapper returns the new due date for a Renew operation as a
     * property of the Item, sometimes as a property of the Charge.  This tries valiantly to
     * return a non-<code>null</code> value by checking first the charge, then the item associated
     * with this response bundle.
     * @return the due date associated with <code>charge</code>, or if null, with <code>item</code>; if
     * neither property provides a useful date, returns <code>new Date(Long.MIN_VALUE)</code>
     */
    public Date getDueDate() {
        if ( charge != null ) {
            if ( charge.getDateDueDisplay() != null ) {
                return charge.getDateDueDisplay();
            }
        }
        if ( item != null ) {
            if ( item.getDueDate() != null ) {
                return item.getDueDate();
            }
        }
        return new Date(_DEFAULT_DUEDATE.getTime());
    }
}
