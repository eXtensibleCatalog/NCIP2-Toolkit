package org.extensiblecatalog.ncip.v2.aleph.AlephAPI.item;

import org.extensiblecatalog.ncip.v2.aleph.AlephAPI.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.AlephAPI.XMLParserUtil;
import org.extensiblecatalog.ncip.v2.aleph.AlephAPI.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.AlephAPI.agency.AlephAgency;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Create an Aleph Item from XML DOM Document object passed in
 * 
 * @author Rick Johnson (NDU)
 *
 */
public class AlephItemFactory implements Serializable {
	
	private static final long serialVersionUID = 65006L;	
	
	public static AlephItem createAlephItem(AlephAgency agency){
		AlephItem item = new AlephItem();
		item.setAgency(agency);
		return item;
	}
	
	/**
	 * Update the AlephItem with any z13 data present in the document
	 * 
	 * @param doc
	 * @param item
	 * @return
	 * @throws AlephException
	 */
	protected static AlephItem getZ13Data(Node z13Node, AlephItem alephItem) throws AlephException{
		if (alephItem!=null&&z13Node!=null&&z13Node.getNodeName().equalsIgnoreCase(AlephConstants.Z13_NODE)&&z13Node.hasChildNodes()){
			NodeList z13Nodes = z13Node.getChildNodes();
			for (int k=0; k<z13Nodes.getLength(); k++){
				Node z13DataNode= z13Nodes.item(k);
				if (z13DataNode!=null){
					if (AlephConstants.Z13_BIB_ID_NODE.equalsIgnoreCase(z13DataNode.getNodeName())){
						alephItem.setBibId(XMLParserUtil.getNodeTextValue(z13DataNode));
					} else if (AlephConstants.Z13_AUTHOR_NODE.equalsIgnoreCase(z13DataNode.getNodeName())){
						alephItem.setAuthor(XMLParserUtil.getNodeTextValue(z13DataNode));
					} else if (AlephConstants.Z13_TITLE_NODE.equalsIgnoreCase(z13DataNode.getNodeName())){
						alephItem.setTitle(XMLParserUtil.getNodeTextValue(z13DataNode));
					} else if (AlephConstants.Z13_PUBLISHER_NODE.equalsIgnoreCase(z13DataNode.getNodeName())){
						alephItem.setPublisher(XMLParserUtil.getNodeTextValue(z13DataNode));
					} else if (AlephConstants.Z13_ISBN_NODE.equalsIgnoreCase(z13DataNode.getNodeName())){
						alephItem.setIsbn(XMLParserUtil.getNodeTextValue(z13DataNode));
					} 
				}
			}
		}
		return alephItem;
	}
	
	/**
	 * Update the AlephItem with any z31 data present in the document
	 * 
	 * @param doc
	 * @param item
	 * @return
	 * @throws AlephException
	 */
	protected static AlephItem getZ31Data(Node z31Node, AlephItem alephItem) throws AlephException{
		if (alephItem!=null&&z31Node!=null&&z31Node.getNodeName().equalsIgnoreCase(AlephConstants.Z31_NODE)&&z31Node.hasChildNodes()){
			NodeList z31Nodes = z31Node.getChildNodes();
			for (int k=0; k<z31Nodes.getLength(); k++){
				Node z31DataNode = z31Nodes.item(k);
				if (z31DataNode!=null){
					if (AlephConstants.Z31_FINE_DATE_NODE.equalsIgnoreCase(z31DataNode.getNodeName())){
						try {
							alephItem.setFineAccrualDate(XMLParserUtil.getNodeTextValue(z31DataNode));
						} catch (ParseException pe){
							//do nothing
						}
					} else if (AlephConstants.Z31_FINE_STATUS_NODE.equalsIgnoreCase(z31DataNode.getNodeName())){
						String status = XMLParserUtil.getNodeTextValue(z31DataNode);
						if (status!=null){
							if (status.equalsIgnoreCase(AlephConstants.FINE_STATUS_PAID)){
								alephItem.setFineStatus(AlephConstants.FINE_STATUS_PAID);
							} else {
								alephItem.setFineStatus(AlephConstants.FINE_STATUS_UNPAID);
							}
						}
					} else if (AlephConstants.Z31_CREDIT_DEBIT_NODE.equalsIgnoreCase(z31DataNode.getNodeName())){
						String creditDebit = XMLParserUtil.getNodeTextValue(z31DataNode);
						if (creditDebit!=null&&creditDebit.startsWith(AlephConstants.FINE_CREDIT)){
							alephItem.setCreditDebit(AlephConstants.FINE_CREDIT);
						} else {
							alephItem.setCreditDebit(AlephConstants.FINE_DEBIT);
						}
					} else if (AlephConstants.Z31_NET_SUM_NODE.equalsIgnoreCase(z31DataNode.getNodeName())){
						alephItem.setFineAmount(XMLParserUtil.getNodeTextValue(z31DataNode));
					}	
				}
			}
		}
		return alephItem;
	}
	
	/**
	 * Update the AlephItem with any z30 data present in the document
	 * 
	 * @param doc
	 * @param item
	 * @return
	 * @throws AlephException
	 */
	protected static AlephItem getZ30Data(Node z30Node, AlephItem alephItem) throws AlephException{
		if (alephItem!=null&&z30Node!=null&&z30Node.getNodeName().equalsIgnoreCase(AlephConstants.Z30_NODE)&&z30Node.hasChildNodes()){
			NodeList z30Nodes = z30Node.getChildNodes();
			for (int k=0; k<z30Nodes.getLength(); k++){
				Node z30DataNode = z30Nodes.item(k);
				if (z30DataNode!=null){
					if (AlephConstants.Z30_BARCODE.equalsIgnoreCase(z30DataNode.getNodeName())){
						alephItem.setBarcode(XMLParserUtil.getNodeTextValue(z30DataNode));
					} else if (AlephConstants.Z30_DOC_NUMBER_NODE.equalsIgnoreCase(z30DataNode.getNodeName())){
						alephItem.setDocNumber(XMLParserUtil.getNodeTextValue(z30DataNode));
					} else if (AlephConstants.Z30_ITEM_SEQUENCE_NODE.equalsIgnoreCase(z30DataNode.getNodeName())){
						alephItem.setSeqNumber(XMLParserUtil.getNodeTextValue(z30DataNode));
					} else if (AlephConstants.Z30_MATERIAL_NODE.equalsIgnoreCase(z30DataNode.getNodeName())){
						alephItem.setMediumType(XMLParserUtil.getNodeTextValue(z30DataNode));
					} else if (AlephConstants.Z30_CALL_NUMBER_NODE.equalsIgnoreCase(z30DataNode.getNodeName())){
						alephItem.setCallNumber(XMLParserUtil.getNodeTextValue(z30DataNode));
					} else if (AlephConstants.Z30_SUB_LIBRARY_NODE.equalsIgnoreCase(z30DataNode.getNodeName())){
						alephItem.setLocation(XMLParserUtil.getNodeTextValue(z30DataNode));
					} else if (AlephConstants.Z30_HOLD_DOC_NUMBER_NODE.equalsIgnoreCase(z30DataNode.getNodeName())){
						alephItem.setHoldingsId(XMLParserUtil.getNodeTextValue(z30DataNode));
					}
				}
			}
		}
		return alephItem;
	}
	
	/**
	 * Update the AlephItem with any z36 data present in the document
	 * 
	 * @param doc
	 * @param item
	 * @return
	 * @throws AlephException
	 */
	protected static AlephItem getZ36Data(Node z36Node, AlephItem alephItem) throws AlephException{
		if (alephItem!=null&&z36Node!=null&&z36Node.getNodeName().equalsIgnoreCase(AlephConstants.Z36_NODE)&&z36Node.hasChildNodes()){
			NodeList z36Nodes = z36Node.getChildNodes();
			for (int k=0; k<z36Nodes.getLength(); k++){
				Node z36DataNode = z36Nodes.item(k);
				if (z36DataNode!=null){
					if (AlephConstants.Z36_DOC_NUMBER_NODE.equalsIgnoreCase(z36DataNode.getNodeName())){
						alephItem.setDocNumber(XMLParserUtil.getNodeTextValue(z36DataNode));
					} else if (AlephConstants.Z36_ITEM_SEQUENCE_NODE.equalsIgnoreCase(z36DataNode.getNodeName())){
						alephItem.setSeqNumber(XMLParserUtil.getNodeTextValue(z36DataNode));
					} else if (AlephConstants.Z36_MATERIAL_NODE.equalsIgnoreCase(z36DataNode.getNodeName())){
						alephItem.setMediumType(XMLParserUtil.getNodeTextValue(z36DataNode));
					} else if (AlephConstants.Z36_SUB_LIBRARY_NODE.equalsIgnoreCase(z36DataNode.getNodeName())){
						alephItem.setLocation(XMLParserUtil.getNodeTextValue(z36DataNode));
					} else if (AlephConstants.Z36_STATUS_NODE.equalsIgnoreCase(z36DataNode.getNodeName())){
						alephItem.setCirculationStatus(XMLParserUtil.getNodeTextValue(z36DataNode));
					} else if (AlephConstants.Z36_DUE_DATE_NODE.equalsIgnoreCase(z36DataNode.getNodeName())){
						try {
							alephItem.setDueDateLoan(XMLParserUtil.getNodeTextValue(z36DataNode));
						} catch (ParseException pe){
							//do nothing for now
						}
					}
					/*
					protected static final String Z36_RECALL_DATE_NODE = "z36-recall-date";
					protected static final String Z36_RECALL_DUE_DATE_NODE = "z36-recall-due-date";*/
				}
			}
		}
		return alephItem;
	}
	
	/**
	 * Update the AlephItem with any z37 data present in the document
	 * 
	 * @param doc
	 * @param item
	 * @return
	 * @throws AlephException
	 */
	protected static AlephItem getZ37Data(Node z37Node, AlephItem alephItem) throws AlephException{
		if (alephItem!=null&&z37Node!=null&&z37Node.getNodeName().equalsIgnoreCase(AlephConstants.Z37_NODE)&&z37Node.hasChildNodes()){
			NodeList z37Nodes = z37Node.getChildNodes();
			for (int k=0; k<z37Nodes.getLength(); k++){
				Node z37DataNode = z37Nodes.item(k);
				if (z37DataNode!=null){
					if (AlephConstants.Z37_DOC_NUMBER_NODE.equalsIgnoreCase(z37DataNode.getNodeName())){
						alephItem.setDocNumber(XMLParserUtil.getNodeTextValue(z37DataNode));
					} else if (AlephConstants.Z37_ITEM_SEQUENCE_NODE.equalsIgnoreCase(z37DataNode.getNodeName())){
						alephItem.setSeqNumber(XMLParserUtil.getNodeTextValue(z37DataNode));
					} else if (AlephConstants.Z37_REQUEST_NUMBER_NODE.equalsIgnoreCase(z37DataNode.getNodeName())){
						alephItem.setHoldRequestId(XMLParserUtil.getNodeTextValue(z37DataNode));
					} else if (AlephConstants.Z37_REQUEST_DATE_NODE.equalsIgnoreCase(z37DataNode.getNodeName())){
						try {
							alephItem.setDateHoldRequested(XMLParserUtil.getNodeTextValue(z37DataNode));
						} catch (ParseException pe){
							//ignore parse exceptions here
						}
					} else if (AlephConstants.Z37_HOLD_DATE_NODE.equalsIgnoreCase(z37DataNode.getNodeName())){
						try {
							alephItem.setDateAvailablePickup(XMLParserUtil.getNodeTextValue(z37DataNode));
						} catch (ParseException pe){
							//ignore parse exceptions here
						}
					}
				}
			}
		}
		return alephItem;
	}
	
	/**
	 * Update the AlephItem with any item data node present in the document
	 * 
	 * @param doc
	 * @param item
	 * @return
	 * @throws AlephException
	 */
	protected static AlephItem getItemData(Node itemDataNode, AlephItem alephItem) throws AlephException{
		if (alephItem!=null&&itemDataNode!=null&&
				(itemDataNode.getNodeName().equalsIgnoreCase(AlephConstants.ITEM_DATA_NODE)||
						itemDataNode.getNodeName().equalsIgnoreCase(AlephConstants.ITEM_NODE))&&
				itemDataNode.hasChildNodes()){
			NodeList childNodes = itemDataNode.getChildNodes();
			for (int k=0; k<childNodes.getLength(); k++){
				Node childNode = childNodes.item(k);
				if (childNode!=null){
					//z30-description
					if (AlephConstants.Z30_DESCRIPTION_NODE.equalsIgnoreCase(childNode.getNodeName())){
						alephItem.setDescription(XMLParserUtil.getNodeTextValue(childNode));
					} else if (AlephConstants.LOAN_STATUS_NODE.equalsIgnoreCase(childNode.getNodeName())){
						alephItem.setCirculationStatus(XMLParserUtil.getNodeTextValue(childNode));
					} else if (AlephConstants.DUE_DATE_NODE.equalsIgnoreCase(childNode.getNodeName())){
						try {
							alephItem.setDueDate(XMLParserUtil.getNodeTextValue(childNode),AlephConstants.CIRC_STATUS_DUE_DATE_FORMAT);
						} catch (ParseException pe){
							//just ignore, probably set to null
						}
					} else if (AlephConstants.LOCATION_NODE.equalsIgnoreCase(childNode.getNodeName())){
						alephItem.setLocation(XMLParserUtil.getNodeTextValue(childNode));
					} else if (AlephConstants.BARCODE_NODE.equalsIgnoreCase(childNode.getNodeName())){
						alephItem.setBarcode(XMLParserUtil.getNodeTextValue(childNode));
					} else if (AlephConstants.REC_KEY_NODE.equalsIgnoreCase(childNode.getNodeName())){
						String itemId = XMLParserUtil.getNodeTextValue(childNode);
						if (itemId!=null) alephItem.setItemId(itemId);
					}
				}
			}
		}
		return alephItem;
	}
	
	/**
	 * Get all loan items in the xml response doc (item-l)
	 * 
	 * @param doc
	 * @return
	 * @throws AlephException
	 */
	public static List<AlephItem> getLoanAlephItems(AlephAgency agency, Document doc) throws AlephException{
		List<AlephItem> items = new ArrayList<AlephItem>();
		NodeList loanItemNodes = doc.getElementsByTagName(AlephConstants.LOAN_ITEM_NODE);
		for (int i=0; i<loanItemNodes.getLength(); i++){
			AlephItem alephItem = createAlephItem(agency);
			alephItem = updateAlephItemByNode(alephItem,loanItemNodes.item(i));
			if (alephItem.getItemId()!=null){
				items.add(alephItem);
			}
		}
		return items;
	}
	
	/**
	 * Get all fine items in the xml response doc (fine)
	 * 
	 * @param doc
	 * @return
	 * @throws AlephException
	 */
	public static List<AlephItem> getFineAlephItems(AlephAgency agency, Document doc) throws AlephException{
		List<AlephItem> items = new ArrayList<AlephItem>();
		NodeList fineItemNodes = doc.getElementsByTagName(AlephConstants.FINE_ITEM_NODE);
		for (int i=0; i<fineItemNodes.getLength(); i++){
			AlephItem alephItem = createAlephItem(agency);
			alephItem = updateAlephItemByNode(alephItem,fineItemNodes.item(i));
			if (alephItem.getItemId()!=null){
				items.add(alephItem);
			}
		}
		return items;
	}
	
	/**
	 * Get all hold items in the xml response doc (item-h or hold-req)
	 * 
	 * @param doc
	 * @return
	 * @throws AlephException
	 */
	public static List<AlephItem> getHoldAlephItems(AlephAgency agency, Document doc ) throws AlephException{
		List<AlephItem> items = new ArrayList<AlephItem>();
		NodeList holdItemNodes = doc.getElementsByTagName(AlephConstants.HOLD_ITEM_NODE);
		if (holdItemNodes==null||holdItemNodes.getLength()<=0){
			//check for hold-req node
			holdItemNodes = doc.getElementsByTagName(AlephConstants.HOLD_REQ_ITEM_NODE);
		}
		for (int i=0; i<holdItemNodes.getLength(); i++){
			AlephItem alephItem = createAlephItem(agency);
			alephItem = updateAlephItemByNode(alephItem,holdItemNodes.item(i));
			if (alephItem.getItemId()!=null){
				items.add(alephItem);
			}
		}
		return items;
	}
	
	/**
	 * Get all read items in the xml response doc (read-item)
	 * 
	 * @param doc
	 * @return
	 * @throws AlephException
	 */
	public static AlephItem getReadAlephItem(AlephAgency agency, Document doc ) throws AlephException{
		NodeList readItemNodes = doc.getElementsByTagName(AlephConstants.READ_ITEM_NODE);
		AlephItem alephItem = createAlephItem(agency);
		if (readItemNodes.getLength()>0){
			alephItem = updateAlephItemByNode(alephItem,readItemNodes.item(0));
		}
		return alephItem;
	}
	
	/**
	 * Update an aleph item based on node data contained. Will process hold items,
	 * loan items, and fine items.
	 * 
	 * @param item
	 * @param node
	 * @return
	 * @throws AlephException
	 */
	private static AlephItem updateAlephItemByNode(AlephItem item, Node node) throws AlephException {
		if (item!=null&&node!=null){
			if ((AlephConstants.HOLD_ITEM_NODE.equalsIgnoreCase(node.getNodeName())||
					AlephConstants.HOLD_REQ_ITEM_NODE.equalsIgnoreCase(node.getNodeName())||
					AlephConstants.FINE_ITEM_NODE.equalsIgnoreCase(node.getNodeName())||
					AlephConstants.READ_ITEM_NODE.equalsIgnoreCase(node.getNodeName())||
					AlephConstants.LOAN_ITEM_NODE.equalsIgnoreCase(node.getNodeName()))&&
					node.hasChildNodes()){
				//call this method recursively for all children
				NodeList nodeData = node.getChildNodes();
				for (int i=0; i<nodeData.getLength(); i++){
					item = updateAlephItemByNode(item, nodeData.item(i));
				}
			} else if (AlephConstants.Z37_NODE.equalsIgnoreCase(node.getNodeName())){
				//get z37 data
				item = getZ37Data(node, item);
			} else if (AlephConstants.Z30_NODE.equalsIgnoreCase(node.getNodeName())){
				//get z30 data
				item = getZ30Data(node, item);
			} else if (AlephConstants.Z31_NODE.equalsIgnoreCase(node.getNodeName())){
				//get z31 data
				item = getZ31Data(node, item);
			} else if (AlephConstants.Z13_NODE.equalsIgnoreCase(node.getNodeName())){
				item = getZ13Data(node, item);
			} else if (AlephConstants.Z36_NODE.equalsIgnoreCase(node.getNodeName())){
				item = getZ36Data(node, item);
			} else if (AlephConstants.ITEM_DATA_NODE.equalsIgnoreCase(node.getNodeName())||
					AlephConstants.ITEM_NODE.equalsIgnoreCase(node.getNodeName())){
				item = getItemData(node, item);
			}
		}
		return item;
	}
	
	/**
	 * Get alephItem data from a findDocResponse
	 * 
	 * @param item
	 * @param doc
	 * @return
	 * @throws AlephException
	 */
	public static List<AlephItem> updateAlephItemsParseFindDocResponse(List<AlephItem> items, Document doc ) throws AlephException{
		for (AlephItem item : items){
			updateAlephItem(item,doc);
		}
		return items;
	}
	
	/**
	 * Get alephItem data from a findDocResponse
	 * 
	 * @param item
	 * @param doc
	 * @return
	 * @throws AlephException
	 */
	public static AlephItem updateAlephItemParseFindDocResponse(AlephItem item, Document doc ) throws AlephException{
		return updateAlephItem(item,doc);
	}
	
	/**
	 * Get alephItem data from an itemDataResponse
	 * 
	 * @param item
	 * @param doc
	 * @return
	 * @throws AlephException
	 */
	public static AlephItem updateAlephItemParseItemDataResponse(AlephItem item, Document doc ) throws AlephException{
		//change to return a list
		
		return updateAlephItem(item,doc);
	}
	
	/**
	 * Get alephItem data from an itemDataResponse
	 * 
	 * @param item
	 * @param doc
	 * @return
	 * @throws AlephException
	 */
	public static List<AlephItem> parseItemDataResponse(AlephAgency agency, Document doc ) throws AlephException{
		//change to return a list
		List<AlephItem> items = new ArrayList<AlephItem>();
		NodeList itemNodes = doc.getElementsByTagName(AlephConstants.ITEM_NODE);
		if (itemNodes.getLength()>0){
			for (int i=0; i< itemNodes.getLength(); i++){
				Node node = itemNodes.item(i);
				AlephItem item = AlephItemFactory.createAlephItem(agency);
				item = updateAlephItemByNode(item,node);
				items.add(item);
			}
		}
		return items; 
	}
	
    
	/**
	 * Get alephItem data from either a findDocResponse or itemDataResponse
	 * 
	 * @param item
	 * @param doc
	 * @return
	 * @throws AlephException
	 */
	private static AlephItem updateAlephItem(AlephItem item, Document doc ) throws AlephException{
		if (item!=null&&doc!=null){
			if (!doc.hasChildNodes()){
				throw new AlephException(AlephConstants.ERROR_FIND_DOC_MISSING);
			}
			NodeList errorNodes = doc.getElementsByTagName(AlephConstants.ERROR_NODE);
			if (errorNodes.getLength()>0){
				Node error = errorNodes.item(0);
				if (error.hasChildNodes()){
					Node detail = error.getFirstChild();
					throw new AlephException(detail.getNodeValue());
				} else {
					throw new AlephException(AlephConstants.ERROR_FIND_DOC_MISSING);
				}
			}
			
			NodeList sessionIdNodes = doc.getElementsByTagName(AlephConstants.SESSION_ID_NODE);
			if (sessionIdNodes.getLength()<=0){
				throw new AlephException(AlephConstants.ERROR_FIND_DOC_FAILED_SESSION_ID_MISSING);
			}
			Node sessionId = sessionIdNodes.item(0);
			if (sessionId.hasChildNodes()){
				Node value = sessionId.getFirstChild();
				item.setSessionId(value.getNodeValue());
			} else {
				throw new AlephException(AlephConstants.ERROR_FIND_DOC_FAILED_SESSION_ID_MISSING);
			}
			
			NodeList itemDataNodes = doc.getElementsByTagName(AlephConstants.ITEM_DATA_NODE);
			NodeList findDocNodes = doc.getElementsByTagName(AlephConstants.FIND_DOC_NODE);
			if (findDocNodes.getLength()<=0&&itemDataNodes.getLength()<=0){
				throw new AlephException(AlephConstants.ERROR_FIND_DOC_MISSING);
			}
			
			//check for record or item data node
			NodeList recordNodes = doc.getElementsByTagName(AlephConstants.RECORD_NODE);
			if (recordNodes.getLength()<=0&&itemDataNodes.getLength()<=0){
				throw new AlephException(AlephConstants.ERROR_RECORD_MISSING);
			}
			
			//get varfield nodes
			NodeList varfields = doc.getElementsByTagName(AlephConstants.VARFIELD_NODE);
			if (varfields.getLength()>0){
				for (int i=0; i< varfields.getLength(); i++){
					//assume only one item returned so get first item
					Node varfield = varfields.item(i);
					if (varfield!=null&&varfield.hasAttributes()){
						NamedNodeMap varAtt = varfield.getAttributes();
						Node id = varAtt.getNamedItem(AlephConstants.ID_NODE_ATTR);
						if (id!=null){
							if (AlephConstants.AUTHOR_NODE_ID.equalsIgnoreCase(id.getNodeValue())){
								String author = XMLParserUtil.getValueFromMarcField(varfield);
								if (author!=null) item.setAuthor(author);
							} else if (AlephConstants.ISBN_NODE_ID.equalsIgnoreCase(id.getNodeValue())){
								String isbn = XMLParserUtil.getValueFromMarcField(varfield); 
								if (isbn!=null) item.setIsbn(isbn);
							} else if (AlephConstants.TITLE_NODE_ID.equalsIgnoreCase(id.getNodeValue())){
								String title = XMLParserUtil.getValueFromMarcField(varfield);
								if (title!=null) item.setTitle(title);
							} else if (AlephConstants.CALL_NUMBER_NODE_ID.equalsIgnoreCase(id.getNodeValue())){
								String callNumber1 = XMLParserUtil.getValueFromMarcField(varfield,AlephConstants.LABEL_ATTRIBUTE,AlephConstants.CALL_NUMBER_LABEL1_ATTR_VALUE);
								String callNumber2 = XMLParserUtil.getValueFromMarcField(varfield,AlephConstants.LABEL_ATTRIBUTE,AlephConstants.CALL_NUMBER_LABEL2_ATTR_VALUE);
								if (callNumber1!=null&&callNumber2==null){
									item.setCallNumber(callNumber1);
								} else if (callNumber1==null&&callNumber2!=null){
									item.setCallNumber(callNumber2);
								} else if (callNumber1!=null&&callNumber2!=null){
									item.setCallNumber(callNumber1+" "+callNumber2);
								}
							} else if (AlephConstants.PUBLISHER_NODE_ID.equalsIgnoreCase(id.getNodeValue())){
								String publisher = XMLParserUtil.getValueFromMarcField(varfield);
								if (publisher!=null) item.setPublisher(publisher);
							} else if (AlephConstants.DESCRIPTION1_NODE_ID.equalsIgnoreCase(id.getNodeValue())||
									AlephConstants.DESCRIPTION2_NODE_ID.equalsIgnoreCase(id.getNodeValue())){
								String description = XMLParserUtil.getValueFromMarcField(varfield);
								if (description!=null){
									if (item.getDescription()!=null){
										//concat value
										item.setDescription(item.getDescription()+"; "+description);
									} else {
										item.setDescription(description);
									}
								}
							} else if (AlephConstants.Z30_NODE_ID.equalsIgnoreCase(id.getNodeValue())){
								String location = XMLParserUtil.getValueFromMarcField(varfield,AlephConstants.LABEL_ATTRIBUTE,AlephConstants.LOCATION_LABEL_ATTR_VALUE);
								if (location!=null) item.setLocation(location);
								//set medium type
								String medium = XMLParserUtil.getValueFromMarcField(varfield,AlephConstants.LABEL_ATTRIBUTE,AlephConstants.MEDIUM_LABEL_ATTR_VALUE);
								if (medium!=null) item.setMediumType(medium);
							} else if (AlephConstants.SERIES_NODE_ID.equalsIgnoreCase(id.getNodeValue())){
								String series = XMLParserUtil.getValueFromMarcField(varfield);
								if (series!=null) item.setSeries(series);
							} else if (AlephConstants.ELECTRONIC_RESOURCE_NODE_ID.equalsIgnoreCase(id.getNodeValue())){
								String eresource = XMLParserUtil.getValueFromMarcField(varfield, ',');
								if (eresource!=null) item.setElectronicResource(eresource);
							} else if (AlephConstants.BIB_ID_NODE_ID.equalsIgnoreCase(id.getNodeValue())){
								String bib_id = XMLParserUtil.getValueFromMarcField(varfield,AlephConstants.LABEL_ATTRIBUTE,AlephConstants.BIB_ID_NODE_ATTR_VALUE);
								if (bib_id!=null) item.setBibId(bib_id);
							}
						}
					}
				}
			}
			
			//check if this is item data
			String circulationStatus = XMLParserUtil.getNodeTextValue(doc,AlephConstants.LOAN_STATUS_NODE);
			if (circulationStatus!=null) item.setCirculationStatus(circulationStatus);
			
			//try to get adm id
			String itemId = XMLParserUtil.getNodeTextValue(doc, AlephConstants.REC_KEY_NODE);
			if (itemId!=null) item.setItemId(itemId);
			
			String barcode = XMLParserUtil.getNodeTextValue(doc, AlephConstants.BARCODE_NODE);
			if (barcode!=null) item.setBarcode(barcode);
		}
		/*
	    item.setHoldQueueLength(holdQueueLength);*/
		return item;
	}
	
	/**
	 * Return a list of alephitems with circ status set to a value in AlephItem's
	 * Availability enumeration.  If a barcode is not found for a record that is being parsed
	 * it will be omitted in the results.  If availability is not present, it will be set to 
	 * unknown. If there is a value but unrecognized, it will be set to possibly available.
	 * 
	 * @param doc
	 * @return
	 * @throws AlephException
	 */
	public static List<AlephItem> getAlephItemsCircStatus(AlephAgency agency, Document doc ) throws AlephException{
		List<AlephItem> items = new ArrayList<AlephItem>();
		NodeList itemDataNodes = doc.getElementsByTagName(AlephConstants.ITEM_DATA_NODE);
		for (int i=0; i<itemDataNodes.getLength(); i++){
			AlephItem alephItem = createAlephItem(agency);
			alephItem = updateAlephItemByNode(alephItem,itemDataNodes.item(i));
			items.add(alephItem);
		}
		return items;
	}
}