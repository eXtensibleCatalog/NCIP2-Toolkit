package org.extensiblecatalog.ncip.v2.koha.util;

import java.net.MalformedURLException;
import java.net.URL;

public class RestApiUrlBuilder extends URLBuilder {

	protected static final String API_VERSION = "v1";

	public RestApiUrlBuilder() {

		this.setBase(LocalConfig.getServerName(), LocalConfig.getIntranetServerPort());

		this.setPath("api", API_VERSION);
	}

	public URL getBiblios(String bibId) throws MalformedURLException {
		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("biblios");

		if (bibId != null)
			return toReturn.appendPath(bibId).toURL();

		return toReturn.toURL();
	}

	public URL getItems(String itemId) throws MalformedURLException {

		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("items");

		if (itemId != null)
			return toReturn.appendPath(itemId).toURL();

		return toReturn.toURL();
	}

	public URL getItemAvailability(String itemId) throws MalformedURLException {

		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("availability", "items");

		if (itemId != null)
			return toReturn.addRequest("itemnumber", itemId).toURL();

		return toReturn.toURL();
	}

	public URL getPatron(String patronId) throws MalformedURLException {

		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("patrons");

		if (patronId != null)
			return toReturn.appendPath(patronId).toURL();

		return toReturn.toURL();
	}

	public URL getHoldsOfPatron(String patronId) throws MalformedURLException {

		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("holds");

		if (patronId != null)
			return toReturn.addRequest("borrowernumber", patronId).toURL();

		return toReturn.toURL();
	}

	public URL getHoldsOfItem(String itemId) throws MalformedURLException {

		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("holds");

		if (itemId != null)
			return toReturn.addRequest("itemnumber", itemId).toURL();

		return toReturn.toURL();
	}

	public URL deleteHold(String reserveId) throws MalformedURLException {

		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("holds");

		return toReturn.appendPath(reserveId).toURL();
	}

	public URL getCheckoutRenewable(String checkoutId) throws KohaException, MalformedURLException {

		if (checkoutId == null)
			throw new KohaException("checkoutId cannot be null while getting checkout renewability");
		
		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("checkouts", checkoutId, "renewability");

		return toReturn.toURL();
	}

	public URL getCheckouts(String patronId) throws MalformedURLException {

		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("checkouts");

		if (patronId != null)
			return toReturn.addRequest("borrowernumber", patronId).toURL();

		return toReturn.toURL();
	}

	public URL putCheckouts(String checkoutId) throws MalformedURLException {

		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("checkouts");

		return toReturn.appendPath(checkoutId).toURL();
	}

	public URL getAccountLines(String patronId) throws MalformedURLException {

		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("accountlines");

		if (patronId != null)
			return toReturn.addRequest("borrowernumber", patronId).toURL();

		return toReturn.toURL();
	}

	public URL getCheckoutsHistoryForPatron(String patronId) throws MalformedURLException, KohaException {

		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("checkouts", "history");

		if (patronId != null)
			return toReturn.addRequest("borrowernumber", patronId).toURL();

		throw new KohaException("patronId cannot be null while getting user history");
	}

	public URL getCheckoutsHistory(String checkoutId) throws MalformedURLException, KohaException {

		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("checkouts", "history");

		if (checkoutId != null)
			return toReturn.appendPath(checkoutId).toURL();

		throw new KohaException("checkoutId cannot be null while getting user history");
	}

	public URL postHolds() throws MalformedURLException {
		return this.getHoldsOfPatron(null);
	}

	public URL getLibraries() throws MalformedURLException {

		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("libraries");

		return toReturn.toURL();
	}

	public URL postAuth() throws MalformedURLException {

		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("auth", "session");

		return toReturn.toURL();
	}
}
