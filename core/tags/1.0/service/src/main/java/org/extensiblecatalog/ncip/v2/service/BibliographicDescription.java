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

/**
 * BibliographicDescription describes a bibliographic entity.
 */
public class BibliographicDescription {
    protected String author;

    protected String authorOfComponent;

    protected List<BibliographicItemId> bibliographicItemIds;

    protected List<BibliographicRecordId> bibliographicRecordIds;

    protected ComponentId componentId;

    protected String edition;

    protected String pagination;

    protected String placeOfPublication;

    protected String publicationDate;

    protected String publicationDateOfComponent;

    protected String publisher;

    protected String seriesTitleNumber;

    protected String title;

    protected String titleOfComponent;

    protected BibliographicLevel bibliographicLevel;

    protected String sponsoringBody;

    protected ElectronicDataFormatType electronicDataFormatType;

    protected Language language;

    protected MediumType mediumType;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorOfComponent() {
        return authorOfComponent;
    }

    public void setAuthorOfComponent(String authorOfComponent) {
        this.authorOfComponent = authorOfComponent;
    }

    public List<BibliographicItemId> getBibliographicItemIds() {
        return bibliographicItemIds;
    }

    public void setBibliographicItemIds(List<BibliographicItemId> bibliographicItemIds) {
        this.bibliographicItemIds = bibliographicItemIds;
    }

    public List<BibliographicRecordId> getBibliographicRecordIds() {
        return bibliographicRecordIds;
    }

    public void setBibliographicRecordIds(List<BibliographicRecordId> bibliographicRecordIds) {
        this.bibliographicRecordIds = bibliographicRecordIds;
    }

    public ComponentId getComponentId() {
        return componentId;
    }

    public void setComponentId(ComponentId componentId) {
        this.componentId = componentId;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getPagination() {
        return pagination;
    }

    public void setPagination(String pagination) {
        this.pagination = pagination;
    }

    public String getPlaceOfPublication() {
        return placeOfPublication;
    }

    public void setPlaceOfPublication(String placeOfPublication) {
        this.placeOfPublication = placeOfPublication;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublicationDateOfComponent() {
        return publicationDateOfComponent;
    }

    public void setPublicationDateOfComponent(String publicationDateOfComponent) {
        this.publicationDateOfComponent = publicationDateOfComponent;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSeriesTitleNumber() {
        return seriesTitleNumber;
    }

    public void setSeriesTitleNumber(String seriesTitleNumber) {
        this.seriesTitleNumber = seriesTitleNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleOfComponent() {
        return titleOfComponent;
    }

    public void setTitleOfComponent(String titleOfComponent) {
        this.titleOfComponent = titleOfComponent;
    }

    public BibliographicLevel getBibliographicLevel() {
        return bibliographicLevel;
    }

    public void setBibliographicLevel(BibliographicLevel bibliographicLevel) {
        this.bibliographicLevel = bibliographicLevel;
    }

    public String getSponsoringBody() {
        return sponsoringBody;
    }

    public void setSponsoringBody(String sponsoringBody) {
        this.sponsoringBody = sponsoringBody;
    }

    public ElectronicDataFormatType getElectronicDataFormatType() {
        return electronicDataFormatType;
    }

    public void setElectronicDataFormatType(ElectronicDataFormatType electronicDataFormatType) {
        this.electronicDataFormatType = electronicDataFormatType;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public MediumType getMediumType() {
        return mediumType;
    }

    public void setMediumType(MediumType mediumType) {
        this.mediumType = mediumType;
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
