/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/.
  *
  */

package org.extensiblecatalog.ncip.v2.common.util;

import java.io.File;

import org.jconfig.Configuration;
import org.jconfig.ConfigurationManager;
import org.jconfig.ConfigurationManagerException;
import org.jconfig.handler.XMLFileHandler;

import org.extensiblecatalog.ncip.v2.common.util.Constants;

/**
 * Configuration for NCIPToolkit 
 * 
 * It implements the Singleton Pattern to ensure everywhere who needs the configuration file 
 * have to come to this class to get it, to protect competetive access to the config file.
 *
 * @Author Sharmila Ranganathan
 * Adapted from MSTConfiguration
 */
public class NCIPConfiguration {
	
    /*  The instance of the NCIP configuration        */
    private static NCIPConfiguration instance = null;
    
    /* The instance of the configuration */
    private static Configuration configuration = null;
    
    /** path to the configuration file */
    private static String urlPath;
    
    /** File separator according to OS. \ for windows  / for unix. */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
       
    /**  Object used to read properties from the default configuration file */
    protected static final Configuration defaultConfiguration = ConfigurationManager.getConfiguration();
    
    /**  Indicates whether NCIP instance folder exist */
    public static boolean NCIPInstanceFolderExist = false;

    /**  Name of NCIP instance */
    public static String instanceName;
    
    /**  Indicates whether instance folder for this instance exist */
    public static boolean currentInstanceFolderExist = false;

    private static String path;

    /** Default constructor */
    private NCIPConfiguration() {}
    
    /**
     * Gets the singleton instance
     */
    public static NCIPConfiguration getInstance(String path, String fileName)
    {
            if(instance != null) {
                    return instance;
            }

            NCIPConfiguration.path = path;

            createConfiguration(path+fileName);
            instance = new NCIPConfiguration();
            return instance;
    }
    
    /*
     * Creates and initializes configuration for NCIP
     */
    private static void createConfiguration(String urlPath) {
            
        instanceName = urlPath;
                      
        NCIPConfiguration.urlPath = urlPath;

        File file = new File(urlPath);
        
	    XMLFileHandler handler = new XMLFileHandler();
	    handler.setFile(file);

	    ConfigurationManager configurationManager =	ConfigurationManager.getInstance();

	    try {
		    configurationManager.load(handler, "myConfig");
            configuration =  ConfigurationManager.getConfiguration("myConfig");
		} catch (ConfigurationManagerException cme) {  
			System.out.println("Got an ConfigurationManagerException: " + cme.getMessage());
		}
    }
    
    /**
     * Get value of given property
     *  
     * @param name name of property
     * @return value of property
     */
    public static String getProperty(String name) {
            return configuration.getProperty(name);
    }

    /**
     * Get relative path from tomcat working directory to NCIP configuration folder 
     * 
     * @return path to NCIP configuration folder
     */
    public  static String getUrlPath() {    
	return urlPath;
    }

    public static Configuration getConfiguration() {
            return configuration;
    }

    public static void setConfiguration(Configuration configuration) {
            NCIPConfiguration.configuration = configuration;
    }

    public static void setUrlPath(String urlPath) {
            NCIPConfiguration.urlPath = urlPath;
    }

    public static String getNCIPInstancesFolderPath(){
            //return System.getProperty("user.dir") + FILE_SEPARATOR;'
	return urlPath;
    }

    public static String getInstanceName() {
            return instanceName;
    }

    public static void setInstanceName(String instanceName) {
            NCIPConfiguration.instanceName = instanceName;
    }
    
    public static String getPath() {
	return path;
    }
}
