/**
 * Copyright (c) 2011 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.binding.jaxb.dozer;

import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.dozer.util.MappingUtils;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemResponseData;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class ProblemResponseConverter extends DozerConverter<List, ProblemResponseData> implements MapperAware {

    protected Mapper mapper;

    public ProblemResponseConverter() {

        super(List.class, ProblemResponseData.class);

    }

    @Override
    public ProblemResponseData convertTo(List jaxbProblemList, ProblemResponseData responseData) {

        ProblemResponseData result = null;

        if ( jaxbProblemList != null && jaxbProblemList.size() > 0 ) {

            result = new ProblemResponseData();

            List<org.extensiblecatalog.ncip.v2.service.Problem> svcProblemsList
                = new ArrayList<Problem>();

            for ( Object obj : jaxbProblemList ) {

                org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Problem jaxbProblem
                    = (org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Problem)obj;

                org.extensiblecatalog.ncip.v2.service.Problem svcProblem
                    = mapper.map(jaxbProblem, org.extensiblecatalog.ncip.v2.service.Problem.class);

                svcProblemsList.add(svcProblem);

            }

            result.setProblems(svcProblemsList);

        } else {

            // Do nothing - input object is null

        }

        return result;
    }

    @Override
    public List convertFrom(ProblemResponseData responseData, List jaxbProblemList) {

        List<org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Problem> jaxbProblemsList = null;

        if ( responseData.getProblems() != null && responseData.getProblems().size() > 0 ) {

            jaxbProblemsList = new ArrayList<org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Problem>();

            for ( org.extensiblecatalog.ncip.v2.service.Problem svcProblem : responseData.getProblems() ) {

                org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Problem jaxbProblem
                    = mapper.map(svcProblem, org.extensiblecatalog.ncip.v2.binding.jaxb.elements.Problem.class);

                jaxbProblemsList.add(jaxbProblem);

            }

        }

        return jaxbProblemsList;

    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
