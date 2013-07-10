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
 * Carries data elements describing the UpdateItem.
 */
public class UpdateItemInitiationData implements NCIPInitiationData {

    /**
     * InitiationHeader
     */
    protected org.extensiblecatalog.ncip.v2.service.InitiationHeader initiationHeader;

    /**
     * Set InitiationHeader.
     */
    public void setInitiationHeader(org.extensiblecatalog.ncip.v2.service.InitiationHeader initiationHeader) {

        this.initiationHeader = initiationHeader;

    }

    /**
     * Get InitiationHeader.
     */
    public org.extensiblecatalog.ncip.v2.service.InitiationHeader getInitiationHeader() {

        return initiationHeader;

    }

    /**
     * MandatedAction
     */
    protected org.extensiblecatalog.ncip.v2.service.MandatedAction mandatedAction;

    /**
     * Set MandatedAction.
     */
    public void setMandatedAction(org.extensiblecatalog.ncip.v2.service.MandatedAction mandatedAction) {

        this.mandatedAction = mandatedAction;

    }

    /**
     * Get MandatedAction.
     */
    public org.extensiblecatalog.ncip.v2.service.MandatedAction getMandatedAction() {

        return mandatedAction;

    }

    /**
     * ItemId
     */
    protected org.extensiblecatalog.ncip.v2.service.ItemId itemId;

    /**
     * Set ItemId.
     */
    public void setItemId(org.extensiblecatalog.ncip.v2.service.ItemId itemId) {

        this.itemId = itemId;

    }

    /**
     * Get ItemId.
     */
    public org.extensiblecatalog.ncip.v2.service.ItemId getItemId() {

        return itemId;

    }

    /**
     * DeleteItemFields
     */
    protected org.extensiblecatalog.ncip.v2.service.DeleteItemFields deleteItemFields;

    /**
     * Set DeleteItemFields.
     */
    public void setDeleteItemFields(org.extensiblecatalog.ncip.v2.service.DeleteItemFields deleteItemFields) {

        this.deleteItemFields = deleteItemFields;

    }

    /**
     * Get DeleteItemFields.
     */
    public org.extensiblecatalog.ncip.v2.service.DeleteItemFields getDeleteItemFields() {

        return deleteItemFields;

    }

    /**
     * AddItemFields
     */
    protected org.extensiblecatalog.ncip.v2.service.AddItemFields addItemFields;

    /**
     * Set AddItemFields.
     */
    public void setAddItemFields(org.extensiblecatalog.ncip.v2.service.AddItemFields addItemFields) {

        this.addItemFields = addItemFields;

    }

    /**
     * Get AddItemFields.
     */
    public org.extensiblecatalog.ncip.v2.service.AddItemFields getAddItemFields() {

        return addItemFields;

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

