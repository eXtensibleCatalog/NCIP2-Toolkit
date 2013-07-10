/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

/**
 * The context information for a given invocation of a service. Use the {@link ServiceValidatorFactory} to create
 * a ServiceContext. Always pass the same instance of ServiceContext to the {@link Translator} for a given
 * invocation of a service.
 */
public interface ServiceContext {

    /**
     * Determines whether the {@link NCIPMessage} object represents a valid message for this service.
     * It may check version, app profile, etc. and possibly move data around and/or transform its representation to
     * "normalize" the message for this service. If the data is not valid and can't be made valid,
     * it throws a ValidationException which contains the Problem element to return.
     * This method will be called by all implementations of {@link Translator#createInitiationMessageStream} and
     * {@link Translator#createResponseMessageStream} methods.
     */
    void validateBeforeMarshalling(NCIPMessage ncipMessage) throws ValidationException;

    /**
     * Determines whether the {@link NCIPMessage} object represents a valid message for this service.
     * It may check version, app profile, etc. and possibly move data around and/or transform its representation to
     * "normalize" the message for this service. If the data is not valid and can't be made valid,
     * it throws a ValidationException which contains the Problem element to return.
     * This method will be called by all implementations of {@link Translator#createInitiationData} and
     * {@link Translator#createResponseData} methods.
     */
    void validateAfterUnmarshalling(NCIPMessage ncipMessage) throws ValidationException;


}
