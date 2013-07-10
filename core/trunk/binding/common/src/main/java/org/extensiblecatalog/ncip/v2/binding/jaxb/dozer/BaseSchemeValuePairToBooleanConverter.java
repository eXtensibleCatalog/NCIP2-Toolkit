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
import org.extensiblecatalog.ncip.v2.service.*;

import java.lang.reflect.Field;
import java.util.*;

/**
 * This class assumes that all service package SchemeValuePair sub-classes have been populated.
 */
public class BaseSchemeValuePairToBooleanConverter<JAXBSVP> extends DozerConverter<List<JAXBSVP>, Boolean> {

    protected final Class<JAXBSVP> jaxbSVPClass;

    public BaseSchemeValuePairToBooleanConverter(Class<List<JAXBSVP>> jaxbSVPListClass, Class<JAXBSVP> jaxbSVPClass) {

        super(jaxbSVPListClass, Boolean.class);
        this.jaxbSVPClass = jaxbSVPClass;

    }

    @Override
    public Boolean convertTo(List srcObj, Boolean targetBoolean) {

        // TODO: This approach means that SVP elements that don't match a boolean flag attribute of the target class are silently ignored. It would be ideal to throw an exception
        Boolean result = Boolean.FALSE;

        if ( srcObj != null ) {

            try {

                SchemeValuePair svcSVP = getServiceSVP();
                // Search the list to see if it has a matching entry
                List<JAXBSVP> jaxbSVPList = (List<JAXBSVP>)srcObj;
                for ( JAXBSVP jaxbSVPObj : jaxbSVPList ) {

                    String scheme = JAXBHelper.getScheme(jaxbSVPObj);
                    String value = JAXBHelper.getValue(jaxbSVPObj);
                    if ( svcSVP.matches(scheme, value) )
                    {

                        result = Boolean.TRUE;
                        break;

                    }

                }

            } catch (ClassNotFoundException e) {

                throw new MappingException(e);

            } catch (NoSuchFieldException e) {

                throw new MappingException(e);

            } catch (IllegalAccessException e) {

                throw new MappingException(e);

            }

        } else {

            result = Boolean.FALSE;

        }

        return result;

    }

    @Override
    public List<JAXBSVP> convertFrom(Boolean srcBoolean, List targetObj) {

        List<JAXBSVP> result;

        if ( targetObj != null ) {

            result = (List<JAXBSVP>)targetObj;

        } else {

            result = new ArrayList<JAXBSVP>();

        }

        if ( srcBoolean ) {

            try {

                SchemeValuePair svcSVP = getServiceSVP();

                JAXBSVP jaxbSVPObject = (JAXBSVP)JAXBHelper.createJAXBSchemeValuePair(jaxbSVPClass, svcSVP.getScheme(), svcSVP.getValue());

                result.add(jaxbSVPObject);

            } catch (IllegalAccessException e) {

                throw new MappingException(e);

            } catch (ClassNotFoundException e) {

                throw new MappingException(e);

            } catch (NoSuchFieldException e) {

                throw new MappingException(e);

            } catch (InstantiationException e) {

                throw new MappingException(e);

            }

        } else {

            // Do nothing - leave the list alone

        }

        return result;

    }

    /**
     * Return the instance of the service package sub-class of SchemeValuePair identified by the
     * custom-converter-param on the dozer mapping's field element for this conversion.
     * @return
     * @throws ClassNotFoundException if there is no such sub-class as that in the custom-converter-param.
     * @throws NoSuchFieldException if there is no such field on the sub-class
     * @throws IllegalAccessException
     */
    protected SchemeValuePair getServiceSVP()
        throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {

        String valueClassAndField = getParameter();
        String className = valueClassAndField.substring(0, valueClassAndField.lastIndexOf("."));
        String fieldName = valueClassAndField.substring(valueClassAndField.lastIndexOf(".") + 1);
        Class svcSVPClass = Class.forName(className);
        Field svpField = svcSVPClass.getField(fieldName);
        SchemeValuePair svcSVP = (SchemeValuePair)svpField.get(null);
        return svcSVP;

    }

}
