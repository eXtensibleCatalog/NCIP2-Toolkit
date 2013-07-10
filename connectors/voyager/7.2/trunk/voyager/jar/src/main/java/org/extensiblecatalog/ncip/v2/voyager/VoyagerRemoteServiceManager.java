package org.extensiblecatalog.ncip.v2.voyager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.ConnectorConfigurationFactory;
import org.extensiblecatalog.ncip.v2.service.AuthenticationInput;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.voyager.util.ILSException;
import org.extensiblecatalog.ncip.v2.voyager.util.LDAPUtils;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConfiguration;
import org.extensiblecatalog.ncip.v2.voyager.util.VoyagerConstants;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;


/**
 * VoyagerRemoteServiceManager is the implementation of the back-end service
 *
 * @author SharmilaR
 *
 */
public class VoyagerRemoteServiceManager implements  RemoteServiceManager {

    /** Logger */
    static Logger log = Logger.getLogger(VoyagerRemoteServiceManager.class);
    HttpClient client;

    private VoyagerConfiguration voyagerConfig;
    {
        try {
            voyagerConfig = (VoyagerConfiguration) new ConnectorConfigurationFactory(new Properties()).getConfiguration();
            //voyagerConfig = (VoyagerConfiguration)ConnectorConfigurationFactory.getConfiguration();
        } catch (ToolkitException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public VoyagerRemoteServiceManager(Properties properties) {
        client = new HttpClient(new MultiThreadedHttpConnectionManager());
    }

    /**
     * Sets up and returns a connection to the voyager database whose location and
     * driver are defined in the configuration file.
     */
    public Connection openReadDbConnection(String patronAgencyId)
    {
        Connection conn;
        String url;
        String username;
        String password;
        log.debug("Entering openReadDbConnection() with patronAgencyId: " + patronAgencyId);
        if (patronAgencyId.equalsIgnoreCase(voyagerConfig.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY))){
            url = voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_DB_URL);
            username = voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_DB_READ_ONLY_USERNAME);
            password = voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_DB_READ_ONLY_PASSWORD);
        } else {
            url = voyagerConfig.getProperty(patronAgencyId + "database");
            username = voyagerConfig.getProperty(patronAgencyId + "username");
            password = voyagerConfig.getProperty(patronAgencyId + "password");
        }
        log.debug("Using url: " + url + " username: " + username + " and password: " + password);
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, username, password);
            log.debug("Voyager DB read connection = " + conn);
        } catch (ClassNotFoundException ce) {
            log.error("An error occurred loading the jdbc driver.", ce);
            return null;
        } catch (SQLException se) {
            log.error("An error occurred while interacting with the Voyager database.  This may have been " +
                    "caused if the VoyagerDatabaseName was set incorrectly in the NCIP Toolkit configuration file.", se);
            return null;
        }
        return conn;
    }

    public String getBibIdForItemId(String itemId, String patronAgencyId) {

        String bibId = "";
        long startTime = System.nanoTime();

        String url;
        String username;
        String password;
        if (patronAgencyId.equalsIgnoreCase(voyagerConfig.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY))){
            url = voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_DB_URL);
            username = voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_DB_READ_ONLY_USERNAME);
            password = voyagerConfig.getProperty(VoyagerConstants.CONFIG_VOYAGER_DB_READ_ONLY_PASSWORD);
        } else {
            url = voyagerConfig.getProperty(patronAgencyId + "database");
            username = voyagerConfig.getProperty(patronAgencyId + "username");
            password = voyagerConfig.getProperty(patronAgencyId + "password");
        }

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ce) {
            log.error("An error occurred loading the jdbc driver.", ce);
            return null;
        }

        String sql;
        if (patronAgencyId.equalsIgnoreCase(voyagerConfig.getProperty(VoyagerConstants.CONFIG_ILS_DEFAULT_AGENCY))) {
            sql = "SELECT bib_id FROM bib_item WHERE bib_item.item_id = ?";
        }
        else {
            String dbuser = voyagerConfig.getProperty(patronAgencyId + "dbuser");
            sql = "SELECT bib_id FROM " + dbuser + ".bib_item WHERE bib_item.item_id = ?";
        }

        // Use java 7's new try-with-resources to get db connection and prepared statement
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1,itemId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                bibId = StringUtils.trim(rs.getString(1));
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch(SQLException e) {
                    log.error("An error occurred closing the ResultSet.");
                }
            }
        } catch(SQLException e) {
            log.error("An SQL Exception error occurred while getting the bibliographic Id from the database.", e);
            return null;
        }
        log.info("Returning bib Id: " + bibId);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        log.info("Time in getBibForItemId: " + duration/1000000000 + " seconds.");
        return bibId;
    }

    /**
     * Authenticate user
     *
     * @param inputs The response from initData's getAuthenticationInputs method
     * @return The authenticated user ID
     */
    public String authenticateUser(List<AuthenticationInput> inputs, String host) {

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
             authenticatedUserId = authenticateUser(username, password, host);
         }
         if (ldapUsername != null && ldapPassword != null) {
             authenticatedUserId = authenticateLDAPUser(ldapUsername, ldapPassword, host);
         }
         return authenticatedUserId;
     }

    /**
     *  Authenticates User before processing the request
     *
     * @param username
     * @param password
     * @return The authenticated user id
     */
    public String authenticateUser(String username, String password, String host) {

        log.debug("Entering authenticateUser for user: " + username + ".");

        // The institution ID returned by the web service assuming username was an institution ID
        String institutionId = getPatronIdFromInstitutionIdAuthData(username, password.toLowerCase(), host);

        if (institutionId != null) {
            log.info("Found institution ID " + institutionId + " from vxws with institution id");
            return institutionId;
        }
        // The web service did not authenticate the user, try again assuming barcode data
        else {
            institutionId = getPatronIdFromBarcodeAuthData(username, password.toLowerCase(), host);
        }

        return institutionId;
    }

    /**
     * Authenticates user against LDAP server
     *
     * @param username User name of user
     * @param password Password
     * @return
     */
    public String authenticateLDAPUser(String username, String password, String ubPrefix) {
        if (log.isDebugEnabled())
            log.debug("Entering authenticateUser for LDAP user: " + username + ".");

        // The connection to the LDAP server
        DirContext ldapConnection = null;
        LDAPUtils ldapUtils = new LDAPUtils();

        try {
            // Get a connection to the LDAP server
            ldapConnection = ldapUtils.getLDAPConnection(username, password);

            // Get the username attribute and start location on the LDAP server
            // from the configuration file
            String usernameAttribute = voyagerConfig.getProperty(VoyagerConstants.CONFIG_EXTERNAL_LDAP_USERNAME_ATTRIBUTE);
            String startLocation = voyagerConfig.getProperty(VoyagerConstants.CONFIG_EXTERNAL_LDAP_START);

            // Get the attributes associated with the user
            Attributes attributes = ldapConnection
                .getAttributes(usernameAttribute + "=" + username + ", "
                    + startLocation);

            // Get the attribute containing the patron key which Voyager needs
            String urid = (String) attributes.get(voyagerConfig.getProperty(VoyagerConstants.CONFIG_EXTERNAL_LDAP_UR_ID)).get();

            // Get the attribute containing the last name which Voyager needs
            String lastName = (String)  attributes.get(voyagerConfig.getProperty(VoyagerConstants.CONFIG_EXTERNAL_LDAP_LAST_NAME)).get();

            log.info("Retrieved urid: " + urid + " and last name: " + lastName);

            // Authenticate against voyager db
            return getPatronIdFromInstitutionIdAuthData(urid, lastName, ubPrefix);

        } catch (NamingException e) {
            log.error("An error occurred while getting the users patron key.", e);
        } finally {
            // If we set up a connection to the LDAP server, close it
            if (ldapConnection != null) {
                try {
                    if (log.isDebugEnabled())
                    log.debug("Closing the LDAP connection.");
                    ldapConnection.close();
                } catch (NamingException e) {
                    log.warn("An error occurred while closing the connection to the LDAP server defined in the "
                                + "configuration file.", e);
                }
            }
        }
        return null;
    }

    /**
     * Given a user's authentication data, return their patron ID.  If their
     * authentication information was not valid, return null instead
     *
     * @param username The user's barcode
     * @param password The user's password
     * @return The user's patron ID if the username and password were correct, or null otherwise
     */
    public String getPatronIdFromBarcodeAuthData(String username, String password, String host)
    {
        log.info("Retrieving patron Id using barcode auth data.");

        // Construct the xml to send to the web service
        // Using B for barcode id
        Namespace serNs = Namespace.getNamespace("ser", "http://www.endinfosys.com/Voyager/serviceParameters");
        Document authUserXML = new Document();

        Element root = new Element("serviceParameters", serNs);
        authUserXML.addContent(root);
        Element parametersElement = new Element("parameters", serNs);
        root.addContent(parametersElement);
        Element patronElement = new Element("patronIdentifier", serNs);
        patronElement.setAttribute("lastName", password);
        root.addContent(patronElement);
        Element authElement = new Element("authFactor", serNs);
        authElement.setAttribute("type", "B");
        authElement.setText(username);
        patronElement.addContent(authElement);

        XMLOutputter xmlOutP = new XMLOutputter();

        String url = host + "/vxws/AuthenticatePatronService";

        Document doc = postWebServicesDoc(url, xmlOutP.outputString(authUserXML));

        try {
            Namespace patNs = Namespace.getNamespace("pat", "http://www.endinfosys.com/Voyager/patronAuthentication");
            // If there is a serviceData element then the user was authenticated.  Otherwise an error response was returned
            if (doc.getRootElement().getChild("serviceData", serNs).getChild("patronIdentifier", patNs) != null) {
                String patronId = doc.getRootElement().getChild("serviceData", serNs).getChild("patronIdentifier", patNs).getAttributeValue("patronId");
                return patronId;
            }
            else
                return null;
        } catch (NullPointerException e) {
            log.debug("User not found with barcode date");
            return null;
        }
    }

    /**
     * Given a user's authentication data, return their patron ID.  If their
     * authentication information was not valid, return null instead
     *
     * @param username The user's username
     * @param password The user's password
     * @param host The hostname of the vxws server
     * @return The user's patron ID if the username and password were correct, or null otherwise
     */
    public String getPatronIdFromInstitutionIdAuthData(String username, String password, String host)
    {
        log.info("Retrieving patron id from institution auth data");

        // Construct the xml to send to the web service
        // Using I for institution id
        Namespace serNs = Namespace.getNamespace("ser", "http://www.endinfosys.com/Voyager/serviceParameters");
        Document authUserXML = new Document();

        Element root = new Element("serviceParameters", serNs);
        authUserXML.addContent(root);
        Element parametersElement = new Element("parameters", serNs);
        root.addContent(parametersElement);
        Element patronElement = new Element("patronIdentifier", serNs);
        patronElement.setAttribute("lastName", password.toLowerCase());
        root.addContent(patronElement);
        Element authElement = new Element("authFactor", serNs);
        authElement.setAttribute("type", "I");
        authElement.setText(username);
        patronElement.addContent(authElement);

        XMLOutputter xmlOutP = new XMLOutputter();

        String url = host + "/vxws/AuthenticatePatronService";

        Document doc = postWebServicesDoc(url, xmlOutP.outputString(authUserXML));

        Namespace patNs = Namespace.getNamespace("pat", "http://www.endinfosys.com/Voyager/patronAuthentication");
        // If there is a serviceData element then the user was authenticated.  Otherwise an error response was returned
        try {
            if (doc.getRootElement().getChild("serviceData", serNs).getChild("patronIdentifier", patNs) != null) {
                String patronId = doc.getRootElement().getChild("serviceData", serNs).getChild("patronIdentifier", patNs).getAttributeValue("patronId");
                return patronId;
            }
            else {
                return null;
            }
        }  catch (NullPointerException npe) {
            log.debug("Did not find patron with I data");
            return null;
        }
    }

    /**
     * Given a partial URL to a vxws service, constructs the entire URL and sends
     * the given XML to the service, returning the response Document
     *
     * @param url url to put the inputXml to
     * @param inputXml Xml to PUT in the HTTP request
     * @return XML Document from the vxws response
     */
    public Document putWebServicesDoc(String url, String inputXml) {

        int statusCode;
        PutMethod putMethod = null;
        InputStream response = null;

        try {
            synchronized(client) {
                putMethod = new PutMethod(url);
                putMethod.setRequestEntity(new StringRequestEntity(inputXml));
                statusCode = client.executeMethod(putMethod);
            }
            if (statusCode == 200) {
                response = putMethod.getResponseBodyAsStream();
            } else {
                log.error("Could not contact the vxws service.  Received HTTP status code: " + statusCode);
            }
        }  catch (IOException e) {
            log.error("IOException caught while contacting the vxws service.  An internal error occurred in the NCIP Toolkit.", e);
        }

        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            // Build a JDOM Document from the response
            doc = builder.build(response);
        } catch (JDOMException e) {
            log.error("JDOMException parsing xml response");
        } catch (IOException e) {
            log.error("IOException parsing xml response");
        } finally {
            // Release the connection.
            putMethod.releaseConnection();
        }
        return doc;
    }

    public String getUrlFromAgencyId(String agencyId) {
        return voyagerConfig.getProperty(agencyId + "vxwsUrl");
    }

    /**
     * Given a URL to a vxws service, issue an HTTP GET request to that URL
     * @param url url to GET
     * @return XML Document from the vxws response
     */
    public Document getWebServicesDoc(String url) {

        int statusCode;
        GetMethod getMethod;
        log.debug("getWebServicesDoc using URL " + url);
        InputStream response;

        try {
            synchronized(client) {
                getMethod = new GetMethod(url);
                statusCode = client.executeMethod(getMethod);
            }

            if (statusCode == 200) {
                response = getMethod.getResponseBodyAsStream();
            } else {
                log.error("Could not contact the vxws service.  Received HTTP status code: " + statusCode);
                return null;
            }
        } catch (IOException e) {
            log.error("IOException caught while contacting the vxws service.  An internal error occurred in the NCIP Toolkit.", e);
            return null;
        }

        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            // Build a JDOM Document from the response
            doc = builder.build(response);
        } catch (JDOMException e) {
            log.error("JDOMException parsing xml response");
            return null;
        } catch (IOException e) {
            log.error("IOException parsing xml response");
            return null;
        } finally {
            // Release the connection.
            getMethod.releaseConnection();
        }

        return doc;
    }

    /**
     * Given a URL to a vxws service, POST
     * the given XML to the service, returning the response Document
     *
     * @param url partial URL to the particular vxws service
     * @return XML Document from the vxws response
     */
    public Document postWebServicesDoc(String url) {

        int statusCode;
        PostMethod postMethod = null;

        InputStream response = null;

        try {
            synchronized(client) {
                postMethod = new PostMethod(url);
                statusCode = client.executeMethod(postMethod);
            }
            if (statusCode == 200) {
                response = postMethod.getResponseBodyAsStream();
            } else {
                log.error("Cound not contact the vxws service with URL: " + url + ".  Recieved HTTP status code: " + statusCode);
            }
        }  catch (IOException e) {
            log.error("IOException caught while contacting the vxws service.  An internal error occurred in the NCIP Toolkit.", e);
        }

        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            // Build a JDOM Document from the response
            doc = builder.build(response);
        } catch (JDOMException e) {
            log.error("JDOMException parsing xml response");
        } catch (IOException e) {
            log.error("IOException parsing xml response");
        } finally {
            // Release the connection.
            postMethod.releaseConnection();
        }

        return doc;
    }

    /**
     * Given a URL to a vxws service, POSTS
     * the given XML to the service, returning the response Document
     *
     * @param url partial URL to the particular vxws service
     * @param inputXml The input XML as a string to post
     * @return XML Document from the vxws response
     */
    public Document postWebServicesDoc(String url, String inputXml) {

        int statusCode;
        PostMethod postMethod = null;

        InputStream response = null;

        try {
            synchronized(client) {
                postMethod = new PostMethod(url);
                postMethod.setRequestEntity(new StringRequestEntity(inputXml));
                statusCode = client.executeMethod(postMethod);
            }
            if (statusCode == 200) {
                response = postMethod.getResponseBodyAsStream();
            } else {
                log.error("Could not contact the vxws service with URL: " + url + ".  Received HTTP status code: " + statusCode);
            }
        }  catch (IOException e) {
            log.error("IOException caught while contacting the vxws service.  An internal error occurred in the NCIP Toolkit.", e);
        }

        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            // Build a JDOM Document from the response
            doc = builder.build(response);
        } catch (JDOMException e) {
            log.error("JDOMException parsing xml response");
        } catch (IOException e) {
            log.error("IOException parsing xml response");
        } finally {
            // Release the connection.
            postMethod.releaseConnection();
        }

        return doc;
    }

    public GregorianCalendar stringDateToGC (String date) {
        String[] dateComponents = date.substring(0, 10).split("-");
        String[] timeComponents = date.substring(11).split(":");

        GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                Integer.parseInt(dateComponents[1]) - 1, //GC months start at 0
                Integer.parseInt(dateComponents[2]),
                Integer.parseInt(timeComponents[0]),
                Integer.parseInt(timeComponents[1]));

        return gc;
    }
}

