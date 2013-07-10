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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BaseExtensionConverter<EXT> extends DozerConverter<Object, EXT> implements MapperAware {

    protected Mapper mapper;

    protected Class aClass;
    protected Constructor aClassCtor;
    protected boolean aIsList = false;

    protected Class bClass;
    protected Constructor bClassCtor;
    protected boolean bIsList = false;

    protected final Class jaxbExtClass;

    public BaseExtensionConverter(Class<EXT> jaxbExtClass) {
        super(Object.class, jaxbExtClass);
        this.jaxbExtClass = jaxbExtClass;
    }

    protected synchronized void lazyInit() {

        if ( aClassCtor == null || bClassCtor == null ) {

            String parameter = getParameter();
            String[] parameters = parameter.split(",");
            if ( parameters.length == 2 ) {
                String aParam = parameters[0];
                if ( aParam.matches("^List<.*>$") ) {

                    aIsList = true;
                    aParam = aParam.substring(5, aParam.length() - 1);
                }

                try {

                    aClass = Class.forName(aParam);
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
    public EXT convertTo(Object source, EXT destination) {

        lazyInit();

        if ( destination == null ) {

            try {

                destination = (EXT)jaxbExtClass.newInstance();

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
    public Object convertFrom(EXT source, Object destination) {

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
