/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

/**
 * This class holds the URI for every standard scheme.
 */
public final class Scheme {

    /**
     * This class is a utility class, so no constructor.
     */
    private Scheme() {
    }

    /**
     * Accept Item Processing Error.
     */
    public static final String ACCEPT_ITEM_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/acceptitemprocessingerror.scm";
    /**
     * Authentication Data Format Type.
     */
    public static final String AUTHENTICATION_DATA_FORMAT_TYPE = "http://www.iana.org/assignments/media-types";
    /**
     * Authentication Input Type.
     */
    public static final String AUTHENTICATION_INPUT_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/authenticationinputtype/authenticationinputtype.scm";
    /**
     * Cancel Request Item Processing Error.
     */
    public static final String CANCEL_REQUEST_ITEM_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/cancelrequestitemprocessingerror.scm";
    /**
     * Check In Item Processing Error.
     */
    public static final String CHECK_IN_ITEM_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/checkinitemprocessingerror.scm";
    /**
     * Check Out Item Processing Error.
     */
    public static final String CHECK_OUT_ITEM_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/checkoutitemprocessingerror.scm";
    /**
     * Circulation Status.
     */
    public static final String CIRCULATION_STATUS
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/circulationstatus/circulationstatus.scm";
    /**
     * Currency Code.
     */
    public static final String CURRENCY_CODE
        = "http://www.bsi-global.com/Technical+Information/Publications/_Publications/tig90x.doc";
    /**
     * General Processing Error.
     */
    public static final String GENERAL_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/generalprocessingerror.scm";
    /**
     * Item Element Type.
     */
    public static final String ITEM_ELEMENT_TYPE
        = "http://www.niso.org/ncip/v1_0/schemes/itemelementtype/itemelementtype.scm";
    /**
     * Lookup Item Processing Error.
     */
    public static final String LOOKUP_ITEM_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/lookupitemprocessingerror.scm";
    /**
     * Messaging Error Type.
     */
    public static final String MESSAGING_ERROR_TYPE
        = "http://www.niso.org/ncip/v1_0/schemes/messagingerrortype/messagingerrortype.scm";
    /**
     * Organization Name Type.
     */
    public static final String ORGANIZATION_NAME_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/organizationnametype/organizationnametype.scm";
    /**
     * Renew Item Processing Error.
     */
    public static final String RENEW_ITEM_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/renewitemprocessingerror.scm";
    /**
     * Request Item Processing Error.
     */
    public static final String REQUEST_ITEM_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/requestitemprocessingerror.scm";
    /**
     * Request Scope Type.
     */
    public static final String REQUEST_SCOPE_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/requestscopetype/requestscopetype.scm";
    /**
     * Request Type.
     */
    public static final String REQUEST_TYPE = "http://www.niso.org/ncip/v1_0/imp1/schemes/requesttype/requesttype.scm";
    /**
     * Requested Action Type.
     */
    public static final String REQUESTED_ACTION_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/requestedactiontype/requestedactiontype.scm";
    /**
     * User Element Type.
     */
    public static final String USER_ELEMENT_TYPE
        = "http://www.niso.org/ncip/v1_0/schemes/userelementtype/userelementtype.scm";
    /**
     * Medium Type.
     */
    public static final String MEDIUM_TYPE = "http://www.niso.org/ncip/v1_0/imp1/schemes/mediumtype/mediumtype.scm";
    /**
     * Language.
     */
    public static final String LANGUAGE = "http://lcweb.loc.gov/standards/iso639-2/bibcodes.html";
    /**
     * Electronic Data Format Type.
     */
    public static final String ELECTRONIC_DATA_FORMAT_TYPE = "http://www.iana.org/assignments/media-types";
    /**
     * Component Identifier Type.
     */
    public static final String COMPONENT_IDENTIFIER_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/componentidentifiertype/componentidentifiertype.scm";
    /**
     * Bibliographic Level.
     */
    public static final String BIBLIOGRAPHIC_LEVEL
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/bibliographiclevel/bibliographiclevel.scm";
    /**
     * Bibliographic Item Identifier Code.
     */
    public static final String BIBLIOGRAPHIC_ITEM_IDENTIFIER_CODE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/bibliographicitemidentifiercode"
        + "/bibliographicitemidentifiercode.scm";
    /**
     * Bibliographic Record Identifier Code.
     */
    public static final String BIBLIOGRAPHIC_RECORD_IDENTIFIER_CODE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/bibliographicrecordidentifiercode"
        + "/bibliographicrecordidentifiercode.scm";
    /**
     * Item Use Restriction Type.
     */
    public static final String ITEM_USE_RESTRICTION_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/itemuserestrictiontype/itemuserestrictiontype.scm";
    /**
     * Lookup User Processing Error.
     */
    public static final String LOOKUP_USER_PROCESSING_ERROR
        = "http://www.niso.org/ncip/v1_0/schemes/processingerrortype/lookupuserprocessingerror.scm";
    /**
     * Electronic Address Type.
     */
    public static final String USER_ELECTRONIC_ADDRESS_TYPE = "http://www.iana.org/assignments/uri-schemes";
    /**
     * Block or Trap Type Scheme
     */
    public static final String BLOCK_OR_TRAP_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/blockortraptype/blockortraptype.scm";
    /**
     * User Address Role Type
     */
    public static final String USER_ADDRESS_ROLE_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/useraddressroletype/useraddressroletype.scm";


}
