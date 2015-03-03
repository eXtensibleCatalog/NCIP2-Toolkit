package org.extensiblecatalog.ncip.v2.koha.util.SAXHandlers;

import java.util.ArrayList;
import java.util.List;

import org.extensiblecatalog.ncip.v2.koha.user.KohaUser;
import org.extensiblecatalog.ncip.v2.koha.util.KohaConstants;
import org.extensiblecatalog.ncip.v2.koha.util.KohaException;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.BlockOrTrap;
import org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Jiří Kozlovský (KOH)
 */
public class KohaLookupUserHandler extends DefaultHandler {

	private KohaUser user;
	private String address;
	private AgencyId agencyId;
	private String city;
	private String fineDate;
	private String fineSum;
	private String fineDescription;
	private List<BlockOrTrap> blockOrTraps;

	private boolean nameInformationDesired;
	private boolean userIdDesired;
	private boolean userAddressInformationDesired;

	/**
	 * Initializes new KohaUserHandler instance with desired services in the following order:
	 * 
	 * @param nameInformationDesired
	 * @param userIdDesired
	 * @param userAddressInformationDesired
	 * @param userFiscalAccountDesired
	 * @param userPrivilegeDesired
	 * @throws KohaException
	 */
	public KohaLookupUserHandler(LookupUserInitiationData initData) throws KohaException {

		userAddressInformationDesired = initData.getUserAddressInformationDesired();
		nameInformationDesired = initData.getNameInformationDesired();
		userIdDesired = initData.getUserIdDesired();

		user = new KohaUser();

		blockOrTraps = new ArrayList<BlockOrTrap>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {}

	public KohaUser getKohaUser() {
		return user;
	}
}
