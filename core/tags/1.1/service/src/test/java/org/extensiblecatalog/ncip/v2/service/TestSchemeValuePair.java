/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.matchers.JUnitMatchers;
import static org.hamcrest.CoreMatchers.*;

public class TestSchemeValuePair {
    @Test
    public void testEquals()  {

        // TODO: Identify which tests are tests of symmetry, transitivity and consistency (see Bloch pp. 27-32).

        Assert.assertEquals(Version1Language.AAR, Version1Language.AAR);
        Assert.assertNotSame(Version1Language.AAR, Version1Language.ENG);

        // Test constructors of a sub-class that is configured to allow any values
        // Note: The two AgencyId objects created via constructors will be equal but not the same;
        // compare to the results below when using the find method
        String AGENCY_SCHEME = "http://dummy";
        String AGENCY_ONE_VALUE = "One";
        String AGENCY_TWO_VALUE = "Two";
        AgencyId agencyIdOne = new AgencyId(null, AGENCY_ONE_VALUE);
        AgencyId agencyIdOtherOne = new AgencyId(null, AGENCY_ONE_VALUE);
        Assert.assertEquals(agencyIdOne, agencyIdOtherOne);
        Assert.assertNotSame(agencyIdOne, agencyIdOtherOne);

        AgencyId agencyIdOneWithScheme = new AgencyId(AGENCY_SCHEME, AGENCY_ONE_VALUE);
        AgencyId agencyIdOtherOneWithScheme = new AgencyId(AGENCY_SCHEME, AGENCY_ONE_VALUE);
        Assert.assertEquals(agencyIdOneWithScheme, agencyIdOtherOneWithScheme);
        Assert.assertNotSame(agencyIdOneWithScheme, agencyIdOtherOneWithScheme);

        // Test find method of a sub-class that is configured to allow any values
        // Note: The two AgencyId objects created via the find method will be equal *and* the same;
        AgencyId agencyIdOneViaFind = null;
        try {
            agencyIdOneViaFind = AgencyId.find(null, AGENCY_ONE_VALUE);
        } catch (ServiceException e) {
            Assert.fail("AgencyId.find() method threw exception for scheme 'null' and value '" + AGENCY_ONE_VALUE
                + "' - was AgencyId set to allow any in configuration?");
        }
        AgencyId agencyIdOtherOneViaFind = null;
        try {
            agencyIdOtherOneViaFind = AgencyId.find(null, AGENCY_ONE_VALUE);
        } catch (ServiceException e) {
            Assert.fail("AgencyId.find() method threw exception for scheme 'null' and value '" + AGENCY_ONE_VALUE
                + "' - was AgencyId set to allow any in configuration?");
        }
        Assert.assertEquals(agencyIdOneViaFind, agencyIdOtherOneViaFind);
        Assert.assertSame(agencyIdOneViaFind, agencyIdOtherOneViaFind);

        AgencyId agencyIdOneWithSchemeViaFind = null;
        try {
            agencyIdOneWithSchemeViaFind = AgencyId.find(AGENCY_SCHEME, AGENCY_ONE_VALUE);
        } catch (ServiceException e) {
            Assert.fail("AgencyId.find() method threw exception for scheme '" + AGENCY_SCHEME + "' and value '"
                + AGENCY_ONE_VALUE + "' - was AgencyId set to allow any in configuration?");
        }
        AgencyId agencyIdOtherOneWithSchemeViaFind = null;
        try {
            agencyIdOtherOneWithSchemeViaFind = AgencyId.find(AGENCY_SCHEME, AGENCY_ONE_VALUE);
        } catch (ServiceException e) {
            Assert.fail("AgencyId.find() method threw exception for scheme '" + AGENCY_SCHEME + "' and value '"
                + AGENCY_ONE_VALUE + "' - was AgencyId set to allow any in configuration?");
        }
        Assert.assertEquals(agencyIdOneWithSchemeViaFind, agencyIdOtherOneWithSchemeViaFind);
        Assert.assertSame(agencyIdOneWithSchemeViaFind, agencyIdOtherOneWithSchemeViaFind);

        String GPE_ALIAS_SCHEME = "http://www.niso.org/ncip/v2_0/schemes/processingerrortype/generalprocessingerror.scm";
        ProblemType genProcErrorWithAliasViaFind = null;
        try {
            genProcErrorWithAliasViaFind = Version1GeneralProcessingError.find(
                GPE_ALIAS_SCHEME,
                Version1GeneralProcessingError.AGENCY_AUTHENTICATION_FAILED.getValue());
        } catch (ServiceException e) {
            Assert.fail("Version1GeneralProcessingError.find() method threw exception for scheme '" + GPE_ALIAS_SCHEME
                + "' and value '" + Version1GeneralProcessingError.AGENCY_AUTHENTICATION_FAILED
                + "' - is " + GPE_ALIAS_SCHEME + " set as an alias for "
                + Version1GeneralProcessingError.VERSION_1_GENERAL_PROCESSING_ERROR + "?");
        }
        Assert.assertEquals(genProcErrorWithAliasViaFind, Version1GeneralProcessingError.AGENCY_AUTHENTICATION_FAILED);
        Assert.assertSame(genProcErrorWithAliasViaFind, Version1GeneralProcessingError.AGENCY_AUTHENTICATION_FAILED);

        // Test matches even if Scheme is null
        SchemeValuePair.allowNullScheme(FiscalActionType.class.getName());
        FiscalActionType fiscalActionTypeWithNullScheme
            = new FiscalActionType(null, Version1FiscalActionType.CANCEL.getValue());
        Assert.assertTrue("Allow null for scheme doesn't work.",
            Version1FiscalActionType.CANCEL.matches(fiscalActionTypeWithNullScheme));

        Language languageCopy = new Language(Version1Language.ZAP.getScheme(),
            Version1Language.ZAP.getValue());
        Assert.assertEquals(Version1Language.ZAP, languageCopy);
        Language foundLanguage = null;
        try {
            foundLanguage = Language.find(Version1Language.ZAP.getScheme(),
                Version1Language.ZAP.getValue());
            Assert.assertEquals(Version1Language.ZAP, foundLanguage);
        } catch (ServiceException e) {
            Assert.fail("Version1Language.ZAP not found via call to Language.find() method "
                + " - was Version1Language loaded in configuration?");
        }
        String BAD_LANGUAGE_SCHEME = "junk";
        String BAD_LANGUAGE_VALUE = "zippy";
        String GOOD_LANGUAGE_SCHEME = Version1Language.ZAP.getScheme();
        String GOOD_LANGUAGE_VALUE = Version1Language.ZAP.getValue();
        try {
            Language unfoundSchemeLanguage = Language.find(BAD_LANGUAGE_SCHEME, GOOD_LANGUAGE_VALUE);
            Assert.fail("Language.find(\"" + BAD_LANGUAGE_SCHEME + "\"), \"" + GOOD_LANGUAGE_VALUE
                + "\") succeeded when it should fail as there's no such language Scheme URI.");
        } catch (ServiceException e) {
            Assert.assertTrue(e.getLocalizedMessage().matches("(?i).*no match found for scheme .*"));
        }
        try {
            Language unfoundValueLanguage = Language.find(GOOD_LANGUAGE_SCHEME, BAD_LANGUAGE_VALUE);
            Assert.fail("Language.find(\"" + GOOD_LANGUAGE_SCHEME + "\"), \"" + BAD_LANGUAGE_VALUE + "\") "
                + " succeeded when it should fail as there's no such value in the Language Scheme(s).");
        } catch (ServiceException e) {
            Assert.assertTrue(e.getLocalizedMessage().matches("(?i).*no match found for scheme .*"));
        }

        // Test two objects that are instances of an immediate-subclass of SchemeValuePair and a subclass of that class.
        class TestAgencyIdAlpha extends AgencyId {

            public TestAgencyIdAlpha(String scheme, String value) {
                super(scheme, value);
            }

        }

        TestAgencyIdAlpha testAgencyIdAlpha = new TestAgencyIdAlpha("x", "y");
        AgencyId testAgencyIdNonAlpha = new AgencyId("x", "y");

        Assert.assertThat("Two objects of the same immediate sub-classes of SchemeValuePair should be equal if"
                + " their Scheme and Value fields are identical.", testAgencyIdAlpha, equalTo(testAgencyIdNonAlpha));

        // Test two objects that are not instances of same immediate-subclass of SchemeValuePair.
        // This is meant to catch the admittedly obscure case where the same Scheme and Value are used to have two different meanings,
        // e.g. something like if the same list of codes were used for both UserLanguage and BibliographicDescription/Language.
        // To test this, we need to create a sub-class of SchemeValuePair that isn't a sub-class of Language, with the same
        // Scheme URI and Value.
        class TestLanguage extends SchemeValuePair {

            public TestLanguage(String scheme, String value) {
                super(scheme, value);
            }

        }

        TestLanguage testLanguageUSEnglish = new TestLanguage(Version1Language.ENG.getScheme(),
            Version1Language.ENG.getValue());

        Assert.assertFalse("Two objects of different immediate sub-classes of SchemeValuePair should not be equal even if"
                + " their Scheme and Value fields are identical.", testLanguageUSEnglish.equals(Version1Language.ENG));



    }
}
