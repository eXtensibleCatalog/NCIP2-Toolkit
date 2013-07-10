package org.extensiblecatalog.ncip.v2.millennium;

public class ErrorCodeMessage {

	public String ErrorMessage (int statusCode) {
		String errorMsg = null;

		if (statusCode == 100) {
			errorMsg = "ERROR! - Service not support.";
		}
		if (statusCode == 101) {
			errorMsg = "ERROR! - Function not support.";
		}
		if (statusCode == 102) {
			errorMsg = "OK! - Pattern found for ";
		}
		if (statusCode == 103) {
			errorMsg = "ERROR! - Pattern not found for ";
		}
		if (statusCode == 104) {
			errorMsg = "ERROR! - ArrayLists not equal.";
		}
		if (statusCode == 105) {
			errorMsg = "ERROR! - item is missing.";
		}
		if (statusCode == 106) {
			errorMsg = "ERROR! - User/Password is missing.";
		}
		if (statusCode == 107) {
			errorMsg = "ERROR! - Invalid user/Password.";
		}
		if (statusCode == 180) {
			errorMsg = "OK - Successful completed.";
		}
		
		//HTTP status codes
		if (statusCode == 200) {
			errorMsg = "OK - Successful HTTP requests.";
		}
		if (statusCode == 280) {
			errorMsg = "OK - Successful Login.";
		}
		if (statusCode == 281) {
			errorMsg = "OK - Successful Received page.";
		}
		if (statusCode == 301) {
			errorMsg = "WARNING! - Moved Permanently.";
		}
		if (statusCode == 302) {
			errorMsg = "WARNING! - Move Temporarily.";
		}
		if (statusCode == 470) {
			errorMsg = "ERROR! - Unknown.";
		}
		if (statusCode == 480) {
			errorMsg = "ERROR! - IOException.";
		}
		if (statusCode == 481) {
			errorMsg = "ERROR! - IOException. Invalided URL";
		}
		if (statusCode == 482) {
			errorMsg = "ERROR! - IOException. SSL not found";
		}
		if (statusCode == 490) {
			errorMsg = "ERROR! - HttpException.";
		}

		return errorMsg;
	}
}
