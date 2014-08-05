package org.extensiblecatalog.ncip.v2.aleph.AlephXServices;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParserUtil implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7645919186202568199L;
	/**
	 * Parses the value out of a marc field (varfield, datafield, etc) node
	 * from xml formatted for marc data.  Uses a space as
	 * the delimiter between items.
	 * 
	 * @param marcField Node containing data
	 * @return value or null if not found
	 */
	public static String getValueFromMarcField(Node marcField){
		return getValueFromMarcField(marcField, ' ');
	}
	
	/**
	 * Parses the value out of a marc field (varfield, datafield, etc) node
	 * from xml formatted for marc data
	 * 
	 * @param marcField Node containing data
	 * @param delimiter character delimiter used in between items concatenated together
	 * @return value or null if not found
	 */
	public static String getValueFromMarcField(Node marcField, char delimiter){
		return getValueFromMarcField(marcField, null, null, delimiter);
	}
	
	/**
	 * Parses the value out of a marc field (varfield, datafield, etc) node
	 * from xml formatted for marc data.  Uses a space as the delimiter
	 * 
	 * @param marcField Node containing data
	 * @param subfieldAttributeName if null uses default (LABEL_ATTRIBUTE)
	 * @param subfieldAttributeValue if not null it will just grab node value with this attribute value, otherwise it will concatenate all subfield values
	 * @return value or null if not found
	 */
	public static String getValueFromMarcField(Node marcField, String subfieldAttributeName, String subfieldAttributeValue){
		return getValueFromMarcField(marcField, subfieldAttributeName, subfieldAttributeValue, ' ');
	}
	
	/**
	 * Parses the value out of a marc field (varfield, datafield, etc) node
	 * from xml formatted for marc data
	 * 
	 * @param marcField Node containing data
	 * @param subfieldAttributeName if null uses default (LABEL_ATTRIBUTE)
	 * @param subfieldAttributeValue if not null it will just grab node value with this attribute value, otherwise it will concatenate all subfield values
	 * @param delimiter character delimiter used in between items concatenated together (default is a space if null)
	 * @return value or null if not found
	 */
	public static String getValueFromMarcField(Node marcField, String subfieldAttributeName, String subfieldAttributeValue, char delimiter){
		String value = null;
		boolean first = true;
		if (subfieldAttributeName==null) subfieldAttributeName = AlephConstants.LABEL_ATTRIBUTE;
		if (marcField.hasChildNodes()){
			NodeList subFields = marcField.getChildNodes();
			if (subFields.getLength()>0){
				//concat all values together
				for (int j=0; j<subFields.getLength(); j++){
					Node subField = subFields.item(j);
					if (subField.hasChildNodes()){
						if (subfieldAttributeValue!=null){
							if (subField.hasAttributes()){
								Node label = subField.getAttributes().getNamedItem(subfieldAttributeName);
								if (label!=null&&subfieldAttributeValue.equals(label.getNodeValue())){
									Node valueNode = subField.getFirstChild();
									if (valueNode!=null){
										value = valueNode.getNodeValue();
										value = trimWhitespaceAndTrailingSpecialCharacters(value);
									}
								}
							}
						} else {
							Node valueNode = subField.getFirstChild();
							if (valueNode!=null){
								if (first){
									value = valueNode.getNodeValue();
								} else {
									if (delimiter==' ') value += delimiter+valueNode.getNodeValue();
									else value += delimiter+" "+valueNode.getNodeValue();
								}
								if (value!=null){
									first = false;
									value = value.trim();
								}
							}
						}
					}
				}
			}
		}
	
		return replaceSpecialCharacters(value);
	}
	
	/**
	 * Return the value of the reply xml node if it exists in the document
	 * @param doc
	 * @return
	 */
	public static String getReply(Document doc){
		return getNodeTextValue(doc, AlephConstants.REPLY_NODE);
	}
	
	/**
	 * Return the value of the error xml node if it exists in the document
	 * @param doc
	 * @return
	 */
	public static String getError(Document doc){
		return getNodeTextValue(doc, AlephConstants.ERROR_NODE);
	}
	
	/**
	 * Return the due date value for renewals etc.
	 * 
	 * @param doc
	 * @return
	 */
	public static String getDueDate(Document doc){
		return getNodeTextValue(doc, AlephConstants.DUE_DATE_NODE);
	}
	
	/**
	 * Get the Value of a node that has an embedded text node as a child
	 * @param doc the Document to search for the node name
	 * @param nodeName the name of the node to get the value
	 * @return the value of the text node child
	 */
	public static String getNodeTextValue(Document doc, String nodeName){
		String value = null;
		NodeList nodes = doc.getElementsByTagName(nodeName);
		if (nodes.getLength()>0){
			Node node = nodes.item(0);
			if (node.hasChildNodes()){
				Node text = node.getFirstChild();
				value = text.getNodeValue();
			}
		}
		return replaceSpecialCharacters(value);
	}
	
	/**
	 * Get the value of a node assuming there is one child node with a text value
	 * 
	 * @param node
	 * @return the value
	 */
	public static String getNodeTextValue(Node node){
		String value = null;
		if (node.hasChildNodes()){
			Node text = node.getFirstChild();
			value = text.getNodeValue();
		}
		return replaceSpecialCharacters(value);
	}
	
	/**
	 * Check to see if a node exists in the document with the node name passed in.
	 * 
	 * @param doc
	 * @param nodeName
	 * @return true if it exists
	 */
	public static boolean getNodeExists(Document doc, String nodeName){
		if (doc==null||nodeName==null) return false;
		NodeList nodes = doc.getElementsByTagName(nodeName);
		if (nodes==null) return false;
		return nodes.getLength()>0;
	}
	
	public static String replaceSpecialCharacters(String value){
		if (value==null) return value;
		//only replace & if it not already an escaped sequence
		char[] val = value.toCharArray();
		int pos = 0;
		
		while (pos < val.length){
			if (val[pos]=='&'){
				int ampPos = pos;
				//move over until last non-whitespace character to see if ;
				while (pos < val.length && (val[pos]+"").matches("[^\\s]") && pos < ampPos+5){
					pos++;
				}

				if (val[pos]!=';'){
					pos = pos+3;
					String newValue = value.substring(0, ampPos)+"&amp;";
					if (val.length>pos+1){
						newValue += value.substring(ampPos+1);
					}
					value = newValue;
					val = value.toCharArray();
				}
			}
			pos++;
		}
		value = value.replace("<", "&lt;");
		value = value.replace(">", "&gt;");
		value = value.replace("\"", "&quot;");
		value = value.replace("'", "&apos;");
		return value;
	}
	
	/**
	 * Makes multiple calls to trimWhitespaceAndTrailingSpecialCharacter
	 * to combine trimming off more than one character in one method call
	 * 
	 * @param value
	 * @return Final string
	 */
	public static String trimWhitespaceAndTrailingSpecialCharacters(String value){
		value = trimWhitespaceAndTrailingSpecialCharacter(value,'/');
		value = trimWhitespaceAndTrailingSpecialCharacter(value,',');
		value = trimWhitespaceAndTrailingSpecialCharacter(value,':');
		return value;
	}
	
	/**
	 * Trim off whitespace and trailing special characters
	 * @param value
	 * @param specialCharacter
	 * @return
	 */
	public static String trimWhitespaceAndTrailingSpecialCharacter(String value, char specialCharacter){
		//trim off slash and whitespace
		if (value!=null){
			value = value.trim();
			if (value.length()==1){
				if (value.equals(specialCharacter)) value = "";
			} else if (value.charAt(value.length()-1)==specialCharacter){
				value = value.substring(0, value.length()-1);
			}
			value = value.trim();
		}
		return value;
	}
	
	/**
	 * Trim all non-numeric characters off of the string
	 * 
	 * @param value
	 * @return
	 */
	public static String trimNonNumericCharacters(String value){
		//keep only numeric characters
		if (value!=null) value = value.replaceAll("[^0-9]", "");
		return value;
	}
	
	/**
	 * Output a DOM node including children using a log4j logger.
	 * If logger is null it will just output to system out
	 * 
	 * @param logger
	 * @param n
	 */
	public static void outputNode(Logger logger, Node n){
		outputNode(logger,n,0);
	}
	
	/**
	 * Output a DOM node including children to system out.
	 * 
	 * @param n
	 */
	public static void outputNode(Node n){
		outputNode(n,0);
	}
	
	/**
	 * Output a DOM node including children to system out 
	 * indented by the number of tabs passed in.
	 * 
	 * @param n
	 * @param tabs
	 */
	public static void outputNode(Node n, int tabs){
		outputNode(null,n,tabs);
	}
	
	/**
	 * Output a DOM node including children using a log4j logger.
	 * If logger is null it will just output to system out.
	 * It will indent by the number of tabs passed in.
	 * This method recursively calls itself to output
	 * children nodes.
	 * 
	 * @param logger
	 * @param n
	 * @param tabs
	 */
	public static void outputNode(Logger logger, Node n, int tabs){
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<tabs; i++){
			sb.append("\t");
		}
		sb.append("<"+n.getNodeName());
		
		if (n.hasAttributes()){
			NamedNodeMap nnMap = n.getAttributes();
			for (int i=0; i < nnMap.getLength(); i++){
				Node att = nnMap.item(i);
				sb.append(" "+att.getNodeName()+"=\""+att.getNodeValue()+"\"");
			}
		}
		sb.append(">");
		sb = printBuffer(logger,sb,true);
		
		for (int i=0; i<tabs+1; i++){
			sb.append("\t");
		}
		sb.append(n.getNodeValue());
		sb = printBuffer(logger,sb,true);
		
		if (n.hasChildNodes()){
			NodeList nodes = n.getChildNodes();
			for (int i=0; i<nodes.getLength(); i++){
				outputNode(nodes.item(i),tabs+1);
			}
		}
		for (int i=0; i<tabs; i++){
			sb.append("\t");
		}
		sb.append("</"+n.getNodeName()+">");
		sb = printBuffer(logger,sb,true);
	}
	
	private static StringBuffer printBuffer(Logger logger, StringBuffer sb, boolean clearBuffer){
		if (logger!=null){
			logger.debug(sb.toString());
		} else {
			System.out.println(sb.toString());
		}
		if (clearBuffer) sb = new StringBuffer();
		return sb;
	}
}
