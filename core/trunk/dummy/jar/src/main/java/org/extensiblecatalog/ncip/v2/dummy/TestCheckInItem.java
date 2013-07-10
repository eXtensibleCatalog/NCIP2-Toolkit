/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.MessageHandler;
import org.extensiblecatalog.ncip.v2.common.MessageHandlerFactory;
import org.extensiblecatalog.ncip.v2.common.ServiceValidatorFactory;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.*;
import org.junit.Test;

public class TestCheckInItem {

    private static final Logger LOG = Logger.getLogger(TestCheckInItem.class);

    protected MessageHandler messageHandler = null;

    {

        try {

            messageHandler = MessageHandlerFactory.buildMessageHandler();

        } catch (ToolkitException e) {

            throw new ExceptionInInitializerError(e);

        }

    }

    @Test
    public void testBasicCheckin() throws ServiceException, ToolkitException {

        String itemIdentifier = "abc";
        NCIPResponseData responseData = performService(itemIdentifier);
        System.out.println("Response: " + responseData);

    }

    public NCIPResponseData performService(String itemIdentifier) throws ToolkitException {

        CheckInItemInitiationData initData = new CheckInItemInitiationData();
        ItemId itemId = new ItemId();
        itemId.setItemIdentifierValue(itemIdentifier);
        initData.setItemId(itemId);

        ServiceContext serviceContext = ServiceValidatorFactory.buildServiceValidator().getInitialServiceContext();

        NCIPResponseData responseData = messageHandler.performService(initData, serviceContext);

        return responseData;

    }

    public static void main(String args[]) throws ToolkitException {

        TestCheckInItem testObj = new TestCheckInItem();

        if (args == null || args.length < 1 ) {

            LOG.error("Error: Missing required parameter 'itemIdentifier'.");
            LOG.error("Usage: TestCheckInItem itemIdentifier");

        } else if ( args.length == 1 ) {

            NCIPResponseData responseData = testObj.performService(args[0]);
            LOG.info("Response: " + responseData);

        } else {

            LOG.error("Error: Too many parameters - found " + args.length + ", expecting 1.");
            LOG.error("Usage: TestCheckInItem itemIdentifier");

        }

    }
}
