/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  */

package org.extensiblecatalog.ncip.v2.voyager.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * Utility class used to run regexes and get a specific group from the result
 * 
 * @author Eric Osisek
 */
public class RegexUtils 
{
	/**
	 * A reference to the logger for this class
	 */
	static Logger log = Logger.getLogger(RegexUtils.class);
	
	/**
	 * A helper method to run a regular expression against a String.  If the 
	 * regex matches the text, the content of the requested group will be 
	 * returned, otherwise this method will return null.
	 * 
	 * @param text The text to run the regex against.
	 * @param regex A regular expression with at least one group.
	 * @param groupNum The group to return.
	 * @return The contents of the first group in the first match, or null if there
	 *         were no matches.
	 */
	public String runRegex(String text, String regex, int groupNum)
	{
		// Create a Pattern based on the passed regex string
    	Pattern regexPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    
    	// Run the Pattern against the passed text string
    	Matcher regexMatcher = regexPattern.matcher(text);
    
    	// Return the first group if any matches were found, 
    	// otherwise return null
    	if(regexMatcher.find())
    		return regexMatcher.group(groupNum);
    	else
    		return null;
	}
	
	/**
	 * A helper method to run a regular expression against a String.  If the 
	 * regex matches the text, a list of the content of the requested group 
	 * accross all matches will be returned, otherwise this method will return 
	 * an empty list.
	 * 
	 * @param text The text to run the regex against.
	 * @param regex A regular expression with at least one group.
	 * @param groupNum The group to return.
	 * @return The contents of the first group in the first match, or null if there
	 *         were no matches.
	 */
	public List<String> runRegexMultipleResults(String text, String regex, int groupNum)
	{
		// A list to store the results
    	List<String> results = new ArrayList<String>();
    	
		// Create a Pattern based on the passed regex string
    	Pattern regexPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    
    	// Run the Pattern against the passed text string
    	Matcher regexMatcher = regexPattern.matcher(text);
    	
    	// For each match, add the contents of the target group
    	while(regexMatcher.find())
    		results.add(regexMatcher.group(groupNum));
    	
    	// Return the results
    	return results;
	}
	
	/**
	 * Given a String, returns a String identical to the provided String except that all
	 * regex special characters will be escaped for use in regular expressions.
	 * 
	 * For example, this method will return "\(hi\)" on input "(hi)"
	 * 
	 * @param escapeMe The string to escape
	 * @return The escaped string
	 */
	public String escapeRegexSpecialChars(String escapeMe)
	{
		if(log.isDebugEnabled())
			log.debug("Entering escapeRegexSpecialChars for the String: " + escapeMe);
		
		// Returns a pattern which matches all special characters
	    Pattern escaper = Pattern.compile("([\\(\\)\\.\\$\\^\\{\\}\\[\\]\\|\\*\\+\\?\\\\])");
	    
	    // Escape the special characters and return
	    String result = escaper.matcher(escapeMe).replaceAll("\\\\$1");
	    
	    if(log.isDebugEnabled())
			log.debug("The escaped String is: " + result);
	    
	    return result;
	}
}
