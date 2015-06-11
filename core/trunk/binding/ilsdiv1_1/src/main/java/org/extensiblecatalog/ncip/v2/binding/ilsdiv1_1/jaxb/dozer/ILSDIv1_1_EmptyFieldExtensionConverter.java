/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.ilsdiv1_1.jaxb.dozer;


import org.extensiblecatalog.ncip.v2.binding.jaxb.dozer.*;
import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.dozer.MappingException;
import org.extensiblecatalog.ncip.v2.binding.jaxb.JAXBHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.extensiblecatalog.ncip.v2.binding.ilsdiv1_1.jaxb.elements.Ext;

public class ILSDIv1_1_EmptyFieldExtensionConverter extends DozerConverter<Object, Ext> implements MapperAware {

    protected Mapper mapper;

    protected Class aClass;
    protected Constructor aClassCtor;
    protected boolean aIsList = false;

    protected Class bClass;
    protected Constructor bClassCtor;
    protected boolean bIsList = false;

    public ILSDIv1_1_EmptyFieldExtensionConverter() {
        super(Object.class, Ext.class);
    }

    protected synchronized void lazyInit() {

        if ( aClassCtor == null || bClassCtor == null ) {

            String parameter = getParameter();
            String[] parameters = new String[2];
            		
            		String[] parametersSplitted = parameter.split(",");
            if (parametersSplitted.length == 1) {
            	parameters[0] = "java.lang.Boolean";
            	parameters[1] = parametersSplitted[0];
            } else {
            	parameters = parametersSplitted;
            }
            if ( parameters.length == 2 ) {
                String aParam = parameters[0];
                if ( aParam.matches("^List<.*>$") ) {

                    aIsList = true;
                    aParam = aParam.substring(5, aParam.length() - 1);
                }

                try {

                    aClass = Class.forName(aParam);
                    
                    if (parametersSplitted.length == 2)
                    	aClassCtor = aClass.getConstructor();

                } catch (ClassNotFoundException e) {

                    throw new MappingException("Could not find class \"" + aParam + "\".");

                } catch (NoSuchMethodException e) {

                    throw new MappingException("Could not find default constructor for \"" + aParam + "\".");

                }

                String bParam = parameters[1];
                if ( bParam.matches("^List<.*>$") ) {

                    bIsList = true;
                    bParam = bParam.substring(5, bParam.length() - 1);
                }
                try {

                    bClass = Class.forName(bParam);
                    bClassCtor = bClass.getConstructor();

                } catch (ClassNotFoundException e) {

                    throw new MappingException("Could not find class \"" + bParam + "\".");

                } catch (NoSuchMethodException e) {

                    throw new MappingException("Could not find default constructor for \"" + bParam + "\".");

                }

            } else {

                throw new MappingException("Parameter must be in the format \"a-class,b-class\", not \""
                    + parameter + "\".");

            }

        }

    }

    @Override
    public Ext convertTo(Object source, Ext destination) {

        lazyInit();
        
        // FIXME Check for possible misunderstanded errors caused by not really understanding what is this doing .. 
        if (source instanceof Boolean) {
        	Boolean bool = (Boolean) source;
        	if (! bool)
        		return null;
        }

        if ( destination == null ) {

            try {

                destination = (Ext)Ext.class.newInstance();

            } catch (InstantiationException e) {

                throw new MappingException(e);

            } catch (IllegalAccessException e) {

                throw new MappingException(e);

            }

        }

        if ( List.class.isAssignableFrom(source.getClass()) ) {

            List<Object> sourceList = (List)source;
            for ( Object srcObj : sourceList ) {

                Object innerObj = doConvert(srcObj, bClass, bClassCtor);
                JAXBHelper.addToExtension(destination, innerObj);

            }

        } else {

            Object innerObj = doConvert(source, bClass, bClassCtor);
            JAXBHelper.addToExtension(destination, innerObj);

        }

        return destination;

    }

    @Override
    public Object convertFrom(Ext source, Object destination) {

        Object result = destination;

        lazyInit();

        if ( source != null ) {

            // For every object in the extension
            for ( Object obj : JAXBHelper.getAnyList(source) ) {

                // Is this the type we're configured to convert?
                if ( bClass.isAssignableFrom(obj.getClass())) {

                    Object svcObj = doConvert(obj, aClass, aClassCtor);
                    if ( aIsList ) {

                        if ( result == null ) {

                            result = new ArrayList<Object>();

                        }
                        ((List)result).add(svcObj);

                    } else {

                        result = svcObj;

                    }

                    continue;

                }

            }

        }

        return result;

    }

    protected Object doConvert(Object source, Class clazz, Constructor ctor) {

        Object obj = null;

        if ( source != null ) {

            try {

                obj = ctor.newInstance();

                obj = mapper.map(source, clazz);


            } catch (InstantiationException e) {

                throw new MappingException("Exception converting from \"" + source.getClass().getName()
                    + "\" to \"" + ctor.getDeclaringClass().getName() + "\".");

            } catch (IllegalAccessException e) {

                throw new MappingException("Exception converting from \"" + source.getClass().getName()
                    + "\" to \"" + ctor.getDeclaringClass().getName() + "\".");

            } catch (InvocationTargetException e) {

                throw new MappingException("Exception converting from \"" + source.getClass().getName()
                    + "\" to \"" + ctor.getDeclaringClass().getName() + "\".");

            }

        }

        return obj;
        
    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

}
