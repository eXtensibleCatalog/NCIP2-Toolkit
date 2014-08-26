package org.extensiblecatalog.ncip.v2.aleph.restdlf;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.agency.AlephAgency;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.agency.AlephAgencyFactory;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephItem;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the Mediator design pattern
 * in order to abstract interaction between Aleph
 * and external services.
 * 
 * @author Rick Johnson (NDU)
 *
 */

//TODO: After all imported classes are fixed, debug this and delete dead code
public class AlephMediator implements Serializable {
	
	private static final long serialVersionUID = 65003L;
	
	private Map<String,AlephAgency> agencies;
	private Map<String,AlephConstants.Availability> availabilityCircStatusMap;
	private String defaultAgencyId;
	
	/**
	 * Protected constructor to limit construction to the factory
	 */
	protected AlephMediator(){}
	
	/**
	 * @param agencies the agencies to set
	 */
	public void setAgencies(Map<String,AlephAgency> agencies) {
		this.agencies = agencies;
	}

	/**
	 * @return the agencies
	 */
	public Map<String,AlephAgency> getAgencies() {
		return agencies;
	}
	
	/**
	 * Get the Aleph Agency from the map if it exists using agency id as a key
	 * 
	 * @param agencyId
	 * @return
	 */
	public AlephAgency getAlephAgency(String agencyId){
		if (agencies==null) agencies = new HashMap<String,AlephAgency>();
		return agencies.get(agencyId);
	}
	
	public void addAlephAgency(String agencyId, String admLibrary, String bibLibrary, String holdLibrary){
		if (agencies==null) agencies = new HashMap<String,AlephAgency>();
		AlephAgency agency = AlephAgencyFactory.createAlephAgency(agencyId, admLibrary, bibLibrary, holdLibrary);
		agencies.put(agencyId, agency);
	}
	
	public void addAlephAgency(AlephAgency agency){
		if (agencies==null) agencies = new HashMap<String,AlephAgency>();
		if (agency!=null&&agency.getAgencyId()!=null){
			agencies.put(agency.getAgencyId(), agency);
		}
	}
	
	public String getBibLibrary(String agencyId){
		AlephAgency agency = getAlephAgency(agencyId);
		if (agency!=null){
			return agency.getBibLibrary();
		} else {
			return null;
		}
	}
	
	public String getAdmLibrary(String agencyId){
		AlephAgency agency = getAlephAgency(agencyId);
		if (agency!=null){
			return agency.getAdmLibrary();
		} else {
			return null;
		}
	}
	
	public String getHoldingsLibrary(String agencyId){
		AlephAgency agency = getAlephAgency(agencyId);
		if (agency!=null){
			return agency.getHoldingsLibrary();
		} else {
			return null;
		}
	}
	
	/**
	 * @param defaultAgencyId the defaultAgencyId to set
	 */
	public void setDefaultAgencyId(String defaultAgencyId) {
		this.defaultAgencyId = defaultAgencyId;
	}

	/**
	 * @return the defaultAgencyId
	 */
	public String getDefaultAgency() {
		if (defaultAgencyId!=null){
			return defaultAgencyId;
		} else if (defaultAgencyId==null&&getAgencies().size()>0){
			return getAgencies().keySet().iterator().next();
		} else {
			return null;
		}
	}
	
	public void addAvailableCircStatus(String status) {
		if (availabilityCircStatusMap==null){
			availabilityCircStatusMap = new HashMap<String,AlephConstants.Availability>();
		}
		//add as lowercase so all checks can be case-insensitive
		if (status!=null){
			availabilityCircStatusMap.put(status.toLowerCase(), AlephConstants.Availability.AVAILABLE);
		}
	}
	
	public void addPossiblyAvailableCircStatus(String status) {
		if (availabilityCircStatusMap==null){
			availabilityCircStatusMap = new HashMap<String,AlephConstants.Availability>();
		}
		//add as lowercase so all checks can be case-insensitive
		if (status!=null){
			availabilityCircStatusMap.put(status.toLowerCase(), AlephConstants.Availability.POSSIBLY_AVAILABLE);
		}
	}
	
	public void addNotAvailableCircStatus(String status) {
		if (availabilityCircStatusMap==null){
			availabilityCircStatusMap = new HashMap<String,AlephConstants.Availability>();
		}
		//add as lowercase so all checks can be case-insensitive
		if (status!=null){
			availabilityCircStatusMap.put(status.toLowerCase(), AlephConstants.Availability.NOT_AVAILABLE);
		}
	}

	/**
	 * @param availabilityCircStatusMap the availabilityCircStatusMap to set
	 */
	public void setAvailabilityCircStatusMap(
			Map<String,AlephConstants.Availability> availabilityCircStatusMap) {
		this.availabilityCircStatusMap = availabilityCircStatusMap;
	}

	/**
	 * @return the availabilityCircStatusMap
	 */
	public Map<String,AlephConstants.Availability> getAvailabilityCircStatusMap() {
		return availabilityCircStatusMap;
	}
	
	public AlephConstants.Availability getAlephItemAvailability(AlephItem item){
		if (availabilityCircStatusMap==null){
			availabilityCircStatusMap = new HashMap<String,AlephConstants.Availability>();
		}
		AlephConstants.Availability available = AlephConstants.Availability.UNKNOWN;
		if (item!=null){
			if (item.getDueDate()!=null){
				available = AlephConstants.Availability.NOT_AVAILABLE;
			} else if (item.getCirculationStatus()!=null){
				available = this.availabilityCircStatusMap.get(item.getCirculationStatus().toString().toLowerCase());
				if (available==null){
					//default to possibly available if a status is set but unrecognized
					available = AlephConstants.Availability.POSSIBLY_AVAILABLE;
				}
			} else {
				available = AlephConstants.Availability.AVAILABLE;
			}
		}
		return available;
	}
}