/**
 * Copyright (c) 2012 North Carolina State University
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.extensiblecatalog.ncip.v2.symphony;

import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.UserId;

import java.util.*;

/**
 * Utility methods for converting from relatively primitive types to types expected by toolkit APIs.
 * @author adam_constabaris@ncsu.edu
 *
 */
public class ConversionUtils {

    private ConversionUtils() {}

    /**
     * Converts a simple string message to a List of of NCIP <code>Problem</code>
     * instances.  Useful for reporting problems communicating with the backend.
     * @param problemType
     * @param message
     * @return
     */
    public static List<Problem> getProblems(String problemType, String message) {
        Problem p = new Problem(SymphonyConstants.BACKEND_ERROR,"Unknown Element", message);
        return Collections.singletonList(p);
    }

    public static UserId getUserId(String userId) {
        UserId rv = new UserId();
        rv.setUserIdentifierValue(userId);
        return rv;
    }

    /**
     * Gets an <code>ItemId</code> object given a string that identifies the object.
     * @param itemId
     * @return
     */
    public static ItemId getItemId(String itemId) {
        ItemId id = new ItemId();
        id.setItemIdentifierValue(itemId);
        return id;
    }

    public static GregorianCalendar toGregorianCalendar(Date date) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        return gc;
    }

    /**
     * Gets a calendar set to a date way in the past.
     * @return
     */
    public static GregorianCalendar getFarPastDate() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.YEAR, -44);
        gc.set(Calendar.MONTH, 2);
        gc.set(Calendar.DATE, 15);
        return gc;
    }

    /**
     * Gets a new calendar
     * @return
     */
    public static GregorianCalendar getAYearHence() {
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.YEAR, 1);
        return gc;
    }
}
