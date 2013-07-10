/**
 * Copyright (c) 2009 University of Rochester
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the
 * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
 * website http://www.extensiblecatalog.org/.
 *
 */

package org.extensiblecatalog.ncip.v2.common.util;

import javax.servlet.http.HttpServlet;
import org.extensiblecatalog.ncip.v2.common.util.Constants;


/**
 * Initialize configuration
 * 
 * @author Hua Fan
 * 
 */
public class InitializeConfiguration extends HttpServlet {

    /** Eclipse generated id, since it has implemented a serializable interface */
    private static final long serialVersionUID = 857672644059513443L;

    /** File separator according to OS. \ for windows / for unix. */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * Initialize configuration
     */
    public void init() {

	String path = System.getenv("NCIPCONFIGURATIONPATH");

	//prepare for the context if environment var is not set.
	String contextDir = Constants.DEFAULT_CONTEXT_NAME;//default name is "ncipv2"

	// If Environment variable is not found, use tomcat home
	if (path == null) {

	    path = System.getProperty("user.dir") + FILE_SEPARATOR + contextDir
		    + FILE_SEPARATOR;
	}
	// If Path ENDS with trailing slash
	else if (path.endsWith(FILE_SEPARATOR)) {
	    path = path + contextDir + FILE_SEPARATOR;
	}
	// If Path ENDS without trailing slash, remove
	else if (!path.endsWith(FILE_SEPARATOR)) {
	    path = path + FILE_SEPARATOR + contextDir + FILE_SEPARATOR;
	}
	
	// Initialize NCIP configuration. this will init the class and read the configuration into the map in memory
	NCIPConfiguration.getInstance(path, "NCIPV2Toolkit_config.xml");
	
	ILSConfiguration.getInstance(path, Constants.ILS_CONFIGURATIONDIR
		+ FILE_SEPARATOR + "driver_config.xml");
    }

}