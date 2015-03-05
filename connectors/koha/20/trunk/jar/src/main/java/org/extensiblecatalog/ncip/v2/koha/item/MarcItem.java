package org.extensiblecatalog.ncip.v2.koha.item;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jirislav <br/>
 *         https://www.github.com/jirislav
 **/
public class MarcItem {

	private String leader;

	private Map<String, Map<String, String>> marcDataFieldMap = new HashMap<String, Map<String, String>>();

	private Map<String, String> marcControlFieldMap = new HashMap<String, String>();

	public void addDataField(String dataFieldTag, Map<String, String> subFieldMap) {
		marcDataFieldMap.put(dataFieldTag, subFieldMap);
	}

	public Map<String, String> getDataField(String dataFieldTag) {
		return marcDataFieldMap.get(dataFieldTag);
	}

	public void addControlField(String controlFieldTag, String controlFieldValue) {
		marcControlFieldMap.put(controlFieldTag, controlFieldValue);
	}

	public String getControlField(String controlFieldTag) {
		return marcControlFieldMap.get(controlFieldTag);
	}

	public void setLeader(String leaderVal) {
		leader = leaderVal;
	}

	public String getLeader() {
		return leader;
	}

}
