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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.symphony.json.CancelHoldResponse;
import org.extensiblecatalog.ncip.v2.symphony.json.DisplayUserResponse;
import org.extensiblecatalog.ncip.v2.symphony.json.JsonMapper;
import org.extensiblecatalog.ncip.v2.symphony.json.RenewItemResponse;
import org.extensiblecatalog.ncip.v2.symphony.json.SymphonyResponse;
import org.springframework.beans.factory.InitializingBean;

/**
 * A service that handles HTTP communications with a SirsiDynix server.
 * <p>
 * Methods in this class typically receive NCIPInitiationData objects of various types, converts them
 * to calls against services running on a SirsiDynix server that return JSON serialized objects,
 * and converts those objects into NCIPResponseData objects of the appropriate type.
 * </p>
 * <p>
 *    The bare minimum needed to use an instance of this class is to set its
 *    <code>symphonyBaseURL</code> property, and initialized with a call to
 *    its <code>afterPropertiesSet()</code> method.  For finer-grained control over the parameters
 *    in the HTTP communication (e.g. requiring HTTP basic auth, maximum number of clients, etc.),
 *    see the source.
 * </p>
 * <p>
 * Instances of this class <em>should be</em> thread-safe, since this instance is designed to be shared by
 * all instances of an NCIP service; in the default configuration, this is achieved by
 * using a thread-safe instance of <code>HttpClient</code> by using the
 * <code>ThreadSafeClientConnManager</code> as the connection manager.
 * </p>
 * @author adam_constabaris@ncsu.edu
 */
public class SymphonyHttpService implements InitializingBean {

    private static final Logger logger = Logger.getLogger(SymphonyHttpService.class);

    /**
     * Default value for <code>maxConnections</code>
     */
    public static final int DEFAULT_MAX_CONNECTIONS = 25;

    /**
     * Default number of milliseconds to wait for data to be sent over the
     * socket (after a connection has been established) before timing out.
     */
    public static final int DEFAULT_SOCKET_TIMEOUT = 5000;

    /**
     * Default number of milliseconds to wait for a connection to be
     * established.
     */
    public static final int DEFAULT_CONNECTION_TIMEOUT = 5000;

    // PATH_ constants correspond to the PATH_INFO passed to
    // the JSON service's CGI script that select the function to be executed.
    private String PATH_LOOKUP_USER = "/lookupUser";

    private String PATH_RENEW_ITEM = "/renewItem";

    private String PATH_CANCEL_HOLD = "/cancelHold";

    private String symphonyBaseURL = "http://my.server.edu/cgi-bin/ncip.pl";

    private HttpClient client;

    private int maxConnections = DEFAULT_MAX_CONNECTIONS;

    private int socketTimeout = DEFAULT_SOCKET_TIMEOUT;

    private int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;

    private ClientConnectionManager connectionManager;

    private JsonMapper mapper;
    
    /**
     * Default constructor.
     * <p>
     * Note that newly created instances are not ready to use; they must be configured
     * with at least a <code>symphonyBaseURL</code> and initialized with
	 * <code>afterPropertiesSet()</code>.
	 * </p>  
     */
    public SymphonyHttpService() {
    }

    /**
     * Initializes the instance.
     * <p>
     * Calling this method will set the <code>httpClient</code>, <code>connectionManager</code>,
     * and <code>jsonMapper</code> properties to 'sensible' defaults if they have not been supplied
     * prior to its execution.
     * </p>
     * @see #getClient()
     * @see #getClientConnectionManager()
     * @see #setMapper(JsonMapper)
     * 
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if ( client == null ) {
            connectionManager = getClientConnectionManager();
            client = new DefaultHttpClient(connectionManager);
            HttpParams params = client.getParams();
            params.setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "utf-8");
            params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, socketTimeout);
            params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
        }
        if ( mapper == null ) {
            mapper = new JsonMapper();
        }
    }
    
    /**
     * Gets the URL to the JSON service CGI script.
     * @return
     */
    public String getSymphonyBaseURL() {
        return symphonyBaseURL;
    }

    /**
     * Sets the URL to the JSON service CGI script.
     * @param symphonyBaseURL
     */
    public void setSymphonyBaseURL(String symphonyBaseURL) {
        this.symphonyBaseURL = symphonyBaseURL;
    }

    /**
     * Gets the HTTP client instance used to communicate with the SirsiDynix server.  
     * The instance will be either the one previously supplied via <code>setHttpClient()</code>,
     * or an instance created by <code>afterPropertiesSet()</code>.
     * <p>
     *  'Default' instances are created with the connection manager returned by <code>getClientConnectionManager()</code>
     *   and use the <code>socketTimeout</code> and <code>connectionTimeout</code> properties of this object.
     * </p>
     * @return an externally supplied client OR the 'default' instance, or <code>null</code> if the instance
     * has not been initialized. 
     */
    public HttpClient getClient() {
        return client;
    }

    /**
     * Sets an HTTP client to be used by this class.  Note that the object supplied will not be further configured
     * by this instance, so the instance should have a property configured <code>ClientConnectionManager</code>
     * etc.
     * @param client the client to be used by this class
     * @throws IllegalArgumentException if <code>client</code> is null.
     */
    public void setClient(HttpClient client) {
    	if ( client == null ) {
    		throw new IllegalArgumentException("you may not supply a null HttpClient instance.");
    	}
        this.client = client;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    /**
     * Sets the amount of time to wait between packets from the Sirsi server.
     * @param socketTimeout the number of milliseconds to wait before SO_TIMEOUT.
     */
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * Sets the amount of time to wait for a connection to be established with the remote
     * server before giving up.
     * @param connectionTimeout the number of milliseconds allowed to establish a connection.
     */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public ClientConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public void setConnectionManager(ClientConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }


    public void setClientConnectionManager(ClientConnectionManager cm) {
        this.connectionManager = cm;
    }


    public ClientConnectionManager getClientConnectionManager() {
        synchronized(this) {
            if (connectionManager == null ) {
                connectionManager = _createDefaultConnectionManager();
            }
        }
        return connectionManager;
    }

    /**
     * Creates the default sort of connection manager for this class. In the
     * normal use case, this will be a thread-safe connection manager with
     * <code>maxConnections</code> available connections in total, with the
     * same value available per-<code>HttpRoute</code>.  This represents sensible
     * defaults for the situation where we are only ever connecting to a single
     * Sirsi host.
     * @return
     */
    private ClientConnectionManager _createDefaultConnectionManager() {
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();
        cm.setMaxTotal(maxConnections);
        cm.setDefaultMaxPerRoute(maxConnections);
        return cm;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }


    /**
     * Does a user lookup.
     * @param userId
     * @return
     * @throws HttpException if there is an error communicating with the JSON service on the Symphony server.
     */
    public DisplayUserResponse doUserLookup(String userId, boolean fiscal, boolean charges,boolean requests) throws HttpException {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add( new BasicNameValuePair("uid", userId) );
        params.add(new BasicNameValuePair("fiscalAccount", String.valueOf(fiscal)));
        params.add(new BasicNameValuePair("loanedItems", String.valueOf(charges)));
        params.add(new BasicNameValuePair("requestedItems", String.valueOf(requests)));
        return executeRequest(PATH_LOOKUP_USER,params, DisplayUserResponse.class);
    }

    public RenewItemResponse doRenewItem(String userId, String itemId) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add( new BasicNameValuePair("uid", userId) );
        params.add( new BasicNameValuePair("item_id", itemId) );
        return executeRequest(PATH_RENEW_ITEM, params, RenewItemResponse.class);
    }

    public CancelHoldResponse doCancelHold(String userId, String itemId) throws HttpException {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add( new BasicNameValuePair("uid", userId) );
        params.add( new BasicNameValuePair("item_id", itemId) );
        params.add( new BasicNameValuePair("hold_type", "COPY") );
        return executeRequest(PATH_CANCEL_HOLD, params, CancelHoldResponse.class);
    }

    private <T extends SymphonyResponse<?>> T executeRequest(String pathInfo, List<NameValuePair> params, Class<T> responseClass)
        throws HttpException {
        String queryString = URLEncodedUtils.format(params,"utf-8");
        URI uri = URI.create(symphonyBaseURL + pathInfo + "?" + queryString);
        logger.info("Executing query on " + uri.toString() );
        HttpGet get = new HttpGet(uri);
        HttpEntity entity = null;
        try {
            HttpResponse resp = client.execute(get);
            entity = resp.getEntity();
            return mapper.readStream(entity.getContent(), responseClass);
        } catch (UnsupportedEncodingException e) {
            throw new HttpException(e);
        } catch (ClientProtocolException e) {
            throw new HttpException(e);
        } catch (IOException e) {
            throw new HttpException(e);
        } finally {
            if ( entity != null ) {
                try {
                    entity.getContent().close();
                } catch( IOException iox ) {
                    // ignore
                }
            }
        }
    }

    public JsonMapper getMapper() {
        return mapper;
    }

    public void setMapper(JsonMapper mapper) {
        this.mapper = mapper;
    }
}
