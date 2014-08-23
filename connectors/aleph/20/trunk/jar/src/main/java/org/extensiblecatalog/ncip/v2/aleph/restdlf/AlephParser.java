package org.extensiblecatalog.ncip.v2.aleph.restdlf;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.user.AlephUser;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AlephParser {
	Document xmlToParse;
	String[] services;

	public AlephParser(Document xmlResponse) {
		xmlToParse = xmlResponse;
	}
	
	private void checkDocumentIntegrity() throws AlephException {
		if (!xmlToParse.hasChildNodes()) {
			throw new AlephException(AlephConstants.ERROR_FIND_DOC_MISSING);
		}
//		NodeList errorNodes = xmlToParse.getElementsByTagName(AlephConstants.ERROR_NODE);
//		if (errorNodes.getLength() > 0) {
//			Node error = errorNodes.item(0);
//			if (error.hasChildNodes()) {
//				Node detail = error.getFirstChild();
//				throw new AlephException(detail.getNodeValue());
//			} else {
//				throw new AlephException(AlephConstants.ERROR_FIND_DOC_MISSING);
//			}
//		}
		
		//Error handled here:
		NodeList replyVerification = xmlToParse.getChildNodes();
		//if(replyVerification != "0000") throw new AlephException("Server returned invalid XML respone (reply-code != 0000)");
	}

	/*
	 * getBibDescription = initData .getBibliographicDescriptionDesired(); boolean getCircStatus = initData.getCirculationStatusDesired(); boolean getElectronicResource = initData.getElectronicResourceDesired(); boolean getHoldQueueLength =
	 * initData.getHoldQueueLengthDesired(); boolean getItemDescription = initData.getItemDescriptionDesired(); boolean getLocation = initData.getLocationDesired(); boolean getCurrentBorrowers = initData.getCurrentBorrowerDesired(); boolean
	 * getCurrentRequesters
	 */

	public AlephItem toAlephItem(boolean bibliographicDescription, boolean circulationStatus, boolean holdQueueLnegth, boolean itemDesrciption) throws AlephException {
		
		try {
			checkDocumentIntegrity();
		} catch (AlephException e1) {
			throw new AlephException("Checking document integrity returned an error: "+e1.getMessage());
		}
		
		// Start parsing data from XML to AlephItem
		AlephItem item = new AlephItem();
		if (bibliographicDescription) {
			//TODO: What is bibDescription??
			//ANSWER: AlephUtil.java -> getBibliographicDescription()
			item.setBibId(xmlToParse.getElementsByTagName("z30-doc-number").item(0).getNodeValue());
		}
		if (circulationStatus) {
			item.setCirculationStatus(xmlToParse.getElementsByTagName("status").item(0).getNodeValue());
		}
		if (holdQueueLnegth) {
			item.setHoldQueueLength(Integer.parseInt(xmlToParse.getElementsByTagName("z30-hol-doc-number").item(0).getNodeValue()));
		}
		if (itemDesrciption) {
			try {
				item.setDescription(xmlToParse.getElementsByTagName("z30-description").item(0).getNodeValue());
			} catch (DOMException e) {
				item.setDescription("Popis položky bohužel není k dispozici.");
			}
		}
		return item;
	}

	public AlephUser toAlephUser() {
		AlephUser user = new AlephUser();
		
		return user;
	}
}
