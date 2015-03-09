package org.extensiblecatalog.ncip.v2.koha;

import java.math.BigDecimal;

import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.koha.util.KohaRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.*;
import org.xml.sax.SAXException;

import junit.framework.TestCase;

public class KohaCreateUserFiscalTransactionTest extends TestCase {
	public void testPerformService() throws ServiceException, ValidationException, ParserConfigurationException, SAXException {
		KohaCreateUserFiscalTransactionService service = new KohaCreateUserFiscalTransactionService();
		KohaRemoteServiceManager serviceManager = new KohaRemoteServiceManager();
		CreateUserFiscalTransactionInitiationData initData = new CreateUserFiscalTransactionInitiationData();
		CreateUserFiscalTransactionResponseData responseData = new CreateUserFiscalTransactionResponseData();

		// Input:

		AgencyId agencyId = new AgencyId("KOH");
		String requestIdVal = "005033321";

		String userIdVal = "701";

		UserId userId = new UserId();
		userId.setAgencyId(agencyId);
		userId.setUserIdentifierType(Version1UserIdentifierType.INSTITUTION_ID_NUMBER);
		userId.setUserIdentifierValue(userIdVal);
		initData.setUserId(userId);

		FiscalTransactionInformation fiscalTransactionInformation = new FiscalTransactionInformation();

		Amount amount = new Amount();
		amount.setMonetaryValue(new BigDecimal("1000"));
		amount.setCurrencyCode(Version1CurrencyCode.CZK);
		fiscalTransactionInformation.setAmount(amount);

		RequestId requestId = new RequestId();
		requestId.setAgencyId(agencyId);
		requestId.setRequestIdentifierValue(requestIdVal);

		fiscalTransactionInformation.setRequestId(requestId);
		fiscalTransactionInformation.setFiscalActionType(Version1FiscalActionType.PAYMENT);
		fiscalTransactionInformation.setFiscalTransactionType(Version1FiscalTransactionType.INTERLIBRARY_LOAN_FEE);
		fiscalTransactionInformation.setPaymentMethodType(Version1PaymentMethodType.BANK_DRAFT);

		initData.setFiscalTransactionInformation(fiscalTransactionInformation);
		
		InitiationHeader initiationHeader = new InitiationHeader();
		
		ToAgencyId toAgencyId = new ToAgencyId();
		toAgencyId.setAgencyId(new AgencyId("KOH-Koha"));
				
		FromAgencyId fromAgencyId = new FromAgencyId();
		fromAgencyId.setAgencyId(new AgencyId("KOH-VuFind"));
		
		initiationHeader.setFromAgencyId(fromAgencyId);
		initiationHeader.setToAgencyId(toAgencyId);
		initData.setInitiationHeader(initiationHeader);
		// Output:

		int fiscalTranIdValLength = 27;

		responseData = service.performService(initData, null, serviceManager);

		assertEquals("Unexpected presence of ns1:Problem element.", true, responseData.getProblems() == null || responseData.getProblems().get(0) == null);

		assertEquals("Unexpected UserId returned.", userIdVal, responseData.getUserId().getUserIdentifierValue());
		assertEquals("Unexpected AgencyId returned.", agencyId.getValue(), responseData.getFiscalTransactionReferenceId().getAgencyId().getValue());
		assertEquals("Unexpected length of FiscalTransactionIdentifierValue returned.", fiscalTranIdValLength, responseData.getFiscalTransactionReferenceId()
				.getFiscalTransactionIdentifierValue().length());
		assertEquals("Unexpected ToAgencyId returned.", fromAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getToAgencyId().getAgencyId().getValue());
		assertEquals("Unexpected FromAgencyId returned.", toAgencyId.getAgencyId().getValue(), responseData.getResponseHeader().getFromAgencyId().getAgencyId().getValue());
	}
}