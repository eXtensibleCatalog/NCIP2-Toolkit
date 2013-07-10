/**
 * Copyright (c) 2012 North Carolina State University
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.extensiblecatalog.ncip.v2.symphony.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.extensiblecatalog.ncip.v2.binding.jaxb.JAXBTranslator;
import org.extensiblecatalog.ncip.v2.common.NCIPConfiguration;
import org.extensiblecatalog.ncip.v2.service.NCIPResponseData;
import org.extensiblecatalog.ncip.v2.service.ServiceException;

/**
 * Class that allows reasonably simple serialization of NCIPResponseData objects to NCIP streams.
 * Not designed for production, primarily of use in testing to make sure properly structured messages
 * are created by the JSON->NCIPResponseData routines.
 * @author adam_constabaris@ncsu.edu
 **/
public class NCIPSerializer {

    private JAXBTranslator translator;


    public NCIPSerializer() {
        NCIPConfiguration config = new NCIPConfiguration();
        config.setSchemaURLs(Arrays.asList("ncip_v2_0_extensions.xsd","ncip_v2_0.xsd"));
        translator = new JAXBTranslator();

    }

    public String serializeResponseData(NCIPResponseData data) throws ServiceException, IOException {
        InputStream is = translator.createResponseMessageStream(data);
        byte [] buf = new byte[4096];
        int len = 0;
        StringBuilder sb = new StringBuilder();
        while( ( len = is.read(buf) ) > 0 ) {
            sb.append( new String(buf, 0, len, "utf-8") );
        }
        is.close();
        return sb.toString();

    }
}
