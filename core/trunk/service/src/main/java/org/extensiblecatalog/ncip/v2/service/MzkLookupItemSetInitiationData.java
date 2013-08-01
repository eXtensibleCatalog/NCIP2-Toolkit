package org.extensiblecatalog.ncip.v2.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class MzkLookupItemSetInitiationData extends LookupItemSetInitiationData {

	//xml tags used in request
	public static final String MZK_TAG_START_VOLUME				= "<ns1:volume>";
	public static final String MZK_TAG_END_VOLUME				= "</ns1:volume>";
	public static final String MZK_TAG_START_PUBLICATION_YEAR	= "<ns1:PublicationYear>";
	public static final String MZK_TAG_END_PUBLICATION_YEAR 	= "</ns1:PublicationYear>";
	
    protected int volume = -1;
    protected int publicationYear = -1;
    
    /**
     * Copy constructor makes shallow copy of parent, class is intended to be used as replacement of parent class ONLY
     * @param init
     */
    public MzkLookupItemSetInitiationData (LookupItemSetInitiationData init) {
    	this.setBibliographicDescriptionDesired( init.getBibliographicDescriptionDesired());
    	this.setBibliographicIds(init.getBibliographicIds());
    	this.setCirculationStatusDesired(init.getCirculationStatusDesired());
    	this.setCurrentBorrowerDesired(init.getCurrentBorrowerDesired());
    	this.setCurrentRequestersDesired(init.getCurrentRequestersDesired());
    	this.setHoldingsSetIds(init.getHoldingsSetIds());
    	this.setHoldQueueLengthDesired(init.getHoldQueueLengthDesired());
    	this.setInitiationHeader(init.getInitiationHeader());
    	this.setItemDescriptionDesired(init.getItemDescriptionDesired());
    	this.setItemIds(init.getItemIds());
    	this.setItemUseRestrictionTypeDesired(init.getItemUseRestrictionTypeDesired());
    	this.setLocationDesired(init.getLocationDesired());
    	this.setMaximumItemsCount(init.getMaximumItemsCount());
    	this.setNextItemToken(init.getNextItemToken());
    	this.setPhysicalConditionDesired(init.getPhysicalConditionDesired());
    	this.setRelyingPartyId(init.getRelyingPartyId());
    	this.setSecurityMarkerDesired(init.getSecurityMarkerDesired());
    	this.setSensitizationFlagDesired(init.getSensitizationFlagDesired());
    	this.setVersion(init.getVersion());
    }
    
    public void setYear ( int year) {
    	this.volume = year;
    }
    
    /**
     * converts String value into Integer
     * @param volume
     */
    public void setVolume ( final String volume ) {
    	try {
    		this.volume = Integer.valueOf(volume).intValue();
    	} catch (NumberFormatException e) {
    		this.volume = -1;
    	}
    }
    
    public void setPublicationYear ( int year) {
    	this.publicationYear = year;
    }
    
    /**
     * converts String value into Integer 
     * @param year
     */
    public void setPublicationYear ( final String year) {
    	try {
    		this.publicationYear = Integer.valueOf(year).intValue();
    	} catch (NumberFormatException e) {
    		this.publicationYear = -1;
    	}
    }
    
    
    /**
     * helping method for reading input stream
     */
    public static String slurp(final InputStream is, final int bufferSize)
    {
      final char[] buffer = new char[bufferSize];
      final StringBuilder out = new StringBuilder();
      try {
        final Reader in = new InputStreamReader(is, "UTF-8");
        try {
          for (;;) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
              break;
            out.append(buffer, 0, rsz);
          }
        }
        finally {
          in.close();
        }
      }
      catch (UnsupportedEncodingException ex) {
        /* ... */
      }
      catch (IOException ex) {
        /* ... */
      }
      return out.toString();
    }
}
