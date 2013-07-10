/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Identifies component part of another bibliographic item.
 */
public class ComponentId {
    protected ComponentIdentifierType componentIdentifierType;
    protected String componentIdentifier;

    public ComponentIdentifierType getComponentIdentifierType() {
        return componentIdentifierType;
    }

    public void setComponentIdentifierType(ComponentIdentifierType componentIdentifierType) {
        this.componentIdentifierType = componentIdentifierType;
    }

    public String getComponentIdentifier() {
        return componentIdentifier;
    }

    public void setComponentIdentifier(String componentIdentifier) {
        this.componentIdentifier = componentIdentifier;
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
