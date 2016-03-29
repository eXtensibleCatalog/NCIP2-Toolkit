package org.extensiblecatalog.ncip.v2.voyager.util;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * Utility class used to communicate with Voyager OPAC server (opacsvr)
 *
 * @author Chris Delis
 */

public class VoyagerOpacsvr {

   /**
   * A reference to the logger for this class
   */
   static Logger log = Logger.getLogger(VoyagerOpacsvr.class);
	
   private Socket socket;
   private InputStream inputStream = null;
   private BufferedOutputStream outputStream = null;
   //private String host;
   //private int port;
   private String version;
   private int number_of_trailing_nulls_in_packet = 2;

   static public void main(String args[]) {

       String version = null;

       String item_host = null;
       int    item_port = -1;
       String bib_id = null;
       //String item_ubid = null;
       String item_id = null;

       String patron_ubid = null;
       String patron_last_name = null;
       String patron_barcode = null;

       String pickup_ubid = null;
       String pickup_location_id = null;

       Properties props = new Properties();
       try {
           InputStream in = new FileInputStream("ub.properties");
           props.load(in);

           version = props.getProperty("version", "2005.0@voyager");

           item_host = props.getProperty("item_host");
           item_port = Integer.parseInt(props.getProperty("item_port"));
           bib_id = props.getProperty("bib_id");
           //item_ubid = props.getProperty("item_ubid");
           item_id = props.getProperty("item_id");

           patron_ubid = props.getProperty("patron_ubid");
           patron_last_name = props.getProperty("patron_last_name");
           patron_barcode = props.getProperty("patron_barcode");

           pickup_ubid = props.getProperty("pickup_ubid");
           pickup_location_id = props.getProperty("pickup_location_id");
       } catch (Exception e) {
           System.err.println("couldn't parse properties file! Exception: " + e);
           System.exit(1);
       }

       VoyagerOpacsvr item_opacsvr = new VoyagerOpacsvr(item_host, item_port, version);
       item_opacsvr.init();

/*
       HashMap<String,HashMap<String,String>> dataList = item_opacsvr.getCircLocations(pickup_ubid);
       for(String keyList: dataList.keySet()){
           System.out.println("\n\nList item :: " + keyList);
           HashMap<String,String> data = dataList.get(keyList);
           for(String key: data.keySet()){
              System.out.println(key  +" :: "+ data.get(key));
           }
       }
*/

       int rc = item_opacsvr.requestUB(patron_last_name,
                         patron_barcode,
                         patron_ubid,
                         bib_id,
                         item_id,
                         pickup_location_id,
                         pickup_ubid);

       System.out.println("RC=" + rc);

       item_opacsvr.close();
   }

   public VoyagerOpacsvr(String host, int port, String version)
   {
      try {
         //this.host = host;
         //this.port = port;
         this.version = version;
         this.socket  = new Socket(host, port);
         inputStream = socket.getInputStream();
         outputStream = new BufferedOutputStream(socket.getOutputStream());
      } catch (IOException e) {
         log.error("Proxy server cannot connect to " + host + ":"
            + port + ":\n" + e);
      }
   }

   private List<String> packetizeString(String str) {
      return packetizeString(str, false);
   }

   private List<String> packetizeString(String str, boolean init) {
      StringReader sr= new StringReader(str);
      BufferedReader br= new BufferedReader(sr);
      String line;
      boolean endOfPacket = false;
      List<String> packet = new java.util.ArrayList<String>();

      if (init) number_of_trailing_nulls_in_packet = 0;

      try {
          while ((line = br.readLine()) != null) {
             if (line.length() == 0) {
                endOfPacket = true;
                if (init) number_of_trailing_nulls_in_packet++;
             } else if (endOfPacket) {
               log.error("Premature empty newline in init_packet! " +
                                  "Empty newlines denote terminating null bytes and " +
                                  "should only be set at the very end of your " +
                                  "packet definition!");
             } else {
                packet.add(line);
             }
          }
      } catch (IOException e) {
         log.error("Couldn't packetize string: " + str + " Exception: " + e);
      }
      return packet;
   }

   // returns final return code from opacsvr: 0 = success 
   public int requestUB(String patron_last_name,
                         String patron_barcode,
                         String patron_ubid,
                         String bib_id,
                         String item_id,
                         String pickup_location_id,
                         String pickup_ubid) {

       String mfhd_id = ""; // we need to find this based off of the item_id
       boolean foundMFHD = false;

       HashMap<String,String> patron_data = login(patron_last_name, patron_barcode, patron_ubid);
       if (!patron_data.get("RC").equals("0")) {
           log.error("Login failed.");
           return -1;
       }

       String patron_id = patron_data.get("PI");
       //log.info("patron_id=" + patron_id);
       String patron_group_id = patron_data.get("GI");
       //log.info("patron_group_id=" + patron_group_id);

       // sanity check
       if (!patron_ubid.equals(patron_data.get("UBID"))) {
           log.error("ERROR: patron_ubid (" + patron_ubid + ") != login data (" + patron_data.get("UBID") + ")");
           return -1;
       }

       HashMap<String,HashMap<String,String>> mfhdDataList = getMFHDs(bib_id);
       for(String mfhdKeyList: mfhdDataList.keySet()){
           HashMap<String,String> mfhdData = mfhdDataList.get(mfhdKeyList);
           if (mfhdData.containsKey("MI")) {
               String this_mfhd_id = mfhdData.get("MI");
               //log.info("this_mfhd_id=" + this_mfhd_id);

               ///////////////////////////////////////////////////////////////
               // We used to (for some reason I can't recall) check and make sure that"IS=1", but not any more.
               // HashMap<String,String> item_data = getMFHDItem(mfhd_id);
               // for(String item_key: item_data.keySet()){
                   // log.info(item_key  +" :: "+ item_data.get(item_key));
               // }
               ///////////////////////////////////////////////////////////////

               HashMap<String,HashMap<String,String>> availableUBItemsList = getAvailableUBItems(bib_id,
                                                                                             patron_id,
                                                                                             patron_group_id,
                                                                                             patron_ubid);
               for(String availableUBItemsKeyList: availableUBItemsList.keySet()){
                   HashMap<String,String> ubItemData = availableUBItemsList.get(availableUBItemsKeyList);
                   if (ubItemData.containsKey("IM")) {
                       String this_item_id = ubItemData.get("IM");
                       //log.info("this_item_id=" + this_item_id);
                       if (this_item_id.equals(item_id)) {
                           mfhd_id = this_mfhd_id;
                           foundMFHD = true;
                           //log.info("Found mfhd_id for item_id (" + item_id + ") = " + mfhd_id);
                           break;
                       }
                   }
               }
               if (foundMFHD) {
                   break;
               }
           }
       }
       if (! foundMFHD) {
           log.warn("Couldn't find MFHD for item ID: " + item_id + ".");
       }
       //log.info("mfhd_id=" + mfhd_id);

       // Create a patron stub record
       HashMap<String,String> systemData = availSysReq(patron_id, patron_ubid, mfhd_id);
       if (!systemData.get("RC").equals("0")) {
           log.error("Couldn't create stub record.");
           return -1;
       }

       HashMap<String,String> requestData = addUBRequest(pickup_ubid,
                                                         bib_id,
                                                         patron_id,
                                                         patron_ubid,
                                                         patron_group_id,
                                                         item_id,
                                                         pickup_location_id);

       int rc = Integer.parseInt(requestData.get("RC"));
       return rc;
   }

   public void init() {
      List<String> init_request_packet = packetizeString(_INIT(version), true);
      sendPacket(init_request_packet);
      readPacket();
   }

   private String _INIT(String version) {

       String fmt =
         "[HEADER]\n" +
         "CO=EISI\n" +
         "AP=OPAC\n" +
         "VN=1.00\n" +
         "TO=120\n" +
         "SK\n" +
         "SQ=0\n" +
         "RQ=INIT\n" +
         "RC=0\n" +
         "[DATA]\n" +
         "AP=OPAC\n" +
         "VN=%s\n" +
         "IP=127.0.0.1\n" +
         "\n" +
         "\n"
         ;

         return String.format(fmt, version);
   }

   public  HashMap<String,String> login(String lastName, String barcode, String ubid) {
      List<String> packet = packetizeString(_OPCPATLOGN(lastName, barcode, ubid));
      sendPacket(packet);
      HashMap <String,String> data = new HashMap<String,String>();
      readPacket(data);
      return data;
   }

   private String _OPCPATLOGN(String lastName, String barcode, String ubid) {

      String fmt =
         "[HEADER]\n" +
         "CO=EISI\n" +
         "AP=WOPAC\n" + 
         "VN=1.00\n" +
         "TO=999\n" +
         "SK=\n" +
         "SQ=1\n" +
         "RQ=OPCPATLOGN\n" +
         "[DATA]\n" +
         "PB=%s\n" +
         "LN=%s\n" +
         "UBID=%s\n" +
         "CLI_IP=127.0.0.1\n" +
         "\n" +
         "\n"
         ;

         return String.format(fmt, barcode, lastName, ubid);
   }

   private  HashMap<String,String> addUBRequest(String pickup_ubid,
                                                String bib_id,
                                                String patron_id,
                                                String patron_ubid,
                                                String patron_group_id,
                                                String item_id,
                                                String pickup_location_id) {

      List<String> packet = packetizeString(_ADDUBREQUEST(pickup_ubid,
                                                          bib_id,
                                                          patron_id,
                                                          patron_ubid,
                                                          patron_group_id,
                                                          item_id,
                                                          pickup_location_id));
      sendPacket(packet);
      HashMap <String,String> data = new HashMap<String,String>();
      readPacket(data);
      return data;
   }


   private String _ADDUBREQUEST(String pickup_ubid,
                                String bib_id,
                                String patron_id,
                                String patron_ubid,
                                String patron_group_id,
                                String item_id,
                                String pickup_location_id) {
      String fmt =
          "[HEADER]\n" +
          "CO=EISI\n" +
          "AP=OPAC\n" +
          "VN=1.00\n" +
          "TO=1000\n" +
          "SK=\n" +
          "SQ=1\n" +
          "RQ=ADDUBREQUEST\n" +
          "RC=0\n" +
          "[DATA]\n" +
          "UBRR=1\n" +
          "PUDK=%s\n" +
          "BI=%s\n" +
          "PI=%s\n" +
          "PIDK=%s\n" +
          "GI=%s\n" +
          "IM=%s\n" +
          "PULI=%s\n" +
          "ED=21\n" +
          "UBRQLMT=9999\n" +
          "CLI_IP=127.0.0.1\n" +
          "\n" +
          "\n"
          ;

         String pickup_dbk = shortenUBID(pickup_ubid);
         String patron_dbk = shortenUBID(patron_ubid);

         return String.format(fmt, pickup_dbk, bib_id, patron_id, patron_dbk, patron_group_id, item_id, pickup_location_id);
   }

   private HashMap<String,HashMap<String,String>> getAvailableUBItems(String bib_id,
                                                                      String patron_id,
                                                                      String patron_group_id,
                                                                      String patron_ubid) {
      HashMap<String,HashMap<String,String>> data = new HashMap<String,HashMap<String,String>>();
      List<String> packet = packetizeString(_AVAILUBITEMS(bib_id, patron_id, patron_group_id, patron_ubid));
      boolean keepGoing = true;
      while (keepGoing) {
         sendPacket(packet);
         keepGoing = readPacket(data, "IM");
      }
      return data;
   }

   private String _AVAILUBITEMS(String bib_id, String patron_id, String patron_group_id, String patron_ubid) {

      String fmt =
          "[HEADER]\n" +
          "CO=EISI\n" +
          "AP=OPAC\n" +
          "VN=1.00\n" +
          "TO=799\n" +
          "SK=\n" +
          "SQ=14\n" +
          "RQ=AVAILUBITEMS\n" +
          "RC=0\n" +
          "[DATA]\n" +
          "BI=%s\n" +
          "PI=%s\n" +
          "GI=%s\n" +
          "DBK=%s\n" +
          "\n" +
          "\n"
          ;

         // UB does not want ubid in the form of 1@LIBRARY; it wants just the LIBRARY portion
         String dbk = shortenUBID(patron_ubid);

         return String.format(fmt, bib_id, patron_id, patron_group_id, dbk);
   }

   static private String shortenUBID(String ubid) {
       String dbk = ubid;
       int inx = ubid.indexOf("@");
       if (inx > 0) {
           dbk = ubid.substring(inx+1);
       }
       return dbk;
   }


   public HashMap<String,HashMap<String,String>> getCircLocations(String ubid) {
      HashMap<String,HashMap<String,String>> data = new HashMap<String,HashMap<String,String>>();
      List<String> packet = packetizeString(_CIRCLOCATION(ubid));
      boolean keepGoing = true;
      while (keepGoing) {
         sendPacket(packet);
         keepGoing = readPacket(data, "LI");
      }
      return data;
   }

   private String _CIRCLOCATION(String ubid) {

      String fmt =
          "[HEADER]\n" +
          "CO=EISI\n" +
          "AP=WOPAC\n" +
          "VN=1.00\n" +
          "TO=999\n" +
          "SK=\n" +
          "SQ=1\n" +
          "RQ=CIRCLOCATION\n" +
          "[DATA]\n" +
          "UBID=%s\n" +
          "CLI_IP=127.0.0.1\n" +
          "\n" +
          "\n"
          ;

         return String.format(fmt, ubid);
   }

   private HashMap<String,HashMap<String,String>> getMFHDs(String bib_id) {
      HashMap<String,HashMap<String,String>> data = new HashMap<String,HashMap<String,String>>();
      List<String> packet = packetizeString(_MFHD_IDS(bib_id));
      boolean keepGoing = true;
      while (keepGoing) {
         sendPacket(packet);
         keepGoing = readPacket(data, "MI");
      }
      return data;
   }

   private String _MFHD_IDS(String bib_id) {

      String fmt =
          "[HEADER]\n" +
          "CO=EISI\n" +
          "AP=WOPAC\n" +
          "VN=1.00\n" +
          "TO=999\n" +
          "SK=\n" +
          "SQ=1\n" +
          "RQ=MFHD_IDS\n" +
          "[DATA]\n" +
          "BI=%s\n" +
          "CLI_IP=127.0.0.1" +
          "\n" +
          "\n"
          ;

         return String.format(fmt, bib_id);
   }

   /*
   private HashMap<String,String> getMFHDItem(String mfhd_id) {
      HashMap<String,String> data = new HashMap<String,String>();
      List<String> packet = packetizeString(_MFHD_ITEM(mfhd_id));
      boolean keepGoing = true;
      while (keepGoing) {
         sendPacket(packet);
         keepGoing = readPacket(data);
      }
      return data;
   }*/

   /*
   private String _MFHD_ITEM(String mfhd_id) {

      String fmt =
          "[HEADER]\n" +
          "CO=EISI\n" +
          "AP=WOPAC\n" +
          "VN=1.00\n" +
          "TO=999\n" +
          "SK=\n" +
          "SQ=1\n" +
          "RQ=MFHD_ITEM\n" +
          "[DATA]\n" +
          "MI=%s\n" +
          "CLI_IP=127.0.0.1\n" +
          "\n" +
          "\n"
          ;

         return String.format(fmt, mfhd_id);
   }*/

   // Creates necessary system requirements (e.g., patron stub records) in owning library's database
   private HashMap<String,String> availSysReq(String patron_id, String patron_ubid, String mfhd_id) {
      List<String> packet = packetizeString(_AVAILSYSREQ(patron_id, patron_ubid, mfhd_id));
      sendPacket(packet);
      HashMap <String,String> data = new HashMap<String,String>();
      readPacket(data);
      return data;
   }

   private String _AVAILSYSREQ(String patron_id, String patron_ubid, String mfhd_id) {

      String fmt =
          "[HEADER]\n" +
          "CO=EISI\n" +
          "AP=OPAC\n" +
          "VN=1.00\n" +
          "TO=1000\n" +
          "SK=\n" +
          "SQ=2\n" +
          "RQ=AVAILSYSREQ\n" +
          "RC=0\n" +
          "[DATA]\n" +
          "PI=%s\n" +
          "UBID=%s\n" +
          "UBRR=1\n" +
          "MI=%s\n" +
          "DBC=LOCAL\n" +
          "\n" +
          "\n"
          ;

         return String.format(fmt, patron_id, patron_ubid, mfhd_id);
   }


   public void close() {
       try {
           socket.close();
       } catch (Exception e) {
       }
   }

   protected void finalize() throws Throwable {
       try {
           close();
       } catch (Throwable t) {
       } finally {
           super.finalize();
       }
   }


   private boolean readPacket()
   {
      return readPacket(null);
   }

   private boolean readPacket(HashMap<String,String> data)
   {
      HashMap<String,HashMap<String,String>> allData = new HashMap<String,HashMap<String,String>>();
      boolean b = readPacket(allData, null);
      if (data != null) {
         data.putAll(allData.get(""));
      }
      return b;
   }

   private boolean readPacket(HashMap<String,HashMap<String,String>> data, String lineItemKey)
   {
      byte[] nb = {0};
      boolean morePacketsLeftToRead = false;
      byte[] request = new byte[1024];
      int bytesRead;
      int nulls = 0;
      boolean done = false;
      StringBuffer parm = new StringBuffer(1024);
      String lineItemValue = "";
      HashMap<String,String> thisData = new HashMap<String,String>();

      try {

        while (!done && (bytesRead = inputStream.read(request)) != -1) {
           for (int j=0; j<bytesRead; j++) {
              if (request[j] == nb[0]) {
                 nulls++;
                 String parmStr = parm.toString();
                 if (parmStr.equals("RC=7")) {
                     morePacketsLeftToRead = true;
                 }
                 if (data != null) {
                     int inx = parmStr.indexOf("=");
                     if (inx > 0) {
                        String name = parmStr.substring(0,inx);
                        String value = parmStr.substring(inx+1);
                        if (lineItemKey != null && name.equals(lineItemKey)) {
                            data.put(lineItemValue, thisData);
                            lineItemValue = value;
                            thisData = new HashMap<String,String>();
                        }
                        thisData.put(name, value);
                    }
                 }
                 parm = new StringBuffer(1024);
              } else {
                 parm.append((char)request[j]);
                 nulls = 0;
              }
              if (nulls >= number_of_trailing_nulls_in_packet) {
                 done = true;
                 break;
              }
           }
        }
        if (data != null && !thisData.isEmpty()) {
            data.put(lineItemValue, thisData);
        }

      } catch (IOException ie) {
         log.error("readPacket exception: " + ie);
      }
      return morePacketsLeftToRead;
   }

   private boolean sendPacket(List<String> packet)
   {
      byte[] nb = {0};

      boolean success = true;

      String str;
      try {
         int i;
         for (i=0; i<packet.size(); i++) {
             str = packet.get(i);
             outputStream.write(str.getBytes(), 0, str.length());
             outputStream.write(nb, 0, 1);
         }

         for (i=0; i<number_of_trailing_nulls_in_packet; i++) {
            outputStream.write(nb, 0, 1);
         }

         outputStream.flush();

      } catch (IOException ie) {
         log.error("sendPacket exception:" + ie);
         success =  false;
      }

      return success;
   }

}


