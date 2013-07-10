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

/**
 * Note: In every JAXB package you must implement a sub-class of this class for every sub-class of
 * {@link SchemeValuePair}, e.g.
<code>
public class ItemDescriptionLevelSchemeValuePairConverter
 extends BaseSchemeValuePairConverter<org.extensiblecatalog.ncip.v2.binding.jaxb.elemenets.SchemeValuePair, ItemDescriptionLevel>
 { ... }
</code>
 *
 * @param <JAXBSVP>
 * @param <SVCSVP>
 */
public class BaseSchemeValuePairConverter< JAXBSVP, SVCSVP extends SchemeValuePair >
    extends DozerConverter<JAXBSVP, SVCSVP> {

    protected final Class jaxbSVPClass;
    protected final Class<SVCSVP> serviceSVPClass;

    public BaseSchemeValuePairConverter(Class jaxbSVPClass, Class serviceSVPClass) {
        super(jaxbSVPClass, serviceSVPClass);
        this.jaxbSVPClass = jaxbSVPClass;
        this.serviceSVPClass = serviceSVPClass;
    }

    @Override
    public SVCSVP convertTo(JAXBSVP source, SVCSVP destination) {

        SVCSVP result = null;
        if ( source != null ) {

            try {

                result = ServiceHelper.findSchemeValuePair(serviceSVPClass,
                    JAXBHelper.getScheme(source), JAXBHelper.getValue(source));

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


        JAXBSVP result = null;
        if ( source != null ) {

            if ( destination != null ) {

                result = destination;
                JAXBHelper.setScheme(result, source.getScheme());
                JAXBHelper.setValue(result, source.getValue());

            } else {

                try {

                    result = (JAXBSVP)JAXBHelper.createJAXBSchemeValuePair(jaxbSVPClass, source.getScheme(), source.getValue());

                } catch (IllegalAccessException e) {

                    throw new MappingException(e);

                } catch (InstantiationException e) {

                    throw new MappingException(e);

                }

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

}
