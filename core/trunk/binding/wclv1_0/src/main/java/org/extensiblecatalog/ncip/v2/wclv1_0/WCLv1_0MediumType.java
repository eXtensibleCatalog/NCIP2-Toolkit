/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.wclv1_0;

import org.apache.log4j.Logger;
import org.extensiblecatalog.ncip.v2.service.MediumType;
import org.extensiblecatalog.ncip.v2.service.ServiceException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WCLv1_0MediumType extends MediumType {

    private static final Logger LOG = Logger.getLogger(WCLv1_0MediumType.class);

    private static final List<WCLv1_0MediumType> VALUES_LIST = new CopyOnWriteArrayList<WCLv1_0MediumType>();

    public WCLv1_0MediumType(String scheme, String value) {
        super(scheme, value);
        VALUES_LIST.add(this);
    }

    /**
     * Find the WCLv1_0MediumType that matches the scheme & value strings supplied.
     *
     * @param scheme a String representing the Scheme URI.
     * @param value  a String representing the Value in the Scheme.
     * @return an WCLv1_0MediumType that matches, or null if none is found to match.
     */
    public static WCLv1_0MediumType find(String scheme, String value) throws ServiceException {
        return (WCLv1_0MediumType)find(scheme, value, VALUES_LIST, WCLv1_0MediumType.class);
    }

    public static final String VERSION_1_WCL_MEDIUM_TYPE
        = "http://worldcat.org/ncip/schemes/v2/extensions/mediumtype.scm";

    /** Archival Material. */
    public static final WCLv1_0MediumType ARCHV = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Archv");

    /** Digital	Downloadable Archival Material. */
    public static final WCLv1_0MediumType ARCHV_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Archv Digital");

    /** An article or chapter.  No URL - used mainly for photocopies. */
    public static final WCLv1_0MediumType ARTCHAP = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "ArtChap");

    /** A physical article. */
    public static final WCLv1_0MediumType ARTCHAP_ARTCL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "ArtChap Artcl");

    /** An electronic article. */
    public static final WCLv1_0MediumType ARTCHAP_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "ArtChap Digital");

    /** A chapter from a book. */
    public static final WCLv1_0MediumType ARTCHAP_CHPTR = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "ArtChap Chptr");

    /** Audiobook.  Use for generic audiobooks and NetLibrary holdings. */
    public static final WCLv1_0MediumType AUDIOBOOK = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Audiobook");

    /** Audiobook on cassette. */
    public static final WCLv1_0MediumType AUDIOBOOK_CASSETTE = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Audiobook Cassette");

    /** Audiobook on tape. */
    public static final WCLv1_0MediumType AUDIOBOOK_TAPE = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Audiobook Tape");

    /** Audiobook on CD. */
    public static final WCLv1_0MediumType AUDIOBOOK_CD = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Audiobook CD");

    /** Audiobook on LP. */
    public static final WCLv1_0MediumType AUDIOBOOK_LP = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Audiobook LP");

    /** Digital copy of an audiobook. */
    public static final WCLv1_0MediumType AUDIOBOOK_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Audiobook Digital");

    /** A book. */
    public static final WCLv1_0MediumType BOOK = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Book");

    /** Braille Book. */
    public static final WCLv1_0MediumType BOOK_BRAILLE = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Book Braille");

    /** Continually updated resource */
    public static final WCLv1_0MediumType BOOK_CONTINUING = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Book Continuing");

    /** An e-book. */
    public static final WCLv1_0MediumType BOOK_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Book Digital");

    /** Large Print Book. */
    public static final WCLv1_0MediumType BOOK_LARGEPRINT = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Book LargePrint");

    /** Microform.  Used for microfilm, microfiche, micro-opaque and master microform. */
    public static final WCLv1_0MediumType BOOK_MIC = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Book Mic");

    /** Thesis/dissertation.  Almost all books with 15% microform. */
    public static final WCLv1_0MediumType BOOK_THSIS = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Book thsis");

    /** Computer File.  Used for generic computer file and older formats, e.g. 5.25" and 3.5" disc. */
    public static final WCLv1_0MediumType COMPFILE = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "CompFile");

    /** Downloadable copy of a computer file. */
    public static final WCLv1_0MediumType COMPFILE_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "CompFile Digital");

    /** A corporate profile. */
    public static final WCLv1_0MediumType CORPPROF = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "CorpProf");

    /** An article from an encyclopedia. */
    public static final WCLv1_0MediumType ENCYC = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Encyc");

    /** Game. */
    public static final WCLv1_0MediumType GAME = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Game");

    /** Video game. */
    public static final WCLv1_0MediumType GAME_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Game Digital");

    /** An image. */
    public static final WCLv1_0MediumType IMAGE = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Image");

    /** 2-D image: not necessarily digitized. */
    public static final WCLv1_0MediumType IMAGE_2D = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Image 2D");

    /** Interactive multimedia. */
    public static final WCLv1_0MediumType INTMM = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "IntMM");

    /** Downloadable multimedia. */
    public static final WCLv1_0MediumType INTMM_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "IntMM Digital");

    /** A kit. */
    public static final WCLv1_0MediumType KIT = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Kit");

    /** Map. */
    public static final WCLv1_0MediumType MAP = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Map");

    /** An electronic map. */
    public static final WCLv1_0MediumType MAP_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Map Digital");

    /** Musical Score. */
    public static final WCLv1_0MediumType MSSCR = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "MsScr");

    /** Downloadable musical score. */
    public static final WCLv1_0MediumType MSSCR_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "MsScr Digital");

    /** Music.  Use for generic musical recordings and "odd" formats. */
    public static final WCLv1_0MediumType MUSIC = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Music");

    /** Musical cassette. */
    public static final WCLv1_0MediumType MUSIC_CASSETTE = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Music Cassette");

    /** E-music. */
    public static final WCLv1_0MediumType MUSIC_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Music Digital");

    /** Musical CD. */
    public static final WCLv1_0MediumType MUSIC_CD = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Music CD");

    /** Musical LP. */
    public static final WCLv1_0MediumType MUSIC_LP = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Music LP");

    /** Newspaper. */
    public static final WCLv1_0MediumType NEWS = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "News");

    /** E-Newspaper. */
    public static final WCLv1_0MediumType NEWS_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "News Digital");

    /** Object/artifact. */
    public static final WCLv1_0MediumType OBJECT = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Object");

    /** A digital object/artifact. */
    public static final WCLv1_0MediumType OBJECT_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Object Digital");

    /** A Paper. */
    public static final WCLv1_0MediumType PAPER = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Paper");

    /** Periodical. */
    public static final WCLv1_0MediumType JRNL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Jrnl");

    /** A single issue of a periodical. */
    public static final WCLv1_0MediumType JRNL_ISSUE = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Jrnl Issue");

    /** An e-journal. */
    public static final WCLv1_0MediumType JRNL_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Jrnl Digital");

    /** A publisher record. */
    public static final WCLv1_0MediumType PUB = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Pub");

    /** A review. */
    public static final WCLv1_0MediumType REV = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Rev");

    /** Non-musical sound recording.  Use for generic non-musical recordings and "odd" formats. */
    public static final WCLv1_0MediumType SND = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Snd");

    /** A non-musical recording on cassette. */
    public static final WCLv1_0MediumType SND_CASSETTE = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Snd Cassette");

    /** A non-musical recording on CD. */
    public static final WCLv1_0MediumType SND_CD = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Snd CD");

    /** A digital copy of a non-musical recording. */
    public static final WCLv1_0MediumType SND_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Snd Digital");

    /** A non-musical recording on LP. */
    public static final WCLv1_0MediumType SND_LP = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Snd LP");

    /** Toy. */
    public static final WCLv1_0MediumType TOY = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Toy");

    /** The format is undefined. */
    public static final WCLv1_0MediumType UND = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Und");

    /** Video.  Use for visual motion pictures on film stock and other generic media. */
    public static final WCLv1_0MediumType VIDEO = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Video");

    /** Blu-ray video. */
    public static final WCLv1_0MediumType VIDEO_BLURAY = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Video Bluray");

    /** DVD video. */
    public static final WCLv1_0MediumType VIDEO_DVD = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Video DVD");

    /** An e-video. */
    public static final WCLv1_0MediumType VIDEO_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Video Digital");

    /** A video on film. */
    public static final WCLv1_0MediumType VIDEO_FILM = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Video Film");

    /** VHS video. */
    public static final WCLv1_0MediumType VIDEO_VHS = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Video VHS");

    /** Visual material.  Use for generic visual material. */
    public static final WCLv1_0MediumType VIS = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Vis");

    /** Downloadable visual material. */
    public static final WCLv1_0MediumType VIS_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Vis Digital");

    /** An internet resource. */
    public static final WCLv1_0MediumType WEB = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Web");

    /** A web-site. */
    public static final WCLv1_0MediumType WEB_DIGITAL = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "Web Digital");

    /** Other */
    public static final WCLv1_0MediumType OTR = new WCLv1_0MediumType(VERSION_1_WCL_MEDIUM_TYPE, "otr");


    public static void loadAll() {
        LOG.debug("Loading WCLv1_0MediumType.");
        // Do nothing - merely invoking this method forces the creation of the instances defined above.
    }

}
