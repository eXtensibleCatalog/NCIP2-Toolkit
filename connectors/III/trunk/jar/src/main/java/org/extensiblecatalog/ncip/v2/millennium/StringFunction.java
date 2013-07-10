/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.millennium;

import org.apache.log4j.Logger;
import java.util.StringTokenizer;

public class StringFunction { 
	
	private static final Logger LOG = Logger.getLogger(MillenniumRemoteServiceManager.class); 
	
	/***
	 * Return the right string of delimiter
	 * @param parseString
	 * @param delimiter
	 * @return strRight
	 */
	public String Rightstr (String parseString, String delimiter) {
		String[] strList;
		String strRight = null;
		if (parseString.length() > 0 && delimiter.length() > 0) {
			//LOG.debug("StringFunction - RightStr - Total split: " + parseString.split(delimiter).length);
			strList = parseString.split(delimiter);
			for (int i = 0; i < strList.length; i++) {
				strRight = strList[i];
				//LOG.debug("StringFunction - RightStr - strRight["+i+"] = " + strList[i]);
			}
		}
		return strRight;
	}
	
	/***
	 * Return the left string of delimiter
	 * @param parseString
	 * @param delimiter
	 * @return strRight
	 */
	public String Leftstr (String parseString, String delimiter) {
		String[] strList;
		String strLeft = null;
		if (parseString.length() > 0 && delimiter.length() > 0) {
			//LOG.debug("StringFunction - RightStr - Total split: " + parseString.split(delimiter).length);
			strList = parseString.split(delimiter);
			if (strList.length > 0) {
				strLeft = strList[0];
			}
		}
		return strLeft;
	}
	
	
	
	/***
	 * Converts a delimited string into an array of string tokens.
	 * 
	 * @param String
	 *            [] The 'separator' separated string.
	 * @param String
	 *            The string separator.
	 * @return String A string array of the original tokens.
	 */
	public static final String[] stringToArray(String str, String separators) {
		StringTokenizer tokenizer;
		String[] array = null;
		int count = 0;

		if (str == null)
			return array;

		if (separators == null)
			separators = ",";

		tokenizer = new StringTokenizer(str, separators);
		if ((count = tokenizer.countTokens()) <= 0) {
			return array;
		}

		array = new String[count];

		int ix = 0;
		while (tokenizer.hasMoreTokens()) {
			array[ix] = tokenizer.nextToken();
			ix++;
		}
		return array;
	}
}

