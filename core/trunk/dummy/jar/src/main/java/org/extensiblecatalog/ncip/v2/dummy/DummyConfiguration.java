package org.extensiblecatalog.ncip.v2.dummy;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.DefaultConnectorConfiguration;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.util.Properties;

public class DummyConfiguration extends DefaultConnectorConfiguration {

    private static final Logger LOG = Logger.getLogger(DummyConfiguration.class);

    public final static String DUMMY_LIBRARY_NAME_KEY = "DummyConfiguration.LibraryName";
    public final static String DUMMY_LIBRARY_NAME_DEFAULT = "Dummytown Library";

    public final static String DUMMY_AGENCY_SCHEME_KEY = "DummyConfiguration.AgencyScheme";
    public final static String DUMMY_AGENCY_SCHEME_DEFAULT = "local.edu";

    public final static String DUMMY_AGENCY_VALUE_KEY = "DummyConfiguration.AgencyValue";
    public final static String DUMMY_AGENCY_VALUE_DEFAULT = "Dummytown";

    protected String name;
    protected AgencyId agency;

    public DummyConfiguration() throws ToolkitException {

        this(null, null);

    }


    public DummyConfiguration(String appName) throws ToolkitException {

        this(appName, null);

    }

    public DummyConfiguration(Properties properties) throws ToolkitException {

        this(null, properties);

    }

    public DummyConfiguration(String appName, Properties properties) throws ToolkitException {

        super(appName, properties);

        if ( properties != null ) {

            String nameString = null;
            String agencyValueString = null;
            String agencySchemeString = null;

            nameString = getProperty(DUMMY_LIBRARY_NAME_KEY, DUMMY_LIBRARY_NAME_DEFAULT);
            if ( nameString != null ) {

                this.name = nameString;

            }

            agencySchemeString = getProperty(DUMMY_AGENCY_SCHEME_KEY, DUMMY_AGENCY_SCHEME_DEFAULT);
            agencyValueString = getProperty(DUMMY_AGENCY_VALUE_KEY, DUMMY_AGENCY_VALUE_DEFAULT);
            if ( agencyValueString != null ) {

                try {

                    this.agency = AgencyId.find(agencySchemeString, agencyValueString);

                } catch (ServiceException e) {

                    LOG.warn("ServiceException creating AgencyId for Dummy connector from scheme '"
                            + agencySchemeString + "' and value '" + agencyValueString + "'.");

                }

            }

        }

        if ( this.agency == null ) {

            LOG.warn("Agency not configured; using defaults instead.");
            try {
                this.agency = AgencyId.find(DUMMY_AGENCY_SCHEME_DEFAULT, DUMMY_AGENCY_VALUE_DEFAULT);
            } catch (ServiceException e) {
                throw new ToolkitException(e);
            }

        }

    }

    public AgencyId getAgencyId() {

        return agency;

    }

    public String getName() {

        return name;

    }

}