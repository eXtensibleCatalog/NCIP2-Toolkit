package org.extensiblecatalog.ncip.v2.aleph.AlephAPI.agency;

import org.extensiblecatalog.ncip.v2.aleph.AlephAPI.agency.AlephAgency;

/**
 * Factory class to create a new AlephAgency object
 * 
 * @author Jiří Kozlovský - Moravian Library in Brno (Moravská zemská knihovna v Brně)
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