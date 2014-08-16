/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.aleph;

import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * This class implements the Renew Item service for the Aleph back-end
 * connector. Basically this just calls the DummyRemoteServiceManager to get
 * hard-coded data.
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService
 * classes, do not use this class as an example. See the NCIP toolkit Connector
 * developer's documentation for guidance.
 */
public class AlephRenewItemService implements RenewItemService {

	protected static int RENEWAL_PERIOD = 10;

	/**
	 * Handles a NCIP RenewItem service by returning data from Aleph.
	 *
	 * @param initData
	 *            the RenewItemInitiationData
	 * @param serviceManager
	 *            provides access to remote services
	 * @return RenewItemResponseData
	 * @throws ServiceException
	 */
	@Override
	public RenewItemResponseData performService(
			RenewItemInitiationData initData, ServiceContext serviceContext,
			RemoteServiceManager serviceManager) throws ServiceException {

		final RenewItemResponseData responseData = new RenewItemResponseData();

		AlephRemoteServiceManager alephRemoteServiceManager = (AlephRemoteServiceManager) serviceManager;

		if (alephRemoteServiceManager.getXServerName() == null
				|| alephRemoteServiceManager.getXServerPort() == null) {
			throw new ServiceException(ServiceError.CONFIGURATION_ERROR,
					"Aleph X-Server name and/or port not set");
		}

		String itemBarcode = initData.getItemId() != null ? initData
				.getItemId().getItemIdentifierValue() : null;
		boolean userIdSet = initData.getUserId() != null && initData.getUserId().getUserIdentifierValue() != null ;

		Date dueDate = null;

		if (itemBarcode != null) {

			try {
				// TODO: Change default MZK AgencyID to choosable value, but
				// leave MZK by default
				// E.G. initData.getItemId().getAgencyId().getValue() != null ?
				// initData.getItemId().getAgencyId().getValue() : "MZK"
				String user = null;
				String pw = null;

				if (userIdSet) {
					user = initData.getUserId().getUserIdentifierValue();
				} else {
					if(initData.getAuthenticationInputs().get(0).getAuthenticationInputType().getValue().toLowerCase()=="username") {
						user = initData.getAuthenticationInputs().get(0).getAuthenticationInputData();
						pw = initData.getAuthenticationInputs().get(1).getAuthenticationInputData();
					} else {
						pw = initData.getAuthenticationInputs().get(0).getAuthenticationInputData();
						user = initData.getAuthenticationInputs().get(1).getAuthenticationInputData();
					}						
				}

				dueDate = alephRemoteServiceManager.renewItemByBibId(user, "MZK",itemBarcode);
				updateResponseData(initData, responseData, dueDate);

			} catch (IOException ie) {
				Problem p = new Problem();
				p.setProblemType(new ProblemType("Procesing error"));
				p.setProblemDetail(ie.getMessage());
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(p);
				responseData.setProblems(problems);
			} catch (ParserConfigurationException pce) {
				Problem p = new Problem();
				p.setProblemType(new ProblemType("Procesing error"));
				p.setProblemDetail(pce.getMessage());
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(p);
				responseData.setProblems(problems);
			} catch (SAXException se) {
				Problem p = new Problem();
				p.setProblemType(new ProblemType("Procesing error"));
				p.setProblemDetail(se.getMessage());
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(p);
				responseData.setProblems(problems);
			} catch (AlephException ae) {
				Problem p = new Problem();
				p.setProblemType(new ProblemType("Procesing error"));
				p.setProblemDetail(ae.getMessage());
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(p);
				responseData.setProblems(problems);
			} catch (Exception e) {
				Problem p = new Problem();
				p.setProblemType(new ProblemType("Procesing error"));
				p.setProblemDetail(e.getMessage());
				List<Problem> problems = new ArrayList<Problem>();
				problems.add(p);
				responseData.setProblems(problems);
			}

			return responseData;

		} else {

			responseData.setProblems(ServiceHelper.generateProblems(
					Version1GeneralProcessingError.NEEDED_DATA_MISSING,
					"//ItemId/ItemIdentifierValue", null,
					"Item barcode is missing."));
		}

		return responseData;
	}

	protected void updateResponseData(RenewItemInitiationData initData, RenewItemResponseData responseData, Date dueDate)
			throws ServiceException {
		
		if (responseData != null && dueDate != null) {

			responseData.setItemId(initData.getItemId());
			responseData.setUserId(initData.getUserId());
			GregorianCalendar dateDueGregCal = new GregorianCalendar();
			dateDueGregCal.setTime(dueDate);
			responseData.setDateDue(dateDueGregCal);

		}
	}
}
