/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb;

import org.extensiblecatalog.ncip.v2.common.NCIPv201ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.common.TranslatorFactory;
import org.junit.Test;
import org.extensiblecatalog.ncip.v2.service.*;

import java.math.BigDecimal;
import java.util.*;

public class TestTranslation {

    @Test
    public void testRequestedItem() throws ServiceException, ToolkitException, ValidationException {

        RequestStatusType requestStatus = Version1RequestStatusType.IN_PROCESS;

        RequestedItem requestedItem = new RequestedItem();

        ItemId requestedItemId = new ItemId();
        requestedItemId.setItemIdentifierValue("");
        requestedItem.setItemId(requestedItemId);
        requestedItem.setTitle("Title");
        requestedItem.setRequestType(Version1RequestType.HOLD);
        if ( requestStatus.equals(Version1RequestStatusType.AVAILABLE_FOR_PICKUP )) {
            requestedItem.setRequestStatusType(Version1RequestStatusType.AVAILABLE_FOR_PICKUP);
            GregorianCalendar pickupCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
            // TODO: Test with & without pickup date
            requestedItem.setPickupDate(pickupCalendar);
        } else if (requestStatus.equals(Version1RequestStatusType.IN_PROCESS )) {
            requestedItem.setRequestStatusType(Version1RequestStatusType.IN_PROCESS);
            // TODO: Test with & without hold queue position
            requestedItem.setHoldQueuePosition(new BigDecimal(2));

        } else {
            requestedItem.setRequestStatusType(Version1RequestStatusType.CANNOT_FULFILL_REQUEST);
        }

        GregorianCalendar requestCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        requestedItem.setDatePlaced(requestCalendar);
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        requestedItem.setPickupExpiryDate(calendar);

        SchemeValuePair.mapBehavior(PickupLocation.class.getName(), SchemeValuePair.Behavior.ALLOW_ANY);
        requestedItem.setPickupLocation(PickupLocation.find(null, "PickupLocation"));

        List<RequestedItem> requestedItemsList = new ArrayList<RequestedItem>();
        requestedItemsList.add(requestedItem);

        LookupUserResponseData lookupUserResponse = new LookupUserResponseData();
        lookupUserResponse.setRequestedItems(requestedItemsList);

        UserId userId = new UserId();
        userId.setUserIdentifierValue("userABC");
        lookupUserResponse.setUserId(userId);
        System.out.println("Response Object: " + lookupUserResponse.toString());
        Translator translator = TranslatorFactory.getSharedTranslator();
        translator.createResponseMessageStream(new NCIPv201ServiceContext(), lookupUserResponse);

    }

}
