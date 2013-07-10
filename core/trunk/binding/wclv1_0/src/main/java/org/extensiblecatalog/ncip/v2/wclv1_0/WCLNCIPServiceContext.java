/**
 * Copyright (c) 2012 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.wclv1_0;

import org.extensiblecatalog.ncip.v2.common.NCIPServiceContext;
import org.extensiblecatalog.ncip.v2.common.ServiceValidatorConfiguration;
import org.extensiblecatalog.ncip.v2.service.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class WCLNCIPServiceContext extends NCIPServiceContext {

    /**
     * The ApplicationProfileType from the initiation message (if we have handled an initiation message, and
     * if it had an ApplicationProfileType).
     */
    protected ApplicationProfileType initMsgAppProfileType;

    public WCLNCIPServiceContext(ServiceValidatorConfiguration config) throws ToolkitException {

        super(config);

    }

    @Override
    public void validateAfterUnmarshalling(NCIPMessage ncipMessage) throws ValidationException {

        // TODO: Write validateAfterUnmarshalling(NCIPMessage ncipMessage)

    }

    @Override
    public void validateBeforeMarshalling(NCIPMessage ncipMessage) throws ValidationException {

        try {

            if ( ncipMessage.getInitiationData() != null ) {

                NCIPInitiationData initData = ncipMessage.getInitiationData();
                if ( initData.getInitiationHeader() != null
                    && initData.getInitiationHeader().getApplicationProfileType() != null ) {

                    initMsgAppProfileType = initData.getInitiationHeader().getApplicationProfileType();

                }

                if ( initMsgAppProfileType == null && requiresApplicationProfileType() ) {

                    throw new ValidationException(ServiceHelper.generateProblems(
                        Version1GeneralProcessingError.TEMPORARY_PROCESSING_FAILURE, null, null,
                        "NCIPMessage object does not have ApplicationProfileType and "
                            + "it is required by this ServiceContext."));

                }

            } else if ( ncipMessage.getResponseData() != null ) {

                if ( initMsgAppProfileType != null && ! initMsgAppProfileType.equals(WCLApplicationProfileType.VERSION_2011) ) {

                    NCIPResponseData ncipRespData = ncipMessage.getResponseData();
                    if ( ncipRespData != null && ncipRespData instanceof LookupUserResponseData ) {

                        LookupUserResponseData lookupUserRespData = (LookupUserResponseData)ncipRespData;

                        for ( LoanedItemsCount loanedItemsCount : lookupUserRespData.getLoanedItemsCounts() ) {

                            CirculationStatus circStatus = loanedItemsCount.getCirculationStatus();

                            if ( circStatus.equals(Version1CirculationStatus.ON_ORDER) ) {

                                    loanedItemsCount.setCirculationStatus(WCLv1_0CirculationStatus.ON_ORDER);

                            } else if ( circStatus.equals(Version1CirculationStatus.AVAILABLE_ON_SHELF) ) {

                                    loanedItemsCount.setCirculationStatus(WCLv1_0CirculationStatus.AVAILABLE);

                            } else if ( circStatus.equals(Version1CirculationStatus.ON_LOAN) ) {

                                    loanedItemsCount.setCirculationStatus(WCLv1_0CirculationStatus.ON_LOAN);

                            } else if ( circStatus.equals(
                                Version1CirculationStatus.IN_TRANSIT_BETWEEN_LIBRARY_LOCATIONS) ) {

                                    loanedItemsCount.setCirculationStatus(WCLv1_0CirculationStatus.LOST);

                            } // else do nothing - the circStatus isn't one that needs translating

                        }

                        // RequestedItemsCount.CirculationStatus
                        for ( RequestedItemsCount requestedItemsCount : lookupUserRespData.getRequestedItemsCounts() ) {

                            CirculationStatus circStatus = requestedItemsCount.getCirculationStatus();

                            if ( circStatus.equals(Version1CirculationStatus.ON_ORDER) ) {

                                    requestedItemsCount.setCirculationStatus(WCLv1_0CirculationStatus.ON_ORDER);

                            } else if ( circStatus.equals(Version1CirculationStatus.AVAILABLE_ON_SHELF) ) {

                                    requestedItemsCount.setCirculationStatus(WCLv1_0CirculationStatus.AVAILABLE);

                            } else if ( circStatus.equals(Version1CirculationStatus.ON_LOAN) ) {

                                    requestedItemsCount.setCirculationStatus(WCLv1_0CirculationStatus.ON_LOAN);

                            } else if ( circStatus.equals(
                                Version1CirculationStatus.IN_TRANSIT_BETWEEN_LIBRARY_LOCATIONS) ) {

                                    requestedItemsCount.setCirculationStatus(WCLv1_0CirculationStatus.LOST);

                            } // else do nothing - the circStatus isn't one that needs translation

                        }

                    } // else do nothing if the service type isn't one we need to handle.

                } // else do nothing - we are not in a WCL App Profile service context

            } else {

                List<Problem> problemsList = ServiceHelper.generateProblems(
                    Version1GeneralProcessingError.TEMPORARY_PROCESSING_FAILURE,
                    null, null, "NCIPMessage object does not have either Initiation or Response data.");
                throw new ValidationException(problemsList);

            }

        } catch (InvocationTargetException e) {

            List<Problem> problemsList = ServiceHelper.generateProblems(
                Version1GeneralProcessingError.TEMPORARY_PROCESSING_FAILURE,
                null, null, "Exception getting NCIPData object from NCIPMessage object.", e);
            throw new ValidationException(problemsList);

        } catch (IllegalAccessException e) {

            List<Problem> problemsList = ServiceHelper.generateProblems(
                Version1GeneralProcessingError.TEMPORARY_PROCESSING_FAILURE,
                null, null, "Exception getting NCIPData object from NCIPMessage object.", e);
            throw new ValidationException(problemsList);

        } catch (ServiceException e) {

            List<Problem> problemsList = ServiceHelper.generateProblems(
                Version1GeneralProcessingError.TEMPORARY_PROCESSING_FAILURE,
                null, null, "Exception getting NCIPData object from NCIPMessage object.", e);
            throw new ValidationException(problemsList);

        }

    }

}
