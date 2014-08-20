package org.extensiblecatalog.ncip.v2.aleph.restdlf;

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
	private static final long serialVersionUID = 65010L;

	/**
	 * Get an AlephMediator that is configured to talk to Aleph API
	 * with the server name and port supplied.
	 * 
	 * @param AlephName
	 * @param AlephPort
	 * @return the AlephMediator
	 */
	public static AlephMediator getAlephMediator(String AlephName, String AlephPort){
		AlephMediator alephM =  new AlephMediator();
		alephM.setAlephName(AlephName);
		alephM.setAlephPort(AlephPort);
		return alephM;
	}
}
