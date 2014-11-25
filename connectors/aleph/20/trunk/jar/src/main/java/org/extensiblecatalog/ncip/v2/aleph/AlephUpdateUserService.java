package org.extensiblecatalog.ncip.v2.aleph;

import java.io.IOException;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.AlephXServices.user.AlephUser;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.AuthenticationInput;
import org.extensiblecatalog.ncip.v2.service.FromAgencyId;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.NCIPService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ToAgencyId;
import org.extensiblecatalog.ncip.v2.service.UpdateUserInitiationData;
import org.extensiblecatalog.ncip.v2.service.UpdateUserResponseData;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.extensiblecatalog.ncip.v2.service.Version1AuthenticationInputType;
import org.xml.sax.SAXException;

public class AlephUpdateUserService implements NCIPService<UpdateUserInitiationData, UpdateUserResponseData> {

	public UpdateUserResponseData performService(UpdateUserInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		String patronId = null;
		String password = null;

		for (AuthenticationInput authInput : initData.getAuthenticationInputs()) {
			if (authInput.getAuthenticationInputType() == Version1AuthenticationInputType.USER_ID) {
				patronId = authInput.getAuthenticationInputData();
			} else if (authInput.getAuthenticationInputType() == Version1AuthenticationInputType.PASSWORD) {
				password = authInput.getAuthenticationInputData();
			}
		}

		if (patronId.isEmpty() || password.isEmpty()) {
			String details = patronId.isEmpty() ? "Username" : "Password" + " is missing.";
			throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "Please supply both authentication inputs of user. " + details);
		}

		AlephRemoteServiceManager alephRemoteServiceManager = (AlephRemoteServiceManager) serviceManager;

		final UpdateUserResponseData responseData = new UpdateUserResponseData();

		try {
			AlephUser alephUser = alephRemoteServiceManager.updateUser(patronId, password, initData);
			if (alephUser == null)
				throw new AlephException("alephUser returned by responder is null");
			else {
				UserId id = new UserId();
				String username = alephUser.getUsername();
				id.setUserIdentifierValue(username);
				responseData.setUserId(id);
			}
		} catch (IOException ie) {
			Problem p = new Problem(new ProblemType("Processing IOException error."), null, ie.getMessage());
			responseData.setProblems(Arrays.asList(p));
		} catch (SAXException se) {
			Problem p = new Problem(new ProblemType("Processing SAXException error."), null, se.getMessage());
			responseData.setProblems(Arrays.asList(p));
		} catch (AlephException ae) {
			Problem p = new Problem(new ProblemType("Processing AlephException error."), null, ae.getMessage());
			responseData.setProblems(Arrays.asList(p));
		} catch (ParserConfigurationException pce) {
			Problem p = new Problem(new ProblemType("Processing ParserConfigurationException error."), null, pce.getMessage());
			responseData.setProblems(Arrays.asList(p));
		} catch (Exception e) {
			Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, e.getMessage());
			responseData.setProblems(Arrays.asList(p));
		}

		InitiationHeader initiationHeader = initData.getInitiationHeader();
		if (initiationHeader != null) {
			ResponseHeader responseHeader = new ResponseHeader();
			if (initiationHeader.getFromAgencyId() != null && initiationHeader.getToAgencyId() != null) {
				// Reverse From/To AgencyId because of the request was processed (return to initiator)
				ToAgencyId toAgencyId = new ToAgencyId();
				toAgencyId.setAgencyIds(initiationHeader.getFromAgencyId().getAgencyIds());

				FromAgencyId fromAgencyId = new FromAgencyId();
				fromAgencyId.setAgencyIds(initiationHeader.getToAgencyId().getAgencyIds());

				responseHeader.setFromAgencyId(fromAgencyId);
				responseHeader.setToAgencyId(toAgencyId);
			}
			if (initiationHeader.getFromSystemId() != null && initiationHeader.getToSystemId() != null) {
				responseHeader.setFromSystemId(initiationHeader.getFromSystemId());
				responseHeader.setToSystemId(initiationHeader.getToSystemId());
				if (initiationHeader.getFromAgencyAuthentication() != null && !initiationHeader.getFromAgencyAuthentication().isEmpty())
					responseHeader.setFromSystemAuthentication(initiationHeader.getFromAgencyAuthentication());
			}
			responseData.setResponseHeader(responseHeader);
		}

		return responseData;
	}

}
