package org.extensiblecatalog.ncip.v2.dummy;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.common.MessageHandler;
import org.extensiblecatalog.ncip.v2.common.MessageHandlerFactory;
import org.extensiblecatalog.ncip.v2.common.ServiceValidatorFactory;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.*;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

public class TestAcceptItem {

    private static final Logger LOG = Logger.getLogger(TestAcceptItem.class);
    protected static int AUTHOR_INDEX = 0;
    protected static int AUTHOR_OF_COMPONENT_INDEX = 1;
    protected static int BIBLIOGRAPHIC_ITEM_ID_CODE_INDEX = 2;
    protected static int BIBLIOGRAPHIC_ITEM_ID_INDEX = 3;
    protected static int BIBLIOGRAPHIC_LEVEL_INDEX = 4;
    protected static int BIBLIOGRAPHIC_RECORD_ID_CODE_INDEX = 5;
    protected static int BIBLIOGRAPHIC_RECORD_ID_INDEX = 6;
    protected static int COMPONENT_ID_TYPE_INDEX = 7;
    protected static int COMPONENT_ID_INDEX = 8;
    protected static int EDITION_INDEX = 9;
    protected static int ELECTRONIC_DATA_FORMAT_TYPE_INDEX = 10;
    protected static int LANGUAGE_INDEX = 11;
    protected static int MEDIUM_TYPE_INDEX = 12;
    protected static int PAGINATION_INDEX = 13;
    protected static int PLACE_OF_PUBLICATION_INDEX = 14;
    protected static int PUBLICATION_DATE_INDEX = 15;
    protected static int PUBLICATION_DATE_OF_COMPONENT_INDEX = 16;
    protected static int PUBLISHER_INDEX = 17;
    protected static int SERIES_TITLE_NUMBER_INDEX = 18;
    protected static int SPONSORING_BODY_INDEX = 19;
    protected static int TITLE_INDEX = 20;
    protected static int TITLE_OF_COMPONENT_INDEX = 21;

    protected static final String LINE_SEPARATOR = System.getProperty("line.separator");
    protected static final String USAGE_STRING = "Usage: TestAcceptItem agencyId "
    + "local-item-id \\" + LINE_SEPARATOR
    + " \"author,author of component,bibliographic item id code,bibliographic item id,\\" + LINE_SEPARATOR
    + " bibliographic level,bibliographic record id code,bibliographic record id,\\" + LINE_SEPARATOR
    + " component id type,component id,edition,electronic data format type,language,medium type,\\" + LINE_SEPARATOR
    + " pagination,place of publication,publication date,publication date of component,publisher,\\" + LINE_SEPARATOR
    + " series title numnber,sponsoring body,title,title of component\"\\" + LINE_SEPARATOR
    + " \"library use only,no reproduction,supervision required,user signature required\"\\" + LINE_SEPARATOR
    + " request-id (Loan|Hold|Copy) local-user-id local-due-date [pickup-location]" + LINE_SEPARATOR
    + "Note: If the local-item-id has a slash (/) in it, the portion preceding the slash " + LINE_SEPARATOR
    + "   will be treated as the value of the agencyid, and the portion following it as the local-item-id." + LINE_SEPARATOR
    + "Note: The \"author,author of component,bibliographic item id code,bibliographic item id,..." + LINE_SEPARATOR
    + "   parameter is used to supply bibliographic information and has some special rules:" + LINE_SEPARATOR
    + "   A) You may supply none, some or all of these parameters, but they must be comma-delimited" + LINE_SEPARATOR
    + "      and there is no provision for embedded commas and it must be quoted." + LINE_SEPARATOR
    + "   B) You must put parameters in order, and use adjacent commas for omitted parameters." + LINE_SEPARATOR
    + "   C) For instance, to enter the title alone you would provide:" + LINE_SEPARATOR
    + "        \",,,,,,,,,,,,,,,,,,,,,A History of Cape Cod,\"" + LINE_SEPARATOR
    + "      That's 21 commas, the title, and a trailing comma." + LINE_SEPARATOR
    + "Note: For the \"library use only,no reproduction,supervision required,user signature required\"" + LINE_SEPARATOR
    + "   parameter, this is used to provide item use restrictions and similarly you may provide" + LINE_SEPARATOR
    + "   none, some or all of the values, but in this case you provide the text \"true\"" + LINE_SEPARATOR
    + "   (case is not significant) for restrictions you wish to impose." + LINE_SEPARATOR
    + "   To set any value other than the first you must provide the preceding ones," + LINE_SEPARATOR
    + "   so for example to set only the \"no reproduction\" parameter you could provide only \"false,true\".";

    protected static final int NUMBER_OF_BIB_FIELDS = 22;


    protected MessageHandler messageHandler;

    {

        try {

            messageHandler = MessageHandlerFactory.buildMessageHandler();

        } catch (ToolkitException e) {

            throw new ExceptionInInitializerError(e);

        }

    }

    @Test
    public void testBasicAccept() throws ServiceException, ToolkitException, ParseException {

        String oclcNumber = "53934212";
        String userIdentifier = "367";
        NCIPResponseData responseData = performService("dummy agency", "12398191", null, null, null, "NAV123", "230913",
            null, null);
        System.out.println("Response: " + responseData);

    }

    public NCIPResponseData performService(String agencyIdString, String localItemIdString, String itemAgencyIdString,
                                            String[] bibDescriptions, boolean[] itemUseRestrictions,
                                            String requestIdString, String userIdString,
                                            String localDueDateString, String pickupLocIdString)
        throws ServiceException, ParseException, ToolkitException {


        AcceptItemInitiationData initData = new AcceptItemInitiationData();

        AgencyId agencyId = new AgencyId(agencyIdString);

        InitiationHeader initHdr = new InitiationHeader();
        ToAgencyId toAgencyId = new ToAgencyId();
        toAgencyId.setAgencyId(agencyId);
        initHdr.setToAgencyId(toAgencyId);
        FromAgencyId fromAgencyId = new FromAgencyId();
        fromAgencyId.setAgencyId(agencyId);
        initHdr.setFromAgencyId(fromAgencyId);
        initData.setInitiationHeader(initHdr);

        ItemId itemId = new ItemId();
        itemId.setItemIdentifierValue(localItemIdString);
        if ( itemAgencyIdString != null && ! itemAgencyIdString.isEmpty() ) {

            itemId.setAgencyId(AgencyId.find(null, itemAgencyIdString));

        } else {

            itemId.setAgencyId(agencyId);

        }
        initData.setItemId(itemId);

        ItemOptionalFields itemOptionalFields = new ItemOptionalFields();
        itemOptionalFields.setBibliographicDescription(makeBibliographicDescription(bibDescriptions, agencyId));
        initData.setItemOptionalFields(itemOptionalFields);

        // TODO: Item Use restrictions

        RequestId requestId = new RequestId();
        requestId.setAgencyId(agencyId);
        requestId.setRequestIdentifierValue(requestIdString);
        initData.setRequestId(requestId);

        UserId userId = new UserId();
        userId.setAgencyId(agencyId);
        userId.setUserIdentifierValue(userIdString);
        initData.setUserId(userId);

        if ( localDueDateString != null ) {

            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
            Date localDueDate = dateFormat.parse(localDueDateString);
            GregorianCalendar calendarLocalDueDate = new GregorianCalendar();
            calendarLocalDueDate.setTime(localDueDate);
            initData.setDateForReturn(calendarLocalDueDate);

        }

        if ( pickupLocIdString != null && ! pickupLocIdString.isEmpty() ) {

            PickupLocation pickupLocation = PickupLocation.find(null, pickupLocIdString);
            initData.setPickupLocation(pickupLocation);

        }

        initData.setRequestedActionType(Version1RequestedActionType.CIRCULATE);

        ServiceContext serviceContext = ServiceValidatorFactory.buildServiceValidator().getInitialServiceContext();

        NCIPResponseData responseData = messageHandler.performService(initData, serviceContext);

        return responseData;

    }

    public static void main(String args[]) throws ToolkitException, ServiceException, ParseException {

        TestAcceptItem testObj = new TestAcceptItem();

        if (args == null || args.length < 7 ) {

            LOG.error("Error: Missing required parameter.");
            LOG.error(USAGE_STRING);

        } else if ( args.length == 8 || args.length == 9 ) {

            String agencyIdString = args[0];
            String localItemIdString = args[1];
            String localItemAgencyIdString = null;
            int positionOfSlash = localItemIdString.indexOf('/');
            if ( positionOfSlash >= 0 )
            {
                localItemIdString = localItemIdString.substring(positionOfSlash + 1);
                localItemAgencyIdString = args[1].substring(0, positionOfSlash);
            }

            String localDueDateString = args[7];

            if ( args[2] == null || args[2].isEmpty() )
            {
                args[2] = ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,";
            }
            String[] bibDescriptions = args[2].split(",", -1);
            if ( bibDescriptions.length != NUMBER_OF_BIB_FIELDS )
            {
                LOG.error(bibDescriptions.length + " descriptions found; must provide " + NUMBER_OF_BIB_FIELDS
                    + " descriptions; use commmas to separate and you may omit values by leaving nothing"
                    + " between the commas.");
            }
            else
            {
                String pickupLocIdString = null;
                if ( args.length == 9 && args[8] != null && !args[8].isEmpty() ) {
                    pickupLocIdString = args[8];
                }

                if ( args[3] == null || args[3].compareTo("") == 0 ) {
                    args[3] = ",,,,";
                }
                String[] itemUseRestrictionStrings = args[3].split(",", -1);
                boolean[] itemUseRestrictions = { false, false, false, false};
                for ( int i = 0; i < itemUseRestrictionStrings.length && i < 4; ++i ) {
                    itemUseRestrictions[i] = Boolean.valueOf(itemUseRestrictionStrings[i]);
                }

                String requestIdString = args[4];

                String userIdString = args[6];

                NCIPResponseData responseData = testObj.performService(agencyIdString, localItemIdString,
                    localItemAgencyIdString, bibDescriptions, itemUseRestrictions, requestIdString, userIdString,
                    localDueDateString, pickupLocIdString);
                LOG.info("Response: " + responseData);

            }

        } else {

            LOG.error("Error: Too many parameters - found " + args.length + ", expecting 8 or 9.");
            LOG.error(USAGE_STRING);

        }

    }

    /**
     * Construct a {@link BibliographicDescription} from the input array of strings.
     *
     * @param bibDescriptions  the array of <code>String</code> descriptive elements
     * @param agencyId used in the BibliographicRecordId, if there is one
     * @return the BibliographicDescription object
     */
    public static BibliographicDescription makeBibliographicDescription(
        String[] bibDescriptions, AgencyId agencyId) throws ServiceException {

      LOG.debug("Entering makeBibliographicDescription");
      BibliographicDescription result = new BibliographicDescription();

      if ( ! bibDescriptions[AUTHOR_INDEX].isEmpty() ) {
        result.setAuthor(bibDescriptions[AUTHOR_INDEX]);
      }

      if ( ! bibDescriptions[AUTHOR_OF_COMPONENT_INDEX].isEmpty() ) {

        result.setAuthorOfComponent(bibDescriptions[AUTHOR_OF_COMPONENT_INDEX]);

      }

      if ( ! bibDescriptions[BIBLIOGRAPHIC_ITEM_ID_INDEX].isEmpty() ) {

        BibliographicItemId bibItemId = makeBibItemId(
            bibDescriptions[BIBLIOGRAPHIC_ITEM_ID_CODE_INDEX],
            bibDescriptions[BIBLIOGRAPHIC_ITEM_ID_INDEX]);
        List<BibliographicItemId> bibItemIds = new ArrayList<BibliographicItemId>();
        bibItemIds.add(bibItemId);
        result.setBibliographicItemIds(bibItemIds);

      } else if (LOG.isEnabledFor(Level.WARN)) {

        if ( ! bibDescriptions[BIBLIOGRAPHIC_ITEM_ID_CODE_INDEX].isEmpty() ) {

          LOG.warn("Must provide bibliographic item id when providing bibliographic item id code");

        }
      }

      if ( ! bibDescriptions[BIBLIOGRAPHIC_LEVEL_INDEX].isEmpty() ) {

        BibliographicLevel bibLevel = BibliographicLevel.find(
            Version1BibliographicLevel.VERSION_1_BIBLIOGRAPHIC_LEVEL, bibDescriptions[BIBLIOGRAPHIC_LEVEL_INDEX]);
        result.setBibliographicLevel(bibLevel);

      }

      if ( ! bibDescriptions[BIBLIOGRAPHIC_RECORD_ID_CODE_INDEX].isEmpty()
          &&  ! bibDescriptions[BIBLIOGRAPHIC_RECORD_ID_INDEX].isEmpty() ) {

        BibliographicRecordId bibRecordId = makeBibRecordId(bibDescriptions[BIBLIOGRAPHIC_RECORD_ID_CODE_INDEX],
            bibDescriptions[BIBLIOGRAPHIC_RECORD_ID_INDEX], agencyId);
        List<BibliographicRecordId> bibRecordIds = new ArrayList<BibliographicRecordId>();
        bibRecordIds.add(bibRecordId);
        result.setBibliographicRecordIds(bibRecordIds);

      } else if (LOG.isEnabledFor(Level.WARN)) {

        if (( ! bibDescriptions[BIBLIOGRAPHIC_RECORD_ID_CODE_INDEX].isEmpty()
            &&  ! bibDescriptions[BIBLIOGRAPHIC_RECORD_ID_INDEX].isEmpty() )
            || ( ! bibDescriptions[BIBLIOGRAPHIC_RECORD_ID_CODE_INDEX].isEmpty()
            &&  ! bibDescriptions[BIBLIOGRAPHIC_RECORD_ID_INDEX].isEmpty() ) ) {

          LOG.warn("Must provide both bibliographic record id code and bibliographic record id or neither of them.");

        }

      }

      if ( ! bibDescriptions[COMPONENT_ID_TYPE_INDEX].isEmpty()
          && ! bibDescriptions[COMPONENT_ID_INDEX].isEmpty() ) {

        ComponentId componentId = makeComponentId(bibDescriptions[COMPONENT_ID_TYPE_INDEX],
            bibDescriptions[COMPONENT_ID_INDEX]);
        result.setComponentId(componentId);

      } else if (LOG.isEnabledFor(Level.WARN)) {

        if (( ! bibDescriptions[COMPONENT_ID_TYPE_INDEX].isEmpty()
            && ! bibDescriptions[COMPONENT_ID_INDEX].isEmpty() )
            || (bibDescriptions[COMPONENT_ID_TYPE_INDEX].isEmpty()
            &&  ! bibDescriptions[COMPONENT_ID_INDEX].isEmpty() ) ) {

          LOG.warn("Must provide both component id type and componentid or neither of them.");

        }
      }

      if ( ! bibDescriptions[EDITION_INDEX].isEmpty() ) {

        result.setEdition(bibDescriptions[EDITION_INDEX]);

      }

      if ( ! bibDescriptions[ELECTRONIC_DATA_FORMAT_TYPE_INDEX].isEmpty() ) {

        ElectronicDataFormatType elecDataFmtType = ElectronicDataFormatType.find(
            Version1ElectronicDataFormatType.VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE,
            (bibDescriptions[ELECTRONIC_DATA_FORMAT_TYPE_INDEX]));
        result.setElectronicDataFormatType(elecDataFmtType);

      }

      if ( ! bibDescriptions[LANGUAGE_INDEX].isEmpty() ) {

        Language language = Language.find(Version1Language.VERSION_1_LANGUAGE, bibDescriptions[LANGUAGE_INDEX]);
        result.setLanguage(language);

      }

      if ( ! bibDescriptions[MEDIUM_TYPE_INDEX].isEmpty() ) {

        MediumType mediumType = MediumType.find(Version1MediumType.VERSION_1_MEDIUM_TYPE,
            bibDescriptions[MEDIUM_TYPE_INDEX]);
        result.setMediumType(mediumType);

      }

      if ( ! bibDescriptions[PAGINATION_INDEX].isEmpty() ) {

        result.setPagination(bibDescriptions[PAGINATION_INDEX]);

      }

      if ( ! bibDescriptions[PLACE_OF_PUBLICATION_INDEX].isEmpty() ) {

        result.setPlaceOfPublication(bibDescriptions[PLACE_OF_PUBLICATION_INDEX]);
      }

      if ( ! bibDescriptions[PUBLICATION_DATE_INDEX].isEmpty() ) {

        result.setPublicationDate(bibDescriptions[PUBLICATION_DATE_INDEX]);

      }

      if ( ! bibDescriptions[PUBLICATION_DATE_OF_COMPONENT_INDEX].isEmpty() ) {

        result.setPublicationDateOfComponent(bibDescriptions[PUBLICATION_DATE_OF_COMPONENT_INDEX]);

      }

      if ( ! bibDescriptions[PUBLISHER_INDEX].isEmpty() ) {

        result.setPublisher(bibDescriptions[PUBLISHER_INDEX]);

      }

      if ( ! bibDescriptions[SERIES_TITLE_NUMBER_INDEX].isEmpty() ) {

        result.setSeriesTitleNumber(bibDescriptions[SERIES_TITLE_NUMBER_INDEX]);

      }

      if ( ! bibDescriptions[SPONSORING_BODY_INDEX].isEmpty() ) {

        result.setSponsoringBody(bibDescriptions[SPONSORING_BODY_INDEX]);

      }

      if ( ! bibDescriptions[TITLE_INDEX].isEmpty() ) {

        result.setTitle(bibDescriptions[TITLE_INDEX]);

      }

      if ( ! bibDescriptions[TITLE_OF_COMPONENT_INDEX].isEmpty() ) {

        result.setTitleOfComponent(bibDescriptions[TITLE_OF_COMPONENT_INDEX]);

      }

      LOG.debug("Returning " + result);
      return result;
    }

    /**
     * Construct a {@link BibliographicItemId} object using the NCIP Bibliographic Item Id Scheme and the provided value.
     *
     * @param value String that matches one of the Values in the Bibliographic Item Identifier Code Scheme
     * @param id    String that identifies the bibliographic item id
     * @return the {@link BibliographicItemId} object
     */
    public static BibliographicItemId makeBibItemId(String value, String id) throws ServiceException {

      BibliographicItemId result = new BibliographicItemId();
      result.setBibliographicItemIdentifier(id);

      if (value != null && ! value.isEmpty()) {

        BibliographicItemIdentifierCode code = BibliographicItemIdentifierCode.find(
                Version1BibliographicItemIdentifierCode.VERSION_1_BIBLIOGRAPHIC_ITEM_IDENTIFIER_CODE, value);
        result.setBibliographicItemIdentifierCode(code);

      }

      return result;

    }

    /**
     * Construct a {@link ComponentId} object using the NCIP Component Id Scheme and the provided value.
     *
     * @param value String that matches one of the Values in the NCIP Component Identifier Type Scheme
     * @param id    the component id
     * @return the {@link ComponentId} object
     */
    public static ComponentId makeComponentId(String value, String id) throws ServiceException {

      ComponentId result = new ComponentId();
      ComponentIdentifierType type = ComponentIdentifierType.find(
          Version1ComponentIdentifierType.VERSION_1_COMPONENT_IDENTIFIER_TYPE, value);
      result.setComponentIdentifierType(type);
      result.setComponentIdentifier(id);
      return result;

    }

    /**
     * Constructs a {@link BibliographicRecordId} object using the NCIP
     * Bibliographic Record Id Scheme, the provided value and id. If the
     * value parameter matches the special code <code>local</code>, then
     * the returned object will contain the UniqueAgencyId (signifying the
     * id is a local system record id), otherwise
     * it will contain the BibliographicRecordIdentifierCode (signifying
     * the id is that of a bibliographic record in a national bibliography,
     * bibliographic utility or other well-known database).
     *
     * @param value        String that matches on of the values in the NCIP Bibliographic Record, or "local"
     * @param id           String containing the record id
     * @param agencyId     {@link AgencyId} object designating the agency associated with the <code>id</code>
     * @return the {@link BibliographicRecordId}
     */
    public static BibliographicRecordId makeBibRecordId(String value, String id, AgencyId agencyId)
         throws ServiceException
    {

      BibliographicRecordId result = new BibliographicRecordId();
      result.setBibliographicRecordIdentifier(id);

      if (value.compareTo("local") == 0) {

        result.setAgencyId(agencyId);

      } else {

          BibliographicRecordIdentifierCode bibRecIdCode = BibliographicRecordIdentifierCode.find(
              Version1BibliographicRecordIdentifierCode.VERSION_1_BIBLIOGRAPHIC_RECORD_IDENTIFIER_CODE, value);
          result.setBibliographicRecordIdentifierCode(bibRecIdCode);

      }

      return result;

    }


}
