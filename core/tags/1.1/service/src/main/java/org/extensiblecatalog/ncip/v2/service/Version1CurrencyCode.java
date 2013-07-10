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
 * This class enumerates all valid currency codes as of 2011-11-09. 
 */
public class Version1CurrencyCode extends CurrencyCode {

    private static final Logger LOG = Logger.getLogger(Version1CurrencyCode.class);

    public static final String VERSION_1_CURRENCY_CODE
        = "http://www.bsi-global.com/Technical+Information/Publications/_Publications/tig90x.doc";

    // Afghani
    public static final Version1CurrencyCode AFN
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "AFN", 2);
    // Algerian Dinar
    public static final Version1CurrencyCode DZD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "DZD", 2);
    // Argentine Peso
    public static final Version1CurrencyCode ARS
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "ARS", 2);
    // Armenian Dram
    public static final Version1CurrencyCode AMD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "AMD", 2);
    // Aruban Guilder
    public static final Version1CurrencyCode AWG
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "AWG", 2);
    // Australian Dollar
    public static final Version1CurrencyCode AUD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "AUD", 2);
    // Azerbaijanian Manat
    public static final Version1CurrencyCode AZN
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "AZN", 2);
    // Bahamian Dollar
    public static final Version1CurrencyCode BSD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BSD", 2);
    // Bahraini Dinar
    public static final Version1CurrencyCode BHD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BHD", 3);
    // Baht
    public static final Version1CurrencyCode THB
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "THB", 2);
    // Balboa
    public static final Version1CurrencyCode PAB
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "PAB", 2);
    // Barbados Dollar
    public static final Version1CurrencyCode BBD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BBD", 2);
    // Belarussian Ruble
    public static final Version1CurrencyCode BYR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BYR", 0);
    // Belize Dollar
    public static final Version1CurrencyCode BZD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BZD", 2);
    // Bermudian Dollar
    public static final Version1CurrencyCode BMD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BMD", 2);
    // Bolivar Fuerte
    public static final Version1CurrencyCode VEF
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "VEF", 2);
    // Boliviano
    public static final Version1CurrencyCode BOB
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BOB", 2);
    // Brazilian Real
    public static final Version1CurrencyCode BRL
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BRL", 2);
    // Brunei Dollar
    public static final Version1CurrencyCode BND
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BND", 2);
    // Bulgarian Lev
    public static final Version1CurrencyCode BGN
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BGN", 2);
    // Burundi Franc
    public static final Version1CurrencyCode BIF
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BIF", 0);
    // CFA Franc BCEAO
    public static final Version1CurrencyCode XOF
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "XOF", 0);
    // CFA Franc BEAC
    public static final Version1CurrencyCode XAF
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "XAF", 0);
    // CFP Franc
    public static final Version1CurrencyCode XPF
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "XPF", 0);
    // Canadian Dollar
    public static final Version1CurrencyCode CAD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "CAD", 2);
    // Cape Verde Escudo
    public static final Version1CurrencyCode CVE
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "CVE", 2);
    // Cayman Islands Dollar
    public static final Version1CurrencyCode KYD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "KYD", 2);
    // Cedi
    public static final Version1CurrencyCode GHS
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "GHS", 2);
    // Chilean Peso
    public static final Version1CurrencyCode CLP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "CLP", 0);
    // Colombian Peso
    public static final Version1CurrencyCode COP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "COP", 2);
    // Comoro Franc
    public static final Version1CurrencyCode KMF
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "KMF", 0);
    // Congolese Franc
    public static final Version1CurrencyCode CDF
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "CDF", 2);
    // Convertible Mark
    public static final Version1CurrencyCode BAM
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BAM", 2);
    // Cordoba Oro
    public static final Version1CurrencyCode NIO
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "NIO", 2);
    // Costa Rican Colon
    public static final Version1CurrencyCode CRC
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "CRC", 2);
    // Croatian Kuna
    public static final Version1CurrencyCode HRK
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "HRK", 2);
    // Cuban Peso
    public static final Version1CurrencyCode CUP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "CUP", 2);
    // Czech Koruna
    public static final Version1CurrencyCode CZK
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "CZK", 2);
    // Dalasi
    public static final Version1CurrencyCode GMD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "GMD", 2);
    // Danish Krone
    public static final Version1CurrencyCode DKK
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "DKK", 2);
    // Denar
    public static final Version1CurrencyCode MKD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "MKD", 2);
    // Djibouti Franc
    public static final Version1CurrencyCode DJF
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "DJF", 0);
    // Dobra
    public static final Version1CurrencyCode STD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "STD", 2);
    // Dominican Peso
    public static final Version1CurrencyCode DOP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "DOP", 2);
    // Dong
    public static final Version1CurrencyCode VND
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "VND", 0);
    // East Caribbean Dollar
    public static final Version1CurrencyCode XCD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "XCD", 2);
    // Egyptian Pound
    public static final Version1CurrencyCode EGP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "EGP", 2);
    // El Salvador Colon
    public static final Version1CurrencyCode SVC
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "SVC", 2);
    // Ethiopian Birr
    public static final Version1CurrencyCode ETB
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "ETB", 2);
    // Euro
    public static final Version1CurrencyCode EUR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "EUR", 2);
    // Falkland Islands Pound
    public static final Version1CurrencyCode FKP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "FKP", 2);
    // Fiji Dollar
    public static final Version1CurrencyCode FJD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "FJD", 2);
    // Forint
    public static final Version1CurrencyCode HUF
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "HUF", 2);
    // Gibraltar Pound
    public static final Version1CurrencyCode GIP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "GIP", 2);
    // Gourde
    public static final Version1CurrencyCode HTG
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "HTG", 2);
    // Guarani
    public static final Version1CurrencyCode PYG
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "PYG", 0);
    // Guinea Franc
    public static final Version1CurrencyCode GNF
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "GNF", 0);
    // Guyana Dollar
    public static final Version1CurrencyCode GYD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "GYD", 2);
    // Hong Kong Dollar
    public static final Version1CurrencyCode HKD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "HKD", 2);
    // Hryvnia
    public static final Version1CurrencyCode UAH
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "UAH", 2);
    // Iceland Krona
    public static final Version1CurrencyCode ISK
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "ISK", 0);
    // Indian Rupee
    public static final Version1CurrencyCode INR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "INR", 2);
    // Iranian Rial
    public static final Version1CurrencyCode IRR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "IRR", 2);
    // Iraqi Dinar
    public static final Version1CurrencyCode IQD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "IQD", 3);
    // Jamaican Dollar
    public static final Version1CurrencyCode JMD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "JMD", 2);
    // Jordanian Dinar
    public static final Version1CurrencyCode JOD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "JOD", 3);
    // Kenyan Shilling
    public static final Version1CurrencyCode KES
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "KES", 2);
    // Kina
    public static final Version1CurrencyCode PGK
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "PGK", 2);
    // Kip
    public static final Version1CurrencyCode LAK
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "LAK", 2);
    // Kroon - dropped from ISO 4217 list as of 2010-09-10
    public static final Version1CurrencyCode EEK
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "EEK", 2);
    // Kuwaiti Dinar
    public static final Version1CurrencyCode KWD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "KWD", 3);
    // Kwacha
    public static final Version1CurrencyCode MWK
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "MWK", 2);
    // Kwanza
    public static final Version1CurrencyCode AOA
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "AOA", 2);
    // Kyat
    public static final Version1CurrencyCode MMK
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "MMK", 2);
    // Lari
    public static final Version1CurrencyCode GEL
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "GEL", 2);
    // Latvian Lats
    public static final Version1CurrencyCode LVL
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "LVL", 2);
    // Lebanese Pound
    public static final Version1CurrencyCode LBP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "LBP", 2);
    // Lek
    public static final Version1CurrencyCode ALL
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "ALL", 2);
    // Lempira
    public static final Version1CurrencyCode HNL
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "HNL", 2);
    // Leone
    public static final Version1CurrencyCode SLL
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "SLL", 2);
    // Leu
    public static final Version1CurrencyCode RON
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "RON", 2);
    // Liberian Dollar
    public static final Version1CurrencyCode LRD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "LRD", 2);
    // Libyan Dinar
    public static final Version1CurrencyCode LYD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "LYD", 3);
    // Lilangeni
    public static final Version1CurrencyCode SZL
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "SZL", 2);
    // Lithuanian Litas
    public static final Version1CurrencyCode LTL
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "LTL", 2);
    // Loti
    public static final Version1CurrencyCode LSL
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "LSL", 2);
    // Malagasy Ariary
    public static final Version1CurrencyCode MGA
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "MGA", 2);
    // Malaysian Ringgit
    public static final Version1CurrencyCode MYR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "MYR", 2);
    // Mauritius Rupee
    public static final Version1CurrencyCode MUR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "MUR", 2);
    // Metical
    public static final Version1CurrencyCode MZN
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "MZN", 2);
    // Mexican Peso
    public static final Version1CurrencyCode MXN
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "MXN", 2);
    // Mexican Unidad de Inversion (UDI)
    public static final Version1CurrencyCode MXV
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "MXV", 2);
    // Moldovan Leu
    public static final Version1CurrencyCode MDL
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "MDL", 2);
    // Moroccan Dirham
    public static final Version1CurrencyCode MAD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "MAD", 2);
    // Mvdol
    public static final Version1CurrencyCode BOV
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BOV", 2);
    // Naira
    public static final Version1CurrencyCode NGN
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "NGN", 2);
    // Nakfa
    public static final Version1CurrencyCode ERN
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "ERN", 2);
    // Namibia Dollar
    public static final Version1CurrencyCode NAD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "NAD", 2);
    // Nepalese Rupee
    public static final Version1CurrencyCode NPR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "NPR", 2);
    // Netherlands Antilliean Guilder
    public static final Version1CurrencyCode ANG
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "ANG", 2);
    // New Israeli Sheqel
    public static final Version1CurrencyCode ILS
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "ILS", 2);
    // New Manat
    public static final Version1CurrencyCode TMT
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "TMT", 2);
    // New Taiwan Dollar
    public static final Version1CurrencyCode TWD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "TWD", 2);
    // New Zealand Dollar
    public static final Version1CurrencyCode NZD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "NZD", 2);
    // Ngultrum
    public static final Version1CurrencyCode BTN
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BTN", 2);
    // North Korean Won
    public static final Version1CurrencyCode KPW
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "KPW", 2);
    // Norwegian Krone
    public static final Version1CurrencyCode NOK
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "NOK", 2);
    // Nuevo Sol
    public static final Version1CurrencyCode PEN
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "PEN", 2);
    // Ouguiya
    public static final Version1CurrencyCode MRO
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "MRO", 2);
    // Pakistan Rupee
    public static final Version1CurrencyCode PKR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "PKR", 2);
    // Pataca
    public static final Version1CurrencyCode MOP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "MOP", 2);
    // Paanga
    public static final Version1CurrencyCode TOP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "TOP", 2);
    // Peso Convertible
    public static final Version1CurrencyCode CUC
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "CUC", 2);
    // Peso Uruguayo
    public static final Version1CurrencyCode UYU
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "UYU", 2);
    // Philippine Peso
    public static final Version1CurrencyCode PHP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "PHP", 2);
    // Pound Sterling
    public static final Version1CurrencyCode GBP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "GBP", 2);
    // Pula
    public static final Version1CurrencyCode BWP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BWP", 2);
    // Qatari Rial
    public static final Version1CurrencyCode QAR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "QAR", 2);
    // Quetzal
    public static final Version1CurrencyCode GTQ
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "GTQ", 2);
    // Rial Omani
    public static final Version1CurrencyCode OMR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "OMR", 3);
    // Riel
    public static final Version1CurrencyCode KHR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "KHR", 2);
    // Rufiyaa
    public static final Version1CurrencyCode MVR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "MVR", 2);
    // Rupiah
    public static final Version1CurrencyCode IDR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "IDR", 2);
    // Russian Ruble
    public static final Version1CurrencyCode RUB
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "RUB", 2);
    // Rwanda Franc
    public static final Version1CurrencyCode RWF
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "RWF", 0);
    // Saint Helena Pound
    public static final Version1CurrencyCode SHP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "SHP", 2);
    // Saudi Riyal
    public static final Version1CurrencyCode SAR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "SAR", 2);
    // Serbian Dinar
    public static final Version1CurrencyCode RSD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "RSD", 2);
    // Seychelles Rupee
    public static final Version1CurrencyCode SCR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "SCR", 2);
    // Singapore Dollar
    public static final Version1CurrencyCode SGD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "SGD", 2);
    // Solomon Islands Dollar
    public static final Version1CurrencyCode SBD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "SBD", 2);
    // Som
    public static final Version1CurrencyCode KGS
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "KGS", 2);
    // Somali Shilling
    public static final Version1CurrencyCode SOS
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "SOS", 2);
    // Somoni
    public static final Version1CurrencyCode TJS
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "TJS", 2);
    // South African Rand
    public static final Version1CurrencyCode ZAR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "ZAR", 2);
    // South Sudanese Pound
    public static final Version1CurrencyCode SSP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "SSP", 2);
    // Sri Lanka Rupee
    public static final Version1CurrencyCode LKR
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "LKR", 2);
    // Sudanese Pound
    public static final Version1CurrencyCode SDG
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "SDG", 2);
    // Surinam Dollar
    public static final Version1CurrencyCode SRD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "SRD", 2);
    // Swedish Krona
    public static final Version1CurrencyCode SEK
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "SEK", 2);
    // Swiss Franc
    public static final Version1CurrencyCode CHF
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "CHF", 2);
    // Syrian Pound
    public static final Version1CurrencyCode SYP
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "SYP", 2);
    // Taka
    public static final Version1CurrencyCode BDT
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "BDT", 2);
    // Tala
    public static final Version1CurrencyCode WST
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "WST", 2);
    // Tanzanian Shilling
    public static final Version1CurrencyCode TZS
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "TZS", 2);
    // Tenge
    public static final Version1CurrencyCode KZT
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "KZT", 2);
    // Trinidad and Tobago Dollar
    public static final Version1CurrencyCode TTD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "TTD", 2);
    // Tugrik
    public static final Version1CurrencyCode MNT
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "MNT", 2);
    // Tunisian Dinar
    public static final Version1CurrencyCode TND
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "TND", 3);
    // Turkish Lira
    public static final Version1CurrencyCode TRY
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "TRY", 2);
    // UAE Dirham
    public static final Version1CurrencyCode AED
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "AED", 2);
    // US Dollar
    public static final Version1CurrencyCode USD
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "USD", 2);
    // US Dollar (Next day)
    public static final Version1CurrencyCode USN
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "USN", 2);
    // US Dollar (Same day)
    public static final Version1CurrencyCode USS
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "USS", 2);
    // Uganda Shilling
    public static final Version1CurrencyCode UGX
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "UGX", 2);
    // Unidad de Valor Real
    public static final Version1CurrencyCode COU
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "COU", 2);
    // Unidades de fomento
    public static final Version1CurrencyCode CLF
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "CLF", 0);
    // Uruguay Peso en Unidades Indexadas (URUIURUI)
    public static final Version1CurrencyCode UYI
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "UYI", 0);
    // Uzbekistan Sum
    public static final Version1CurrencyCode UZS
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "UZS", 2);
    // Vatu
    public static final Version1CurrencyCode VUV
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "VUV", 0);
    // WIR Euro
    public static final Version1CurrencyCode CHE
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "CHE", 2);
    // WIR Franc
    public static final Version1CurrencyCode CHW
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "CHW", 2);
    // Won
    public static final Version1CurrencyCode KRW
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "KRW", 0);
    // Yemeni Rial
    public static final Version1CurrencyCode YER
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "YER", 2);
    // Yen
    public static final Version1CurrencyCode JPY
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "JPY", 0);
    // Yuan Renminbi
    public static final Version1CurrencyCode CNY
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "CNY", 2);
    // Zambian Kwacha
    public static final Version1CurrencyCode ZMK
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "ZMK", 2);
    // Zimbabwe Dollar
    public static final Version1CurrencyCode ZWL
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "ZWL", 2);
    // Zloty
    public static final Version1CurrencyCode PLN
        = new Version1CurrencyCode(VERSION_1_CURRENCY_CODE, "PLN", 2);

    public static void loadAll() {
        LOG.debug("Loading Version1CurrencyCode.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    protected Version1CurrencyCode(String scheme, String value, int minorUnit) {
        super(scheme, value, minorUnit);
    }
}
