/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.common;

import org.extensiblecatalog.ncip.v2.service.*;

import java.util.List;


/**
 * This is a dummy implementation of NCIPservice interface. This is
 * used in case no service implementation class is configured in
 * connector configuration file for a NCIP service.
 *
 * @author SharmilaR
 */
public class DefaultService implements NCIPService<NCIPInitiationData, NCIPResponseData> {

    @Override
    public NCIPResponseData performService(NCIPInitiationData initiationData,
                                           ServiceContext serviceContext,
                                           RemoteServiceManager serviceManager) {

        List<Problem> problems = ServiceHelper.createUnsupportedServiceProblems(initiationData);
        ProblemResponseData problemResponseData = new ProblemResponseData();
        problemResponseData.setProblems(problems);
        return problemResponseData;

    }

}
