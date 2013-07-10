package org.extensiblecatalog.ncip.v2.voyager;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.NCIPConfiguration;
import org.extensiblecatalog.ncip.v2.service.AccountDetails;
import org.extensiblecatalog.ncip.v2.service.Amount;
import org.extensiblecatalog.ncip.v2.service.AuthenticationInput;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemId;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemIdentifierCode;
import org.extensiblecatalog.ncip.v2.service.ElectronicAddress;
import org.extensiblecatalog.ncip.v2.service.ElectronicResource;
import org.extensiblecatalog.ncip.v2.service.FiscalTransactionInformation;
import org.extensiblecatalog.ncip.v2.service.HoldingsInformation;
import org.extensiblecatalog.ncip.v2.service.ItemDescription;
import org.extensiblecatalog.ncip.v2.service.ItemDetails;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.LoanedItem;
import org.extensiblecatalog.ncip.v2.service.LoanedItemsCount;
import org.extensiblecatalog.ncip.v2.service.Location;
import org.extensiblecatalog.ncip.v2.service.LocationName;
import org.extensiblecatalog.ncip.v2.service.LocationNameInstance;
import org.extensiblecatalog.ncip.v2.service.NameInformation;
import org.extensiblecatalog.ncip.v2.service.PersonalNameInformation;
import org.extensiblecatalog.ncip.v2.service.PhysicalAddress;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.RequestedItem;
import org.extensiblecatalog.ncip.v2.service.SchemeValuePair;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.StructuredPersonalUserName;
import org.extensiblecatalog.ncip.v2.service.UnstructuredAddress;
import org.extensiblecatalog.ncip.v2.service.UserAddressInformation;
import org.extensiblecatalog.ncip.v2.service.Version1BibliographicItemIdentifierCode;
import org.extensiblecatalog.ncip.v2.service.Version1FiscalActionType;
import org.extensiblecatalog.ncip.v2.service.Version1RequestType;
import org.extensiblecatalog.ncip.v2.service.XcCirculationStatus;
import org.extensiblecatalog.ncip.v2.service.XcRequestType;
import org.extensiblecatalog.ncip.v2.voyager.util.ILSException;
import org.extensiblecatalog.ncip.v2.voyager.util.LDAPUtils;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerAuthentication;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConstants;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerUtil;


/**
 * VoyagerRemoteServiceManager is the implementation of the back-end service
 * 
 * @author SharmilaR
 *
 */
public class VoyagerRemoteServiceManager implements  RemoteServiceManager { 
	
	/** Logger */
    static Logger log = Logger.getLogger(VoyagerRemoteServiceManager.class);
	
	/** Database read connection */
	private static Connection conn;

	/** Database write connection */
	private static Connection writeConn;
	
    /** The user ID of the user who is currently authenticated    */
    private String authenticatedUserId = null;

    private VoyagerAuthentication voyagerAuthentication = null;
    

    /**
     * Constructor
     */
    public VoyagerRemoteServiceManager() throws ILSException {
    	openReadDbConnection();
    	
    	if (!NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_DB_WRITE_AUTH_USERNAME).equalsIgnoreCase("")){
			openWriteDbConnection();
			voyagerAuthentication = new VoyagerAuthentication();	    
			voyagerAuthentication.setupPidAndSeq(NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_BASE_URL) + "?DB=local&PAGE=First");
    	} else
    		log.info("Oracle DB Write username was not configured");
	}

	/**
	 * Sets up and returns a connection to the voyager database whose location and
	 * driver are defined in the configuration file.
	 */
	private void openReadDbConnection() throws ILSException
	{
		if(log.isDebugEnabled())
			log.debug("Entering openReadDbConnection()");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
	        String url = NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_DB_URL);
	        String username = NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_DB_READ_ONLY_USERNAME);
	        String password = NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_DB_READ_ONLY_PASSWORD);
	
			conn = DriverManager.getConnection(url, username, password);
			
		    conn.setAutoCommit(false);
		    
		    log.debug("Voyager DB read connection = " + conn);
		} catch (ClassNotFoundException ce) {
			log.error("An error occurred while connecting to database.", ce);
			throw new ILSException(ce);
		} catch (SQLException se) {
			log.error("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", se);
			throw new ILSException(se);
		}
	}

	
	/**
	 * Sets up and returns a connection to the voyager database whose location and
	 * driver are defined in the configuration file. The connection will have read/write
	 * access to only those tables required for authentication
	 */
	private void openWriteDbConnection() throws ILSException
	{
		if(log.isDebugEnabled())
			log.debug("Entering openWriteDbConnection()");
		
	    try 
	    {
	    	// Load the JDBC driver for Oracle since all Voyager installs use a Voyager database
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	    
	        // Get the URL, username and password to log into the database from the configuration file
	        String url = NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_DB_URL);
	        String username = NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_DB_WRITE_AUTH_USERNAME);
	        String password = NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_DB_WRITE_AUTH_PASSWORD);
	        
	        if(log.isDebugEnabled())
				log.debug("Building a connection to the database at " + url + " with the username " + username);
	        
	        // Create a connection to the database
	        writeConn = DriverManager.getConnection(url, username, password);
	        log.debug("Voyager DB write connection = " + writeConn);
	    }
	    catch (ClassNotFoundException e) // Could not find the database driver
	    {
	    	log.error("An error occurred while connecting to database.", e);	
	    	throw new ILSException(e);
	    }
	    catch (SQLException e) // Could not connect to the database
	    {
			log.error("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
			throw new ILSException(e);
	    }
	    catch(UnsatisfiedLinkError e) // Something was wrong with the URL
	    {
			log.error("Unable to connect to the database specified in the NCIP Toolkit configuration file.  " + 
	                               "The URL is incorrect.", e);
			throw new ILSException(e);
	    }

	}
	
	/**
	 * Authenticate user
	 * 
	 * @param inputs
	 * @return
	 * @throws ILSException
	 * @throws ILSException
	 */
	public String authenticateUser(List<AuthenticationInput> inputs) throws ILSException {
		 
		 String username = null;
		 String password = null;
		 String ldapUsername = null;
		 String ldapPassword = null;
		 String authenticatedUserId = null;
		 
		 for (AuthenticationInput a : inputs) {
			if (a.getAuthenticationInputType().getValue().equalsIgnoreCase("Username")) {
				username = a.getAuthenticationInputData();
			} else if (a.getAuthenticationInputType().getValue().equalsIgnoreCase("Password")) {
				password = a.getAuthenticationInputData();
			} else if (a.getAuthenticationInputType().getValue().equalsIgnoreCase("LDAPUsername")) {
				ldapUsername = a.getAuthenticationInputData();
			} else if (a.getAuthenticationInputType().getValue().equalsIgnoreCase("LDAPPassword")) {
				ldapPassword = a.getAuthenticationInputData();
			}
		 }
		 
		 if (username != null && password != null) {
			 if (!NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_DB_WRITE_AUTH_USERNAME).equalsIgnoreCase("")) 
				 authenticatedUserId = authenticateUser(username, password);
			 else
				 authenticatedUserId = authenticateReadOnlyUser(username, password);
		 }
		 
		 if (ldapUsername != null && ldapPassword != null) {
			 authenticatedUserId = authenticateLDAPUser(ldapUsername, ldapPassword);
		 }
		 return authenticatedUserId;
	 }

	
	/**
	 *  Authenticates User with Read Only Oracle acct before processing the request
	 *  
	 * @param username
	 * @param password
	 * @return authenticatedUserId
	 * @throws ILSException
	 */
    public String authenticateReadOnlyUser(String username, String password) throws ILSException {
    	if (log.isDebugEnabled())
    	    log.debug("Entering authenticateUser for user: " + username + ".");

    	// If this user is already authenticated then return.
    	if (authenticatedUserId != null && authenticatedUserId.equalsIgnoreCase(username)) {
    		log.debug("User already authenticated for UserId =" + authenticatedUserId);
    		return authenticatedUserId;
    	}
 
    	// The institution ID returned by the SQL query
	    String institutionId = getInstitutionIdFromAuthData(
		    username, password.toLowerCase());

	    if (institutionId != null) {
    		if (log.isDebugEnabled())
    		    log.debug("Found a row for institution ID " + institutionId
    			    + " in the voyager database");

    		return institutionId;
	    } else { // There were no rows in the database, try again assuming the username is a barcode and not a student ID
    		institutionId = getInstitutionIdFromBarcodeAuthData(
    			username, password.toLowerCase());

    		// If any results were returned
    		if (institutionId != null) {
    		    if (log.isDebugEnabled())
    			log.debug("Found a row for institution ID "
    				+ institutionId + " in the voyager database");
	
    		    return institutionId;
    		} else { // There were no rows in the database, try again assuming the username is a barcode and not a student ID
    		    log.info("The username or password provided by the user was incorrect.");

    		    throw new ILSException(
    			    "The username or password was incorrect.");
    		}
	    }
    }		
	
	/**
	 *  Authenticates User before processing the request
	 *  
	 * @param username
	 * @param password
	 * @param agency
	 * @return
	 * @throws ILSException
	 * @throws ILSException
	 */
    public String authenticateUser(String username, String password) throws ILSException {
    	if (log.isDebugEnabled())
    	    log.debug("Entering authenticateUser for user: " + username + ".");

    	// If this user is already authenticated then return.
    	if (authenticatedUserId != null && authenticatedUserId.equalsIgnoreCase(username)) {
    		log.debug("User already authenticated for UserId =" + authenticatedUserId);
    		return authenticatedUserId;
    	}
    	// If someone's already logged in, get a new PID and SEQ and reset the
    	// authenticatedUserId. This will effectively log out whomever was
    	// previously authenticated.
    	if (authenticatedUserId != null && authenticatedUserId.length() > 0) {
    	    if (log.isDebugEnabled())
    		log
    			.debug("Removing authentication data for previously authenticated user with ID "
    				+ authenticatedUserId);

    	    authenticatedUserId = null;
    	    voyagerAuthentication.setupPidAndSeq(NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_BASE_URL) + "?DB=local&PAGE=First");
    	}

    	// Check that the PID and SEQ are valid
    	if (!voyagerAuthentication.validPidAndSeq()) {
    	    if (log.isDebugEnabled())
    		log.debug("The PID or SEQ was not set up correctly");

    	    // Since the PID and SEQ were setup during initialization, the
    	    // initializationError string
    	    // contains the correct human readable error message explaining what
    	    // went wrong.
    	    throw new ILSException("User authentication failed.");
    	}

    	try {
    	    // The institution ID returned by the SQL query
    	    String institutionId = getInstitutionIdFromAuthData(
    		    username, password.toLowerCase());

    	    if (institutionId != null) {
	    		if (log.isDebugEnabled())
	    		    log.debug("Found a row for institution ID " + institutionId
	    			    + " in the voyager database");
	
	    		// Add a row to the voyager database associating the PID with
	    		// the institution ID
	    		authenticatedUserId = insertPidAndPatronkey(
	    			institutionId, voyagerAuthentication.getPid());
	
	    		// Send the link confirming that the user has been authenticated
	    		voyagerAuthentication.sendAuthenticationUrl();
	
	    		// Setup the heartbeat Thread to maintain authentication for the
	    		// newly authenticated user
	    		voyagerAuthentication.setupHeartbeatThread(institutionId);
	
	    		return institutionId;
    	    } else { // There were no rows in the database, try again assuming the username is a barcode and not a student ID
	    		institutionId = getInstitutionIdFromBarcodeAuthData(
	    			username, password.toLowerCase());
	
	    		// If any results were returned
	    		if (institutionId != null) {
	    		    if (log.isDebugEnabled())
	    			log.debug("Found a row for institution ID "
	    				+ institutionId + " in the voyager database");
	
	    		    // Add a row to the voyager database associating the PID
	    		    // with the institution ID
	    		    authenticatedUserId = insertPidAndPatronkey(
	    			    institutionId, voyagerAuthentication.getPid());
	
	    		    // Send the link confirming that the user has been
	    		    // authenticated
	    		    voyagerAuthentication.sendAuthenticationUrl();
	
	    		    // Setup the heartbeat Thread to maintain authentication for
	    		    // the newly authenticated user
	    		    voyagerAuthentication.setupHeartbeatThread(institutionId);
	
	    		    return institutionId;
	    		} else { // There were no rows in the database, try again assuming the username is a barcode and not a student ID
	    		    log.info("The username or password provided by the user was incorrect.");
	
	    		    throw new ILSException(
	    			    "The username or password was incorrect.");
	    		}
    	    }
    	} catch (IOException e) {
    	    log.error("An error occurred while confirming authentication on Voyager.", e);

    	    throw new ILSException(
    		    "An error occurred while confirming authentication on Voyager.  This may have been caused "
    			    + "if the VoyagerUrl was set incorrectly in the NCIP Toolkit configuration file.");
    	}
    }
    
    /**
     * Authenticates user against LDAP server
     * 
     * @param username User name of user
     * @param password Password
     * @return
     * @throws ILSException
     * @throws ILSException
     */
    public String authenticateLDAPUser(String username, String password) throws ILSException {
    	if (log.isDebugEnabled())
    	    log.debug("Entering authenticateUser for LDAP user: " + username + ".");
    	// If someone's already logged in, get a new PID and SEQ and reset the
    	// authenticatedUserId. This will effectively log out whomever was
    	// previously authenticated.
    	if (authenticatedUserId != null && authenticatedUserId.length() > 0) {
    	    if (log.isDebugEnabled())
    		log
    			.debug("Removing authentication data for previously authenticated user with ID "
    				+ authenticatedUserId);

    	    authenticatedUserId = null;
    	    voyagerAuthentication.setupPidAndSeq(NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_BASE_URL) + "?DB=local&PAGE=First");
    	}

    	// Check that the PID and SEQ are valid
    	if (!voyagerAuthentication.validPidAndSeq()) {
    	    if (log.isDebugEnabled())
    		log.debug("The PID or SEQ was not set up correctly");

    	    // Since the PID and SEQ were setup during initialization, the
    	    // initializationError string
    	    // contains the correct human readable error message explaining what
    	    // went wrong.
    	    throw new ILSException("User authentication failed.");
    	}

    	// The connection to the LDAP server
    	DirContext ldapConnection = null;
    	LDAPUtils ldapUtils = new LDAPUtils();

    	try {
    	    // Get a connection to the LDAP server
    	    ldapConnection = ldapUtils.getLDAPConnection(username, password);

    	    // Get the username attribute and start location on the LDAP server
    	    // from the configuration file
    	    String usernameAttribute = NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_EXTERNAL_LDAP_USERNAME_ATTRIBUTE);
    	    String startLocation = NCIPConfiguration
    		    .getProperty(VoyagerConstants.CONFIG_EXTERNAL_LDAP_START);

    	    // Get the attributes associated with the user
    	    Attributes attributes = ldapConnection
    		    .getAttributes(usernameAttribute + "=" + username + ", "
    			    + startLocation);

    	    // Get the attribute containing the patron key which Voyager needs
    	    String urid = (String) attributes
    		    .get(
    			    NCIPConfiguration
    				    .getProperty(VoyagerConstants.CONFIG_EXTERNAL_LDAP_UR_ID))
    		    .get();

    	    if (log.isDebugEnabled())
    		log.debug("Found that the patron key was: " + urid + ".");

    	    // Add a row to the voyager database associating the PID with the
    	    // URID
    	    authenticatedUserId = insertPidAndPatronkey(urid, voyagerAuthentication.getPid());

    	    // Send the link confirming that the user has been authenticated
    	    voyagerAuthentication.sendAuthenticationUrl();

    	    // Setup the heartbeat Thread to maintain authentication for the
    	    // newly authenticated user
    	    voyagerAuthentication.setupHeartbeatThread(urid);

    	    // Return the user which we authenticated
    	    return urid;
    	} catch (NamingException e) {
    	    log.error("An error occurred while getting the users patron key.",
    		    e);

    	    throw new ILSException(
    		    "An error occurred while getting the users patron key.  This may have been caused "
    			    + "if the ExternalLDAPVoyagerPatronKey was set incorrectly in the NCIP Toolkit configuration file.");
    	} catch (IOException e) {
    	    log.error("An error occurred while confirming authentication on Voyager.", e);

    	    throw new ILSException(
    		    "An error occurred while confirming authentication on Voyager.  This may have been caused "
    			    + "if the VoyagerUrl was set incorrectly in the NCIP Toolkit configuration file.");
    	} finally {
    	    // If we set up a connection to the LDAP server, close it
    	    if (ldapConnection != null) {
	    		try {
	    		    if (log.isDebugEnabled())
	    			log.debug("Closing the LDAP connection.");
	
	    		    ldapConnection.close();
	    		} catch (NamingException e) {
	    		    log
	    			    .warn(
	    				    "An error occurred while closing the connection to the LDAP server defined in the "
	    					    + "configuration file.", e);
	    		}
    	    }
    	}
    }

	/**
	 * Given a user's authentication data, return their institution ID.  If their
	 * authentication information was not valid, return null instead
	 * 
	 * @param username The user's barcode
	 * @param password The user's password
	 * @return The user's institution ID if the username and password were correct, or null otherwise
	 * @throws ILSException If an error occurred while connecting to the database
	 */
	public String getInstitutionIdFromBarcodeAuthData(String username, String password) throws ILSException
	{
		// The ResultSet from the SQL query
		ResultSet results = null;
		PreparedStatement psGetInstitutionIdFromBarcodeAuthentication = null;
		
		try
		{
			// SQL to get the patron ID and institution ID where the institution ID is
			// the student ID and the last name is LIKE the last name they entered (not 
			// case sensitive) and where the barcode for the student ID has a status of 1 (active).
			String sql = "SELECT " +  "patron.institution_id " +
		                 "FROM " +  "patron " +
		                 "INNER JOIN " +  "patron_barcode ON " +  "patron.patron_id = " +  "patron_barcode.patron_id " +
		                 "WHERE " +  "patron_barcode.patron_barcode = ? " +
		                 "AND LOWER(" +  "patron.last_name) LIKE ? " +
		                 "AND " +  "patron_barcode.barcode_status = '1'";

			if(log.isDebugEnabled())
				log.debug("Creating the get institution ID from barcode authentication data PreparedStatement from the SQL: " + sql);
			
			// Prepared Statement to get the location ID from the location
			// Using a prepared statement should prevent SQL injection
			psGetInstitutionIdFromBarcodeAuthentication = conn.prepareStatement(sql);
			
			// Set the parameters for the statement
			psGetInstitutionIdFromBarcodeAuthentication.setString(1, username);
			psGetInstitutionIdFromBarcodeAuthentication.setString(2, password.toLowerCase());
			
			// Execute the query
			results = psGetInstitutionIdFromBarcodeAuthentication.executeQuery();
			
			// Return true if any rows were returned.  This will occur only when the item exists.
			return (results.next() ? results.getString(1).trim() : null);
		}
		catch(SQLException e)
		{
			log.error("An error occurred while checking a user's authentication data.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
					"caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {	
			// If we created a ResultSet, close it
			if(results != null)
			{
				try
				{
					if(log.isDebugEnabled())
						log.debug("Closing the ResultSet.");
					
					results.close();
				}
				catch(SQLException e)
				{
					log.warn("An error occurred while closing the ResultSet.", e);				
				}
			}
		}
	}

	
	/**
	 * Inserts a row in the voyager database associating the passed patron key with
	 * the PID which was set up in the constructor.
	 * 
	 * @param voyagerDb A connection to the voyager database
	 * @param patronKey The patron key we're associating with the PID
	 * @param pid The PID we're associating with the patron key
	 * @return The patron key
	 * @throws SQLException If the queries run on the voyager database fail
	 */
	public String insertPidAndPatronkey(String patronKey, String pid) throws ILSException
	{
		if(log.isDebugEnabled())
			log.debug("Entering insertPidAndPatronkey for patron key: " + patronKey + " and PID: " + pid + ".");
		
		PreparedStatement psDeleteOrphans = null;
		PreparedStatement psAddPatronKey = null;
		
		try
		{
			// A previous failed login may result in orphaned rows in the
			// Voyager database.  Delete any rows associated with the
			// PID or patron key we're using so Voyager doesn't error
			// thinking this is a duplicate login.
			String deleteOrphans = "DELETE FROM "+ "WOPAC_PID_PATRON_KEYS " +
			  			           "WHERE PATRON_KEY = ? " + 
			   			           "OR PID = ?";
			
			if(log.isDebugEnabled())
				log.debug("Creating the delete orphans PreparedStatement from the SQL: " + deleteOrphans);
			
			// A prepared statement to run the deleteOrphans SQL
			// This should sanitize the SQL and prevent SQL injection
			psDeleteOrphans = writeConn.prepareStatement(deleteOrphans);
			
			// Set the parameters on the prepared statement
			psDeleteOrphans.setString(1, patronKey);
			psDeleteOrphans.setString(2, pid);
				
			psDeleteOrphans.executeQuery();
		
			// Insert a row into the WOPAC_PID_PATRON_KEYS table to tell Voyager
			// that the user has logged in.  We insert a row containing both the
			// PID we got for this user and their user ID so that Voyager knows to
			// associate all calls made with this PID with the correct user
			String addPatronKey = "INSERT INTO " +  "WOPAC_PID_PATRON_KEYS (PID, PATRON_KEY) " + 
			                      "VALUES (?, ?)";
			
			if(log.isDebugEnabled())
				log.debug("Creating the add patron key for PID PreparedStatement from the SQL: " + addPatronKey);
			
			// A prepared statement to run the addPatronKey SQL
			// This should sanitize the SQL and prevent SQL injection
			psAddPatronKey = writeConn.prepareStatement(addPatronKey);
			
			// Set the parameters on the prepared statement
			psAddPatronKey.setString(1, pid);
			psAddPatronKey.setString(2, patronKey);
			
			// Execute the statement
			int test = psAddPatronKey.executeUpdate();
			log.debug("Execute update result = " + test);
		
			// If we got here without throwing an exception, the patron is now logged in.  Save
			// their user ID for future reference
			return patronKey;
		}
		catch(SQLException e)
		{
			log.error("An error occurred while inserting the row into the database.", e);
	        
	        throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
	                			   "caused if the NCIP Toolkit did not have write permissions to the Voyager database, or " + 
	                			   "if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.",e);
		}

	}


	/**
	 * Given a user's authentication data, return their institution ID.  If their
	 * authentication information was not valid, return null instead
	 * 
	 * @param username The user's username
	 * @param password The user's password
	 * @return The user's institution ID if the username and password were correct, or null otherwise
	 * @throws ILSException If an error occurred while connecting to the database
	 */
	public String getInstitutionIdFromAuthData(String username, String password) throws ILSException
	{
		// The ResultSet from the SQL query
		ResultSet results = null;
		PreparedStatement psGetInstitutionIdFromAuthentication = null;
		
		try
		{
			// SQL to get the patron ID and institution ID where the institution ID is
			// the student ID and the last name is LIKE the last name they entered (not 
			// case sensitive) and where the barcode for the student ID has a status of 1 (active).
			String sql = "SELECT " +  "patron.institution_id " +
			             "FROM " +  "patron " +
			             "INNER JOIN " +  "patron_barcode ON " +  "patron.patron_id = " +  "patron_barcode.patron_id " +
			             "WHERE " +  "patron.institution_id = ? " +
			             "AND LOWER(" +  "patron.last_name) LIKE ? " +
			             "AND " +  "patron_barcode.barcode_status = '1'";

			if(log.isDebugEnabled())
				log.debug("Creating the get institution ID from authentication data PreparedStatement from the SQL: " + sql);
			
			// Prepared Statement to get the location ID from the location
			// Using a prepared statement should prevent SQL injection
			psGetInstitutionIdFromAuthentication = conn.prepareStatement(sql);
				
			// Set the parameters for the statement
			psGetInstitutionIdFromAuthentication.setString(1, username);
			psGetInstitutionIdFromAuthentication.setString(2, password.toLowerCase());
			
			// Execute the query
			results = psGetInstitutionIdFromAuthentication.executeQuery();
			
			// Return true if any rows were returned.  This will occur only when the item exists.
			return (results.next() ? results.getString(1).trim() : null);
		}
		catch(SQLException e)
		{
			log.error("An error occurred while checking a user's authentication data.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.",e);
		}
		finally
		{	
			try {
				if(psGetInstitutionIdFromAuthentication != null) {
					psGetInstitutionIdFromAuthentication.close();
				}
			} catch (SQLException e) {
				throw new ILSException("An error occurred while interacting with the Voyager database.", e);
			}
		}
	}


    
	public String getPatronIdFromInstitutionId(String userId) throws ILSException {
		// SQL to get the patron ID for the user with the institution ID equal to the user's unique ID
		String sql = "SELECT " +  "patron.patron_id " +
		             "FROM "  + "patron " +
		             "WHERE " + "patron.institution_id = ?";

		String patronId = null;
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
	
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();    
	
			// Get the patron ID for the user. The patron ID is used for lookups on
			// several different
			// tables, but the NCIPUser object will only have the institution ID.
			while(rs.next()){    
				patronId = rs.getString(1);
			}
		} catch (SQLException se) {
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", se);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				throw new ILSException("An error occurred while interacting with the Voyager database.", e);
			}		
		}
		return patronId;
	}

	public NameInformation getNameInformation(String patronId) throws ILSException {
		// SQL to get the name information for patron ID 
		String sql = "SELECT patron.first_name, patron.middle_name, patron.last_name " +
		             "FROM "  + "patron " +
		             "WHERE " + "patron.patron_id = ?";

		PreparedStatement pstmt = null;
		StructuredPersonalUserName structuredUserName = new StructuredPersonalUserName();
		
		try {
			pstmt = conn.prepareStatement(sql);
	
			pstmt.setString(1, patronId);
			ResultSet rs = pstmt.executeQuery();    
	
			StringBuffer name = new StringBuffer();
			while(rs.next()){    
				name.append(rs.getString(1));
				structuredUserName.setGivenName(rs.getString(1));
				if (rs.getString(2) != null) {
					name.append(" " + rs.getString(2));
					structuredUserName.setInitials(rs.getString(2));
				}
				name.append(" " + rs.getString(3));
				structuredUserName.setSurname(rs.getString(3));
			}
			
			PersonalNameInformation p  = new PersonalNameInformation();
			p.setUnstructuredPersonalUserName(name.toString());
			p.setStructuredPersonalUserName(structuredUserName);
				
			NameInformation n = new NameInformation();
			n.setPersonalNameInformation(p);
			return n;
		} catch (SQLException se) {
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", se);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				throw new ILSException("An error occurred while interacting with the Voyager database.", e);
			}		
		}
	}

	
	/**
	 * Given a user's patron ID, lookup and return their address from the Voyager database
	 * 
	 * @param patronId The patron ID of the user whose address should be returned
	 * @return The user's address
	 * @throws ILSException If an error occurred while connecting to the database.
	 */
	public List<UserAddressInformation> getAddress(String patronId) throws ILSException
	{
		// The ResultSet from the SQL query
		ResultSet results = null;
		PreparedStatement psGetAddress = null;
		
		try	{
			// SQL to get the user's address based on their patron ID
			// The address is stored in 5 different columns in the voyager
			// database.  We need to select all 5 columns and combine them into
			// a single string.  We also select the address type so we know if
			// the row is a home, work, or email address
			String sql = "SELECT " + "patron_address.address_line1, " +
			                  		 "patron_address.address_line2, " +
			                  		 "patron_address.address_line3, " +
			                  		 "patron_address.address_line4, " +
			                  		 "patron_address.address_line5, " +
			                  		 "patron_address.city, " +
			                  		 "patron_address.state_province, " +
			                  		 "patron_address.zip_postal, " +
			                  		 "patron_address.address_type " +
			             "FROM " + "patron_address " +
			             "WHERE " + "patron_address.patron_id = ? " +
			             	"and ((EFFECT_DATE is null and EXPIRE_DATE is null) " +
				             	"or (EFFECT_DATE < CURRENT_TIMESTAMP and Expire_DATE > CURRENT_TIMESTAMP) " +
				             	"or (EFFECT_DATE is null and EXPIRE_DATE > CURRENT_TIMESTAMP) " +
				             	"or (EFFECT_DATE < CURRENT_TIMESTAMP and EXPIRE_DATE is null)) " +
				             "order by ADDRESS_TYPE" ;

			if(log.isDebugEnabled())
				log.debug("Creating the get address PreparedStatement from the SQL: " + sql);
			
			// Prepared Statement to get the location ID from the location
			// Using a prepared statement should prevent SQL injection
			psGetAddress = conn.prepareStatement(sql);
		
			// Set the parameters for the statement
			psGetAddress.setString(1, patronId);
			
			// Execute the query
			results = psGetAddress.executeQuery();
			
			List<UserAddressInformation> userAddressInformations = new ArrayList<UserAddressInformation>();
			
			// If any results were returned
			if(results.next())
			{
				UserAddressInformation userAddressInformation = new UserAddressInformation();
				
				int type = results.getInt(9);
				
				// The title returned by the SQL query
				StringBuilder address = new StringBuilder();
				
				String temp = null;
				
				if (type != VoyagerConstants.ADDRESS_TYPE_EMAIL) {
					// Append address lines 1-5 if they're not null
					temp = results.getString(1);
					if(temp != null)
						address.append("\n").append(temp.trim());

					temp = results.getString(2);
					if(temp != null)
						address.append("\n").append(temp.trim());
					
					temp = results.getString(3);
					if(temp != null)
						address.append("\n").append(temp.trim());
					
					temp = results.getString(4);
					if(temp != null)
						address.append("\n").append(temp.trim());
					
					temp = results.getString(5);
					if(temp != null)
						address.append("\n").append(temp.trim());
					
					// Append the city
					temp = results.getString(6);
					if(temp != null)
						address.append("\n").append(temp.trim());
					
					// Append the state
					temp = results.getString(7);
					if(temp != null)
						address.append("\n").append(temp.trim());
					
					// Append the zip code
					temp = results.getString(8);
					if(temp != null)
						address.append("\n").append(temp.trim());
					
					
			    	PhysicalAddress physicalAddress = new PhysicalAddress();
			    	if (type == VoyagerConstants.ADDRESS_TYPE_PERMANENT) {
			    		physicalAddress.setPhysicalAddressType(new SchemeValuePair(VoyagerConstants.LOCATION_TYPE_PERMANENT));
			    		userAddressInformation.setUserAddressRoleType(new SchemeValuePair(VoyagerConstants.LOCATION_TYPE_PERMANENT));
			    	} else {
			    		physicalAddress.setPhysicalAddressType(new SchemeValuePair(VoyagerConstants.LOCATION_TYPE_TEMPORARY));
			    		userAddressInformation.setUserAddressRoleType(new SchemeValuePair(VoyagerConstants.LOCATION_TYPE_TEMPORARY));
			    	}
				    
				    UnstructuredAddress unstructuredAddress = new UnstructuredAddress();
				    unstructuredAddress.setUnstructuredAddressData(address.toString());
				    unstructuredAddress.setUnstructuredAddressType(new SchemeValuePair("newline-delimited text"));
				    physicalAddress.setUnstructuredAddress(unstructuredAddress);
				    
					userAddressInformation.setPhysicalAddress(physicalAddress);
					
				} else {
					// The first line in the address returned by the SQL query
					address.append(results.getString(1));
					
					ElectronicAddress electronicAddress = new ElectronicAddress();
					electronicAddress.setElectronicAddressData(address.toString());
					electronicAddress.setElectronicAddressType(new SchemeValuePair(VoyagerConstants.ADDRESS_EMAIL));
					userAddressInformation.setElectronicAddress(electronicAddress);
					userAddressInformation.setUserAddressRoleType(new SchemeValuePair(VoyagerConstants.ADDRESS_EMAIL));
				}
				userAddressInformations.add(userAddressInformation);
				
				if(log.isDebugEnabled())
					log.debug("Found the address to be " + address.toString() + " in the voyager database");

				// Return userAddressInformations
				return userAddressInformations;
				} else  {// There were no rows in the database, the address could not be found
					log.info("The address was not found in the database.");
					
					return null;
				}
		} catch(SQLException e)	{
			log.error("An error occurred while getting the address from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			try {
				if(psGetAddress != null) {
					psGetAddress.close();
				}
			} catch (SQLException e) {
				throw new ILSException("An error occurred while interacting with the Voyager database.", e);
			}
		}
	}

	/**
	 * Returns the currency code used by the version of Voyager we're talking with
	 * 
	 * @return The currency code Voyager is using
	 * @throws ILSException If an error occurred while connecting to the database.
	 */
	public String getCurrencyCode() throws ILSException
	{
		// The ResultSet from the SQL query
		ResultSet results = null;
		PreparedStatement psGetCurrencyCode  = null;
		
		try
		{
			// SQL to get the location ID where the location is LIKE the locationString (not case sensitive)
			String sql = "SELECT " + "base_currency.base_currency_code " +
			             "FROM " + "base_currency ";

			if(log.isDebugEnabled())
				log.debug("Creating the get currency code PreparedStatement from the SQL: " + sql);
			
			// Prepared Statement to get the location ID from the location
			// Using a prepared statement should prevent SQL injection
			psGetCurrencyCode = conn.prepareStatement(sql);

			// Execute the query
			results = psGetCurrencyCode.executeQuery(sql);
			
			// If any results were returned
			if(results.next())
			{
				// The location ID returned by the SQL query
				String currencyCode = results.getString(1).trim();
				
				if(log.isDebugEnabled())
					log.debug("Found a row for the currency code " + currencyCode + " in the voyager database");

				// Return the location ID
				return currencyCode;
			}
			else // There were no rows in the database, the location could not be found
			{				
				log.info("The currency code was not found in the database, defaulting to configured value");
				
				return NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_VOYAGER_DEFAULT_CURRENCY);
			}
		}
		catch(SQLException e)
		{
			log.error("An error occurred while inserting the row into the database.", e);
	        
	        throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
	                			   "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(psGetCurrencyCode != null)
			{
				try
				{
					psGetCurrencyCode.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);
				}
			}
		}
	}

	/**
	 * Given a user's patron ID, lookup and return their total fines from the Voyager database.
	 * 
	 * @param patronId The patron ID of the user whose total fines should be returned
	 * @return The user's total fines
	 * @throws ILSException If an error occurred while connecting to the database.
	 */
	public BigDecimal getTotalFines(String patronId) throws ILSException
	{
		// The ResultSet from the SQL query
		ResultSet results = null;
		PreparedStatement psGetTotalFines = null;
		
		try
		{
			// SQL to get the user's total fines based on their patron ID
			String sql = "SELECT " + "patron.total_fees_due " +
			      "FROM " + "patron " +
			      "WHERE " + "patron.patron_id = ?";

			if(log.isDebugEnabled())
				log.debug("Creating the get total fines PreparedStatement from the SQL: " + sql);
			
			// Prepared Statement to get the total fines from the patron ID
			// Using a prepared statement should prevent SQL injection
			psGetTotalFines = conn.prepareStatement(sql);

			// Set the parameters for the statement
			psGetTotalFines.setString(1, patronId);
				
			// Execute the query
			results = psGetTotalFines.executeQuery();
			
			// If any results were returned
			if(results.next())
			{
				BigDecimal totalFines = results.getBigDecimal(1);
				
				if(log.isDebugEnabled())
					log.debug("Found the total fines to be " + totalFines + " in the voyager database");

				// Return the total fines
				return totalFines;
			}
			else // There were no rows in the database, the name could not be found
			{				
				log.info("The total fines were not found in the database.");
				
				return BigDecimal.ZERO;
			}
		} catch(SQLException e) {
			log.error("An error occurred while getting the total fines from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(psGetTotalFines != null)
			{
				try
				{
					psGetTotalFines.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
	}

	public List<RequestedItem> getRequestedItemForCallslipRequests(String patronId) throws ILSException {
		
		PreparedStatement pstmt = null;
		List<RequestedItem> requestedItems = new ArrayList<RequestedItem>();
		
		try {
			// SQL to get the date placed, pickup date and bibliographic ID of the item
			// for all callslip requests placed by the user
	
			String sql = "SELECT " + "call_slip.date_requested, " 
				    + "hold_recall.available_notice_date, " 
				    + "call_slip.item_id ," 
				    + "hold_recall_items.queue_position, " 
				    + "call_slip_status_type.status_desc,  "
				    + "bib_text.title "
		            + "FROM   call_slip  "
		            + "LEFT JOIN   hold_recall ON   call_slip.call_slip_id =   hold_recall.call_slip_id "  
		            + "INNER JOIN   bib_item ON   call_slip.item_id =   bib_item.item_id  	"
		            + "inner join hold_recall_items on hold_recall_items.hold_recall_id =    hold_recall.hold_recall_id	 "
		            + "INNER JOIN   call_slip_status_type    ON   call_slip_status_type.status_type = call_slip.status "	
		            + "inner join bib_text on bib_text.bib_id = call_slip.bib_id  "
				  	+ "WHERE   call_slip.patron_id = ? " 
				  	+ "AND   call_slip.status <> '7'"; 		
			
			pstmt = conn.prepareStatement(sql);
	
			pstmt.setString(1, patronId);
			ResultSet rs = pstmt.executeQuery();    
			
			// Loop over the results to get the callslips
			while(rs.next())
			{
				RequestedItem requestedItem = new RequestedItem();
				requestedItem.setDatePlaced(VoyagerUtil.convertDateToGregorianCalendar(rs.getTimestamp(1)));
				requestedItem.setPickupDate(VoyagerUtil.convertDateToGregorianCalendar(rs.getTimestamp(2)));
				
				ItemId itemId = new ItemId();
				itemId.setAgencyId(new SchemeValuePair(NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY)));
				itemId.setItemIdentifierValue(rs.getString(3));
				requestedItem.setItemId(itemId);
	
				if (rs.getBigDecimal(4).compareTo(BigDecimal.ZERO) == 1) {
					requestedItem.setHoldQueuePosition(rs.getBigDecimal(4));
				}
				requestedItem.setRequestType(XcRequestType.CALL_SLIP);
				requestedItem.setRequestStatusType(new SchemeValuePair(rs.getString(5)));
				requestedItem.setTitle(rs.getString(6));
				requestedItems.add(requestedItem);
			}
		} catch(SQLException e)	{
			log.error("An error occurred while getting the requested items from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
			
		return requestedItems;
	}

	public List<RequestedItem> getRequestedItemForHoldRequests(String patronId) throws ILSException {
		
		PreparedStatement pstmt = null;
		List<RequestedItem> requestedItems = new ArrayList<RequestedItem>();
		
		try {
		    // Add the holds to the user's list of requested items
			// SQL to get the date placed, pickup date and item ID of the item
			// for all hold requests placed by the user
			
			String sql =  "SELECT   hold_recall.create_date, hold_recall.available_notice_date, hold_recall_items.item_id, hold_recall_items.queue_position, hold_recall_status.hr_status_desc,  bib_text.title "
					+ "FROM    hold_recall "
					+ "Inner join (hold_recall_status INNER JOIN   hold_recall_items    ON   hold_recall_status.hr_status_type = hold_recall_items.hold_recall_status ) "
					+ "on   hold_recall_items.hold_recall_id =    hold_recall.hold_recall_id " 
					+ "inner join bib_text on bib_text.bib_id = hold_recall.bib_id "
					+ "WHERE    hold_recall.patron_id = ? "
					+ "AND    hold_recall.hold_recall_type = 'H'";
	
			pstmt = conn.prepareStatement(sql);
	
			pstmt.setString(1, patronId);
			ResultSet rs = pstmt.executeQuery();    
			
			// Loop over the results to get the callslips
			while(rs.next())
			{
				RequestedItem requestedItem = new RequestedItem();
				requestedItem.setDatePlaced(VoyagerUtil.convertDateToGregorianCalendar(rs.getTimestamp(1)));
				requestedItem.setPickupDate(VoyagerUtil.convertDateToGregorianCalendar(rs.getTimestamp(2)));
				
				ItemId itemId = new ItemId();
				itemId.setAgencyId(new SchemeValuePair(NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY)));
				itemId.setItemIdentifierValue(rs.getString(3));
				requestedItem.setItemId(itemId);

				if (rs.getBigDecimal(4).compareTo(BigDecimal.ZERO) == 1) {
					requestedItem.setHoldQueuePosition(rs.getBigDecimal(4));
				}
				
				requestedItem.setRequestType(Version1RequestType.HOLD);
				requestedItem.setRequestStatusType(new SchemeValuePair(rs.getString(5)));
				requestedItem.setTitle(rs.getString(6));
				requestedItems.add(requestedItem);
			}
		} catch(SQLException e)	{
			log.error("An error occurred while getting the requested items from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
			
		return requestedItems;
	}

	public List<LoanedItem> getLoanedItems(String patronId) throws ILSException {

		PreparedStatement pstmt = null;
		PreparedStatement amountPstmt = null;
		try {
			String sql = "SELECT " + "CIRC_TRANSACTIONS.CHARGE_DUE_DATE, CIRC_TRANSACTIONS.ITEM_ID , CIRC_TRANSACTIONS.OVERDUE_NOTICE_COUNT, BIB_TEXT.TITLE, FINE_FEE.FINE_FEE_BALANCE " +
				"FROM circ_transactions INNER JOIN 	(BIB_ITEM INNER JOIN BIB_TEXT ON BIB_ITEM.BIB_ID = BIB_TEXT.BIB_ID) ON CIRC_TRANSACTIONS.ITEM_ID = BIB_ITEM.ITEM_ID " +
				" LEFT OUTER JOIN FINE_FEE ON FINE_FEE.ITEM_ID = CIRC_TRANSACTIONS.ITEM_ID " +
				"WHERE " +  "CIRC_TRANSACTIONS.PATRON_ID = ?";
			
			pstmt = conn.prepareStatement(sql);
		
			pstmt.setString(1, patronId);
			ResultSet rs = pstmt.executeQuery();  
			
			List<LoanedItem> loans = new ArrayList<LoanedItem>();
		
			// Get the patron ID for the user. The patron ID is used for lookups on
			// several different
			// tables, but the NCIPUser object will only have the institution ID.
			// Loop over the results to get the loaned items
			while(rs.next())
			{
				// Create a new LoanedItem Object to store information on the item the user checked out
				LoanedItem loanedItem = new LoanedItem();
				ItemId itemId = new ItemId();
				itemId.setAgencyId(new SchemeValuePair(NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY)));
				itemId.setItemIdentifierValue(rs.getString(2));
				loanedItem.setItemId(itemId);
				loanedItem.setDateDue(VoyagerUtil.convertDateToGregorianCalendar(rs.getTimestamp(1)));
				log.debug("dd ="+ rs.getDate(1));
				log.debug("T ="+rs.getTime(1));
				log.debug("ts="+ rs.getTimestamp(1));
				log.debug("converted dd ="+ VoyagerUtil.convertDateToGregorianCalendar(rs.getTimestamp(1)));
				
				// TODO Value of Amount element needs to determined
				Amount amount  = new Amount();
				amount.setCurrencyCode(new SchemeValuePair(getCurrencyCode()));
				if (rs.getBigDecimal(5) != null && rs.getBigDecimal(5).compareTo(BigDecimal.ZERO) == 1  ) {
					amount.setMonetaryValue(rs.getBigDecimal(5));
				} else {
					amount.setMonetaryValue(BigDecimal.ZERO);
				}
				loanedItem.setAmount(amount);
				
				// TODO ReminderLevel -used circ_transactions.overdue_notice_count field. Need to make sure if thats correct
				// Sometimes returns 0 which is unacceptable for positive integer. what should be set in those cases?. For now its set to 1
				if (rs.getBigDecimal(3).compareTo(BigDecimal.ZERO) == 1) {
					loanedItem.setReminderLevel(rs.getBigDecimal(3));
				} else {
					loanedItem.setReminderLevel(BigDecimal.ONE);
				}
				loanedItem.setTitle(rs.getString(4));
				loans.add(loanedItem);
			}
			log.debug("Number of loaned items = " + loans.size());
			return loans;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the loaned items from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
			if(amountPstmt != null)
			{
				try
				{
					amountPstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
	}
	
	public List<LoanedItemsCount> getLoanedItemsCount(List<String> itemIds) throws ILSException, ServiceException {

		PreparedStatement pstmt = null;
		try {
			String sql = "SELECT item_status_type.item_status_desc, count(*) FROM item_status INNER JOIN item_status_type ON item_status.item_status = item_status_type.item_status_type WHERE item_status.item_id in ( ";
				
			for (int i = 0; i < itemIds.size(); i++) {
				sql = sql + "?";
				if (i != itemIds.size() -1) {
					sql = sql + ",";
				}
			}
						 
			sql = sql + ") GROUP BY item_status_type.item_status_desc";
			log.debug("SQL = "+sql);
			pstmt = conn.prepareStatement(sql);
		
			for (int i = 1; i <= itemIds.size(); i++) {
				pstmt.setString(i, itemIds.get(i - 1));
			}
			
			ResultSet rs = pstmt.executeQuery();    
		
			List<LoanedItemsCount> loans = new ArrayList<LoanedItemsCount>();
		
			while(rs.next())
			{
				// Create a new LoanedItemsCount Object to store information 
				LoanedItemsCount loanedItemsCount = new LoanedItemsCount();

				loanedItemsCount.setCirculationStatus(XcCirculationStatus.find(XcCirculationStatus.XC_CIRCULATION_STATUS, rs.getString(1)));
				
				loanedItemsCount.setLoanedItemCountValue(rs.getBigDecimal(2));
		
				loans.add(loanedItemsCount);
			}
			log.debug("Number of loaned items Count= " + loans.size());
			return loans;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the loaned items count from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
	}

	/**
	 * Given a user's patron ID, lookup and return their fines from the Voyager database.
	 * 
	 * @param patronId The patron ID of the user whose fines should be returned
	 * @return The user's fines
	 * @throws ILSException If an error occurred while connecting to the database.
	 */
	public List<AccountDetails> getAccountDetails(String patronId)  throws ILSException {
		// The ResultSet from the SQL query
		PreparedStatement pstmt = null;
		
		List<AccountDetails> accountDetails = new ArrayList<AccountDetails>();
		
		try
		{
			String currencyCode = getCurrencyCode();
			
			// SQL to get the balance, creation date for all the user's fines with a non-zero
			// balance as well as the title and bibliographic ID of the item the fine is for
			String sql = "SELECT " +  "fine_fee.fine_fee_balance, " +
                                      "fine_fee.create_date, " +
                                      "fine_fee.item_id, " +
                                      "fine_fee.orig_charge_date, " +
                                      "circ_trans_archive.discharge_date, " +
                                      "renew_transactions.renew_date, " +
                                      "bib_text.title ," +
                                      "fine_fee.due_date, " +
                                      "fine_fee_type.fine_fee_desc " +
			             "FROM " +  "fine_fee " +
			             "LEFT JOIN (bib_item Inner join bib_text on bib_item.bib_id = bib_text.bib_id) ON  fine_fee.item_id = bib_item.item_id " +
			             "INNER JOIN fine_fee_type ON fine_fee_type.fine_fee_type = fine_fee.FINE_FEE_TYPE " +
			             "LEFT JOIN circ_trans_archive ON circ_trans_archive.item_id = fine_fee.item_id and circ_trans_archive.patron_id = ? " +
			             "LEFT JOIN ( circ_transactions INNER JOIN  renew_transactions ON renew_transactions.circ_transaction_id = circ_transactions.circ_transaction_id ) " +
			             "on circ_transactions.item_id = fine_fee.item_id " +
			             "WHERE " +  "fine_fee.patron_id = ? " + 
			             "AND fine_fee.fine_fee_balance <> '0'";

			if(log.isDebugEnabled())
				log.debug("Creating the get fines PreparedStatement from the SQL: " + sql);
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, patronId);
			pstmt.setString(2, patronId);
			ResultSet results = pstmt.executeQuery();    

			// Loop over the results to get the fines
			while(results.next())
			{
				AccountDetails details = new AccountDetails();
				
				details.setAccrualDate(VoyagerUtil.convertDateToGregorianCalendar(results.getTimestamp(2)));
				
				FiscalTransactionInformation fiscalTransactionInformation = new FiscalTransactionInformation();
				Amount amount = new Amount();
				amount.setCurrencyCode(new SchemeValuePair(currencyCode));
				amount.setMonetaryValue(results.getBigDecimal(1));
				fiscalTransactionInformation.setAmount(amount);

				// TODO - temporarily set to 'Payment'
				fiscalTransactionInformation.setFiscalActionType(Version1FiscalActionType.PAYMENT);
				
				ItemDetails itemDetails = new ItemDetails();
				ItemId itemId = new ItemId();
				itemId.setAgencyId(new SchemeValuePair(NCIPConfiguration.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY)));
				itemId.setItemIdentifierValue(results.getString(3));
				itemDetails.setItemId(itemId);
				
				Timestamp dateCheckedOut = results.getTimestamp(4);
				if (dateCheckedOut != null) {
					itemDetails.setDateCheckedOut(VoyagerUtil.convertDateToGregorianCalendar(dateCheckedOut));
				}
				
				Timestamp dateReturned = results.getTimestamp(5);
				if (dateCheckedOut != null) {
					itemDetails.setDateReturned(VoyagerUtil.convertDateToGregorianCalendar(dateReturned));
				}
				
				Timestamp dateRenewed = results.getTimestamp(6);
				if (dateCheckedOut != null) {
					// TODO list of dates
					List<GregorianCalendar> dates = new ArrayList<GregorianCalendar>();
					dates.add(VoyagerUtil.convertDateToGregorianCalendar(dateRenewed));
					itemDetails.setDateRenewed(dates);
				}
				
				BibliographicDescription description = new BibliographicDescription();
				description.setTitle(results.getString(7));
				itemDetails.setBibliographicDescription(description);
				
				
				Timestamp dueDate = results.getTimestamp(8);
				if (dueDate != null) {
					itemDetails.setDateDue(VoyagerUtil.convertDateToGregorianCalendar(dueDate));
				}
				fiscalTransactionInformation.setItemDetails(itemDetails);
				
				fiscalTransactionInformation.setFiscalTransactionType(new SchemeValuePair(results.getString(9)));
				
				details.setFiscalTransactionInformation(fiscalTransactionInformation);
				
				accountDetails.add(details);
			}
							
			log.debug("Found " + accountDetails.size() + " fines in the database.");
			
			return accountDetails;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the account details from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
	}
		

	public BibliographicDescription getBibliographicDescription(String itemId) throws ILSException {
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT bib_text.bib_id, bib_text.author, bib_text.isbn, bib_text.issn, bib_text.publisher,bib_text.series,bib_text.title FROM bib_text WHERE bib_text.bib_id = (select bib_id from bib_item where bib_item.item_id = ?) ");
	
			pstmt.setString(1,itemId);
			ResultSet rs = pstmt.executeQuery();    
	   
			BibliographicDescription bibliographicDescription = new BibliographicDescription();
			boolean hasBibDesc = false;
			
			List<BibliographicItemId> bibliographicItemIds;
			while(rs.next()) {
				String author = StringUtils.trim(rs.getString(2));
				String isbn = StringUtils.trim(rs.getString(3));
				String issn = StringUtils.trim(rs.getString(4));
				String title = StringUtils.trim(rs.getString(7));
			
				if(author!=null && author.length()>0){
					bibliographicDescription.setAuthor(author);
					hasBibDesc = true;
				}
				
				bibliographicItemIds = new ArrayList<BibliographicItemId>();
				// Set bib item id
				if(isbn != null && isbn.length() > 0 ){
					BibliographicItemId bibliographicItemId = new BibliographicItemId();
					bibliographicItemId.setBibliographicItemIdentifier(isbn);
					BibliographicItemIdentifierCode code = new BibliographicItemIdentifierCode(
							Version1BibliographicItemIdentifierCode.VERSION_1_BIBLIOGRAPHIC_ITEM_IDENTIFIER_CODE,
							"ISBN");
					bibliographicItemId.setBibliographicItemIdentifierCode(code);
					bibliographicItemIds.add(bibliographicItemId);
					bibliographicDescription.setBibliographicItemIds(bibliographicItemIds);
					hasBibDesc = true;
				}
				else if(issn != null && issn.length() > 0){
					BibliographicItemId bibliographicItemId = new BibliographicItemId();
					bibliographicItemId.setBibliographicItemIdentifier(issn);
					BibliographicItemIdentifierCode code = new BibliographicItemIdentifierCode(
							Version1BibliographicItemIdentifierCode.VERSION_1_BIBLIOGRAPHIC_ITEM_IDENTIFIER_CODE,
							"ISSN");
					bibliographicItemId.setBibliographicItemIdentifierCode(code);
					bibliographicItemIds.add(bibliographicItemId);
					bibliographicDescription.setBibliographicItemIds(bibliographicItemIds);
					hasBibDesc = true;
				}
		
				if(title!=null && title.length()>0){
					bibliographicDescription.setTitle(title);
					hasBibDesc = true;
				}

			}
			
			//only set when there is at least one field in bib desc
			if(!hasBibDesc){
				return null;
			}
			
			log.debug("Bibliographic Description : " + bibliographicDescription);
			
			return bibliographicDescription;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the bibliographic description from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		} 

	}

	public BibliographicDescription getBibliographicDescriptionForBibId(String bibId) throws ILSException {
		
		java.util.Date start = new java.util.Date();
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("SELECT bib_text.bib_id, bib_text.author, bib_text.isbn, bib_text.issn, bib_text.publisher,bib_text.series,bib_text.title FROM bib_text WHERE bib_text.bib_id = ? ");
	
			pstmt.setString(1,bibId);
			ResultSet rs = pstmt.executeQuery();    
	   
			BibliographicDescription bibliographicDescription = new BibliographicDescription();
			boolean hasBibDesc = false;
			
			List<BibliographicItemId> bibliographicItemIds ;
			while(rs.next()) {
				String author = StringUtils.trim(rs.getString(2));
				String isbn = StringUtils.trim(rs.getString(3));
				String issn = StringUtils.trim(rs.getString(4));
				String title = StringUtils.trim(rs.getString(7));
			
				if(author!=null && author.length()>0){
					bibliographicDescription.setAuthor(author);
					hasBibDesc = true;
				}
				
				bibliographicItemIds = new ArrayList<BibliographicItemId>();
				
				// Set bib item id
				if(isbn != null && isbn.length() > 0 ){
					BibliographicItemId bibliographicItemId = new BibliographicItemId();
					bibliographicItemId.setBibliographicItemIdentifier(isbn);
					BibliographicItemIdentifierCode code = new BibliographicItemIdentifierCode(
							Version1BibliographicItemIdentifierCode.VERSION_1_BIBLIOGRAPHIC_ITEM_IDENTIFIER_CODE,
							"ISBN");
					bibliographicItemId.setBibliographicItemIdentifierCode(code);
					bibliographicItemIds.add(bibliographicItemId);
					bibliographicDescription.setBibliographicItemIds(bibliographicItemIds);
					hasBibDesc = true;
				}
				else if(issn != null && issn.length() > 0){
					BibliographicItemId bibliographicItemId = new BibliographicItemId();
					bibliographicItemId.setBibliographicItemIdentifier(issn);
					BibliographicItemIdentifierCode code = new BibliographicItemIdentifierCode(
							Version1BibliographicItemIdentifierCode.VERSION_1_BIBLIOGRAPHIC_ITEM_IDENTIFIER_CODE,
							"ISSN");
					bibliographicItemId.setBibliographicItemIdentifierCode(code);
					bibliographicItemIds.add(bibliographicItemId);
					bibliographicDescription.setBibliographicItemIds(bibliographicItemIds);
					hasBibDesc = true;
				}
		
				if(title!=null && title.length()>0){
					bibliographicDescription.setTitle(title);
					hasBibDesc = true;
				}

			}
			
			//only set when there is at least one field in bib desc
			if(!hasBibDesc){
				return null;
			}
			
			log.debug("Bibliographic Description : " + bibliographicDescription);
			
			java.util.Date end = new java.util.Date();
			log.debug("getBibliographicDescriptionForBibId time: " + (end.getTime() - start.getTime()) + "  " + ((end.getTime() - start.getTime())/1000) + " sec");
			return bibliographicDescription;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the bibliographic description from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		} 

	}

	
	public int getItemCount(String itemId) throws ILSException {
		
		PreparedStatement pstmt = null;
		int count = 0;
		
		try {
			pstmt = conn.prepareStatement("SELECT count(*) FROM item WHERE item_id = ?");
			
			pstmt.setString(1, itemId);
			ResultSet rs = pstmt.executeQuery();    
		
			while(rs.next()){    
				count = rs.getInt(1);
			}
			log.debug("Item count = " + count);
			return count;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the item count from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		} 
	}
	
	public String getCirculationStatus(String itemId)  throws ILSException {
		PreparedStatement pstmt = null;
		String status = null;
		try {
			
			pstmt = conn.prepareStatement("SELECT item_status_type.item_status_desc FROM item_status INNER JOIN item_status_type ON item_status.item_status = item_status_type.item_status_type WHERE item_status.item_id = ? ORDER BY item_status.item_status_date DESC");
	
			pstmt.setString(1, itemId);
			ResultSet rs = pstmt.executeQuery();    
	   
			while(rs.next()){    
				status = StringUtils.trim(rs.getString(1));
	
				if(status!=null && status.length()>0)
					break;//only get the first one of the status by date
			}
			return status;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the circulation status from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		} 
	}
	
	public Map<String, String> getCirculationStatusForItemIds(List<String> itemIds)  throws ILSException {
		PreparedStatement pstmt = null;

		try {
			
			log.debug("Item Ids = " + itemIds);
			
			String sql = "select prequery.item_Id, item_status_type.ITEM_STATUS_DESC from  (select item_id, max(item_status_date) as dt from item_status where ITEM_ID  in (";
	
			for (int i = 0; i < itemIds.size(); i++) {
				sql = sql + "?";
				if (i != itemIds.size() -1) {
					sql = sql + ",";
				}
			}
						 
			sql = sql + ") group by ITEM_ID) prequery, item_status_type, item_status " 
				+ "where item_status.ITEM_ID = prequery.item_id and (prequery.dt is null or item_status.ITEM_STATUS_DATE = prequery.dt ) and item_status.ITEM_STATUS =  item_status_type.ITEM_STATUS_TYPE";

			pstmt = conn.prepareStatement(sql);

			for (int i = 1; i <= itemIds.size(); i++) {
				pstmt.setString(i, itemIds.get(i - 1));
			}
			ResultSet rs = pstmt.executeQuery();    
	   
			Map<String, String> statuses = new HashMap<String, String>();
			while(rs.next()){    
				statuses.put(StringUtils.trim(rs.getString(1)), StringUtils.trim(rs.getString(2)));
			}
			return statuses;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the circulation status from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		} 
	}

	public BigDecimal getTitleLevelHoldQueueLength(String bibId) throws ILSException {
		
		PreparedStatement pstmt = null;
		BigDecimal queueLength = null;
		int qLength;
		try {
			pstmt = conn.prepareStatement("select count(*) from hold_recall where bib_id = ? and request_level = 'T'");
	
			pstmt.setString(1, bibId);
			ResultSet rs = pstmt.executeQuery();    
	
			while(rs.next()){    
				qLength = rs.getInt(1);
				log.debug("qLength="+qLength);
				queueLength = rs.getBigDecimal(1);
			}
			return queueLength;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the title level hold queue length from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
		
	}
	
	public BigDecimal getHoldQueueLength(String itemId) throws ILSException {
		
		PreparedStatement pstmt = null;
		BigDecimal queueLength = null;
		try {
			pstmt = conn.prepareStatement("SELECT item.holds_placed FROM item WHERE item.item_id = ?");
	
			pstmt.setString(1, itemId);
			ResultSet rs = pstmt.executeQuery();    
			 
	
			while(rs.next()){    
				queueLength = rs.getBigDecimal(1);
			}
			return queueLength;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the hold queue length from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{ 
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
		
	}

	public Map<String, BigDecimal> getHoldQueueLengthForItemIds(List<String> itemIds) throws ILSException {
		
		PreparedStatement pstmt = null;
		Map<String, BigDecimal> queueLengths = new HashMap<String, BigDecimal>();
		try {
			String sql = "SELECT item.item_id,item.holds_placed FROM item WHERE item.item_id in (";
			 
			for (int i = 0; i < itemIds.size(); i++) {
				sql = sql + "?";
				if (i != itemIds.size() -1) {
					sql = sql + ",";
				}
			}
						 
			sql = sql + ")";
			pstmt = conn.prepareStatement(sql);
	
			for (int i = 1; i <= itemIds.size(); i++) {
				pstmt.setString(i, itemIds.get(i - 1));
			}		
			ResultSet rs = pstmt.executeQuery();    

			while (rs.next()) {
				queueLengths.put(StringUtils.trim(rs.getString(1)), rs.getBigDecimal(2));
			}
			return queueLengths;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the hold queue length from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
		
	}
	
	public ElectronicResource getElectronicResource(String itemId) throws ILSException {
		
		PreparedStatement pstmt = null;
		ElectronicResource electronicResource = null;
		try {
			pstmt = conn.prepareStatement("SELECT eitem.link FROM eitem WHERE eitem.mfhd_id = (SELECT mfhd_item.mfhd_id FROM mfhd_item WHERE mfhd_item.item_id = ?)");
		
			pstmt.setString(1, itemId);
			ResultSet rs = pstmt.executeQuery();    
		
			while(rs.next()){    
				electronicResource = new ElectronicResource();
				electronicResource.setReferenceToResource(StringUtils.trim(rs.getString(1)));
			}
			
			return electronicResource;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the electronic resource from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
	}
	
	public ElectronicResource getElectronicResourceForHoldingId(String holdingId) throws ILSException {
		
		PreparedStatement pstmt = null;
		ElectronicResource electronicResource = null;
		try {
			String sql = "SELECT eitem.link FROM eitem WHERE eitem.mfhd_id = ?";
							
			pstmt = conn.prepareStatement(sql);
		
			pstmt.setString(1, holdingId);
			ResultSet rs = pstmt.executeQuery();    
		
			while(rs.next()){    
				electronicResource = new ElectronicResource();
				electronicResource.setReferenceToResource(StringUtils.trim(rs.getString(1)));
			}
			
			return electronicResource;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the electronic resource from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
	}
	
	public Location getPermanentLocation(String itemId) throws ILSException {
	
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("select location.location_display_name from item, location where item.perm_location = location.location_id and item.item_id = ?");
			pstmt.setString(1, itemId);
			ResultSet rs = pstmt.executeQuery();    
			
			Location location = new Location();
			while(rs.next()){    
				LocationNameInstance locationNameInstance = new LocationNameInstance();

				locationNameInstance.setLocationNameValue(StringUtils.trim(rs.getString(1)));
				//TODO: more to come from requirement for level
				locationNameInstance.setLocationNameLevel(new BigDecimal("1"));//temperarily set to 1.
				

				List<LocationNameInstance> locationNameInstances = new ArrayList<LocationNameInstance>();
				locationNameInstances.add(locationNameInstance);

				LocationName locationName = new LocationName();
				locationName.setLocationNameInstances(locationNameInstances);

				location = new Location();
				location.setLocationName(locationName);
				location.setLocationType(new SchemeValuePair(VoyagerConstants.LOCATION_TYPE_PERMANENT));
			}
			
		
			return location;
			
		} catch(SQLException e)	{
			log.error("An error occurred while getting the permanent location from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
	                "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
	}

	public Map<String, List<Location>> getTemporaryLocationForItemIds(List<String> itemIds) throws ILSException {
		
		PreparedStatement pstmt = null;
		try {
			String sql = "select location.location_display_name, item.item_id from item, location where item.temp_location = location.location_id and item.item_id in (";
			
			for (int i = 0; i < itemIds.size(); i++) {
				sql = sql + "?";
				if (i != itemIds.size() -1) {
					sql = sql + ",";
				}
			}
						 
			sql = sql + ")";
			
			pstmt = conn.prepareStatement(sql);
			
			
			for (int i = 1; i <= itemIds.size(); i++) {
				pstmt.setString(i, itemIds.get(i - 1));
			}		
			ResultSet rs = pstmt.executeQuery();
			
			Location location = null;
			Map<String, List<Location>> tempLocations = new HashMap<String, List<Location>>();
			while(rs.next()){    
				LocationNameInstance locationNameInstance = new LocationNameInstance();

				locationNameInstance.setLocationNameValue(StringUtils.trim(rs.getString(1)));
				locationNameInstance.setLocationNameLevel(new BigDecimal("1"));

				List<LocationNameInstance> locationNameInstances = new ArrayList<LocationNameInstance>();
				locationNameInstances.add(locationNameInstance);

				LocationName locationName = new LocationName();
				locationName.setLocationNameInstances(locationNameInstances);

				location = new Location();
				location.setLocationName(locationName);
				location.setLocationType(new SchemeValuePair(VoyagerConstants.LOCATION_TYPE_TEMPORARY));
				List<Location> locations = new ArrayList<Location>();
				locations.add(location);
				tempLocations.put(rs.getString(2), locations);
			}
		
			return tempLocations;
			
		} catch(SQLException e)	{
			log.error("An error occurred while getting the temporary location from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
	                "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
	}
	
	public Location getTemporaryLocation(String itemId) throws ILSException {
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("select location.location_display_name from item, location where item.temp_location = location.location_id and item.item_id = ?");
			pstmt.setString(1, itemId);
			ResultSet rs = pstmt.executeQuery();    
			
			Location location = null;
			while(rs.next()){    
				LocationNameInstance locationNameInstance = new LocationNameInstance();

				locationNameInstance.setLocationNameValue(StringUtils.trim(rs.getString(1)));
				locationNameInstance.setLocationNameLevel(new BigDecimal("1"));//temperarily set to 1.
				//TODO: more to come from requirement for level

				List<LocationNameInstance> locationNameInstances = new ArrayList<LocationNameInstance>();
				locationNameInstances.add(locationNameInstance);

				LocationName locationName = new LocationName();
				locationName.setLocationNameInstances(locationNameInstances);

				location = new Location();
				location.setLocationName(locationName);
				location.setLocationType(new SchemeValuePair(VoyagerConstants.LOCATION_TYPE_TEMPORARY));

			}
		
			return location;
			
		} catch(SQLException e)	{
			log.error("An error occurred while getting the temporary location from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
	                "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
	}

	public Location getPermanentLocationForHoldingId(String holdingId) throws ILSException {
		java.util.Date start = new java.util.Date();
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("select location.location_display_name from mfhd_master , location where mfhd_master.location_id = location.location_id and mfhd_master.mfhd_id = ?");
			pstmt.setString(1, holdingId);
			ResultSet rs = pstmt.executeQuery();    
			
			Location location = new Location();
			while(rs.next()){    
				LocationNameInstance locationNameInstance = new LocationNameInstance();

				locationNameInstance.setLocationNameValue(StringUtils.trim(rs.getString(1)));
				//TODO: more to come from requirement for level
				locationNameInstance.setLocationNameLevel(new BigDecimal("1"));//temperarily set to 1.
				

				List<LocationNameInstance> locationNameInstances = new ArrayList<LocationNameInstance>();
				locationNameInstances.add(locationNameInstance);

				LocationName locationName = new LocationName();
				locationName.setLocationNameInstances(locationNameInstances);

				location = new Location();
				location.setLocationName(locationName);
				location.setLocationType(new SchemeValuePair(VoyagerConstants.LOCATION_TYPE_PERMANENT));
			}
			
			java.util.Date end = new java.util.Date();
			log.debug("getPermanentLocationForHoldingId time: " + (end.getTime() - start.getTime()) + "  " + ((end.getTime() - start.getTime())/1000) + " sec");
			return location;
			
		} catch(SQLException e)	{
			log.error("An error occurred while getting the permanent location from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
	                "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
	}

	public 	ItemDescription getItemDescription(String itemId) throws ILSException {
		
		ItemDescription itemDescription = new ItemDescription();
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmtCopyNumber = null;
		try {
		
			//just to opimize, so the call number is fetched with other info in this sql, logically it should go a sperate query, like these 2:
			//SELECT mfhd_master.display_call_no FROM mfhd_master, mfhd_item WHERE mfhd_master.mfhd_id = mfhd_item.mfhd_id AND mfhd_item.item_id = ?
			//SELECT mfhd_item.item_enum, mfhd_item.chron, mfhd_item.year FROM mfhd_item WHERE  mfhd_item.item_id = ?"
			pstmt = conn.prepareStatement("SELECT mfhd_item.item_enum, mfhd_item.chron, mfhd_item.year ,mfhd_master.display_call_no FROM mfhd_item,  mfhd_master WHERE mfhd_master.mfhd_id = mfhd_item.mfhd_id AND mfhd_item.item_id = ?");
			pstmt.setString(1, itemId);
			ResultSet rs = pstmt.executeQuery();    
		
			if(rs.next()){    
				String resultString = "";
					    	
		    	if (rs.getString(1) != null && rs.getString(1).trim().length() > 0) {
			    	   resultString = StringUtils.trim(rs.getString(1));
		    	}
		    	if (rs.getString(2) != null && rs.getString(2).trim().length() > 0) {
			    	   resultString = resultString + " " + StringUtils.trim(rs.getString(2));
		    	}
		    	if (rs.getString(3) != null && rs.getString(3).trim().length() > 0) {
			    	   resultString = resultString + " " + StringUtils.trim(rs.getString(3));
		    	}
		
		    	log.debug("Unstructured holdings data : " + resultString);
		    	
				if(resultString != null && resultString.trim().length()>0) {
					HoldingsInformation holdingsInformation = new HoldingsInformation();
			    	holdingsInformation.setUnstructuredHoldingsData(resultString);
					itemDescription.setHoldingsInformation(holdingsInformation);
				}
		
				itemDescription.setCallNumber(StringUtils.trim(rs.getString(4)));
			}
			
			pstmtCopyNumber = conn.prepareStatement("SELECT item.copy_number FROM item WHERE item.item_id = ?");
			
			pstmtCopyNumber.setString(1, itemId);
			ResultSet rsCopyNumber = pstmtCopyNumber.executeQuery();    
			 
	
			while(rsCopyNumber.next()){    
				itemDescription.setCopyNumber(StringUtils.trim(rs.getString(1)));
			}
				
			return itemDescription;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the item description from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
	                "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
					pstmtCopyNumber.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}

	}

	public 	Map<String, ItemDescription> getItemDescriptionForItemIds(List<String> itemIds) throws ILSException {
		
		Map<String, ItemDescription> itemDescriptions = new HashMap<String, ItemDescription>();
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmtCopyNumber = null;
		try {
		
			String sql = "SELECT mfhd_item.item_enum, mfhd_item.chron, mfhd_item.year , mfhd_item.item_id FROM mfhd_item WHERE mfhd_item.item_id in (";
			
			
			for (int i = 0; i < itemIds.size(); i++) {
				sql = sql + "?";
				if (i != itemIds.size() -1) {
					sql = sql + ",";
				}
			}
						 
			sql = sql + ")";
			pstmt = conn.prepareStatement(sql);
	
			for (int i = 1; i <= itemIds.size(); i++) {
				pstmt.setString(i, itemIds.get(i - 1));
			}		
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()){
				ItemDescription itemDescription = new ItemDescription();
				String resultString = "";
					    	
		    	if (rs.getString(1) != null && rs.getString(1).trim().length() > 0) {
			    	   resultString = StringUtils.trim(rs.getString(1));
		    	}
		    	if (rs.getString(2) != null && rs.getString(2).trim().length() > 0) {
			    	   resultString = resultString + " " + StringUtils.trim(rs.getString(2));
		    	}
		    	if (rs.getString(3) != null && rs.getString(3).trim().length() > 0) {
			    	   resultString = resultString + " " + StringUtils.trim(rs.getString(3));
		    	}
		
		    	log.debug("Unstructured holdings data : " + resultString);
		    	
				if(resultString != null && resultString.trim().length()>0) {
					HoldingsInformation holdingsInformation = new HoldingsInformation();
			    	holdingsInformation.setUnstructuredHoldingsData(resultString);
					itemDescription.setHoldingsInformation(holdingsInformation);
				}
		
				itemDescriptions.put(rs.getString(4), itemDescription);
			}
			
			log.debug("itemDescriptions : " + itemDescriptions);
			sql = "SELECT item.item_id, item.copy_number FROM item WHERE item.item_id in (";
			
			for (int i = 0; i < itemIds.size(); i++) {
				sql = sql + "?";
				if (i != itemIds.size() -1) {
					sql = sql + ",";
				}
			}
						 
			sql = sql + ")";
			pstmtCopyNumber = conn.prepareStatement(sql);
	
			for (int i = 1; i <= itemIds.size(); i++) {
				pstmtCopyNumber.setString(i, itemIds.get(i - 1));
			}		
			ResultSet rsCopyNumber = pstmtCopyNumber.executeQuery();    
			 
	
			while(rsCopyNumber.next()){    
				String copyNumber = StringUtils.trim(rsCopyNumber.getString(2));
				ItemDescription idesc = itemDescriptions.get(rsCopyNumber.getString(1));
				if (idesc == null && copyNumber != null) {
					idesc = new ItemDescription();
					idesc.setCopyNumber(copyNumber);
					itemDescriptions.put(rsCopyNumber.getString(1), idesc);
				} else if (idesc != null && copyNumber != null) {
					idesc.setCopyNumber(copyNumber);
					itemDescriptions.put(rsCopyNumber.getString(1), idesc);
				}
				
				
			}
			
			log.debug("itemDescriptions with copy number : " + itemDescriptions);
				
			return itemDescriptions;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the item description from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
	                "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
					pstmtCopyNumber.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}

	}

	public 	String getCallNumberForHoldingId(String holdingId) throws ILSException {
		
		PreparedStatement pstmt = null;
		String callNumber = null;
		try {
		
			String sql = "SELECT mfhd_master.display_call_no FROM mfhd_master WHERE mfhd_master.mfhd_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, holdingId);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()){
				callNumber = rs.getString(1);
			}
			
			return callNumber;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the call number from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
	                "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}

	}

	/**
	 * Returns holding Ids for a bibliographic Id
	 * 
	 * @param bibId
	 * @return
	 * @throws ILSException
	 */
	public 	List<String> getHoldingIdsForBibId(String bibId) throws ILSException {
		java.util.Date start = new java.util.Date();
		PreparedStatement pstmt = null;
		try {
		
			pstmt = conn.prepareStatement("SELECT " + "bib_mfhd.mfhd_id " +
							             "FROM " +  "bib_mfhd " +
							             "WHERE " +  "bib_mfhd.bib_id = ?");
			pstmt.setString(1, bibId);
			ResultSet rs = pstmt.executeQuery();    

			List<String> holdingIds = new ArrayList<String>();
			while (rs.next()){    
				holdingIds.add(rs.getString(1));
			}
			java.util.Date end = new java.util.Date();
			log.debug("getHoldingIdsForBibId time: " + (end.getTime() - start.getTime())  + "  " + ((end.getTime() - start.getTime())/1000) + " sec");
			return holdingIds;
		} catch(SQLException e)	{
			log.error("An error occurred while getting holding Ids from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
	                "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}

	}
	
	/**
	 * Returns item Ids for a holding Id
	 * 
	 * @param holdingId
	 * @return
	 * @throws ILSException
	 */
	public 	List<String> getItemIdsForHoldingId(String holdingId) throws ILSException {
		java.util.Date start = new java.util.Date();
		
		PreparedStatement pstmt = null;
		try {
		
			pstmt = conn.prepareStatement("SELECT " + "mfhd_item.item_id " +
		             "FROM " + "mfhd_item " +
		             "WHERE " + "mfhd_item.mfhd_id = ?");
			pstmt.setString(1, holdingId);
			ResultSet rs = pstmt.executeQuery();    

			List<String> itemIds = new ArrayList<String>();
			while (rs.next()){    
				itemIds.add(rs.getString(1));
			}
			java.util.Date end = new java.util.Date();
			log.debug("getItemIdsForHoldingId time: " + (end.getTime() - start.getTime())  + "  " + ((end.getTime() - start.getTime())/1000) + " sec");
			return itemIds;
		} catch(SQLException e)	{
			log.error("An error occurred while getting item Ids from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
	                "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}

	}

	public Map<String, GregorianCalendar> getDueDateForItemIds(List<String> itemIds) throws ILSException {
		
		PreparedStatement pstmt = null;
		Map<String, GregorianCalendar> dueDates = new HashMap<String, GregorianCalendar>();
		try {
			String sql = "select item_id, current_due_date from circ_transactions where item_id in (";
			 
			for (int i = 0; i < itemIds.size(); i++) {
				sql = sql + "?";
				if (i != itemIds.size() -1) {
					sql = sql + ",";
				}
			}
						 
			sql = sql + ")";
			pstmt = conn.prepareStatement(sql);
	
			for (int i = 1; i <= itemIds.size(); i++) {
				pstmt.setString(i, itemIds.get(i - 1));
			}		
			ResultSet rs = pstmt.executeQuery();    

			while (rs.next()) {
				Timestamp dueDate = rs.getTimestamp(2);
				if (dueDate != null) {
					dueDates.put(StringUtils.trim(rs.getString(1)), VoyagerUtil.convertDateToGregorianCalendar(dueDate));
				} 
			}
			return dueDates;
		} catch(SQLException e)	{
			log.error("An error occurred while getting the due date from the database.", e);
	        
			throw new ILSException("An error occurred while interacting with the Voyager database.  This may have been " +  
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", e);
		} finally {
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				}
				catch(SQLException e)
				{
					throw new ILSException("An error occurred while interacting with the Voyager database.", e);				
				}
			}
		}
		
	}

}

