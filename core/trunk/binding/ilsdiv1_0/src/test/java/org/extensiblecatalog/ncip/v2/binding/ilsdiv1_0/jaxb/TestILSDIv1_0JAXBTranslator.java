package org.extensiblecatalog.ncip.v2.binding.ilsdiv1_0.jaxb;

import org.extensiblecatalog.ncip.v2.binding.jaxb.BaseTestJAXBTranslator;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.junit.Test;

import java.io.FileNotFoundException;

public class TestILSDIv1_0JAXBTranslator extends BaseTestJAXBTranslator {

    @Test
    public void testSampleFiles() throws FileNotFoundException, ToolkitException {

        doTest();

    }

}
