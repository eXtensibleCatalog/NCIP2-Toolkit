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

public class HoldingsSet {

    /**
     * Holdings Set Id
     */
    protected String holdingsSetId;
    /**
     * Bibliographic Description
     */
    protected BibliographicDescription bibliographicDescription;
    /**
     * Location
     */
    protected Location location;
    /**
     * Call Number
     */
    protected String callNumber;
    /**
     * Summary Holdings Information
     */
    protected SummaryHoldingsInformation summaryHoldingsInformation;
    /**
     * Electronic Resource
     */
    protected ElectronicResource electronicResource;
    /**
     * Item Information
     */
    protected List<ItemInformation> itemInformations;
    /**
     * Problems
     */
    protected List<Problem> problems;

    public String getHoldingsSetId() {
        return holdingsSetId;
    }

    public void setHoldingsSetId(String holdingsSetId) {
        this.holdingsSetId = holdingsSetId;
    }

    public BibliographicDescription getBibliographicDescription() {
        return bibliographicDescription;
    }

    public void setBibliographicDescription(BibliographicDescription bibliographicDescription) {
        this.bibliographicDescription = bibliographicDescription;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public SummaryHoldingsInformation getSummaryHoldingsInformation() {
        return summaryHoldingsInformation;
    }

    public void setSummaryHoldingsInformation(SummaryHoldingsInformation summaryHoldingsInformation) {
        this.summaryHoldingsInformation = summaryHoldingsInformation;
    }

    public ElectronicResource getElectronicResource() {
        return electronicResource;
    }

    public void setElectronicResource(ElectronicResource electronicResource) {
        this.electronicResource = electronicResource;
    }

    public List<ItemInformation> getItemInformations() {
        return itemInformations;
    }

    public ItemInformation getItemInformation(int index) {
        return itemInformations.get(index);
    }

    public void setItemInformations(List<ItemInformation> itemInformations) {
        this.itemInformations = itemInformations;
    }

    public List<Problem> getProblems() {
        return problems;
    }

    public Problem getProblem(int index) {
        return problems.get(index);
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
