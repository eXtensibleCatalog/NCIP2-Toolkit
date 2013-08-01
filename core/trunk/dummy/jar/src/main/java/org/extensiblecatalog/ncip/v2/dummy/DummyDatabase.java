/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ElectronicAddress;
import org.extensiblecatalog.ncip.v2.service.ElectronicAddressType;
import org.extensiblecatalog.ncip.v2.service.NameInformation;
import org.extensiblecatalog.ncip.v2.service.PersonalNameInformation;
import org.extensiblecatalog.ncip.v2.service.PhysicalAddress;
import org.extensiblecatalog.ncip.v2.service.PhysicalAddressType;
import org.extensiblecatalog.ncip.v2.service.StructuredAddress;
import org.extensiblecatalog.ncip.v2.service.StructuredPersonalUserName;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.extensiblecatalog.ncip.v2.service.UnstructuredAddress;
import org.extensiblecatalog.ncip.v2.service.UnstructuredAddressType;
import org.extensiblecatalog.ncip.v2.service.UserAddressInformation;
import org.extensiblecatalog.ncip.v2.service.UserAddressRoleType;
import org.extensiblecatalog.ncip.v2.service.UserOptionalFields;

public class DummyDatabase {

    private static final Logger LOG = Logger.getLogger(DummyDatabase.class);

    protected static final int RENEWAL_PERIOD = 14;
    protected static final int MAX_RENEWALS = 3;

    /**
     * This enumeration represents the Circulation Statuses in this ILS.
     */
    public enum CircStatus {
        /**
         * The item is on order, i.e. in acquisitions and not available for circulation.
         */
        ON_ORDER,
        /**
         * The item is on the shelf, i.e. available for circulation.
         */
        ON_SHELF,
        /**
         * The item is checked-out, i.e.e not available for circulation, possibly overdue.
         */
        CHECKED_OUT,
        /**
         * The item is in-transit between library locations and not available for circulation.
         */
        IN_TRANSIT
    }

    public enum RenewErrorCode {

        NOT_RENEWABLE,
        MAX_RENEWALS_EXCEEDED,
        ITEM_ON_HOLD,
        USER_BLOCKED,
        GENERAL_POLICY_PROBLEM
        
    }

    public enum MediaTypeEnum {
        BOOK,
        AUDIO_TAPE,
        CD,
        DVD,
        MAGAZINE
    }

    protected static class RequestInfo {

        protected static Map<String, RequestInfo> requestInfos = new HashMap<String, RequestInfo>();
        protected static Map<String, List<RequestInfo>> requestedItemsByItemBarcode = new HashMap<String, List<RequestInfo>>();
        protected static Map<String, List<RequestInfo>> requestedItemsByUserNo = new HashMap<String, List<RequestInfo>>();
        protected static void insertIntoRequestInfoList(Map<String, List<RequestInfo>> map, String key,
                                                        RequestInfo requestInfo) {

            List<RequestInfo> list = map.get(key);
            if ( list != null ) {

                list.add(requestInfo);

            } else {

                list = new ArrayList<RequestInfo>();
                list.add(requestInfo);
                map.put(key, list);

            }
        }

        protected String requestNo;
        protected String userNo;
        protected String itemBarcode;
        protected GregorianCalendar createDate;
        protected String pickupLoc;
        protected GregorianCalendar pickupStart;
        protected GregorianCalendar pickupEnd;
        protected int itemAvailableCount;

        public RequestInfo(String requestNo, String userNo, String itemBarcode, String pickupLoc) throws ToolkitException {

            if (userNo == null) {

                throw new ToolkitException("User number must not be null in call to RequestInfo constructor.");

            }
            if (itemBarcode == null) {

                throw new ToolkitException("Item barcode must not be null in call to RequestInfo constructor.");

            }

            if ( requestNo == null || requestNo.isEmpty() ) {

                requestNo = getNextRequestNo();
            }

            this.requestNo = requestNo;
            this.userNo = userNo;
            this.itemBarcode = itemBarcode;
            this.pickupLoc = pickupLoc;

            this.createDate = new GregorianCalendar(TimeZone.getTimeZone("UTC"));


            // These are set when the request is filled.
            this.pickupStart = null;
            this.pickupEnd = null;
            this.itemAvailableCount = 0;

            requestInfos.put(requestNo, this);
            insertIntoRequestInfoList(requestedItemsByItemBarcode, this.itemBarcode, this);
            insertIntoRequestInfoList(requestedItemsByUserNo, this.userNo, this);
        }
        
      

        public static RequestInfo getByRequestNo(String requestNo) {

            return requestInfos.get(requestNo);

        }

        public static List<RequestInfo> getByUserNo(String userNo) {

            return requestedItemsByUserNo.get(userNo);

        }

        public int getQueuePosition() {

            int queuePosition = 0;
            // Queue position zero means the hold is awaiting pickup, i.e. it's the first one in this list.
            for ( RequestInfo requestInfo : requestedItemsByItemBarcode.get(this.itemBarcode) ) {

                if ( requestInfo.requestNo.compareToIgnoreCase(this.requestNo) == 0 ) {

                    break;
                }

                queuePosition++;

            }

            return queuePosition;

        }

        public void fill() {

            this.pickupStart = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
            this.pickupEnd = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
            this.pickupEnd.add(Calendar.DAY_OF_YEAR, 7);
            // Pretend we sent the "Your item is available" notice
            this.itemAvailableCount = 1;

        }

    }

    protected static class FeeInfo {

        protected static Map<String, FeeInfo> feeInfos = new HashMap<String, FeeInfo>();

    }

    protected static class FineInfo {

        protected static Map<String, FineInfo> fineInfos = new HashMap<String, FineInfo>();

    }

    protected static class UserInfo {

        protected static Map<String, UserInfo> userInfos = new HashMap<String, UserInfo>();

        protected String userNo;
        
        protected String plainPassword;
        
        protected UserOptionalFields optionalFields;

        public UserInfo(String userNo) {

            this.userNo = userNo;

        }
        
        public void setPlainPassword(String password) {
        	this.plainPassword = password;
        }
        
        public static UserInfo getUserInfo(String userNo) {
        	return userInfos.get(userNo);
        }
        
        public boolean confirmPassword(String password) {
        	return plainPassword == null || password == null ? false : plainPassword.equals(password);
        }

		public UserOptionalFields getOptionalFields() {
			return optionalFields;
		}

		public void setOptionalFields(UserOptionalFields optionalFields) {
			this.optionalFields = optionalFields;
		}
		
    }

    protected static class BibInfo {

        protected static Map<String, BibInfo> bibInfos = new HashMap<String, BibInfo>();
        protected static Map<String, List<BibInfo>> bibsByOCLCNo = new HashMap<String, List<BibInfo>>();
        protected static void insertIntoBibInfoList(Map<String, List<BibInfo>> map, String key, BibInfo bibInfo) {

            List<BibInfo> list = map.get(key);
            if ( list != null ) {

                list.add(bibInfo);

            } else {

                list = new ArrayList<BibInfo>();
                list.add(bibInfo);
                map.put(key, list);

            }
        }

        protected String bibNo;
        protected String title;
        protected String author;
        protected String publisher;
        protected String edition;
        protected String pubDate;
        protected String language;
        protected String oclcNo;
        protected MediaTypeEnum mediaType;
        protected HoldingInfo[] holdings;

        public BibInfo(String bibNo, String title, String author, String publisher, String edition, String pubDate,
                       String language, String oclcNo, MediaTypeEnum mediaType) {

            this.bibNo = bibNo;
            this.title = title;
            this.author = author;
            this.publisher = publisher;
            this.edition = edition;
            this.pubDate = pubDate;
            this.language = language;
            this.oclcNo = oclcNo;
            this.mediaType = mediaType;
            bibInfos.put(bibNo, this);
            insertIntoBibInfoList(bibsByOCLCNo, this.oclcNo, this);

        }

        public void addHolding(HoldingInfo holding)
        {
            if ( this.holdings == null )
            {
                this.holdings = new HoldingInfo[1];
                this.holdings[0] = holding;
            }
            else
            {
                int originalLength = this.holdings.length;
                this.holdings = Arrays.copyOf(this.holdings, this.holdings.length + 1);
                this.holdings[originalLength] = holding;
            }
        }

        public static BibInfo getByBibNo(String bibNo)
        {
            return bibInfos.get(bibNo);
        }

        public static List<BibInfo> getBibsByOCLCNo(String oclcNo)
        {
            return bibsByOCLCNo.get(oclcNo);
        }
    }

    protected static class HoldingInfo {

        protected static Map<String, HoldingInfo> holdingInfos = new HashMap<String, HoldingInfo>();
        protected static Map<String, HoldingInfo> holdingInfosByItemBarcode = new HashMap<String, HoldingInfo>();

        protected String holdingId;
        protected BibInfo bibInfo;
        protected String location;
        protected String summaryHoldings;
        protected ItemInfo[] items;

        public HoldingInfo(String holdingId, BibInfo bibInfo, String location, String summaryHoldings) {

            this.holdingId = holdingId;
            this.bibInfo = bibInfo;
            bibInfo.addHolding(this);
            this.location = location;
            this.summaryHoldings = summaryHoldings;
            holdingInfos.put(holdingId, this);

        }

        public void addItem(ItemInfo itemInfo)
        {
            if ( items == null )
            {
                this.items = new ItemInfo[1];
                this.items[0] = itemInfo;
            }
            else
            {
                int originalLength = this.items.length;
                this.items = Arrays.copyOf(this.items, this.items.length + 1);
                this.items[originalLength] = itemInfo;
            }
            holdingInfosByItemBarcode.put(itemInfo.barcode, this);

        }

        public static HoldingInfo getByHoldingId(String holdingId)
        {
            return holdingInfos.get(holdingId);
        }

        public static HoldingInfo getByItemBarcode(String barcode)
        {
            return holdingInfosByItemBarcode.get(barcode);
        }
    }

    protected static class ItemInfo {

        protected static Map<String, ItemInfo> itemInfos = new HashMap<String, ItemInfo>();
        protected static Map<String, BibInfo> bibByItemBarcode = new HashMap<String, BibInfo>();
        protected static Map<String, HoldingInfo> holdingByItemBarcode = new HashMap<String, HoldingInfo>();
        protected static Map<String, List<ItemInfo>> chargedItemsByUserNo = new HashMap<String, List<ItemInfo>>();
        protected static void insertIntoItemInfoList(Map<String, List<ItemInfo>> map, String key, ItemInfo itemInfo) {

            List<ItemInfo> list = map.get(key);
            if ( list != null ) {

                list.add(itemInfo);

            } else {

                list = new ArrayList<ItemInfo>();
                list.add(itemInfo);
                map.put(key, list);

            }
        }
        protected static void removeFromItemInfoList(Map<String, List<ItemInfo>> map, String key, ItemInfo itemInfo) {

            List<ItemInfo> itemList = map.get(key);
            if ( itemList != null ) {

                if ( ! itemList.remove(itemInfo) ) {

                    LOG.error("(removeFromItemInfoList) Item " + itemInfo.barcode + " was not in the list.");

                } // else - this is the expected case.

            } else {

                LOG.error("(removeFromItemInfoList) Key " + key + " is not in the map.");

            }

        }

        protected String barcode;
        protected HoldingInfo holdingInfo;
        protected String callNo;
        protected String copyNo;
        protected CircStatus circStatus = CircStatus.ON_SHELF;
        protected String userNo;
        protected GregorianCalendar checkoutDate;
        protected GregorianCalendar dateDue;
        protected GregorianCalendar dateRenewed;
        protected int overdueReminderCount;
        protected int renewalCount;

        public ItemInfo(String barcode, HoldingInfo holdingInfo, String callNo, String copyNo) {

            this.barcode = barcode;
            this.holdingInfo = holdingInfo;
            holdingInfo.addItem(this);
            this.callNo = callNo;
            this.copyNo = copyNo;
            itemInfos.put(barcode, this);
        }

        public void checkout(String userNo,GregorianCalendar dateDue) {

            assert(userNo != null);
            assert(this.userNo == null);

            this.circStatus = CircStatus.CHECKED_OUT;
            this.userNo = userNo;
            this.dateDue = dateDue;
            this.overdueReminderCount = 0;
            this.renewalCount = 0;
            this.checkoutDate = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
            insertIntoItemInfoList(chargedItemsByUserNo, this.userNo, this);

        }

        public void sendOverdueReminder() {

            overdueReminderCount++;
            // No reminder is actually sent - this is a dummy ILS.

        }

        public RenewErrorCode renew() {

            RenewErrorCode result = null;
            this.dateRenewed = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
            if ( this.renewalCount < MAX_RENEWALS ) {

                this.renewalCount++;
                this.dateDue.add(Calendar.DAY_OF_YEAR, RENEWAL_PERIOD);

            } else {

                result = RenewErrorCode.MAX_RENEWALS_EXCEEDED;

            }

            return result;
            
        }

        public void checkin() {

            removeFromItemInfoList(chargedItemsByUserNo, this.userNo, this);
            this.circStatus = CircStatus.ON_SHELF;
            this.userNo = null;
            this.dateDue = null;
            this.checkoutDate = null;
            this.dateRenewed = null;
            this.overdueReminderCount = 0;

        }

        public RequestInfo placeOnHold(String requestNo, String userNo, String pickupLoc) throws ToolkitException {

            RequestInfo requestInfo = new RequestInfo(requestNo, userNo, this.barcode, pickupLoc);
            return requestInfo;

        }

        public static ItemInfo getByBarcode(String barcode) {

            return itemInfos.get(barcode);

        }

        public static List<ItemInfo> getChargedItemsByUserNo(String userNo) {

            return chargedItemsByUserNo.get(userNo);

        }

    }


    protected static int nextRequestId = 39191;
    protected static GregorianCalendar todayPlus20Days;
    protected static GregorianCalendar todayPlus35Days;
    protected static GregorianCalendar todayPlus40Days;
    static {

        todayPlus20Days = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        todayPlus20Days.add(Calendar.DAY_OF_YEAR, 20);
        todayPlus35Days = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        todayPlus35Days.add(Calendar.DAY_OF_YEAR, 35);
        todayPlus40Days = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        todayPlus40Days.add(Calendar.DAY_OF_YEAR, 40);

    }

    static {

        try {

            UserInfo userMeganRichards = new UserInfo("760ecd7d-3e17-4433-e040-ae843b716ecd");
            UserInfo userABC = new UserInfo("abc");
            userABC.setPlainPassword("abc");
            UserInfo userDEF = new UserInfo("def");
            
            
            
            UserInfo dummyVufindUser = new UserInfo("vufind");
            dummyVufindUser.setPlainPassword("abc");
            UserInfo.userInfos.put(dummyVufindUser.userNo,dummyVufindUser);
            UserInfo.userInfos.put(userABC.userNo, userABC);
            
            UserOptionalFields dummyUserOptionalFields = new UserOptionalFields();
            dummyUserOptionalFields.setDateOfBirth(new GregorianCalendar( 1950,10,11));
            PersonalNameInformation nameInfo = new PersonalNameInformation();
            StructuredPersonalUserName structuredPersonalName = new StructuredPersonalUserName();
            structuredPersonalName.setGivenName("DummyName");
            structuredPersonalName.setInitials("DD");
            structuredPersonalName.setSurname("DummySurname");
            structuredPersonalName.setSuffix("DummySuffix");
            structuredPersonalName.setPrefix("DummyPrefix");
            nameInfo.setStructuredPersonalUserName(structuredPersonalName);
            NameInformation dummyNameInfo = new NameInformation();
            dummyNameInfo.setPersonalNameInformation(nameInfo);
            dummyUserOptionalFields.setNameInformation(dummyNameInfo);
            UserAddressInformation dummyAddress1 = new UserAddressInformation();
            ElectronicAddress elA1 = new ElectronicAddress();
            ElectronicAddressType type1 = new ElectronicAddressType("Personal");
            elA1.setElectronicAddressData("dummy.user@provider.com");
            elA1.setElectronicAddressType(type1);
//            dummyAddress1.setElectronicAddress(elA1);
            PhysicalAddress phA1 = new PhysicalAddress();
            StructuredAddress sA1 = new StructuredAddress();
            sA1.setCareOf("CareOf");
            sA1.setCountry("Neverland");
            sA1.setDistrict("District");
//            sA1.setLine1("Line 1");
//            sA1.setLine2("line 2");
            sA1.setLocality("Locality");
            sA1.setPostalCode("12345");
            sA1.setPostOfficeBox("box");
            sA1.setRegion("region");
            sA1.setStreet("street");
            phA1.setStructuredAddress(sA1);
            phA1.setPhysicalAddressType(new PhysicalAddressType("scheme","personal"));
            dummyAddress1.setPhysicalAddress(phA1);
            dummyAddress1.setUserAddressRoleType(new UserAddressRoleType("AddressRoleType"));
            UserAddressInformation dummyAddress2 = new UserAddressInformation();
            PhysicalAddress ph2 = new PhysicalAddress();
            
            UnstructuredAddress ua1 = new UnstructuredAddress();
            ua1.setUnstructuredAddressData("unstructred 51 , address 12123");
            ua1.setUnstructuredAddressType(new UnstructuredAddressType("schema","personal2"));
            ph2.setUnstructuredAddress(ua1);
            ph2.setPhysicalAddressType(new PhysicalAddressType("scheme","personal"));
            dummyAddress2.setUserAddressRoleType(new UserAddressRoleType("AddressRoleType"));
            dummyAddress2.setPhysicalAddress(ph2);
            List<UserAddressInformation> listAddress = new ArrayList<UserAddressInformation>();
            listAddress.add(dummyAddress1);
            listAddress.add(dummyAddress2);
            dummyUserOptionalFields.setUserAddressInformations(listAddress);
            dummyVufindUser.setOptionalFields(dummyUserOptionalFields);
            
            BibInfo dummyBook1 = new BibInfo("bib001", "On the road", "Jack Kerouac","Dummy publisher" , "2nd", "1948", "English", "3dfq435", MediaTypeEnum.BOOK);
            BibInfo dummyBook2 = new BibInfo("mzk.001168629", "Integrovany zachrany system", "Kerouac","Dummy publisher" , "3rd", "1958", "English", "001238920", MediaTypeEnum.BOOK);
            BibInfo bib123 = new BibInfo("mzk.001168631", "Almanach ke 100.vyroci zalozeni Gymnazia Moravske Budejovice", "Steinway", "Odd Books, Old York", "1st", "1967", "eng", "987", MediaTypeEnum.BOOK);

            HoldingInfo holdingInfo123_1 = new HoldingInfo("bib123-1", bib123, "Main", "2 copies");
            HoldingInfo holdingInfo123_2 = new HoldingInfo("bib123-2", dummyBook1, "Main", "3 copies");
            HoldingInfo holdingInfo123_3 = new HoldingInfo("bib123-3", dummyBook2, "Main", "3 copies");

            ItemInfo itemInfo123_1_1 = new ItemInfo("mzk.0011686311", holdingInfo123_1, "813.52 St34yV c.1", "copy 1");
            itemInfo123_1_1.checkout(dummyVufindUser.userNo, todayPlus20Days);

            ItemInfo itemInfo123_1_2 = new ItemInfo("mzk.0011686312", holdingInfo123_1, "813.52 St34yV c.2", "copy 2");
            itemInfo123_1_2.checkout(userMeganRichards.userNo, todayPlus20Days);
            
            ItemInfo itemInfoDummy1 = new ItemInfo("25556134324132", holdingInfo123_2, "813.52 St34yV c.1", "copy 1");
            RequestInfo requestDummy2 = itemInfoDummy1.placeOnHold("req0002", userABC.userNo, "Under the carpet");
            requestDummy2.fill();
            RequestInfo requestDummy1 = itemInfoDummy1.placeOnHold("req0001", dummyVufindUser.userNo, "Under the carpet");
            requestDummy1.fill();
            
            ItemInfo itemInfoDummy2 = new ItemInfo("mzk.0011686291", holdingInfo123_3, "234.5 Rt34yX", "copy 1");
            itemInfoDummy2.checkout(dummyVufindUser.userNo, todayPlus40Days);
            
            ItemInfo itemInfoDummy3 = new ItemInfo("mzk.0011686292", holdingInfo123_3, "234.5 Rt34yX", "copy 2");
            itemInfoDummy3.checkin();

            BibInfo bib345 = new BibInfo("mzk.001168634", "The Mouse That Roared",  "Rodent, Rodney", "Dog-eared Press, Chicago", "7th expanded", "1907", "eng", "765", MediaTypeEnum.BOOK);
            
            HoldingInfo holdingInfo345_1 = new HoldingInfo("bib345-1", bib345, "Law", "1 copy");

            ItemInfo itemInfo345_1_1 = new ItemInfo("mzk.0011686341", holdingInfo345_1, "813.52 St34yV c.1", "copy 1");
            itemInfo345_1_1.checkout(userMeganRichards.userNo, todayPlus40Days);
            itemInfo345_1_1.checkout(dummyVufindUser.userNo, todayPlus20Days);
            itemInfo345_1_1.renew();

            BibInfo bib567 = new BibInfo("567", "Sense and Non-sense",  "Merlot-Pouley", "Hieronymous Bach, Sheffield", "1st", "2012", "eng", "543", MediaTypeEnum.BOOK);

            HoldingInfo holdingInfo567_1 = new HoldingInfo("bib567-1", bib567, "Main", "2 copies");

            ItemInfo itemInfo567_1_1 = new ItemInfo("25556119105917", holdingInfo567_1, "113.52 St34yV c.1", "copy 1");
            itemInfo567_1_1.checkout(userABC.userNo, todayPlus40Days);
            itemInfo567_1_1.renew();
            RequestInfo requestInfo567_1_1 = itemInfo567_1_1.placeOnHold("1239", dummyVufindUser.userNo, "Main Circ Desk");
            // Backdate the request
            requestInfo567_1_1.createDate.add(Calendar.DAY_OF_YEAR, 10);
            
            requestInfo567_1_1.fill();

            ItemInfo itemInfo567_1_2 = new ItemInfo("25559171261518", holdingInfo567_1, "113.52 St34yV c.2", "copy 2");
            itemInfo567_1_2.checkout(userDEF.userNo, todayPlus35Days);
            RequestInfo requestInfo567_1_2_7891 = itemInfo567_1_2.placeOnHold("7891", userABC.userNo, "Main Circ Desk");
            // Backdate the request
            requestInfo567_1_2_7891.createDate.add(Calendar.DAY_OF_YEAR, 10);

            RequestInfo requestInfo567_1_2_8917 = itemInfo567_1_2.placeOnHold("8917", userMeganRichards.userNo, "South Side Branch");
            // Backdate the request
            requestInfo567_1_2_8917.createDate.add(Calendar.DAY_OF_YEAR, 10);

            BibInfo bib789 = new BibInfo("789", "Easy Money",  "Jones, Edward", "Vanity Publishers, Corning, NY.", "8th", "1990", "eng", "321", MediaTypeEnum.BOOK);

            HoldingInfo holdingInfo789_1 = new HoldingInfo("bib789-1", bib789, "Econ", "");

            ItemInfo itemInfo789_1_1 = new ItemInfo("25556819818614", holdingInfo789_1, "918.1 XH c.1", "copy 1");


        } catch (ToolkitException e) {

            throw new ExceptionInInitializerError(e);

        }

    }

    public static synchronized String getNextRequestNo() {

        return Integer.toString(nextRequestId++);

    }

}
