/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

public class StatisticsBean implements ToolkitComponent {

    private static final Logger LOG = Logger.getLogger(StatisticsBean.class);

    public static final String COMPONENT_NAME = StatisticsBean.class.getSimpleName();

    // The numeric label is meant to allow ordering them the rows of statistics in the order in which they occur.
    public static final String[] RESPONDER_UNMARSHAL_MESSAGE_LABELS = {"1", "Unmarshal Message"};
    public static final String[] RESPONDER_CREATE_DATA_LABELS = {"2", "Create Data From Message"};
    public static final String[] RESPONDER_PERFORM_SERVICE_LABELS = {"3", "Perform Service"};
    public static final String[] RESPONDER_CREATE_MESSAGE_LABELS = {"4", "Create Message From Data"};
    public static final String[] RESPONDER_MARSHAL_MESSAGE_LABELS = {"5", "Marshal Message"};
    public static final String[] RESPONDER_TOTAL_LABELS = {"6", "Total"};

    public static final String[] INITIATOR_CREATE_MESSAGE_LABELS = {"1", "Create Message From Data"};
    public static final String[] INITIATOR_MARSHAL_MESSAGE_LABELS = {"2", "Marshal Message"};
    public static final String[] INITIATOR_SEND_MESSAGE_LABELS = {"3", "Send Message"};
    public static final String[] INITIATOR_UNMARSHAL_MESSAGE_LABELS = {"4", "Unmarshal Message"};
    public static final String[] INITIATOR_CREATE_DATA_LABELS = {"5", "Create Data From Message"};
    public static final String[] INITIATOR_TOTAL_LABELS = {"6", "Total"};

    protected int maxLabels = 0;

    public class StatsRecord {

        protected Object[] labels;
        protected long count;
        protected long totalIntervals;

        /**
         * Makes a copy of the labels array.
         * @param labels
         * @param startTime
         * @param endTime
         */
        public StatsRecord(Object[] labels, long startTime, long endTime) {

            // System.arraycopy does a shallow copy, but that's ideal for a one-dimensional array of Strings
            // because Strings are idempotent (i.e. they never change).
            this.labels = new Object[labels.length];
            System.arraycopy(labels, 0, this.labels, 0, labels.length);
            add(startTime, endTime);

        }

        /**
         * Returns the array (not a copy of it).
         * @return
         */
        public Object[] getLabels() {

            return this.labels;
        }

        // TODO: Rather than always have to call back to this class to count the labels, why not just translate the object array into a String[] in the ctor?
        /**
         * Return a cCount the number of labels
         * @return
         */
        public long getLabelCount() {

            return countLabels(this.labels);

        }

        /**
         * Returns the count of intervals in the record.
         * @return
         */
        public long getCount() {

            return this.count;

        }

        /**
         * Returns the total of all intervals in the record.
         * @return
         */
        public long getTotalIntervals() {

            return this.totalIntervals;

        }

        /**
         * Add an interval (endTime - startTime) to the record and increments the count of intervals.
         * @param startTime
         * @param endTime
         */
        public final void add(long startTime, long endTime) {

            this.count++;
            this.totalIntervals += Math.max(endTime - startTime, 0);

        }

    }

    private static Map<String, StatsRecord> statisticsMap = new TreeMap<String, StatsRecord>();
    private static final String separator = " ";

    public StatisticsBean() {

        // Do nothing

    }

    public StatisticsBean(Properties properties) {

        // TODO: Set order of steps from properties - default to the order needed by NCIP Responder

    }

    public StatisticsBean(StatisticsBeanConfiguration config) {

        // TODO: Set order of steps from config - default to the order needed by NCIP Responder

    }

    public synchronized void record(long startTime, long endTime, Object ... labels) {

        String key = createKey(labels);
        LOG.debug(key + ": " + ( endTime - startTime) + " milliseconds.");

        maxLabels = Math.max(maxLabels, countLabels(labels));

        StatsRecord statsRecord = statisticsMap.get(key);
        if ( statsRecord != null ) {

            statsRecord.add(startTime, endTime);

        } else {

            statisticsMap.put(key, new StatsRecord(labels, startTime, endTime));

        }

    }

    public int countLabels(Object[] labelArray) {

        int count = 0;

        for ( Object obj : labelArray ) {

            if ( obj != null && obj.getClass().isArray() ) {

                Object[] objArray = (Object[])obj;
                count+= countLabels(objArray);

            } else {

                count++;

            }
        }

        return count;

    }

    /**
     * Contents of the labels array are either Objects, or arrays of Objects.
     * This will call toString() on each, in order, recursing into each label that is itself an array.
     *
     * @param labels
     * @return
     */
    public static String createKey(Object[] labels) {

        String key;
        if ( labels == null || labels.length == 0 ) {

            key = "";

        } else if ( labels.length == 1 ) {

            if ( labels[0] != null && labels[0].getClass().isArray() ) {

                key = createKey((Object[])labels[0]);

            } else {

                key = labels[0].toString();

            }

        } else {

            StringBuffer sb = new StringBuffer();
            // Copy up to but not including the last label so we don't append the separator after the last label.
            int stopIndex = labels.length - 1;
            for ( int i = 0; i < stopIndex; i++ ) {

                Object label = labels[i];
                if ( label != null && label.getClass().isArray() ) {

                    sb.append(createKey((Object[])label)).append(separator);

                } else {

                    sb.append(label).append(separator);

                }

            }
            sb.append(labels[stopIndex]);
            key = sb.toString();
        }

        return key;

    }

    public synchronized long getMaxLabels() {

        return maxLabels;

    }

    public synchronized Map<String, StatsRecord> getStatsRecords() {

        return new TreeMap<String, StatsRecord>(statisticsMap);

    }

    public synchronized void clear() {

        statisticsMap.clear();
        maxLabels = 0;
        
    }

    public String createCSVReport() {

        // Create a csv report to stdout.
        DecimalFormat formatter = new DecimalFormat("0.00");
        StringBuilder statsReport = new StringBuilder();
        // Header line
        for ( long i = 0; i < getMaxLabels(); i++ ) {

            statsReport.append("Type").append(",");
        }
        statsReport.append("Count, Total ms.,Average ms.").append(System.getProperty("line.separator"));

        // Body of report
        for ( Map.Entry<String, StatisticsBean.StatsRecord> statRecord : getStatsRecords().entrySet() ) {

            buildKeyCSV(statsReport, statRecord.getValue().getLabels());

            if ( statRecord.getValue().getLabelCount() < getMaxLabels() ) {

                for ( long i = statRecord.getValue().getLabelCount(); i <= getMaxLabels(); i++ ) {

                    statsReport.append(",");

                }

            }

            float floatAvg = ((float)statRecord.getValue().getTotalIntervals()) / ((float)statRecord.getValue().getCount());

            statsReport.append(statRecord.getValue().getCount()).append(",")
                .append(statRecord.getValue().getTotalIntervals()).append(",")
                .append(formatter.format(floatAvg))
                .append(System.getProperty("line.separator"));

        }

        return statsReport.toString();

    }

    private void buildKeyCSV(StringBuilder responseMsg, Object keyPart) {

        if ( keyPart != null && keyPart.getClass().isArray() ) {

            Object[] keyPartArray = (Object[])keyPart;
            for ( Object obj : keyPartArray ) {

                buildKeyCSV(responseMsg, obj);

            }

        } else {

            responseMsg.append(keyPart).append(",");

        }

    }


}
