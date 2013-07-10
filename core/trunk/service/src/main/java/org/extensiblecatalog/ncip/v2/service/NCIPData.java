/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

/*
 * Definition of NCIPData objects, which are either initiation or response messages.
 */
public interface NCIPData {

    String getVersion();

    void setVersion(String version);

}
