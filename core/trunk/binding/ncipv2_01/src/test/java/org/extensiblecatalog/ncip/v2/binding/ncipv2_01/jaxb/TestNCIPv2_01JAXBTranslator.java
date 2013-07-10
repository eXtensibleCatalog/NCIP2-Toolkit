package org.extensiblecatalog.ncip.v2.binding.ncipv2_01.jaxb;

import org.extensiblecatalog.ncip.v2.binding.jaxb.BaseTestJAXBTranslator;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.junit.Test;

import java.io.FileNotFoundException;

public class TestNCIPv2_01JAXBTranslator extends BaseTestJAXBTranslator {

    @Test
    public void testSampleFiles() throws FileNotFoundException, ToolkitException {

        doTest();

    }

}
