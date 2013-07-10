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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.extensiblecatalog.ncip.v2.symphony.HttpException;

/**
 * Class responsible for converting JSON responses to DTOs.
 * 
 */
public class JsonMapper {

	
	private final static Logger logger = Logger.getLogger(JsonMapper.class);
	
    private MappingJsonFactory factory;

    /**
     * Creates a new default mapper that does not fail if it encounters unknown properties and
     * converts SirsiDynix-formatted dates.
     */
    public JsonMapper() {
        ObjectMapper mapper = new ObjectMapper();
    	factory = new MappingJsonFactory(mapper);
    	mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	SimpleModule module = new SimpleModule("SirsiDynix Unicorn JSON", new Version(0,0,1, "beta"));
    	module.addDeserializer(Amount.class, new Amount.Deserializer());
    	DateFormat fmt = new SirsiDateFormat();
    	mapper.setDateFormat(fmt);
    }

    /**
     * Converts data read from the given stream to a DTO of a specified type.  Note that this method does
     * not take responsibility for closing the input stream.
     * @param input the stream from which to read JSON data.
     * @param responseClass the type of object to create.
     * @return a deserialization of the input data to an instance of <code>responseClass</code>.
     * @throws HttpException if there is an error reading from <code>input</code> or the data cannot
     * be deserialized correctly.
     */
    public <E extends SymphonyResponse<?>> E readStream(InputStream input, Class<E> responseClass) {
        try {
            JsonParser p = factory.createJsonParser(input);
            return p.readValueAs(responseClass);
        } catch( JsonProcessingException jpx ) {
        	logger.error("Unable to deserialize server response to " + responseClass.getName(), jpx);
            throw new HttpException("Response from server was not in expected form.");
        } catch (IOException e) {
        	throw new HttpException("Unable to read response from servier");
        } 
    }

    /**
     * Used for simple spot check tests..
     * @param args a set of filenames to attempt to convert into DisplayUserResponses.
     */
    public static void main(String [] args) {
        JsonMapper mapper = new JsonMapper();
        NCIPSerializer serializer = new NCIPSerializer();
        for( String arg: args) {
            try {
            	DisplayUserResponse response = mapper.readStream( new FileInputStream(arg), DisplayUserResponse.class );
                String serialized = serializer.serializeResponseData(response.getPatronInfo().getResponseData());
                System.out.println( serialized );
            } catch( Exception e ) {
                e.printStackTrace(System.err);
            }
        }

    }
}
