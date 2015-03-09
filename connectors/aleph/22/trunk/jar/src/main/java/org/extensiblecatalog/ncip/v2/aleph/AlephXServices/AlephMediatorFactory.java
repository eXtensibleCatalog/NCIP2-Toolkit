package org.extensiblecatalog.ncip.v2.aleph.AlephXServices;

import java.io.Serializable;

/**
 * Used to create a new AlephMediator object
 * that is used to interact with Aleph.
 * 
 * @author Rick Johnson
 *
 */
public class AlephMediatorFactory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 719749280875892529L;

	/**
	 * Get an AlephMediator that is configured to talk to an Aleph x server
	 * with the server name and port supplied.
	 * 
	 * @param xServerName
	 * @param xServerPort
	 * @return the AlephMediator
	 */
	public static AlephMediator getAlephMediator(String xServerName, String xServerPort){
		AlephMediator alephM =  new AlephMediator();
		alephM.setXServerName(xServerName);
		alephM.setXServerPort(xServerPort);
		return alephM;
	}
}
