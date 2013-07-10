/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.service;

import org.apache.log4j.Logger;

/**
 * Note: This is in fact inherited from the NCIP version 1 VisibleItemIdentifierType, but
 * as the VisibleItemIdentifier element was renamed ItemIdentifier with version 2,
 * that is the name used for this scheme.
 */
public class Version1ElectronicDataFormatType extends ElectronicDataFormatType {

    private static final Logger LOG = Logger.getLogger(Version1ElectronicDataFormatType.class);

    public static final String VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE
            = "http://www.iana.org/assignments/media-types";

    // 1d-interleaved-parityfec
    public static final Version1ElectronicDataFormatType APPLICATION_1D_INTERLEAVED_PARITYFEC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/1d-interleaved-parityfec");
    // 3gpp-ims+xml
    public static final Version1ElectronicDataFormatType APPLICATION_3GPP_IMS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/3gpp-ims+xml");
    // activemessage
    public static final Version1ElectronicDataFormatType APPLICATION_ACTIVEMESSAGE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/activemessage");
    // andrew-inset
    public static final Version1ElectronicDataFormatType APPLICATION_ANDREW_INSET
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/andrew-inset");
    // applefile
    public static final Version1ElectronicDataFormatType APPLICATION_APPLEFILE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/applefile");
    // atom+xml
    public static final Version1ElectronicDataFormatType APPLICATION_ATOM_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/atom+xml");
    // atomicmail
    public static final Version1ElectronicDataFormatType APPLICATION_ATOMICMAIL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/atomicmail");
    // atomcat+xml
    public static final Version1ElectronicDataFormatType APPLICATION_ATOMCAT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/atomcat+xml");
    // atomsvc+xml
    public static final Version1ElectronicDataFormatType APPLICATION_ATOMSVC_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/atomsvc+xml");
    // auth-policy+xml
    public static final Version1ElectronicDataFormatType APPLICATION_AUTH_POLICY_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/auth-policy+xml");
    // batch-SMTP
    public static final Version1ElectronicDataFormatType APPLICATION_BATCH_SMTP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/batch-SMTP");
    // beep+xml
    public static final Version1ElectronicDataFormatType APPLICATION_BEEP_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/beep+xml");
    // cals-1840
    public static final Version1ElectronicDataFormatType APPLICATION_CALS_1840
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/cals-1840");
    // ccxml+xml
    public static final Version1ElectronicDataFormatType APPLICATION_CCXML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/ccxml+xml");
    // cea-2018+xml
    public static final Version1ElectronicDataFormatType APPLICATION_CEA_2018_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/cea-2018+xml");
    // cellml+xml
    public static final Version1ElectronicDataFormatType APPLICATION_CELLML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/cellml+xml");
    // cnrp+xml
    public static final Version1ElectronicDataFormatType APPLICATION_CNRP_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/cnrp+xml");
    // commonground
    public static final Version1ElectronicDataFormatType APPLICATION_COMMONGROUND
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/commonground");
    // conference-info+xml
    public static final Version1ElectronicDataFormatType APPLICATION_CONFERENCE_INFO_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/conference-info+xml");
    // cpl+xml
    public static final Version1ElectronicDataFormatType APPLICATION_CPL_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/cpl+xml");
    // csta+xml
    public static final Version1ElectronicDataFormatType APPLICATION_CSTA_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/csta+xml");
    // CSTAdata+xml
    public static final Version1ElectronicDataFormatType APPLICATION_CSTADATA_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/CSTAdata+xml");
    // cybercash
    public static final Version1ElectronicDataFormatType APPLICATION_CYBERCASH
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/cybercash");
    // davmount+xml
    public static final Version1ElectronicDataFormatType APPLICATION_DAVMOUNT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/davmount+xml");
    // dca-rft
    public static final Version1ElectronicDataFormatType APPLICATION_DCA_RFT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/dca-rft");
    // dec-dx
    public static final Version1ElectronicDataFormatType APPLICATION_DEC_DX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/dec-dx");
    // dialog-info+xml
    public static final Version1ElectronicDataFormatType APPLICATION_DIALOG_INFO_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/dialog-info+xml");
    // dicom
    public static final Version1ElectronicDataFormatType APPLICATION_DICOM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/dicom");
    // dns
    public static final Version1ElectronicDataFormatType APPLICATION_DNS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/dns");
    // dskpp+xml
    public static final Version1ElectronicDataFormatType APPLICATION_DSKPP_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/dskpp+xml");
    // dssc+der
    public static final Version1ElectronicDataFormatType APPLICATION_DSSC_DER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/dssc+der");
    // dssc+xml
    public static final Version1ElectronicDataFormatType APPLICATION_DSSC_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/dssc+xml");
    // dvcs
    public static final Version1ElectronicDataFormatType APPLICATION_DVCS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/dvcs");
    // ecmascript
    public static final Version1ElectronicDataFormatType APPLICATION_ECMASCRIPT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/ecmascript");
    // EDI-Consent
    public static final Version1ElectronicDataFormatType APPLICATION_EDI_CONSENT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/EDI-Consent");
    // EDIFACT
    public static final Version1ElectronicDataFormatType APPLICATION_EDIFACT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/EDIFACT");
    // EDI-X12
    public static final Version1ElectronicDataFormatType APPLICATION_EDI_X12
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/EDI-X12");
    // emma+xml
    public static final Version1ElectronicDataFormatType APPLICATION_EMMA_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/emma+xml");
    // epp+xml
    public static final Version1ElectronicDataFormatType APPLICATION_EPP_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/epp+xml");
    // eshop
    public static final Version1ElectronicDataFormatType APPLICATION_ESHOP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/eshop");
    // example
    public static final Version1ElectronicDataFormatType APPLICATION_EXAMPLE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/example");
    // exi
    public static final Version1ElectronicDataFormatType APPLICATION_EXI
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/exi");
    // fastinfoset
    public static final Version1ElectronicDataFormatType APPLICATION_FASTINFOSET
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/fastinfoset");
    // fastsoap
    public static final Version1ElectronicDataFormatType APPLICATION_FASTSOAP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/fastsoap");
    // fits
    public static final Version1ElectronicDataFormatType APPLICATION_FITS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/fits");
    // font-tdpfr
    public static final Version1ElectronicDataFormatType APPLICATION_FONT_TDPFR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/font-tdpfr");
    // H224
    public static final Version1ElectronicDataFormatType APPLICATION_H224
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/H224");
    // held+xml
    public static final Version1ElectronicDataFormatType APPLICATION_HELD_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/held+xml");
    // http
    public static final Version1ElectronicDataFormatType APPLICATION_HTTP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/http");
    // hyperstudio
    public static final Version1ElectronicDataFormatType APPLICATION_HYPERSTUDIO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/hyperstudio");
    // ibe-key-request+xml
    public static final Version1ElectronicDataFormatType APPLICATION_IBE_KEY_REQUEST_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/ibe-key-request+xml");
    // ibe-pkg-reply+xml
    public static final Version1ElectronicDataFormatType APPLICATION_IBE_PKG_REPLY_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/ibe-pkg-reply+xml");
    // ibe-pp-data
    public static final Version1ElectronicDataFormatType APPLICATION_IBE_PP_DATA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/ibe-pp-data");
    // iges
    public static final Version1ElectronicDataFormatType APPLICATION_IGES
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/iges");
    // im-iscomposing+xml
    public static final Version1ElectronicDataFormatType APPLICATION_IM_ISCOMPOSING_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/im-iscomposing+xml");
    // index
    public static final Version1ElectronicDataFormatType APPLICATION_INDEX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/index");
    // index.cmd
    public static final Version1ElectronicDataFormatType APPLICATION_INDEX_CMD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/index.cmd");
    // index.obj
    public static final Version1ElectronicDataFormatType APPLICATION_INDEX_OBJ
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/index.obj");
    // index.response
    public static final Version1ElectronicDataFormatType APPLICATION_INDEX_RESPONSE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/index.response");
    // index.vnd
    public static final Version1ElectronicDataFormatType APPLICATION_INDEX_VND
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/index.vnd");
    // iotp
    public static final Version1ElectronicDataFormatType APPLICATION_IOTP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/iotp");
    // ipfix
    public static final Version1ElectronicDataFormatType APPLICATION_IPFIX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/ipfix");
    // ipp
    public static final Version1ElectronicDataFormatType APPLICATION_IPP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/ipp");
    // isup
    public static final Version1ElectronicDataFormatType APPLICATION_ISUP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/isup");
    // javascript
    public static final Version1ElectronicDataFormatType APPLICATION_JAVASCRIPT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/javascript");
    // json
    public static final Version1ElectronicDataFormatType APPLICATION_JSON
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/json");
    // kpml-request+xml
    public static final Version1ElectronicDataFormatType APPLICATION_KPML_REQUEST_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/kpml-request+xml");
    // kpml-response+xml
    public static final Version1ElectronicDataFormatType APPLICATION_KPML_RESPONSE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/kpml-response+xml");
    // lost+xml
    public static final Version1ElectronicDataFormatType APPLICATION_LOST_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/lost+xml");
    // mac-binhex40
    public static final Version1ElectronicDataFormatType APPLICATION_MAC_BINHEX40
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mac-binhex40");
    // macwriteii
    public static final Version1ElectronicDataFormatType APPLICATION_MACWRITEII
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/macwriteii");
    // marc
    public static final Version1ElectronicDataFormatType APPLICATION_MARC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/marc");
    // mathematica
    public static final Version1ElectronicDataFormatType APPLICATION_MATHEMATICA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mathematica");
    // mathml-content+xml
    public static final Version1ElectronicDataFormatType APPLICATION_MATHML_CONTENT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mathml-content+xml");
    // mathml-presentation+xml
    public static final Version1ElectronicDataFormatType APPLICATION_MATHML_PRESENTATION_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mathml-presentation+xml");
    // mathml+xml
    public static final Version1ElectronicDataFormatType APPLICATION_MATHML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mathml+xml");
    // mbms-associated-procedure-description+xml
    public static final Version1ElectronicDataFormatType APPLICATION_MBMS_ASSOCIATED_PROCEDURE_DESCRIPTION_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mbms-associated-procedure-description+xml");
    // mbms-deregister+xml
    public static final Version1ElectronicDataFormatType APPLICATION_MBMS_DEREGISTER_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mbms-deregister+xml");
    // mbms-envelope+xml
    public static final Version1ElectronicDataFormatType APPLICATION_MBMS_ENVELOPE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mbms-envelope+xml");
    // mbms-msk-response+xml
    public static final Version1ElectronicDataFormatType APPLICATION_MBMS_MSK_RESPONSE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mbms-msk-response+xml");
    // mbms-msk+xml
    public static final Version1ElectronicDataFormatType APPLICATION_MBMS_MSK_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mbms-msk+xml");
    // mbms-protection-description+xml
    public static final Version1ElectronicDataFormatType APPLICATION_MBMS_PROTECTION_DESCRIPTION_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mbms-protection-description+xml");
    // mbms-reception-report+xml
    public static final Version1ElectronicDataFormatType APPLICATION_MBMS_RECEPTION_REPORT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mbms-reception-report+xml");
    // mbms-register-response+xml
    public static final Version1ElectronicDataFormatType APPLICATION_MBMS_REGISTER_RESPONSE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mbms-register-response+xml");
    // mbms-register+xml
    public static final Version1ElectronicDataFormatType APPLICATION_MBMS_REGISTER_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mbms-register+xml");
    // mbms-user-service-description+xml
    public static final Version1ElectronicDataFormatType APPLICATION_MBMS_USER_SERVICE_DESCRIPTION_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mbms-user-service-description+xml");
    // mbox
    public static final Version1ElectronicDataFormatType APPLICATION_MBOX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mbox");
    // media_control+xml
    public static final Version1ElectronicDataFormatType APPLICATION_MEDIA_CONTROL_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/media_control+xml");
    // mediaservercontrol+xml
    public static final Version1ElectronicDataFormatType APPLICATION_MEDIASERVERCONTROL_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mediaservercontrol+xml");
    // metalink4+xml
    public static final Version1ElectronicDataFormatType APPLICATION_METALINK4_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/metalink4+xml");
    // mikey
    public static final Version1ElectronicDataFormatType APPLICATION_MIKEY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mikey");
    // moss-keys
    public static final Version1ElectronicDataFormatType APPLICATION_MOSS_KEYS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/moss-keys");
    // moss-signature
    public static final Version1ElectronicDataFormatType APPLICATION_MOSS_SIGNATURE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/moss-signature");
    // mosskey-data
    public static final Version1ElectronicDataFormatType APPLICATION_MOSSKEY_DATA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mosskey-data");
    // mosskey-request
    public static final Version1ElectronicDataFormatType APPLICATION_MOSSKEY_REQUEST
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mosskey-request");
    // mp21
    public static final Version1ElectronicDataFormatType APPLICATION_MP21
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mp21");
    // mpeg4-generic
    public static final Version1ElectronicDataFormatType APPLICATION_MPEG4_GENERIC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mpeg4-generic");
    // mpeg4-iod
    public static final Version1ElectronicDataFormatType APPLICATION_MPEG4_IOD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mpeg4-iod");
    // mpeg4-iod-xmt
    public static final Version1ElectronicDataFormatType APPLICATION_MPEG4_IOD_XMT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mpeg4-iod-xmt");
    // mp4
    public static final Version1ElectronicDataFormatType APPLICATION_MP4
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mp4");
    // msword
    public static final Version1ElectronicDataFormatType APPLICATION_MSWORD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/msword");
    // mxf
    public static final Version1ElectronicDataFormatType APPLICATION_MXF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/mxf");
    // nasdata
    public static final Version1ElectronicDataFormatType APPLICATION_NASDATA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/nasdata");
    // news-checkgroups
    public static final Version1ElectronicDataFormatType APPLICATION_NEWS_CHECKGROUPS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/news-checkgroups");
    // news-groupinfo
    public static final Version1ElectronicDataFormatType APPLICATION_NEWS_GROUPINFO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/news-groupinfo");
    // news-transmission
    public static final Version1ElectronicDataFormatType APPLICATION_NEWS_TRANSMISSION
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/news-transmission");
    // nss
    public static final Version1ElectronicDataFormatType APPLICATION_NSS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/nss");
    // ocsp-request
    public static final Version1ElectronicDataFormatType APPLICATION_OCSP_REQUEST
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/ocsp-request");
    // ocsp-response
    public static final Version1ElectronicDataFormatType APPLICATION_OCSP_RESPONSE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/ocsp-response");
    // octet-stream
    public static final Version1ElectronicDataFormatType APPLICATION_OCTET_STREAM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/octet-stream");
    // oda
    public static final Version1ElectronicDataFormatType APPLICATION_ODA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/oda");
    // oebps-package+xml
    public static final Version1ElectronicDataFormatType APPLICATION_OEBPS_PACKAGE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/oebps-package+xml");
    // ogg
    public static final Version1ElectronicDataFormatType APPLICATION_OGG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/ogg");
    // parityfec
    public static final Version1ElectronicDataFormatType APPLICATION_PARITYFEC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/parityfec");
    // patch-ops-error+xml
    public static final Version1ElectronicDataFormatType APPLICATION_PATCH_OPS_ERROR_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/patch-ops-error+xml");
    // pdf
    public static final Version1ElectronicDataFormatType APPLICATION_PDF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pdf");
    // pgp-encrypted
    public static final Version1ElectronicDataFormatType APPLICATION_PGP_ENCRYPTED
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pgp-encrypted");
    // pgp-keys
    public static final Version1ElectronicDataFormatType APPLICATION_PGP_KEYS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pgp-keys");
    // pgp-signature
    public static final Version1ElectronicDataFormatType APPLICATION_PGP_SIGNATURE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pgp-signature");
    // pidf+xml
    public static final Version1ElectronicDataFormatType APPLICATION_PIDF_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pidf+xml");
    // pidf-diff+xml
    public static final Version1ElectronicDataFormatType APPLICATION_PIDF_DIFF_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pidf-diff+xml");
    // pkcs10
    public static final Version1ElectronicDataFormatType APPLICATION_PKCS10
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pkcs10");
    // pkcs7-mime
    public static final Version1ElectronicDataFormatType APPLICATION_PKCS7_MIME
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pkcs7-mime");
    // pkcs7-signature
    public static final Version1ElectronicDataFormatType APPLICATION_PKCS7_SIGNATURE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pkcs7-signature");
    // pkcs8
    public static final Version1ElectronicDataFormatType APPLICATION_PKCS8
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pkcs8");
    // pkix-attr-cert
    public static final Version1ElectronicDataFormatType APPLICATION_PKIX_ATTR_CERT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pkix-attr-cert");
    // pkix-cert
    public static final Version1ElectronicDataFormatType APPLICATION_PKIX_CERT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pkix-cert");
    // pkixcmp
    public static final Version1ElectronicDataFormatType APPLICATION_PKIXCMP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pkixcmp");
    // pkix-crl
    public static final Version1ElectronicDataFormatType APPLICATION_PKIX_CRL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pkix-crl");
    // pkix-pkipath
    public static final Version1ElectronicDataFormatType APPLICATION_PKIX_PKIPATH
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pkix-pkipath");
    // pls+xml
    public static final Version1ElectronicDataFormatType APPLICATION_PLS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pls+xml");
    // poc-settings+xml
    public static final Version1ElectronicDataFormatType APPLICATION_POC_SETTINGS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/poc-settings+xml");
    // postscript
    public static final Version1ElectronicDataFormatType APPLICATION_POSTSCRIPT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/postscript");
    // prs.alvestrand.titrax-sheet
    public static final Version1ElectronicDataFormatType APPLICATION_PRS_ALVESTRAND_TITRAX_SHEET
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/prs.alvestrand.titrax-sheet");
    // prs.cww
    public static final Version1ElectronicDataFormatType APPLICATION_PRS_CWW
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/prs.cww");
    // prs.nprend
    public static final Version1ElectronicDataFormatType APPLICATION_PRS_NPREND
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/prs.nprend");
    // prs.plucker
    public static final Version1ElectronicDataFormatType APPLICATION_PRS_PLUCKER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/prs.plucker");
    // prs.rdf-xml-crypt
    public static final Version1ElectronicDataFormatType APPLICATION_PRS_RDF_XML_CRYPT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/prs.rdf-xml-crypt");
    // prs.xsf+xml
    public static final Version1ElectronicDataFormatType APPLICATION_PRS_XSF_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/prs.xsf+xml");
    // pskc+xml
    public static final Version1ElectronicDataFormatType APPLICATION_PSKC_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/pskc+xml");
    // rdf+xml
    public static final Version1ElectronicDataFormatType APPLICATION_RDF_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/rdf+xml");
    // qsig
    public static final Version1ElectronicDataFormatType APPLICATION_QSIG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/qsig");
    // reginfo+xml
    public static final Version1ElectronicDataFormatType APPLICATION_REGINFO_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/reginfo+xml");
    // relax-ng-compact-syntax
    public static final Version1ElectronicDataFormatType APPLICATION_RELAX_NG_COMPACT_SYNTAX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/relax-ng-compact-syntax");
    // remote-printing
    public static final Version1ElectronicDataFormatType APPLICATION_REMOTE_PRINTING
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/remote-printing");
    // resource-lists-diff+xml
    public static final Version1ElectronicDataFormatType APPLICATION_RESOURCE_LISTS_DIFF_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/resource-lists-diff+xml");
    // resource-lists+xml
    public static final Version1ElectronicDataFormatType APPLICATION_RESOURCE_LISTS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/resource-lists+xml");
    // riscos
    public static final Version1ElectronicDataFormatType APPLICATION_RISCOS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/riscos");
    // rlmi+xml
    public static final Version1ElectronicDataFormatType APPLICATION_RLMI_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/rlmi+xml");
    // rls-services+xml
    public static final Version1ElectronicDataFormatType APPLICATION_RLS_SERVICES_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/rls-services+xml");
    // rtf
    public static final Version1ElectronicDataFormatType APPLICATION_RTF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/rtf");
    // rtx
    public static final Version1ElectronicDataFormatType APPLICATION_RTX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/rtx");
    // samlassertion+xml
    public static final Version1ElectronicDataFormatType APPLICATION_SAMLASSERTION_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/samlassertion+xml");
    // samlmetadata+xml
    public static final Version1ElectronicDataFormatType APPLICATION_SAMLMETADATA_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/samlmetadata+xml");
    // sbml+xml
    public static final Version1ElectronicDataFormatType APPLICATION_SBML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/sbml+xml");
    // scvp-cv-request
    public static final Version1ElectronicDataFormatType APPLICATION_SCVP_CV_REQUEST
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/scvp-cv-request");
    // scvp-cv-response
    public static final Version1ElectronicDataFormatType APPLICATION_SCVP_CV_RESPONSE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/scvp-cv-response");
    // scvp-vp-request
    public static final Version1ElectronicDataFormatType APPLICATION_SCVP_VP_REQUEST
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/scvp-vp-request");
    // scvp-vp-response
    public static final Version1ElectronicDataFormatType APPLICATION_SCVP_VP_RESPONSE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/scvp-vp-response");
    // sdp
    public static final Version1ElectronicDataFormatType APPLICATION_SDP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/sdp");
    // set-payment
    public static final Version1ElectronicDataFormatType APPLICATION_SET_PAYMENT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/set-payment");
    // set-payment-initiation
    public static final Version1ElectronicDataFormatType APPLICATION_SET_PAYMENT_INITIATION
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/set-payment-initiation");
    // set-registration
    public static final Version1ElectronicDataFormatType APPLICATION_SET_REGISTRATION
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/set-registration");
    // set-registration-initiation
    public static final Version1ElectronicDataFormatType APPLICATION_SET_REGISTRATION_INITIATION
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/set-registration-initiation");
    // sgml
    public static final Version1ElectronicDataFormatType APPLICATION_SGML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/sgml");
    // sgml-open-catalog
    public static final Version1ElectronicDataFormatType APPLICATION_SGML_OPEN_CATALOG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/sgml-open-catalog");
    // shf+xml
    public static final Version1ElectronicDataFormatType APPLICATION_SHF_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/shf+xml");
    // sieve
    public static final Version1ElectronicDataFormatType APPLICATION_SIEVE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/sieve");
    // simple-filter+xml
    public static final Version1ElectronicDataFormatType APPLICATION_SIMPLE_FILTER_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/simple-filter+xml");
    // simple-message-summary
    public static final Version1ElectronicDataFormatType APPLICATION_SIMPLE_MESSAGE_SUMMARY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/simple-message-summary");
    // simpleSymbolContainer
    public static final Version1ElectronicDataFormatType APPLICATION_SIMPLESYMBOLCONTAINER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/simpleSymbolContainer");
    // slate
    public static final Version1ElectronicDataFormatType APPLICATION_SLATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/slate");
    // smil
    public static final Version1ElectronicDataFormatType APPLICATION_SMIL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/smil");
    // smil+xml
    public static final Version1ElectronicDataFormatType APPLICATION_SMIL_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/smil+xml");
    // soap+fastinfoset
    public static final Version1ElectronicDataFormatType APPLICATION_SOAP_FASTINFOSET
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/soap+fastinfoset");
    // soap+xml
    public static final Version1ElectronicDataFormatType APPLICATION_SOAP_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/soap+xml");
    // sparql-query
    public static final Version1ElectronicDataFormatType APPLICATION_SPARQL_QUERY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/sparql-query");
    // sparql-results+xml
    public static final Version1ElectronicDataFormatType APPLICATION_SPARQL_RESULTS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/sparql-results+xml");
    // spirits-event+xml
    public static final Version1ElectronicDataFormatType APPLICATION_SPIRITS_EVENT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/spirits-event+xml");
    // srgs
    public static final Version1ElectronicDataFormatType APPLICATION_SRGS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/srgs");
    // srgs+xml
    public static final Version1ElectronicDataFormatType APPLICATION_SRGS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/srgs+xml");
    // ssml+xml
    public static final Version1ElectronicDataFormatType APPLICATION_SSML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/ssml+xml");
    // tamp-apex-update
    public static final Version1ElectronicDataFormatType APPLICATION_TAMP_APEX_UPDATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/tamp-apex-update");
    // tamp-apex-update-confirm
    public static final Version1ElectronicDataFormatType APPLICATION_TAMP_APEX_UPDATE_CONFIRM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/tamp-apex-update-confirm");
    // tamp-community-update
    public static final Version1ElectronicDataFormatType APPLICATION_TAMP_COMMUNITY_UPDATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/tamp-community-update");
    // tamp-community-update-confirm
    public static final Version1ElectronicDataFormatType APPLICATION_TAMP_COMMUNITY_UPDATE_CONFIRM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/tamp-community-update-confirm");
    // tamp-error
    public static final Version1ElectronicDataFormatType APPLICATION_TAMP_ERROR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/tamp-error");
    // tamp-sequence-adjust
    public static final Version1ElectronicDataFormatType APPLICATION_TAMP_SEQUENCE_ADJUST
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/tamp-sequence-adjust");
    // tamp-sequence-adjust-confirm
    public static final Version1ElectronicDataFormatType APPLICATION_TAMP_SEQUENCE_ADJUST_CONFIRM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/tamp-sequence-adjust-confirm");
    // tamp-status-query
    public static final Version1ElectronicDataFormatType APPLICATION_TAMP_STATUS_QUERY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/tamp-status-query");
    // tamp-status-response
    public static final Version1ElectronicDataFormatType APPLICATION_TAMP_STATUS_RESPONSE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/tamp-status-response");
    // tamp-update
    public static final Version1ElectronicDataFormatType APPLICATION_TAMP_UPDATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/tamp-update");
    // tamp-update-confirm
    public static final Version1ElectronicDataFormatType APPLICATION_TAMP_UPDATE_CONFIRM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/tamp-update-confirm");
    // tei+xml
    public static final Version1ElectronicDataFormatType APPLICATION_TEI_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/tei+xml");
    // thraud+xml
    public static final Version1ElectronicDataFormatType APPLICATION_THRAUD_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/thraud+xml");
    // timestamp-query
    public static final Version1ElectronicDataFormatType APPLICATION_TIMESTAMP_QUERY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/timestamp-query");
    // timestamp-reply
    public static final Version1ElectronicDataFormatType APPLICATION_TIMESTAMP_REPLY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/timestamp-reply");
    // timestamped-data
    public static final Version1ElectronicDataFormatType APPLICATION_TIMESTAMPED_DATA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/timestamped-data");
    // tve-trigger
    public static final Version1ElectronicDataFormatType APPLICATION_TVE_TRIGGER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/tve-trigger");
    // ulpfec
    public static final Version1ElectronicDataFormatType APPLICATION_ULPFEC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/ulpfec");
    // vemmi
    public static final Version1ElectronicDataFormatType APPLICATION_VEMMI
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vemmi");
    // vnd.3gpp.bsf+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_3GPP_BSF_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.3gpp.bsf+xml");
    // vnd.3gpp.pic-bw-large
    public static final Version1ElectronicDataFormatType APPLICATION_VND_3GPP_PIC_BW_LARGE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.3gpp.pic-bw-large");
    // vnd.3gpp.pic-bw-small
    public static final Version1ElectronicDataFormatType APPLICATION_VND_3GPP_PIC_BW_SMALL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.3gpp.pic-bw-small");
    // vnd.3gpp.pic-bw-var
    public static final Version1ElectronicDataFormatType APPLICATION_VND_3GPP_PIC_BW_VAR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.3gpp.pic-bw-var");
    // vnd.3gpp.sms
    public static final Version1ElectronicDataFormatType APPLICATION_VND_3GPP_SMS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.3gpp.sms");
    // vnd.3gpp2.bcmcsinfo+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_3GPP2_BCMCSINFO_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.3gpp2.bcmcsinfo+xml");
    // vnd.3gpp2.sms
    public static final Version1ElectronicDataFormatType APPLICATION_VND_3GPP2_SMS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.3gpp2.sms");
    // vnd.3gpp2.tcap
    public static final Version1ElectronicDataFormatType APPLICATION_VND_3GPP2_TCAP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.3gpp2.tcap");
    // vnd.3M.Post-it-Notes
    public static final Version1ElectronicDataFormatType APPLICATION_VND_3M_POST_IT_NOTES
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.3M.Post-it-Notes");
    // vnd.accpac.simply.aso
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ACCPAC_SIMPLY_ASO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.accpac.simply.aso");
    // vnd.accpac.simply.imp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ACCPAC_SIMPLY_IMP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.accpac.simply.imp");
    // vnd.acucobol
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ACUCOBOL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.acucobol");
    // vnd.acucorp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ACUCORP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.acucorp");
    // vnd.adobe.fxp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ADOBE_FXP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.adobe.fxp");
    // vnd.adobe.partial-upload
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ADOBE_PARTIAL_UPLOAD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.adobe.partial-upload");
    // vnd.adobe.xdp+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ADOBE_XDP_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.adobe.xdp+xml");
    // vnd.adobe.xfdf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ADOBE_XFDF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.adobe.xfdf");
    // vnd.aether.imp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_AETHER_IMP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.aether.imp");
    // vnd.ah-barcode
    public static final Version1ElectronicDataFormatType APPLICATION_VND_AH_BARCODE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ah-barcode");
    // vnd.ahead.space
    public static final Version1ElectronicDataFormatType APPLICATION_VND_AHEAD_SPACE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ahead.space");
    // vnd.airzip.filesecure.azf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_AIRZIP_FILESECURE_AZF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.airzip.filesecure.azf");
    // vnd.airzip.filesecure.azs
    public static final Version1ElectronicDataFormatType APPLICATION_VND_AIRZIP_FILESECURE_AZS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.airzip.filesecure.azs");
    // vnd.americandynamics.acc
    public static final Version1ElectronicDataFormatType APPLICATION_VND_AMERICANDYNAMICS_ACC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.americandynamics.acc");
    // vnd.amiga.ami
    public static final Version1ElectronicDataFormatType APPLICATION_VND_AMIGA_AMI
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.amiga.ami");
    // vnd.anser-web-certificate-issue-initiation
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ANSER_WEB_CERTIFICATE_ISSUE_INITIATION
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.anser-web-certificate-issue-initiation");
    // vnd.antix.game-component
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ANTIX_GAME_COMPONENT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.antix.game-component");
    // vnd.apple.mpegurl
    public static final Version1ElectronicDataFormatType APPLICATION_VND_APPLE_MPEGURL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.apple.mpegurl");
    // vnd.apple.installer+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_APPLE_INSTALLER_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.apple.installer+xml");
    // vnd.arastra.swi
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ARASTRA_SWI
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.arastra.swi");
    // vnd.aristanetworks.swi
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ARISTANETWORKS_SWI
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.aristanetworks.swi");
    // vnd.audiograph
    public static final Version1ElectronicDataFormatType APPLICATION_VND_AUDIOGRAPH
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.audiograph");
    // vnd.autopackage
    public static final Version1ElectronicDataFormatType APPLICATION_VND_AUTOPACKAGE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.autopackage");
    // vnd.avistar+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_AVISTAR_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.avistar+xml");
    // vnd.blueice.multipass
    public static final Version1ElectronicDataFormatType APPLICATION_VND_BLUEICE_MULTIPASS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.blueice.multipass");
    // vnd.bluetooth.ep.oob
    public static final Version1ElectronicDataFormatType APPLICATION_VND_BLUETOOTH_EP_OOB
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.bluetooth.ep.oob");
    // vnd.bmi
    public static final Version1ElectronicDataFormatType APPLICATION_VND_BMI
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.bmi");
    // vnd.businessobjects
    public static final Version1ElectronicDataFormatType APPLICATION_VND_BUSINESSOBJECTS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.businessobjects");
    // vnd.cab-jscript
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CAB_JSCRIPT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.cab-jscript");
    // vnd.canon-cpdl
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CANON_CPDL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.canon-cpdl");
    // vnd.canon-lips
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CANON_LIPS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.canon-lips");
    // vnd.cendio.thinlinc.clientconf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CENDIO_THINLINC_CLIENTCONF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.cendio.thinlinc.clientconf");
    // vnd.chemdraw+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CHEMDRAW_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.chemdraw+xml");
    // vnd.chipnuts.karaoke-mmd
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CHIPNUTS_KARAOKE_MMD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.chipnuts.karaoke-mmd");
    // vnd.cinderella
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CINDERELLA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.cinderella");
    // vnd.cirpack.isdn-ext
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CIRPACK_ISDN_EXT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.cirpack.isdn-ext");
    // vnd.claymore
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CLAYMORE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.claymore");
    // vnd.cloanto.rp9
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CLOANTO_RP9
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.cloanto.rp9");
    // vnd.clonk.c4group
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CLONK_C4GROUP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.clonk.c4group");
    // vnd.cluetrust.cartomobile-config
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CLUETRUST_CARTOMOBILE_CONFIG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.cluetrust.cartomobile-config");
    // vnd.cluetrust.cartomobile-config-pkg
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CLUETRUST_CARTOMOBILE_CONFIG_PKG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.cluetrust.cartomobile-config-pkg");
    // vnd.commerce-battelle
    public static final Version1ElectronicDataFormatType APPLICATION_VND_COMMERCE_BATTELLE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.commerce-battelle");
    // vnd.commonspace
    public static final Version1ElectronicDataFormatType APPLICATION_VND_COMMONSPACE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.commonspace");
    // vnd.cosmocaller
    public static final Version1ElectronicDataFormatType APPLICATION_VND_COSMOCALLER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.cosmocaller");
    // vnd.contact.cmsg
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CONTACT_CMSG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.contact.cmsg");
    // vnd.crick.clicker
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CRICK_CLICKER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.crick.clicker");
    // vnd.crick.clicker.keyboard
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CRICK_CLICKER_KEYBOARD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.crick.clicker.keyboard");
    // vnd.crick.clicker.palette
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CRICK_CLICKER_PALETTE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.crick.clicker.palette");
    // vnd.crick.clicker.template
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CRICK_CLICKER_TEMPLATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.crick.clicker.template");
    // vnd.crick.clicker.wordbank
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CRICK_CLICKER_WORDBANK
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.crick.clicker.wordbank");
    // vnd.criticaltools.wbs+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CRITICALTOOLS_WBS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.criticaltools.wbs+xml");
    // vnd.ctc-posml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CTC_POSML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ctc-posml");
    // vnd.ctct.ws+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CTCT_WS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ctct.ws+xml");
    // vnd.cups-pdf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CUPS_PDF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.cups-pdf");
    // vnd.cups-postscript
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CUPS_POSTSCRIPT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.cups-postscript");
    // vnd.cups-ppd
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CUPS_PPD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.cups-ppd");
    // vnd.cups-raster
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CUPS_RASTER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.cups-raster");
    // vnd.cups-raw
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CUPS_RAW
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.cups-raw");
    // vnd.curl
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CURL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.curl");
    // vnd.cybank
    public static final Version1ElectronicDataFormatType APPLICATION_VND_CYBANK
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.cybank");
    // vnd.data-vision.rdz
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DATA_VISION_RDZ
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.data-vision.rdz");
    // vnd.dece.data
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DECE_DATA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dece.data");
    // vnd.dece.ttml+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DECE_TTML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dece.ttml+xml");
    // vnd.dece.unspecified
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DECE_UNSPECIFIED
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dece.unspecified");
    // vnd.denovo.fcselayout-link
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DENOVO_FCSELAYOUT_LINK
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.denovo.fcselayout-link");
    // vnd.dir-bi.plate-dl-nosuffix
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DIR_BI_PLATE_DL_NOSUFFIX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dir-bi.plate-dl-nosuffix");
    // vnd.dna
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DNA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dna");
    // vnd.dolby.mobile.1
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DOLBY_MOBILE_1
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dolby.mobile.1");
    // vnd.dolby.mobile.2
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DOLBY_MOBILE_2
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dolby.mobile.2");
    // vnd.dpgraph
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DPGRAPH
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dpgraph");
    // vnd.dreamfactory
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DREAMFACTORY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dreamfactory");
    // vnd.dvb.esgcontainer
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DVB_ESGCONTAINER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dvb.esgcontainer");
    // vnd.dvb.ipdcdftnotifaccess
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DVB_IPDCDFTNOTIFACCESS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dvb.ipdcdftnotifaccess");
    // vnd.dvb.ipdcesgaccess
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DVB_IPDCESGACCESS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dvb.ipdcesgaccess");
    // vnd.dvb.ipdcesgaccess2
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DVB_IPDCESGACCESS2
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dvb.ipdcesgaccess2");
    // vnd.dvb.ipdcesgpdd
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DVB_IPDCESGPDD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dvb.ipdcesgpdd");
    // vnd.dvb.ipdcroaming
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DVB_IPDCROAMING
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dvb.ipdcroaming");
    // vnd.dvb.iptv.alfec-base
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DVB_IPTV_ALFEC_BASE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dvb.iptv.alfec-base");
    // vnd.dvb.iptv.alfec-enhancement
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DVB_IPTV_ALFEC_ENHANCEMENT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dvb.iptv.alfec-enhancement");
    // vnd.dvb.notif-aggregate-root+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DVB_NOTIF_AGGREGATE_ROOT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dvb.notif-aggregate-root+xml");
    // vnd.dvb.notif-container+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DVB_NOTIF_CONTAINER_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dvb.notif-container+xml");
    // vnd.dvb.notif-generic+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DVB_NOTIF_GENERIC_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dvb.notif-generic+xml");
    // vnd.dvb.notif-ia-msglist+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DVB_NOTIF_IA_MSGLIST_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dvb.notif-ia-msglist+xml");
    // vnd.dvb.notif-ia-registration-request+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DVB_NOTIF_IA_REGISTRATION_REQUEST_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dvb.notif-ia-registration-request+xml");
    // vnd.dvb.notif-ia-registration-response+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DVB_NOTIF_IA_REGISTRATION_RESPONSE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dvb.notif-ia-registration-response+xml");
    // vnd.dvb.notif-init+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DVB_NOTIF_INIT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dvb.notif-init+xml");
    // vnd.dxr
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DXR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dxr");
    // vnd.dynageo
    public static final Version1ElectronicDataFormatType APPLICATION_VND_DYNAGEO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.dynageo");
    // vnd.easykaraoke.cdgdownload
    public static final Version1ElectronicDataFormatType APPLICATION_VND_EASYKARAOKE_CDGDOWNLOAD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.easykaraoke.cdgdownload");
    // vnd.ecdis-update
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ECDIS_UPDATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ecdis-update");
    // vnd.ecowin.chart
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ECOWIN_CHART
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ecowin.chart");
    // vnd.ecowin.filerequest
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ECOWIN_FILEREQUEST
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ecowin.filerequest");
    // vnd.ecowin.fileupdate
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ECOWIN_FILEUPDATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ecowin.fileupdate");
    // vnd.ecowin.series
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ECOWIN_SERIES
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ecowin.series");
    // vnd.ecowin.seriesrequest
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ECOWIN_SERIESREQUEST
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ecowin.seriesrequest");
    // vnd.ecowin.seriesupdate
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ECOWIN_SERIESUPDATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ecowin.seriesupdate");
    // vnd.emclient.accessrequest+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_EMCLIENT_ACCESSREQUEST_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.emclient.accessrequest+xml");
    // vnd.enliven
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ENLIVEN
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.enliven");
    // vnd.epson.esf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_EPSON_ESF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.epson.esf");
    // vnd.epson.msf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_EPSON_MSF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.epson.msf");
    // vnd.epson.quickanime
    public static final Version1ElectronicDataFormatType APPLICATION_VND_EPSON_QUICKANIME
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.epson.quickanime");
    // vnd.epson.salt
    public static final Version1ElectronicDataFormatType APPLICATION_VND_EPSON_SALT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.epson.salt");
    // vnd.epson.ssf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_EPSON_SSF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.epson.ssf");
    // vnd.ericsson.quickcall
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ERICSSON_QUICKCALL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ericsson.quickcall");
    // vnd.eszigno3+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ESZIGNO3_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.eszigno3+xml");
    // vnd.etsi.aoc+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ETSI_AOC_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.etsi.aoc+xml");
    // vnd.etsi.cug+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ETSI_CUG_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.etsi.cug+xml");
    // vnd.etsi.iptvcommand+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ETSI_IPTVCOMMAND_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.etsi.iptvcommand+xml");
    // vnd.etsi.iptvdiscovery+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ETSI_IPTVDISCOVERY_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.etsi.iptvdiscovery+xml");
    // vnd.etsi.iptvprofile+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ETSI_IPTVPROFILE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.etsi.iptvprofile+xml");
    // vnd.etsi.iptvsad-bc+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ETSI_IPTVSAD_BC_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.etsi.iptvsad-bc+xml");
    // vnd.etsi.iptvsad-cod+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ETSI_IPTVSAD_COD_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.etsi.iptvsad-cod+xml");
    // vnd.etsi.iptvsad-npvr+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ETSI_IPTVSAD_NPVR_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.etsi.iptvsad-npvr+xml");
    // vnd.etsi.iptvueprofile+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ETSI_IPTVUEPROFILE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.etsi.iptvueprofile+xml");
    // vnd.etsi.mcid+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ETSI_MCID_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.etsi.mcid+xml");
    // vnd.etsi.overload-control-policy-dataset+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ETSI_OVERLOAD_CONTROL_POLICY_DATASET_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.etsi.overload-control-policy-dataset+xml");
    // vnd.etsi.sci+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ETSI_SCI_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.etsi.sci+xml");
    // vnd.etsi.simservs+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ETSI_SIMSERVS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.etsi.simservs+xml");
    // vnd.etsi.tsl+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ETSI_TSL_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.etsi.tsl+xml");
    // vnd.etsi.tsl.der
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ETSI_TSL_DER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.etsi.tsl.der");
    // vnd.eudora.data
    public static final Version1ElectronicDataFormatType APPLICATION_VND_EUDORA_DATA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.eudora.data");
    // vnd.ezpix-album
    public static final Version1ElectronicDataFormatType APPLICATION_VND_EZPIX_ALBUM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ezpix-album");
    // vnd.ezpix-package
    public static final Version1ElectronicDataFormatType APPLICATION_VND_EZPIX_PACKAGE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ezpix-package");
    // vnd.f-secure.mobile
    public static final Version1ElectronicDataFormatType APPLICATION_VND_F_SECURE_MOBILE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.f-secure.mobile");
    // vnd.fdf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FDF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fdf");
    // vnd.fdsn.mseed
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FDSN_MSEED
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fdsn.mseed");
    // vnd.fdsn.seed
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FDSN_SEED
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fdsn.seed");
    // vnd.ffsns
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FFSNS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ffsns");
    // vnd.fints
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FINTS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fints");
    // vnd.FloGraphIt
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FLOGRAPHIT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.FloGraphIt");
    // vnd.fluxtime.clip
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FLUXTIME_CLIP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fluxtime.clip");
    // vnd.font-fontforge-sfd
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FONT_FONTFORGE_SFD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.font-fontforge-sfd");
    // vnd.framemaker
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FRAMEMAKER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.framemaker");
    // vnd.frogans.fnc
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FROGANS_FNC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.frogans.fnc");
    // vnd.frogans.ltf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FROGANS_LTF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.frogans.ltf");
    // vnd.fsc.weblaunch
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FSC_WEBLAUNCH
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fsc.weblaunch");
    // vnd.fujitsu.oasys
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FUJITSU_OASYS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fujitsu.oasys");
    // vnd.fujitsu.oasys2
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FUJITSU_OASYS2
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fujitsu.oasys2");
    // vnd.fujitsu.oasys3
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FUJITSU_OASYS3
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fujitsu.oasys3");
    // vnd.fujitsu.oasysgp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FUJITSU_OASYSGP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fujitsu.oasysgp");
    // vnd.fujitsu.oasysprs
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FUJITSU_OASYSPRS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fujitsu.oasysprs");
    // vnd.fujixerox.ART4
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FUJIXEROX_ART4
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fujixerox.ART4");
    // vnd.fujixerox.ART-EX
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FUJIXEROX_ART_EX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fujixerox.ART-EX");
    // vnd.fujixerox.ddd
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FUJIXEROX_DDD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fujixerox.ddd");
    // vnd.fujixerox.docuworks
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FUJIXEROX_DOCUWORKS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fujixerox.docuworks");
    // vnd.fujixerox.docuworks.binder
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FUJIXEROX_DOCUWORKS_BINDER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fujixerox.docuworks.binder");
    // vnd.fujixerox.HBPL
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FUJIXEROX_HBPL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fujixerox.HBPL");
    // vnd.fut-misnet
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FUT_MISNET
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fut-misnet");
    // vnd.fuzzysheet
    public static final Version1ElectronicDataFormatType APPLICATION_VND_FUZZYSHEET
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.fuzzysheet");
    // vnd.genomatix.tuxedo
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GENOMATIX_TUXEDO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.genomatix.tuxedo");
    // vnd.geocube+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GEOCUBE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.geocube+xml");
    // vnd.geogebra.file
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GEOGEBRA_FILE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.geogebra.file");
    // vnd.geogebra.tool
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GEOGEBRA_TOOL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.geogebra.tool");
    // vnd.geometry-explorer
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GEOMETRY_EXPLORER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.geometry-explorer");
    // vnd.geonext
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GEONEXT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.geonext");
    // vnd.geoplan
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GEOPLAN
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.geoplan");
    // vnd.geospace
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GEOSPACE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.geospace");
    // vnd.globalplatform.card-content-mgt
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GLOBALPLATFORM_CARD_CONTENT_MGT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.globalplatform.card-content-mgt");
    // vnd.globalplatform.card-content-mgt-response
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GLOBALPLATFORM_CARD_CONTENT_MGT_RESPONSE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.globalplatform.card-content-mgt-response");
    // vnd.gmx
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GMX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.gmx");
    // vnd.google-earth.kml+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GOOGLE_EARTH_KML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.google-earth.kml+xml");
    // vnd.google-earth.kmz
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GOOGLE_EARTH_KMZ
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.google-earth.kmz");
    // vnd.grafeq
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GRAFEQ
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.grafeq");
    // vnd.gridmp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GRIDMP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.gridmp");
    // vnd.groove-account
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GROOVE_ACCOUNT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.groove-account");
    // vnd.groove-help
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GROOVE_HELP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.groove-help");
    // vnd.groove-identity-message
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GROOVE_IDENTITY_MESSAGE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.groove-identity-message");
    // vnd.groove-injector
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GROOVE_INJECTOR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.groove-injector");
    // vnd.groove-tool-message
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GROOVE_TOOL_MESSAGE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.groove-tool-message");
    // vnd.groove-tool-template
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GROOVE_TOOL_TEMPLATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.groove-tool-template");
    // vnd.groove-vcard
    public static final Version1ElectronicDataFormatType APPLICATION_VND_GROOVE_VCARD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.groove-vcard");
    // vnd.hal+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_HAL_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.hal+xml");
    // vnd.HandHeld-Entertainment+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_HANDHELD_ENTERTAINMENT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.HandHeld-Entertainment+xml");
    // vnd.hbci
    public static final Version1ElectronicDataFormatType APPLICATION_VND_HBCI
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.hbci");
    // vnd.hcl-bireports
    public static final Version1ElectronicDataFormatType APPLICATION_VND_HCL_BIREPORTS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.hcl-bireports");
    // vnd.hhe.lesson-player
    public static final Version1ElectronicDataFormatType APPLICATION_VND_HHE_LESSON_PLAYER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.hhe.lesson-player");
    // vnd.hp-HPGL
    public static final Version1ElectronicDataFormatType APPLICATION_VND_HP_HPGL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.hp-HPGL");
    // vnd.hp-hpid
    public static final Version1ElectronicDataFormatType APPLICATION_VND_HP_HPID
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.hp-hpid");
    // vnd.hp-hps
    public static final Version1ElectronicDataFormatType APPLICATION_VND_HP_HPS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.hp-hps");
    // vnd.hp-jlyt
    public static final Version1ElectronicDataFormatType APPLICATION_VND_HP_JLYT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.hp-jlyt");
    // vnd.hp-PCL
    public static final Version1ElectronicDataFormatType APPLICATION_VND_HP_PCL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.hp-PCL");
    // vnd.hp-PCLXL
    public static final Version1ElectronicDataFormatType APPLICATION_VND_HP_PCLXL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.hp-PCLXL");
    // vnd.httphone
    public static final Version1ElectronicDataFormatType APPLICATION_VND_HTTPHONE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.httphone");
    // vnd.hydrostatix.sof-data
    public static final Version1ElectronicDataFormatType APPLICATION_VND_HYDROSTATIX_SOF_DATA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.hydrostatix.sof-data");
    // vnd.hzn-3d-crossword
    public static final Version1ElectronicDataFormatType APPLICATION_VND_HZN_3D_CROSSWORD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.hzn-3d-crossword");
    // vnd.ibm.afplinedata
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IBM_AFPLINEDATA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ibm.afplinedata");
    // vnd.ibm.electronic-media
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IBM_ELECTRONIC_MEDIA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ibm.electronic-media");
    // vnd.ibm.MiniPay
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IBM_MINIPAY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ibm.MiniPay");
    // vnd.ibm.modcap
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IBM_MODCAP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ibm.modcap");
    // vnd.ibm.rights-management
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IBM_RIGHTS_MANAGEMENT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ibm.rights-management");
    // vnd.ibm.secure-container
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IBM_SECURE_CONTAINER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ibm.secure-container");
    // vnd.iccprofile
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ICCPROFILE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.iccprofile");
    // vnd.igloader
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IGLOADER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.igloader");
    // vnd.immervision-ivp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IMMERVISION_IVP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.immervision-ivp");
    // vnd.immervision-ivu
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IMMERVISION_IVU
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.immervision-ivu");
    // vnd.informedcontrol.rms+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_INFORMEDCONTROL_RMS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.informedcontrol.rms+xml");
    // vnd.infotech.project
    public static final Version1ElectronicDataFormatType APPLICATION_VND_INFOTECH_PROJECT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.infotech.project");
    // vnd.infotech.project+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_INFOTECH_PROJECT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.infotech.project+xml");
    // vnd.informix-visionary
    public static final Version1ElectronicDataFormatType APPLICATION_VND_INFORMIX_VISIONARY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.informix-visionary");
    // vnd.insors.igm
    public static final Version1ElectronicDataFormatType APPLICATION_VND_INSORS_IGM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.insors.igm");
    // vnd.intercon.formnet
    public static final Version1ElectronicDataFormatType APPLICATION_VND_INTERCON_FORMNET
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.intercon.formnet");
    // vnd.intergeo
    public static final Version1ElectronicDataFormatType APPLICATION_VND_INTERGEO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.intergeo");
    // vnd.intertrust.digibox
    public static final Version1ElectronicDataFormatType APPLICATION_VND_INTERTRUST_DIGIBOX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.intertrust.digibox");
    // vnd.intertrust.nncp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_INTERTRUST_NNCP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.intertrust.nncp");
    // vnd.intu.qbo
    public static final Version1ElectronicDataFormatType APPLICATION_VND_INTU_QBO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.intu.qbo");
    // vnd.intu.qfx
    public static final Version1ElectronicDataFormatType APPLICATION_VND_INTU_QFX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.intu.qfx");
    // vnd.iptc.g2.conceptitem+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IPTC_G2_CONCEPTITEM_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.iptc.g2.conceptitem+xml");
    // vnd.iptc.g2.knowledgeitem+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IPTC_G2_KNOWLEDGEITEM_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.iptc.g2.knowledgeitem+xml");
    // vnd.iptc.g2.newsitem+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IPTC_G2_NEWSITEM_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.iptc.g2.newsitem+xml");
    // vnd.iptc.g2.packageitem+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IPTC_G2_PACKAGEITEM_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.iptc.g2.packageitem+xml");
    // vnd.ipunplugged.rcprofile
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IPUNPLUGGED_RCPROFILE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ipunplugged.rcprofile");
    // vnd.irepository.package+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IREPOSITORY_PACKAGE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.irepository.package+xml");
    // vnd.is-xpr
    public static final Version1ElectronicDataFormatType APPLICATION_VND_IS_XPR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.is-xpr");
    // vnd.isac.fcs
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ISAC_FCS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.isac.fcs");
    // vnd.jam
    public static final Version1ElectronicDataFormatType APPLICATION_VND_JAM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.jam");
    // vnd.japannet-directory-service
    public static final Version1ElectronicDataFormatType APPLICATION_VND_JAPANNET_DIRECTORY_SERVICE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.japannet-directory-service");
    // vnd.japannet-jpnstore-wakeup
    public static final Version1ElectronicDataFormatType APPLICATION_VND_JAPANNET_JPNSTORE_WAKEUP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.japannet-jpnstore-wakeup");
    // vnd.japannet-payment-wakeup
    public static final Version1ElectronicDataFormatType APPLICATION_VND_JAPANNET_PAYMENT_WAKEUP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.japannet-payment-wakeup");
    // vnd.japannet-registration
    public static final Version1ElectronicDataFormatType APPLICATION_VND_JAPANNET_REGISTRATION
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.japannet-registration");
    // vnd.japannet-registration-wakeup
    public static final Version1ElectronicDataFormatType APPLICATION_VND_JAPANNET_REGISTRATION_WAKEUP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.japannet-registration-wakeup");
    // vnd.japannet-setstore-wakeup
    public static final Version1ElectronicDataFormatType APPLICATION_VND_JAPANNET_SETSTORE_WAKEUP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.japannet-setstore-wakeup");
    // vnd.japannet-verification
    public static final Version1ElectronicDataFormatType APPLICATION_VND_JAPANNET_VERIFICATION
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.japannet-verification");
    // vnd.japannet-verification-wakeup
    public static final Version1ElectronicDataFormatType APPLICATION_VND_JAPANNET_VERIFICATION_WAKEUP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.japannet-verification-wakeup");
    // vnd.jcp.javame.midlet-rms
    public static final Version1ElectronicDataFormatType APPLICATION_VND_JCP_JAVAME_MIDLET_RMS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.jcp.javame.midlet-rms");
    // vnd.jisp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_JISP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.jisp");
    // vnd.joost.joda-archive
    public static final Version1ElectronicDataFormatType APPLICATION_VND_JOOST_JODA_ARCHIVE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.joost.joda-archive");
    // vnd.kahootz
    public static final Version1ElectronicDataFormatType APPLICATION_VND_KAHOOTZ
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.kahootz");
    // vnd.kde.karbon
    public static final Version1ElectronicDataFormatType APPLICATION_VND_KDE_KARBON
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.kde.karbon");
    // vnd.kde.kchart
    public static final Version1ElectronicDataFormatType APPLICATION_VND_KDE_KCHART
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.kde.kchart");
    // vnd.kde.kformula
    public static final Version1ElectronicDataFormatType APPLICATION_VND_KDE_KFORMULA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.kde.kformula");
    // vnd.kde.kivio
    public static final Version1ElectronicDataFormatType APPLICATION_VND_KDE_KIVIO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.kde.kivio");
    // vnd.kde.kontour
    public static final Version1ElectronicDataFormatType APPLICATION_VND_KDE_KONTOUR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.kde.kontour");
    // vnd.kde.kpresenter
    public static final Version1ElectronicDataFormatType APPLICATION_VND_KDE_KPRESENTER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.kde.kpresenter");
    // vnd.kde.kspread
    public static final Version1ElectronicDataFormatType APPLICATION_VND_KDE_KSPREAD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.kde.kspread");
    // vnd.kde.kword
    public static final Version1ElectronicDataFormatType APPLICATION_VND_KDE_KWORD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.kde.kword");
    // vnd.kenameaapp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_KENAMEAAPP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.kenameaapp");
    // vnd.kidspiration
    public static final Version1ElectronicDataFormatType APPLICATION_VND_KIDSPIRATION
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.kidspiration");
    // vnd.Kinar
    public static final Version1ElectronicDataFormatType APPLICATION_VND_KINAR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.Kinar");
    // vnd.koan
    public static final Version1ElectronicDataFormatType APPLICATION_VND_KOAN
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.koan");
    // vnd.kodak-descriptor
    public static final Version1ElectronicDataFormatType APPLICATION_VND_KODAK_DESCRIPTOR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.kodak-descriptor");
    // vnd.las.las+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_LAS_LAS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.las.las+xml");
    // vnd.liberty-request+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_LIBERTY_REQUEST_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.liberty-request+xml");
    // vnd.llamagraphics.life-balance.desktop
    public static final Version1ElectronicDataFormatType APPLICATION_VND_LLAMAGRAPHICS_LIFE_BALANCE_DESKTOP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.llamagraphics.life-balance.desktop");
    // vnd.llamagraphics.life-balance.exchange+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_LLAMAGRAPHICS_LIFE_BALANCE_EXCHANGE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.llamagraphics.life-balance.exchange+xml");
    // vnd.lotus-1-2-3
    public static final Version1ElectronicDataFormatType APPLICATION_VND_LOTUS_1_2_3
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.lotus-1-2-3");
    // vnd.lotus-approach
    public static final Version1ElectronicDataFormatType APPLICATION_VND_LOTUS_APPROACH
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.lotus-approach");
    // vnd.lotus-freelance
    public static final Version1ElectronicDataFormatType APPLICATION_VND_LOTUS_FREELANCE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.lotus-freelance");
    // vnd.lotus-notes
    public static final Version1ElectronicDataFormatType APPLICATION_VND_LOTUS_NOTES
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.lotus-notes");
    // vnd.lotus-organizer
    public static final Version1ElectronicDataFormatType APPLICATION_VND_LOTUS_ORGANIZER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.lotus-organizer");
    // vnd.lotus-screencam
    public static final Version1ElectronicDataFormatType APPLICATION_VND_LOTUS_SCREENCAM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.lotus-screencam");
    // vnd.lotus-wordpro
    public static final Version1ElectronicDataFormatType APPLICATION_VND_LOTUS_WORDPRO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.lotus-wordpro");
    // vnd.macports.portpkg
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MACPORTS_PORTPKG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.macports.portpkg");
    // vnd.marlin.drm.actiontoken+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MARLIN_DRM_ACTIONTOKEN_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.marlin.drm.actiontoken+xml");
    // vnd.marlin.drm.conftoken+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MARLIN_DRM_CONFTOKEN_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.marlin.drm.conftoken+xml");
    // vnd.marlin.drm.license+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MARLIN_DRM_LICENSE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.marlin.drm.license+xml");
    // vnd.marlin.drm.mdcf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MARLIN_DRM_MDCF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.marlin.drm.mdcf");
    // vnd.mcd
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MCD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.mcd");
    // vnd.medcalcdata
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MEDCALCDATA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.medcalcdata");
    // vnd.mediastation.cdkey
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MEDIASTATION_CDKEY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.mediastation.cdkey");
    // vnd.meridian-slingshot
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MERIDIAN_SLINGSHOT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.meridian-slingshot");
    // vnd.MFER
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MFER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.MFER");
    // vnd.mfmp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MFMP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.mfmp");
    // vnd.micrografx.flo
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MICROGRAFX_FLO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.micrografx.flo");
    // vnd.micrografx.igx
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MICROGRAFX_IGX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.micrografx.igx");
    // vnd.mif
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MIF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.mif");
    // vnd.minisoft-hp3000-save
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MINISOFT_HP3000_SAVE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.minisoft-hp3000-save");
    // vnd.mitsubishi.misty-guard.trustweb
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MITSUBISHI_MISTY_GUARD_TRUSTWEB
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.mitsubishi.misty-guard.trustweb");
    // vnd.Mobius.DAF
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOBIUS_DAF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.Mobius.DAF");
    // vnd.Mobius.DIS
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOBIUS_DIS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.Mobius.DIS");
    // vnd.Mobius.MBK
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOBIUS_MBK
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.Mobius.MBK");
    // vnd.Mobius.MQY
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOBIUS_MQY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.Mobius.MQY");
    // vnd.Mobius.MSL
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOBIUS_MSL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.Mobius.MSL");
    // vnd.Mobius.PLC
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOBIUS_PLC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.Mobius.PLC");
    // vnd.Mobius.TXF
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOBIUS_TXF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.Mobius.TXF");
    // vnd.mophun.application
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOPHUN_APPLICATION
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.mophun.application");
    // vnd.mophun.certificate
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOPHUN_CERTIFICATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.mophun.certificate");
    // vnd.motorola.flexsuite
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOTOROLA_FLEXSUITE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.motorola.flexsuite");
    // vnd.motorola.flexsuite.adsi
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOTOROLA_FLEXSUITE_ADSI
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.motorola.flexsuite.adsi");
    // vnd.motorola.flexsuite.fis
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOTOROLA_FLEXSUITE_FIS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.motorola.flexsuite.fis");
    // vnd.motorola.flexsuite.gotap
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOTOROLA_FLEXSUITE_GOTAP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.motorola.flexsuite.gotap");
    // vnd.motorola.flexsuite.kmr
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOTOROLA_FLEXSUITE_KMR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.motorola.flexsuite.kmr");
    // vnd.motorola.flexsuite.ttc
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOTOROLA_FLEXSUITE_TTC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.motorola.flexsuite.ttc");
    // vnd.motorola.flexsuite.wem
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOTOROLA_FLEXSUITE_WEM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.motorola.flexsuite.wem");
    // vnd.motorola.iprm
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOTOROLA_IPRM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.motorola.iprm");
    // vnd.mozilla.xul+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MOZILLA_XUL_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.mozilla.xul+xml");
    // vnd.ms-artgalry
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_ARTGALRY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-artgalry");
    // vnd.ms-asf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_ASF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-asf");
    // vnd.ms-cab-compressed
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_CAB_COMPRESSED
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-cab-compressed");
    // vnd.mseq
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MSEQ
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.mseq");
    // vnd.ms-excel
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_EXCEL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-excel");
    // vnd.ms-fontobject
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_FONTOBJECT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-fontobject");
    // vnd.ms-htmlhelp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_HTMLHELP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-htmlhelp");
    // vnd.msign
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MSIGN
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.msign");
    // vnd.ms-ims
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_IMS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-ims");
    // vnd.ms-lrm
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_LRM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-lrm");
    // vnd.ms-playready.initiator+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_PLAYREADY_INITIATOR_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-playready.initiator+xml");
    // vnd.ms-powerpoint
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_POWERPOINT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-powerpoint");
    // vnd.ms-project
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_PROJECT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-project");
    // vnd.ms-tnef
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_TNEF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-tnef");
    // vnd.ms-wmdrm.lic-chlg-req
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_WMDRM_LIC_CHLG_REQ
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-wmdrm.lic-chlg-req");
    // vnd.ms-wmdrm.lic-resp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_WMDRM_LIC_RESP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-wmdrm.lic-resp");
    // vnd.ms-wmdrm.meter-chlg-req
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_WMDRM_METER_CHLG_REQ
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-wmdrm.meter-chlg-req");
    // vnd.ms-wmdrm.meter-resp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_WMDRM_METER_RESP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-wmdrm.meter-resp");
    // vnd.ms-works
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_WORKS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-works");
    // vnd.ms-wpl
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_WPL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-wpl");
    // vnd.ms-xpsdocument
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MS_XPSDOCUMENT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ms-xpsdocument");
    // vnd.multiad.creator
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MULTIAD_CREATOR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.multiad.creator");
    // vnd.multiad.creator.cif
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MULTIAD_CREATOR_CIF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.multiad.creator.cif");
    // vnd.musician
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MUSICIAN
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.musician");
    // vnd.music-niff
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MUSIC_NIFF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.music-niff");
    // vnd.muvee.style
    public static final Version1ElectronicDataFormatType APPLICATION_VND_MUVEE_STYLE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.muvee.style");
    // vnd.ncd.control
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NCD_CONTROL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ncd.control");
    // vnd.ncd.reference
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NCD_REFERENCE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ncd.reference");
    // vnd.nervana
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NERVANA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nervana");
    // vnd.netfpx
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NETFPX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.netfpx");
    // vnd.neurolanguage.nlu
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NEUROLANGUAGE_NLU
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.neurolanguage.nlu");
    // vnd.noblenet-directory
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOBLENET_DIRECTORY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.noblenet-directory");
    // vnd.noblenet-sealer
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOBLENET_SEALER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.noblenet-sealer");
    // vnd.noblenet-web
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOBLENET_WEB
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.noblenet-web");
    // vnd.nokia.catalogs
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_CATALOGS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.catalogs");
    // vnd.nokia.conml+wbxml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_CONML_WBXML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.conml+wbxml");
    // vnd.nokia.conml+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_CONML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.conml+xml");
    // vnd.nokia.iptv.config+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_IPTV_CONFIG_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.iptv.config+xml");
    // vnd.nokia.iSDS-radio-presets
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_ISDS_RADIO_PRESETS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.iSDS-radio-presets");
    // vnd.nokia.landmark+wbxml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_LANDMARK_WBXML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.landmark+wbxml");
    // vnd.nokia.landmark+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_LANDMARK_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.landmark+xml");
    // vnd.nokia.landmarkcollection+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_LANDMARKCOLLECTION_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.landmarkcollection+xml");
    // vnd.nokia.ncd
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_NCD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.ncd");
    // vnd.nokia.n-gage.ac+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_N_GAGE_AC_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.n-gage.ac+xml");
    // vnd.nokia.n-gage.data
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_N_GAGE_DATA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.n-gage.data");
    // vnd.nokia.n-gage.symbian.install
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_N_GAGE_SYMBIAN_INSTALL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.n-gage.symbian.install");
    // vnd.nokia.pcd+wbxml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_PCD_WBXML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.pcd+wbxml");
    // vnd.nokia.pcd+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_PCD_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.pcd+xml");
    // vnd.nokia.radio-preset
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_RADIO_PRESET
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.radio-preset");
    // vnd.nokia.radio-presets
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOKIA_RADIO_PRESETS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.nokia.radio-presets");
    // vnd.novadigm.EDM
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOVADIGM_EDM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.novadigm.EDM");
    // vnd.novadigm.EDX
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOVADIGM_EDX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.novadigm.EDX");
    // vnd.novadigm.EXT
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NOVADIGM_EXT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.novadigm.EXT");
    // vnd.ntt-local.file-transfer
    public static final Version1ElectronicDataFormatType APPLICATION_VND_NTT_LOCAL_FILE_TRANSFER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ntt-local.file-transfer");
    // vnd.oasis.opendocument.chart
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_CHART
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.chart");
    // vnd.oasis.opendocument.chart-template
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_CHART_TEMPLATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.chart-template");
    // vnd.oasis.opendocument.database
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_DATABASE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.database");
    // vnd.oasis.opendocument.formula
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_FORMULA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.formula");
    // vnd.oasis.opendocument.formula-template
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_FORMULA_TEMPLATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.formula-template");
    // vnd.oasis.opendocument.graphics
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_GRAPHICS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.graphics");
    // vnd.oasis.opendocument.graphics-template
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_GRAPHICS_TEMPLATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.graphics-template");
    // vnd.oasis.opendocument.image
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_IMAGE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.image");
    // vnd.oasis.opendocument.image-template
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_IMAGE_TEMPLATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.image-template");
    // vnd.oasis.opendocument.presentation
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_PRESENTATION
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.presentation");
    // vnd.oasis.opendocument.presentation-template
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_PRESENTATION_TEMPLATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.presentation-template");
    // vnd.oasis.opendocument.spreadsheet
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.spreadsheet");
    // vnd.oasis.opendocument.spreadsheet-template
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET_TEMPLATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.spreadsheet-template");
    // vnd.oasis.opendocument.text
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.text");
    // vnd.oasis.opendocument.text-master
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT_MASTER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.text-master");
    // vnd.oasis.opendocument.text-template
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT_TEMPLATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.text-template");
    // vnd.oasis.opendocument.text-web
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT_WEB
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oasis.opendocument.text-web");
    // vnd.obn
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OBN
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.obn");
    // vnd.oipf.contentaccessdownload+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OIPF_CONTENTACCESSDOWNLOAD_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oipf.contentaccessdownload+xml");
    // vnd.oipf.contentaccessstreaming+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OIPF_CONTENTACCESSSTREAMING_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oipf.contentaccessstreaming+xml");
    // vnd.oipf.cspg-hexbinary
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OIPF_CSPG_HEXBINARY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oipf.cspg-hexbinary");
    // vnd.oipf.mippvcontrolmessage+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OIPF_MIPPVCONTROLMESSAGE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oipf.mippvcontrolmessage+xml");
    // vnd.oipf.spdiscovery+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OIPF_SPDISCOVERY_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oipf.spdiscovery+xml");
    // vnd.oipf.spdlist+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OIPF_SPDLIST_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oipf.spdlist+xml");
    // vnd.oipf.ueprofile+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OIPF_UEPROFILE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oipf.ueprofile+xml");
    // vnd.oipf.userprofile+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OIPF_USERPROFILE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oipf.userprofile+xml");
    // vnd.olpc-sugar
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OLPC_SUGAR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.olpc-sugar");
    // vnd.oma.bcast.associated-procedure-parameter+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_BCAST_ASSOCIATED_PROCEDURE_PARAMETER_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.bcast.associated-procedure-parameter+xml");
    // vnd.oma.bcast.drm-trigger+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_BCAST_DRM_TRIGGER_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.bcast.drm-trigger+xml");
    // vnd.oma.bcast.imd+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_BCAST_IMD_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.bcast.imd+xml");
    // vnd.oma.bcast.ltkm
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_BCAST_LTKM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.bcast.ltkm");
    // vnd.oma.bcast.notification+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_BCAST_NOTIFICATION_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.bcast.notification+xml");
    // vnd.oma.bcast.provisioningtrigger
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_BCAST_PROVISIONINGTRIGGER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.bcast.provisioningtrigger");
    // vnd.oma.bcast.sgboot
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_BCAST_SGBOOT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.bcast.sgboot");
    // vnd.oma.bcast.sgdd+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_BCAST_SGDD_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.bcast.sgdd+xml");
    // vnd.oma.bcast.sgdu
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_BCAST_SGDU
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.bcast.sgdu");
    // vnd.oma.bcast.simple-symbol-container
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_BCAST_SIMPLE_SYMBOL_CONTAINER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.bcast.simple-symbol-container");
    // vnd.oma.bcast.smartcard-trigger+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_BCAST_SMARTCARD_TRIGGER_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.bcast.smartcard-trigger+xml");
    // vnd.oma.bcast.sprov+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_BCAST_SPROV_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.bcast.sprov+xml");
    // vnd.oma.bcast.stkm
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_BCAST_STKM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.bcast.stkm");
    // vnd.oma.cab-address-book+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_CAB_ADDRESS_BOOK_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.cab-address-book+xml");
    // vnd.oma.cab-feature-handler+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_CAB_FEATURE_HANDLER_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.cab-feature-handler+xml");
    // vnd.oma.cab-pcc+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_CAB_PCC_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.cab-pcc+xml");
    // vnd.oma.cab-user-prefs+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_CAB_USER_PREFS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.cab-user-prefs+xml");
    // vnd.oma.dcd
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_DCD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.dcd");
    // vnd.oma.dcdc
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_DCDC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.dcdc");
    // vnd.oma.dd2+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_DD2_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.dd2+xml");
    // vnd.oma.drm.risd+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_DRM_RISD_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.drm.risd+xml");
    // vnd.oma.group-usage-list+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_GROUP_USAGE_LIST_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.group-usage-list+xml");
    // vnd.oma.poc.detailed-progress-report+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_POC_DETAILED_PROGRESS_REPORT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.poc.detailed-progress-report+xml");
    // vnd.oma.poc.final-report+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_POC_FINAL_REPORT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.poc.final-report+xml");
    // vnd.oma.poc.groups+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_POC_GROUPS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.poc.groups+xml");
    // vnd.oma.poc.invocation-descriptor+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_POC_INVOCATION_DESCRIPTOR_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.poc.invocation-descriptor+xml");
    // vnd.oma.poc.optimized-progress-report+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_POC_OPTIMIZED_PROGRESS_REPORT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.poc.optimized-progress-report+xml");
    // vnd.oma.push
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_PUSH
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.push");
    // vnd.oma.scidm.messages+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_SCIDM_MESSAGES_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.scidm.messages+xml");
    // vnd.oma.xcap-directory+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_XCAP_DIRECTORY_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma.xcap-directory+xml");
    // vnd.omads-email+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMADS_EMAIL_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.omads-email+xml");
    // vnd.omads-file+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMADS_FILE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.omads-file+xml");
    // vnd.omads-folder+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMADS_FOLDER_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.omads-folder+xml");
    // vnd.omaloc-supl-init
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMALOC_SUPL_INIT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.omaloc-supl-init");
    // vnd.oma-scws-config
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_SCWS_CONFIG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma-scws-config");
    // vnd.oma-scws-http-request
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_SCWS_HTTP_REQUEST
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma-scws-http-request");
    // vnd.oma-scws-http-response
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OMA_SCWS_HTTP_RESPONSE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.oma-scws-http-response");
    // vnd.openofficeorg.extension
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENOFFICEORG_EXTENSION
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openofficeorg.extension");
    // vnd.openxmlformats-officedocument.custom-properties+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_CUSTOM_PROPERTIES_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.custom-properties+xml");
    // vnd.openxmlformats-officedocument.customXmlProperties+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_CUSTOMXMLPROPERTIES_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.customXmlProperties+xml");
    // vnd.openxmlformats-officedocument.drawing+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_DRAWING_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.drawing+xml");
    // vnd.openxmlformats-officedocument.drawingml.chart+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_DRAWINGML_CHART_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.drawingml.chart+xml");
    // vnd.openxmlformats-officedocument.drawingml.chartshapes+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_DRAWINGML_CHARTSHAPES_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.drawingml.chartshapes+xml");
    // vnd.openxmlformats-officedocument.drawingml.diagramColors+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_DRAWINGML_DIAGRAMCOLORS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.drawingml.diagramColors+xml");
    // vnd.openxmlformats-officedocument.drawingml.diagramData+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_DRAWINGML_DIAGRAMDATA_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.drawingml.diagramData+xml");
    // vnd.openxmlformats-officedocument.drawingml.diagramLayout+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_DRAWINGML_DIAGRAMLAYOUT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.drawingml.diagramLayout+xml");
    // vnd.openxmlformats-officedocument.drawingml.diagramStyle+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_DRAWINGML_DIAGRAMSTYLE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.drawingml.diagramStyle+xml");
    // vnd.openxmlformats-officedocument.extended-properties+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_EXTENDED_PROPERTIES_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.extended-properties+xml");
    // vnd.openxmlformats-officedocument.presentationml.commentAuthors+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_COMMENTAUTHORS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.commentAuthors+xml");
    // vnd.openxmlformats-officedocument.presentationml.comments+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_COMMENTS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.comments+xml");
    // vnd.openxmlformats-officedocument.presentationml.handoutMaster+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_HANDOUTMASTER_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.handoutMaster+xml");
    // vnd.openxmlformats-officedocument.presentationml.notesMaster+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_NOTESMASTER_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.notesMaster+xml");
    // vnd.openxmlformats-officedocument.presentationml.notesSlide+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_NOTESSLIDE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.notesSlide+xml");
    // vnd.openxmlformats-officedocument.presentationml.presentation.main+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_PRESENTATION_MAIN_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.presentation.main+xml");
    // vnd.openxmlformats-officedocument.presentationml.presProps+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_PRESPROPS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.presProps+xml");
    // vnd.openxmlformats-officedocument.presentationml.slide+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_SLIDE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.slide+xml");
    // vnd.openxmlformats-officedocument.presentationml.slideLayout+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_SLIDELAYOUT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.slideLayout+xml");
    // vnd.openxmlformats-officedocument.presentationml.slideMaster+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_SLIDEMASTER_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.slideMaster+xml");
    // vnd.openxmlformats-officedocument.presentationml.slideshow.main+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_SLIDESHOW_MAIN_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.slideshow.main+xml");
    // vnd.openxmlformats-officedocument.presentationml.slideUpdateInfo+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_SLIDEUPDATEINFO_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.slideUpdateInfo+xml");
    // vnd.openxmlformats-officedocument.presentationml.tableStyles+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_TABLESTYLES_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.tableStyles+xml");
    // vnd.openxmlformats-officedocument.presentationml.tags+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_TAGS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.tags+xml");
    // vnd.openxmlformats-officedocument.presentationml.template.main+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_TEMPLATE_MAIN_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.template.main+xml");
    // vnd.openxmlformats-officedocument.presentationml.viewProps+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_VIEWPROPS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.presentationml.viewProps+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.calcChain+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_CALCCHAIN_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.calcChain+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.chartsheet+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_CHARTSHEET_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.chartsheet+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.comments+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_COMMENTS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.comments+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.connections+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_CONNECTIONS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.connections+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.dialogsheet+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_DIALOGSHEET_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.dialogsheet+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.externalLink+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_EXTERNALLINK_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.externalLink+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.pivotCacheDefinition+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_PIVOTCACHEDEFINITION_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.pivotCacheDefinition+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.pivotCacheRecords+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_PIVOTCACHERECORDS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.pivotCacheRecords+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.pivotTable+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_PIVOTTABLE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.pivotTable+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.queryTable+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_QUERYTABLE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.queryTable+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.revisionHeaders+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_REVISIONHEADERS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.revisionHeaders+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.revisionLog+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_REVISIONLOG_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.revisionLog+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.sharedStrings+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_SHAREDSTRINGS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sharedStrings+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_SHEET_MAIN_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.sheetMetadata+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_SHEETMETADATA_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheetMetadata+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.styles+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_STYLES_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.table+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_TABLE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.table+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.tableSingleCells+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_TABLESINGLECELLS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.tableSingleCells+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.template.main+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_TEMPLATE_MAIN_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.template.main+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.userNames+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_USERNAMES_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.userNames+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.volatileDependencies+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_VOLATILEDEPENDENCIES_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.volatileDependencies+xml");
    // vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_WORKSHEET_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml");
    // vnd.openxmlformats-officedocument.theme+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_THEME_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.theme+xml");
    // vnd.openxmlformats-officedocument.themeOverride+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_THEMEOVERRIDE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.themeOverride+xml");
    // vnd.openxmlformats-officedocument.vmlDrawing
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_VMLDRAWING
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.vmlDrawing");
    // vnd.openxmlformats-officedocument.wordprocessingml.comments+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_COMMENTS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.comments+xml");
    // vnd.openxmlformats-officedocument.wordprocessingml.document.glossary+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_DOCUMENT_GLOSSARY_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.document.glossary+xml");
    // vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_DOCUMENT_MAIN_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml");
    // vnd.openxmlformats-officedocument.wordprocessingml.endnotes+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_ENDNOTES_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.endnotes+xml");
    // vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_FONTTABLE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml");
    // vnd.openxmlformats-officedocument.wordprocessingml.footer+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_FOOTER_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.footer+xml");
    // vnd.openxmlformats-officedocument.wordprocessingml.footnotes+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_FOOTNOTES_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.footnotes+xml");
    // vnd.openxmlformats-officedocument.wordprocessingml.numbering+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_NUMBERING_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.numbering+xml");
    // vnd.openxmlformats-officedocument.wordprocessingml.settings+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_SETTINGS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml");
    // vnd.openxmlformats-officedocument.wordprocessingml.styles+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_STYLES_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml");
    // vnd.openxmlformats-officedocument.wordprocessingml.template.main+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_TEMPLATE_MAIN_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.template.main+xml");
    // vnd.openxmlformats-officedocument.wordprocessingml.webSettings+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_WEBSETTINGS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-officedocument.wordprocessingml.webSettings+xml");
    // vnd.openxmlformats-package.core-properties+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_PACKAGE_CORE_PROPERTIES_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-package.core-properties+xml");
    // vnd.openxmlformats-package.digital-signature-xmlsignature+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_PACKAGE_DIGITAL_SIGNATURE_XMLSIGNATURE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-package.digital-signature-xmlsignature+xml");
    // vnd.openxmlformats-package.relationships+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OPENXMLFORMATS_PACKAGE_RELATIONSHIPS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.openxmlformats-package.relationships+xml");
    // vnd.osa.netdeploy
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OSA_NETDEPLOY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.osa.netdeploy");
    // vnd.osgeo.mapguide.package
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OSGEO_MAPGUIDE_PACKAGE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.osgeo.mapguide.package");
    // vnd.osgi.bundle
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OSGI_BUNDLE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.osgi.bundle");
    // vnd.osgi.dp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OSGI_DP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.osgi.dp");
    // vnd.otps.ct-kip+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_OTPS_CT_KIP_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.otps.ct-kip+xml");
    // vnd.palm
    public static final Version1ElectronicDataFormatType APPLICATION_VND_PALM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.palm");
    // vnd.paos.xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_PAOS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.paos.xml");
    // vnd.pawaafile
    public static final Version1ElectronicDataFormatType APPLICATION_VND_PAWAAFILE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.pawaafile");
    // vnd.pg.format
    public static final Version1ElectronicDataFormatType APPLICATION_VND_PG_FORMAT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.pg.format");
    // vnd.pg.osasli
    public static final Version1ElectronicDataFormatType APPLICATION_VND_PG_OSASLI
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.pg.osasli");
    // vnd.piaccess.application-licence
    public static final Version1ElectronicDataFormatType APPLICATION_VND_PIACCESS_APPLICATION_LICENCE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.piaccess.application-licence");
    // vnd.picsel
    public static final Version1ElectronicDataFormatType APPLICATION_VND_PICSEL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.picsel");
    // vnd.pmi.widget
    public static final Version1ElectronicDataFormatType APPLICATION_VND_PMI_WIDGET
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.pmi.widget");
    // vnd.poc.group-advertisement+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_POC_GROUP_ADVERTISEMENT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.poc.group-advertisement+xml");
    // vnd.pocketlearn
    public static final Version1ElectronicDataFormatType APPLICATION_VND_POCKETLEARN
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.pocketlearn");
    // vnd.powerbuilder6
    public static final Version1ElectronicDataFormatType APPLICATION_VND_POWERBUILDER6
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.powerbuilder6");
    // vnd.powerbuilder6-s
    public static final Version1ElectronicDataFormatType APPLICATION_VND_POWERBUILDER6_S
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.powerbuilder6-s");
    // vnd.powerbuilder7
    public static final Version1ElectronicDataFormatType APPLICATION_VND_POWERBUILDER7
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.powerbuilder7");
    // vnd.powerbuilder75
    public static final Version1ElectronicDataFormatType APPLICATION_VND_POWERBUILDER75
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.powerbuilder75");
    // vnd.powerbuilder75-s
    public static final Version1ElectronicDataFormatType APPLICATION_VND_POWERBUILDER75_S
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.powerbuilder75-s");
    // vnd.powerbuilder7-s
    public static final Version1ElectronicDataFormatType APPLICATION_VND_POWERBUILDER7_S
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.powerbuilder7-s");
    // vnd.preminet
    public static final Version1ElectronicDataFormatType APPLICATION_VND_PREMINET
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.preminet");
    // vnd.previewsystems.box
    public static final Version1ElectronicDataFormatType APPLICATION_VND_PREVIEWSYSTEMS_BOX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.previewsystems.box");
    // vnd.proteus.magazine
    public static final Version1ElectronicDataFormatType APPLICATION_VND_PROTEUS_MAGAZINE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.proteus.magazine");
    // vnd.publishare-delta-tree
    public static final Version1ElectronicDataFormatType APPLICATION_VND_PUBLISHARE_DELTA_TREE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.publishare-delta-tree");
    // vnd.pvi.ptid1
    public static final Version1ElectronicDataFormatType APPLICATION_VND_PVI_PTID1
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.pvi.ptid1");
    // vnd.pwg-multiplexed
    public static final Version1ElectronicDataFormatType APPLICATION_VND_PWG_MULTIPLEXED
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.pwg-multiplexed");
    // vnd.pwg-xhtml-print+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_PWG_XHTML_PRINT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.pwg-xhtml-print+xml");
    // vnd.qualcomm.brew-app-res
    public static final Version1ElectronicDataFormatType APPLICATION_VND_QUALCOMM_BREW_APP_RES
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.qualcomm.brew-app-res");
    // vnd.Quark.QuarkXPress
    public static final Version1ElectronicDataFormatType APPLICATION_VND_QUARK_QUARKXPRESS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.Quark.QuarkXPress");
    // vnd.radisys.moml+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RADISYS_MOML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.radisys.moml+xml");
    // vnd.radisys.msml-audit-conf+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RADISYS_MSML_AUDIT_CONF_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.radisys.msml-audit-conf+xml");
    // vnd.radisys.msml-audit-conn+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RADISYS_MSML_AUDIT_CONN_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.radisys.msml-audit-conn+xml");
    // vnd.radisys.msml-audit-dialog+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RADISYS_MSML_AUDIT_DIALOG_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.radisys.msml-audit-dialog+xml");
    // vnd.radisys.msml-audit-stream+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RADISYS_MSML_AUDIT_STREAM_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.radisys.msml-audit-stream+xml");
    // vnd.radisys.msml-audit+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RADISYS_MSML_AUDIT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.radisys.msml-audit+xml");
    // vnd.radisys.msml-conf+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RADISYS_MSML_CONF_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.radisys.msml-conf+xml");
    // vnd.radisys.msml-dialog-base+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RADISYS_MSML_DIALOG_BASE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.radisys.msml-dialog-base+xml");
    // vnd.radisys.msml-dialog-fax-detect+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RADISYS_MSML_DIALOG_FAX_DETECT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.radisys.msml-dialog-fax-detect+xml");
    // vnd.radisys.msml-dialog-fax-sendrecv+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RADISYS_MSML_DIALOG_FAX_SENDRECV_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.radisys.msml-dialog-fax-sendrecv+xml");
    // vnd.radisys.msml-dialog-group+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RADISYS_MSML_DIALOG_GROUP_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.radisys.msml-dialog-group+xml");
    // vnd.radisys.msml-dialog-speech+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RADISYS_MSML_DIALOG_SPEECH_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.radisys.msml-dialog-speech+xml");
    // vnd.radisys.msml-dialog-transform+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RADISYS_MSML_DIALOG_TRANSFORM_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.radisys.msml-dialog-transform+xml");
    // vnd.radisys.msml-dialog+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RADISYS_MSML_DIALOG_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.radisys.msml-dialog+xml");
    // vnd.radisys.msml+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RADISYS_MSML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.radisys.msml+xml");
    // vnd.rapid
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RAPID
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.rapid");
    // vnd.realvnc.bed
    public static final Version1ElectronicDataFormatType APPLICATION_VND_REALVNC_BED
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.realvnc.bed");
    // vnd.recordare.musicxml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RECORDARE_MUSICXML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.recordare.musicxml");
    // vnd.recordare.musicxml+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RECORDARE_MUSICXML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.recordare.musicxml+xml");
    // vnd.RenLearn.rlprint
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RENLEARN_RLPRINT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.RenLearn.rlprint");
    // vnd.rig.cryptonote
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RIG_CRYPTONOTE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.rig.cryptonote");
    // vnd.route66.link66+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ROUTE66_LINK66_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.route66.link66+xml");
    // vnd.ruckus.download
    public static final Version1ElectronicDataFormatType APPLICATION_VND_RUCKUS_DOWNLOAD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ruckus.download");
    // vnd.s3sms
    public static final Version1ElectronicDataFormatType APPLICATION_VND_S3SMS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.s3sms");
    // vnd.sailingtracker.track
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SAILINGTRACKER_TRACK
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sailingtracker.track");
    // vnd.sbm.cid
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SBM_CID
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sbm.cid");
    // vnd.sbm.mid2
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SBM_MID2
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sbm.mid2");
    // vnd.scribus
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SCRIBUS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.scribus");
    // vnd.sealed.3df
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SEALED_3DF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sealed.3df");
    // vnd.sealed.csf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SEALED_CSF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sealed.csf");
    // vnd.sealed.doc
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SEALED_DOC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sealed.doc");
    // vnd.sealed.eml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SEALED_EML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sealed.eml");
    // vnd.sealed.mht
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SEALED_MHT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sealed.mht");
    // vnd.sealed.net
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SEALED_NET
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sealed.net");
    // vnd.sealed.ppt
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SEALED_PPT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sealed.ppt");
    // vnd.sealed.tiff
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SEALED_TIFF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sealed.tiff");
    // vnd.sealed.xls
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SEALED_XLS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sealed.xls");
    // vnd.sealedmedia.softseal.html
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SEALEDMEDIA_SOFTSEAL_HTML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sealedmedia.softseal.html");
    // vnd.sealedmedia.softseal.pdf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SEALEDMEDIA_SOFTSEAL_PDF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sealedmedia.softseal.pdf");
    // vnd.seemail
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SEEMAIL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.seemail");
    // vnd.sema
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SEMA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sema");
    // vnd.semd
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SEMD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.semd");
    // vnd.semf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SEMF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.semf");
    // vnd.shana.informed.formdata
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SHANA_INFORMED_FORMDATA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.shana.informed.formdata");
    // vnd.shana.informed.formtemplate
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SHANA_INFORMED_FORMTEMPLATE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.shana.informed.formtemplate");
    // vnd.shana.informed.interchange
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SHANA_INFORMED_INTERCHANGE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.shana.informed.interchange");
    // vnd.shana.informed.package
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SHANA_INFORMED_PACKAGE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.shana.informed.package");
    // vnd.SimTech-MindMapper
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SIMTECH_MINDMAPPER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.SimTech-MindMapper");
    // vnd.smaf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SMAF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.smaf");
    // vnd.smart.notebook
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SMART_NOTEBOOK
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.smart.notebook");
    // vnd.smart.teacher
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SMART_TEACHER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.smart.teacher");
    // vnd.software602.filler.form+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SOFTWARE602_FILLER_FORM_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.software602.filler.form+xml");
    // vnd.software602.filler.form-xml-zip
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SOFTWARE602_FILLER_FORM_XML_ZIP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.software602.filler.form-xml-zip");
    // vnd.solent.sdkm+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SOLENT_SDKM_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.solent.sdkm+xml");
    // vnd.spotfire.dxp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SPOTFIRE_DXP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.spotfire.dxp");
    // vnd.spotfire.sfs
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SPOTFIRE_SFS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.spotfire.sfs");
    // vnd.sss-cod
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SSS_COD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sss-cod");
    // vnd.sss-dtf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SSS_DTF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sss-dtf");
    // vnd.sss-ntf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SSS_NTF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sss-ntf");
    // vnd.stepmania.stepchart
    public static final Version1ElectronicDataFormatType APPLICATION_VND_STEPMANIA_STEPCHART
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.stepmania.stepchart");
    // vnd.street-stream
    public static final Version1ElectronicDataFormatType APPLICATION_VND_STREET_STREAM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.street-stream");
    // vnd.sun.wadl+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SUN_WADL_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sun.wadl+xml");
    // vnd.sus-calendar
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SUS_CALENDAR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.sus-calendar");
    // vnd.svd
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SVD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.svd");
    // vnd.swiftview-ics
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SWIFTVIEW_ICS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.swiftview-ics");
    // vnd.syncml.dm.notification
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SYNCML_DM_NOTIFICATION
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.syncml.dm.notification");
    // vnd.syncml.dm+wbxml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SYNCML_DM_WBXML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.syncml.dm+wbxml");
    // vnd.syncml.dm+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SYNCML_DM_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.syncml.dm+xml");
    // vnd.syncml.ds.notification
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SYNCML_DS_NOTIFICATION
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.syncml.ds.notification");
    // vnd.syncml+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_SYNCML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.syncml+xml");
    // vnd.tao.intent-module-archive
    public static final Version1ElectronicDataFormatType APPLICATION_VND_TAO_INTENT_MODULE_ARCHIVE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.tao.intent-module-archive");
    // vnd.tmobile-livetv
    public static final Version1ElectronicDataFormatType APPLICATION_VND_TMOBILE_LIVETV
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.tmobile-livetv");
    // vnd.trid.tpt
    public static final Version1ElectronicDataFormatType APPLICATION_VND_TRID_TPT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.trid.tpt");
    // vnd.triscape.mxs
    public static final Version1ElectronicDataFormatType APPLICATION_VND_TRISCAPE_MXS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.triscape.mxs");
    // vnd.trueapp
    public static final Version1ElectronicDataFormatType APPLICATION_VND_TRUEAPP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.trueapp");
    // vnd.truedoc
    public static final Version1ElectronicDataFormatType APPLICATION_VND_TRUEDOC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.truedoc");
    // vnd.ufdl
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UFDL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.ufdl");
    // vnd.uiq.theme
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UIQ_THEME
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.uiq.theme");
    // vnd.umajin
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UMAJIN
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.umajin");
    // vnd.unity
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UNITY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.unity");
    // vnd.uoml+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UOML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.uoml+xml");
    // vnd.uplanet.alert
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UPLANET_ALERT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.uplanet.alert");
    // vnd.uplanet.alert-wbxml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UPLANET_ALERT_WBXML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.uplanet.alert-wbxml");
    // vnd.uplanet.bearer-choice
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UPLANET_BEARER_CHOICE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.uplanet.bearer-choice");
    // vnd.uplanet.bearer-choice-wbxml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UPLANET_BEARER_CHOICE_WBXML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.uplanet.bearer-choice-wbxml");
    // vnd.uplanet.cacheop
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UPLANET_CACHEOP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.uplanet.cacheop");
    // vnd.uplanet.cacheop-wbxml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UPLANET_CACHEOP_WBXML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.uplanet.cacheop-wbxml");
    // vnd.uplanet.channel
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UPLANET_CHANNEL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.uplanet.channel");
    // vnd.uplanet.channel-wbxml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UPLANET_CHANNEL_WBXML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.uplanet.channel-wbxml");
    // vnd.uplanet.list
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UPLANET_LIST
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.uplanet.list");
    // vnd.uplanet.listcmd
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UPLANET_LISTCMD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.uplanet.listcmd");
    // vnd.uplanet.listcmd-wbxml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UPLANET_LISTCMD_WBXML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.uplanet.listcmd-wbxml");
    // vnd.uplanet.list-wbxml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UPLANET_LIST_WBXML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.uplanet.list-wbxml");
    // vnd.uplanet.signal
    public static final Version1ElectronicDataFormatType APPLICATION_VND_UPLANET_SIGNAL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.uplanet.signal");
    // vnd.vcx
    public static final Version1ElectronicDataFormatType APPLICATION_VND_VCX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.vcx");
    // vnd.vd-study
    public static final Version1ElectronicDataFormatType APPLICATION_VND_VD_STUDY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.vd-study");
    // vnd.vectorworks
    public static final Version1ElectronicDataFormatType APPLICATION_VND_VECTORWORKS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.vectorworks");
    // vnd.verimatrix.vcas
    public static final Version1ElectronicDataFormatType APPLICATION_VND_VERIMATRIX_VCAS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.verimatrix.vcas");
    // vnd.vidsoft.vidconference
    public static final Version1ElectronicDataFormatType APPLICATION_VND_VIDSOFT_VIDCONFERENCE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.vidsoft.vidconference");
    // vnd.visio
    public static final Version1ElectronicDataFormatType APPLICATION_VND_VISIO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.visio");
    // vnd.visionary
    public static final Version1ElectronicDataFormatType APPLICATION_VND_VISIONARY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.visionary");
    // vnd.vividence.scriptfile
    public static final Version1ElectronicDataFormatType APPLICATION_VND_VIVIDENCE_SCRIPTFILE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.vividence.scriptfile");
    // vnd.vsf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_VSF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.vsf");
    // vnd.wap.sic
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WAP_SIC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wap.sic");
    // vnd.wap.slc
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WAP_SLC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wap.slc");
    // vnd.wap.wbxml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WAP_WBXML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wap.wbxml");
    // vnd.wap.wmlc
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WAP_WMLC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wap.wmlc");
    // vnd.wap.wmlscriptc
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WAP_WMLSCRIPTC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wap.wmlscriptc");
    // vnd.webturbo
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WEBTURBO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.webturbo");
    // vnd.wfa.wsc
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WFA_WSC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wfa.wsc");
    // vnd.wmc
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WMC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wmc");
    // vnd.wmf.bootstrap
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WMF_BOOTSTRAP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wmf.bootstrap");
    // vnd.wolfram.mathematica
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WOLFRAM_MATHEMATICA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wolfram.mathematica");
    // vnd.wolfram.mathematica.package
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WOLFRAM_MATHEMATICA_PACKAGE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wolfram.mathematica.package");
    // vnd.wolfram.player
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WOLFRAM_PLAYER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wolfram.player");
    // vnd.wordperfect
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WORDPERFECT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wordperfect");
    // vnd.wqd
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WQD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wqd");
    // vnd.wrq-hp3000-labelled
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WRQ_HP3000_LABELLED
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wrq-hp3000-labelled");
    // vnd.wt.stf
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WT_STF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wt.stf");
    // vnd.wv.csp+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WV_CSP_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wv.csp+xml");
    // vnd.wv.csp+wbxml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WV_CSP_WBXML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wv.csp+wbxml");
    // vnd.wv.ssp+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_WV_SSP_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.wv.ssp+xml");
    // vnd.xara
    public static final Version1ElectronicDataFormatType APPLICATION_VND_XARA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.xara");
    // vnd.xfdl
    public static final Version1ElectronicDataFormatType APPLICATION_VND_XFDL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.xfdl");
    // vnd.xfdl.webform
    public static final Version1ElectronicDataFormatType APPLICATION_VND_XFDL_WEBFORM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.xfdl.webform");
    // vnd.xmi+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_XMI_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.xmi+xml");
    // vnd.xmpie.cpkg
    public static final Version1ElectronicDataFormatType APPLICATION_VND_XMPIE_CPKG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.xmpie.cpkg");
    // vnd.xmpie.dpkg
    public static final Version1ElectronicDataFormatType APPLICATION_VND_XMPIE_DPKG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.xmpie.dpkg");
    // vnd.xmpie.plan
    public static final Version1ElectronicDataFormatType APPLICATION_VND_XMPIE_PLAN
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.xmpie.plan");
    // vnd.xmpie.ppkg
    public static final Version1ElectronicDataFormatType APPLICATION_VND_XMPIE_PPKG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.xmpie.ppkg");
    // vnd.xmpie.xlim
    public static final Version1ElectronicDataFormatType APPLICATION_VND_XMPIE_XLIM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.xmpie.xlim");
    // vnd.yamaha.hv-dic
    public static final Version1ElectronicDataFormatType APPLICATION_VND_YAMAHA_HV_DIC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.yamaha.hv-dic");
    // vnd.yamaha.hv-script
    public static final Version1ElectronicDataFormatType APPLICATION_VND_YAMAHA_HV_SCRIPT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.yamaha.hv-script");
    // vnd.yamaha.hv-voice
    public static final Version1ElectronicDataFormatType APPLICATION_VND_YAMAHA_HV_VOICE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.yamaha.hv-voice");
    // vnd.yamaha.openscoreformat.osfpvg+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_YAMAHA_OPENSCOREFORMAT_OSFPVG_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.yamaha.openscoreformat.osfpvg+xml");
    // vnd.yamaha.openscoreformat
    public static final Version1ElectronicDataFormatType APPLICATION_VND_YAMAHA_OPENSCOREFORMAT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.yamaha.openscoreformat");
    // vnd.yamaha.smaf-audio
    public static final Version1ElectronicDataFormatType APPLICATION_VND_YAMAHA_SMAF_AUDIO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.yamaha.smaf-audio");
    // vnd.yamaha.smaf-phrase
    public static final Version1ElectronicDataFormatType APPLICATION_VND_YAMAHA_SMAF_PHRASE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.yamaha.smaf-phrase");
    // vnd.yellowriver-custom-menu
    public static final Version1ElectronicDataFormatType APPLICATION_VND_YELLOWRIVER_CUSTOM_MENU
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.yellowriver-custom-menu");
    // vnd.zul
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ZUL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.zul");
    // vnd.zzazz.deck+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VND_ZZAZZ_DECK_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vnd.zzazz.deck+xml");
    // voicexml+xml
    public static final Version1ElectronicDataFormatType APPLICATION_VOICEXML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/voicexml+xml");
    // vq-rtcpxr
    public static final Version1ElectronicDataFormatType APPLICATION_VQ_RTCPXR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/vq-rtcpxr");
    // watcherinfo+xml
    public static final Version1ElectronicDataFormatType APPLICATION_WATCHERINFO_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/watcherinfo+xml");
    // whoispp-query
    public static final Version1ElectronicDataFormatType APPLICATION_WHOISPP_QUERY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/whoispp-query");
    // whoispp-response
    public static final Version1ElectronicDataFormatType APPLICATION_WHOISPP_RESPONSE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/whoispp-response");
    // widget
    public static final Version1ElectronicDataFormatType APPLICATION_WIDGET
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/widget");
    // wita
    public static final Version1ElectronicDataFormatType APPLICATION_WITA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/wita");
    // wordperfect5.1
    public static final Version1ElectronicDataFormatType APPLICATION_WORDPERFECT5_1
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/wordperfect5.1");
    // wsdl+xml
    public static final Version1ElectronicDataFormatType APPLICATION_WSDL_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/wsdl+xml");
    // wspolicy+xml
    public static final Version1ElectronicDataFormatType APPLICATION_WSPOLICY_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/wspolicy+xml");
    // x400-bp
    public static final Version1ElectronicDataFormatType APPLICATION_X400_BP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/x400-bp");
    // xcap-att+xml
    public static final Version1ElectronicDataFormatType APPLICATION_XCAP_ATT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xcap-att+xml");
    // xcap-caps+xml
    public static final Version1ElectronicDataFormatType APPLICATION_XCAP_CAPS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xcap-caps+xml");
    // xcap-diff+xml
    public static final Version1ElectronicDataFormatType APPLICATION_XCAP_DIFF_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xcap-diff+xml");
    // xcap-el+xml
    public static final Version1ElectronicDataFormatType APPLICATION_XCAP_EL_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xcap-el+xml");
    // xcap-error+xml
    public static final Version1ElectronicDataFormatType APPLICATION_XCAP_ERROR_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xcap-error+xml");
    // xcap-ns+xml
    public static final Version1ElectronicDataFormatType APPLICATION_XCAP_NS_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xcap-ns+xml");
    // xcon-conference-info-diff+xml
    public static final Version1ElectronicDataFormatType APPLICATION_XCON_CONFERENCE_INFO_DIFF_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xcon-conference-info-diff+xml");
    // xcon-conference-info+xml
    public static final Version1ElectronicDataFormatType APPLICATION_XCON_CONFERENCE_INFO_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xcon-conference-info+xml");
    // xenc+xml
    public static final Version1ElectronicDataFormatType APPLICATION_XENC_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xenc+xml");
    // xhtml-voice+xml
    public static final Version1ElectronicDataFormatType APPLICATION_XHTML_VOICE_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xhtml-voice+xml");
    // xhtml+xml
    public static final Version1ElectronicDataFormatType APPLICATION_XHTML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xhtml+xml");
    // xml
    public static final Version1ElectronicDataFormatType APPLICATION_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xml");
    // xml-dtd
    public static final Version1ElectronicDataFormatType APPLICATION_XML_DTD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xml-dtd");
    // xml-external-parsed-entity
    public static final Version1ElectronicDataFormatType APPLICATION_XML_EXTERNAL_PARSED_ENTITY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xml-external-parsed-entity");
    // xmpp+xml
    public static final Version1ElectronicDataFormatType APPLICATION_XMPP_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xmpp+xml");
    // xop+xml
    public static final Version1ElectronicDataFormatType APPLICATION_XOP_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xop+xml");
    // xslt+xml
    public static final Version1ElectronicDataFormatType APPLICATION_XSLT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xslt+xml");
    // xv+xml
    public static final Version1ElectronicDataFormatType APPLICATION_XV_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/xv+xml");
    // yang
    public static final Version1ElectronicDataFormatType APPLICATION_YANG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/yang");
    // yin+xml
    public static final Version1ElectronicDataFormatType APPLICATION_YIN_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/yin+xml");
    // zip
    public static final Version1ElectronicDataFormatType APPLICATION_ZIP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "application/zip");
    // 1d-interleaved-parityfec
    public static final Version1ElectronicDataFormatType AUDIO_1D_INTERLEAVED_PARITYFEC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/1d-interleaved-parityfec");
    // 32kadpcm
    public static final Version1ElectronicDataFormatType AUDIO_32KADPCM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/32kadpcm");
    // 3gpp
    public static final Version1ElectronicDataFormatType AUDIO_3GPP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/3gpp");
    // 3gpp2
    public static final Version1ElectronicDataFormatType AUDIO_3GPP2
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/3gpp2");
    // ac3
    public static final Version1ElectronicDataFormatType AUDIO_AC3
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/ac3");
    // AMR
    public static final Version1ElectronicDataFormatType AUDIO_AMR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/AMR");
    // AMR-WB
    public static final Version1ElectronicDataFormatType AUDIO_AMR_WB
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/AMR-WB");
    // amr-wb+
    public static final Version1ElectronicDataFormatType AUDIO_AMR_WB_PLUS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/amr-wb+");
    // asc
    public static final Version1ElectronicDataFormatType AUDIO_ASC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/asc");
    // ATRAC-ADVANCED-LOSSLESS
    public static final Version1ElectronicDataFormatType AUDIO_ATRAC_ADVANCED_LOSSLESS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/ATRAC-ADVANCED-LOSSLESS");
    // ATRAC-X
    public static final Version1ElectronicDataFormatType AUDIO_ATRAC_X
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/ATRAC-X");
    // ATRAC3
    public static final Version1ElectronicDataFormatType AUDIO_ATRAC3
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/ATRAC3");
    // basic
    public static final Version1ElectronicDataFormatType AUDIO_BASIC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/basic");
    // BV16
    public static final Version1ElectronicDataFormatType AUDIO_BV16
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/BV16");
    // BV32
    public static final Version1ElectronicDataFormatType AUDIO_BV32
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/BV32");
    // clearmode
    public static final Version1ElectronicDataFormatType AUDIO_CLEARMODE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/clearmode");
    // CN
    public static final Version1ElectronicDataFormatType AUDIO_CN
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/CN");
    // DAT12
    public static final Version1ElectronicDataFormatType AUDIO_DAT12
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/DAT12");
    // dls
    public static final Version1ElectronicDataFormatType AUDIO_DLS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/dls");
    // dsr-es201108
    public static final Version1ElectronicDataFormatType AUDIO_DSR_ES201108
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/dsr-es201108");
    // dsr-es202050
    public static final Version1ElectronicDataFormatType AUDIO_DSR_ES202050
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/dsr-es202050");
    // dsr-es202211
    public static final Version1ElectronicDataFormatType AUDIO_DSR_ES202211
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/dsr-es202211");
    // dsr-es202212
    public static final Version1ElectronicDataFormatType AUDIO_DSR_ES202212
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/dsr-es202212");
    // eac3
    public static final Version1ElectronicDataFormatType AUDIO_EAC3
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/eac3");
    // DVI4
    public static final Version1ElectronicDataFormatType AUDIO_DVI4
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/DVI4");
    // EVRC
    public static final Version1ElectronicDataFormatType AUDIO_EVRC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/EVRC");
    // EVRC0
    public static final Version1ElectronicDataFormatType AUDIO_EVRC0
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/EVRC0");
    // EVRC1
    public static final Version1ElectronicDataFormatType AUDIO_EVRC1
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/EVRC1");
    // EVRCB
    public static final Version1ElectronicDataFormatType AUDIO_EVRCB
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/EVRCB");
    // EVRCB0
    public static final Version1ElectronicDataFormatType AUDIO_EVRCB0
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/EVRCB0");
    // EVRCB1
    public static final Version1ElectronicDataFormatType AUDIO_EVRCB1
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/EVRCB1");
    // EVRC-QCP
    public static final Version1ElectronicDataFormatType AUDIO_EVRC_QCP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/EVRC-QCP");
    // EVRCWB
    public static final Version1ElectronicDataFormatType AUDIO_EVRCWB
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/EVRCWB");
    // EVRCWB0
    public static final Version1ElectronicDataFormatType AUDIO_EVRCWB0
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/EVRCWB0");
    // EVRCWB1
    public static final Version1ElectronicDataFormatType AUDIO_EVRCWB1
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/EVRCWB1");
    // example
    public static final Version1ElectronicDataFormatType AUDIO_EXAMPLE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/example");
    // G719
    public static final Version1ElectronicDataFormatType AUDIO_G719
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/G719");
    // G722
    public static final Version1ElectronicDataFormatType AUDIO_G722
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/G722");
    // G7221
    public static final Version1ElectronicDataFormatType AUDIO_G7221
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/G7221");
    // G723
    public static final Version1ElectronicDataFormatType AUDIO_G723
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/G723");
    // G726-16
    public static final Version1ElectronicDataFormatType AUDIO_G726_16
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/G726-16");
    // G726-24
    public static final Version1ElectronicDataFormatType AUDIO_G726_24
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/G726-24");
    // G726-32
    public static final Version1ElectronicDataFormatType AUDIO_G726_32
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/G726-32");
    // G726-40
    public static final Version1ElectronicDataFormatType AUDIO_G726_40
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/G726-40");
    // G728
    public static final Version1ElectronicDataFormatType AUDIO_G728
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/G728");
    // G729
    public static final Version1ElectronicDataFormatType AUDIO_G729
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/G729");
    // G7291
    public static final Version1ElectronicDataFormatType AUDIO_G7291
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/G7291");
    // G729D
    public static final Version1ElectronicDataFormatType AUDIO_G729D
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/G729D");
    // G729E
    public static final Version1ElectronicDataFormatType AUDIO_G729E
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/G729E");
    // GSM
    public static final Version1ElectronicDataFormatType AUDIO_GSM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/GSM");
    // GSM-EFR
    public static final Version1ElectronicDataFormatType AUDIO_GSM_EFR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/GSM-EFR");
    // GSM-HR-08
    public static final Version1ElectronicDataFormatType AUDIO_GSM_HR_08
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/GSM-HR-08");
    // iLBC
    public static final Version1ElectronicDataFormatType AUDIO_ILBC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/iLBC");
    // L8
    public static final Version1ElectronicDataFormatType AUDIO_L8
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/L8");
    // L16
    public static final Version1ElectronicDataFormatType AUDIO_L16
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/L16");
    // L20
    public static final Version1ElectronicDataFormatType AUDIO_L20
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/L20");
    // L24
    public static final Version1ElectronicDataFormatType AUDIO_L24
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/L24");
    // LPC
    public static final Version1ElectronicDataFormatType AUDIO_LPC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/LPC");
    // mobile-xmf
    public static final Version1ElectronicDataFormatType AUDIO_MOBILE_XMF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/mobile-xmf");
    // MPA
    public static final Version1ElectronicDataFormatType AUDIO_MPA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/MPA");
    // mp4
    public static final Version1ElectronicDataFormatType AUDIO_MP4
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/mp4");
    // MP4A-LATM
    public static final Version1ElectronicDataFormatType AUDIO_MP4A_LATM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/MP4A-LATM");
    // mpa-robust
    public static final Version1ElectronicDataFormatType AUDIO_MPA_ROBUST
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/mpa-robust");
    // mpeg
    public static final Version1ElectronicDataFormatType AUDIO_MPEG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/mpeg");
    // mpeg4-generic
    public static final Version1ElectronicDataFormatType AUDIO_MPEG4_GENERIC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/mpeg4-generic");
    // ogg
    public static final Version1ElectronicDataFormatType AUDIO_OGG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/ogg");
    // parityfec
    public static final Version1ElectronicDataFormatType AUDIO_PARITYFEC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/parityfec");
    // PCMA
    public static final Version1ElectronicDataFormatType AUDIO_PCMA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/PCMA");
    // PCMA-WB
    public static final Version1ElectronicDataFormatType AUDIO_PCMA_WB
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/PCMA-WB");
    // PCMU
    public static final Version1ElectronicDataFormatType AUDIO_PCMU
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/PCMU");
    // PCMU-WB
    public static final Version1ElectronicDataFormatType AUDIO_PCMU_WB
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/PCMU-WB");
    // prs.sid
    public static final Version1ElectronicDataFormatType AUDIO_PRS_SID
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/prs.sid");
    // QCELP
    public static final Version1ElectronicDataFormatType AUDIO_QCELP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/QCELP");
    // RED
    public static final Version1ElectronicDataFormatType AUDIO_RED
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/RED");
    // rtp-enc-aescm128
    public static final Version1ElectronicDataFormatType AUDIO_RTP_ENC_AESCM128
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/rtp-enc-aescm128");
    // rtp-midi
    public static final Version1ElectronicDataFormatType AUDIO_RTP_MIDI
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/rtp-midi");
    // rtx
    public static final Version1ElectronicDataFormatType AUDIO_RTX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/rtx");
    // SMV
    public static final Version1ElectronicDataFormatType AUDIO_SMV
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/SMV");
    // SMV0
    public static final Version1ElectronicDataFormatType AUDIO_SMV0
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/SMV0");
    // SMV-QCP
    public static final Version1ElectronicDataFormatType AUDIO_SMV_QCP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/SMV-QCP");
    // sp-midi
    public static final Version1ElectronicDataFormatType AUDIO_SP_MIDI
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/sp-midi");
    // speex
    public static final Version1ElectronicDataFormatType AUDIO_SPEEX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/speex");
    // t140c
    public static final Version1ElectronicDataFormatType AUDIO_T140C
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/t140c");
    // t38
    public static final Version1ElectronicDataFormatType AUDIO_T38
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/t38");
    // telephone-event
    public static final Version1ElectronicDataFormatType AUDIO_TELEPHONE_EVENT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/telephone-event");
    // tone
    public static final Version1ElectronicDataFormatType AUDIO_TONE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/tone");
    // UEMCLIP
    public static final Version1ElectronicDataFormatType AUDIO_UEMCLIP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/UEMCLIP");
    // ulpfec
    public static final Version1ElectronicDataFormatType AUDIO_ULPFEC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/ulpfec");
    // VDVI
    public static final Version1ElectronicDataFormatType AUDIO_VDVI
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/VDVI");
    // VMR-WB
    public static final Version1ElectronicDataFormatType AUDIO_VMR_WB
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/VMR-WB");
    // vnd.3gpp.iufp
    public static final Version1ElectronicDataFormatType AUDIO_VND_3GPP_IUFP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.3gpp.iufp");
    // vnd.4SB
    public static final Version1ElectronicDataFormatType AUDIO_VND_4SB
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.4SB");
    // vnd.audiokoz
    public static final Version1ElectronicDataFormatType AUDIO_VND_AUDIOKOZ
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.audiokoz");
    // vnd.CELP
    public static final Version1ElectronicDataFormatType AUDIO_VND_CELP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.CELP");
    // vnd.cisco.nse
    public static final Version1ElectronicDataFormatType AUDIO_VND_CISCO_NSE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.cisco.nse");
    // vnd.cmles.radio-events
    public static final Version1ElectronicDataFormatType AUDIO_VND_CMLES_RADIO_EVENTS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.cmles.radio-events");
    // vnd.cns.anp1
    public static final Version1ElectronicDataFormatType AUDIO_VND_CNS_ANP1
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.cns.anp1");
    // vnd.cns.inf1
    public static final Version1ElectronicDataFormatType AUDIO_VND_CNS_INF1
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.cns.inf1");
    // vnd.dece.audio
    public static final Version1ElectronicDataFormatType AUDIO_VND_DECE_AUDIO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.dece.audio");
    // vnd.digital-winds
    public static final Version1ElectronicDataFormatType AUDIO_VND_DIGITAL_WINDS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.digital-winds");
    // vnd.dlna.adts
    public static final Version1ElectronicDataFormatType AUDIO_VND_DLNA_ADTS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.dlna.adts");
    // vnd.dolby.heaac.1
    public static final Version1ElectronicDataFormatType AUDIO_VND_DOLBY_HEAAC_1
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.dolby.heaac.1");
    // vnd.dolby.heaac.2
    public static final Version1ElectronicDataFormatType AUDIO_VND_DOLBY_HEAAC_2
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.dolby.heaac.2");
    // vnd.dolby.mlp
    public static final Version1ElectronicDataFormatType AUDIO_VND_DOLBY_MLP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.dolby.mlp");
    // vnd.dolby.mps
    public static final Version1ElectronicDataFormatType AUDIO_VND_DOLBY_MPS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.dolby.mps");
    // vnd.dolby.pl2
    public static final Version1ElectronicDataFormatType AUDIO_VND_DOLBY_PL2
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.dolby.pl2");
    // vnd.dolby.pl2x
    public static final Version1ElectronicDataFormatType AUDIO_VND_DOLBY_PL2X
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.dolby.pl2x");
    // vnd.dolby.pl2z
    public static final Version1ElectronicDataFormatType AUDIO_VND_DOLBY_PL2Z
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.dolby.pl2z");
    // vnd.dolby.pulse.1
    public static final Version1ElectronicDataFormatType AUDIO_VND_DOLBY_PULSE_1
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.dolby.pulse.1");
    // vnd.dra
    public static final Version1ElectronicDataFormatType AUDIO_VND_DRA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.dra");
    // vnd.dts
    public static final Version1ElectronicDataFormatType AUDIO_VND_DTS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.dts");
    // vnd.dts.hd
    public static final Version1ElectronicDataFormatType AUDIO_VND_DTS_HD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.dts.hd");
    // vnd.everad.plj
    public static final Version1ElectronicDataFormatType AUDIO_VND_EVERAD_PLJ
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.everad.plj");
    // vnd.hns.audio
    public static final Version1ElectronicDataFormatType AUDIO_VND_HNS_AUDIO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.hns.audio");
    // vnd.lucent.voice
    public static final Version1ElectronicDataFormatType AUDIO_VND_LUCENT_VOICE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.lucent.voice");
    // vnd.ms-playready.media.pya
    public static final Version1ElectronicDataFormatType AUDIO_VND_MS_PLAYREADY_MEDIA_PYA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.ms-playready.media.pya");
    // vnd.nokia.mobile-xmf
    public static final Version1ElectronicDataFormatType AUDIO_VND_NOKIA_MOBILE_XMF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.nokia.mobile-xmf");
    // vnd.nortel.vbk
    public static final Version1ElectronicDataFormatType AUDIO_VND_NORTEL_VBK
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.nortel.vbk");
    // vnd.nuera.ecelp4800
    public static final Version1ElectronicDataFormatType AUDIO_VND_NUERA_ECELP4800
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.nuera.ecelp4800");
    // vnd.nuera.ecelp7470
    public static final Version1ElectronicDataFormatType AUDIO_VND_NUERA_ECELP7470
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.nuera.ecelp7470");
    // vnd.nuera.ecelp9600
    public static final Version1ElectronicDataFormatType AUDIO_VND_NUERA_ECELP9600
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.nuera.ecelp9600");
    // vnd.octel.sbc
    public static final Version1ElectronicDataFormatType AUDIO_VND_OCTEL_SBC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.octel.sbc");
    // vnd.qcelp
    public static final Version1ElectronicDataFormatType AUDIO_VND_QCELP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.qcelp");
    // vnd.rhetorex.32kadpcm
    public static final Version1ElectronicDataFormatType AUDIO_VND_RHETOREX_32KADPCM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.rhetorex.32kadpcm");
    // vnd.sealedmedia.softseal.mpeg
    public static final Version1ElectronicDataFormatType AUDIO_VND_SEALEDMEDIA_SOFTSEAL_MPEG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.sealedmedia.softseal.mpeg");
    // vnd.vmx.cvsd
    public static final Version1ElectronicDataFormatType AUDIO_VND_VMX_CVSD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vnd.vmx.cvsd");
    // vorbis
    public static final Version1ElectronicDataFormatType AUDIO_VORBIS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vorbis");
    // vorbis-config
    public static final Version1ElectronicDataFormatType AUDIO_VORBIS_CONFIG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "audio/vorbis-config");
    // cgm
    public static final Version1ElectronicDataFormatType IMAGE_CGM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/cgm");
    // example
    public static final Version1ElectronicDataFormatType IMAGE_EXAMPLE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/example");
    // fits
    public static final Version1ElectronicDataFormatType IMAGE_FITS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/fits");
    // g3fax
    public static final Version1ElectronicDataFormatType IMAGE_G3FAX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/g3fax");
    // gif
    public static final Version1ElectronicDataFormatType IMAGE_GIF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/gif");
    // ief
    public static final Version1ElectronicDataFormatType IMAGE_IEF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/ief");
    // jp2
    public static final Version1ElectronicDataFormatType IMAGE_JP2
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/jp2");
    // jpeg
    public static final Version1ElectronicDataFormatType IMAGE_JPEG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/jpeg");
    // jpm
    public static final Version1ElectronicDataFormatType IMAGE_JPM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/jpm");
    // jpx
    public static final Version1ElectronicDataFormatType IMAGE_JPX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/jpx");
    // ktx
    public static final Version1ElectronicDataFormatType IMAGE_KTX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/ktx");
    // naplps
    public static final Version1ElectronicDataFormatType IMAGE_NAPLPS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/naplps");
    // png
    public static final Version1ElectronicDataFormatType IMAGE_PNG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/png");
    // prs.btif
    public static final Version1ElectronicDataFormatType IMAGE_PRS_BTIF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/prs.btif");
    // prs.pti
    public static final Version1ElectronicDataFormatType IMAGE_PRS_PTI
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/prs.pti");
    // t38
    public static final Version1ElectronicDataFormatType IMAGE_T38
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/t38");
    // tiff
    public static final Version1ElectronicDataFormatType IMAGE_TIFF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/tiff");
    // tiff-fx
    public static final Version1ElectronicDataFormatType IMAGE_TIFF_FX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/tiff-fx");
    // vnd.adobe.photoshop
    public static final Version1ElectronicDataFormatType IMAGE_VND_ADOBE_PHOTOSHOP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.adobe.photoshop");
    // vnd.cns.inf2
    public static final Version1ElectronicDataFormatType IMAGE_VND_CNS_INF2
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.cns.inf2");
    // vnd.dece.graphic
    public static final Version1ElectronicDataFormatType IMAGE_VND_DECE_GRAPHIC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.dece.graphic");
    // vnd.djvu
    public static final Version1ElectronicDataFormatType IMAGE_VND_DJVU
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.djvu");
    // vnd.dwg
    public static final Version1ElectronicDataFormatType IMAGE_VND_DWG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.dwg");
    // vnd.dxf
    public static final Version1ElectronicDataFormatType IMAGE_VND_DXF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.dxf");
    // vnd.fastbidsheet
    public static final Version1ElectronicDataFormatType IMAGE_VND_FASTBIDSHEET
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.fastbidsheet");
    // vnd.fpx
    public static final Version1ElectronicDataFormatType IMAGE_VND_FPX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.fpx");
    // vnd.fst
    public static final Version1ElectronicDataFormatType IMAGE_VND_FST
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.fst");
    // vnd.fujixerox.edmics-mmr
    public static final Version1ElectronicDataFormatType IMAGE_VND_FUJIXEROX_EDMICS_MMR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.fujixerox.edmics-mmr");
    // vnd.fujixerox.edmics-rlc
    public static final Version1ElectronicDataFormatType IMAGE_VND_FUJIXEROX_EDMICS_RLC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.fujixerox.edmics-rlc");
    // vnd.globalgraphics.pgb
    public static final Version1ElectronicDataFormatType IMAGE_VND_GLOBALGRAPHICS_PGB
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.globalgraphics.pgb");
    // vnd.microsoft.icon
    public static final Version1ElectronicDataFormatType IMAGE_VND_MICROSOFT_ICON
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.microsoft.icon");
    // vnd.mix
    public static final Version1ElectronicDataFormatType IMAGE_VND_MIX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.mix");
    // vnd.ms-modi
    public static final Version1ElectronicDataFormatType IMAGE_VND_MS_MODI
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.ms-modi");
    // vnd.net-fpx
    public static final Version1ElectronicDataFormatType IMAGE_VND_NET_FPX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.net-fpx");
    // vnd.radiance
    public static final Version1ElectronicDataFormatType IMAGE_VND_RADIANCE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.radiance");
    // vnd.sealed.png
    public static final Version1ElectronicDataFormatType IMAGE_VND_SEALED_PNG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.sealed.png");
    // vnd.sealedmedia.softseal.gif
    public static final Version1ElectronicDataFormatType IMAGE_VND_SEALEDMEDIA_SOFTSEAL_GIF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.sealedmedia.softseal.gif");
    // vnd.sealedmedia.softseal.jpg
    public static final Version1ElectronicDataFormatType IMAGE_VND_SEALEDMEDIA_SOFTSEAL_JPG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.sealedmedia.softseal.jpg");
    // vnd.svf
    public static final Version1ElectronicDataFormatType IMAGE_VND_SVF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.svf");
    // vnd.wap.wbmp
    public static final Version1ElectronicDataFormatType IMAGE_VND_WAP_WBMP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.wap.wbmp");
    // vnd.xiff
    public static final Version1ElectronicDataFormatType IMAGE_VND_XIFF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "image/vnd.xiff");
    // CPIM
    public static final Version1ElectronicDataFormatType MESSAGE_CPIM
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/CPIM");
    // delivery-status
    public static final Version1ElectronicDataFormatType MESSAGE_DELIVERY_STATUS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/delivery-status");
    // disposition-notification
    public static final Version1ElectronicDataFormatType MESSAGE_DISPOSITION_NOTIFICATION
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/disposition-notification");
    // example
    public static final Version1ElectronicDataFormatType MESSAGE_EXAMPLE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/example");
    // external-body
    public static final Version1ElectronicDataFormatType MESSAGE_EXTERNAL_BODY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/external-body");
    // feedback-report
    public static final Version1ElectronicDataFormatType MESSAGE_FEEDBACK_REPORT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/feedback-report");
    // global
    public static final Version1ElectronicDataFormatType MESSAGE_GLOBAL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/global");
    // global-delivery-status
    public static final Version1ElectronicDataFormatType MESSAGE_GLOBAL_DELIVERY_STATUS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/global-delivery-status");
    // global-disposition-notification
    public static final Version1ElectronicDataFormatType MESSAGE_GLOBAL_DISPOSITION_NOTIFICATION
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/global-disposition-notification");
    // global-headers
    public static final Version1ElectronicDataFormatType MESSAGE_GLOBAL_HEADERS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/global-headers");
    // http
    public static final Version1ElectronicDataFormatType MESSAGE_HTTP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/http");
    // imdn+xml
    public static final Version1ElectronicDataFormatType MESSAGE_IMDN_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/imdn+xml");
    // news
    public static final Version1ElectronicDataFormatType MESSAGE_NEWS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/news");
    // partial
    public static final Version1ElectronicDataFormatType MESSAGE_PARTIAL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/partial");
    // rfc822
    public static final Version1ElectronicDataFormatType MESSAGE_RFC822
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/rfc822");
    // s-http
    public static final Version1ElectronicDataFormatType MESSAGE_S_HTTP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/s-http");
    // sip
    public static final Version1ElectronicDataFormatType MESSAGE_SIP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/sip");
    // sipfrag
    public static final Version1ElectronicDataFormatType MESSAGE_SIPFRAG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/sipfrag");
    // tracking-status
    public static final Version1ElectronicDataFormatType MESSAGE_TRACKING_STATUS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/tracking-status");
    // vnd.si.simp
    public static final Version1ElectronicDataFormatType MESSAGE_VND_SI_SIMP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "message/vnd.si.simp");
    // example
    public static final Version1ElectronicDataFormatType MODEL_EXAMPLE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "model/example");
    // iges
    public static final Version1ElectronicDataFormatType MODEL_IGES
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "model/iges");
    // mesh
    public static final Version1ElectronicDataFormatType MODEL_MESH
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "model/mesh");
    // vnd.dwf
    public static final Version1ElectronicDataFormatType MODEL_VND_DWF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "model/vnd.dwf");
    // vnd.flatland.3dml
    public static final Version1ElectronicDataFormatType MODEL_VND_FLATLAND_3DML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "model/vnd.flatland.3dml");
    // vnd.gdl
    public static final Version1ElectronicDataFormatType MODEL_VND_GDL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "model/vnd.gdl");
    // vnd.gs-gdl
    public static final Version1ElectronicDataFormatType MODEL_VND_GS_GDL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "model/vnd.gs-gdl");
    // vnd.gtw
    public static final Version1ElectronicDataFormatType MODEL_VND_GTW
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "model/vnd.gtw");
    // vnd.moml+xml
    public static final Version1ElectronicDataFormatType MODEL_VND_MOML_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "model/vnd.moml+xml");
    // vnd.mts
    public static final Version1ElectronicDataFormatType MODEL_VND_MTS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "model/vnd.mts");
    // vnd.parasolid.transmit.binary
    public static final Version1ElectronicDataFormatType MODEL_VND_PARASOLID_TRANSMIT_BINARY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "model/vnd.parasolid.transmit.binary");
    // vnd.parasolid.transmit.text
    public static final Version1ElectronicDataFormatType MODEL_VND_PARASOLID_TRANSMIT_TEXT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "model/vnd.parasolid.transmit.text");
    // vnd.vtu
    public static final Version1ElectronicDataFormatType MODEL_VND_VTU
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "model/vnd.vtu");
    // vrml
    public static final Version1ElectronicDataFormatType MODEL_VRML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "model/vrml");
    // alternative
    public static final Version1ElectronicDataFormatType MULTIPART_ALTERNATIVE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "multipart/alternative");
    // appledouble
    public static final Version1ElectronicDataFormatType MULTIPART_APPLEDOUBLE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "multipart/appledouble");
    // byteranges
    public static final Version1ElectronicDataFormatType MULTIPART_BYTERANGES
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "multipart/byteranges");
    // digest
    public static final Version1ElectronicDataFormatType MULTIPART_DIGEST
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "multipart/digest");
    // encrypted
    public static final Version1ElectronicDataFormatType MULTIPART_ENCRYPTED
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "multipart/encrypted");
    // example
    public static final Version1ElectronicDataFormatType MULTIPART_EXAMPLE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "multipart/example");
    // form-data
    public static final Version1ElectronicDataFormatType MULTIPART_FORM_DATA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "multipart/form-data");
    // header-set
    public static final Version1ElectronicDataFormatType MULTIPART_HEADER_SET
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "multipart/header-set");
    // mixed
    public static final Version1ElectronicDataFormatType MULTIPART_MIXED
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "multipart/mixed");
    // parallel
    public static final Version1ElectronicDataFormatType MULTIPART_PARALLEL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "multipart/parallel");
    // related
    public static final Version1ElectronicDataFormatType MULTIPART_RELATED
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "multipart/related");
    // report
    public static final Version1ElectronicDataFormatType MULTIPART_REPORT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "multipart/report");
    // signed
    public static final Version1ElectronicDataFormatType MULTIPART_SIGNED
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "multipart/signed");
    // voice-message
    public static final Version1ElectronicDataFormatType MULTIPART_VOICE_MESSAGE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "multipart/voice-message");
    // 1d-interleaved-parityfec
    public static final Version1ElectronicDataFormatType TEXT_1D_INTERLEAVED_PARITYFEC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/1d-interleaved-parityfec");
    // calendar
    public static final Version1ElectronicDataFormatType TEXT_CALENDAR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/calendar");
    // css
    public static final Version1ElectronicDataFormatType TEXT_CSS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/css");
    // csv
    public static final Version1ElectronicDataFormatType TEXT_CSV
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/csv");
    // directory
    public static final Version1ElectronicDataFormatType TEXT_DIRECTORY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/directory");
    // dns
    public static final Version1ElectronicDataFormatType TEXT_DNS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/dns");
    // ecmascript
    public static final Version1ElectronicDataFormatType TEXT_ECMASCRIPT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/ecmascript");
    // enriched
    public static final Version1ElectronicDataFormatType TEXT_ENRICHED
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/enriched");
    // example
    public static final Version1ElectronicDataFormatType TEXT_EXAMPLE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/example");
    // html
    public static final Version1ElectronicDataFormatType TEXT_HTML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/html");
    // javascript
    public static final Version1ElectronicDataFormatType TEXT_JAVASCRIPT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/javascript");
    // parityfec
    public static final Version1ElectronicDataFormatType TEXT_PARITYFEC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/parityfec");
    // plain
    public static final Version1ElectronicDataFormatType TEXT_PLAIN
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/plain");
    // prs.fallenstein.rst
    public static final Version1ElectronicDataFormatType TEXT_PRS_FALLENSTEIN_RST
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/prs.fallenstein.rst");
    // prs.lines.tag
    public static final Version1ElectronicDataFormatType TEXT_PRS_LINES_TAG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/prs.lines.tag");
    // RED
    public static final Version1ElectronicDataFormatType TEXT_RED
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/RED");
    // rfc822-headers
    public static final Version1ElectronicDataFormatType TEXT_RFC822_HEADERS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/rfc822-headers");
    // richtext
    public static final Version1ElectronicDataFormatType TEXT_RICHTEXT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/richtext");
    // rtf
    public static final Version1ElectronicDataFormatType TEXT_RTF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/rtf");
    // rtp-enc-aescm128
    public static final Version1ElectronicDataFormatType TEXT_RTP_ENC_AESCM128
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/rtp-enc-aescm128");
    // rtx
    public static final Version1ElectronicDataFormatType TEXT_RTX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/rtx");
    // sgml
    public static final Version1ElectronicDataFormatType TEXT_SGML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/sgml");
    // t140
    public static final Version1ElectronicDataFormatType TEXT_T140
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/t140");
    // tab-separated-values
    public static final Version1ElectronicDataFormatType TEXT_TAB_SEPARATED_VALUES
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/tab-separated-values");
    // troff
    public static final Version1ElectronicDataFormatType TEXT_TROFF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/troff");
    // ulpfec
    public static final Version1ElectronicDataFormatType TEXT_ULPFEC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/ulpfec");
    // uri-list
    public static final Version1ElectronicDataFormatType TEXT_URI_LIST
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/uri-list");
    // vnd.abc
    public static final Version1ElectronicDataFormatType TEXT_VND_ABC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.abc");
    // vnd.curl
    public static final Version1ElectronicDataFormatType TEXT_VND_CURL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.curl");
    // vnd.DMClientScript
    public static final Version1ElectronicDataFormatType TEXT_VND_DMCLIENTSCRIPT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.DMClientScript");
    // vnd.esmertec.theme-descriptor
    public static final Version1ElectronicDataFormatType TEXT_VND_ESMERTEC_THEME_DESCRIPTOR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.esmertec.theme-descriptor");
    // vnd.fly
    public static final Version1ElectronicDataFormatType TEXT_VND_FLY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.fly");
    // vnd.fmi.flexstor
    public static final Version1ElectronicDataFormatType TEXT_VND_FMI_FLEXSTOR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.fmi.flexstor");
    // vnd.graphviz
    public static final Version1ElectronicDataFormatType TEXT_VND_GRAPHVIZ
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.graphviz");
    // vnd.in3d.3dml
    public static final Version1ElectronicDataFormatType TEXT_VND_IN3D_3DML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.in3d.3dml");
    // vnd.in3d.spot
    public static final Version1ElectronicDataFormatType TEXT_VND_IN3D_SPOT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.in3d.spot");
    // vnd.IPTC.NewsML
    public static final Version1ElectronicDataFormatType TEXT_VND_IPTC_NEWSML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.IPTC.NewsML");
    // vnd.IPTC.NITF
    public static final Version1ElectronicDataFormatType TEXT_VND_IPTC_NITF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.IPTC.NITF");
    // vnd.latex-z
    public static final Version1ElectronicDataFormatType TEXT_VND_LATEX_Z
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.latex-z");
    // vnd.motorola.reflex
    public static final Version1ElectronicDataFormatType TEXT_VND_MOTOROLA_REFLEX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.motorola.reflex");
    // vnd.ms-mediapackage
    public static final Version1ElectronicDataFormatType TEXT_VND_MS_MEDIAPACKAGE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.ms-mediapackage");
    // vnd.net2phone.commcenter.command
    public static final Version1ElectronicDataFormatType TEXT_VND_NET2PHONE_COMMCENTER_COMMAND
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.net2phone.commcenter.command");
    // vnd.radisys.msml-basic-layout
    public static final Version1ElectronicDataFormatType TEXT_VND_RADISYS_MSML_BASIC_LAYOUT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.radisys.msml-basic-layout");
    // vnd.si.uricatalogue
    public static final Version1ElectronicDataFormatType TEXT_VND_SI_URICATALOGUE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.si.uricatalogue");
    // vnd.sun.j2me.app-descriptor
    public static final Version1ElectronicDataFormatType TEXT_VND_SUN_J2ME_APP_DESCRIPTOR
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.sun.j2me.app-descriptor");
    // vnd.trolltech.linguist
    public static final Version1ElectronicDataFormatType TEXT_VND_TROLLTECH_LINGUIST
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.trolltech.linguist");
    // vnd.wap.si
    public static final Version1ElectronicDataFormatType TEXT_VND_WAP_SI
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.wap.si");
    // vnd.wap.sl
    public static final Version1ElectronicDataFormatType TEXT_VND_WAP_SL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.wap.sl");
    // vnd.wap.wml
    public static final Version1ElectronicDataFormatType TEXT_VND_WAP_WML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.wap.wml");
    // vnd.wap.wmlscript
    public static final Version1ElectronicDataFormatType TEXT_VND_WAP_WMLSCRIPT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/vnd.wap.wmlscript");
    // xml
    public static final Version1ElectronicDataFormatType TEXT_XML
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/xml");
    // xml-external-parsed-entity
    public static final Version1ElectronicDataFormatType TEXT_XML_EXTERNAL_PARSED_ENTITY
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "text/xml-external-parsed-entity");
    // 1d-interleaved-parityfec
    public static final Version1ElectronicDataFormatType VIDEO_1D_INTERLEAVED_PARITYFEC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/1d-interleaved-parityfec");
    // 3gpp
    public static final Version1ElectronicDataFormatType VIDEO_3GPP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/3gpp");
    // 3gpp2
    public static final Version1ElectronicDataFormatType VIDEO_3GPP2
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/3gpp2");
    // 3gpp-tt
    public static final Version1ElectronicDataFormatType VIDEO_3GPP_TT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/3gpp-tt");
    // BMPEG
    public static final Version1ElectronicDataFormatType VIDEO_BMPEG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/BMPEG");
    // BT656
    public static final Version1ElectronicDataFormatType VIDEO_BT656
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/BT656");
    // CelB
    public static final Version1ElectronicDataFormatType VIDEO_CELB
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/CelB");
    // DV
    public static final Version1ElectronicDataFormatType VIDEO_DV
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/DV");
    // example
    public static final Version1ElectronicDataFormatType VIDEO_EXAMPLE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/example");
    // H261
    public static final Version1ElectronicDataFormatType VIDEO_H261
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/H261");
    // H263
    public static final Version1ElectronicDataFormatType VIDEO_H263
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/H263");
    // H263-1998
    public static final Version1ElectronicDataFormatType VIDEO_H263_1998
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/H263-1998");
    // H263-2000
    public static final Version1ElectronicDataFormatType VIDEO_H263_2000
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/H263-2000");
    // H264
    public static final Version1ElectronicDataFormatType VIDEO_H264
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/H264");
    // H264-RCDO
    public static final Version1ElectronicDataFormatType VIDEO_H264_RCDO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/H264-RCDO");
    // JPEG
    public static final Version1ElectronicDataFormatType VIDEO_JPEG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/JPEG");
    // jpeg2000
    public static final Version1ElectronicDataFormatType VIDEO_JPEG2000
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/jpeg2000");
    // MJ2
    public static final Version1ElectronicDataFormatType VIDEO_MJ2
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/MJ2");
    // MP1S
    public static final Version1ElectronicDataFormatType VIDEO_MP1S
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/MP1S");
    // MP2P
    public static final Version1ElectronicDataFormatType VIDEO_MP2P
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/MP2P");
    // MP2T
    public static final Version1ElectronicDataFormatType VIDEO_MP2T
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/MP2T");
    // mp4
    public static final Version1ElectronicDataFormatType VIDEO_MP4
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/mp4");
    // MP4V-ES
    public static final Version1ElectronicDataFormatType VIDEO_MP4V_ES
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/MP4V-ES");
    // MPV
    public static final Version1ElectronicDataFormatType VIDEO_MPV
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/MPV");
    // mpeg
    public static final Version1ElectronicDataFormatType VIDEO_MPEG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/mpeg");
    // mpeg4-generic
    public static final Version1ElectronicDataFormatType VIDEO_MPEG4_GENERIC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/mpeg4-generic");
    // nv
    public static final Version1ElectronicDataFormatType VIDEO_NV
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/nv");
    // ogg
    public static final Version1ElectronicDataFormatType VIDEO_OGG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/ogg");
    // parityfec
    public static final Version1ElectronicDataFormatType VIDEO_PARITYFEC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/parityfec");
    // pointer
    public static final Version1ElectronicDataFormatType VIDEO_POINTER
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/pointer");
    // quicktime
    public static final Version1ElectronicDataFormatType VIDEO_QUICKTIME
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/quicktime");
    // raw
    public static final Version1ElectronicDataFormatType VIDEO_RAW
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/raw");
    // rtp-enc-aescm128
    public static final Version1ElectronicDataFormatType VIDEO_RTP_ENC_AESCM128
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/rtp-enc-aescm128");
    // rtx
    public static final Version1ElectronicDataFormatType VIDEO_RTX
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/rtx");
    // SMPTE292M
    public static final Version1ElectronicDataFormatType VIDEO_SMPTE292M
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/SMPTE292M");
    // ulpfec
    public static final Version1ElectronicDataFormatType VIDEO_ULPFEC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/ulpfec");
    // vc1
    public static final Version1ElectronicDataFormatType VIDEO_VC1
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vc1");
    // vnd.CCTV
    public static final Version1ElectronicDataFormatType VIDEO_VND_CCTV
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.CCTV");
    // vnd.dece.hd
    public static final Version1ElectronicDataFormatType VIDEO_VND_DECE_HD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.dece.hd");
    // vnd.dece.mobile
    public static final Version1ElectronicDataFormatType VIDEO_VND_DECE_MOBILE
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.dece.mobile");
    // vnd.dece.mp4
    public static final Version1ElectronicDataFormatType VIDEO_VND_DECE_MP4
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.dece.mp4");
    // vnd.dece.pd
    public static final Version1ElectronicDataFormatType VIDEO_VND_DECE_PD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.dece.pd");
    // vnd.dece.sd
    public static final Version1ElectronicDataFormatType VIDEO_VND_DECE_SD
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.dece.sd");
    // vnd.dece.video
    public static final Version1ElectronicDataFormatType VIDEO_VND_DECE_VIDEO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.dece.video");
    // vnd.directv.mpeg
    public static final Version1ElectronicDataFormatType VIDEO_VND_DIRECTV_MPEG
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.directv.mpeg");
    // vnd.directv.mpeg-tts
    public static final Version1ElectronicDataFormatType VIDEO_VND_DIRECTV_MPEG_TTS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.directv.mpeg-tts");
    // vnd.dlna.mpeg-tts
    public static final Version1ElectronicDataFormatType VIDEO_VND_DLNA_MPEG_TTS
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.dlna.mpeg-tts");
    // vnd.fvt
    public static final Version1ElectronicDataFormatType VIDEO_VND_FVT
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.fvt");
    // vnd.hns.video
    public static final Version1ElectronicDataFormatType VIDEO_VND_HNS_VIDEO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.hns.video");
    // vnd.iptvforum.1dparityfec-1010
    public static final Version1ElectronicDataFormatType VIDEO_VND_IPTVFORUM_1DPARITYFEC_1010
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.iptvforum.1dparityfec-1010");
    // vnd.iptvforum.1dparityfec-2005
    public static final Version1ElectronicDataFormatType VIDEO_VND_IPTVFORUM_1DPARITYFEC_2005
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.iptvforum.1dparityfec-2005");
    // vnd.iptvforum.2dparityfec-1010
    public static final Version1ElectronicDataFormatType VIDEO_VND_IPTVFORUM_2DPARITYFEC_1010
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.iptvforum.2dparityfec-1010");
    // vnd.iptvforum.2dparityfec-2005
    public static final Version1ElectronicDataFormatType VIDEO_VND_IPTVFORUM_2DPARITYFEC_2005
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.iptvforum.2dparityfec-2005");
    // vnd.iptvforum.ttsavc
    public static final Version1ElectronicDataFormatType VIDEO_VND_IPTVFORUM_TTSAVC
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.iptvforum.ttsavc");
    // vnd.iptvforum.ttsmpeg2
    public static final Version1ElectronicDataFormatType VIDEO_VND_IPTVFORUM_TTSMPEG2
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.iptvforum.ttsmpeg2");
    // vnd.motorola.video
    public static final Version1ElectronicDataFormatType VIDEO_VND_MOTOROLA_VIDEO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.motorola.video");
    // vnd.motorola.videop
    public static final Version1ElectronicDataFormatType VIDEO_VND_MOTOROLA_VIDEOP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.motorola.videop");
    // vnd.mpegurl
    public static final Version1ElectronicDataFormatType VIDEO_VND_MPEGURL
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.mpegurl");
    // vnd.ms-playready.media.pyv
    public static final Version1ElectronicDataFormatType VIDEO_VND_MS_PLAYREADY_MEDIA_PYV
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.ms-playready.media.pyv");
    // vnd.nokia.interleaved-multimedia
    public static final Version1ElectronicDataFormatType VIDEO_VND_NOKIA_INTERLEAVED_MULTIMEDIA
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.nokia.interleaved-multimedia");
    // vnd.nokia.videovoip
    public static final Version1ElectronicDataFormatType VIDEO_VND_NOKIA_VIDEOVOIP
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.nokia.videovoip");
    // vnd.objectvideo
    public static final Version1ElectronicDataFormatType VIDEO_VND_OBJECTVIDEO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.objectvideo");
    // vnd.sealed.mpeg1
    public static final Version1ElectronicDataFormatType VIDEO_VND_SEALED_MPEG1
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.sealed.mpeg1");
    // vnd.sealed.mpeg4
    public static final Version1ElectronicDataFormatType VIDEO_VND_SEALED_MPEG4
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.sealed.mpeg4");
    // vnd.sealed.swf
    public static final Version1ElectronicDataFormatType VIDEO_VND_SEALED_SWF
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.sealed.swf");
    // vnd.sealedmedia.softseal.mov
    public static final Version1ElectronicDataFormatType VIDEO_VND_SEALEDMEDIA_SOFTSEAL_MOV
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.sealedmedia.softseal.mov");
    // vnd.uvvu.mp4
    public static final Version1ElectronicDataFormatType VIDEO_VND_UVVU_MP4
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.uvvu.mp4");
    // vnd.vivo
    public static final Version1ElectronicDataFormatType VIDEO_VND_VIVO
            = new Version1ElectronicDataFormatType(VERSION_1_ELECTRONIC_DATA_FORMAT_TYPE, "video/vnd.vivo");

    public static void loadAll() {
        LOG.debug("Loading Version1ElectronicDataFormatType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1ElectronicDataFormatType(String scheme, String value) {
        super(scheme, value);
    }

}
