package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Used for unit testing {@link TranslatorFactory}.
 */
public class FakeTranslator implements Translator {

    public FakeTranslator(TranslatorConfiguration configuration) {
        // Do nothing - this class only for unit testing.
    }

    @Override
    public NCIPInitiationData createInitiationData(ServiceContext serviceContext, InputStream inputStream) throws ServiceException, ValidationException {
        // Do nothing - this class only for unit testing.
        return null;
    }

    @Override
    public NCIPResponseData createResponseData(ServiceContext serviceContext, InputStream inputStream) throws ServiceException, ValidationException {
        // Do nothing - this class only for unit testing.
        return null;
    }

    @Override
    public ByteArrayInputStream createInitiationMessageStream(ServiceContext serviceContext, NCIPInitiationData initiationData) throws ServiceException, ValidationException {
        // Do nothing - this class only for unit testing.
        return null;
    }

    @Override
    public ByteArrayInputStream createResponseMessageStream(ServiceContext serviceContext, NCIPResponseData responseData) throws ServiceException, ValidationException {
        // Do nothing - this class only for unit testing.
        return null;
    }
}
