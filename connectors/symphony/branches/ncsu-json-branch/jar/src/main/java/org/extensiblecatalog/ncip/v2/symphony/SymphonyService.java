/**
 * Copyright (c) 2012 North Carolina State University
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.extensiblecatalog.ncip.v2.symphony;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.extensiblecatalog.ncip.v2.symphony.SymphonyConnectorException;

public class SymphonyService {

    private Logger _logger;


    protected SymphonyJSONRemoteServiceManager getServiceManager(RemoteServiceManager serviceManager) {
        try {
            if ( serviceManager == null ) {
                throw new Error("Please configure a serviceManager");
            }
            return (SymphonyJSONRemoteServiceManager)serviceManager;
        } catch( ClassCastException ccx ) {
            throw new Error(
                    String.format("Application badly configured: serviceManager for Symphony connector must be subclass of %s (type: %s)", SymphonyJSONRemoteServiceManager.class.getName(), serviceManager.getClass().getName())
            );
        }
    }
	
	
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

    protected Logger getLogger() {
        synchronized(this) {
            if ( _logger == null ) {
                _logger = Logger.getLogger(this.getClass());
            }
        }
        return _logger;
    }
	    

}
