package org.extensiblecatalog.ncip.v2.koha.agency;

/**
 * Factory class to create a new KohaAgency object
 * 
 * @author Rick Johnson (NDU)
 *
 */
public class KohaAgencyFactory {
	
	public static KohaAgency createKohaAgency(String agencyId, String admLibrary, String bibLibrary, String holdLibrary){
		KohaAgency agency = new KohaAgency();
		agency.setAgencyId(agencyId);
		agency.setAdmLibrary(admLibrary);
		agency.setBibLibrary(bibLibrary);
		agency.setHoldingsLibrary(holdLibrary);
		return agency;
	}
}
