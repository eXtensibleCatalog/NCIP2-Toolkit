package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.ToolkitException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.matchers.StringContains.*;


import java.util.Properties;

public class TestTranslatorFactory {

    // TODO: Figure out why this doesn't fail; I suspect it has to do w/ initialization of a static member
    //@Test This is not failing as expected, not sure why
    public void testGenericNoProperties() throws ToolkitException {
        try {
            Translator translator = TranslatorFactory.buildTranslator();
            Assert.fail("TranslatorFactory.buildTranslator() call should fail with ClassNotFound exception.");
        } catch (ToolkitException e) {
            Assert.assertSame(e.getCause().getClass(), ClassNotFoundException.class);
            Assert.assertThat(e.getMessage(), containsString("org.extensiblecatalog.ncip.v2.binding.ncipv2_02.jaxb.dozer.NCIPv2_02JAXBDozerTranslator"));
        }
    }

    @Test
    public void testGenericWithProperties() throws ToolkitException {
        Properties properties = new Properties();
        properties.setProperty(TranslatorConfiguration.TRANSLATOR_CLASS_NAME_KEY, FakeTranslator.class.getName());
        Translator translator = TranslatorFactory.buildTranslator(null, properties);
        assertNotNull("Translator was not built.", translator);
    }

    // TODO: Figure out why this doesn't fail; I suspect it has to do w/ initialization of a static member
    //@Test This is not failing as expected, not sure why
    public void testAppNameNoProperties() throws ToolkitException {
        try {
            Translator translator = TranslatorFactory.buildTranslator("FakeApp");
            Assert.fail("TranslatorFactory.buildTranslator() call should fail with ClassNotFound exception.");
        } catch (ToolkitException e) {
            Assert.assertSame(e.getCause().getClass(), ClassNotFoundException.class);
            Assert.assertThat(e.getMessage(), containsString("org.extensiblecatalog.ncip.v2.binding.ncipv2_02.jaxb.dozer.NCIPv2_02JAXBDozerTranslator"));
        }
    }

    @Test
    public void testAppNameWithProperties() throws ToolkitException {
        Properties properties = new Properties();
        properties.setProperty(TranslatorConfiguration.TRANSLATOR_CLASS_NAME_KEY, FakeTranslator.class.getName());
        Translator translator = TranslatorFactory.buildTranslator("FakeApp", properties);
        assertNotNull("Translator was not built.", translator);
    }
}
