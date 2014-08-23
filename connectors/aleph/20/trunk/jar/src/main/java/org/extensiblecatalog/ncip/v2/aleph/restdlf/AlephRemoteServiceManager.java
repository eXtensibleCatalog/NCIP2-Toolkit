package org.extensiblecatalog.ncip.v2.aleph.restdlf;

import java.util.Properties;

import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.common.DefaultConnectorConfiguration;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephMediator;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephConfiguration;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;

/**
 * AlephRemoteServiceManager just extends AlephMediator to interface with Aleph X-Services for fulfilling NCIP requests
 *
 * @author Rick Johnson
 * @organization University of Notre Dame
 */
public class AlephRemoteServiceManager extends AlephConnector implements RemoteServiceManager {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 65011L;


	public AlephRemoteServiceManager() throws ServiceException {
    	initializeAgencyMap();
    	initializeAvailabilityMaps();
    }
	
	public AlephRemoteServiceManager(Properties properties) throws ServiceException {
		this();
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
    protected void initializeAgencyMap() {    	
		
    	String[] agencyIds = alephConfig.getArray(AlephConstants.CONFIG_ALEPH_AGENCY);
    	String[] admLibraries = alephConfig.getArray(AlephConstants.CONFIG_ALEPH_ADM_LIBRARY);
    	String[] bibLibraries = alephConfig.getArray(AlephConstants.CONFIG_ALEPH_BIB_LIBRARY);
    	String[] holdLibraries = alephConfig.getArray(AlephConstants.CONFIG_ALEPH_HOLD_LIBRARY);
		
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
    	String[] availableCircStatus = alephConfig.getArray(AlephConstants.CONFIG_ALEPH_CIRC_STATUS_AVAILABLE);
    	String[] possiblyAvailableCircStatus = alephConfig.getArray(AlephConstants.CONFIG_ALEPH_CIRC_STATUS_POSSIBLY_AVAILABLE);
    	String[] notAvailableCircStatus = alephConfig.getArray(AlephConstants.CONFIG_ALEPH_CIRC_STATUS_NOT_AVAILABLE);
		
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
    
    public String getCurrencyCode(){
    	return alephConfig.getProperty(AlephConstants.CONFIG_ALEPH_CURRENCY_CODE);
    }
}
