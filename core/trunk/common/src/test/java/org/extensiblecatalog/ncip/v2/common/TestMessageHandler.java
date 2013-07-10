/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestMessageHandler {

    private static String USER_IDENTIFIER_VALUE = "12313";

    public MessageHandler myMessageHandler;
    {
        try {
            myMessageHandler = MessageHandlerFactory.buildMessageHandler();
        } catch (ToolkitException e) {
            throw new ExceptionInInitializerError(e);
        }

    }

    private LookupUserInitiationData supportedInitData;
    {

        supportedInitData = new LookupUserInitiationData();
        UserId userId = new UserId();
        userId.setUserIdentifierValue(USER_IDENTIFIER_VALUE);
        supportedInitData.setUserId(userId);

    }

    private ServiceContext supportedServiceContext;
    {

        try {
            supportedServiceContext = ServiceValidatorFactory.buildServiceValidator().getInitialServiceContext();
        } catch (ToolkitException e) {
            throw new ExceptionInInitializerError(e);
        }

    }

    private ProblemResponseData supportedServiceAndSupportedServiceContextResponse;
    {

        supportedServiceAndSupportedServiceContextResponse = new ProblemResponseData();
        supportedServiceAndSupportedServiceContextResponse.setProblems(
            ServiceHelper.createUnsupportedServiceProblems(new LookupUserInitiationData()));

    }

    private ProblemResponseData supportedServiceAndUnsupportedServiceContextResponse;
    {

        // TODO: How to make sure we share the same text - or is that not worth testing?
        supportedServiceAndUnsupportedServiceContextResponse = new ProblemResponseData();
        supportedServiceAndSupportedServiceContextResponse.setProblems(
            ServiceHelper.createUnsupportedServiceProblems(new LookupUserInitiationData()));

    }

    private ProblemResponseData unsupportedServiceAndSupportedServiceContextResponse;
    {

        unsupportedServiceAndSupportedServiceContextResponse = new ProblemResponseData();
        supportedServiceAndSupportedServiceContextResponse.setProblems(
            ServiceHelper.createUnsupportedServiceProblems(new LookupUserInitiationData()));

    }

    private ProblemResponseData unsupportedServiceAndUnsupportedServiceContextResponse;
    {

        // We expect that the unsupported service is detected before the unsupported service context
        unsupportedServiceAndUnsupportedServiceContextResponse = unsupportedServiceAndSupportedServiceContextResponse;

    }

    private RenewItemInitiationData unsupportedInitData;
    {

        unsupportedInitData = new RenewItemInitiationData();
        ItemId itemId = new ItemId();
        itemId.setItemIdentifierValue("123");
        unsupportedInitData.setItemId(itemId);

    }

    private ServiceContext unsupportedServiceContext;
    {
        unsupportedServiceContext = new ServiceContext() {

            @Override
            public void validateBeforeMarshalling(NCIPMessage ncipMessage) throws ValidationException {
                return; // Do nothing - this class is a stub
            }

            @Override
            public void validateAfterUnmarshalling(NCIPMessage ncipMessage) throws ValidationException {
                return; // Do nothing - this class is a stub
            }
        };
    }

    public TestMessageHandler(MessageHandler myMessageHandler) {
        this.myMessageHandler = myMessageHandler;
    }

    @Test
    public final void testPerformService_SupportedServiceAndSupportedServiceContext() {
        NCIPResponseData actualResponse = myMessageHandler.performService(supportedInitData, supportedServiceContext);
        assertThat(actualResponse, instanceOf(supportedServiceAndSupportedServiceContextResponse.getClass()));
        // TODO: Test whether equal to supportedServiceAndSupportedServiceContextResponse and remove the instanceOf test
    }

    @Test
    public final void testPerformService_SupportedServiceAndUnsupportedServiceContext() {
        NCIPResponseData actualResponse = myMessageHandler.performService(supportedInitData, unsupportedServiceContext);
        assertThat(actualResponse, instanceOf(supportedServiceAndUnsupportedServiceContextResponse.getClass()));
        // TODO: Test whether equal to supportedServiceAndUnsupportedServiceContextResponse and remove the instanceOf test
    }

    @Test
    public final void testPerformService_UnsupportedServiceAndSupportedServiceContext() {
        NCIPResponseData actualResponse = myMessageHandler.performService(unsupportedInitData, supportedServiceContext);
        assertThat(actualResponse, instanceOf(unsupportedServiceAndSupportedServiceContextResponse.getClass()));
        // TODO: Test whether equal to unsupportedServiceAndSupportedServiceContextResponse and remove the instanceOf test
    }

    @Test
    public final void testPerformService_UnsupportedServiceAndUnsupportedServiceContext() {
        NCIPResponseData actualResponse = myMessageHandler.performService(unsupportedInitData, unsupportedServiceContext);
        assertThat(actualResponse, instanceOf(unsupportedServiceAndUnsupportedServiceContextResponse.getClass()));
        // TODO: Test whether equal to unsupportedServiceAndUnsupportedServiceContextResponse and remove the instanceOf test
    }

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() throws ToolkitException {
        return Arrays.asList(
                         new Object[]{MessageHandlerFactory.buildMessageHandler()},
            // TODO: Move this class to Initiator package
                         new Object[]{MessageHandlerFactory.buildMessageHandler()}
        );
    }

}
