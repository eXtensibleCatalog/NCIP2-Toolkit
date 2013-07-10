/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.XMLFilterImpl;

/**
 * Taken from http://stackoverflow.com/questions/277502/jaxb-how-to-ignore-namespace-during-unmarshalling-xml-document/2148541#2148541
 * This filter is designed to both be able to add the namespace if it is not present:
 *    new NamespaceFilter("http://www.example.com/namespaceurl", true);
 * and to remove any present namespace:
 *    new NamespaceFilter(null, false);
 * The filter can be used during parsing as follows:
 * <code>
 //Prepare JAXB objects
 JAXBContext jc = JAXBContext.newInstance("jaxb.package");
 Unmarshaller u = jc.createUnmarshaller();

 //Create an XMLReader to use with our filter
 XMLReader reader = XMLReaderFactory.createXMLReader();

 //Create the filter (to add namespace) and set the xmlReader as its parent.
 NamespaceFilter inFilter = new NamespaceFilter("http://www.example.com/namespaceurl", true);
 inFilter.setParent(reader);

 //Prepare the input, in this case a java.io.File (output)
 InputSource is = new InputSource(new FileInputStream(output));

 //Create a SAXSource specifying the filter
 SAXSource source = new SAXSource(inFilter, is);

 //Do unmarshalling
 Object myJaxbObject = u.unmarshal(source);
 * </code>
 *
 * Use this filter to output XML from a JAXB object as follows:
 * <code>
 //Prepare JAXB objects
 JAXBContext jc = JAXBContext.newInstance("jaxb.package");
 Marshaller m = jc.createMarshaller();

 //Define an output file
 File output = new File("test.xml");

 //Create a filter that will remove the xmlns attribute
 NamespaceFilter outFilter = new NamespaceFilter(null, false);

 //Do some formatting, this is obviously optional and may effect performance
 OutputFormat format = new OutputFormat();
 format.setIndent(true);
 format.setNewlines(true);

 //Create a new org.dom4j.io.XMLWriter that will serve as the
 //ContentHandler for our filter.
 XMLWriter writer = new XMLWriter(new FileOutputStream(output), format);

 //Attach the writer to the filter
 outFilter.setContentHandler(writer);

 //Tell JAXB to marshall to the filter which in turn will call the writer
 m.marshal(myJaxbObject, outFilter);

 * </code>
 */
public class NamespaceFilter extends XMLFilterImpl {

    private String usedNamespaceUri;
    private boolean addNamespace;

    //State variable
    private boolean addedNamespace = false;

    public NamespaceFilter(String namespaceUri,
            boolean addNamespace) {
        super();

        if (addNamespace) {

            this.usedNamespaceUri = namespaceUri.intern();

        } else {

            this.usedNamespaceUri = "";

        }
        this.addNamespace = addNamespace;

    }



    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        if (addNamespace) {
            startControlledPrefixMapping();
        }
    }



    @Override
    public void startElement(String arg0, String arg1, String arg2,
            Attributes arg3) throws SAXException {

        super.startElement(this.usedNamespaceUri, arg1, arg2, arg3);
    }

    @Override
    public void endElement(String arg0, String arg1, String arg2)
            throws SAXException {

        super.endElement(this.usedNamespaceUri, arg1, arg2);
    }

    @Override
    public void startPrefixMapping(String prefix, String url)
            throws SAXException {


        if (addNamespace) {
            this.startControlledPrefixMapping();
        } else {
            //Remove the namespace, i.e. don't call startPrefixMapping for parent!
        }

    }

    private void startControlledPrefixMapping() throws SAXException {

        if (this.addNamespace && !this.addedNamespace) {
            //We should add namespace since it is set and has not yet been done.
            super.startPrefixMapping("", this.usedNamespaceUri);

            //Make sure we dont do it twice
            this.addedNamespace = true;
        }
    }
}