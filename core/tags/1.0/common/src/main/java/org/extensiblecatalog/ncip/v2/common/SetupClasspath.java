/**
  * Copyright (c) 2009 eXtensible Catalog Organization
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/.
  *
  */

package org.extensiblecatalog.ncip.v2.common;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



public class SetupClasspath {

    static Logger log = Logger.getLogger(SetupClasspath.class);

    public static void setupClasspath(String dir) {
        System.setProperty("line.separator", "\n");
        if (dir == null) {
            dir = "ncipv2";
        }
        try {
            String rootDir = null;
            if (System.getenv("NCIP_ROOT_DIR") != null) {
                rootDir = System.getenv("NCIP_ROOT_DIR");
                if (!rootDir.endsWith("/"))
                    rootDir += "/";
            }

            if (rootDir == null) {
                File workingDir = new File(".");
                rootDir = workingDir.getAbsolutePath();
                rootDir += "/";
            }
            NCIPConfiguration.rootDir = rootDir;

            String fileProto = "file:";
            if (!rootDir.startsWith("/")) {
                fileProto = fileProto+"/";
            }
            String url = fileProto+rootDir+"NCIP-instances/"+dir+"/";
            String configUrl = url.replaceAll("\\\\", "/");
            System.out.println("url: "+configUrl);
            addURL(new URL(configUrl));

            // Load driver jar
            String jarUrl = "jar:" + url + "driver/driver.jar!/";

            jarUrl = jarUrl.replaceAll("\\\\", "/");
            addURL(new URL(jarUrl));

             // Load dependent jars inside lib folder
            File folder = new File(rootDir+"NCIP-instances/"+dir+"/"+"driver/lib/");
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles != null) {
                for (int i = 0; i < listOfFiles.length; i++) {
                  if (listOfFiles[i].isFile()) {
                          jarUrl = "jar:" + url +"driver/lib/" + listOfFiles[i].getName() + "!/";
                          addURL(new URL(jarUrl));

                  }
                }
            } else {
                log.debug("No JARs found under path " + (rootDir+"NCIP-instances/"+dir+"/"+"driver/lib/"));
            }
            // Load Log4j
            PropertyConfigurator.configure(rootDir+"NCIP-instances/"+dir+"/log4j.config.txt");

        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }


    @SuppressWarnings("unchecked")
    public static void addURL(URL u) {

        URLClassLoader sysloader = (URLClassLoader)new SetupClasspath().getClass().getClassLoader();
        Class sysclass = URLClassLoader.class;

        try {
            Method method = sysclass.getDeclaredMethod("addURL",  new Class[]{URL.class});
            method.setAccessible(true);
            method.invoke(sysloader, new Object[]{u});
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException("Error, could not add URL to system classloader");
        }

    }
}
