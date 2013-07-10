/**
 * Copyright (c) 2012 North Carolina State University
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.extensiblecatalog.ncip.v2.symphony.json;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateFormat subclass that allows for parsing dates emitted by Sirsi API calls, which may be
 * full (with time information) or partial (without).
 * @author adam_constabaris@ncsu.edu, $LastChangedBy$
 * @since 1.2
 * @version $LastChangedRevision$
 */
public class SirsiDateFormat extends DateFormat {
	
	
	DateFormat full = new SimpleDateFormat("MM/dd/yyyy,HH:mm");

	DateFormat partial = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	@Override
	public StringBuffer format(Date arg0, StringBuffer arg1, FieldPosition arg2) {
		return full.format(arg0,arg1,arg2);
	}

	@Override
	public Date parse(String dateString, ParsePosition position) {
        Date d = full.parse(dateString,position);
		return d != null ? d: partial.parse(dateString,position);
	}
	
	
	/**
	 * Creates a copy of this object.
	 * <p>
	 *  This method was implemented for use by Jackson, which tries very hard to maintain
	 *  threadsafety and always clones the dateformat.
	 *  </p>
	 */
	@SuppressWarnings({"CloneDoesntCallSuperClone"}) // eclipse doesn't like this, intellij wants it
    @Override
	public Object clone() {
		return new SirsiDateFormat();
	}

}
