/**
 * Copyright (c) 2012 North Carolina State University
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.extensiblecatalog.ncip.v2.symphony;

import org.extensiblecatalog.ncip.v2.service.MediumType;
import org.extensiblecatalog.ncip.v2.service.Version1MediumType;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for mapping item types as reported by Sirsi Symphony (this is typically set in a policy file)
 * to NCIP objects.
 * @author adam_constabaris@ncsu.edu, $LastChangedBy$
 * @since 1.2
 */
public class ItemTypeMapper {

    public static MediumType EBOOK_READER = new MediumType(Version1MediumType.VERSION_1_MEDIUM_TYPE,"E-Book Reader");

    public static MediumType LENDING_LAPTOP = new MediumType(Version1MediumType.VERSION_1_MEDIUM_TYPE,"Lending Laptop");

    public static MediumType NEWSPAPER = new MediumType(Version1MediumType.VERSION_1_MEDIUM_TYPE,"Newspaper");

    public static MediumType DVD = new MediumType(Version1MediumType.VERSION_1_MEDIUM_TYPE,"DVD");

    public static MediumType OTHER = new MediumType(Version1MediumType.VERSION_1_MEDIUM_TYPE,"Other");


    private static final Map<String,MediumType> typeMap = new HashMap<String,MediumType>()
    {/**
		 * 
		 */
		private static final long	serialVersionUID	= 1L;

	{
            put("book", Version1MediumType.BOOK);
            put("magazine", Version1MediumType.MAGAZINE);
            put("microform", Version1MediumType.MICROFORM);
            put("compact disc (audio)", Version1MediumType.COMPACT_DISC);
            put("circulating cd-rom", Version1MediumType.CD_ROM);
            put("non-circulating cd-rom", Version1MediumType.CD_ROM);

            put("dvd",DVD);
            put("ebook device", EBOOK_READER);
            put("staff lending laptop", LENDING_LAPTOP);
            put("patron lending laptop", LENDING_LAPTOP);
            put("newspaper", NEWSPAPER);
            put("--default--", OTHER);
    }};


    /**
     * Gets the NCIP object representing the type of item given its textual description.
     * @param itemType the (case-insensitive) name of the item type.
     * @return the API object representing the specified type, or <code>OTHER</code> if
     * <code>itemType</code> is <code>null</code> or lacks a mapping.
     */
    public static MediumType getMediumType(String itemType) {
        String lt = itemType != null ?  itemType.toLowerCase() : "--default--";
        MediumType mt = typeMap.get(lt);
        return mt != null ? mt : OTHER;
    }


}
