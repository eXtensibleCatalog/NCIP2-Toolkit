/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.binding.BindingError;
import org.extensiblecatalog.ncip.v2.binding.BindingException;
import org.extensiblecatalog.ncip.v2.common.NCIP2TranslatorConfiguration;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.*;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.AcknowledgedFeeAmount;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Amount;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.AuthenticationInput;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.BibInformation;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.BibliographicId;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.BibliographicItemId;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.BibliographicRecordId;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.BlockOrTrap;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ChronologyLevelInstance;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ComponentId;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.CurrentBorrower;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.CurrentRequester;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Destination;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ElectronicAddress;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ElectronicResource;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.EnumerationLevelInstance;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.FiscalTransactionInformation;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.FiscalTransactionReferenceId;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.FromAgencyId;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.HoldingsChronology;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.HoldingsEnumeration;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.HoldingsInformation;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.HoldingsSet;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.IndeterminateLoanPeriodFlag;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.InitiationHeader;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ItemDescription;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ItemDetails;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ItemId;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ItemInformation;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ItemTransaction;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Location;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.LocationName;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.LocationNameInstance;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.MandatedAction;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NameInformation;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.OnBehalfOfAgency;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.OrganizationNameInformation;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.PaidFeeAmount;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.PersonalNameInformation;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.PhysicalAddress;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.PhysicalCondition;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.PreviousUserId;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Problem;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.RelatedFiscalTransactionReferenceId;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.RequestId;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.RequiredFeeAmount;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ResponseHeader;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.RoutingInformation;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SchemeValuePair;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ShippingInformation;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.StructuredAddress;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.StructuredHoldingsData;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.StructuredPersonalUserName;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.SummaryHoldingsInformation;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.ToAgencyId;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.UnstructuredAddress;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.UserAddressInformation;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.UserId;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.UserOptionalFields;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.UserPrivilege;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.UserPrivilegeFee;
import org.extensiblecatalog.ncip.v2.binding.jaxb.elements.UserPrivilegeStatus;
import org.extensiblecatalog.ncip.v2.common.*;
import org.extensiblecatalog.ncip.v2.service.*;

import javax.xml.bind.*;
import javax.xml.validation.Schema;


public class JAXBTranslator implements org.extensiblecatalog.ncip.v2.service.Translator {
    // TODO: Handle extensions in the create... methods.

    private static final Logger LOG = Logger.getLogger(JAXBTranslator.class);

    protected static final String PACKAGE_NAME = "org.extensiblecatalog.ncip.v2.binding.jaxb.elements";

    // TODO: Get this from ServiceContext
    protected static final String NCIP_VERSION_V2_0 = "http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0.xsd";

    protected static final String NCIP_VERSION_V2_0_WITH_ILS_DI_EXTENSIONS
        = "http://www.niso.org/schemas/ncip/v2_0/imp1/xsd/ncip_v2_0_extensions.xsd";

    // TODO: The marshalling-specific code should be moved to a separate class so it can be re-used (e.g. in test classes)
    static JAXBContext jaxbContext;
    {
        try {

            jaxbContext = JAXBContext.newInstance(PACKAGE_NAME);

        } catch (JAXBException e) {

            LOG.error("Exception creating a new instance of JAXBContext:", e);
            throw new ExceptionInInitializerError(e);

        }
    }

    /**
     * The {@link org.extensiblecatalog.ncip.v2.common.StatisticsBean} instance used to report performance data.
     */
    protected StatisticsBean statisticsBean;
    {
        try {
            statisticsBean = StatisticsBeanFactory.getSharedStatisticsBean();
        } catch (ToolkitException e) {

            throw new ExceptionInInitializerError(e);
        }

    }

    protected static Schema schema;
    static {
        try {

            TranslatorConfiguration config = TranslatorConfigurationFactory.getConfiguration();

            if ( config instanceof NCIP2TranslatorConfiguration) {

                schema = XMLHelper.loadSchema(((NCIP2TranslatorConfiguration)config).getSchemaURLs());

            } else {

                schema = null;

            }

        } catch (ToolkitException e) {

            LOG.error("Exception loading schema; messages will not be validated against a schema.", e);

        }

    }

    protected final ObjectFactory objectFactory = new ObjectFactory();

    protected ScopeConvertJAX2SVC scopeConvertJAX2SVC;
    protected ScopeConvertSVC2JAX scopeConvertSVC2JAX;

    public JAXBTranslator() {
        scopeConvertJAX2SVC = new ScopeConvertJAX2SVC(this);
        scopeConvertSVC2JAX = new ScopeConvertSVC2JAX(this);
    }

    // Translate from Service API to JAXB API.

    protected InitiationHeader createInitiationHeader(org.extensiblecatalog.ncip.v2.service.InitiationHeader initHdr) {

        InitiationHeader jaxbInitiationHeader = null;

        if (initHdr != null) {

            if (initHdr.getFromSystemId() != null) {

                if (jaxbInitiationHeader == null) {

                    jaxbInitiationHeader = new InitiationHeader();

                }

                jaxbInitiationHeader.setFromSystemId(convertSVP(initHdr.getFromSystemId()));

            }

            if (initHdr.getFromSystemAuthentication() != null) {

                if (jaxbInitiationHeader == null) {

                    jaxbInitiationHeader = new InitiationHeader();

                }

                jaxbInitiationHeader.setFromSystemAuthentication(initHdr.getFromSystemAuthentication());

            }

            if (initHdr.getFromAgencyId() != null) {

                if (jaxbInitiationHeader == null) {

                    jaxbInitiationHeader = new InitiationHeader();

                }

                jaxbInitiationHeader.setFromAgencyId(createFromAgencyId(initHdr.getFromAgencyId()));

            }

            if (initHdr.getFromAgencyAuthentication() != null) {

                if (jaxbInitiationHeader == null) {

                    jaxbInitiationHeader = new InitiationHeader();

                }

                jaxbInitiationHeader.setFromAgencyAuthentication(initHdr.getFromAgencyAuthentication());

            }

            if (initHdr.getOnBehalfOfAgency() != null) {

                if (jaxbInitiationHeader == null) {

                    jaxbInitiationHeader = new InitiationHeader();

                }

                jaxbInitiationHeader.setOnBehalfOfAgency(createOnBehalfOfAgency(initHdr.getOnBehalfOfAgency()));

            }

            if (initHdr.getToSystemId() != null) {

                if (jaxbInitiationHeader == null) {

                    jaxbInitiationHeader = new InitiationHeader();

                }

                jaxbInitiationHeader.setToSystemId(convertSVP(initHdr.getToSystemId()));

            }

            if (initHdr.getToAgencyId() != null) {

                if (jaxbInitiationHeader == null) {

                    jaxbInitiationHeader = new InitiationHeader();

                }

                jaxbInitiationHeader.setToAgencyId(createToAgencyId(initHdr.getToAgencyId()));

            }

            if (initHdr.getApplicationProfileType() != null) {

                if (jaxbInitiationHeader == null) {

                    jaxbInitiationHeader = new InitiationHeader();

                }

                jaxbInitiationHeader.setApplicationProfileType(convertSVP(initHdr.getApplicationProfileType()));

            }
        }

        return jaxbInitiationHeader;
    }

    protected ResponseHeader createResponseHeader(org.extensiblecatalog.ncip.v2.service.ResponseHeader respHdr) {

        ResponseHeader jaxbRespHdr = null;

        if (respHdr != null) {

            if (respHdr.getFromSystemId() != null) {

                if (jaxbRespHdr == null) {

                    jaxbRespHdr = new ResponseHeader();

                }

                jaxbRespHdr.setFromSystemId(convertSVP(respHdr.getFromSystemId()));

            }

            if (respHdr.getFromSystemAuthentication() != null) {

                if (jaxbRespHdr == null) {

                    jaxbRespHdr = new ResponseHeader();

                }

                jaxbRespHdr.setFromSystemAuthentication(respHdr.getFromSystemAuthentication());

            }

            if (respHdr.getFromAgencyId() != null) {

                if (jaxbRespHdr == null) {

                    jaxbRespHdr = new ResponseHeader();

                }

                jaxbRespHdr.setFromAgencyId(createFromAgencyId(respHdr.getFromAgencyId()));

            }

            if (respHdr.getFromAgencyAuthentication() != null) {

                if (jaxbRespHdr == null) {

                    jaxbRespHdr = new ResponseHeader();

                }

                jaxbRespHdr.setFromAgencyAuthentication(respHdr.getFromAgencyAuthentication());

            }

            if (respHdr.getToSystemId() != null) {

                if (jaxbRespHdr == null) {

                    jaxbRespHdr = new ResponseHeader();

                }

                jaxbRespHdr.setToSystemId(convertSVP(respHdr.getToSystemId()));

            }

            if (respHdr.getToAgencyId() != null) {

                if (jaxbRespHdr == null) {

                    jaxbRespHdr = new ResponseHeader();

                }

                jaxbRespHdr.setToAgencyId(createToAgencyId(respHdr.getToAgencyId()));

            }

        }

        return jaxbRespHdr;
    }

    protected FromAgencyId createFromAgencyId(org.extensiblecatalog.ncip.v2.service.FromAgencyId fromAgencyId) {
        FromAgencyId jaxbFromAgencyId = null;

        if (fromAgencyId != null) {

            jaxbFromAgencyId = new FromAgencyId();
            jaxbFromAgencyId.setAgencyId(convertSVP(fromAgencyId.getAgencyId()));

        }

        return jaxbFromAgencyId;
    }

    protected ToAgencyId createToAgencyId(org.extensiblecatalog.ncip.v2.service.ToAgencyId toAgencyId) {
        ToAgencyId jaxbToAgencyId = null;

        if (toAgencyId != null) {

            jaxbToAgencyId = new ToAgencyId();
            jaxbToAgencyId.setAgencyId(convertSVP(toAgencyId.getAgencyId()));

        }

        return jaxbToAgencyId;
    }

    protected OnBehalfOfAgency createOnBehalfOfAgency(org.extensiblecatalog.ncip.v2.service.OnBehalfOfAgency svcOnBehalfOfAgency) {

        OnBehalfOfAgency jaxbOnBehalfOfAgency = null;

        if (svcOnBehalfOfAgency != null) {

            jaxbOnBehalfOfAgency = new OnBehalfOfAgency();
            jaxbOnBehalfOfAgency.setAgencyId(convertSVP(svcOnBehalfOfAgency.getAgencyId()));

        }

        return jaxbOnBehalfOfAgency;

    }

    protected SchemeValuePair convertSVP(org.extensiblecatalog.ncip.v2.service.SchemeValuePair svp) {
        SchemeValuePair jaxbSVP = null;
        if (svp != null) {
            jaxbSVP = new SchemeValuePair();
            if (svp.getScheme() != null && svp.getScheme().length() > 0) {
                jaxbSVP.setScheme(svp.getScheme());
            }
            jaxbSVP.setValue(svp.getValue());
        }
        return jaxbSVP;
    }

    protected List<SchemeValuePair> convertSVPsToJAXB(
        List<? extends org.extensiblecatalog.ncip.v2.service.SchemeValuePair> svcSchemeValuePairs)
        throws BindingException {

        List<SchemeValuePair> jaxbSVPs = new ArrayList<SchemeValuePair>();

        if (svcSchemeValuePairs != null && svcSchemeValuePairs.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.SchemeValuePair svcSchemeValuePair : svcSchemeValuePairs) {

                SchemeValuePair jaxbSVP = convertSVP(svcSchemeValuePair);

                if (jaxbSVP != null) {

                    jaxbSVPs.add(jaxbSVP);

                } else {


                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                        "svcSchemeValuePair is null in convertSVPsToJAXB.");

                }

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "svcSchemeValuePairs is null or empty in convertSVPsToJAXB.");

        }

        return jaxbSVPs;
    }

    protected ItemId createItemId(org.extensiblecatalog.ncip.v2.service.ItemId itemId) {
        ItemId jaxbItemId = new ItemId();

        if (itemId.getAgencyId() != null) {
            jaxbItemId.setAgencyId(convertSVP(itemId.getAgencyId()));
        }

        if (itemId.getItemIdentifierType() != null) {
            jaxbItemId.setItemIdentifierType(convertSVP(itemId.getItemIdentifierType()));
        }

        jaxbItemId.setItemIdentifierValue(itemId.getItemIdentifierValue());

        return jaxbItemId;
    }

    protected List<ItemId> createJAXBItemIds(List<org.extensiblecatalog.ncip.v2.service.ItemId> svcItemIds) {

        List<ItemId> jaxbItemIds = new ArrayList<ItemId>();

        for ( org.extensiblecatalog.ncip.v2.service.ItemId svcItemId : svcItemIds) {

            jaxbItemIds.add(createItemId(svcItemId));

        }

        return jaxbItemIds;

    }

    protected RequestId createRequestId(org.extensiblecatalog.ncip.v2.service.RequestId requestId) {
        RequestId jaxbRequestId = new RequestId();

        if (requestId.getAgencyId() != null) {
            jaxbRequestId.setAgencyId(convertSVP(requestId.getAgencyId()));
        }

        if (requestId.getRequestIdentifierType() != null) {
            jaxbRequestId.setRequestIdentifierType(convertSVP(requestId.getRequestIdentifierType()));
        }

        jaxbRequestId.setRequestIdentifierValue(requestId.getRequestIdentifierValue());

        return jaxbRequestId;
    }

    protected List<RequestId> createJAXBRequestIds(List<org.extensiblecatalog.ncip.v2.service.RequestId> svcRequestIds) {

        List<RequestId> jaxbRequestIds = new ArrayList<RequestId>();

        for ( org.extensiblecatalog.ncip.v2.service.RequestId svcRequestId : svcRequestIds) {

            jaxbRequestIds.add(createRequestId(svcRequestId));

        }

        return jaxbRequestIds;

    }

    // Translate from JAXB API to Service API

    protected org.extensiblecatalog.ncip.v2.service.OnBehalfOfAgency createOnBehalfOfAgency(OnBehalfOfAgency jaxbOnBehalfOfAgency) {

        org.extensiblecatalog.ncip.v2.service.OnBehalfOfAgency svcOnBehalfOfAgency = null;

        if (jaxbOnBehalfOfAgency != null) {

            svcOnBehalfOfAgency = new org.extensiblecatalog.ncip.v2.service.OnBehalfOfAgency();
            svcOnBehalfOfAgency.setAgencyId(createAgencyId(jaxbOnBehalfOfAgency.getAgencyId()));

        }

        return svcOnBehalfOfAgency;

    }

    protected List<GregorianCalendar> convertDatesToSVC(List<XMLGregorianCalendar> jaxbDates) throws BindingException {

        List<GregorianCalendar> svcDates;


        if (jaxbDates != null && jaxbDates.size() > 0) {

            svcDates = new ArrayList<GregorianCalendar>();

            for (XMLGregorianCalendar jaxbDate : jaxbDates) {

                GregorianCalendar svcDate = convertDate(jaxbDate);

                if (jaxbDate != null) {

                    svcDates.add(svcDate);

                }
            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "XMLGregorianCalendar must be non-null and non-empty in convertDatesToSVC method.");

        }

        return svcDates;

    }

    protected List<XMLGregorianCalendar> convertDatesToJAXB(List<GregorianCalendar> dates) throws BindingException {

        List<XMLGregorianCalendar> jaxbDates;


        if (dates != null && dates.size() > 0) {

            jaxbDates = new ArrayList<XMLGregorianCalendar>();

            for (GregorianCalendar date : dates) {

                XMLGregorianCalendar jaxbDate = convertDate(date);

                if (jaxbDate != null) {

                    jaxbDates.add(jaxbDate);

                }
            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "GregorianCalendar must be non-null and non-empty in convertDatesToJAXB method.");

        }

        return jaxbDates;

    }

    /**
     * Convert a {@link GregorianCalendar} to an {@link XMLGregorianCalendar}.
     *
     * @param inCalendar the GregorianCalendar object representing the date to be converted
     * @return the XMLGregorianCalendar object that represents the date
     * @throws BindingException if conversion fails
     */
    protected static XMLGregorianCalendar convertDate(GregorianCalendar inCalendar) throws BindingException {
        XMLGregorianCalendar outDate;
        try {
            outDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(inCalendar);
        } catch (DatatypeConfigurationException e) {
            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Could not convert this date: "
                + inCalendar.toString(), e);
        }
        return outDate;
    }

    protected org.extensiblecatalog.ncip.v2.service.ItemId createItemId(ItemId jaxbItemId) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ItemId itemId = null;

        if (jaxbItemId != null) {

            itemId = new org.extensiblecatalog.ncip.v2.service.ItemId();

            itemId.setAgencyId(createAgencyId(jaxbItemId.getAgencyId()));

            try {
                if (jaxbItemId.getItemIdentifierType() != null) {
                    itemId.setItemIdentifierType(org.extensiblecatalog.ncip.v2.service.ItemIdentifierType.find(
                        jaxbItemId.getItemIdentifierType().getScheme(),
                        jaxbItemId.getItemIdentifierType().getValue()));
                }
            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }


            itemId.setItemIdentifierValue(jaxbItemId.getItemIdentifierValue());

        }

        return itemId;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.ItemId> createSVCItemIds(List<ItemId> jaxbItemIds) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.ItemId> svcItemIds = null;

        if (jaxbItemIds != null && jaxbItemIds.size() > 0) {

            svcItemIds = new ArrayList<org.extensiblecatalog.ncip.v2.service.ItemId>();

            for (ItemId jaxbItemId : jaxbItemIds) {

                svcItemIds.add(createItemId(jaxbItemId));

            }
        }

        return svcItemIds;

    }

    protected org.extensiblecatalog.ncip.v2.service.RequestId createRequestId(RequestId jaxbRequestId) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.RequestId svcRequestId = null;

        if (jaxbRequestId != null) {

            svcRequestId = new org.extensiblecatalog.ncip.v2.service.RequestId();
            svcRequestId.setAgencyId(createAgencyId(jaxbRequestId.getAgencyId()));
            svcRequestId.setRequestIdentifierType(convertRequestIdentifierType(jaxbRequestId.getRequestIdentifierType()));
            svcRequestId.setRequestIdentifierValue(jaxbRequestId.getRequestIdentifierValue());
        }

        return svcRequestId;
    }

    protected org.extensiblecatalog.ncip.v2.service.RequestIdentifierType convertRequestIdentifierType(
        SchemeValuePair jaxbRequestIdentifierType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.RequestIdentifierType svcRequestIdentifierType = null;

        if (jaxbRequestIdentifierType != null) {

            try {

                svcRequestIdentifierType = org.extensiblecatalog.ncip.v2.service.RequestIdentifierType.find(
                    jaxbRequestIdentifierType.getScheme(), jaxbRequestIdentifierType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcRequestIdentifierType;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.RequestId> createSVCRequestIds(List<RequestId> jaxbRequestIds) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.RequestId> svcRequestIds = null;

        if (jaxbRequestIds != null && jaxbRequestIds.size() > 0) {

            svcRequestIds = new ArrayList<org.extensiblecatalog.ncip.v2.service.RequestId>();

            for (RequestId jaxbRequestId : jaxbRequestIds) {

                svcRequestIds.add(createRequestId(jaxbRequestId));

            }
        }

        return svcRequestIds;

    }

    protected List<org.extensiblecatalog.ncip.v2.service.Problem> createSVCProblems(List<Problem> jaxbProblems)
        throws BindingException {
        List<org.extensiblecatalog.ncip.v2.service.Problem> problems
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.Problem>();
        for (Problem jaxbProblem : jaxbProblems) {
            problems.add(createProblem(jaxbProblem));
        }
        return problems;
    }

    protected org.extensiblecatalog.ncip.v2.service.Problem createProblem(Problem jaxbProblem) throws BindingException {
        org.extensiblecatalog.ncip.v2.service.Problem problem = new org.extensiblecatalog.ncip.v2.service.Problem();

        if (jaxbProblem.getProblemDetail() != null) {
            problem.setProblemDetail(jaxbProblem.getProblemDetail());
        }

        if (jaxbProblem.getProblemElement() != null) {
            problem.setProblemElement(jaxbProblem.getProblemElement());
        }

        if (jaxbProblem.getProblemValue() != null) {
            problem.setProblemValue(jaxbProblem.getProblemValue());
        }

        if (jaxbProblem.getProblemType() != null) {
            problem.setProblemType(convertProblemType(jaxbProblem.getProblemType()));
        }

        return problem;
    }

    protected org.extensiblecatalog.ncip.v2.service.ProblemType convertProblemType(
        SchemeValuePair jaxbProblemType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ProblemType svcProblemType = null;

        if (jaxbProblemType != null) {

            try {

                svcProblemType = org.extensiblecatalog.ncip.v2.service.ProblemType.find(
                    jaxbProblemType.getScheme(), jaxbProblemType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcProblemType;
    }

    protected org.extensiblecatalog.ncip.v2.service.ItemOptionalFields createItemOptionalFields(
        ItemOptionalFields jaxbItemOptionalFields)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ItemOptionalFields itemOptionalFields = null;

        if (jaxbItemOptionalFields != null) {

            if (jaxbItemOptionalFields.getBibliographicDescription() != null) {

                if (itemOptionalFields == null) {

                    itemOptionalFields = new org.extensiblecatalog.ncip.v2.service.ItemOptionalFields();
                }

                itemOptionalFields.setBibliographicDescription(createBibliographicDescription(
                    jaxbItemOptionalFields.getBibliographicDescription()));

            }

            if (jaxbItemOptionalFields.getCirculationStatus() != null) {

                if (itemOptionalFields == null) {

                    itemOptionalFields = new org.extensiblecatalog.ncip.v2.service.ItemOptionalFields();
                }

                try {

                    itemOptionalFields.setCirculationStatus(
                        org.extensiblecatalog.ncip.v2.service.CirculationStatus.find(
                            jaxbItemOptionalFields.getCirculationStatus().getScheme(),
                            jaxbItemOptionalFields.getCirculationStatus().getValue()));

                } catch (ServiceException e) {

                    throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);
                }

            }

            if (jaxbItemOptionalFields.getElectronicResource() != null) {

                if (itemOptionalFields == null) {

                    itemOptionalFields = new org.extensiblecatalog.ncip.v2.service.ItemOptionalFields();
                }

                itemOptionalFields.setElectronicResource(createElectronicResource(
                    jaxbItemOptionalFields.getElectronicResource()));

            }

            if (jaxbItemOptionalFields.getHoldQueueLength() != null) {

                if (itemOptionalFields == null) {

                    itemOptionalFields = new org.extensiblecatalog.ncip.v2.service.ItemOptionalFields();
                }

                itemOptionalFields.setHoldQueueLength(jaxbItemOptionalFields.getHoldQueueLength());

            }

            if (jaxbItemOptionalFields.getItemDescription() != null) {

                if (itemOptionalFields == null) {

                    itemOptionalFields = new org.extensiblecatalog.ncip.v2.service.ItemOptionalFields();
                }

                itemOptionalFields.setItemDescription(createItemDescription(
                    jaxbItemOptionalFields.getItemDescription()));

            }

            if (jaxbItemOptionalFields.getItemUseRestrictionType() != null
                && jaxbItemOptionalFields.getItemUseRestrictionType().size() > 0) {

                if (itemOptionalFields == null) {

                    itemOptionalFields = new org.extensiblecatalog.ncip.v2.service.ItemOptionalFields();
                }

                itemOptionalFields.setItemUseRestrictionTypes(convertItemUseRestrictionTypes(
                    jaxbItemOptionalFields.getItemUseRestrictionType()));

            }

            if (jaxbItemOptionalFields.getLocation() != null) {

                if (itemOptionalFields == null) {

                    itemOptionalFields = new org.extensiblecatalog.ncip.v2.service.ItemOptionalFields();
                }

                itemOptionalFields.setLocations(createSVCLocations(jaxbItemOptionalFields.getLocation()));

            }

            if (jaxbItemOptionalFields.getPhysicalCondition() != null) {

                if (itemOptionalFields == null) {

                    itemOptionalFields = new org.extensiblecatalog.ncip.v2.service.ItemOptionalFields();
                }

                itemOptionalFields.setPhysicalCondition(createPhysicalCondition(
                    jaxbItemOptionalFields.getPhysicalCondition()));

            }

            if (jaxbItemOptionalFields.getSecurityMarker() != null) {

                if (itemOptionalFields == null) {

                    itemOptionalFields = new org.extensiblecatalog.ncip.v2.service.ItemOptionalFields();
                }

                itemOptionalFields.setSecurityMarker(convertSecurityMarker(jaxbItemOptionalFields.getSecurityMarker()));

            }

            if (jaxbItemOptionalFields.getSensitizationFlag() != null) {

                if (itemOptionalFields == null) {

                    itemOptionalFields = new org.extensiblecatalog.ncip.v2.service.ItemOptionalFields();
                }

                itemOptionalFields.setSensitizationFlag(true);

            }
        }

        return itemOptionalFields;
    }

    protected org.extensiblecatalog.ncip.v2.service.SecurityMarker convertSecurityMarker(
        SchemeValuePair jaxbSecurityMarker) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.SecurityMarker svcSecurityMarker = null;

        if (jaxbSecurityMarker != null) {

            try {

                svcSecurityMarker = org.extensiblecatalog.ncip.v2.service.SecurityMarker.find(
                    jaxbSecurityMarker.getScheme(), jaxbSecurityMarker.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcSecurityMarker;
    }

    protected org.extensiblecatalog.ncip.v2.service.PhysicalCondition createPhysicalCondition(
        PhysicalCondition jaxbPhysicalCondition) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.PhysicalCondition physicalCondition = null;

        if (jaxbPhysicalCondition != null) {

            physicalCondition = new org.extensiblecatalog.ncip.v2.service.PhysicalCondition();

            if (jaxbPhysicalCondition.getPhysicalConditionDetails() != null) {

                physicalCondition.setPhysicalConditionDetails(jaxbPhysicalCondition.getPhysicalConditionDetails());
            }

            if (jaxbPhysicalCondition.getPhysicalConditionType() != null) {
                physicalCondition.setPhysicalConditionType(convertPhysicalConditionType(
                    jaxbPhysicalCondition.getPhysicalConditionType()));
            }
        }

        return physicalCondition;
    }

    protected org.extensiblecatalog.ncip.v2.service.PhysicalConditionType convertPhysicalConditionType(
        SchemeValuePair jaxbPhysicalConditionType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.PhysicalConditionType svcPhysicalConditionType = null;

        if (jaxbPhysicalConditionType != null) {

            try {

                svcPhysicalConditionType = org.extensiblecatalog.ncip.v2.service.PhysicalConditionType.find(
                    jaxbPhysicalConditionType.getScheme(), jaxbPhysicalConditionType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcPhysicalConditionType;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.Location> createSVCLocations(List<Location> jaxbLocations) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.Location> locations = null;

        if (jaxbLocations != null && jaxbLocations.size() > 0) {

            locations = new ArrayList<org.extensiblecatalog.ncip.v2.service.Location>();

            for (Location jaxbLocation : jaxbLocations) {

                locations.add(createLocation(jaxbLocation));

            }
        }

        return locations;

    }

    protected org.extensiblecatalog.ncip.v2.service.Location createLocation(Location jaxbLocation)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.Location location = null;

        if ( jaxbLocation != null ) {

            if (jaxbLocation.getLocationName() != null) {

                if ( location == null ) {

                    location = new org.extensiblecatalog.ncip.v2.service.Location();

                }

                location.setLocationName(convertLocationName(jaxbLocation.getLocationName()));

            }

            if (jaxbLocation.getLocationType() != null) {

                if ( location == null ) {

                    location = new org.extensiblecatalog.ncip.v2.service.Location();

                }

                location.setLocationType(convertLocationType(jaxbLocation.getLocationType()));

            }

            if (jaxbLocation.getValidFromDate() != null) {

                if ( location == null ) {

                    location = new org.extensiblecatalog.ncip.v2.service.Location();

                }

                location.setValidFromDate(convertDate(jaxbLocation.getValidFromDate()));

            }

            if (jaxbLocation.getValidToDate() != null) {

                if ( location == null ) {

                    location = new org.extensiblecatalog.ncip.v2.service.Location();

                }

                location.setValidToDate(convertDate(jaxbLocation.getValidToDate()));

            }

        }
        
        return location;

    }

    protected org.extensiblecatalog.ncip.v2.service.LocationType convertLocationType(
        SchemeValuePair jaxbLocationType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.LocationType svcLocationType = null;

        if (jaxbLocationType != null) {

            try {

                svcLocationType = org.extensiblecatalog.ncip.v2.service.LocationType.find(
                    jaxbLocationType.getScheme(), jaxbLocationType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcLocationType;
    }

    protected org.extensiblecatalog.ncip.v2.service.LocationName convertLocationName(LocationName jaxbLocationName) {
        org.extensiblecatalog.ncip.v2.service.LocationName locationName
            = new org.extensiblecatalog.ncip.v2.service.LocationName();

        List<org.extensiblecatalog.ncip.v2.service.LocationNameInstance> locationNameInstances =
            new ArrayList<org.extensiblecatalog.ncip.v2.service.LocationNameInstance>();

        for (LocationNameInstance jaxbLocationNameInstance : jaxbLocationName.getLocationNameInstance()) {
            locationNameInstances.add(createLocationNameInstance(jaxbLocationNameInstance));
        }

        locationName.setLocationNameInstances(locationNameInstances);

        return locationName;
    }

    protected org.extensiblecatalog.ncip.v2.service.LocationNameInstance createLocationNameInstance(
        LocationNameInstance jaxbLocationNameInstance) {
        org.extensiblecatalog.ncip.v2.service.LocationNameInstance locationNameInstance =
            new org.extensiblecatalog.ncip.v2.service.LocationNameInstance();

        if (jaxbLocationNameInstance.getLocationNameValue() != null) {
            locationNameInstance.setLocationNameValue(jaxbLocationNameInstance.getLocationNameValue());
        }

        if (jaxbLocationNameInstance.getLocationNameLevel() != null) {
            locationNameInstance.setLocationNameLevel(jaxbLocationNameInstance.getLocationNameLevel());
        }

        return locationNameInstance;
    }

    protected org.extensiblecatalog.ncip.v2.service.ItemDescription createItemDescription(
        ItemDescription jaxbItemDescription)
        throws BindingException {
        org.extensiblecatalog.ncip.v2.service.ItemDescription itemDescription
            = new org.extensiblecatalog.ncip.v2.service.ItemDescription();

        if (jaxbItemDescription.getCallNumber() != null) {
            itemDescription.setCallNumber(jaxbItemDescription.getCallNumber());
        }
        if (jaxbItemDescription.getCopyNumber() != null) {
            itemDescription.setCopyNumber(jaxbItemDescription.getCopyNumber());
        }
        if (jaxbItemDescription.getHoldingsInformation() != null) {
            itemDescription.setHoldingsInformation(createHoldingsInformation(
                jaxbItemDescription.getHoldingsInformation()));
        }
        if (jaxbItemDescription.getItemDescriptionLevel() != null) {
            itemDescription.setItemDescriptionLevel(convertItemDescriptionLevel(jaxbItemDescription.getItemDescriptionLevel()));
        }
        if (jaxbItemDescription.getNumberOfPieces() != null) {
            itemDescription.setNumberOfPieces(jaxbItemDescription.getNumberOfPieces());
        }

        return itemDescription;
    }

    protected org.extensiblecatalog.ncip.v2.service.ItemDescriptionLevel convertItemDescriptionLevel(
        SchemeValuePair jaxbItemDescriptionLevel) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ItemDescriptionLevel svcItemDescriptionLevel = null;

        if (jaxbItemDescriptionLevel != null) {

            try {

                svcItemDescriptionLevel = org.extensiblecatalog.ncip.v2.service.ItemDescriptionLevel.find(
                    jaxbItemDescriptionLevel.getScheme(), jaxbItemDescriptionLevel.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcItemDescriptionLevel;
    }
    
    protected org.extensiblecatalog.ncip.v2.service.HoldingsInformation createHoldingsInformation(
        HoldingsInformation jaxbHoldingsInformation)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.HoldingsInformation holdingsInformation = null;

        if ( jaxbHoldingsInformation != null ) {

            holdingsInformation = new org.extensiblecatalog.ncip.v2.service.HoldingsInformation();

            if (jaxbHoldingsInformation.getStructuredHoldingsData() != null
                && jaxbHoldingsInformation.getStructuredHoldingsData().size() > 0) {

                List<org.extensiblecatalog.ncip.v2.service.StructuredHoldingsData> structuredHoldingsDataList =
                    new ArrayList<org.extensiblecatalog.ncip.v2.service.StructuredHoldingsData>();

                for (StructuredHoldingsData jaxbStructuredHoldingsData
                    : jaxbHoldingsInformation.getStructuredHoldingsData()) {

                    if (jaxbStructuredHoldingsData.getContent() != null
                        && jaxbStructuredHoldingsData.getContent().size() > 0) {

                        for (Object obj : jaxbStructuredHoldingsData.getContent()) {

                            org.extensiblecatalog.ncip.v2.service.StructuredHoldingsData structuredHoldingsData =
                                new org.extensiblecatalog.ncip.v2.service.StructuredHoldingsData();

                            if (obj instanceof HoldingsChronology) {
                                HoldingsChronology jaxbHoldingsChronology = (HoldingsChronology)obj;
                                structuredHoldingsData.setHoldingsChronology(
                                    createHoldingsChronology(jaxbHoldingsChronology));
                            } else if (obj instanceof HoldingsEnumeration) {
                                HoldingsEnumeration jaxbHoldingsEnumeration = (HoldingsEnumeration)obj;
                                structuredHoldingsData.setHoldingsEnumeration(
                                    createHoldingsEnumeration(jaxbHoldingsEnumeration));
                            } else {
                                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected object class "
                                    + obj.getClass().getName() + " in StructuredHoldingsData.");
                            }

                            structuredHoldingsDataList.add(structuredHoldingsData);

                        }

                    } else {

                        throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                            "StructuredHoldingsData must not be empty.");

                    }
                }

                if ( structuredHoldingsDataList.size() > 0 ) {

                    holdingsInformation.setStructuredHoldingsData(structuredHoldingsDataList);

                }

            } else if (jaxbHoldingsInformation.getUnstructuredHoldingsData() != null
                && jaxbHoldingsInformation.getUnstructuredHoldingsData().length() > 0) {

                holdingsInformation.setUnstructuredHoldingsData(jaxbHoldingsInformation.getUnstructuredHoldingsData());

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Either StructuredHoldingsData or UnstructuredHoldingsData must be non-null and non-empty "
                        + "in HoldingsInformation.");

            }

        }

        return holdingsInformation;
    }

    protected org.extensiblecatalog.ncip.v2.service.SummaryHoldingsInformation createSummaryHoldingsInformation(
        SummaryHoldingsInformation jaxbSummaryHoldingsInformation)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.SummaryHoldingsInformation summarySummaryHoldingsInformation = null;

        if ( jaxbSummaryHoldingsInformation != null ) {

            summarySummaryHoldingsInformation = new org.extensiblecatalog.ncip.v2.service.SummaryHoldingsInformation();

            if (jaxbSummaryHoldingsInformation.getStructuredHoldingsData() != null
                && jaxbSummaryHoldingsInformation.getStructuredHoldingsData().size() > 0) {
                for (StructuredHoldingsData jaxbStructuredHoldingsData
                    : jaxbSummaryHoldingsInformation.getStructuredHoldingsData()) {
                    if (jaxbStructuredHoldingsData.getContent() != null
                        && jaxbStructuredHoldingsData.getContent().size() > 0) {

                        List<org.extensiblecatalog.ncip.v2.service.StructuredHoldingsData> structuredHoldingsDataList =
                            new ArrayList<org.extensiblecatalog.ncip.v2.service.StructuredHoldingsData>();

                        for (Object obj : jaxbStructuredHoldingsData.getContent()) {

                            org.extensiblecatalog.ncip.v2.service.StructuredHoldingsData structuredHoldingsData =
                                new org.extensiblecatalog.ncip.v2.service.StructuredHoldingsData();

                            if (obj instanceof HoldingsChronology) {
                                HoldingsChronology jaxbHoldingsChronology = (HoldingsChronology)obj;
                                structuredHoldingsData.setHoldingsChronology(
                                    createHoldingsChronology(jaxbHoldingsChronology));
                            } else if (obj instanceof HoldingsEnumeration) {
                                HoldingsEnumeration jaxbHoldingsEnumeration = (HoldingsEnumeration)obj;
                                structuredHoldingsData.setHoldingsEnumeration(
                                    createHoldingsEnumeration(jaxbHoldingsEnumeration));
                            } else {
                                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected object class "
                                    + obj.getClass().getName() + " in StructuredHoldingsData.");
                            }

                            structuredHoldingsDataList.add(structuredHoldingsData);

                        }

                        summarySummaryHoldingsInformation.setStructuredHoldingsData(structuredHoldingsDataList);

                    } else {

                        throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                            "StructuredHoldingsData must not be empty.");

                    }
                }
            } else if (jaxbSummaryHoldingsInformation.getUnstructuredHoldingsData() != null
                && jaxbSummaryHoldingsInformation.getUnstructuredHoldingsData().length() > 0) {

                summarySummaryHoldingsInformation.setUnstructuredHoldingsData(jaxbSummaryHoldingsInformation.getUnstructuredHoldingsData());

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Either StructuredHoldingsData or UnstructuredHoldingsData must be non-null and non-empty "
                        + "in SummaryHoldingsInformation.");

            }

        }

        return summarySummaryHoldingsInformation;
    }

    protected org.extensiblecatalog.ncip.v2.service.HoldingsEnumeration createHoldingsEnumeration(
        HoldingsEnumeration jaxbHoldingsEnumeration) throws BindingException {
        org.extensiblecatalog.ncip.v2.service.HoldingsEnumeration holdingsEnumeration =
            new org.extensiblecatalog.ncip.v2.service.HoldingsEnumeration();

        List<org.extensiblecatalog.ncip.v2.service.EnumerationLevelInstance> enumerationLevelInstances
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.EnumerationLevelInstance>();

        for (EnumerationLevelInstance enumerationLevelInstance
            : jaxbHoldingsEnumeration.getEnumerationLevelInstance()) {
            enumerationLevelInstances.add(createEnumerationLevelInstance(enumerationLevelInstance));
        }

        holdingsEnumeration.setEnumerationLevelInstances(enumerationLevelInstances);

        return holdingsEnumeration;
    }

    protected org.extensiblecatalog.ncip.v2.service.EnumerationLevelInstance createEnumerationLevelInstance(
        EnumerationLevelInstance jaxbEnumerationLevelInstance) throws BindingException {
        org.extensiblecatalog.ncip.v2.service.EnumerationLevelInstance enumerationLevelInstance =
            new org.extensiblecatalog.ncip.v2.service.EnumerationLevelInstance();

        for (Object obj : jaxbEnumerationLevelInstance.getContent()) {
            if (obj instanceof JAXBElement) {
                JAXBElement jaxbElement = (JAXBElement)obj;
                if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("EnumerationCaption") == 0) {
                    enumerationLevelInstance.setEnumerationCaption((String)jaxbElement.getValue());
                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("EnumerationValue") == 0) {
                    enumerationLevelInstance.setEnumerationValue((String)jaxbElement.getValue());
                } else {
                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected element name "
                        + jaxbElement.getName().getLocalPart() + " in EnumerationLevelInstance.");
                }
            } else if (obj instanceof BigDecimal) {
                enumerationLevelInstance.setEnumerationLevel((BigDecimal)obj);
            } else {
                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected object class "
                    + obj.getClass().getName() + " in EnumerationLevelInstance.");
            }
        }

        return enumerationLevelInstance;
    }

    protected org.extensiblecatalog.ncip.v2.service.HoldingsChronology createHoldingsChronology(
        HoldingsChronology jaxbHoldingsChronology) throws BindingException {
        org.extensiblecatalog.ncip.v2.service.HoldingsChronology holdingsChronology =
            new org.extensiblecatalog.ncip.v2.service.HoldingsChronology();

        List<org.extensiblecatalog.ncip.v2.service.ChronologyLevelInstance> chronologyLevelInstances
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.ChronologyLevelInstance>();

        for (ChronologyLevelInstance chronologyLevelInstance : jaxbHoldingsChronology.getChronologyLevelInstance()) {
            chronologyLevelInstances.add(createChronologyLevelInstance(chronologyLevelInstance));
        }

        holdingsChronology.setChronologyLevelInstances(chronologyLevelInstances);

        return holdingsChronology;
    }

    protected org.extensiblecatalog.ncip.v2.service.ChronologyLevelInstance createChronologyLevelInstance(
        ChronologyLevelInstance jaxbChronologyLevelInstance) throws BindingException {
        org.extensiblecatalog.ncip.v2.service.ChronologyLevelInstance chronologyLevelInstance =
            new org.extensiblecatalog.ncip.v2.service.ChronologyLevelInstance();

        for (Object obj : jaxbChronologyLevelInstance.getContent()) {
            if (obj instanceof JAXBElement) {
                JAXBElement jaxbElement = (JAXBElement)obj;
                if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("ChronologyCaption") == 0) {
                    chronologyLevelInstance.setChronologyCaption((String)jaxbElement.getValue());
                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("ChronologyValue") == 0) {
                    chronologyLevelInstance.setChronologyValue((String)jaxbElement.getValue());
                } else {
                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected element name "
                        + jaxbElement.getName().getLocalPart() + " in ChronologyLevelInstance.");
                }
            } else if (obj instanceof BigDecimal) {
                chronologyLevelInstance.setChronologyLevel((BigDecimal)obj);
            } else {
                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected object class "
                    + obj.getClass().getName() + " in ChronologyLevelInstance.");
            }
        }

        return chronologyLevelInstance;
    }

    protected org.extensiblecatalog.ncip.v2.service.ElectronicResource createElectronicResource(
        ElectronicResource jaxbElectronicResource) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ElectronicResource svcElectronicResource = null;

        if (jaxbElectronicResource != null) {

            svcElectronicResource = new org.extensiblecatalog.ncip.v2.service.ElectronicResource();

            if (jaxbElectronicResource.getActualResource() != null
                && jaxbElectronicResource.getElectronicDataFormatType() != null
                && jaxbElectronicResource.getReferenceToResource() == null) {

                svcElectronicResource.setActualResource(jaxbElectronicResource.getActualResource());

                svcElectronicResource.setElectronicDataFormatType(convertElectronicDataFormatType(
                    jaxbElectronicResource.getElectronicDataFormatType()));

            } else if (jaxbElectronicResource.getActualResource() == null
                && jaxbElectronicResource.getElectronicDataFormatType() == null
                && jaxbElectronicResource.getReferenceToResource() != null) {

                svcElectronicResource.setReferenceToResource(jaxbElectronicResource.getReferenceToResource());

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Either ActualResource and ElectronicDataFormatType must be non-null "
                        + "(and ReferenceToResource must be null), or ReferenceToResource must be non-null "
                        + "(and ActualResource and ElectronicDataFormatType must be null).");

            }

        }

        return svcElectronicResource;

    }

    protected org.extensiblecatalog.ncip.v2.service.BibliographicDescription createBibliographicDescription(
        BibliographicDescription jaxbBibliographicDescription) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.BibliographicDescription bibliographicDescription = null;

        if ( jaxbBibliographicDescription != null) {

            bibliographicDescription = new org.extensiblecatalog.ncip.v2.service.BibliographicDescription();

            if (jaxbBibliographicDescription.getAuthor() != null) {
                bibliographicDescription.setAuthor(jaxbBibliographicDescription.getAuthor());
            }

            if (jaxbBibliographicDescription.getAuthorOfComponent() != null) {
                bibliographicDescription.setAuthorOfComponent(jaxbBibliographicDescription.getAuthorOfComponent());
            }

            if (jaxbBibliographicDescription.getBibliographicItemId() != null
                && jaxbBibliographicDescription.getBibliographicItemId().size() > 0) {
                bibliographicDescription.setBibliographicItemIds(
                    createSVCBibliographicItemIds(jaxbBibliographicDescription.getBibliographicItemId()));
            }

            if (jaxbBibliographicDescription.getBibliographicRecordId() != null
                && jaxbBibliographicDescription.getBibliographicRecordId().size() > 0) {
                bibliographicDescription.setBibliographicRecordIds(
                    createSVCBibliographicRecordIds(jaxbBibliographicDescription.getBibliographicRecordId()));
            }

            if (jaxbBibliographicDescription.getComponentId() != null) {
                bibliographicDescription.setComponentId(createComponentId(jaxbBibliographicDescription.getComponentId()));
            }

            if (jaxbBibliographicDescription.getEdition() != null) {
                bibliographicDescription.setEdition(jaxbBibliographicDescription.getEdition());
            }

            if (jaxbBibliographicDescription.getPagination() != null) {
                bibliographicDescription.setPagination(jaxbBibliographicDescription.getPagination());
            }

            if (jaxbBibliographicDescription.getPlaceOfPublication() != null) {
                bibliographicDescription.setPlaceOfPublication(jaxbBibliographicDescription.getPlaceOfPublication());
            }

            if (jaxbBibliographicDescription.getPublicationDate() != null) {
                bibliographicDescription.setPublicationDate(jaxbBibliographicDescription.getPublicationDate());
            }

            if (jaxbBibliographicDescription.getPublicationDateOfComponent() != null) {
                bibliographicDescription.setPublicationDateOfComponent(
                    jaxbBibliographicDescription.getPublicationDateOfComponent());
            }

            if (jaxbBibliographicDescription.getPublisher() != null) {
                bibliographicDescription.setPublisher(jaxbBibliographicDescription.getPublisher());
            }

            if (jaxbBibliographicDescription.getSeriesTitleNumber() != null) {
                bibliographicDescription.setSeriesTitleNumber(jaxbBibliographicDescription.getSeriesTitleNumber());
            }

            if (jaxbBibliographicDescription.getTitle() != null) {
                bibliographicDescription.setTitle(jaxbBibliographicDescription.getTitle());
            }

            if (jaxbBibliographicDescription.getTitleOfComponent() != null) {
                bibliographicDescription.setTitleOfComponent(jaxbBibliographicDescription.getTitleOfComponent());
            }

            if (jaxbBibliographicDescription.getBibliographicLevel() != null) {
                bibliographicDescription.setBibliographicLevel(convertBibliographicLevel(
                    jaxbBibliographicDescription.getBibliographicLevel()));
            }

            if (jaxbBibliographicDescription.getSponsoringBody() != null) {
                bibliographicDescription.setSponsoringBody(jaxbBibliographicDescription.getSponsoringBody());
            }

            if (jaxbBibliographicDescription.getElectronicDataFormatType() != null) {
                bibliographicDescription.setElectronicDataFormatType(
                    convertElectronicDataFormatType(jaxbBibliographicDescription.getElectronicDataFormatType()));
            }

            if (jaxbBibliographicDescription.getLanguage() != null) {
                bibliographicDescription.setLanguage(convertLanguage(jaxbBibliographicDescription.getLanguage()));
            }

            if (jaxbBibliographicDescription.getMediumType() != null) {
                bibliographicDescription.setMediumType(convertMediumType(jaxbBibliographicDescription.getMediumType()));
            }

        }

        return bibliographicDescription;
    }

    protected org.extensiblecatalog.ncip.v2.service.BibliographicLevel convertBibliographicLevel(
        SchemeValuePair jaxbBibliographicLevel) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.BibliographicLevel svcBibliographicLevel = null;

        if (jaxbBibliographicLevel != null) {

            try {

                svcBibliographicLevel = org.extensiblecatalog.ncip.v2.service.BibliographicLevel.find(
                    jaxbBibliographicLevel.getScheme(), jaxbBibliographicLevel.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcBibliographicLevel;
    }

    protected org.extensiblecatalog.ncip.v2.service.ElectronicDataFormatType convertElectronicDataFormatType(
        SchemeValuePair jaxbElectronicDataFormatType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ElectronicDataFormatType svcElectronicDataFormatType = null;

        if (jaxbElectronicDataFormatType != null) {

            try {

                svcElectronicDataFormatType = org.extensiblecatalog.ncip.v2.service.ElectronicDataFormatType.find(
                    jaxbElectronicDataFormatType.getScheme(), jaxbElectronicDataFormatType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcElectronicDataFormatType;
    }

    protected org.extensiblecatalog.ncip.v2.service.Language convertLanguage(
        SchemeValuePair jaxbLanguage) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.Language svcLanguage = null;

        if (jaxbLanguage != null) {

            try {

                svcLanguage = org.extensiblecatalog.ncip.v2.service.Language.find(
                    jaxbLanguage.getScheme(), jaxbLanguage.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcLanguage;
    }

    protected org.extensiblecatalog.ncip.v2.service.MediumType convertMediumType(
        SchemeValuePair jaxbMediumType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.MediumType svcMediumType = null;

        if (jaxbMediumType != null) {

            try {

                svcMediumType = org.extensiblecatalog.ncip.v2.service.MediumType.find(
                    jaxbMediumType.getScheme(), jaxbMediumType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcMediumType;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.BibliographicDescription> createSVCBibliographicDescription(
        List<BibliographicDescription> jaxbBibliographicDescriptions) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.BibliographicDescription> bibliographicDescriptions
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.BibliographicDescription>();

        for (BibliographicDescription bibliographicDescription : jaxbBibliographicDescriptions) {
            bibliographicDescriptions.add(createBibliographicDescription(bibliographicDescription));
        }

        return bibliographicDescriptions;
    }

    protected org.extensiblecatalog.ncip.v2.service.ComponentId createComponentId(ComponentId jaxbComponentId)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ComponentId componentId
            = new org.extensiblecatalog.ncip.v2.service.ComponentId();

        if (jaxbComponentId.getComponentIdentifier() != null) {

            componentId.setComponentIdentifier(jaxbComponentId.getComponentIdentifier());

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "ComponentIdentifier must be non-null in ComponentId.");

        }

        if (jaxbComponentId.getComponentIdentifierType() != null) {

            componentId.setComponentIdentifierType(convertComponentIdentifierType(jaxbComponentId.getComponentIdentifierType()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "ComponentIdentifierType must be non-null in ComponentId.");

        }

        return componentId;
    }

    protected org.extensiblecatalog.ncip.v2.service.ComponentIdentifierType convertComponentIdentifierType(
        SchemeValuePair jaxbComponentIdentifierType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ComponentIdentifierType svcComponentIdentifierType = null;

        if (jaxbComponentIdentifierType != null) {

            try {

                svcComponentIdentifierType = org.extensiblecatalog.ncip.v2.service.ComponentIdentifierType.find(
                    jaxbComponentIdentifierType.getScheme(), jaxbComponentIdentifierType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcComponentIdentifierType;
    }

    protected org.extensiblecatalog.ncip.v2.service.BibliographicRecordId createBibliographicRecordId(
        BibliographicRecordId jaxbBibliographicRecordId) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.BibliographicRecordId bibliographicRecordId = null;

        if ( jaxbBibliographicRecordId != null ) {

            bibliographicRecordId = new org.extensiblecatalog.ncip.v2.service.BibliographicRecordId();

            if (jaxbBibliographicRecordId.getBibliographicRecordIdentifier() != null) {

                bibliographicRecordId.setBibliographicRecordIdentifier(
                    jaxbBibliographicRecordId.getBibliographicRecordIdentifier());

                if (jaxbBibliographicRecordId.getAgencyId() != null) {
                    bibliographicRecordId.setAgencyId(createAgencyId(jaxbBibliographicRecordId.getAgencyId()));
                } else if (jaxbBibliographicRecordId.getBibliographicRecordIdentifierCode() != null) {
                    bibliographicRecordId.setBibliographicRecordIdentifierCode(
                        convertBibliographicRecordIdentifierCode(jaxbBibliographicRecordId.getBibliographicRecordIdentifierCode()));
                } else {

                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                        "AgencyId or BibliographicRecordIdentifierCode must be non-null in BibliographicRecordId.");

                }

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "BibliographicRecordIdentifier must be non-null in BibliographicRecordId.");

            }
        }

        return bibliographicRecordId;

    }

    protected List<org.extensiblecatalog.ncip.v2.service.BibliographicRecordId> createSVCBibliographicRecordIds(
        List<BibliographicRecordId> jaxbBibliographicRecordIds) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.BibliographicRecordId> svcBibliographicRecordIds
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.BibliographicRecordId>();

        if (jaxbBibliographicRecordIds != null && jaxbBibliographicRecordIds.size() > 0) {

            for (BibliographicRecordId jaxbBibliographicRecordId : jaxbBibliographicRecordIds) {

                // TODO: Should we be testing for null returns?
                svcBibliographicRecordIds.add(createBibliographicRecordId(jaxbBibliographicRecordId));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "List<BibliographicRecordId> must be non-null and non-empty"
                    + " in createSVCBibliographicRecordIds method.");

        }

        return svcBibliographicRecordIds;

    }

    protected org.extensiblecatalog.ncip.v2.service.BibliographicItemId createBibliographicItemId(
        BibliographicItemId jaxbBibliographicItemId) throws BindingException {
        org.extensiblecatalog.ncip.v2.service.BibliographicItemId bibliographicItemId
            = new org.extensiblecatalog.ncip.v2.service.BibliographicItemId();

        if (jaxbBibliographicItemId.getBibliographicItemIdentifier() != null) {

            bibliographicItemId.setBibliographicItemIdentifier(
                jaxbBibliographicItemId.getBibliographicItemIdentifier());

            if (jaxbBibliographicItemId.getBibliographicItemIdentifierCode() != null) {
                bibliographicItemId.setBibliographicItemIdentifierCode(
                    convertBibliographicItemIdentifierCode(jaxbBibliographicItemId.getBibliographicItemIdentifierCode()));
            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "BibliographicItemIdentifier must be non-null in BibliographicItemId.");

        }

        return bibliographicItemId;
    }

    protected List<BibliographicItemId> createJAXBBibliographicItemIds(List<org.extensiblecatalog.ncip.v2.service.BibliographicItemId> svcBibliographicItemIds) throws BindingException {

        List<BibliographicItemId> jaxbBibliographicItemIds = new ArrayList<BibliographicItemId>();

        if (svcBibliographicItemIds != null && svcBibliographicItemIds.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.BibliographicItemId svcBibliographicItemId : svcBibliographicItemIds) {

                // TODO: Should we be testing for null returns?
                jaxbBibliographicItemIds.add(createBibliographicItemId(svcBibliographicItemId));

            }

        }

        return jaxbBibliographicItemIds;

    }

    public org.extensiblecatalog.ncip.v2.service.NCIPInitiationData createInitiationData(NCIPMessage initMsg)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.NCIPInitiationData initiationData;
        if (initMsg.getAcceptItem() != null) {
            initiationData = createAcceptItemInitiationData(initMsg.getAcceptItem());
        } else if (initMsg.getCheckInItem() != null) {
            initiationData = createCheckInItemInitiationData(initMsg.getCheckInItem());
        } else if (initMsg.getCheckOutItem() != null) {
            initiationData = createCheckOutItemInitiationData(initMsg.getCheckOutItem());
        } else if (initMsg.getLookupItem() != null) {
            initiationData = createLookupItemInitiationData(initMsg.getLookupItem());
        } else if (initMsg.getLookupRequest() != null) {
            initiationData = createLookupRequestInitiationData(initMsg.getLookupRequest());
        } else if (initMsg.getLookupUser() != null) {
            initiationData = createLookupUserInitiationData(initMsg.getLookupUser());
        } else if (initMsg.getRenewItem() != null) {
            initiationData = createRenewItemInitiationData(initMsg.getRenewItem());
        } else if (initMsg.getRequestItem() != null) {
            initiationData = createRequestItemInitiationData(initMsg.getRequestItem());
        } else if (initMsg.getExt() != null) {
            Ext extension = initMsg.getExt();
            Object extensionMsg = extension.getAny().get(0);
            if (extensionMsg instanceof LookupItemSet) {
                initiationData = createLookupItemSetInitiationData((LookupItemSet)extensionMsg);
            }
            else {
                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unsupported Extension: "
                    + extensionMsg.getClass().getName() + ".");
            }
        } else {
            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unsupported Initiation Message type.");
        }

        return initiationData;
    }

    protected NCIPMessage createLookupItemInitiationMessage(
        org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData lookupItemInitiationData)
        throws BindingException {

        NCIPMessage ncipInitMsg = new NCIPMessage();
        ncipInitMsg.setVersion(NCIP_VERSION_V2_0);
        LookupItem jaxbLookupItem = new LookupItem();

        ncipInitMsg.setLookupItem(jaxbLookupItem);

        if (lookupItemInitiationData.getInitiationHeader() != null) {
            jaxbLookupItem.setInitiationHeader(createInitiationHeader(lookupItemInitiationData.getInitiationHeader()));
        }

        if (lookupItemInitiationData.getItemId() != null) {
            if (lookupItemInitiationData.getRequestId() != null) {
                jaxbLookupItem.setRequestId(createRequestId(lookupItemInitiationData.getRequestId()));
            }
            jaxbLookupItem.setItemId(createItemId(lookupItemInitiationData.getItemId()));
        } else {
            if (lookupItemInitiationData.getRequestId() != null) {
                jaxbLookupItem.setRequestId(createRequestId(lookupItemInitiationData.getRequestId()));
            } else {
                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "ItemId or RequestId or both must be non-null in Lookup Item.");
            }
        }

        if (lookupItemInitiationData.getCurrentBorrowerDesired()) {
            jaxbLookupItem.setCurrentBorrowerDesired(objectFactory.createCurrentBorrowerDesired());
        }

        if (lookupItemInitiationData.getCurrentRequestersDesired()) {
            jaxbLookupItem.setCurrentRequestersDesired(objectFactory.createCurrentRequestersDesired());
        }

        if (lookupItemInitiationData.getBibliographicDescriptionDesired()) {
            jaxbLookupItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.BIBLIOGRAPHIC_DESCRIPTION));
        }

        if (lookupItemInitiationData.getCirculationStatusDesired()) {
            jaxbLookupItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.CIRCULATION_STATUS));
        }

        if (lookupItemInitiationData.getElectronicResourceDesired()) {
            jaxbLookupItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ELECTRONIC_RESOURCE));
        }

        if (lookupItemInitiationData.getHoldQueueLengthDesired()) {
            jaxbLookupItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.HOLD_QUEUE_LENGTH));
        }

        if (lookupItemInitiationData.getItemDescriptionDesired()) {
            jaxbLookupItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ITEM_DESCRIPTION));
        }

        if (lookupItemInitiationData.getItemUseRestrictionTypeDesired()) {
            jaxbLookupItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ITEM_USE_RESTRICTION_TYPE));
        }

        if (lookupItemInitiationData.getLocationDesired()) {
            jaxbLookupItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.LOCATION));
        }

        if (lookupItemInitiationData.getPhysicalConditionDesired()) {
            jaxbLookupItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.PHYSICAL_CONDITION));
        }

        if (lookupItemInitiationData.getSecurityMarkerDesired()) {
            jaxbLookupItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.SECURITY_MARKER));
        }

        if (lookupItemInitiationData.getSensitizationFlagDesired()) {
            jaxbLookupItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.SENSITIZATION_FLAG));
        }

        return ncipInitMsg;
    }

    protected NCIPMessage createLookupItemResponseMessage(
        org.extensiblecatalog.ncip.v2.service.LookupItemResponseData lookupItemResponseData)
        throws BindingException {

        NCIPMessage ncipRespMsg = new NCIPMessage();
        ncipRespMsg.setVersion(NCIP_VERSION_V2_0);
        LookupItemResponse lookupItemResponse = new LookupItemResponse();

        ncipRespMsg.setLookupItemResponse(lookupItemResponse);

        if (lookupItemResponseData.getResponseHeader() != null) {
            ResponseHeader respHdr = createResponseHeader(lookupItemResponseData.getResponseHeader());
            lookupItemResponse.getContent().add(respHdr);
        }

        //response message should be either with problems or normal data
        if (lookupItemResponseData.getProblems() != null
            && lookupItemResponseData.getProblems().size() > 0) {

            lookupItemResponse.getContent().addAll(createJAXBProblems(lookupItemResponseData.getProblems()));

        } else {

            //2. normal data
            if (lookupItemResponseData.getItemId() != null) {
                if (lookupItemResponseData.getRequestId() != null) {
                    lookupItemResponse.getContent().add(createRequestId(lookupItemResponseData.getRequestId()));
                }
                lookupItemResponse.getContent().add(createItemId(lookupItemResponseData.getItemId()));
            } else {
                if (lookupItemResponseData.getRequestId() != null) {
                    lookupItemResponse.getContent().add(createRequestId(lookupItemResponseData.getRequestId()));
                } else {
                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                        "ItemId or RequestId or both must be non-null in Lookup Item Response.");
                }
            }

            if (lookupItemResponseData.getItemOptionalFields() != null) {
                lookupItemResponse.getContent().add(createItemOptionalFields(
                    lookupItemResponseData.getItemOptionalFields()));
            }

            if (lookupItemResponseData.getHoldPickupDate() != null) {
                lookupItemResponse.getContent().add(
                    objectFactory.createHoldPickupDate(convertDate(lookupItemResponseData.getHoldPickupDate())));
            }

            if (lookupItemResponseData.getDateRecalled() != null) {
                lookupItemResponse.getContent().add(
                    objectFactory.createDateRecalled(convertDate(lookupItemResponseData.getDateRecalled())));
            }

            if (lookupItemResponseData.getItemTransaction() != null) {
                lookupItemResponse.getContent().add(createItemTransaction(lookupItemResponseData.getItemTransaction()));
            }
        }
        return ncipRespMsg;
    }

    protected NCIPMessage createLookupItemSetInitiationMessage(
        org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData lookupItemSetInitiationData)
        throws BindingException {

        NCIPMessage ncipInitMsg = new NCIPMessage();
        ncipInitMsg.setVersion(NCIP_VERSION_V2_0_WITH_ILS_DI_EXTENSIONS);
        LookupItemSet jaxbLookupItemSet = new LookupItemSet();

        Ext ext = new Ext();
        ext.getAny().add(jaxbLookupItemSet);
        ncipInitMsg.setExt(ext);

        if (lookupItemSetInitiationData.getInitiationHeader() != null) {
            jaxbLookupItemSet.setInitiationHeader(createInitiationHeader(lookupItemSetInitiationData.getInitiationHeader()));
        }

        if (lookupItemSetInitiationData.getBibliographicIds() != null
            && lookupItemSetInitiationData.getBibliographicIds().size() > 0) {

            jaxbLookupItemSet.getBibliographicId().addAll(createJAXBBibliographicIds(lookupItemSetInitiationData.getBibliographicIds()));

        } else if (lookupItemSetInitiationData.getHoldingsSetIds() != null
            && lookupItemSetInitiationData.getHoldingsSetIds().size() > 0 ) {

            jaxbLookupItemSet.getHoldingsSetId().addAll(lookupItemSetInitiationData.getHoldingsSetIds());

        } else if (lookupItemSetInitiationData.getItemIds() != null
            && lookupItemSetInitiationData.getItemIds().size() > 0 ) {

            jaxbLookupItemSet.getItemId().addAll(createJAXBItemIds(lookupItemSetInitiationData.getItemIds()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "One of BibliographicId, ItemId or HoldingsSetId must be non-null in Lookup Item Set.");

        }

        if (lookupItemSetInitiationData.getCurrentBorrowerDesired()) {
            jaxbLookupItemSet.setCurrentBorrowerDesired(objectFactory.createCurrentBorrowerDesired());
        }

        if (lookupItemSetInitiationData.getCurrentRequestersDesired()) {
            jaxbLookupItemSet.setCurrentRequestersDesired(objectFactory.createCurrentRequestersDesired());
        }

        if (lookupItemSetInitiationData.getBibliographicDescriptionDesired()) {
            jaxbLookupItemSet.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.BIBLIOGRAPHIC_DESCRIPTION));
        }

        if (lookupItemSetInitiationData.getCirculationStatusDesired()) {
            jaxbLookupItemSet.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.CIRCULATION_STATUS));
        }

        if (lookupItemSetInitiationData.getElectronicResourceDesired()) {
            jaxbLookupItemSet.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ELECTRONIC_RESOURCE));
        }

        if (lookupItemSetInitiationData.getHoldQueueLengthDesired()) {
            jaxbLookupItemSet.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.HOLD_QUEUE_LENGTH));
        }

        if (lookupItemSetInitiationData.getItemDescriptionDesired()) {
            jaxbLookupItemSet.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ITEM_DESCRIPTION));
        }

        if (lookupItemSetInitiationData.getItemUseRestrictionTypeDesired()) {
            jaxbLookupItemSet.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ITEM_USE_RESTRICTION_TYPE));
        }

        if (lookupItemSetInitiationData.getLocationDesired()) {
            jaxbLookupItemSet.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.LOCATION));
        }

        if (lookupItemSetInitiationData.getPhysicalConditionDesired()) {
            jaxbLookupItemSet.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.PHYSICAL_CONDITION));
        }

        if (lookupItemSetInitiationData.getSecurityMarkerDesired()) {
            jaxbLookupItemSet.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.SECURITY_MARKER));
        }

        if (lookupItemSetInitiationData.getSensitizationFlagDesired()) {
            jaxbLookupItemSet.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.SENSITIZATION_FLAG));
        }

        if (lookupItemSetInitiationData.getMaximumItemsCount() != null) {
            jaxbLookupItemSet.setMaximumItemsCount(lookupItemSetInitiationData.getMaximumItemsCount());
        }

        if (lookupItemSetInitiationData.getNextItemToken() != null) {
            jaxbLookupItemSet.setNextItemToken(lookupItemSetInitiationData.getNextItemToken());
        }

        return ncipInitMsg;
    }

    protected NCIPMessage createLookupItemSetResponseMessage(
        org.extensiblecatalog.ncip.v2.service.LookupItemSetResponseData lookupItemSetResponseData)
        throws BindingException {

        NCIPMessage ncipRespMsg = new NCIPMessage();
        ncipRespMsg.setVersion(NCIP_VERSION_V2_0_WITH_ILS_DI_EXTENSIONS);
        LookupItemSetResponse lookupItemSetResponse = new LookupItemSetResponse();

        Ext ext = new Ext();
        ext.getAny().add(lookupItemSetResponse);
        ncipRespMsg.setExt(ext);

        if (lookupItemSetResponseData.getResponseHeader() != null) {
            ResponseHeader respHdr = createResponseHeader(lookupItemSetResponseData.getResponseHeader());
            lookupItemSetResponse.setResponseHeader(respHdr);
        }

        if (lookupItemSetResponseData.getBibInformations() != null
            && lookupItemSetResponseData.getBibInformations().size() > 0) {

            lookupItemSetResponse.getBibInformation().addAll(createJAXBBibInformations(lookupItemSetResponseData.getBibInformations()));
            lookupItemSetResponse.setNextItemToken(lookupItemSetResponseData.getNextItemToken());

        } else if (lookupItemSetResponseData.getProblems() != null
            && lookupItemSetResponseData.getProblems().size() > 0) {

            lookupItemSetResponse.getProblem().addAll(createJAXBProblems(lookupItemSetResponseData.getProblems()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "BibInformation or Problem must be non-null in LookupItemSetResponse.");

        }

        return ncipRespMsg;

    }

    protected NCIPMessage createLookupRequestInitiationMessage(
        org.extensiblecatalog.ncip.v2.service.LookupRequestInitiationData lookupRequestInitiationData)
        throws BindingException {

        NCIPMessage ncipInitMsg = new NCIPMessage();
        ncipInitMsg.setVersion(NCIP_VERSION_V2_0);
        LookupRequest jaxbLookupRequest = new LookupRequest();

        ncipInitMsg.setLookupRequest(jaxbLookupRequest);

        if (lookupRequestInitiationData.getInitiationHeader() != null) {
            jaxbLookupRequest.setInitiationHeader(createInitiationHeader(lookupRequestInitiationData.getInitiationHeader()));
        }

        if ((lookupRequestInitiationData.getUserId() != null
            || lookupRequestInitiationData.getAuthenticationInputs() != null
            && lookupRequestInitiationData.getAuthenticationInputs().size() > 0)) {

            if (lookupRequestInitiationData.getItemId() != null
              && lookupRequestInitiationData.getRequestType() != null) {

                jaxbLookupRequest.setUserId(createUserId(lookupRequestInitiationData.getUserId()));
                jaxbLookupRequest.getAuthenticationInput().addAll(createJAXBAuthenticationInputs(lookupRequestInitiationData.getAuthenticationInputs()));
                jaxbLookupRequest.setItemId(createItemId(lookupRequestInitiationData.getItemId()));
                jaxbLookupRequest.setRequestType(convertSVP(lookupRequestInitiationData.getRequestType()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "ItemId and RequestType must be non-null in Lookup Request if either UserId or AuthenticationInput are present.");

            }


        } else if (lookupRequestInitiationData.getRequestId() != null) {

                jaxbLookupRequest.setRequestId(createRequestId(lookupRequestInitiationData.getRequestId()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "RequestId, UserId or AuthenticationInput must be non-null in Lookup Request.");

        }

        if (lookupRequestInitiationData.getRequestElementTypes() != null
            && lookupRequestInitiationData.getRequestElementTypes().size() > 0) {
            jaxbLookupRequest.getRequestElementType().addAll(
                convertSVPsToJAXB(lookupRequestInitiationData.getRequestElementTypes()));
        }


        if (lookupRequestInitiationData.getBibliographicDescriptionDesired()) {

            jaxbLookupRequest.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.BIBLIOGRAPHIC_DESCRIPTION));

        }

        if (lookupRequestInitiationData.getCirculationStatusDesired()) {

            jaxbLookupRequest.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.CIRCULATION_STATUS));

        }

        if (lookupRequestInitiationData.getElectronicResourceDesired()) {

            jaxbLookupRequest.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ELECTRONIC_RESOURCE));

        }

        if (lookupRequestInitiationData.getHoldQueueLengthDesired()) {

            jaxbLookupRequest.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.HOLD_QUEUE_LENGTH));

        }

        if (lookupRequestInitiationData.getItemDescriptionDesired()) {

            jaxbLookupRequest.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ITEM_DESCRIPTION));

        }

        if (lookupRequestInitiationData.getItemUseRestrictionTypeDesired()) {

            jaxbLookupRequest.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ITEM_USE_RESTRICTION_TYPE));

        }

        if (lookupRequestInitiationData.getLocationDesired()) {

            jaxbLookupRequest.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.LOCATION));

        }

        if (lookupRequestInitiationData.getPhysicalConditionDesired()) {

            jaxbLookupRequest.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.PHYSICAL_CONDITION));

        }

        if (lookupRequestInitiationData.getSecurityMarkerDesired()) {

            jaxbLookupRequest.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.SECURITY_MARKER));

        }

        if (lookupRequestInitiationData.getSensitizationFlagDesired()) {

            jaxbLookupRequest.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.SENSITIZATION_FLAG));

        }

        if (lookupRequestInitiationData.getAuthenticationInputDesired()) {
            jaxbLookupRequest.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.AUTHENTICATION_INPUT));
        }

        if (lookupRequestInitiationData.getBlockOrTrapDesired()) {
            jaxbLookupRequest.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.BLOCK_OR_TRAP));
        }

        if (lookupRequestInitiationData.getDateOfBirthDesired()) {
            jaxbLookupRequest.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.DATE_OF_BIRTH));
        }

        if (lookupRequestInitiationData.getNameInformationDesired()) {
            jaxbLookupRequest.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.NAME_INFORMATION));
        }

        if (lookupRequestInitiationData.getUserAddressInformationDesired()) {
            jaxbLookupRequest.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_ADDRESS_INFORMATION));
        }

        if (lookupRequestInitiationData.getUserLanguageDesired()) {
            jaxbLookupRequest.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_LANGUAGE));
        }

        if (lookupRequestInitiationData.getUserPrivilegeDesired()) {
            jaxbLookupRequest.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_PRIVILEGE));
        }

        if (lookupRequestInitiationData.getUserIdDesired()) {
            jaxbLookupRequest.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_ID));
        }

        if (lookupRequestInitiationData.getPreviousUserIdDesired()) {
            jaxbLookupRequest.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.PREVIOUS_USER_ID));
        }


        return ncipInitMsg;
    }

    protected NCIPMessage createLookupRequestResponseMessage(
        org.extensiblecatalog.ncip.v2.service.LookupRequestResponseData lookupRequestResponseData)
        throws BindingException {

        NCIPMessage ncipRespMsg = new NCIPMessage();
        ncipRespMsg.setVersion(NCIP_VERSION_V2_0);
        LookupRequestResponse lookupRequestResponse = new LookupRequestResponse();

        ncipRespMsg.setLookupRequestResponse(lookupRequestResponse);

        if (lookupRequestResponseData.getResponseHeader() != null) {
            ResponseHeader respHdr = createResponseHeader(lookupRequestResponseData.getResponseHeader());
            lookupRequestResponse.getContent().add(respHdr);
        }

        if (lookupRequestResponseData.getProblems() != null
            && lookupRequestResponseData.getProblems().size() > 0) {

            lookupRequestResponse.getContent().addAll(createJAXBProblems(lookupRequestResponseData.getProblems()));

        } else {

            if (lookupRequestResponseData.getItemId() != null) {

                lookupRequestResponse.getContent().add(createItemId(lookupRequestResponseData.getItemId()));

                if (lookupRequestResponseData.getRequestId() != null) {

                    lookupRequestResponse.getContent().add(createRequestId(lookupRequestResponseData.getRequestId()));

                }

            } else if (lookupRequestResponseData.getRequestId() != null) {

                    lookupRequestResponse.getContent().add(createRequestId(lookupRequestResponseData.getRequestId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Problem or ItemId or RequestId must be non-null in Lookup Item Response.");

            }

            if (lookupRequestResponseData.getUserId() != null) {

                lookupRequestResponse.getContent().add(createUserId(
                    lookupRequestResponseData.getUserId()));

            }

            if (lookupRequestResponseData.getRequestType() != null) {

                lookupRequestResponse.getContent().add(objectFactory.createRequestType(
                    convertSVP(lookupRequestResponseData.getRequestType())));

            }

            if (lookupRequestResponseData.getRequestScopeType() != null) {

                lookupRequestResponse.getContent().add(objectFactory.createRequestScopeType(convertSVP(
                    lookupRequestResponseData.getRequestScopeType())));

            }

            if (lookupRequestResponseData.getRequestStatusType() != null) {

                lookupRequestResponse.getContent().add(objectFactory.createRequestStatusType(convertSVP(
                    lookupRequestResponseData.getRequestStatusType())));

            }

            if (lookupRequestResponseData.getHoldQueuePosition() != null) {

                lookupRequestResponse.getContent().add(objectFactory.createHoldQueuePosition(
                    lookupRequestResponseData.getHoldQueuePosition()));

            }

            if (lookupRequestResponseData.getShippingInformation() != null) {

                lookupRequestResponse.getContent().add(createShippingInformation(
                    lookupRequestResponseData.getShippingInformation()));

            }

            if (lookupRequestResponseData.getEarliestDateNeeded() != null) {

                lookupRequestResponse.getContent().add(objectFactory.createEarliestDateNeeded(convertDate(
                    lookupRequestResponseData.getEarliestDateNeeded())));

            }

            if (lookupRequestResponseData.getNeedBeforeDate() != null) {

                lookupRequestResponse.getContent().add(objectFactory.createNeedBeforeDate(convertDate(
                    lookupRequestResponseData.getNeedBeforeDate())));

            }

            if (lookupRequestResponseData.getPickupDate() != null) {

                lookupRequestResponse.getContent().add(objectFactory.createPickupDate(convertDate(
                    lookupRequestResponseData.getPickupDate())));

            }

            if (lookupRequestResponseData.getPickupLocation() != null) {

                lookupRequestResponse.getContent().add(objectFactory.createPickupLocation(convertSVP(
                    lookupRequestResponseData.getPickupLocation())));

            }

            if (lookupRequestResponseData.getPickupExpiryDate() != null) {

                lookupRequestResponse.getContent().add(objectFactory.createPickupExpiryDate(convertDate(
                    lookupRequestResponseData.getPickupExpiryDate())));

            }

            if (lookupRequestResponseData.getDateOfUserRequest() != null) {

                lookupRequestResponse.getContent().add(objectFactory.createDateOfUserRequest(convertDate(
                    lookupRequestResponseData.getDateOfUserRequest())));

            }

            if (lookupRequestResponseData.getDateAvailable() != null) {

                lookupRequestResponse.getContent().add(objectFactory.createDateAvailable(convertDate(
                    lookupRequestResponseData.getDateAvailable())));

            }

            if (lookupRequestResponseData.getAcknowledgedFeeAmount() != null) {

                lookupRequestResponse.getContent().add(createAcknowledgedFeeAmount(
                    lookupRequestResponseData.getAcknowledgedFeeAmount()));

            }

            if (lookupRequestResponseData.getPaidFeeAmount() != null) {

                lookupRequestResponse.getContent().add(createPaidFeeAmount(
                    lookupRequestResponseData.getPaidFeeAmount()));

            }

            if (lookupRequestResponseData.getItemOptionalFields() != null) {

                lookupRequestResponse.getContent().add(createItemOptionalFields(
                    lookupRequestResponseData.getItemOptionalFields()));

            }

            if (lookupRequestResponseData.getUserOptionalFields() != null) {

                lookupRequestResponse.getContent().add(createUserOptionalFields(
                    lookupRequestResponseData.getUserOptionalFields()));

            }

        }
        return ncipRespMsg;
    }

    protected List<BibInformation> createJAXBBibInformations(List<org.extensiblecatalog.ncip.v2.service.BibInformation> svcBibInformations) throws BindingException {

        List<BibInformation> jaxbBibInformations = new ArrayList<BibInformation>();

        if (svcBibInformations != null && svcBibInformations.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.BibInformation svcBibInformation : svcBibInformations) {

                // TODO: Should we be testing for null returns?
                jaxbBibInformations.add(createBibInformation(svcBibInformation));

            }

        }

        return jaxbBibInformations;

    }

    protected BibInformation createBibInformation(org.extensiblecatalog.ncip.v2.service.BibInformation svcBibInformation)
        throws BindingException {

        BibInformation jaxbBibInformation = null;

        if ( svcBibInformation != null) {

            jaxbBibInformation = new BibInformation();
            jaxbBibInformation.setBibliographicId(createBibliographicId(svcBibInformation.getBibliographicId()));

            if (svcBibInformation.getHoldingsSets() != null && svcBibInformation.getHoldingsSets().size() > 0) {

                jaxbBibInformation.setBibliographicDescription(createBibliographicDescription(svcBibInformation.getBibliographicDescription()));
                jaxbBibInformation.setTitleHoldQueueLength(svcBibInformation.getTitleHoldQueueLength());

                if (svcBibInformation.getCurrentRequesters() != null && svcBibInformation.getCurrentRequesters().size() > 0) {

                    jaxbBibInformation.getCurrentRequester().addAll(createJAXBCurrentRequesters(svcBibInformation.getCurrentRequesters()));

                }

                jaxbBibInformation.getHoldingsSet().addAll(createJAXBHoldingsSets(svcBibInformation.getHoldingsSets()));

            } else if ( svcBibInformation.getProblems() != null && svcBibInformation.getProblems().size() > 0) {

                jaxbBibInformation.getProblem().addAll(createJAXBProblems(svcBibInformation.getProblems()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "HoldingsSet or Problem must be non-null in BibInformation.");

            }

        }

        return jaxbBibInformation;

    }

    protected List<HoldingsSet> createJAXBHoldingsSets(List<org.extensiblecatalog.ncip.v2.service.HoldingsSet> svcHoldingsSets) throws BindingException {

        List<HoldingsSet> jaxbHoldingsSets = new ArrayList<HoldingsSet>();

        if (svcHoldingsSets != null && svcHoldingsSets.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.HoldingsSet svcHoldingsSet : svcHoldingsSets) {

                // TODO: Should we be testing for null returns?
                jaxbHoldingsSets.add(createHoldingsSet(svcHoldingsSet));

            }

        }

        return jaxbHoldingsSets;

    }

    protected HoldingsSet createHoldingsSet(org.extensiblecatalog.ncip.v2.service.HoldingsSet svcHoldingsSet)
        throws BindingException {

        HoldingsSet jaxbHoldingsSet = null;

        if ( svcHoldingsSet != null) {

            jaxbHoldingsSet = new HoldingsSet();
            jaxbHoldingsSet.setHoldingsSetId(svcHoldingsSet.getHoldingsSetId());

            if (svcHoldingsSet.getItemInformations() != null && svcHoldingsSet.getItemInformations().size() > 0) {

                jaxbHoldingsSet.setBibliographicDescription(
                    createBibliographicDescription(svcHoldingsSet.getBibliographicDescription()));
                jaxbHoldingsSet.setLocation(createLocation(svcHoldingsSet.getLocation()));
                jaxbHoldingsSet.setCallNumber(svcHoldingsSet.getCallNumber());
                jaxbHoldingsSet.setSummaryHoldingsInformation(createSummaryHoldingsInformation(svcHoldingsSet.getSummaryHoldingsInformation()));
                jaxbHoldingsSet.setElectronicResource(createElectronicResource(svcHoldingsSet.getElectronicResource()));

                if (svcHoldingsSet.getItemInformations() != null && svcHoldingsSet.getItemInformations().size() > 0) {

                    jaxbHoldingsSet.getItemInformation().addAll(createJAXBItemInformations(svcHoldingsSet.getItemInformations()));

                }

            } else if ( svcHoldingsSet.getProblems() != null && svcHoldingsSet.getProblems().size() > 0) {

                jaxbHoldingsSet.getProblem().addAll(createJAXBProblems(svcHoldingsSet.getProblems()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "ItemInformation or Problem must be non-null in HoldingsSet.");

            }

        }

        return jaxbHoldingsSet;

    }

    protected List<ItemInformation> createJAXBItemInformations(List<org.extensiblecatalog.ncip.v2.service.ItemInformation> svcItemInformations) throws BindingException {

        List<ItemInformation> jaxbItemInformations = new ArrayList<ItemInformation>();

        if (svcItemInformations != null && svcItemInformations.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.ItemInformation svcItemInformation : svcItemInformations) {

                // TODO: Should we be testing for null returns?
                jaxbItemInformations.add(createItemInformation(svcItemInformation));

            }

        }

        return jaxbItemInformations;

    }

    protected ItemInformation createItemInformation(org.extensiblecatalog.ncip.v2.service.ItemInformation svcItemInformation) throws BindingException {

        ItemInformation jaxbItemInformation = null;

        if (svcItemInformation != null) {

            jaxbItemInformation = new ItemInformation();

            jaxbItemInformation.setItemId(createItemId(
                svcItemInformation.getItemId()));

            if ( (svcItemInformation.getRequestIds() != null && svcItemInformation.getRequestIds().size() > 0)
                || svcItemInformation.getCurrentBorrower() != null
                || (svcItemInformation.getCurrentRequesters() != null && svcItemInformation.getCurrentRequesters().size() > 0)
                || svcItemInformation.getDateDue() != null || svcItemInformation.getHoldPickupDate() != null || svcItemInformation.getDateRecalled() != null
                || svcItemInformation.getItemTransaction() != null || svcItemInformation.getItemOptionalFields() != null
                || svcItemInformation.getItemNote() != null) {

                if (svcItemInformation.getRequestIds() != null && svcItemInformation.getRequestIds().size() > 0) {

                    jaxbItemInformation.getRequestId().addAll(createJAXBRequestIds(
                        svcItemInformation.getRequestIds()));

                }

                if (svcItemInformation.getCurrentBorrower() != null) {

                    jaxbItemInformation.setCurrentBorrower(createCurrentBorrower(svcItemInformation.getCurrentBorrower()));

                }

                if (svcItemInformation.getCurrentRequesters() != null && svcItemInformation.getCurrentRequesters().size() > 0) {

                    jaxbItemInformation.getCurrentRequester().addAll(createJAXBCurrentRequesters(
                        svcItemInformation.getCurrentRequesters()));

                }

                if (svcItemInformation.getDateDue() != null) {

                    jaxbItemInformation.setDateDue(convertDate(svcItemInformation.getDateDue()));

                }

                if (svcItemInformation.getHoldPickupDate() != null) {

                    jaxbItemInformation.setHoldPickupDate(convertDate(svcItemInformation.getHoldPickupDate()));

                }

                if (svcItemInformation.getDateRecalled() != null) {

                    jaxbItemInformation.setDateRecalled(convertDate(svcItemInformation.getDateRecalled()));

                }


                if (svcItemInformation.getItemTransaction() != null) {

                    jaxbItemInformation.setItemTransaction(createItemTransaction(svcItemInformation.getItemTransaction()));

                }

                if (svcItemInformation.getItemOptionalFields() != null) {

                    jaxbItemInformation.setItemOptionalFields(createItemOptionalFields(svcItemInformation.getItemOptionalFields()));

                }

                if (svcItemInformation.getItemNote() != null) {

                    jaxbItemInformation.setItemNote(svcItemInformation.getItemNote());

                }

            } else if (svcItemInformation.getProblems() != null && svcItemInformation.getProblems().size() > 0) {

                jaxbItemInformation.getProblem().addAll(createJAXBProblems(svcItemInformation.getProblems()));

            } else {

                // Note: This is not an error - ItemInformation might be empty without there being any Problem.

            }

        }

        return jaxbItemInformation;

    }

    protected NCIPMessage createRequestItemInitiationMessage(
        org.extensiblecatalog.ncip.v2.service.RequestItemInitiationData requestItemInitiationData)
        throws BindingException {

        NCIPMessage ncipInitMsg = new NCIPMessage();
        ncipInitMsg.setVersion(NCIP_VERSION_V2_0);
/*
        RequestItem jaxbRequestItem = new RequestItem();

        ncipInitMsg.setRequestItem(jaxbRequestItem);

        if (requestItemInitiationData.getInitiationHeader() != null) {
            jaxbRequestItem.setInitiationHeader(createInitiationHeader(
                requestItemInitiationData.getInitiationHeader()));
        }

        if (requestItemInitiationData.getMandatedAction() != null) {
            jaxbRequestItem.setMandatedAction(createMandatedAction(
                requestItemInitiationData.getMandatedAction()));
        }

        if (requestItemInitiationData.getUserId() != null) {
            jaxbRequestItem.setUserId(createUserId(requestItemInitiationData.getUserId()));
        } else {
            if (requestItemInitiationData.getAuthenticationInputs() != null) {
                jaxbRequestItem.getAuthenticationInput().addAll(createJAXBAuthenticationInputs(
                    requestItemInitiationData.getAuthenticationInputs()));
            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "UserId or AuthenticationInput must be non-null in Request Item.");

            }
        }

        if (requestItemInitiationData.getItemId() != null) {
            if (requestItemInitiationData.getRequestId() != null) {
                jaxbRequestItem.setRequestId(createRequestId(requestItemInitiationData.getRequestId()));
            }
            jaxbRequestItem.setItemId(createItemId(requestItemInitiationData.getItemId()));
        } else {
            if (requestItemInitiationData.getBibliographicId() != null) {
                jaxbRequestItem.setBibliographicId(createBibliographicId(
                    requestItemInitiationData.getBibliographicId()));
            } else {
                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "ItemId or BibliographicId must be non-null in Request Item.");
            }
        }

        if (requestItemInitiationData.getRequestId() != null) {
            jaxbRequestItem.setRequestId(createRequestId(requestItemInitiationData.getRequestId()));
        }

        if (requestItemInitiationData.getRequestType() != null) {
            jaxbRequestItem.setRequestType(convertSVP(requestItemInitiationData.getRequestType()));
        } else {
            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "RequestType must be non-null in Request Item.");
        }

        if (requestItemInitiationData.getRequestScopeType() != null) {
            jaxbRequestItem.setRequestScopeType(convertSVP(requestItemInitiationData.getRequestScopeType()));
        } else {
            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "RequestScopeType must be non-null in Request Item.");
        }

        if (requestItemInitiationData.getItemOptionalFields() != null) {
            jaxbRequestItem.setItemOptionalFields(createItemOptionalFields(
                requestItemInitiationData.getItemOptionalFields()));
        }

        if (requestItemInitiationData.getItemOptionalFields() != null) {
            jaxbRequestItem.setItemOptionalFields(createItemOptionalFields(
                requestItemInitiationData.getItemOptionalFields()));
        }

        if (requestItemInitiationData.getShippingInformation() != null) {
            jaxbRequestItem.setShippingInformation(createShippingInformation(
                requestItemInitiationData.getShippingInformation()));
        }

        if (requestItemInitiationData.getEarliestDateNeeded() != null) {
            jaxbRequestItem.setEarliestDateNeeded(convertDate(requestItemInitiationData.getEarliestDateNeeded()));
        }

        if (requestItemInitiationData.getNeedBeforeDate() != null) {
            jaxbRequestItem.setNeedBeforeDate(convertDate(requestItemInitiationData.getNeedBeforeDate()));
        }

        if (requestItemInitiationData.getPickupLocation() != null) {
            jaxbRequestItem.setPickupLocation(convertSVP(requestItemInitiationData.getPickupLocation()));
        }

        if (requestItemInitiationData.getPickupExpiryDate() != null) {
            jaxbRequestItem.setPickupExpiryDate(convertDate(requestItemInitiationData.getPickupExpiryDate()));
        }

        if (requestItemInitiationData.getAcknowledgedFeeAmount() != null) {
            jaxbRequestItem.setAcknowledgedFeeAmount(createAcknowledgedFeeAmount(
                requestItemInitiationData.getAcknowledgedFeeAmount()));
        }

        if (requestItemInitiationData.getPaidFeeAmount() != null) {
            jaxbRequestItem.setPaidFeeAmount(createPaidFeeAmount(
                requestItemInitiationData.getPaidFeeAmount()));
        }

        if (requestItemInitiationData.getAcknowledgedItemUseRestrictionTypes() != null
            && requestItemInitiationData.getAcknowledgedItemUseRestrictionTypes().size() > 0) {
            jaxbRequestItem.getAcknowledgedItemUseRestrictionType().addAll(
                convertSVPsToJAXB(requestItemInitiationData.getAcknowledgedItemUseRestrictionTypes()));
        }

        if (requestItemInitiationData.getBibliographicDescriptionDesired()) {
            jaxbRequestItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.BIBLIOGRAPHIC_DESCRIPTION));
        }

        if (requestItemInitiationData.getCirculationStatusDesired()) {
            jaxbRequestItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.CIRCULATION_STATUS));
        }

        if (requestItemInitiationData.getHoldQueueLengthDesired()) {
            jaxbRequestItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.HOLD_QUEUE_LENGTH));
        }

        if (requestItemInitiationData.getItemDescriptionDesired()) {
            jaxbRequestItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ITEM_DESCRIPTION));
        }

        if (requestItemInitiationData.getItemUseRestrictionTypeDesired()) {
            jaxbRequestItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ITEM_USE_RESTRICTION_TYPE));
        }

        if (requestItemInitiationData.getLocationDesired()) {
            jaxbRequestItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.LOCATION));
        }

        if (requestItemInitiationData.getPhysicalConditionDesired()) {
            jaxbRequestItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.PHYSICAL_CONDITION));
        }

        if (requestItemInitiationData.getSecurityMarkerDesired()) {
            jaxbRequestItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.SECURITY_MARKER));
        }

        if (requestItemInitiationData.getSensitizationFlagDesired()) {
            jaxbRequestItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.SENSITIZATION_FLAG));
        }

        if (requestItemInitiationData.getAuthenticationInputDesired()) {
            jaxbRequestItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.AUTHENTICATION_INPUT));
        }

        if (requestItemInitiationData.getBlockOrTrapDesired()) {
            jaxbRequestItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.BLOCK_OR_TRAP));
        }

        if (requestItemInitiationData.getDateOfBirthDesired()) {
            jaxbRequestItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.DATE_OF_BIRTH));
        }

        if (requestItemInitiationData.getNameInformationDesired()) {
            jaxbRequestItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.NAME_INFORMATION));
        }

        if (requestItemInitiationData.getUserAddressInformationDesired()) {
            jaxbRequestItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_ADDRESS_INFORMATION));
        }

        if (requestItemInitiationData.getUserLanguageDesired()) {
            jaxbRequestItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_LANGUAGE));
        }

        if (requestItemInitiationData.getUserPrivilegeDesired()) {
            jaxbRequestItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_PRIVILEGE));
        }

        if (requestItemInitiationData.getUserIdDesired()) {
            jaxbRequestItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_ID));
        }

        if (requestItemInitiationData.getPreviousUserIdDesired()) {
            jaxbRequestItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.PREVIOUS_USER_ID));
        }
*/

        return ncipInitMsg;
    }

    protected PaidFeeAmount createPaidFeeAmount(org.extensiblecatalog.ncip.v2.service.PaidFeeAmount paidFeeAmount)
        throws BindingException {

        PaidFeeAmount jaxbPaidFeeAmount = null;

        if (paidFeeAmount != null) {

            if (paidFeeAmount.getCurrencyCode() != null) {

                if (paidFeeAmount.getMonetaryValue() != null) {

                    jaxbPaidFeeAmount = new PaidFeeAmount();
                    jaxbPaidFeeAmount.setCurrencyCode(convertSVP(paidFeeAmount.getCurrencyCode()));
                    jaxbPaidFeeAmount.setMonetaryValue(paidFeeAmount.getMonetaryValue());

                } else {

                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                        "MonetaryValue must be non-null in PaidFeeAmount.");

                }
            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "CurrencyCode must be non-null in PaidFeeAmount.");

            }
        }

        return jaxbPaidFeeAmount;

    }

    protected AcknowledgedFeeAmount createAcknowledgedFeeAmount(
        org.extensiblecatalog.ncip.v2.service.AcknowledgedFeeAmount acknowledgedFeeAmount) throws BindingException {

        AcknowledgedFeeAmount jaxbAcknowledgedFeeAmount = null;

        if (acknowledgedFeeAmount != null) {

            if (acknowledgedFeeAmount.getCurrencyCode() != null) {

                if (acknowledgedFeeAmount.getMonetaryValue() != null) {

                    jaxbAcknowledgedFeeAmount = new AcknowledgedFeeAmount();
                    jaxbAcknowledgedFeeAmount.setCurrencyCode(convertSVP(acknowledgedFeeAmount.getCurrencyCode()));
                    jaxbAcknowledgedFeeAmount.setMonetaryValue(acknowledgedFeeAmount.getMonetaryValue());

                } else {

                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                        "MonetaryValue must be non-null in AcknowledgedFeeAmount.");

                }
            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "CurrencyCode must be non-null in AcknowledgedFeeAmount.");

            }
        }

        return jaxbAcknowledgedFeeAmount;

    }

    protected ShippingInformation createShippingInformation(
        org.extensiblecatalog.ncip.v2.service.ShippingInformation shippingInformation) throws BindingException {

        ShippingInformation jaxbShippingInformation = null;

        if (shippingInformation != null) {

            if (shippingInformation.getPhysicalAddress() != null) {

                jaxbShippingInformation = new ShippingInformation();
                jaxbShippingInformation.setPhysicalAddress(
                    createPhysicalAddress(shippingInformation.getPhysicalAddress()));

            } else if (shippingInformation.getElectronicAddress() != null) {

                jaxbShippingInformation = new ShippingInformation();
                jaxbShippingInformation.setElectronicAddress(
                    createElectronicAddress(shippingInformation.getElectronicAddress()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "PhysicalAddress or ElectronicAddress or both must be non-null in ShippingInformation.");

            }

            if (shippingInformation.getShippingInstructions() != null) {

                jaxbShippingInformation.setShippingInstructions(shippingInformation.getShippingInstructions());

            }

            if (shippingInformation.getShippingNote() != null) {

                jaxbShippingInformation.setShippingNote(shippingInformation.getShippingNote());

            }

        }

        return jaxbShippingInformation;

    }

    protected ElectronicAddress createElectronicAddress(
        org.extensiblecatalog.ncip.v2.service.ElectronicAddress electronicAddress) throws BindingException {

        ElectronicAddress jaxbElectronicAddress = null;

        if (electronicAddress != null) {

            if (electronicAddress.getElectronicAddressData() != null) {

                if (electronicAddress.getElectronicAddressType() != null) {

                    jaxbElectronicAddress = new ElectronicAddress();
                    jaxbElectronicAddress.setElectronicAddressData(electronicAddress.getElectronicAddressData());
                    jaxbElectronicAddress.setElectronicAddressType(
                        convertSVP(electronicAddress.getElectronicAddressType()));

                } else {

                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                        "ElectronicAddressType must be non-null in ElectronicAddress.");

                }
            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "ElectronicAddressData must be non-null in ElectronicAddress.");

            }

        }

        return jaxbElectronicAddress;

    }

    protected PhysicalAddress createPhysicalAddress(
        org.extensiblecatalog.ncip.v2.service.PhysicalAddress physicalAddress) throws BindingException {

        PhysicalAddress jaxbPhysicalAddress = null;

        if (physicalAddress != null) {

            if (physicalAddress.getPhysicalAddressType() != null) {

                if (physicalAddress.getStructuredAddress() != null) {

                    jaxbPhysicalAddress = new PhysicalAddress();
                    jaxbPhysicalAddress.setStructuredAddress(
                        createStructuredAddress(physicalAddress.getStructuredAddress()));

                } else if (physicalAddress.getUnstructuredAddress() != null) {

                    jaxbPhysicalAddress = new PhysicalAddress();
                    jaxbPhysicalAddress.setUnstructuredAddress(
                        createUnstructuredAddress(physicalAddress.getUnstructuredAddress()));

                } else {

                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                        "StructuredAddress or UnstructuredAddress must be non-null in PhysicalAddress.");

                }

                jaxbPhysicalAddress.setPhysicalAddressType(convertSVP(physicalAddress.getPhysicalAddressType()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "PhysicalAddressType must be non-null in PhysicalAddress.");

            }

        }

        return jaxbPhysicalAddress;

    }

    protected UnstructuredAddress createUnstructuredAddress(
        org.extensiblecatalog.ncip.v2.service.UnstructuredAddress unstructuredAddress) throws BindingException {

        UnstructuredAddress jaxbUnstructuredAddress = null;

        if (unstructuredAddress != null) {

            if (unstructuredAddress.getUnstructuredAddressType() != null) {

                if (unstructuredAddress.getUnstructuredAddressData() != null) {

                    jaxbUnstructuredAddress = new UnstructuredAddress();
                    jaxbUnstructuredAddress.setUnstructuredAddressType(
                        convertSVP(unstructuredAddress.getUnstructuredAddressType()));
                    jaxbUnstructuredAddress.setUnstructuredAddressData(
                        unstructuredAddress.getUnstructuredAddressData());


                } else {

                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                        "UnstructuredAddressData must be non-null in UnstructuredAddress.");

                }

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "UnstructuredAddressType must be non-null in UnstructuredAddress.");

            }
        }

        return jaxbUnstructuredAddress;

    }

    protected StructuredAddress createStructuredAddress(
        org.extensiblecatalog.ncip.v2.service.StructuredAddress structuredAddress) throws BindingException {

        StructuredAddress jaxbStructuredAddress = null;

        if (structuredAddress != null) {

            if (structuredAddress.getLine1() != null) {

                jaxbStructuredAddress = new StructuredAddress();

                jaxbStructuredAddress.getContent().add(objectFactory.createLine1(structuredAddress.getLine1()));

                if (structuredAddress.getLine2() != null) {

                    jaxbStructuredAddress.getContent().add(objectFactory.createLine2(structuredAddress.getLine2()));

                }

            } else if (structuredAddress.getDistrict() != null || structuredAddress.getPostOfficeBox() != null
                || structuredAddress.getStreet() != null) {

                jaxbStructuredAddress = new StructuredAddress();

                if (structuredAddress.getLocationWithinBuilding() != null) {

                    jaxbStructuredAddress.getContent().add(objectFactory.createLocationWithinBuilding(
                        structuredAddress.getLocationWithinBuilding()));

                }

                if (structuredAddress.getHouseName() != null) {

                    jaxbStructuredAddress.getContent().add(objectFactory.createHouseName(
                        structuredAddress.getHouseName()));

                }

                if (structuredAddress.getStreet() != null) {

                    jaxbStructuredAddress.getContent().add(objectFactory.createStreet(
                        structuredAddress.getStreet()));

                }

                if (structuredAddress.getPostOfficeBox() != null) {

                    jaxbStructuredAddress.getContent().add(objectFactory.createPostOfficeBox(
                        structuredAddress.getPostOfficeBox()));

                }

                if (structuredAddress.getDistrict() != null) {

                    jaxbStructuredAddress.getContent().add(objectFactory.createDistrict(
                        structuredAddress.getDistrict()));

                }

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "One of Line1, Street, PostOfficeBox, or District must be non-null in StructuredAddress.");

            }


            if (structuredAddress.getLocality() != null) {

                jaxbStructuredAddress.getContent().add(objectFactory.createLocality(
                    structuredAddress.getLocality()));

            }

            if (structuredAddress.getRegion() != null) {

                jaxbStructuredAddress.getContent().add(objectFactory.createRegion(
                    structuredAddress.getRegion()));

            }

            if (structuredAddress.getCountry() != null) {

                jaxbStructuredAddress.getContent().add(objectFactory.createCountry(
                    structuredAddress.getCountry()));

            }

            if (structuredAddress.getPostalCode() != null) {

                jaxbStructuredAddress.getContent().add(objectFactory.createPostalCode(
                    structuredAddress.getPostalCode()));

            }

            if (structuredAddress.getCareOf() != null) {

                jaxbStructuredAddress.getContent().add(objectFactory.createCareOf(
                    structuredAddress.getCareOf()));

            }

        }

        return jaxbStructuredAddress;

    }

    protected BibliographicId createBibliographicId(
        org.extensiblecatalog.ncip.v2.service.BibliographicId bibliographicId)
        throws BindingException {

        BibliographicId jaxbBibliographicId = null;

        if (bibliographicId != null) {

            jaxbBibliographicId = new BibliographicId();

            if (bibliographicId.getBibliographicItemId() != null) {

                jaxbBibliographicId.setBibliographicItemId(createBibliographicItemId(
                    bibliographicId.getBibliographicItemId()));

            } else if (bibliographicId.getBibliographicRecordId() != null) {

                jaxbBibliographicId.setBibliographicRecordId(createBibliographicRecordId(
                    bibliographicId.getBibliographicRecordId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "BibliographicItemId or BibliographicRecordId or both must be non-null in BibliographicId.");

            }

        }

        return jaxbBibliographicId;

    }

    protected List<BibliographicId> createJAXBBibliographicIds(List<org.extensiblecatalog.ncip.v2.service.BibliographicId>
        svcBibliographicIds) throws BindingException {

        List<BibliographicId> jaxbBibliographicIds = new ArrayList<BibliographicId>();

        for ( org.extensiblecatalog.ncip.v2.service.BibliographicId svcBibliographicId : svcBibliographicIds) {

            jaxbBibliographicIds.add(createBibliographicId(svcBibliographicId));

        }

        return jaxbBibliographicIds;

    }

    protected MandatedAction createMandatedAction(org.extensiblecatalog.ncip.v2.service.MandatedAction mandatedAction)
        throws BindingException {

        MandatedAction jaxbMandatedAction = null;

        if (mandatedAction != null && mandatedAction.getDateEventOccurred() != null) {

            jaxbMandatedAction = new MandatedAction();
            jaxbMandatedAction.setDateEventOccurred(convertDate(mandatedAction.getDateEventOccurred()));

        }

        return jaxbMandatedAction;
    }

    protected NCIPMessage createRequestItemResponseMessage(
        org.extensiblecatalog.ncip.v2.service.RequestItemResponseData requestItemResponseData)
        throws BindingException {

        NCIPMessage ncipRespMsg = new NCIPMessage();
        ncipRespMsg.setVersion(NCIP_VERSION_V2_0);
        RequestItemResponse requestItemResponse = new RequestItemResponse();

        ncipRespMsg.setRequestItemResponse(requestItemResponse);

        if (requestItemResponseData.getResponseHeader() != null) {

            ResponseHeader respHdr = createResponseHeader(requestItemResponseData.getResponseHeader());
            requestItemResponse.getContent().add(respHdr);

        }

        if (requestItemResponseData.getProblems() != null && requestItemResponseData.getProblems().size() > 0) {

            requestItemResponse.getContent().addAll(createJAXBProblems(requestItemResponseData.getProblems()));

            if (requestItemResponseData.getRequiredFeeAmount() != null) {
                requestItemResponse.getContent().add(
                    createRequiredFeeAmount(requestItemResponseData.getRequiredFeeAmount()));
            }

            if (requestItemResponseData.getRequiredItemUseRestrictionTypes() != null
                && requestItemResponseData.getRequiredItemUseRestrictionTypes().size() > 0) {

                for ( org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType
                    svcRequiredItemUseRestrictionType : requestItemResponseData.getRequiredItemUseRestrictionTypes()) {

                        requestItemResponse.getContent().add(
                            objectFactory.createRequiredItemUseRestrictionType(
                            convertSVP(svcRequiredItemUseRestrictionType)));

                }
            }

        } else {

            // The following sequence of if-else looks like it could be simplified, but I think this is the only
            // way to ensure that, if there's a request id, the item id gets added before the request id does.
            // And I *think* failing to ensure that order could result in the elements appearing out-of-order
            // in the XML.
            if (requestItemResponseData.getRequestId() != null) {

                requestItemResponse.getContent().add(createRequestId(requestItemResponseData.getRequestId()));

                if (requestItemResponseData.getItemId() != null) {
                    requestItemResponse.getContent().add(createItemId(requestItemResponseData.getItemId()));
                }

            } else if (requestItemResponseData.getItemId() != null) {

                requestItemResponse.getContent().add(createItemId(requestItemResponseData.getItemId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Problem, ItemId or RequestId must be non-null in RequestItemResponse.");

            }

            if (requestItemResponseData.getUserId() != null) {

                requestItemResponse.getContent().add(createUserId(requestItemResponseData.getUserId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Problem or UserId must be non-null in RequestItemResponse.");

            }

            if (requestItemResponseData.getRequestType() != null) {

                requestItemResponse.getContent().add(objectFactory.createRequestType(
                    convertSVP(requestItemResponseData.getRequestType())));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Problem or RequestType must be non-null in RequestItemResponse.");

            }

            if (requestItemResponseData.getRequestScopeType() != null) {

                requestItemResponse.getContent().add(objectFactory.createRequestScopeType(
                    convertSVP(requestItemResponseData.getRequestScopeType())));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Problem or RequestScopeType must be non-null in RequestItemResponse.");

            }

            if (requestItemResponseData.getShippingInformation() != null) {

                requestItemResponse.getContent().add(createShippingInformation(
                    requestItemResponseData.getShippingInformation()));

            }

            if (requestItemResponseData.getDateAvailable() != null) {

                requestItemResponse.getContent().add(objectFactory.createDateAvailable(convertDate(
                    requestItemResponseData.getDateAvailable())));

            }

            if (requestItemResponseData.getHoldPickupDate() != null) {

                requestItemResponse.getContent().add(objectFactory.createHoldPickupDate(convertDate(
                    requestItemResponseData.getHoldPickupDate())));

            }

            if (requestItemResponseData.getFiscalTransactionInformation() != null) {

                requestItemResponse.getContent().add(createFiscalTransactionInformation(
                    requestItemResponseData.getFiscalTransactionInformation()));

            }

            if (requestItemResponseData.getItemOptionalFields() != null) {

                requestItemResponse.getContent().add(createItemOptionalFields(
                    requestItemResponseData.getItemOptionalFields()));

            }

            if (requestItemResponseData.getUserOptionalFields() != null) {

                requestItemResponse.getContent().add(createUserOptionalFields(
                    requestItemResponseData.getUserOptionalFields()));

            }

        }

        return ncipRespMsg;
    }

    protected UserOptionalFields createUserOptionalFields(
        org.extensiblecatalog.ncip.v2.service.UserOptionalFields userOptionalFields) throws BindingException {

        UserOptionalFields jaxbUserOptionalFields = null;

        if (userOptionalFields != null) {

            if (userOptionalFields.getNameInformation() != null) {

                if (jaxbUserOptionalFields == null) {

                    jaxbUserOptionalFields = new UserOptionalFields();

                }

                jaxbUserOptionalFields.setNameInformation(
                    createNameInformation(userOptionalFields.getNameInformation()));

            }

            if (userOptionalFields.getUserAddressInformations() != null) {

                if (jaxbUserOptionalFields == null) {

                    jaxbUserOptionalFields = new UserOptionalFields();

                }

                jaxbUserOptionalFields.getUserAddressInformation().clear();
                jaxbUserOptionalFields.getUserAddressInformation().addAll(
                    createJAXBUserAddressInformations(userOptionalFields.getUserAddressInformations()));

            }

            if (userOptionalFields.getDateOfBirth() != null) {

                if (jaxbUserOptionalFields == null) {

                    jaxbUserOptionalFields = new UserOptionalFields();

                }

                jaxbUserOptionalFields.setDateOfBirth(convertDate(userOptionalFields.getDateOfBirth()));

            }

            if (userOptionalFields.getUserLanguages() != null) {

                if (jaxbUserOptionalFields == null) {

                    jaxbUserOptionalFields = new UserOptionalFields();

                }

                jaxbUserOptionalFields.getUserLanguage().clear();
                jaxbUserOptionalFields.getUserLanguage().addAll(
                    convertSVPsToJAXB(userOptionalFields.getUserLanguages()));

            }

            if (userOptionalFields.getUserPrivileges() != null) {

                if (jaxbUserOptionalFields == null) {

                    jaxbUserOptionalFields = new UserOptionalFields();

                }

                jaxbUserOptionalFields.getUserPrivilege().clear();
                jaxbUserOptionalFields.getUserPrivilege().addAll(
                    createUserPrivileges(userOptionalFields.getUserPrivileges()));

            }

            if (userOptionalFields.getBlockOrTraps() != null) {

                if (jaxbUserOptionalFields == null) {

                    jaxbUserOptionalFields = new UserOptionalFields();

                }

                jaxbUserOptionalFields.getBlockOrTrap().clear();
                jaxbUserOptionalFields.getBlockOrTrap().addAll(
                    createBlockOrTraps(userOptionalFields.getBlockOrTraps()));

            }

            if (userOptionalFields.getUserIds() != null) {

                if (jaxbUserOptionalFields == null) {

                    jaxbUserOptionalFields = new UserOptionalFields();

                }

                jaxbUserOptionalFields.getUserId().clear();
                jaxbUserOptionalFields.getUserId().addAll(
                    createUserIds(userOptionalFields.getUserIds()));

            }

            if (userOptionalFields.getPreviousUserIds() != null) {

                if (jaxbUserOptionalFields == null) {

                    jaxbUserOptionalFields = new UserOptionalFields();

                }

                jaxbUserOptionalFields.getPreviousUserId().clear();
                jaxbUserOptionalFields.getPreviousUserId().addAll(
                    createPreviousUserIds(userOptionalFields.getPreviousUserIds()));

            }

        }

        return jaxbUserOptionalFields;

    }

    protected List<PreviousUserId> createPreviousUserIds(
        List<org.extensiblecatalog.ncip.v2.service.PreviousUserId> previousUserIds)
        throws BindingException {

        List<PreviousUserId> jaxbPreviousUserIds = new ArrayList<PreviousUserId>();

        if (previousUserIds != null && previousUserIds.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.PreviousUserId previousUserId : previousUserIds) {

                PreviousUserId jaxbPreviousUserId = createPreviousUserId(previousUserId);

                if (jaxbPreviousUserId != null) {

                    jaxbPreviousUserIds.add(jaxbPreviousUserId);

                }

            }

        }

        return jaxbPreviousUserIds;

    }

    protected PreviousUserId createPreviousUserId(
        org.extensiblecatalog.ncip.v2.service.PreviousUserId previousUserId) throws BindingException {

        PreviousUserId jaxbPreviousUserId = null;

        if (previousUserId != null) {

            if (previousUserId.getAgencyId() != null) {

                if (jaxbPreviousUserId == null) {

                    jaxbPreviousUserId = new PreviousUserId();

                }

                jaxbPreviousUserId.setAgencyId(
                    convertSVP(previousUserId.getAgencyId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "AgencyId must be non-null in PreviousUserId.");
            }

            if (previousUserId.getUserIdentifierValue() != null) {

                if (jaxbPreviousUserId == null) {

                    jaxbPreviousUserId = new PreviousUserId();

                }

                jaxbPreviousUserId.setUserIdentifierValue(previousUserId.getUserIdentifierValue());

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "UserIdentifierValue must be non-null in PreviousUserId.");
            }

            if (previousUserId.getValidFromDate() != null) {

                if (jaxbPreviousUserId == null) {

                    jaxbPreviousUserId = new PreviousUserId();

                }

                jaxbPreviousUserId.setValidFromDate(
                    convertDate(previousUserId.getValidFromDate()));

            }

            if (previousUserId.getValidToDate() != null) {

                if (jaxbPreviousUserId == null) {

                    jaxbPreviousUserId = new PreviousUserId();

                }

                jaxbPreviousUserId.setValidToDate(
                    convertDate(previousUserId.getValidToDate()));

            }

        }

        return jaxbPreviousUserId;

    }

    protected List<BlockOrTrap> createBlockOrTraps(
        List<org.extensiblecatalog.ncip.v2.service.BlockOrTrap> blockOrTraps)
        throws BindingException {

        List<BlockOrTrap> jaxbBlockOrTraps = new ArrayList<BlockOrTrap>();

        if (blockOrTraps != null && blockOrTraps.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.BlockOrTrap blockOrTrap : blockOrTraps) {

                BlockOrTrap jaxbBlockOrTrap = createBlockOrTrap(blockOrTrap);

                if (jaxbBlockOrTrap != null) {

                    jaxbBlockOrTraps.add(jaxbBlockOrTrap);

                }

            }

        }

        return jaxbBlockOrTraps;

    }

    protected BlockOrTrap createBlockOrTrap(
        org.extensiblecatalog.ncip.v2.service.BlockOrTrap blockOrTrap) throws BindingException {

        BlockOrTrap jaxbBlockOrTrap = null;

        if (blockOrTrap != null) {

            if (blockOrTrap.getAgencyId() != null) {

                if (jaxbBlockOrTrap == null) {

                    jaxbBlockOrTrap = new BlockOrTrap();

                }

                jaxbBlockOrTrap.setAgencyId(
                    convertSVP(blockOrTrap.getAgencyId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "AgencyId must be non-null in BlockOrTrap.");
            }

            if (blockOrTrap.getBlockOrTrapType() != null) {

                if (jaxbBlockOrTrap == null) {

                    jaxbBlockOrTrap = new BlockOrTrap();

                }

                jaxbBlockOrTrap.setBlockOrTrapType(convertSVP(blockOrTrap.getBlockOrTrapType()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "BlockOrTrapType must be non-null in BlockOrTrap.");
            }

            if (blockOrTrap.getValidFromDate() != null) {

                if (jaxbBlockOrTrap == null) {

                    jaxbBlockOrTrap = new BlockOrTrap();

                }

                jaxbBlockOrTrap.setValidFromDate(
                    convertDate(blockOrTrap.getValidFromDate()));

            }

            if (blockOrTrap.getValidToDate() != null) {

                if (jaxbBlockOrTrap == null) {

                    jaxbBlockOrTrap = new BlockOrTrap();

                }

                jaxbBlockOrTrap.setValidToDate(
                    convertDate(blockOrTrap.getValidToDate()));
            }

        }

        return jaxbBlockOrTrap;

    }

    protected List<UserPrivilege> createUserPrivileges(
        List<org.extensiblecatalog.ncip.v2.service.UserPrivilege> userPrivileges)
        throws BindingException {

        List<UserPrivilege> jaxbUserPrivileges = new ArrayList<UserPrivilege>();

        if (userPrivileges != null && userPrivileges.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.UserPrivilege userPrivilege : userPrivileges) {

                UserPrivilege jaxbUserPrivilege = createUserPrivilege(userPrivilege);

                if (jaxbUserPrivilege != null) {

                    jaxbUserPrivileges.add(jaxbUserPrivilege);

                }

            }

        }

        return jaxbUserPrivileges;

    }

    protected UserPrivilege createUserPrivilege(
        org.extensiblecatalog.ncip.v2.service.UserPrivilege svcUserPrivilege) throws BindingException {

        UserPrivilege jaxbUserPrivilege = null;

        if (svcUserPrivilege != null) {

            if (svcUserPrivilege.getAgencyId() != null) {

                if (jaxbUserPrivilege == null) {

                    jaxbUserPrivilege = new UserPrivilege();

                }

                jaxbUserPrivilege.setAgencyId(
                    convertSVP(svcUserPrivilege.getAgencyId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "AgencyId must be non-null in UserPrivilege.");
            }

            if (svcUserPrivilege.getAgencyUserPrivilegeType() != null) {

                if (jaxbUserPrivilege == null) {

                    jaxbUserPrivilege = new UserPrivilege();

                }

                jaxbUserPrivilege.setAgencyUserPrivilegeType(
                    convertSVP(svcUserPrivilege.getAgencyUserPrivilegeType()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "AgencyUserPrivilegeType must be non-null in UserPrivilege.");
            }


            if (svcUserPrivilege.getValidFromDate() != null) {

                if (jaxbUserPrivilege == null) {

                    jaxbUserPrivilege = new UserPrivilege();

                }

                jaxbUserPrivilege.setValidFromDate(
                    convertDate(svcUserPrivilege.getValidFromDate()));

            }

            if (svcUserPrivilege.getValidToDate() != null) {

                if (jaxbUserPrivilege == null) {

                    jaxbUserPrivilege = new UserPrivilege();

                }

                jaxbUserPrivilege.setValidToDate(
                    convertDate(svcUserPrivilege.getValidToDate()));

            }

            if (svcUserPrivilege.getUserPrivilegeFee() != null) {

                if (jaxbUserPrivilege == null) {

                    jaxbUserPrivilege = new UserPrivilege();

                }

                jaxbUserPrivilege.setUserPrivilegeFee(
                    createUserPrivilegeFee(svcUserPrivilege.getUserPrivilegeFee()));

            }

            if (svcUserPrivilege.getUserPrivilegeStatus() != null) {

                if (jaxbUserPrivilege == null) {

                    jaxbUserPrivilege = new UserPrivilege();

                }

                jaxbUserPrivilege.setUserPrivilegeStatus(
                    createUserPrivilegeStatus(svcUserPrivilege.getUserPrivilegeStatus()));

            }

            if (svcUserPrivilege.getUserPrivilegeDescription() != null) {

                if (jaxbUserPrivilege == null) {

                    jaxbUserPrivilege = new UserPrivilege();

                }

                jaxbUserPrivilege.setUserPrivilegeDescription(svcUserPrivilege.getUserPrivilegeDescription());

            }

        }

        return jaxbUserPrivilege;

    }

    protected UserPrivilegeStatus createUserPrivilegeStatus(
        org.extensiblecatalog.ncip.v2.service.UserPrivilegeStatus svcUserPrivilegeStatus) throws BindingException {

        UserPrivilegeStatus jaxbUserPrivilegeStatus = null;

        if (svcUserPrivilegeStatus != null) {

            jaxbUserPrivilegeStatus = new UserPrivilegeStatus();

            if (svcUserPrivilegeStatus.getUserPrivilegeStatusType() != null) {

                jaxbUserPrivilegeStatus.setUserPrivilegeStatusType(
                    convertSVP(svcUserPrivilegeStatus.getUserPrivilegeStatusType()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "UserPrivilegeStatusType must be non-null in UserPrivilegeStatus.");
            }

            if (svcUserPrivilegeStatus.getDateOfUserPrivilegeStatus() != null) {

                jaxbUserPrivilegeStatus.setDateOfUserPrivilegeStatus(
                    convertDate(svcUserPrivilegeStatus.getDateOfUserPrivilegeStatus()));

            } 

        }

        return jaxbUserPrivilegeStatus;

    }

    protected UserPrivilegeFee createUserPrivilegeFee(
        org.extensiblecatalog.ncip.v2.service.UserPrivilegeFee svcUserPrivilegeFee) throws BindingException {

        UserPrivilegeFee jaxbUserPrivilegeFee = null;

        if (svcUserPrivilegeFee.getAmount() != null) {

            if (jaxbUserPrivilegeFee == null) {

                jaxbUserPrivilegeFee = new UserPrivilegeFee();

            }

            jaxbUserPrivilegeFee.setAmount(
                createAmount(svcUserPrivilegeFee.getAmount()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "Amount must be non-null in UserPrivilegeFee.");
        }

        if (svcUserPrivilegeFee.getPaymentMethodType() != null) {

            if (jaxbUserPrivilegeFee == null) {

                jaxbUserPrivilegeFee = new UserPrivilegeFee();

            }

            jaxbUserPrivilegeFee.setPaymentMethodType(
                convertSVP(svcUserPrivilegeFee.getPaymentMethodType()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "PaymentMethodType must be non-null in UserPrivilegeFee.");
        }

        return jaxbUserPrivilegeFee;

    }

    protected List<UserAddressInformation> createJAXBUserAddressInformations(
        List<org.extensiblecatalog.ncip.v2.service.UserAddressInformation> userAddressInformations)
        throws BindingException {

        List<UserAddressInformation> jaxbUserAddressInformations = new ArrayList<UserAddressInformation>();

        if (userAddressInformations != null && userAddressInformations.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.UserAddressInformation userAddressInformation
                : userAddressInformations) {

                UserAddressInformation jaxbUserAddressInformation
                    = createUserAddressInformation(userAddressInformation);

                if (jaxbUserAddressInformation != null) {

                    jaxbUserAddressInformations.add(jaxbUserAddressInformation);

                }

            }

        }

        return jaxbUserAddressInformations;

    }

    protected UserAddressInformation createUserAddressInformation(
        org.extensiblecatalog.ncip.v2.service.UserAddressInformation svcUserAddressInformation)
        throws BindingException {

        UserAddressInformation jaxbUserAddressInformation = null;

        if (svcUserAddressInformation != null) {

            jaxbUserAddressInformation = new UserAddressInformation();

            if (svcUserAddressInformation.getUserAddressRoleType() != null) {

                jaxbUserAddressInformation.setUserAddressRoleType(
                    convertSVP(svcUserAddressInformation.getUserAddressRoleType()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "UserAddressRoleType must be non-null in UserAddressInformation.");
            }

            if (svcUserAddressInformation.getValidFromDate() != null) {

                jaxbUserAddressInformation.setValidFromDate(
                    convertDate(svcUserAddressInformation.getValidFromDate()));

            }

            if (svcUserAddressInformation.getValidToDate() != null) {

                jaxbUserAddressInformation.setValidToDate(
                    convertDate(svcUserAddressInformation.getValidToDate()));

            }

            if (svcUserAddressInformation.getPhysicalAddress() != null) {

                jaxbUserAddressInformation.setPhysicalAddress(
                    createPhysicalAddress(svcUserAddressInformation.getPhysicalAddress()));

            } else if (svcUserAddressInformation.getElectronicAddress() != null) {

                jaxbUserAddressInformation.setElectronicAddress(
                    createElectronicAddress(svcUserAddressInformation.getElectronicAddress()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "PhysicalAddress or ElectronicAddress must be non-null in UserAddressInformation.");

            }

        }

        return jaxbUserAddressInformation;

    }

    protected NameInformation createNameInformation(
        org.extensiblecatalog.ncip.v2.service.NameInformation svcNameInformation) throws BindingException {

        NameInformation jaxbNameInformation = null;

        if (svcNameInformation != null) {

            jaxbNameInformation = new NameInformation();

            if (svcNameInformation.getPersonalNameInformation() != null) {

                jaxbNameInformation.setPersonalNameInformation(
                    createPersonalNameInformation(svcNameInformation.getPersonalNameInformation()));

            } else if (svcNameInformation.getOrganizationNameInformations() != null) {

                jaxbNameInformation.getOrganizationNameInformation().clear();
                jaxbNameInformation.getOrganizationNameInformation().addAll(
                    createJAXBOrganizationNameInformations(svcNameInformation.getOrganizationNameInformations()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "PersonalNameInformation or OrganizationNameInformation must be non-null in NameInformation.");

            }

        }

        return jaxbNameInformation;

    }

    protected PersonalNameInformation createPersonalNameInformation(
        org.extensiblecatalog.ncip.v2.service.PersonalNameInformation svcPersonalNameInformation)
        throws BindingException {
        PersonalNameInformation jaxbPersonalNameInformation = null;
/*
        <xs:choice>
          <xs:element ref="UnstructuredPersonalUserName"/>
          <xs:sequence>
            <xs:element ref="StructuredPersonalUserName"/>
            <xs:element ref="UnstructuredPersonalUserName" minOccurs="0"/>
          </xs:sequence>
        </xs:choice>
*/

        if (svcPersonalNameInformation != null) {
            jaxbPersonalNameInformation = new PersonalNameInformation();

            if (svcPersonalNameInformation.getStructuredPersonalUserName() != null) {

                jaxbPersonalNameInformation.getContent().add(
                    createStructuredPersonalUserName(svcPersonalNameInformation.getStructuredPersonalUserName()));

            } else if (svcPersonalNameInformation.getUnstructuredPersonalUserName() != null) {

                jaxbPersonalNameInformation.getContent().add(
                    objectFactory.createUnstructuredPersonalUserName(
                        svcPersonalNameInformation.getUnstructuredPersonalUserName()));

            }
        }
        return jaxbPersonalNameInformation;
    }

    protected StructuredPersonalUserName createStructuredPersonalUserName(
        org.extensiblecatalog.ncip.v2.service.StructuredPersonalUserName svcStructuredPersonalUserName)
        throws BindingException {

        StructuredPersonalUserName jaxbStructuredPersonalUserName = null;
        if (svcStructuredPersonalUserName != null) {
            jaxbStructuredPersonalUserName = new StructuredPersonalUserName();
            if (svcStructuredPersonalUserName.getSurname() != null) {
                jaxbStructuredPersonalUserName.setSurname(svcStructuredPersonalUserName.getSurname());
            } else {
                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Surname must be non-null in StructuredPersonalUserName.");
            }
            if (svcStructuredPersonalUserName.getGivenName() != null) {
                jaxbStructuredPersonalUserName.setGivenName(svcStructuredPersonalUserName.getGivenName());
            }
            if (svcStructuredPersonalUserName.getInitials() != null) {
                jaxbStructuredPersonalUserName.setInitials(svcStructuredPersonalUserName.getInitials());
            }
            if (svcStructuredPersonalUserName.getPrefix() != null) {
                jaxbStructuredPersonalUserName.setPrefix(svcStructuredPersonalUserName.getPrefix());
            }
            if (svcStructuredPersonalUserName.getSuffix() != null) {
                jaxbStructuredPersonalUserName.setSuffix(svcStructuredPersonalUserName.getSuffix());
            }

        }
        return jaxbStructuredPersonalUserName;
    }

    protected List<OrganizationNameInformation> createJAXBOrganizationNameInformations(
        List<org.extensiblecatalog.ncip.v2.service.OrganizationNameInformation> svcOrganizationNameInformations) {
        // TODO: Write createOrganizationNameInformations method
        return null;
    }

    protected OrganizationNameInformation createOrganizationNameInformation(
        org.extensiblecatalog.ncip.v2.service.OrganizationNameInformation svcOrganizationNameInformation) {
        // TODO: Write createOrganizationNameInformation method
        return null;
    }

    protected FiscalTransactionInformation createFiscalTransactionInformation(
        org.extensiblecatalog.ncip.v2.service.FiscalTransactionInformation fiscalTransactionInformation)
        throws BindingException {

        FiscalTransactionInformation jaxbFiscalTransactionInformation = null;

        if (fiscalTransactionInformation != null) {

            if (fiscalTransactionInformation.getFiscalActionType() != null) {

                jaxbFiscalTransactionInformation = new FiscalTransactionInformation();
                jaxbFiscalTransactionInformation.setFiscalActionType(
                    convertSVP(fiscalTransactionInformation.getFiscalActionType()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "FiscalActionType must be non-null in FiscalTransactionInformation.");

            }

            if (fiscalTransactionInformation.getFiscalTransactionReferenceId() != null) {

                jaxbFiscalTransactionInformation.setFiscalTransactionReferenceId(
                    createFiscalTransactionReferenceId(
                        fiscalTransactionInformation.getFiscalTransactionReferenceId()));

            }

            if (fiscalTransactionInformation.getRelatedFiscalTransactionReferenceIds() != null
                && fiscalTransactionInformation.getRelatedFiscalTransactionReferenceIds().size() > 0) {

                jaxbFiscalTransactionInformation.getRelatedFiscalTransactionReferenceId().addAll(
                    createRelatedFiscalTransactionReferenceIds(
                        fiscalTransactionInformation.getRelatedFiscalTransactionReferenceIds()));

            }

            if (fiscalTransactionInformation.getFiscalTransactionType() != null) {

                jaxbFiscalTransactionInformation.setFiscalTransactionType(
                    convertSVP(fiscalTransactionInformation.getFiscalTransactionType()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "FiscalTransactionType must be non-null in FiscalTransactionInformation.");
            }

            if (fiscalTransactionInformation.getValidFromDate() != null) {

                jaxbFiscalTransactionInformation.setValidFromDate(
                    convertDate(fiscalTransactionInformation.getValidFromDate()));

            }

            if (fiscalTransactionInformation.getValidToDate() != null) {

                jaxbFiscalTransactionInformation.setValidToDate(
                    convertDate(fiscalTransactionInformation.getValidToDate()));

            }

            if (fiscalTransactionInformation.getAmount() != null) {

                jaxbFiscalTransactionInformation.setAmount(
                    createAmount(fiscalTransactionInformation.getAmount()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Amount must be non-null in FiscalTransactionInformation.");
            }

            if (fiscalTransactionInformation.getPaymentMethodType() != null) {

                jaxbFiscalTransactionInformation.setPaymentMethodType(
                    convertSVP(fiscalTransactionInformation.getPaymentMethodType()));

            }

            if (fiscalTransactionInformation.getFiscalTransactionDescription() != null) {

                jaxbFiscalTransactionInformation.setFiscalTransactionDescription(
                    fiscalTransactionInformation.getFiscalTransactionDescription());

            }

            if (fiscalTransactionInformation.getRequestId() != null) {

                jaxbFiscalTransactionInformation.setRequestId(
                    createRequestId(fiscalTransactionInformation.getRequestId()));

            }

            if (fiscalTransactionInformation.getItemDetails() != null) {

                jaxbFiscalTransactionInformation.setItemDetails(
                    createItemDetails(fiscalTransactionInformation.getItemDetails()));

            }

        }

        return jaxbFiscalTransactionInformation;

    }

    protected ItemDetails createItemDetails(org.extensiblecatalog.ncip.v2.service.ItemDetails itemDetails)
        throws BindingException {

        ItemDetails jaxbItemDetails = null;

        if (itemDetails != null) {

            jaxbItemDetails = new ItemDetails();

            if (itemDetails.getBibliographicDescription() != null) {
                jaxbItemDetails.setBibliographicDescription(
                        createBibliographicDescription(itemDetails.getBibliographicDescription()));
            }

            if (itemDetails.getItemId() != null) {
                jaxbItemDetails.setItemId(
                        createItemId(itemDetails.getItemId()));
            }

            if (itemDetails.getDateCheckedOut() != null) {
                jaxbItemDetails.setDateCheckedOut(convertDate(itemDetails.getDateCheckedOut()));
            }

            if (itemDetails.getDateDue() != null) {
                jaxbItemDetails.setDateDue(
                        convertDate(itemDetails.getDateDue()));
            }

            if (itemDetails.getDateReturned() != null) {
                jaxbItemDetails.setDateReturned(
                        convertDate(itemDetails.getDateReturned()));
            }

            if (itemDetails.getDateReneweds() != null) {
                jaxbItemDetails.getDateRenewed().addAll(
                        convertDatesToJAXB(itemDetails.getDateReneweds()));
            }

        }

        return jaxbItemDetails;

    }

    protected List<RelatedFiscalTransactionReferenceId> createRelatedFiscalTransactionReferenceIds(
        List<org.extensiblecatalog.ncip.v2.service.RelatedFiscalTransactionReferenceId>
            relatedFiscalTransactionReferenceIds) {

        List<RelatedFiscalTransactionReferenceId> jaxbRelatedFiscalTransactionReferenceIds
            = new ArrayList<RelatedFiscalTransactionReferenceId>();

        if (relatedFiscalTransactionReferenceIds != null && relatedFiscalTransactionReferenceIds.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.RelatedFiscalTransactionReferenceId fiscalTransRefId
                : relatedFiscalTransactionReferenceIds) {

                RelatedFiscalTransactionReferenceId jaxbFiscalTransRefId
                    = createRelatedFiscalTransactionReferenceId(fiscalTransRefId);

                if (fiscalTransRefId != null) {

                    jaxbRelatedFiscalTransactionReferenceIds.add(jaxbFiscalTransRefId);

                }

            }

        }

        return jaxbRelatedFiscalTransactionReferenceIds;

    }

    protected RelatedFiscalTransactionReferenceId createRelatedFiscalTransactionReferenceId(
        org.extensiblecatalog.ncip.v2.service.RelatedFiscalTransactionReferenceId
            relatedFiscalTransactionReferenceId) {

        RelatedFiscalTransactionReferenceId jaxbFiscalTransRefId = null;

        if (relatedFiscalTransactionReferenceId.getAgencyId() != null
            && relatedFiscalTransactionReferenceId.getFiscalTransactionIdentifierValue() != null) {

            jaxbFiscalTransRefId = new RelatedFiscalTransactionReferenceId();
            jaxbFiscalTransRefId.setAgencyId(convertSVP(relatedFiscalTransactionReferenceId.getAgencyId()));
            jaxbFiscalTransRefId.setFiscalTransactionIdentifierValue(
                relatedFiscalTransactionReferenceId.getFiscalTransactionIdentifierValue());

        }

        return jaxbFiscalTransRefId;

    }

    protected Amount createAmount(org.extensiblecatalog.ncip.v2.service.Amount amount) {

        Amount jaxbAmount = null;

        if (amount != null) {

            jaxbAmount = new Amount();
            jaxbAmount.setCurrencyCode(convertSVP(amount.getCurrencyCode()));
            jaxbAmount.setMonetaryValue(amount.getMonetaryValue());
        }

        return jaxbAmount;

    }

    protected FiscalTransactionReferenceId createFiscalTransactionReferenceId(
        org.extensiblecatalog.ncip.v2.service.FiscalTransactionReferenceId fiscalTransactionReferenceId) {

        FiscalTransactionReferenceId jaxbFiscalTransactionReferenceId = null;

        if (fiscalTransactionReferenceId != null) {

            jaxbFiscalTransactionReferenceId = new FiscalTransactionReferenceId();

            jaxbFiscalTransactionReferenceId.setAgencyId(convertSVP(fiscalTransactionReferenceId.getAgencyId()));
            jaxbFiscalTransactionReferenceId.setFiscalTransactionIdentifierValue(
                fiscalTransactionReferenceId.getFiscalTransactionIdentifierValue());

        }

        return jaxbFiscalTransactionReferenceId;

    }

    protected RequiredFeeAmount createRequiredFeeAmount(
        org.extensiblecatalog.ncip.v2.service.RequiredFeeAmount requiredFeeAmount)
        throws BindingException {

        RequiredFeeAmount jaxbRequiredFeeAmount = null;

        if (requiredFeeAmount != null) {

            if (requiredFeeAmount.getCurrencyCode() != null) {

                if (requiredFeeAmount.getMonetaryValue() != null) {

                    jaxbRequiredFeeAmount = new RequiredFeeAmount();
                    jaxbRequiredFeeAmount.setCurrencyCode(convertSVP(requiredFeeAmount.getCurrencyCode()));
                    jaxbRequiredFeeAmount.setMonetaryValue(requiredFeeAmount.getMonetaryValue());

                } else {

                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                        "MonetaryValue must be non-null in RequiredFeeAmount.");

                }
            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "CurrencyCode must be non-null in RequiredFeeAmount.");

            }
        }

        return jaxbRequiredFeeAmount;

    }

    protected NCIPMessage createAcceptItemInitiationMessage(
        org.extensiblecatalog.ncip.v2.service.AcceptItemInitiationData acceptItemInitiationData)
        throws BindingException {

        NCIPMessage ncipInitMsg = new NCIPMessage();
        ncipInitMsg.setVersion(NCIP_VERSION_V2_0);
        AcceptItem jaxbAcceptItem = new AcceptItem();

        ncipInitMsg.setAcceptItem(jaxbAcceptItem);

        if (acceptItemInitiationData.getInitiationHeader() != null) {
            jaxbAcceptItem.setInitiationHeader(createInitiationHeader(
                acceptItemInitiationData.getInitiationHeader()));
        }

        jaxbAcceptItem.setMandatedAction(createMandatedAction(
            acceptItemInitiationData.getMandatedAction()));

        if (acceptItemInitiationData.getRequestId() != null) {

            jaxbAcceptItem.setRequestId(createRequestId(acceptItemInitiationData.getRequestId()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "RequestId must be non-null in Accept Item.");
        }

        if (acceptItemInitiationData.getRequestedActionType() != null) {

            jaxbAcceptItem.setRequestedActionType(convertSVP(acceptItemInitiationData.getRequestedActionType()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "RequestedActionType must be non-null in Accept Item.");
        }

        if (acceptItemInitiationData.getUserId() != null) {

            jaxbAcceptItem.setUserId(createUserId(acceptItemInitiationData.getUserId()));

        }

        if (acceptItemInitiationData.getDateForReturn() != null) {

            jaxbAcceptItem.setDateForReturn(convertDate(acceptItemInitiationData.getDateForReturn()));

        } else if (acceptItemInitiationData.getIndeterminateLoanPeriodFlag() != null) {

            jaxbAcceptItem.setIndeterminateLoanPeriodFlag(new IndeterminateLoanPeriodFlag());

        } else if (acceptItemInitiationData.getNonReturnableFlag() != null) {

            jaxbAcceptItem.setNonReturnableFlag(new NonReturnableFlag());

        }

        if (acceptItemInitiationData.getRenewalNotPermitted() != null) {

            jaxbAcceptItem.setRenewalNotPermitted(new RenewalNotPermitted());

        }

        if (acceptItemInitiationData.getFiscalTransactionInformation() != null) {

            jaxbAcceptItem.setFiscalTransactionInformation(createFiscalTransactionInformation(
                acceptItemInitiationData.getFiscalTransactionInformation()));

        }

        if (acceptItemInitiationData.getItemOptionalFields() != null) {

            jaxbAcceptItem.setItemOptionalFields(createItemOptionalFields(
                acceptItemInitiationData.getItemOptionalFields()));

        }

        if (acceptItemInitiationData.getUserOptionalFields() != null) {

            jaxbAcceptItem.setUserOptionalFields(createUserOptionalFields(
                acceptItemInitiationData.getUserOptionalFields()));

        }

        if (acceptItemInitiationData.getPickupLocation() != null) {

            jaxbAcceptItem.setPickupLocation(convertSVP(
                acceptItemInitiationData.getPickupLocation()));

        }

        if (acceptItemInitiationData.getPickupExpiryDate() != null) {

            jaxbAcceptItem.setPickupExpiryDate(convertDate(
                acceptItemInitiationData.getPickupExpiryDate()));

        }

        if (acceptItemInitiationData.getItemId() != null) {

            jaxbAcceptItem.setItemId(createItemId(acceptItemInitiationData.getItemId()));

        }

        return ncipInitMsg;

    }

    protected NCIPMessage createCheckInItemInitiationMessage(
        org.extensiblecatalog.ncip.v2.service.CheckInItemInitiationData checkInItemInitiationData)
        throws BindingException {

        NCIPMessage ncipInitMsg = new NCIPMessage();
        ncipInitMsg.setVersion(NCIP_VERSION_V2_0);
        CheckInItem jaxbCheckInItem = new CheckInItem();

        ncipInitMsg.setCheckInItem(jaxbCheckInItem);

        if (checkInItemInitiationData.getInitiationHeader() != null) {
            jaxbCheckInItem.setInitiationHeader(createInitiationHeader(
                checkInItemInitiationData.getInitiationHeader()));
        }

        jaxbCheckInItem.setMandatedAction(createMandatedAction(
            checkInItemInitiationData.getMandatedAction()));

        if (checkInItemInitiationData.getItemId() != null) {

            jaxbCheckInItem.setItemId(createItemId(checkInItemInitiationData.getItemId()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "ItemId must be non-null in Check In Item.");
        }

        if (checkInItemInitiationData.getBibliographicDescriptionDesired()) {
            jaxbCheckInItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.BIBLIOGRAPHIC_DESCRIPTION));
        }

        if (checkInItemInitiationData.getCirculationStatusDesired()) {
            jaxbCheckInItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.CIRCULATION_STATUS));
        }

        if (checkInItemInitiationData.getHoldQueueLengthDesired()) {
            jaxbCheckInItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.HOLD_QUEUE_LENGTH));
        }

        if (checkInItemInitiationData.getItemDescriptionDesired()) {
            jaxbCheckInItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ITEM_DESCRIPTION));
        }

        if (checkInItemInitiationData.getItemUseRestrictionTypeDesired()) {
            jaxbCheckInItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ITEM_USE_RESTRICTION_TYPE));
        }

        if (checkInItemInitiationData.getLocationDesired()) {
            jaxbCheckInItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.LOCATION));
        }

        if (checkInItemInitiationData.getPhysicalConditionDesired()) {
            jaxbCheckInItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.PHYSICAL_CONDITION));
        }

        if (checkInItemInitiationData.getSecurityMarkerDesired()) {
            jaxbCheckInItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.SECURITY_MARKER));
        }

        if (checkInItemInitiationData.getSensitizationFlagDesired()) {
            jaxbCheckInItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.SENSITIZATION_FLAG));
        }

        if (checkInItemInitiationData.getAuthenticationInputDesired()) {
            jaxbCheckInItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.AUTHENTICATION_INPUT));
        }

        if (checkInItemInitiationData.getBlockOrTrapDesired()) {
            jaxbCheckInItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.BLOCK_OR_TRAP));
        }

        if (checkInItemInitiationData.getDateOfBirthDesired()) {
            jaxbCheckInItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.DATE_OF_BIRTH));
        }

        if (checkInItemInitiationData.getNameInformationDesired()) {
            jaxbCheckInItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.NAME_INFORMATION));
        }

        if (checkInItemInitiationData.getUserAddressInformationDesired()) {
            jaxbCheckInItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_ADDRESS_INFORMATION));
        }

        if (checkInItemInitiationData.getUserLanguageDesired()) {
            jaxbCheckInItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_LANGUAGE));
        }

        if (checkInItemInitiationData.getUserPrivilegeDesired()) {
            jaxbCheckInItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_PRIVILEGE));
        }

        if (checkInItemInitiationData.getUserIdDesired()) {
            jaxbCheckInItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_ID));
        }

        if (checkInItemInitiationData.getPreviousUserIdDesired()) {
            jaxbCheckInItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.PREVIOUS_USER_ID));
        }

        return ncipInitMsg;

    }

    protected NCIPMessage createCheckOutItemInitiationMessage(
        org.extensiblecatalog.ncip.v2.service.CheckOutItemInitiationData checkOutItemInitiationData)
        throws BindingException {

        NCIPMessage ncipInitMsg = new NCIPMessage();
        ncipInitMsg.setVersion(NCIP_VERSION_V2_0);
        CheckOutItem jaxbCheckOutItem = new CheckOutItem();

        ncipInitMsg.setCheckOutItem(jaxbCheckOutItem);

        if (checkOutItemInitiationData.getInitiationHeader() != null) {
            jaxbCheckOutItem.setInitiationHeader(createInitiationHeader(
                checkOutItemInitiationData.getInitiationHeader()));
        }

        jaxbCheckOutItem.setMandatedAction(createMandatedAction(
            checkOutItemInitiationData.getMandatedAction()));

        if (checkOutItemInitiationData.getUserId() != null) {

            jaxbCheckOutItem.setUserId(createUserId(checkOutItemInitiationData.getUserId()));

        } else if (checkOutItemInitiationData.getAuthenticationInputs() != null
            && checkOutItemInitiationData.getAuthenticationInputs().size() > 0) {

            jaxbCheckOutItem.getAuthenticationInput().addAll(createJAXBAuthenticationInputs(
                checkOutItemInitiationData.getAuthenticationInputs()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "UserId or AuthenticationData or both must be non-null in Check Out Item.");

        }

        if (checkOutItemInitiationData.getItemId() != null) {

            jaxbCheckOutItem.setItemId(createItemId(checkOutItemInitiationData.getItemId()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "ItemId must be non-null in Check Out Item.");
        }

        if (checkOutItemInitiationData.getRequestId() != null) {

            jaxbCheckOutItem.setRequestId(createRequestId(checkOutItemInitiationData.getRequestId()));

        }

        if (checkOutItemInitiationData.getAcknowledgedFeeAmount() != null) {
            jaxbCheckOutItem.setAcknowledgedFeeAmount(createAcknowledgedFeeAmount(
                checkOutItemInitiationData.getAcknowledgedFeeAmount()));
        }

        if (checkOutItemInitiationData.getPaidFeeAmount() != null) {
            jaxbCheckOutItem.setPaidFeeAmount(createPaidFeeAmount(
                checkOutItemInitiationData.getPaidFeeAmount()));
        }

        if (checkOutItemInitiationData.getAcknowledgedItemUseRestrictionTypes() != null
            && checkOutItemInitiationData.getAcknowledgedItemUseRestrictionTypes().size() > 0) {
            jaxbCheckOutItem.getAcknowledgedItemUseRestrictionType().addAll(
                convertSVPsToJAXB(checkOutItemInitiationData.getAcknowledgedItemUseRestrictionTypes()));
        }

        if (checkOutItemInitiationData.getShippingInformation() != null) {
            jaxbCheckOutItem.setShippingInformation(createShippingInformation(
                checkOutItemInitiationData.getShippingInformation()));
        }

        if (checkOutItemInitiationData.getResourceDesired() != null
            && checkOutItemInitiationData.getResourceDesired()) {
            jaxbCheckOutItem.setResourceDesired(objectFactory.createResourceDesired());
        }

        if (checkOutItemInitiationData.getDesiredDateDue() != null) {
            jaxbCheckOutItem.setDesiredDateDue(convertDate(checkOutItemInitiationData.getDesiredDateDue()));
        }

        if (checkOutItemInitiationData.getBibliographicDescriptionDesired()) {
            jaxbCheckOutItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.BIBLIOGRAPHIC_DESCRIPTION));
        }

        if (checkOutItemInitiationData.getCirculationStatusDesired()) {
            jaxbCheckOutItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.CIRCULATION_STATUS));
        }

        if (checkOutItemInitiationData.getHoldQueueLengthDesired()) {
            jaxbCheckOutItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.HOLD_QUEUE_LENGTH));
        }

        if (checkOutItemInitiationData.getItemDescriptionDesired()) {
            jaxbCheckOutItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ITEM_DESCRIPTION));
        }

        if (checkOutItemInitiationData.getItemUseRestrictionTypeDesired()) {
            jaxbCheckOutItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ITEM_USE_RESTRICTION_TYPE));
        }

        if (checkOutItemInitiationData.getLocationDesired()) {
            jaxbCheckOutItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.LOCATION));
        }

        if (checkOutItemInitiationData.getPhysicalConditionDesired()) {
            jaxbCheckOutItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.PHYSICAL_CONDITION));
        }

        if (checkOutItemInitiationData.getSecurityMarkerDesired()) {
            jaxbCheckOutItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.SECURITY_MARKER));
        }

        if (checkOutItemInitiationData.getSensitizationFlagDesired()) {
            jaxbCheckOutItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.SENSITIZATION_FLAG));
        }

        if (checkOutItemInitiationData.getAuthenticationInputDesired()) {
            jaxbCheckOutItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.AUTHENTICATION_INPUT));
        }

        if (checkOutItemInitiationData.getBlockOrTrapDesired()) {
            jaxbCheckOutItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.BLOCK_OR_TRAP));
        }

        if (checkOutItemInitiationData.getDateOfBirthDesired()) {
            jaxbCheckOutItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.DATE_OF_BIRTH));
        }

        if (checkOutItemInitiationData.getNameInformationDesired()) {
            jaxbCheckOutItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.NAME_INFORMATION));
        }

        if (checkOutItemInitiationData.getUserAddressInformationDesired()) {
            jaxbCheckOutItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_ADDRESS_INFORMATION));
        }

        if (checkOutItemInitiationData.getUserLanguageDesired()) {
            jaxbCheckOutItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_LANGUAGE));
        }

        if (checkOutItemInitiationData.getUserPrivilegeDesired()) {
            jaxbCheckOutItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_PRIVILEGE));
        }

        if (checkOutItemInitiationData.getUserIdDesired()) {
            jaxbCheckOutItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_ID));
        }

        if (checkOutItemInitiationData.getPreviousUserIdDesired()) {
            jaxbCheckOutItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.PREVIOUS_USER_ID));
        }

        return ncipInitMsg;
    }

    protected NCIPMessage createRenewItemInitiationMessage(
        org.extensiblecatalog.ncip.v2.service.RenewItemInitiationData renewItemInitiationData)
        throws BindingException {

        NCIPMessage ncipInitMsg = new NCIPMessage();
        ncipInitMsg.setVersion(NCIP_VERSION_V2_0);
        RenewItem jaxbRenewItem = new RenewItem();

        ncipInitMsg.setRenewItem(jaxbRenewItem);

        if (renewItemInitiationData.getInitiationHeader() != null) {
            jaxbRenewItem.setInitiationHeader(createInitiationHeader(
                renewItemInitiationData.getInitiationHeader()));
        }

        jaxbRenewItem.setMandatedAction(createMandatedAction(
            renewItemInitiationData.getMandatedAction()));

        if (renewItemInitiationData.getUserId() != null) {

            jaxbRenewItem.setUserId(createUserId(renewItemInitiationData.getUserId()));

        } else if (renewItemInitiationData.getAuthenticationInputs() != null
            && renewItemInitiationData.getAuthenticationInputs().size() > 0) {

            jaxbRenewItem.getAuthenticationInput().addAll(createJAXBAuthenticationInputs(
                renewItemInitiationData.getAuthenticationInputs()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "UserId or AuthenticationData or both must be non-null in Renew Item.");

        }

        if (renewItemInitiationData.getItemId() != null) {

            jaxbRenewItem.setItemId(createItemId(renewItemInitiationData.getItemId()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "ItemId must be non-null in Renew Item.");
        }

        if (renewItemInitiationData.getBibliographicDescriptionDesired()) {
            jaxbRenewItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.BIBLIOGRAPHIC_DESCRIPTION));
        }

        if (renewItemInitiationData.getCirculationStatusDesired()) {
            jaxbRenewItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.CIRCULATION_STATUS));
        }

        if (renewItemInitiationData.getHoldQueueLengthDesired()) {
            jaxbRenewItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.HOLD_QUEUE_LENGTH));
        }

        if (renewItemInitiationData.getItemDescriptionDesired()) {
            jaxbRenewItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ITEM_DESCRIPTION));
        }

        if (renewItemInitiationData.getItemUseRestrictionTypeDesired()) {
            jaxbRenewItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.ITEM_USE_RESTRICTION_TYPE));
        }

        if (renewItemInitiationData.getLocationDesired()) {
            jaxbRenewItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.LOCATION));
        }

        if (renewItemInitiationData.getPhysicalConditionDesired()) {
            jaxbRenewItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.PHYSICAL_CONDITION));
        }

        if (renewItemInitiationData.getSecurityMarkerDesired()) {
            jaxbRenewItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.SECURITY_MARKER));
        }

        if (renewItemInitiationData.getSensitizationFlagDesired()) {
            jaxbRenewItem.getItemElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1ItemElementType.SENSITIZATION_FLAG));
        }

        if (renewItemInitiationData.getAuthenticationInputDesired()) {
            jaxbRenewItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.AUTHENTICATION_INPUT));
        }

        if (renewItemInitiationData.getBlockOrTrapDesired()) {
            jaxbRenewItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.BLOCK_OR_TRAP));
        }

        if (renewItemInitiationData.getDateOfBirthDesired()) {
            jaxbRenewItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.DATE_OF_BIRTH));
        }

        if (renewItemInitiationData.getNameInformationDesired()) {
            jaxbRenewItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.NAME_INFORMATION));
        }

        if (renewItemInitiationData.getUserAddressInformationDesired()) {
            jaxbRenewItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_ADDRESS_INFORMATION));
        }

        if (renewItemInitiationData.getUserLanguageDesired()) {
            jaxbRenewItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_LANGUAGE));
        }

        if (renewItemInitiationData.getUserPrivilegeDesired()) {
            jaxbRenewItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_PRIVILEGE));
        }

        if (renewItemInitiationData.getUserIdDesired()) {
            jaxbRenewItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_ID));
        }

        if (renewItemInitiationData.getPreviousUserIdDesired()) {
            jaxbRenewItem.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.PREVIOUS_USER_ID));
        }

        if (renewItemInitiationData.getDesiredDateDue() != null) {
            jaxbRenewItem.setDesiredDateDue(convertDate(renewItemInitiationData.getDesiredDateDue()));
        }

        if (renewItemInitiationData.getDesiredDateForReturn() != null) {
            jaxbRenewItem.setDesiredDateForReturn(convertDate(renewItemInitiationData.getDesiredDateForReturn()));
        }

        if (renewItemInitiationData.getAcknowledgedFeeAmount() != null) {
            jaxbRenewItem.setAcknowledgedFeeAmount(createAcknowledgedFeeAmount(
                renewItemInitiationData.getAcknowledgedFeeAmount()));
        }

        if (renewItemInitiationData.getPaidFeeAmount() != null) {
            jaxbRenewItem.setPaidFeeAmount(createPaidFeeAmount(
                renewItemInitiationData.getPaidFeeAmount()));
        }

        if (renewItemInitiationData.getAcknowledgedItemUseRestrictionTypes() != null
            && renewItemInitiationData.getAcknowledgedItemUseRestrictionTypes().size() > 0) {
            jaxbRenewItem.getAcknowledgedItemUseRestrictionType().addAll(
                convertSVPsToJAXB(renewItemInitiationData.getAcknowledgedItemUseRestrictionTypes()));
        }

        return ncipInitMsg;
    }

    protected List<AuthenticationInput> createJAXBAuthenticationInputs(
        List<org.extensiblecatalog.ncip.v2.service.AuthenticationInput> authenticationInputs) throws BindingException {

        List<AuthenticationInput> jaxbAuthenticationInputs = new ArrayList<AuthenticationInput>();

        if (authenticationInputs != null && authenticationInputs.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.AuthenticationInput authInput : authenticationInputs) {

                // TODO: Should we be testing for null returns?
                jaxbAuthenticationInputs.add(createAuthenticationInput(authInput));

            }

        }

        return jaxbAuthenticationInputs;

    }

    protected AuthenticationInput createAuthenticationInput(
        org.extensiblecatalog.ncip.v2.service.AuthenticationInput svcAuthenticationInput) throws BindingException {

        AuthenticationInput jaxbAuthenticationInput = null;

        if ( svcAuthenticationInput != null) {

            if (svcAuthenticationInput.getAuthenticationInputData() != null
                && svcAuthenticationInput.getAuthenticationDataFormatType() != null
                && svcAuthenticationInput.getAuthenticationInputType() != null) {

                jaxbAuthenticationInput = new AuthenticationInput();

                jaxbAuthenticationInput.setAuthenticationInputData(
                    svcAuthenticationInput.getAuthenticationInputData());
                jaxbAuthenticationInput.setAuthenticationDataFormatType(
                    convertSVP(svcAuthenticationInput.getAuthenticationDataFormatType()));
                jaxbAuthenticationInput.setAuthenticationInputType(
                    convertSVP(svcAuthenticationInput.getAuthenticationInputType()));

            }

        }

        return jaxbAuthenticationInput;

    }

    protected NCIPMessage createCheckInItemResponseMessage(
        org.extensiblecatalog.ncip.v2.service.CheckInItemResponseData checkInItemResponseData)
        throws BindingException {

        NCIPMessage ncipRespMsg = new NCIPMessage();
        ncipRespMsg.setVersion(NCIP_VERSION_V2_0);
        CheckInItemResponse jaxbCheckInItemResponse = new CheckInItemResponse();

        ncipRespMsg.setCheckInItemResponse(jaxbCheckInItemResponse);

        if (checkInItemResponseData.getResponseHeader() != null) {
            ResponseHeader respHdr = createResponseHeader(checkInItemResponseData.getResponseHeader());
            jaxbCheckInItemResponse.setResponseHeader(respHdr);
        }

        if (checkInItemResponseData.getProblems() != null && checkInItemResponseData.getProblems().size() > 0) {

            jaxbCheckInItemResponse.getProblem().addAll(createJAXBProblems(checkInItemResponseData.getProblems()));

        } else if (checkInItemResponseData.getItemId() != null) {

            jaxbCheckInItemResponse.setItemId(createItemId(checkInItemResponseData.getItemId()));

            if (checkInItemResponseData.getUserId() != null) {
                jaxbCheckInItemResponse.setUserId(createUserId(checkInItemResponseData.getUserId()));
            }

            if (checkInItemResponseData.getRoutingInformation() != null) {

                jaxbCheckInItemResponse.setRoutingInformation(
                    createRoutingInformation(checkInItemResponseData.getRoutingInformation()));

            }

            if (checkInItemResponseData.getFiscalTransactionInformation() != null) {

                jaxbCheckInItemResponse.setFiscalTransactionInformation(
                    createFiscalTransactionInformation(checkInItemResponseData.getFiscalTransactionInformation()));

            }

            if (checkInItemResponseData.getItemOptionalFields() != null) {

                jaxbCheckInItemResponse.setItemOptionalFields(
                    createItemOptionalFields(checkInItemResponseData.getItemOptionalFields()));

            }

            if (checkInItemResponseData.getUserOptionalFields() != null) {

                jaxbCheckInItemResponse.setUserOptionalFields(
                    createUserOptionalFields(checkInItemResponseData.getUserOptionalFields()));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "Either both ItemId and UserId, or else Problems must be non-null in Check In Item Response.");

        }

        return ncipRespMsg;
    }

    protected RoutingInformation createRoutingInformation(
        org.extensiblecatalog.ncip.v2.service.RoutingInformation svcRoutingInformation) throws BindingException {

        RoutingInformation jaxbRoutingInformation = null;

        if (svcRoutingInformation != null) {

            if (svcRoutingInformation.getRoutingInstructions() != null
                && svcRoutingInformation.getDestination() != null) {

                jaxbRoutingInformation = new RoutingInformation();

                jaxbRoutingInformation.setRoutingInstructions(
                    svcRoutingInformation.getRoutingInstructions());

                jaxbRoutingInformation.setDestination(
                    createDestination(svcRoutingInformation.getDestination()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "RoutingInformation and Destination must be non-null in RoutingInformation.");

            }

            if (svcRoutingInformation.getRequestType() != null) {

                jaxbRoutingInformation.setRequestType(convertSVP(svcRoutingInformation.getRequestType()));

            }

            if (svcRoutingInformation.getUserId() != null) {

                jaxbRoutingInformation.setUserId(createUserId(svcRoutingInformation.getUserId()));

            }

            if (svcRoutingInformation.getNameInformation() != null) {

                jaxbRoutingInformation.setNameInformation(
                    createNameInformation(svcRoutingInformation.getNameInformation()));

            }

        }

        return jaxbRoutingInformation;

    }

    protected Destination createDestination(org.extensiblecatalog.ncip.v2.service.Destination svcDestination)
        throws BindingException {

        Destination jaxbDestination = new Destination();
        if (svcDestination.getLocation() != null) {

            jaxbDestination.getContent().add(createLocation(
                svcDestination.getLocation()));


        } else if (svcDestination.getBinNumber() != null) {

            jaxbDestination.getContent().add(objectFactory.createBinNumber(
                svcDestination.getBinNumber()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "Location or BinNumber must be non-null in Destination.");

        }

        return jaxbDestination;

    }

    protected NCIPMessage createAcceptItemResponseMessage(
        org.extensiblecatalog.ncip.v2.service.AcceptItemResponseData acceptItemResponseData)
        throws BindingException {

        NCIPMessage ncipRespMsg = new NCIPMessage();
        ncipRespMsg.setVersion(NCIP_VERSION_V2_0);
        AcceptItemResponse jaxbAcceptItemResponse = new AcceptItemResponse();

        ncipRespMsg.setAcceptItemResponse(jaxbAcceptItemResponse);

        if (acceptItemResponseData.getResponseHeader() != null) {
            ResponseHeader respHdr = createResponseHeader(acceptItemResponseData.getResponseHeader());
            jaxbAcceptItemResponse.setResponseHeader(respHdr);
        }

        if (acceptItemResponseData.getProblems() != null && acceptItemResponseData.getProblems().size() > 0) {

            jaxbAcceptItemResponse.getProblem().addAll(createJAXBProblems(acceptItemResponseData.getProblems()));

        } else if (acceptItemResponseData.getRequestId() != null) {

            jaxbAcceptItemResponse.setRequestId(createRequestId(acceptItemResponseData.getRequestId()));

            if (acceptItemResponseData.getItemId() != null) {

                jaxbAcceptItemResponse.setItemId(createItemId(acceptItemResponseData.getItemId()));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "RequestId or Problems must be non-null in Accept Item Response.");

        }

        return ncipRespMsg;
    }

    protected NCIPMessage createCheckOutItemResponseMessage(
        org.extensiblecatalog.ncip.v2.service.CheckOutItemResponseData checkOutItemResponseData)
        throws BindingException {

        NCIPMessage ncipRespMsg = new NCIPMessage();
        ncipRespMsg.setVersion(NCIP_VERSION_V2_0);
        CheckOutItemResponse jaxbCheckOutItemResponse = new CheckOutItemResponse();

        ncipRespMsg.setCheckOutItemResponse(jaxbCheckOutItemResponse);

        if (checkOutItemResponseData.getResponseHeader() != null) {
            ResponseHeader respHdr = createResponseHeader(checkOutItemResponseData.getResponseHeader());
            jaxbCheckOutItemResponse.setResponseHeader(respHdr);
        }

        if (checkOutItemResponseData.getProblems() != null && checkOutItemResponseData.getProblems().size() > 0) {

            jaxbCheckOutItemResponse.getProblem().addAll(createJAXBProblems(checkOutItemResponseData.getProblems()));

            if (checkOutItemResponseData.getRequiredFeeAmount() != null) {

                jaxbCheckOutItemResponse.setRequiredFeeAmount(
                    createRequiredFeeAmount(checkOutItemResponseData.getRequiredFeeAmount()));

            }

            if (checkOutItemResponseData.getRequiredItemUseRestrictionTypes() != null
                && checkOutItemResponseData.getRequiredItemUseRestrictionTypes().size() > 0) {

                jaxbCheckOutItemResponse.getRequiredItemUseRestrictionType().clear();
                jaxbCheckOutItemResponse.getRequiredItemUseRestrictionType().addAll(
                    convertSVPsToJAXB(checkOutItemResponseData.getRequiredItemUseRestrictionTypes()));

            }

        } else if (checkOutItemResponseData.getItemId() != null && checkOutItemResponseData.getUserId() != null) {

            jaxbCheckOutItemResponse.setItemId(createItemId(checkOutItemResponseData.getItemId()));

            jaxbCheckOutItemResponse.setUserId(createUserId(checkOutItemResponseData.getUserId()));

            if (checkOutItemResponseData.getDateDue() != null) {

                jaxbCheckOutItemResponse.setDateDue(convertDate(checkOutItemResponseData.getDateDue()));

            } else if (checkOutItemResponseData.getIndeterminateLoanPeriodFlag() != null) {

                jaxbCheckOutItemResponse.setIndeterminateLoanPeriodFlag(new IndeterminateLoanPeriodFlag());

            } else if (checkOutItemResponseData.getNonReturnableFlag() != null) {

                jaxbCheckOutItemResponse.setNonReturnableFlag(new NonReturnableFlag());

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Either DateDue, IndeterminateLoanPeriodFlag, NonReturnableFlag or Problem"
                        + " must be non-null in Check Out Item Response.");

            }

            if (checkOutItemResponseData.getRenewalCount() != null) {

                jaxbCheckOutItemResponse.setRenewalCount(checkOutItemResponseData.getRenewalCount());

            }

            if (checkOutItemResponseData.getElectronicResource() != null) {

                jaxbCheckOutItemResponse.setElectronicResource(
                    createElectronicResource(checkOutItemResponseData.getElectronicResource()));

            }

            if (checkOutItemResponseData.getFiscalTransactionInformation() != null) {

                jaxbCheckOutItemResponse.setFiscalTransactionInformation(
                    createFiscalTransactionInformation(checkOutItemResponseData.getFiscalTransactionInformation()));

            }

            if (checkOutItemResponseData.getItemOptionalFields() != null) {

                jaxbCheckOutItemResponse.setItemOptionalFields(
                    createItemOptionalFields(checkOutItemResponseData.getItemOptionalFields()));

            }

            if (checkOutItemResponseData.getUserOptionalFields() != null) {

                jaxbCheckOutItemResponse.setUserOptionalFields(
                    createUserOptionalFields(checkOutItemResponseData.getUserOptionalFields()));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "Either both ItemId and UserId, or else Problems must be non-null in Check Out Item Response.");

        }

        return ncipRespMsg;
    }

    protected NCIPMessage createRenewItemResponseMessage(
        org.extensiblecatalog.ncip.v2.service.RenewItemResponseData renewItemResponseData)
        throws BindingException {

        NCIPMessage ncipRespMsg = new NCIPMessage();
        ncipRespMsg.setVersion(NCIP_VERSION_V2_0);
        RenewItemResponse jaxbRenewItemResponse = new RenewItemResponse();

        ncipRespMsg.setRenewItemResponse(jaxbRenewItemResponse);

        if (renewItemResponseData.getResponseHeader() != null) {
            ResponseHeader respHdr = createResponseHeader(renewItemResponseData.getResponseHeader());
            jaxbRenewItemResponse.setResponseHeader(respHdr);
        }

        if (renewItemResponseData.getProblems() != null && renewItemResponseData.getProblems().size() > 0) {

            jaxbRenewItemResponse.getProblem().addAll(createJAXBProblems(renewItemResponseData.getProblems()));

            if (renewItemResponseData.getRequiredFeeAmount() != null) {

                jaxbRenewItemResponse.setRequiredFeeAmount(
                    createRequiredFeeAmount(renewItemResponseData.getRequiredFeeAmount()));

            }

            if (renewItemResponseData.getRequiredItemUseRestrictionTypes() != null
                && renewItemResponseData.getRequiredItemUseRestrictionTypes().size() > 0) {

                jaxbRenewItemResponse.getRequiredItemUseRestrictionType().clear();
                jaxbRenewItemResponse.getRequiredItemUseRestrictionType().addAll(
                    convertSVPsToJAXB(renewItemResponseData.getRequiredItemUseRestrictionTypes()));

            }

        } else if (renewItemResponseData.getPending() != null ) {

            jaxbRenewItemResponse.setPending(createPending(renewItemResponseData.getPending()));

        } else if (renewItemResponseData.getItemId() != null) {

            jaxbRenewItemResponse.setItemId(createItemId(renewItemResponseData.getItemId()));

            jaxbRenewItemResponse.setUserId(createUserId(renewItemResponseData.getUserId()));

            if (renewItemResponseData.getDateDue() != null) {

                jaxbRenewItemResponse.setDateDue(convertDate(renewItemResponseData.getDateDue()));

            }

            if (renewItemResponseData.getDateForReturn() != null) {

                jaxbRenewItemResponse.setDateForReturn(convertDate(renewItemResponseData.getDateForReturn()));

            }

            if (renewItemResponseData.getRenewalCount() != null) {

                jaxbRenewItemResponse.setRenewalCount(renewItemResponseData.getRenewalCount());

            }

            if (renewItemResponseData.getFiscalTransactionInformation() != null) {

                jaxbRenewItemResponse.setFiscalTransactionInformation(
                    createFiscalTransactionInformation(renewItemResponseData.getFiscalTransactionInformation()));

            }

            if (renewItemResponseData.getItemOptionalFields() != null) {

                jaxbRenewItemResponse.setItemOptionalFields(
                    createItemOptionalFields(renewItemResponseData.getItemOptionalFields()));

            }

            if (renewItemResponseData.getUserOptionalFields() != null) {

                jaxbRenewItemResponse.setUserOptionalFields(
                    createUserOptionalFields(renewItemResponseData.getUserOptionalFields()));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "Either ItemId, Pending, or Problems must be non-null in Renew Item Response.");

        }

        return ncipRespMsg;
    }

    protected org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Pending createPending(
        org.extensiblecatalog.ncip.v2.service.Pending svcPending)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Pending jaxbPending = null;

        if ( svcPending != null ) {

            jaxbPending = new org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Pending();

            jaxbPending.setDateOfExpectedReply(convertDate(svcPending.getDateOfExpectedReply()));

        }

        return jaxbPending;

    }


    protected List<Problem> createJAXBProblems(List<org.extensiblecatalog.ncip.v2.service.Problem> svcProblems)
        throws BindingException {

        List<Problem> jaxbProblems = new ArrayList<Problem>();

        if (svcProblems != null && svcProblems.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.Problem svcProblem : svcProblems) {

                Problem jaxbProblem = createProblem(svcProblem);

                if (svcProblem != null) {

                    jaxbProblems.add(jaxbProblem);

                }

            }

        }

        return jaxbProblems;

    }

    protected org.extensiblecatalog.ncip.v2.service.InitiationHeader createInitiationHeader(
        InitiationHeader jaxbInitHdr) {

        org.extensiblecatalog.ncip.v2.service.InitiationHeader initHdr = null;

        if (jaxbInitHdr != null) {

            if (jaxbInitHdr.getFromSystemId() != null) {

                if (initHdr == null) {

                    initHdr = new org.extensiblecatalog.ncip.v2.service.InitiationHeader();

                }

                initHdr.setFromSystemId(createFromSystemId(jaxbInitHdr.getFromSystemId()));

            }

            if (jaxbInitHdr.getFromSystemAuthentication() != null) {

                if (initHdr == null) {

                    initHdr = new org.extensiblecatalog.ncip.v2.service.InitiationHeader();

                }

                initHdr.setFromSystemAuthentication(jaxbInitHdr.getFromSystemAuthentication());

            }

            if (jaxbInitHdr.getFromAgencyId() != null) {

                if (initHdr == null) {

                    initHdr = new org.extensiblecatalog.ncip.v2.service.InitiationHeader();

                }

                initHdr.setFromAgencyId(createFromAgencyId(jaxbInitHdr.getFromAgencyId()));

            }

            if (jaxbInitHdr.getFromAgencyAuthentication() != null) {

                if (initHdr == null) {

                    initHdr = new org.extensiblecatalog.ncip.v2.service.InitiationHeader();

                }

                initHdr.setFromAgencyAuthentication(jaxbInitHdr.getFromAgencyAuthentication());

            }

            if (jaxbInitHdr.getOnBehalfOfAgency() != null) {

                if (initHdr == null) {

                    initHdr = new org.extensiblecatalog.ncip.v2.service.InitiationHeader();

                }

                initHdr.setOnBehalfOfAgency(createOnBehalfOfAgency(jaxbInitHdr.getOnBehalfOfAgency()));

            }

            if (jaxbInitHdr.getToSystemId() != null) {

                if (initHdr == null) {

                    initHdr = new org.extensiblecatalog.ncip.v2.service.InitiationHeader();

                }

                initHdr.setToSystemId(createToSystemId(jaxbInitHdr.getToSystemId()));

            }

            if (jaxbInitHdr.getToAgencyId() != null) {

                if (initHdr == null) {

                    initHdr = new org.extensiblecatalog.ncip.v2.service.InitiationHeader();

                }

                initHdr.setToAgencyId(createToAgencyId(jaxbInitHdr.getToAgencyId()));

            }

            if (jaxbInitHdr.getApplicationProfileType() != null) {

                if (initHdr == null) {

                    initHdr = new org.extensiblecatalog.ncip.v2.service.InitiationHeader();

                }

                initHdr.setApplicationProfileType(createApplicationProfileType(jaxbInitHdr.getApplicationProfileType()));

            }

        }

        return initHdr;
    }

    protected org.extensiblecatalog.ncip.v2.service.FromAgencyId createFromAgencyId(FromAgencyId jaxbFromAgencyId) {
        org.extensiblecatalog.ncip.v2.service.FromAgencyId svcFromAgencyId = null;

        if (jaxbFromAgencyId != null) {

            svcFromAgencyId = new org.extensiblecatalog.ncip.v2.service.FromAgencyId();
            svcFromAgencyId.setAgencyId(createAgencyId(jaxbFromAgencyId.getAgencyId()));

        }

        return svcFromAgencyId;
    }

    protected org.extensiblecatalog.ncip.v2.service.ToAgencyId createToAgencyId(ToAgencyId jaxbToAgencyId) {
        org.extensiblecatalog.ncip.v2.service.ToAgencyId svcToAgencyId = null;

        if (jaxbToAgencyId != null) {

            svcToAgencyId = new org.extensiblecatalog.ncip.v2.service.ToAgencyId();
            svcToAgencyId.setAgencyId(createAgencyId(jaxbToAgencyId.getAgencyId()));

        }

        return svcToAgencyId;
    }

    protected org.extensiblecatalog.ncip.v2.service.AgencyId createAgencyId(SchemeValuePair jaxbAgencyId) {

        org.extensiblecatalog.ncip.v2.service.AgencyId svcAgencyId = null;

        if ( jaxbAgencyId != null) {

            svcAgencyId = new org.extensiblecatalog.ncip.v2.service.AgencyId(
                jaxbAgencyId.getScheme(), jaxbAgencyId.getValue());

        }

        return svcAgencyId;

    }

    protected org.extensiblecatalog.ncip.v2.service.FromSystemId createFromSystemId(SchemeValuePair jaxbFromSystemId) {
        org.extensiblecatalog.ncip.v2.service.FromSystemId svcFromSystemId = null;

        if (jaxbFromSystemId != null) {

            svcFromSystemId = new org.extensiblecatalog.ncip.v2.service.FromSystemId(
                jaxbFromSystemId.getScheme(), jaxbFromSystemId.getValue());

        }

        return svcFromSystemId;
    }

    protected org.extensiblecatalog.ncip.v2.service.ToSystemId createToSystemId(SchemeValuePair jaxbToSystemId) {
        org.extensiblecatalog.ncip.v2.service.ToSystemId svcToSystemId = null;

        if (jaxbToSystemId != null) {

            svcToSystemId = new org.extensiblecatalog.ncip.v2.service.ToSystemId(
                jaxbToSystemId.getScheme(), jaxbToSystemId.getValue());

        }

        return svcToSystemId;
    }

    protected org.extensiblecatalog.ncip.v2.service.ApplicationProfileType createApplicationProfileType(SchemeValuePair jaxbApplicationProfileType) {
        org.extensiblecatalog.ncip.v2.service.ApplicationProfileType svcApplicationProfileType = null;

        if (jaxbApplicationProfileType != null) {

            svcApplicationProfileType = new org.extensiblecatalog.ncip.v2.service.ApplicationProfileType(
                jaxbApplicationProfileType.getScheme(), jaxbApplicationProfileType.getValue());

        }

        return svcApplicationProfileType;
    }

    protected org.extensiblecatalog.ncip.v2.service.AcceptItemInitiationData createAcceptItemInitiationData(
        AcceptItem jaxbAcceptItem) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.AcceptItemInitiationData svcAcceptItem = null;

        if (jaxbAcceptItem != null) {

            svcAcceptItem = new org.extensiblecatalog.ncip.v2.service.AcceptItemInitiationData();

            svcAcceptItem.setInitiationHeader(createInitiationHeader(jaxbAcceptItem.getInitiationHeader()));

            svcAcceptItem.setMandatedAction(createMandatedAction(jaxbAcceptItem.getMandatedAction()));

            if (jaxbAcceptItem.getRequestId() != null) {

                svcAcceptItem.setRequestId(createRequestId(jaxbAcceptItem.getRequestId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "RequestId must be non-null in AcceptItem.");

            }

            if (jaxbAcceptItem.getRequestedActionType() != null) {

                svcAcceptItem.setRequestedActionType(
                    convertRequestedActionType(jaxbAcceptItem.getRequestedActionType()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "RequestedActionType must be non-null in AcceptItem.");

            }

            svcAcceptItem.setUserId(createUserId(jaxbAcceptItem.getUserId()));

            svcAcceptItem.setItemId(createItemId(jaxbAcceptItem.getItemId()));

            if (jaxbAcceptItem.getDateForReturn() != null) {

                svcAcceptItem.setDateForReturn(convertDate(jaxbAcceptItem.getDateForReturn()));

            } else if (jaxbAcceptItem.getIndeterminateLoanPeriodFlag() != null) {

                svcAcceptItem.setIndeterminateLoanPeriodFlag(true);

            } else if (jaxbAcceptItem.getNonReturnableFlag() != null) {

                svcAcceptItem.setNonReturnableFlag(true);

            }

            if (jaxbAcceptItem.getRenewalNotPermitted() != null) {

                svcAcceptItem.setRenewalNotPermitted(true);

            }

            if (jaxbAcceptItem.getFiscalTransactionInformation() != null) {
                svcAcceptItem.setFiscalTransactionInformation(createFiscalTransactionInformation(
                    jaxbAcceptItem.getFiscalTransactionInformation()));
            }

            if (jaxbAcceptItem.getPickupLocation() != null) {
                svcAcceptItem.setPickupLocation(convertPickupLocation(jaxbAcceptItem.getPickupLocation()));
            }

            if (jaxbAcceptItem.getPickupExpiryDate() != null) {
                svcAcceptItem.setPickupExpiryDate(convertDate(jaxbAcceptItem.getPickupExpiryDate()));
            }

            if (jaxbAcceptItem.getItemOptionalFields() != null) {
                svcAcceptItem.setItemOptionalFields(createItemOptionalFields(jaxbAcceptItem.getItemOptionalFields()));
            }

            if (jaxbAcceptItem.getUserOptionalFields() != null) {
                svcAcceptItem.setUserOptionalFields(createUserOptionalFields(jaxbAcceptItem.getUserOptionalFields()));
            }

        }

        return svcAcceptItem;

    }

    protected org.extensiblecatalog.ncip.v2.service.CheckInItemInitiationData createCheckInItemInitiationData(
        CheckInItem jaxbCheckInItem) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.CheckInItemInitiationData svcCheckInItem =
            new org.extensiblecatalog.ncip.v2.service.CheckInItemInitiationData();

        svcCheckInItem.setInitiationHeader(createInitiationHeader(jaxbCheckInItem.getInitiationHeader()));

        svcCheckInItem.setMandatedAction(createMandatedAction(jaxbCheckInItem.getMandatedAction()));

        if (jaxbCheckInItem.getItemId() != null) {

            svcCheckInItem.setItemId(createItemId(jaxbCheckInItem.getItemId()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "ItemId must be non-null in CheckInItem.");

        }


        List<SchemeValuePair> itemElementTypes = jaxbCheckInItem.getItemElementType();
        for (SchemeValuePair svp : itemElementTypes) {

            try {

                org.extensiblecatalog.ncip.v2.service.ItemElementType itemElementType =
                    org.extensiblecatalog.ncip.v2.service.ItemElementType.find(svp.getScheme(), svp.getValue());

                if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .BIBLIOGRAPHIC_DESCRIPTION)) {
                    svcCheckInItem.setBibliographicDescriptionDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .CIRCULATION_STATUS)) {
                    svcCheckInItem.setCirculationStatusDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .ELECTRONIC_RESOURCE)) {
                    svcCheckInItem.setElectronicResourceDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .HOLD_QUEUE_LENGTH)) {
                    svcCheckInItem.setHoldQueueLengthDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .ITEM_DESCRIPTION)) {
                    svcCheckInItem.setItemDescriptionDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .ITEM_USE_RESTRICTION_TYPE)) {
                    svcCheckInItem.setItemUseRestrictionTypeDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .LOCATION)) {
                    svcCheckInItem.setLocationDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .PHYSICAL_CONDITION)) {
                    svcCheckInItem.setPhysicalConditionDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .SECURITY_MARKER)) {
                    svcCheckInItem.setSecurityMarkerDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .SENSITIZATION_FLAG)) {
                    svcCheckInItem.setSensitizationFlagDesired(true);
                } else {
                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected enumeration value "
                        + (itemElementType.getScheme() != null ? itemElementType.getScheme() + " " : " ")
                        + itemElementType.getValue() + " in Check In Item.");
                }

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        if (jaxbCheckInItem.getUserElementType() != null
            && jaxbCheckInItem.getUserElementType().size() > 0) {

            List<SchemeValuePair> userElementTypes = jaxbCheckInItem.getUserElementType();
            for (SchemeValuePair svp : userElementTypes) {

                try {

                    org.extensiblecatalog.ncip.v2.service.UserElementType userElementType =
                        org.extensiblecatalog.ncip.v2.service.UserElementType.find(svp.getScheme(), svp.getValue());

                    if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .AUTHENTICATION_INPUT)) {
                        svcCheckInItem.setAuthenticationInputDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .BLOCK_OR_TRAP)) {
                        svcCheckInItem.setBlockOrTrapDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .DATE_OF_BIRTH)) {
                        svcCheckInItem.setDateOfBirthDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .NAME_INFORMATION)) {
                        svcCheckInItem.setNameInformationDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_ADDRESS_INFORMATION)) {
                        svcCheckInItem.setUserAddressInformationDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_LANGUAGE)) {
                        svcCheckInItem.setUserLanguageDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_PRIVILEGE)) {
                        svcCheckInItem.setUserPrivilegeDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_ID)) {
                        svcCheckInItem.setUserIdDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .PREVIOUS_USER_ID)) {
                        svcCheckInItem.setPreviousUserIdDesired(true);
                    } else {
                        throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected enumeration value "
                            + (userElementType.getScheme() != null ? userElementType.getScheme() + " " : " ")
                            + userElementType.getValue() + " in INIT_MSG_NAME.");
                    }

                } catch (ServiceException e) {

                    throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                }
            }

        }

        return svcCheckInItem;

    }

    protected org.extensiblecatalog.ncip.v2.service.CheckOutItemInitiationData createCheckOutItemInitiationData(
        CheckOutItem jaxbCheckOutItem) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.CheckOutItemInitiationData svcCheckOutItem =
            new org.extensiblecatalog.ncip.v2.service.CheckOutItemInitiationData();

        svcCheckOutItem.setInitiationHeader(createInitiationHeader(jaxbCheckOutItem.getInitiationHeader()));

        svcCheckOutItem.setMandatedAction(createMandatedAction(jaxbCheckOutItem.getMandatedAction()));

        if (jaxbCheckOutItem.getUserId() != null) {

            svcCheckOutItem.setUserId(createUserId(jaxbCheckOutItem.getUserId()));

        } else if (jaxbCheckOutItem.getAuthenticationInput() != null) {

            svcCheckOutItem.setAuthenticationInputs(createSVCAuthenticationInputs(
                jaxbCheckOutItem.getAuthenticationInput()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "UserId or AuthenticationInput must be non-null in CheckOutItem.");

        }

        if (jaxbCheckOutItem.getItemId() != null) {

            svcCheckOutItem.setItemId(createItemId(jaxbCheckOutItem.getItemId()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "ItemId must be non-null in CheckOutItem.");

        }

        svcCheckOutItem.setRequestId(createRequestId(jaxbCheckOutItem.getRequestId()));

        svcCheckOutItem.setAcknowledgedFeeAmount(createAcknowledgedFeeAmount(
            jaxbCheckOutItem.getAcknowledgedFeeAmount()));

        svcCheckOutItem.setPaidFeeAmount(createPaidFeeAmount(jaxbCheckOutItem.getPaidFeeAmount()));

        if (jaxbCheckOutItem.getAcknowledgedItemUseRestrictionType() != null
            && jaxbCheckOutItem.getAcknowledgedItemUseRestrictionType().size() > 0) {

            svcCheckOutItem.setAcknowledgedItemUseRestrictionTypes(
                convertItemUseRestrictionTypes(jaxbCheckOutItem.getAcknowledgedItemUseRestrictionType()));

        }

        svcCheckOutItem.setShippingInformation(createShippingInformation(jaxbCheckOutItem.getShippingInformation()));

        svcCheckOutItem.setResourceDesired(jaxbCheckOutItem.getResourceDesired() != null);

        svcCheckOutItem.setDesiredDateDue(convertDate(jaxbCheckOutItem.getDesiredDateDue()));

        List<SchemeValuePair> itemElementTypes = jaxbCheckOutItem.getItemElementType();
        for (SchemeValuePair svp : itemElementTypes) {

            try {

                org.extensiblecatalog.ncip.v2.service.ItemElementType itemElementType =
                    org.extensiblecatalog.ncip.v2.service.ItemElementType.find(svp.getScheme(), svp.getValue());

                if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .BIBLIOGRAPHIC_DESCRIPTION)) {
                    svcCheckOutItem.setBibliographicDescriptionDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .CIRCULATION_STATUS)) {
                    svcCheckOutItem.setCirculationStatusDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .ELECTRONIC_RESOURCE)) {
                    svcCheckOutItem.setElectronicResourceDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .HOLD_QUEUE_LENGTH)) {
                    svcCheckOutItem.setHoldQueueLengthDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .ITEM_DESCRIPTION)) {
                    svcCheckOutItem.setItemDescriptionDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .ITEM_USE_RESTRICTION_TYPE)) {
                    svcCheckOutItem.setItemUseRestrictionTypeDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .LOCATION)) {
                    svcCheckOutItem.setLocationDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .PHYSICAL_CONDITION)) {
                    svcCheckOutItem.setPhysicalConditionDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .SECURITY_MARKER)) {
                    svcCheckOutItem.setSecurityMarkerDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .SENSITIZATION_FLAG)) {
                    svcCheckOutItem.setSensitizationFlagDesired(true);
                } else {
                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected enumeration value "
                        + (itemElementType.getScheme() != null ? itemElementType.getScheme() + " " : " ")
                        + itemElementType.getValue() + " in CheckOutItem.");
                }

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        if (jaxbCheckOutItem.getUserElementType() != null
            && jaxbCheckOutItem.getUserElementType().size() > 0) {

            List<SchemeValuePair> userElementTypes = jaxbCheckOutItem.getUserElementType();
            for (SchemeValuePair svp : userElementTypes) {

                try {

                    org.extensiblecatalog.ncip.v2.service.UserElementType userElementType =
                        org.extensiblecatalog.ncip.v2.service.UserElementType.find(svp.getScheme(), svp.getValue());

                    if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .AUTHENTICATION_INPUT)) {
                        svcCheckOutItem.setAuthenticationInputDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .BLOCK_OR_TRAP)) {
                        svcCheckOutItem.setBlockOrTrapDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .DATE_OF_BIRTH)) {
                        svcCheckOutItem.setDateOfBirthDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .NAME_INFORMATION)) {
                        svcCheckOutItem.setNameInformationDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_ADDRESS_INFORMATION)) {
                        svcCheckOutItem.setUserAddressInformationDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_LANGUAGE)) {
                        svcCheckOutItem.setUserLanguageDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_PRIVILEGE)) {
                        svcCheckOutItem.setUserPrivilegeDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_ID)) {
                        svcCheckOutItem.setUserIdDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .PREVIOUS_USER_ID)) {
                        svcCheckOutItem.setPreviousUserIdDesired(true);
                    } else {
                        throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected enumeration value "
                            + (userElementType.getScheme() != null ? userElementType.getScheme() + " " : " ")
                            + userElementType.getValue() + " in INIT_MSG_NAME.");
                    }

                } catch (ServiceException e) {

                    throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                }
            }

        }

        return svcCheckOutItem;

    }

    protected org.extensiblecatalog.ncip.v2.service.RenewItemInitiationData createRenewItemInitiationData(
        RenewItem jaxbRenewItem) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.RenewItemInitiationData svcRenewItem =
            new org.extensiblecatalog.ncip.v2.service.RenewItemInitiationData();

        svcRenewItem.setInitiationHeader(createInitiationHeader(jaxbRenewItem.getInitiationHeader()));

        svcRenewItem.setMandatedAction(createMandatedAction(jaxbRenewItem.getMandatedAction()));

        if (jaxbRenewItem.getUserId() != null) {

            svcRenewItem.setUserId(createUserId(jaxbRenewItem.getUserId()));

        } else if (jaxbRenewItem.getAuthenticationInput() != null) {

            svcRenewItem.setAuthenticationInputs(createSVCAuthenticationInputs(
                jaxbRenewItem.getAuthenticationInput()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "UserId or AuthenticationInput must be non-null in CheckOutItem.");

        }

        if (jaxbRenewItem.getItemId() != null) {

            svcRenewItem.setItemId(createItemId(jaxbRenewItem.getItemId()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "ItemId must be non-null in RenewItem.");

        }

        if (jaxbRenewItem.getItemElementType() != null
            && jaxbRenewItem.getItemElementType().size() > 0) {

            List<SchemeValuePair> itemElementTypes = jaxbRenewItem.getItemElementType();
            for (SchemeValuePair svp : itemElementTypes) {

                try {

                    org.extensiblecatalog.ncip.v2.service.ItemElementType itemElementType =
                        org.extensiblecatalog.ncip.v2.service.ItemElementType.find(svp.getScheme(), svp.getValue());

                    if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .BIBLIOGRAPHIC_DESCRIPTION)) {
                        svcRenewItem.setBibliographicDescriptionDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .CIRCULATION_STATUS)) {
                        svcRenewItem.setCirculationStatusDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .ELECTRONIC_RESOURCE)) {
                        svcRenewItem.setElectronicResourceDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .HOLD_QUEUE_LENGTH)) {
                        svcRenewItem.setHoldQueueLengthDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .ITEM_DESCRIPTION)) {
                        svcRenewItem.setItemDescriptionDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .ITEM_USE_RESTRICTION_TYPE)) {
                        svcRenewItem.setItemUseRestrictionTypeDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .LOCATION)) {
                        svcRenewItem.setLocationDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .PHYSICAL_CONDITION)) {
                        svcRenewItem.setPhysicalConditionDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .SECURITY_MARKER)) {
                        svcRenewItem.setSecurityMarkerDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .SENSITIZATION_FLAG)) {
                        svcRenewItem.setSensitizationFlagDesired(true);
                    } else {
                        throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected enumeration value "
                            + (itemElementType.getScheme() != null ? itemElementType.getScheme() + " " : " ")
                            + itemElementType.getValue() + " in RenewItem.");
                    }

                } catch (ServiceException e) {

                    throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                }
            }

        }

        if (jaxbRenewItem.getUserElementType() != null
            && jaxbRenewItem.getUserElementType().size() > 0) {

            List<SchemeValuePair> userElementTypes = jaxbRenewItem.getUserElementType();
            for (SchemeValuePair svp : userElementTypes) {

                try {

                    org.extensiblecatalog.ncip.v2.service.UserElementType userElementType =
                        org.extensiblecatalog.ncip.v2.service.UserElementType.find(svp.getScheme(), svp.getValue());

                    if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .AUTHENTICATION_INPUT)) {
                        svcRenewItem.setAuthenticationInputDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .BLOCK_OR_TRAP)) {
                        svcRenewItem.setBlockOrTrapDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .DATE_OF_BIRTH)) {
                        svcRenewItem.setDateOfBirthDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .NAME_INFORMATION)) {
                        svcRenewItem.setNameInformationDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_ADDRESS_INFORMATION)) {
                        svcRenewItem.setUserAddressInformationDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_LANGUAGE)) {
                        svcRenewItem.setUserLanguageDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_PRIVILEGE)) {
                        svcRenewItem.setUserPrivilegeDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_ID)) {
                        svcRenewItem.setUserIdDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .PREVIOUS_USER_ID)) {
                        svcRenewItem.setPreviousUserIdDesired(true);
                    } else {
                        throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected enumeration value "
                            + (userElementType.getScheme() != null ? userElementType.getScheme() + " " : " ")
                            + userElementType.getValue() + " in INIT_MSG_NAME.");
                    }

                } catch (ServiceException e) {

                    throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                }
            }

        }

        svcRenewItem.setDesiredDateDue(convertDate(jaxbRenewItem.getDesiredDateDue()));

        svcRenewItem.setDesiredDateForReturn(convertDate(jaxbRenewItem.getDesiredDateForReturn()));

        svcRenewItem.setAcknowledgedFeeAmount(createAcknowledgedFeeAmount(
            jaxbRenewItem.getAcknowledgedFeeAmount()));

        svcRenewItem.setPaidFeeAmount(createPaidFeeAmount(jaxbRenewItem.getPaidFeeAmount()));

        if (jaxbRenewItem.getAcknowledgedItemUseRestrictionType() != null
            && jaxbRenewItem.getAcknowledgedItemUseRestrictionType().size() > 0) {

            svcRenewItem.setAcknowledgedItemUseRestrictionTypes(
                convertItemUseRestrictionTypes(jaxbRenewItem.getAcknowledgedItemUseRestrictionType()));

        }

        return svcRenewItem;

    }

    protected org.extensiblecatalog.ncip.v2.service.CurrencyCode convertCurrencyCode(
        SchemeValuePair jaxbCurrencyCode) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.CurrencyCode svcCurrencyCode = null;

        if (jaxbCurrencyCode != null) {

            try {

                svcCurrencyCode = org.extensiblecatalog.ncip.v2.service.CurrencyCode.find(
                    jaxbCurrencyCode.getScheme(), jaxbCurrencyCode.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcCurrencyCode;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.CurrencyCode> convertCurrencyCodes(
        List<SchemeValuePair> jaxbCurrencyCodes) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.CurrencyCode> svcCurrencyCodes;

        if (jaxbCurrencyCodes != null && jaxbCurrencyCodes.size() > 0) {

            svcCurrencyCodes = new ArrayList<org.extensiblecatalog.ncip.v2.service.CurrencyCode>();

            for (SchemeValuePair jaxbCurrencyCode : jaxbCurrencyCodes) {

                svcCurrencyCodes.add(convertCurrencyCode(jaxbCurrencyCode));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "CurrencyCode must be non-null and non-empty in convertCurrencyCodes.");

        }

        return svcCurrencyCodes;

    }

    protected org.extensiblecatalog.ncip.v2.service.ItemElementType convertItemElementType(
        SchemeValuePair jaxbItemElementType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ItemElementType svcItemElementType = null;

        if (jaxbItemElementType != null) {

            try {

                svcItemElementType = org.extensiblecatalog.ncip.v2.service.ItemElementType.find(
                    jaxbItemElementType.getScheme(), jaxbItemElementType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcItemElementType;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.ItemElementType> convertItemElementTypes(
        List<SchemeValuePair> jaxbItemElementTypes) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.ItemElementType> svcItemElementTypes;

        if (jaxbItemElementTypes != null && jaxbItemElementTypes.size() > 0) {

            svcItemElementTypes = new ArrayList<org.extensiblecatalog.ncip.v2.service.ItemElementType>();

            for (SchemeValuePair jaxbItemElementType : jaxbItemElementTypes) {

                svcItemElementTypes.add(convertItemElementType(jaxbItemElementType));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "ItemElementType must be non-null and non-empty in convertItemElementTypes.");

        }

        return svcItemElementTypes;
    }

    protected org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType convertItemUseRestrictionType(
        SchemeValuePair jaxbItemUseRestrictionType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType svcItemUseRestrictionType = null;

        if (jaxbItemUseRestrictionType != null) {

            try {

                svcItemUseRestrictionType = org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType.find(
                    jaxbItemUseRestrictionType.getScheme(), jaxbItemUseRestrictionType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcItemUseRestrictionType;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType> convertItemUseRestrictionTypes(
        List<SchemeValuePair> jaxbItemUseRestrictionTypes) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType> svcItemUseRestrictionTypes;

        if (jaxbItemUseRestrictionTypes != null && jaxbItemUseRestrictionTypes.size() > 0) {

            svcItemUseRestrictionTypes = new ArrayList<org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType>();

            for (SchemeValuePair jaxbItemUseRestrictionType : jaxbItemUseRestrictionTypes) {

                svcItemUseRestrictionTypes.add(convertItemUseRestrictionType(jaxbItemUseRestrictionType));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "ItemUseRestrictionType must be non-null and non-empty in convertItemUseRestrictionTypes.");

        }

        return svcItemUseRestrictionTypes;
    }

    protected org.extensiblecatalog.ncip.v2.service.OrganizationNameType convertOrganizationNameType(
        SchemeValuePair jaxbOrganizationNameType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.OrganizationNameType svcOrganizationNameType = null;

        if (jaxbOrganizationNameType != null) {

            try {

                svcOrganizationNameType = org.extensiblecatalog.ncip.v2.service.OrganizationNameType.find(
                    jaxbOrganizationNameType.getScheme(), jaxbOrganizationNameType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcOrganizationNameType;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.OrganizationNameType> convertOrganizationNameTypes(
        List<SchemeValuePair> jaxbOrganizationNameTypes) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.OrganizationNameType> svcOrganizationNameTypes;

        if (jaxbOrganizationNameTypes != null && jaxbOrganizationNameTypes.size() > 0) {

            svcOrganizationNameTypes = new ArrayList<org.extensiblecatalog.ncip.v2.service.OrganizationNameType>();

            for (SchemeValuePair jaxbOrganizationNameType : jaxbOrganizationNameTypes) {

                svcOrganizationNameTypes.add(convertOrganizationNameType(jaxbOrganizationNameType));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "OrganizationNameType must be non-null and non-empty in convertOrganizationNameTypes.");

        }

        return svcOrganizationNameTypes;
    }

    protected org.extensiblecatalog.ncip.v2.service.RequestedActionType convertRequestedActionType(
        SchemeValuePair jaxbRequestedActionType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.RequestedActionType svcRequestedActionType = null;

        if (jaxbRequestedActionType != null) {

            try {

                svcRequestedActionType = org.extensiblecatalog.ncip.v2.service.RequestedActionType.find(
                    jaxbRequestedActionType.getScheme(), jaxbRequestedActionType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcRequestedActionType;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.RequestedActionType> convertRequestedActionTypes(
        List<SchemeValuePair> jaxbRequestedActionTypes) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.RequestedActionType> svcRequestedActionTypes;

        if (jaxbRequestedActionTypes != null && jaxbRequestedActionTypes.size() > 0) {

            svcRequestedActionTypes = new ArrayList<org.extensiblecatalog.ncip.v2.service.RequestedActionType>();

            for (SchemeValuePair jaxbRequestedActionType : jaxbRequestedActionTypes) {

                svcRequestedActionTypes.add(convertRequestedActionType(jaxbRequestedActionType));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "RequestedActionType must be non-null and non-empty in convertRequestedActionTypes.");

        }

        return svcRequestedActionTypes;
    }

    protected org.extensiblecatalog.ncip.v2.service.RequestType convertRequestType(
        SchemeValuePair jaxbRequestType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.RequestType svcRequestType = null;

        if (jaxbRequestType != null) {

            try {

                svcRequestType = org.extensiblecatalog.ncip.v2.service.RequestType.find(
                    jaxbRequestType.getScheme(), jaxbRequestType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcRequestType;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.RequestType> convertRequestTypes(
        List<SchemeValuePair> jaxbRequestTypes) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.RequestType> svcRequestTypes;

        if (jaxbRequestTypes != null && jaxbRequestTypes.size() > 0) {

            svcRequestTypes = new ArrayList<org.extensiblecatalog.ncip.v2.service.RequestType>();

            for (SchemeValuePair jaxbRequestType : jaxbRequestTypes) {

                svcRequestTypes.add(convertRequestType(jaxbRequestType));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "RequestType must be non-null and non-empty in convertRequestTypes.");

        }

        return svcRequestTypes;
    }

    protected org.extensiblecatalog.ncip.v2.service.RequestScopeType convertRequestScopeType(
        SchemeValuePair jaxbRequestScopeType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.RequestScopeType svcRequestScopeType = null;

        if (jaxbRequestScopeType != null) {

            try {

                svcRequestScopeType = org.extensiblecatalog.ncip.v2.service.RequestScopeType.find(
                    jaxbRequestScopeType.getScheme(), jaxbRequestScopeType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcRequestScopeType;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.RequestScopeType> convertRequestScopeTypes(
        List<SchemeValuePair> jaxbRequestScopeTypes) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.RequestScopeType> svcRequestScopeTypes;

        if (jaxbRequestScopeTypes != null && jaxbRequestScopeTypes.size() > 0) {

            svcRequestScopeTypes = new ArrayList<org.extensiblecatalog.ncip.v2.service.RequestScopeType>();

            for (SchemeValuePair jaxbRequestScopeType : jaxbRequestScopeTypes) {

                svcRequestScopeTypes.add(convertRequestScopeType(jaxbRequestScopeType));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "RequestScopeType must be non-null and non-empty in convertRequestScopeTypes.");

        }

        return svcRequestScopeTypes;
    }

    protected org.extensiblecatalog.ncip.v2.service.PickupLocation convertPickupLocation(
        SchemeValuePair jaxbPickupLocation) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.PickupLocation svcPickupLocation = null;

        if (jaxbPickupLocation != null) {

            try {

                svcPickupLocation = org.extensiblecatalog.ncip.v2.service.PickupLocation.find(
                    jaxbPickupLocation.getScheme(), jaxbPickupLocation.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcPickupLocation;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.PickupLocation> convertPickupLocations(
        List<SchemeValuePair> jaxbPickupLocations) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.PickupLocation> svcPickupLocations;

        if (jaxbPickupLocations != null && jaxbPickupLocations.size() > 0) {

            svcPickupLocations = new ArrayList<org.extensiblecatalog.ncip.v2.service.PickupLocation>();

            for (SchemeValuePair jaxbPickupLocation : jaxbPickupLocations) {

                svcPickupLocations.add(convertPickupLocation(jaxbPickupLocation));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "PickupLocation must be non-null and non-empty in convertPickupLocations.");

        }

        return svcPickupLocations;
    }

    protected org.extensiblecatalog.ncip.v2.service.RequestStatusType convertRequestStatusType(
        SchemeValuePair jaxbRequestStatusType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.RequestStatusType svcRequestStatusType = null;

        if (jaxbRequestStatusType != null) {

            try {

                svcRequestStatusType = org.extensiblecatalog.ncip.v2.service.RequestStatusType.find(
                    jaxbRequestStatusType.getScheme(), jaxbRequestStatusType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcRequestStatusType;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.RequestStatusType> convertRequestStatusTypes(
        List<SchemeValuePair> jaxbRequestStatusTypes) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.RequestStatusType> svcRequestStatusTypes;

        if (jaxbRequestStatusTypes != null && jaxbRequestStatusTypes.size() > 0) {

            svcRequestStatusTypes = new ArrayList<org.extensiblecatalog.ncip.v2.service.RequestStatusType>();

            for (SchemeValuePair jaxbRequestStatusType : jaxbRequestStatusTypes) {

                svcRequestStatusTypes.add(convertRequestStatusType(jaxbRequestStatusType));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "RequestStatusType must be non-null and non-empty in convertRequestStatusTypes.");

        }

        return svcRequestStatusTypes;
    }

    protected org.extensiblecatalog.ncip.v2.service.BibliographicItemIdentifierCode convertBibliographicItemIdentifierCode(
        SchemeValuePair jaxbBibliographicItemIdentifierCode) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.BibliographicItemIdentifierCode svcBibliographicItemIdentifierCode = null;

        if (jaxbBibliographicItemIdentifierCode != null) {

            try {

                svcBibliographicItemIdentifierCode = org.extensiblecatalog.ncip.v2.service.BibliographicItemIdentifierCode.find(
                    jaxbBibliographicItemIdentifierCode.getScheme(), jaxbBibliographicItemIdentifierCode.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcBibliographicItemIdentifierCode;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.BibliographicItemIdentifierCode> convertBibliographicItemIdentifierCodes(
        List<SchemeValuePair> jaxbBibliographicItemIdentifierCodes) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.BibliographicItemIdentifierCode> svcBibliographicItemIdentifierCodes;

        if (jaxbBibliographicItemIdentifierCodes != null && jaxbBibliographicItemIdentifierCodes.size() > 0) {

            svcBibliographicItemIdentifierCodes = new ArrayList<org.extensiblecatalog.ncip.v2.service.BibliographicItemIdentifierCode>();

            for (SchemeValuePair jaxbBibliographicItemIdentifierCode : jaxbBibliographicItemIdentifierCodes) {

                svcBibliographicItemIdentifierCodes.add(convertBibliographicItemIdentifierCode(jaxbBibliographicItemIdentifierCode));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "BibliographicItemIdentifierCode must be non-null and non-empty in convertBibliographicItemIdentifierCodes.");

        }

        return svcBibliographicItemIdentifierCodes;
    }

    protected org.extensiblecatalog.ncip.v2.service.BibliographicRecordIdentifierCode convertBibliographicRecordIdentifierCode(
        SchemeValuePair jaxbBibliographicRecordIdentifierCode) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.BibliographicRecordIdentifierCode svcBibliographicRecordIdentifierCode = null;

        if (jaxbBibliographicRecordIdentifierCode != null) {

            try {

                svcBibliographicRecordIdentifierCode = org.extensiblecatalog.ncip.v2.service.BibliographicRecordIdentifierCode.find(
                    jaxbBibliographicRecordIdentifierCode.getScheme(), jaxbBibliographicRecordIdentifierCode.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcBibliographicRecordIdentifierCode;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.BibliographicRecordIdentifierCode> convertBibliographicRecordIdentifierCodes(
        List<SchemeValuePair> jaxbBibliographicRecordIdentifierCodes) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.BibliographicRecordIdentifierCode> svcBibliographicRecordIdentifierCodes;

        if (jaxbBibliographicRecordIdentifierCodes != null && jaxbBibliographicRecordIdentifierCodes.size() > 0) {

            svcBibliographicRecordIdentifierCodes = new ArrayList<org.extensiblecatalog.ncip.v2.service.BibliographicRecordIdentifierCode>();

            for (SchemeValuePair jaxbBibliographicRecordIdentifierCode : jaxbBibliographicRecordIdentifierCodes) {

                svcBibliographicRecordIdentifierCodes.add(convertBibliographicRecordIdentifierCode(jaxbBibliographicRecordIdentifierCode));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "BibliographicRecordIdentifierCode must be non-null and non-empty in convertBibliographicRecordIdentifierCodes.");

        }

        return svcBibliographicRecordIdentifierCodes;
    }

    protected org.extensiblecatalog.ncip.v2.service.RequestElementType convertRequestElementType(
        SchemeValuePair jaxbRequestElementType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.RequestElementType svcRequestElementType = null;

        if (jaxbRequestElementType != null) {

            try {

                svcRequestElementType = org.extensiblecatalog.ncip.v2.service.RequestElementType.find(
                    jaxbRequestElementType.getScheme(), jaxbRequestElementType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcRequestElementType;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.RequestElementType> convertRequestElementTypes(
        List<SchemeValuePair> jaxbRequestElementTypes) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.RequestElementType> svcRequestElementTypes;

        if (jaxbRequestElementTypes != null && jaxbRequestElementTypes.size() > 0) {

            svcRequestElementTypes = new ArrayList<org.extensiblecatalog.ncip.v2.service.RequestElementType>();

            for (SchemeValuePair jaxbRequestElementType : jaxbRequestElementTypes) {

                svcRequestElementTypes.add(convertRequestElementType(jaxbRequestElementType));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "RequestElementType must be non-null and non-empty in convertRequestElementTypes.");

        }

        return svcRequestElementTypes;
    }

    protected org.extensiblecatalog.ncip.v2.service.UserElementType convertUserElementType(
        SchemeValuePair jaxbUserElementType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.UserElementType svcUserElementType = null;

        if (jaxbUserElementType != null) {

            try {

                svcUserElementType = org.extensiblecatalog.ncip.v2.service.UserElementType.find(
                    jaxbUserElementType.getScheme(), jaxbUserElementType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcUserElementType;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.UserElementType> convertUserElementTypes(
        List<SchemeValuePair> jaxbUserElementTypes) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.UserElementType> svcUserElementTypes;

        if (jaxbUserElementTypes != null && jaxbUserElementTypes.size() > 0) {

            svcUserElementTypes = new ArrayList<org.extensiblecatalog.ncip.v2.service.UserElementType>();

            for (SchemeValuePair jaxbUserElementType : jaxbUserElementTypes) {

                svcUserElementTypes.add(convertUserElementType(jaxbUserElementType));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "UserElementType must be non-null and non-empty in convertUserElementTypes.");

        }

        return svcUserElementTypes;
    }

    protected org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData createLookupItemSetInitiationData(
        LookupItemSet jaxbLookupItemSet) throws BindingException {
     
        org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData svcLookupItemSetInitiationData =
            new org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData();

        svcLookupItemSetInitiationData.setInitiationHeader(
            createInitiationHeader(jaxbLookupItemSet.getInitiationHeader()));

        if ( jaxbLookupItemSet.getBibliographicId() != null && jaxbLookupItemSet.getBibliographicId().size() > 0 ) {

            svcLookupItemSetInitiationData.setBibliographicIds(createSVCBibliographicIds(jaxbLookupItemSet.getBibliographicId()));

        } else if ( jaxbLookupItemSet.getHoldingsSetId() != null && jaxbLookupItemSet.getHoldingsSetId().size() > 0 ) {

            svcLookupItemSetInitiationData.setHoldingsSetIds(jaxbLookupItemSet.getHoldingsSetId());

        } else if ( jaxbLookupItemSet.getItemId() != null && jaxbLookupItemSet.getItemId().size() > 0 ) {

            svcLookupItemSetInitiationData.setItemIds(createSVCItemIds(jaxbLookupItemSet.getItemId()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "BibliographiId, ItemId, or HoldingsSetId must be non-null in LookupItemSet.");

        }

        if (jaxbLookupItemSet.getCurrentBorrowerDesired() != null) {

            svcLookupItemSetInitiationData.setCurrentBorrowerDesired(true);

        }

        if (jaxbLookupItemSet.getCurrentRequestersDesired() != null) {

            svcLookupItemSetInitiationData.setCurrentRequestersDesired(true);

        }

        List<SchemeValuePair> itemElementTypes = jaxbLookupItemSet.getItemElementType();
        for (SchemeValuePair svp : itemElementTypes) {

            try {

                org.extensiblecatalog.ncip.v2.service.ItemElementType itemElementType =
                    org.extensiblecatalog.ncip.v2.service.ItemElementType.find(svp.getScheme(), svp.getValue());

                if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .BIBLIOGRAPHIC_DESCRIPTION)) {
                    svcLookupItemSetInitiationData.setBibliographicDescriptionDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .CIRCULATION_STATUS)) {
                    svcLookupItemSetInitiationData.setCirculationStatusDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .ELECTRONIC_RESOURCE)) {
                    svcLookupItemSetInitiationData.setElectronicResourceDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .HOLD_QUEUE_LENGTH)) {
                    svcLookupItemSetInitiationData.setHoldQueueLengthDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .ITEM_DESCRIPTION)) {
                    svcLookupItemSetInitiationData.setItemDescriptionDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .ITEM_USE_RESTRICTION_TYPE)) {
                    svcLookupItemSetInitiationData.setItemUseRestrictionTypeDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .LOCATION)) {
                    svcLookupItemSetInitiationData.setLocationDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .PHYSICAL_CONDITION)) {
                    svcLookupItemSetInitiationData.setPhysicalConditionDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .SECURITY_MARKER)) {
                    svcLookupItemSetInitiationData.setSecurityMarkerDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .SENSITIZATION_FLAG)) {
                    svcLookupItemSetInitiationData.setSensitizationFlagDesired(true);
                } else {
                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected enumeration value "
                        + (itemElementType.getScheme() != null ? itemElementType.getScheme() + " " : " ")
                        + itemElementType.getValue() + " in LookupItemSet.");
                }

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        if (jaxbLookupItemSet.getMaximumItemsCount() != null) {

            svcLookupItemSetInitiationData.setMaximumItemsCount(jaxbLookupItemSet.getMaximumItemsCount());

        }

        if (jaxbLookupItemSet.getNextItemToken() != null) {

            svcLookupItemSetInitiationData.setNextItemToken(jaxbLookupItemSet.getNextItemToken());

        }

        return svcLookupItemSetInitiationData;

    }

    protected org.extensiblecatalog.ncip.v2.service.LookupItemSetResponseData createLookupItemSetResponseData(
        LookupItemSetResponse jaxbLookupItemSetResponse) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.LookupItemSetResponseData svcLookupItemSetResponseData =
            new org.extensiblecatalog.ncip.v2.service.LookupItemSetResponseData();

        svcLookupItemSetResponseData.setResponseHeader(createResponseHeader(
            jaxbLookupItemSetResponse.getResponseHeader()));

        if (jaxbLookupItemSetResponse.getBibInformation() != null && jaxbLookupItemSetResponse.getBibInformation().size() > 0) {

            svcLookupItemSetResponseData.setBibInformations(createSVCBibInformations(jaxbLookupItemSetResponse.getBibInformation()));
            svcLookupItemSetResponseData.setNextItemToken(jaxbLookupItemSetResponse.getNextItemToken());

        } else if (jaxbLookupItemSetResponse.getProblem() != null && jaxbLookupItemSetResponse.getProblem().size() > 0) {

            svcLookupItemSetResponseData.setProblems(
                createSVCProblems(jaxbLookupItemSetResponse.getProblem()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "BibInformation or Problem must be non-null in LookupItemSetResponse.");

        }

        return svcLookupItemSetResponseData;

    }

    protected List<org.extensiblecatalog.ncip.v2.service.BibInformation> createSVCBibInformations(List<BibInformation> jaxbBibInformations) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.BibInformation> svcBibInformations = new ArrayList<org.extensiblecatalog.ncip.v2.service.BibInformation>();

        if (jaxbBibInformations != null && jaxbBibInformations.size() > 0) {

            for (BibInformation jaxbBibInformation : jaxbBibInformations) {

                // TODO: Should we be testing for null returns?
                svcBibInformations.add(createBibInformation(jaxbBibInformation));

            }

        }

        return svcBibInformations;

    }

    protected org.extensiblecatalog.ncip.v2.service.BibInformation createBibInformation(BibInformation jaxbBibInformation)
       throws BindingException {

       org.extensiblecatalog.ncip.v2.service.BibInformation svcBibInformation = null;

       if ( jaxbBibInformation != null) {

           svcBibInformation = new org.extensiblecatalog.ncip.v2.service.BibInformation();
           svcBibInformation.setBibliographicId(createBibliographicId(jaxbBibInformation.getBibliographicId()));

           if (jaxbBibInformation.getHoldingsSet() != null && jaxbBibInformation.getHoldingsSet().size() > 0) {

               svcBibInformation.setBibliographicDescription(createBibliographicDescription(jaxbBibInformation.getBibliographicDescription()));
               svcBibInformation.setTitleHoldQueueLength(jaxbBibInformation.getTitleHoldQueueLength());

               if (jaxbBibInformation.getCurrentRequester() != null && jaxbBibInformation.getCurrentRequester().size() > 0) {

                   svcBibInformation.setCurrentRequesters(createSVCCurrentRequesters(jaxbBibInformation.getCurrentRequester()));

               }

               svcBibInformation.setHoldingsSets(createSVCHoldingsSets(jaxbBibInformation.getHoldingsSet()));

           } else if ( jaxbBibInformation.getProblem() != null && jaxbBibInformation.getProblem().size() > 0) {

               svcBibInformation.setProblems(createSVCProblems(jaxbBibInformation.getProblem()));

           } else {

               throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                   "HoldingsSet or Problem must be non-null in BibInformation.");

           }

       }

       return svcBibInformation;

   }

    protected List<org.extensiblecatalog.ncip.v2.service.HoldingsSet> createSVCHoldingsSets(List<HoldingsSet> jaxbHoldingsSets) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.HoldingsSet> svcHoldingsSets
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.HoldingsSet>();

        if (jaxbHoldingsSets != null && jaxbHoldingsSets.size() > 0) {

            for (HoldingsSet jaxbHoldingsSet : jaxbHoldingsSets) {

                // TODO: Should we be testing for null returns?
                svcHoldingsSets.add(createHoldingsSet(jaxbHoldingsSet));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "List<HoldingsSet> must be non-null and non-empty"
                    + " in createSVCItemSets method.");

        }

        return svcHoldingsSets;

    }

    protected org.extensiblecatalog.ncip.v2.service.HoldingsSet createHoldingsSet(HoldingsSet jaxbHoldingsSet) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.HoldingsSet svcHoldingsSet = null;

        if (jaxbHoldingsSet != null) {

            svcHoldingsSet = new org.extensiblecatalog.ncip.v2.service.HoldingsSet();
            svcHoldingsSet.setHoldingsSetId(jaxbHoldingsSet.getHoldingsSetId());

            if (jaxbHoldingsSet.getItemInformation() != null && jaxbHoldingsSet.getItemInformation().size() > 0) {

                svcHoldingsSet.setBibliographicDescription(
                    createBibliographicDescription(jaxbHoldingsSet.getBibliographicDescription()));
                svcHoldingsSet.setLocation(createLocation(jaxbHoldingsSet.getLocation()));
                svcHoldingsSet.setCallNumber(jaxbHoldingsSet.getCallNumber());
                svcHoldingsSet.setSummaryHoldingsInformation(createSummaryHoldingsInformation(jaxbHoldingsSet.getSummaryHoldingsInformation()));
                svcHoldingsSet.setElectronicResource(createElectronicResource(jaxbHoldingsSet.getElectronicResource()));

                if (jaxbHoldingsSet.getItemInformation() != null && jaxbHoldingsSet.getItemInformation().size() > 0) {

                    svcHoldingsSet.setItemInformations(createSVCItemInformations(jaxbHoldingsSet.getItemInformation()));

                }

            } else if ( jaxbHoldingsSet.getProblem() != null && jaxbHoldingsSet.getProblem().size() > 0) {

                svcHoldingsSet.setProblems(createSVCProblems(jaxbHoldingsSet.getProblem()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "ItemInformation or Problem must be non-null in HoldingsSet.");

            }

        }

        return svcHoldingsSet;

    }

    protected List<org.extensiblecatalog.ncip.v2.service.ItemInformation> createSVCItemInformations(List<ItemInformation> jaxbItemInformations) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.ItemInformation> svcItemInformations
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.ItemInformation>();

        if (jaxbItemInformations != null && jaxbItemInformations.size() > 0) {

            for (ItemInformation jaxbItemInformation : jaxbItemInformations) {

                // TODO: Should we be testing for null returns?
                svcItemInformations.add(createItemInformation(jaxbItemInformation));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "List<ItemInformation> must be non-null and non-empty"
                    + " in createSVCItemInformations method.");

        }

        return svcItemInformations;

    }

    protected org.extensiblecatalog.ncip.v2.service.ItemInformation createItemInformation(ItemInformation jaxbItemInformation) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ItemInformation svcItemInformation = null;

        if (jaxbItemInformation != null ) {

            svcItemInformation = new org.extensiblecatalog.ncip.v2.service.ItemInformation();

            svcItemInformation.setItemId(createItemId(jaxbItemInformation.getItemId()));

            if ((jaxbItemInformation.getRequestId() != null && jaxbItemInformation.getRequestId().size() >0)
                || jaxbItemInformation.getCurrentBorrower() != null
                || (jaxbItemInformation.getCurrentRequester() != null && jaxbItemInformation.getCurrentRequester().size() > 0)
                || jaxbItemInformation.getDateDue() != null || jaxbItemInformation.getHoldPickupDate() != null || jaxbItemInformation.getDateRecalled() != null
                || jaxbItemInformation.getItemTransaction() != null || jaxbItemInformation.getItemOptionalFields() != null
                || jaxbItemInformation.getItemNote() != null) {

                svcItemInformation.setRequestIds(createSVCRequestIds(jaxbItemInformation.getRequestId()));
                svcItemInformation.setCurrentBorrower(createCurrentBorrower(jaxbItemInformation.getCurrentBorrower()));
                svcItemInformation.setCurrentRequesters(createSVCCurrentRequesters(jaxbItemInformation.getCurrentRequester()));
                svcItemInformation.setDateDue(convertDate(jaxbItemInformation.getDateDue()));
                svcItemInformation.setHoldPickupDate(convertDate(jaxbItemInformation.getHoldPickupDate()));
                svcItemInformation.setDateRecalled(convertDate(jaxbItemInformation.getDateRecalled()));
                svcItemInformation.setItemTransaction(createItemTransaction(jaxbItemInformation.getItemTransaction()));
                svcItemInformation.setItemOptionalFields(createItemOptionalFields(jaxbItemInformation.getItemOptionalFields()));
                svcItemInformation.setItemNote(jaxbItemInformation.getItemNote());

            } else if (jaxbItemInformation.getProblem() != null) {

                svcItemInformation.setProblems(createSVCProblems(jaxbItemInformation.getProblem()));

            }
        }

        return svcItemInformation;

    }

    protected org.extensiblecatalog.ncip.v2.service.RequestItemInitiationData createRequestItemInitiationData(
        RequestItem jaxbRequestItem)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.RequestItemInitiationData svcRequestItem =
            new org.extensiblecatalog.ncip.v2.service.RequestItemInitiationData();

/*
        svcRequestItem.setInitiationHeader(createInitiationHeader(jaxbRequestItem.getInitiationHeader()));

        svcRequestItem.setMandatedAction(createMandatedAction(jaxbRequestItem.getMandatedAction()));

        if (jaxbRequestItem.getUserId() != null) {

            svcRequestItem.setUserId(createUserId(jaxbRequestItem.getUserId()));

        } else if (jaxbRequestItem.getAuthenticationInput() != null) {

            svcRequestItem.setAuthenticationInputs(createSVCAuthenticationInputs(
                jaxbRequestItem.getAuthenticationInput()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "UserId or AuthenticationInput must be non-null in RequestItem.");

        }

        if (jaxbRequestItem.getItemId() != null) {

            svcRequestItem.setItemId(createItemId(jaxbRequestItem.getItemId()));

        } else if (jaxbRequestItem.getBibliographicId() != null) {

            svcRequestItem.setBibliographicId(createBibliographicId(jaxbRequestItem.getBibliographicId()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "ItemId or RequestId must be non-null in Request Item.");

        }

        svcRequestItem.setRequestId(createRequestId(jaxbRequestItem.getRequestId()));

        if (jaxbRequestItem.getRequestScopeType() != null) {

            svcRequestItem.setRequestScopeType(convertRequestScopeType(jaxbRequestItem.getRequestScopeType()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "RequestScopeType must be non-null in RequestItem.");

        }

        if (jaxbRequestItem.getRequestType() != null) {

            svcRequestItem.setRequestType(convertRequestType(jaxbRequestItem.getRequestType()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "RequestType must be non-null in RequestItem.");

        }

        svcRequestItem.setItemOptionalFields(createItemOptionalFields(jaxbRequestItem.getItemOptionalFields()));

        svcRequestItem.setShippingInformation(createShippingInformation(jaxbRequestItem.getShippingInformation()));

        svcRequestItem.setEarliestDateNeeded(convertDate(jaxbRequestItem.getEarliestDateNeeded()));

        svcRequestItem.setNeedBeforeDate(convertDate(jaxbRequestItem.getNeedBeforeDate()));

        svcRequestItem.setPickupLocation(convertPickupLocation(jaxbRequestItem.getPickupLocation()));

        svcRequestItem.setPickupExpiryDate(convertDate(jaxbRequestItem.getPickupExpiryDate()));

        svcRequestItem.setAcknowledgedFeeAmount(createAcknowledgedFeeAmount(
            jaxbRequestItem.getAcknowledgedFeeAmount()));

        svcRequestItem.setPaidFeeAmount(createPaidFeeAmount(jaxbRequestItem.getPaidFeeAmount()));

        if (jaxbRequestItem.getAcknowledgedItemUseRestrictionType() != null
            && jaxbRequestItem.getAcknowledgedItemUseRestrictionType().size() > 0) {

            svcRequestItem.setAcknowledgedItemUseRestrictionTypes(
                convertItemUseRestrictionTypes(jaxbRequestItem.getAcknowledgedItemUseRestrictionType()));

        }

        if (jaxbRequestItem.getItemElementType() != null
            && jaxbRequestItem.getItemElementType().size() > 0) {

            List<SchemeValuePair> itemElementTypes = jaxbRequestItem.getItemElementType();
            for (SchemeValuePair svp : itemElementTypes) {

                try {

                    org.extensiblecatalog.ncip.v2.service.ItemElementType itemElementType =
                        org.extensiblecatalog.ncip.v2.service.ItemElementType.find(svp.getScheme(), svp.getValue());

                    if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .BIBLIOGRAPHIC_DESCRIPTION)) {
                        svcRequestItem.setBibliographicDescriptionDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .CIRCULATION_STATUS)) {
                        svcRequestItem.setCirculationStatusDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .ELECTRONIC_RESOURCE)) {
                        svcRequestItem.setElectronicResourceDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .HOLD_QUEUE_LENGTH)) {
                        svcRequestItem.setHoldQueueLengthDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .ITEM_DESCRIPTION)) {
                        svcRequestItem.setItemDescriptionDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .ITEM_USE_RESTRICTION_TYPE)) {
                        svcRequestItem.setItemUseRestrictionTypeDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .LOCATION)) {
                        svcRequestItem.setLocationDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .PHYSICAL_CONDITION)) {
                        svcRequestItem.setPhysicalConditionDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .SECURITY_MARKER)) {
                        svcRequestItem.setSecurityMarkerDesired(true);
                    } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                        .SENSITIZATION_FLAG)) {
                        svcRequestItem.setSensitizationFlagDesired(true);
                    } else {
                        throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected enumeration value "
                            + (itemElementType.getScheme() != null ? itemElementType.getScheme() + " " : " ")
                            + itemElementType.getValue() + " in RequestItem.");
                    }

                } catch (ServiceException e) {

                    throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                }
            }

        }

        if (jaxbRequestItem.getUserElementType() != null
            && jaxbRequestItem.getUserElementType().size() > 0) {

            List<SchemeValuePair> userElementTypes = jaxbRequestItem.getUserElementType();
            for (SchemeValuePair svp : userElementTypes) {

                try {

                    org.extensiblecatalog.ncip.v2.service.UserElementType userElementType =
                        org.extensiblecatalog.ncip.v2.service.UserElementType.find(svp.getScheme(), svp.getValue());

                    if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .AUTHENTICATION_INPUT)) {
                        svcRequestItem.setAuthenticationInputDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .BLOCK_OR_TRAP)) {
                        svcRequestItem.setBlockOrTrapDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .DATE_OF_BIRTH)) {
                        svcRequestItem.setDateOfBirthDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .NAME_INFORMATION)) {
                        svcRequestItem.setNameInformationDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_ADDRESS_INFORMATION)) {
                        svcRequestItem.setUserAddressInformationDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_LANGUAGE)) {
                        svcRequestItem.setUserLanguageDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_PRIVILEGE)) {
                        svcRequestItem.setUserPrivilegeDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_ID)) {
                        svcRequestItem.setUserIdDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .PREVIOUS_USER_ID)) {
                        svcRequestItem.setPreviousUserIdDesired(true);
                    } else {
                        throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected enumeration value "
                            + (userElementType.getScheme() != null ? userElementType.getScheme() + " " : " ")
                            + userElementType.getValue() + " in INIT_MSG_NAME.");
                    }

                } catch (ServiceException e) {

                    throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                }
            }

        }
*/

        return svcRequestItem;
    }

    protected org.extensiblecatalog.ncip.v2.service.BibliographicId createBibliographicId(
        BibliographicId jaxbBibliographicId) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.BibliographicId svcBibliographicId = null;

        if (jaxbBibliographicId != null) {

            svcBibliographicId = new org.extensiblecatalog.ncip.v2.service.BibliographicId();

            if (jaxbBibliographicId.getBibliographicItemId() != null) {

                svcBibliographicId.setBibliographicItemId(
                    createBibliographicItemId(jaxbBibliographicId.getBibliographicItemId()));

            } else if (jaxbBibliographicId.getBibliographicRecordId() != null) {

                svcBibliographicId.setBibliographicRecordId(
                    createBibliographicRecordId(jaxbBibliographicId.getBibliographicRecordId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "BibliographicItemId or BibliographicRecordId must be non-null in BibliographicId.");

            }

        }

        return svcBibliographicId;

    }

    protected List<org.extensiblecatalog.ncip.v2.service.BibliographicId> createSVCBibliographicIds(List<BibliographicId> jaxbBibliographicIds) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.BibliographicId> svcBibliographicIds = null;

        if (jaxbBibliographicIds != null && jaxbBibliographicIds.size() > 0) {

            svcBibliographicIds = new ArrayList<org.extensiblecatalog.ncip.v2.service.BibliographicId>();

            for (BibliographicId jaxbBibliographicId : jaxbBibliographicIds) {

                svcBibliographicIds.add(createBibliographicId(jaxbBibliographicId));

            }
        }

        return svcBibliographicIds;

    }

    protected org.extensiblecatalog.ncip.v2.service.AcknowledgedFeeAmount createAcknowledgedFeeAmount(
        AcknowledgedFeeAmount jaxbAcknowledgedFeeAmount) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.AcknowledgedFeeAmount svcAcknowledgedFeeAmount = null;

        if (jaxbAcknowledgedFeeAmount != null) {

            svcAcknowledgedFeeAmount = new org.extensiblecatalog.ncip.v2.service.AcknowledgedFeeAmount();

            svcAcknowledgedFeeAmount.setCurrencyCode(convertCurrencyCode(jaxbAcknowledgedFeeAmount.getCurrencyCode()));

            svcAcknowledgedFeeAmount.setMonetaryValue(jaxbAcknowledgedFeeAmount.getMonetaryValue());

        }

        return svcAcknowledgedFeeAmount;

    }

    protected org.extensiblecatalog.ncip.v2.service.PaidFeeAmount createPaidFeeAmount(
        PaidFeeAmount jaxbPaidFeeAmount) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.PaidFeeAmount svcPaidFeeAmount = null;

        if (jaxbPaidFeeAmount != null) {

            svcPaidFeeAmount = new org.extensiblecatalog.ncip.v2.service.PaidFeeAmount();

            svcPaidFeeAmount.setCurrencyCode(convertCurrencyCode(jaxbPaidFeeAmount.getCurrencyCode()));

            svcPaidFeeAmount.setMonetaryValue(jaxbPaidFeeAmount.getMonetaryValue());

        }

        return svcPaidFeeAmount;

    }

    protected org.extensiblecatalog.ncip.v2.service.ShippingInformation createShippingInformation(
        ShippingInformation jaxbShippingInformation) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ShippingInformation svcShippingInformation = null;

        if (jaxbShippingInformation != null) {

            svcShippingInformation = new org.extensiblecatalog.ncip.v2.service.ShippingInformation();

            svcShippingInformation.setShippingInstructions(jaxbShippingInformation.getShippingInstructions());

            svcShippingInformation.setShippingNote(jaxbShippingInformation.getShippingNote());

            if (jaxbShippingInformation.getPhysicalAddress() != null) {

                svcShippingInformation.setPhysicalAddress(createPhysicalAddress(
                    jaxbShippingInformation.getPhysicalAddress()));

            } else if (jaxbShippingInformation.getElectronicAddress() != null) {

                svcShippingInformation.setElectronicAddress(createElectronicAddress(
                    jaxbShippingInformation.getElectronicAddress()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "PhysicalAddress or ElectronicAddress must be non-null in ShippingInformation.");

            }

        }

        return svcShippingInformation;

    }

    protected org.extensiblecatalog.ncip.v2.service.PhysicalAddress createPhysicalAddress(
        PhysicalAddress jaxbPhysicalAddress) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.PhysicalAddress svcPhysicalAddress = null;

        if (jaxbPhysicalAddress != null) {

            svcPhysicalAddress = new org.extensiblecatalog.ncip.v2.service.PhysicalAddress();

            if (jaxbPhysicalAddress.getStructuredAddress() != null) {

                svcPhysicalAddress.setStructuredAddress(createStructuredAddress(
                    jaxbPhysicalAddress.getStructuredAddress()));

            } else if (jaxbPhysicalAddress.getUnstructuredAddress() != null) {

                svcPhysicalAddress.setUnstructuredAddress(createUnstructuredAddress(
                    jaxbPhysicalAddress.getUnstructuredAddress()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "StructuredAddress or UnstructuredAddress must be non-null in PhysicalAddress.");

            }

            svcPhysicalAddress.setPhysicalAddressType(convertPhysicalAddressType(jaxbPhysicalAddress.getPhysicalAddressType()));

        }

        return svcPhysicalAddress;

    }

    protected org.extensiblecatalog.ncip.v2.service.UnstructuredAddressType convertUnstructuredAddressType(
        SchemeValuePair jaxbUnstructuredAddressType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.UnstructuredAddressType svcUnstructuredAddressType = null;

        if (jaxbUnstructuredAddressType != null) {

            try {

                svcUnstructuredAddressType = org.extensiblecatalog.ncip.v2.service.UnstructuredAddressType.find(
                    jaxbUnstructuredAddressType.getScheme(), jaxbUnstructuredAddressType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcUnstructuredAddressType;
    }

    protected org.extensiblecatalog.ncip.v2.service.PhysicalAddressType convertPhysicalAddressType(
        SchemeValuePair jaxbPhysicalAddressType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.PhysicalAddressType svcPhysicalAddressType = null;

        if (jaxbPhysicalAddressType != null) {

            try {

                svcPhysicalAddressType = org.extensiblecatalog.ncip.v2.service.PhysicalAddressType.find(
                    jaxbPhysicalAddressType.getScheme(), jaxbPhysicalAddressType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcPhysicalAddressType;
    }

    protected org.extensiblecatalog.ncip.v2.service.StructuredAddress createStructuredAddress(
        StructuredAddress jaxbStructuredAddress) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.StructuredAddress svcStructuredAddress = null;

        if (jaxbStructuredAddress != null && jaxbStructuredAddress.getContent() != null) {

            svcStructuredAddress = new org.extensiblecatalog.ncip.v2.service.StructuredAddress();

            String locationWithinBuilding = null;
            String houseName = null;
            String street = null;
            String postOfficeBox = null;
            String district = null;
            String line1 = null;
            String line2 = null;
            String locality = null;
            String region = null;
            String country = null;
            String postalCode = null;
            String careOf = null;

            for (Object object : jaxbStructuredAddress.getContent()) {

                if (object instanceof JAXBElement) {

                    JAXBElement jaxbElement = (JAXBElement)object;

                    if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("LocationWithinBuilding") == 0) {

                        locationWithinBuilding = (String)jaxbElement.getValue();

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("HouseName") == 0) {

                        houseName = (String)jaxbElement.getValue();

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("Street") == 0) {

                        street = (String)jaxbElement.getValue();

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("PostOfficeBox") == 0) {

                        postOfficeBox = (String)jaxbElement.getValue();

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("District") == 0) {

                        district = (String)jaxbElement.getValue();

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("Line1") == 0) {

                        line1 = (String)jaxbElement.getValue();

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("Line2") == 0) {

                        line2 = (String)jaxbElement.getValue();

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("Locality") == 0) {

                        locality = (String)jaxbElement.getValue();

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("Region") == 0) {

                        region = (String)jaxbElement.getValue();

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("Country") == 0) {

                        country = (String)jaxbElement.getValue();

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("PostalCode") == 0) {

                        postalCode = (String)jaxbElement.getValue();

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("CareOf") == 0) {

                        careOf = (String)jaxbElement.getValue();

                    } else {

                        throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected element "
                            + jaxbElement.getName().getLocalPart() + " in StructuredAddress.");

                    }
                } else {

                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected object of class "
                        + object.getClass().getName() + " in StructuredAddress.");

                }
            }

            if (district != null || postOfficeBox != null || street != null) {

                svcStructuredAddress.setLocationWithinBuilding(locationWithinBuilding);

                svcStructuredAddress.setHouseName(houseName);

                svcStructuredAddress.setDistrict(district);

                svcStructuredAddress.setPostOfficeBox(postOfficeBox);

                svcStructuredAddress.setStreet(street);

            } else if (line1 != null) {

                svcStructuredAddress.setLine1(line1);
                svcStructuredAddress.setLine1(line2);

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Either one of District, PostOfficeBox or Street,"
                        + " or Line1 must be non-null in StructuredAddress.");

            }

            svcStructuredAddress.setLocality(locality);

            svcStructuredAddress.setRegion(region);

            svcStructuredAddress.setCountry(country);

            svcStructuredAddress.setPostalCode(postalCode);

            svcStructuredAddress.setCareOf(careOf);
        }

        return svcStructuredAddress;

    }

    protected org.extensiblecatalog.ncip.v2.service.UnstructuredAddress createUnstructuredAddress(
        UnstructuredAddress jaxbUnstructuredAddress) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.UnstructuredAddress svcUnstructuredAddress = null;

        if (jaxbUnstructuredAddress != null) {

            svcUnstructuredAddress = new org.extensiblecatalog.ncip.v2.service.UnstructuredAddress();

            svcUnstructuredAddress.setUnstructuredAddressType(convertUnstructuredAddressType(
                jaxbUnstructuredAddress.getUnstructuredAddressType()));

            svcUnstructuredAddress.setUnstructuredAddressData(jaxbUnstructuredAddress.getUnstructuredAddressData());

        }

        return svcUnstructuredAddress;

    }

    protected org.extensiblecatalog.ncip.v2.service.ElectronicAddress createElectronicAddress(
        ElectronicAddress jaxbElectronicAddress) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ElectronicAddress svcElectronicAddress = null;

        if (jaxbElectronicAddress != null) {

            svcElectronicAddress = new org.extensiblecatalog.ncip.v2.service.ElectronicAddress();

            svcElectronicAddress.setElectronicAddressType(convertElectronicAddressType(jaxbElectronicAddress.getElectronicAddressType()));

            svcElectronicAddress.setElectronicAddressData(jaxbElectronicAddress.getElectronicAddressData());

        }

        return svcElectronicAddress;

    }

    protected org.extensiblecatalog.ncip.v2.service.ElectronicAddressType convertElectronicAddressType(
        SchemeValuePair jaxbElectronicAddressType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ElectronicAddressType svcElectronicAddressType = null;

        if (jaxbElectronicAddressType != null) {

            try {

                svcElectronicAddressType = org.extensiblecatalog.ncip.v2.service.ElectronicAddressType.find(
                    jaxbElectronicAddressType.getScheme(), jaxbElectronicAddressType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcElectronicAddressType;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.AuthenticationInput> createSVCAuthenticationInputs(
        List<AuthenticationInput> jaxbAuthenticationInputs) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.AuthenticationInput> svcAuthenticationInputs
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.AuthenticationInput>();

        if (jaxbAuthenticationInputs != null && jaxbAuthenticationInputs.size() > 0) {

            for (AuthenticationInput jaxbAuthenticationInput : jaxbAuthenticationInputs) {

                // TODO: Should we be testing for null returns?
                svcAuthenticationInputs.add(createAuthenticationInput(jaxbAuthenticationInput));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "List<AuthenticationInput> must be non-null and non-empty"
                    + " in createSVCAuthenticationInputs method.");

        }

        return svcAuthenticationInputs;

    }

    protected org.extensiblecatalog.ncip.v2.service.AuthenticationInput createAuthenticationInput(
        AuthenticationInput jaxbAuthenticationInput) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.AuthenticationInput svcAuthenticationInput = null;

        if ( jaxbAuthenticationInput != null) {

            if (jaxbAuthenticationInput.getAuthenticationInputData() != null
                && jaxbAuthenticationInput.getAuthenticationDataFormatType() != null
                && jaxbAuthenticationInput.getAuthenticationInputType() != null) {

                svcAuthenticationInput = new org.extensiblecatalog.ncip.v2.service.AuthenticationInput();

                svcAuthenticationInput.setAuthenticationInputData(
                    jaxbAuthenticationInput.getAuthenticationInputData());
                svcAuthenticationInput.setAuthenticationDataFormatType(
                    convertAuthenticationDataFormatType(jaxbAuthenticationInput.getAuthenticationDataFormatType()));
                svcAuthenticationInput.setAuthenticationInputType(
                    convertAuthenticationInputType(jaxbAuthenticationInput.getAuthenticationInputType()));

            }

        }

        return svcAuthenticationInput;

    }

    protected org.extensiblecatalog.ncip.v2.service.AuthenticationInputType convertAuthenticationInputType(
        SchemeValuePair jaxbAuthenticationInputType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.AuthenticationInputType svcAuthenticationInputType = null;

        if (jaxbAuthenticationInputType != null) {

            try {

                svcAuthenticationInputType = org.extensiblecatalog.ncip.v2.service.AuthenticationInputType.find(
                    jaxbAuthenticationInputType.getScheme(), jaxbAuthenticationInputType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcAuthenticationInputType;
    }
    
    protected org.extensiblecatalog.ncip.v2.service.AuthenticationDataFormatType convertAuthenticationDataFormatType(
        SchemeValuePair jaxbAuthenticationDataFormatType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.AuthenticationDataFormatType svcAuthenticationDataFormatType = null;

        if (jaxbAuthenticationDataFormatType != null) {

            try {

                svcAuthenticationDataFormatType = org.extensiblecatalog.ncip.v2.service.AuthenticationDataFormatType.find(
                    jaxbAuthenticationDataFormatType.getScheme(), jaxbAuthenticationDataFormatType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcAuthenticationDataFormatType;
    }

    protected org.extensiblecatalog.ncip.v2.service.MandatedAction createMandatedAction(
        MandatedAction jaxbMandatedAction) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.MandatedAction svcMandatedAction = null;

        if (jaxbMandatedAction != null) {

            svcMandatedAction = new org.extensiblecatalog.ncip.v2.service.MandatedAction();

            if (jaxbMandatedAction.getDateEventOccurred() != null) {

                svcMandatedAction.setDateEventOccurred(convertDate(jaxbMandatedAction.getDateEventOccurred()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "DateEventOccurred must be non-null in MandatedAction.");

            }

        }

        return svcMandatedAction;

    }

    protected org.extensiblecatalog.ncip.v2.service.CheckInItemResponseData createCheckInItemResponseData(
        CheckInItemResponse jaxbCheckoutItemResponseMsg) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.CheckInItemResponseData svcCheckInItemResponseData =
            new org.extensiblecatalog.ncip.v2.service.CheckInItemResponseData();

        if (jaxbCheckoutItemResponseMsg.getResponseHeader() != null) {

            svcCheckInItemResponseData.setResponseHeader(createResponseHeader(
                jaxbCheckoutItemResponseMsg.getResponseHeader()));

        }

        if (jaxbCheckoutItemResponseMsg.getProblem() != null && jaxbCheckoutItemResponseMsg.getProblem().size() > 0) {

            svcCheckInItemResponseData.setProblems(
                createSVCProblems(jaxbCheckoutItemResponseMsg.getProblem()));

        } else {

            if (jaxbCheckoutItemResponseMsg.getItemId() != null) {

                svcCheckInItemResponseData.setItemId(createItemId(jaxbCheckoutItemResponseMsg.getItemId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "ItemId must be non-null in CheckInItemResponse.");

            }

            if (jaxbCheckoutItemResponseMsg.getUserId() != null) {

                svcCheckInItemResponseData.setUserId(createUserId(jaxbCheckoutItemResponseMsg.getUserId()));

            }

            svcCheckInItemResponseData.setRoutingInformation(
                createRoutingInformation(jaxbCheckoutItemResponseMsg.getRoutingInformation()));

            svcCheckInItemResponseData.setFiscalTransactionInformation(
                createFiscalTransactionInformation(jaxbCheckoutItemResponseMsg.getFiscalTransactionInformation()));

            svcCheckInItemResponseData.setItemOptionalFields(createItemOptionalFields(
                jaxbCheckoutItemResponseMsg.getItemOptionalFields()));

            svcCheckInItemResponseData.setUserOptionalFields(createUserOptionalFields(
                jaxbCheckoutItemResponseMsg.getUserOptionalFields()));

        }

        return svcCheckInItemResponseData;
    }

    protected org.extensiblecatalog.ncip.v2.service.RoutingInformation createRoutingInformation(
        org.extensiblecatalog.ncip.v2.binding.jaxb.elements.RoutingInformation jaxbRoutingInformation)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.RoutingInformation svcRoutingInformation = null;

        if (jaxbRoutingInformation != null) {

            svcRoutingInformation = new org.extensiblecatalog.ncip.v2.service.RoutingInformation();

            if (jaxbRoutingInformation.getRoutingInstructions() != null) {

                svcRoutingInformation.setRoutingInstructions(
                    jaxbRoutingInformation.getRoutingInstructions());

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "RoutingInstructions must be non-null in RoutingInformation.");
            }

            if (jaxbRoutingInformation.getDestination() != null) {

                svcRoutingInformation.setDestination(
                    createDestination(jaxbRoutingInformation.getDestination()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Destination must be non-null in RoutingInformation.");
            }

            if (jaxbRoutingInformation.getRequestType() != null) {

                svcRoutingInformation.setRequestType(
                    convertRequestType(jaxbRoutingInformation.getRequestType()));

            }

            if (jaxbRoutingInformation.getUserId() != null) {

                svcRoutingInformation.setUserId(
                    createUserId(jaxbRoutingInformation.getUserId()));

            }

            if (jaxbRoutingInformation.getNameInformation() != null) {

                svcRoutingInformation.setNameInformation(
                    createNameInformation(jaxbRoutingInformation.getNameInformation()));

            }

        }

        return svcRoutingInformation;

    }

    protected org.extensiblecatalog.ncip.v2.service.Destination createDestination(
        org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Destination jaxbDestination) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.Destination svcDestination = null;

        if (jaxbDestination != null && jaxbDestination.getContent() != null) {

            svcDestination = new org.extensiblecatalog.ncip.v2.service.Destination();

            org.extensiblecatalog.ncip.v2.service.Location location = null;
            String binNumber = null;

            for (Object object : jaxbDestination.getContent()) {

                if (object instanceof JAXBElement) {

                    JAXBElement jaxbElement = (JAXBElement)object;

                    if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("Location") == 0) {

                        location = createLocation((Location)jaxbElement.getValue());

                    } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("BinNumber") == 0) {

                        binNumber = (String)jaxbElement.getValue();

                    } else {

                        throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected element "
                            + jaxbElement.getName().getLocalPart() + " in Destination.");

                    }
                } else {

                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected object of class "
                        + object.getClass().getName() + " in Destination.");

                }
            }

            if (location != null) {

                svcDestination.setLocation(location);
                svcDestination.setBinNumber(binNumber);

            } else if (binNumber != null) {

                svcDestination.setBinNumber(binNumber);

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Location, or BinNumber must be non-null in Destination.");

            }

        }

        return svcDestination;

    }

    protected org.extensiblecatalog.ncip.v2.service.AcceptItemResponseData createAcceptItemResponseData(
        AcceptItemResponse jaxbAcceptItemResponseMsg) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.AcceptItemResponseData svcAcceptItemResponseData =
            new org.extensiblecatalog.ncip.v2.service.AcceptItemResponseData();

        if (jaxbAcceptItemResponseMsg.getResponseHeader() != null) {

            svcAcceptItemResponseData.setResponseHeader(createResponseHeader(
                jaxbAcceptItemResponseMsg.getResponseHeader()));

        }

        if (jaxbAcceptItemResponseMsg.getProblem() != null && jaxbAcceptItemResponseMsg.getProblem().size() > 0) {

            svcAcceptItemResponseData.setProblems(
                createSVCProblems(jaxbAcceptItemResponseMsg.getProblem()));

        } else {

            if (jaxbAcceptItemResponseMsg.getRequestId() != null) {

                svcAcceptItemResponseData.setRequestId(createRequestId(jaxbAcceptItemResponseMsg.getRequestId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "RequestId must be non-null in AcceptItemResponse.");

            }

            if (jaxbAcceptItemResponseMsg.getItemId() != null) {

                svcAcceptItemResponseData.setItemId(createItemId(jaxbAcceptItemResponseMsg.getItemId()));

            }

        }

        return svcAcceptItemResponseData;
    }

    protected org.extensiblecatalog.ncip.v2.service.CheckOutItemResponseData createCheckOutItemResponseData(
        CheckOutItemResponse jaxbCheckoutItemResponseMsg) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.CheckOutItemResponseData svcCheckOutItemResponseData =
            new org.extensiblecatalog.ncip.v2.service.CheckOutItemResponseData();

        if (jaxbCheckoutItemResponseMsg.getResponseHeader() != null) {

            svcCheckOutItemResponseData.setResponseHeader(createResponseHeader(
                jaxbCheckoutItemResponseMsg.getResponseHeader()));

        }

        if (jaxbCheckoutItemResponseMsg.getProblem() != null && jaxbCheckoutItemResponseMsg.getProblem().size() > 0) {

            svcCheckOutItemResponseData.setProblems(
                createSVCProblems(jaxbCheckoutItemResponseMsg.getProblem()));

            if (jaxbCheckoutItemResponseMsg.getRequiredFeeAmount() != null) {

                svcCheckOutItemResponseData.setRequiredFeeAmount(
                    createRequiredFeeAmount(jaxbCheckoutItemResponseMsg.getRequiredFeeAmount()));

            }

            if (jaxbCheckoutItemResponseMsg.getRequiredItemUseRestrictionType() != null
                && jaxbCheckoutItemResponseMsg.getRequiredItemUseRestrictionType().size() > 0) {

                svcCheckOutItemResponseData.setRequiredItemUseRestrictionTypes(
                    convertItemUseRestrictionTypes(
                        jaxbCheckoutItemResponseMsg.getRequiredItemUseRestrictionType()));

            }

        } else {

            if (jaxbCheckoutItemResponseMsg.getItemId() != null) {

                svcCheckOutItemResponseData.setItemId(createItemId(jaxbCheckoutItemResponseMsg.getItemId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "ItemId must be non-null in CheckOutItemResponse.");

            }

            if (jaxbCheckoutItemResponseMsg.getUserId() != null) {

                svcCheckOutItemResponseData.setUserId(createUserId(jaxbCheckoutItemResponseMsg.getUserId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "UserId must be non-null in CheckOutItemResponse.");

            }

            if (jaxbCheckoutItemResponseMsg.getDateDue() != null) {

                svcCheckOutItemResponseData.setDateDue(convertDate(jaxbCheckoutItemResponseMsg.getDateDue()));

            } else if (jaxbCheckoutItemResponseMsg.getIndeterminateLoanPeriodFlag() != null) {

                svcCheckOutItemResponseData.setIndeterminateLoanPeriodFlag(
                    jaxbCheckoutItemResponseMsg.getIndeterminateLoanPeriodFlag() != null);

            } else if (jaxbCheckoutItemResponseMsg.getNonReturnableFlag() != null) {

                svcCheckOutItemResponseData.setNonReturnableFlag(
                    jaxbCheckoutItemResponseMsg.getNonReturnableFlag() != null);

            }


            svcCheckOutItemResponseData.setRenewalCount(jaxbCheckoutItemResponseMsg.getRenewalCount());

            svcCheckOutItemResponseData.setElectronicResource(
                createElectronicResource(jaxbCheckoutItemResponseMsg.getElectronicResource()));

            svcCheckOutItemResponseData.setFiscalTransactionInformation(
                createFiscalTransactionInformation(jaxbCheckoutItemResponseMsg.getFiscalTransactionInformation()));

            svcCheckOutItemResponseData.setItemOptionalFields(createItemOptionalFields(
                jaxbCheckoutItemResponseMsg.getItemOptionalFields()));

            svcCheckOutItemResponseData.setUserOptionalFields(createUserOptionalFields(
                jaxbCheckoutItemResponseMsg.getUserOptionalFields()));

        }

        return svcCheckOutItemResponseData;
    }

    protected org.extensiblecatalog.ncip.v2.service.RenewItemResponseData createRenewItemResponseData(
        RenewItemResponse jaxbRenewItemResponseMsg) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.RenewItemResponseData svcRenewItemResponseData =
            new org.extensiblecatalog.ncip.v2.service.RenewItemResponseData();

        if (jaxbRenewItemResponseMsg.getResponseHeader() != null) {

            svcRenewItemResponseData.setResponseHeader(createResponseHeader(
                jaxbRenewItemResponseMsg.getResponseHeader()));

        }

        if (jaxbRenewItemResponseMsg.getProblem() != null && jaxbRenewItemResponseMsg.getProblem().size() > 0) {

            svcRenewItemResponseData.setProblems(
                createSVCProblems(jaxbRenewItemResponseMsg.getProblem()));

            if (jaxbRenewItemResponseMsg.getRequiredFeeAmount() != null) {

                svcRenewItemResponseData.setRequiredFeeAmount(
                    createRequiredFeeAmount(jaxbRenewItemResponseMsg.getRequiredFeeAmount()));

            }

            if (jaxbRenewItemResponseMsg.getRequiredItemUseRestrictionType() != null
                && jaxbRenewItemResponseMsg.getRequiredItemUseRestrictionType().size() > 0) {

                svcRenewItemResponseData.setRequiredItemUseRestrictionTypes(
                    convertItemUseRestrictionTypes(
                        jaxbRenewItemResponseMsg.getRequiredItemUseRestrictionType()));

            }

        } else if (jaxbRenewItemResponseMsg.getPending() != null) {

            svcRenewItemResponseData.setPending(createPending(jaxbRenewItemResponseMsg.getPending()));

        } else if (jaxbRenewItemResponseMsg.getItemId() != null) {

            svcRenewItemResponseData.setItemId(createItemId(jaxbRenewItemResponseMsg.getItemId()));

            svcRenewItemResponseData.setUserId(createUserId(jaxbRenewItemResponseMsg.getUserId()));

            svcRenewItemResponseData.setDateDue(convertDate(jaxbRenewItemResponseMsg.getDateDue()));

            svcRenewItemResponseData.setDateForReturn(convertDate(jaxbRenewItemResponseMsg.getDateForReturn()));

            svcRenewItemResponseData.setRenewalCount(jaxbRenewItemResponseMsg.getRenewalCount());

            svcRenewItemResponseData.setFiscalTransactionInformation(
                createFiscalTransactionInformation(jaxbRenewItemResponseMsg.getFiscalTransactionInformation()));

            svcRenewItemResponseData.setItemOptionalFields(createItemOptionalFields(
                jaxbRenewItemResponseMsg.getItemOptionalFields()));

            svcRenewItemResponseData.setUserOptionalFields(createUserOptionalFields(
                jaxbRenewItemResponseMsg.getUserOptionalFields()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "Problem, Pending, or ItemId must be non-null in RenewItemResponse.");

        }

        return svcRenewItemResponseData;

    }

    protected org.extensiblecatalog.ncip.v2.service.Pending createPending(
        org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Pending jaxbPending)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.Pending svcPending = null;

        if ( jaxbPending != null ) {

            svcPending = new org.extensiblecatalog.ncip.v2.service.Pending();

            svcPending.setDateOfExpectedReply(convertDate(jaxbPending.getDateOfExpectedReply()));

        }

        return svcPending;

    }

    protected org.extensiblecatalog.ncip.v2.service.RequiredFeeAmount createRequiredFeeAmount(
        org.extensiblecatalog.ncip.v2.binding.jaxb.elements.RequiredFeeAmount jaxbRequiredFeeAmount)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.RequiredFeeAmount svcRequiredFeeAmount = null;

        if (jaxbRequiredFeeAmount != null) {

            svcRequiredFeeAmount = new org.extensiblecatalog.ncip.v2.service.RequiredFeeAmount();

            if (jaxbRequiredFeeAmount.getCurrencyCode() != null) {

                svcRequiredFeeAmount.setCurrencyCode(
                    convertCurrencyCode(jaxbRequiredFeeAmount.getCurrencyCode()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "CurrencyCode must be non-null in RequiredFeeAmount.");
            }

        }

        return svcRequiredFeeAmount;

    }

    protected org.extensiblecatalog.ncip.v2.service.RequestItemResponseData createRequestItemResponseData(
        RequestItemResponse requestItemResponseMsg) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.RequestItemResponseData requestItemResponseData =
            new org.extensiblecatalog.ncip.v2.service.RequestItemResponseData();

        // Set up the local variables to populate in the loop that iterates over the content list.
        org.extensiblecatalog.ncip.v2.service.ResponseHeader svcResponseHeader = null;
        List<org.extensiblecatalog.ncip.v2.service.Problem> svcProblems = null;
        org.extensiblecatalog.ncip.v2.service.RequiredFeeAmount svcRequiredFeeAmount = null;
        List<org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType> svcRequiredItemUseRestrictionTypes = null;
        org.extensiblecatalog.ncip.v2.service.ItemId svcItemId = null;
        org.extensiblecatalog.ncip.v2.service.RequestId svcRequestId = null;
        org.extensiblecatalog.ncip.v2.service.UserId svcUserId = null;
        org.extensiblecatalog.ncip.v2.service.RequestType svcRequestType = null;
        org.extensiblecatalog.ncip.v2.service.RequestScopeType svcRequestScopeType = null;
        org.extensiblecatalog.ncip.v2.service.ShippingInformation svcShippingInformation = null;
        GregorianCalendar svcDateAvailable = null;
        GregorianCalendar svcHoldPickupDate = null;
        org.extensiblecatalog.ncip.v2.service.FiscalTransactionInformation svcFiscalTransactionInformation = null;
        org.extensiblecatalog.ncip.v2.service.ItemOptionalFields svcItemOptionalFields = null;
        org.extensiblecatalog.ncip.v2.service.UserOptionalFields svcUserOptionalFields = null;

        // Iterate through the content list, populating local variables.
        for (Object obj : requestItemResponseMsg.getContent()) {

            if (obj instanceof ResponseHeader) {

                ResponseHeader jaxbResponseHeader = (ResponseHeader)obj;

                svcResponseHeader = createResponseHeader(jaxbResponseHeader);

            } else if (obj instanceof Problem) {

                Problem jaxbProblem = (Problem)obj;

                if (svcProblems == null) {

                    svcProblems = new ArrayList<org.extensiblecatalog.ncip.v2.service.Problem>();

                }

                svcProblems.add(createProblem(jaxbProblem));

            } else if (obj instanceof RequiredFeeAmount) {

                svcRequiredFeeAmount = createRequiredFeeAmount((RequiredFeeAmount)obj);

            } else if (obj instanceof ItemId) {

                svcItemId = createItemId((ItemId)obj);

            } else if (obj instanceof RequestId) {

                svcRequestId = createRequestId((RequestId)obj);

            } else if (obj instanceof UserId) {

                svcUserId = createUserId((UserId)obj);

            } else if (obj instanceof ShippingInformation) {

                svcShippingInformation = createShippingInformation((ShippingInformation)obj);

            } else if (obj instanceof JAXBElement) {

                JAXBElement jaxbElement = (JAXBElement)obj;

                if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("DateAvailable") == 0) {

                    svcDateAvailable = convertDate((XMLGregorianCalendar)(jaxbElement.getValue()));

                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("HoldPickupDate") == 0) {

                    svcHoldPickupDate = convertDate((XMLGregorianCalendar)(jaxbElement.getValue()));

                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase(
                    "RequiredItemUseRestrictionType") == 0) {

                    if (svcRequiredItemUseRestrictionTypes == null) {

                        svcRequiredItemUseRestrictionTypes
                            = new ArrayList<org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType>();

                    }

                    svcRequiredItemUseRestrictionTypes.add(
                        convertItemUseRestrictionType((SchemeValuePair)jaxbElement.getValue()));

                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("RequestType") == 0) {

                    svcRequestType = convertRequestType((SchemeValuePair)jaxbElement.getValue());

                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("RequestScopeType") == 0) {

                    svcRequestScopeType = convertRequestScopeType((SchemeValuePair)jaxbElement.getValue());

                } else {

                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected element name "
                        + jaxbElement.getName().getLocalPart() + " in RequestItemResponse.");

                }

            } else if (obj instanceof FiscalTransactionInformation) {

                svcFiscalTransactionInformation = createFiscalTransactionInformation(
                    (FiscalTransactionInformation)obj);

            } else if (obj instanceof ItemOptionalFields) {

                svcItemOptionalFields = createItemOptionalFields((ItemOptionalFields)obj);

            } else if (obj instanceof UserOptionalFields) {

                svcUserOptionalFields = createUserOptionalFields((UserOptionalFields)obj);

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected object class "
                    + obj.getClass().getName() + " in RequestItemResponse.");

            }

        }

        if (svcProblems != null && svcProblems.size() > 0) {

            requestItemResponseData.setResponseHeader(svcResponseHeader);

            requestItemResponseData.setProblems(svcProblems);

            if (svcRequiredFeeAmount != null) {

                requestItemResponseData.setRequiredFeeAmount(svcRequiredFeeAmount);

            }

            if (svcRequiredItemUseRestrictionTypes != null && svcRequiredItemUseRestrictionTypes.size() > 0) {

                requestItemResponseData.setRequiredItemUseRestrictionTypes(svcRequiredItemUseRestrictionTypes);

            }

        } else {

            requestItemResponseData.setResponseHeader(svcResponseHeader);

            if (svcItemId != null) {

                requestItemResponseData.setItemId(svcItemId);

                if (svcRequestId != null) {

                    requestItemResponseData.setRequestId(svcRequestId);

                }

            } else if (svcRequestId != null) {

                requestItemResponseData.setRequestId(svcRequestId);

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Problem, ItemId or RequestId must be non-null in RequestItem.");

            }

            if (svcUserId != null) {

                requestItemResponseData.setUserId(svcUserId);

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "If ItemId or RequestId is non-null, then UserId must be non-null in RequestItem.");

            }

            if (svcRequestType != null) {

                requestItemResponseData.setRequestType(svcRequestType);

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "If ItemId or RequestId is non-null, then RequestType must be non-null in RequestItem.");

            }

            if (svcRequestScopeType != null) {

                requestItemResponseData.setRequestScopeType(svcRequestScopeType);

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "If ItemId or RequestId is non-null, then RequestScopeType must be non-null in RequestItem.");

            }

            if (svcShippingInformation != null) {

                requestItemResponseData.setShippingInformation(svcShippingInformation);
            }

            if (svcDateAvailable != null) {

                requestItemResponseData.setDateAvailable(svcDateAvailable);
            }

            if (svcHoldPickupDate != null) {

                requestItemResponseData.setHoldPickupDate(svcHoldPickupDate);
            }

            if (svcFiscalTransactionInformation != null) {

                requestItemResponseData.setFiscalTransactionInformation(svcFiscalTransactionInformation);
            }

            if (svcItemOptionalFields != null) {

                requestItemResponseData.setItemOptionalFields(svcItemOptionalFields);
            }

            if (svcUserOptionalFields != null) {

                requestItemResponseData.setUserOptionalFields(svcUserOptionalFields);
            }

        }

        return requestItemResponseData;

    }


    protected org.extensiblecatalog.ncip.v2.service.UserOptionalFields createUserOptionalFields(
        UserOptionalFields jaxbUserOptionalFields) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.UserOptionalFields svcUserOptionalFields = null;

        if (jaxbUserOptionalFields != null) {

            svcUserOptionalFields = new org.extensiblecatalog.ncip.v2.service.UserOptionalFields();

            svcUserOptionalFields.setNameInformation(
                createNameInformation(jaxbUserOptionalFields.getNameInformation()));

            if (jaxbUserOptionalFields.getUserAddressInformation() != null
                && jaxbUserOptionalFields.getUserAddressInformation().size() > 0) {

                svcUserOptionalFields.setUserAddressInformations(
                    createSVCUserAddressInformations(jaxbUserOptionalFields.getUserAddressInformation()));

            }

            svcUserOptionalFields.setDateOfBirth(
                convertDate(jaxbUserOptionalFields.getDateOfBirth()));

            if (jaxbUserOptionalFields.getUserLanguage() != null
                && jaxbUserOptionalFields.getUserLanguage().size() > 0) {

                svcUserOptionalFields.setUserLanguages(
                    convertUserLanguages(jaxbUserOptionalFields.getUserLanguage()));

            }

            if (jaxbUserOptionalFields.getUserPrivilege() != null
                && jaxbUserOptionalFields.getUserPrivilege().size() > 0) {
                svcUserOptionalFields.setUserPrivileges(
                    createSVCUserPrivileges(jaxbUserOptionalFields.getUserPrivilege()));
            }

            if (jaxbUserOptionalFields.getBlockOrTrap() != null
                && jaxbUserOptionalFields.getBlockOrTrap().size() > 0) {
                svcUserOptionalFields.setBlockOrTraps(
                    createSVCBlockOrTraps(jaxbUserOptionalFields.getBlockOrTrap()));
            }

            if (jaxbUserOptionalFields.getUserId() != null
                && jaxbUserOptionalFields.getUserId().size() > 0) {
                svcUserOptionalFields.setUserIds(
                    createSVCUserIds(jaxbUserOptionalFields.getUserId()));
            }

            if (jaxbUserOptionalFields.getPreviousUserId() != null
                && jaxbUserOptionalFields.getPreviousUserId().size() > 0) {
                svcUserOptionalFields.setPreviousUserIds(
                    createSVCPreviousUserIds(jaxbUserOptionalFields.getPreviousUserId()));
            }

        }

        return svcUserOptionalFields;

    }

    protected org.extensiblecatalog.ncip.v2.service.UserLanguage convertUserLanguage(
        SchemeValuePair jaxbUserLanguage) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.UserLanguage svcUserLanguage = null;

        if (jaxbUserLanguage != null) {

            try {

                svcUserLanguage = org.extensiblecatalog.ncip.v2.service.UserLanguage.find(
                    jaxbUserLanguage.getScheme(), jaxbUserLanguage.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcUserLanguage;
    }   

    protected List<org.extensiblecatalog.ncip.v2.service.UserLanguage> convertUserLanguages(
        List<SchemeValuePair> jaxbUserLanguages) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.UserLanguage> svcUserLanguages;

        if (jaxbUserLanguages != null && jaxbUserLanguages.size() > 0) {

            svcUserLanguages = new ArrayList<org.extensiblecatalog.ncip.v2.service.UserLanguage>();

            for (SchemeValuePair jaxbUserLanguage : jaxbUserLanguages) {

                svcUserLanguages.add(convertUserLanguage(jaxbUserLanguage));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "UserLanguage must be non-null and non-empty in convertUserLanguages.");

        }

        return svcUserLanguages;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.UserPrivilege> createSVCUserPrivileges(
        List<UserPrivilege> jaxbUserPrivilege) {
        // TODO: Write createUserPrivileges method
        return null;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.BlockOrTrap> createSVCBlockOrTraps(
        List<BlockOrTrap> jaxbBlockOrTrap) {
        // TODO: Write createBlockOrTraps method
        return null;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.UserId> createSVCUserIds(List<UserId> jaxbUserId) {
        // TODO: Write createUserIds method
        return null;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.PreviousUserId> createSVCPreviousUserIds(
        List<PreviousUserId> jaxbPreviousUserId) {
        // TODO: Write createPreviousUserIds method
        return null;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.UserAddressInformation> createSVCUserAddressInformations(
        List<UserAddressInformation> jaxbUserAddressInformation) {
        // TODO: Write createUserAddressInformations method
        return null;
    }

    protected org.extensiblecatalog.ncip.v2.service.NameInformation createNameInformation(
        NameInformation jaxbNameInformation) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.NameInformation svcNameInformation = null;

        if (jaxbNameInformation != null) {

            svcNameInformation = new org.extensiblecatalog.ncip.v2.service.NameInformation();

            if (jaxbNameInformation.getPersonalNameInformation() != null) {

                svcNameInformation.setPersonalNameInformation(
                    createPersonalNameInformation(jaxbNameInformation.getPersonalNameInformation()));

            } else if (jaxbNameInformation.getOrganizationNameInformation() != null) {

                svcNameInformation.setOrganizationNameInformations(
                    createSVCOrganizationNameInformations(jaxbNameInformation.getOrganizationNameInformation()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "PersonalNameInformation or OrganizationNameInformation must be non-null in NameInformation.");

            }

        }

        return svcNameInformation;

    }


    protected org.extensiblecatalog.ncip.v2.service.PersonalNameInformation createPersonalNameInformation(
        org.extensiblecatalog.ncip.v2.binding.jaxb.elements.PersonalNameInformation jaxbPersonalNameInformation)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.PersonalNameInformation svcPersonalNameInformation = null;

        if (jaxbPersonalNameInformation != null) {

            svcPersonalNameInformation = new org.extensiblecatalog.ncip.v2.service.PersonalNameInformation();

            if ( jaxbPersonalNameInformation.getContent() != null
                && jaxbPersonalNameInformation.getContent().size() > 0 ) {

                for (Object obj : jaxbPersonalNameInformation.getContent()) {

                    if (obj instanceof StructuredPersonalUserName) {
                        StructuredPersonalUserName jaxbStructuredPersonalUserName = (StructuredPersonalUserName)obj;
                        svcPersonalNameInformation.setStructuredPersonalUserName(
                            createStructuredPersonalUserName(jaxbStructuredPersonalUserName));
                    } else if (obj instanceof JAXBElement) {
                        JAXBElement jaxbElement = (JAXBElement)obj;
                        if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("UnstructuredPersonalUserName") == 0) {
                            String jaxbUnstructuredPersonalUserName = (String)jaxbElement.getValue();
                            svcPersonalNameInformation.setUnstructuredPersonalUserName(jaxbUnstructuredPersonalUserName);
                        } else {
                            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected element "
                                + jaxbElement.getName().getLocalPart() + " in PersonalNameInformation.");
                        }
                    } else {
                        throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected object class "
                            + obj.getClass().getName() + " in PersonalNameInformation.");
                    }

                }

            }

        }

        return svcPersonalNameInformation;

    }

    protected org.extensiblecatalog.ncip.v2.service.StructuredPersonalUserName createStructuredPersonalUserName(
        StructuredPersonalUserName jaxbStructuredPersonalUserName) {

        org.extensiblecatalog.ncip.v2.service.StructuredPersonalUserName svcStructuredPersonalUserName = null;

        // TODO: Write createStructuredPersonalUserName

        return svcStructuredPersonalUserName;

    }

    protected List<org.extensiblecatalog.ncip.v2.service.OrganizationNameInformation>
    createSVCOrganizationNameInformations(List<OrganizationNameInformation> jaxbOrganizationNameInformation) {
        // TODO: Write createSVCOrganizationNameInformations method
        return null;
    }

    protected org.extensiblecatalog.ncip.v2.service.OrganizationNameInformation createOrganizationNameInformation(
        org.extensiblecatalog.ncip.v2.binding.jaxb.elements.OrganizationNameInformation
            jaxbOrganizationNameInformation)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.OrganizationNameInformation svcOrganizationNameInformation = null;

        if (jaxbOrganizationNameInformation != null) {

            svcOrganizationNameInformation = new org.extensiblecatalog.ncip.v2.service.OrganizationNameInformation();

            if (jaxbOrganizationNameInformation.getOrganizationNameType() != null) {

                svcOrganizationNameInformation.setOrganizationNameType(
                    convertOrganizationNameType(jaxbOrganizationNameInformation.getOrganizationNameType()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "OrganizationNameType must be non-null in OrganizationNameInformation.");
            }

            if (jaxbOrganizationNameInformation.getOrganizationName() != null) {

                svcOrganizationNameInformation.setOrganizationName(
                    jaxbOrganizationNameInformation.getOrganizationName());

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "OrganizationName must be non-null in OrganizationNameInformation.");
            }

        }

        return svcOrganizationNameInformation;

    }

    protected org.extensiblecatalog.ncip.v2.service.FiscalTransactionInformation createFiscalTransactionInformation(
        FiscalTransactionInformation jaxbFiscalTransactionInformation) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.FiscalTransactionInformation svcFiscalTransactionInformation = null;

        if (jaxbFiscalTransactionInformation != null) {

            svcFiscalTransactionInformation = new org.extensiblecatalog.ncip.v2.service.FiscalTransactionInformation();

            svcFiscalTransactionInformation.setFiscalActionType(
                convertFiscalActionType(jaxbFiscalTransactionInformation.getFiscalActionType()));

            svcFiscalTransactionInformation.setFiscalTransactionReferenceId(
                createFiscalTransactionReferenceId(
                    jaxbFiscalTransactionInformation.getFiscalTransactionReferenceId()));

            svcFiscalTransactionInformation.setFiscalTransactionType(
                convertFiscalTransactionType(jaxbFiscalTransactionInformation.getFiscalTransactionType()));

            svcFiscalTransactionInformation.setValidFromDate(
                convertDate(jaxbFiscalTransactionInformation.getValidFromDate()));

            svcFiscalTransactionInformation.setValidToDate(convertDate(
                jaxbFiscalTransactionInformation.getValidToDate()));

            svcFiscalTransactionInformation.setAmount(createAmount(jaxbFiscalTransactionInformation.getAmount()));

            svcFiscalTransactionInformation.setPaymentMethodType(
                convertPaymentMethodType(jaxbFiscalTransactionInformation.getPaymentMethodType()));

            svcFiscalTransactionInformation.setFiscalTransactionDescription(
                jaxbFiscalTransactionInformation.getFiscalTransactionDescription());

            svcFiscalTransactionInformation.setRequestId(createRequestId(
                jaxbFiscalTransactionInformation.getRequestId()));

            svcFiscalTransactionInformation.setItemDetails(
                createItemDetails(jaxbFiscalTransactionInformation.getItemDetails()));

        }

        return svcFiscalTransactionInformation;

    }

    protected org.extensiblecatalog.ncip.v2.service.FiscalActionType convertFiscalActionType(
        SchemeValuePair jaxbFiscalActionType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.FiscalActionType svcFiscalActionType = null;

        if (jaxbFiscalActionType != null) {

            try {

                svcFiscalActionType = org.extensiblecatalog.ncip.v2.service.FiscalActionType.find(
                    jaxbFiscalActionType.getScheme(), jaxbFiscalActionType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcFiscalActionType;
    }

    protected org.extensiblecatalog.ncip.v2.service.FiscalTransactionType convertFiscalTransactionType(
        SchemeValuePair jaxbFiscalTransactionType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.FiscalTransactionType svcFiscalTransactionType = null;

        if (jaxbFiscalTransactionType != null) {

            try {

                svcFiscalTransactionType = org.extensiblecatalog.ncip.v2.service.FiscalTransactionType.find(
                    jaxbFiscalTransactionType.getScheme(), jaxbFiscalTransactionType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcFiscalTransactionType;
    }

    protected org.extensiblecatalog.ncip.v2.service.PaymentMethodType convertPaymentMethodType(
        SchemeValuePair jaxbPaymentMethodType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.PaymentMethodType svcPaymentMethodType = null;

        if (jaxbPaymentMethodType != null) {

            try {

                svcPaymentMethodType = org.extensiblecatalog.ncip.v2.service.PaymentMethodType.find(
                    jaxbPaymentMethodType.getScheme(), jaxbPaymentMethodType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcPaymentMethodType;
    }

    protected org.extensiblecatalog.ncip.v2.service.ItemDetails createItemDetails(
        ItemDetails jaxbItemDetails) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ItemDetails svcItemDetails = null;

        if (jaxbItemDetails != null) {

            svcItemDetails = new org.extensiblecatalog.ncip.v2.service.ItemDetails();

            if (jaxbItemDetails.getItemId() != null) {

                svcItemDetails.setItemId(createItemId(jaxbItemDetails.getItemId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "ItemId must be non-null in ItemDetails.");
            }

            if (jaxbItemDetails.getBibliographicDescription() != null) {

                svcItemDetails.setBibliographicDescription(
                    createBibliographicDescription(jaxbItemDetails.getBibliographicDescription()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "BibliographicDescription must be non-null in ItemDetails.");
            }

            if (jaxbItemDetails.getDateCheckedOut() != null) {

                svcItemDetails.setDateCheckedOut(convertDate(jaxbItemDetails.getDateCheckedOut()));

            }

            if (jaxbItemDetails.getDateRenewed() != null) {

                svcItemDetails.setDateReneweds(convertDatesToSVC(jaxbItemDetails.getDateRenewed()));

            }

            if (jaxbItemDetails.getDateDue() != null) {

                svcItemDetails.setDateDue(convertDate(jaxbItemDetails.getDateDue()));

            } else if (jaxbItemDetails.getIndeterminateLoanPeriodFlag() != null) {

                svcItemDetails.setIndeterminateLoanPeriodFlag(jaxbItemDetails.getIndeterminateLoanPeriodFlag() != null);

            } else if (jaxbItemDetails.getNonReturnableFlag() != null) {

                svcItemDetails.setNonReturnableFlag(jaxbItemDetails.getNonReturnableFlag() != null);

            }

            if (jaxbItemDetails.getDateReturned() != null) {

                svcItemDetails.setDateReturned(convertDate(jaxbItemDetails.getDateReturned()));

            }

        }

        return svcItemDetails;

    }

    protected org.extensiblecatalog.ncip.v2.service.Amount createAmount(
        Amount jaxbAmount) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.Amount svcAmount = null;

        if (jaxbAmount != null) {

            svcAmount = new org.extensiblecatalog.ncip.v2.service.Amount();

            if (jaxbAmount.getCurrencyCode() != null) {

                svcAmount.setCurrencyCode(convertCurrencyCode(jaxbAmount.getCurrencyCode()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "CurrencyCode must be non-null in Amount.");
            }

            if (jaxbAmount.getMonetaryValue() != null) {

                svcAmount.setMonetaryValue(jaxbAmount.getMonetaryValue());

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "CurrencyCode must be non-null in Amount.");
            }

        }

        return svcAmount;

    }

    protected org.extensiblecatalog.ncip.v2.service.FiscalTransactionReferenceId createFiscalTransactionReferenceId(
        FiscalTransactionReferenceId jaxbFiscalTransactionReferenceId) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.FiscalTransactionReferenceId svcFiscalTransactionReferenceId = null;

        if (jaxbFiscalTransactionReferenceId != null) {

            svcFiscalTransactionReferenceId = new org.extensiblecatalog.ncip.v2.service.FiscalTransactionReferenceId();

            if (jaxbFiscalTransactionReferenceId.getAgencyId() != null) {

                svcFiscalTransactionReferenceId.setAgencyId(
                    createAgencyId(jaxbFiscalTransactionReferenceId.getAgencyId()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "AgencyId must be non-null in FiscalTransactionReferenceId.");
            }

            if (jaxbFiscalTransactionReferenceId.getFiscalTransactionIdentifierValue() != null) {

                svcFiscalTransactionReferenceId.setFiscalTransactionIdentifierValue(
                    jaxbFiscalTransactionReferenceId.getFiscalTransactionIdentifierValue());

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "FiscalTransactionIdentifierValue must be non-null in FiscalTransactionReferenceId.");
            }

        }

        return svcFiscalTransactionReferenceId;

    }

    // TODO: Write createAmount method


    // TODO: Write createItemDetails method

    protected org.extensiblecatalog.ncip.v2.service.ResponseHeader createResponseHeader(
        ResponseHeader jaxbResponseHeader) {

        org.extensiblecatalog.ncip.v2.service.ResponseHeader respHdr = null;

        if (jaxbResponseHeader != null) {

            if (jaxbResponseHeader.getFromSystemId() != null) {

                if (respHdr == null) {

                    respHdr = new org.extensiblecatalog.ncip.v2.service.ResponseHeader();

                }

                respHdr.setFromSystemId(createFromSystemId(jaxbResponseHeader.getFromSystemId()));

            }

            if (jaxbResponseHeader.getFromSystemAuthentication() != null) {

                if (respHdr == null) {

                    respHdr = new org.extensiblecatalog.ncip.v2.service.ResponseHeader();

                }

                respHdr.setFromSystemAuthentication(jaxbResponseHeader.getFromSystemAuthentication());

            }

            if (jaxbResponseHeader.getFromAgencyId() != null) {

                if (respHdr == null) {

                    respHdr = new org.extensiblecatalog.ncip.v2.service.ResponseHeader();

                }

                respHdr.setFromAgencyId(createFromAgencyId(jaxbResponseHeader.getFromAgencyId()));

            }

            if (jaxbResponseHeader.getFromAgencyAuthentication() != null) {

                if (respHdr == null) {

                    respHdr = new org.extensiblecatalog.ncip.v2.service.ResponseHeader();

                }

                respHdr.setFromAgencyAuthentication(jaxbResponseHeader.getFromAgencyAuthentication());

            }

            if (jaxbResponseHeader.getToSystemId() != null) {

                if (respHdr == null) {

                    respHdr = new org.extensiblecatalog.ncip.v2.service.ResponseHeader();

                }

                respHdr.setToSystemId(createToSystemId(jaxbResponseHeader.getToSystemId()));

            }

            if (jaxbResponseHeader.getToAgencyId() != null) {

                if (respHdr == null) {

                    respHdr = new org.extensiblecatalog.ncip.v2.service.ResponseHeader();

                }

                respHdr.setToAgencyId(createToAgencyId(jaxbResponseHeader.getToAgencyId()));

            }

        }

        return respHdr;
    }

    protected ItemTransaction createItemTransaction(
        org.extensiblecatalog.ncip.v2.service.ItemTransaction itemTransaction)
        throws BindingException {

        ItemTransaction jaxbItemTransaction = new ItemTransaction();

        if (itemTransaction.getCurrentBorrower() != null) {
            jaxbItemTransaction.setCurrentBorrower(createCurrentBorrower(itemTransaction.getCurrentBorrower()));
        }

        if (itemTransaction.getCurrentRequesters() != null) {
            jaxbItemTransaction.getCurrentRequester().addAll(createJAXBCurrentRequesters(
                itemTransaction.getCurrentRequesters()));
        }

        return jaxbItemTransaction;

    }

    protected List<CurrentRequester> createJAXBCurrentRequesters(
        List<org.extensiblecatalog.ncip.v2.service.CurrentRequester> currentRequesters) throws BindingException {
        List<CurrentRequester> jaxbCurrentRequesters = new ArrayList<CurrentRequester>();

        if (currentRequesters != null && currentRequesters.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.CurrentRequester currentRequester : currentRequesters) {

                jaxbCurrentRequesters.add(createCurrentRequester(currentRequester));

            }

        }

        return jaxbCurrentRequesters;
    }

    protected CurrentRequester createCurrentRequester(
        org.extensiblecatalog.ncip.v2.service.CurrentRequester currentRequester)
        throws BindingException {

        CurrentRequester jaxbCurrentRequester = new CurrentRequester();

        jaxbCurrentRequester.setUserId(createUserId(currentRequester.getUserId()));

        return jaxbCurrentRequester;
    }

    protected CurrentBorrower createCurrentBorrower(
        org.extensiblecatalog.ncip.v2.service.CurrentBorrower currentBorrower)
        throws BindingException {
        CurrentBorrower jaxbCurrentBorrower = new CurrentBorrower();

        jaxbCurrentBorrower.setUserId(createUserId(currentBorrower.getUserId()));

        return jaxbCurrentBorrower;
    }

    protected List<UserId> createUserIds(
        List<org.extensiblecatalog.ncip.v2.service.UserId> userIds)
        throws BindingException {

        List<UserId> jaxbUserIds = new ArrayList<UserId>();

        if (userIds != null && userIds.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.UserId userId : userIds) {

                UserId jaxbUserId = createUserId(userId);

                jaxbUserIds.add(jaxbUserId);

            }

        }

        return jaxbUserIds;

    }

    protected UserId createUserId(org.extensiblecatalog.ncip.v2.service.UserId userId) throws BindingException {

        UserId jaxbUserId = null;
        
        if ( userId != null ) {

            jaxbUserId = new UserId();

            if (userId.getUserIdentifierValue() != null) {

                jaxbUserId.setUserIdentifierValue(userId.getUserIdentifierValue());

                if (userId.getAgencyId() != null) {
                    jaxbUserId.setAgencyId(convertSVP(userId.getAgencyId()));
                }

                if (userId.getUserIdentifierType() != null) {
                    jaxbUserId.setUserIdentifierType(convertSVP(userId.getUserIdentifierType()));
                }

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "UserIdentifierValue must be non-null in UserId.");

            }

        }

        return jaxbUserId;

    }

    protected NCIPMessage createProblemResponseMessage(
        org.extensiblecatalog.ncip.v2.service.ProblemResponseData problemResponseData) throws BindingException {

        NCIPMessage ncipRespMsg = new NCIPMessage();
        ncipRespMsg.setVersion(NCIP_VERSION_V2_0);

        List<Problem> jaxbProblems = ncipRespMsg.getProblem();

        for (org.extensiblecatalog.ncip.v2.service.Problem problem : problemResponseData.getProblems()) {
            jaxbProblems.add(createProblem(problem));
        }

        return ncipRespMsg;
    }

    protected Problem createProblem(org.extensiblecatalog.ncip.v2.service.Problem problem) {
        Problem jaxbProblem = new Problem();

        if (problem.getProblemDetail() != null) {
            jaxbProblem.setProblemDetail(problem.getProblemDetail());
        }

        if (problem.getProblemElement() != null) {
            jaxbProblem.setProblemElement(problem.getProblemElement());
        }

        if (problem.getProblemValue() != null) {
            jaxbProblem.setProblemValue(problem.getProblemValue());
        }

        if (problem.getProblemType() != null) {
            jaxbProblem.setProblemType(convertSVP(problem.getProblemType()));
        }

        return jaxbProblem;

    }

    protected ItemOptionalFields createItemOptionalFields(
        org.extensiblecatalog.ncip.v2.service.ItemOptionalFields itemOptionalFields)
        throws BindingException {
        ItemOptionalFields jaxbItemOptionalFields = new ItemOptionalFields();

        if (itemOptionalFields.getBibliographicDescription() != null) {
            jaxbItemOptionalFields.setBibliographicDescription(createBibliographicDescription(
                itemOptionalFields.getBibliographicDescription()));
        }

        if (itemOptionalFields.getCirculationStatus() != null) {
            jaxbItemOptionalFields.setCirculationStatus(
                convertSVP(itemOptionalFields.getCirculationStatus()));
        }

        if (itemOptionalFields.getElectronicResource() != null) {
            jaxbItemOptionalFields.setElectronicResource(
                createElectronicResource(itemOptionalFields.getElectronicResource()));
        }

        if (itemOptionalFields.getHoldQueueLength() != null) {
            jaxbItemOptionalFields.setHoldQueueLength(itemOptionalFields.getHoldQueueLength());
        }

        if (itemOptionalFields.getItemDescription() != null) {
            jaxbItemOptionalFields.setItemDescription(createItemDescription(itemOptionalFields.getItemDescription()));
        }

        if (itemOptionalFields.getItemUseRestrictionTypes() != null
            && itemOptionalFields.getItemUseRestrictionTypes().size() > 0) {
            jaxbItemOptionalFields.getItemUseRestrictionType().addAll(
                convertSVPsToJAXB(itemOptionalFields.getItemUseRestrictionTypes()));
        }

        if (itemOptionalFields.getLocations() != null && itemOptionalFields.getLocations().size() > 0) {
            jaxbItemOptionalFields.getLocation().addAll(createJAXBLocations(itemOptionalFields.getLocations()));
        }

        if (itemOptionalFields.getPhysicalCondition() != null) {
            jaxbItemOptionalFields.setPhysicalCondition(createPhysicalCondition(
                itemOptionalFields.getPhysicalCondition()));
        }

        if (itemOptionalFields.getSecurityMarker() != null) {
            jaxbItemOptionalFields.setSecurityMarker(convertSVP(itemOptionalFields.getSecurityMarker()));
        }

        if (itemOptionalFields.getSensitizationFlag()) {
            jaxbItemOptionalFields.setSensitizationFlag(new SensitizationFlag());
        }

        return jaxbItemOptionalFields;
    }

    protected PhysicalCondition createPhysicalCondition(
        org.extensiblecatalog.ncip.v2.service.PhysicalCondition physicalCondition)
        throws BindingException {
        PhysicalCondition jaxbPhysicalCondition = new PhysicalCondition();

        if (physicalCondition.getPhysicalConditionType() != null) {
            jaxbPhysicalCondition.setPhysicalConditionType(convertSVP(physicalCondition.getPhysicalConditionType()));
            if (physicalCondition.getPhysicalConditionDetails() != null) {
                jaxbPhysicalCondition.setPhysicalConditionDetails(physicalCondition.getPhysicalConditionDetails());
            }
        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "PhysicalCondition must contain a non-empty PhysicalConditionType.");

        }

        return jaxbPhysicalCondition;
    }

    protected List<Location> createJAXBLocations(List<org.extensiblecatalog.ncip.v2.service.Location> locations)
        throws BindingException {

        List<Location> jaxbLocations = new ArrayList<Location>();

        if (locations != null && locations.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.Location location : locations) {

                jaxbLocations.add(createLocation(location));

            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "List<Location> must be non-null and non-empty in createJAXBLocations.");

        }

        return jaxbLocations;
    }

    protected ItemDescription createItemDescription(
        org.extensiblecatalog.ncip.v2.service.ItemDescription itemDescription)
        throws BindingException {

        ItemDescription jaxbItemDescription = null;

        if (itemDescription != null) {

            jaxbItemDescription = new ItemDescription();

            if (itemDescription.getCallNumber() != null) {

                jaxbItemDescription.setCallNumber(itemDescription.getCallNumber());

            }

            if (itemDescription.getCopyNumber() != null) {

                jaxbItemDescription.setCopyNumber(itemDescription.getCopyNumber());

            }

            if (itemDescription.getHoldingsInformation() != null) {

                jaxbItemDescription.setHoldingsInformation(
                    createHoldingsInformation(itemDescription.getHoldingsInformation()));

            }

            if (itemDescription.getItemDescriptionLevel() != null) {

                jaxbItemDescription.setItemDescriptionLevel(convertSVP(itemDescription.getItemDescriptionLevel()));

            }

            if (itemDescription.getNumberOfPieces() != null) {

                jaxbItemDescription.setNumberOfPieces(itemDescription.getNumberOfPieces());

            }

        }

        return jaxbItemDescription;
    }

    protected HoldingsInformation createHoldingsInformation(
        org.extensiblecatalog.ncip.v2.service.HoldingsInformation holdingsInformation)
        throws BindingException {

        HoldingsInformation jaxbHoldingsInformation = null;

        if ( holdingsInformation != null ) {

            jaxbHoldingsInformation = new HoldingsInformation();

            if (holdingsInformation.getStructuredHoldingsData() != null
                && holdingsInformation.getStructuredHoldingsData().size() > 0) {

                for (org.extensiblecatalog.ncip.v2.service.StructuredHoldingsData structuredHoldingsData
                    : holdingsInformation.getStructuredHoldingsData()) {

                    jaxbHoldingsInformation.getStructuredHoldingsData().add(createStructuredHoldingsData(
                        structuredHoldingsData));

                }

            } else if (holdingsInformation.getUnstructuredHoldingsData() != null
                && holdingsInformation.getUnstructuredHoldingsData().length() > 0) {

                jaxbHoldingsInformation.setUnstructuredHoldingsData(holdingsInformation.getUnstructuredHoldingsData());

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Either StructuredHoldingsData or UnstructuredHoldingsData must be non-null and non-empty "
                        + "in HoldingsInformation.");

            }

        }

        return jaxbHoldingsInformation;

    }

    protected SummaryHoldingsInformation createSummaryHoldingsInformation(
        org.extensiblecatalog.ncip.v2.service.SummaryHoldingsInformation summaryHoldingsInformation)
        throws BindingException {

        SummaryHoldingsInformation jaxbSummaryHoldingsInformation = null;

        if ( summaryHoldingsInformation != null ) {

            jaxbSummaryHoldingsInformation = new SummaryHoldingsInformation();

            if (summaryHoldingsInformation.getStructuredHoldingsData() != null
                && summaryHoldingsInformation.getStructuredHoldingsData().size() > 0) {

                for (org.extensiblecatalog.ncip.v2.service.StructuredHoldingsData structuredHoldingsData
                    : summaryHoldingsInformation.getStructuredHoldingsData()) {

                    jaxbSummaryHoldingsInformation.getStructuredHoldingsData().add(createStructuredHoldingsData(
                        structuredHoldingsData));

                }

            } else if (summaryHoldingsInformation.getUnstructuredHoldingsData() != null
                && summaryHoldingsInformation.getUnstructuredHoldingsData().length() > 0) {

                jaxbSummaryHoldingsInformation.setUnstructuredHoldingsData(summaryHoldingsInformation.getUnstructuredHoldingsData());

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "Either StructuredHoldingsData or UnstructuredHoldingsData must be non-null and non-empty "
                        + "in SummaryHoldingsInformation.");

            }

        }

        return jaxbSummaryHoldingsInformation;

    }

    protected StructuredHoldingsData createStructuredHoldingsData(
        org.extensiblecatalog.ncip.v2.service.StructuredHoldingsData structuredHoldingsData) {
        StructuredHoldingsData jaxbStructuredHoldingsData = new StructuredHoldingsData();

        if (structuredHoldingsData.getHoldingsChronology() != null) {

            jaxbStructuredHoldingsData.getContent().add(createHoldingsChronology(
                structuredHoldingsData.getHoldingsChronology()));

        }

        if (structuredHoldingsData.getHoldingsEnumeration() != null) {

            jaxbStructuredHoldingsData.getContent().add(createHoldingsEnumeration(
                structuredHoldingsData.getHoldingsEnumeration()));

        }

        return jaxbStructuredHoldingsData;

    }

    protected HoldingsEnumeration createHoldingsEnumeration(
        org.extensiblecatalog.ncip.v2.service.HoldingsEnumeration holdingsEnumeration) {
        HoldingsEnumeration jaxbHoldingsEnumeration = new HoldingsEnumeration();

        for (org.extensiblecatalog.ncip.v2.service.EnumerationLevelInstance enumerationLevelInstance
            : holdingsEnumeration.getEnumerationLevelInstances()) {
            jaxbHoldingsEnumeration.getEnumerationLevelInstance().add(
                createEnumerationLevelInstance(enumerationLevelInstance));
        }

        return jaxbHoldingsEnumeration;
    }

    protected EnumerationLevelInstance createEnumerationLevelInstance(
        org.extensiblecatalog.ncip.v2.service.EnumerationLevelInstance enumerationLevelInstance) {
        EnumerationLevelInstance jaxbEnumerationLevelInstance = new EnumerationLevelInstance();
        if (enumerationLevelInstance.getEnumerationCaption() != null) {
            jaxbEnumerationLevelInstance.getContent().add(objectFactory.createEnumerationCaption(
                enumerationLevelInstance.getEnumerationCaption()));
        }

        if (enumerationLevelInstance.getEnumerationLevel() != null) {
            jaxbEnumerationLevelInstance.getContent().add(objectFactory.createEnumerationLevel(
                enumerationLevelInstance.getEnumerationLevel()));
        }

        if (enumerationLevelInstance.getEnumerationValue() != null) {
            jaxbEnumerationLevelInstance.getContent().add(objectFactory.createEnumerationValue(
                enumerationLevelInstance.getEnumerationValue()));
        }

        return jaxbEnumerationLevelInstance;
    }

    protected HoldingsChronology createHoldingsChronology(
        org.extensiblecatalog.ncip.v2.service.HoldingsChronology holdingsChronology) {
        HoldingsChronology jaxbHoldingsChronology = new HoldingsChronology();
        for (org.extensiblecatalog.ncip.v2.service.ChronologyLevelInstance chronologyLevelInstance
            : holdingsChronology.getChronologyLevelInstances()) {
            jaxbHoldingsChronology.getChronologyLevelInstance().add(
                createChronologyLevelInstance(chronologyLevelInstance));
        }

        return jaxbHoldingsChronology;
    }

    protected ChronologyLevelInstance createChronologyLevelInstance(
        org.extensiblecatalog.ncip.v2.service.ChronologyLevelInstance chronologyLevelInstance) {
        ChronologyLevelInstance jaxbChronologyLevelInstance = new ChronologyLevelInstance();
        if (chronologyLevelInstance.getChronologyCaption() != null) {
            jaxbChronologyLevelInstance.getContent().add(objectFactory.createChronologyCaption(
                chronologyLevelInstance.getChronologyCaption()));
        }

        if (chronologyLevelInstance.getChronologyLevel() != null) {
            jaxbChronologyLevelInstance.getContent().add(objectFactory.createChronologyLevel(
                chronologyLevelInstance.getChronologyLevel()));
        }

        if (chronologyLevelInstance.getChronologyValue() != null) {
            jaxbChronologyLevelInstance.getContent().add(objectFactory.createChronologyValue(
                chronologyLevelInstance.getChronologyValue()));
        }

        return jaxbChronologyLevelInstance;
    }

    protected Location createLocation(org.extensiblecatalog.ncip.v2.service.Location location) throws BindingException {
        Location jaxbLocation = null;
        if (location != null) {
            jaxbLocation = new Location();
            if (location.getLocationName() != null) {
                jaxbLocation.setLocationName(createLocationName(location.getLocationName()));
            }
            if (location.getLocationType() != null) {
                jaxbLocation.setLocationType(convertSVP(location.getLocationType()));
            }
            if (location.getValidFromDate() != null) {
                jaxbLocation.setValidFromDate(convertDate(location.getValidFromDate()));
            }
            if (location.getValidToDate() != null) {
                jaxbLocation.setValidToDate(convertDate(location.getValidToDate()));
            }

        }

        return jaxbLocation;

    }

    protected LocationName createLocationName(org.extensiblecatalog.ncip.v2.service.LocationName locationName) {
        LocationName jaxbLocationName = new LocationName();

        if (locationName.getLocationNameInstances() != null) {
            jaxbLocationName.getLocationNameInstance().addAll(createLocationNameInstances(
                locationName.getLocationNameInstances()));
        }

        return jaxbLocationName;
    }

    protected List<LocationNameInstance> createLocationNameInstances(
        List<org.extensiblecatalog.ncip.v2.service.LocationNameInstance> locationNameInstances) {
        List<LocationNameInstance> jaxbLocationNameInstances = new ArrayList<LocationNameInstance>();

        if (locationNameInstances != null && locationNameInstances.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.LocationNameInstance locationNameInstance
                : locationNameInstances) {

                LocationNameInstance jaxbLocationNameInstance = new LocationNameInstance();

                if (locationNameInstance.getLocationNameValue() != null
                    && locationNameInstance.getLocationNameValue().length() > 0) {
                    jaxbLocationNameInstance.setLocationNameValue(locationNameInstance.getLocationNameValue());
                }

                jaxbLocationNameInstance.setLocationNameLevel(locationNameInstance.getLocationNameLevel());

                jaxbLocationNameInstances.add(jaxbLocationNameInstance);

            }

        }

        return jaxbLocationNameInstances;
    }

    protected ElectronicResource createElectronicResource(
        org.extensiblecatalog.ncip.v2.service.ElectronicResource electronicResource) {
        ElectronicResource jaxbElectronicResource = null;

        if ( electronicResource != null) {

            if (electronicResource.getActualResource() != null) {

                jaxbElectronicResource = new ElectronicResource();
                jaxbElectronicResource.setActualResource(electronicResource.getActualResource());
                jaxbElectronicResource.setElectronicDataFormatType(
                    convertSVP(electronicResource.getElectronicDataFormatType()));

            } else if (electronicResource.getReferenceToResource() != null) {

                jaxbElectronicResource = new ElectronicResource();
                jaxbElectronicResource.setReferenceToResource(electronicResource.getReferenceToResource());

            }

        }

        return jaxbElectronicResource;
    }

    protected BibliographicDescription createBibliographicDescription(
        org.extensiblecatalog.ncip.v2.service.BibliographicDescription bibliographicDescription)
        throws BindingException {

        BibliographicDescription jaxbBibliographicDescription = null;

        if (bibliographicDescription != null) {

            jaxbBibliographicDescription = new BibliographicDescription();
            if (bibliographicDescription.getAuthor() != null) {
                jaxbBibliographicDescription.setAuthor(bibliographicDescription.getAuthor());
            }

            if (bibliographicDescription.getAuthorOfComponent() != null) {
                jaxbBibliographicDescription.setAuthorOfComponent(bibliographicDescription.getAuthorOfComponent());
            }

            if (bibliographicDescription.getBibliographicItemIds() != null
                && bibliographicDescription.getBibliographicItemIds().size() > 0) {

                jaxbBibliographicDescription.getBibliographicItemId().addAll(createJAXBBibliographicItemIds(
                    bibliographicDescription.getBibliographicItemIds()));

            }

            if (bibliographicDescription.getBibliographicRecordIds() != null
                && bibliographicDescription.getBibliographicRecordIds().size() > 0) {

                jaxbBibliographicDescription.getBibliographicRecordId().addAll((createJAXBBibliographicRecordIds(
                    bibliographicDescription.getBibliographicRecordIds())));

            }

            if (bibliographicDescription.getComponentId() != null) {
                jaxbBibliographicDescription.setComponentId(createComponentId(bibliographicDescription.getComponentId()));
            }

            if (bibliographicDescription.getEdition() != null) {
                jaxbBibliographicDescription.setEdition(bibliographicDescription.getEdition());
            }

            if (bibliographicDescription.getPagination() != null) {
                jaxbBibliographicDescription.setPagination(bibliographicDescription.getPagination());
            }

            if (bibliographicDescription.getPlaceOfPublication() != null) {
                jaxbBibliographicDescription.setPlaceOfPublication(bibliographicDescription.getPlaceOfPublication());
            }

            if (bibliographicDescription.getPublicationDate() != null) {
                jaxbBibliographicDescription.setPublicationDate(bibliographicDescription.getPublicationDate());
            }

            if (bibliographicDescription.getPublicationDateOfComponent() != null) {
                jaxbBibliographicDescription.setPublicationDateOfComponent(
                    bibliographicDescription.getPublicationDateOfComponent());
            }

            if (bibliographicDescription.getPublisher() != null) {
                jaxbBibliographicDescription.setPublisher(bibliographicDescription.getPublisher());
            }

            if (bibliographicDescription.getSeriesTitleNumber() != null) {
                jaxbBibliographicDescription.setSeriesTitleNumber(bibliographicDescription.getSeriesTitleNumber());
            }

            if (bibliographicDescription.getTitle() != null) {
                jaxbBibliographicDescription.setTitle(bibliographicDescription.getTitle());
            }

            if (bibliographicDescription.getTitleOfComponent() != null) {
                jaxbBibliographicDescription.setTitleOfComponent(bibliographicDescription.getTitleOfComponent());
            }

            if (bibliographicDescription.getBibliographicLevel() != null) {
                jaxbBibliographicDescription.setBibliographicLevel(
                    convertSVP(bibliographicDescription.getBibliographicLevel()));
            }

            if (bibliographicDescription.getSponsoringBody() != null) {
                jaxbBibliographicDescription.setSponsoringBody(bibliographicDescription.getSponsoringBody());
            }

            if (bibliographicDescription.getElectronicDataFormatType() != null) {
                jaxbBibliographicDescription.setElectronicDataFormatType(
                    convertSVP(bibliographicDescription.getElectronicDataFormatType()));
            }

            if (bibliographicDescription.getLanguage() != null) {
                jaxbBibliographicDescription.setLanguage(convertSVP(bibliographicDescription.getLanguage()));
            }

            if (bibliographicDescription.getMediumType() != null) {
                jaxbBibliographicDescription.setMediumType(convertSVP(bibliographicDescription.getMediumType()));
            }

        }

        return jaxbBibliographicDescription;

    }

    protected List<BibliographicDescription> createJAXBBibliographicDescriptions(
        List<org.extensiblecatalog.ncip.v2.service.BibliographicDescription> svcBibliographicDescriptions) throws BindingException {

        List<BibliographicDescription> jaxbBibliographicDescriptions = new ArrayList<BibliographicDescription>();

        for ( org.extensiblecatalog.ncip.v2.service.BibliographicDescription svcBibliographicDescription : svcBibliographicDescriptions) {

            jaxbBibliographicDescriptions.add(createBibliographicDescription(svcBibliographicDescription));

        }

        return jaxbBibliographicDescriptions;

    }

    protected ComponentId createComponentId(org.extensiblecatalog.ncip.v2.service.ComponentId componentId)
        throws BindingException {
        ComponentId jaxbComponentId = new ComponentId();

        if (componentId.getComponentIdentifier() != null) {

            jaxbComponentId.setComponentIdentifier(componentId.getComponentIdentifier());

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "ComponentIdentifier must be non-null in ComponentId.");

        }

        if (componentId.getComponentIdentifierType() != null) {

            jaxbComponentId.setComponentIdentifierType(convertSVP(componentId.getComponentIdentifierType()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "ComponentIdentifierType must be non-null in ComponentId.");

        }

        return jaxbComponentId;
    }

    protected BibliographicRecordId createBibliographicRecordId(
        org.extensiblecatalog.ncip.v2.service.BibliographicRecordId bibliographicRecordId) throws BindingException {

        BibliographicRecordId jaxbBibliographicRecordId = null;

        if ( bibliographicRecordId != null ) {

            jaxbBibliographicRecordId = new BibliographicRecordId();

            if (bibliographicRecordId.getBibliographicRecordIdentifier() != null) {

                jaxbBibliographicRecordId.setBibliographicRecordIdentifier(
                    bibliographicRecordId.getBibliographicRecordIdentifier());

                if (bibliographicRecordId.getAgencyId() != null) {
                    jaxbBibliographicRecordId.setAgencyId(convertSVP(bibliographicRecordId.getAgencyId()));
                } else if (bibliographicRecordId.getBibliographicRecordIdentifierCode() != null) {
                    jaxbBibliographicRecordId.setBibliographicRecordIdentifierCode(
                        convertSVP(bibliographicRecordId.getBibliographicRecordIdentifierCode()));
                } else {

                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                        "AgencyId or BibliographicRecordIdentifierCode must be non-null in BibliographicRecordId.");

                }

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "BibliographicRecordIdentifier must be non-null in BibliographicRecordId.");

            }

        }

        return jaxbBibliographicRecordId;

    }

    protected List<BibliographicRecordId> createJAXBBibliographicRecordIds(List<org.extensiblecatalog.ncip.v2.service.BibliographicRecordId> svcBibliographicRecordIds) throws BindingException {

        List<BibliographicRecordId> jaxbBibliographicRecordIds = new ArrayList<BibliographicRecordId>();

        if (svcBibliographicRecordIds != null && svcBibliographicRecordIds.size() > 0) {

            for (org.extensiblecatalog.ncip.v2.service.BibliographicRecordId svcBibliographicRecordId : svcBibliographicRecordIds) {

                // TODO: Should we be testing for null returns?
                jaxbBibliographicRecordIds.add(createBibliographicRecordId(svcBibliographicRecordId));

            }

        }

        return jaxbBibliographicRecordIds;

    }

    protected BibliographicItemId createBibliographicItemId(
        org.extensiblecatalog.ncip.v2.service.BibliographicItemId bibliographicItemId) throws BindingException {
        BibliographicItemId jaxbBibliographicItemId = new BibliographicItemId();

        if (bibliographicItemId.getBibliographicItemIdentifier() != null) {

            jaxbBibliographicItemId.setBibliographicItemIdentifier(
                bibliographicItemId.getBibliographicItemIdentifier());

            if (bibliographicItemId.getBibliographicItemIdentifierCode() != null) {
                jaxbBibliographicItemId.setBibliographicItemIdentifierCode(
                    convertSVP(bibliographicItemId.getBibliographicItemIdentifierCode()));
            }

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "BibliographicItemIdentifier must be non-null in BibliographicItemId.");

        }

        return jaxbBibliographicItemId;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.BibliographicItemId> createSVCBibliographicItemIds(
        List<BibliographicItemId> jaxbBibliographicItemIds) throws BindingException {

        List<org.extensiblecatalog.ncip.v2.service.BibliographicItemId> svcBibliographicItemIds
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.BibliographicItemId>();

        if (jaxbBibliographicItemIds != null && jaxbBibliographicItemIds.size() > 0) {

            for (BibliographicItemId jaxbBibliographicItemId : jaxbBibliographicItemIds) {

                // TODO: Should we be testing for null returns?
                svcBibliographicItemIds.add(createBibliographicItemId(jaxbBibliographicItemId));

            }

        }

        return svcBibliographicItemIds;

    }

    protected org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData createLookupItemInitiationData(
        LookupItem lookupItemMsg)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData lookupItemInitiationData =
            new org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData();

        org.extensiblecatalog.ncip.v2.service.InitiationHeader initHdr = createInitiationHeader(
            lookupItemMsg.getInitiationHeader());
        if (initHdr != null) {
            lookupItemInitiationData.setInitiationHeader(initHdr);
        }

        lookupItemInitiationData.setCurrentBorrowerDesired(lookupItemMsg.getCurrentBorrowerDesired() != null);

        lookupItemInitiationData.setCurrentRequestersDesired(lookupItemMsg.getCurrentRequestersDesired() != null);

        List<SchemeValuePair> itemElementTypes = lookupItemMsg.getItemElementType();
        for (SchemeValuePair svp : itemElementTypes) {

            try {

                org.extensiblecatalog.ncip.v2.service.ItemElementType itemElementType =
                    org.extensiblecatalog.ncip.v2.service.ItemElementType.find(svp.getScheme(), svp.getValue());

                if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .BIBLIOGRAPHIC_DESCRIPTION)) {
                    lookupItemInitiationData.setBibliographicDescriptionDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .CIRCULATION_STATUS)) {
                    lookupItemInitiationData.setCirculationStatusDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .ELECTRONIC_RESOURCE)) {
                    lookupItemInitiationData.setElectronicResourceDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .HOLD_QUEUE_LENGTH)) {
                    lookupItemInitiationData.setHoldQueueLengthDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .ITEM_DESCRIPTION)) {
                    lookupItemInitiationData.setItemDescriptionDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .ITEM_USE_RESTRICTION_TYPE)) {
                    lookupItemInitiationData.setItemUseRestrictionTypeDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .LOCATION)) {
                    lookupItemInitiationData.setLocationDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .PHYSICAL_CONDITION)) {
                    lookupItemInitiationData.setPhysicalConditionDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .SECURITY_MARKER)) {
                    lookupItemInitiationData.setSecurityMarkerDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .SENSITIZATION_FLAG)) {
                    lookupItemInitiationData.setSensitizationFlagDesired(true);
                } else {
                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected enumeration value "
                        + (itemElementType.getScheme() != null ? itemElementType.getScheme() + " " : " ")
                        + itemElementType.getValue() + " in LookupItem.");
                }

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        if (lookupItemMsg.getItemId() != null) {
            lookupItemInitiationData.setItemId(createItemId(lookupItemMsg.getItemId()));
        }

        if (lookupItemMsg.getRequestId() != null) {
            lookupItemInitiationData.setRequestId(createRequestId(lookupItemMsg.getRequestId()));
        }

        return lookupItemInitiationData;
    }

    protected org.extensiblecatalog.ncip.v2.service.LookupItemResponseData createLookupItemResponseData(
        LookupItemResponse lookupItemResponseMsg) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.LookupItemResponseData lookupItemResponseData =
            new org.extensiblecatalog.ncip.v2.service.LookupItemResponseData();

        List<org.extensiblecatalog.ncip.v2.service.Problem> problems
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.Problem>();

        for (Object obj : lookupItemResponseMsg.getContent()) {

            if (obj instanceof JAXBElement) {
                JAXBElement jaxbElement = (JAXBElement)obj;
                if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("DateRecalled") == 0) {
                    lookupItemResponseData.setDateRecalled(convertDate(
                        (XMLGregorianCalendar)(jaxbElement.getValue())));
                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("DateRecalled") == 0) {
                    lookupItemResponseData.setHoldPickupDate(
                        convertDate((XMLGregorianCalendar)(jaxbElement.getValue())));
                } else {
                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected element "
                        + jaxbElement.getName().getLocalPart() + " in LookupItemResponse.");
                }
            } else if (obj instanceof ItemId) {
                lookupItemResponseData.setItemId(createItemId((ItemId)obj));
            } else if (obj instanceof RequestId) {
                lookupItemResponseData.setRequestId(createRequestId((RequestId)obj));
            } else if (obj instanceof ResponseHeader) {
                ResponseHeader jaxbResponseHeader = (ResponseHeader)obj;
                lookupItemResponseData.setResponseHeader(createResponseHeader(jaxbResponseHeader));
            } else if (obj instanceof Problem) {
                Problem ncipProblem = (Problem)obj;
                problems.add(createProblem(ncipProblem));
            } else if (obj instanceof ItemOptionalFields) {
                lookupItemResponseData.setItemOptionalFields(createItemOptionalFields((ItemOptionalFields)obj));
            } else if (obj instanceof ItemTransaction) {
                lookupItemResponseData.setItemTransaction(createItemTransaction((ItemTransaction)obj));
            } else {
                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected object class "
                    + obj.getClass().getName() + " in LookupItemResponse.");
            }

        }

        if (problems.size() > 0) {
            lookupItemResponseData.setProblems(problems);
        }

        return lookupItemResponseData;
    }

    protected org.extensiblecatalog.ncip.v2.service.LookupRequestInitiationData createLookupRequestInitiationData(
        LookupRequest lookupRequestMsg)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.LookupRequestInitiationData lookupRequestInitiationData =
            new org.extensiblecatalog.ncip.v2.service.LookupRequestInitiationData();

        org.extensiblecatalog.ncip.v2.service.InitiationHeader initHdr = createInitiationHeader(
            lookupRequestMsg.getInitiationHeader());
        if (initHdr != null) {
            lookupRequestInitiationData.setInitiationHeader(initHdr);
        }

        if (lookupRequestMsg.getUserId() != null
            || (lookupRequestMsg.getAuthenticationInput() != null
            && lookupRequestMsg.getAuthenticationInput().size() > 0)) {

            if (lookupRequestMsg.getItemId() != null
              && lookupRequestMsg.getRequestType() != null) {

                lookupRequestInitiationData.setUserId(createUserId(lookupRequestMsg.getUserId()));
                lookupRequestInitiationData.setAuthenticationInputs(createSVCAuthenticationInputs(
                    lookupRequestMsg.getAuthenticationInput()));
                lookupRequestInitiationData.setItemId(createItemId(lookupRequestMsg.getItemId()));
                lookupRequestInitiationData.setRequestType(convertRequestType(lookupRequestMsg.getRequestType()));

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "ItemId and RequestType must be non-null in Lookup Request if either UserId or AuthenticationInput are present.");

            }

        } else if (lookupRequestMsg.getRequestId() != null) {

            lookupRequestInitiationData.setRequestId(createRequestId(lookupRequestMsg.getRequestId()));

        } else {

            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "RequestId, UserId or AuthenticationInput must be non-null in Lookup Request.");

        }

        // TODO: Finish createLookupRequestInitiationData
        if (lookupRequestMsg.getRequestElementType() != null
            && lookupRequestMsg.getRequestElementType().size() > 0) {

            lookupRequestInitiationData.setRequestElementTypes(convertRequestElementTypes(
                lookupRequestMsg.getRequestElementType()));

        }

        List<SchemeValuePair> itemElementTypes = lookupRequestMsg.getItemElementType();
        for (SchemeValuePair svp : itemElementTypes) {

            try {

                org.extensiblecatalog.ncip.v2.service.ItemElementType itemElementType =
                    org.extensiblecatalog.ncip.v2.service.ItemElementType.find(svp.getScheme(), svp.getValue());

                if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .BIBLIOGRAPHIC_DESCRIPTION)) {
                    lookupRequestInitiationData.setBibliographicDescriptionDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .CIRCULATION_STATUS)) {
                    lookupRequestInitiationData.setCirculationStatusDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .ELECTRONIC_RESOURCE)) {
                    lookupRequestInitiationData.setElectronicResourceDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .HOLD_QUEUE_LENGTH)) {
                    lookupRequestInitiationData.setHoldQueueLengthDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .ITEM_DESCRIPTION)) {
                    lookupRequestInitiationData.setItemDescriptionDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .ITEM_USE_RESTRICTION_TYPE)) {
                    lookupRequestInitiationData.setItemUseRestrictionTypeDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .LOCATION)) {
                    lookupRequestInitiationData.setLocationDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .PHYSICAL_CONDITION)) {
                    lookupRequestInitiationData.setPhysicalConditionDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .SECURITY_MARKER)) {
                    lookupRequestInitiationData.setSecurityMarkerDesired(true);
                } else if (itemElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1ItemElementType
                    .SENSITIZATION_FLAG)) {
                    lookupRequestInitiationData.setSensitizationFlagDesired(true);
                } else {
                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected enumeration value "
                        + (itemElementType.getScheme() != null ? itemElementType.getScheme() + " " : " ")
                        + itemElementType.getValue() + " in LookupRequest.");
                }

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        if (lookupRequestMsg.getUserElementType() != null
            && lookupRequestMsg.getUserElementType().size() > 0) {

            List<SchemeValuePair> userElementTypes = lookupRequestMsg.getUserElementType();
            for (SchemeValuePair svp : userElementTypes) {

                try {

                    org.extensiblecatalog.ncip.v2.service.UserElementType userElementType =
                        org.extensiblecatalog.ncip.v2.service.UserElementType.find(svp.getScheme(), svp.getValue());

                    if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .AUTHENTICATION_INPUT)) {
                        lookupRequestInitiationData.setAuthenticationInputDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .BLOCK_OR_TRAP)) {
                        lookupRequestInitiationData.setBlockOrTrapDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .DATE_OF_BIRTH)) {
                        lookupRequestInitiationData.setDateOfBirthDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .NAME_INFORMATION)) {
                        lookupRequestInitiationData.setNameInformationDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_ADDRESS_INFORMATION)) {
                        lookupRequestInitiationData.setUserAddressInformationDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_LANGUAGE)) {
                        lookupRequestInitiationData.setUserLanguageDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_PRIVILEGE)) {
                        lookupRequestInitiationData.setUserPrivilegeDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .USER_ID)) {
                        lookupRequestInitiationData.setUserIdDesired(true);
                    } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                        .PREVIOUS_USER_ID)) {
                        lookupRequestInitiationData.setPreviousUserIdDesired(true);
                    } else {
                        throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected enumeration value "
                            + (userElementType.getScheme() != null ? userElementType.getScheme() + " " : " ")
                            + userElementType.getValue() + " in INIT_MSG_NAME.");
                    }

                } catch (ServiceException e) {

                    throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

                }
            }

        }

        return lookupRequestInitiationData;
    }

    protected org.extensiblecatalog.ncip.v2.service.LookupRequestResponseData createLookupRequestResponseData(
        LookupRequestResponse lookupRequestResponseMsg) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.LookupRequestResponseData lookupRequestResponseData =
            new org.extensiblecatalog.ncip.v2.service.LookupRequestResponseData();

        // TODO: Finish createLookupRequestResponseData
        List<org.extensiblecatalog.ncip.v2.service.Problem> problems
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.Problem>();

        for (Object obj : lookupRequestResponseMsg.getContent()) {

            if (obj instanceof JAXBElement) {
                JAXBElement jaxbElement = (JAXBElement)obj;
                if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("DateAvailable") == 0) {
                    lookupRequestResponseData.setDateAvailable(convertDate(
                        (XMLGregorianCalendar)(jaxbElement.getValue())));
                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("DateOfUserRequest") == 0) {
                    lookupRequestResponseData.setDateOfUserRequest(
                        convertDate((XMLGregorianCalendar)(jaxbElement.getValue())));
                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("EarliestDateNeeded") == 0) {
                    lookupRequestResponseData.setEarliestDateNeeded(
                        convertDate((XMLGregorianCalendar)(jaxbElement.getValue())));
                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("NeedBeforeDate") == 0) {
                    lookupRequestResponseData.setNeedBeforeDate(
                        convertDate((XMLGregorianCalendar)(jaxbElement.getValue())));
                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("PickupDate") == 0) {
                    lookupRequestResponseData.setPickupDate(
                        convertDate((XMLGregorianCalendar)(jaxbElement.getValue())));
                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("PickupExpiryDate") == 0) {
                    lookupRequestResponseData.setPickupExpiryDate(
                        convertDate((XMLGregorianCalendar)(jaxbElement.getValue())));
                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("HoldQueuePosition") == 0) {
                    lookupRequestResponseData.setHoldQueuePosition((BigDecimal)(jaxbElement.getValue()));
                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("PickupLocation") == 0) {
                    lookupRequestResponseData.setPickupLocation(convertPickupLocation((SchemeValuePair)(jaxbElement.getValue())));
                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("RequestScopeType") == 0) {
                    lookupRequestResponseData.setRequestScopeType(convertRequestScopeType((SchemeValuePair)(jaxbElement.getValue())));
                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("RequestStatusType") == 0) {
                    lookupRequestResponseData.setRequestStatusType(convertRequestStatusType((SchemeValuePair)(jaxbElement.getValue())));
                } else if (jaxbElement.getName().getLocalPart().compareToIgnoreCase("RequestType") == 0) {
                    lookupRequestResponseData.setRequestType(convertRequestType((SchemeValuePair)(jaxbElement.getValue())));
                } else {
                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected element "
                        + jaxbElement.getName().getLocalPart() + " in LookupRequestResponse.");
                }
            } else if (obj instanceof ResponseHeader) {
                lookupRequestResponseData.setResponseHeader(createResponseHeader((ResponseHeader)obj));
            } else if (obj instanceof Problem) {
                Problem ncipProblem = (Problem)obj;
                problems.add(createProblem(ncipProblem));
            } else if (obj instanceof AcknowledgedFeeAmount) {
                lookupRequestResponseData.setAcknowledgedFeeAmount(createAcknowledgedFeeAmount((AcknowledgedFeeAmount)obj));
            } else if (obj instanceof ItemId) {
                lookupRequestResponseData.setItemId(createItemId((ItemId)obj));
            } else if (obj instanceof ItemOptionalFields) {
                lookupRequestResponseData.setItemOptionalFields(createItemOptionalFields((ItemOptionalFields)obj));
            } else if (obj instanceof PaidFeeAmount) {
                lookupRequestResponseData.setPaidFeeAmount(createPaidFeeAmount((PaidFeeAmount)obj));
            } else if (obj instanceof RequestId) {
                lookupRequestResponseData.setRequestId(createRequestId((RequestId)obj));
            } else if (obj instanceof ShippingInformation) {
                lookupRequestResponseData.setShippingInformation(createShippingInformation((ShippingInformation)obj));
            } else if (obj instanceof UserId) {
                lookupRequestResponseData.setUserId(createUserId((UserId)obj));
            } else if (obj instanceof UserOptionalFields) {
                lookupRequestResponseData.setUserOptionalFields(createUserOptionalFields((UserOptionalFields)obj));
            } else {
                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected object class "
                    + obj.getClass().getName() + " in LookupRequestResponse.");
            }

        }

        if (problems.size() > 0) {
            lookupRequestResponseData.setProblems(problems);
        }

        return lookupRequestResponseData;

    }

    protected org.extensiblecatalog.ncip.v2.service.ItemTransaction createItemTransaction(
        ItemTransaction jaxbItemTransaction) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.ItemTransaction itemTransaction = null;

        if (jaxbItemTransaction != null) {

            itemTransaction = new org.extensiblecatalog.ncip.v2.service.ItemTransaction();

            if (jaxbItemTransaction.getCurrentBorrower() != null) {
                itemTransaction.setCurrentBorrower(createCurrentBorrower(jaxbItemTransaction.getCurrentBorrower()));
            }

            if (jaxbItemTransaction.getCurrentRequester() != null && jaxbItemTransaction.getCurrentRequester().size() > 0) {
                itemTransaction.setCurrentRequesters(createSVCCurrentRequesters(
                    jaxbItemTransaction.getCurrentRequester()));
            }

        }

        return itemTransaction;
    }

    protected List<org.extensiblecatalog.ncip.v2.service.CurrentRequester> createSVCCurrentRequesters(
        List<CurrentRequester> jaxbCurrentRequesters) throws BindingException {
        List<org.extensiblecatalog.ncip.v2.service.CurrentRequester> currentRequesters
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.CurrentRequester>();

        for (CurrentRequester currentRequester : jaxbCurrentRequesters) {
            currentRequesters.add(createCurrentRequester(currentRequester));
        }

        return currentRequesters;
    }

    protected org.extensiblecatalog.ncip.v2.service.CurrentRequester createCurrentRequester(
        CurrentRequester jaxbCurrentRequester)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.CurrentRequester currentRequester
            = new org.extensiblecatalog.ncip.v2.service.CurrentRequester();

        currentRequester.setUserId(createUserId(jaxbCurrentRequester.getUserId()));

        return currentRequester;

    }

    protected org.extensiblecatalog.ncip.v2.service.CurrentBorrower createCurrentBorrower(
        CurrentBorrower jaxbCurrentBorrower)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.CurrentBorrower currentBorrower = null;

        if (jaxbCurrentBorrower != null) {

            currentBorrower = new org.extensiblecatalog.ncip.v2.service.CurrentBorrower();
            currentBorrower.setUserId(createUserId(jaxbCurrentBorrower.getUserId()));
            
        }

        return currentBorrower;

    }

    protected org.extensiblecatalog.ncip.v2.service.UserId createUserId(UserId jaxbUserId) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.UserId userId = null;

        if (jaxbUserId != null) {

            userId = new org.extensiblecatalog.ncip.v2.service.UserId();

            if (jaxbUserId.getUserIdentifierValue() != null) {

                userId.setUserIdentifierValue(jaxbUserId.getUserIdentifierValue());

                if (jaxbUserId.getAgencyId() != null) {
                    userId.setAgencyId(createAgencyId(jaxbUserId.getAgencyId()));
                }

                if (jaxbUserId.getUserIdentifierType() != null) {
                    userId.setUserIdentifierType(convertUserIdentifierType(jaxbUserId.getUserIdentifierType()));
                }

            } else {

                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "UserIdentifierValue must be non-null in UserId.");

            }

        }

        return userId;
    }

    protected org.extensiblecatalog.ncip.v2.service.UserIdentifierType convertUserIdentifierType(
        SchemeValuePair jaxbUserIdentifierType) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.UserIdentifierType svcUserIdentifierType = null;

        if (jaxbUserIdentifierType != null) {

            try {

                svcUserIdentifierType = org.extensiblecatalog.ncip.v2.service.UserIdentifierType.find(
                    jaxbUserIdentifierType.getScheme(), jaxbUserIdentifierType.getValue());

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return svcUserIdentifierType;
    }

    protected GregorianCalendar convertDate(XMLGregorianCalendar jaxbCalendar) {

        GregorianCalendar gregorianCalendar = null;

        if (jaxbCalendar != null) {

            gregorianCalendar = jaxbCalendar.toGregorianCalendar();
        }

        return gregorianCalendar;

    }

    protected NCIPMessage createInitiationMessage(
        org.extensiblecatalog.ncip.v2.service.NCIPInitiationData initiationData)
        throws BindingException {
        NCIPMessage ncipMsg;
        if (initiationData instanceof org.extensiblecatalog.ncip.v2.service.AcceptItemInitiationData) {
            ncipMsg = createAcceptItemInitiationMessage(
                (org.extensiblecatalog.ncip.v2.service.AcceptItemInitiationData)initiationData);
        } else if (initiationData instanceof org.extensiblecatalog.ncip.v2.service.CheckInItemInitiationData) {
            ncipMsg = createCheckInItemInitiationMessage(
                (org.extensiblecatalog.ncip.v2.service.CheckInItemInitiationData)initiationData);
        } else if (initiationData instanceof org.extensiblecatalog.ncip.v2.service.CheckOutItemInitiationData) {
            ncipMsg = createCheckOutItemInitiationMessage(
                (org.extensiblecatalog.ncip.v2.service.CheckOutItemInitiationData)initiationData);
        } else if (initiationData instanceof org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData) {
            ncipMsg = createLookupItemInitiationMessage(
                (org.extensiblecatalog.ncip.v2.service.LookupItemInitiationData)initiationData);
        } else if (initiationData instanceof org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData) {
            ncipMsg = createLookupItemSetInitiationMessage(
                (org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData)initiationData);
        } else if (initiationData instanceof org.extensiblecatalog.ncip.v2.service.LookupRequestInitiationData) {
            ncipMsg = createLookupRequestInitiationMessage(
                (org.extensiblecatalog.ncip.v2.service.LookupRequestInitiationData)initiationData);
        } else if (initiationData instanceof org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData) {
            ncipMsg = createLookupUserInitiationMessage(
                (org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData)initiationData);
        } else if (initiationData instanceof org.extensiblecatalog.ncip.v2.service.RequestItemInitiationData) {
            ncipMsg = createRequestItemInitiationMessage(
                (org.extensiblecatalog.ncip.v2.service.RequestItemInitiationData)initiationData);
        } else if (initiationData instanceof org.extensiblecatalog.ncip.v2.service.RenewItemInitiationData) {
            ncipMsg = createRenewItemInitiationMessage(
                (org.extensiblecatalog.ncip.v2.service.RenewItemInitiationData)initiationData);
        } else {
            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "Unsupported NCIPInitiationData sub-class: " + initiationData.getClass().getName());
        }
        return ncipMsg;
    }

    @Override
    public ByteArrayInputStream createInitiationMessageStream(ServiceContext serviceContext,
        org.extensiblecatalog.ncip.v2.service.NCIPInitiationData initiationData)
        throws org.extensiblecatalog.ncip.v2.service.ServiceException {
        NCIPMessage ncipMsg;
        try {
            ncipMsg = createInitiationMessage(initiationData);
        } catch (BindingException e) {
            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "BindingException creating the NCIPMessage from the NCIPInitiationData object.", e);
        }

        return createMsgStream(ncipMsg);

    }

    @Override
    public org.extensiblecatalog.ncip.v2.service.NCIPInitiationData createInitiationData(
        ServiceContext serviceContext, InputStream inputStream)
        throws org.extensiblecatalog.ncip.v2.service.ServiceException {

        try {

            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage initiationMsg
                = createNCIPMessage(inputStream);

            org.extensiblecatalog.ncip.v2.service.NCIPInitiationData initiationData
                = createInitiationData(initiationMsg);

            return initiationData;

        } catch (BindingException e) {
            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "BindingException converting from NCIPMessage to NCIPInitiationData.", e);
        }

    }

    protected NCIPMessage createNCIPMessage(InputStream inputStream)
        throws org.extensiblecatalog.ncip.v2.service.ServiceException {

        try {

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            if (schema != null) {

                unmarshaller.setSchema(schema);

            }

            try {

                org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage ncipMsg =
                    (org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage)
                        unmarshaller.unmarshal(inputStream);

                return ncipMsg;

            } catch (JAXBException e) {
                throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                    org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                    "JAXBException unmarshalling from InputStream.", e);
            }

        } catch (JAXBException e) {
            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.RUNTIME_ERROR,
                "JAXBException creating the unmarshaller.", e);
        }

    }

    protected NCIPMessage createResponseMessage(ServiceContext serviceContext, org.extensiblecatalog.ncip.v2.service.NCIPResponseData responseData)
        throws BindingException {
        NCIPMessage ncipMsg;
        if (responseData instanceof org.extensiblecatalog.ncip.v2.service.AcceptItemResponseData) {
            ncipMsg = createAcceptItemResponseMessage(
                (org.extensiblecatalog.ncip.v2.service.AcceptItemResponseData)responseData);
        } else if (responseData instanceof org.extensiblecatalog.ncip.v2.service.CheckInItemResponseData) {
            ncipMsg = createCheckInItemResponseMessage(
                (org.extensiblecatalog.ncip.v2.service.CheckInItemResponseData)responseData);
        } else if (responseData instanceof org.extensiblecatalog.ncip.v2.service.CheckOutItemResponseData) {
            ncipMsg = createCheckOutItemResponseMessage(
                (org.extensiblecatalog.ncip.v2.service.CheckOutItemResponseData)responseData);
        } else if (responseData instanceof org.extensiblecatalog.ncip.v2.service.LookupItemResponseData) {
            ncipMsg = createLookupItemResponseMessage(
                (org.extensiblecatalog.ncip.v2.service.LookupItemResponseData)responseData);
        } else if (responseData instanceof org.extensiblecatalog.ncip.v2.service.LookupRequestResponseData) {
            ncipMsg = createLookupRequestResponseMessage(
                (org.extensiblecatalog.ncip.v2.service.LookupRequestResponseData)responseData);
        } else if (responseData instanceof org.extensiblecatalog.ncip.v2.service.LookupUserResponseData) {
            ncipMsg = createLookupUserResponseMessage(
                (org.extensiblecatalog.ncip.v2.service.LookupUserResponseData)responseData);
        } else if (responseData instanceof org.extensiblecatalog.ncip.v2.service.RenewItemResponseData) {
            ncipMsg = createRenewItemResponseMessage(
                (org.extensiblecatalog.ncip.v2.service.RenewItemResponseData)responseData);
        } else if (responseData instanceof org.extensiblecatalog.ncip.v2.service.RequestItemResponseData) {
            ncipMsg = createRequestItemResponseMessage(
                (org.extensiblecatalog.ncip.v2.service.RequestItemResponseData)responseData);
        } else if (responseData instanceof org.extensiblecatalog.ncip.v2.service.ProblemResponseData) {
            ncipMsg = createProblemResponseMessage(
                (org.extensiblecatalog.ncip.v2.service.ProblemResponseData)responseData);
        } else if (responseData instanceof org.extensiblecatalog.ncip.v2.service.LookupItemSetResponseData) {
            ncipMsg = createLookupItemSetResponseMessage(
                (org.extensiblecatalog.ncip.v2.service.LookupItemSetResponseData)responseData);
        } else {
            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "Unsupported NCIPResponseData sub-class: " + responseData.getClass().getName());
        }
        return ncipMsg;
    }

//    protected byte[] createResponseMessageBytes(org.extensiblecatalog.ncip.v2.service.NCIPResponseData responseData)
//        throws org.extensiblecatalog.ncip.v2.service.ServiceException {
//        NCIPMessage ncipMsg;
//        try {
//            ncipMsg = createResponseMessage(serviceContext, responseData);
//        } catch (BindingException e) {
//            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
//                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
//                "BindingException creating the NCIPMessage from the NCIPResponseData object.", e);
//        }
//
//        return createMsgBytes(ncipMsg);
//    }
//
    @Override
    public ByteArrayInputStream createResponseMessageStream(ServiceContext serviceContext,
        org.extensiblecatalog.ncip.v2.service.NCIPResponseData responseData)
        throws org.extensiblecatalog.ncip.v2.service.ServiceException {
        NCIPMessage ncipMsg;
        try {
            ncipMsg = createResponseMessage(serviceContext, responseData);
        } catch (BindingException e) {
            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "BindingException creating the NCIPMessage from the NCIPResponseData object.", e);
        }

        return createMsgStream(ncipMsg);
    }

    protected byte[] createMsgBytes(NCIPMessage ncipMsg) throws org.extensiblecatalog.ncip.v2.service.ServiceException {

        Marshaller m;
        try {
            m = jaxbContext.createMarshaller();
            if (schema != null) {
                m.setSchema(schema);
            }
        } catch (JAXBException e) {
            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.RUNTIME_ERROR,
                "JAXBException creating the Mashaller.", e);
        }

        // TODO: Consider whether these are needed:
        // m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);
        // m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, noNamespaceSchemaLocation);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(0);

        try {
            m.marshal(ncipMsg, byteArrayOutputStream);
        } catch (JAXBException e) {
            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "JAXBException marshalling the message.", e);
        }

        return byteArrayOutputStream.toByteArray();

    }

    protected ByteArrayInputStream createMsgStream(NCIPMessage ncipMsg)
        throws org.extensiblecatalog.ncip.v2.service.ServiceException {

        Marshaller m;
        try {
            m = jaxbContext.createMarshaller();
            if (schema != null) {
                m.setSchema(schema);
            }
        } catch (JAXBException e) {
            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.RUNTIME_ERROR,
                "JAXBException creating the Mashaller.", e);
        }

        // TODO Consider whether these are needed:
        // m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, schemaLocation);
        // m.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, noNamespaceSchemaLocation);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(0);

        try {
            m.marshal(ncipMsg, byteArrayOutputStream);
        } catch (JAXBException e) {
            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "JAXBException marshalling the message.", e);
        }

        byte[] bytes = byteArrayOutputStream.toByteArray();

        ByteArrayInputStream resultStream = new ByteArrayInputStream(bytes);
        return resultStream;

    }

    @Override
    public org.extensiblecatalog.ncip.v2.service.NCIPResponseData createResponseData(
        ServiceContext serviceContext, InputStream responseMsgInputStream)
        throws org.extensiblecatalog.ncip.v2.service.ServiceException {

        try {

            org.extensiblecatalog.ncip.v2.binding.jaxb.elements.NCIPMessage responseMsg
                = createNCIPMessage(responseMsgInputStream);

            org.extensiblecatalog.ncip.v2.service.NCIPResponseData responseData = createResponseData(responseMsg);

            return responseData;

        } catch (BindingException e) {
            throw new org.extensiblecatalog.ncip.v2.service.ServiceException(
                org.extensiblecatalog.ncip.v2.service.ServiceError.INVALID_MESSAGE_FORMAT,
                "BindingException converting from NCIPMessage to NCIPResponseData.", e);
        }

    }

    protected org.extensiblecatalog.ncip.v2.service.NCIPResponseData createResponseData(NCIPMessage respMsg)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.NCIPResponseData responseData;
        if (respMsg.getAcceptItemResponse() != null) {
            responseData = createAcceptItemResponseData(respMsg.getAcceptItemResponse());
        } else if (respMsg.getCheckInItemResponse() != null) {
            responseData = createCheckInItemResponseData(respMsg.getCheckInItemResponse());
        } else if (respMsg.getCheckOutItemResponse() != null) {
            responseData = createCheckOutItemResponseData(respMsg.getCheckOutItemResponse());
        } else if (respMsg.getLookupItemResponse() != null) {
            responseData = createLookupItemResponseData(respMsg.getLookupItemResponse());
        } else if (respMsg.getLookupRequestResponse() != null) {
            responseData = createLookupRequestResponseData(respMsg.getLookupRequestResponse());
        } else if (respMsg.getLookupUserResponse() != null) {
            responseData = createLookupUserResponseData(respMsg.getLookupUserResponse());
        } else if (respMsg.getRenewItemResponse() != null) {
            responseData = createRenewItemResponseData(respMsg.getRenewItemResponse());
        } else if (respMsg.getRequestItemResponse() != null) {
            responseData = createRequestItemResponseData(respMsg.getRequestItemResponse());
        } else if (respMsg.getProblem() != null && respMsg.getProblem().size() > 0) {
            responseData = createProblemResponseData(respMsg.getProblem());
        } else if (respMsg.getExt() != null) {
            Ext extension = respMsg.getExt();
            Object extensionMsg = extension.getAny().get(0);
            if (extensionMsg instanceof LookupItemSetResponse) {
                responseData = createLookupItemSetResponseData((LookupItemSetResponse)extensionMsg);
            }
            else {
                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unsupported Extension: "
                    + extensionMsg.getClass().getName() + ".");
            }
        } else {
            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unsupported Response Message type.");
        }

        return responseData;
    }

    protected org.extensiblecatalog.ncip.v2.service.NCIPResponseData createProblemResponseData(List<Problem> problems)
        throws BindingException {
        org.extensiblecatalog.ncip.v2.service.ProblemResponseData responseData
            = new org.extensiblecatalog.ncip.v2.service.ProblemResponseData();
        responseData.setProblems(createSVCProblems(problems));
        return responseData;
    }
    /*
         Added by David 10-03-2010
      */

    /*
       The actual funtion that transits a LookupUser message
             from Service scope to JAXB scope
        when it's from Service to JAXB, the order of elements
             should be enforced,  it should follow the
             sequence defined in the xsd file
      */

    protected NCIPMessage createLookupUserInitiationMessage(
        org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData lookupUserInitiationData)
        throws BindingException {

        NCIPMessage ncipInitMsg = new NCIPMessage();
        ncipInitMsg.setVersion(NCIP_VERSION_V2_0);
        LookupUser jaxbLookupUser = new LookupUser();

        ncipInitMsg.setLookupUser(jaxbLookupUser);

        //process header
        if (lookupUserInitiationData.getInitiationHeader() != null) {
            jaxbLookupUser.setInitiationHeader(
                createInitiationHeader(
                    lookupUserInitiationData.getInitiationHeader()));
        }

        //User Id and AuthenticationInput
        if (lookupUserInitiationData.getUserId() != null) {
            jaxbLookupUser.setUserId(createUserId(lookupUserInitiationData.getUserId()));
            if (lookupUserInitiationData.getAuthenticationInputs() != null
                && lookupUserInitiationData.getAuthenticationInputs().size() > 0) {
                jaxbLookupUser.getAuthenticationInput().addAll(
                    createJAXBAuthenticationInputs(
                        lookupUserInitiationData.getAuthenticationInputs()));
            }
        } else {
            if (lookupUserInitiationData.getAuthenticationInputs() != null
                && lookupUserInitiationData.getAuthenticationInputs().size() > 0) {
                jaxbLookupUser.getAuthenticationInput().addAll(
                    createJAXBAuthenticationInputs(
                        lookupUserInitiationData.getAuthenticationInputs()));
            } else {
                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "UserId or AuthenticationInput must be non-null in Lookup User.");
            }
        }

        //UserElementType
        if (lookupUserInitiationData.getAuthenticationInputDesired()) {
            jaxbLookupUser.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.AUTHENTICATION_INPUT));
        }

        if (lookupUserInitiationData.getBlockOrTrapDesired()) {
            jaxbLookupUser.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.BLOCK_OR_TRAP));
        }

        if (lookupUserInitiationData.getDateOfBirthDesired()) {
            jaxbLookupUser.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.DATE_OF_BIRTH));
        }

        if (lookupUserInitiationData.getNameInformationDesired()) {
            jaxbLookupUser.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.NAME_INFORMATION));
        }

        if (lookupUserInitiationData.getUserAddressInformationDesired()) {
            jaxbLookupUser.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_ADDRESS_INFORMATION));
        }

        if (lookupUserInitiationData.getUserLanguageDesired()) {
            jaxbLookupUser.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_LANGUAGE));
        }

        if (lookupUserInitiationData.getUserPrivilegeDesired()) {
            jaxbLookupUser.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_PRIVILEGE));
        }

        if (lookupUserInitiationData.getUserIdDesired()) {
            jaxbLookupUser.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_ID));
        }

        if (lookupUserInitiationData.getPreviousUserIdDesired()) {
            jaxbLookupUser.getUserElementType().add(convertSVP(
                org.extensiblecatalog.ncip.v2.service.Version1UserElementType.PREVIOUS_USER_ID));
        }

        //LoanedItemsDesired
        /* Note: In the service scope LoadItemDesired has a definite type, boolean.
         *     whereas in the JAXB scope it is a blank class, generated by XSD.
         *     there would be no value in the created instance from the class by objectFatory,
         *     the only judgement upon it would be null-detection.
         */
        if (lookupUserInitiationData.getLoanedItemsDesired()) {
            jaxbLookupUser.setLoanedItemsDesired(
                objectFactory.createLoanedItemsDesired());
        }

        //RequestedItemsDesired
        if (lookupUserInitiationData.getRequestedItemsDesired()) {
            jaxbLookupUser.setRequestedItemsDesired(
                objectFactory.createRequestedItemsDesired());
        }

        //UserFiscalAccountDesired
        if (lookupUserInitiationData.getUserFiscalAccountDesired()) {
            jaxbLookupUser.setUserFiscalAccountDesired(
                objectFactory.createUserFiscalAccountDesired());
        }

        //all user element types processing, may not be strongly related to look up user service
        if (lookupUserInitiationData.getAuthenticationInputDesired()) {
            jaxbLookupUser.getUserElementType().add(
                convertSVP(org.extensiblecatalog.ncip.v2.service.Version1UserElementType.AUTHENTICATION_INPUT));
        }

        if (lookupUserInitiationData.getBlockOrTrapDesired()) {
            jaxbLookupUser.getUserElementType().add(
                convertSVP(org.extensiblecatalog.ncip.v2.service.Version1UserElementType.BLOCK_OR_TRAP));
        }

        if (lookupUserInitiationData.getDateOfBirthDesired()) {
            jaxbLookupUser.getUserElementType().add(
                convertSVP(org.extensiblecatalog.ncip.v2.service.Version1UserElementType.DATE_OF_BIRTH));
        }

        if (lookupUserInitiationData.getNameInformationDesired()) {
            jaxbLookupUser.getUserElementType().add(
                convertSVP(org.extensiblecatalog.ncip.v2.service.Version1UserElementType.NAME_INFORMATION));
        }

        if (lookupUserInitiationData.getUserAddressInformationDesired()) {
            jaxbLookupUser.getUserElementType().add(
                convertSVP(org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_ADDRESS_INFORMATION));
        }

        if (lookupUserInitiationData.getUserLanguageDesired()) {
            jaxbLookupUser.getUserElementType().add(
                convertSVP(org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_LANGUAGE));
        }

        if (lookupUserInitiationData.getUserPrivilegeDesired()) {
            jaxbLookupUser.getUserElementType().add(
                convertSVP(org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_PRIVILEGE));
        }

        if (lookupUserInitiationData.getUserIdDesired()) {
            jaxbLookupUser.getUserElementType().add(
                convertSVP(org.extensiblecatalog.ncip.v2.service.Version1UserElementType.USER_ID));
        }

        if (lookupUserInitiationData.getPreviousUserIdDesired()) {
            jaxbLookupUser.getUserElementType().add(
                convertSVP(org.extensiblecatalog.ncip.v2.service.Version1UserElementType.PREVIOUS_USER_ID));
        }

        return ncipInitMsg;
    }

    /*
       The actual funtion that transits a LookupUser message
             from JAXB scope to Service scope
       when it's from JAXB to Service, the order of elements
             is not enforced
      */

    protected org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData createLookupUserInitiationData(
        LookupUser lookupUserMsg)
        throws BindingException {

        org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData lookupUserInitiationData =
            new org.extensiblecatalog.ncip.v2.service.LookupUserInitiationData();

        org.extensiblecatalog.ncip.v2.service.InitiationHeader initHdr
            = createInitiationHeader(lookupUserMsg.getInitiationHeader());
        if (initHdr != null) {
            lookupUserInitiationData.setInitiationHeader(initHdr);
        }

        if (lookupUserMsg.getUserId() != null) {
            lookupUserInitiationData.setUserId(
                createUserId(lookupUserMsg.getUserId()));
        } else if (lookupUserMsg.getAuthenticationInput() != null) {
            lookupUserInitiationData.setAuthenticationInputs(
                createSVCAuthenticationInputs(lookupUserMsg.getAuthenticationInput()));
        } else {
            throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                "UserId or AuthenticationInput must be non-null in Lookup User.");
        }

        lookupUserInitiationData.setLoanedItemsDesired(lookupUserMsg.getLoanedItemsDesired() != null);

        lookupUserInitiationData.setRequestedItemsDesired(lookupUserMsg.getRequestedItemsDesired() != null);

        lookupUserInitiationData.setUserFiscalAccountDesired(lookupUserMsg.getUserFiscalAccountDesired() != null);

        List<SchemeValuePair> userElementTypes = lookupUserMsg.getUserElementType();
        for (SchemeValuePair svp : userElementTypes) {

            try {

                org.extensiblecatalog.ncip.v2.service.UserElementType userElementType =
                    org.extensiblecatalog.ncip.v2.service.UserElementType.find(svp.getScheme(), svp.getValue());

                if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                    .AUTHENTICATION_INPUT)) {
                    lookupUserInitiationData.setAuthenticationInputDesired(true);
                } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                    .BLOCK_OR_TRAP)) {
                    lookupUserInitiationData.setBlockOrTrapDesired(true);
                } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                    .DATE_OF_BIRTH)) {
                    lookupUserInitiationData.setDateOfBirthDesired(true);
                } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                    .NAME_INFORMATION)) {
                    lookupUserInitiationData.setNameInformationDesired(true);
                } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                    .USER_ADDRESS_INFORMATION)) {
                    lookupUserInitiationData.setUserAddressInformationDesired(true);
                } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                    .USER_LANGUAGE)) {
                    lookupUserInitiationData.setUserLanguageDesired(true);
                } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                    .USER_PRIVILEGE)) {
                    lookupUserInitiationData.setUserPrivilegeDesired(true);
                } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                    .USER_ID)) {
                    lookupUserInitiationData.setUserIdDesired(true);
                } else if (userElementType.equals(org.extensiblecatalog.ncip.v2.service.Version1UserElementType
                    .PREVIOUS_USER_ID)) {
                    lookupUserInitiationData.setPreviousUserIdDesired(true);
                } else {
                    throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Unexpected enumeration value "
                        + (userElementType.getScheme() != null ? userElementType.getScheme() + " " : " ")
                        + userElementType.getValue() + " in LookupUser.");
                }

            } catch (ServiceException e) {

                throw new BindingException(BindingError.INVALID_SCHEME_VALUE, e.getLocalizedMessage(), e);

            }
        }

        return lookupUserInitiationData;
    }

    /*
       The actual funtion that transits a LookupUserResponse message
             from service scope to JAXB scope
       when it's from Service to JAXB , the order of elements
             is  enforced
      */

    protected NCIPMessage createLookupUserResponseMessage(
        org.extensiblecatalog.ncip.v2.service.LookupUserResponseData lookupUserResponseData)
        throws BindingException {

        NCIPMessage ncipRespMsg = new NCIPMessage();
        ncipRespMsg.setVersion(NCIP_VERSION_V2_0);
        LookupUserResponse lookupUserResponse = new LookupUserResponse();

        ncipRespMsg.setLookupUserResponse(lookupUserResponse);

        if (lookupUserResponseData.getResponseHeader() != null) {
            ResponseHeader respHdr = createResponseHeader(lookupUserResponseData.getResponseHeader());
            lookupUserResponse.setResponseHeader(respHdr);
        }

        if (lookupUserResponseData.getProblems() != null
            && lookupUserResponseData.getProblems().size() > 0) {
            lookupUserResponse.getProblem().addAll(createJAXBProblems(lookupUserResponseData.getProblems()));
        } else {
            if (lookupUserResponseData.getUserId() != null) {
                lookupUserResponse.setUserId(createUserId(lookupUserResponseData.getUserId()));
            } else {
                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT,
                    "UserId must be non-null in Lookup User Response.");
            }

            if (lookupUserResponseData.getUserFiscalAccounts() != null
                && lookupUserResponseData.getUserFiscalAccounts().size() > 0) {
                lookupUserResponse.getUserFiscalAccount().addAll(
                    scopeConvertSVC2JAX.createUserFiscalAccounts(lookupUserResponseData.getUserFiscalAccounts()));
            }

            //LoanedItemsCount
            if (lookupUserResponseData.getLoanedItemsCounts() != null
                && lookupUserResponseData.getLoanedItemsCounts().size() > 0) {
                lookupUserResponse.getLoanedItemsCount().addAll(
                    scopeConvertSVC2JAX.createLoanedItemsCounts(lookupUserResponseData.getLoanedItemsCounts()));
            }

            //LoanedItem
            if (lookupUserResponseData.getLoanedItems() != null
                && lookupUserResponseData.getLoanedItems().size() > 0) {
                lookupUserResponse.getLoanedItem().addAll(
                    scopeConvertSVC2JAX.createLoanedItems(lookupUserResponseData.getLoanedItems()));
            }

            //RequestedItemsCount
            if (lookupUserResponseData.getRequestedItemsCounts() != null
                && lookupUserResponseData.getRequestedItemsCounts().size() > 0) {
                lookupUserResponse.getRequestedItemsCount().addAll(
                    scopeConvertSVC2JAX.createRequestedItemsCounts(
                        lookupUserResponseData.getRequestedItemsCounts()));
            }

            //RequestedItems
            if (lookupUserResponseData.getRequestedItems() != null
                && lookupUserResponseData.getRequestedItems().size() > 0) {
                lookupUserResponse.getRequestedItem().addAll(
                    scopeConvertSVC2JAX.createRequestedItems(lookupUserResponseData.getRequestedItems()));
            }

            //UserOptionalFields
            if (lookupUserResponseData.getUserOptionalFields() != null) {
                lookupUserResponse.setUserOptionalFields(createUserOptionalFields(
                    lookupUserResponseData.getUserOptionalFields()));
            }
        }

        return ncipRespMsg;
    }

    /*
       The actual funtion that transits a LookupUserResponse message
             from JAXB scope to service scope
       when it's from JAXB to Service , the order of elements
             is not  enforced
      */

    protected org.extensiblecatalog.ncip.v2.service.LookupUserResponseData createLookupUserResponseData(
        LookupUserResponse lookupUserResponseMsg) throws BindingException {

        org.extensiblecatalog.ncip.v2.service.LookupUserResponseData lookupUserResponseData =
            new org.extensiblecatalog.ncip.v2.service.LookupUserResponseData();

        List<org.extensiblecatalog.ncip.v2.service.Problem> problems
            = new ArrayList<org.extensiblecatalog.ncip.v2.service.Problem>();
        problems.addAll(createSVCProblems(lookupUserResponseMsg.getProblem()));
        if (problems.size() > 0) {
            lookupUserResponseData.setProblems(problems);
        } else {
            boolean validMsg = false;
            //userid
            if (lookupUserResponseMsg.getUserId() != null) {
                lookupUserResponseData.setUserId(createUserId(lookupUserResponseMsg.getUserId()));
                validMsg = true;
            }

            //userfiscalaccount
            if (lookupUserResponseMsg.getUserFiscalAccount() != null
                && lookupUserResponseMsg.getUserFiscalAccount().size() > 0) {
                List<org.extensiblecatalog.ncip.v2.service.UserFiscalAccount> userFiscalAccounts
                    = new ArrayList<org.extensiblecatalog.ncip.v2.service.UserFiscalAccount>();
                userFiscalAccounts.addAll(scopeConvertJAX2SVC.createUserFiscalAccounts(
                    lookupUserResponseMsg.getUserFiscalAccount()));
                lookupUserResponseData.setUserFiscalAccounts(userFiscalAccounts);

                validMsg = true;
            }

            if (lookupUserResponseMsg.getLoanedItemsCount() != null
                && lookupUserResponseMsg.getLoanedItemsCount().size() > 0) {
                List<org.extensiblecatalog.ncip.v2.service.LoanedItemsCount> loanedItemsCounts
                    = new ArrayList<org.extensiblecatalog.ncip.v2.service.LoanedItemsCount>();
                loanedItemsCounts.addAll(scopeConvertJAX2SVC.createLoanedItemsCounts(
                    lookupUserResponseMsg.getLoanedItemsCount()));
                lookupUserResponseData.setLoanedItemsCounts(loanedItemsCounts);

                validMsg = true;
            }

            if (lookupUserResponseMsg.getLoanedItem() != null
                && lookupUserResponseMsg.getLoanedItem().size() > 0) {
                List<org.extensiblecatalog.ncip.v2.service.LoanedItem> loanedItem
                    = new ArrayList<org.extensiblecatalog.ncip.v2.service.LoanedItem>();
                loanedItem.addAll(scopeConvertJAX2SVC.createLoanedItems(lookupUserResponseMsg.getLoanedItem()));
                lookupUserResponseData.setLoanedItems(loanedItem);

                validMsg = true;
            }

            if (lookupUserResponseMsg.getRequestedItemsCount() != null
                && lookupUserResponseMsg.getRequestedItemsCount().size() > 0) {
                List<org.extensiblecatalog.ncip.v2.service.RequestedItemsCount> requestedItemsCounts
                    = new ArrayList<org.extensiblecatalog.ncip.v2.service.RequestedItemsCount>();
                requestedItemsCounts.addAll(scopeConvertJAX2SVC.createRequestedItemsCounts(
                    lookupUserResponseMsg.getRequestedItemsCount()));
                lookupUserResponseData.setRequestedItemsCounts(requestedItemsCounts);

                validMsg = true;
            }

            if (lookupUserResponseMsg.getRequestedItem() != null
                && lookupUserResponseMsg.getRequestedItem().size() > 0) {
                List<org.extensiblecatalog.ncip.v2.service.RequestedItem> requestedItem
                    = new ArrayList<org.extensiblecatalog.ncip.v2.service.RequestedItem>();
                requestedItem.addAll(scopeConvertJAX2SVC.createRequestedItems(
                    lookupUserResponseMsg.getRequestedItem()));
                lookupUserResponseData.setRequestedItems(requestedItem);

                validMsg = true;
            }

            //UserOptionalFields
            if (lookupUserResponseData.getUserOptionalFields() != null) {
                lookupUserResponseData.setUserOptionalFields(
                    createUserOptionalFields(
                        lookupUserResponseMsg.getUserOptionalFields()));

                validMsg = true;
            }

            if (!validMsg) {
                throw new BindingException(BindingError.INVALID_MESSAGE_FORMAT, "Invalid message "
                    + lookupUserResponseMsg.toString() + " in LookupUserResponse.");
            }
        }
        return lookupUserResponseData;
    }

}
