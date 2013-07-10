/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.millennium;

import org.extensiblecatalog.ncip.v2.common.DefaultConnectorConfiguration;
import org.extensiblecatalog.ncip.v2.common.ToolkitHelper;

import java.util.List;
import java.util.Properties;

public class MillenniumConfiguration extends DefaultConnectorConfiguration {

    public enum MillenniumAuthenticationType { LDAP, PATRON, BOTH };

    public final static String MILLENNIUM_URL_KEY = "MillenniumConfiguration.URL";
    public final static String MILLENNIUM_URL_DEFAULT = null;

    public final static String MILLENNIUM_PORT_KEY = "MillenniumConfiguration.Port";
    public final static String MILLENNIUM_PORT_DEFAULT = "80";

    public final static String MILLENNIUM_LIBRARY_NAME_KEY = "MillenniumConfiguration.LibraryName";
    public final static String MILLENNIUM_LIBRARY_NAME_DEFAULT = "Library Name";

    public final static String MILLENNIUM_DEFAULT_AGENCY_KEY = "MillenniumConfiguration.DefaultAgency";
    public final static String MILLENNIUM_DEFAULT_AGENCY_DEFAULT = null;

    public final static String MILLENNIUM_CALL_NO_LABEL_KEY = "MillenniumConfiguration.CallNoLabel";
    public final static String MILLENNIUM_CALL_NO_LABEL_DEFAULT = "CALL #";

    public final static String MILLENNIUM_LOCATION_LABEL_KEY = "MillenniumConfiguration.LocationLabel";
    public final static String MILLENNIUM_LOCATION_LABEL_DEFAULT = "Location";

    public final static String MILLENNIUM_LIBRARY_HAS_LABEL_KEY = "MillenniumConfiguration.LibraryHasLabel";
    public final static String MILLENNIUM_LIBRARY_HAS_LABEL_DEFAULT = "Lib. Has";

    public final static String MILLENNIUM_SEARCH_SCOPE_KEY = "MillenniumConfiguration.SearchScope";
    public final static String MILLENNIUM_SEARCH_SCOPE_DEFAULT = null;

    public final static String MILLENNIUM_SERVICES_KEY = "MillenniumConfiguration.Services";
    public final static String MILLENNIUM_SERVICES_DEFAULT = "LookupItem, LookupItemSet, LookupUser, RequestItem, CancelItem, RenewItem, ChechOutItem, CheckInItem";

    public final static String MILLENNIUM_FUNCTIONS_KEY = "MillenniumConfiguration.Functions";
    public final static String MILLENNIUM_FUNCTIONS_DEFAULT = "Cancel, Renew, Request";

    // These are used temporarily until authentication issues are resolved
    public final static String MILLENNIUM_TEMPORARY_USER_KEY = "MillenniumConfiguration.TemporaryUser";
    public final static String MILLENNIUM_TEMPORARY_USER_DEFAULT = null;

    public final static String MILLENNIUM_TEMPORARY_PASSWORD_KEY = "MillenniumConfiguration.TemporaryPassword";
    public final static String MILLENNIUM_TEMPORARY_PASSWORD_DEFAULT = null;

    public final static String MILLENNIUM_LDAP_USER_VARIABLE_KEY = "MillenniumConfiguration.LDAPUserVariable";
    public final static String MILLENNIUM_LDAP_USER_VARIABLE_DEFAULT = null;

    public final static String MILLENNIUM_LDAP_PASSWORD_VARIABLE_KEY = "MillenniumConfiguration.LDAPPasswordVariable";
    public final static String MILLENNIUM_LDAP_PASSWORD_VARIABLE_DEFAULT = null;

    public final static String MILLENNIUM_PATRON_USER_VARIABLE_KEY = "MillenniumConfiguration.PatronUserVariable";
    public final static String MILLENNIUM_PATRON_USER_VARIABLE_DEFAULT = null;

    public final static String MILLENNIUM_PATRON_PASSWORD_VARIABLE_KEY = "MillenniumConfiguration.PatronPasswordVariable";
    public final static String MILLENNIUM_PATRON_PASSWORD_VARIABLE_DEFAULT = null;

    public final static String MILLENNIUM_AUTHENTICATION_TYPE_KEY = "MillenniumConfiguration.AuthenticationType";
    public final static String MILLENNIUM_AUTHENTICATION_TYPE_DEFAULT = "Both";

    protected String url;
    protected int port;
    protected String libraryName;
    //protected List<String> defaultAgency;
    protected String defaultAgency;
    protected String callNoLabel;
    protected String locationLabel;
    protected String libraryHasLabel;
    protected String searchScope;
    protected String services;
    protected String functions;
    protected String temporaryUser;
    protected String temporaryPassword;
    protected String ldapUserVariable;
    protected String ldapPasswordVariable;
    protected String patronUserVariable;
    protected String patronPasswordVariable;
    protected MillenniumAuthenticationType authenticationType;

    public MillenniumConfiguration() {

        // Do nothing
    }

    public MillenniumConfiguration(Properties properties) {

        super(properties);

        String urlString = null;
        String portString = null;
        String libraryNameString = null;
        String defaultAgencyString = null;
        String callNoLabelString = null;
        String locationLabelString = null;
        String libraryHasLabelString = null;
        String searchScopeString = null;
        String servicesString = null;
        String functionsString = null;
        String temporaryUserString = null;
        String temporaryPasswordString = null;
        String ldapUserVariableString = null;
        String ldapPasswordVariableString = null;
        String patronUserVariableString = null;
        String patronPasswordVariableString = null;
        String authenticationTypeString = null;

        urlString = getProperty(MILLENNIUM_URL_KEY, MILLENNIUM_URL_DEFAULT);
        portString = getProperty(MILLENNIUM_PORT_KEY, MILLENNIUM_PORT_DEFAULT);
        libraryNameString = getProperty(MILLENNIUM_LIBRARY_NAME_KEY, MILLENNIUM_LIBRARY_NAME_DEFAULT);
        defaultAgencyString = getProperty(MILLENNIUM_DEFAULT_AGENCY_KEY, MILLENNIUM_DEFAULT_AGENCY_DEFAULT);
        callNoLabelString = getProperty(MILLENNIUM_CALL_NO_LABEL_KEY, MILLENNIUM_CALL_NO_LABEL_DEFAULT);
        locationLabelString = getProperty(MILLENNIUM_LOCATION_LABEL_KEY, MILLENNIUM_LOCATION_LABEL_DEFAULT);
        libraryHasLabelString = getProperty(MILLENNIUM_LIBRARY_HAS_LABEL_KEY, MILLENNIUM_LIBRARY_HAS_LABEL_DEFAULT);
        searchScopeString = getProperty(MILLENNIUM_SEARCH_SCOPE_KEY, MILLENNIUM_SEARCH_SCOPE_DEFAULT);
        servicesString = getProperty(MILLENNIUM_SERVICES_KEY, MILLENNIUM_SERVICES_DEFAULT);
        functionsString = getProperty(MILLENNIUM_FUNCTIONS_KEY, MILLENNIUM_FUNCTIONS_DEFAULT);
        temporaryUserString = getProperty(MILLENNIUM_TEMPORARY_USER_KEY, MILLENNIUM_TEMPORARY_USER_DEFAULT);
        temporaryPasswordString = getProperty(MILLENNIUM_TEMPORARY_PASSWORD_KEY, MILLENNIUM_TEMPORARY_PASSWORD_DEFAULT);
        ldapUserVariableString = getProperty(MILLENNIUM_LDAP_USER_VARIABLE_KEY, MILLENNIUM_LDAP_USER_VARIABLE_DEFAULT);
        ldapPasswordVariableString = getProperty(MILLENNIUM_LDAP_PASSWORD_VARIABLE_KEY,
            MILLENNIUM_LDAP_PASSWORD_VARIABLE_DEFAULT);
        patronUserVariableString = getProperty(MILLENNIUM_PATRON_USER_VARIABLE_KEY,
            MILLENNIUM_PATRON_USER_VARIABLE_DEFAULT);
        patronPasswordVariableString = getProperty(MILLENNIUM_PATRON_PASSWORD_VARIABLE_KEY,
            MILLENNIUM_PATRON_PASSWORD_VARIABLE_DEFAULT);
        authenticationTypeString = getProperty(MILLENNIUM_AUTHENTICATION_TYPE_KEY,
            MILLENNIUM_AUTHENTICATION_TYPE_DEFAULT);

        if ( urlString != null ) {

            this.url = urlString;

        }

        if ( portString != null ) {

            this.port = Integer.parseInt(portString);

        }

        if ( libraryNameString != null ) {

            this.libraryName = libraryNameString;

        }

        if ( defaultAgencyString != null ) {

            //this.defaultAgency = ToolkitHelper.createStringList(defaultAgencyString);
        	this.defaultAgency = defaultAgencyString;

        }

        if ( callNoLabelString != null ) {

            this.callNoLabel = callNoLabelString;

        }

        if ( locationLabelString != null ) {

            this.locationLabel = locationLabelString;

        }

        if ( libraryHasLabelString != null ) {

            this.libraryHasLabel = libraryHasLabelString;

        }

        if ( searchScopeString != null ) {

            this.searchScope = searchScopeString;

        }

        if ( servicesString != null ) {

            this.services = servicesString;

        }

        if ( functionsString != null ) {

            this.functions = functionsString;

        }

        if ( temporaryUserString != null ) {

            this.temporaryUser = temporaryUserString;

        }

        if ( temporaryPasswordString != null ) {

            this.temporaryPassword = temporaryPasswordString;

        }

        if ( ldapUserVariableString != null ) {

            this.ldapUserVariable = ldapUserVariableString;

        }

        if ( ldapPasswordVariableString != null ) {

            this.ldapPasswordVariable = ldapPasswordVariableString;

        }

        if ( patronUserVariableString != null ) {

            this.patronUserVariable = patronUserVariableString;

        }

        if ( patronPasswordVariableString != null ) {

            this.patronPasswordVariable = patronPasswordVariableString;

        }

        if ( authenticationTypeString != null ) {

            this.authenticationType = MillenniumAuthenticationType.valueOf(authenticationTypeString.toUpperCase());

        }

    }


    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getDefaultAgency() {
        return defaultAgency;
    }
    //public List<String> getDefaultAgency() {
    //    return defaultAgency;
    //}

    public void setDefaultAgency(String defaultAgency) {
        this.defaultAgency = defaultAgency;
    }

    public String getCallNoLabel() {
        return callNoLabel;
    }

    public void setCallNoLabel(String callNoLabel) {
        this.callNoLabel = callNoLabel;
    }

    public String getLocationLabel() {
        return locationLabel;
    }

    public void setLocationLabel(String locationLabel) {
        this.locationLabel = locationLabel;
    }

    public String getLibraryHasLabel() {
        return libraryHasLabel;
    }

    public void setLibraryHasLabel(String libraryHasLabel) {
        this.libraryHasLabel = libraryHasLabel;
    }

    public String getSearchScope() {
        return searchScope;
    }

    public void setSearchScope(String searchScope) {
        this.searchScope = searchScope;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getFunctions() {
        return functions;
    }

    public void setFunctions(String functions) {
        this.functions = functions;
    }

    public String getTemporaryUser() {
        return temporaryUser;
    }

    public void setTemporaryUser(String temporaryUser) {
        this.temporaryUser = temporaryUser;
    }

    public String getTemporaryPassword() {
        return temporaryPassword;
    }

    public void setTemporaryPassword(String temporaryPassword) {
        this.temporaryPassword = temporaryPassword;
    }

    public String getLdapUserVariable() {
        return ldapUserVariable;
    }

    public void setLdapUserVariable(String ldapUserVariable) {
        this.ldapUserVariable = ldapUserVariable;
    }

    public String getLdapPasswordVariable() {
        return ldapPasswordVariable;
    }

    public void setLdapPasswordVariable(String ldapPasswordVariable) {
        this.ldapPasswordVariable = ldapPasswordVariable;
    }

    public String getPatronUserVariable() {
        return patronUserVariable;
    }

    public void setPatronUserVariable(String patronUserVariable) {
        this.patronUserVariable = patronUserVariable;
    }

    public String getPatronPasswordVariable() {
        return patronPasswordVariable;
    }

    public void setPatronPasswordVariable(String patronPasswordVariable) {
        this.patronPasswordVariable = patronPasswordVariable;
    }

    public MillenniumAuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(MillenniumAuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
    }

} 