/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/.
  *
  */

package org.extensiblecatalog.ncip.v2.common.util;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.extensiblecatalog.ncip.v2.common.util.Constants;
import org.extensiblecatalog.ncip.v2.common.util.NCIPConfiguration;

import org.apache.log4j.*;

/**
 * Initialize log
 */
public class InitializeLog extends HttpServlet {

        /**
         * Eclipse generated id
         */
        private static final long serialVersionUID = 6847591197004656298L;

        /**
         * Initialize logging
         */
        public void init() {
			//this will locate the log4j configuraton file and get the configuration loaded
			//PropertyConfigurator comes from log4j, it then gets the log configuration file, and load it.
			//the logger file location is specified in the above loaded configuration file.
           	PropertyConfigurator.configure(NCIPConfiguration.getProperty(Constants.CONFIG_LOGGER_CONFIG_FILE_LOCATION));                            
        }

        public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
              
        }

}