/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.MessageHandlerFactory;
import org.extensiblecatalog.ncip.v2.common.ServiceValidatorFactory;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.*;
import org.junit.Test;

public class TestRenewItem {

    private static final Logger LOG = Logger.getLogger(TestRenewItem.class);

    protected MessageHandler messageHandler = null;

    {

        try {

            messageHandler = MessageHandlerFactory.getSharedMessageHandler();

        } catch (ToolkitException e) {

            throw new ExceptionInInitializerError(e);

        }

    }

    @Test
    public void testBasicRenew() throws ServiceException, ToolkitException {

        String itemIdentifier = "abc";
        String userIdentifier = "123";
        NCIPResponseData responseData = performService(itemIdentifier, userIdentifier);
        System.out.println("Response: " + responseData);

    }

    public NCIPResponseData performService(String itemIdentifier, String userIdentifier) throws ToolkitException {

        RenewItemInitiationData initData = new RenewItemInitiationData();
        ItemId itemId = new ItemId();
        itemId.setItemIdentifierValue(itemIdentifier);
        initData.setItemId(itemId);
        UserId userId = new UserId();
        userId.setUserIdentifierValue(userIdentifier);
        initData.setUserId(userId);

        ServiceContext serviceContext = ServiceValidatorFactory.getSharedServiceValidator().getInitialServiceContext();

        NCIPResponseData responseData = messageHandler.performService(initData, serviceContext);

        return responseData;

    }

    public static void main(String args[]) throws ToolkitException {

        TestRenewItem testObj = new TestRenewItem();

        if (args == null || args.length < 2 ) {

            LOG.error("Error: Missing required parameter 'itemIdentifier', 'userIdentifier', or both.");
            LOG.error("Usage: TestRenewItem itemIdentifier userIdentifier");

        } else if ( args.length == 2 ) {

            NCIPResponseData responseData = testObj.performService(args[0], args[1]);
            LOG.info("Response: " + responseData);

        } else {

            LOG.error("Error: Too many parameters - found " + args.length + ", expecting 2.");
            LOG.error("Usage: TestRenewItem itemIdentifier userIdentifier");

        }

    }
}
