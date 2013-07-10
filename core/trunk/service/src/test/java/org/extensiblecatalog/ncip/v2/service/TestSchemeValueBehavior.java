/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class TestSchemeValueBehavior {

    private static final Logger LOG = Logger.getLogger(TestSchemeValuePair.class);

    @Test
    public void testUnset() {

        SchemeValuePair.mapBehavior(Language.class.getName(), SchemeValueBehavior.UNSET);
        String BAD_SCHEME = "Fake scheme";
        String BAD_VALUE = "Fake value";
        try {
            Language lang = Language.find(BAD_SCHEME, BAD_VALUE);
            Assert.fail("Language.find(\"" + BAD_SCHEME + ", \"" + BAD_VALUE
                + "\") succeeded when it should fail as there's no such language Scheme URI.");
        } catch (ServiceException e) {
            LOG.debug("Exception thrown:", e);
            Assert.assertTrue(e.getLocalizedMessage().matches("(?i).*no match found for scheme .*UNSET.*"));
        }

    }

    @Test
    public void testDefinedOnly() {

        SchemeValuePair.mapBehavior(CurrencyCode.class.getName(), SchemeValueBehavior.DEFINED_ONLY);
        String BAD_SCHEME = "Fake scheme";
        String BAD_VALUE = "Fake value";
        try {
            CurrencyCode currencyCode = CurrencyCode.find(BAD_SCHEME, BAD_VALUE);
            Assert.fail("CurrencyCode.find(\"" + BAD_SCHEME + ", \"" + BAD_VALUE
                + "\") succeeded when it should fail as there's no such currency code Scheme URI.");
        } catch (ServiceException e) {
            LOG.debug("Exception thrown:", e);
            Assert.assertTrue(e.getLocalizedMessage().matches("(?i).*no match found for scheme .*DEFINED_ONLY.*"));
        }

    }

    @Test
    public void testAllowAny() {

        SchemeValuePair.mapBehavior(BibliographicItemIdentifierCode.class.getName(), SchemeValueBehavior.ALLOW_ANY);
        String BAD_SCHEME = "Fake scheme";
        String BAD_VALUE = "Fake value";
        try {
            BibliographicItemIdentifierCode bibIdCode = BibliographicItemIdentifierCode.find(BAD_SCHEME, BAD_VALUE);
            Assert.assertNotNull(bibIdCode);
            Assert.assertEquals(bibIdCode.getScheme(), BAD_SCHEME);
            Assert.assertEquals(bibIdCode.getValue(), BAD_VALUE);
        } catch (ServiceException e) {
            LOG.debug("Exception thrown:", e);
            Assert.fail("BibliographicItemIdentifierCode.find(\"" + BAD_SCHEME + ", \"" + BAD_VALUE
                + "\") failed when it should succeed as the behavior is set to 'allow any'.");
        }

    }

}
