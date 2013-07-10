package org.extensiblecatalog.ncip.v2.dummy;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.MessageHandler;
import org.extensiblecatalog.ncip.v2.common.MessageHandlerFactory;
import org.extensiblecatalog.ncip.v2.common.ServiceValidatorFactory;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestRequestItem {

    private static final Logger LOG = Logger.getLogger(TestRequestItem.class);

    protected static final String USAGE_STRING = "Usage: TestRequestItem agencyId targetId targetIdType userIdentifier";

    protected MessageHandler messageHandler;

    {

        try {

            messageHandler = MessageHandlerFactory.buildMessageHandler();

        } catch (ToolkitException e) {

            throw new ExceptionInInitializerError(e);

        }

    }

    @Test
    public void testBasicRequest() throws ServiceException, ToolkitException {

        String oclcNumber = "53934212";
        String userIdentifier = "367";
        NCIPResponseData responseData = performService("dummy agency", oclcNumber, "oclc", userIdentifier);
        System.out.println("Response: " + responseData);

    }

    public NCIPResponseData performService(String agencyIdString, String targetId, String targetTypeString, String userIdentifier) throws ToolkitException {

        RequestItemInitiationData initData = new RequestItemInitiationData();

        AgencyId agencyId = new AgencyId(agencyIdString);

        if ( targetTypeString.compareToIgnoreCase("localctrlno") == 0 ) {

            BibliographicId bibId = new BibliographicId();
            BibliographicRecordId bibRecId = new BibliographicRecordId();
            bibRecId.setBibliographicRecordIdentifier(targetId);
            bibRecId.setAgencyId(agencyId);
            bibId.setBibliographicRecordId(bibRecId);
            List<BibliographicId> bibIdList = new ArrayList<BibliographicId>();
            bibIdList.add(bibId);
            initData.setBibliographicIds(bibIdList);

        } else if ( targetTypeString.compareToIgnoreCase("oclc") == 0 ) {

            BibliographicId bibId = new BibliographicId();
            BibliographicRecordId bibRecId = new BibliographicRecordId();
            bibRecId.setBibliographicRecordIdentifier(targetId);
            bibRecId.setBibliographicRecordIdentifierCode(Version1BibliographicRecordIdentifierCode.OCLC);
            bibId.setBibliographicRecordId(bibRecId);
            List<BibliographicId> bibIdList = new ArrayList<BibliographicId>();
            bibIdList.add(bibId);
            initData.setBibliographicIds(bibIdList);

        } else if ( targetTypeString.compareToIgnoreCase("isbn") == 0 ) {

            BibliographicId bibId = new BibliographicId();
            BibliographicItemId bibItemId = new BibliographicItemId();
            bibItemId.setBibliographicItemIdentifier(targetId);
            bibItemId.setBibliographicItemIdentifierCode(Version1BibliographicItemIdentifierCode.ISBN);
            bibId.setBibliographicItemId(bibItemId);
            List<BibliographicId> bibIdList = new ArrayList<BibliographicId>();
            bibIdList.add(bibId);
            initData.setBibliographicIds(bibIdList);

        } else if ( targetTypeString.compareToIgnoreCase("barcode") == 0 ) {

            ItemId itemId = new ItemId();
            itemId.setItemIdentifierValue(targetId);
            List<ItemId> itemIdList = new ArrayList<ItemId>();
            itemIdList.add(itemId);
            initData.setItemIds(itemIdList);

        } else {

            throw new ToolkitException("Unrecognized target type of '" + targetTypeString + "'.");

        }

        UserId userId = new UserId();
        userId.setUserIdentifierValue(userIdentifier);
        initData.setUserId(userId);
        initData.setRequestScopeType(Version1RequestScopeType.BIBLIOGRAPHIC_ITEM);

        ServiceContext serviceContext = ServiceValidatorFactory.buildServiceValidator().getInitialServiceContext();

        NCIPResponseData responseData = messageHandler.performService(initData, serviceContext);

        return responseData;

    }

    public static void main(String args[]) throws ToolkitException {

        TestRequestItem testObj = new TestRequestItem();

        if (args == null || args.length < 4 ) {

            LOG.error("Error: Missing required parameter.");
            LOG.error(USAGE_STRING);

        } else if ( args.length == 4 ) {

            NCIPResponseData responseData = testObj.performService(args[0], args[1], args[2], args[3]);
            LOG.info("Response: " + responseData);

        } else {

            LOG.error("Error: Too many parameters - found " + args.length + ", expecting 4.");
            LOG.error(USAGE_STRING);

        }

    }
}
