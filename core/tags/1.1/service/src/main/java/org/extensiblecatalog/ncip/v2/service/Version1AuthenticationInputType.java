/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

public class Version1AuthenticationInputType extends AuthenticationInputType {

    private static final Logger LOG = Logger.getLogger(Version1AuthenticationInputType.class);

    public static final String VERSION_1_AUTHENTICATION_INPUT_TYPE
        = "http://www.niso.org/ncip/v1_0/imp1/schemes/authenticationinputtype/authenticationinputtype.scm";

    public static final Version1AuthenticationInputType BARCODE_ID
        = new Version1AuthenticationInputType(VERSION_1_AUTHENTICATION_INPUT_TYPE, "Barcode Id");
    public static final Version1AuthenticationInputType MD5_MESSAGE_DIGEST_ALGORITHM
        = new Version1AuthenticationInputType(VERSION_1_AUTHENTICATION_INPUT_TYPE, "MD5 Message Digest Algorithm");
    public static final Version1AuthenticationInputType PASSWORD
        = new Version1AuthenticationInputType(VERSION_1_AUTHENTICATION_INPUT_TYPE, "Password");
    public static final Version1AuthenticationInputType PIN
        = new Version1AuthenticationInputType(VERSION_1_AUTHENTICATION_INPUT_TYPE, "PIN");
    public static final Version1AuthenticationInputType SECONDARY_CONFIRMATION_STRING
        = new Version1AuthenticationInputType(VERSION_1_AUTHENTICATION_INPUT_TYPE, "Secondary Confirmation String");
    public static final Version1AuthenticationInputType USER_ID
        = new Version1AuthenticationInputType(VERSION_1_AUTHENTICATION_INPUT_TYPE, "User Id");
    public static final Version1AuthenticationInputType X_509_CERTIFICATE
        = new Version1AuthenticationInputType(VERSION_1_AUTHENTICATION_INPUT_TYPE, "X.509 Certificate");

    public static void loadAll() {
        LOG.debug("Loading Version1AuthenticationInputType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1AuthenticationInputType(String scheme, String value) {
        super(scheme, value);
    }
}
