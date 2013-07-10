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
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Ext;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class EmptyFieldExtensionConverter extends DozerConverter<Boolean, Ext> implements MapperAware {

    protected Mapper mapper;

    protected Class clazz;
    protected Constructor classCtor;
    protected boolean isList = false;

    public EmptyFieldExtensionConverter() {
        super(Boolean.class, Ext.class);
    }

    protected synchronized void lazyInit() {

        if ( classCtor == null ) {

            String parameter = getParameter();
            if ( parameter.matches("^List<.*>$") ) {

                isList = true;
                parameter = parameter.substring(5, parameter.length() - 1);
            }

            try {

                clazz = Class.forName(parameter);
                classCtor = clazz.getConstructor();

            } catch (ClassNotFoundException e) {

                throw new MappingException("Could not find class \"" + parameter + "\".");

            } catch (NoSuchMethodException e) {

                throw new MappingException("Could not find default constructor for \"" + parameter + "\".");

            }

        }

    }

    @Override
    public Ext convertTo(Boolean source, Ext destination) {

        lazyInit();

        if ( source != null && source ) {

            Object innerObj = doConvert(source, clazz, classCtor);

            if ( destination == null ) {

                destination = new org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Ext();

            }

            destination.getAny().add(innerObj);

        }

        return destination;

    }

    @Override
    public Boolean convertFrom(Ext source, Boolean destination) {

        Boolean result = destination;

        lazyInit();

        if ( source != null ) {

            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Ext ext =
                (org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Ext)source;

            // For every object in the extension
            for ( Object obj : ext.getAny() ) {

                // Is this the type we're configured to convert?
                if ( clazz.isAssignableFrom(obj.getClass())) {

                    result = Boolean.TRUE;
                    break;

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
