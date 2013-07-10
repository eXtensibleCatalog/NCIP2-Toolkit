/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import java.util.GregorianCalendar;
import java.util.List;
import java.math.BigDecimal;

/**
 * Carries data elements describing the AuthenticationPrompt.
 */
public class AuthenticationPrompt {

    /**
     * PromptOutput
     */
    protected org.extensiblecatalog.ncip.v2.service.PromptOutput promptOutput;

    /**
     * Set PromptOutput.
     */
    public void setPromptOutput(org.extensiblecatalog.ncip.v2.service.PromptOutput promptOutput) {

        this.promptOutput = promptOutput;

    }

    /**
     * Get PromptOutput.
     */
    public org.extensiblecatalog.ncip.v2.service.PromptOutput getPromptOutput() {

        return promptOutput;

    }

    /**
     * PromptInput
     */
    protected org.extensiblecatalog.ncip.v2.service.PromptInput promptInput;

    /**
     * Set PromptInput.
     */
    public void setPromptInput(org.extensiblecatalog.ncip.v2.service.PromptInput promptInput) {

        this.promptInput = promptInput;

    }

    /**
     * Get PromptInput.
     */
    public org.extensiblecatalog.ncip.v2.service.PromptInput getPromptInput() {

        return promptInput;

    }

    /**
     * Generic toString() implementation.
     *
     * @return String
     */
    @Override
    public String toString() {

        return ReflectionToStringBuilder.reflectionToString(this);

    }

}

