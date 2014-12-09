package org.extensiblecatalog.ncip.v2.aleph.util.SAXHandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.aleph.util.LocalConfig;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemId;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.LoanedItem;
import org.extensiblecatalog.ncip.v2.service.MediumType;
import org.extensiblecatalog.ncip.v2.service.Version1ItemIdentifierType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Jiří Kozlovský (MZK)
 *
 */
public class AlephLoanHandler extends DefaultHandler {

	private LocalConfig localConfig;

	private List<LoanedItem> loanedItems;
	private LoanedItem currentLoanedItem;

	private BibliographicDescription bibliographicDescription;

	private String bibDocNumber;
	private String itemDocNumber;
	private String itemSequenceNumber;

	private String loanNumber;

	private boolean localizationDesired = false;
	private boolean renewable;

	// Dates
	private boolean dueDateReached = false;
	private boolean loanDateReached = false;

	// Bibliographic description
	private boolean authorReached = false;
	private boolean titleReached = false;
	private boolean publisherReached = false;
	private boolean materialReached = false;

	// Item identifiers
	private boolean bibDocNoReached = false;
	private boolean itemDocNoReached = false;
	private boolean itemSequenceReached = false;

	// False if one of bibDocNumber, itemDocNumber, itemSeqNo had empty node
	private boolean itemFullIdFound;

	// Loan identifier
	private boolean loanNumberReached = false;

	/**
	 * This initializes SAX parser for parsing loans.
	 */
	public AlephLoanHandler(LocalConfig localConfig) {
		this.localConfig = localConfig;
		loanedItems = new ArrayList<LoanedItem>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase(AlephConstants.LOAN_ITEM_NODE)) {

			bibliographicDescription = new BibliographicDescription();
			currentLoanedItem = new LoanedItem();

			String renewAttr = attributes.getValue(AlephConstants.RENEW_NODE_ATTR);
			if (renewAttr != null && renewAttr.equalsIgnoreCase(AlephConstants.YES)) {
				renewable = true;
			} else
				renewable = false;

			// Expect there will be data needed to construct Item Id (item doc no., item bib doc no. & agency id)
			// If one of these is not found, then this boolean is set to false
			itemFullIdFound = true;

		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_AUTHOR_NODE)) {
			authorReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_TITLE_NODE)) {
			titleReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_PUBLISHER_NODE)) {
			publisherReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DOC_NUMBER_NODE)) {
			itemDocNoReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_DOC_NUMBER_NODE)) {
			bibDocNoReached = true;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_MATERIAL_NODE)) {
			materialReached = true;
		} else if (qName.matches(AlephConstants.Z36_DUE_DATE_NODE + "|" + AlephConstants.Z36H_DUE_DATE_NODE)) {
			dueDateReached = true;
		} else if (qName.matches(AlephConstants.Z36_LOAN_DATE_NODE + "|" + AlephConstants.Z36H_LOAN_DATE_NODE)) {
			loanDateReached = true;
		} else if (qName.matches(AlephConstants.Z36_ITEM_SEQUENCE_NODE + "|" + AlephConstants.Z36H_ITEM_SEQUENCE_NODE)) {
			itemSequenceReached = true;
		} else if (qName.matches(AlephConstants.Z36_NUMBER_NODE + "|" + AlephConstants.Z36H_NUMBER_NODE)) {
			loanNumberReached = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase(AlephConstants.LOAN_ITEM_NODE)) {
			if (itemFullIdFound) {
				// Create unique bibliographic item id from bibLibrary, bibDocNo, admLibrary, itemDocNo & itemSequence
				String bibliographicItemIdentifier = AlephUtil.buildAlephItemId(localConfig, bibDocNumber, itemDocNumber, itemSequenceNumber);

				BibliographicItemId bibliographicItemId = AlephUtil.createBibliographicItemIdAsURI(bibliographicItemIdentifier);

				bibliographicDescription.setBibliographicItemIds(Arrays.asList(bibliographicItemId));
			}
			// Create loan identifier from admLibrary & loanNumber
			ItemId itemId = new ItemId();

			if (renewable && loanNumber != null) {
				// Loan number is needed to apply RenewItemService
				itemId.setItemIdentifierValue(localConfig.getAdmLibrary() + loanNumber);
			} else {
				// This will be significant for loaned items marked as not renewable
				itemId.setItemIdentifierValue("-1");
			}
			itemId.setItemIdentifierType(Version1ItemIdentifierType.ACCESSION_NUMBER);
			currentLoanedItem.setItemId(itemId);

			currentLoanedItem.setBibliographicDescription(bibliographicDescription);

			if (currentLoanedItem.getDateDue() == null)
				currentLoanedItem.setIndeterminateLoanPeriodFlag(true);

			loanedItems.add(currentLoanedItem);

		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_AUTHOR_NODE) && authorReached) {
			authorReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_TITLE_NODE) && titleReached) {
			titleReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_PUBLISHER_NODE) && publisherReached) {
			publisherReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_DOC_NUMBER_NODE) && itemDocNoReached) {
			itemFullIdFound = false;
			itemDocNoReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z13_DOC_NUMBER_NODE) && bibDocNoReached) {
			itemFullIdFound = false;
			bibDocNoReached = false;
		} else if (qName.equalsIgnoreCase(AlephConstants.Z30_MATERIAL_NODE) && materialReached) {
			materialReached = false;
		} else if (qName.matches(AlephConstants.Z36_DUE_DATE_NODE + "|" + AlephConstants.Z36H_DUE_DATE_NODE) && dueDateReached) {
			dueDateReached = false;
		} else if (qName.matches(AlephConstants.Z36_LOAN_DATE_NODE + "|" + AlephConstants.Z36H_LOAN_DATE_NODE) && loanDateReached) {
			loanDateReached = false;
		} else if (qName.matches(AlephConstants.Z36_ITEM_SEQUENCE_NODE + "|" + AlephConstants.Z36H_ITEM_SEQUENCE_NODE) && itemSequenceReached) {
			itemFullIdFound = false;
			itemSequenceReached = false;
		} else if (qName.matches(AlephConstants.Z36_NUMBER_NODE + "|" + AlephConstants.Z36H_NUMBER_NODE) && loanNumberReached) {
			loanNumberReached = false;
		}

	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (itemSequenceReached) {
			itemSequenceNumber = new String(ch, start, length);
			itemSequenceReached = false;
		} else if (materialReached) {
			MediumType mediumType = AlephUtil.detectMediumType(new String(ch, start, length), localizationDesired);
			bibliographicDescription.setMediumType(mediumType);
			materialReached = false;
		} else if (authorReached) {
			bibliographicDescription.setAuthor(new String(ch, start, length));
			authorReached = false;
		} else if (titleReached) {
			bibliographicDescription.setTitle(new String(ch, start, length));
			titleReached = false;
		} else if (publisherReached) {
			bibliographicDescription.setPublisher(new String(ch, start, length));
			publisherReached = false;
		} else if (itemDocNoReached) {
			itemDocNumber = new String(ch, start, length);
			itemDocNoReached = false;
		} else if (bibDocNoReached) {
			bibDocNumber = new String(ch, start, length);
			bibDocNoReached = false;
		} else if (dueDateReached) {
			GregorianCalendar dateDue = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
			currentLoanedItem.setDateDue(dateDue);
			dueDateReached = false;
		} else if (loanDateReached) {
			GregorianCalendar loanDate = AlephUtil.parseGregorianCalendarFromAlephDate(new String(ch, start, length));
			currentLoanedItem.setDateCheckedOut(loanDate);
			loanDateReached = false;
		} else if (loanNumberReached) {
			loanNumber = new String(ch, start, length);
			loanNumberReached = false;
		}
	}

	public List<LoanedItem> getListOfLoanedItems() {
		return loanedItems;
	}

	public void setLocalizationDesired(boolean localizationDesired) {
		this.localizationDesired = localizationDesired;
	}
}
