package org.extensiblecatalog.ncip.v2.koha.util.SAXHandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.extensiblecatalog.ncip.v2.koha.util.KohaConstants;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.koha.util.LocalConfig;
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
 * @author Jiří Kozlovský (KOH)
 */
public class KohaLoanHandler extends DefaultHandler {

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
	private boolean z36dueDateReached = false;
	private boolean z36loanDateReached = false;

	// Bibliographic description
	private boolean z13authorReached = false;
	private boolean z13titleReached = false;
	private boolean z13publisherReached = false;
	private boolean z30materialReached = false;

	// Item identifiers
	private boolean z13docNumberReached = false;
	private boolean z30docNumberReached = false;
	private boolean z36itemSequenceReached = false;

	// False if one of bibDocNumber, itemDocNumber, itemSeqNo had empty node
	private boolean itemFullIdFound;

	// Loan identifier
	private boolean z36numberReached = false;

	/**
	 * This initializes SAX parser for parsing loans.
	 */
	public KohaLoanHandler() {
		loanedItems = new ArrayList<LoanedItem>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase(KohaConstants.LOAN_ITEM_NODE)) {

			bibliographicDescription = new BibliographicDescription();
			currentLoanedItem = new LoanedItem();

			String renewAttr = attributes.getValue(KohaConstants.RENEW_NODE_ATTRIBUTE);
			if (renewAttr != null && renewAttr.equalsIgnoreCase(KohaConstants.YES)) {
				renewable = true;
			} else
				renewable = false;

			// Expect there will be data needed to construct Item Id (item doc no., item bib doc no. & agency id)
			// If one of these is not found, then this boolean is set to false
			itemFullIdFound = true;

		} else if (qName.equalsIgnoreCase(KohaConstants.Z13_AUTHOR_NODE)) {
			z13authorReached = true;
		} else if (qName.equalsIgnoreCase(KohaConstants.Z13_TITLE_NODE)) {
			z13titleReached = true;
		} else if (qName.equalsIgnoreCase(KohaConstants.Z13_PUBLISHER_NODE)) {
			z13publisherReached = true;
		} else if (qName.equalsIgnoreCase(KohaConstants.Z30_DOC_NUMBER_NODE)) {
			z30docNumberReached = true;
		} else if (qName.equalsIgnoreCase(KohaConstants.Z13_DOC_NUMBER_NODE)) {
			z13docNumberReached = true;
		} else if (qName.equalsIgnoreCase(KohaConstants.Z30_MATERIAL_NODE)) {
			z30materialReached = true;
		} else if (qName.matches(KohaConstants.Z36_DUE_DATE_NODE + "|" + KohaConstants.Z36H_DUE_DATE_NODE)) {
			z36dueDateReached = true;
		} else if (qName.matches(KohaConstants.Z36_LOAN_DATE_NODE + "|" + KohaConstants.Z36H_LOAN_DATE_NODE)) {
			z36loanDateReached = true;
		} else if (qName.matches(KohaConstants.Z36_ITEM_SEQUENCE_NODE + "|" + KohaConstants.Z36H_ITEM_SEQUENCE_NODE)) {
			z36itemSequenceReached = true;
		} else if (qName.matches(KohaConstants.Z36_NUMBER_NODE + "|" + KohaConstants.Z36H_NUMBER_NODE)) {
			z36numberReached = true;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase(KohaConstants.LOAN_ITEM_NODE)) {
			if (itemFullIdFound) {
				// Create unique bibliographic item id from bibLibrary, bibDocNo, admLibrary, itemDocNo & itemSequence
				String bibliographicItemIdentifier = KohaUtil.buildKohaItemId(bibDocNumber, itemDocNumber, itemSequenceNumber);

				BibliographicItemId bibliographicItemId = KohaUtil.createBibliographicItemIdAsURI(bibliographicItemIdentifier);

				bibliographicDescription.setBibliographicItemIds(Arrays.asList(bibliographicItemId));
			}
			// Create loan identifier from admLibrary & loanNumber
			ItemId itemId = new ItemId();

			if (renewable && loanNumber != null) {
				// Loan number is needed to apply RenewItemService
				itemId.setItemIdentifierValue(LocalConfig.getAdmLibrary() + loanNumber);
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

		} else if (qName.equalsIgnoreCase(KohaConstants.Z13_AUTHOR_NODE) && z13authorReached) {
			z13authorReached = false;
		} else if (qName.equalsIgnoreCase(KohaConstants.Z13_TITLE_NODE) && z13titleReached) {
			z13titleReached = false;
		} else if (qName.equalsIgnoreCase(KohaConstants.Z13_PUBLISHER_NODE) && z13publisherReached) {
			z13publisherReached = false;
		} else if (qName.equalsIgnoreCase(KohaConstants.Z30_DOC_NUMBER_NODE) && z30docNumberReached) {
			itemFullIdFound = false;
			z30docNumberReached = false;
		} else if (qName.equalsIgnoreCase(KohaConstants.Z13_DOC_NUMBER_NODE) && z13docNumberReached) {
			itemFullIdFound = false;
			z13docNumberReached = false;
		} else if (qName.equalsIgnoreCase(KohaConstants.Z30_MATERIAL_NODE) && z30materialReached) {
			z30materialReached = false;
		} else if (qName.matches(KohaConstants.Z36_DUE_DATE_NODE + "|" + KohaConstants.Z36H_DUE_DATE_NODE) && z36dueDateReached) {
			z36dueDateReached = false;
		} else if (qName.matches(KohaConstants.Z36_LOAN_DATE_NODE + "|" + KohaConstants.Z36H_LOAN_DATE_NODE) && z36loanDateReached) {
			z36loanDateReached = false;
		} else if (qName.matches(KohaConstants.Z36_ITEM_SEQUENCE_NODE + "|" + KohaConstants.Z36H_ITEM_SEQUENCE_NODE) && z36itemSequenceReached) {
			itemFullIdFound = false;
			z36itemSequenceReached = false;
		} else if (qName.matches(KohaConstants.Z36_NUMBER_NODE + "|" + KohaConstants.Z36H_NUMBER_NODE) && z36numberReached) {
			z36numberReached = false;
		}

	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		if (z36itemSequenceReached) {
			itemSequenceNumber = new String(ch, start, length);
			z36itemSequenceReached = false;
		} else if (z30materialReached) {
			MediumType mediumType = KohaUtil.detectMediumType(new String(ch, start, length), localizationDesired);
			bibliographicDescription.setMediumType(mediumType);
			z30materialReached = false;
		} else if (z13authorReached) {
			bibliographicDescription.setAuthor(new String(ch, start, length));
			z13authorReached = false;
		} else if (z13titleReached) {
			bibliographicDescription.setTitle(new String(ch, start, length));
			z13titleReached = false;
		} else if (z13publisherReached) {
			bibliographicDescription.setPublisher(new String(ch, start, length));
			z13publisherReached = false;
		} else if (z30docNumberReached) {
			itemDocNumber = new String(ch, start, length);
			z30docNumberReached = false;
		} else if (z13docNumberReached) {
			bibDocNumber = new String(ch, start, length);
			z13docNumberReached = false;
		} else if (z36dueDateReached) {
			GregorianCalendar dateDue = KohaUtil.parseGregorianCalendarFromKohaDate(new String(ch, start, length));
			currentLoanedItem.setDateDue(dateDue);
			z36dueDateReached = false;
		} else if (z36loanDateReached) {
			GregorianCalendar loanDate = KohaUtil.parseGregorianCalendarFromKohaDate(new String(ch, start, length));
			currentLoanedItem.setDateCheckedOut(loanDate);
			z36loanDateReached = false;
		} else if (z36numberReached) {
			loanNumber = new String(ch, start, length);
			z36numberReached = false;
		}
	}

	public List<LoanedItem> getListOfLoanedItems() {
		return loanedItems;
	}

	public void setLocalizationDesired(boolean localizationDesired) {
		this.localizationDesired = localizationDesired;
	}
}
