package org.extensiblecatalog.ncip.v2.binding.wclv1_0.jaxb;

import org.extensiblecatalog.ncip.v2.binding.jaxb.BaseTestJAXBTranslator;
import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.junit.Test;

import java.io.FileNotFoundException;

// TODO: See if there's a way to get maven (surefire?) to run test classes from another package
// so we don't have to extend the BaseTestJAXBTranslator in every sub-module of the binding module.
public class TestWCLv1_0JAXBTranslator extends BaseTestJAXBTranslator {

    @Test
    public void testSampleFiles() throws FileNotFoundException, ToolkitException {

        doTest();

    }

}
