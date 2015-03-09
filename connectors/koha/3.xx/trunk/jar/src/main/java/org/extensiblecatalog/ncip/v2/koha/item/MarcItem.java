package org.extensiblecatalog.ncip.v2.koha.item;

import java.util.HashMap;
import java.util.Map;

import org.extensiblecatalog.ncip.v2.koha.util.LocalConfig;

/**
 * @author jirislav <br/>
 *         https://www.github.com/jirislav
 **/
public class MarcItem {

	private String leader;

	private boolean holdingsItemSubfieldsInitialized = false;

	private Map<String, Map<String, String>> marcDataFieldMap = new HashMap<String, Map<String, String>>();

	private Map<String, String> marcControlFieldMap = new HashMap<String, String>();

	private Map<String, String> holdingsItemSubfields;

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

	public Map<String, String> getHoldingsItemSubfields() {
		if (!holdingsItemSubfieldsInitialized)
			this.initializeHoldingsItemSubfields();
		return holdingsItemSubfields;
	}

	private void setHoldingsItemDatafield(Map<String, String> holdingsItemSubfields) {
		this.holdingsItemSubfields = holdingsItemSubfields;
	}

	private void initializeHoldingsItemSubfields() {
		this.setHoldingsItemDatafield(this.getDataField(LocalConfig.getMarcHoldingsItemTag()));
		this.holdingsItemSubfieldsInitialized = true;
	}

}
