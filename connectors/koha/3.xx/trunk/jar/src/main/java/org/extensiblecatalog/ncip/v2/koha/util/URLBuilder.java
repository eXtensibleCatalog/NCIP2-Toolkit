package org.extensiblecatalog.ncip.v2.koha.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;

public class URLBuilder {

	private Map<String, String> params = new HashMap<String, String>();
	private boolean secured;
	private String[] path;
	private String base;
	private String port;

	public URLBuilder setPath(String... path) {
		this.path = path;
		return this;
	}

	public URLBuilder appendPath(String... path) {
		this.path = (String[]) ArrayUtils.addAll(this.path, path);
		return this;
	}

	public URLBuilder setBase(String server, String port, boolean isSecured) {
		base = server;
		this.port = port;
		secured = isSecured;
		return this;
	}

	/**
	 * By default is built not secure connection You should call this function
	 * only if you want to establish secured connection (https URL prefix)
	 * 
	 * @param server
	 */
	public URLBuilder setBase(String server, String port) {
		base = server;
		this.port = port;
		secured = false;
		return this;
	}

	/**
	 * By default is built not secure connection You should call this function
	 * only if you want to establish secured connection (https URL prefix)
	 * 
	 * @param server
	 */
	public URLBuilder setBase(String server) {
		base = server;
		port = "80";
		secured = false;
		return this;
	}

	public URLBuilder addRequest(String key, String value) {
		params.put(key, value);
		return this;
	}

	public URLBuilder addRequest(String key) {
		params.put(key, null);
		return this;
	}

	public URLBuilder parseLink(String link) {
		String[] linkParts = link.split("/");
		String[] serverAndPort = linkParts[2].split(":");
		String port = null;

		if (serverAndPort.length == 2)
			port = serverAndPort[1];

		this.setBase(serverAndPort[0], port == null || port.isEmpty() ? "80" : port, linkParts[0].contains("https"));

		this.setPath(Arrays.copyOfRange(linkParts, 3, linkParts.length));
		return this;
	}

	public URL toURL() throws MalformedURLException {
		StringBuilder sb = new StringBuilder();

		if (secured == false) {
			sb.append("http://");
		} else {
			sb.append("https://");
		}

		sb.append(base);
		sb.append(":");
		sb.append(port);

		for (String folder : path) {
			sb.append("/");
			sb.append(folder);
		}

		if (params != null && !params.isEmpty()) {
			int remaining = params.size();
			sb.append("?");
			for (Entry<String, String> entry : params.entrySet()) {

				sb.append(entry.getKey());
				if (entry.getValue() != null) {
					sb.append("=");
					sb.append(entry.getValue());
				}
				if (--remaining >= 1) {
					sb.append("&");
				}
			}
		}
		return new URL(sb.toString());
	}

}
