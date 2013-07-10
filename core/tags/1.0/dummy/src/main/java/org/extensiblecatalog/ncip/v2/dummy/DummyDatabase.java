/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;

import org.apache.log4j.Logger;

import java.util.*;

public class DummyDatabase {

    private static final Logger LOG = Logger.getLogger(DummyDatabase.class);

    protected static final String LOG4J_CONFIG_FILENAME = "src\\main\\resources\\log4j.properties";

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

        public RequestInfo(String requestNo, String userNo, String itemBarcode, String pickupLoc) {

            assert(requestNo != null);
            assert(userNo != null);
            assert(itemBarcode != null);

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
        protected String language;
        protected String oclcNo;
        protected HoldingInfo[] holdings;

        public BibInfo(String bibNo, String title, String language, String oclcNo) {

            this.bibNo = bibNo;
            this.title = title;
            this.language = language;
            this.oclcNo = oclcNo;
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

        }

        public static HoldingInfo getByHoldingId(String holdingId)
        {
            return holdingInfos.get(holdingId);
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
        protected GregorianCalendar dateDue;
        protected int overdueReminderCount;

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
            insertIntoItemInfoList(chargedItemsByUserNo, this.userNo, this);

        }

        public void sendOverdueReminder() {

            overdueReminderCount++;
            // No reminder is actually sent - this is a dummy ILS.

        }

        public void checkin() {

            removeFromItemInfoList(chargedItemsByUserNo, this.userNo, this);
            this.circStatus = CircStatus.ON_SHELF;
            this.userNo = null;
            this.dateDue = null;
            this.overdueReminderCount = 0;

        }

        public RequestInfo placeOnHold(String requestNo, String userNo, String pickupLoc) {

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

        UserInfo userABC = new UserInfo("abc");
        UserInfo userDEF = new UserInfo("def");

        BibInfo bib123 = new BibInfo("123", "Of Mice and Men", "eng", "987");

        HoldingInfo holdingInfo123_1 = new HoldingInfo("bib123-1", bib123, "Main", "2 copies");

        ItemInfo itemInfo123_1_1 = new ItemInfo("25556192919132", holdingInfo123_1, "813.52 St34yV c.1", "copy 1");
        itemInfo123_1_1.checkout(userABC.userNo, todayPlus20Days);

        ItemInfo itemInfo123_1_2 = new ItemInfo("25556192919198", holdingInfo123_1, "813.52 St34yV c.2", "copy 2");

        BibInfo bib345 = new BibInfo("345", "The Mouse That Roared", "eng", "765");

        HoldingInfo holdingInfo345_1 = new HoldingInfo("bib345-1", bib345, "Law", "1 copy");

        ItemInfo itemInfo345_1_1 = new ItemInfo("25556192919171", holdingInfo345_1, "813.52 St34yV c.1", "copy 1");

        BibInfo bib567 = new BibInfo("567", "Sense and Non-sense", "eng", "543");

        HoldingInfo holdingInfo567_1 = new HoldingInfo("bib567-1", bib567, "Main", "2 copies");

        ItemInfo itemInfo567_1_1 = new ItemInfo("25556119105917", holdingInfo567_1, "113.52 St34yV c.1", "copy 1");
        itemInfo567_1_1.checkout(userABC.userNo, todayPlus40Days);
        itemInfo567_1_1.placeOnHold("1239", userABC.userNo, "Main Circ Desk");

        ItemInfo itemInfo567_1_2 = new ItemInfo("25559171261518", holdingInfo567_1, "113.52 St34yV c.2", "copy 2");
        itemInfo567_1_2.checkout(userDEF.userNo, todayPlus35Days);
        itemInfo567_1_2.placeOnHold("7891", userABC.userNo, "Main Circ Desk");


    }

    public String getNextRequestId() {

        return Integer.toString(nextRequestId++);

    }

}
