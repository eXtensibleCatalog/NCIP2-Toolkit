package org.extensiblecatalog.ncip.v2.koha.util;

import java.net.MalformedURLException;
import java.net.URL;

public class RestApiUrlBuilder extends URLBuilder {
	
	protected static final String API_VERSION = "v1";
	
	public RestApiUrlBuilder() {
		
		this.setBase(LocalConfig.getServerName(), LocalConfig.getIntranetServerPort());
		
		this.setPath("api", API_VERSION);
	}
	
	public URL getPatron(String patronId) throws MalformedURLException {
		
		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("patrons");
		
		if (patronId != null)
			return toReturn.appendPath(patronId).toURL();
			
		return toReturn.toURL();
	}

	public URL getHolds(String patronId) throws MalformedURLException {
		
		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("holds");
		
		if (patronId != null)
			return toReturn.addRequest("borrowernumber", patronId).toURL();
			
		return toReturn.toURL();
	}

	public URL getCheckouts(String patronId) throws MalformedURLException {
		
		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("checkouts");
		
		if (patronId != null)
			return toReturn.addRequest("borrowernumber", patronId).toURL();
			
		return toReturn.toURL();
	}
	
	public URL getAccountLines(String patronId) throws MalformedURLException {
		
		RestApiUrlBuilder toReturn = (RestApiUrlBuilder) this.appendPath("accountlines");
		
		if (patronId != null)
			return toReturn.addRequest("borrowernumber", patronId).toURL();
			
		return toReturn.toURL();
	}
}
