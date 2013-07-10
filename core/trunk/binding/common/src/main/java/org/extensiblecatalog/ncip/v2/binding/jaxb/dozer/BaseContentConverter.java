/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb.dozer;

import org.apache.log4j.Logger;
import org.dozer.*;
import org.extensiblecatalog.ncip.v2.binding.jaxb.JAXBHelper;
import org.extensiblecatalog.ncip.v2.service.*;
import org.w3c.dom.Node;

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

/**
 * JAXB uses a "catch-all" approach for certain content models occurring in NCIP (e.g. this one:
 *
     <xsd:choice>
         <xsd:element ref = "ItemId"/>
         <xsd:sequence>
             <xsd:element ref = "RequestId"/>
             <xsd:element ref = "ItemId" minOccurs = "0"/>
         </xsd:sequence>
      </xsd:choice>
 *
 * This catch-all approach is implemented by putting the child elements into a List<Object> which is
 * accessed via the getContent() method of the class. JAXB requires the application code
 * to put elements into the content list <i>in the order they are to be marshaled.</i>
 * To meet that expectation this class has to "know" the order of child elements for those classes.
 * This is hard-coded below and should the XML schema be changed this might need changing too.
 * A more dynamic approach would be to determine this at start-up time from the schema itself.
 * A much less difficult (?) approach would be to figure out how to put this element ordering into the
 * dozer mapping file rather than into this class; at least then the element-order information would
 * be in the same place where the element is configured to use this BaseContentConverter.
 *
 *  Note: You must create an instance of this class for <i>each</i> JAXB package.
 */
public abstract class BaseContentConverter<JAXBSVPCLASS, EXTCLASS>
        extends DozerConverter<Object /* service package */, Object /* jaxb */> implements MapperAware {

    private static final Logger LOG = Logger.getLogger(BaseContentConverter.class);

    protected final Class<JAXBSVPCLASS> jaxbSVPClass;
    protected final Class<EXTCLASS> jaxbExtensionClass;
    protected final String jaxbPackageNameWithPeriod;
    protected final String svcPackageNameWithPeriod;

    protected final Map<String, Method> objectFactoryMethodsByName = new HashMap<String, Method>();

    protected final Map<String, Constructor> defaultCtorsByClassName = new HashMap<String, Constructor>();

    protected Map<String, JAXBSVPCLASS> svpAgencyElementTypeFields;
    protected Map<String, JAXBSVPCLASS> svpItemElementTypeFields;
    protected Map<String, JAXBSVPCLASS> svpRequestElementTypeFields;
    protected Map<String, JAXBSVPCLASS> svpUserElementTypeFields;

    protected Map<String, JAXBSVPCLASS> createMap(Iterator<? extends SchemeValuePair> iterator) {

        Map<String, JAXBSVPCLASS> map = new HashMap<String, JAXBSVPCLASS>();
        while ( iterator.hasNext() ) {

            SchemeValuePair svcSVP = iterator.next();
            String scheme = svcSVP.getScheme();
            String value = svcSVP.getValue();
            String fieldName = convertToFieldName(value);
            JAXBSVPCLASS jaxbSVP;
            try {

                jaxbSVP = JAXBHelper.createJAXBSchemeValuePair(jaxbSVPClass, scheme, value);

            } catch (IllegalAccessException e) {

                throw new MappingException(e);

            } catch (InstantiationException e) {

                throw new MappingException(e);

            }
            map.put(fieldName, jaxbSVP);

        }

        return map;

    }

    protected static final Map<String, List<String>> elementOrderByParentElementName
        = new HashMap<String, List<String>>();

    // If any element handled by this class has extension elements, there must be a list of those
    // extension elements here so the class can know that this element must be put in an Ext object.
    protected static final Map<String, List<String>> extensionElementsByParentObjectName
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
    protected static final List<String> requestedItemExtensions = new ArrayList<String>();

    static {

        requestedItemElementOrder.add("RequestId");
        requestedItemElementOrder.add("ItemId");
        requestedItemElementOrder.add("BibliographicId");
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
        requestedItemElementOrder.add("Ext");

        requestedItemExtensions.add("BibliographicDescription");
        requestedItemExtensions.add("EarliestDateNeeded");
        requestedItemExtensions.add("HoldQueueLength");
        requestedItemExtensions.add("NeedBeforeDate");
        requestedItemExtensions.add("SuspensionStartDate");
        requestedItemExtensions.add("SuspensionEndDate");

    }

    protected static final List<String> requestItemElementOrder = new ArrayList<String>();
    protected static final List<String> requestItemExtensions = new ArrayList<String>();

    static {

        requestItemElementOrder.add("InitiationHeader");
        requestItemElementOrder.add("MandatedAction");
        requestItemElementOrder.add("UserId");
        requestItemElementOrder.add("AuthenticationInput");
        requestItemElementOrder.add("BibliographicId");
        requestItemElementOrder.add("ItemId");
        requestItemElementOrder.add("RequestId");
        requestItemElementOrder.add("RequestType");
        requestItemElementOrder.add("RequestScopeType");
        requestItemElementOrder.add("ItemOptionalFields");
        requestItemElementOrder.add("ShippingInformation");
        requestItemElementOrder.add("EarliestDateNeeded");
        requestItemElementOrder.add("NeedBeforeDate");
        requestItemElementOrder.add("PickupLocation");
        requestItemElementOrder.add("PickupExpiryDate");
        requestItemElementOrder.add("AcknowledgedFeeAmount");
        requestItemElementOrder.add("PaidFeeAmount");
        requestItemElementOrder.add("AcknowledgedItemUseRestrictionType");
        requestItemElementOrder.add("ItemElementType");
        requestItemElementOrder.add("UserElementType");
        requestItemElementOrder.add("Ext");

        requestItemExtensions.add("BibliographicDescription");
        requestItemExtensions.add("DesiredEdition");
        requestItemExtensions.add("EditionSubstitutionType");
        requestItemExtensions.add("MaxFeeAmount");
        requestItemExtensions.add("SuspensionStartDate");
        requestItemExtensions.add("SuspensionEndDate");
        requestItemExtensions.add("UserNote");
        requestItemExtensions.add("UserOptionalFields");

    }

    protected static final List<String> requestItemResponseElementOrder = new ArrayList<String>();
    protected static final List<String> requestItemResponseExtensions = new ArrayList<String>();

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

        requestItemResponseExtensions.add("HoldQueueLength");
        requestItemResponseExtensions.add("HoldQueuePosition");

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

        elementOrderByParentElementName.put("CancelRequestItem", cancelRequestItemElementOrder);
        elementOrderByParentElementName.put("CancelRequestItemResponse", cancelRequestItemResponseElementOrder);
        elementOrderByParentElementName.put("ChronologyLevelInstance", chronologyLevelInstanceElementOrder);
        elementOrderByParentElementName.put("Destination", destinationElementOrder);
        elementOrderByParentElementName.put("EnumerationLevelInstance", enumerationLevelInstanceElementOrder);
        elementOrderByParentElementName.put("ItemRequestCancelled", itemRequestCancelledElementOrder);
        elementOrderByParentElementName.put("ItemShipped", itemShippedElementOrder);
        elementOrderByParentElementName.put("LookupItemResponse", lookupItemResponseElementOrder);
        elementOrderByParentElementName.put("LookupRequestResponse", lookupRequestResponseElementOrder);
        elementOrderByParentElementName.put("PersonalNameInformation", personalNameInformationElementOrder);
        elementOrderByParentElementName.put("RequestedItem", requestedItemElementOrder);
        elementOrderByParentElementName.put("RequestItem", requestItemElementOrder);
        elementOrderByParentElementName.put("RequestItemResponse", requestItemResponseElementOrder);
        elementOrderByParentElementName.put("StructuredAddress", structuredAddressElementOrder);
        elementOrderByParentElementName.put("StructuredHoldingsData", structuredHoldingsDataElementOrder);

        extensionElementsByParentObjectName.put("RequestedItem", requestedItemExtensions);
        extensionElementsByParentObjectName.put("RequestItem", requestItemExtensions);
        extensionElementsByParentObjectName.put("RequestItemResponse", requestItemResponseExtensions);

    }

    protected Mapper mapper;

    public BaseContentConverter(Class<JAXBSVPCLASS> jaxbSVPClass, Class<EXTCLASS> jaxbExtensionClass) {

        super(Object.class, Object.class);
        this.jaxbSVPClass = jaxbSVPClass;
        this.jaxbExtensionClass = jaxbExtensionClass;
        this.jaxbPackageNameWithPeriod = jaxbSVPClass.getPackage().getName() + ".";
        this.svcPackageNameWithPeriod = SchemeValuePair.class.getPackage().getName() + ".";
        svpAgencyElementTypeFields = createMap(AgencyElementType.iterator());
        svpItemElementTypeFields = createMap(ItemElementType.iterator());
        svpRequestElementTypeFields = createMap(RequestElementType.iterator());
        svpUserElementTypeFields = createMap(UserElementType.iterator());

    }

    protected static String convertToFieldName(String value) {

        return value.replaceAll(" ","");

    }

    protected Object mapToSVCObject(Object jaxbFieldObj) {

        Object svcFieldObj = null;

        String elementName = getElementName(jaxbFieldObj);
        String svcClassName = svcPackageNameWithPeriod + elementName;
        try {

            Class<?> svcFieldClass = Class.forName(svcClassName);
            svcFieldObj = mapper.map(jaxbFieldObj, svcFieldClass);

        } catch (ClassNotFoundException e) {

            LOG.warn("Primitive type " + jaxbFieldObj.getClass().getName() + " not mapped.");

        }

        return svcFieldObj;
    }

    protected Object mapToJAXBObject(Object svcFieldObj, String createMethodName, Object svcObj) {

        Object jaxbFieldObj = null;

        Class svcClass = svcFieldObj.getClass();

        if ( NCIPMessage.MessageType.class.isAssignableFrom(svcClass)) {

            // Do nothing, return null

        } else if ( BigDecimal.class.isAssignableFrom(svcClass) ) {

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

        } else if ( SchemeValuePair.class.isAssignableFrom(svcClass) ) {

            SchemeValuePair svcSVP = (SchemeValuePair)svcObj;
            JAXBSVPCLASS jaxbSVP;
            try {

                jaxbSVP = JAXBHelper.createJAXBSchemeValuePair(jaxbSVPClass, svcSVP.getScheme(), svcSVP.getValue());

            } catch (IllegalAccessException e) {

                throw new MappingException(e);

            } catch (InstantiationException e) {

                throw new MappingException(e);

            }
            jaxbFieldObj = callObjectFactory(createMethodName, jaxbSVP);

        } else {

            String elementName = svcClass.getSimpleName();
            String jaxbClassName = jaxbPackageNameWithPeriod + elementName;
            try {

                Class<?> jaxbClass = Class.forName(jaxbClassName);
                jaxbFieldObj = mapper.map(svcFieldObj, jaxbClass);

            } catch (ClassNotFoundException e) {

                throw new MappingException("Exception creating JAXB object.", e);

            }

        }

        return jaxbFieldObj;
    }

    protected Object callObjectFactory(String createMethodName, Object srcObj) {

        Object jaxbObject;

        Method objFactoryMethod = getObjectFactoryMethod(createMethodName, srcObj.getClass());

        try {

            jaxbObject = objFactoryMethod.invoke(getObjectFactory(), srcObj);

        } catch (IllegalAccessException e) {

            throw new MappingException("Exception creating JAXB object.", e);

        } catch (InvocationTargetException e) {

            throw new MappingException("Exception creating JAXB object.", e);

        }

        return jaxbObject;

    }

    /**
     * Make a create method name (e.g. "createDateDue") out of a get method name (e.g. "getDateDue"),
     * handling plural forms of the get method (e.g. "getDateDues").
     *
     * @param getMethodName the getter method name
     * @return the method name
     */

    protected String makeCreateMethodName(final String getMethodName) {

        String createMethodName = getMethodName;
        if ( createMethodName.endsWith("s")) {
            createMethodName = createMethodName.substring(0, createMethodName.length() - 2);
        }
        if ( createMethodName.startsWith("get")) {
            createMethodName = createMethodName.substring(3);
        }
        if ( !createMethodName.startsWith("create")) {
            createMethodName = "create" + createMethodName;
        }

        return createMethodName;

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

    // DozerConverter.convertTo is convert to type A, the first generic parm, which in our case is service package class
    @Override
    public Object convertTo(Object srcObj, Object destObj) {

        return determineObjectTypeAndConvert(srcObj, destObj);

    }

    // DozerConverter.convertFrom is convert from type B, the second generic parm, which in our case is JAXB class
    @Override
    public Object convertFrom(Object srcObj, Object destObj) {

        return determineObjectTypeAndConvert(srcObj, destObj);

    }

    protected Object determineObjectTypeAndConvert(Object srcObj, Object destObj) {

        if ( srcObj != null ) {

            if ( srcObj.getClass().getPackage().getName().compareTo(
                    SchemeValuePair.class.getPackage().getName()) == 0 ) {

                destObj = convertToJAXBFromSVC(srcObj, destObj);

            } else if ( srcObj.getClass().getPackage().getName().contains(".jaxb.") ) {

                destObj = convertFromJAXBToSVC(srcObj, destObj);

            } else {

                throw new MappingException("The source object does not appear to be a member of Service package "
                        + "or of a JAXB bindings package.");
            }

        } else {

            destObj = null;

        }

        return destObj;

    }

    public Object convertFromJAXBToSVC(Object srcJAXBObj, Object destSVCObj) {

        Object result = null;
        if ( srcJAXBObj != null ) {

            Class jaxbClass = srcJAXBObj.getClass();
            Class svcClass = getSVCClassForElement(jaxbClass.getSimpleName());

            try {

                Constructor svcCtor = svcClass.getConstructor();
                Object svcObj = svcCtor.newInstance();
                Method getContentMethod = jaxbClass.getMethod("getContent");
                List<Object> contentList = (List<Object>)getContentMethod.invoke(srcJAXBObj);

                for ( Object jaxbFieldObj : contentList ) {

                    mapAndSetSVCFieldFromJAXBFieldObject(jaxbFieldObj, svcObj);

                }

                result = svcObj;

            } catch (NoSuchMethodException e) {

                throw new MappingException("Exception creating service object.", e);

            } catch (InvocationTargetException e) {

                throw new MappingException("Exception creating service object.", e);

            } catch (IllegalAccessException e) {

                throw new MappingException("Exception creating service object.", e);

            } catch (InstantiationException e) {

                throw new MappingException("Exception creating service object.", e);

            }
        }

        return result;

    }

    void mapAndSetSVCFieldFromJAXBFieldObject(Object jaxbFieldObj, Object svcObj) {

        String elementName = getElementName(jaxbFieldObj);

        Field field = ReflectionHelper.findField(svcObj.getClass(), elementName);

        if ( field != null ) {

            Class<?> svcFieldClass = field.getType();
            if ( java.util.List.class.isAssignableFrom(svcFieldClass)) {

                mapAndSetSVCListFieldFromJAXBObject(jaxbFieldObj, svcObj, elementName);

            } else {

                mapAndSetSVCObjectFieldFromJAXBObject(jaxbFieldObj, svcFieldClass, svcObj, elementName);

            }

        } else {

            if ( elementName.compareTo("Ext") == 0 ) {

                mapSVCFieldFromExtension(jaxbFieldObj, svcObj, field);

            } else {

                LOG.debug("content list contained an element (" + elementName
                    + ") for which no similarly-named field was found on the service object ("
                    + svcObj.getClass().getName() + "); skipping that field.");

            }

        }

    }

    String getExtensionName(Object jaxbObj) {

        String extensionName;
        if ( jaxbObj instanceof JAXBElement ) {

            JAXBElement jaxbElement = (JAXBElement)jaxbObj;
            extensionName = jaxbElement.getName().getLocalPart();

        } else if ( jaxbObj instanceof Node) {

            extensionName = ((Node)jaxbObj).getLocalName();

        } else {

            extensionName = jaxbObj.getClass().getSimpleName();

        }

        return extensionName;

    }

    void mapSVCFieldFromExtension(Object jaxbFieldObj, Object svcObj, Field field) {

        EXTCLASS ext = (EXTCLASS)jaxbFieldObj;
        for ( Object innerJAXBObj : JAXBHelper.getAnyList(ext) ) {

            String extensionName = getExtensionName(innerJAXBObj);
            field = ReflectionHelper.findField(svcObj.getClass(), extensionName);
            if ( field != null ) {

                Class<?> svcFieldClass = field.getType();
                Object svcFieldObj = mapper.map(innerJAXBObj, svcFieldClass);
                Method setMethod = ReflectionHelper.findMethod(svcObj.getClass(), "set" + extensionName, svcFieldClass);
                try {

                    setMethod.invoke(svcObj, svcFieldObj);

                } catch (IllegalAccessException e) {

                    throw new MappingException("Exception invoking set method on service object.", e);

                } catch (InvocationTargetException e) {

                    throw new MappingException("Exception invoking set method on service object.", e);

                }

            } else {

                LOG.debug("content list contained an extension element (" + extensionName
                    + ") for which no similarly-named field was found on the service object ("
                    + svcObj.getClass().getName() + "); skipping that field.");

            }
        }

    }

    void mapAndSetSVCListFieldFromJAXBObject(Object jaxbFieldObj, Object svcObj, String fieldName) {

        Object svcFieldObj = mapToSVCObject(jaxbFieldObj);
        Method getMethod = ReflectionHelper.findMethod(svcObj.getClass(),
            "get" + fieldName + "s");
        try {

            List<Object> svcList = (List<Object>)getMethod.invoke(svcObj);

            if ( svcList == null ) {

                svcList = new ArrayList();
                Method setMethod = ReflectionHelper.findMethod(svcObj.getClass(), "set" + fieldName + "s", List.class);

                try {

                    setMethod.invoke(svcObj, svcList);

                } catch (IllegalAccessException e) {

                    throw new MappingException("Exception invoking set method on service object.", e);

                } catch (InvocationTargetException e) {

                    throw new MappingException("Exception invoking set method on service object.", e);

                }
            }

            svcList.add(svcFieldObj);

        } catch (IllegalAccessException e) {

            throw new MappingException("Exception invoking get method on service object.", e);

        } catch (InvocationTargetException e) {

            throw new MappingException("Exception invoking get method on service object.", e);

        }

    }

    void mapAndSetSVCObjectFieldFromJAXBObject(Object jaxbFieldObj, Class<?> svcFieldClass, Object svcObj, String fieldName) {

        Object svcFieldObj = mapper.map(jaxbFieldObj, svcFieldClass);
        Method setMethod = ReflectionHelper.findMethod(svcObj.getClass(), "set" + fieldName,
            svcFieldClass);
        try {

            setMethod.invoke(svcObj, svcFieldObj);

        } catch (IllegalAccessException e) {

            throw new MappingException("Exception invoking set method on service object.", e);

        } catch (InvocationTargetException e) {

            throw new MappingException("Exception invoking set method on service object.", e);

        }

    }

    String getElementName(Object jaxbFieldObj) {

        String elementName;

        if (jaxbFieldObj instanceof JAXBElement) {

            JAXBElement jaxbElement = (JAXBElement)jaxbFieldObj;
            elementName = jaxbElement.getName().getLocalPart();

        } else {

            elementName = jaxbFieldObj.getClass().getSimpleName();

        }

        return elementName;
    }

    public Object convertToJAXBFromSVC(Object srcSVCObj, Object destJAXBObj) {

        Object result = null;

        if ( srcSVCObj != null ) {

            Class svcClass = srcSVCObj.getClass();
            String elementName = ServiceHelper.getElementName(svcClass);
            Class jaxbClass = getJAXBClassForElement(elementName);

            try {

                Object jaxbObj = jaxbClass.getConstructor().newInstance();
                Method getContentMethod = jaxbObj.getClass().getMethod("getContent");
                List<Object> workingJAXBContentList = new ArrayList<Object>();
                List<Object> jaxbExtensionList = new ArrayList<Object>();

                Method[] svcMethods = srcSVCObj.getClass().getMethods();
                for ( Method svcGetMethod : svcMethods ) {

                    mapJAXBFieldFromSVCObject(svcGetMethod, srcSVCObj, workingJAXBContentList, jaxbExtensionList, elementName);

                }

                List<Object> jaxbContentList = (List<Object>) getContentMethod.invoke(jaxbObj);
                jaxbContentList.clear();

                if ( ! workingJAXBContentList.isEmpty() ) {

                    sortContentList(jaxbContentList, workingJAXBContentList, elementName);

                }

                if ( ! jaxbExtensionList.isEmpty() ) {

                    sortExtensionList(jaxbExtensionList, jaxbContentList, elementName);

                }

                result = jaxbObj;


            } catch (InstantiationException e) {

                throw new MappingException("Exception creating JAXB object.", e);

            } catch (IllegalAccessException e) {

                throw new MappingException("Exception creating JAXB object.", e);

            } catch (InvocationTargetException e) {

                throw new MappingException("Exception creating JAXB object.", e);

            } catch (NoSuchMethodException e) {

                throw new MappingException("Exception creating JAXB object.", e);

            }

        }

        return result;

    }

    void sortExtensionList(List<Object> jaxbExtensionList, List<Object> jaxbContentList, String elementName) {

        ContentMappingComparator comparator
            = new ContentMappingComparator(extensionElementsByParentObjectName.get(elementName));
        java.util.Collections.sort(jaxbExtensionList, comparator);

        EXTCLASS ext = createExtension();
        JAXBHelper.addAllToExtension(ext, jaxbExtensionList);

        jaxbContentList.add(ext);

    }

    void sortContentList(List<Object> jaxbContentList, List<Object> workingJAXBContentList, String elementName) {

        ContentMappingComparator comparator
            = new ContentMappingComparator(elementOrderByParentElementName.get(elementName));
        java.util.Collections.sort(workingJAXBContentList, comparator);

        jaxbContentList.addAll(workingJAXBContentList);

    }

    void mapJAXBFieldFromSVCObject(Method svcGetMethod, Object srcSVCObj, List<Object> workingJAXBContentList,
                     List<Object> jaxbExtensionList, String elementName) {

        if ( svcGetMethod.getName().startsWith("get") ) {

            LOG.debug("Method " + svcGetMethod.getName() + " is a getter.");
            if ( svcGetMethod.getParameterTypes().length == 0 ) {

                Object svcFieldObj;
                try {

                    svcFieldObj = svcGetMethod.invoke(srcSVCObj);

                } catch (IllegalAccessException e) {

                    throw new MappingException("Exception invoking get method on service object.", e);

                } catch (InvocationTargetException e) {

                    throw new MappingException("Exception invoking get method on service object.", e);

                }

                if ( svcFieldObj != null ) {

                    Class svcFieldClass = svcGetMethod.getReturnType();

                    mapJAXBFieldFromSVCField(srcSVCObj, svcGetMethod, svcFieldClass,svcFieldObj,
                            workingJAXBContentList, jaxbExtensionList, elementName);

                } // If the srcObj's field is null there's nothing to copy

            } else {

                LOG.debug("Method " + svcGetMethod.getName() + " has parameters; skipping.");

            }

        }

    }

    void mapJAXBFieldFromSVCField(Object srcSVCObj, Method svcGetMethod, Class<?> svcFieldClass, Object svcFieldObj,
                     List<Object> workingJAXBContentList, List<Object> jaxbExtensionList, String elementName) {

        if ( SchemeValuePair.class.getName().compareTo(svcFieldClass.getName()) == 0 ) {

            throw new MappingException("BaseContentConverter found field named '"
                + svcGetMethod.getName().substring("get".length())
                + "' of type SchemeValuePair in " + srcSVCObj.getClass()
                + "; this field should have a more narrow type.");

        } else if ( svcFieldClass.getName().compareToIgnoreCase(
            java.lang.Class.class.getName()) == 0 ) {

            LOG.debug("Skipping " + svcGetMethod.getName()
                + " - this is not actually a data field.");

        } else if ( List.class.isAssignableFrom(svcFieldClass) ) {

            mapList(svcGetMethod, (List)svcFieldObj, workingJAXBContentList);

        } else if ( boolean.class.isInstance(svcFieldObj) || Boolean.class.isInstance(svcFieldObj) ) {

            Boolean svcFieldBoolean = (Boolean)svcFieldObj;
            mapBoolean(svcGetMethod, svcFieldBoolean);

        } else {

            mapExtension(svcGetMethod, svcFieldObj, jaxbExtensionList, workingJAXBContentList,
                elementName);

        }

    }

    void mapList(Method svcGetMethod, List svcFieldObjList, List<Object> workingJAXBContentList) {

        for ( Object innerSVCFieldObj : svcFieldObjList) {

            String createMethodName = makeCreateMethodName(svcGetMethod.getName());
            Object jaxbFieldObj = mapToJAXBObject(innerSVCFieldObj, createMethodName,
                innerSVCFieldObj);
            if ( jaxbFieldObj != null ) {
                workingJAXBContentList.add(jaxbFieldObj);
            }
        }

    }

    void mapBoolean(Method svcGetMethod, Boolean svcFieldValue) {

        // TODO: These need to be references to the lists in the JAXB bean
        List<JAXBSVPCLASS> agencyElementTypeList = new ArrayList<JAXBSVPCLASS>();
        List<JAXBSVPCLASS> itemElementTypeList = new ArrayList<JAXBSVPCLASS>();
        List<JAXBSVPCLASS> requestElementTypeList = new ArrayList<JAXBSVPCLASS>();
        List<JAXBSVPCLASS> userElementTypeList = new ArrayList<JAXBSVPCLASS>();

        String fieldName = svcGetMethod.getName().substring(3);

        if ( svcFieldValue ) {

            if ( getAgencyElementType(fieldName) != null ) {

                agencyElementTypeList.add(getAgencyElementType(fieldName));

            } else if ( getItemElementType(fieldName) != null ) {

                itemElementTypeList.add(getItemElementType(fieldName));

            } else if ( getRequestElementType(fieldName) != null) {

                requestElementTypeList.add(getRequestElementType(fieldName));

            } else if ( getUserElementType(fieldName) != null ) {

                userElementTypeList.add(getUserElementType(fieldName));

            } else {

                throw new MappingException("Boolean field " + fieldName + " not recognized.");

            }


        }
        // otherwise skip it - "false" ElementType fields in the svc object are
        // represented by omitting the ElementType from the jaxb object

    }

    void mapExtension(Method svcGetMethod, Object svcFieldObj, List<Object> jaxbExtensionList,
                      List<Object> workingJAXBContentList, String elementName) {

        String createMethodName = makeCreateMethodName(svcGetMethod.getName());
        Object jaxbFieldObj = mapToJAXBObject(svcFieldObj, createMethodName, svcFieldObj);

        if ( jaxbFieldObj != null ) {

            List<String> extensionElementsList = extensionElementsByParentObjectName.get(elementName);

            String fieldName = svcGetMethod.getName().substring(3);
            if ( extensionElementsList != null && ! extensionElementsList.isEmpty()
                && extensionElementsList.contains(fieldName)) {

                    jaxbExtensionList.add(jaxbFieldObj);

            } else {

                workingJAXBContentList.add(jaxbFieldObj);

            }
        } else {

            LOG.debug("Got null jaxbFieldObj back from mapping of " + svcFieldObj);

        }

    }

    Class<?> getSVCClassForElement(String elementName) {

        Class<?> svcClass;
        try {

            svcClass = getElementNamesToServiceClassMap().get(elementName);
            if ( svcClass == null ) {

                String className = this.svcPackageNameWithPeriod + elementName;
                svcClass = Class.forName(className);

            }

            return svcClass;

        } catch (ClassNotFoundException e) {

            throw new MappingException("Could not find service-package class for element name '"  + elementName + "'.");

        }

    }

    Class<?> getJAXBClassForElement(String elementName) {

        Class<?> jaxbClass;
        try {

            jaxbClass = getElementNamesToJAXBClassMap().get(elementName);
            if ( jaxbClass == null ) {

                String className = this.jaxbPackageNameWithPeriod + elementName;
                jaxbClass = Class.forName(className);

            }

            return jaxbClass;

        } catch (ClassNotFoundException e) {

            throw new MappingException("Could not find JAXB package class for element name '"  + elementName + "'.");

        }

    }

    public JAXBSVPCLASS getAgencyElementType(String fieldName) {

        return svpAgencyElementTypeFields.get(fieldName);

    }

    public JAXBSVPCLASS getItemElementType(String fieldName) {

        return svpItemElementTypeFields.get(fieldName);

    }

    public JAXBSVPCLASS getRequestElementType(String fieldName) {

        return svpRequestElementTypeFields.get(fieldName);

    }

    public JAXBSVPCLASS getUserElementType(String fieldName) {

        return svpUserElementTypeFields.get(fieldName);

    }

    protected abstract EXTCLASS createExtension();
    protected abstract Object getObjectFactory();
    protected abstract Map<String, Class<?>> getElementNamesToServiceClassMap();
    protected abstract Map<String, Class<?>> getElementNamesToJAXBClassMap();

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

}
