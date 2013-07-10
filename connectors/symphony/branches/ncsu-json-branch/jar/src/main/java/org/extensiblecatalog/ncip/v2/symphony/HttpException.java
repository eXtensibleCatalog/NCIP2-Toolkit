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


/**
 * Exception indicating an error occurred in HTTP communications with the JSON web service backend.  Clients
 * interested in the difference between backend communication errors and malformed messages can use the
 * following pattern:
 * <code>
 *     try {
 *         someOperation();
 *     } catch( HttpException hx ) {
 *         // report HTTP problem
 *     } catch( SymphonyConnectorException scx ) {
 *         // report error in communication.
 *     }
 * </code>
 * @author adam_constabaris@ncsu.edu
 */
public class HttpException extends RuntimeException {


    /**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public HttpException(String message) {
        super(message);
    }

    public HttpException(Throwable initCause) {
        super(initCause);
    }

    public HttpException(String message, Throwable initCause) {
        super(message, initCause);
    }
}
