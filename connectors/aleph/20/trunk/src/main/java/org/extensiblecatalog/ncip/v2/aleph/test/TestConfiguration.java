package org.extensiblecatalog.ncip.v2.aleph.test;

import java.io.IOException;
import java.util.Properties;

public class TestConfiguration {
	private static Properties props;
	
	
	public static String getProperty(String name) throws IOException{
		return getProperties().getProperty(name);
	}
	
	public static Properties getProperties() throws IOException {
		if (props==null){
			props = new Properties();
			props.load(ClassLoader.getSystemResourceAsStream("org/extensiblecatalog/ncip/v2/aleph/test/test.properties"));
		}
		return props;
	}
	
	public static final String EXPECTED_LOAN_SIZE = "4";
	/*public static final String USERNAME = "21004187500100";
	public static final String PASSWORD = "1009";
	public static final String FULLNAME = "Johnson, Richard Patrick";
	public static final String ADDRESS = "1140 Sunnymede Avenue\nSouth Bend IN 46615";
	public static final String PATRON_ID = "MLC200033079";
	public static final String EXPECTED_AGENCY = "University of Notre Dame";
	public static final String RESTRICTED_ADM_ID = "002323761000010";
	public static final String AVAIL_ADM_ID1 = "001834556000010";
	public static final String AVAIL_ADM_ID2 = "002249515000010";
	
	//item to check in ND and SMC
	public static final String AGENCY_2 = "Saint Mary's College";
	public static final String BOTH_AGENCY_ADM_ID = "000000001000010";
	
	public static final String LOAN_HOLD_ID_1 = "002257092";
	public static final String LOAN_ADM_ID1 = "001616262000010";
	public static final String LOAN_BIB_ID1 = "001616262";
	public static final String LOAN_AUTHOR_1 = "Ramsay, William Mitchell, Sir, 1851-1939.";
	public static final String LOAN_CALLNUMBER_1 = "BS 2685 .R29 1997";
	public static final String LOAN_CIRC_STATUS_1 = "Checked Out";
	public static final String LOAN_ISBN_1 = "0825436389 (alk. paper)";
	public static final String LOAN_LOCATION_1 = "Hesburgh Library";
	public static final String LOAN_MEDIUMTYPE_1= "Book";
	public static final String LOAN_PUBLISHER_1 = "Grand Rapids, MI : Kregel Publications, c1997.";
	public static final String LOAN_TITLE_1 = "Historical commentary on Galatians / William M. Ramsay ; edited by Mark Wilson.";
	
	public static final String LOAN_ADM_ID2 = "002386932000010";
	public static final String LOAN_BIB_ID2 = "002386932";
	public static final String LOAN_AUTHOR_2 = "Schemmel, Matthias.";
	public static final String LOAN_CALLNUMBER_2 = "Q 175 .B747 v.268";
	public static final String LOAN_CIRC_STATUS_2 = "Checked Out";
	public static final String LOAN_ISBN_2 = "9781402054983 (set : cased)";
	public static final String LOAN_LOCATION_2 = "Hesburgh Library";
	public static final String LOAN_MEDIUMTYPE_2= "Book";
	public static final String LOAN_PUBLISHER_2 = "[London] : Springer, c2008.";
	public static final String LOAN_TITLE_2 = "The English Galileo : Thomas Harriot's work on motion as an example of preclassical mechanics / by M";
	
	public static final String LOAN_ADM_ID3 = "002312643000010";
	public static final String LOAN_BIB_ID3 = "002312625";
	public static final String LOAN_AUTHOR_3 = null;
	public static final String LOAN_CALLNUMBER_3 = "QB 36 .G2 G35 2007";
	public static final String LOAN_CIRC_STATUS_3 = "Checked Out";
	public static final String LOAN_ISBN_3 = "9780977964055 (v. 1)";
	public static final String LOAN_LOCATION_3 = "Hesburgh Library";
	public static final String LOAN_MEDIUMTYPE_3= "Book";
	public static final String LOAN_PUBLISHER_3 = "State Line, Pa. : Catholic Apologetics International Pub., 2007.";
	public static final String LOAN_TITLE_3 = "Galileo was wrong : the church was right.";
	
	public static final String LOAN_ADM_ID4 = "002249689000010";
	public static final String LOAN_BIB_ID4 = "002249515";
	public static final String LOAN_HOLD_ID4 = "002254290";
	public static final String LOAN_AUTHOR_4 = "Conkin, Paul Keith.";
	public static final String LOAN_ISBN_4 = "0813124115 (hardcover : alk. paper)";
	public static final String LOAN_TITLE_4 = "The state of the Earth : environmental challenges on the road to 2100 / Paul K. Conkin.";
	public static final String LOAN_CALLNUMBER_4 = "GE 195.7 .C66 2007";
	public static final String LOAN_PUBLISHER_4 = "Lexington, Ky. : University Press of Kentucky, c2007.";
	public static final String LOAN_DESCRIPTION_4 = "xii, 308 p. : ill. ; 24 cm.";
	public static final String LOAN_LOCATION_4 = "Hesburgh Library";
	public static final String LOAN_ESRESOURCE_4 = "Table of contents only, http://www.loc.gov/catdir/toc/ecip072/2006032085.html, Click for ONLINE ACCESS";
	public static final String LOAN_MEDIUMTYPE_4 = "BOOK";
	public static final String LOAN_CIRC_STATUS_4 = "Checked Out";
	public static final String LOAN_PICKUP_LOCATION_4 = "Hesburgh Library General Counter";
	*/
}
