package org.extensiblecatalog.ncip.v2.dummy;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.MessageHandler;
import org.extensiblecatalog.ncip.v2.common.MessageHandlerFactory;
import org.extensiblecatalog.ncip.v2.common.ServiceValidatorFactory;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.*;
import org.junit.Test;

public class TestCancelRequestItem {

    private static final Logger LOG = Logger.getLogger(TestCancelRequestItem.class);

    protected static final String USAGE_STRING = "Usage: TestCancelRequestItem agencyId reservationId";
    protected static final int NUMBER_OF_REQUIRED_PARMS = 2;

    protected MessageHandler messageHandler;

    {

        try {

            messageHandler = MessageHandlerFactory.buildMessageHandler();

        } catch (ToolkitException e) {

            throw new ExceptionInInitializerError(e);

        }

    }

    @Test
    public void test() throws ServiceException, ToolkitException {

        String reservationId = "1123";
        NCIPResponseData responseData = performService("dummy agency", reservationId);
        System.out.println("Response: " + responseData);

    }

    public NCIPResponseData performService(String agencyIdString, String reservationId) throws ToolkitException {

        CancelRequestItemInitiationData initData = new CancelRequestItemInitiationData();

        AgencyId agencyId = ServiceHelper.createAgencyId(agencyIdString);
        ToAgencyId toAgencyId = new ToAgencyId();
        toAgencyId.setAgencyId(agencyId);
        FromAgencyId fromAgencyId = new FromAgencyId();
        fromAgencyId.setAgencyId(agencyId);
        InitiationHeader initHeader = new InitiationHeader();
        initHeader.setToAgencyId(toAgencyId);
        initHeader.setFromAgencyId(fromAgencyId);
        initData.setInitiationHeader(initHeader);

        RequestId requestId = new RequestId();
        requestId.setRequestIdentifierValue(reservationId);
        initData.setRequestId(requestId);

        ServiceContext serviceContext = ServiceValidatorFactory.buildServiceValidator().getInitialServiceContext();

        NCIPResponseData responseData = messageHandler.performService(initData, serviceContext);

        return responseData;

    }

    public static void main(String args[]) throws ToolkitException {

        TestCancelRequestItem testObj = new TestCancelRequestItem();

        if (args == null || args.length < NUMBER_OF_REQUIRED_PARMS ) {

            LOG.error("Error: Missing required parameter.");
            LOG.error(USAGE_STRING);

        } else if ( args.length ==  NUMBER_OF_REQUIRED_PARMS ) {

            NCIPResponseData responseData = testObj.performService(args[0], args[1]);
            LOG.info("Response: " + responseData);

        } else {

            LOG.error("Error: Too many parameters - found " + args.length + ", expecting " + NUMBER_OF_REQUIRED_PARMS
                + ".");
            LOG.error(USAGE_STRING);

        }

    }
}
