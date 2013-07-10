/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.initiator.implprof1;

import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.initiator.NCIPClient;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * This class implements an initiator for the NCIP Implementation Profile 1 transport mechanisms of HTTP and HTTPS.
 * Note: Raw Socket transport will be added in a future version.
 */
public class NCIPImplProf1Client implements NCIPClient {
    /**
     * The target address or URL of the NCIP responder.
     */
    String targetAddress = null;
    /**
     * A sub-class of {@link HostnameVerifier}; only used for HTTPS transport, and optional even then.
     */
    HostnameVerifier hostnameVerifier = null;
    /**
     * The default connect timeout interval of 30000 ms.
     */
    protected static final int DEFAULT_CONNECT_TIMEOUT = 30000;
    /**
     * The default read timeout interval of 30000 ms.
     */
    protected static final int DEFAULT_READ_TIMEOUT = 180000;

    /**
     * The connect timeout for this client's connections.
     */
    protected int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    /**
     * The read timeout for this client's connections.
     */
    protected int readTimeout = DEFAULT_READ_TIMEOUT;

    /**
     * Construct a new NCIPImplProf1Client for the provided target address, with default timeout values.
     *
     * @param targetAddress the network address of the target NCIP responder
     */
    public NCIPImplProf1Client(String targetAddress) {
        this(targetAddress, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * Construct a new NCIPImplProf1Client with no target address (that must be set before calling {@link #sendMessage}
     * and default timeout values.
     */
    public NCIPImplProf1Client() {
        this(null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * Construct a new NCIPImplProf1Client with the supplied target address and timeout values.
     *
     * @param targetAddress  the network address of the target NCIP responder
     * @param connectTimeout the connect timeout for this client's connections
     * @param readTimeout    the read timeout for this client's connections
     */
    public NCIPImplProf1Client(String targetAddress, int connectTimeout, int readTimeout) {
        this.targetAddress = targetAddress;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    /**
     * Set the network address of the target NCIP responder for this client.
     *
     * @param targetAddress the network address of the target NCIP responder
     */
    public void setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
    }

    /**
     * Ge the network address of the target NCIP responder for this client.
     *
     * @return the network address
     */
    public String getTargetAddress() {
        return targetAddress;
    }

    /**
     * Get the {@link HostnameVerifier} for this client.
     *
     * @return the hostname verifier
     */
    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    /**
     * Set the {@link HostnameVerifier} for this client.
     *
     * @param hostnameVerifier the hostname verifier used by this client
     */
    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    /**
     * Get the connect timeout for this client's connections.
     *
     * @return the connect timeout in milliseconds
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * Set the connect timeout for this client's connections.
     *
     * @param connectTimeout the connect timeout in milliseconds for this client's connections
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * Get the read timeout for this client's connections.
     *
     * @return the read timeout in milliseconds
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * Set the read timeout for this client's connections.
     *
     * @param readTimeout the read timeout in milliseconds for this client's connections
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * Send the provided NCIP initiation message, represented as an array of bytes, to the current target address
     * using the current connect and read timeouts.
     *
     * @param initiationMsgBytes the initiation message as an array of bytes (network octets)
     * @return the {@link InputStream} from which the response message can be read
     * @throws ServiceException if the exchange of messages with the NCIP responder fails
     */
    public InputStream sendMessage(byte[] initiationMsgBytes) throws ServiceException {
        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {

            // Make sure that the url starts with https:// or http://
            if (targetAddress.substring(0, targetAddress.indexOf("://")).compareToIgnoreCase("https") != 0
                && targetAddress.substring(0, targetAddress.indexOf("://")).compareToIgnoreCase("http") != 0) {
                MalformedURLException e =
                    new MalformedURLException("The target address' protocol is not https or http.");
                throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, e);
            }

            URL targetURL = new URL(targetAddress);

            HttpURLConnection con = (HttpURLConnection)targetURL.openConnection();
            if (hostnameVerifier != null) {
                if (con instanceof HttpsURLConnection) {
                    ((HttpsURLConnection)con).setHostnameVerifier(hostnameVerifier);
                }
            }
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setConnectTimeout(connectTimeout);
            con.setReadTimeout(readTimeout);
            con.setRequestProperty("Content-Type", "application/xml; charset=\"utf-8\"");
            con.setRequestProperty("Content-length", String.valueOf(initiationMsgBytes.length));

            outputStream = con.getOutputStream();
            outputStream.write(initiationMsgBytes);
            outputStream.flush();

            String httpResponseMsg = con.getResponseMessage();
            if (httpResponseMsg.equalsIgnoreCase("OK")) {
                inputStream = con.getInputStream();
            } else {
                throw new ServiceException(ServiceError.SERVICE_UNAVAILABLE, httpResponseMsg, null);
            }
        } catch (SSLException e) {
            throw new ServiceException(ServiceError.RUNTIME_ERROR, e);
        } catch (MalformedURLException e) {
            throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, e);
        } catch (ProtocolException e) {
            throw new ServiceException(ServiceError.RUNTIME_ERROR,
                "Exception preparing HttpsURLConnection.", e);
        } catch (IOException e) {
            throw new ServiceException(ServiceError.SERVICE_UNAVAILABLE,
                "Exception connecting to or exchanging messages with target.", e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // Do nothing
                }
            }
        }

        return inputStream;
    }

}
