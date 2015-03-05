package org.extensiblecatalog.ncip.v2.koha.item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.extensiblecatalog.ncip.v2.koha.agency.KohaAgency;
import org.extensiblecatalog.ncip.v2.koha.user.KohaUser;
import org.extensiblecatalog.ncip.v2.koha.util.KohaConstants;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemId;
import org.extensiblecatalog.ncip.v2.service.CirculationStatus;
import org.extensiblecatalog.ncip.v2.service.ElectronicResource;
import org.extensiblecatalog.ncip.v2.service.HoldingsInformation;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType;
import org.extensiblecatalog.ncip.v2.service.MediumType;
import org.extensiblecatalog.ncip.v2.service.Version1ItemIdentifierType;

/**
 * An item returned from Koha
 * 
 * @author Rick Johnson (NDU) & Jiří Kozlovský (KOH)
 *
 */
public class KohaItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 163979327947903965L;

	private List<ItemUseRestrictionType> itemUseRestrictionTypes;


	private int holdQueueLength = -1;

	private BigDecimal numberOfPieces;
	private BigDecimal fineAmount;

	private Date dateAvailablePickup;
	private Date dateHoldRequested;
	private Date fineAccrualDate;
	private Date dueDate;

	private ElectronicResource electronicResource;

	private CirculationStatus circulationStatus;

	private BibliographicDescription barcode;

	private HoldingsInformation description;

	private BibliographicItemId isbn;

	private MediumType mediumType;

	private KohaAgency agency;

	// Item identifiers
	private String holdingsId;
	private ItemId itemId;
	private String bibId;

	// Bib description:
	private String callNumber;
	private String author;
	private String publisher;
	private String series;
	private String title;
	private String copyNumber;

	// Item locations
	private String location;
	private String collection;

	// User variables
	private String fineStatus;
	private String creditDebit;
	private String holdRequestId;
	private String sessionId;

	private List<KohaUser> borrowingUsers;

	private List<KohaUser> requestingUsers;

	public KohaItem() {
		itemUseRestrictionTypes = new ArrayList<ItemUseRestrictionType>();
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}


	public List<ItemUseRestrictionType> getItemUseRestrictionTypes() {
		return itemUseRestrictionTypes;
	}

	/**
	 * @return the bibId
	 */
	public String getBibId() {
		return bibId;
	}


	/**
	 * @return the admId
	 */
	public ItemId getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the admId to set
	 */
	public void setItemId(ItemId itemId) {
		this.itemId = itemId;
	}

	public void setItemId(String itemIdValue) {
		ItemId itemId = new ItemId();
		itemId.setItemIdentifierValue(itemIdValue);
		itemId.setItemIdentifierType(Version1ItemIdentifierType.ACCESSION_NUMBER);
		this.itemId = itemId;
	}

	/**
	 * @return the description
	 */
	public HoldingsInformation getDescription() {
		return description;
	}

	public void setDescription(HoldingsInformation description) {
		this.description = description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = KohaUtil.createHoldingsInformationUnscructured(description);
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the callNumber
	 */
	public String getCallNumber() {
		return callNumber;
	}

	/**
	 * @param callNumber
	 *            the callNumber to set
	 */
	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
		// check for any special characters that should be removed
		if (callNumber != null) {
			this.callNumber = this.callNumber.replaceAll("&nbsp;", " ");
		}
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the isbn
	 */
	public BibliographicItemId getIsbn() {
		return isbn;
	}

	public void setIsbn(BibliographicItemId isbn) {
		this.isbn = isbn;
	}

	/**
	 * @param isbn
	 *            the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = KohaUtil.createBibliographicItemIdAsISBN(isbn);
	}

	/**
	 * @return the mediumType
	 */
	public MediumType getMediumType() {
		return mediumType;
	}

	/**
	 * @param mediumType
	 *            the mediumType to set
	 */
	public void setMediumType(MediumType mediumType) {
		this.mediumType = mediumType;
	}

	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher
	 *            the publisher to set
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return the series
	 */
	public String getSeries() {
		return series;
	}

	/**
	 * @param series
	 *            the series to set
	 */
	public void setSeries(String series) {
		this.series = series;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the circulationStatus
	 */
	public CirculationStatus getCirculationStatus() {
		return circulationStatus;
	}

	/**
	 * @param circulationStatus
	 *            the circulationStatus to set
	 */
	public void setCirculationStatus(CirculationStatus circulationStatus) {
		this.circulationStatus = circulationStatus;
	}

	/**
	 * @return the electronicResource
	 */
	public ElectronicResource getElectronicResource() {
		return electronicResource;
	}

	public void setElectronicResource(ElectronicResource electronicResource) {
		this.electronicResource = electronicResource;
	}

	/**
	 * @param electronicResource
	 *            the electronicResource to set
	 */
	public void setElectronicResource(String electronicResource) {

		ElectronicResource resource = new ElectronicResource();
		resource.setReferenceToResource(electronicResource);
		this.electronicResource = resource;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @return the holdingsId
	 */
	public String getHoldingsId() {
		return holdingsId;
	}

	/**
	 * @param holdQueueLength
	 *            the holdQueueLength to set
	 */
	public void setHoldQueueLength(int holdQueueLength) {
		this.holdQueueLength = holdQueueLength;
	}

	/**
	 * @return the holdQueueLength
	 */
	public int getHoldQueueLength() {
		return holdQueueLength;
	}

	/**
	 * Add a borrowing user to this koha item. It will not add it to the internal list if it already exists
	 * 
	 * @param user
	 */
	public void addBorrowingUser(KohaUser user) {
		if (!borrowingUsers.contains(user)) {
			borrowingUsers.add(user);
		}
	}

	/**
	 * @return the borrowingUsers
	 */
	public List<KohaUser> getBorrowingUsers() {
		return borrowingUsers;
	}

	/**
	 * Add a requesting user to this koha item. It will not add it to the internal list if it already exists
	 * 
	 * @param user
	 */
	public void addRequestingUser(KohaUser user) {
		if (!requestingUsers.contains(user)) {
			requestingUsers.add(user);
		}
	}

	/**
	 * @return the requestingUsers
	 */
	public List<KohaUser> getRequestingUsers() {
		return requestingUsers;
	}

	/**
	 * @param barcode
	 *            the barcode to set
	 */
	public void setBarcode(String barcode) {
		BibliographicDescription bibDesc = new BibliographicDescription();

		BibliographicItemId bibId = KohaUtil.createBibliographicItemIdAsLegalDepositNumber(barcode);

		bibDesc.setBibliographicItemIds(Arrays.asList(bibId));
		this.barcode = bibDesc;
	}

	public void setBarcode(BibliographicDescription barcode) {
		this.barcode = barcode;
	}

	/**
	 * @return the barcode
	 */
	public BibliographicDescription getBarcode() {
		return barcode;
	}

	public String getBarcodeValue() {
		return barcode.getBibliographicItemId(0).getBibliographicItemIdentifier();
	}

	/**
	 * @param dateHoldRequested
	 *            the dateHoldRequested to set
	 */
	public void setDateHoldRequested(Date dateHoldRequested) {
		this.dateHoldRequested = dateHoldRequested;
	}

	/**
	 * @return the dateHoldRequested
	 */
	public Date getDateHoldRequested() {
		return dateHoldRequested;
	}

	/**
	 * @param dateAvailablePickup
	 *            the dateAvailablePickup to set
	 */
	public void setDateAvailablePickup(Date dateAvailablePickup) {
		this.dateAvailablePickup = dateAvailablePickup;
	}


	/**
	 * @return the dateAvailablePickup
	 */
	public Date getDateAvailablePickup() {
		return dateAvailablePickup;
	}

	/**
	 * @param dueDate
	 *            the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	/**
	 * Parse date from string and set to value if parsing successful
	 * 
	 * @param date
	 * @throws ParseException
	 */
	public void setDueDate(String date, String format) throws ParseException {
		// assume date in right format and parse, ignore empty values
		if (date != null && date.length() > 0) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
			setDueDate(dateFormatter.parse(date));
		}
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param fineAmount
	 *            the fineAmount to set
	 */
	public void setFineAmount(double fineAmount) {
		this.fineAmount = new BigDecimal(fineAmount);
		this.fineAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * @return the fineAmount
	 */
	public BigDecimal getFineAmount() {
		return fineAmount;
	}

	/**
	 * Try to set the fine amount from the string passed in
	 * 
	 * @param fineAmount
	 */
	public void setFineAmount(String fineAmount) {
		try {
			setFineAmount(Double.parseDouble(fineAmount));
		} catch (NumberFormatException nfe) {
			// do nothing...make
		}
	}

	/**
	 * @param fineAccrualDate
	 *            the fineAccrualDate to set
	 */
	public void setFineAccrualDate(Date fineAccrualDate) {
		this.fineAccrualDate = fineAccrualDate;
	}

	/**
	 * @return the fineAccrualDate
	 */
	public Date getFineAccrualDate() {
		return fineAccrualDate;
	}

	/**
	 * @param fineStatus
	 *            the fineStatus to set
	 */
	public void setFineStatus(String fineStatus) {
		this.fineStatus = fineStatus;
	}

	/**
	 * @return the fineStatus
	 */
	public String getFineStatus() {
		return fineStatus;
	}

	/**
	 * @param creditDebit
	 *            the creditDebit to set
	 */
	public void setCreditDebit(String creditDebit) {
		this.creditDebit = creditDebit;
	}

	/**
	 * @return the creditDebit
	 */
	public String getCreditDebit() {
		return creditDebit;
	}

	/**
	 * @param holdRequestId
	 *            the holdRequestId to set
	 */
	public void setHoldRequestId(String holdRequestId) {
		this.holdRequestId = holdRequestId;
	}

	/**
	 * @return the holdRequestId
	 */
	public String getHoldRequestId() {
		return holdRequestId;
	}

	/**
	 * @param agency
	 *            the agency to set
	 */
	public void setAgency(KohaAgency agency) {
		this.agency = agency;
	}

	/**
	 * @return the agency
	 */
	public KohaAgency getAgency() {
		return agency;
	}

	/**
	 * This method will update this overwrite anything in this kohaItem with the kohaItem's values passed in if they are not null.
	 * 
	 * @param item
	 */
	public void updateFromKohaItem(KohaItem item) {
		if (item != null) {
			if (item.getAgency() != null) {
				this.setAgency(item.getAgency());
			}
			if (item.getAuthor() != null) {
				this.setAuthor(item.getAuthor());
			}
			if (item.getBarcode() != null) {
				this.setBarcode(item.getBarcode());
			}
			if (item.getBorrowingUsers() != null) {
				for (KohaUser user : item.getBorrowingUsers()) {
					this.addBorrowingUser(user);
				}
			}
			if (item.getCallNumber() != null) {
				this.setCallNumber(item.getCallNumber());
			}
			if (item.getCirculationStatus() != null) {
				this.setCirculationStatus(item.getCirculationStatus());
			}
			if (item.getCreditDebit() != null) {
				this.setCreditDebit(item.getCreditDebit());
			}
			if (item.getDateAvailablePickup() != null) {
				this.setDateAvailablePickup(item.getDateAvailablePickup());
			}
			if (item.getDateHoldRequested() != null) {
				this.setDateHoldRequested(item.getDateHoldRequested());
			}
			if (item.getDescription() != null) {
				this.setDescription(item.getDescription());
			}
			if (item.getDueDate() != null) {
				this.setDueDate(item.getDueDate());
			}
			if (item.getElectronicResource() != null) {
				this.setElectronicResource(item.getElectronicResource());
			}
			if (item.getFineAccrualDate() != null) {
				this.setFineAccrualDate(item.getFineAccrualDate());
			}
			if (item.getFineAmount() != null) {
				this.setFineAmount(item.getFineAmount().doubleValue());
			}
			if (item.getFineStatus() != null) {
				this.setFineStatus(item.getFineStatus());
			}
			if (item.getHoldQueueLength() != this.getHoldQueueLength()) {
				this.setHoldQueueLength(item.getHoldQueueLength());
			}
			if (item.getHoldRequestId() != null) {
				this.setHoldRequestId(item.getHoldRequestId());
			}
			if (item.getIsbn() != null) {
				this.setIsbn(item.getIsbn());
			}
			if (item.getItemId() != null) {
				this.setItemId(item.getItemId());
			}
			if (item.getLocation() != null) {
				this.setLocation(item.getLocation());
			}
			if (item.getMediumType() != null) {
				this.setMediumType(item.getMediumType());
			}
			if (item.getPublisher() != null) {
				this.setPublisher(item.getPublisher());
			}
			if (item.getRequestingUsers() != null) {
				for (KohaUser user : item.getRequestingUsers()) {
					this.addRequestingUser(user);
				}
			}
			if (item.getSeries() != null) {
				this.setSeries(item.getSeries());
			}
			if (item.getSessionId() != null) {
				this.setSessionId(item.getSessionId());
			}
			if (item.getTitle() != null) {
				this.setTitle(item.getTitle());
			}
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[BibID:" + (getBibId() != null ? getBibId() : "null") + "],");
		sb.append("[ItemID:" + (getItemId() != null ? getItemId() : "null") + "],");
		sb.append("[HoldingsID:" + (getHoldingsId() != null ? getHoldingsId() : "null") + "],");
		sb.append("[Barcode:" + (getBarcode() != null ? getBarcode() : "null") + "],");
		sb.append("[Agency:" + (getAgency() != null && getAgency().getAgencyId() != null ? getAgency().getAgencyId() : "null") + "],");
		sb.append("[Description:" + (getDescription() != null ? getDescription() : "null") + "],");
		sb.append("[Location:" + (getLocation() != null ? getLocation() : "null") + "],");
		sb.append("[CallNumber:" + (getCallNumber() != null ? getCallNumber() : "null") + "],");
		sb.append("[Author:" + (getAuthor() != null ? getAuthor() : "null") + "],");
		sb.append("[ISBN:" + (getIsbn() != null ? getIsbn() : "null") + "],");
		sb.append("[Medium Type:" + (getMediumType() != null ? getMediumType() : "null") + "],");
		sb.append("[Publisher:" + (getPublisher() != null ? getPublisher() : "null") + "],");
		sb.append("[Series:" + (getSeries() != null ? getSeries() : "null") + "],");
		sb.append("[Title:" + (getTitle() != null ? getTitle() : "null") + "],");
		sb.append("[CirculationStatus:" + (getCirculationStatus() != null ? getCirculationStatus() : "null") + "],");
		sb.append("[ElectronicResource:" + (getElectronicResource() != null ? getElectronicResource() : "null") + "],");
		sb.append("[HoldRequestID:" + (getHoldRequestId() != null ? getHoldRequestId() : "null") + "],");
		sb.append("[DateHoldRequested:" + (getDateHoldRequested() != null ? getDateHoldRequested() : "null") + "],");
		sb.append("[DateAvailablePickup:" + (getDateAvailablePickup() != null ? getDateAvailablePickup() : "null") + "],");
		sb.append("[DueDate:" + (getDueDate() != null ? getDueDate() : "null") + "],");
		sb.append("[HoldQueueLength:" + getHoldQueueLength() + "],");
		sb.append("[FineAmount:" + (getFineAmount() != null ? getFineAmount().doubleValue() : "null") + "],");
		sb.append("[FineAccrualDate:" + (getFineAccrualDate() != null ? getFineAccrualDate() : "null") + "],");
		sb.append("[FineStatus:" + (getFineStatus() != null ? getFineStatus() : "null") + "],");
		sb.append("[CreditDebit:" + (getCreditDebit() != null ? getCreditDebit() : "null") + "],");
		sb.append("[BorrowingUsers:" + (getBorrowingUsers() != null ? getBorrowingUsers() : "null") + "],");
		sb.append("[RequestingUsers:" + (getRequestingUsers() != null ? getRequestingUsers() : "null") + "],");
		sb.append("[SessionId:" + (getSessionId() != null ? getSessionId() : "null") + "],");
		return sb.toString();
	}

	public BigDecimal getNumberOfPieces() {
		return numberOfPieces;
	}

	public void setNumberOfPieces(BigDecimal numberOfPieces) {
		this.numberOfPieces = numberOfPieces;
	}

	public String getCopyNumber() {
		return copyNumber;
	}

	public void setCopyNumber(String copyNumber) {
		this.copyNumber = copyNumber;
	}
}
