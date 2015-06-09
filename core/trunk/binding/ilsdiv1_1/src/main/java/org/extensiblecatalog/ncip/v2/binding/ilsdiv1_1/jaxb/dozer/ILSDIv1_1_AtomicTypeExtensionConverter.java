/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.ilsdiv1_1.jaxb.dozer;

import org.extensiblecatalog.ncip.v2.binding.jaxb.dozer.BaseAtomicTypeExtensionConverter;
import org.extensiblecatalog.ncip.v2.binding.ilsdiv1_1.jaxb.elements.Ext;
import org.extensiblecatalog.ncip.v2.binding.ilsdiv1_1.jaxb.elements.ObjectFactory;

public class ILSDIv1_1_AtomicTypeExtensionConverter extends BaseAtomicTypeExtensionConverter<Ext> {

    public ILSDIv1_1_AtomicTypeExtensionConverter() {

        super(Ext.class);

    }

    @Override
    protected Ext createExtension() {

        return new Ext();

    }

    @Override
    protected Object getObjectFactory() {

        return new ObjectFactory();

    }
}
