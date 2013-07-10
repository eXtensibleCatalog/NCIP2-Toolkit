/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

public final class SchemeLoader {

    private SchemeLoader() {
        // Do nothing - prevent construction of instances of this utility class.
    }

    public static void loadAll() {
        // TODO: Figure out a better way to make sure that what's needed by sample files is loaded
        // Perhaps a config file in the sample file directory?
        Version1AcceptItemProcessingError.loadAll();
        Version1AuthenticationDataFormatType.loadAll();
        Version1AuthenticationInputType.loadAll();
        Version1BibliographicItemIdentifierCode.loadAll();
        Version1BibliographicLevel.loadAll();
        Version1BibliographicRecordIdentifierCode.loadAll();
        Version1CheckInItemProcessingError.loadAll();
        Version1CheckOutItemProcessingError.loadAll();
        Version1CirculationStatus.loadAll();
        Version1ComponentIdentifierType.loadAll();
        Version1CurrencyCode.loadAll();
        Version1ElectronicDataFormatType.loadAll();
        Version1FiscalActionType.loadAll();
        Version1GeneralProcessingError.loadAll();
        Version1ItemDescriptionLevel.loadAll();
        Version1ItemElementType.loadAll();
        Version1ItemIdentifierType.loadAll();
        Version1ItemUseRestrictionType.loadAll();
        Version1Language.loadAll();
        Version1LocationType.loadAll();
        Version1LookupItemProcessingError.loadAll();
        Version1LookupUserProcessingError.loadAll();
        Version1MediumType.loadAll();
        Version1OrganizationNameType.loadAll();
        Version1PhysicalAddressType.loadAll();
        Version1PhysicalConditionType.loadAll();
        Version1RequestedActionType.loadAll();
        Version1RequestElementType.loadAll();
        Version1RequestItemProcessingError.loadAll();
        Version1RequestScopeType.loadAll();
        Version1RequestStatusType.loadAll();
        Version1RequestType.loadAll();
        Version1SecurityMarker.loadAll();
        Version1UnstructuredAddressType.loadAll();
        Version1UserElementType.loadAll();

        SchemeValuePair.mapBehavior(AgencyId.class.getName(), SchemeValuePair.Behavior.ALLOW_ANY);
        SchemeValuePair.mapBehavior(ApplicationProfileType.class.getName(), SchemeValuePair.Behavior.ALLOW_ANY);
        SchemeValuePair.mapBehavior(FromSystemId.class.getName(), SchemeValuePair.Behavior.ALLOW_ANY);
        SchemeValuePair.mapBehavior(PickupLocation.class.getName(), SchemeValuePair.Behavior.ALLOW_ANY);
        SchemeValuePair.mapBehavior(RequestIdentifierType.class.getName(), SchemeValuePair.Behavior.ALLOW_ANY);
        SchemeValuePair.mapBehavior(ToSystemId.class.getName(), SchemeValuePair.Behavior.ALLOW_ANY);
    }
}
