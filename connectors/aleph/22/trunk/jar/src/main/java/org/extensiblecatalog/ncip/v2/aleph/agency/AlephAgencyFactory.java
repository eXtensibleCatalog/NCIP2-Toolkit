package org.extensiblecatalog.ncip.v2.aleph.agency;

/**
 * Factory class to create a new AlephAgency object
 * 
 * @author Rick Johnson (NDU)
 *
 */
public class AlephAgencyFactory {
	
	public static AlephAgency createAlephAgency(String agencyId, String admLibrary, String bibLibrary, String holdLibrary){
		AlephAgency agency = new AlephAgency();
		agency.setAgencyId(agencyId);
		agency.setAdmLibrary(admLibrary);
		agency.setBibLibrary(bibLibrary);
		agency.setHoldingsLibrary(holdLibrary);
		return agency;
	}
}
