/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb.dozer;

import org.apache.log4j.Logger;
import org.dozer.BeanFactory;
import org.dozer.DozerBeanMapper;
import org.dozer.util.MappingUtils;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ObjectFactory;
import org.extensiblecatalog.ncip.v2.common.ReflectionHelper;
import org.extensiblecatalog.ncip.v2.service.SchemeValuePair;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public class ContentMappingFactory implements BeanFactory {

    private static final Logger LOG = Logger.getLogger(ContentMappingFactory.class);

    protected static final Map<String, Method> objectFactoryMethodsByName = new HashMap<String, Method>();
    protected static final Map<String, Constructor> defaultCtorsByClassName = new HashMap<String, Constructor>();

    // JAXB uses a "catch-all" approach for certain content models occurring in NCIP (e.g. this one:
    /*
        <xsd:choice>
            <xsd:element ref = "ItemId"/>
            <xsd:sequence>
                <xsd:element ref = "RequestId"/>
                <xsd:element ref = "ItemId" minOccurs = "0"/>
            </xsd:sequence>
         </xsd:choice>
     */
    // This catch-all approach is implemented by putting the child elements into a List<Object> which is
    // accessed via the getContent() method of the parent class. JAXB requires the application code
    // to put elements into the content list *in the order they are to be marshaled.**
    // To meet that expectation this class has to "know" the order of child elements for those classes.
    // This is hard-coded below and should the scheme be changed this might need changing too.
    // A more dynamic approach would be to determine this at start-up time from the schema itself.
    // A much less difficult approach would be to figure out how to put this element ordering into the
    // dozer mapping file rather than into this class; at least then the element-order information would
    // be in the same place where the element is configured to use this ContentMappingFactory.
    protected static final Map<String, List<String>> elementOrderByParentElementName
        = new HashMap<String, List<String>>();

    protected static final List<String> cancelRequestItemElementOrder
        = new ArrayList<String>();

    static {

        cancelRequestItemElementOrder.add("InitiationHeader");
        cancelRequestItemElementOrder.add("MandatedAction");
        cancelRequestItemElementOrder.add("UserId");
        cancelRequestItemElementOrder.add("AuthenticationInput");
        cancelRequestItemElementOrder.add("RequestId");
        cancelRequestItemElementOrder.add("ItemId");
        cancelRequestItemElementOrder.add("RequestType");
        cancelRequestItemElementOrder.add("RequestScopeType");
        cancelRequestItemElementOrder.add("AcknowledgedFeeAmount");
        cancelRequestItemElementOrder.add("PaidFeeAmount");
        cancelRequestItemElementOrder.add("ItemElementType");
        cancelRequestItemElementOrder.add("UserElementType");

    }

    protected static final List<String> cancelRequestItemResponseElementOrder = new ArrayList<String>();

    static {

        cancelRequestItemResponseElementOrder.add("ResponseHeader");
        cancelRequestItemResponseElementOrder.add("Problem");
        cancelRequestItemResponseElementOrder.add("RequestId");
        cancelRequestItemResponseElementOrder.add("ItemId");
        cancelRequestItemResponseElementOrder.add("UserId");
        cancelRequestItemResponseElementOrder.add("FiscalTransactionInformation");
        cancelRequestItemResponseElementOrder.add("ItemOptionalFields");
        cancelRequestItemResponseElementOrder.add("UserOptionalFields");

    }

    protected static final List<String> chronologyLevelInstanceElementOrder = new ArrayList<String>();

    static {

        chronologyLevelInstanceElementOrder.add("ChronologyLevel");
        chronologyLevelInstanceElementOrder.add("ChronologyCaption");
        chronologyLevelInstanceElementOrder.add("ChronologyValue");

    }

    protected static final List<String> destinationElementOrder = new ArrayList<String>();

    static {

        destinationElementOrder.add("BinNumber");
        destinationElementOrder.add("Location");

    }

    protected static final List<String> enumerationLevelInstanceElementOrder = new ArrayList<String>();

    static {

        enumerationLevelInstanceElementOrder.add("EnumerationLevel");
        enumerationLevelInstanceElementOrder.add("EnumerationCaption");
        enumerationLevelInstanceElementOrder.add("EnumerationValue");

    }

    protected static final List<String> itemRequestCancelledElementOrder = new ArrayList<String>();

    static {

        itemRequestCancelledElementOrder.add("InitiationHeader");
        itemRequestCancelledElementOrder.add("UserId");
        itemRequestCancelledElementOrder.add("RequestId");
        itemRequestCancelledElementOrder.add("ItemId");
        itemRequestCancelledElementOrder.add("RequestType");
        itemRequestCancelledElementOrder.add("RequestScopeType");
        itemRequestCancelledElementOrder.add("FiscalTransactionInformation");
        itemRequestCancelledElementOrder.add("ItemOptionalFields");
        itemRequestCancelledElementOrder.add("UserOptionalFields");

    }

    protected static final List<String> itemShippedElementOrder = new ArrayList<String>();

    static {

        itemShippedElementOrder.add("InitiationHeader");
        itemShippedElementOrder.add("RequestId");
        itemShippedElementOrder.add("ItemId");
        itemShippedElementOrder.add("UserId");
        itemShippedElementOrder.add("DateShipped");
        itemShippedElementOrder.add("ShippingInformation");
        itemShippedElementOrder.add("ItemOptionalFields");
        itemShippedElementOrder.add("UserOptionalFields");

    }

    protected static final List<String> lookupItemResponseElementOrder = new ArrayList<String>();

    static {

        lookupItemResponseElementOrder.add("ResponseHeader");
        lookupItemResponseElementOrder.add("Problem");
        lookupItemResponseElementOrder.add("RequestId");
        lookupItemResponseElementOrder.add("ItemId");
        lookupItemResponseElementOrder.add("HoldPickupDate");
        lookupItemResponseElementOrder.add("DateRecalled");
        lookupItemResponseElementOrder.add("ItemTransaction");
        lookupItemResponseElementOrder.add("ItemOptionalFields");

    }

    protected static final List<String> lookupRequestResponseElementOrder = new ArrayList<String>();

    static {

        lookupRequestResponseElementOrder.add("ResponseHeader");
        lookupRequestResponseElementOrder.add("Problem");
        lookupRequestResponseElementOrder.add("RequestId");
        lookupRequestResponseElementOrder.add("ItemId");
        lookupRequestResponseElementOrder.add("UserId");
        lookupRequestResponseElementOrder.add("RequestType");
        lookupRequestResponseElementOrder.add("RequestScopeType");
        lookupRequestResponseElementOrder.add("RequestStatusType");
        lookupRequestResponseElementOrder.add("HoldQueuePosition");
        lookupRequestResponseElementOrder.add("ShippingInformation");
        lookupRequestResponseElementOrder.add("EarliestDateNeeded");
        lookupRequestResponseElementOrder.add("NeedBeforeDate");
        lookupRequestResponseElementOrder.add("PickupDate");
        lookupRequestResponseElementOrder.add("PickupLocation");
        lookupRequestResponseElementOrder.add("PickupExpiryDate");
        lookupRequestResponseElementOrder.add("DateOfUserRequest");
        lookupRequestResponseElementOrder.add("DateAvailable");
        lookupRequestResponseElementOrder.add("AcknowledgedFeeAmount");
        lookupRequestResponseElementOrder.add("PaidFeeAmount");
        lookupRequestResponseElementOrder.add("ItemOptionalFields");
        lookupRequestResponseElementOrder.add("UserOptionalFields");

    }

    protected static final List<String> personalNameInformationElementOrder = new ArrayList<String>();

    static {

        personalNameInformationElementOrder.add("StructuredPersonalUserName");
        personalNameInformationElementOrder.add("UnstructuredPersonalUserName");

    }

    protected static final List<String> requestedItemElementOrder = new ArrayList<String>();

    static {

        requestedItemElementOrder.add("RequestId");
        requestedItemElementOrder.add("ItemId");
        requestedItemElementOrder.add("RequestType");
        requestedItemElementOrder.add("RequestStatusType");
        requestedItemElementOrder.add("DatePlaced");
        requestedItemElementOrder.add("PickupDate");
        requestedItemElementOrder.add("PickupLocation");
        requestedItemElementOrder.add("PickupExpiryDate");
        requestedItemElementOrder.add("ReminderLevel");
        requestedItemElementOrder.add("HoldQueuePosition");
        requestedItemElementOrder.add("Title");
        requestedItemElementOrder.add("MediumType");

    }

    protected static final List<String> requestItemResponseElementOrder = new ArrayList<String>();

    static {

        requestItemResponseElementOrder.add("ResponseHeader");
        requestItemResponseElementOrder.add("Problem");
        requestItemResponseElementOrder.add("RequiredFeeAmount");
        requestItemResponseElementOrder.add("RequiredItemUseRestrictionType");
        requestItemResponseElementOrder.add("RequestId");
        requestItemResponseElementOrder.add("ItemId");
        requestItemResponseElementOrder.add("UserId");
        requestItemResponseElementOrder.add("RequestType");
        requestItemResponseElementOrder.add("RequestScopeType");
        requestItemResponseElementOrder.add("ShippingInformation");
        requestItemResponseElementOrder.add("DateAvailable");
        requestItemResponseElementOrder.add("HoldPickupDate");
        requestItemResponseElementOrder.add("FiscalTransactionInformation");
        requestItemResponseElementOrder.add("ItemOptionalFields");
        requestItemResponseElementOrder.add("UserOptionalFields");

    }

    protected static final List<String> structuredAddressElementOrder = new ArrayList<String>();

    static {

        structuredAddressElementOrder.add("LocationWithinBuilding");
        structuredAddressElementOrder.add("HouseName");
        structuredAddressElementOrder.add("Street");
        structuredAddressElementOrder.add("PostOfficeBox");
        structuredAddressElementOrder.add("District");
        structuredAddressElementOrder.add("Line1");
        structuredAddressElementOrder.add("Line2");
        structuredAddressElementOrder.add("Locality");
        structuredAddressElementOrder.add("Region");
        structuredAddressElementOrder.add("Country");
        structuredAddressElementOrder.add("PostalCode");
        structuredAddressElementOrder.add("CareOf");

    }

    protected static final List<String> structuredHoldingsDataElementOrder = new ArrayList<String>();

    static {

        structuredHoldingsDataElementOrder.add("HoldingsEnumeration");
        structuredHoldingsDataElementOrder.add("HoldingsChronology");

    }

    static {

        elementOrderByParentElementName.put(
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.CancelRequestItem.class.getName(),
            cancelRequestItemElementOrder);
        elementOrderByParentElementName.put(
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.CancelRequestItemResponse.class.getName(),
            cancelRequestItemResponseElementOrder);
        elementOrderByParentElementName.put(
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ChronologyLevelInstance.class.getName(),
            chronologyLevelInstanceElementOrder);
        elementOrderByParentElementName.put(
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Destination.class.getName(),
            destinationElementOrder);
        elementOrderByParentElementName.put(
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.EnumerationLevelInstance.class.getName(),
            enumerationLevelInstanceElementOrder);
        elementOrderByParentElementName.put(
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ItemRequestCancelled.class.getName(),
            itemRequestCancelledElementOrder);
        elementOrderByParentElementName.put(
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ItemShipped.class.getName(),
            itemShippedElementOrder);
        elementOrderByParentElementName.put(
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.LookupItemResponse.class.getName(),
            lookupItemResponseElementOrder);
        elementOrderByParentElementName.put(
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.LookupRequestResponse.class.getName(),
            lookupRequestResponseElementOrder);
        elementOrderByParentElementName.put(
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.PersonalNameInformation.class.getName(),
            personalNameInformationElementOrder);
        elementOrderByParentElementName.put(
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.RequestedItem.class.getName(),
            requestedItemElementOrder);
        elementOrderByParentElementName.put(
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.RequestItemResponse.class.getName(),
            requestItemResponseElementOrder);
        elementOrderByParentElementName.put(
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.StructuredAddress.class.getName(),
            structuredAddressElementOrder);
        elementOrderByParentElementName.put(
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.StructuredHoldingsData.class.getName(),
            structuredHoldingsDataElementOrder);

    }

    static final DozerBeanMapper mapper;

    static {

        mapper = new DozerBeanMapper();
        List<String> mappingFilesList = new ArrayList<String>();
        mappingFilesList.add("ncipv2_mappings.xml");
        mapper.setMappingFiles(mappingFilesList);

    }

    protected final ObjectFactory objectFactory = new ObjectFactory();

    @Override
    public Object createBean(Object srcObj, Class<?> srcClass, String targetBeanId) {

        Object result = null;

        if ( targetBeanId.contains("org.extensiblecatalog.ncip.v2.service.") ) {

            // We're mapping a JAXB-generated object, via its getContent method, to a class from the service package.
            Constructor ctor = getDefaultConstructor(targetBeanId);

            try {

                Object svcObj = ctor.newInstance();

                Method getContentMethod = srcClass.getMethod("getContent");
                List<Object> contentList = (List<Object>)getContentMethod.invoke(srcObj);

                for ( Object jaxbFieldObj : contentList ) {

                    String elementName;
                    if (jaxbFieldObj instanceof JAXBElement) {

                        JAXBElement jaxbElement = (JAXBElement)jaxbFieldObj;
                        elementName = jaxbElement.getName().getLocalPart();

                    } else {

                        elementName = jaxbFieldObj.getClass().getName();
                        // Remove package name, if any
                        int lastDot = elementName.lastIndexOf(".");
                        if ( lastDot > 0 ) {
                            elementName = elementName.substring(lastDot + 1);
                        }

                    }

                    Field field = ReflectionHelper.findField(svcObj.getClass(), elementName);

                    if ( field != null ) {

                        Class svcFieldClass = field.getType();
                        if ( java.util.List.class.isAssignableFrom(svcFieldClass)) {
                            Object svcFieldObj = mapToSVCObject(jaxbFieldObj);
                            Method getMethod = ReflectionHelper.findMethod(svcObj.getClass(),
                                "get" + elementName + "s");
                            List svcList = (List)getMethod.invoke(svcObj);
                            if ( svcList == null ) {
                                svcList = new ArrayList();
                                Method setMethod = ReflectionHelper.findMethod(svcObj.getClass(),
                                    "set" + elementName + "s", java.util.List.class);
                                setMethod.invoke(svcObj, svcList);
                            }

                            svcList.add(svcFieldObj);

                        } else {
                            Object svcFieldObj = mapper.map(jaxbFieldObj, svcFieldClass);
                            Method setMethod = ReflectionHelper.findMethod(svcObj.getClass(), "set" + elementName,
                                svcFieldClass);
                            setMethod.invoke(svcObj, svcFieldObj);
                        }

                    } else {

                        LOG.debug("content list contained an element (" + elementName
                            + ") for which no similarly-named field was found on the service object ("
                            + svcObj.getClass().getName() + "); skipping that field.");

                    }

                }

                result = svcObj;

            } catch (NoSuchMethodException e) {

                MappingUtils.throwMappingException(e);

            } catch (InvocationTargetException e) {

                MappingUtils.throwMappingException(e);

            } catch (IllegalAccessException e) {

                MappingUtils.throwMappingException(e);

            } catch (InstantiationException e) {

                MappingUtils.throwMappingException(e);

            }


        } else {

            // We're mapping from a class in the service package to a JAXB-generated object via its getContent method
            Constructor ctor = getDefaultConstructor(targetBeanId);

            try {

                Object jaxbObj = ctor.newInstance();
                Method getContentMethod = jaxbObj.getClass().getMethod("getContent");
                List<Object> jaxbContentList = (List<Object>) getContentMethod.invoke(jaxbObj);
                List<Object> workingJAXBContentList = new ArrayList<Object>();

                Method[] svcMethods = srcObj.getClass().getMethods();
                for ( Method svcGetMethod : svcMethods ) {

                    if ( svcGetMethod.getName().startsWith("get") ) {

                        LOG.debug("Method " + svcGetMethod.getName() + " is a getter.");
                        if ( svcGetMethod.getParameterTypes().length == 0 ) {

                            Object svcFieldObj = svcGetMethod.invoke(srcObj);
                            if ( svcFieldObj != null ) {

                                Class svcFieldClass = svcGetMethod.getReturnType();

                                if ( SchemeValuePair.class.getName().compareTo(svcFieldClass.getName()) == 0 ) {

                                    MappingUtils.throwMappingException("ContentMappingFactory found field named '"
                                        + svcGetMethod.getName().substring("get".length())
                                        + "' of type SchemeValuePair in " + srcObj.getClass()
                                        + "; this field should have a more narrow type.");

                                } else if ( svcFieldClass.getName().compareToIgnoreCase(
                                    java.lang.Class.class.getName()) == 0 ) {

                                    LOG.debug("Skipping " + svcGetMethod.getName()
                                        + " - this is not actually a data field.");

                                } else if ( java.util.List.class.isAssignableFrom(svcFieldClass) ) {

                                    // Get everything in this list and map that
                                    List svcFieldObjList = (List)svcFieldObj;
                                    for ( Object innerSVCFieldObj : svcFieldObjList) {

                                        String jaxbFactoryMethodName = makeJAXBObjectFactoryMethodName(
                                            svcGetMethod.getName());
                                        Object jaxbFieldObj = mapToJAXBObject(innerSVCFieldObj, jaxbFactoryMethodName,
                                            innerSVCFieldObj);
                                        if ( jaxbFieldObj != null ) {
                                            workingJAXBContentList.add(jaxbFieldObj);
                                        }
                                    }

                                } else {

                                    String jaxbFactoryMethodName = makeJAXBObjectFactoryMethodName(
                                        svcGetMethod.getName());
                                    Object jaxbFieldObj = mapToJAXBObject(svcFieldObj, jaxbFactoryMethodName,
                                        svcFieldObj);
                                    if ( jaxbFieldObj != null ) {
                                        workingJAXBContentList.add(jaxbFieldObj);
                                    }
                                }


                            } // If the srcObj's field is null there's nothing to copy

                        } else {

                            LOG.debug("Method " + svcGetMethod.getName() + " has parameters; skipping.");
                        }

                    }

                }

                jaxbContentList.clear();
                if ( workingJAXBContentList.size() > 0 ) {

                    ContentMappingComparator comparator = new ContentMappingComparator(
                        elementOrderByParentElementName.get(targetBeanId));
                    java.util.Collections.sort(workingJAXBContentList, comparator);

                    jaxbContentList.addAll(workingJAXBContentList);

                }

                result = jaxbObj;


            } catch (InstantiationException e) {

                MappingUtils.throwMappingException(e);

            } catch (IllegalAccessException e) {

                MappingUtils.throwMappingException(e);

            } catch (InvocationTargetException e) {

                MappingUtils.throwMappingException(e);

            } catch (NoSuchMethodException e) {

                MappingUtils.throwMappingException(e);

            }


        }

        return result;

    }

    protected Object mapToSVCObject(Object jaxbFieldObj) {

        Object svcFieldObj = null;

        String className = jaxbFieldObj.getClass().getName();
        // Remove package name, if any
        int lastDot = className.lastIndexOf(".");
        if ( lastDot > 0 ) {
            className = className.substring(lastDot + 1);
        }

        String svcClassName = "org.extensiblecatalog.ncip.v2.service." + className;
        try {

            Class svcFieldClass = Class.forName(svcClassName);
            svcFieldObj = mapper.map(jaxbFieldObj, svcFieldClass);

        } catch (ClassNotFoundException e) {

            LOG.warn("Primitive type " + className + " not mapped.");

        }

        return svcFieldObj;
    }

    protected Object mapToJAXBObject(Object svcFieldObj, String getMethodName, Object svcObj) {

        Object jaxbFieldObj = null;

        Class svcClass = svcFieldObj.getClass();

        if ( BigDecimal.class.isAssignableFrom(svcClass) ) {

            jaxbFieldObj = callObjectFactory(getMethodName, svcObj);

        } else if ( String.class.isAssignableFrom(svcClass) ) {

            jaxbFieldObj = callObjectFactory(getMethodName, svcObj);

        } else if ( GregorianCalendar.class.isAssignableFrom(svcClass) ) {

            XMLGregorianCalendar xmlGregorianCalendar = null;
            try {
                xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar)svcObj);
            } catch (DatatypeConfigurationException e) {
                MappingUtils.throwMappingException("Could not convert this date: "
                    + svcObj.toString(), e);
            }
            jaxbFieldObj = callObjectFactory(getMethodName, xmlGregorianCalendar);

        } else if ( SchemeValuePair.class.isAssignableFrom(svcClass) ) {

            SchemeValuePair svcSVP = (SchemeValuePair)svcObj;
            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair jaxbSVP
                = objectFactory.createSchemeValuePair();
            jaxbSVP.setScheme(svcSVP.getScheme());
            jaxbSVP.setValue(svcSVP.getValue());
            jaxbFieldObj = callObjectFactory(getMethodName, jaxbSVP);

        } else {
            // Remove package name, if any
            String svcClassName = svcClass.getName();
            int lastDot = svcClassName.lastIndexOf(".");
            if ( lastDot > 0 ) {
                svcClassName = svcClassName.substring(lastDot + 1);
            }

            String jaxbClassName = "org.extensiblecatalog.ncip.v2.binding.jaxb.elements." + svcClassName;
            try {

                Class jaxbClass = Class.forName(jaxbClassName);
                jaxbFieldObj = mapper.map(svcFieldObj, jaxbClass);

            } catch (ClassNotFoundException e) {
                MappingUtils.throwMappingException(e);
            }

        }

        return jaxbFieldObj;
    }

    protected Object callObjectFactory(String getMethodName, Object srcObj) {

        Object jaxbObject = null;

        Method objFactoryMethod = getObjectFactoryMethod(getMethodName, srcObj.getClass());

        try {

            jaxbObject = objFactoryMethod.invoke(objectFactory, srcObj);

        } catch (IllegalAccessException e) {

            MappingUtils.throwMappingException(e);

        } catch (InvocationTargetException e) {

            MappingUtils.throwMappingException(e);

        }

        return jaxbObject;

    }

    protected String makeJAXBObjectFactoryMethodName(final String getMethodName) {

        String methodName = getMethodName;
        if ( methodName.endsWith("s")) {
            methodName = methodName.substring(0, methodName.length() - 2);
        }
        if ( methodName.startsWith("get")) {
            methodName = methodName.substring(3);
        }
        if ( !methodName.startsWith("create")) {
            methodName = "create" + methodName;
        }

        return methodName;

    }
    protected Constructor getDefaultConstructor(String className) {

        Constructor ctor = defaultCtorsByClassName.get(className);

        if ( ctor == null ) {

            try {

                Class clazz = Class.forName(className);
                ctor = clazz.getConstructor();
                defaultCtorsByClassName.put(className, ctor);

            } catch (NoSuchMethodException e) {

                MappingUtils.throwMappingException(e);

            } catch (ClassNotFoundException e) {

                MappingUtils.throwMappingException(e);

            }

        }

        return ctor;
    }

    protected Method getObjectFactoryMethod(String methodName, Class srcObjClass) {

        // Note that this assumes that there's only ever one method by this name, i.e. it doesn't
        // account for different parameter types
        Method method = objectFactoryMethodsByName.get(methodName);

        if ( method == null) {

            method = ReflectionHelper.findMethod(objectFactory.getClass(), methodName, srcObjClass);
            objectFactoryMethodsByName.put(methodName, method);

        }

        return method;

    }

}
