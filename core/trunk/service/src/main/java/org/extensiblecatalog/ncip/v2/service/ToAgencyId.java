/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.math.BigDecimal;

/**
 * Carries data elements describing the ToAgencyId.
 */
public class ToAgencyId {

    /**
     * AgencyId
     */
    protected List<AgencyId> agencyIds = new ArrayList<AgencyId>();

    /**
     * Set AgencyId.
     */
    public void setAgencyId(AgencyId agencyId) {

        this.agencyIds.add(agencyId);

    }

    /**
     * Set the list of AgencyIds.
     * @param agencyIds the list of AgencyIds
     */
    public void setAgencyIds(List<AgencyId> agencyIds) {

        this.agencyIds = agencyIds;

    }

    /**
     * Get AgencyId.
     */
    public AgencyId getAgencyId() {

        return agencyIds.size() > 0 ? agencyIds.get(0) : null;

    }

    /**
     * Get the list of AgencyIds
     * @return the list of AgencyIds
     */
    public List<AgencyId> getAgencyIds() {

        return agencyIds;

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

