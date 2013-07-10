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

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.CoreMatchers.*;

public class TestSchemeValuePair {

    private static final Logger LOG = Logger.getLogger(TestSchemeValuePair.class);

    @Test
    public void testToString() {

        AgencyId agencyId = new AgencyId("Some Scheme", "Some Value");
        Assert.assertNotNull("AgencyId.toString() should not return null.", agencyId.toString());
    }

    @Test
    public void testFind() {

        String AGENCY_SCHEME = "http://dummy";
        String AGENCY_ONE_VALUE = "One";

        SchemeValuePair.mapBehavior(AgencyId.class.getName(), SchemeValueBehavior.ALLOW_ANY);

        // Test find method of a sub-class that is configured to allow any values
        // Note: The two AgencyId objects created via the find method will be equal *and* the same;
        String AGENCY_TWO_VALUE = "Two";
        AgencyId agencyIdTwoViaFind = null;
        try {
            agencyIdTwoViaFind = AgencyId.find(null, AGENCY_TWO_VALUE);
        } catch (ServiceException e) {
            LOG.debug("Exception thrown:", e);
            Assert.fail("AgencyId.find() method threw exception for scheme 'null' and value '" + AGENCY_TWO_VALUE
                + "' - was AgencyId set to allow any in configuration?");
        }
        AgencyId agencyIdOtherTwoViaFind = null;
        try {
            agencyIdOtherTwoViaFind = AgencyId.find(null, AGENCY_TWO_VALUE);
        } catch (ServiceException e) {
            LOG.debug("Exception thrown:", e);
            Assert.fail("AgencyId.find() method threw exception for scheme 'null' and value '" + AGENCY_TWO_VALUE
                + "' - was AgencyId set to allow any in configuration?");
        }
        Assert.assertEquals(agencyIdTwoViaFind, agencyIdOtherTwoViaFind);
        Assert.assertSame(agencyIdTwoViaFind, agencyIdOtherTwoViaFind);

        AgencyId agencyIdOneWithSchemeViaFind = null;
        try {
            agencyIdOneWithSchemeViaFind = AgencyId.find(AGENCY_SCHEME, AGENCY_ONE_VALUE);
        } catch (ServiceException e) {
            LOG.debug("Exception thrown:", e);
            Assert.fail("AgencyId.find() method threw exception for scheme '" + AGENCY_SCHEME + "' and value '"
                + AGENCY_ONE_VALUE + "' - was AgencyId set to allow any in configuration?");
        }
        AgencyId agencyIdOtherOneWithSchemeViaFind = null;
        try {
            agencyIdOtherOneWithSchemeViaFind = AgencyId.find(AGENCY_SCHEME, AGENCY_ONE_VALUE);
        } catch (ServiceException e) {
            LOG.debug("Exception thrown:", e);
            Assert.fail("AgencyId.find() method threw exception for scheme '" + AGENCY_SCHEME + "' and value '"
                + AGENCY_ONE_VALUE + "' - was AgencyId set to allow any in configuration?");
        }
        Assert.assertEquals(agencyIdOneWithSchemeViaFind, agencyIdOtherOneWithSchemeViaFind);
        Assert.assertSame(agencyIdOneWithSchemeViaFind, agencyIdOtherOneWithSchemeViaFind);

        // Confirm that when you create a SchemeValuePair object via the ctor, it's found via find.
        String AGENCY_THREE_VALUE = "Three";
        AgencyId agencyIdThreeViaCtor = new AgencyId(null, AGENCY_THREE_VALUE);
        AgencyId agencyIdThreeViaFind = null;
        try {
            agencyIdThreeViaFind = AgencyId.find(null, AGENCY_THREE_VALUE);
        } catch (ServiceException e) {
            LOG.debug("Exception thrown:", e);
            Assert.fail("AgencyId.find() method threw exception for scheme 'null' and value '" + AGENCY_THREE_VALUE
                + "' - was AgencyId set to allow any in configuration?");
        }
        Assert.assertEquals(agencyIdThreeViaCtor, agencyIdThreeViaFind);
        Assert.assertSame(agencyIdThreeViaCtor, agencyIdThreeViaFind);

        String GPE_ALIAS_SCHEME = "http://www.niso.org/ncip/v2_0/schemes/processingerrortype/generalprocessingerror.scm";
        ProblemType genProcErrorWithAliasViaFind = null;
        try {
            genProcErrorWithAliasViaFind = Version1GeneralProcessingError.find(
                GPE_ALIAS_SCHEME,
                Version1GeneralProcessingError.AGENCY_AUTHENTICATION_FAILED.getValue());
        } catch (ServiceException e) {
            LOG.debug("Exception thrown:", e);
            Assert.fail("Version1GeneralProcessingError.find() method threw exception for scheme '" + GPE_ALIAS_SCHEME
                + "' and value '" + Version1GeneralProcessingError.AGENCY_AUTHENTICATION_FAILED
                + "' - is " + GPE_ALIAS_SCHEME + " set as an alias for "
                + Version1GeneralProcessingError.VERSION_1_GENERAL_PROCESSING_ERROR + "?");
        }
        Assert.assertEquals(genProcErrorWithAliasViaFind, Version1GeneralProcessingError.AGENCY_AUTHENTICATION_FAILED);
        Assert.assertSame(genProcErrorWithAliasViaFind, Version1GeneralProcessingError.AGENCY_AUTHENTICATION_FAILED);

        Language languageCopy = new Language(Version1Language.ZAP.getScheme(),
            Version1Language.ZAP.getValue());
        Assert.assertEquals(Version1Language.ZAP, languageCopy);
        Language foundLanguage = null;
        try {
            foundLanguage = Language.find(Version1Language.ZAP.getScheme(),
                Version1Language.ZAP.getValue());
            Assert.assertEquals(Version1Language.ZAP, foundLanguage);
        } catch (ServiceException e) {
            LOG.debug("Exception thrown:", e);
            Assert.fail("Version1Language.ZAP not found via call to Language.find() method "
                + " - was Version1Language loaded in configuration?");
        }
        String BAD_LANGUAGE_SCHEME = "Fake scheme";
        String BAD_LANGUAGE_VALUE = "Fake value";
        String GOOD_LANGUAGE_SCHEME = Version1Language.ZAP.getScheme();
        String GOOD_LANGUAGE_VALUE = Version1Language.ZAP.getValue();
        try {
            Language unfoundSchemeLanguage = Language.find(BAD_LANGUAGE_SCHEME, GOOD_LANGUAGE_VALUE);
            Assert.fail("Language.find(\"" + BAD_LANGUAGE_SCHEME + "\"), \"" + GOOD_LANGUAGE_VALUE
                + "\") succeeded when it should fail as there's no such language Scheme URI.");
        } catch (ServiceException e) {
            LOG.debug("Exception thrown:", e);
            Assert.assertTrue(e.getLocalizedMessage().matches("(?i).*no match found for scheme .*"));
        }
        try {
            Language unfoundValueLanguage = Language.find(GOOD_LANGUAGE_SCHEME, BAD_LANGUAGE_VALUE);
            Assert.fail("Language.find(\"" + GOOD_LANGUAGE_SCHEME + "\"), \"" + BAD_LANGUAGE_VALUE + "\") "
                + " succeeded when it should fail as there's no such value in the Language Scheme(s).");
        } catch (ServiceException e) {
            LOG.debug("Exception thrown:", e);
            Assert.assertTrue(e.getLocalizedMessage().matches("(?i).*no match found for scheme .*"));
        }

        // Test that find won't allow an undefined Scheme for a SchemeValuePair sub-class that doesn't permit it.
        SchemeValuePair.mapBehavior(ComponentIdentifierType.class.getName(), SchemeValueBehavior.DEFINED_ONLY);
        try {
            ComponentIdentifierType.find("undefined scheme", "eng");
        } catch (ServiceException e) {
            LOG.debug("Exception thrown:", e);
            Assert.assertTrue(e.getLocalizedMessage().matches("(?i).*no match found for scheme .*DEFINED_ONLY.*"));
        }

    }

    @Test
    public void testMatches() {

        // Test matches even if Scheme is null
        SchemeValuePair.allowNullScheme(FiscalActionType.class.getName());
        FiscalActionType fiscalActionTypeWithNullScheme
            = new FiscalActionType(null, Version1FiscalActionType.CANCEL.getValue());
        Assert.assertTrue("Allow null for scheme doesn't work when this.scheme != null.",
            Version1FiscalActionType.CANCEL.matches(fiscalActionTypeWithNullScheme));

        // Test that two SVP objects, one with and the other without a Scheme, of a class that allows Scheme
        // to be null, that both have same value, match.
        Assert.assertTrue("Allow null for scheme doesn't work when this.scheme == null",
            fiscalActionTypeWithNullScheme.matches(Version1FiscalActionType.CANCEL));

        Assert.assertFalse("A Version1CurrencyCode.USD should not match Version1Language.ENG.",
            Version1Language.ENG.matches(Version1CurrencyCode.USD));

        Assert.assertFalse("A Version1Language.ENG should not match Version1CurrencyCode.USD.",
            Version1CurrencyCode.USD.matches(Version1Language.ENG));

    }

    @Test
    public void testEquals() throws InvocationTargetException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException {

        // TODO: Remove all the tests that aren't testing equals

        SchemeValuePair.mapBehavior(AgencyId.class.getName(), SchemeValueBehavior.ALLOW_ANY);

        // TODO: Identify which tests are tests of symmetry, transitivity and consistency (see Bloch pp. 27-32).

        Assert.assertEquals(Version1Language.AAR, Version1Language.AAR);
        Assert.assertNotSame(Version1Language.AAR, Version1Language.ENG);

        // Test constructors of a sub-class that is configured to allow any values
        // Note: The two AgencyId objects created via constructors will be equal but not the same;
        // note how this differs from the result when using the find method instead of the constructor.
        String AGENCY_SCHEME = "http://dummy";
        String AGENCY_ONE_VALUE = "One";
        AgencyId agencyIdOneViaCtor = new AgencyId(null, AGENCY_ONE_VALUE);
        AgencyId agencyIdOtherOneViaCtor = new AgencyId(null, AGENCY_ONE_VALUE);
        Assert.assertEquals(agencyIdOneViaCtor, agencyIdOtherOneViaCtor);
        Assert.assertNotSame(agencyIdOneViaCtor, agencyIdOtherOneViaCtor);

        AgencyId agencyIdOneWithSchemeViaCtor = new AgencyId(AGENCY_SCHEME, AGENCY_ONE_VALUE);
        AgencyId agencyIdOtherOneWithSchemeViaCtor = new AgencyId(AGENCY_SCHEME, AGENCY_ONE_VALUE);
        Assert.assertEquals(agencyIdOneWithSchemeViaCtor, agencyIdOtherOneWithSchemeViaCtor);
        Assert.assertNotSame(agencyIdOneWithSchemeViaCtor, agencyIdOtherOneWithSchemeViaCtor);

        // Test that two AgencyIds don't match if the second one's value is null
        AgencyId agencyIdWithSchemeAndValue = new AgencyId(AGENCY_SCHEME, "Test value");
        AgencyId agencyIdWithSchemeButNoValue = new AgencyId(AGENCY_SCHEME, null);
        Assert.assertFalse(
            "AgencyIds shouldn't match if the first one has a value but the second one's is null,"
            + " even if schemes are the same.",
            agencyIdWithSchemeAndValue.equals(agencyIdWithSchemeButNoValue));

        // Test that two AgencyIds don't match if the first one's value is null
        Assert.assertFalse(
            "AgencyIds shouldn't match if the second one has a value but the first one's is null,"
            + " even if schemes are the same.",
            agencyIdWithSchemeButNoValue.equals(agencyIdWithSchemeAndValue));

        // Test that two AgencyIds  match if both have the same scheme even if both have null values
        AgencyId agencyIdOtherOneWithSchemeButNoValue = new AgencyId(AGENCY_SCHEME, null);
        Assert.assertTrue(
            "AgencyIds should match if both have the same scheme and both have null values.",
            agencyIdWithSchemeButNoValue.equals(agencyIdOtherOneWithSchemeButNoValue));

        // Test that an object that isn't an instance of SchemeValuePair is not equal to a SchemeValuePair
        Assert.assertFalse("Version1Language.ENG should not be equal to a String.",
            Version1Language.ENG.equals("asdf"));
        Assert.assertFalse("A String should not be equal to Version1Language.ENG.",
            "asdf".equals(Version1Language.ENG));

        // Test that we can construct a SchemeValuePair with only a value
        Assert.assertNotNull("It should be possible to construct an AgencyId with only a value parameter.",
            new AgencyId("value only"));

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
