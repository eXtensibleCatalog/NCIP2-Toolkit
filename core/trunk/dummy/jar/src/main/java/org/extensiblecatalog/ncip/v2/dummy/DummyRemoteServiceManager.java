/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;


import org.extensiblecatalog.ncip.v2.service.*;

import java.util.*;


/**
 * ServiceManager is responsible for locating the correct back-end service; for
 * the Dummy back-end there are no services; this class always returns the same hard-coded values.
 * Note: If you're looking for a model of how to code your own ILS's RemoteServiceManager, do not
 * use this class's methods as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class DummyRemoteServiceManager implements RemoteServiceManager {

    protected static DummyDatabase database = new DummyDatabase();
    protected DummyConfiguration config;

    /**
     * Construct a DummyRemoteServiceManager; this class is not configurable so there are no parameters.
     */
    public DummyRemoteServiceManager() throws ToolkitException {

        this.config = new DummyConfiguration();

    }

    /**
     * Construct a DummyRemoteServiceManager; the properties are ignored.
     */
    public DummyRemoteServiceManager(Properties properties) throws ToolkitException {

        this.config = new DummyConfiguration(properties);

    }

    /**
     * Get the library's name.
     *
     * @return the library name
     */
    public String getLibraryName() {

        return config.getName();

    }

    public CurrencyCode getLocalCurrencyCode() {

        try {

            return CurrencyCode.find(Version1CurrencyCode.VERSION_1_CURRENCY_CODE,
                Currency.getInstance(Locale.getDefault()).getCurrencyCode());

        } catch (ServiceException e) {

            return Version1CurrencyCode.CUC; // Purposely choosing a code that won't be expected in most locales. 

        }

    }

    public AgencyId getAgencyId() {

        return config.getAgencyId();

    }

    public static CirculationStatus translateCircStatus(DummyDatabase.CircStatus circStatus) {

        CirculationStatus ncipCircStatus;

            switch ( circStatus) {

                case ON_ORDER :
                    ncipCircStatus = Version1CirculationStatus.ON_ORDER;
                    break;

                case ON_SHELF :
                    ncipCircStatus = Version1CirculationStatus.AVAILABLE_ON_SHELF;
                    break;

                case CHECKED_OUT :
                    ncipCircStatus = Version1CirculationStatus.ON_LOAN;
                    break;

                case IN_TRANSIT :
                    ncipCircStatus = Version1CirculationStatus.IN_TRANSIT_BETWEEN_LIBRARY_LOCATIONS;
                    break;

                default :
                    ncipCircStatus = Version1CirculationStatus.CIRCULATION_STATUS_UNDEFINED;

            }

        return ncipCircStatus;

    }

    public MediumType translateMediaType(DummyDatabase.MediaTypeEnum mediaType) {

        MediumType ncipMediumType;

        switch ( mediaType) {

            case BOOK :
                ncipMediumType = Version1MediumType.BOOK;
                break;

            case AUDIO_TAPE :
                ncipMediumType = Version1MediumType.AUDIO_TAPE;
                break;

            case CD :
                ncipMediumType = Version1MediumType.CD_ROM;
                break;

            case DVD :
                ncipMediumType = Version1MediumType.CD_ROM;
                break;

            case MAGAZINE :
                ncipMediumType = Version1MediumType.MAGAZINE;
                break;

            default :
                ncipMediumType = Version1MediumType.BOOK;
                break;

        }

        return ncipMediumType;


    }

    public BibliographicDescription getBibliographicDescription(DummyDatabase.BibInfo bibInfo) {

        BibliographicDescription bibDesc = new BibliographicDescription();

        bibDesc.setTitle(bibInfo.title);
        bibDesc.setAuthor(bibInfo.author);
        bibDesc.setPublisher(bibInfo.publisher);
        bibDesc.setEdition(bibInfo.edition);
        bibDesc.setPublicationDate(bibInfo.pubDate);
        bibDesc.setMediumType(translateMediaType(bibInfo.mediaType));

        BibliographicRecordId localBibliographicRecordId = new BibliographicRecordId();
        localBibliographicRecordId.setBibliographicRecordIdentifier(bibInfo.bibNo);
        AgencyId agencyId = new AgencyId(getLibraryName());
        localBibliographicRecordId.setAgencyId(agencyId);
        List<BibliographicRecordId> bibRecordIds = new ArrayList<BibliographicRecordId>();
        bibRecordIds.add(localBibliographicRecordId);

        BibliographicRecordId oclcBibliographicRecordId = new BibliographicRecordId();
        oclcBibliographicRecordId.setBibliographicRecordIdentifier(bibInfo.oclcNo);
        oclcBibliographicRecordId.setBibliographicRecordIdentifierCode(Version1BibliographicRecordIdentifierCode.OCLC);
        bibRecordIds.add(oclcBibliographicRecordId);

        bibDesc.setBibliographicRecordIds(bibRecordIds);

        Language language = new Language(Version1Language.VERSION_1_LANGUAGE, bibInfo.language);
        bibDesc.setLanguage(language);

        return bibDesc;

    }

}
