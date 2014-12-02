package org.extensiblecatalog.ncip.v2.aleph.AlephXServices.xservice;

import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * This class is used to call an X Service within Aleph.
 * Any necessary parameters should first be initialized before
 * executing the remote X Service.
 * However, all this initialization will be taken care of by XServiceFactory class.
 * 
 * @author Rick Johnson (NDU)
 */
public class XService implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6422599660124064422L;
	private Map<String,List<String>> parameters;
	
	public XService(String xServiceName){
		addParameter(AlephConstants.PARAM_X_SERVICE_NAME,xServiceName);
	}
	
	/**
	 * addParameter
	 * 
	 * This method will update a parameters map stored internally with the
	 * new parameter name and value pair passed in.
	 * When, it adds the name and value pair, it will first look to see if there is already
	 * a value for that parameter in the Map, if so it will append to the list.
	 * Otherwise, it will create a new value list for that parameter.  Allow duplicates is set
	 * to false (call other method if you need to override).
	 * 
	 * @param name Name of the parameter
	 * @param value Value of the parameter
	 * @return void
	 */
	public void addParameter(String name, String value){
		addParameter(name,value,false);
	}
	
	/**
	 * addParameter
	 * 
	 * This method will update a parameters map stored internally with the
	 * new parameter name and value pair passed in.
	 * When, it adds the name and value pair, it will first look to see if there is already
	 * a value for that parameter in the Map, if so it will append to the list.
	 * Otherwise, it will create a new value list for that parameter.
	 * 
	 * @param name Name of the parameter
	 * @param value Value of the parameter
	 * @param allowDuplicates If true, allow the same value to be in a parameter list more than once.
	 * @return void
	 */
	public void addParameter(String name, String value, boolean allowDuplicates){
		if (parameters==null){
			parameters = new HashMap<String,List<String>>();
		}
		List<String> paramList = parameters.get(name);
		if (paramList==null){
			paramList = new ArrayList<String>();
		}
		if (allowDuplicates||!paramList.contains(value)){
			paramList.add(value);
		}
		parameters.put(name, paramList);
	}
	
	/**
	 * getParameters
	 * 
	 * Return the current parameters map for this XService call.
	 * The parameters map contains parameter name (String) mapped
	 * to a list of parameter values (List<String>).
	 * 
	 * @return the parameters Map in form Map<String,List<String>
	 */
	public Map<String,List<String>> getParameters(){
		if (parameters==null) parameters = new HashMap<String,List<String>>();
		return parameters;
	}
	
	/**
	 * Get parameter values for the parameter name passed
	 * 
	 * @param param
	 * @return List<String> containing values, zero length list if no values in map
	 */
	public List<String> getParameterValues(String param){
		List<String> values = getParameters().get(param);
		if (values==null){
			values = new ArrayList<String>();
		}
		return values;
	}
	
	/**
	 * encodeParameters
	 * 
	 * @param parameters Map containing lists of values for each parameter name (key in map)
	 * @return String containing encoded parameters string in UTF-8
	 * 
	 * @throws IOException
	 */
	protected String encodeParameters(Map<String,List<String>> parameters) throws IOException{
		boolean first = true;
		StringBuffer data = new StringBuffer();
		if (parameters!=null){
			for (String paramName : parameters.keySet()){
				if (paramName!=null){
					List<String> values = parameters.get(paramName);
					if (values!=null){
						for (String value : values){
							if (value!=null){
								if (!first){
									data.append("&");
								}
								data.append(URLEncoder.encode(paramName,"UTF-8"));
								data.append("=");
								data.append(URLEncoder.encode(value,"UTF-8"));
								
								first = false;
							}
						}
					}
				}
			}
		}
		return data.toString();
	}
	
	/**
	 * execute
	 * 
	 * Executes this X Service call against a remote xServer
	 * 
	 * @param xServerName
	 * @param xServerPort
	 * @param sslEnabled
	 * @return xml Document object containing response
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Document execute(String xServerName, String xServerPort, 
			boolean sslEnabled) throws AlephException, IOException, ParserConfigurationException, SAXException{
		if (xServerName==null||xServerPort==null) throw new AlephException ("X-Server Name and/or port undefined");
		URL url = new URL(getUrlString(xServerName,xServerPort,sslEnabled));
		
		return postHttpRequest(url);
	}
	
	public String getUrlString(String xServerName, String xServerPort, boolean sslEnabled){
		String urlString = sslEnabled?"https://":"http://";
		urlString += xServerName+":"+xServerPort+"/X";
		return urlString;
	}
	
	/**
	 * postHttpRequest
	 * 
	 * Post an http request with the url object passed
	 * 
	 * @param url The url object to connect and write to
	 * @param parameters A map of String to a List since a parameter can have more than one value that needs to be written
	 * @return xml Document object containing response
	 * 
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException 
	 */
	protected Document postHttpRequest(URL url) throws IOException,ParserConfigurationException, SAXException{
		
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		if(url!=null){
	        // Construct data
			String data = encodeParameters(getParameters());
	    
	        // Send data
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.write(data.toString());
	        wr.flush();
	    
	        // Get the response
			doc = docBuilder.parse(conn.getInputStream());
	        wr.close();
		}
		return doc;
	}
}
