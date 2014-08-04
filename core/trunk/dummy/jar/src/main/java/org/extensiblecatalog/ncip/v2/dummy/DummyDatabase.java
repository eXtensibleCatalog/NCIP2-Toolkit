/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;

import java.util.*;

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

        public UserInfo(String userNo) {

            this.userNo = userNo;

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
            UserInfo userDEF = new UserInfo("def");

            BibInfo bib123 = new BibInfo("123", "Of Mice and Men", "Steinway", "Odd Books, Old York", "1st", "1967", "eng", "987", MediaTypeEnum.BOOK);

            HoldingInfo holdingInfo123_1 = new HoldingInfo("bib123-1", bib123, "Main", "2 copies");

            ItemInfo itemInfo123_1_1 = new ItemInfo("25556192919132", holdingInfo123_1, "813.52 St34yV c.1", "copy 1");
            itemInfo123_1_1.checkout(userABC.userNo, todayPlus20Days);

            ItemInfo itemInfo123_1_2 = new ItemInfo("25556192919198", holdingInfo123_1, "813.52 St34yV c.2", "copy 2");
            itemInfo123_1_2.checkout(userMeganRichards.userNo, todayPlus20Days);

            BibInfo bib345 = new BibInfo("345", "The Mouse That Roared",  "Rodent, Rodney", "Dog-eared Press, Chicago", "7th expanded", "1907", "eng", "765", MediaTypeEnum.BOOK);

            HoldingInfo holdingInfo345_1 = new HoldingInfo("bib345-1", bib345, "Law", "1 copy");

            ItemInfo itemInfo345_1_1 = new ItemInfo("25556192919171", holdingInfo345_1, "813.52 St34yV c.1", "copy 1");
            itemInfo345_1_1.checkout(userMeganRichards.userNo, todayPlus40Days);
            itemInfo345_1_1.renew();

            BibInfo bib567 = new BibInfo("567", "Sense and Non-sense",  "Merlot-Pouley", "Hieronymous Bach, Sheffield", "1st", "2012", "eng", "543", MediaTypeEnum.BOOK);

            HoldingInfo holdingInfo567_1 = new HoldingInfo("bib567-1", bib567, "Main", "2 copies");

            ItemInfo itemInfo567_1_1 = new ItemInfo("25556119105917", holdingInfo567_1, "113.52 St34yV c.1", "copy 1");
            itemInfo567_1_1.checkout(userABC.userNo, todayPlus40Days);
            itemInfo567_1_1.renew();
            RequestInfo requestInfo567_1_1 = itemInfo567_1_1.placeOnHold("1239", userDEF.userNo, "Main Circ Desk");
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
