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
import java.io.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.extensiblecatalog.ncip.v2.common.util.Constants;
import org.extensiblecatalog.ncip.v2.common.util.NCIPConfiguration;

import org.apache.log4j.*;

/**
 * Initialize log
 *
 * @authorHua Fan
 *
 */
public class InitializeSpringContext extends HttpServlet {

        /**
         * Initialize logging
         */
	    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

        public void init() {

			//this will locate the log4j configuraton file and get the configuration loaded
			//PropertyConfigurator comes from log4j, it then gets the log configuration file, and loaded it.
			//the logger file location is specified in that(the above loaded) configuration file.
           	//PropertyConfigurator.configure(NCIPConfiguration.getProperty(Constants.CONFIG_LOGGER_CONFIG_FILE_LOCATION));  

			//the dir where jvm starts from, usually the webserver's installation root. absolute path

				String contextDir = "ncipv2";//System.getProperty("user.dir");
				System.out.println("contextDir:" + contextDir);//
				String path = System.getProperty("user.dir") + FILE_SEPARATOR +"webapps" +FILE_SEPARATOR + contextDir + FILE_SEPARATOR;
								System.out.println("path:" + path);//
				String thePath = path + "WEB-INF" + FILE_SEPARATOR + "spring" + FILE_SEPARATOR;
								System.out.println("thepath:" + thePath);//
				



				String currentRecord = null;
				BufferedReader file;
				path = thePath + "ncipv2-core-context.xml";
				try
				{
					file = new BufferedReader(new FileReader(path));
					currentRecord = file.readLine();
				}
				catch (FileNotFoundException e)
				{
					System.out.println("reading file error.");
				}
				catch (IOException e)
				{
					System.out.println("io error.");
				}
				if (currentRecord == null)
					System.out.println("empty file:");//

				//if file empty
				else
				{
					System.out.println("currentRecord:" +currentRecord);//

				}



        }

        public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
              
        }

}