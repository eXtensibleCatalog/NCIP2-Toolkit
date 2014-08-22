package org.extensiblecatalog.ncip.v2.aleph.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class URIBuilder {
	
	public URIBuilder(String[] path) {
		super();
		this.path = path;
	}

	String[] path;
	
	Map<String, String> params = new HashMap<String, String>();
	
	public URIBuilder addParam(String key, String param) {
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
