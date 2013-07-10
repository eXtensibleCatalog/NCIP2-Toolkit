/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.symphony;





import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.RequestId;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.UserId;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * ServiceManager is responsible for locating the correct back-end service.
 */
public class SymphonyRemoteServiceManager implements RemoteServiceManager {
	
	
	
	private SymphonyConfiguration symphonyConfig;
	{
		try{
			symphonyConfig = (SymphonyConfiguration)ConnectorConfigurationFactory.getConfiguration();
		}
		catch(ToolkitException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	private String scriptUrl = (String) symphonyConfig.getProperty("SirsiScriptURL");
	private String daysUntilHoldExpires = (String) symphonyConfig.getProperty("daysUntilHoldExpires");

	
	public SymphonyRemoteServiceManager() throws Exception {
		
	
	}
	
	 public SymphonyRemoteServiceManager(Properties properties) {
	        // Do nothing
	 }
	 
	    
	public LookupUserTransactionResults lookupSirsiUser(UserId userid) {
		LookupUserTransactionResults lookupUserTransactionResults = new LookupUserTransactionResults();
		try {
			URL url = new URL(scriptUrl + "lookupUser.pl?id="+userid.getUserIdentifierValue());
			StringTokenizer stringTokenizer = this.callURL(url);
			lookupUserTransactionResults = this.parseSirsiUser(stringTokenizer);
		}
		catch(Exception e) {
			lookupUserTransactionResults.setStatusCode(SymphonyConstants.SIRSI_FAILED);
		}
        return lookupUserTransactionResults;
	}
	
	public CheckoutTransactionReults checkoutItem(UserId userid,ItemId itemId) {
		CheckoutTransactionReults checkoutTrans = new CheckoutTransactionReults();
		try {
			URL url = new URL(scriptUrl + "checkoutItem.pl?id="+userid.getUserIdentifierValue()+"&itemid="+itemId.getItemIdentifierValue());
			StringTokenizer stringTokenizer = this.callURL(url);
	        checkoutTrans = this.parseCheckoutResults(stringTokenizer);
		}
		catch(Exception e) {
			checkoutTrans.setStatusCode(SymphonyConstants.SIRSI_FAILED);
		}
        return checkoutTrans;
	}
	
	public CheckInTransactionResults checkInItem(ItemId itemId) {
		CheckInTransactionResults checkInTranResults = new CheckInTransactionResults();
		try {
			URL url = new URL(scriptUrl + "checkinItem.pl?itemid="+itemId.getItemIdentifierValue());
			StringTokenizer stringTokenizer = this.callURL(url);
			checkInTranResults = this.parseCheckInResults(stringTokenizer);
		}
		catch(Exception e) {
			checkInTranResults.setStatusCode(SymphonyConstants.SIRSI_FAILED);
		}
        return checkInTranResults;
	}
	
	public AcceptItemTransactionResults acceptItem(UserId userid,ItemId itemId, RequestId requestId, String title) {
		AcceptItemTransactionResults acceptItemTransactionResults = new AcceptItemTransactionResults();
		try {
			Date now = new Date(); 
			Calendar cal = Calendar.getInstance();  
			cal.setTime(now);  
			cal.add(Calendar.DAY_OF_YEAR, new Integer(daysUntilHoldExpires).intValue()); 
			Date dateHoldExpires = cal.getTime();
		    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		    String dateHoldExpiresAsString = formatter.format(dateHoldExpires);
		    String reqIdDigitsOnly = this.getOnlyNumerics(requestId.getRequestIdentifierValue());
		    String encodedTitle = URLEncoder.encode(SymphonyRemoteServiceManager.truncate(title,40), "UTF-8");
			URL url = new URL(scriptUrl + "acceptItem.pl?id="+userid.getUserIdentifierValue()+"&itemid="+itemId.getItemIdentifierValue()+"&dateExpires=" + dateHoldExpiresAsString + "&requestId=" + reqIdDigitsOnly+ "&fullRequestId=" + requestId.getRequestIdentifierValue() + "&title=" + encodedTitle);
			//System.out.println(url);
			StringTokenizer stringTokenizer = this.callURL(url);
			acceptItemTransactionResults = this.parseAcceptItemResults(stringTokenizer);
		}
		catch(Exception e) {
			acceptItemTransactionResults.setStatusCode(SymphonyConstants.SIRSI_FAILED);
		}
        return acceptItemTransactionResults;
	}
	
	public StringTokenizer callURL(URL url) throws Exception{
		try {
			String result = null;
			URLConnection conn = url.openConnection ();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) 	{
				sb.append(line);
			}	
			rd.close();
			result = sb.toString();
		
			StringTokenizer stringTokenizer = new StringTokenizer(result,"^");
			return stringTokenizer;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	
	
	private CheckoutTransactionReults parseCheckoutResults(StringTokenizer stringTokenizer) {
		CheckoutTransactionReults checkoutTransaction = new CheckoutTransactionReults();
    	Hashtable<String,String> ht = new Hashtable<String,String>();
    	ht = this.stringTokenizerToHashtable(stringTokenizer);
    	checkoutTransaction.setDueDateString((String)ht.get(SymphonyConstants.SIRSI_DUE_DATE));
    	checkoutTransaction.setStatusCode((String)ht.get(SymphonyConstants.SIRSI_MESSAGE_NUMBER));
    	return checkoutTransaction;  	
	}
	
	
	private CheckInTransactionResults parseCheckInResults(StringTokenizer stringTokenizer) {
		CheckInTransactionResults checkInTransaction = new CheckInTransactionResults();
    	Hashtable<String,String> ht = new Hashtable<String,String>();
    	ht = this.stringTokenizerToHashtable(stringTokenizer);
    	checkInTransaction.setUserId((String)ht.get(SymphonyConstants.SIRSI_USERID));
    	checkInTransaction.setCallNumber((String)ht.get(SymphonyConstants.SIRSI_CALL_NUMBER));
    	checkInTransaction.setAuthor((String)ht.get(SymphonyConstants.SIRSI_AUTHOR));
    	checkInTransaction.setTitle((String)ht.get(SymphonyConstants.SIRSI_TITLE));
    	checkInTransaction.setStatusCode((String)ht.get(SymphonyConstants.SIRSI_MESSAGE_NUMBER));
    	return checkInTransaction;
	}
	
	private AcceptItemTransactionResults parseAcceptItemResults(StringTokenizer stringTokenizer) {
		AcceptItemTransactionResults acceptItemTransactionResults = new AcceptItemTransactionResults();
    	Hashtable<String,String> ht = new Hashtable<String,String>();
    	ht = this.stringTokenizerToHashtable(stringTokenizer);
    	acceptItemTransactionResults.setStatusCode((String)ht.get(SymphonyConstants.SIRSI_MESSAGE_NUMBER));
    	return acceptItemTransactionResults;
	}
	
    private LookupUserTransactionResults parseSirsiUser(StringTokenizer stringTokenizer) {
    	
    	LookupUserTransactionResults sirsiUser = new LookupUserTransactionResults();
    	Hashtable<String,String> ht = new Hashtable<String,String>();
    	while(stringTokenizer.hasMoreTokens()) {
        	String valueString = stringTokenizer.nextToken();
        	try {
        	  String key = valueString.substring(0,2);
        	  String value = valueString.substring(2,valueString.length());
        	  if (key.equals(SymphonyConstants.SIRSI_ENTRY_ID)) {
        	   if (value.equals(SymphonyConstants.SIRSI_CITY_STATE)) {
        		   stringTokenizer.nextToken();
        		   valueString = stringTokenizer.nextToken();
        		   ht.put(SymphonyConstants.SIRSI_CITY_STATE, valueString.substring(2,valueString.length()));
        	   }
        	   if (value.equals(SymphonyConstants.SIRSI_STREET)) {
        		   stringTokenizer.nextToken();
        		   valueString = stringTokenizer.nextToken();
        		   ht.put(SymphonyConstants.SIRSI_STREET, valueString.substring(2,valueString.length()));
        	   }
        	   if (value.equals(SymphonyConstants.SIRSI_ZIP)) {
        		   stringTokenizer.nextToken();
        		   valueString = stringTokenizer.nextToken();
        		   ht.put(SymphonyConstants.SIRSI_ZIP, valueString.substring(2,valueString.length()));
        	   }
        	   if (value.equals(SymphonyConstants.SIRSI_EMAIL)) {
        		   stringTokenizer.nextToken();
        		   valueString = stringTokenizer.nextToken();
        		   ht.put(SymphonyConstants.SIRSI_EMAIL, valueString.substring(2,valueString.length()));
        	   }
        	   if (value.equals(SymphonyConstants.SIRSI_HOME_PHONE)) {
        		   stringTokenizer.nextToken();
        		   valueString = stringTokenizer.nextToken();
        		   ht.put(SymphonyConstants.SIRSI_HOME_PHONE, valueString.substring(2,valueString.length()));
        	   }
        	  } 
        	 ht.put(valueString.substring(0,2), valueString.substring(2,valueString.length()));
        	}
        	catch(Exception e) {
        		//Some parts of the string are not needed...this is okay
        		//System.out.println("FYI: unable to add value to collection of data:" + valueString);
        	}
    	}
    	
    	sirsiUser.setUserid(ht.get(SymphonyConstants.SIRSI_USERID));
    	sirsiUser.setStatusCode(ht.get(SymphonyConstants.SIRSI_MESSAGE_NUMBER));
    	sirsiUser.setEmailAddress((String)ht.get(SymphonyConstants.SIRSI_EMAIL));
    	sirsiUser.setLanguage((String)ht.get(SymphonyConstants.SIRSI_LANGUAGE));
    	sirsiUser.setLastName((String)ht.get(SymphonyConstants.SIRSI_LAST_NAME));
    	sirsiUser.setFirstName((String)ht.get(SymphonyConstants.SIRSI_FIRST_NAME));
    	sirsiUser.setFullName((String)ht.get(SymphonyConstants.SIRSI_FULL_NAME));
    	sirsiUser.setUserid((String)ht.get(SymphonyConstants.SIRSI_USERID));
    	sirsiUser.setPhysAddressStreet((String)ht.get(SymphonyConstants.SIRSI_STREET));
    	sirsiUser.setPhysAddressCityState((String)ht.get(SymphonyConstants.SIRSI_CITY_STATE));
    	sirsiUser.setPhysAddressZip((String)ht.get(SymphonyConstants.SIRSI_ZIP));
    	sirsiUser.setPrivilegeHomeLibrary((String)ht.get((String) symphonyConfig.getProperty("HOME_LIBRARY")));
    	sirsiUser.setPrivilegePatronType(ht.get(SymphonyConstants.SIRSI_PRIV_PATRON_TYPE));
    	sirsiUser.setPrivilegeStatus(ht.get(SymphonyConstants.SIRSI_PRIV_STATUS));
    	sirsiUser.setPrivilegeDepartment(ht.get(SymphonyConstants.SIRSI_PRIV_DEPT));
    	sirsiUser.setTelephone((String)ht.get(SymphonyConstants.SIRSI_HOME_PHONE));
    	return sirsiUser;
    }
    
    private Hashtable<String,String> stringTokenizerToHashtable(StringTokenizer stringTokenizer) {
    	
		Hashtable<String,String> ht = new Hashtable<String,String>();
		while(stringTokenizer.hasMoreTokens()) {
        	String valueString = stringTokenizer.nextToken();
        	try {
        	  String key = valueString.substring(0,2);
        	  String value = valueString.substring(2,valueString.length());
        	  ht.put(key,value);
        	}
        	catch(Exception e) {
        		//Some parts of the string are not needed...this is okay
        		//System.out.println("FYI: unable to add value to collection of data:" + valueString);
        	}
    	}
		return ht;
	}
    
    private String getOnlyNumerics(String str) {
        
        if (str == null) {
            return null;
        }

        StringBuffer strBuff = new StringBuffer();
        char c;
        
        for (int i = 0; i < str.length() ; i++) {
            c = str.charAt(i);
            
            if (Character.isDigit(c)) {
                strBuff.append(c);
            }
        }
        return strBuff.toString();
    }
    
    public static String truncate(String value, int length)  {
  	  if (value != null && value.length() > length)
  	      value = value.substring(0, length);
  	  return value;
  	}
       
}
