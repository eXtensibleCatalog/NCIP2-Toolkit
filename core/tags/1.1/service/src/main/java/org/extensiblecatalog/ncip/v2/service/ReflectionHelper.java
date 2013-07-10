/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;


public final class ReflectionHelper {

    private static final Logger LOG = Logger.getLogger(ReflectionHelper.class);

    private ReflectionHelper() {

        // Private constructor to prevent instantiation.

    }

    /**
     * Handles case-insensitive lookups
     * Note: Tests for plural form of fieldName, e.g. when the field is a collection
     *
     * @param objClass
     * @param fieldName
     * @return
     */
    public static Field findField(Class objClass, String fieldName) {

        LOG.debug("Looking for field " + fieldName + " in " + objClass.getName());
        Field result = null;

        Field[] fields = objClass.getDeclaredFields();
        for ( Field f : fields ) {

            if ( f.getName().compareToIgnoreCase(fieldName) == 0 ) {

                result = f;
                break;

            }
        }

        // Handle collections
        if ( result == null ) {

            fields = objClass.getDeclaredFields();
            for ( Field f : fields ) {

                if ( f.getName().compareToIgnoreCase(fieldName + "s") == 0 ) {

                    result = f;
                    break;

                }
            }

        }

        return result;

    }

    /**
     * Create an object of the objClass by calling the constructor that matches the parameters.
     * @param objClass
     * @param parameters
     * @return
     */
    public static Object createObject(Class<?> objClass, Object ... parameters) throws ToolkitException {

        Object obj;
        try {

            Class[] parmTypes;
            if ( parameters != null ) {

                parmTypes = new Class[parameters.length];
                for ( int index = 0; index < parameters.length; ++index ) {

                    parmTypes[index] = parameters[index].getClass();

                    // Strip quote marks from around strings
                    // Note: this would change the type of objects that are sub-classes of String into String,
                    // but as String is a final class there can't actually be any sub-types of String,
                    // so this is safe to do.
                    if ( parameters[index] instanceof String ) {

                        String temp = (String)parameters[index];
                        if ( temp.startsWith("\"") && temp.endsWith("\"") ) {

                            parameters[index] = temp.substring(1, temp.length() - 1);

                        }
                    }

                }

            } else {

                parmTypes = new Class[0];

            }

            Constructor ctor = objClass.getConstructor(parmTypes);
            obj = ctor.newInstance(parameters);

        } catch (InstantiationException e) {

            throw new ToolkitException("Exception constructing an instance of " + objClass, e);

        } catch (IllegalAccessException e) {

            throw new ToolkitException("Exception constructing an instance of " + objClass, e);

        } catch (NoSuchMethodException e) {

            throw new ToolkitException("Exception constructing an instance of " + objClass, e);

        } catch (InvocationTargetException e) {

            throw new ToolkitException("Exception constructing an instance of " + objClass, e);

        }

        return obj;

    }

    /**
     * Return the Method object for the given methodName and parameterTypes on the given objClass.
     * @param objClass
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method findMethod(Class objClass, String methodName, Class ... parameterTypes) {

        LOG.debug("Looking for method " + methodName + "(" + formatClassNames(parameterTypes) + ") on "
            + objClass.getName() + ".");
        Method result = null;

        Method[] methods = objClass.getDeclaredMethods();
        for ( Method m : methods ) {

            if ( m.getName().compareToIgnoreCase(methodName) == 0 ) {

                Class[] methodParameterTypes = m.getParameterTypes();
                if ( parametersMatch(methodParameterTypes, parameterTypes) ) {

                    result = m;
                    break;
                }

            }

        }

        // Handle collections
        if ( result == null ) {

            methods = objClass.getDeclaredMethods();
            for ( Method m : methods ) {

                if ( m.getName().compareToIgnoreCase(methodName + "s") == 0 ) {

                    Class[] methodParameterTypes = m.getParameterTypes();
                    if ( parametersMatch(methodParameterTypes, parameterTypes) ) {

                        result = m;
                        break;
                    }

                }

            }

        }

        if ( result == null ) {
            LOG.debug("Method " + methodName + "(" + formatClassNames(parameterTypes) + ") on "
                + objClass.getName() + " not found.");
        }
        return result;

    }

    /**
     * Convenience method for {@link #determineClass(Object, Class, String)} which passes the class of the obj parameter
     * for the objClass parameter.
     * @param obj
     * @param fieldName
     * @return
     * @throws ToolkitException
     */
    public static Class determineClass(final Object obj, String fieldName) throws ToolkitException {

        return determineClass(obj, obj.getClass(), fieldName);

    }

    /**
     * Given an object, its class and the name of a field on that object, try to deduce the field's Class.
     * Note: Does test for plural form of fieldName, e.g. when the field is a collection
     * @param obj
     * @param fieldName
     * @return
     */
    public static Class determineClass(Object obj, Class objClass, String fieldName) throws ToolkitException {

        Class fieldClass;

        Field field = ReflectionHelper.findField(objClass, fieldName);

        if ( field != null ) {

            fieldClass = field.getType();

            if ( Collection.class.isAssignableFrom(fieldClass)) {

                // This is why we require an indexed getter for our collections so we can determine the return type
                Method method = findMethod(objClass, "get" + fieldName, int.class);

                if ( method != null ) {

                    fieldClass = method.getReturnType();

                } else {

                    throw new ToolkitException("Can not determine class of Collection field '" + fieldName + "' in "
                            + obj.getClass().getName() + "; perhaps there is no indexed getter?");

                }

            }

        } else {

            throw new ToolkitException("No such field '" + fieldName + "' in " + obj.getClass().getName());

        }

        return fieldClass;

    }

    /**
     * Format class names
     * @param classes
     * @return
     */
    public static String formatClassNames(Class ... classes) {

        String result = "";
        if ( classes != null && classes.length > 0 ) {

            StringBuilder sb = new StringBuilder();
            for ( Class c : classes ) {

                sb.append(c.getName()).append(", ");
            }
            result = sb.toString();
            result = result.substring(0, result.length() - 2);

        }
        return result;

    }

    /**
     * Compare two arrays of classes and return true if they are identical.
     * @param methodParameterTypes
     * @param parameterTypes
     * @return
     */
    public static boolean parametersMatch(Class<?>[] methodParameterTypes, Class[] parameterTypes) {

        boolean parameterTypesMatch = true;

        if ( methodParameterTypes.length == 0 && ( parameterTypes == null || parameterTypes.length == 0 ) ) {

            // Do nothing - parameter types match

        } else if ( parameterTypes != null && parameterTypes.length == methodParameterTypes.length ) {

            for ( int i = 0; i < parameterTypes.length; i++ ) {

                if ( !methodParameterTypes[i].isAssignableFrom(parameterTypes[i]) ) {

                    parameterTypesMatch = false;
                    break;

                }

            }

        } else {

            parameterTypesMatch = false;

        }

        return parameterTypesMatch;

    }

    /**
     * Note: Does test for plural form of fieldName, e.g. when the field is a collection
     * @param objClass
     * @param fieldName
     * @return
     */
    public static boolean isFieldACollection(Class objClass, String fieldName) {

        boolean isCollection = false;

        Class fieldClass;

        Field field = ReflectionHelper.findField(objClass, fieldName);

        if ( field != null ) {

            fieldClass = field.getType();

            if ( isCollection(fieldClass)) {

                isCollection = true;

            }
        }

        return isCollection;

    }

    /**
     * Convenience method for {@link java.util.Collection#getClass()#isAssignableFrom(Class)}.
     * @param clazz
     * @return
     */
    public static boolean isCollection(Class clazz) {

        return Collection.class.isAssignableFrom(clazz);

    }

    /**
     * Return the class name, minus the package name (if any).
     * @param clazz
     * @return
     */
    public static String getSimpleClassName(Class clazz) {

        String simpleClassName = clazz.getName();
        // Remove package name, if any
        int lastDot = simpleClassName.lastIndexOf(".");
        if ( lastDot > 0 ) {
            simpleClassName = simpleClassName.substring(lastDot + 1);
        }

        return simpleClassName;

    }

    /**
     * Iterate over the fields, find the first {@Link NCIPData} field with a getter that is non-null
     * and return it.
     *
     * @param wrapper
     * @return
     */
    public static NCIPData unwrapFirstNonNullNCIPDataFieldViaGetter(Object wrapper)
        throws IllegalAccessException, InvocationTargetException {

        NCIPData result = null;

        if ( wrapper != null ) {

            Class objClass = wrapper.getClass();
            Field[] fields = objClass.getDeclaredFields();
            for ( Field f : fields ) {

                Method m = findMethod(objClass, "get" + f.getName());

                if ( m != null ) {

                    Object obj = m.invoke(wrapper);
                    if ( obj != null && NCIPData.class.isAssignableFrom(obj.getClass())) {

                        result = (NCIPData)obj;
                        break;

                    }

                }

            }

        }

        return result;

    }

    public static void setField(Object obj, Object field, String fieldName) throws InvocationTargetException, IllegalAccessException, ToolkitException {

        if ( field != null ) {

            Class objClass = obj.getClass();
            Method setterMethod = findMethod(objClass, "set" + fieldName, field.getClass());
            if ( setterMethod != null) {

                setterMethod.invoke(obj, field);

            } else {

                throw new ToolkitException("No such field '" + fieldName + "' in " + objClass.getName());

            }
        }

    }

    public static List<Problem> getProblems(NCIPResponseData responseData)
        throws InvocationTargetException, IllegalAccessException {

        List<Problem> problems = null;

        Method getProblemsMethod = ReflectionHelper.findMethod(responseData.getClass(), "getProblems");

        if ( getProblemsMethod != null ) {

            problems = (List<Problem>)getProblemsMethod.invoke(responseData);

        }

        return problems;


    }

    public static NCIPInitiationData getInitiationData(org.extensiblecatalog.ncip.v2.service.NCIPMessage svcMessage) throws InvocationTargetException, IllegalAccessException {

        NCIPInitiationData initData = null;
        // TODO: If necessary, make this smart enough to avoid returning ProblemResponseData when its problem list is empty
        initData = (NCIPInitiationData)ReflectionHelper.unwrapFirstNonNullNCIPDataFieldViaGetter(svcMessage);
        return initData;

    }

    public static NCIPResponseData getResponseData(org.extensiblecatalog.ncip.v2.service.NCIPMessage svcMessage) throws InvocationTargetException, IllegalAccessException {

        NCIPResponseData respData = null;
        // TODO: If necessary, make this smart enough to avoid returning ProblemResponseData when its problem list is empty
        respData = (NCIPResponseData)ReflectionHelper.unwrapFirstNonNullNCIPDataFieldViaGetter(svcMessage);
        return respData;

    }

}
