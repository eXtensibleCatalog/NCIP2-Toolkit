package org.extensiblecatalog.ncip.v2.common;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

/**
 * Configures properties files for NCIP
 * 
 * @author SharmilaR
 *
 */
public class NCIPConfiguration extends PropertyPlaceholderConfigurer implements ApplicationContextAware {


    private static final Logger LOG = Logger.getLogger(NCIPConfiguration.class);

    protected static Properties properties = new Properties();

    protected ApplicationContext applicationContext = null;

    /*  The instance of the NCIP configuration     */
    private static NCIPConfiguration instance = null;

    /** Name of category */
    private static String urlPath;

    public static String rootDir;

    public List<String> schemaURLs;

    /** File separator according to OS. \ for windows  / for unix. */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /**  Indicates whether NCIP instance folder exists */
    public static boolean ncipInstanceFolderExists = false;

    /**  Name of NCIP instance */
    public static String instanceName;

    /**  Indicates whether instance folder for this instance exists */
    public static  boolean currentInstanceFolderExists = false;
    public static final String SCHEMA_URLS = "schemaURLs";

    /** Default constructor */
    public NCIPConfiguration() {
        if (NCIPConfiguration.instance == null) {
            NCIPConfiguration.instance = this;
        }
    }

    /**
     * Gets the singleton instance of the NCIPConfiguration
     */
    public static NCIPConfiguration getInstance()
    {
        return instance;
    }

    protected boolean resolvePlaceholderVisited = false;

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
        String val = super.resolvePlaceholder(placeholder, props, systemPropertiesMode);
        if (!resolvePlaceholderVisited) {
            resolvePlaceholderVisited = true;
            for (Object key : props.keySet()) {
                String keyStr = key.toString();
                String pval = props.getProperty(keyStr);
                properties.put(keyStr, pval);
                LOG.info("key: "+key+" val: "+pval);
            }
            init2();
        }

        // If cannot resolve place holder then initialize to DummyService
        if (val == null || val.trim().equalsIgnoreCase("")) {
            val = "org.extensiblecatalog.ncip.v2.service.DummyService";
        }
        return val;
    }

    /*
     * Creates and initializes configuration for NCIP
     */
    public void init2() {
        if (applicationContext instanceof WebApplicationContext) {
            String urlPath = ((WebApplicationContext)applicationContext).getServletContext().getContextPath();
            // Remove the / in '/ncipv2'
            urlPath = urlPath.substring(1, urlPath.length());
            instanceName = urlPath;
        } else {
            instanceName = "ncipv2";
        }

        File ncipInstances = new File(rootDir+"/"+getProperty(Constants.INSTANCES_FOLDER_NAME));
        if (ncipInstances.exists()) {
            ncipInstanceFolderExists = true;
        }

        File currentInstance = new File(rootDir+"/"+getProperty(Constants.INSTANCES_FOLDER_NAME) +  FILE_SEPARATOR + instanceName);
        if (currentInstance.exists()) {
            currentInstanceFolderExists = true;
        }
        urlPath = rootDir + getProperty(Constants.INSTANCES_FOLDER_NAME) +  FILE_SEPARATOR + instanceName;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Get value of given property
     *  
     * @param name name of property
     * @return value of property
     */
    public static String getProperty(String name) {
        return properties.getProperty(name);
    }

    public static String[] getArray(String name) {
        String property = getProperty(name);
        if (property ==null) return new String[0];
        else return getProperty(name).split(",");
    }

    /**
     * Get relative path from tomcat working directory to NCIP configuration folder 
     * 
     * @return path to NCIP configuration folder
     */
    public static String getUrlPath() {
        return urlPath;
    }

    public static String getInstanceName() {
        return instanceName;
    }

    public void setSchemaURLs(List<String> schemaURLs) {
        this.schemaURLs = schemaURLs;
    }

    public List<String> getSchemaURLs() {
        return schemaURLs;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        NCIPConfiguration.properties = properties;
    }

}
