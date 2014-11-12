package org.extensiblecatalog.ncip.v2.aleph.util;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.common.DefaultConnectorConfiguration;
import org.extensiblecatalog.ncip.v2.aleph.AlephLookupItemService;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.AlephMediator;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephItem;

/**
 * AlephRemoteServiceManager just extends AlephMediator to interface with Aleph X-Services for fulfilling NCIP requests
 *
 * @author Rick Johnson
 * @organization University of Notre Dame
 */
public class AlephRemoteServiceManager extends RestDlfConnector implements RemoteServiceManager {

	static Logger log = Logger.getLogger(AlephLookupItemService.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 7115174311329481562L;

	private AlephConfiguration alephConfig = null;
	{
		try {
			DefaultConnectorConfiguration config = (DefaultConnectorConfiguration) new ConnectorConfigurationFactory(new Properties()).getConfiguration();
			alephConfig = new AlephConfiguration(config);
		} catch (ToolkitException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public AlephRemoteServiceManager() throws ServiceException {
		initializeAgencyMap();
		initializeAvailabilityMaps();
	}

	public AlephRemoteServiceManager(Properties properties) throws ServiceException {
		this();
		log.info("AlephRemoteServiceManager constructor called");
	}

	/**
	 * Initialize the internal agency map to values from NCIPToolkit_config.xml
	 * 
	 * It expects blocks of agency data that is indexed for each agency Example:
	 * 
	 * AlephILSAgency1 AlephAdmLibrary1 AlephBibLibrary1 AlephHoldLibrary1
	 * 
	 */
	protected void initializeAgencyMap() {

		String[] agencyIds = alephConfig.getArray(AlephConstants.CONFIG_ALEPH_AGENCY);
		String[] admLibraries = alephConfig.getArray(AlephConstants.CONFIG_ALEPH_ADM_LIBRARY);
		String[] bibLibraries = alephConfig.getArray(AlephConstants.CONFIG_ALEPH_BIB_LIBRARY);
		String[] holdLibraries = alephConfig.getArray(AlephConstants.CONFIG_ALEPH_HOLD_LIBRARY);

		// check everything same length
		if (agencyIds == null || admLibraries == null || bibLibraries == null || holdLibraries == null || agencyIds.length != admLibraries.length
				|| agencyIds.length != bibLibraries.length || agencyIds.length != holdLibraries.length) {
			return;
		}

		for (int i = 0; i < agencyIds.length; i++) {
			// ignore exception thrown in this case only
			addAlephAgency(agencyIds[i], admLibraries[i], bibLibraries[i], holdLibraries[i]);
			setDefaultAgencyId(agencyIds[i]);
		}
	}

	/**
	 * Initialize the internal availability maps of circ status to availability from NCIPToolkit_config.xml
	 */
	protected void initializeAvailabilityMaps() {
		String[] availableCircStatus = alephConfig.getArray(AlephConstants.CONFIG_ALEPH_CIRC_STATUS_AVAILABLE);
		String[] possiblyAvailableCircStatus = alephConfig.getArray(AlephConstants.CONFIG_ALEPH_CIRC_STATUS_POSSIBLY_AVAILABLE);
		String[] notAvailableCircStatus = alephConfig.getArray(AlephConstants.CONFIG_ALEPH_CIRC_STATUS_NOT_AVAILABLE);

		if (availableCircStatus != null) {
			for (int i = 0; i < availableCircStatus.length; i++) {
				// ignore exception thrown in this case only
				addAvailableCircStatus(availableCircStatus[i]);
			}
		}
		if (possiblyAvailableCircStatus != null) {
			for (int i = 0; i < possiblyAvailableCircStatus.length; i++) {
				// ignore exception thrown in this case only
				addPossiblyAvailableCircStatus(possiblyAvailableCircStatus[i]);
			}
		}
		if (notAvailableCircStatus != null) {
			for (int i = 0; i < notAvailableCircStatus.length; i++) {
				// ignore exception thrown in this case only
				addNotAvailableCircStatus(notAvailableCircStatus[i]);
			}
		}
	}

	/**
	 * Get the X Server Name that this AlephInterface will use in call to Aleph X-Service Requests
	 * 
	 * @return X Server Name
	 */
	public String getXServerName() {
		return alephConfig.getProperty(AlephConstants.CONFIG_ALEPH_X_SERVER_NAME);
	}

	/**
	 * Get the X Server Port that this AlephInterface will use in call to Aleph X-Service Requests
	 * 
	 * @return X Server Port
	 */
	public String getXServerPort() {
		return alephConfig.getProperty(AlephConstants.CONFIG_ALEPH_X_SERVER_PORT);
	}

	public String getCurrencyCode() {
		return alephConfig.getProperty(AlephConstants.CONFIG_ALEPH_CURRENCY_CODE);
	}
}
