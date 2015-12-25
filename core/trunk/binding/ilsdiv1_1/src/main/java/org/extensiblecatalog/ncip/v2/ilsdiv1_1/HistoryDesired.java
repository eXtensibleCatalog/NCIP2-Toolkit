/**
 * Copyright (c) 2015 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php. 
 */

package org.extensiblecatalog.ncip.v2.ilsdiv1_1;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.math.BigDecimal;

public class HistoryDesired {

    /**
     * Page of history desired
     */
    protected BigDecimal page;

	public BigDecimal getPage() {
		return page;
	}

	public void setPage(BigDecimal page) {
		this.page = page;
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
