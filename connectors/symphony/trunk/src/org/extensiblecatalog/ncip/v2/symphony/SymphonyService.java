/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.symphony;

import java.util.regex.Pattern;

import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.extensiblecatalog.ncip.v2.symphony.SymphonyConnectorException;

public class SymphonyService {
	
	
	  protected void validateItemIdIsPresent(ItemId itemId) throws SymphonyConnectorException {
		    
	    	if (itemId==null || itemId.getItemIdentifierValue()==null) {
	    		SymphonyConnectorException luException = new SymphonyConnectorException("Item id missing");
	    		throw luException;
	    	}
	    		
	    }
	    
	  protected void validateItemIdIsValid(ItemId itemId) throws SymphonyConnectorException{
	    	  final Pattern idPattern = Pattern.compile("^[a-zA-Z0-9\\s\\.\\-_]+$");
	    	  if (!idPattern.matcher( itemId.getItemIdentifierValue() ).matches() || itemId.getItemIdentifierValue().length() > 100) {
	    		  SymphonyConnectorException luException = new SymphonyConnectorException("Item id is invalid");
	      		  throw luException;
	    	  } 
	    }
	 
	  
	  protected void validateUserIdIsPresent(UserId userId) throws SymphonyConnectorException {
		    
	    	if (userId==null || userId.getUserIdentifierValue()==null) {
	    		SymphonyConnectorException exception = new SymphonyConnectorException("User id missing");
	    		throw exception;
	    	}
	    		
	    }
	    
	   protected void validateUserIdIsValid(UserId userId) throws SymphonyConnectorException{
	    	
	    	  final Pattern idPattern = Pattern.compile("^[a-zA-Z0-9\\s\\.\\-_]+$");
	    	  if (!idPattern.matcher( userId.getUserIdentifierValue() ).matches() || userId.getUserIdentifierValue().length() > 100) {
	    		  SymphonyConnectorException exception = new SymphonyConnectorException("User id is invalid");
	      		  throw exception;
	    	  } 
	    }
	    

}
