package org.extensiblecatalog.ncip.v2.aleph.restdlf;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.user.AlephUser;
import org.xml.sax.helpers.DefaultHandler;

public class AlephUserHandler extends DefaultHandler {
	private AlephUser user;
	private boolean requireAtLeastOneService = true;
	private boolean nameInformationDesired;
	private boolean userIdDesired;
	private boolean userAddressInformationDesired;
	private boolean userFiscalAccountDesired;

	public AlephUserHandler(boolean nameInformationDesired, boolean userIdDesired, boolean userAddressInformationDesired, boolean userFiscalAccountDesired) throws AlephException {
		if (nameInformationDesired || userIdDesired || userAddressInformationDesired || userFiscalAccountDesired) {
			this.nameInformationDesired = nameInformationDesired;
			this.userIdDesired = userIdDesired;
			this.userAddressInformationDesired = userAddressInformationDesired;
			this.userFiscalAccountDesired = userFiscalAccountDesired;
		} else if (requireAtLeastOneService ) {
			throw new AlephException("No service desired. Please supply at least one service you wish to use.");
		}
	}
}
