/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.List;

public class SummaryHoldingsInformation {

    protected List<StructuredHoldingsData> structuredHoldingsData;

    protected String unstructuredHoldingsData;

    public List<StructuredHoldingsData> getStructuredHoldingsData() {
        return structuredHoldingsData;
    }

    public StructuredHoldingsData getStructuredHoldingsData(int index) {
        return structuredHoldingsData.get(index);
    }

    public void setStructuredHoldingsData(List<StructuredHoldingsData> structuredHoldingsData) {
        this.structuredHoldingsData = structuredHoldingsData;
    }

    public String getUnstructuredHoldingsData() {
        return unstructuredHoldingsData;
    }

    public void setUnstructuredHoldingsData(String unstructuredHoldingsData) {
        this.unstructuredHoldingsData = unstructuredHoldingsData;
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
