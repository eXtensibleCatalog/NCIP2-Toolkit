/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  */

package org.extensiblecatalog.ncip.v2.voyager.util;

import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.util.Properties;
import java.lang.ExceptionInInitializerError;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.log4j.Logger;

/**
 * Utility class used to get a connection to an LDAP server
 * 
 * @author Eric Osisek
 */
public class LDAPUtils 
{
	/**
	 * A reference to the logger for this class
	 */
	static Logger log = Logger.getLogger(LDAPUtils.class);
	
	private VoyagerConfiguration voyagerConfig;
    {
        try {
            voyagerConfig = (VoyagerConfiguration)ConnectorConfigurationFactory.getConfiguration();
        } catch (ToolkitException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
	
	/**
	 * Creates a connection to the LDAP server based on values defined in the configuration file.
	 * This method logs into the server with a specified username and password
	 * 
	 * @param username The username to log into the LDAP server
	 * @param password The password to log into the LDAP server
	 * @return A connection to the LDAP server defined in the configuration file.
	 * @throws ILSException if the username and password were wrong or we couldn't find the LDAP server
	 */
	public DirContext getLDAPConnection(String username, String password)
	{
		log.debug("Entering getLDAPConnection() for username " + username);		
		try
		{			
			// Set up the environment for creating the initial context
			Properties ldapProperties = getGenericLDAPProperties();

			// Get the username attribute and start location on the LDAP server from the configuration file
			String usernameAttribute = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_EXTERNAL_LDAP_USERNAME_ATTRIBUTE);
			String startLocation = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_EXTERNAL_LDAP_START);

			// Set up the properties to authenticate with the correct username and password
			// The username passed to this function will be something like "jsmith", but we
			// need to authenticate to the correct LDAP location using the provided parameter.
			// For this reason we pull the username attribute at start locations from the
			// configuration file.  The result will be setting the SECURITY_PRINCIPAL (LDAP username)
			// to something like "uid=jsmith, ou=people, dc=rochester, dc=edu"
			ldapProperties.setProperty(Context.SECURITY_AUTHENTICATION, "simple"); // Set this property because we will be authenticating
			ldapProperties.setProperty(Context.SECURITY_PRINCIPAL, usernameAttribute + "=" + username + ", " + startLocation);
			ldapProperties.setProperty(Context.SECURITY_CREDENTIALS, password);

			if(log.isDebugEnabled())
				log.debug("Logging into LDAP server with username " + usernameAttribute + "=" + username + ", " + startLocation);

			// Get the environment properties (props) for creating initial
			// context and specifying LDAP service provider parameters.
			return new InitialDirContext(ldapProperties);
		}
		catch(NamingException e) {
			// If the exception was an error code 49, the username or password was incorrect.
			if(e.getMessage().contains("error code 49")) {
				log.warn("The username or password provided by the user was incorrect.");
				return null;
			}
			else if(e.getMessage().contains("error code 32")) {
				// Test whether or not we can access the LDAP Server without trying to authenticate.
				// If we can the username or password was incorrect.  Otherwise the LDAP Server
				// defined in the configuration file was incorrect.
				if(testLDAPConfiguration()) {
					log.warn("Either the username or password provided by the user was incorrect.");
					return null;
				}
				else {
					log.warn("An error occurred while connecting to the LDAP server defined in the configuration file.", e);
					return null;			
				}
			}
			// The exception was not an error code 49 or 32, so the error could be anything.
			log.warn("An error occurred while connecting to the LDAP server defined in the configuration file.", e);
			return null;
		}
	}
	
	/**
	 * Tests whether or not the LDAP server defined in the NCIP Toolkit configuration file can be accessed.
	 * 
	 * @return true if we could access the LDAP server defined in the NCIP Toolkit configuration file
	 */
	public boolean testLDAPConfiguration()
	{
		if(log.isDebugEnabled())
			log.debug("Entering testLDAPConfiguration()");
		
		InitialDirContext test = null;
		
		try {						
			// Set up the environment for creating the initial context
			Properties ldapProperties = getGenericLDAPProperties();

			// Set this property because we will not be authenticating
			ldapProperties.setProperty(Context.SECURITY_AUTHENTICATION, "none");

			if(log.isDebugEnabled())
				log.debug("Connecting to the LDAP server.");

			// Get the environment properties (props) for creating initial
			// context and specifying LDAP service provider parameters.
			test = new InitialDirContext(ldapProperties);
			
			if(log.isDebugEnabled())
				log.debug("Successfully connected to the LDAP server.");
			
			return (test != null);
		}
		catch(Exception e) {
			// An Exception occurred so we could not access the LDAP server.
			log.warn("An error occurred while connecting to the LDAP server defined in the configuration file.", e);
			
			return false;		
		}
		finally {
			try {
				if(test != null)
					test.close();
			}
			catch(NamingException e) {
				log.warn("An error occurred while closing the connection to the LDAP server.", e);
			}
		}
	}
	
	/**
	 * Sets up the Properties used to create an LDAP connection
	 * 
	 * @return The Properties for an LDAP connection
	 */
	public Properties getGenericLDAPProperties()
	{
		if(log.isDebugEnabled())
			log.debug("Entering getGenericLDAPProperties()");
		
		// Get important values from the configuration file for connecting to the LDAP server.
		String url = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_EXTERNAL_LDAP_LOCATION);
		String  port = (String) voyagerConfig.getProperty(VoyagerConstants.CONFIG_EXTERNAL_LDAP_PORT);
					
		// Set up the environment for creating the initial context
		Properties ldapProperties = new Properties();
		ldapProperties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		ldapProperties.setProperty(Context.PROVIDER_URL, url + ":" + port);

		if(log.isDebugEnabled())
			log.debug("Built properties for LDAP server at " + url + ":" + port);
		
		return ldapProperties;
	}
}
