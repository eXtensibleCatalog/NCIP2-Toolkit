package org.extensiblecatalog.ncip.v2.koha;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.extensiblecatalog.ncip.v2.koha.util.KohaConstants;
import org.extensiblecatalog.ncip.v2.koha.util.KohaException;
import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.koha.util.KohaUtil;
import org.extensiblecatalog.ncip.v2.service.AuthenticationInput;
import org.extensiblecatalog.ncip.v2.service.NCIPService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.UpdateUserInitiationData;
import org.extensiblecatalog.ncip.v2.service.UpdateUserResponseData;
import org.extensiblecatalog.ncip.v2.service.Version1AuthenticationInputType;
import org.xml.sax.SAXException;

public class KohaUpdateUserService implements NCIPService<UpdateUserInitiationData, UpdateUserResponseData> {

	public UpdateUserResponseData performService(UpdateUserInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager) throws ServiceException {

		final UpdateUserResponseData responseData = new UpdateUserResponseData();

		String patronId = null;
		String password = null;

		if (initData.getUserId() != null) {
			patronId = initData.getUserId().getUserIdentifierValue();

		} else if (initData.getAuthenticationInputs() != null) {

			for (AuthenticationInput authInput : initData.getAuthenticationInputs()) {
				if (authInput.getAuthenticationInputType() == Version1AuthenticationInputType.USER_ID) {
					patronId = authInput.getAuthenticationInputData();
				} else if (authInput.getAuthenticationInputType() == Version1AuthenticationInputType.PASSWORD) {
					password = authInput.getAuthenticationInputData();
				}
			}
		}
		boolean userIdIsEmpty = patronId.isEmpty();
		boolean authIsSetAndPwIsEmpty = initData.getAuthenticationInputs() != null && initData.getAuthenticationInputs().size() > 0 && password.isEmpty();

		if (userIdIsEmpty || authIsSetAndPwIsEmpty) {
			List<Problem> problems = new ArrayList<Problem>();

			if (userIdIsEmpty) {

				if (initData.getUserId() == null) {
					Problem p = new Problem(new ProblemType("User Id is undefined."), null, null, "Set AuthenticationInputType to \""
							+ Version1AuthenticationInputType.USER_ID.getValue() + "\" to specify user id input.");
					problems.add(p);
				} else {
					Problem p = new Problem(new ProblemType("User Id is empty."), null, null);
					problems.add(p);
				}

			}
			if (authIsSetAndPwIsEmpty) {

				Problem p = new Problem(new ProblemType("Password is undefined."), null, "Can't authenticate without password specified.", "Set AuthenticationInputType to \""
						+ Version1AuthenticationInputType.PASSWORD.getValue() + "\" to specify password input.");
				problems.add(p);

			}

			responseData.setProblems(problems);
		} else {

			KohaRemoteServiceManager kohaRemoteServiceManager = (KohaRemoteServiceManager) serviceManager;

			try {
				if (initData.getUserId() == null) {
					// Here is AuthenticationInput set

				} else {
					// Here is UserId set .. no authentication

					String updateUserReplytext = kohaRemoteServiceManager.updateUser(initData);

					if (updateUserReplytext != null && updateUserReplytext.equalsIgnoreCase(KohaConstants.STATUS_OK))
						responseData.setUserId(initData.getUserId());
					else {
						Problem p = new Problem(new ProblemType("UpdateUserService failed."), null, null, updateUserReplytext);
						responseData.setProblems(Arrays.asList(p));
					}
				}
			} catch (IOException ie) {
				Problem p = new Problem(new ProblemType("Processing IOException error."), ie.getMessage(), "Are you connected to the Internet/Intranet?");
				responseData.setProblems(Arrays.asList(p));
			} catch (SAXException se) {
				Problem p = new Problem(new ProblemType("Processing SAXException error."), null, se.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (KohaException ae) {
				Problem p = new Problem(new ProblemType("Processing KohaException error."), null, ae.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (ParserConfigurationException pce) {
				Problem p = new Problem(new ProblemType("Processing ParserConfigurationException error."), null, pce.getMessage());
				responseData.setProblems(Arrays.asList(p));
			} catch (FactoryConfigurationError fce) {
				Problem p = new Problem(new ProblemType("Processing FactoryConfigurationError."), null, fce.getMessage(), "Error initializing XMLBuilder.");
				responseData.setProblems(Arrays.asList(p));
			} catch (Exception e) {
				Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, e.getMessage());
				responseData.setProblems(Arrays.asList(p));
			}

			ResponseHeader responseHeader = KohaUtil.reverseInitiationHeader(initData);

			if (responseHeader != null)
				responseData.setResponseHeader(responseHeader);
		}
		return responseData;
	}
}
