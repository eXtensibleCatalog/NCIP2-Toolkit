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


public class ILSConfiguration {

    /* The instance of the NCIP configuration */
    private static ILSConfiguration instance = null;

    /* The instance of the configuration */
    private static Configuration configuration = null;

    /** Name of category */
    private static String urlPath;

    /** File separator according to OS. \ for windows / for unix. */
    public static final String FILE_SEPARATOR = System
	    .getProperty("file.separator");

    /** Object used to read properties from the default configuration file */
    protected static final Configuration defaultConfiguration = ConfigurationManager
	    .getConfiguration();

    /** Indicates whether NCIP instance folder exist */
    public static boolean NCIPInstanceFolderExist = false;

    /** Name of NCIP instance */
    public static String instanceName;

    /** Indicates whether instance folder for this instance exist */
    public static boolean currentInstanceFolderExist = false;

    /**
     * Gets the singleton instance
     */
    public static ILSConfiguration getInstance(String path, String fileName) {
	if (instance != null) {
	    return instance;
	}
	
	createConfiguration(path+fileName);
	instance = new ILSConfiguration();
	return instance;
    }

    /*
     * Creates and initializes configuration for NCIP
     */
    private static void createConfiguration(String urlPath) {

		ILSConfiguration.urlPath = urlPath;

		File file = new File(urlPath);

		XMLFileHandler handler = new XMLFileHandler();
		handler.setFile(file);

		ConfigurationManager configurationManager = ConfigurationManager.getInstance();
		try {
		    configurationManager.load(handler, "myConfig");
			configuration = ConfigurationManager.getConfiguration("myConfig");
		} catch (ConfigurationManagerException cme) {
		}

	}
    
    /**
     * Get value of given property
     * 
     * @param name
     *            name of property
     * @return value of property
     */
    public static String getProperty(String name) {
	return configuration.getProperty(name);
    }
    
}
