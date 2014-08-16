package org.extensiblecatalog.ncip.v2.aleph.AlephAPI.user;

import org.extensiblecatalog.ncip.v2.aleph.AlephAPI.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.AlephAPI.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.AlephAPI.XMLParserUtil;
import org.extensiblecatalog.ncip.v2.aleph.AlephAPI.agency.AlephAgency;
import org.extensiblecatalog.ncip.v2.aleph.AlephAPI.item.AlephItemFactory;

import java.io.Serializable;

import org.w3c.dom.Document;


/**
 * This class will generate an AlephUser based on the xml
 * document passed in.
 * 
 * @author Rick Johnson (NDU)
 *
 */
public class AlephUserFactory implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 65007L;
	/**
	 * Create an AlephUser based on the xml document passed in.
	 * If an error is included in the document an AlephException will be thrown.
	 * 
	 * @param doc
	 * @return AlephUser
	 * @throws AlephException
	 */
	public static AlephUser createAlephUser(AlephAgency agency, Document doc) throws AlephException{
		AlephUser user = new AlephUser();
		if (doc!=null){
			if (!doc.hasChildNodes()){
				throw new AlephException(AlephConstants.ERROR_AUTHENTICATION_FAILED_UNKNOWN);
			}
			String error = XMLParserUtil.getError(doc);
			if (error!=null){
				throw new AlephException(error);
			} 

			String sessionId = XMLParserUtil.getNodeTextValue(doc, AlephConstants.SESSION_ID_NODE);
			if (sessionId==null){
				throw new AlephException(AlephConstants.ERROR_AUTHENTICATION_FAILED_SESSION_ID_MISSING);
			} else {
				user.setSessionId(sessionId);
			}

			if (!XMLParserUtil.getNodeExists(doc, AlephConstants.Z303_ID_NODE)){
				throw new AlephException(AlephConstants.ERROR_GLOBAL_PATRON_RECORD_MISSING);
			}

			if (!XMLParserUtil.getNodeExists(doc, AlephConstants.Z304_NODE)){
				throw new AlephException(AlephConstants.ERROR_ADDRESS_INFORMATION_MISSING);
			}

			if (!XMLParserUtil.getNodeExists(doc, AlephConstants.Z305_NODE)){
				throw new AlephException(AlephConstants.ERROR_LOCAL_PATRON_RECORD_MISSING);
			}

			String username = XMLParserUtil.getNodeTextValue(doc, AlephConstants.Z303_ID_NODE);
			if (username!=null){
				user.setUsername(username);
			} else {
				throw new AlephException(AlephConstants.ERROR_GLOBAL_PATRON_RECORD_MISSING);
			}

			String email = XMLParserUtil.getNodeTextValue(doc, AlephConstants.Z304_EMAIL_NODE);
			if (email!=null){
				user.setEmailAddress(email);
				//do not throw error if null since not required
			}

			String fullName = XMLParserUtil.getNodeTextValue(doc, AlephConstants.Z303_NAME_NODE);
			if (fullName!=null){
				user.setFullName(fullName);
				//do not throw error if null since not required
			}

			String address = "";
			String[] addressLines = new String[3];
			addressLines[0] = XMLParserUtil.getNodeTextValue(doc, AlephConstants.Z304_ADDRESS_1_NODE);
			addressLines[1] = XMLParserUtil.getNodeTextValue(doc, AlephConstants.Z304_ADDRESS_2_NODE);
			addressLines[2] = XMLParserUtil.getNodeTextValue(doc, AlephConstants.Z304_ADDRESS_3_NODE);

			boolean first = true;
			for (String addressLine : addressLines){
				if (addressLine!=null){
					if (!first){
						address += "\n";
					}
					address += addressLine;
					first = false;
				}
			}

			if (address.length()>0){
				//replace extra spaces
				while (address.indexOf("  ")!=-1){
					address = address.replaceFirst("  ", " ");
				}
				user.setAddress(address);
			}

			//get blocks
			
			//get global blocks
			String delinquency = XMLParserUtil.getNodeTextValue(doc, AlephConstants.Z303_DELINQUENCY_1_NODE);
			String note = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z303_DELINQUENCY_NOTE_1_NODE);
			String updateDate = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z303_DELINQUENCY_1_UPDATE_DATE_NODE);
			if (delinquency!=null&&!delinquency.equalsIgnoreCase(AlephConstants.BLOCK_NONE_CODE)){
				String block = "Global Block: "+delinquency;
				if (note!=null){
					block += " (Notes: "+note+")";
					user.addNote("Global Block Note: "+note);
				}
				if (updateDate!=null) block += " (Date: "+updateDate+")";
				user.addBlock(block);
			}
			
			delinquency = XMLParserUtil.getNodeTextValue(doc, AlephConstants.Z303_DELINQUENCY_2_NODE);
			note = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z303_DELINQUENCY_NOTE_2_NODE);
			updateDate = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z303_DELINQUENCY_2_UPDATE_DATE_NODE);
			if (delinquency!=null&&!delinquency.equalsIgnoreCase(AlephConstants.BLOCK_NONE_CODE)){
				String block = "Global Block: "+delinquency;
				if (note!=null){
					block += " (Notes: "+note+")";
					user.addNote("Global Block Note: "+note);
				}
				if (updateDate!=null) block += " (Date: "+updateDate+")";
				user.addBlock(block);
			}
			
			delinquency = XMLParserUtil.getNodeTextValue(doc, AlephConstants.Z303_DELINQUENCY_3_NODE);
			note = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z303_DELINQUENCY_NOTE_3_NODE);
			updateDate = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z303_DELINQUENCY_3_UPDATE_DATE_NODE);
			if (delinquency!=null&&!delinquency.equalsIgnoreCase(AlephConstants.BLOCK_NONE_CODE)){
				String block = "Global Block: "+delinquency;
				if (note!=null){
					block += " (Notes: "+note+")";
					user.addNote("Global Block Note: "+note);
				}
				if (updateDate!=null) block += " (Date: "+updateDate+")";
				user.addBlock(block);
			}

			//get global notes
			note = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z303_FIELD_NOTE_1_NODE);
			if (note!=null) user.addNote("Global Note: "+note);
			note = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z303_FIELD_NOTE_2_NODE);
			if (note!=null) user.addNote("Global Note: "+note);
			note = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z303_FIELD_NOTE_3_NODE);
			if (note!=null) user.addNote("Global Note: "+note);
			note = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z303_ADDITIONAL_NOTE_1_NODE);
			if (note!=null) user.addNote("Global Note: "+note);
			note = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z303_ADDITIONAL_NOTE_2_NODE);
			if (note!=null) user.addNote("Global Note: "+note);
			
			//get local block
			String loanPermission = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z305_LOAN_PERMISSION_NODE);
			if (loanPermission!=null&&loanPermission.equalsIgnoreCase(AlephConstants.NO)){
				user.addBlock("Loan Blocked");
			}
			String holdPermission = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z305_HOLD_PERMISSION_NODE);
			if (holdPermission!=null&&holdPermission.equalsIgnoreCase(AlephConstants.NO)){
				user.addBlock("Hold Blocked");
			}
			String renewPermission = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z305_RENEW_PERMISSION_NODE);
			if (renewPermission!=null&&renewPermission.equalsIgnoreCase(AlephConstants.NO)){
				user.addBlock("Renew Blocked");
			}
			
			delinquency = XMLParserUtil.getNodeTextValue(doc, AlephConstants.Z305_DELINQUENCY_1_NODE);
			note = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z305_DELINQUENCY_NOTE_1_NODE);
			updateDate = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z305_DELINQUENCY_1_UPDATE_DATE_NODE);
			if (delinquency!=null&&!delinquency.equalsIgnoreCase(AlephConstants.BLOCK_NONE_CODE)){
				String block = "Local Block: "+delinquency;
				if (note!=null){
					block += " (Notes: "+note+")";
					user.addNote("Local Block Note: "+note);
				}
				if (updateDate!=null) block += " (Date: "+updateDate+")";
				user.addBlock(block);
			}
			
			delinquency = XMLParserUtil.getNodeTextValue(doc, AlephConstants.Z305_DELINQUENCY_2_NODE);
			note = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z305_DELINQUENCY_NOTE_2_NODE);
			updateDate = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z305_DELINQUENCY_2_UPDATE_DATE_NODE);
			if (delinquency!=null&&!delinquency.equalsIgnoreCase(AlephConstants.BLOCK_NONE_CODE)){
				String block = "Local Block: "+delinquency;
				if (note!=null){
					block += " (Notes: "+note+")";
					user.addNote("Local Block Note: "+note);
				}
				if (updateDate!=null) block += " (Date: "+updateDate+")";
				user.addBlock(block);
			}
			
			delinquency = XMLParserUtil.getNodeTextValue(doc, AlephConstants.Z305_DELINQUENCY_3_NODE);
			note = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z305_DELINQUENCY_NOTE_3_NODE);
			updateDate = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z305_DELINQUENCY_3_UPDATE_DATE_NODE);
			if (delinquency!=null&&!delinquency.equalsIgnoreCase(AlephConstants.BLOCK_NONE_CODE)){
				String block = "Local Block: "+delinquency;
				if (note!=null){
					block += " (Notes: "+note+")";
					user.addNote("Local Block Note: "+note);
				}
				if (updateDate!=null) block += " (Date: "+updateDate+")";
				user.addBlock(block);
			}
			
			//get local notes
			note = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z305_FIELD_NOTE_1_NODE);
			if (note!=null) user.addNote("Local Note: "+note);
			note = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z305_FIELD_NOTE_2_NODE);
			if (note!=null) user.addNote("Local Note: "+note);
			note = XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z305_FIELD_NOTE_3_NODE);
			if (note!=null) user.addNote("Local Note: "+note);
			
			//get fines balance
			String balance = XMLParserUtil.getNodeTextValue(doc, AlephConstants.BALANCE_NODE);
			if (balance==null) user.setBalance(0.00);
			else user.setBalance(balance);
			
			//get fine items
			user.setFineItems(AlephItemFactory.getFineAlephItems(agency,doc));
			
			//get requested items
			user.setRequestedItems(AlephItemFactory.getHoldAlephItems(agency,doc));
			
			//get loaned items
			user.setLoanItems(AlephItemFactory.getLoanAlephItems(agency,doc));
			
			//set barcode
			user.setAuthenticatedUsername(XMLParserUtil.getNodeTextValue(doc,AlephConstants.Z305_FIELD_NOTE_1_NODE));
		}
		return user;
	}
}