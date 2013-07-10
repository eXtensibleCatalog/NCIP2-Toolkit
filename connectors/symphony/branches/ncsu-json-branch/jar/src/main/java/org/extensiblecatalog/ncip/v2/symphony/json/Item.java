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
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.MediumType;
import org.extensiblecatalog.ncip.v2.symphony.ItemTypeMapper;

import java.util.Date;


/**
 * Represents an item that can be on loan, or on hold, or to which a bill may pertain.
 * Instances of this class pertain to the durable properties of the item, rather than
 * to contextual facts about the item.
 * @see Charge
 * @see Hold
 * @see Bill
 */
public class Item {
	
	
	@JsonProperty("itemID")
	private String itemId;
	
	@JsonProperty("catalogKey")
	private String catalogKey;

	@JsonProperty("callNumberDisplay")
	private String callNumber;
	
	@JsonProperty("authorName")
	private String authorName;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("type")
	private String itemType;
	
	@JsonProperty("displayLibrary")
	private String library;
	
	@JsonProperty("copyNumber")
	private String copyNumber;

    @JsonProperty("dateDueDisplay")
    private Date dueDate;

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the catalogKey
	 */
	public String getCatalogKey() {
		return catalogKey;
	}

	/**
	 * @param catalogKey the catalogKey to set
	 */
	public void setCatalogKey(String catalogKey) {
		this.catalogKey = catalogKey;
	}

	/**
	 * @return the callNumber
	 */
	public String getCallNumber() {
		return callNumber;
	}

	/**
	 * @param callNumber the callNumber to set
	 */
	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * @return the library
	 */
	public String getLibrary() {
		return library;
	}

	/**
	 * @param library the library to set
	 */
	public void setLibrary(String library) {
		this.library = library;
	}
	
	public String toString() {
			return String.format("[%s] %s (author: %s) barcode: %s",catalogKey,title,authorName,itemId);
	}

	/**
	 * @return the copyNumber
	 */
	public String getCopyNumber() {
		return copyNumber;
	}

	/**
	 * @param copyNumber the copyNumber to set
	 */
	public void setCopyNumber(String copyNumber) {
		this.copyNumber = copyNumber;
	}

    public ItemId getNCIPItemId() {
        ItemId iid = new ItemId();
        iid.setItemIdentifierValue(getItemId());
        return iid;
    }

    public MediumType getMediumType() {
        return ItemTypeMapper.getMediumType(itemType);
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
