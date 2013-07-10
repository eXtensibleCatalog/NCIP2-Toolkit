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

public class ExtensionConverter extends DozerConverter<Object, Ext> implements MapperAware {

    protected Mapper mapper;
    protected Class aClass;
    protected Constructor aClassCtor;
    protected Class bClass;
    protected Constructor bClassCtor;

    public ExtensionConverter() {
        super(Object.class, Ext.class);
    }

    protected synchronized void lazyInit() {

        if ( aClassCtor == null || bClassCtor == null ) {

            String parameter = getParameter();
            String[] parameters = parameter.split(",");
            if ( parameters.length == 2 ) {
                String aParam = parameters[0];
                try {

                    aClass = Class.forName(aParam);
                    aClassCtor = aClass.getConstructor();

                } catch (ClassNotFoundException e) {

                    throw new MappingException("Could not find class \"" + aParam + "\".");

                } catch (NoSuchMethodException e) {

                    throw new MappingException("Could not find default constructor for \"" + aParam + "\".");

                }

                String bParam = parameters[1];
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

        Object innerObj = doConvert(source, destination, bClass, bClassCtor);
        org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Ext ext;
        if ( destination == null ) {

            ext = new org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Ext();

        } else {

            ext = (org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Ext)destination;

        }
        ext.getAny().add(innerObj);

        return ext;

    }

    @Override
    public Object convertFrom(Ext source, Object destination) {

        Object result = null;

        lazyInit();

        if ( source != null ) {

            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Ext ext =
                (org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Ext)source;

            for ( Object obj : ext.getAny() ) {

                if ( bClass.isAssignableFrom(obj.getClass())) {

                    result = doConvert(obj, destination, aClass, aClassCtor);
                    // TODO: Handle conversion to collections of aClass (e.g. List<AccountDetails>).
                    break;

                }

            }

        }

        return result;

    }

    protected Object doConvert(Object source, Object destination, Class clazz, Constructor ctor) {

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
