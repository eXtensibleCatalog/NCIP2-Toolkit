/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;
import java.util.List;

public class BibInformation {

    protected BibliographicId bibliographicId;
    protected BibliographicDescription bibliographicDescription;
    protected BigDecimal titleHoldQueueLength;
    protected List<CurrentRequester> currentRequesters;
    protected List<HoldingsSet> holdingsSets;
    /**
     * Problems
     */
    protected List<Problem> problems;

    public BibliographicId getBibliographicId() {
        return bibliographicId;
    }

    public void setBibliographicId(BibliographicId bibliographicId) {
        this.bibliographicId = bibliographicId;
    }

    public BibliographicDescription getBibliographicDescription() {
        return bibliographicDescription;
    }

    public void setBibliographicDescription(BibliographicDescription bibliographicDescription) {
        this.bibliographicDescription = bibliographicDescription;
    }

    public BigDecimal getTitleHoldQueueLength() {
        return titleHoldQueueLength;
    }

    public void setTitleHoldQueueLength(BigDecimal titleHoldQueueLength) {
        this.titleHoldQueueLength = titleHoldQueueLength;
    }

    public List<CurrentRequester> getCurrentRequesters() {
        return currentRequesters;
    }

    public void setCurrentRequesters(List<CurrentRequester> currentRequesters) {
        this.currentRequesters = currentRequesters;
    }

    public List<HoldingsSet> getHoldingsSets() {
        return holdingsSets;
    }

    public void setHoldingsSets(List<HoldingsSet> holdingsSets) {
        this.holdingsSets = holdingsSets;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public void setProblems(List<Problem> problems) {
        this.problems = problems;
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
