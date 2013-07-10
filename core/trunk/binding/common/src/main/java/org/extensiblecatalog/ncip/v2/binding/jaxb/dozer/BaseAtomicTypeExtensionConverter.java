/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb.dozer;

import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.dozer.MappingException;
import org.extensiblecatalog.ncip.v2.binding.jaxb.JAXBHelper;
import org.extensiblecatalog.ncip.v2.service.ReflectionHelper;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public abstract class BaseAtomicTypeExtensionConverter<EXT> extends DozerConverter<Object, EXT> implements MapperAware {

    protected static final Map<String, Method> objectFactoryMethodsByName = new HashMap<String, Method>();

    protected Mapper mapper;

    protected String fieldName;
    protected Class svcClass;
    protected boolean svcClassIsList = false;

    public BaseAtomicTypeExtensionConverter(Class extensionClass) {
        super(Object.class, extensionClass);
    }

    @Override
    public EXT convertTo(Object source, EXT destination) {

        init();

        EXT ext;
        if ( destination == null ) {

            ext = createExtension();

        } else {

            ext = destination;

        }

        if ( List.class.isAssignableFrom(source.getClass()) ) {

            List<Object> sourceList = (List)source;
            for ( Object srcObj : sourceList ) {

                Object innerObj = createJAXBObject(srcObj, fieldName);
                JAXBHelper.addToExtension(ext, innerObj);

            }

        } else {

            Object innerObj = createJAXBObject(source, fieldName);
            JAXBHelper.addToExtension(ext, innerObj);

        }

        return ext;

    }

    @Override
    public Object convertFrom(EXT source, Object destination) {

        init();

        Object result = destination;

        if ( source != null ) {

            // For every object in the extension
            for ( Object obj : JAXBHelper.getAnyList(source) ) {

                // Is it an "Atomic Type", e.g. represented in JAXB as a JAXBElement?
                if ( JAXBElement.class.isAssignableFrom(obj.getClass())
                    && ((JAXBElement)obj).getName().getLocalPart().compareTo(fieldName) == 0 ) {

                    Object svcObj = mapper.map(obj, svcClass);
                    if ( svcClassIsList ) {

                        if ( result == null ) {

                            result = new ArrayList<Object>();

                        }
                        ((List)result).add(svcObj);

                    } else {

                        result = svcObj;

                    }

                }

            }

        }

        return result;

    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    protected void init() {

        String parameter = getParameter();
        String[] parameters = parameter.split(",");
        if ( parameters.length == 2 ) {
            String className = parameters[0];
            if ( className.matches("^List<.*>$") ) {

                svcClassIsList = true;
                className = className.substring(5, className.length() - 1);

            }

            try {

                svcClass = Class.forName(className);

            } catch (ClassNotFoundException e) {

                throw new MappingException("Could not find class \"" + className + "\".");

            }

            fieldName = parameters[1];

        } else {

            throw new MappingException("Parameter must be in the format \"className,fieldName\", not \""
                + parameter + "\".");

        }


    }

    // TODO: These three methods are similar to ones in BaseContentConverter; try to refactor into helper methods
    protected Object createJAXBObject(Object svcObj, String fieldName) {

        Object jaxbFieldObj;

        Class svcClass = svcObj.getClass();

        String createMethodName;
        if ( fieldName.endsWith("s")) {
            createMethodName = "create" + fieldName.substring(0, fieldName.length() - 2);
        } else {
            createMethodName = "create" + fieldName;
        }

        if ( BigDecimal.class.isAssignableFrom(svcClass) ) {

            jaxbFieldObj = callObjectFactory(createMethodName, svcObj);

        } else if ( String.class.isAssignableFrom(svcClass) ) {

            jaxbFieldObj = callObjectFactory(createMethodName, svcObj);

        } else if ( GregorianCalendar.class.isAssignableFrom(svcClass) ) {

            XMLGregorianCalendar xmlGregorianCalendar;

            try {

                xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)svcObj);

            } catch (DatatypeConfigurationException e) {

                throw new MappingException("Could not convert this date: " + svcObj.toString(), e);

            }

            jaxbFieldObj = callObjectFactory(createMethodName, xmlGregorianCalendar);

        } else {

            throw new MappingException("This converter does not handle the '" + svcClass.getName() + "' class.");

        }

        return jaxbFieldObj;
    }

    protected Object callObjectFactory(String methodName, Object srcObj) {

        Object jaxbObject;

        Method objFactoryMethod = getObjectFactoryMethod(methodName, srcObj.getClass());

        try {

            jaxbObject = objFactoryMethod.invoke(getObjectFactory(), srcObj);

        } catch (IllegalAccessException e) {

            throw new MappingException("Exception creating JAXB object.", e);

        } catch (InvocationTargetException e) {

            throw new MappingException("Exception creating JAXB object.", e);

        }

        return jaxbObject;

    }

    protected Method getObjectFactoryMethod(String methodName, Class srcObjClass) {

        // Note that this assumes that there's only ever one method by this name, i.e. it doesn't
        // account for different parameter types
        Method method = objectFactoryMethodsByName.get(methodName);

        if ( method == null) {

            method = ReflectionHelper.findMethod(getObjectFactory().getClass(), methodName, srcObjClass);
            objectFactoryMethodsByName.put(methodName, method);

        }

        return method;

    }

    protected abstract EXT createExtension();
    protected abstract Object getObjectFactory();
}
