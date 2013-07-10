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
 * Note: This scheme is defined in the NCIP version 1 Implementation Profile 1.
 * Note: This does not include the range qaa through qtz, which is defined as "Reserved for local use".
 * If your implementation requires those you should probably add a LocalUseLanguage class rather than change this.
 */
public class Version1Language extends Language {

    private static final Logger LOG = Logger.getLogger(Version1Language.class);

    public static final String VERSION_1_LANGUAGE
            = "http://lcweb.loc.gov/standards/iso639-2/bibcodes.html";

    // Afar
    public static final Version1Language AAR
            = new Version1Language(VERSION_1_LANGUAGE, "aar");
    // Abkhazian
    public static final Version1Language ABK
            = new Version1Language(VERSION_1_LANGUAGE, "abk");
    // Achinese
    public static final Version1Language ACE
            = new Version1Language(VERSION_1_LANGUAGE, "ace");
    // Acoli
    public static final Version1Language ACH
            = new Version1Language(VERSION_1_LANGUAGE, "ach");
    // Adangme
    public static final Version1Language ADA
            = new Version1Language(VERSION_1_LANGUAGE, "ada");
    // Adyghe; Adygei
    public static final Version1Language ADY
            = new Version1Language(VERSION_1_LANGUAGE, "ady");
    // Afro-Asiatic languages
    public static final Version1Language AFA
            = new Version1Language(VERSION_1_LANGUAGE, "afa");
    // Afrihili
    public static final Version1Language AFH
            = new Version1Language(VERSION_1_LANGUAGE, "afh");
    // Afrikaans
    public static final Version1Language AFR
            = new Version1Language(VERSION_1_LANGUAGE, "afr");
    // Ainu
    public static final Version1Language AIN
            = new Version1Language(VERSION_1_LANGUAGE, "ain");
    // Akan
    public static final Version1Language AKA
            = new Version1Language(VERSION_1_LANGUAGE, "aka");
    // Akkadian
    public static final Version1Language AKK
            = new Version1Language(VERSION_1_LANGUAGE, "akk");
    // Albanian
    public static final Version1Language ALB
            = new Version1Language(VERSION_1_LANGUAGE, "alb");
    // Aleut
    public static final Version1Language ALE
            = new Version1Language(VERSION_1_LANGUAGE, "ale");
    // Algonquian languages
    public static final Version1Language ALG
            = new Version1Language(VERSION_1_LANGUAGE, "alg");
    // Southern Altai
    public static final Version1Language ALT
            = new Version1Language(VERSION_1_LANGUAGE, "alt");
    // Amharic
    public static final Version1Language AMH
            = new Version1Language(VERSION_1_LANGUAGE, "amh");
    // English, Old (ca.450-1100)
    public static final Version1Language ANG
            = new Version1Language(VERSION_1_LANGUAGE, "ang");
    // Angika
    public static final Version1Language ANP
            = new Version1Language(VERSION_1_LANGUAGE, "anp");
    // Apache languages
    public static final Version1Language APA
            = new Version1Language(VERSION_1_LANGUAGE, "apa");
    // Arabic
    public static final Version1Language ARA
            = new Version1Language(VERSION_1_LANGUAGE, "ara");
    // Official Aramaic (700-300 BCE); Imperial Aramaic (700-300 BCE)
    public static final Version1Language ARC
            = new Version1Language(VERSION_1_LANGUAGE, "arc");
    // Aragonese
    public static final Version1Language ARG
            = new Version1Language(VERSION_1_LANGUAGE, "arg");
    // Armenian
    public static final Version1Language ARM
            = new Version1Language(VERSION_1_LANGUAGE, "arm");
    // Mapudungun; Mapuche
    public static final Version1Language ARN
            = new Version1Language(VERSION_1_LANGUAGE, "arn");
    // Arapaho
    public static final Version1Language ARP
            = new Version1Language(VERSION_1_LANGUAGE, "arp");
    // Artificial languages
    public static final Version1Language ART
            = new Version1Language(VERSION_1_LANGUAGE, "art");
    // Arawak
    public static final Version1Language ARW
            = new Version1Language(VERSION_1_LANGUAGE, "arw");
    // Assamese
    public static final Version1Language ASM
            = new Version1Language(VERSION_1_LANGUAGE, "asm");
    // Asturian; Bable; Leonese; Asturleonese
    public static final Version1Language AST
            = new Version1Language(VERSION_1_LANGUAGE, "ast");
    // Athapascan languages
    public static final Version1Language ATH
            = new Version1Language(VERSION_1_LANGUAGE, "ath");
    // Australian languages
    public static final Version1Language AUS
            = new Version1Language(VERSION_1_LANGUAGE, "aus");
    // Avaric
    public static final Version1Language AVA
            = new Version1Language(VERSION_1_LANGUAGE, "ava");
    // Avestan
    public static final Version1Language AVE
            = new Version1Language(VERSION_1_LANGUAGE, "ave");
    // Awadhi
    public static final Version1Language AWA
            = new Version1Language(VERSION_1_LANGUAGE, "awa");
    // Aymara
    public static final Version1Language AYM
            = new Version1Language(VERSION_1_LANGUAGE, "aym");
    // Azerbaijani
    public static final Version1Language AZE
            = new Version1Language(VERSION_1_LANGUAGE, "aze");
    // Banda languages
    public static final Version1Language BAD
            = new Version1Language(VERSION_1_LANGUAGE, "bad");
    // Bamileke languages
    public static final Version1Language BAI
            = new Version1Language(VERSION_1_LANGUAGE, "bai");
    // Bashkir
    public static final Version1Language BAK
            = new Version1Language(VERSION_1_LANGUAGE, "bak");
    // Baluchi
    public static final Version1Language BAL
            = new Version1Language(VERSION_1_LANGUAGE, "bal");
    // Bambara
    public static final Version1Language BAM
            = new Version1Language(VERSION_1_LANGUAGE, "bam");
    // Balinese
    public static final Version1Language BAN
            = new Version1Language(VERSION_1_LANGUAGE, "ban");
    // Basque
    public static final Version1Language BAQ
            = new Version1Language(VERSION_1_LANGUAGE, "baq");
    // Basa
    public static final Version1Language BAS
            = new Version1Language(VERSION_1_LANGUAGE, "bas");
    // Baltic languages
    public static final Version1Language BAT
            = new Version1Language(VERSION_1_LANGUAGE, "bat");
    // Beja; Bedawiyet
    public static final Version1Language BEJ
            = new Version1Language(VERSION_1_LANGUAGE, "bej");
    // Belarusian
    public static final Version1Language BEL
            = new Version1Language(VERSION_1_LANGUAGE, "bel");
    // Bemba
    public static final Version1Language BEM
            = new Version1Language(VERSION_1_LANGUAGE, "bem");
    // Bengali
    public static final Version1Language BEN
            = new Version1Language(VERSION_1_LANGUAGE, "ben");
    // Berber languages
    public static final Version1Language BER
            = new Version1Language(VERSION_1_LANGUAGE, "ber");
    // Bhojpuri
    public static final Version1Language BHO
            = new Version1Language(VERSION_1_LANGUAGE, "bho");
    // Bihari languages
    public static final Version1Language BIH
            = new Version1Language(VERSION_1_LANGUAGE, "bih");
    // Bikol
    public static final Version1Language BIK
            = new Version1Language(VERSION_1_LANGUAGE, "bik");
    // Bini; Edo
    public static final Version1Language BIN
            = new Version1Language(VERSION_1_LANGUAGE, "bin");
    // Bislama
    public static final Version1Language BIS
            = new Version1Language(VERSION_1_LANGUAGE, "bis");
    // Siksika
    public static final Version1Language BLA
            = new Version1Language(VERSION_1_LANGUAGE, "bla");
    // Bantu (Other)
    public static final Version1Language BNT
            = new Version1Language(VERSION_1_LANGUAGE, "bnt");
    // Bosnian
    public static final Version1Language BOS
            = new Version1Language(VERSION_1_LANGUAGE, "bos");
    // Braj
    public static final Version1Language BRA
            = new Version1Language(VERSION_1_LANGUAGE, "bra");
    // Breton
    public static final Version1Language BRE
            = new Version1Language(VERSION_1_LANGUAGE, "bre");
    // Batak languages
    public static final Version1Language BTK
            = new Version1Language(VERSION_1_LANGUAGE, "btk");
    // Buriat
    public static final Version1Language BUA
            = new Version1Language(VERSION_1_LANGUAGE, "bua");
    // Buginese
    public static final Version1Language BUG
            = new Version1Language(VERSION_1_LANGUAGE, "bug");
    // Bulgarian
    public static final Version1Language BUL
            = new Version1Language(VERSION_1_LANGUAGE, "bul");
    // Burmese
    public static final Version1Language BUR
            = new Version1Language(VERSION_1_LANGUAGE, "bur");
    // Blin; Bilin
    public static final Version1Language BYN
            = new Version1Language(VERSION_1_LANGUAGE, "byn");
    // Caddo
    public static final Version1Language CAD
            = new Version1Language(VERSION_1_LANGUAGE, "cad");
    // Central American Indian languages
    public static final Version1Language CAI
            = new Version1Language(VERSION_1_LANGUAGE, "cai");
    // Galibi Carib
    public static final Version1Language CAR
            = new Version1Language(VERSION_1_LANGUAGE, "car");
    // Catalan; Valencian
    public static final Version1Language CAT
            = new Version1Language(VERSION_1_LANGUAGE, "cat");
    // Caucasian languages
    public static final Version1Language CAU
            = new Version1Language(VERSION_1_LANGUAGE, "cau");
    // Cebuano
    public static final Version1Language CEB
            = new Version1Language(VERSION_1_LANGUAGE, "ceb");
    // Celtic languages
    public static final Version1Language CEL
            = new Version1Language(VERSION_1_LANGUAGE, "cel");
    // Chamorro
    public static final Version1Language CHA
            = new Version1Language(VERSION_1_LANGUAGE, "cha");
    // Chibcha
    public static final Version1Language CHB
            = new Version1Language(VERSION_1_LANGUAGE, "chb");
    // Chechen
    public static final Version1Language CHE
            = new Version1Language(VERSION_1_LANGUAGE, "che");
    // Chagatai
    public static final Version1Language CHG
            = new Version1Language(VERSION_1_LANGUAGE, "chg");
    // Chinese
    public static final Version1Language CHI
            = new Version1Language(VERSION_1_LANGUAGE, "chi");
    // Chuukese
    public static final Version1Language CHK
            = new Version1Language(VERSION_1_LANGUAGE, "chk");
    // Mari
    public static final Version1Language CHM
            = new Version1Language(VERSION_1_LANGUAGE, "chm");
    // Chinook jargon
    public static final Version1Language CHN
            = new Version1Language(VERSION_1_LANGUAGE, "chn");
    // Choctaw
    public static final Version1Language CHO
            = new Version1Language(VERSION_1_LANGUAGE, "cho");
    // Chipewyan; Dene Suline
    public static final Version1Language CHP
            = new Version1Language(VERSION_1_LANGUAGE, "chp");
    // Cherokee
    public static final Version1Language CHR
            = new Version1Language(VERSION_1_LANGUAGE, "chr");
    // Church Slavic; Old Slavonic; Church Slavonic; Old Bulgarian; Old Church Slavonic
    public static final Version1Language CHU
            = new Version1Language(VERSION_1_LANGUAGE, "chu");
    // Chuvash
    public static final Version1Language CHV
            = new Version1Language(VERSION_1_LANGUAGE, "chv");
    // Cheyenne
    public static final Version1Language CHY
            = new Version1Language(VERSION_1_LANGUAGE, "chy");
    // Chamic languages
    public static final Version1Language CMC
            = new Version1Language(VERSION_1_LANGUAGE, "cmc");
    // Coptic
    public static final Version1Language COP
            = new Version1Language(VERSION_1_LANGUAGE, "cop");
    // Cornish
    public static final Version1Language COR
            = new Version1Language(VERSION_1_LANGUAGE, "cor");
    // Corsican
    public static final Version1Language COS
            = new Version1Language(VERSION_1_LANGUAGE, "cos");
    // Creoles and pidgins, English based
    public static final Version1Language CPE
            = new Version1Language(VERSION_1_LANGUAGE, "cpe");
    // Creoles and pidgins, French-based
    public static final Version1Language CPF
            = new Version1Language(VERSION_1_LANGUAGE, "cpf");
    // Creoles and pidgins, Portuguese-based
    public static final Version1Language CPP
            = new Version1Language(VERSION_1_LANGUAGE, "cpp");
    // Cree
    public static final Version1Language CRE
            = new Version1Language(VERSION_1_LANGUAGE, "cre");
    // Crimean Tatar; Crimean Turkish
    public static final Version1Language CRH
            = new Version1Language(VERSION_1_LANGUAGE, "crh");
    // Creoles and pidgins
    public static final Version1Language CRP
            = new Version1Language(VERSION_1_LANGUAGE, "crp");
    // Kashubian
    public static final Version1Language CSB
            = new Version1Language(VERSION_1_LANGUAGE, "csb");
    // Cushitic languages
    public static final Version1Language CUS
            = new Version1Language(VERSION_1_LANGUAGE, "cus");
    // Czech
    public static final Version1Language CZE
            = new Version1Language(VERSION_1_LANGUAGE, "cze");
    // Dakota
    public static final Version1Language DAK
            = new Version1Language(VERSION_1_LANGUAGE, "dak");
    // Danish
    public static final Version1Language DAN
            = new Version1Language(VERSION_1_LANGUAGE, "dan");
    // Dargwa
    public static final Version1Language DAR
            = new Version1Language(VERSION_1_LANGUAGE, "dar");
    // Land Dayak languages
    public static final Version1Language DAY
            = new Version1Language(VERSION_1_LANGUAGE, "day");
    // Delaware
    public static final Version1Language DEL
            = new Version1Language(VERSION_1_LANGUAGE, "del");
    // Slave (Athapascan)
    public static final Version1Language DEN
            = new Version1Language(VERSION_1_LANGUAGE, "den");
    // Dogrib
    public static final Version1Language DGR
            = new Version1Language(VERSION_1_LANGUAGE, "dgr");
    // Dinka
    public static final Version1Language DIN
            = new Version1Language(VERSION_1_LANGUAGE, "din");
    // Divehi; Dhivehi; Maldivian
    public static final Version1Language DIV
            = new Version1Language(VERSION_1_LANGUAGE, "div");
    // Dogri
    public static final Version1Language DOI
            = new Version1Language(VERSION_1_LANGUAGE, "doi");
    // Dravidian languages
    public static final Version1Language DRA
            = new Version1Language(VERSION_1_LANGUAGE, "dra");
    // Lower Sorbian
    public static final Version1Language DSB
            = new Version1Language(VERSION_1_LANGUAGE, "dsb");
    // Duala
    public static final Version1Language DUA
            = new Version1Language(VERSION_1_LANGUAGE, "dua");
    // Dutch, Middle (ca.1050-1350)
    public static final Version1Language DUM
            = new Version1Language(VERSION_1_LANGUAGE, "dum");
    // Dutch; Flemish
    public static final Version1Language DUT
            = new Version1Language(VERSION_1_LANGUAGE, "dut");
    // Dyula
    public static final Version1Language DYU
            = new Version1Language(VERSION_1_LANGUAGE, "dyu");
    // Dzongkha
    public static final Version1Language DZO
            = new Version1Language(VERSION_1_LANGUAGE, "dzo");
    // Efik
    public static final Version1Language EFI
            = new Version1Language(VERSION_1_LANGUAGE, "efi");
    // Egyptian (Ancient)
    public static final Version1Language EGY
            = new Version1Language(VERSION_1_LANGUAGE, "egy");
    // Ekajuk
    public static final Version1Language EKA
            = new Version1Language(VERSION_1_LANGUAGE, "eka");
    // Elamite
    public static final Version1Language ELX
            = new Version1Language(VERSION_1_LANGUAGE, "elx");
    // English
    public static final Version1Language ENG
            = new Version1Language(VERSION_1_LANGUAGE, "eng");
    // English, Middle (1100-1500)
    public static final Version1Language ENM
            = new Version1Language(VERSION_1_LANGUAGE, "enm");
    // Esperanto
    public static final Version1Language EPO
            = new Version1Language(VERSION_1_LANGUAGE, "epo");
    // Estonian
    public static final Version1Language EST
            = new Version1Language(VERSION_1_LANGUAGE, "est");
    // Ewe
    public static final Version1Language EWE
            = new Version1Language(VERSION_1_LANGUAGE, "ewe");
    // Ewondo
    public static final Version1Language EWO
            = new Version1Language(VERSION_1_LANGUAGE, "ewo");
    // Fang
    public static final Version1Language FAN
            = new Version1Language(VERSION_1_LANGUAGE, "fan");
    // Faroese
    public static final Version1Language FAO
            = new Version1Language(VERSION_1_LANGUAGE, "fao");
    // Fanti
    public static final Version1Language FAT
            = new Version1Language(VERSION_1_LANGUAGE, "fat");
    // Fijian
    public static final Version1Language FIJ
            = new Version1Language(VERSION_1_LANGUAGE, "fij");
    // Filipino; Pilipino
    public static final Version1Language FIL
            = new Version1Language(VERSION_1_LANGUAGE, "fil");
    // Finnish
    public static final Version1Language FIN
            = new Version1Language(VERSION_1_LANGUAGE, "fin");
    // Finno-Ugrian languages
    public static final Version1Language FIU
            = new Version1Language(VERSION_1_LANGUAGE, "fiu");
    // Fon
    public static final Version1Language FON
            = new Version1Language(VERSION_1_LANGUAGE, "fon");
    // French
    public static final Version1Language FRE
            = new Version1Language(VERSION_1_LANGUAGE, "fre");
    // French, Middle (ca.1400-1600)
    public static final Version1Language FRM
            = new Version1Language(VERSION_1_LANGUAGE, "frm");
    // French, Old (842-ca.1400)
    public static final Version1Language FRO
            = new Version1Language(VERSION_1_LANGUAGE, "fro");
    // Northern Frisian
    public static final Version1Language FRR
            = new Version1Language(VERSION_1_LANGUAGE, "frr");
    // Eastern Frisian
    public static final Version1Language FRS
            = new Version1Language(VERSION_1_LANGUAGE, "frs");
    // Western Frisian
    public static final Version1Language FRY
            = new Version1Language(VERSION_1_LANGUAGE, "fry");
    // Fulah
    public static final Version1Language FUL
            = new Version1Language(VERSION_1_LANGUAGE, "ful");
    // Friulian
    public static final Version1Language FUR
            = new Version1Language(VERSION_1_LANGUAGE, "fur");
    // Ga
    public static final Version1Language GAA
            = new Version1Language(VERSION_1_LANGUAGE, "gaa");
    // Gayo
    public static final Version1Language GAY
            = new Version1Language(VERSION_1_LANGUAGE, "gay");
    // Gbaya
    public static final Version1Language GBA
            = new Version1Language(VERSION_1_LANGUAGE, "gba");
    // Germanic languages
    public static final Version1Language GEM
            = new Version1Language(VERSION_1_LANGUAGE, "gem");
    // Georgian
    public static final Version1Language GEO
            = new Version1Language(VERSION_1_LANGUAGE, "geo");
    // German
    public static final Version1Language GER
            = new Version1Language(VERSION_1_LANGUAGE, "ger");
    // Geez
    public static final Version1Language GEZ
            = new Version1Language(VERSION_1_LANGUAGE, "gez");
    // Gilbertese
    public static final Version1Language GIL
            = new Version1Language(VERSION_1_LANGUAGE, "gil");
    // Gaelic; Scottish Gaelic
    public static final Version1Language GLA
            = new Version1Language(VERSION_1_LANGUAGE, "gla");
    // Irish
    public static final Version1Language GLE
            = new Version1Language(VERSION_1_LANGUAGE, "gle");
    // Galician
    public static final Version1Language GLG
            = new Version1Language(VERSION_1_LANGUAGE, "glg");
    // Manx
    public static final Version1Language GLV
            = new Version1Language(VERSION_1_LANGUAGE, "glv");
    // German, Middle High (ca.1050-1500)
    public static final Version1Language GMH
            = new Version1Language(VERSION_1_LANGUAGE, "gmh");
    // German, Old High (ca.750-1050)
    public static final Version1Language GOH
            = new Version1Language(VERSION_1_LANGUAGE, "goh");
    // Gondi
    public static final Version1Language GON
            = new Version1Language(VERSION_1_LANGUAGE, "gon");
    // Gorontalo
    public static final Version1Language GOR
            = new Version1Language(VERSION_1_LANGUAGE, "gor");
    // Gothic
    public static final Version1Language GOT
            = new Version1Language(VERSION_1_LANGUAGE, "got");
    // Grebo
    public static final Version1Language GRB
            = new Version1Language(VERSION_1_LANGUAGE, "grb");
    // Greek, Ancient (to 1453)
    public static final Version1Language GRC
            = new Version1Language(VERSION_1_LANGUAGE, "grc");
    // Greek, Modern (1453-)
    public static final Version1Language GRE
            = new Version1Language(VERSION_1_LANGUAGE, "gre");
    // Guarani
    public static final Version1Language GRN
            = new Version1Language(VERSION_1_LANGUAGE, "grn");
    // Swiss German; Alemannic; Alsatian
    public static final Version1Language GSW
            = new Version1Language(VERSION_1_LANGUAGE, "gsw");
    // Gujarati
    public static final Version1Language GUJ
            = new Version1Language(VERSION_1_LANGUAGE, "guj");
    // Gwich'in
    public static final Version1Language GWI
            = new Version1Language(VERSION_1_LANGUAGE, "gwi");
    // Haida
    public static final Version1Language HAI
            = new Version1Language(VERSION_1_LANGUAGE, "hai");
    // Haitian; Haitian Creole
    public static final Version1Language HAT
            = new Version1Language(VERSION_1_LANGUAGE, "hat");
    // Hausa
    public static final Version1Language HAU
            = new Version1Language(VERSION_1_LANGUAGE, "hau");
    // Hawaiian
    public static final Version1Language HAW
            = new Version1Language(VERSION_1_LANGUAGE, "haw");
    // Hebrew
    public static final Version1Language HEB
            = new Version1Language(VERSION_1_LANGUAGE, "heb");
    // Herero
    public static final Version1Language HER
            = new Version1Language(VERSION_1_LANGUAGE, "her");
    // Hiligaynon
    public static final Version1Language HIL
            = new Version1Language(VERSION_1_LANGUAGE, "hil");
    // Himachali languages; Western Pahari languages
    public static final Version1Language HIM
            = new Version1Language(VERSION_1_LANGUAGE, "him");
    // Hindi
    public static final Version1Language HIN
            = new Version1Language(VERSION_1_LANGUAGE, "hin");
    // Hittite
    public static final Version1Language HIT
            = new Version1Language(VERSION_1_LANGUAGE, "hit");
    // Hmong; Mong
    public static final Version1Language HMN
            = new Version1Language(VERSION_1_LANGUAGE, "hmn");
    // Hiri Motu
    public static final Version1Language HMO
            = new Version1Language(VERSION_1_LANGUAGE, "hmo");
    // Croatian
    public static final Version1Language HRV
            = new Version1Language(VERSION_1_LANGUAGE, "hrv");
    // Upper Sorbian
    public static final Version1Language HSB
            = new Version1Language(VERSION_1_LANGUAGE, "hsb");
    // Hungarian
    public static final Version1Language HUN
            = new Version1Language(VERSION_1_LANGUAGE, "hun");
    // Hupa
    public static final Version1Language HUP
            = new Version1Language(VERSION_1_LANGUAGE, "hup");
    // Iban
    public static final Version1Language IBA
            = new Version1Language(VERSION_1_LANGUAGE, "iba");
    // Igbo
    public static final Version1Language IBO
            = new Version1Language(VERSION_1_LANGUAGE, "ibo");
    // Icelandic
    public static final Version1Language ICE
            = new Version1Language(VERSION_1_LANGUAGE, "ice");
    // Ido
    public static final Version1Language IDO
            = new Version1Language(VERSION_1_LANGUAGE, "ido");
    // Sichuan Yi; Nuosu
    public static final Version1Language III
            = new Version1Language(VERSION_1_LANGUAGE, "iii");
    // Ijo languages
    public static final Version1Language IJO
            = new Version1Language(VERSION_1_LANGUAGE, "ijo");
    // Inuktitut
    public static final Version1Language IKU
            = new Version1Language(VERSION_1_LANGUAGE, "iku");
    // Interlingue; Occidental
    public static final Version1Language ILE
            = new Version1Language(VERSION_1_LANGUAGE, "ile");
    // Iloko
    public static final Version1Language ILO
            = new Version1Language(VERSION_1_LANGUAGE, "ilo");
    // Interlingua (International Auxiliary Language Association)
    public static final Version1Language INA
            = new Version1Language(VERSION_1_LANGUAGE, "ina");
    // Indic languages
    public static final Version1Language INC
            = new Version1Language(VERSION_1_LANGUAGE, "inc");
    // Indonesian
    public static final Version1Language IND
            = new Version1Language(VERSION_1_LANGUAGE, "ind");
    // Indo-European languages
    public static final Version1Language INE
            = new Version1Language(VERSION_1_LANGUAGE, "ine");
    // Ingush
    public static final Version1Language INH
            = new Version1Language(VERSION_1_LANGUAGE, "inh");
    // Inupiaq
    public static final Version1Language IPK
            = new Version1Language(VERSION_1_LANGUAGE, "ipk");
    // Iranian languages
    public static final Version1Language IRA
            = new Version1Language(VERSION_1_LANGUAGE, "ira");
    // Iroquoian languages
    public static final Version1Language IRO
            = new Version1Language(VERSION_1_LANGUAGE, "iro");
    // Italian
    public static final Version1Language ITA
            = new Version1Language(VERSION_1_LANGUAGE, "ita");
    // Javanese
    public static final Version1Language JAV
            = new Version1Language(VERSION_1_LANGUAGE, "jav");
    // Lojban
    public static final Version1Language JBO
            = new Version1Language(VERSION_1_LANGUAGE, "jbo");
    // Japanese
    public static final Version1Language JPN
            = new Version1Language(VERSION_1_LANGUAGE, "jpn");
    // Judeo-Persian
    public static final Version1Language JPR
            = new Version1Language(VERSION_1_LANGUAGE, "jpr");
    // Judeo-Arabic
    public static final Version1Language JRB
            = new Version1Language(VERSION_1_LANGUAGE, "jrb");
    // Kara-Kalpak
    public static final Version1Language KAA
            = new Version1Language(VERSION_1_LANGUAGE, "kaa");
    // Kabyle
    public static final Version1Language KAB
            = new Version1Language(VERSION_1_LANGUAGE, "kab");
    // Kachin; Jingpho
    public static final Version1Language KAC
            = new Version1Language(VERSION_1_LANGUAGE, "kac");
    // Kalaallisut; Greenlandic
    public static final Version1Language KAL
            = new Version1Language(VERSION_1_LANGUAGE, "kal");
    // Kamba
    public static final Version1Language KAM
            = new Version1Language(VERSION_1_LANGUAGE, "kam");
    // Kannada
    public static final Version1Language KAN
            = new Version1Language(VERSION_1_LANGUAGE, "kan");
    // Karen languages
    public static final Version1Language KAR
            = new Version1Language(VERSION_1_LANGUAGE, "kar");
    // Kashmiri
    public static final Version1Language KAS
            = new Version1Language(VERSION_1_LANGUAGE, "kas");
    // Kanuri
    public static final Version1Language KAU
            = new Version1Language(VERSION_1_LANGUAGE, "kau");
    // Kawi
    public static final Version1Language KAW
            = new Version1Language(VERSION_1_LANGUAGE, "kaw");
    // Kazakh
    public static final Version1Language KAZ
            = new Version1Language(VERSION_1_LANGUAGE, "kaz");
    // Kabardian
    public static final Version1Language KBD
            = new Version1Language(VERSION_1_LANGUAGE, "kbd");
    // Khasi
    public static final Version1Language KHA
            = new Version1Language(VERSION_1_LANGUAGE, "kha");
    // Khoisan languages
    public static final Version1Language KHI
            = new Version1Language(VERSION_1_LANGUAGE, "khi");
    // Central Khmer
    public static final Version1Language KHM
            = new Version1Language(VERSION_1_LANGUAGE, "khm");
    // Khotanese; Sakan
    public static final Version1Language KHO
            = new Version1Language(VERSION_1_LANGUAGE, "kho");
    // Kikuyu; Gikuyu
    public static final Version1Language KIK
            = new Version1Language(VERSION_1_LANGUAGE, "kik");
    // Kinyarwanda
    public static final Version1Language KIN
            = new Version1Language(VERSION_1_LANGUAGE, "kin");
    // Kirghiz; Kyrgyz
    public static final Version1Language KIR
            = new Version1Language(VERSION_1_LANGUAGE, "kir");
    // Kimbundu
    public static final Version1Language KMB
            = new Version1Language(VERSION_1_LANGUAGE, "kmb");
    // Konkani
    public static final Version1Language KOK
            = new Version1Language(VERSION_1_LANGUAGE, "kok");
    // Komi
    public static final Version1Language KOM
            = new Version1Language(VERSION_1_LANGUAGE, "kom");
    // Kongo
    public static final Version1Language KON
            = new Version1Language(VERSION_1_LANGUAGE, "kon");
    // Korean
    public static final Version1Language KOR
            = new Version1Language(VERSION_1_LANGUAGE, "kor");
    // Kosraean
    public static final Version1Language KOS
            = new Version1Language(VERSION_1_LANGUAGE, "kos");
    // Kpelle
    public static final Version1Language KPE
            = new Version1Language(VERSION_1_LANGUAGE, "kpe");
    // Karachay-Balkar
    public static final Version1Language KRC
            = new Version1Language(VERSION_1_LANGUAGE, "krc");
    // Karelian
    public static final Version1Language KRL
            = new Version1Language(VERSION_1_LANGUAGE, "krl");
    // Kru languages
    public static final Version1Language KRO
            = new Version1Language(VERSION_1_LANGUAGE, "kro");
    // Kurukh
    public static final Version1Language KRU
            = new Version1Language(VERSION_1_LANGUAGE, "kru");
    // Kuanyama; Kwanyama
    public static final Version1Language KUA
            = new Version1Language(VERSION_1_LANGUAGE, "kua");
    // Kumyk
    public static final Version1Language KUM
            = new Version1Language(VERSION_1_LANGUAGE, "kum");
    // Kurdish
    public static final Version1Language KUR
            = new Version1Language(VERSION_1_LANGUAGE, "kur");
    // Kutenai
    public static final Version1Language KUT
            = new Version1Language(VERSION_1_LANGUAGE, "kut");
    // Ladino
    public static final Version1Language LAD
            = new Version1Language(VERSION_1_LANGUAGE, "lad");
    // Lahnda
    public static final Version1Language LAH
            = new Version1Language(VERSION_1_LANGUAGE, "lah");
    // Lamba
    public static final Version1Language LAM
            = new Version1Language(VERSION_1_LANGUAGE, "lam");
    // Lao
    public static final Version1Language LAO
            = new Version1Language(VERSION_1_LANGUAGE, "lao");
    // Latin
    public static final Version1Language LAT
            = new Version1Language(VERSION_1_LANGUAGE, "lat");
    // Latvian
    public static final Version1Language LAV
            = new Version1Language(VERSION_1_LANGUAGE, "lav");
    // Lezghian
    public static final Version1Language LEZ
            = new Version1Language(VERSION_1_LANGUAGE, "lez");
    // Limburgan; Limburger; Limburgish
    public static final Version1Language LIM
            = new Version1Language(VERSION_1_LANGUAGE, "lim");
    // Lingala
    public static final Version1Language LIN
            = new Version1Language(VERSION_1_LANGUAGE, "lin");
    // Lithuanian
    public static final Version1Language LIT
            = new Version1Language(VERSION_1_LANGUAGE, "lit");
    // Mongo
    public static final Version1Language LOL
            = new Version1Language(VERSION_1_LANGUAGE, "lol");
    // Lozi
    public static final Version1Language LOZ
            = new Version1Language(VERSION_1_LANGUAGE, "loz");
    // Luxembourgish; Letzeburgesch
    public static final Version1Language LTZ
            = new Version1Language(VERSION_1_LANGUAGE, "ltz");
    // Luba-Lulua
    public static final Version1Language LUA
            = new Version1Language(VERSION_1_LANGUAGE, "lua");
    // Luba-Katanga
    public static final Version1Language LUB
            = new Version1Language(VERSION_1_LANGUAGE, "lub");
    // Ganda
    public static final Version1Language LUG
            = new Version1Language(VERSION_1_LANGUAGE, "lug");
    // Luiseno
    public static final Version1Language LUI
            = new Version1Language(VERSION_1_LANGUAGE, "lui");
    // Lunda
    public static final Version1Language LUN
            = new Version1Language(VERSION_1_LANGUAGE, "lun");
    // Luo (Kenya and Tanzania)
    public static final Version1Language LUO
            = new Version1Language(VERSION_1_LANGUAGE, "luo");
    // Lushai
    public static final Version1Language LUS
            = new Version1Language(VERSION_1_LANGUAGE, "lus");
    // Macedonian
    public static final Version1Language MAC
            = new Version1Language(VERSION_1_LANGUAGE, "mac");
    // Madurese
    public static final Version1Language MAD
            = new Version1Language(VERSION_1_LANGUAGE, "mad");
    // Magahi
    public static final Version1Language MAG
            = new Version1Language(VERSION_1_LANGUAGE, "mag");
    // Marshallese
    public static final Version1Language MAH
            = new Version1Language(VERSION_1_LANGUAGE, "mah");
    // Maithili
    public static final Version1Language MAI
            = new Version1Language(VERSION_1_LANGUAGE, "mai");
    // Makasar
    public static final Version1Language MAK
            = new Version1Language(VERSION_1_LANGUAGE, "mak");
    // Malayalam
    public static final Version1Language MAL
            = new Version1Language(VERSION_1_LANGUAGE, "mal");
    // Mandingo
    public static final Version1Language MAN
            = new Version1Language(VERSION_1_LANGUAGE, "man");
    // Maori
    public static final Version1Language MAO
            = new Version1Language(VERSION_1_LANGUAGE, "mao");
    // Austronesian languages
    public static final Version1Language MAP
            = new Version1Language(VERSION_1_LANGUAGE, "map");
    // Marathi
    public static final Version1Language MAR
            = new Version1Language(VERSION_1_LANGUAGE, "mar");
    // Masai
    public static final Version1Language MAS
            = new Version1Language(VERSION_1_LANGUAGE, "mas");
    // Malay
    public static final Version1Language MAY
            = new Version1Language(VERSION_1_LANGUAGE, "may");
    // Moksha
    public static final Version1Language MDF
            = new Version1Language(VERSION_1_LANGUAGE, "mdf");
    // Mandar
    public static final Version1Language MDR
            = new Version1Language(VERSION_1_LANGUAGE, "mdr");
    // Mende
    public static final Version1Language MEN
            = new Version1Language(VERSION_1_LANGUAGE, "men");
    // Irish, Middle (900-1200)
    public static final Version1Language MGA
            = new Version1Language(VERSION_1_LANGUAGE, "mga");
    // Mi'kmaq; Micmac
    public static final Version1Language MIC
            = new Version1Language(VERSION_1_LANGUAGE, "mic");
    // Minangkabau
    public static final Version1Language MIN
            = new Version1Language(VERSION_1_LANGUAGE, "min");
    // Uncoded languages
    public static final Version1Language MIS
            = new Version1Language(VERSION_1_LANGUAGE, "mis");
    // Mon-Khmer languages
    public static final Version1Language MKH
            = new Version1Language(VERSION_1_LANGUAGE, "mkh");
    // Malagasy
    public static final Version1Language MLG
            = new Version1Language(VERSION_1_LANGUAGE, "mlg");
    // Maltese
    public static final Version1Language MLT
            = new Version1Language(VERSION_1_LANGUAGE, "mlt");
    // Manchu
    public static final Version1Language MNC
            = new Version1Language(VERSION_1_LANGUAGE, "mnc");
    // Manipuri
    public static final Version1Language MNI
            = new Version1Language(VERSION_1_LANGUAGE, "mni");
    // Manobo languages
    public static final Version1Language MNO
            = new Version1Language(VERSION_1_LANGUAGE, "mno");
    // Mohawk
    public static final Version1Language MOH
            = new Version1Language(VERSION_1_LANGUAGE, "moh");
    // Mongolian
    public static final Version1Language MON
            = new Version1Language(VERSION_1_LANGUAGE, "mon");
    // Mossi
    public static final Version1Language MOS
            = new Version1Language(VERSION_1_LANGUAGE, "mos");
    // Multiple languages
    public static final Version1Language MUL
            = new Version1Language(VERSION_1_LANGUAGE, "mul");
    // Munda languages
    public static final Version1Language MUN
            = new Version1Language(VERSION_1_LANGUAGE, "mun");
    // Creek
    public static final Version1Language MUS
            = new Version1Language(VERSION_1_LANGUAGE, "mus");
    // Mirandese
    public static final Version1Language MWL
            = new Version1Language(VERSION_1_LANGUAGE, "mwl");
    // Marwari
    public static final Version1Language MWR
            = new Version1Language(VERSION_1_LANGUAGE, "mwr");
    // Mayan languages
    public static final Version1Language MYN
            = new Version1Language(VERSION_1_LANGUAGE, "myn");
    // Erzya
    public static final Version1Language MYV
            = new Version1Language(VERSION_1_LANGUAGE, "myv");
    // Nahuatl languages
    public static final Version1Language NAH
            = new Version1Language(VERSION_1_LANGUAGE, "nah");
    // North American Indian languages
    public static final Version1Language NAI
            = new Version1Language(VERSION_1_LANGUAGE, "nai");
    // Neapolitan
    public static final Version1Language NAP
            = new Version1Language(VERSION_1_LANGUAGE, "nap");
    // Nauru
    public static final Version1Language NAU
            = new Version1Language(VERSION_1_LANGUAGE, "nau");
    // Navajo; Navaho
    public static final Version1Language NAV
            = new Version1Language(VERSION_1_LANGUAGE, "nav");
    // Ndebele, South; South Ndebele
    public static final Version1Language NBL
            = new Version1Language(VERSION_1_LANGUAGE, "nbl");
    // Ndebele, North; North Ndebele
    public static final Version1Language NDE
            = new Version1Language(VERSION_1_LANGUAGE, "nde");
    // Ndonga
    public static final Version1Language NDO
            = new Version1Language(VERSION_1_LANGUAGE, "ndo");
    // Low German; Low Saxon; German, Low; Saxon, Low
    public static final Version1Language NDS
            = new Version1Language(VERSION_1_LANGUAGE, "nds");
    // Nepali
    public static final Version1Language NEP
            = new Version1Language(VERSION_1_LANGUAGE, "nep");
    // Nepal Bhasa; Newari
    public static final Version1Language NEW
            = new Version1Language(VERSION_1_LANGUAGE, "new");
    // Nias
    public static final Version1Language NIA
            = new Version1Language(VERSION_1_LANGUAGE, "nia");
    // Niger-Kordofanian languages
    public static final Version1Language NIC
            = new Version1Language(VERSION_1_LANGUAGE, "nic");
    // Niuean
    public static final Version1Language NIU
            = new Version1Language(VERSION_1_LANGUAGE, "niu");
    // Norwegian Nynorsk; Nynorsk, Norwegian
    public static final Version1Language NNO
            = new Version1Language(VERSION_1_LANGUAGE, "nno");
    // Bokml, Norwegian; Norwegian Bokml
    public static final Version1Language NOB
            = new Version1Language(VERSION_1_LANGUAGE, "nob");
    // Nogai
    public static final Version1Language NOG
            = new Version1Language(VERSION_1_LANGUAGE, "nog");
    // Norse, Old
    public static final Version1Language NON
            = new Version1Language(VERSION_1_LANGUAGE, "non");
    // Norwegian
    public static final Version1Language NOR
            = new Version1Language(VERSION_1_LANGUAGE, "nor");
    // N'Ko
    public static final Version1Language NQO
            = new Version1Language(VERSION_1_LANGUAGE, "nqo");
    // Pedi; Sepedi; Northern Sotho
    public static final Version1Language NSO
            = new Version1Language(VERSION_1_LANGUAGE, "nso");
    // Nubian languages
    public static final Version1Language NUB
            = new Version1Language(VERSION_1_LANGUAGE, "nub");
    // Classical Newari; Old Newari; Classical Nepal Bhasa
    public static final Version1Language NWC
            = new Version1Language(VERSION_1_LANGUAGE, "nwc");
    // Chichewa; Chewa; Nyanja
    public static final Version1Language NYA
            = new Version1Language(VERSION_1_LANGUAGE, "nya");
    // Nyamwezi
    public static final Version1Language NYM
            = new Version1Language(VERSION_1_LANGUAGE, "nym");
    // Nyankole
    public static final Version1Language NYN
            = new Version1Language(VERSION_1_LANGUAGE, "nyn");
    // Nyoro
    public static final Version1Language NYO
            = new Version1Language(VERSION_1_LANGUAGE, "nyo");
    // Nzima
    public static final Version1Language NZI
            = new Version1Language(VERSION_1_LANGUAGE, "nzi");
    // Occitan (post 1500); Provenal
    public static final Version1Language OCI
            = new Version1Language(VERSION_1_LANGUAGE, "oci");
    // Ojibwa
    public static final Version1Language OJI
            = new Version1Language(VERSION_1_LANGUAGE, "oji");
    // Oriya
    public static final Version1Language ORI
            = new Version1Language(VERSION_1_LANGUAGE, "ori");
    // Oromo
    public static final Version1Language ORM
            = new Version1Language(VERSION_1_LANGUAGE, "orm");
    // Osage
    public static final Version1Language OSA
            = new Version1Language(VERSION_1_LANGUAGE, "osa");
    // Ossetian; Ossetic
    public static final Version1Language OSS
            = new Version1Language(VERSION_1_LANGUAGE, "oss");
    // Turkish, Ottoman (1500-1928)
    public static final Version1Language OTA
            = new Version1Language(VERSION_1_LANGUAGE, "ota");
    // Otomian languages
    public static final Version1Language OTO
            = new Version1Language(VERSION_1_LANGUAGE, "oto");
    // Papuan languages
    public static final Version1Language PAA
            = new Version1Language(VERSION_1_LANGUAGE, "paa");
    // Pangasinan
    public static final Version1Language PAG
            = new Version1Language(VERSION_1_LANGUAGE, "pag");
    // Pahlavi
    public static final Version1Language PAL
            = new Version1Language(VERSION_1_LANGUAGE, "pal");
    // Pampanga; Kapampangan
    public static final Version1Language PAM
            = new Version1Language(VERSION_1_LANGUAGE, "pam");
    // Panjabi; Punjabi
    public static final Version1Language PAN
            = new Version1Language(VERSION_1_LANGUAGE, "pan");
    // Papiamento
    public static final Version1Language PAP
            = new Version1Language(VERSION_1_LANGUAGE, "pap");
    // Palauan
    public static final Version1Language PAU
            = new Version1Language(VERSION_1_LANGUAGE, "pau");
    // Persian, Old (ca.600-400 B.C.)
    public static final Version1Language PEO
            = new Version1Language(VERSION_1_LANGUAGE, "peo");
    // Persian
    public static final Version1Language PER
            = new Version1Language(VERSION_1_LANGUAGE, "per");
    // Philippine languages
    public static final Version1Language PHI
            = new Version1Language(VERSION_1_LANGUAGE, "phi");
    // Phoenician
    public static final Version1Language PHN
            = new Version1Language(VERSION_1_LANGUAGE, "phn");
    // Pali
    public static final Version1Language PLI
            = new Version1Language(VERSION_1_LANGUAGE, "pli");
    // Polish
    public static final Version1Language POL
            = new Version1Language(VERSION_1_LANGUAGE, "pol");
    // Pohnpeian
    public static final Version1Language PON
            = new Version1Language(VERSION_1_LANGUAGE, "pon");
    // Portuguese
    public static final Version1Language POR
            = new Version1Language(VERSION_1_LANGUAGE, "por");
    // Prakrit languages
    public static final Version1Language PRA
            = new Version1Language(VERSION_1_LANGUAGE, "pra");
    // Provenal, Old (to 1500)
    public static final Version1Language PRO
            = new Version1Language(VERSION_1_LANGUAGE, "pro");
    // Pushto; Pashto
    public static final Version1Language PUS
            = new Version1Language(VERSION_1_LANGUAGE, "pus");
    // Quechua
    public static final Version1Language QUE
            = new Version1Language(VERSION_1_LANGUAGE, "que");
    // Rajasthani
    public static final Version1Language RAJ
            = new Version1Language(VERSION_1_LANGUAGE, "raj");
    // Rapanui
    public static final Version1Language RAP
            = new Version1Language(VERSION_1_LANGUAGE, "rap");
    // Rarotongan; Cook Islands Maori
    public static final Version1Language RAR
            = new Version1Language(VERSION_1_LANGUAGE, "rar");
    // Romance languages
    public static final Version1Language ROA
            = new Version1Language(VERSION_1_LANGUAGE, "roa");
    // Romansh
    public static final Version1Language ROH
            = new Version1Language(VERSION_1_LANGUAGE, "roh");
    // Romany
    public static final Version1Language ROM
            = new Version1Language(VERSION_1_LANGUAGE, "rom");
    // Romanian; Moldavian; Moldovan
    public static final Version1Language RUM
            = new Version1Language(VERSION_1_LANGUAGE, "rum");
    // Rundi
    public static final Version1Language RUN
            = new Version1Language(VERSION_1_LANGUAGE, "run");
    // Aromanian; Arumanian; Macedo-Romanian
    public static final Version1Language RUP
            = new Version1Language(VERSION_1_LANGUAGE, "rup");
    // Russian
    public static final Version1Language RUS
            = new Version1Language(VERSION_1_LANGUAGE, "rus");
    // Sandawe
    public static final Version1Language SAD
            = new Version1Language(VERSION_1_LANGUAGE, "sad");
    // Sango
    public static final Version1Language SAG
            = new Version1Language(VERSION_1_LANGUAGE, "sag");
    // Yakut
    public static final Version1Language SAH
            = new Version1Language(VERSION_1_LANGUAGE, "sah");
    // South American Indian (Other)
    public static final Version1Language SAI
            = new Version1Language(VERSION_1_LANGUAGE, "sai");
    // Salishan languages
    public static final Version1Language SAL
            = new Version1Language(VERSION_1_LANGUAGE, "sal");
    // Samaritan Aramaic
    public static final Version1Language SAM
            = new Version1Language(VERSION_1_LANGUAGE, "sam");
    // Sanskrit
    public static final Version1Language SAN
            = new Version1Language(VERSION_1_LANGUAGE, "san");
    // Sasak
    public static final Version1Language SAS
            = new Version1Language(VERSION_1_LANGUAGE, "sas");
    // Santali
    public static final Version1Language SAT
            = new Version1Language(VERSION_1_LANGUAGE, "sat");
    // Sicilian
    public static final Version1Language SCN
            = new Version1Language(VERSION_1_LANGUAGE, "scn");
    // Scots
    public static final Version1Language SCO
            = new Version1Language(VERSION_1_LANGUAGE, "sco");
    // Selkup
    public static final Version1Language SEL
            = new Version1Language(VERSION_1_LANGUAGE, "sel");
    // Semitic languages
    public static final Version1Language SEM
            = new Version1Language(VERSION_1_LANGUAGE, "sem");
    // Irish, Old (to 900)
    public static final Version1Language SGA
            = new Version1Language(VERSION_1_LANGUAGE, "sga");
    // Sign Languages
    public static final Version1Language SGN
            = new Version1Language(VERSION_1_LANGUAGE, "sgn");
    // Shan
    public static final Version1Language SHN
            = new Version1Language(VERSION_1_LANGUAGE, "shn");
    // Sidamo
    public static final Version1Language SID
            = new Version1Language(VERSION_1_LANGUAGE, "sid");
    // Sinhala; Sinhalese
    public static final Version1Language SIN
            = new Version1Language(VERSION_1_LANGUAGE, "sin");
    // Siouan languages
    public static final Version1Language SIO
            = new Version1Language(VERSION_1_LANGUAGE, "sio");
    // Sino-Tibetan languages
    public static final Version1Language SIT
            = new Version1Language(VERSION_1_LANGUAGE, "sit");
    // Slavic languages
    public static final Version1Language SLA
            = new Version1Language(VERSION_1_LANGUAGE, "sla");
    // Slovak
    public static final Version1Language SLO
            = new Version1Language(VERSION_1_LANGUAGE, "slo");
    // Slovenian
    public static final Version1Language SLV
            = new Version1Language(VERSION_1_LANGUAGE, "slv");
    // Southern Sami
    public static final Version1Language SMA
            = new Version1Language(VERSION_1_LANGUAGE, "sma");
    // Northern Sami
    public static final Version1Language SME
            = new Version1Language(VERSION_1_LANGUAGE, "sme");
    // Sami languages
    public static final Version1Language SMI
            = new Version1Language(VERSION_1_LANGUAGE, "smi");
    // Lule Sami
    public static final Version1Language SMJ
            = new Version1Language(VERSION_1_LANGUAGE, "smj");
    // Inari Sami
    public static final Version1Language SMN
            = new Version1Language(VERSION_1_LANGUAGE, "smn");
    // Samoan
    public static final Version1Language SMO
            = new Version1Language(VERSION_1_LANGUAGE, "smo");
    // Skolt Sami
    public static final Version1Language SMS
            = new Version1Language(VERSION_1_LANGUAGE, "sms");
    // Shona
    public static final Version1Language SNA
            = new Version1Language(VERSION_1_LANGUAGE, "sna");
    // Sindhi
    public static final Version1Language SND
            = new Version1Language(VERSION_1_LANGUAGE, "snd");
    // Soninke
    public static final Version1Language SNK
            = new Version1Language(VERSION_1_LANGUAGE, "snk");
    // Sogdian
    public static final Version1Language SOG
            = new Version1Language(VERSION_1_LANGUAGE, "sog");
    // Somali
    public static final Version1Language SOM
            = new Version1Language(VERSION_1_LANGUAGE, "som");
    // Songhai languages
    public static final Version1Language SON
            = new Version1Language(VERSION_1_LANGUAGE, "son");
    // Sotho, Southern
    public static final Version1Language SOT
            = new Version1Language(VERSION_1_LANGUAGE, "sot");
    // Spanish; Castilian
    public static final Version1Language SPA
            = new Version1Language(VERSION_1_LANGUAGE, "spa");
    // Sardinian
    public static final Version1Language SRD
            = new Version1Language(VERSION_1_LANGUAGE, "srd");
    // Sranan Tongo
    public static final Version1Language SRN
            = new Version1Language(VERSION_1_LANGUAGE, "srn");
    // Serbian
    public static final Version1Language SRP
            = new Version1Language(VERSION_1_LANGUAGE, "srp");
    // Serer
    public static final Version1Language SRR
            = new Version1Language(VERSION_1_LANGUAGE, "srr");
    // Nilo-Saharan languages
    public static final Version1Language SSA
            = new Version1Language(VERSION_1_LANGUAGE, "ssa");
    // Swati
    public static final Version1Language SSW
            = new Version1Language(VERSION_1_LANGUAGE, "ssw");
    // Sukuma
    public static final Version1Language SUK
            = new Version1Language(VERSION_1_LANGUAGE, "suk");
    // Sundanese
    public static final Version1Language SUN
            = new Version1Language(VERSION_1_LANGUAGE, "sun");
    // Susu
    public static final Version1Language SUS
            = new Version1Language(VERSION_1_LANGUAGE, "sus");
    // Sumerian
    public static final Version1Language SUX
            = new Version1Language(VERSION_1_LANGUAGE, "sux");
    // Swahili
    public static final Version1Language SWA
            = new Version1Language(VERSION_1_LANGUAGE, "swa");
    // Swedish
    public static final Version1Language SWE
            = new Version1Language(VERSION_1_LANGUAGE, "swe");
    // Classical Syriac
    public static final Version1Language SYC
            = new Version1Language(VERSION_1_LANGUAGE, "syc");
    // Syriac
    public static final Version1Language SYR
            = new Version1Language(VERSION_1_LANGUAGE, "syr");
    // Tahitian
    public static final Version1Language TAH
            = new Version1Language(VERSION_1_LANGUAGE, "tah");
    // Tai languages
    public static final Version1Language TAI
            = new Version1Language(VERSION_1_LANGUAGE, "tai");
    // Tamil
    public static final Version1Language TAM
            = new Version1Language(VERSION_1_LANGUAGE, "tam");
    // Tatar
    public static final Version1Language TAT
            = new Version1Language(VERSION_1_LANGUAGE, "tat");
    // Telugu
    public static final Version1Language TEL
            = new Version1Language(VERSION_1_LANGUAGE, "tel");
    // Timne
    public static final Version1Language TEM
            = new Version1Language(VERSION_1_LANGUAGE, "tem");
    // Tereno
    public static final Version1Language TER
            = new Version1Language(VERSION_1_LANGUAGE, "ter");
    // Tetum
    public static final Version1Language TET
            = new Version1Language(VERSION_1_LANGUAGE, "tet");
    // Tajik
    public static final Version1Language TGK
            = new Version1Language(VERSION_1_LANGUAGE, "tgk");
    // Tagalog
    public static final Version1Language TGL
            = new Version1Language(VERSION_1_LANGUAGE, "tgl");
    // Thai
    public static final Version1Language THA
            = new Version1Language(VERSION_1_LANGUAGE, "tha");
    // Tibetan
    public static final Version1Language TIB
            = new Version1Language(VERSION_1_LANGUAGE, "tib");
    // Tigre
    public static final Version1Language TIG
            = new Version1Language(VERSION_1_LANGUAGE, "tig");
    // Tigrinya
    public static final Version1Language TIR
            = new Version1Language(VERSION_1_LANGUAGE, "tir");
    // Tiv
    public static final Version1Language TIV
            = new Version1Language(VERSION_1_LANGUAGE, "tiv");
    // Tokelau
    public static final Version1Language TKL
            = new Version1Language(VERSION_1_LANGUAGE, "tkl");
    // Klingon; tlhIngan-Hol
    public static final Version1Language TLH
            = new Version1Language(VERSION_1_LANGUAGE, "tlh");
    // Tlingit
    public static final Version1Language TLI
            = new Version1Language(VERSION_1_LANGUAGE, "tli");
    // Tamashek
    public static final Version1Language TMH
            = new Version1Language(VERSION_1_LANGUAGE, "tmh");
    // Tonga (Nyasa)
    public static final Version1Language TOG
            = new Version1Language(VERSION_1_LANGUAGE, "tog");
    // Tonga (Tonga Islands)
    public static final Version1Language TON
            = new Version1Language(VERSION_1_LANGUAGE, "ton");
    // Tok Pisin
    public static final Version1Language TPI
            = new Version1Language(VERSION_1_LANGUAGE, "tpi");
    // Tsimshian
    public static final Version1Language TSI
            = new Version1Language(VERSION_1_LANGUAGE, "tsi");
    // Tswana
    public static final Version1Language TSN
            = new Version1Language(VERSION_1_LANGUAGE, "tsn");
    // Tsonga
    public static final Version1Language TSO
            = new Version1Language(VERSION_1_LANGUAGE, "tso");
    // Turkmen
    public static final Version1Language TUK
            = new Version1Language(VERSION_1_LANGUAGE, "tuk");
    // Tumbuka
    public static final Version1Language TUM
            = new Version1Language(VERSION_1_LANGUAGE, "tum");
    // Tupi languages
    public static final Version1Language TUP
            = new Version1Language(VERSION_1_LANGUAGE, "tup");
    // Turkish
    public static final Version1Language TUR
            = new Version1Language(VERSION_1_LANGUAGE, "tur");
    // Altaic languages
    public static final Version1Language TUT
            = new Version1Language(VERSION_1_LANGUAGE, "tut");
    // Tuvalu
    public static final Version1Language TVL
            = new Version1Language(VERSION_1_LANGUAGE, "tvl");
    // Twi
    public static final Version1Language TWI
            = new Version1Language(VERSION_1_LANGUAGE, "twi");
    // Tuvinian
    public static final Version1Language TYV
            = new Version1Language(VERSION_1_LANGUAGE, "tyv");
    // Udmurt
    public static final Version1Language UDM
            = new Version1Language(VERSION_1_LANGUAGE, "udm");
    // Ugaritic
    public static final Version1Language UGA
            = new Version1Language(VERSION_1_LANGUAGE, "uga");
    // Uighur; Uyghur
    public static final Version1Language UIG
            = new Version1Language(VERSION_1_LANGUAGE, "uig");
    // Ukrainian
    public static final Version1Language UKR
            = new Version1Language(VERSION_1_LANGUAGE, "ukr");
    // Umbundu
    public static final Version1Language UMB
            = new Version1Language(VERSION_1_LANGUAGE, "umb");
    // Undetermined
    public static final Version1Language UND
            = new Version1Language(VERSION_1_LANGUAGE, "und");
    // Urdu
    public static final Version1Language URD
            = new Version1Language(VERSION_1_LANGUAGE, "urd");
    // Uzbek
    public static final Version1Language UZB
            = new Version1Language(VERSION_1_LANGUAGE, "uzb");
    // Vai
    public static final Version1Language VAI
            = new Version1Language(VERSION_1_LANGUAGE, "vai");
    // Venda
    public static final Version1Language VEN
            = new Version1Language(VERSION_1_LANGUAGE, "ven");
    // Vietnamese
    public static final Version1Language VIE
            = new Version1Language(VERSION_1_LANGUAGE, "vie");
    // Volapk
    public static final Version1Language VOL
            = new Version1Language(VERSION_1_LANGUAGE, "vol");
    // Votic
    public static final Version1Language VOT
            = new Version1Language(VERSION_1_LANGUAGE, "vot");
    // Wakashan languages
    public static final Version1Language WAK
            = new Version1Language(VERSION_1_LANGUAGE, "wak");
    // Walamo
    public static final Version1Language WAL
            = new Version1Language(VERSION_1_LANGUAGE, "wal");
    // Waray
    public static final Version1Language WAR
            = new Version1Language(VERSION_1_LANGUAGE, "war");
    // Washo
    public static final Version1Language WAS
            = new Version1Language(VERSION_1_LANGUAGE, "was");
    // Welsh
    public static final Version1Language WEL
            = new Version1Language(VERSION_1_LANGUAGE, "wel");
    // Sorbian languages
    public static final Version1Language WEN
            = new Version1Language(VERSION_1_LANGUAGE, "wen");
    // Walloon
    public static final Version1Language WLN
            = new Version1Language(VERSION_1_LANGUAGE, "wln");
    // Wolof
    public static final Version1Language WOL
            = new Version1Language(VERSION_1_LANGUAGE, "wol");
    // Kalmyk; Oirat
    public static final Version1Language XAL
            = new Version1Language(VERSION_1_LANGUAGE, "xal");
    // Xhosa
    public static final Version1Language XHO
            = new Version1Language(VERSION_1_LANGUAGE, "xho");
    // Yao
    public static final Version1Language YAO
            = new Version1Language(VERSION_1_LANGUAGE, "yao");
    // Yapese
    public static final Version1Language YAP
            = new Version1Language(VERSION_1_LANGUAGE, "yap");
    // Yiddish
    public static final Version1Language YID
            = new Version1Language(VERSION_1_LANGUAGE, "yid");
    // Yoruba
    public static final Version1Language YOR
            = new Version1Language(VERSION_1_LANGUAGE, "yor");
    // Yupik languages
    public static final Version1Language YPK
            = new Version1Language(VERSION_1_LANGUAGE, "ypk");
    // Zapotec
    public static final Version1Language ZAP
            = new Version1Language(VERSION_1_LANGUAGE, "zap");
    // Blissymbols; Blissymbolics; Bliss
    public static final Version1Language ZBL
            = new Version1Language(VERSION_1_LANGUAGE, "zbl");
    // Zenaga
    public static final Version1Language ZEN
            = new Version1Language(VERSION_1_LANGUAGE, "zen");
    // Zhuang; Chuang
    public static final Version1Language ZHA
            = new Version1Language(VERSION_1_LANGUAGE, "zha");
    // Zande languages
    public static final Version1Language ZND
            = new Version1Language(VERSION_1_LANGUAGE, "znd");
    // Zulu
    public static final Version1Language ZUL
            = new Version1Language(VERSION_1_LANGUAGE, "zul");
    // Zuni
    public static final Version1Language ZUN
            = new Version1Language(VERSION_1_LANGUAGE, "zun");
    // No linguistic content; Not applicable
    public static final Version1Language ZXX
            = new Version1Language(VERSION_1_LANGUAGE, "zxx");
    // Zaza; Dimili; Dimli; Kirdki; Kirmanjki; Zazaki
    public static final Version1Language ZZA
            = new Version1Language(VERSION_1_LANGUAGE, "zza");

    public static void loadAll() {
        LOG.debug("Loading Version1Language.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

    public Version1Language(String scheme, String value) {
        super(scheme, value);
    }

}
