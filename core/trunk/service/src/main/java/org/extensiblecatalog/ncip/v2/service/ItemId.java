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
 * This class represents identifiers for items.
 * Note: an "item" in NCIP is typically the circulating unit.
 */
public final class ItemId {
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 4389080391314069789L;

    /**
     * The identifier of this item.
     */
    protected String itemIdentifierValue;
    /**
     * The id of the agency associated with this item.
     */
    protected AgencyId agencyId;
    /**
     * The type of the identifier, indicates whether e.g. this is an item barcode, item record #, etc.
     */
    protected ItemIdentifierType itemIdentifierType;

    public ItemId() {

    }

    public void setItemIdentifierValue(String itemIdentifierValue) {
        this.itemIdentifierValue = itemIdentifierValue;
    }

    /**
     * Returns the identifier value.
     *
     * @return the identifier value
     */
    public String getItemIdentifierValue() {
        return itemIdentifierValue;
    }

    public void setItemIdentifierType(ItemIdentifierType itemIdentifierType) {
        this.itemIdentifierType = itemIdentifierType;
    }

    /**
     * Returns the id's identifier type.
     *
     * @return the id's identifier type
     */
    public ItemIdentifierType getItemIdentifierType() {
        return itemIdentifierType;
    }

    public void setAgencyId(AgencyId agencyId) {
        this.agencyId = agencyId;
    }

    /**
     * Returns the id's agency id.
     *
     * @return the id's agency id
     */
    public AgencyId getAgencyId() {
        return agencyId;
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
