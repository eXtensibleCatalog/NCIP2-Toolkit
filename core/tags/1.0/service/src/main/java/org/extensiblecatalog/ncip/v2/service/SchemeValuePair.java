/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * A pair of Strings, one a value and the other a scheme, represented by a URI
 * which gives a globally-unique context for the value. The scheme is optional
 * for uses where both parties to the exchange of information understand implicitly
 * the vocabulary from which the value is taken. This implementation is intended to
 * implement the type-safe enum pattern (see Joshua Bloch's Effective Java, ISBN 0-201-31005-8,
 * Item 21 "Replace enum constructs with classes", pp. 104-114).
 */
public abstract class SchemeValuePair {

    private static final Logger LOG = Logger.getLogger(SchemeValuePair.class);

    public enum Behavior {
        UNSET, DEFINED_ONLY, ALLOW_ANY
    }

    // Default, for classes not in this map, is NOT to allow null scheme
    protected static final Map<String /* Class name including pkg */, Boolean> CLASSES_ALLOWING_NULL_SCHEME
        = new HashMap<String, Boolean>();

    public static void allowNullScheme(String ... classNames) {

        for ( String className : classNames ) {
            CLASSES_ALLOWING_NULL_SCHEME.put(className, Boolean.TRUE);
        }

    }

    protected static final Map<String /* Class name including pkg */, Behavior> BEHAVIOR_BY_CLASS_NAME_MAP
        = new HashMap<String, Behavior>();

    public static void mapBehavior(String className, Behavior behavior) {

        BEHAVIOR_BY_CLASS_NAME_MAP.put(className, behavior);

    }

    protected static final Map<String, String> SCHEME_URI_ALIAS_MAP = new HashMap<String, String>();

    static {

        // Known aliases
        // Due to an editing mistake in Version 2.0 of the NCIP standard, all of the Schemes published with
        // version 1 were presented with *different* URIs ("v1_0" was changed to "v2_0"). The following allows
        // the Toolkit to recognize all of the Scheme URIs published in version 2 of the Standard.
        setSchemeURIAlias(Version1AcceptItemProcessingError.VERSION_1_ACCEPT_ITEM_PROCESSING_ERROR,
                    "http://www.niso.org/ncip/v2_0/schemes/processingerrortype/acceptitemprocessingerror.scm");
        setSchemeURIAlias(Version1AgencyAddressRoleType.VERSION_1_AGENCY_ADDRESS_ROLE_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/agencyaddressroletype/agencyaddressroletype.scm");
        setSchemeURIAlias(Version1AgencyElementType.VERSION_1_AGENCY_ELEMENT_TYPE,
                    "http://www.niso.org/ncip/v2_0/schemes/agencyelementtype/agencyelementtype.scm");
        setSchemeURIAlias(Version1AuthenticationInputType.VERSION_1_AUTHENTICATION_INPUT_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/authenticationinputtype/authenticationinputtype.scm");
        setSchemeURIAlias(Version1BibliographicItemIdentifierCode.VERSION_1_BIBLIOGRAPHIC_ITEM_IDENTIFIER_CODE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/bibliographicitemidentifiercode/bibliographicitemidentifiercode.scm");
        setSchemeURIAlias(Version1BibliographicLevel.VERSION_1_BIBLIOGRAPHIC_LEVEL,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/bibliographiclevel/bibliographiclevel.scm");
        setSchemeURIAlias(Version1BibliographicRecordIdentifierCode.VERSION_1_BIBLIOGRAPHIC_RECORD_IDENTIFIER_CODE,
                    "http://www.niso.org/ncip/v2_0/schemes/bibliographicrecordidentifiercode/bibliographicrecordidentifiercode.scm");
        setSchemeURIAlias(Version1BibliographicRecordIdentifierCode.VERSION_1_BIBLIOGRAPHIC_RECORD_IDENTIFIER_CODE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/bibliographicrecordidentifiercode/bibliographicrecordidentifiercode.scm");
        setSchemeURIAlias(Version1CheckInItemProcessingError.VERSION_1_CHECK_IN_ITEM_PROCESSING_ERROR,
                    "http://www.niso.org/ncip/v2_0/schemes/processingerrortype/checkinitemprocessingerror.scm");
        setSchemeURIAlias(Version1CheckOutItemProcessingError.VERSION_1_CHECK_OUT_ITEM_PROCESSING_ERROR,
                    "http://www.niso.org/ncip/v2_0/schemes/processingerrortype/checkoutitemprocessingerror.scm");
        setSchemeURIAlias(Version1CirculationStatus.VERSION_1_CIRCULATION_STATUS,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/circulationstatus/circulationstatus.scm");
        setSchemeURIAlias(Version1ComponentIdentifierType.VERSION_1_COMPONENT_IDENTIFIER_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/componentidentifiertype/componentidentifiertype.scm");
        setSchemeURIAlias(Version1FiscalActionType.VERSION_1_FISCAL_ACTION_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/fiscalactiontype/fiscalactiontype.scm");
        setSchemeURIAlias(Version1FiscalTransactionType.VERSION_1_FISCAL_TRANSACTION_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/fiscaltransactiontype/fiscaltransactiontype.scm");
        setSchemeURIAlias(Version1GeneralProcessingError.VERSION_1_GENERAL_PROCESSING_ERROR,
                    "http://www.niso.org/ncip/v2_0/schemes/processingerrortype/generalprocessingerror.scm");
        setSchemeURIAlias(Version1ItemDescriptionLevel.VERSION_1_ITEM_DESCRIPTION_LEVEL,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/itemdescriptionlevel/itemdescriptionlevel.scm");
        setSchemeURIAlias(Version1ItemElementType.VERSION_1_ITEM_ELEMENT_TYPE,
                    "http://www.niso.org/ncip/v2_0/schemes/itemelementtype/itemelementtype.scm");
        setSchemeURIAlias(Version1ItemIdentifierType.VERSION_1_ITEM_IDENTIFIER_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/visibleitemidentifiertype/visibleitemidentifiertype.scm");
        setSchemeURIAlias(Version1ItemUseRestrictionType.VERSION_1_ITEM_USE_RESTRICTION_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/itemuserestrictiontype/itemuserestrictiontype.scm");
        setSchemeURIAlias(Version1LocationType.VERSION_1_LOCATION_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/locationtype/locationtype.scm");
        setSchemeURIAlias(Version1LookupItemProcessingError.VERSION_1_LOOKUP_ITEM_PROCESSING_ERROR,
                    "http://www.niso.org/ncip/v2_0/schemes/processingerrortype/lookupitemprocessingerror.scm");
        setSchemeURIAlias(Version1LookupUserProcessingError.VERSION_1_LOOKUP_USER_PROCESSING_ERROR,
                    "http://www.niso.org/ncip/v2_0/schemes/processingerrortype/lookupuserprocessingerror.scm");
        setSchemeURIAlias(Version1MediumType.VERSION_1_MEDIUM_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/mediumtype/mediumtype.scm");
        setSchemeURIAlias(Version1MessagingError.VERSION_1_MESSAGING_ERROR,
                    "http://www.niso.org/ncip/v2_0/schemes/messagingerrortype/messagingerrortype.scm");
        setSchemeURIAlias(Version1OrganizationNameType.VERSION_1_ORGANIZATION_NAME_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/organizationnametype/organizationnametype.scm");
        setSchemeURIAlias(Version1PaymentMethodType.VERSION_1_PAYMENT_METHOD_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/paymentmethodtype/paymentmethodtype.scm");
        setSchemeURIAlias(Version1PhysicalAddressType.VERSION_1_PHYSICAL_ADDRESS_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/physicaladdresstype/physicaladdresstype.scm");
        setSchemeURIAlias(Version1PhysicalConditionType.VERSION_1_PHYSICAL_CONDITION_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/physicalconditiontype/physicalconditiontype.scm");
        setSchemeURIAlias(Version1RequestElementType.VERSION_1_REQUEST_ELEMENT_TYPE,
                    "http://www.niso.org/ncip/v2_0/schemes/requestelementtype/requestelementtype.scm");
        setSchemeURIAlias(Version1RequestItemProcessingError.VERSION_1_REQUEST_ITEM_PROCESSING_ERROR,
                    "http://www.niso.org/ncip/v2_0/schemes/processingerrortype/requestitemprocessingerror.scm");
        setSchemeURIAlias(Version1RequestScopeType.VERSION_1_REQUEST_SCOPE_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/requestscopetype/requestscopetype.scm");
        setSchemeURIAlias(Version1RequestStatusType.VERSION_1_REQUEST_STATUS_TYPE,
                    "http://www.niso.org/ncip/v2_0/schemes/requeststatustype/requeststatustype.scm");
        setSchemeURIAlias(Version1RequestStatusType.VERSION_1_REQUEST_STATUS_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/requeststatustype/requeststatustype.scm");
        setSchemeURIAlias(Version1RequestType.VERSION_1_REQUEST_TYPE,
                    "http://www.niso.org/ncip/v2_0/schemes/requesttype/requesttype.scm");
        setSchemeURIAlias(Version1RequestType.VERSION_1_REQUEST_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/requesttype/requesttype.scm");
        setSchemeURIAlias(Version1RequestedActionType.VERSION_1_REQUESTED_ACTION_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/requestedactiontype/requestedactiontype.scm");
        setSchemeURIAlias(Version1SecurityMarker.VERSION_1_SECURITY_MARKER,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/securitymarker/securitymarker.scm");
        setSchemeURIAlias(Version1UnstructuredAddressType.VERSION_1_UNSTRUCTURED_ADDRESS_TYPE,
                    "http://www.niso.org/ncip/v2_0/imp1/schemes/unstructuredaddresstype/unstructuredaddresstype.scm");
        setSchemeURIAlias(Version1UserElementType.VERSION_1_USER_ELEMENT_TYPE,
                    "http://www.niso.org/ncip/v2_0/schemes/userelementtype/userelementtype.scm");

    }

    public static void setSchemeURIAlias(String canonicalURI, String aliasURI) {

        SCHEME_URI_ALIAS_MAP.put(aliasURI, canonicalURI);

    }

    protected static String canonicalizeSchemeURI(String scheme) {

        if ( scheme != null ) {

            String newScheme = SCHEME_URI_ALIAS_MAP.get(scheme);
            if ( newScheme != null ) {

                scheme = newScheme;

            }

        }

        return scheme;

    }

    /**
     * The Scheme or URI of the Scheme/Value pair.
     */
    protected String scheme;
    /**
     * The Value of the Scheme/Value pair.
     */
    protected String value;

    /**
     * Construct a SchemeValuePair with a scheme and a value.
     *
     * @param scheme the NCIP Scheme (a URI)
     * @param value  a Value within the Scheme
     */
    public SchemeValuePair(String scheme, String value) {
        this.scheme = scheme;
        this.value = value;
    }

    /**
     * Construct a SchemeValuePair with a value but no scheme; the scheme is implicit between the two systems
     * exchanging NCIP messages using this object.
     *
     * @param value a Value in an implicit scheme
     */
    public SchemeValuePair(String value) {
        this.value = value;
    }

    /**
     * Returns the scheme (a URI) for this object's value.
     *
     * @return the scheme
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * Returns the value of this object.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Find the instance that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme; must not be null
     * @return an instance that matches, or null if none is found to match.
     */
    public static <SVP extends SchemeValuePair> SchemeValuePair find(final String scheme, final String value,
                                                                        List<SVP> values, Class<SVP> svpClass)
        throws ServiceException {

        // TODO: Reorganize this so we match on Scheme, and then on value, not testing everything in the list.
        // I think that will require changes to any SVP-derived class, which may mean class in connector projects.
        SVP match = null;
        if ( value != null && value.length() > 0 ) {
            for (SVP svp : values) {
                if ( svp.matches(scheme, value) ) {
                    match = svp;
                    break;
                }
            }
        }

        if (match == null) {

            Behavior behavior = BEHAVIOR_BY_CLASS_NAME_MAP.get(svpClass.getName());

            if (behavior == null) {
                behavior = Behavior.UNSET;
            }

            switch (behavior) {

                case UNSET:

                    throw new ServiceException(ServiceError.INVALID_SCHEME_VALUE, "No match found for scheme '" + scheme
                        + "', value '" + value + "' in " + svpClass.getName()
                        + "; configuration option for defining values for this class is unset.");

                case DEFINED_ONLY:

                    throw new ServiceException(ServiceError.INVALID_SCHEME_VALUE, "No match found for scheme '" + scheme
                        + "', value '" + value + "' in " + svpClass.getName()
                        + "; configuration option for defining values for this class is 'DEFINED_ONLY'.");

                case ALLOW_ANY:
                    synchronized(values) {

                        // While synchronized on the values list, check that this instance still isn't in the list
                        // and if not, add it.

                        // This is identical to the logic used earlier and must be kept in synch.
                        // This isn't moved to a method because the compiler doesn't realize it's the same type
                        // being returned by the method as is defined here as SVP.
                        // We could move it to a method and cast the result, but that risks mistakes that
                        // the casting would hide.
                        if ( value != null && value.length() > 0 ) {
                            for (SVP svp : values) {
                                if ( svp.matches(scheme, value) ) {
                                    match = svp;
                                    break;
                                }
                            }
                        }

                        if ( match == null ) {

                            LOG.info("Adding SchemeValuePair(" + scheme + ", " + value + ") to " + svpClass.getName());
                            Class[] parmTypes = new Class[2];
                            parmTypes[0] = String.class;
                            parmTypes[1] = String.class;
                            try {
                                Constructor<SVP> ctor = svpClass.getConstructor(parmTypes);
                                Object[] parmInstances = new Object[2];
                                parmInstances[0] = scheme;
                                parmInstances[1] = value;
                                match = ctor.newInstance(parmInstances);

                            } catch (NoSuchMethodException e) {

                                throw new ServiceException(ServiceError.RUNTIME_ERROR, e);

                            } catch (InvocationTargetException e) {

                                throw new ServiceException(ServiceError.RUNTIME_ERROR, e);

                            } catch (InstantiationException e) {

                                throw new ServiceException(ServiceError.RUNTIME_ERROR, e);

                            } catch (IllegalAccessException e) {

                                throw new ServiceException(ServiceError.RUNTIME_ERROR, e);

                            }
                            values.add(match);

                        } else {

                            LOG.debug("Skipping adding (" + match.getScheme() + ", " + match.getValue() + ")"
                                + " because it's already been added.");

                        }

                    }

                    break;

                default :

                    throw new ServiceException(ServiceError.CONFIGURATION_ERROR,
                        "Configuration option for defining values for this class has an unexpected value of '"
                            + behavior + ".");

            }
        }

        return match;
    }

    /**
     * Test two instances for equality. In addition to returning true if the passed-in Object <code>o</code> is the
     * same instance as <code>this</code>, this method considers two objects equal if, after translating their schemes
     * to canonical form, the two object's schemes are equal and the two object's values are equal.
     *
     * Note: Sub-classes of SchemeValuePair that add comparable attributes (e.g. CurrencyCode) must override
     * the equals method, call their superclass's equals, and then (if the objects are equal) compare the
     * added attributes.
     *
     */
    @Override
    public boolean equals(Object o) {

        if ( o == this ) {
            return true;
        }
        if (!(o instanceof SchemeValuePair)) {
            return false;
        }

        if ( ! this.isComparableSVPSubclass(o) ) {
            return false;
        }

        SchemeValuePair svpO = (SchemeValuePair)o;

        return matches(svpO.getScheme(), svpO.getValue());

    }

    protected Class getImmediateSVPSubclass(Class svpClass) {

        Class immediateSubclass = svpClass;
        while ( !immediateSubclass.getSuperclass().equals(SchemeValuePair.class) ) {
            immediateSubclass = immediateSubclass.getSuperclass();
        }

        return immediateSubclass;

    }

    /**
     * Return true if the two objects subclass the same "immediate child class" of SchemeValuePair
     * @param o
     * @return
     */
    public boolean isComparableSVPSubclass(Object o ) {

        if ( ! o.getClass().equals(this.getClass()) )  {

            Class oImmediateSubClass = getImmediateSVPSubclass(o.getClass());

            Class thisImmediateSubClass = getImmediateSVPSubclass(this.getClass());

            if ( ! oImmediateSubClass.equals(thisImmediateSubClass ) ) {

                return false;

            }

        }

        return true;

    }

    public boolean matches(SchemeValuePair svp) {

        if ( isComparableSVPSubclass(svp) ) {

            return matches(svp.getScheme(), svp.getValue());

        } else {

            return false;
        }

    }
    
    /**
     * Note: If value is null and this.value is null, this will return true.
     * @param scheme
     * @param value
     * @return
     */
    public boolean matches(String scheme, String value) {

        // Note: We test for null value even though it should never be null. This is because this is called by
        // {@link #equals} and we can't violate the equals contract just because we've violated that design contract
        // for SchemeValuePair; and this is a public method and we don't want to impose any unnecessary requirements
        // on callers.

        boolean thisClassAllowsNullScheme = areNullSchemesAllowed();

        if ( this.getScheme() != null ) {
            String thisSchemeCanonical = canonicalizeSchemeURI(this.getScheme());
            // TODO: Consider storing canonical form of Scheme to speed this comparison, per Bloch p. 33-4.
            if ( scheme != null ) {
                String svpOSchemeCanonical = canonicalizeSchemeURI(scheme);
                if ( thisSchemeCanonical.compareToIgnoreCase(svpOSchemeCanonical) == 0 ) {
                    return this.compareValue(value);
                } else {
                    return false; // this.scheme != o.scheme
                }
            } else if ( thisClassAllowsNullScheme ) {
                return this.compareValue(value);
            } else {
                return false; // this.scheme != null && o.scheme == null
            }
        } else { // this.scheme == null
            if ( scheme != null ) {
              if ( thisClassAllowsNullScheme ) {
                return this.compareValue(value);
              } else {
                return false; // this.scheme == null && o.scheme != null
              }
            } else { // this.scheme == null && o.scheme == null
                return this.compareValue(value);
            }
        }
    }

    protected boolean compareValue(String value) {
        if ( this.getValue() != null ) {
            if ( value != null ) {
                if ( this.getValue().compareToIgnoreCase(value) == 0 ) {
                    return true;
                } else {
                    return false; // this.value != o.value
                }
            } else {
                return false; // this.value != null && o.value == null
            }
        } else {
            if ( value != null ) {
                return false; // this.value == null && o.value != null
            } else {
                return true; // this.value == null && o.value == null
            }
        }
    }

    public boolean areNullSchemesAllowed() {

        if ( CLASSES_ALLOWING_NULL_SCHEME.containsKey(this.getClass().getName()) ) {
            return true;
        }

        Class immediateSubclass = getImmediateSVPSubclass(this.getClass());

        if ( CLASSES_ALLOWING_NULL_SCHEME.containsKey(immediateSubclass.getName()) ) {
            return true;
        }

        return false;

    }

    /**
     * Generic toString() implementation.
     *
     * @return String
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
