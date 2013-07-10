package org.extensiblecatalog.ncip.v2.millennium;

/**
 * Manages the data which may be found in an NCIP Item Object.
 */
public class NCIPItem
{
	
	/**
	 * The Item's call number
	 */
	private String callNumber = null;
	
	/**
	 * True if any fields besides holdingAgency or itemIdentifier have been set.
	 */
	private boolean hasOptionalData = false;
	
	/**
	 * The Item's circulation status
	 */
	private String circulationStatus = null;
	
	/**
	 * The Item's electronic resource
	 */
	private String electronicResource = null;
	
	/**
	 * The Item's hold queue length
	 */
	private int holdQueueLength = -1;
	
	/**
	 * The Item's physical description (will go in PhysicalCondition section)
	 */
	private String itemDescription = null;
		
	/**
	 * The Item's location
	 */
	private String location = null;
	
	/**
	 * The Item's author
	 */
	private String author = null;
	
	/**
	 * The Item's ISBN code
	 */
	private String isbn = null;
	
	/**
	 * The Item's bibliographic ID
	 */
	private String bibId = null;
	
	/**
	 * The Item's holdings ID
	 */
	private String holdingsId = null;
	
	/**
	 * Holdings Information
	 */
	private String holdingsInfo = null;
	
	/**
	 * The Item's item ID
	 */
	private String itemId = null;
	
	/**
	 * The type of medium the Item is
	 */
	private String mediumType = null;
	
	/**
	 * Publication information about the Item
	 */
	private String publisher = null;
	
	/**
	 * The series to which the Item belongs
	 */
	private String series = null;
	
	/**
	 * The Item's title
	 */
	private String title = null;
	
	/**
	 * Gets the item's circulation status
	 * 
	 * @return The item's circulation status
	 */
	public String getCirculationStatus()
	{
		return circulationStatus;
	}

	/**
	 * Gets the item's electronic resource
	 * 
	 * @return The item's electronic resource
	 */
	public String getElectronicResource()
	{
		return electronicResource;
	}

	/**
	 * Gets the length of the item's hold queue
	 * 
	 * @return The length of the item's hold queue
	 */
	public int getHoldQueueLength()
	{
		return holdQueueLength;
	}

	/**
	 * Gets the item's description
	 * 
	 * @return The item's description
	 */
	public String getItemDescription()
	{
		return itemDescription;
	}
	
	/**
	 * Gets the item's location
	 * 
	 * @return The item's location
	 */
	public String getLocation()
	{
		return location;
	}
	
	/**
	 * Returns the item's call number
	 * 
	 * @return The item's call number
	 */
	public String getCallNumber()
	{
		return callNumber;
	}
	
	/**
	 * Returns the item's author
	 * 
	 * @return The item's author
	 */
	public String getAuthor()
	{
		return author;
	}

	/**
	 * Returns the item's ISBN number
	 * 
	 * @return The item's ISBN number
	 */
	public String getIsbn()
	{
		return isbn;
	}

	/**
	 * Returns the item's bibliographic ID
	 * 
	 * @return The item's bibliographic ID
	 */
	public String getBibId()
	{
		return bibId;
	}
	
	/**
	 * Returns the item's holdings ID
	 * 
	 * @return The item's holdings ID
	 */
	public String getHoldingsId()
	{
		return holdingsId;
	}

	public String getHoldingsInfo()
	{
	    return holdingsInfo;
	}
	
	/**
	 * Returns the item's item ID
	 * 
	 * @return The item's item ID
	 */
	public String getItemId()
	{
		return itemId;
	}
	
	/**
	 * Returns the item's medium type (Book, CD, etc.)
	 * 
	 * @return The item's medium type
	 */
	public String getMediumType()
	{
		return mediumType;
	}

	/**
	 * Returns the item's publisher
	 * 
	 * @return The item's publisher
	 */
	public String getPublisher()
	{
		return publisher;
	}

	/**
	 * Returns the name of the series to which the item belongs
	 * 
	 * @return The name of the item's series
	 */
	public String getSeries()
	{
		return series;
	}

	/**
	 * Returns the item's title
	 * 
	 * @return The item's title
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * Sets the item's author
	 * 
	 * @param author The item's new author
	 */
	public void setAuthor(String author)
	{
		this.author = author;
	}

	/**
	 * Sets the item's ISBN number
	 * 
	 * @param isbn The item's new ISBN number
	 */
	public void setIsbn(String isbn)
	{
		this.isbn = isbn;
	}

	/**
	 * Sets the item's bibliographic ID
	 * 
	 * @param bibId The item's new bibliographic ID
	 */
	public void setBibId(String bibId)
	{
		this.bibId = bibId;
	}
	
	/**
	 * Sets the item's holdings ID
	 * 
	 * @param bibId The item's new holdings ID
	 */
	public void setHoldingsId(String holdingsId)
	{
		this.holdingsId = holdingsId;
	}
	
	public void setHoldingsInfo(String holdingsInfo) 
	{
	    this.holdingsInfo = holdingsInfo;
	}
	
	/**
	 * Sets the item's item ID
	 * 
	 * @param bibId The item's new item ID
	 */
	public void setItemId(String itemId)
	{
		this.itemId = itemId;
	}

	/**
	 * Sets the item's medium type (Book, CD, etc.)
	 * 
	 * @param circulationStatus The item's new medium type
	 */
	public void setMediumType(String mediumType)
	{
		this.mediumType = mediumType;
	}

	/**
	 * Sets the item's publisher
	 * 
	 * @param publisher The item's new publisher
	 */
	public void setPublisher(String publisher)
	{
		this.publisher = publisher;
	}

	/**
	 * Sets the series to which the item belongs
	 * 
	 * @param series The item's new series
	 */
	public void setSeries(String series)
	{
		this.series = series;
	}

	/**
	 * Sets the item's title
	 * 
	 * @param title The item's new title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Sets the item's circulation status
	 * 
	 * @param circulationStatus The item's new circulation status
	 */
	public void setCirculationStatus(String circulationStatus)
	{
		hasOptionalData = true;
		this.circulationStatus = circulationStatus;
	}

	/**
	 * Sets the item's electronic resource
	 * 
	 * @param electronicResource The item's new electronic resource
	 */
	public void setElectronicResource(String electronicResource)
	{
		hasOptionalData = true;
		this.electronicResource = electronicResource;
	}

	/**
	 * Sets the length of the item's hold queue
	 * 
	 * @param holdQueueLength The new length of the item's hold queue
	 */
	public void setHoldQueueLength(int holdQueueLength)
	{
		hasOptionalData = true;
		this.holdQueueLength = holdQueueLength;
	}

	/**
	 * Sets the item's description
	 * 
	 * @param itemDescription The item's new description
	 */
	public void setItemDescription(String itemDescription)
	{
		hasOptionalData = true;
		this.itemDescription = itemDescription;
	}

	/**
	 * Sets the item's location
	 * 
	 * @param location The item's new location
	 */
	public void setLocation(String location)
	{
		hasOptionalData = true;
		this.location = location;
	}
	
	/**
	 * Sets the item's call number
	 * 
	 * @param callNumber The item's new call number
	 */
	public void setCallNumber(String callNumber)
	{
		this.callNumber = callNumber;
	}
	
}