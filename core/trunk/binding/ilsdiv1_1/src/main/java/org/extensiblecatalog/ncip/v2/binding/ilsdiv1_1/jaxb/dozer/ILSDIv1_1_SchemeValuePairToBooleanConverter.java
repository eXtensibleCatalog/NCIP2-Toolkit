/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.ilsdiv1_1.jaxb.dozer;

import org.extensiblecatalog.ncip.v2.binding.jaxb.dozer.BaseSchemeValuePairToBooleanConverter;
import org.extensiblecatalog.ncip.v2.binding.ilsdiv1_1.jaxb.elements.SchemeValuePair;

import java.util.ArrayList;
import java.util.List;

public class ILSDIv1_1_SchemeValuePairToBooleanConverter extends BaseSchemeValuePairToBooleanConverter {

    // We only need this to pass the Class object to the super-constructor.
    private final static List<SchemeValuePair> jaxbSVPList = new ArrayList<SchemeValuePair>();

    public ILSDIv1_1_SchemeValuePairToBooleanConverter() {

        super( jaxbSVPList.getClass(), SchemeValuePair.class);

    }
}
