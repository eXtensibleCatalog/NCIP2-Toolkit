package org.extensiblecatalog.ncip.v2.aleph;

import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.common.NCIPConfiguration;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.AlephMediator;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;

/**
 * AlephRemoteServiceManager just extends AlephMediator to interface with Aleph X-Services for fulfilling NCIP requests
 *
 * @author Rick Johnson
 * @organization University of Notre Dame
 */
public class AlephRemoteServiceManager extends AlephMediator implements RemoteServiceManager{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7115174311329481562L;

	public AlephRemoteServiceManager(){
    	initializeAgencyMap();
    	initializeAvailabilityMaps();
    }
	
    /**
     * Initialize the internal agency map to values from NCIPToolkit_config.xml
     * 
     * It expects blocks of agency data that is indexed for each agency
     * Example:
     * 
     * AlephILSAgency1
     * AlephAdmLibrary1
     * AlephBibLibrary1
     * AlephHoldLibrary1
     * 
     */
    protected void initializeAgencyMap(){
		
    	String[] agencyIds = NCIPConfiguration.getArray(AlephConstants.CONFIG_ALEPH_AGENCY);
    	String[] admLibraries = NCIPConfiguration.getArray(AlephConstants.CONFIG_ALEPH_ADM_LIBRARY);
    	String[] bibLibraries = NCIPConfiguration.getArray(AlephConstants.CONFIG_ALEPH_BIB_LIBRARY);
    	String[] holdLibraries = NCIPConfiguration.getArray(AlephConstants.CONFIG_ALEPH_HOLD_LIBRARY);
		
    	//check everything same length
    	if (agencyIds==null||admLibraries==null||bibLibraries==null||holdLibraries==null||
    		agencyIds.length!=admLibraries.length||agencyIds.length!=bibLibraries.length||
    		agencyIds.length!=holdLibraries.length){
    		return;
    	}
		
    	for (int i=0; i<agencyIds.length; i++){
    		//ignore exception thrown in this case only
    		addAlephAgency(agencyIds[i], admLibraries[i], bibLibraries[i], holdLibraries[i]);
    	}
    }
	
    /**
     * Initialize the internal availability maps of circ status to availability from NCIPToolkit_config.xml
     */
    protected void initializeAvailabilityMaps(){
    	String[] availableCircStatus = NCIPConfiguration.getArray(AlephConstants.CONFIG_ALEPH_CIRC_STATUS_AVAILABLE);
    	String[] possiblyAvailableCircStatus = NCIPConfiguration.getArray(AlephConstants.CONFIG_ALEPH_CIRC_STATUS_POSSIBLY_AVAILABLE);
    	String[] notAvailableCircStatus = NCIPConfiguration.getArray(AlephConstants.CONFIG_ALEPH_CIRC_STATUS_NOT_AVAILABLE);
		
    	if (availableCircStatus!=null){
    		for (int i=0; i<availableCircStatus.length; i++){
    			//ignore exception thrown in this case only
    			addAvailableCircStatus(availableCircStatus[i]);
    		}
    	}
    	if (possiblyAvailableCircStatus!=null){
    		for (int i=0; i<possiblyAvailableCircStatus.length; i++){
    			//ignore exception thrown in this case only
    			addPossiblyAvailableCircStatus(possiblyAvailableCircStatus[i]);
    		}
    	}
    	if (notAvailableCircStatus!=null){
    		for (int i=0; i<notAvailableCircStatus.length; i++){
    			//ignore exception thrown in this case only
    			addNotAvailableCircStatus(notAvailableCircStatus[i]);
    		}
    	}
    }

    /**
     * Get the X Server Name that this AlephInterface will use in call to Aleph X-Service Requests
     * 
     * @return X Server Name
     */
    public String getXServerName(){
    	return NCIPConfiguration.getProperty(AlephConstants.CONFIG_ALEPH_X_SERVER_NAME);
    }
    
    /**
     * Get the X Server Port that this AlephInterface will use in call to Aleph X-Service Requests
     * 
     * @return X Server Port
     */
    public String getXServerPort(){
    	return NCIPConfiguration.getProperty(AlephConstants.CONFIG_ALEPH_X_SERVER_PORT);
    }
    
    public String getCurrencyCode(){
    	return NCIPConfiguration.getProperty(AlephConstants.CONFIG_ALEPH_CURRENCY_CODE);
    }
}
