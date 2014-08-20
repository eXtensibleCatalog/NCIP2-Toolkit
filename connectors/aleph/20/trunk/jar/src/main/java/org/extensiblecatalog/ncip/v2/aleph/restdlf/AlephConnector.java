package org.extensiblecatalog.ncip.v2.aleph.restdlf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class AlephConnector {
	
	public AlephConnector() {
		
	}
	
	public static class ParamBuilder {
		
		public ParamBuilder(String[] path) {
			super();
			this.path = path;
		}

		String[] path;
		
		Map<String, String> params = new HashMap<String, String>();
		
		public ParamBuilder addParam(String key, String param) {
			params.put(key, param);
			return this;
		}
		
		public URL done() throws MalformedURLException {
			StringBuilder sb = new StringBuilder();
			
			for (String folder : path) {
				sb.append("/");
				sb.append(folder);
			}
			
			if(params != null) {
				int remaining = params.size();
				sb.append("?");
				for (Entry<String, String> entry : params.entrySet()) {
					
					sb.append(entry.getKey());
					sb.append("=");
					sb.append(entry.getValue());
					
					if(remaining > 1) {
						sb.append("&");
						remaining--;
					}
				}
			}
			return new URL(sb.toString());
		}
		
		
	}
	
	public ParamBuilder buildURL(String... path){
		return new ParamBuilder(path);
	}

	protected Document getXMLResponse(URL url) throws ParserConfigurationException, IOException, SAXException {
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		if(url!=null){
			
	        // Send data
	        URLConnection conn = url.openConnection();
	        conn.setDoOutput(true);
	    
	        // Get the response
			doc = docBuilder.parse(conn.getInputStream());
		}
		return doc;
	}
}
