/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb.dozer;

import org.dozer.DozerConverter;
import org.dozer.MappingException;
import org.extensiblecatalog.ncip.v2.binding.jaxb.JAXBHelper;
import org.extensiblecatalog.ncip.v2.service.SchemeValuePair;
import org.extensiblecatalog.ncip.v2.service.ServiceHelper;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import javax.xml.bind.JAXBElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Note: In every JAXB package you must implement a sub-class of this class for every sub-class of
 * {@link SchemeValuePair}, e.g.
<code>
public class ItemDescriptionLevelJAXBElementSchemeValuePairConverter
 extends BaseJAXBElementSchemeValuePairConverter<org.extensiblecatalog.ncip.v2.binding.jaxb.elemenets.SchemeValuePair, ItemDescriptionLevel>
 { ... }
</code>
 *
 * @param <JAXBSVP>
 * @param <SVCSVP>
 */
public class BaseJAXBElementSchemeValuePairConverter< JAXBSVP, SVCSVP extends SchemeValuePair >
    extends DozerConverter<JAXBSVP, SVCSVP> {

    protected static final Map<Class, Method> findMethodsByClass = new HashMap<Class, Method>();

    protected final Class jaxbSVPClass;
    protected final Class<SVCSVP> serviceSVPClass;

    public BaseJAXBElementSchemeValuePairConverter(Class jaxbSVPClass, Class serviceSVPClass) {
        super(jaxbSVPClass, serviceSVPClass);
        this.jaxbSVPClass = jaxbSVPClass;
        this.serviceSVPClass = serviceSVPClass;
    }

    @Override
    public SVCSVP convertTo(JAXBSVP source, SVCSVP destination) {
        // Convert to JAXB svp from service SVP
        // If the parent JAXB object uses the get-Content catch-all, then SchemeValuePair objects are contained
        //  w/i JAXBElement objects, so we have to unwrap them here.

        SVCSVP result = null;
        if ( source != null ) {

            try {

                result = ServiceHelper.findSchemeValuePair(serviceSVPClass,
                        JAXBHelper.getScheme(((JAXBElement)source).getValue()),
                        JAXBHelper.getValue(((JAXBElement)source).getValue()));

            } catch (ToolkitException e) {

                throw new MappingException(e);

            }

        } else {

            if ( destination != null ) {

                throw new MappingException("Source is null but destination is not null.");

            } else {

                // This is ok - return null

            }

        }
        return result;

    }

    @Override
    public JAXBSVP convertFrom(SVCSVP source, JAXBSVP destination) {

        // Convert from service SVP to JAXBElement
        throw new MappingException("Unsupported mapping from service.SchemeValuePair to JAXBElement.");

    }

}
