/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.millennium;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.ConnectorConfiguration;
import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MillenniumRemoteServiceManager is responsible for locating the correct back-end service;  
 * Note: If you're looking for a model of how to code your own ILS's RemoteServiceManager, do not use this class's methods
 * as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class MillenniumRemoteServiceManager implements RemoteServiceManager {

    private MillenniumConfiguration MillenniumConfig;

	final String IIIClassicBaseUrl;
	final int intPort;
	final String SNo;

	// You can you System.out.println for quick test also
	private static final Logger LOG = Logger.getLogger(MillenniumRemoteServiceManager.class); 
	
	StringFunction strFunction = new org.extensiblecatalog.ncip.v2.millennium.StringFunction();
	ErrorCodeMessage errorCodeMsg = new org.extensiblecatalog.ncip.v2.millennium.ErrorCodeMessage();
	
	/**
	 * HttpClient used for screen scraping the web opac
	 */
	private HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());

    public MillenniumRemoteServiceManager() throws ToolkitException {

        this((new ConnectorConfigurationFactory()).getConfiguration());

    }

  public MillenniumRemoteServiceManager(Properties properties) throws ToolkitException {

     /* this(ConnectorConfigurationFactory.getConfiguration(properties)); */
	   this(new ConnectorConfigurationFactory(properties).getConfiguration());

  }

  public MillenniumRemoteServiceManager(ConnectorConfiguration config) throws ToolkitException {

      MillenniumConfig = (MillenniumConfiguration)config;
      IIIClassicBaseUrl = MillenniumConfig.getURL();
      intPort = MillenniumConfig.getPort();
      SNo = MillenniumConfig.getSearchScope();

  }

    public MillenniumConfiguration getConfiguration() {
        return MillenniumConfig;
    }

	public String getBibLanguage(String bibRecordId) {
		return "eng";
	}

	/*
	 * This class is configuration for status of any return in class
	 *    Return - True - good record
	 *    Return - False - Error.  Therefore, need to find out why
	 */
	public class Status {
		public Boolean returnStatus;
		public String returnMsg;
	}
	
	/*
	 * This class is configuration for status of any String Class return
	 *    Return - True - good string or good record
	 *    Return - False - Error.  Therefore, need to find out why
	 */
	public class StatusString {
		public Status recordStatus;
		public String statusValue;
	}
	
	/*
	 * This class is configuration for status of any ArrayList Class return
	 *    Return - True - good ArrayList
	 *    Return - False - Error.  Therefore, need to find out why
	 */
	public class StatusArrayString {
		public Status recordStatus;
		public ArrayList<String> itemsList;
	}

	/*
	 * This class is configuration for 3 main items return for each book status
	 *    Circulation status - such as available, on shelf, due date
	 *    Call Number status - any string
	 *    Location status - where the item locate such as level 1 and so on
	 */
	public class ItemStatus {
		public String itemCirStatus;
		public String itemCallNoStatus;
		public String itemLocStatus;
	}
	
	public class itemCirculation {
		public Status recordStatus;
		public ArrayList<ItemStatus> itemsList;
	}
	
	/*
	 * This class is configuration for return http Property when request for http client access to server
	 */
	
	public class htmlProperty {
		public Status recordStatus;
		public String sessionId;
		public String userid;
		public String pageItem;
		public String url;
		public String html;
	}
	
	/*
	 * This class is configuration for Lookup User to return all information about loan, fine, request item. 
	 */
	public class AuthenticationItemsInfo {
		public String bRecord; // Currently Checked Out - Same for Holds and Checked Out
		public String iTitle; // Currently Checked Out - Same for Holds and Checked Out
		public String iBarcode;  // Currently Checked Out
		public String iStatus;  // Currently Checked Out - Same for Holds
		public String iCallNo; // Currently checked Out
		public String iMark; // Currently checked Out, Same for Holds
		public String iMarkValue; // Currently checked Out
		public String iCheckout; // ReadingHistory
		public String iAuthor; // ReadingHistory
		public String iDetails; // ReadingHistory
		public String iPickupLocation; // Holds
		public String iCancelIfNotPickup; //Holds
		public String iFinesEntryTitle; // Overdue
		public String iFinesEntryDetail; // Overdue
		public String iFinesDetailType; // Overdue
		public String iFinesDetailAmt; // Overdue
	}
		
	public class UserItemInfo {
		public Status recordStatus;
		public ArrayList<AuthenticationItemsInfo> itemsList;
	}
	
	/*
	 * This class is configuration for Looking Pattern Matching when searching information on html file format
	 */
	
	public class PatternGroup {
		public String patternValue;
		public int groupNo[];
	}
	
	/*
	 * This class is configuration for Pair the username, password with the configuration setup file to pull information
	 * into a pair so we don't care how many fields in customer login page any more
	 */
	
	public class PairGroup {
		public String firstValue;
		public String secondValue;

		public PairGroup() {
		}
		
		public void setFirstValue (String firstValue) {
			this.firstValue = firstValue;
		}
		
		public void setSecondValue (String secondValue) {
			this.secondValue = secondValue;
		}
	}
		
	/*
	 * StatusArrayString getArrayListItemStatus
	 * This class get the multiple patterns and compare to html. if success then return the arraylist.
	 * It is also format the string by removing un-want characters
	 * 
	 * 		@param: lookupPattern, html, groupNo[], lookupName
	 * 		Return: Class: StatusArrayString
	 */
	public StatusArrayString getArrayListItemStatus (String lookupPattern, String html, int groupNo[], String lookupName) {
		Status iStatus = new Status();
		StatusArrayString getItemListStatus = new StatusArrayString();
		ArrayList <String> arraylistItemStatus = new ArrayList <String>();
		//LOG.debug("getArrayListItemStatus - loolupPattern: " + lookupPattern);
		Pattern findPattern = Pattern.compile(lookupPattern, Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher findPatternMatch = findPattern.matcher(html);
		String itemValue = "";
		String itemValueFormat = "";
		boolean itemFound = false;

		while (findPatternMatch.find()) {
			String finalValue = "";
			itemFound = true;
			for (int x=0; x < groupNo.length; x++){
				itemValue = findPatternMatch.group(groupNo[x]);
				//LOG.debug("Process group: " + x + " .itemValue: " + itemValue);
				// This section for removing extra fields in Call No 
				if (itemValue.contains("<!-- field v -->")) {
					itemValueFormat = itemValue.replaceAll("<!-- field v -->", "");
					itemValue = itemValueFormat.trim();
					//LOG.debug("Found <!-- field # --> " + "- New itemValue: " + itemValue);
				}
				if (itemValue.contains("<!-- field # -->")) {
					itemValueFormat = itemValue.replaceAll("<!-- field # -->", "");
					itemValue = itemValueFormat.trim();
					//LOG.debug("Found <!-- field # --> " + "- New itemValue: " + itemValue);
				}
				if (itemValue.contains("<!-- field y -->")) {
					itemValueFormat = itemValue.replaceAll("<!-- field y -->", ""); 
					itemValue = itemValueFormat.trim();
					//LOG.debug("Found <!-- field y --> " + "- New itemValue: " + itemValue);
				}
				// This section for removing extra fields in Location
				if (itemValue.contains("<!-- field ! -->")) {
					itemValueFormat = itemValue.replaceAll("<!-- field ! -->", ""); 
					itemValue = itemValueFormat.trim();
					//LOG.debug("Found <!-- field ! --> " + "- New itemValue: " + itemValue);
				}
				// This section for removing <br />
				if (itemValue.contains("<br />")) {
					itemValueFormat = itemValue.replaceAll("<br />", ""); 
					itemValue = itemValueFormat.trim();
					LOG.debug("Found <br /> " + "- New itemValue: " + itemValue);
				}
				// This section for removing <em>
				if (itemValue.contains("<em>")) {
					itemValueFormat = itemValue.replaceAll("<em>", ""); 
					itemValue = itemValueFormat.trim();
					//LOG.debug("Found <em> " + "- New itemValue: " + itemValue);
				}
				// This section for removing </em>
				if (itemValue.contains("</em>")) {
					itemValueFormat = itemValue.replaceAll("</em>", ""); 
					itemValue = itemValueFormat.trim();
					//LOG.debug("Found </em> " + "- New itemValue: " + itemValue);
				}
				if (itemValue.contains("&nbsp;")) {
					itemValueFormat = itemValue.replaceAll("&nbsp;", "");
					itemValue = itemValueFormat.trim();
					//LOG.debug("Found &nbsp; " + "- New itemValue: " + itemValue);
				}
				if (itemValue.contains("\n")) {
					itemValueFormat = itemValue.replaceAll("\n", "");
					itemValue = itemValueFormat.trim();
					//LOG.debug("Found '\n' " + "- New itemValue: " + itemValue);
				}
				itemValue = itemValue.trim();
				if (itemValue.contains("<a href=")) {
					String patternLookup = "^<a(.*?)>(.*?)</a>(?s)(.*?)$";
					//LOG.debug("itemValue: " + itemValue);
					int noGroup[];
					noGroup = new int[2];
					noGroup[0] = 2;
					noGroup[1] = 3;
					StatusArrayString getArrayListItemItemStatusNoLink = new StatusArrayString();
					getArrayListItemItemStatusNoLink = getArrayListItemStatus(patternLookup, itemValue, noGroup, "Link");
					//LOG.debug("getArrayListItemItemStatusNoLink Size is: " + getArrayListItemItemStatusNoLink.itemsList.size());
					if (getArrayListItemItemStatusNoLink.itemsList.size() > 0) {
						itemValue = getArrayListItemItemStatusNoLink.itemsList.get(0);
					}	
				}
				//LOG.debug("itemValue: " + itemValue);
				if (itemValue.length() > 0) {
					if (finalValue.length() > 0) {
						finalValue = finalValue + " " + itemValue.trim();
					} else {
						finalValue = itemValue.trim();	
					}
				}
			}
			if (finalValue.length() <= 0) {
				finalValue = "Missing";
			}
			//LOG.debug("finalValue: " + finalValue);
			arraylistItemStatus.add(finalValue);
		}
		if (itemFound) {
			iStatus.returnStatus = true;
			//iStatus.returnMsg = "Found pattern match for: " + lookupName;
			iStatus.returnMsg = errorCodeMsg.ErrorMessage(102) + lookupName;
			getItemListStatus.recordStatus = iStatus;
			getItemListStatus.itemsList = arraylistItemStatus;
		} else {
			iStatus.returnStatus = false;
			//iStatus.returnMsg = "Could not find pattern match for: " + lookupName;
			iStatus.returnMsg = errorCodeMsg.ErrorMessage(103) + lookupName;
			getItemListStatus.recordStatus = iStatus;
			getItemListStatus.itemsList = null;
		}
		return getItemListStatus;
	}

	/*
	 * getItemStatusList
	 * This class to hold multiple records contains three items value:
	 *     Circ
	 *     Location
	 *     Call No.
	 */
	public itemCirculation getItemStatusList(String html) {

		// You can turn on these lines below for quick test with your LOCAL modify page
		
		/*
		MillenniumFileHandle millenniumFileHandle = new MillenniumFileHandle();
		String html = "";
		html = millenniumFileHandle.ReadTextFile("H:\\My Documents\\LookupItem Test Case.txt"); 
		LOG.debug(html);
		*/
		
		int i = 0;
		Status iStatus = new Status();
		itemCirculation itemCirculationStatus = new itemCirculation();
		ArrayList<ItemStatus> itemStatusList = new ArrayList<ItemStatus>();
		boolean itemFound = false;
		boolean additionalItem = false;
		boolean circulationFound = true;
		boolean callnoFound = true;
		boolean locationFound = true;
		
		// Searching html to find if it has more additional copies of item status
		Pattern additionCopyPattern = Pattern.compile("^(.*?)center><form method=(?s)(.*?)post(.*?)(?s)(.*?)action=(?s)(.*?)>(.*?)<input type(.*?)(?s)(.*?)submit(?s)(.*?)value=(?s)(.*?)/>$",
				Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher additionalCopyPatternMatch = additionCopyPattern.matcher(html);
		String additionalLink = "";
		String additionalStr = "";
		String additionalhtml = "";
		
		while (additionalCopyPatternMatch.find()){
			additionalLink = additionalCopyPatternMatch.group(5).replace("\"", ""); // The link for additional copies
			//LOG.debug("Millennium-getItemStatusList - Group 5: '" + additionalLink + "'");
			additionalStr = additionalCopyPatternMatch.group(10).trim();
			additionalStr = additionalStr.replace("\"", "");
			//LOG.debug("Millennium-getItemStatusList - Group 10: '" + additionalStr + "'");
			String strCompare = "View additional copies or search for a specific volume/copy";
			//LOG.debug("strCompare: '" + strCompare + "'");
			if (additionalStr.equals(strCompare)){
				LOG.debug("Millennium-getItemStatusList - Found Multiple BibInfo for Circulation"); 
				additionalItem = true;
			}
		}
		if (additionalItem) {
			StatusString getAdditionalhtml = getItemPage(additionalLink, "Additional");
			additionalStr = "additional ";
			if (getAdditionalhtml.recordStatus.returnStatus) {
				additionalhtml = getAdditionalhtml.statusValue;
			}
		} else {
			LOG.debug("Millennium-getItemStatusList - 'View addition copies...' string is not found!");
			additionalhtml = html;
		}

		if (additionalhtml.length() > 0) {

			LOG.debug("Millennium-getItemStatusList - Length of " + additionalStr + "bib record html: " + additionalhtml.length());	
			
			int groupNo[]; 
			PatternGroup iPatternGroup = new PatternGroup();
			ArrayList <PatternGroup> patternGroupList = new ArrayList<PatternGroup>();
			
			// Circulation Status
			
			/*
			 * 10/31/2011 @ 4:39 PM - Bach Nguyen - Updated this section. Using the simplest pattern but will check on
			 * getArrayListItemStatus function (Class) to remove 
			 * 		<!-- field ! -->
			 * 
			 
			iPatternGroup.patternValue = "^(.*?)<!-- field % -->&nbsp;(.*?)<!-- field ! -->(.*?)</td></tr>$";
			groupNo = new int[2];
			groupNo[0] = 2;
			groupNo[1] = 3;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);

			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			iPatternGroup.patternValue = "^(.*?)<!-- field % -->&nbsp;(.*?)</td></tr>$";
			groupNo = new int[1];
			groupNo[0] = 2;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			*/
			
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			iPatternGroup.patternValue = "^(.*?)<!-- field % -->(.*?)</td></tr>$";
			groupNo = new int[1];
			groupNo[0] = 2;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			String getLibraryHas = MillenniumConfig.getLibraryHasLabel();
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			iPatternGroup.patternValue = "^(.*?)<!-- BEGIN BIBHOLDINGSRECORDS -->(?s)(.*?)bibHoldingsLabel(.*?)(?s)" + getLibraryHas + "(.*?)bibHoldingsEntry(.*?)>(.*?)</td>$";
			groupNo = new int[1];
			groupNo[0] = 6;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			StatusArrayString cirsArrayList = new StatusArrayString();
			int itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Circ Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug ("Circ Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug ("Circ groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				cirsArrayList = getArrayListItemStatus (patternGroupList.get(x).patternValue, additionalhtml, patternGroupList.get(x).groupNo, "Circulation");
				if (cirsArrayList.recordStatus.returnStatus && cirsArrayList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find item value for this circulation pattern. Go to next Pattern group");
				} else {
					LOG.error("Could not find item value for this Circulation pattern.");
					circulationFound = false;
				}
				itemProcess++;
			}

			// Call No Status
			
			patternGroupList = new ArrayList<PatternGroup>();
			/*
			 * 10/31/2011 @ 3:45 PM - Bach Nguyen - Updated this section. Using the simplest pattern but will check on
			 * getArrayListItemStatus function (Class) to remove 
			 * 		<!-- field v -->
			 * 		<!-- field # -->
			 * 		<!-- field y -->
			 * 
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			iPatternGroup.patternValue = "^(.*?)field C -->&nbsp;<a(.*?)>(.*?)</a>(.*?)<!-- field v -->(.*?)</td>$";
			groupNo = new int[2];
			groupNo[0] = 3;
			groupNo[1] = 5;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			iPatternGroup.patternValue = "^(.*?)field C -->(.*?)<!-- field v -->(.*?)</td>$";
			groupNo = new int[2];
			groupNo[0] = 2;
			groupNo[1] = 3;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			 */
			
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			iPatternGroup.patternValue = "^(.*?)<!-- field C -->(.*?)</td>$";
			groupNo = new int[1];
			groupNo[0] = 2;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			String getCallNo = MillenniumConfig.getCallNoLabel();
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			iPatternGroup.patternValue = "^(.*?)<!-- BEGIN BIBHOLDINGSRECORDS -->(?s)(.*?)bibHoldingsLabel(.*?)(?s)" + getCallNo + "(.*?)bibHoldingsEntry(.*?)>(.*?)</td>$";
			groupNo = new int[1];
			groupNo[0] = 6;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
					
			StatusArrayString callNoArrayList = new StatusArrayString();
			itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("CallNo Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("CallNo Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("CallNo groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				callNoArrayList = getArrayListItemStatus (patternGroupList.get(x).patternValue, additionalhtml, patternGroupList.get(x).groupNo, "Call Number");
				if (callNoArrayList.recordStatus.returnStatus && callNoArrayList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()){
					LOG.debug("Could not find item value for this Call No pattern. Go to next Pattern group");
				} else {
					LOG.error("Could not find item value for this Call No pattern.");
					callnoFound = false;
				}
				itemProcess++;
			}
		
			// Location Status
			
			patternGroupList = new ArrayList<PatternGroup>();
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			//iPatternGroup.patternValue = "^(.*?)bibItemsEntry(?s)(.*?)(?s)<td(?s)(.*?)(?s)(.*?)field 1 -->&nbsp;(.*?)</td>$";
			iPatternGroup.patternValue = "^(?s)(.*?)<!-- field 1 -->(.*?)</td>$";
			groupNo = new int[1];
			groupNo[0] = 2;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			String getLocation = MillenniumConfig.getLocationLabel();
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			iPatternGroup.patternValue = "^(.*?)<!-- BEGIN BIBHOLDINGSRECORDS -->(?s)(.*?)bibHoldingsLabel(.*?)(?s)" + getLocation + "(.*?)bibHoldingsEntry(.*?)>(.*?)</td>$";
			groupNo = new int[1];
			groupNo[0] = 6;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			StatusArrayString locationArrayList = new StatusArrayString();
			itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Location Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Location Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("Location groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				locationArrayList = getArrayListItemStatus (patternGroupList.get(x).patternValue, additionalhtml, patternGroupList.get(x).groupNo, "Location");
				if (locationArrayList.recordStatus.returnStatus && locationArrayList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find item value for this Location pattern. Go to next Pattern group");
				} else {
					LOG.error("Could not find item value for this Location pattern.");
					locationFound = false;
				}
				itemProcess++;
			}
			//LOG.debug("Circ: " + circulationFound +", CallNo: " + callnoFound + ", Location: " + locationFound);
			String circulationStatusString = "";
			if (circulationFound && callnoFound && locationFound) {
				LOG.debug("Millennium-getItemStatusList - Found all pattern for Circ, CallNo and Location");
				LOG.debug("Millennium-getItemStatusList (Circ, CallNo, Location) = " + "(" + 
						cirsArrayList.itemsList.size() + ", " + callNoArrayList.itemsList.size() + ", " +
						locationArrayList.itemsList.size() +")");
				i = 0;
				//String circCheck = "";
				if ((cirsArrayList.itemsList != null && callNoArrayList.itemsList != null && locationArrayList.itemsList != null)
						&& (cirsArrayList.itemsList.size() == callNoArrayList.itemsList.size()) && 
						(callNoArrayList.itemsList.size() == locationArrayList.itemsList.size())){
					
					LOG.debug("Millennium-getItemStatusList - All Array ItemStatus Size are match (Same Array Size)");
					
					for (i=0; i < cirsArrayList.itemsList.size(); i++){
						ItemStatus itemStatus = new ItemStatus();
						itemStatus.itemCirStatus = cirsArrayList.itemsList.get(i);
						itemStatus.itemCallNoStatus = callNoArrayList.itemsList.get(i);
						itemStatus.itemLocStatus = locationArrayList.itemsList.get(i);
						itemStatusList.add(itemStatus);
					}
					itemFound = true;
					i = 0;
					//LOG.debug("Millennium - Size of itemStatusList: " + itemStatusList.size());	
					while (i < itemStatusList.size()) {
						LOG.debug("Millennium - itemStatusList[" + i + "].Location: " + itemStatusList.get(i).itemLocStatus);
						LOG.debug("Millennium - itemStatusList[" + i + "].CallNo: " + itemStatusList.get(i).itemCallNoStatus);
						LOG.debug("Millennium - itemStatusList[" + i + "].Circulation: " + itemStatusList.get(i).itemCirStatus);
						i++;
					}
				}
				else {
					LOG.error("Millennium-getItemStatusList - Circ, CallNo, Location have different array size");	
				}
				
				if (itemFound) {
					iStatus.returnStatus = true;
					iStatus.returnMsg = "Millennium-getItemStatusList - Found all same array size for Circulation Status";
					itemCirculationStatus.recordStatus = iStatus;
					itemCirculationStatus.itemsList = itemStatusList;
				} else {
					iStatus.returnStatus = false;
					iStatus.returnMsg = "Millennium-getItemStatusList - Circ, CallNo, Location have different array size";
					itemCirculationStatus.recordStatus = iStatus;
					itemCirculationStatus.itemsList = null;
				}
			}
			else if (cirsArrayList.itemsList == null && callNoArrayList.itemsList == null && locationArrayList.itemsList == null){
				LOG.debug("Millennium-getItemStatusList - Checking online or on ordered");

				String getAuthor = "";
				getAuthor = getBibDescription(html, "Author");
				if (getAuthor != null && getAuthor.equals("Missing")) {
					getAuthor = null;
				}
				String getTitle = "";
				getTitle = getBibDescription(html, "Title");
				if (getTitle != null && getTitle.equals("Missing")) {
					getTitle = null;
				}
				String getPublisher = "";
				getPublisher = getBibDescription(html, "Publisher");
				if (getPublisher != null && getPublisher.equals("Missing")) {
					getPublisher = null;
				}
				//LOG.debug("Value for Author, Title, Publisher: " + getAuthor + ", " + getTitle + "," + getPublisher);
				if (getAuthor != null || getTitle != null || getPublisher != null) {
					if (additionalhtml.contains("class=\"bibLinks\"")) {
						LOG.debug("Millennium - item Status of Circulation, Location, Call No is on the web");	
						ItemStatus itemStatus = new ItemStatus();
						itemStatus.itemCirStatus = "Available";
						itemStatus.itemCallNoStatus = "Online";
						itemStatus.itemLocStatus = "On the Web";
						itemStatusList.add(itemStatus);		

						iStatus.returnStatus = true;
						iStatus.returnMsg = "Millennium-getItemStatusList - Found Circulation Online";
						itemCirculationStatus.recordStatus = iStatus;
						itemCirculationStatus.itemsList = itemStatusList;
						
					} else if (additionalhtml.contains("class=\"bibOrderEntry\"")) {
						LOG.debug("Millennium - item Status of Circulation, Location, Call No is on ordered/processed");
						
						ItemStatus itemStatus = new ItemStatus();
						itemStatus.itemCirStatus = "";
						itemStatus.itemCallNoStatus = "";
						itemStatus.itemLocStatus = "Ordered/Processed";
						itemStatusList.add(itemStatus);		

						iStatus.returnStatus = true;
						iStatus.returnMsg = "Millennium-getItemStatusList - Found Circulation on Ordered/Processed";
						itemCirculationStatus.recordStatus = iStatus;
						itemCirculationStatus.itemsList = itemStatusList;
					} else {
						iStatus.returnStatus = false;
						iStatus.returnMsg = "Millennium-getItemStatusList - Couldn't found any information for Circulation Status";
						itemCirculationStatus.recordStatus = iStatus;
						itemCirculationStatus.itemsList = null;
					}
				}
				else {
					LOG.debug("Millennium - The html return page does not contain any information for looking item!");
					iStatus.returnStatus = false;
					iStatus.returnMsg = "Millennium-getItemStatusList - Couldn't found any information for Circulation Status";
					itemCirculationStatus.recordStatus = iStatus;
					itemCirculationStatus.itemsList = null;
				}
			} 
			else {
				if (circulationFound == false) circulationStatusString = "Circulation";
				if (callnoFound == false){
					if (circulationStatusString.length() == 0) circulationStatusString = "Call No";
					else circulationStatusString = circulationStatusString + ", Call No";
				}
				if (locationFound == false) {
					if (circulationStatusString.length() == 0) circulationStatusString = "Location";
					else circulationStatusString = circulationStatusString + ", Location";
				}
				LOG.error("Millennium-getItemStatusList - Couldn't find pattern for: " + circulationStatusString);
				
				iStatus.returnStatus = false;
				iStatus.returnMsg = "Millennium-getItemStatusList - Couldn't find pattern for: " + circulationStatusString;
				itemCirculationStatus.recordStatus = iStatus;
				itemCirculationStatus.itemsList = null;
			}
		} else {
			iStatus.returnStatus = false;
			iStatus.returnMsg = "Item bib record html is empty!";
			itemCirculationStatus.recordStatus = iStatus;
			itemCirculationStatus.itemsList = null;
		}
		return itemCirculationStatus;
	}
	
	/**
	 * getTesting
	 * This class is created for tesing the return value of any pattern
	 * 
	 * @param html
	 * @return string
	 */
	public String getTesting(String html) {
		String strReturn = "";
		String searchPattern = "^(.*?)<tr class=\"patFuncFinesEntryTitle\">(?s)(.*?)<td(.*?)class=\"patFuncFinesEntryTitle\">(.*?)" +
				"</td>(?s)(.*?)</tr>(?s)(.*?)<tr class=\"patFuncFinesEntryDetail\">(?s)(.*?)<td>(.*?)</td>(?s)(.*?)<td class=\"patFuncFinesDetailType\">" +
				"(.*?)</td>(?s)(.*?)<td(.*?)class=\"patFuncFinesDetailAmt\">(.*?)</td>$";
		//String searchPattern = "^(.*?)<td(.*?)class=\"patFuncFinesTotalAmt\">(.*?)</em></td>$";
		//LOG.debug("SearchingPattern: " + searchPattern);
		Pattern iPattern = Pattern.compile(searchPattern, Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher iPatternMatch = iPattern.matcher(html);

		int i =0;
		while (iPatternMatch.find()){
			//LOG.debug("Testing: [" + i + "] - group(1): " + iPatternMatch.group(1));
			LOG.debug("Testing: [" + i + "] - group(2): " + iPatternMatch.group(2));
			LOG.debug("Testing: [" + i + "] - group(3): " + iPatternMatch.group(3));
			LOG.debug("Testing: [" + i + "] - group(4): " + iPatternMatch.group(4));
			LOG.debug("Testing: [" + i + "] - group(5): " + iPatternMatch.group(5));
			LOG.debug("Testing: [" + i + "] - group(6): " + iPatternMatch.group(6));
			LOG.debug("Testing: [" + i + "] - group(7): " + iPatternMatch.group(7));
			LOG.debug("Testing: [" + i + "] - group(8): " + iPatternMatch.group(8));
			LOG.debug("Testing: [" + i + "] - group(9): " + iPatternMatch.group(9));
			LOG.debug("Testing: [" + i + "] - group(10): " + iPatternMatch.group(10));
			LOG.debug("Testing: [" + i + "] - group(11): " + iPatternMatch.group(11));
			LOG.debug("Testing: [" + i + "] - group(12): " + iPatternMatch.group(12));
			LOG.debug("Testing: [" + i + "] - group(13): " + iPatternMatch.group(13));
			strReturn = iPatternMatch.group(3).replace("\n", "");
			i++;
		}
		if (iPatternMatch.find()) {
			strReturn = iPatternMatch.group(3).replace("\n", "");
			LOG.debug("Millennium - Testing: " + strReturn);
		}
		return strReturn;
	}

	/**
	 * getBibDescription
	 * This class is get all information about bib description base on lookupField
	 */
	public String getBibDescription(String lookupPage, String lookupField) {
		/*
		 * 11/01/2011 @2:49PM - Bach Nguyen created this section to simulate the html from text file instead of
		 * real production web site
		 */
		//MillenniumFileHandle millenniumFileHandle = new MillenniumFileHandle();
		//String lookupPage = "";
		//lookupPage = millenniumFileHandle.ReadTextFile("H:\\My Documents\\LookupItem Test Case.txt"); 
		//LOG.debug(lookupPage);
				
		String patternValue = null;
		int groupNo[];
		StatusArrayString itemValue = new StatusArrayString();
		StatusArrayString AltAuthorValue = new StatusArrayString();
		StatusArrayString AltAuthorText = new StatusArrayString();
		
		// Lookup Author
		if (lookupField.toLowerCase().equals("author")){
			patternValue = "^<tr><!-- next row for fieldtag=a -->(?s)(.*?)<a(.*?)>(.*?)</a>$";
			groupNo = new int[1];
			groupNo[0] = 3;
			itemValue = getArrayListItemStatus (patternValue, lookupPage, groupNo, "Author");
			if (itemValue.recordStatus.returnStatus) {
				LOG.debug("Millennium - Author: " + itemValue.itemsList.get(0) + "- Record found: " + itemValue.itemsList.size());
				patternValue = itemValue.itemsList.get(0);
			} else {
				LOG.debug("Could not find main: " + lookupField + ". Try to find Alternate Author!");
				patternValue = "^<tr><!-- next row for fieldtag=b -->(?s)(.*?)<a(.*?)>(.*?)</a>$";
				groupNo = new int[1];
				groupNo[0] = 3;
				AltAuthorValue = getArrayListItemStatus (patternValue, lookupPage, groupNo, "Author");
				if (AltAuthorValue.recordStatus.returnStatus) {
					// Searching for Alternate Author in group 1
					patternValue = "^<tr><!-- next row for fieldtag=b -->(?s)(.*?)<a(.*?)>(.*?)</a>$";
					groupNo = new int[1];
					groupNo[0] = 1;
					AltAuthorText = getArrayListItemStatus (patternValue, lookupPage, groupNo, "Author");
					if (AltAuthorText.recordStatus.returnStatus) {
						if (AltAuthorText.itemsList.get(0).contains("Author")) {
							LOG.debug("Millennium - Alternate Author: " + AltAuthorValue.itemsList.get(0) + "- Record found: " + AltAuthorValue.itemsList.size());
							patternValue = AltAuthorValue.itemsList.get(0);							
						} else {
							LOG.debug("Could not find: " + lookupField);
							patternValue = null;
						}
					}
				} else {
					LOG.debug("Could not find: " + lookupField);
					patternValue = null;
				}
			}
		}
		
		// Lookup Title
		if (lookupField.toLowerCase().equals("title")){
			patternValue = "^<tr><!-- next row for fieldtag=t -->(?s)(.*?)<strong>(.*?)</strong></td></tr>$";
			groupNo = new int[1];
			groupNo[0] = 2;
			itemValue = getArrayListItemStatus (patternValue, lookupPage, groupNo, "Title");
			if (itemValue.recordStatus.returnStatus) {
				LOG.debug("Millennium - Title: " + itemValue.itemsList.get(0) + "- Record found: " + itemValue.itemsList.size());
				patternValue = itemValue.itemsList.get(0);				
			} else {
				LOG.debug("Could not find: " + lookupField);
				patternValue = null;
			}
		}
		
		// Lookup Publisher
		if (lookupField.toLowerCase().equals("publisher")){
			patternValue = "^<tr><!-- next row for fieldtag=p -->(?s)(.*?)bibInfoData(.*?)>(.*?)</td></tr>$";
			groupNo = new int[1];
			groupNo[0] = 3;
			itemValue = getArrayListItemStatus (patternValue, lookupPage, groupNo, "Publisher");
			if (itemValue.recordStatus.returnStatus) {
				LOG.debug("Millennium - Publisher: " + itemValue.itemsList.get(0) + "- Record found: " + itemValue.itemsList.size());
				patternValue = itemValue.itemsList.get(0);
			} else {
				LOG.debug("Could not find: " + lookupField);
				patternValue = null;
			}
		}
		
		// Lookup Series
		if (lookupField.toLowerCase().equals("series")){
			patternValue = "^<tr><!-- next row for fieldtag=s -->(?s)(.*?)bibInfoData(.*?)>(.*?)</td></tr>$";
			groupNo = new int[1];
			groupNo[0] = 3;
			itemValue = getArrayListItemStatus (patternValue, lookupPage, groupNo, "Series");
			if (itemValue.recordStatus.returnStatus) {
				LOG.debug("Millennium - Series: " + itemValue.itemsList.get(0) + "- Record found: " + itemValue.itemsList.size());
				patternValue = itemValue.itemsList.get(0);
			} else {
				LOG.debug("Could not find: " + lookupField);
				patternValue = null;
			}
		}
		
		// Lookup ISBN
		if (lookupField.toLowerCase().equals("isbn")){
			patternValue = "^<tr><!-- next row for fieldtag=i -->(?s)(.*?)bibInfoData(.*?)>(.*?)</td></tr>$";
			groupNo = new int[1];
			groupNo[0] = 3;
			itemValue = getArrayListItemStatus (patternValue, lookupPage, groupNo, "ISBN");
			if (itemValue.recordStatus.returnStatus) {
				LOG.debug("Millennium - ISBN: " + itemValue.itemsList.get(0) + "- Record found: " + itemValue.itemsList.size());
				patternValue = itemValue.itemsList.get(0);
			} else {
				LOG.debug("Could not find: " + lookupField);
				patternValue = null;
			}	
		}
		
		// Lookup Description
		if (lookupField.toLowerCase().equals("description")){
			patternValue = "^<tr><!-- next row for fieldtag=r -->(?s)(.*?)bibInfoData(.*?)>(.*?)</td></tr>$";
			groupNo = new int[1];
			groupNo[0] = 3;
			itemValue = getArrayListItemStatus (patternValue, lookupPage, groupNo, "Description");
			if (itemValue.recordStatus.returnStatus) {
				LOG.debug("Millennium - Description: " + itemValue.itemsList.get(0) + "- Record found: " + itemValue.itemsList.size());
				patternValue = itemValue.itemsList.get(0);
			} else {
				LOG.debug("Could not find: " + lookupField);
				patternValue = null;
			}	
		}

		//LOG.debug("bibDescription returning-" + lookupField + ": " + patternValue);
		return patternValue;

	} // End getBibDescription

	/**
	 * return the html for an ID
	 * 
	 * @param itemId
	 *            the item id
	 * @return the html for the page
	 * @throws IOException
	 */
	public StatusString getItemPage(String itemId, String lookupInfo)  {
		// Setup Get Method
		Status iStatus = new Status();
		StatusString iStatusString = new StatusString();
		String baseUrl = "http://" + IIIClassicBaseUrl;
		String recordUrl = null;
		if (lookupInfo.equals("Additional")) {
			recordUrl = itemId;
		} else {
			recordUrl = "/record=" + itemId;
		}
		String url = baseUrl + recordUrl;
		LOG.debug("Millennium-getItemPage - url: " + url);
		GetMethod getMethod = new GetMethod(url);
		String html = null;
		// Execute & return status code
		try {
			int statusCode = client.executeMethod(getMethod);
			// If Successful
			if (statusCode == 200) {
				html = getMethod.getResponseBodyAsString();
				iStatus.returnStatus = true;
				iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemPage - Success";
				iStatusString.recordStatus = iStatus;
				iStatusString.statusValue = html;
			} else {
				iStatus.returnStatus = false;
				iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemPage - False to receive page, statusCode(" + statusCode + ")";
				iStatusString.recordStatus = iStatus;
				iStatusString.statusValue = html;
			}
		} catch (HttpException e) {
			//e.printStackTrace();
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemPage - " + errorCodeMsg.ErrorMessage(490);
			iStatusString.recordStatus = iStatus;
			iStatusString.statusValue = html;
		} catch (IOException e) {
			//e.printStackTrace();
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemPage - " + errorCodeMsg.ErrorMessage(480);
			iStatusString.recordStatus = iStatus;
			iStatusString.statusValue = html;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
		return iStatusString;
	}

	/**
	 * A helper method to replace special characters for XML
	 * 
	 * @param inputString
	 *            The string to replace the characters of
	 * @return String a string of replaced characters
	 */
	public static String escapeCharacters(String inputString) {
		String returnString = StringEscapeUtils.escapeXml(inputString);

		return returnString;
	}

	/**
	 * Pulls the session id out of the Set-Cookie response header from a GET
	 * request on the given url.
	 * 
	 * @param url
	 *            - the url that the GET request will hit.
	 * @return the session id.
	 */
	public StatusString getIIISessionId(String url) {

		String sessionId = null;
		Status iStatus = new Status();
		StatusString iStatusString = new StatusString();
		HttpClient client = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		// set cookie policy
		getMethod.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

		try {
			client.executeMethod(getMethod);
			Header setCookieHeader = getMethod.getResponseHeader("Set-Cookie");
			String cookieValue = setCookieHeader.getValue();
			//LOG.debug("MillenniumRemoteServiceManager/getIIISessionId/cookiValue: " + cookieValue);
			if (cookieValue.contains("III_SESSION_ID")) {
				int start = cookieValue.indexOf("III_SESSION_ID") + 15;
				int end = cookieValue.indexOf(";", start);
				sessionId = cookieValue.substring(start, end);

				iStatus.returnStatus = true;
				iStatus.returnMsg = "MillenniumRemoteServiceManager-getIIISessionId - Success";
				iStatusString.recordStatus = iStatus;
				iStatusString.statusValue = sessionId;
			}

		} catch (HttpException e) {
			//e.printStackTrace();
			//LOG.debug("MillenniumRemoteServiceManager-getIIISessionId - HttpException");
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-getIIISessionId - " + errorCodeMsg.ErrorMessage(490);
			iStatusString.recordStatus = iStatus;
			iStatusString.statusValue = sessionId;
		} catch (IOException e) {
			//e.printStackTrace();
			//LOG.debug("MillenniumRemoteServiceManager-getIIISessionId - IOException");
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-getIIISessionId - " + errorCodeMsg.ErrorMessage(480);
			iStatusString.recordStatus = iStatus;
			iStatusString.statusValue = sessionId;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
		return iStatusString;
	}

	/**
	 * Makes a GET request using the given URL to resolve any HTTP redirects.
	 * 
	 * @param url
	 *            - the base url which may result in a redirect.
	 * @return the corrected url.
	 */
	public StatusString getRedirectedUrl(String url, String sessionId) /* throws ILSException */{

		Status iStatus = new Status();
		StatusString iStatusString = new StatusString();
		GetMethod getMethod = null;
		String returnedUrl = null;

		try {
			synchronized (client) {
				getMethod = new GetMethod(url);
				getMethod.addRequestHeader("Cookie", "III_SESSION_ID=" + sessionId);
				int statusCode = client.executeMethod(getMethod);
			
				if (statusCode == 200) {
					returnedUrl = getMethod.getURI().toString();
					iStatus.returnStatus = true;
					iStatus.returnMsg = "MillenniumRemoteServiceManager-getRedirectedUrl - Success";
					iStatusString.recordStatus = iStatus;
					iStatusString.statusValue = returnedUrl;
				} else { // We could not get the page
					iStatus.returnStatus = false;
					iStatus.returnMsg = "MillenniumRemoteServiceManager-getRedirectedUrl - statusCode(" + statusCode + "). " + errorCodeMsg.ErrorMessage(470);
					iStatusString.recordStatus = iStatus;
					iStatusString.statusValue = returnedUrl;
				}
			}
		}
		catch (IOException e) {
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-getRedirectedUrl - " + errorCodeMsg.ErrorMessage(480);
			iStatusString.recordStatus = iStatus;
			iStatusString.statusValue = returnedUrl;
		}
		finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
		return iStatusString;
	}

	public htmlProperty Authenticate(ArrayList<PairGroup> authUserName, ArrayList<PairGroup> authUserPassword, String baseUrl) {

		String strSessionId = null;
		String redirectedUrl = null;
		String userId = null;
		String itemPage = null;
		String html = null;
		Status iStatus = new Status();
		htmlProperty authenticateStatus = new htmlProperty();		
		StatusString sessionIdStatus = getIIISessionId(baseUrl);
		if (sessionIdStatus.recordStatus.returnStatus) {
			strSessionId = sessionIdStatus.statusValue;
			LOG.debug("Authenticate - strSessionId: " + strSessionId);
			StatusString redirectedUrlStatus = getRedirectedUrl(baseUrl, strSessionId);
			if (redirectedUrlStatus.recordStatus.returnStatus) {
				String redirectUrl = redirectedUrlStatus.statusValue;
				LOG.debug("Authenticate - redirectUrl: " + redirectUrl);
				// create post method
				htmlProperty loginFormStatus;
				PostMethod loginForm = new PostMethod(redirectUrl);
				// Set up authentication types
				
				for (int x = 0; x < authUserName.size(); x++) {
					loginForm.addParameter(authUserName.get(x).firstValue, authUserName.get(x).secondValue);
					//LOG.debug("User Pair: " + authUserName.get(x).firstValue + ", " + authUserName.get(x).secondValue);
				}
				
				for (int x = 0; x < authUserPassword.size(); x++) {
					loginForm.addParameter(authUserPassword.get(x).firstValue, authUserPassword.get(x).secondValue);
					//LOG.debug("Pass Pair: " + authUserPassword.get(x).firstValue + ", " + authUserPassword.get(x).secondValue);
				}
				
				loginFormStatus = LogIntoWebForm(loginForm, strSessionId);
				if (loginFormStatus.recordStatus.returnStatus) {
					iStatus.returnStatus = true;
					iStatus.returnMsg = loginFormStatus.recordStatus.returnMsg;
					authenticateStatus.recordStatus = iStatus;
					authenticateStatus.sessionId = strSessionId;
					authenticateStatus.url = loginFormStatus.url;
					authenticateStatus.pageItem = loginFormStatus.pageItem;
					authenticateStatus.userid = loginFormStatus.userid;
					authenticateStatus.html = loginFormStatus.html;							
				} else {
					iStatus.returnStatus = false;
					iStatus.returnMsg = loginFormStatus.recordStatus.returnMsg;
					authenticateStatus.recordStatus = iStatus;
					authenticateStatus.sessionId = strSessionId;
					authenticateStatus.url = loginFormStatus.url;
					authenticateStatus.pageItem = loginFormStatus.pageItem;
					authenticateStatus.userid = loginFormStatus.userid;
					authenticateStatus.html = loginFormStatus.html;
				}
			} else {
				iStatus.returnStatus = false;
				iStatus.returnMsg = redirectedUrlStatus.recordStatus.returnMsg;
				authenticateStatus.recordStatus = iStatus;
				authenticateStatus.sessionId = strSessionId;
				authenticateStatus.url = redirectedUrl;
				authenticateStatus.pageItem = itemPage;
				authenticateStatus.userid = userId;
				authenticateStatus.html = html;
			} // if (redirectedUrlStatus.recordStatus.returnStatus)			
		} else {
			iStatus.returnStatus = false;
			iStatus.returnMsg = sessionIdStatus.recordStatus.returnMsg;
			authenticateStatus.recordStatus = iStatus;
			authenticateStatus.sessionId = strSessionId;
			authenticateStatus.url = redirectedUrl;
			authenticateStatus.pageItem = itemPage;
			authenticateStatus.userid = userId;
			authenticateStatus.html = html;
		} // if (sessionIdStatus.recordStatus.returnStatus)
		return authenticateStatus;
	}

	/**
	 * Attempts a login using the given PostMethod by adding the needed form
	 * parameters.
	 * 
	 * @param postMethod
	 *            - the PostMethod with all other needed params already
	 *            inserted.
	 * @return the response as a String.
	 */
	public htmlProperty LogIntoWebForm(PostMethod postMethod, String sessionId) {
	
		String url = null;
		String redirectLocation = null; 
		String userId = null;
		String itemPage = null;
		String html = null;
		Status iStatus = new Status();
		htmlProperty loginStatus = new htmlProperty();

		// set the login params

		postMethod.addRequestHeader("Cookie", "III_SESSION_ID=" + sessionId);

		//postMethod.addParameter("submit.x", "0");
		//postMethod.addParameter("submit.y", "0");
		postMethod.addParameter("pat_submit", "xxx");

		// Since login returns a 302, we have to make another GET request after the POST.
		GetMethod getMethod = null;

		try {
			int statusCode = client.executeMethod(postMethod);
			URI uri = postMethod.getURI();
			url = uri.toString();

			// We're redirecting on the first request
			LOG.debug("lenght of postMethod.getResponseBodyAsString(): " + postMethod.getResponseBodyAsString().length());

			if (statusCode == 302) {
				
				LOG.debug("LoginWebForm - Success submited Page (StatusCode = 302)");

				redirectLocation = postMethod.getResponseHeader("Location").getValue(); 
				//LOG.debug("LoginWebForm - redirectLocation: " + redirectLocation);
				itemPage = strFunction.Rightstr(url, "/");
				//LOG.debug("LoginWebForm - itemPage 1: " + itemPage);
				url = url.substring(0, url.indexOf("/" + itemPage)) + redirectLocation;
				//LOG.debug("LoginWebForm - url: " + url);
				itemPage = strFunction.Rightstr(url, "/");
				//LOG.debug("LoginWebForm - itemPage: " + itemPage);
				int start = url.indexOf(SNo);
				int end = url.indexOf("/" + itemPage);
				userId = url.substring(start, end).replace(SNo, "").replace("/", "");
				//LOG.debug("LoginWebForm - userId: " + userId);					
				
				getMethod = new GetMethod(url);
				getMethod.addRequestHeader("Cookie", "III_SESSION_ID="	+ sessionId);

				statusCode = client.executeMethod(getMethod);
				//LOG.debug("LoginWebForm - Received contents statusCode: " + statusCode);
				 if (statusCode == 200) {
					 LOG.debug("LoginWebForm - statusCode(" + statusCode + ") - Success received Login page");
					 html = getMethod.getResponseBodyAsString();
					 //LOG.debug("LoginWebForm - html.length: " + html.length());
					 iStatus.returnStatus = true;
					 iStatus.returnMsg = "MillenniumRemoteServiceManager-LogIntoWebForm - Success";
					 loginStatus.recordStatus = iStatus;
					 loginStatus.sessionId = sessionId;
					 loginStatus.url = url;
					 loginStatus.pageItem = itemPage.trim();
					 loginStatus.userid = userId;
					 loginStatus.html = html;	 
				 } else {
					 LOG.debug("LoginWebForm - statusCode(" + statusCode + ") - false received Login page");
					 iStatus.returnStatus = false;
					 iStatus.returnMsg = "MillenniumRemoteServiceManager-LogIntoWebForm - statusCode(" + statusCode + "). " + errorCodeMsg.ErrorMessage(470);
					 loginStatus.recordStatus = iStatus;
					 loginStatus.sessionId = sessionId;
					 loginStatus.url = url;
					 loginStatus.pageItem = itemPage;
					 loginStatus.userid = userId;
					 loginStatus.html = html;
				 } 
			}
			/*
			else if (statusCode == 200) {
			 LOG.debug("LoginWebForm - statusCode(" + statusCode + ") - Success received Login page");
			 
			redirectLocation = postMethod.getResponseHeader("Location").getValue(); 
			//LOG.debug("LoginWebForm - redirectLocation: " + redirectLocation);
			itemPage = strFunction.Rightstr(url, "/");
			//LOG.debug ("LoginWebForm - itemPage 1: " + itemPage);
			url = url.substring(0, url.indexOf("/" + itemPage)) + redirectLocation;
			//LOG.debug("LoginWebForm - url: " + url);
			itemPage = strFunction.Rightstr(url, "/");
			//LOG.debug ("LoginWebForm - itemPage: " + itemPage);
			int start = url.indexOf(SNo);
			int end = url.indexOf("/" + itemPage);
			userId = url.substring(start, end).replace(SNo, "").replace("/", "");
			//LOG.debug("LoginWebForm - userId: " + userId);	
			 
			 html = postMethod.getResponseBodyAsString();
			 //LOG.debug("LoginWebForm - html.length: " + html.length());
			 iStatus.returnStatus = true;
			 iStatus.returnMsg = "MillenniumRemoteServiceManager-LogIntoWebForm - Success";
			 loginStatus.recordStatus = iStatus;
			 loginStatus.sessionId = sessionId;
			 loginStatus.url = url;
			 loginStatus.pageItem = itemPage.trim();
			 loginStatus.userid = userId;
			 loginStatus.html = html;	
			}
			*/
			else {
				LOG.error("LoginWebForm - LDAP False to Login or others false to Login - statusCode(" + statusCode + "). ");
				iStatus.returnStatus = false;
				iStatus.returnMsg = "MillenniumRemoteServiceManager-LogIntoWebForm - statusCode(" + statusCode + "). " + errorCodeMsg.ErrorMessage(470);
				loginStatus.recordStatus = iStatus;
				loginStatus.sessionId = sessionId;
				loginStatus.url = url;
				loginStatus.pageItem = itemPage;
				loginStatus.userid = userId;
				loginStatus.html = html;
			}

		} catch (HttpException e1) {
			LOG.error("LoginWebForm - Error! HttpException.");
			//e1.printStackTrace();
			
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-LogIntoWebForm - " + errorCodeMsg.ErrorMessage(490);
			loginStatus.recordStatus = iStatus;
			loginStatus.sessionId = sessionId;
			loginStatus.url = url;
			loginStatus.pageItem = itemPage;
			loginStatus.userid = userId;
			loginStatus.html = html;
			
		} catch (IOException e1) {
			LOG.error("LoginWebForm - Error! IOException.");
			//e1.printStackTrace();
			
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-LogIntoWebForm - " + errorCodeMsg.ErrorMessage(480);;
			loginStatus.recordStatus = iStatus;
			loginStatus.sessionId = sessionId;
			loginStatus.url = url;
			loginStatus.pageItem = itemPage;
			loginStatus.userid = userId;
			loginStatus.html = html;

		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
		return loginStatus;
	}

	/**
	 * Attempts a login using the given PostMethod by adding the needed form
	 * parameters.
	 * 
	 * @param postMethod
	 *            - the PostMethod with all other needed params already
	 *            inserted.
	 * @return the response as a String.
	 */
	public StatusString LogInWebActionForm(PostMethod postMethod, String sessionId) {
	
		String html = null;
		Status iStatus = new Status();
		StatusString loginStatus = new StatusString();

		postMethod.addRequestHeader("Cookie", "III_SESSION_ID=" + sessionId);

		try {
			int statusCode = client.executeMethod(postMethod);
			if (statusCode == 200) {
				 LOG.debug("LogInWebActionForm - statusCode(" + statusCode + ") - Success received Login page");
				 html = postMethod.getResponseBodyAsString();
				 //LOG.debug("LoginWebForm - html.length: " + html.length());
				 iStatus.returnStatus = true;
				 iStatus.returnMsg = "MillenniumRemoteServiceManager-LogInWebActionForm - Success";
				 loginStatus.recordStatus = iStatus;
				 loginStatus.statusValue = html;	 
			} else {
				 LOG.debug("LogInWebActionForm - statusCode(" + statusCode + ") - false received Login page");
				 iStatus.returnStatus = false;
				 iStatus.returnMsg = "MillenniumRemoteServiceManager-LogInWebActionForm - statusCode(" + statusCode + "). " + errorCodeMsg.ErrorMessage(470);
				 loginStatus.recordStatus = iStatus;
				 loginStatus.statusValue = html;
			 } 

		} catch (HttpException e1) {
			LOG.error("LogInWebActionForm - Error! HttpException.");
			//e1.printStackTrace();
			
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-LogInWebActionForm - " + errorCodeMsg.ErrorMessage(490);
			loginStatus.recordStatus = iStatus;
			loginStatus.statusValue = html;
			
		} catch (IOException e1) {
			LOG.error("LogInWebActionForm - Error! IOException.");
			//e1.printStackTrace();
			
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-LogInWebActionForm - " + errorCodeMsg.ErrorMessage(480);;
			loginStatus.recordStatus = iStatus;
			loginStatus.statusValue = html;

		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
		return loginStatus;
	}
	
	public StatusString RequestItemLogIn (ArrayList<PairGroup> authUserName, ArrayList<PairGroup> authUserPassword,
			String pickupLocation, String baseUrl) {
		Status iStatus = new Status();
		StatusString loginStatus = new StatusString();
	
		PostMethod getRequestItemLoginStatus = new PostMethod(baseUrl);
		
		for (int x = 0; x < authUserName.size(); x++) {
			getRequestItemLoginStatus.addParameter(authUserName.get(x).firstValue, authUserName.get(x).secondValue);
			//LOG.debug("User Pair: " + authUserName.get(x).firstValue + ", " + authUserName.get(x).secondValue);
		}
		
		for (int x = 0; x < authUserPassword.size(); x++) {
			getRequestItemLoginStatus.addParameter(authUserPassword.get(x).firstValue, authUserPassword.get(x).secondValue);
			//LOG.debug("Pass Pair: " + authUserPassword.get(x).firstValue + ", " + authUserPassword.get(x).secondValue);
		}
		
		getRequestItemLoginStatus.addParameter("locx00", pickupLocation);
		getRequestItemLoginStatus.addParameter("pat_submit", "xxx");
		
		StatusString getLoginStatus = SingleLogInForm(getRequestItemLoginStatus);
		if (getLoginStatus.recordStatus.returnStatus) {
			iStatus.returnStatus = true;
			iStatus.returnMsg = "RequestItemLogIn - " + getLoginStatus.recordStatus.returnMsg;
			loginStatus.recordStatus = iStatus;
			loginStatus.statusValue = getLoginStatus.statusValue;
		} else {
			iStatus.returnStatus = false;
			iStatus.returnMsg = "RequestItemLogIn - " + getLoginStatus.recordStatus.returnMsg;
			loginStatus.recordStatus = iStatus;
			loginStatus.statusValue = null;
		}
		return loginStatus;
	}
	
	public StatusString RequestItemPickLocation (String pickupLocation, int getYear, int getMonth, int getDate, String baseUrl) {
		Status iStatus = new Status();
		StatusString loginStatus = new StatusString();
		
		PostMethod getRequestItemLoginStatus = new PostMethod(baseUrl);
			
		//LOG.debug("Year/Month/Date: " + Integer.toString(getYear) + "/" + Integer.toString(getMonth) + "/" + Integer.toString(getDate));
		String sgetMonth = Integer.toString(getMonth);
		if (sgetMonth.length() < 2) {
			sgetMonth = "0" + sgetMonth;
		}
		String sgetDate = Integer.toString(getDate);
		if (sgetDate.length() < 2) {
			sgetDate = "0" + sgetDate;
		}
		String sgetYear = Integer.toString(getYear);
		//LOG.debug("Year/Month/Date: " + sgetYear + "/" + sgetMonth + "/" + sgetDate);
		getRequestItemLoginStatus.addParameter("pat_submit", "xxx");
		
		//LOG.debug("RequestItemPickLocation-PickupLocation: " + pickupLocation);
		//LOG.debug("RequestItemPickLocation-Month: " + sgetMonth);
		//LOG.debug("RequestItemPickLocation-Date: " + sgetDate);
		//LOG.debug("RequestItemPickLocation-Year " + sgetYear);
		
		getRequestItemLoginStatus.addParameter("locx00", pickupLocation);
		getRequestItemLoginStatus.addParameter("needby_Month", sgetMonth);
		getRequestItemLoginStatus.addParameter("needby_Day", sgetDate);
		getRequestItemLoginStatus.addParameter("needby_Year", sgetYear);
		
		StatusString getLoginStatus = SingleLogInForm(getRequestItemLoginStatus);
		if (getLoginStatus.recordStatus.returnStatus) {
			iStatus.returnStatus = true;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-RequestItemPickLocation - Success";
			loginStatus.recordStatus = iStatus;
			loginStatus.statusValue = getLoginStatus.statusValue;
		} else {
			iStatus.returnStatus = false;
			iStatus.returnMsg = "RequestItemPickLocation - " + getLoginStatus.recordStatus.returnMsg;
			loginStatus.recordStatus = iStatus;
			loginStatus.statusValue = null;
		}
		return loginStatus;
	}
	
	public StatusString RequestItemConfirmLogIn (ArrayList<PairGroup> authUserName, ArrayList<PairGroup> authUserPassword,
			String pickupLocation, int getYear, int getMonth, int getDate, String itemValue, String baseUrl) {
		Status iStatus = new Status();
		StatusString loginStatus = new StatusString();
		
		PostMethod getRequestItemLoginStatus = new PostMethod(baseUrl);

		//LOG.debug("user, pw, item, loc: " + authenticatedUserName + ", " + authenticatedUserPassword + ", " + itemValue + ", " + pickupLocation);
		getRequestItemLoginStatus.addParameter("radio", itemValue);
		
		for (int x = 0; x < authUserName.size(); x++) {
			getRequestItemLoginStatus.addParameter(authUserName.get(x).firstValue, authUserName.get(x).secondValue);
			//LOG.debug("User Pair: " + authUserName.get(x).firstValue + ", " + authUserName.get(x).secondValue);
		}
		
		for (int x = 0; x < authUserPassword.size(); x++) {
			getRequestItemLoginStatus.addParameter(authUserPassword.get(x).firstValue, authUserPassword.get(x).secondValue);
			//LOG.debug("Pass Pair: " + authUserPassword.get(x).firstValue + ", " + authUserPassword.get(x).secondValue);
		}
				
		//LOG.debug("Year/Month/Date: " + Integer.toString(getYear) + "/" + Integer.toString(getMonth) + "/" + Integer.toString(getDate));
		String sgetMonth = Integer.toString(getMonth);
		if (sgetMonth.length() < 2) {
			sgetMonth = "0" + sgetMonth;
		}
		String sgetDate = Integer.toString(getDate);
		if (sgetDate.length() < 2) {
			sgetDate = "0" + sgetDate;
		}
		String sgetYear = Integer.toString(getYear);
		//LOG.debug("Year/Month/Date: " + sgetYear + "/" + sgetMonth + "/" + sgetDate);
		getRequestItemLoginStatus.addParameter("pat_submit", "xxx");
		
		LOG.debug("RequestItemConfirmLogIn-PickupLocation: " + pickupLocation);
		LOG.debug("RequestItemConfirmLogIn-Month: " + sgetMonth);
		LOG.debug("RequestItemConfirmLogIn-Date: " + sgetDate);
		LOG.debug("RequestItemConfirmLogIn-Year " + sgetYear);
		
		getRequestItemLoginStatus.addParameter("locx00", pickupLocation);
		getRequestItemLoginStatus.addParameter("needby_Month", sgetMonth);
		getRequestItemLoginStatus.addParameter("needby_Day", sgetDate);
		getRequestItemLoginStatus.addParameter("needby_Year", sgetYear);

		StatusString getLoginStatus = SingleLogInForm(getRequestItemLoginStatus);
		if (getLoginStatus.recordStatus.returnStatus) {
			iStatus.returnStatus = true;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-RequestItemConfirmLogIn - Success";
			loginStatus.recordStatus = iStatus;
			loginStatus.statusValue = getLoginStatus.statusValue;
		} else {
			iStatus.returnStatus = false;
			iStatus.returnMsg = "RequestItemConfirmLogIn - " + getLoginStatus.recordStatus.returnMsg;
			loginStatus.recordStatus = iStatus;
			loginStatus.statusValue = null;
		}
		return loginStatus;
	}
	
	public StatusString LogOut (String baseUrl) {
		Status iStatus = new Status();
		StatusString logOutStatus = new StatusString();
		
		//baseUrl = "https://" + IIIClassicBaseUrl + "/logout";
		//LOG.debug("Logout - baseUrl: " + baseUrl);
		PostMethod postMethod = new PostMethod(baseUrl);
		
		StatusString getLogOutStatus = SingleLogInForm(postMethod);
		
		if (getLogOutStatus.recordStatus.returnStatus) {
			LOG.debug("Millennium-LogOut - Success");
			iStatus.returnStatus = true;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-LogOut - Success";
			logOutStatus.recordStatus = iStatus;
			logOutStatus.statusValue = getLogOutStatus.statusValue;
		} else {
			LOG.debug("Millennium-LogOut - False - " + getLogOutStatus.recordStatus.returnMsg);
			iStatus.returnStatus = true;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-LogOut - Success";
			logOutStatus.recordStatus = iStatus;
			logOutStatus.statusValue = getLogOutStatus.statusValue;

		}
		return logOutStatus;
	}
		
	/**
	 * Bach Nguyen - 01/05/2012 - Created SingleLoginForm to use for all login
	 * If success then replace all login classes above
	 * Attempts a login using the given PostMethod by adding the needed form parameters.
	 * 
	 * @param postMethod
	 *            - the PostMethod with all other needed params already
	 *            inserted.
	 * @return the response as a String.
	 */
	public StatusString SingleLogInForm(PostMethod postMethod) {
	
		String html = null;
		Status iStatus = new Status();
		StatusString loginStatus = new StatusString();

		try {
			int statusCode = client.executeMethod(postMethod);
			//LOG.debug("statusCode: " + statusCode);
			if (statusCode == 200) {
				 LOG.debug("SingleLogInForm - statusCode(" + statusCode + ") - Success received Login page");
				 html = postMethod.getResponseBodyAsString();
				 //LOG.debug("LoginWebForm - html.length: " + html.length());
				 iStatus.returnStatus = true;
				 iStatus.returnMsg = "MillenniumRemoteServiceManager-SingleLogInForm - Success";
				 loginStatus.recordStatus = iStatus;
				 loginStatus.statusValue = html;
				 
			} else if (statusCode == 301) {
				 LOG.debug("SingleLogInForm - statusCode(" + statusCode + ") - Link moved permanently");
				 String redirectLocation = postMethod.getResponseHeader("Location").getValue(); 
				 LOG.debug("MillenniumRemoteServiceManager-SingleLogInForm - redirectLocation: '" + redirectLocation +"'");
				 
				 if (redirectLocation.length() > 0) {
			  		//LOG.debug("SingleLogInForm - Found Redirect Url");
					iStatus.returnStatus = true;
					iStatus.returnMsg = "MillenniumRemoteServiceManager-SingleLogInForm - (Handshake-301)";
					loginStatus.recordStatus = iStatus;
					loginStatus.statusValue = redirectLocation;
				 } else {
				 	//LOG.debug("SingleLogInForm - Error in finding Redirct Url");
					iStatus.returnStatus = false;
					iStatus.returnMsg = "SingleLogInForm - Error in finding Redirct Url";
					loginStatus.recordStatus = iStatus;
					loginStatus.statusValue = html;
				}
				 
			} else if (statusCode == 302) {
				String redirectLocation = postMethod.getResponseHeader("Location").getValue();
				//LOG.debug("LoginWebForm (Handshake-302) - redirectLocation: " + redirectLocation);
				//LOG.debug("LoginWebForm - HostName: " + postMethod.getURI().getHost());
				String url = "http://" + postMethod.getURI().getHost() + redirectLocation;
				if (redirectLocation.contains(postMethod.getURI().getHost())) {
					url = redirectLocation;	
				}
				LOG.debug("SingleLogInForm-url(302): " + url);
				GetMethod getMethod = new GetMethod(url);
	
				statusCode = client.executeMethod(getMethod);
				//LOG.debug("SingleLogInForm-statusCode of getPost: " + statusCode);
				
				if (statusCode == 200) {
					LOG.debug("SingleLogInForm - (postMethod(302)-GetMethod (Handshake-200)");
					html = getMethod.getResponseBodyAsString();
					iStatus.returnStatus = true;
					iStatus.returnMsg = "MillenniumRemoteServiceManager-SingleLogInForm - (postMethod(302)-GetMethod (Handshake-200)";
					loginStatus.recordStatus = iStatus;
					loginStatus.statusValue = html;
				 } else {
					LOG.debug("SingleLogInForm - Error in (postMethod(302)-getMethod(" + statusCode + ")");
					iStatus.returnStatus = false;
					iStatus.returnMsg = "SingleLogInForm - Error in (postMethod(302)-getMethod(" + statusCode + ")";
					loginStatus.recordStatus = iStatus;
					loginStatus.statusValue = html;
				}
				if (getMethod != null) {
					getMethod.releaseConnection();
				}
			}
			else {
				LOG.debug("SingleLogInForm - statusCode(" + statusCode + ") - false received Login page");
				iStatus.returnStatus = false;
				iStatus.returnMsg = "MillenniumRemoteServiceManager-SingleLogInForm - statusCode(" + statusCode + "). " + errorCodeMsg.ErrorMessage(470);
				loginStatus.recordStatus = iStatus;
				loginStatus.statusValue = html;
			 } 

		} catch (HttpException e1) {
			LOG.error("SingleLogInForm - Error! HttpException.");
			//e1.printStackTrace();
			
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-SingleLogInForm - " + errorCodeMsg.ErrorMessage(490);
			loginStatus.recordStatus = iStatus;
			loginStatus.statusValue = html;
			
		} catch (IOException e1) {
			LOG.error("SingleLogInForm - Error! IOException.");
			if (e1.getMessage().contains("unable to find valid certification path")) {
				LOG.debug("SingleLoginForm - Error! IOException - SSL-Unable to find valid certification path!");
				iStatus.returnStatus = false;
				iStatus.returnMsg = "MillenniumRemoteServiceManager-SingleLogInForm - " + errorCodeMsg.ErrorMessage(482);;
				loginStatus.recordStatus = iStatus;
				loginStatus.statusValue = html;	
			} else {
				iStatus.returnStatus = false;
				iStatus.returnMsg = "MillenniumRemoteServiceManager-SingleLogInForm - " + errorCodeMsg.ErrorMessage(480);;
				loginStatus.recordStatus = iStatus;
				loginStatus.statusValue = html;		
			}
			//e1.printStackTrace();
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
		return loginStatus;
	}
	
	/*
	 * 11/09/2011 @ 9:00AM - Bach Nguyen - Added getAuthenticationItemsPage
	 * This function is using for get AuthenticationItemsPage such as Currently Item Checked Out, Holding, Overdue, Fine
	 * iLookup will be items, overdue, fines, etc
	 */
	
	public StatusString getAuthenticationItemsPage (String userId, String sessionId, String iLookup) {
		Status iStatus = new Status();
		StatusString getItempageStatus = new StatusString();
		String html = null;
		//String baseUrl = strConfig.getProperty("millenniumUrl");
		String baseUrl = MillenniumConfig.getURL(); // Note: You should probably use IIIClassicBaseUrl.
		String url = "https://" + baseUrl + "/patroninfo~" + SNo + "/" + userId + "/" + iLookup;

		GetMethod getMethod = null;
		try {
			synchronized (client) {
				getMethod = new GetMethod(url);
				getMethod.addRequestHeader("Cookie", "III_SESSION_ID=" + sessionId);
				int statusCode = client.executeMethod(getMethod);

				if (statusCode == 200) {
					LOG.debug("getAuthenticationItemsPage for: (" + iLookup + ") - Success, statusCode(200).");
					html = getMethod.getResponseBodyAsString();
					iStatus.returnStatus = true;
					iStatus.returnMsg = "MillenniumRemoteServiceManager-getAuthenticationItemsPage for: (" + iLookup + ") - Success";
					getItempageStatus.recordStatus = iStatus;
					getItempageStatus.statusValue = html;
				} else // We could not get the page
				{
					LOG.debug("getAuthenticationItemsPage for: (" + iLookup + ") - False, statusCode (" + statusCode + ").");
					iStatus.returnStatus = false;
					iStatus.returnMsg = "MillenniumRemoteServiceManager-getAuthenticationItemsPage for: (" + iLookup + ") - False";
					getItempageStatus.recordStatus = iStatus;
					getItempageStatus.statusValue = html;
				}
			}
		} catch (HttpException e) {
			//e.printStackTrace();
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-getAuthenticationItemsPage for: (" + iLookup + ") - " + errorCodeMsg.ErrorMessage(490);
			getItempageStatus.recordStatus = iStatus;
			getItempageStatus.statusValue = sessionId;
		} catch (IOException e) {
			//e.printStackTrace();
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-getAuthenticationItemsPagefor: (" + iLookup + ") - " + errorCodeMsg.ErrorMessage(480);
			getItempageStatus.recordStatus = iStatus;
			getItempageStatus.statusValue = sessionId;
		} finally {
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
		return getItempageStatus;
	}
	
	/*
	 * 11/10/2011 @ 8:15 AM - Bach Nguyen - Added getItemsCheckedOut to get information about Items Currently Checked Out
	 * It will look all brecord, Title, barcode, Status and Call number in received html page then put them into array list.
	 * Finally, if all array size for brecord, title.... are the same then put it into master array list to return to response
	 * ArrayList<AuthenticationItemsInfo> 
	 */
	public UserItemInfo getItemsCheckedOut (String html, boolean foundRenew) {
		//String html = null;
		Status iStatus = new Status();
		UserItemInfo itemsStatus = new UserItemInfo();
		ArrayList<AuthenticationItemsInfo> authenticationItemsInfoList = new ArrayList<AuthenticationItemsInfo>();
		//html = "";//getAuthenticationItemsPage (userId, sessionId, iLookup);
		if (html != null && html.contains("<a href=\"/logout")) {
			LOG.debug("Success Login into items page");
			//String Testing = getTesting (html);
			//LOG.debug("testing:" + Testing);
			
			int groupNo[]; 
			PatternGroup iPatternGroup = new PatternGroup();
			ArrayList <PatternGroup> patternGroupList = new ArrayList<PatternGroup>();
				
			// Get Mark (Renew checkbox) 
			iPatternGroup.patternValue = "^<tr class=\"patFuncEntry\"><td(.*?)class=\"patFuncMark\"><input type=\"(.*?)\" name=\"(.*?)\" id=\"(.*?)\" value=\"(.*?)\"(.*?)/>(.*?)$";
			groupNo = new int[1];
			groupNo[0] = 4;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			StatusArrayString iMarkList = new StatusArrayString();
			int itemProcess = 1;
			if (foundRenew) {
				for (int x=0; x < patternGroupList.size(); x++) {
					/*
					LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
					LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
					for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
						LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
					}
					*/

					iMarkList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Items Mark Id");
					if (iMarkList.recordStatus.returnStatus && iMarkList.itemsList.size() > 0) {
						break;
					} else if (itemProcess < patternGroupList.size()) {
						LOG.debug("Could not find Check Mark ID for this iMark pattern. Go to next Pattern group");
					} else {
						LOG.debug("Could not find Check Mark ID for this iMark pattern.");
					}
					itemProcess++;
				}
				//LOG.debug("iMarkList - Size: " + iMarkList.itemsList.size() + " - " + iMarkList.recordStatus.returnStatus);
			}

			// Get Mark Value (Renew checkbox) 
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			patternGroupList = new ArrayList<PatternGroup>();
			iPatternGroup.patternValue = "^<tr class=\"patFuncEntry\"><td(.*?)class=\"patFuncMark\"><input type=\"(.*?)\" name=\"(.*?)\" id=\"(.*?)\" value=\"(.*?)\"(.*?)/>(.*?)$";
			groupNo = new int[1];
			groupNo[0] = 5;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			StatusArrayString iMarkValueList = new StatusArrayString();
			itemProcess = 1;
			if (foundRenew) {
				for (int x=0; x < patternGroupList.size(); x++) {
					/*
					LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
					LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
					for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
						LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
					}
					*/
					iMarkValueList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Items Mark value");
					if (iMarkValueList.recordStatus.returnStatus && iMarkValueList.itemsList.size() > 0) {
						break;
					} else if (itemProcess < patternGroupList.size()) {
						LOG.debug("Could not find Check Mark Value for this iMark Value pattern. Go to next Pattern group");
					} else {
						LOG.debug("Could not find Check Mark Value for this iMark Value pattern.");
					}
					itemProcess++;
				}
				//LOG.debug("iMarkValueList - Size: " + iMarkValueList.itemsList.size() + " - " + iMarkValueList.recordStatus.returnStatus);
			}

			// Get bRecord 
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			patternGroupList = new ArrayList<PatternGroup>();
			iPatternGroup.patternValue = "^(.*?)<td(.*?)class=\"patFuncTitle\">(.*?)<a href=\"/record=(.*?)~" + SNo +"\">(.*?)</a></label>$";
			groupNo = new int[1];
			groupNo[0] = 4;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			StatusArrayString iRecordList = new StatusArrayString();
			itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				iRecordList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - bRecord");
				if (iRecordList.recordStatus.returnStatus && iRecordList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find bRecord value for this bRecord pattern. Go to next Pattern group");
				} else {
					LOG.debug("Could not find bRecord value for this bRecord pattern.");
				}
				itemProcess++;
			}
			//LOG.debug("iRecordList - Size: " + iRecordList.itemsList.size() + " - " + iRecordList.recordStatus.returnStatus);
			
			// Get Title 
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			patternGroupList = new ArrayList<PatternGroup>();
			iPatternGroup.patternValue = "^(.*?)<td(.*?)class=\"patFuncTitle\">(.*?)<a href=\"/record=(.*?)~" + SNo + "\">(.*?)</a></label>$";
			groupNo = new int[1];
			groupNo[0] = 5;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			StatusArrayString iTitleList = new StatusArrayString();
			itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				iTitleList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Title");
				if (iTitleList.recordStatus.returnStatus && iTitleList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find Title value for this Title pattern. Go to next Pattern group");
				} else {
					LOG.debug("Could not find Title value for this Title pattern.");
				}
				itemProcess++;
			}
			//LOG.debug("iTitleList - Size: " + iTitleList.itemsList.size() + " - " + iTitleList.recordStatus.returnStatus);

			// Get Barcode	
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			patternGroupList = new ArrayList<PatternGroup>();
			iPatternGroup.patternValue = "^(.*?)<td(.*?)class=\"patFuncBarcode\">(.*?)</td>$";
			groupNo = new int[1];
			groupNo[0] = 3;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			StatusArrayString iBarCodeList = new StatusArrayString();
			itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				iBarCodeList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Barcode");
				if (iBarCodeList.recordStatus.returnStatus && iBarCodeList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find Barcode value for this Barcode pattern. Go to next Pattern group");
				} else {
					LOG.debug("Could not find Barcode value for this Barcode pattern.");
				}
				itemProcess++;
			}
			//LOG.debug("iBarCodeList - Size: " + iBarCodeList.itemsList.size() + " - " + iBarCodeList.recordStatus.returnStatus);

			// Get Status
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			patternGroupList = new ArrayList<PatternGroup>();
			iPatternGroup.patternValue = "^(.*?)<td(.*?)class=\"patFuncStatus\">(?s)(.*?)</td>$";
			groupNo = new int[1];
			groupNo[0] = 3;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			StatusArrayString iStatusList = new StatusArrayString();
			itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				iStatusList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Item Status");
				if (iStatusList.recordStatus.returnStatus && iStatusList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find Status value for this Status pattern[" + x + "]. Go to next Pattern group");
				} else {
					LOG.debug("Could not find Status value for this Status pattern.");
				}
				itemProcess++;
			}
			//LOG.debug("iStatusList - Size: " + iStatusList.itemsList.size() + " - " + iStatusList.recordStatus.returnStatus);
		
			// Get Call No			
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			patternGroupList = new ArrayList<PatternGroup>();
			iPatternGroup.patternValue = "^(.*?)<td(.*?)class=\"patFuncCallNo\">(?s)(.*?)</td>$";
			groupNo = new int[1];
			groupNo[0] = 3;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			StatusArrayString iCallNoList = new StatusArrayString();
			itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				iCallNoList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Call Number");
				if (iCallNoList.recordStatus.returnStatus && iCallNoList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find Call Number value for this Call Number pattern. Go to next Pattern group");
				} else {
					LOG.debug("Could not find Call Number value for this Call Number pattern.");
				}
				itemProcess++;
			}
			//LOG.debug("iCallNoList - Size: " + iCallNoList.itemsList.size() + " - " + iCallNoList.recordStatus.returnStatus);
			
			if (foundRenew) {
				if ((!iRecordList.itemsList.isEmpty() && !iTitleList.itemsList.isEmpty() && !iBarCodeList.itemsList.isEmpty() && 
						!iStatusList.itemsList.isEmpty() && !iCallNoList.itemsList.isEmpty() && !iMarkList.itemsList.isEmpty() &&
						!iMarkValueList.itemsList.isEmpty()) && 
						(iRecordList.itemsList.size() == iTitleList.itemsList.size()) && 
						(iTitleList.itemsList.size() == iBarCodeList.itemsList.size()) && 
						(iBarCodeList.itemsList.size() == iStatusList.itemsList.size()) && 
						(iStatusList.itemsList.size() == iCallNoList.itemsList.size()) &&
						(iCallNoList.itemsList.size() == iMarkList.itemsList.size()) &&
						(iMarkList.itemsList.size() == iMarkValueList.itemsList.size())){
					
					LOG.debug("Millennium - All Array ItemStatus Size are match (Same Array Size)");
						
					for (int i=0; i < iRecordList.itemsList.size(); i++){
						AuthenticationItemsInfo authenticationItemsInfo = new AuthenticationItemsInfo();
						authenticationItemsInfo.bRecord = iRecordList.itemsList.get(i);
						authenticationItemsInfo.iTitle = iTitleList.itemsList.get(i);
						authenticationItemsInfo.iBarcode = iBarCodeList.itemsList.get(i);
						authenticationItemsInfo.iStatus = iStatusList.itemsList.get(i);
						authenticationItemsInfo.iCallNo = iCallNoList.itemsList.get(i);
						authenticationItemsInfo.iMark = iMarkList.itemsList.get(i);
						authenticationItemsInfo.iMarkValue = iMarkValueList.itemsList.get(i);
						authenticationItemsInfoList.add(authenticationItemsInfo);
					}

					int i = 0;
					//LOG.debug("Size of authenticationItemsInfoList: " + authenticationItemsInfoList.size());	
					while (i < authenticationItemsInfoList.size()) {
						LOG.debug("authenticationItemsInfoList[" + i + "].bRecord: " + authenticationItemsInfoList.get(i).bRecord);
						LOG.debug("authenticationItemsInfoList[" + i + "].iTitle: " + authenticationItemsInfoList.get(i).iTitle);
						LOG.debug("authenticationItemsInfoList[" + i + "].iBarcode: " + authenticationItemsInfoList.get(i).iBarcode);
						LOG.debug("authenticationItemsInfoList[" + i + "].iStatus: " + authenticationItemsInfoList.get(i).iStatus);
						LOG.debug("authenticationItemsInfoList[" + i + "].iCallNo: " + authenticationItemsInfoList.get(i).iCallNo);
						LOG.debug("authenticationItemsInfoList[" + i + "].iMark: " + authenticationItemsInfoList.get(i).iMark);
						LOG.debug("authenticationItemsInfoList[" + i + "].iMarkValue: " + authenticationItemsInfoList.get(i).iMarkValue);
					    i++;
					}
					iStatus.returnStatus = true;
					iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemsCheckedOut - Success";
					itemsStatus.recordStatus = iStatus;
					itemsStatus.itemsList = authenticationItemsInfoList;
				}
				else { // if (iRecordList.isEmpty() && iTitleList.isEmpty() && iBarCodeList.isEmpty() && iStatusList.isEmpty() && iCallNoList.isEmpty()){
					LOG.debug("Millennium - All Array AuthenticationItemsStatus Size for Items are empty!");
					iStatus.returnStatus = false;
					iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemsCheckedOut - Array AuthenticationItemsStatus Size for Items are empty";
					itemsStatus.recordStatus = iStatus;
					itemsStatus.itemsList = authenticationItemsInfoList;
				}				
			} else {
				if ((!iRecordList.itemsList.isEmpty() && !iTitleList.itemsList.isEmpty() && !iBarCodeList.itemsList.isEmpty() &&
						!iStatusList.itemsList.isEmpty() && !iCallNoList.itemsList.isEmpty()) && 
						(iRecordList.itemsList.size() == iTitleList.itemsList.size()) && 
						(iTitleList.itemsList.size() == iBarCodeList.itemsList.size()) &&
						(iBarCodeList.itemsList.size() == iStatusList.itemsList.size()) &&
						(iStatusList.itemsList.size() == iCallNoList.itemsList.size())){
					
					LOG.debug("Millennium - All Array ItemStatus Size are match (Same Array Size)");
						
					for (int i=0; i < iRecordList.itemsList.size(); i++){
						AuthenticationItemsInfo authenticationItemsInfo = new AuthenticationItemsInfo();
						authenticationItemsInfo.bRecord = iRecordList.itemsList.get(i);
						authenticationItemsInfo.iTitle = iTitleList.itemsList.get(i);
						authenticationItemsInfo.iBarcode = iBarCodeList.itemsList.get(i);
						authenticationItemsInfo.iStatus = iStatusList.itemsList.get(i);
						authenticationItemsInfo.iCallNo = iCallNoList.itemsList.get(i);
						authenticationItemsInfo.iMark = null;
						authenticationItemsInfo.iMarkValue = iMarkValueList.itemsList.get(i);
						authenticationItemsInfoList.add(authenticationItemsInfo);
					}

					int i = 0;
					//LOG.debug("Size of authenticationItemsInfoList: " + authenticationItemsInfoList.size());	
					while (i < authenticationItemsInfoList.size()) {
						LOG.debug("authenticationItemsInfoList[" + i + "].bRecord: " + authenticationItemsInfoList.get(i).bRecord);
						LOG.debug("authenticationItemsInfoList[" + i + "].iTitle: " + authenticationItemsInfoList.get(i).iTitle);
						LOG.debug("authenticationItemsInfoList[" + i + "].iBarcode: " + authenticationItemsInfoList.get(i).iBarcode);
						LOG.debug("authenticationItemsInfoList[" + i + "].iStatus: " + authenticationItemsInfoList.get(i).iStatus);
						LOG.debug("authenticationItemsInfoList[" + i + "].iCallNo: " + authenticationItemsInfoList.get(i).iCallNo);
						LOG.debug("authenticationItemsInfoList[" + i + "].iMark: " + authenticationItemsInfoList.get(i).iMark);
						LOG.debug("authenticationItemsInfoList[" + i + "].iMarkValue: " + authenticationItemsInfoList.get(i).iMarkValue);
					    i++;
					}
					iStatus.returnStatus = true;
					iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemsCheckedOut - Success";
					itemsStatus.recordStatus = iStatus;
					itemsStatus.itemsList = authenticationItemsInfoList;
				}
				else { // if (iRecordList.isEmpty() && iTitleList.isEmpty() && iBarCodeList.isEmpty() && iStatusList.isEmpty() && iCallNoList.isEmpty()){
					LOG.debug("Millennium - All Array AuthenticationItemsStatus Size for Items are empty!");
					iStatus.returnStatus = false;
					iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemsCheckedOut - Array AuthenticationItemsStatus Size for Items are empty";
					itemsStatus.recordStatus = iStatus;
					itemsStatus.itemsList = authenticationItemsInfoList;
				}
			}

		} else {
			LOG.debug("Millennium - Items page does not contain good information!");
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemsCheckedOut - Items page does not contain good information!";
			itemsStatus.recordStatus = iStatus;
			itemsStatus.itemsList = authenticationItemsInfoList;
		} //if (html != null && html.contains("<a href=\"/logout"))
		return itemsStatus;
	}
	
	/*
	 * 11/10/2011 @ 2:15 PM - Bach Nguyen - Added getItemsOverdues to get information about Items Overdues
	 * It will look all Fines in received html page then put them into array list.
	 * Finally, if all array size for brecord, title.... are the same then put it into master array list to return to response
	 * ArrayList<AuthenticationItemsInfo> 
	 */
		
	public UserItemInfo getItemsOverdues (String html) {
		//String html = null;
		Status iStatus = new Status();
		UserItemInfo itemsStatus = new UserItemInfo();
		ArrayList<AuthenticationItemsInfo> authenticationItemsInfoList = new ArrayList<AuthenticationItemsInfo>();
		//html = getAuthenticationItemsPage (userId, sessionId, iLookup);
		if (html != null && html.contains("<a href=\"/logout")) {
			LOG.debug("Success Login into overdues page");
			int groupNo[]; 
			PatternGroup iPatternGroup = new PatternGroup();
			ArrayList <PatternGroup> patternGroupList = new ArrayList<PatternGroup>();
				
			// Get FinesEntryTitle 
			iPatternGroup.patternValue = "^(.*?)<tr class=\"patFuncFinesEntryTitle\">(?s)(.*?)<td(.*?)class=\"patFuncFinesEntryTitle\">(.*?)" +
					"</td>(?s)(.*?)</tr>(?s)(.*?)<tr class=\"patFuncFinesEntryDetail\">(?s)(.*?)<td>(.*?)</td>(?s)(.*?)<td class=\"patFuncFinesDetailType\">" +
					"(.*?)</td>(?s)(.*?)<td(.*?)class=\"patFuncFinesDetailAmt\">(.*?)</td>$";
			groupNo = new int[1];
			groupNo[0] = 4;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
				
			StatusArrayString iFinesEntryTitleList = new StatusArrayString();
			int itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				iFinesEntryTitleList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Fines Entry Title");
				if (iFinesEntryTitleList.recordStatus.returnStatus && iFinesEntryTitleList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find Fines Entry Title value for this FinesEntryTitle pattern. Go to next Pattern group");
				} else {
					LOG.debug("Could not find Fines Entry Title value for this FinesEntryTitle pattern.");
				}
				itemProcess++;
			}
			//LOG.debug("iFinesEntryTitleList - Size: " + iFinesEntryTitleList.itemsList.size() + " - " + iFinesEntryTitleList.recordStatus.returnStatus);
				
			// Get FinesEntryDetail 
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			patternGroupList = new ArrayList<PatternGroup>();
			iPatternGroup.patternValue = "^(.*?)<tr class=\"patFuncFinesEntryTitle\">(?s)(.*?)<td(.*?)class=\"patFuncFinesEntryTitle\">(.*?)" +
					"</td>(?s)(.*?)</tr>(?s)(.*?)<tr class=\"patFuncFinesEntryDetail\">(?s)(.*?)<td>(.*?)</td>(?s)(.*?)<td class=\"patFuncFinesDetailType\">" +
					"(.*?)</td>(?s)(.*?)<td(.*?)class=\"patFuncFinesDetailAmt\">(.*?)</td>$";
			
			groupNo = new int[1];
			groupNo[0] = 8;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			StatusArrayString iFinesEntryDetailList = new StatusArrayString();
			itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				iFinesEntryDetailList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Fines Entry Detail");
				if (iFinesEntryDetailList.recordStatus.returnStatus && iFinesEntryDetailList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find Fines Entry Detail value for this FinesEntryDetail pattern. Go to next Pattern group");
				} else {
					LOG.debug("Could not find Fines Entry Detail value for this FinesEntryDetail pattern.");
				}
				itemProcess++;
			}
			//LOG.debug("iFinesEntryDetailList - Size: " + iFinesEntryDetailList.itemsList.size() + " - " + iFinesEntryDetailList.recordStatus.returnStatus);
				
			// Get FinesDetailType 
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			patternGroupList = new ArrayList<PatternGroup>();
			iPatternGroup.patternValue = "^(.*?)<tr class=\"patFuncFinesEntryTitle\">(?s)(.*?)<td(.*?)class=\"patFuncFinesEntryTitle\">(.*?)" +
					"</td>(?s)(.*?)</tr>(?s)(.*?)<tr class=\"patFuncFinesEntryDetail\">(?s)(.*?)<td>(.*?)</td>(?s)(.*?)<td class=\"patFuncFinesDetailType\">" +
					"(.*?)</td>(?s)(.*?)<td(.*?)class=\"patFuncFinesDetailAmt\">(.*?)</td>$";
			
			groupNo = new int[1];
			groupNo[0] = 10;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			StatusArrayString iFinesDetailTypeList = new StatusArrayString();
			itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				iFinesDetailTypeList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Fines Detail Type");
				if (iFinesDetailTypeList.recordStatus.returnStatus && iFinesDetailTypeList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find Fines Detail Type value for this FinesDetailType pattern. Go to next Pattern group");
				} else {
					LOG.debug("Could not find Fines Detail Type value for this FinesDetailType pattern.");
				}
				itemProcess++;
			}
			//LOG.debug("iFinesDetailTypeList - Size: " + iFinesDetailTypeList.itemsList.size() + " - " + iFinesDetailTypeList.recordStatus.returnStatus);
				
			// Get FinesDetailAmt 
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			patternGroupList = new ArrayList<PatternGroup>();
			iPatternGroup.patternValue = "^(.*?)<tr class=\"patFuncFinesEntryTitle\">(?s)(.*?)<td(.*?)class=\"patFuncFinesEntryTitle\">(.*?)" +
					"</td>(?s)(.*?)</tr>(?s)(.*?)<tr class=\"patFuncFinesEntryDetail\">(?s)(.*?)<td>(.*?)</td>(?s)(.*?)<td class=\"patFuncFinesDetailType\">" +
					"(.*?)</td>(?s)(.*?)<td(.*?)class=\"patFuncFinesDetailAmt\">(.*?)</td>$";
			
			groupNo = new int[1];
			groupNo[0] = 13;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			StatusArrayString iFinesDetailAmtList = new StatusArrayString();
			itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				iFinesDetailAmtList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Fines Detail Amount");
				if (iFinesDetailAmtList.recordStatus.returnStatus && iFinesDetailAmtList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find Fines Detail Amt value for this FinesDetailAmt pattern. Go to next Pattern group");
				} else {
					LOG.debug("Could not find Fines Detail Amt value for this FinesDetailAmt pattern.");
				}
				itemProcess++;
			}
			//LOG.debug("iFinesDetailAmtList - Size: " + iFinesDetailAmtList.itemsList.size() + " - " + iFinesDetailAmtList.recordStatus.returnStatus);
				
			// Get FuncFinesTotalLabel 
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			patternGroupList = new ArrayList<PatternGroup>();
			iPatternGroup.patternValue = "^(.*?)<td(.*?)class=\"patFuncFinesTotalAmt\">(.*?)</em></td>$";
			groupNo = new int[1];
			groupNo[0] = 3;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			StatusArrayString iFuncFinesTotalLabelList = new StatusArrayString();
			itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				iFuncFinesTotalLabelList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Fines Total Label");
				if (iFuncFinesTotalLabelList.recordStatus.returnStatus && iFuncFinesTotalLabelList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find Fines Total Label value for this FuncFinesTotalLabel pattern. Go to next Pattern group");
				} else {
					LOG.debug("Could not find Fines Total Label value for this Title pattern.");
				}
				itemProcess++;
			}
			//LOG.debug("iFuncFinesTotalLabelList - Size: " + iFuncFinesTotalLabelList.itemsList.size() + " - " + iFuncFinesTotalLabelList.recordStatus.returnStatus);
			
			// Bach Nguyen - Later we can compare the total amount here with total FinesDetailAmt
			//LOG.debug("Total Fines Amount: " + iFuncFinesTotalLabelList.get(0));
			
			if (!iFinesEntryTitleList.itemsList.isEmpty() && iFinesEntryTitleList.itemsList.size() > 0){
					
				LOG.debug("Millennium - All Array ItemStatus Size are match (Same Array Size)");
				
				for (int i=0; i < iFinesEntryTitleList.itemsList.size(); i++){
					AuthenticationItemsInfo authenticationItemsInfo = new AuthenticationItemsInfo();
					authenticationItemsInfo.iFinesEntryTitle = iFinesEntryTitleList.itemsList.get(i);
					if (iFinesEntryDetailList.itemsList.get(i).equals("Missing")) {
						authenticationItemsInfo.iFinesEntryDetail = "Missing";
					} else {
						authenticationItemsInfo.iFinesEntryDetail = iFinesEntryDetailList.itemsList.get(i);	
					}
					if (iFinesDetailTypeList.itemsList.get(i).equals("Missing")) {
						authenticationItemsInfo.iFinesDetailType = "Fine";
					} else {
						authenticationItemsInfo.iFinesDetailType = iFinesDetailTypeList.itemsList.get(i);
					}
					authenticationItemsInfo.iFinesDetailAmt = iFinesDetailAmtList.itemsList.get(i);
					authenticationItemsInfoList.add(authenticationItemsInfo);
				}

				int i = 0;
				//LOG.debug("Size of authenticationItemsInfoList: " + authenticationItemsInfoList.size());	
				while (i < authenticationItemsInfoList.size()) {
					LOG.debug("authenticationItemsInfoList[" + i + "].iFinesEntryTitle: " + authenticationItemsInfoList.get(i).iFinesEntryTitle);
					LOG.debug("authenticationItemsInfoList[" + i + "].iFinesEntryDetail: " + authenticationItemsInfoList.get(i).iFinesEntryDetail);
					LOG.debug("authenticationItemsInfoList[" + i + "].iFinesDetailType: " + authenticationItemsInfoList.get(i).iFinesDetailType);
					LOG.debug("authenticationItemsInfoList[" + i + "].iFinesDetailAmt: " + authenticationItemsInfoList.get(i).iFinesDetailAmt);
					i++;
				}
				iStatus.returnStatus = true;
				iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemsOverdues - Success";
				itemsStatus.recordStatus = iStatus;
				itemsStatus.itemsList = authenticationItemsInfoList;
					
			} else {
				LOG.debug("Millennium - Array AuthenticationItemsStatus Size for Overdues are empty");
				iStatus.returnStatus = false;
				iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemsOverdues - Array AuthenticationItemsStatus Size for Overdues are empty!";
				itemsStatus.recordStatus = iStatus;
				itemsStatus.itemsList = authenticationItemsInfoList;
			}
		} else {
			LOG.debug("Millennium - Items page does not contain good information!");
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemsOverdues - Items page does not contain good information!";
			itemsStatus.recordStatus = iStatus;
			itemsStatus.itemsList = authenticationItemsInfoList;
		} // End http != null
		return itemsStatus;
	}
	
	/*
	 * 11/10/2011 @ 8:15 AM - Bach Nguyen - Added getItemsHolds to get information about Items Holds
	 * It will look all brecord, Title, Status and Location in received html page then put them into array list.
	 * Finally, if all array size for brecord, title.... are the same then put it into master array list to return to response
	 * ArrayList<AuthenticationItemsInfo> 
	 */
	
	public UserItemInfo getItemsHolds (String html, boolean foundCancel) {
		//String html = null;
		Status iStatus = new Status();
		UserItemInfo itemsHoldsStatus = new UserItemInfo();
		ArrayList<AuthenticationItemsInfo> authenticationItemsInfoList = new ArrayList<AuthenticationItemsInfo>();
		//html = "";//getAuthenticationItemsPage (userId, sessionId, iLookup);
		
		/*
		 * 11/01/2011 @2:04PM - Bach Nguyen created this section to simulate the html from text file instead of
		 * real production web site
		 */
		/*
		MillenniumFileHandle millenniumFileHandle = new MillenniumFileHandle();
		html = "";
		html = millenniumFileHandle.ReadTextFile("H:\\My Documents\\LookupUser Holds Test Case.txt"); 
		LOG.debug(html);
		*/
		if (html != null && html.contains("<a href=\"/logout")) {
			LOG.debug("Success Login into holds page!");
			int groupNo[]; 
			PatternGroup iPatternGroup = new PatternGroup();
			ArrayList <PatternGroup> patternGroupList = new ArrayList<PatternGroup>();
				
			// Get bRecord 
			iPatternGroup.patternValue = "^(.*?)<td(.*?)class=\"patFuncTitle\">(?s)(.*?)<a href=\"/record=(.*?)~" + SNo + "\">(.*?)</a></label>$";
			groupNo = new int[1];
			groupNo[0] = 4;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
				
			StatusArrayString iRecordList = new StatusArrayString();
			int itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				iRecordList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Hold Record");
				if (iRecordList.recordStatus.returnStatus && iRecordList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find bRecord value for this bRecord pattern. Go to next Pattern group");
				} else {
					LOG.debug("Could not find bRecord value for this bRecord pattern.");
				}
				itemProcess++;
			}
			//LOG.debug("iRecordList - Size: " + iRecordList.itemsList.size() + " - " + iRecordList.recordStatus.returnStatus);
	
			// Get Title 
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			patternGroupList = new ArrayList<PatternGroup>();
			iPatternGroup.patternValue = "^(.*?)<td(.*?)class=\"patFuncTitle\">(?s)(.*?)<a href=\"/record=(.*?)~" + SNo + "\">(.*?)</a></label>$";
			groupNo = new int[1];
			groupNo[0] = 5;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
				
			StatusArrayString iTitleList = new StatusArrayString();
			itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				iTitleList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Hold Title");
				if (iTitleList.recordStatus.returnStatus && iTitleList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find Title value for this Title pattern. Go to next Pattern group");
				} else {
					LOG.debug("Could not find Title value for this Title pattern.");
				}
				itemProcess++;
			}
			//LOG.debug("iTitleList - Size: " + iTitleList.itemsList.size() + " - " + iTitleList.recordStatus.returnStatus);

			// Get Mark (Cancel checkbox) 
			iPatternGroup.patternValue = "^(.*?)<input type=\"checkbox\"(.*?)name=\"(.*?)\"(.*?)id=\"(.*?)\"(.*?)/></td>(.*?)$";
			//iPatternGroup.patternValue = "^<tr(?s)class=\"patFuncEntry\">(.*?)<td(?s)class=\"patFuncMark\"(.*?)>(.*?)<input(?s)type=\"(.*?)\"(.*?)name=\"(.*?)\"(.*?)id=\"(.*?)\"(.*?)/></td>(.*?)$";
			groupNo = new int[1];
			groupNo[0] = 5;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
				
			StatusArrayString iMarkList = new StatusArrayString();
			itemProcess = 1;
			if (foundCancel) {
				for (int x=0; x < patternGroupList.size(); x++) {
					/*
					LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
					LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
					for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
						LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
					}
					*/
					iMarkList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Hold Mark Id");
					if (iMarkList.recordStatus.returnStatus && iMarkList.itemsList.size() > 0) {
						break;
					} else if (itemProcess < patternGroupList.size()) {
						LOG.debug("Could not find Cancel ID for this iMark pattern. Go to next Pattern group");
					} else {
						LOG.debug("Could not find Cancel ID for this iMark pattern.");
					}
					itemProcess++;
				}
				//LOG.debug("iMarkList - Size: " + iMarkList.itemsList.size() + " - " + iMarkList.recordStatus.returnStatus);
			}

			// Get Status
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			patternGroupList = new ArrayList<PatternGroup>();
			iPatternGroup.patternValue = "^(.*?)<td(.*?)class=\"patFuncStatus\">(?s)(.*?)</td>$";
			groupNo = new int[1];
			groupNo[0] = 3;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			StatusArrayString iStatusList = new StatusArrayString();
			itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				iStatusList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Hold Status");
				if (iStatusList.recordStatus.returnStatus && iStatusList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find Status value for this Status pattern. Go to next Pattern group");
				} else {
					LOG.debug("Could not find Status value for this Status pattern.");
				}
				itemProcess++;
			}
			LOG.debug("iStatusList - Size: " + iStatusList.itemsList.size() + " - " + iStatusList.recordStatus.returnStatus);
				
			// Get Pickup Location	
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			patternGroupList = new ArrayList<PatternGroup>();
			iPatternGroup.patternValue = "^(.*?)<td(.*?)class=\"patFuncPickup\">(.*?)</td>$";
			groupNo = new int[1];
			groupNo[0] = 3;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
				
			StatusArrayString iPickupLocationList = new StatusArrayString();
			itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				iPickupLocationList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Pickup Location");
				if (iPickupLocationList.recordStatus.returnStatus && iPickupLocationList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find Pickup Location value for this Pickup Location pattern. Go to next Pattern group");
				} else {
					LOG.debug("Could not find Pickup Location value for this Pickup Location pattern.");
				}
				itemProcess++;
			}
			LOG.debug("iPickupLocationList - Size: " + iPickupLocationList.itemsList.size() + " - " + iPickupLocationList.recordStatus.returnStatus);
		
			// Get Cancel if not filled by			
			iPatternGroup = new PatternGroup(); // Always reset to get new item value
			patternGroupList = new ArrayList<PatternGroup>();
			iPatternGroup.patternValue = "^(.*?)<td(.*?)class=\"patFuncCancel\">(?s)(.*?)</td>$";
			groupNo = new int[1];
			groupNo[0] = 3;
			iPatternGroup.groupNo = groupNo;
			patternGroupList.add(iPatternGroup);
			
			StatusArrayString iCancelIfNotPickupList = new StatusArrayString();
			itemProcess = 1;
			for (int x=0; x < patternGroupList.size(); x++) {
				/*
				LOG.debug("Pattern group [" + x + "] Pattern Value is: " + patternGroupList.get(x).patternValue);
				LOG.debug("Pattern group [" + x + "] GroupNo length is: " + patternGroupList.get(x).groupNo.length);
				for (int y=0; y < patternGroupList.get(x).groupNo.length; y++) {
					LOG.debug("groupNo [" + y + "] in patternGroup [" + x + "] value is: " + patternGroupList.get(x).groupNo[y]);
				}
				*/
				iCancelIfNotPickupList = getArrayListItemStatus (patternGroupList.get(x).patternValue, html, patternGroupList.get(x).groupNo, "User - Cancel if not filled by");
				if (iCancelIfNotPickupList.recordStatus.returnStatus && iCancelIfNotPickupList.itemsList.size() > 0) {
					break;
				} else if (itemProcess < patternGroupList.size()) {
					LOG.debug("Could not find Cancel If not Filled by value for this Cancel pattern. Go to next Pattern group");
				} else {
					LOG.debug("Could not find Cancel if not Filled by value for this Cancel pattern.");
				}
				itemProcess++;
			}
			LOG.debug("iCancelIfNotPickupList - Size: " + iCancelIfNotPickupList.itemsList.size() + " - " + iCancelIfNotPickupList.recordStatus.returnStatus);
	
			if (foundCancel) {
				if ((!iRecordList.itemsList.isEmpty() && !iTitleList.itemsList.isEmpty() && !iPickupLocationList.itemsList.isEmpty() &&
						!iStatusList.itemsList.isEmpty() && !iMarkList.itemsList.isEmpty() && !iCancelIfNotPickupList.itemsList.isEmpty()) && 
						(iRecordList.itemsList.size() == iTitleList.itemsList.size()) && (iTitleList.itemsList.size() == iPickupLocationList.itemsList.size())
						&& (iPickupLocationList.itemsList.size() == iStatusList.itemsList.size())
						&& (iStatusList.itemsList.size() == iMarkList.itemsList.size())
						&& (iMarkList.itemsList.size() == iCancelIfNotPickupList.itemsList.size())){
					
					LOG.debug("Millennium - All Array ItemStatus Size are match (Same Array Size)");
					
					for (int i=0; i < iRecordList.itemsList.size(); i++){
						AuthenticationItemsInfo authenticationItemsInfo = new AuthenticationItemsInfo();
						authenticationItemsInfo.bRecord = iRecordList.itemsList.get(i);
						authenticationItemsInfo.iTitle = iTitleList.itemsList.get(i);
						authenticationItemsInfo.iMark = iMarkList.itemsList.get(i);
						authenticationItemsInfo.iStatus = iStatusList.itemsList.get(i);
						authenticationItemsInfo.iPickupLocation = iPickupLocationList.itemsList.get(i);
						authenticationItemsInfo.iCancelIfNotPickup = iCancelIfNotPickupList.itemsList.get(i);
						authenticationItemsInfoList.add(authenticationItemsInfo);
					}

					int i = 0;
					//LOG.debug("Size of authenticationItemsInfoList: " + authenticationItemsInfoList.size());	
					while (i < authenticationItemsInfoList.size()) {
						LOG.debug("authenticationItemsInfoList[" + i + "].bRecord: " + authenticationItemsInfoList.get(i).bRecord);
						LOG.debug("authenticationItemsInfoList[" + i + "].iTitle: " + authenticationItemsInfoList.get(i).iTitle);
						LOG.debug("authenticationItemsInfoList[" + i + "].iMark: " + authenticationItemsInfoList.get(i).iMark);
						LOG.debug("authenticationItemsInfoList[" + i + "].iStatus: " + authenticationItemsInfoList.get(i).iStatus);
						LOG.debug("authenticationItemsInfoList[" + i + "].iPickupLocation: " + authenticationItemsInfoList.get(i).iPickupLocation);
					    LOG.debug("authenticationItemsInfoList[" + i + "].iCancelIfNotPickup: " + authenticationItemsInfoList.get(i).iCancelIfNotPickup);
						i++;
					}
						
					iStatus.returnStatus = true;
					iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemsHolds - Success";
					itemsHoldsStatus.recordStatus = iStatus;
					itemsHoldsStatus.itemsList = authenticationItemsInfoList;
				}
				else { //if (iRecordList.isEmpty() && iTitleList.isEmpty() && iPickupLocationList.isEmpty() 
						//&& iStatusList.isEmpty() && iCancelIfNotPickupList.isEmpty() && iMarkList.isEmpty()){
					LOG.debug("Millennium - Array AuthenticationItemsStatus Size for Holds are empty");
					iStatus.returnStatus = false;
					iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemsOverdues - Array AuthenticationItemsStatus Size for Holds are empty!";
					itemsHoldsStatus.recordStatus = iStatus;
					itemsHoldsStatus.itemsList = authenticationItemsInfoList;
				}
				
			} else {
				if ((!iRecordList.itemsList.isEmpty() && !iTitleList.itemsList.isEmpty() &&	!iPickupLocationList.itemsList.isEmpty() &&
						!iStatusList.itemsList.isEmpty() && !iCancelIfNotPickupList.itemsList.isEmpty()) && 
						(iRecordList.itemsList.size() == iTitleList.itemsList.size()) && 
						(iTitleList.itemsList.size() == iPickupLocationList.itemsList.size()) &&
						(iPickupLocationList.itemsList.size() == iStatusList.itemsList.size()) && 
						(iStatusList.itemsList.size() == iCancelIfNotPickupList.itemsList.size())){
					
					LOG.debug("Millennium - All Array ItemStatus Size are match (Same Array Size)-(No Cancel)");
					
					for (int i=0; i < iRecordList.itemsList.size(); i++){
						AuthenticationItemsInfo authenticationItemsInfo = new AuthenticationItemsInfo();
						authenticationItemsInfo.bRecord = iRecordList.itemsList.get(i);
						authenticationItemsInfo.iTitle = iTitleList.itemsList.get(i);
						authenticationItemsInfo.iMark = null;
						authenticationItemsInfo.iStatus = iStatusList.itemsList.get(i);
						authenticationItemsInfo.iPickupLocation = iPickupLocationList.itemsList.get(i);
						authenticationItemsInfo.iCancelIfNotPickup = iCancelIfNotPickupList.itemsList.get(i);
						authenticationItemsInfoList.add(authenticationItemsInfo);
					}

					int i = 0;
					//LOG.debug("Size of authenticationItemsInfoList: " + authenticationItemsInfoList.size());	
					while (i < authenticationItemsInfoList.size()) {
					    LOG.debug("authenticationItemsInfoList[" + i + "].bRecord: " + authenticationItemsInfoList.get(i).bRecord);
					    LOG.debug("authenticationItemsInfoList[" + i + "].iTitle: " + authenticationItemsInfoList.get(i).iTitle);
					    LOG.debug("authenticationItemsInfoList[" + i + "].iMark: " + authenticationItemsInfoList.get(i).iMark);
					    LOG.debug("authenticationItemsInfoList[" + i + "].iStatus: " + authenticationItemsInfoList.get(i).iStatus);
					    LOG.debug("authenticationItemsInfoList[" + i + "].iPickupLocation: " + authenticationItemsInfoList.get(i).iPickupLocation);
					    LOG.debug("authenticationItemsInfoList[" + i + "].iCancelIfNotPickup: " + authenticationItemsInfoList.get(i).iCancelIfNotPickup);
						i++;
					}
						
					iStatus.returnStatus = true;
					iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemsHolds - Success";
					itemsHoldsStatus.recordStatus = iStatus;
					itemsHoldsStatus.itemsList = authenticationItemsInfoList;
				}
				else { //if (iRecordList.isEmpty() && iTitleList.isEmpty() && iPickupLocationList.isEmpty() 
						//&& iStatusList.isEmpty() && iCancelIfNotPickupList.isEmpty() && iMarkList.isEmpty()){
					LOG.debug("Millennium - Array AuthenticationItemsStatus Size for Holds are empty");
					iStatus.returnStatus = false;
					iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemsOverdues - Array AuthenticationItemsStatus Size for Holds are empty!";
					itemsHoldsStatus.recordStatus = iStatus;
					itemsHoldsStatus.itemsList = authenticationItemsInfoList;
				}
			}

		} else {
			LOG.debug("Millennium - Holds page does not contain good information!");
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-getItemsOverdues - Holds page does not contain good information!";
			itemsHoldsStatus.recordStatus = iStatus;
			itemsHoldsStatus.itemsList = authenticationItemsInfoList;
		} // End (html.contains("<a href=\"/logout~S0?\" target=\"_self\">"))
		
		//return authenticationItemsInfoList;
		return itemsHoldsStatus;
	}
	
	/*
	 * This class is for LookupUser - Looking for Checkout Due Date or Holding Date
	 */
	public Date setSimpleDateFormat (String ParseDate) {
		Date dueDate = null; // set to null
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
		String beginStr1 = null;
		String endStr1 = null;
		String lookupPattern = "^(.*?)[0-9][0-9]-[0-9][0-9]-[0-9][0-9](.*?)$";
		Pattern findPattern = Pattern.compile(lookupPattern, Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher findPatternMatch = findPattern.matcher(ParseDate);

		while (findPatternMatch.find()) {
			beginStr1 = findPatternMatch.group(1).trim();
			endStr1 = findPatternMatch.group(2).trim();
			//LOG.debug("beginStr1: " + beginStr1 + ", endStr1: " + endStr1);
		}
		
		String strDate = ParseDate.replaceAll(beginStr1, "").replace(endStr1, "").replace("-", "/").trim(); // format string
		//LOG.debug("setSimpleDateFormat - Status Date: " + strDate);
		
		try {
			dueDate = dateFormat.parse(strDate);
			//LOG.debug("......DueDate: " + dueDate);
		} catch (ParseException pe) {
			LOG.debug("setSimpleDateFormat ERROR: Cannot parse \""	+ strDate + "\"");
		}
		return dueDate;
	}
		
	/*
	 * This class is for RenewItem - Looking for Status
	 */
	public StatusString setRenewSimpleDateFormat (String ParseDate) {
		Status iStatus = new Status();
		StatusString RenewItemDateString = new StatusString();

		//ParseDate = "DUE 05-19-12 <font color=\"(.*?)\">  TOO SOON TO RENEW</font> <span  class=\"patFuncRenewCount\">Renewed 1 (time.*)</span>$";
		//String patternValue = "^DUE(.*?)<font color=\"(.*?)\">(.*?)</font>(.*?)<span(.*?)class=\"patFuncRenewCount\">Renewed 1 (time.*)</span>$";

		//ParseDate = "DUE 03-27-12 <b> RENEWED</b>Now due 05-19-12 <span class=\"patFuncRenewCount\">Renewed 2 times</span>";
		String patternValue = "^DUE(.*?)<b>(.*?)</b>Now due(.*?)<span(.*?)class=\"patFuncRenewCount\">Renewed(.*?)(time.*)</span>$";

		Pattern findPattern = Pattern.compile(patternValue, Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher findPatternMatch = findPattern.matcher(ParseDate);

		if (findPatternMatch.find()) {
			iStatus.returnStatus = true;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-setRenewSimpleDateFormat - Success";
			RenewItemDateString.recordStatus = iStatus;
			RenewItemDateString.statusValue = findPatternMatch.group(3);
		} else {
			patternValue = "^DUE(.*?)<font color=\"(.*?)\">(.*?)</font>(.*?)<span(.*?)class=\"patFuncRenewCount\">Renewed(.*?)(time.*)</span>$";
			findPattern = Pattern.compile(patternValue, Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
			findPatternMatch = findPattern.matcher(ParseDate);
			if (findPatternMatch.find()) {
				iStatus.returnStatus = false;
				iStatus.returnMsg = "MillenniumRemoteServiceManager-setRenewSimpleDateFormat - False";
				RenewItemDateString.recordStatus = iStatus;
				RenewItemDateString.statusValue = findPatternMatch.group(3);
			} else {
				iStatus.returnStatus = false;
				iStatus.returnMsg = "MillenniumRemoteServiceManager-setRenewSimpleDateFormat - Couldn't find pattern";
				RenewItemDateString.recordStatus = iStatus;
				RenewItemDateString.statusValue = "MillenniumRemoteServiceManager-setRenewSimpleDateFormat - Couldn't find pattern";
			}
		}
		return RenewItemDateString;
	}

	public String getReminderLevel (String strStatus) {
		String reminderLevel = null;
		//String beginStr1 = null;
		String endStr1 = null;
		String beginStr2 = null;
		String lookupPattern = "^(.*?)[0-9][0-9]-[0-9][0-9]-[0-9][0-9](.*?)$";
		Pattern findPattern = Pattern.compile(lookupPattern, Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher findPatternMatch = findPattern.matcher(strStatus);

		while (findPatternMatch.find()) {
			//beginStr1 = findPatternMatch.group(1).trim();
			endStr1 = findPatternMatch.group(2).trim();
			//LOG.debug("Millennium - beginStr1: " + beginStr1 + ", endStr1: '" + endStr1 + "'");	
		}
		if (endStr1.length() > 0 && endStr1.contains("RECALLED")) {
			lookupPattern = "^+(.*?)RECALLED(.*?)$";
			findPattern = Pattern.compile(lookupPattern, Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
			findPatternMatch = findPattern.matcher(endStr1);
			while (findPatternMatch.find()) {
				beginStr2 = findPatternMatch.group(1).trim();
				//LOG.debug("Millennium - beginStr2/beginStr1: " + beginStr2);
			}
			endStr1 = null;
			reminderLevel = beginStr2.replace("+", "").trim(); 
			//LOG.debug("Millennium - ReminderLevel: " + reminderLevel);
		}
		LOG.debug("Reminderlevel: '" + reminderLevel +"'");
		return reminderLevel;
	}
	
	public String getItemFineAmount (String strStatus) {
		String itemFineAmount = null;
		//LOG.debug("strStatus: " + strStatus); 
		String lookupPattern = "^(.*?)FINE(.*?)up to now(.*?)</font>(.*?)$";
		Pattern findPattern = Pattern.compile(lookupPattern, Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher findPatternMatch = findPattern.matcher(strStatus);

		while (findPatternMatch.find()) {
			itemFineAmount = findPatternMatch.group(3).replace(")", "").replace(".", "").replace("$", "").trim();
		}
		LOG.debug("itemFineAmount: '" + itemFineAmount +"'");
		return itemFineAmount;
	}
	
	public String getpatFuncStatus (String strStatus) {
		String patFuncStatus = null;
		String beginStr1 = null;
		//String endStr1 = null;
		if (strStatus.contains("reqFillTimeMsg")) {
			String lookupPattern = "^(.*?)<span class=\"reqFillTimeMsg\">(.*?)</span>$";
			Pattern findPattern = Pattern.compile(lookupPattern, Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
			Matcher findPatternMatch = findPattern.matcher(strStatus);

			while (findPatternMatch.find()) {
				beginStr1 = findPatternMatch.group(1).trim();
				//endStr1 = findPatternMatch.group(2).trim();
				//LOG.debug("beginStr1: " + beginStr1 + ", endStr1: '" + endStr1 + "'");	
			}
			patFuncStatus = beginStr1;
		} else {
			patFuncStatus = strStatus;
		}

		return patFuncStatus;
	}
	
	public String getHoldQueuePosition (String strStatus) {
		String HoldQueuePosition = null;
		String beginStr1 = null;
		//String endStr1 = null;
		String lookupPattern = "^(.*?)of(.*?)holds$";
		Pattern findPattern = Pattern.compile(lookupPattern, Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher findPatternMatch = findPattern.matcher(strStatus);
		//LOG.debug("strStatus in getHoldsQueuePosition: " + strStatus);		
		while (findPatternMatch.find()) {
			beginStr1 = findPatternMatch.group(1).trim();
			//endStr1 = findPatternMatch.group(2).trim();
			//LOG.debug("beginStr1: " + beginStr1 + ", endStr1: '" + endStr1 + "'");	
		}
		HoldQueuePosition = beginStr1;
		return HoldQueuePosition;
	}
	
	public String getRenewalCount (String strStatus) {
		String renewCount = null;
		//String beginStr1 = null;
		String lookupPattern = "^(.*?)<span  class=\"patFuncRenewCount\">Renewed(.*?)(time.*)(.*?)$";
		Pattern findPattern = Pattern.compile(lookupPattern, Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher findPatternMatch = findPattern.matcher(strStatus);
		//LOG.debug("strCount in getRenewalCount: " + strStatus);
		//int y = 1;
		while (findPatternMatch.find()) {
			renewCount = findPatternMatch.group(2).trim();
			//LOG.debug("group1: " + findPatternMatch.group(1).trim());
			//LOG.debug("group2: " + findPatternMatch.group(2).trim());
			//LOG.debug("group3: " + findPatternMatch.group(3).trim());
			//LOG.debug("group4: " + findPatternMatch.group(4).trim());
			//LOG.debug("Process: " + y + " - renewCount: " + renewCount);
			//y++;
		}
		return renewCount;
	}
	
	public StatusString getRequestItemValue (String html) {
		Status iStatus = new Status();
		StatusString requestItemValue = new StatusString();
		String lookupPattern = "^<td(.*?)<input type=\"radio\" name=\"radio\" value=\"(.*?)\"(.*?)</td>$";
		Pattern findPattern = Pattern.compile(lookupPattern, Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher findPatternMatch = findPattern.matcher(html);
		if (findPatternMatch.find()) {
			iStatus.returnStatus = true;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-getRequestItemValue - Success";
			requestItemValue.recordStatus = iStatus;
			requestItemValue.statusValue = findPatternMatch.group(2).trim();
		} else {
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-getRequestItemValue - Pattern not found";
			requestItemValue.recordStatus = iStatus;
			requestItemValue.statusValue = null;
		}
		return requestItemValue;
	}
	
	public StatusString getRequestItemTitle (String html) {
		Status iStatus = new Status();
		StatusString requestItemTitle = new StatusString();
		String lookupPattern = "^(.*?)Requesting <strong>(.*?)</strong>(.*?)$";
		Pattern findPattern = Pattern.compile(lookupPattern, Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher findPatternMatch = findPattern.matcher(html);
		if (findPatternMatch.find()) {
			iStatus.returnStatus = true;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-getRequestItemTitle - Success";
			requestItemTitle.recordStatus = iStatus;
			requestItemTitle.statusValue = findPatternMatch.group(2).trim();
		} else {
			iStatus.returnStatus = false;
			iStatus.returnMsg = "MillenniumRemoteServiceManager-getRequestItemTitle - Pattern not found";
			requestItemTitle.recordStatus = iStatus;
			requestItemTitle.statusValue = null;
		}
		return requestItemTitle;
	}
		
	public String getRequestItemLocationValue (String html) {
		String requestItemLocationValue = null;
		String lookupPattern = "^(.*?)<input type=\"hidden\" name=\"locx00\" value=\"(.*?)\"(.*?)/>$";
		Pattern findPattern = Pattern.compile(lookupPattern, Pattern.MULTILINE + Pattern.CASE_INSENSITIVE);
		Matcher findPatternMatch = findPattern.matcher(html);
		while (findPatternMatch.find()) {
			requestItemLocationValue = findPatternMatch.group(2).trim();
		}
		return requestItemLocationValue;
	}
	
	public PairGroup setPairGroup (String firstValue, String secondValue) {
		PairGroup pairGroup = new PairGroup();
		pairGroup.firstValue = firstValue;
		pairGroup.secondValue = secondValue;
		return pairGroup;
	}
}
