package org.extensiblecatalog.ncip.v2.aleph.util;

public class RequestItemXMLBuilder {

	private String xml;
	private String initialParentElement;
	private String finalParentElement;
	private String pickupLocation;
	private String needBeforeDate;
	private String earliestDateNeeded;
	private String firstNote;
	private String secondNote;
	private String rush;

	/*
	 * Here is an example:
	 * 
	 * post_xml= <hold-request-parameters><last-interest-date>20141005</last-interest-date> <start-interest-date>20140906</start-interest-date> <note-1>dg</note-1> <note-2></note-2> <rush>N</rush> </hold-request-parameters>
	 */

	/**
	 * Creates "post_xml=" & does in fact nothing.
	 * 
	 */
	public RequestItemXMLBuilder() {
		xml = new String("post_xml=");
		initialParentElement = "";
		finalParentElement = "";
		pickupLocation = "";
		needBeforeDate = "";
		earliestDateNeeded = "";
		firstNote = "";
		secondNote = "";
		rush = "";
	}

	public RequestItemXMLBuilder setParent(String parentElement) {
		if (parentElement != null) {
			this.initialParentElement = "<" + parentElement + ">";
			this.finalParentElement = "</" + parentElement + ">";
		}
		return this;
	}

	public RequestItemXMLBuilder setPickupLocation(String pickupLocation) {
		if (pickupLocation != null)
			this.pickupLocation = "<pickup-location>" + pickupLocation + "</pickup-location>";
		return this;
	}

	public RequestItemXMLBuilder setLastInterestDate(String needBeforeDate) {
		if (needBeforeDate != null)
			this.needBeforeDate = "<last-interest-date>" + needBeforeDate + "</last-interest-date>";
		return this;
	}

	public RequestItemXMLBuilder setStartInterestDate(String earliestDateNeeded) {
		if (earliestDateNeeded != null)
			this.earliestDateNeeded = "<start-interest-date>" + earliestDateNeeded + "</start-interest-date>";
		return this;
	}

	public RequestItemXMLBuilder setFirstNote(String firstNote) {
		if (firstNote != null)
			this.firstNote = "<note-1>" + firstNote + "</note-1>";
		return this;
	}

	public RequestItemXMLBuilder setSecondNote(String secondNote) {
		if (secondNote != null)
			this.firstNote = "<note-2>" + secondNote + "</note-2>";
		return this;
	}

	public RequestItemXMLBuilder setRush(String rush) {
		if (rush != null)
			this.rush = "<rush>" + rush + "</rush>";
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(xml);
		sb.append(initialParentElement);
		sb.append(pickupLocation);
		sb.append(needBeforeDate);
		sb.append(earliestDateNeeded);
		sb.append(firstNote);
		sb.append(secondNote);
		sb.append(rush);
		sb.append(finalParentElement);
		return sb.toString();
	}
}
