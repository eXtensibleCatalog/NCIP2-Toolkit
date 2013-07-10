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
import org.dozer.util.MappingUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class EmptyFieldConverter extends DozerConverter<Object, Boolean> {

    protected static final Map<String, Constructor> defaultCtorsByClassName = new HashMap<String, Constructor>();

    public EmptyFieldConverter() {
        super(Object.class, Boolean.class);
    }

    @Override
    public Boolean convertTo(Object srcObj, Boolean targetBoolean) {

        Boolean result;
        if ( srcObj != null ) {

            result = Boolean.TRUE;

        } else {

            result = Boolean.FALSE;

        }

        return result;

    }

    @Override
    public Object convertFrom(Boolean srcBoolean, Object targetObj) {

        Object result = null;

        if (srcBoolean) {

            try {

                Constructor ctor = getDefaultConstructor(getParameter());

                result = ctor.newInstance();

            } catch (InstantiationException e) {

                throw new MappingException(e);

            } catch (IllegalAccessException e) {

                throw new MappingException(e);

            } catch (InvocationTargetException e) {

                throw new MappingException(e);

            }

        } else {

            result = null;

        }

        return result;

    }

    protected Constructor getDefaultConstructor(String className) {

        Constructor ctor = defaultCtorsByClassName.get(className);

        if ( ctor == null ) {

            try {

                Class<?> clazz = Class.forName(className);
                ctor = clazz.getConstructor();
                defaultCtorsByClassName.put(className, ctor);

            } catch (NoSuchMethodException e) {

                throw new MappingException(e);

            } catch (ClassNotFoundException e) {

                throw new MappingException(e);

            }

        }

        return ctor;
    }

}
