**NCIP Meeting 3/04/2014**

Present: Bach,Patrick, John, Prem, Maheshwaran

  * 1.2 release - due to a check in from John, need to do another round of testing.
  * Group decided that John will check in a fix for 168 and it will be part of the 1.2 release.  From his email "I have a change that should address most of NCIP-168, but I held off checking it in for the group to decide whether to include or not. It is to wrap the performService call in NCIPServlet with a try/catch block that catches any Exception and returns a valid NCIP message (that would contain a Problem element describing the exception). Without that change the client would likely get the server’s default page for an ‘Internal Server Error’ (HTTP status code 500). I think the change is isolated but we’d have to test not only that the connectors still work (which we have to do anyway) but we’d have to set up a test where a connector fails in a way that would be caught by that change. I.e. we’d have to establish a test case before we apply that change and then re-run the test after the change and confirm it now returns a proper NCIP response."
  * Patrick will execute the testing for this referenced above.  Test and get a 500 response now.  Then get new changes from John and test that get a toolkit message.   After Patrick tests ,Michelle, Bach, Patrick build and test.
  * Defer for future release - NCIP-104: Log file missing in Linux openSUSE 12.1
  * Patrick will determine after looking at 168 fix.  NCIP-171: Improve NCIP Response when Voyager webservice is down
  * Patrick will determine after looking at 168 fix.  NCIP-144: Improve error message response when toolkit is unable to contact the vxws service
  * John to do meeting minutes

OLE

  * Adam had asked some questions and John respond with options for how to configure the Toolkit to use the values that are needed.   John offered to do follow up vis skype or email.  Ball is in OLE developers hands for now and they will reach out when they need to.


**NCIP Meeting 2/18/2014**

Present: Bach,Patrick, John, Brandon

  * CARLI is making great progress with bringing up hte infrastructure needed for the XC community.  Jira is done and website and mailing lists are being worked on.
  * 1.2 release - John to review open issues and clean up.  We will try to wrap up and get the 1.2 release out soon, as it includes important functionality that is already complete, so we will minimize the delay.
  * future of the group for now we will continue to have schedule meeting every two weeks.
  * Randy to contact Michele and Adam to inquire about status with OLE.
  * Discussed 1.3 release and will need to define the work that should be in it as a group.



**NCIP Meeting 9/17/2013**

Present: Bach,Patrick, John, Adam, Michelle, Prem

  * Bach's issue moved forward but now is in John's court.   Bach to continue touching base with John every couple of days.  John thinks has figured out the problem with the config loading.
  * Then John has another code check in, and all the developers to give blessing.
  * OLE project - Once 1.2 is released and tagged, then OLE project will pull code from the tagged 1.2 code base, and create needed jars that are embedded into OLE.  This will occur when the OLE project is ready, probably for the first release after OLE 1.0.
  * OLE looking at checkout options, whether they need SIP connector in addition to NCIP.  John to review the topic and decide on appropriate action, if any is needed.

  * Next meeting on 10/1/2013 and John Bodfish will facilitate in Randy's absence.



**NCIP Meeting 8/27/2013**

Present: Bach,Patrick, John, Adam

  * Update from Michelle via email to correct a statement made last meeting - The vufind connector (that Chicago, Villanova and Lehigh are collaborating on) ...will not be an NCIP connector as I thought -- it will communicate directly with the OLE api and NOT through NCIP.
  * Adam reports still planning to use NCIP with OLE.  We suspect there is a goal to add extension elements without having to impact the core.  E.g. should be able to be done by just the connector developer.  But, need to do some chunk of work in the core in order to make it happen.
  * Bach's issue moved forward but now is in John's court.   Bach to continue touching base with John every couple of days.
  * OLE - Use different operator id for each library sending message, but use the same item type.  John to write up use cases that may be of help as some of the coding has not been done is starting.
  * Patrick - nothing to add
  * move the next meeting from 9/3 to 9/10




**NCIP Meeting 8/6/2013**

Present: Bach, Michelle, Prem, Patrick, John

  * Release 1.2 update - Bach still needs to connect with John.   Bach be proactive.   John has done no further work with 1.2.  Will try to commit some hours this week.   One more build and deploy after John's next check in.
  * OLE press/announcement timing - Project Manager for OLE?  Michelle to ask the question.
  * HTC update - identifier needed to identify "who" is calling.   1. Initiation Header, From Agency ID, (as replacement/surrogate OLE operator id for 2.  look at Application Profile Type. 3. IP address.  John says there is also a "from system id" to consider.
  * OLE - Use different operator id for each library sending message, but use the same item type.  John to write up use cases that may be of help as some of the coding has not been done is starting.
  * use of "to agency" - any use case? - too early to tell, but it is being discussed (consortia).  Many libraries sharing one OLE, but also possible separate OLEs, but needing to interact as of they were one.
  * Villanova, University of Chicago and Lehigh - work on the VuFind side.  Good luck.
  * Next meeting  8/27 (moved from 8/20 due to vacations) - will stay at every two weeks until 1.2 is released, and will move to 10am Eastern and via a Skype call initiated by Randy.




**NCIP Meeting 7/23/2013**

Present: Bach, Michelle, Prem

  * Release 1.2 update
  * Introductions - Spec all services needed for Self Service (VuFind) and for ILL that Lehigh is using.  The 4 NCIP services that Lehigh currently uses.  Priority is on the ILL, and VUfind connector is next.  Incorporating code now.
  * Bach still having errors with the III connector.  Bach needs John to get back to him.
  * HTC developers (OLE) access to Jira - set Prem up and Randy create two issues
  * Next meeting  - will stay at every two weeks until 1.2 is released, and will move to 10am Eastern and via a Skype call initiated by Randy.

**NCIP Meeting 7/9/2013**

Present: John, Bach, Michelle

Agenda

  * 1.2 touch base - need to check code in that will address 104, 163, 157, 168.  Bach's work still not working.    John and Bach need to work together.   Then once back code checked, all developers should run some tests again.
  * John to write release notes.
  * Jira use - Randy demonstrated how to use Jira to find just the issues for the release we are working on.   Go to the issues tab (3rd over), then select new filter, then on Project Button choose the NCIP project.   From the Status button choose both Open and In Progress.   The last button is a "+More Criteria" and you can add "Fix Version" and then from that choose the version we are working on.
  * meeting schedule - meet 7/23 and then monthly after that.  Randy will adjust meeting schedule after the next meeting.
  * NCIP and OLE - some work may be starting soon.  Michelle meeting with developers on Wed. morning.  Michelle to extend an invitation for OLE developers to join our group.  Perhaps pick up some low lying fruit by them participating.




**NCIP Meeting 6/25/2013**

Present: John, Bach, Michelle

Agenda


  * 1.2 release - NCIP 104 log file issue will be fixed, (168 also )and in next code check in.  We will proceed with 1.2, and these may be a 1.2.1 release.
  * Michelle - tested on Tomcat fine, but not working on Jetty 9.    If that doesn't work, then will test on the old jetty 7 server.
  * Bach and John - John sent email with a change to make.  Bach will make and report back.
  * John to write release notes.
  * Can release when Michelle and Bach report back and the release notes are done.
  * Release 1.3 0 105 may be the best for community needs.   111 may be highest need for OCLC.   We will play it by ear.
  * Once 1.2 is out, we will change to once a month meeting, on the 2nd Tuesday at 11am.  So next meeting is July 16, and then the switch to monthly with that starting on August 13th.



**NCIP Meeting 6/11/2013**

Present: John, Bach, Pat

Agenda

  * 1.2 release - NCIP 104 log file issue will be fixed, (168 also )and in next code check in.
  * XCO and CARLI MOU - Randy talked about the infrastructure support that will begin soon and the minimal impact it will have for this group, at least for the next 13 months..
  * Michelle has a conflict today, and has been on vacation so no updates on connector testing from her.
  * John to create a testing issue and then subtasks.
  * Bach doing some testing with 1.2 and waiting for something from John.
  * Voyager connector is good to go anytime, only problem us with Voyager webservices.  Is there a surrogate HTML webservices to return the data instead of rest-ful services.Also can we return a problem if there is a problem.  Randy open issue for Patrick.


**NCIP Meeting 5/28/2013**

Present: John, Bach, Michelle, Pat

Agenda

  * General discuss bugs, recently found.  Patrick to send some materials to John to help with recent bug.
  * Release potential - too early to talk about.
  * open issues in 1.2
  * Bach doing some testing with 1.2 and waiting for something from John.
  * John to create a testing issue and then subtasks.
  * Michelle having problem with getting her connector to load correct config.  Also should be a subtask.




**NCIP Meeting 5/14/2013**

Present: John, Bach, Michelle, Pat

Agenda

  * Config directions and feedback/bugs from Patrick.  John has not resolved.  Probably effects all the connectors.
  * Michelle - problem with a factory.     Michelle, please point the source of the config class you are having problems with and John will send the fix.  Run time error. In the Symphony Remote Service Manager.
  * Bach had the same problem it seems as Michelle.
  * 



**NCIP Meeting 4/30/2013**

Present: John, Bach, Michelle, Pat
Adam has a conflict

Agenda

  * Config directions are out.
  * Bach and Pat this week and Michelle next week
  * Patrick - end of week for building
  * can release 1.2 after connector developers

  * 1.3 main issue is identified in Jira

RFID, SIP2, NCIP2 work - reported by John B.
  * topic discussed at last weeks
  * NCIP2 feels "cludgy" to RFID developers.
  * will work with RFID community and maybe add extensions
  * John co-chairs SIP2, some interest in perhaps adding support to the toolkit
  * work on international front, ISO, ISO-ILL (10160, 10161 etc.) - overlapping membership, discussions occurring towards extensions.
  * Toolkit could grow to support other

Update from Adam C
  * I don't have much new to add at the moment with the Symphony connector except it does appear that I misreported last time, we appear to be using it with something labeled as version "1.2" of the connector infrastructure and not "1.1" as I stated.  I'm not sure that's the 1.2 release, though, because it's coming out of something I built locally.  I don't think there would be much difficulty porting to release 1.2 though.
  * With respect to OLE, we're trying to work out how to develop it, including who is going to do it and helping guide that process.  Another part relates to deployment models; the "separate web application" approach might create some issues in OLE (performance and complicates the deployment picture).
  * I have pitched the idea of possibly **embedding** the connector code directly into OLE, so that the responder servlet would live in the same webapp context as OLE, instead of running as a separate application.   I'd like to take some time to talk about exactly how feasible that is, because while it is certainly possible, it might be more trouble than it's worth and we don't want to create a fork.  That is generally going to be a very technical discussion, though, so it might not be a good topic for the group call at this stage, although we'd certainly want to work it out with the group later.

response from John B
  * Re: **embedding** the connector code directly into OLE, so that the responder servlet would live in the same webapp context as OLE
I think this is quite possible. There isn’t much in the toolkit’s web apps since the code comes from various jar files (e.g. responder, binding, connector). Beyond that there would need to be a few entries in the web.xml of the OLE webapp, and then some Spring to wire-up the Toolkit’s dependencies (if OLE doesn’t use Spring for dependency injection we can do this via a property file)
  * There may be other implications of such embedding that I’m not familiar with nor imagining, but that’s a starting-point, and I’d be quite happy to write-up the steps in detail and then work through the fall-out.



**NCIP Meeting 4/16/2013**

Present: John, Bach, Michelle, Adam

  * The 4/2 meeting was cancelled

Agenda

  * 1.2 release - John has gotten back to working on backwards compatible binding.
  * NCIP 163 is only open issue
  * Developers - at least two week window

Michelle - Kuali update
  * Kuali OLE update - Lehigh will need at least support for NCIP responder for what they currently have.  Wrote specs, reviewed and some other partner schools have reviewed.   Should be moving to coding.  Specs says what is needed for VuFind My Account features and Relais interaction. Ideally would support My Account stuff via NCIP.  May not be done for early adopters, but needed for NCSU when they roll out.
  * John - perhaps this creates new requirements for the core if there are elements needed for VUFind that are not there already.
  * John asked to see the Google doc that Michelle wrote, to look for any requirements that might be needed, so that we can meet the schedule.  And how it syncs with profile being prototyped with EBSCO.

Adam
  * On 1.1 connector, their version of Symphony connector.  Not going to release official 1.1 connector.  Not likely to get Lehigh and NCSU code merged.  Lehigh will end supporting once they go with Kuali, and NCSU is likely to just use what they have until they go, about a year later.  Has to do with difficulties in perl code, solaris vs linux, etc.
  * Adam has pushed for OLE developer to join call.  We'll see if that happens.

Bach

1.3 release





**NCIP Meeting 3/19/2013**

Present: John, Bach, Patrick

  * Adam and Michelle both at training.

Agenda

  * focus on 1.2 release - John needs to write email about how to configure, and once that is done the developers can proceed to build and test.
  * Patrick will need to test NCIP-157 when John completes it.
  * Patrick - I know you are planning to work on Request Item.  John can share via this group some of the "issues" that OCLC has encountered with this issue.


Agenda for next meeting
  * OLE meeting update from Michelle and Adam - was on agenda for this meeting.
  * Discuss 1.3 release and jira issues, and when we move to monthly meetings.

**NCIP Meeting 3/7/2013**

Present: John, Bach, Patrick

Note: meeting moved one day from 3/6 to 3/7.  Michelle not able to attend.

Agenda

  * Discuss new meeting time/day per John Bodfish - will try moving to Tuesday at 11am.
  * focus on 1.2 release - John thinks he has just about finished the last issue.  Backwards work should be in this week.  The delays were due to other things taking priority.


Next meeting
  * OLE meeting update from Michelle and Adam - was on agenda for this meeting.
  * Discuss 1.3 release and jira issues, and when we move to monthly meetings.




**NCIP Meeting 2/20/2013**

Present: John, Bach,Patrick, Michelle, Adam

Note that the 2/6/2013 meeting was cancelled due to many conflicts for the group.

John
  * should be able to check in the 1.2 backwards compatibility work (NCIP-163).  John to notify by email.
  * connector developers - sounds like a go for all 3 responding in some way to test and report about the backwards compatibility issue

Michelle
  * downloaded 1.2 code, build looks good, no compile errors.   Working on getting it running now.

Adam
  * no updates

Randy
  * Implementation forms discussion - Randy to standardize all connectors forms and submit based on today's discussion.  John and Bach to meet and get me the III form.
  * Moved issues 104 and 143 to release 1.3
  * Asked about OLE - need web services, Lehigh still needs NCIP to work with Relais.
  * Adam reports NCIP not going to be part of official OLE project.    Lehigh will have to build their own connector.  Certainly not on the near term horizon.


Next meeting
  * 3/6/2013 - focus on 1.2 release
  * Discuss 1.3 release and jira issues, and when we move to monthly meetings.
  * OLE meeting update from Michelle and Adam


**NCIP Meeting 1/23/2013**

Present: John, Bach,Patrick, Adam and Michelle

Agenda: Release

  * Implementation forms to Randy in the next week or so for each connector.
  * NCIP-163 – John has not completed the changes for backwards compatibility.    Primarily due to limited time.   Hoping for the best and that he can return to the work.
  * Michelle – can work on compiling the 1.2 code, and will have to do a little extra work when JOhn checks in the 163 code.  Other option would be to working merging of code with Adams work.
  * Patrick – will work on Voyager response improvements for when web services are down.

  * Atlas – need an inquiry into if they can supply web services to customers that don’t require learning the databases, that developers can start to use.

Next meeting on 2/6/2013 – John will be absent but will send notes in advance.


**NCIP Meeting 1/9/2013**

Present: John, Bach,and Michelle

Agenda: Release

Michelle
  * nothing with 1.2 version for compiling, will try to get to it in the next week, but wait for John to do the NCIP-163 work.
  * did spend some time with trying to get Adam's code running and made progress, once she gets it running she can fairly easily add new services to it.  Then Adam needs to test against Adam's Sirsi.    This would all be 1.0 core coe (right????).    Would need to be brought up to 1.2.   Goal or merging two Sirsi for 1.2.

Bach
  * One open Jira issue with John for backwards compliance.  That is only open problem.
    * Issue with backwards compatibility remains and is needed for both Patrick and Bach and is NCIP-163 in Jira


Illiad question - John to know more after meeting with Atlas next week.





**NCIP Meeting 12/12/2012**

Present: John, Bach, Patrick and Michelle

Agenda:

  * Issues in Jira that are not in 1.2 release version - John to move to 1.2
  * We went through Jira issues for 1.2 and moved some out to unscheduled and others forward to 1.3

John
  * ready to make the call that the developers can proceed.
  * Michelle to start soon, and should not be affected by the backwards compatibility issue.
  * John to help Bach with a change for LUIS
  * Patrick is done
  * Issue with backwards compatibility remains and is needed for both Patrick and Bach and is NCIP-163 in Jira

Patrick
  * NCIP-144 to work on and do then test the backwards compatibility.

Next Meeting:  will cancel 12/26/2012 due to vacations and will hold the next meeting on 1/9/2013.


**NCIP Meeting 11/28/2012**

Present:  Meeting cancelled

Agenda: None

**NCIP Meeting 11/14/2012**

Present: Bach, Adam, Michelle, John

Agenda: None

John
  * Will send everyone a link to the NCIP JIRA list for 1.2; would like everyone to make sure that any issue that's important to them is on the list and has a high priority.

Bach
  * His library is moving to WMS, expect to migrate in June. He's not sure how much he'll be able to continue working on the Millennium connector.

Michelle:
  * (I didn't make any notes on her report - JB)

Adam:
  * (I didn't make any notes on his report - JB)


**NCIP Meeting 10/31/2012**

Present: Bach, Patrick, Michelle, John

Agenda

  * Randy will be out for knee surgery on 11/14, so we need to discuss holding vs. moving the meeting.  Decided to have it as scheduled and John to facilitate meeting.
  * Talk about NCIP trending towards no for circ status support for "summary holdings".
  * Code4lib pitch

John
  * With 1.2, can support various NCIP versions.  Config option whether to support 2.01 or various extensions.   Default support 2.01 without extensions.  Need to support 2.03 as a natural.
  * LI problem with Bach is solved, and LUIS problem, John thinks he knows the reason.   Once this is resolved, then other connector developers like Michelle can test.
  * Patrick built 1.2 without a problem.  Static method that is gone now.    If developers used that, then there would be a problem.
  * John asked for some files from Patrick.
  * Too early to estimate 1.2 release date

Adam/OLE
  * Extension elements that are to be added - need to identify.  More work needed most likely from Chicago.
  * Michelle is looking at data points needed/used and will feed back to Tod Olson from OLE may be writing an OLE NCIP requirements list.  Michelle documenting what she has done, which will be a start.
  * option 1 general get extension vs. option 2 a 1by1 mapping, extending the service to include the data element specifically.   We expect to go with option 2.  Option 2 is like what we did with LUIS.    So, we will support elements that are needed specifically and then later work on adding that to the standard.
  * Will open Jira issues for 1.3 that Epic for OLE connector.  Stay engaged with OLE project, via Adam as liaison.

Code4lib pitch
  * Begin with a movie, to start coding.
  * Demonstrate how to build a connector to meet local needs
  * Novel idea from Lehigh - Michelle to try to find out.
  * Not show java, might scare people, can we do Groovy, Javascript.  Script module add on?  Groovy client to send a message as a proof of concept.  Using the toolkit as a client idea?  Rhino to bridge back to Javascript.  Use some sort of JVM language as a bridge.  Jruby, jython?

Next meetings
  * Next Meeting as scheduled 11/14 and John will facilitate


**NCIP Meeting 10/24/2012**

Present: Adam, Bach, Patrick, Michelle, John

Randy - to discuss
  * Question on the IDs in the records, 001, etc.   Group agreed that the MARC exports from an ILS should carry the local IDs for the record.
  * Logistics 11/14

John
  * Thinks 1.2 is ready, but had to make several changes to POM files
  * One change for Millennium code was involved.   Called a method that was not longer avail. so this might require a one line code change for other developers
  * Would like Bach to run through and say yes/no before others try
  * Lots of progress with archetype (thank  you Adam)
  * Bach will still have to get the files from the dummy for IE.
  * Then John will write up what needs to be done, next week.  Vacation rest of this week.

Bach

  * Can Michael get the MARC records to contain correct 001 field data?  Bach to follow up.
  * John to see if he can drum up info on how to do the extract
  * other options include new step in teh MST Norm Service that is III specific to move the ID from the 907 to the 001 or use the OAI Toolkit XSLT


Patrick
  * Bug - in LU service, not getting loaned items for UB.  Is fixed now.
  * Drupal - aggregation.  Randy to Patrick to meet later.

Michelle
  * NC State/Lehigh merger - NC State on connector 1.0, version behind, so Michelle can't do a code merge
  * Per Adam - Need to install Perl part of code on dev Sirsi instance and get that part working, and then process inside the connector.  The inside the connector work is for Adam (map the output) and the Perl test side is for Michelle.  The upgrade to the required Perl took the Lehigh person a long time.  Sorry.
  * Michelle to get a range of perl output to Adam so he can do the JSon sample cgi output mapping in the connector.
  * Lesson is don't use Solaris (opinion here).   Some portion of Symphony libraries may experience this "pain" to use the more modern version of Perl that the new connector requires.  This is a documentation note step that we need to make in the statement of requirements.   Adam will follow up on this.

  * John - Code4Lib - presentation about Toolkit - John will be there, Michelle to see if they can attend and do a dual proposal.

Next meetings
  * Next Meeting 10/31/2012
  * Randy will be out for knee surgery on 11/14, so we need to discuss holding vs. moving the meeting.
  * Talk about NCIP trending towards no for circ status support for "summary holdings".  John to draft a document for use to reach to.


**NCIP Meeting 10/3/2012**

Present: Adam, Bach, Patrick, Michelle, John

John
  * Just checked in 1.2, first shot.  Not yet ready for developers to test with it.   In next day or so John hopes to give the go ahead to rebuild and test.
  * Significant piece not there yet, eg. NCIP 2.02 support not there yet.
  * Making updates to wiki page.  No need a minimum Java of 1.6.025
  * Adam says all of 1.6 support us ending in February 2013.    No disadvantage of sticking with 1.6.025.
  * At next release (1.3) we will try to use 1.7 and hope that other XC software also use 1.7.
  * hope to check to Voyager, Millennium and Symphony, can only test the Millennium
  * Each developer will need to download, and rebuild their connector.

Michelle
  * Moving forward slowly

Adam
  * Standing by ready to give assistance when ready.

Patrick
  * Test page - ready for me to test.    1.1 release
  * No other updates


Bach
  * Released new 1.1.1 - avail, location call number, Drupal developers to look at why it is not showing

Other
  * Application Profile between NC State and OCLC


Next meeting - move 17th to the 24th due to several absences



**NCIP Meeting 9/19/2012**

Present: Adam, Bach, Patrick, Michelle, John


Next meeting
  * Update on merger of two connectors eval
  * Ap Profile for Adam's use case - progress
  * Maven archetype (no progress yet)
  * other Jira issues review
  * Patrick Jira issues
  * Progress on 1.2

John
  * Lots of progress on 1.2, hope to check to Voyager, Millennium and Symphony, can only test the Millennium
  * Each developer will need to download, and rebuild their connector.
  * Testing that John will be doing is that it still works for OCLC implementations
  * Patrick will incorporate Test Page into Voyager so that it will be there for the Test Bed
  * John proposed that all connectors make use of it.  Need 4 files to do basic version and then edit for other templates you wish to use.  Reminder that this includes the fix for IE9.  All developers agreed to support test page
  * There will be further checkins of code to address other issues (so initial checkin and then others to follow)
  * Can use 2.01 with extensions or with 2.02 without extensions.   Design is that connector should not see a difference.   First time 1.2 release will need to test both.  Default should be 2.02 without extensions and all developers agreed to that.

Michelle and Adam
  * Evaluation of Accept Item.  First hurdle is Perl version needed 5.10 (quite old 2008 maybe) at min to support Unicode.  Also newer version of Apache.  Compile a separate PHP one, so that other production stuff can remain.
  * Estimates 40-80 hours of work estimate.  Unknown is the size of the changes needed that are specific to Accept Item.  Spread out over a couple of months.
  * After Perl, Adam can take it back over.
  * John asked about if Core had support for Json to Toolkit mapping whether it would be helpful.   Already done for idiosyncratic work for Sirsi, not generalized yet.   Adam would like to generalize it himself when someone else needs it, and then move to core.
  * Sirsi connector will be Lehigh version for initial release of 1.2.  Merged connector will be released whenever it is ready.

Bach

  * Needs to change his connector to support both when an Agency ID is supplied and when it is omitted in the requests and then do a 1.1.1 re-release. Then port those changes over to 1.2.



**NCIP Meeting 9/5/2012**

Present: Adam, Bach, Patrick, Michelle

John absent but reported that there was no change on his end.

  * Action for next meeting to discuss with Michelle how to manage two versions

Lehigh will not be on Symphony much past a year, so there is no possibility that they would make a switch a new version.

Accept Item will be most problematic service.  Check in and Check out probably could be done by Adam but Adam has no way to test.  Lookup User is already done and Adam’s version covers more functionality than Michelle’s.

Michelle can evaluate what would be involved in a “merger” for Accept Item.  Michelle can perform her evaluation against a checked in code base branch in 1.0 version of Toolkit.  Before moving forward and coding though, she would need latest code from Adam.  This would be checked in as a branch in the 1.1 code base.   The first release candidate for this connector though would be in the 1.2 release.  Randy to create Jira issues for this as issue NCIP-145.   Michelle would be willing to test the ILL type functions.

  * Test Page work

Adam has made a bit of progress on the test page, but due to an outage at google code (http://code.google.com/p/support/issues/detail?id=28556) cannot commit it.  He’s tested it with IE 9 on Win 7 and IE 10 on Win 8 (don't have any other versions to test with), Firefox 14/15 and Chrome ... 21?  on Mac.  The only really problematic browser is IE.

He will attach a patch file and instructions to the Jira issue if anybody has a crushing need for this functionality.

This work would need to be copied into each connector.  Going into Dummy for now, and ILS developers would have to copy over to the ILS connectors.

Next meeting
  * Update on merger of two connectors eval
  * Ap Profile for Adam's use case - progress
  * Maven archetype (no progress yet)
  * other Jira issues review
  * Patrick Jira issues

**NCIP Meeting 8/22/2012**

Present: Adam, Bach, John, Patrick

1.2 Report
  * Essentially the same, though many more hours have been put into it.
  * Make NCIP 2.02 release compatible.   Randy to make jira changes.

Adam
  * Working version of their version Symphony against 1.1 and in production for their Patron Account services.   John suggested creating an Application Profile for this this.  John has some examples to provide John.     Adam didn't get it to work with Spring, so pulled out just what he needed. LU, Cancel Request, Renew Item.
  * VM crashed today though and needs to be investigated.    John mentioned a stats area of 1.1 that is not documented, so John will share that with Adam in case it helps him at all.
  * Also added a service template (e.g. LU request, but in real user id) using jquery.   That would be a good thing and eliminate the copy paste of sample XML.  Currently there is a java and a perl version, so not sure how implement. Randy to open issue for 1.2 to add and check in the sample files.  jquery stuff should solve the IE test page issue.
  * John reports WorldCatLocal and WMS (ILS)has a similar Profile and will seek approval to share it with Adam and then can grow the Profile together, between OCLC and NC State.

Randy
  * That the NCIP 2.02 was released. http://www.niso.org/workrooms/ncip

Patrick
  * Nothing to report.

Bach
  * No updates

Next meeting
  * Action for next meeting to discuss with Michelle how to manage two versions
  * Ap Profile for Adam's use case - progress
  * Maven archetype (no progress yet)
  * other Jira issues review
  * Patrick Jira issues



**NCIP Meeting 8/8/2012**

Present: Meeting cancelled but John reported that he was able to build the millennium connector in the 1.2 release (yippee!) but has not tried the Symphony connector yet. Due to other assignments he is not sure how soon he'll check in the 1.2 code.


**NCIP Meeting 7/25/2012**

Present: John, Michelle, Adam, Bach, Patrick

1.1
  * will announce after I hear from Michelle.  Patrick, John and Bach are done.
  * Patrick sent question and John will respond.  It was about a response when there is no item.

1.2
  * Early next week John will have a working version of 1.2 but needs to clean up, add copyright statement, and wants to try to build the other connectors first.
  * Connector developers will need to test.  Should be straight forward.

1.3 - Next steps.
  * Make NCIP 2.02 release compatible.
  * Should not impact connectors beyond building and testing

Next meeting is on 8/8 and Randy will be away.  John will facilitate for a touch base on the 1.2 release.

Also, Randy would like to find ways to approach vendors so that they support an NCIP Toolkit based NCIP response.

**NCIP Meeting 7/11/2012**

Present: Michelle, Adam, Bach, Patrick

1.1 Related
  * Switched to Jira - all but Adam.  Randy will sign Adam up.
  * 1.1 is out,but not announced.  After Voyager is built and the wiki clean up is done, we will announce next week.
  * Voyager connector for 1.1 not released yet.  Patrick thinks it will be ready by Friday.
  * Test bed is down.  Randy to talk with Patrick.
  * Wiki clean up - Jira issues for all.  Group thinks this can be done by Friday.
  * Voyager Java JDBC - how to distribute, how to deploy, best practices.  Suggestion is to install in Tomcat library directory.  Randy opening issue for this.
  * Java 1.7 vs. Java 1.6 - should we maintain dual compatibility?  1.6 is end of life in Nov. Tell Maven to build at 1.6 level, but can use either a 1.6 or 1.7 compiler.  No JDK 7 for a Mac OSX.  Sticking with 1.6 source code is to be weighed against some developers being pushed out of development. John to update [issue 114](https://code.google.com/p/xcncip2toolkit/issues/detail?id=114) with these comments but will not move to 1.7 yet.
  * John to tag 1.1 release

1.2 Related
  * 1.2 release - status, next steps, what it means for developers on this call.
  * John has a working version of 1.2 but needs to clean up, add copyright statement, and wants to try to build the other connectors first.
  * John will work on this week, and then John is on vacation next week.  John will have 1.2 code checked in by end of day Friday so that each connector developer can start this next week.
  * create Maven archetype (a quick start for a new connector developers), Adam has a start at this, but goes against 1.0.   Adam to bundle his start at this and get to John so that John can start to work on this the week of 7/23 or after.
  * can the 2 threads of Symphony connectors get merged together.  Adam's stuff is more Patron account and does not support accept item at all.  Adam does want to make his work compatible with 1.2 but not likely.  Most likely will have still only have one 1.2 connector for Symphony, the Michelle/Lehigh one, but probably not the Adam one.
  * Lehigh will keep their Symphony connector compatible until they go to OLE.




**NCIP Meeting 6/27/2012**

Present: Cancelled - couple members away

**NCIP Meeting 6/13/2012**

Present:   John, Michelle, Bach

Release 1.1 update/discussion
  * Bach is completely set to go with 1.1
  * Michelle is not working yet, but not sure if it is a Jetty problem or the toolkit.  John, with Tomcat, able to see that it is loading the context.  Anything similar with Jetty?  Michelle will try to build and run under Tomcat, to isolate whether it is toolkit or Jetty.
  * Voyager, what do we do? Due to Rochester Voyager upgrade soon, we will postpone John building and have Rochester resource test
  * switch to Jira after the 1.1 release
  * Bach to open new Google code issue when connector deployed on linux, the log file doesn't get renamed like it should.


1.2 Release is functional, will take couple days to merge code, then connector developers need to test.


**NCIP Meeting 5/30/2012**

Present:   Bach, John, Michelle, Adam

Release 1.1 update/discussion
  * John and Bach are still working on the override file.
  * Finding things that are not the problem.  John will have more time now to work on this now that other work is wrapping up.
  * Same plan as last time, John to work with Back to solve and then move on to the other connectors.

  * Randy reported hearing rumblings of interest from Rochester regarding ILLiad and NCIP use - would need Request Item, Check In/Out, and Accept Item.

Next Meeting June 13, 2012

**NCIP Meeting 5/16/2012**

Present:   Bach, John, Michelle, Adam

Release 1.1 update/discussion
  * John and Bach are still working on the override file.
  * Michelle - looked at code suggestion from John.  Didn't think it would work for her remote services manager settings/designed.
  * John wants to solve problems for Bach first, then assess the Symphony work, if anything remains.
  * Problem that remains is getting the Config properties overridden using the local parameters
  * will need new core and new dummy war uploaded
  * Wiki pages to revise - Bach is done, Michelle can do some updates, at least context descriptor properties  that are needed

Release 1.2 reviewed and approved.

  * Issues tagged as release 1.2 were approved by group
  * Randy to add 3 new issues to summary the work in

Randy to ask National Library of Australia for their Voyager side logic/problems faced for their Check In and Check Out


Next meeting 5/30/2012


**NCIP Meeting 5/2/2012**

Present:   Bach, John, Michelle, Adam

Randy
  * Announced that Jira is up at http://extensiblecatalog.lib.rochester.edu:8080/secure/Dashboard.jspa and is available to this group in whatever manner we would like.  I encourage all to sign up for an account, or poke around.

Michelle
  * built war and webapp works, having issue with context file and Jetty.  Config files in war file and deployed with war work, now wants to allow changes to war file, which config file in Jetty, to make run outside of war.

John
  * Uploaded dummy war after testing it.  Core and Dummy war is ready.
  * Been updating release notes for Core, little more time and then that is done
  * Been working with Bach to try to get the overrides to work in his environment

Adam
  * no updates toward 1.1 release

Bach
  * Still working on getting the config settings to work.  John to continue to work today.
  * Update the wiki page what he can while waiting for John.

Randy
  * Asked if John he could redo a build, assess what can be done.  John can try this.
  * Respond to Testbed being down

Release 1.2
  * switch to maven, and other issues in the 1.2 list.  All should review.
  * Anyone want to do IE test page?  Adam had a few ideas.  Use some other tool to hide the problem parts. John and Adam to talk off line.

Next meeting 5/16


**NCIP Meeting 4/18/2012**

Present:   Bach, John, Michelle, Adam

Agenda
  * John changed the snapshot
  * Dummy has been tested
  * Michelle - had trouble with Maven and build, but as of this morning can build war now, and now needs to test.   Also update Symphony install directions, including anything extra for Perl scripts.
  * Patrick - Patrick needs the same thing as others
  * Bach - needs to get newest code from trunc, rebuild, and then revisit the general installation instructions and then change the III Millennium page for context descriptors and Millennium connector specifics.  John available to talk with Bach if he needs
  * New 1.1 goal is by May 2 - if all goes well
  * Randy to pole group 4/25 re. 1.1 release

Adam
  * next couple of weeks might be updating code to work with 1.1 - not related to 1.1 release, but may be longer due to new library build that is going on
  * satisfied that what he wants with packaging and design are part of 1.1
  * next week large Kuali meeting.  Chicago looking at NCIP the protocol, not sure about Toolkit.  Lehigh plans to be early adopter of OLE, unclear on NCIP needs at this point.
  * Future - use cases that might need extensions - documentation on how to do that?  For loaned items, number of renewals remaining, etc.   1.2 release will be documented and support this better.  Right now new data elements may need updating in dozer and services classes and jaxb - multiple places.  Consider doing formal enhancement to NISO SC.
  * another general use case - convey item information, not sure what is missing from functionality.  Our groups goal should be to be as responsive as possible.  Just a heads up, but any requests would be driven by actual needs

John
  * Evergreen reach out - have an NCIP responder via perl scripts (could be used with WorldCat Navigator).  OCLC has testing to do.  But has moved away from Toolkit at this point.
  * test databed - may help unit tests, and get away from managing a real test bed, etc. create issue and

Randy
  * Randy to review the 1.2 tagged issues, to make sure they should be included
  * when do we make switch to latest official protocol - after NCIP protocol is released and when a need arises.   No pressure other than general principle that should support.   Should be no coding work, just testing for connector developers.

Next meeting:
  * next meeting 5/2/2012


**NCIP Meeting 4/4/2012**

Present:   Bach, John, Michelle, Adam

Patrick absent/sick, Bach - vaca

Agenda:
  * Version of 1.0 becomes part of release, putting the version number into one file that the poms refer to
  * take snapshot out, part of name of file - John to do today
  * each connector developer needs to rebuild the war file and run some test to make sure it works.  Then upload to the download place and check to make sure sure tagged and old are deprecated
  * Patrick - rebuild and test Voyager
  * Michelle still needs to release
  * John - needs to dummy connector release note
  * Patrick review and revisit documentation
  * Bach also review documentation

  * Then can get 1.1 out the door.  New Target date is 4/11

Adam
  * proposes possibly moving to Git hub for code repository at some point.
  * Packaging questions - does 1.1 solve most of the issues?  John and Adam to chat.

Michelle and group
  * Toolkit came up in an OLE meeting recently.  Tim pulled together a meeting with integration team for OLE (developers, PM, etc. and did an introduction).   Tod Olsen from Chicago part of meeting.  Either speak NCIP natively or develop a connector for for OLE.   At least 3 OLE institutions are interested in using NCIP protocol.  Tod is wanting to do live circ status and things relating to discover ability.

Randy
  * mentioned consider moving to Jira.  Can we integrate Jira with Google Code SVN server?  Or do we bring it in.  Decide later once other XC toolkits make the jump
  * Status with Evergreen - connector, hired by EOU, 3 perl scripts to to resource sharing (not Accept Item).   Not made avail yet.   John to just touch base.

Next meeting:
  * next meeting 4/18/2012



**NCIP Meeting 3/14/2012**

Present:   Bach, Pat, John, Michelle, Adam

Agenda:
  * Release 1.1
  * Michelle - still don't have running, but has made progress.  1.  Stuck on being able to instantiate a class.  John and Michelle can talk off line on this topic.   2.  Does she need Maven to build war file.  John suggests talking off line.  Needs to look at install directions for release.
  * Patrick - absent?   No status?  Document the ODBC
  * Bach - still done
  * John - Release notes to finish and document how to configure.  This is needed for all connector.  John also to review Bach's documentation.  All connector devs need to change their  documentation about what should be changed.
  * open issues review
  * Adam's Packaging questions - will send email with questions, John to review.  We think that 1.1 release solves most of not Adam's discussion to continue via email.
  * Lehigh update - no student worker this semester.
  * Indiana update -"I just exchanged emails with the developer from IU.  They were able to re-use parts of the sirsi code from the Symphony connector project and re-purpose it but they won't be using the NCIP connector."
  * National Libraries Australia - Randy to reach out after the 1.1 release.

**NCIP Meeting 3/7/2012**

Present:   Bach, Pat, John, Michelle, Adam

1.1 Release
  * Patrick checked out newest code, has some questions.  NCIP 2.01 schema to be used.  Say building Loaned Item object - set bib desc element and Dozer will set it.  Entire Bib Desc.
  * Michelle connector changes - debugging her test XML.  Blowing up at core she thinks.  Test XML not getting to Symphony connector.  Might try turning off validation.  Adam uses Oxygen XML editor.  John send instructions for how to turn off schema validation.  Will keep in mind sending a fix request to Relais if appropriate.  Send stack trace to John.
  * III Millennium connector - good to go.
  * Target release date 3/14/2012

1.2 connector
  * defined 1.2 - Protocol Version support, and application profile support and ability to use extensions (e.g. to address [issue 34](https://code.google.com/p/xcncip2toolkit/issues/detail?id=34)).   Should have no required changes to connector developers beyond testing, but optionally can then take advantage of the changes in the protocol versions.  John B - already prototyped 2 extensions needed for [issue 34](https://code.google.com/p/xcncip2toolkit/issues/detail?id=34).  Putting in the bib description.  Code changes to core to allow look inside the extension.

Adam
  * new branch for Json version of the connector has installed, builds against 1.0 Core
  * does not do accept item, does new stuff and not do all of Michelle's functionality.  Experimental still.  Will wait for 1.1 to officially drop and then investigate.
  * Still TBD whether 1.2 will have one or two Symphony connectors.  Randy still wants to push for one.  What are pros and cons.  Cons - little more complex to install, needs version of Perl installation, and to install Perl modules, put as CGI script.  Pros - loaned, cancel request, requested items added - LU is now pretty robust.
  * Packaging questions - ability to easily build and ship WARs for local testing, externalizing the configuration - one war per connector.  Will send email


Future meeting:
  * Group agreed to move the 3/21 meeting to 3/14 to support/wrap up the 1.2 release.
  * The meeting after 3/14 will be 4/4 (3 weeks later) and during the intervening time John will work on the 1.2 core code changes and we can touch base on that

Agenda for next meeting
  * Release 1.1
  * open issues review
  * Adam's Packaging questions - will send email with questions
  * Lehigh update
  * Indiana update -"I just exchanged emails with the developer from IU.  They were able to re-use parts of the sirsi code from the Symphony connector project and re-purpose it but they won't be using the NCIP connector."
  * National Libraries Australia


**NCIP Meeting 2/22/2012**

Present:   Bach, Pat, John, Michelle, Adam

General
  * Status of 1.1 core and connector - Bach and Patrick - John's last check in fixed all open problems for both.     Bach  wants to test at other non-UNCC sites.
  * Circ status question from Bach - not set in stone how to handle this.  Build what need when need it.  Will return free text( what it can get) for now.
  * Question from Bach on Authors - multiple vs. one.  Grabbing first to start.
  * Inclusion of extensions vs. new schema.  Will be in extension mode for now, not in the new NCIP Schema.  When the cut over later happens, should not have heavy work for connectors.  Drupal Toolkit will have to make change later.  John checking in this week.
  * Estimate for 1.1 release - March 7th release estimate for.  ODBC.  Will release 1.1 when have at least one connector, and will strive for all 3 - III, Sirsi and Voyager.

Michelle
  * Has newest code - still trying to get core code running on local Tomcat and debug in Eclipse.  John, send an email to group after call with a few idea.  Where is the log?
  * John mentioned use of Intelli J.
  * Adam mentioned Jetty.

Sirsi timeline
  * next release is Michelle's 1.1
  * NC State - private fork for 1.0 for now, new version of Connector
  * Consider 1.2 release as a merge together of both Sirsi branches
  * Unclear at this point whether we will have one vs. two version of the Sirsi connector

Adam
  * Packaging questions - not discussed
  * Project he is working on -json message to NCIP.  Patron self service, my account type work.  Using which NCIP messages -   LU (adding on to Michelle/Lehigh work)and Cancel Request (new)and Renew Item (new).  Wrote perl module, takes binary Sirsi delivered and turns to more human readable, json.  Using mapping Jackson library.
  * Substantial rewrite against 1.0 tag.  Migration from 1.0 to 1.1 unlikely, may try for 1.2.  John to updates his original email on the migration and add what Patrick and Bach came across and send.

John
  * Document how to setup from start - open new issue to document how to debug a connector.  John to create issue.

Future
  * Release 1.1
  * open issues
  * define 1.2 - Protocol Version support, and application profile support and ability to use extensions (e.g. to address [issue 34](https://code.google.com/p/xcncip2toolkit/issues/detail?id=34)).   Should have no required changes to connector developers beyond testing, but optionally can then take advantage of the changes in the protocol versions.  John B - already prototyped 2 extensions needed for [issue 34](https://code.google.com/p/xcncip2toolkit/issues/detail?id=34).  Putting in the bib description.  Code changes to core to allow look inside the extension.
  * Adam's Packaging questions - not discussed
  * Stability - any issues of late
  * Lehigh update
  * Indiana update
  * National Libraries Australia

**NCIP Meeting 2/8/2012**

Present:   Bach, Pat
(John and Michelle not available)

General
  * The 2/15 release is looking unlikely due to continued problems with the 1.1 build.

John (via email)
  * As I believe most of you are aware, Patrick, Bach and I are wrestling with a mysterious "Unsupported Service" failure that's preventing them from fully testing their connectors on the 1.1 core. I think Patrick's now got things working, but we haven't yet figured out how to "migrate" that to Bach's environment. As time permits I'll keeping trying to track this down.
  * Also I owe some Adam Constabaris, who's working on the Symphony connector, some updated migration instructions.

Pat
  * LUIS is not working on Windows 7 machine
  * LUIS, LU, Renew are working on a test install in the Test Bed Server, Ubunbtu
  * Question to Patrick - Test bed seems to go down often. Is there something inherent as a problem with the Toolkit.  John will to help diagnose with Patrick.  Not crashed since opening up the tomcat debugging port, so that can attach debugger. Because not a hard crash.   Code starts to be used and some log messages are.
  * Randy would like to review/refresh what the performance test consisted of.  Tested both Core and Connected against LUIS.  One bib per request, concurrent and one at a time.
  * work on using error msgs in compliance with NCIP messages

Bach
  * 1.1 still not working
  * built Linux box, trying it out

Other
  * Need to learn more about Lehigh and NC State work at next meeting


**NCIP Meeting 1/25/2012**

Present:   Bach, John, Michelle
(Patrick is out)

General
  * Connector status, remaining issues - non for John, but perhaps Patrick will find more
  * Extensions for Bib data - will come as soon as a connector gets finished

John
  * Bib Ids in Loaned and Request Items - coding done, but waiting for one connector to finish 1.1.  1-2 days turn around to check in changes plus the items.

Bach
  * Status of 1.1 work - work on config class has been started
  * work on 1.0 will transition right to 1.1, and not do a 1.0 release.   Randy also suggested that he not do the rest of testing with Request and Cancel Request but to focus on getting a 1.1 release out that used LU, LUIS and Renew.
  * John and Bach to talk about adding support for connectors to connect to external websites using https, that require security.
  * LUIS - problem also will discuss with John.

Patrick (sick)
  * Question to Patrick - Test bed seems to go down often.  Is there something inherent as a problem with the Toolkit
  * John will to help diagnose with Patrick

Michelle
  * No work on 1.1 started yet.  Current sprint work is an evaluation of work
  * Looks like the student Doug will be coming back on Monday and maybe can start that.   Michelle to check.
  * Need to sync with changes that Adam (NC State) is making (parsing on Perl side), replacing will be a decent sized changed
  * Needs to talk to Adam, maybe get code changes earlier
  * Recap of discussion in the fall.  NC State to add to LU.  LUIS with VuFind by Lehigh...but has not started.
  * Runs on Jetty, production for a year and only two problems, both with bad data.    No scheduled restarts.

Future
  * 1.1 - readjust release date from 1/31/2012 to February 15.
  * 1.2 - Protocol Version support, and application profile support and ability to use extensions (e.g. to address [issue 34](https://code.google.com/p/xcncip2toolkit/issues/detail?id=34)).   Should have no required changes to connector developers beyond testing, but optionally can then take advantage of the changes in the protocol versions.  John B - already prototyped 2 extensions needed for [issue 34](https://code.google.com/p/xcncip2toolkit/issues/detail?id=34).  Putting in the bib description.  Code changes to core to allow look inside the extension.

**NCIP Meeting 1/11/2012**

Present:   Canceled due to last minute absences, email updates as follows

General
  * John very close to checking in the 1.1 code and then the connector developers will start working on the new config options.

Bach
  * Finished all coding:
1)	LookupItem (Test with 5 libraries)
2)	LookupItemSet (Test with 5 libraries)
3)	LookupUser (Test only with UNCC)
4)	RenewItem (Test only with UNCC)
5)	CancelItem (Test only with UNCC)
6)	RequestItem (Test only with UNCC)
  * I have trouble with other libraries:
1)	Because of SSL (https://wncln.wncln.org/) – therefore I am looking how I can trust all SSL in Java or update cacert file in java home/security – Any suggestion?
2)	Not standard login (some have 3 variables such as username, pin, password, some have only 2 variables: username and password – I had a solution already – Read User request html into arraylist and compare to config file
3)	Requestitem – UNCC have only one page for username, password and pickup location, Western Library – after login – will show another page to select pickup location and cancel date – No solution yet – Can ask other Library goes to same standard to all III database library – one page for login, pickup location and cancel date?


Patrick

  * Added ODBC boilerplate to Voyager connector.
  * Implemented a method that queries database and retrieves bib Id based on given item Id -- tested that this works on the CARLI NCIP testbed.
  * Checked out latest DT code from Git and started trying to understand how the NCIP module works in DT.
  * Today and tomorrow (possibly Friday) I will be working on a bug fix for our VuFind.



**NCIP Meeting 12/14/2011**

Present:   John, Randy, Patrick, Bach, Michelle

General
  * Will do an email update around the 12/28 time in liew of a meeting
  * Next meeting 1/11/2012
  * Randy to extend meeting series until June 2012 on same schedule


Michelle
  * Looks like the student will be coming back
  * Need to sync with changes that Adam is making (parsing on Perl side), replacing will be a decent sized changed
  * Needs to talk to Adam, maybe get code changes earlier

John
  * 1.1 Code - config and the extensions

Bach
  * LU and LUIS are both done.  LU only tested at UNCC.  Will have one other III site to test on before we distribute it.
  * Renew Item
  * The do a release.  John will help get code checked in.   Patrick or Michelle can help with uploading the jar.



**NCIP Meeting 11/30/2011**

Present:   John, Randy, Patrick, Bach, Michelle

General
  * Discussion 1.0 release - John to do tomorrow 12/1 (and release notes)
  * Michelle and Patrick do testing quickly and then build jars and war
  * ready to go by Friday 12/2, announce on 12/6
  * Not slip on other dates

Michelle
  * Shared code with Indiana University - John - is investigating for a self service use.  If decides to use it, then maybe ask to join this call.   On board with changes Adam proposed.
  * NC State - Adam - modifying code - toward goal of changing the parsing - he wants perls script to return JSON instead of ugly string from Symphony (raw string) and currently parsed in Java.  Benefits non toolkit consumers of data, and thinks that perl is better at parsing.  His work is in extending LU.
  * Lehigh work for LUIS is on back burner -
  * Goal to stay on only one version of Sirsi connector

Patrick
  * testing for 1.0
  * Ex Libris - enhancement request accepted - use item id for lookup

Bach
  * working on LU - Title and Pickup location - maybe variation on scheme problem reported by Michelle.
  * working authentication, going well



**NCIP Meeting 11/16/2011**

Present:   John, Randy, Patrick, Bach, Alex, Theo, Michelle

Michelle
  * Coding is done for Sirsi 1.0.  Problem with format of XML in core.  Vendors sample xml missing namespace and missing scheme elements.  Validation turn off may be required.  John has next steps with missing scheme.  Re. namespace (case 37) needs to be fixed at some point and will be gotten around for now by turning off validation.  Lehigh is OK with this.
  * John made the point that turning off validation saves processing time but lose chance to catch a new problem?
  * Building download for 1.0 - Patrick do core and Voyager, Michele will do Sirsi, and then announce it to our list and NISO.  Release notes - connectors will say at a min "updated for compatibility with core" and JOhn will do core.  Randy to send announcement note based on everyone's release note.  Will aim to announce after Thanksgiving.  Goal would be before out next meeting on 11/30, but if not then that means there are problems we will discuss on 11/30.
  * Michelle is at Kuali days - showing toolkit to some other institutions.
  * NC State work - not progressed much.  One of the developers Adam, has some ideas for changing Sirsi connector.  They will try to connect at Kuali Days or shortly after.

Patrick
  * Call slip - work, looking at wrong schema.   Voyager webservices requires both.  Will defer this for now, but there are issues to pick up when work resumes.

John
  * Will commit config code work as early as he is able to, connector developers can then work and John will circle back to work on adding the extensions for Bib Ids in Loaned and Request Items.  Connector developers will then have more work to do.  This will be Translator part of message in 1.1.
  * Security - logging can store patron credentials.   Config file - what level of logging can be done.   Needs to be off by default in Dozer for 1.0.  Wiki needs a note that "This application handles credentials.  Installation doc on wiki - Randy to point John.

Alex and Theo
  * Maven 2 vs. 3 - Requires Maven 2.x right now.  Seems that there may only be a small holdup preventing use of 3.x.   Theo to log new issue and John to look at.
  * Voyager string message problem - Theo needs to connect with Patrick.

Bach
  * Randy to look at email from Bach.
  * missing scheme problem (Request Type is a problem) and John will address when he addresses Michelle's missing scheme elements.
  * Lookup User is 80% done


Other - perhaps schedule a special meeting and invite others for December or January to discuss resource sharing, ILL (e.g. ILLiad) connector


**NCIP Meeting 10/26/2011**

Present:   John, Randy, Patrick, Bach, Alex, Michelle

General
  * Discussion of John's proposal - will it work for others?  The group felt that the proposal (will be release 1.1) is good and heading in the right direction.  Also felt that there is a desire to keep the code base simpler and not require backward compatibility to the config methods in 1.0.
  * Initial release will have a single properties file vs. the ability to configure different parts of the core to work differently (e.g. some parts work one way for resource sharing and other parts work differently for discovery)
  * Connector work will be less than a weeks worth of work, and connector developers will have at least 4 weeks (January 2012) to do this per current plan.

Milestones agreed to by group
  * November 16 meeting - finalize release 1.0 (Michelle and Patrick to signoff that Voyager and Sirsi connectors work and are ready to go)
  * December 31, 2011 - Release 1.1 core code to developers, with additional information for developers will be made available (this is not a release of 1.1, this the date John will complete the implementation of his proposal and when developers can begin to make connector changes).  John of course is welcome to do this earlier.
  * January 31, 2012 - release of 1.1 core with connector support for Voyager and Sirsi (and perhaps III depending, this is not a requirement for release 1.0 nor 1.1, but Bach will try to use the most current code)



**NCIP Meeting 10/19/2011**

Present:   John, Randy, Patrick, Bach, Alex, Michelle

General
  * NCIP SC Meeting - will officially be adding LUIS, optional due date to Item Optional Fields, repeatable, optional Bib ID to the Loaned Item and Requested Item elements.  Good news.
  * John to talk to Tony O. about the schema to include the Bib Id work sooner vs. waiting for for official inclusion, as it impacts the Drupal UI from what it now has (my account feature will lose functionality)
  * Discuss Configuration proposal for Toolkit from John B. - had limited discussion on this and decided that next meeting will focus on this.   Randy asked if all for connector developers could come ready to answer the question of whether this needs backward compatibility.

Patrick
  * Performance test - load test.  LUIS 1-3 seconds.  Majority of response time is in Voyager connector.  Next step could be to add timing code to Voyager connector time waiting, objective test.
  * John to work with Patrick to add Voyager response time code.  Will copy other connector developers to follow along with the process.
  * add additional constructor that takes value parameter from all the scheme value pairs - added some and others had questions on.  Send list to John for input.
  * testing - place hold request, Voyager needs bib id and location (internal number).  Need to figure out solution.  Patrick has list of things to discuss with Randy.  Callslip request need both bib and item id, but think we will be ok with this.

Michelle
  * Intern working on Dozer core had exams and working is delayed.   Another 2 weeks or so.  Team is good with waiting.  Also will give more time for Drupal integration

John
  * updates were covered in other parts of conversation

Bach
  * LUIS for III is essentially done.  Next work will be for Lookup User.  One page with many different piece of data.  Don't see personal info.  Screen scraping is specific to UNCC?  Any way to allow easy customizations to other institutions?  Bach to explore with Micheal other III institutions to test with (maybe UNC Chapel Hill??)
  * Randy - other III sites that can test against.

Alex
  * No updates

Next meeting:  due to schedule conflicts, the 11/2 meeting will be rescheduled for next week 10/26 at 11am eastern (same day of week and time, just on the off week)

Future:  Support for multiple schemas - include discussion of bib id appearing and impact it might have



**NCIP Meeting 10/05/2011**

Present:   John, Randy, Patrick, Bach, Alex, Theo

General

  * Waiting on Sirsi connector to weigh in on 1.0 release

John
  * Wants to add issue using 2.0.1.  Requires change to service classes.  In 2.0.1 can repeat elements. Add attribute to do check.  Add new method to service.  Changing service classes in connectors.  When message comes in it declares which version it is using and new core code would check that then uses the appropriate schema to parse the message.  Copies into service object.    Makes it possible to take advantage.  Goal to make it backwards compatible.  Will also support other coding, like JSON.
  * This will come after the Dozer changes.   Probably 2nd major release after the 1.0 release.
  * Performance testing - ready for Patrick to install and run against Voyager.   Thinks the biggest delay will be in responding to Voyager.

Bach
  * LUIS message is working.  Works if bib only has one item, and working getting it to work for multiple.

Patrick
  * Proactively add additional constructor that takes value parameter from all the scheme value pairs.   Patrick - to take this on.
  * install performance testing and execute against Rochester Voyager.
  * His 7.2 environment is gone and can use demo site for non UB requests.

Alex and Theo
  * Voyager -   Issue with it being very single client oriented.  Voyager can only communicate with one Voyager url?  Multiple Voyagers would require multiple instance in same web server.   Configuration issue that John had started working on a few months ago should help this.  Would need to connect to Voyager services all over the world.  Bind in the jar file for Voyager/ILS into application.
  * Per John, ultimate goal is to put the configuration behind a UI (registry).
  * Currently very static and hard coded where to run.

Releases
  * 1.0 - Dozer core - Only work remaining on core is the proactively search and fix for scheme/value pairs that Patrick will do and then need the Sirsi connector updated.
  * 1.1 - Configurable connections, allowing multiple connections to many ILS- John write up and share approach before IP filtering work, and will be via email before next meeting.   John will take work on.  Will probably require a couple of days of work for each connector developer
  * 1.2 - Protocol Version support, and application profile support and ability to use extensions (e.g. to address [issue 34](https://code.google.com/p/xcncip2toolkit/issues/detail?id=34)).   Should have no required changes to connector developers beyond testing, but optionally can then take advantage of the changes in the protocol versions.  John B - already prototyped 2 extensions needed for [issue 34](https://code.google.com/p/xcncip2toolkit/issues/detail?id=34).  Putting in the bib description.  Code changes to core to allow look inside the extension.

Next meeting:
  * John B. - Proposal for hybrid by IP filter.  Servlet filter, common approach.  Nobody had any better ideas.  John to write up, provide examples, send to this list and ILS DI in advance of next meeting

**NCIP Meeting 09/21/2011**

Present:   John, Randy, Patrick, Bach, Alex, Michelle

General
  * Topic Lookup Item vs. Lookup Item set - Bach should switch to using bib id in LUIS and abandon for now the use of LI.  The code Bach inherited from Owen was from NCIP 1 and we broke the protocol rules and allowed bib level ids to be sent, but in LI only item levels ids should be used and we are abiding by NCIP 2 rules.  John to continue to work with Bach.
  * Dozer Impact - 1st issue - many scheme value pairs and the code did not allow just the value to be constructed (hold over from NCIP 1 which required them).  John to throw in the rest that are missing (constructor to class).
  * Dzer - 2nd issue - omission in code that does mapping for requested item element, but there may be other similar omissions.  Let John know if there are problems and he will look at quickly.    Patrick reported that he experience a similar error message with LUIS with Title but Patrick reported he fixed it by including the monetary value value..so developers should be aware that error maybe related to something different that what was really missing.  If Toolkit says you are missing something, it maybe a different element.   Contact John B. if that happens.  He will run back through the code when he has time but will address quickly anything that people raise as issues.
  * Test Bed - Lookup Item - not supported in EL webservices.  Patrick to open an issue with EL to explore.  Randy added just a few comments to make usage of the test bed a bit clearer.
  * phone vs. Skype - we will continue with the phone call in

Michelle:
  * Hired a work study student (Doug) that will be making the Dozer changes.  Started last week.   Using Netbeans.  Michelle helping him.  Michelle will let Emily know.  FYI...Michelle is half time on Kuali.
  * Emily found an issue with connector, so Michelle is needing to look at.  One of config settings.  Using in one and not in the other.  Michelle will add issue.

John:
  * Load Test - Reads in file of IDs, running against Dummy.  There is statistics summary.  Will check in changes.  PZ to send url.  Randy to add John's ip address to server.  John to test, Randy to review and will involve Patrick as needed.  Then the code will be checked in and any connector developer can use.  John....will need to explain use on wiki.
  * Make work in trusted environment or not? - ILS Interop group should decide full use case.   Open, Closed are covered now.  Know that there is a desire for Hybrid - John to create proposal and spec'ed out before it's done in Toolkit.

Bach
  * See General.  Working with John.  Making progress and getting responses from Millennium.  Awesome news.

Patrick
  * Dozer change work continues.  LUIS is done.  Have to go through each test case on test bed and test.
  * Voyager connector config for LDAP, non-LDAP
  * Test Bed - Lookup Item - not supported in EL webservices.  Patrick to open an issue with EL to explore.

Next meeting:
  * [Issue 34](https://code.google.com/p/xcncip2toolkit/issues/detail?id=34) proposal - John B
  * John B. - Proposal for hybrid by IP filter.  Servlet filter, common approach.  Nobody had any better ideas.  John to write up, provide examples, send to this list and ILS DI in advance of next meeting



**NCIP Meeting 09/7/2011**

Present:   John, Randy, Patrick, Bach

General
  * Release 1 definition remains unchanged.  Need Sirsi connector to work and have been tester with Dozer core, XC Drupal Toolkit to be upgraded and able to use Voyager connector with Dozer core, performance testing (John) to be completed, releases for Dozer core and both ILS connectors.
  * Test bed was released 9/6/2011.  Emails sent by Randy to ncipinfo NISO list, XC General, XC NCIP and code4lib lists.
  * Test Bed required a new Core to be released.  So from now on version 0.1.2 is the pre-Dozer changes version which incorporated a few fixes for the recent Voyager and test bed release. And the "Dozer version" is 0.1.3 (and forward).   If you've checked-out the trunk since John checked-in the Dozer changes on August 22nd, you will need to re-get the trunk and build the projects again to create the properly #d jar and war files.

John
  * Busy with non NCIP Toolkit stuff lately.
  * Performance testing is next work.  Class is written.  Developer modify script to point to right url and correct file.  Timing code will spit out to a log. John's program will test LI, LUIS, LU.  Timing test complete round trip, not core timing vs. connector.  John to look into adding a new feature time core vs. connector time.

Bach
  * John working with him.  Able to build core, but still getting errors with the Millennium build.

Patrick
  * Compliance with new Dozer Core will be next work
  * Needs to test LDAP working - any more testing required?
  * Configure to check LDAP or not first, Only Voyager, hybrid
  * Make work in trusted environment or not? - ILS Interop group should de

Michelle (reported via email)
  * I have not looked at the Dozer code yet, but I have been in touch with Emily to let her know I will be making Dozer related changes to the connector.


**NCIP Meeting 08/25/2011**

Present:   John, Randy, Patrick, Bach, Michelle

General
  * When to release 1.0  Brainstormed what should be in place.
  * Core Release Notes need to be written and Release 1.0 along with connectors that have been made compliant with new core.
  * Definitions - Supports Sirsi and Voyager (expand), test bed environment, 2 areas of application, resource share and discovery, Drupal Toolkit inclusion.
  * everyone to review open issues and we will set definition of release 1 at next meeting.
  * Test Bed - what to say, work with group, Randy will be point of contact.

Patrick
  * Voyager Test Bed and Connector release at same time.  Will be LU, LUIS, and Renew Item, all on webservices.  Called Release 0.1.2
  * Compliance with new Dozer Core will be next work
  * Needs to test LDAP working - any more testing required?
  * Configure to check LDAP or not first, Only Voyager, hybrid
  * Make work in trusted environment or not?

John
  * Performance testing.  Class is written.  Developer modify script to point to right url and correct file.  Timing code will spit out to a log.
  * John's program will test LI, LUIS, LU
  * Timing test complete round trip, not core timing vs. connector.  John to look into adding a new feature time core vs. connector time.
  * What about Evergreen?  Work with new core?   John to follow up.

Bach
  * Not setup dev environment, has some questions out to Patrick
  * Learning III still

Michelle
  * Emily been in communication and reports LU working at NC State.  Planning to add to it to complete and bring to ILS Interop group recommendation.
  * LUIS service work - this sprint may start depending on time to upgrade to Dozer core.
  * NC State -LU  Dozer core compliance by Michelle first and then added function by NC State.  Michelle to do communication.


**NCIP Meeting 08/11/2011**

Present:   John, Randy, Patrick, Bach

General
  * Looked at GC [issue 26](https://code.google.com/p/xcncip2toolkit/issues/detail?id=26) and see of there are any open questions.  There was an open item to discuss 2md part fix.  The that Tells JAXB to create XML is fixed.  Earlier in the process the part with Jaxb hand translation will not need to be fixed given the Dozer work John is doing.
  * Schema testing - [issue 31](https://code.google.com/p/xcncip2toolkit/issues/detail?id=31) - after the Dozer work

Michele
  * Reports that she has the Perl scripts from Emily at NC State.

Randy
  * Webservices not setup for Training databases and the person that can do it is on vacation.  We will resume Testbed work next week and hope to announce the Test bed by end of next week (8/19/2011)
  * Pascal has sent our a narrow call for Aleph connector work.  If no bites, then will broaden the ask.

Bach
  * Pat to finish Eclipse dev setup instructions today
  * Bach to setup his dev env
  * Install and use Owen's code
  * Continue learning III and then add more functionality.

Patrick
  * LUIS load testing on restful interface
  * Renew testing with UB
  * Install Eclipse and documentation on wiki.
  * Renew Spreadsheet - fill in/document missing data elements
  * Order of serials in response
  * Issue with Ex Libris on non circ items

John
  * Command line test can be created to repeatedly run same lookups to help load test.  About an hour to write.  Question exists on will subsequent lookups for same bibs be faster?
  * Will do types of LUIS test - one request with lots of bibs and  many requests of single bibs.  Randy to confirm best approach.  (note, Randy confirmed that first approach will be lots of single bib requests)
  * Randy will generate list of ids from Rochester Test Voyager (not testbed).  One bib id per line.  ODBC and web-services can then be tested side by side.
  * Upgrade to Dozer.  Few days away.  Has written notes on what developers will need to do.  Patrick will to be be first to convert the connector code to comply.  This is highest priority at this point.
  * Worldcat local - amlib and wms systems communicate with Worldcat local.  WMS is the first that is going to be integrated with NCIP Toolkit code. wms is webscale library management system.  This work is just getting going so no announcement to be made for a while, but very exciting.



**NCIP Meeting 07/27/2011**

Present: John Bodfish, Michelle, Bach Nygyen, and Patrick Zurek.

General

  * Test Bed - Raised the topic, Randy will drag Patrick along.  Perhaps our group, Randy?, will coordinate and manage potential conflicts when the time comes.  Some hand holding with testers will have to happen.  Refresh database each day.  Users should attend these calls.
  * Welcome Bach - John to take a look and work with Bach in diagnosing installation problem.
  * Bach to keep list of issues that come up during install and we will put on wiki

Michelle
  * Code4lib article is published.  Congrats.  Will also sent to NISO NCIP SC, Ill Interop and Lita list?  Randy the first 2 and John the last.
  * Blip with toolkit - privilege expire dates in 1995.  Connector caught the error.  Would be applicable to Sirsi Connector and apply to other Sirsi clients too.  Michelle to add issue to GC enhance LU.
  * [Issue 30](https://code.google.com/p/xcncip2toolkit/issues/detail?id=30) - work with NCState - swap perl scripts, work during August.  Will send email to get the exchange of info going.

John
  * Conversion to Dozer - couple days from being done, and will handle everything.   Will try to have in July.  The August work that Patrick, Michelle and NC State will do should use this and the connecters should be updated to reflect the changes.
  * Relates to [issue 28](https://code.google.com/p/xcncip2toolkit/issues/detail?id=28) - Setting up Bach, general setup.  Use of Maven or not?  Patrick uses Maven and Eclipse.  Focus on Eclipse and Maven.  Content improvement. John to start the documentation process
  * Randy to respond to John with time to discuss schema changes.

Patrick
  * Converted RenewItem to Restful for Voyager
  * Voyager 7.2.5 environment is setup now
  * Problems with RequestItem - can't get toolkit to add new service.  Will work with John.
  * NCIP Compliant messages - testing to resume.  Need to revisit NCIP Schema compliance, test of Xerces.   Issue to be added.
  * Status of open issue with Ex Libris.  Will relook at it.

**NCIP Meeting 07/13/2011**

Present: John Bodfish, Michelle, and Patrick Zurek.

General
  * In need of Aleph developers to continue the work Notre Dame started.  Rick will document where his work left off.  Question was asked if other developers (perhaps that had Aleph experience in the past) could work on against a test database.
  * NC State and Lehigh will be doing an exploratory collaborative Sirsi effort in August.   NC State will look at LU and Lehigh will look at LUIS. If that goes well, pehaps additional connector work will continue.
  * Updates/changes for submission to NCIP SC - John look at changes
  * XC Drupal Toolkit work is starting and will use XML requests as it is the fastest route to completion.
  * Randy will triage issues posted to the issue list.  Anyone is welcome to comment at anytime.

Michelle
  * Suggestion - create a slick install for developer environment.  Michelle will pass John's name and email to him and John can work on requirements for further review.

John
  * Working on adding missing field in the initiation header for [issue 27](https://code.google.com/p/xcncip2toolkit/issues/detail?id=27).  Hoping to finish today.  Not a core build until a connector developer requests.
  * NCIP Schema validation - will force discovery of fail points.  Is a configuration change but in Spring.  Will make the Toolkit operate more slowly.  All developers should development with it on.  Will be on by default.  When a site goes from testing to production use and has need of the performance improvement, make a config change to turn off.  The change is in the Spring file.  Open new issue.
  * EVERGREEN - Lending Side - CheckIn, CheckOut, RequestItem in production.  Eastern Oregon in production with WorldCatNavigator.  Yeah.  Will announce when we can.

Patrick
  * Test new NCIP Schema validation - will force discovery of fail points.
  * Working on non-NCIP stuff, and not back till Friday.
  * Dev environment back soon
  * Priority:  Get new core with NCIP validation, Documenting Excel requirements for all services, test the Schema Validation, RenewItem for UB aware, then RequestItem (as adding new service), LUIS - order of the items returned.



**NCIP Meeting 06/29/2011**

Present: John Bodfish, Michelle, and Patrick Zurek.

Michelle
  * Thinks their session (on VuFIND, NCIP and Drupal) drew the largest audience at the Sirsi User meeting (north east)- Awesome!!!
  * Will check out/test latest core performance enhancement at the same time at the Dozer stuff (see below).
  * Cod4lib article in final edits

John:
  * Core Not validating against the schema.   Used to and then it stopped.  Still need to find solution as it is not finding the schema files.  Randy to make sure issue created.
  * No progress on Dummy connector - after the Dozer work
  * Performance enhancement work - change made and Patrick has tested.
  * Exploring tool called Dozer to help with the hand creation of the service based classes (Add methods to JaxbTranslator class).      Thinks it might be a more maintainable way to go.  Used NCIP 1 to test against and results were good.  Code is slightly less performant, but negligible.   Would empty JaxBTranslator class, and replace.  Couple of days of work for John.  Should have no impact on connector developers.  John can make sure each connector builds.   Do before we get to Cancel Request.
  * John email the group with a proposal for a new configuration approach. But these should come after the Dozer work.
  * Evergreen testing this Friday.
  * Recap priority 1.  NCIP Schema validate 2. Dozer work and connector developers need to test 3. Config proposed changes, include Dummy connector work

Randy
  * Randy is now officially on the NCIP SC
  * My change suggestions for NCIP SC - John to review and get back to me

Patrick
  * Development environment at CARLI is gone for probably the next two weeks.  Was going to work on converting webservice Voyager to be UB aware.
  * Switched over to Request Item on Rochester environment
  * Patrick - document difference in Web services - document at next release.  Probably document now on wiki and then expose the wiki page when we do next release
  * Order of Items in LUIS - connector developers should return multi volume sets and serials in ascending order as default.
  * LUIS- impact of change from BibliographicItemID to BibliographicRecordID for local ILS ids (we have been doing it wrong)- Patrick to add FB issue- sample XML being updated.
  * Need to let Rick for Alpeh know about the change from ItemID to RecordID
  * LU - putting ToAgencyId of the InitiationHeader - is correct change and no impact on anyone



**NCIP Meeting 06/15/2011**

Present: John Bodfish, Rick Johnson and Patrick Zurek.

John:
  * Working on making Dummy responder a connector and adapting the configuration accordingly.
  * Have discovered several issues with the way configuration is presently done - e.g. hard-code directory names, dynamically modifying classpath, etc.
  * I'll email the group with a proposal for a new configuration approach.
  * If we make changes, we'll need to maintain the present approach for existing connectors until they switch, if they ever do, to the new approach.

Pat:
  * Wrapping up lookup user, significant functionality is missing - he'll discuss with Randy.
  * The problem Demian was having with the responder crashing doesn't happen for him, but he's using the test form (ncipv2/index.html) not actually sending the NCIP message from a remote machine as Demina would be.
  * Pat will send John an example message for testing and John will use the test client classes to run tests over a remote connection in an attempt to reproduce the crash.
  * There are cases where the tomcat startup appears to go cleanly, but the NCIP responder application is not available, and when he goes to shut down tomcat there are errors indicating that tomcat is not running.
  * Pat will send John sample logs and output in hopes John can recognize the problem or at least suggest how to troubleshoot it.

Rick
  * No new updates, he's been pulled to other projects.
  * So current status is as before: Lookup Item & LUIS done, not looked at others yet.
  * At this point, may be time next week or week after to work on it.


**NCIP Meeting 05/18/2011**

Attendees:  John, Randy, Pat, Michelle

Michelle

  * Released new Sirsi connector and release notes.
  * First draft for Code4lib in a couple of weeks

John

  * Some work on config
  * Work with Jon Scot on an Evergreen Connector- Check in, Check Out, Request Item are working with Evergreen.  Other planned AcceptItem.   Will be for Navigator to do circ integration.    Work on Toolkit connector and using lots of Perl scripts.  Will be added to XC NCIP Toolkit when complete.  Will be very configurable and may be able to be re-purposed to other systems to call Perl or other CGI-BIN scripts.  Lookup item type work will use z39.50 and Lookup user type work will be via an alternative authentication protocol (instead of NCIP)
  * 

Randy
  * Cancel next weeks meeting
  * Next meeting will be one June 1, unless Michelle, Rick and John report that they would like to cancel that too.  It will depend on progress and existence of discussion topics.

Pat
  * UB and xws - 76 instances.  Have some decisions to make and will get back to me.  These are config and setup issues, not NCIP Toolkit
  * Ticket with Ex Libris will be opened for the non circ item issue.  There was a delay due to Eluna.  Kathy should do it today.
  * Check that the web 8 changes are being reported to Ex Libris
  * Non circ item will be for 7.2 and 8.0
  * Webservices LUIS is done.  Still need to do LU User and Renew has a bug (Randy needs more info from Pat).




**NCIP Meeting 05/11/2011**

Attendees:  John, Randy, Pat, Michelle

Michelle

  * Skype call with Joe from Dartmouth.  May not be able to much further due to limitations with III.  Randy says to let him him know if he should follow up in any way.
  * Code4lib proposal accepted, quarterly publication - Yeah!
  * Accept Item code updated for real title and going to release a new Sirsi Connenctor jar
  * Release notes for Sirsi Connector will be added to wiki
  * Resource discovery not on radar for next few months

Pat
  * Status of Webservices Voyager, not all records returned as we discovered that non-circulating items (e.g. reference items) not being looked up.
  * Working on Voyager connector that uses both kinds of webservices.  Patrick reports that the restful were designed to be used in tandem with the older xml services.  Documentation for Restful services actually references using the XML services.
  * To create a list of what is missing from ODBC services for Randy and he to discuss
  * Open issue today with Ex Libris regarding the non-circ issue we discovered


John
  * Dummy connector work continues

Randy
  * Lookupversion stands outside of Implementation Profile, either v1 or v2, so that it will not change.  Lookupversion not happen until  use case arises or funding is found for all of NCIP
  * Followed up with interest from  Tom from http://www.finalist.nl.  Not heard more yet.
  * If any one has leads of interest, please direct them to Randy.


**NCIP Meeting 05/04/2011**

Attendees:  John, Randy, Pat

Michelle (not present)

  * Staff at Lehigh contacted Sirsi about making the Sirsi code for this project more freely available as was done with VuFind.  Sirsi was not receptive to the idea, so it will remain available as currently.
  * On Friday will walk Joe from Dartmouth through the Toolkit and Connector code using Skype (sharing her desktop).
  * If Dartmouth is interested in pursuing the Telnet approach for III Millennium, we should contact OCLC about sharing some of the work they have already done.

Pat
  * Released 0.1.1 Voyager, it includes several bug fixes to LUIS.  It is still ODBC driven.
  * New core will go about in next day, and it includes changes for Dummy Connector (not require having connector installed).  Core will now install and work with out a connector being installed.  There are still some errors to be cleaned up but we will not hold off.  John to create an issue for these exceptions he has found.
  * Will Let Demien know he can  resume test.
  * Web service Voyager Connector - will not include Lookup Item (not able to use web services starting with an Item ID) and the LUIS will not include Hold Queue Length (the data is not available via webservices).
  * Still need to make LUIS and LU be UB aware.
  * Priorities after that are Renew with XWS and UB aware, followed by Request and Cancel Request.

John
  * Still working on Dummy as a separate connector
  * Issue with exception in Core that were just found
  * Standing Committee - not moving LUIS forward into standard yet.  Need more information for data dictionaries, documentation, etc.  Next meeting in 6 months should hopefully see it being promoted into standard.   The concept was generally well received with no philosophical issues raised with it.


**NCIP Meeting 04/27/2011**

Attendees:  John, Michelle, Pat, Rick

John
  * Not finished making the dummy responder its own connector. Changes will need to be made to Spring configuration file that hopefully will not impact any other part of the connectors. Will install another connector to test that.
  * At the NCIP Standing Committee meeting Relais announced that the PALCI consortium is in production using NCIP.

Pat
  * Fixed several bugs in LUIS, working on the last one. When finished will put out a new release for Damein to work with.
  * Converting LUIS to work with web services.
  * Have to use a mix of XML over HTTP along with RESTful because not all the data needed is available via one or the other.
  * Hold queue length is not available over either XML over HTTP or the RESTful services.
  * The web services are about 10 times slower than Oracle calls. It's taking about 15 sec for 20 items, whereas Rick is seeing about 5 sec for 30-40 items against Aleph, which is not nearly as bad. Because the new OPAC uses these same web services and its performance is acceptable we are hoping it is a matter of tuning and not of asking Ex Libris to re-write their web services to achieve adequate performance.

Michelle
  * Has not yet done the one coding change from last time.
  * The proposal for CODE4LIB presentation that was accepted.
  * Got an inquiry from Dartmouth about the presentation they're doing at the SD meeting; sent him some documentation and offered to walk him thru code. Dartmouth is a III Millennium library.

Rick
  * Finished coding LUIS, needs to finish unit testing.
  * Next will be working on Lookup User


**NCIP Meeting 04/13/2011**

Attendees:  John, Randy, Michelle, Pat

Discussion
  * Next weeks meeting 4/20 will be canceled due to the NCIP meeting on April 20 and 21.  Proposed agenda has been sent out.
  * Call in info for NCIP meeting - John to look at and send along.
  * The 4/27 meeting will be facilitated by John B. while Randy is on vacation
  * VUFind work with Voyager - obstacles faced by Damien.  John needs to check in dummy config file and then Patrick will rebuild zip.  Due this week.
  * Configuration of Voyager config file, documentation of elements in file, wiki can point file.  Another obstacle faced by Demian that we will resolve.  Release this week.
  * Randy create issues - 3 of them - core,  Voyager and Sirsi.  Patrick do release.
  * Patrick - Webservice Work for Voyager for LUIS.  Looks like LUIS (1-2 weeks) and LU (1 week) are good to go based on bib ids and user info.  This is next priority for Patrick.
  * Code include the schema owner?  Randy Create issue.  Change the SQL in connector to prefix the table name.  Need to work both with and without table name supplied.  Check with Damien that this solves problem.
  * From Rick via email "I started work on the LookupItemSetService for Aleph.  It is about 50% complete.  I am using the Voyager implementation as an example and should have something complete by next week's call."
  * Michelle - everything going well with live implementation.  Create temp record for Accept item, and put actual Title in the Title element and will put on Google code.
  * Michelle will submit an article in code4lib.  Deadline is 4/22 and group will vote on.
  * Michelle contacted by Bywater about possible Koha interest.  Will keep us posted.
  * John worked on Dummy separation from Core.  Not in next release.  Target release early May.



**NCIP Meeting 04/06/2011**

Attendees:  John, Randy, Michelle, Pat, Rick

Discussion

  * Michelle fielded question about release and attendance at a large Sirsi mtg.    Lehigh will not be going there, but are continuing work toward a presentation at the Lancaster meeting.    Also still pondering Code4lib angle.  Perhaps how library has bridged gap between standard and a vendor api, tell story of what was needed and success.
  * VUFind - would need functionality to send request.  VUFind support for NCIP 2.0.  Randy to touch base with Damien.
  * Randy has to follow up on a Horizon request that came in.
  * Is there any similarity between Aleph/Voyager webservices.  Rick to take a look over the next week.  Rick - looking at sharing of code.  Aleph work to-date has only used xservices.   Voyager to-date has used only XML over HTTP.  In future, for new work, should consider use of rest services.  Looks like the rest servies share a lot of overlap, but not identical.
  * Pat - lookup what data loss would occur, if any, for Voyager if we move the existing work (LU, LIUS, LI) to webservice instead of database calls.
  * Pat to continue work on Request Item afor Voyager
  * Rick - Aleph - Lookup Item is done.  LUIS (would be next) and then LU.  Feedback on LUIS from another ILS developer would be helpful to the LUIS definition.
  * John made some progress on Dummy Service.  Not started Cancel Request.
  * John and Ex Libris testing work that is coming up- will relay community needs perhaps for vendor to support good array of web services and let community build connector (NCIP, Jangle, other).




**NCIP Meeting 03/30/2011**

Attendees:  John, Sharmila, Randy, Michelle, Pat

Discussion

  * Meeting Frequency - stay weekly,another 2 months and then reassess
  * Michele - announcement Randy drafted looks pretty good, but having it reviewed.  Will post to the following- XC Announcement, NCIP Developer list, NCIP list, Code4lib
  * Announcement for release of NCIP connectors for Sirsi and Voyager
  * John - how to sign up for NCIP list, then Randy post there.
  * Michelle to consider article for Code4lib, and our hope would be that it would induce participation perhaps both for more services and ILS.  Submission deadline April 22.  Will also perhaps do a presentation at Sirsi user group meeting in Lancaster.
  * Does not appear to have circ type webservices for Voyager.
  * Pat take over Voyager Connector work after Sharmila's departure, look over code today and discuss with Sharmila
  * Consider for future a way ask Ex Libris to create standard webservice for check in /out etc. and let community develop against that standard.
  * Patrick has training database and also access to Rochester NCIP dev environment so that he can support the Voyager connector.
  * John - wants to get back to work to make Dummy Service after Cancel Request.
  * General discussion on how to make NCIP Toolkit more used more broadly.  For example, Summon could use toolkit on initiator side that send message to connector directly.  John to create power point of two options using the Toolkit to talk to connectors directly and ways of adding protocols to the toolkit like Jangle.




**NCIP Meeting 03/23/2011**

Attendees:  John, Sharmila, Randy, Michelle, Pat

Discussion

  * Sharmila estimate for Check in and Check out to Randy.   Discussed a conversation Randy and Kevin had (from Relais) about adding Voyager connector support for some resources sharing services.
  * Michelle - connector works with Unicorn and maybe Symphony but probably not Horizon.  Will do some research into what actually works and possible need to rename some code files, on wiki, etc.
  * Versions of ILS supported - need to be clear in our wiki pages about what version of ILS are supported.    For Sirsi this includes which Sirsi products are supported and for Voyager this needs to include version 7.1 and 7.2 notes.
  * Michelle - had to make a change to perl scripts for item type temp item, needs to be deleted after the Accept Item is done.  Making a change to perl scripts and putting more notes in for other Sirsi Clients.
  * Due Date now in core for LUIS - Sharmila to add Due Date to Voyager LUIS and then release new Core and Voyager connector for LI, LU, and LUIS.  New War file will then be used by Michelle, let her know when it is available
  * Final discussion on readiness for announcement next week- hope to announce release of Sirsi (which products) working for Check In, Check Out, Accept Item and Lookup User.  For Voyager hope to announce LU, LI, LUIS and perhaps RenewItem.
  * Renew Item - at Voyager is with HTTP webservices.  Sharmila working through some issues.
  * Patrick has no dev environment for next 3 weeks - they are doing an upgrade to Voyager 8, Oracle upgrade, and Solaris.    Will touch base with Randy about best ways to optimize the situation.
  * Request Item - request has been started by Sharmila
  * Cancel Request is not in Core Code yet.  At this point it is not expected to be in eom release when Sharmila leaves.
  * Hope is that at Sharmila's departure we have Voyager connector working for LI, LUIS, LU, Renew and Request using HTTP web services.     Patrick will pick up this work in April and create a new Voyager branch that will make use of Restful web services.



**NCIP Meeting 03/16/2011**

Attendees:  John, Sharmila, Randy, Michelle, Rick

Discussion

Rick:
  * All of the code into the Aleph code base now, checked in.
  * Lookup Item pretty much done - need to confirm that "required" fields ILS-DI spreadsheet are there.
  * Then Lookup Item Set

Michelle:
  * Pretty much ready to release code & driver jar files.
  * Wiki page for Sirsi connector is updated.
    * Had last-minute fix to Sirsi scripts.
  * Will wait for newer core file.

John:
  * Added Schemes to Scheme Loader.

  * PALCI 45 of 50 schools ready, they have to make code changes so it probably won't go live within the next week.
    * Sharmila: Want me to incorporate DueDate in schema. I'll do that w/i the next day or two. [Ummm...]
    * Sharmila and Randy will discuss when to release Voyager connector, which will include new core file, which will then allow Michelle to release new SirsiDynix connector.
  * Sharmila completed Lookup Item Set and is working on Request Item
  * Rick will be out next two weeks (conf next week, vacation)



**NCIP Meeting 03/9/2011**

Attendees:  John, Sharmila, Randy, Michelle, Rick

Discussion

  * Michelle - update - document for developers how to get set up in Eclipse. Code work still in progress for Sirsi connector.  Email coming to JB and SR on a question about required field that should not be required.
  * LUIS is on the April NCIP standing committee meeting.  Meeting may be general idea discussion and perhaps get into reviewing the schema.  Schema is submitted to group.   John will try to get discussion going before the meeting.  Next steps TBD.
  * John to send out info on the NCIP Implementers group mailing list for this group for anyone interested in joining.  Also will send call in info for the April meeting.   Randy would like to call in for this.
  * Rick - keep getting pulled, but code in progress.  Will convert ANT stuff to Maven to remove one more dependency.
  * Sharmila - LUIS is updated, will install on server.  Randy then test.
  * SR - Request Item and Cancel Request will be looked at next....waiting on Renew Item Code check in from Pat.
  * John to work on Cancel Request Item core code.
  * Ex Libris link from Rick, goal was to minimize code or reuse code if possible.  Sharmila - to look at to see if any benefit from reuse or if SR code is better then Rick may use hers.
  * John to run next weeks meeting in Randy's absence.


**NCIP Meeting 03/2/2011**

Attendees:  John, Sharmila, Randy, Michelle, Pat, Rick

Discussion

  * Michelle - update - no major news, still planning on work Sirsi work by end of next week (current sprint)
  * Rick - Aleph - currently has 2 jars, one external to Google code base, but he will combine into XC SVN.  Unit test for LI is done.
  * Pat - working on Renew item, having difficulty getting it to work for Voyager with the Universal Borrowing (UB) layer.  Will open issue with Ex Libris.  Also reports that there is nothing in the web service response (that he knows of) that is a message, indicating success, failure etc.  Is attending an Ex Libris developer conference next week and will see what more he can find out.
  * While waiting for Renew item road blocks to clear, Pat will work on making LookItem and Lookup User user web services instead of Oracle queries.
  * Recapped that an installer of the toolkit would be expected to download 2 zip files - one containing the core warm and config or log related files and the other for the connector of choice that would contain one jar and config or log related files.
  * Rick to share a link re. Ex Libris web services that might benefit Pat and Sharmila.


**NCIP Meeting 02/23/2011**

Attendees:  John, Sharmila, Randy, Michelle, Pat, Rick

Discussion

  * Michelle - update - will sync up Sirsi connector now and again later in a few months.  Has been give time to do this over the next 3 weeks sprint.  Sync the 4 services with new code base and deploy for other Sirsi Clients, including externalize config settings,  documentation, perl scripts on shared Sirsi sites.
  * Rick - Lookup Item service written for Aleph, and working on test cases.  Then Lookup User and LUIS.
  * Aleph - will include mapping config setting for consortium use (Notre Dame is a consortium of 4) and includes a config mapping for Agency ID.  This would allow a mapping from say an LC Org code to an ILS OPAC instance.
  * John - will do a diff of new LUIS schema vs current and send out to group.
  * LUIS - hopefully the ILS ID group will approve draft LUIS Schema today and John will work on incorporating changes and hope to have this out March 4.
  * Voyager Consortium use - will need use of Agency ID. Pat and Sharmila to discuss approach.
  * John mentions some Service properties files in web application that are empty.  No one reports using them or John will remove them?
  * Create LUIS minimum standard? - Will assign issue to Randy but not sure when I will get to it.


**NCIP Meeting 02/16/2011**

Attendees:  John, Sharmila, Randy, Michelle, Pat, Rick

Discussion

  * Most developers know I opened and assigned issues to developers that previously have been in meeting minutes (and in danger of me forgetting about them).  Note that issue updates will post to the NCIP developers mail list.
  * Michelle reports success.   Perhaps she will be the first into production use of NCIP 2 Toolkit services.  Current work is non NCIP parts of software
  * Michelle will figure out whether she proceeds with making Sirsi Connector code sharable now (eg. next month or two) vs. later (many months from now).   Thinks there may be some other Sirsi interest and will see when this can proceed.  Has a student worker to help code over the next few months.
  * Rick - start with Look Up Item today for Aleph.  Will proceed to Lookup User, Renew Item and Lookup Item set.
  * Rick commented that at Notre Dame they currently make use of some NCIP 1 Toolkit code code that talks to Alpeh, enabling authentication, single sign on service if user account in catalog, can then give access to resources all in the Consortium.  Check in and out being done via other non\_NCIP methods (Primo layer).  Not currently using NCIP services themselves.
  * LUIS - Draft implementation version nearly done for Voyager.  Sharmila/Randy coding/testing now.  Will use as an example to ILS Interop group.   John not making any schema changes until dust settles with Interop group.  Most of current potential change revolves around pulling some possibly repeatable data out of the lower layers and moving it up in the hierarchy (e.g. call number up to holding set level)
  * Authentication - discussion needs to occur between Sharmila, Pat and Randy regarding Voyager.
  * General FYI about Access rights - synonyms - In writing the Voyager connector we assumed that the accounts used would be schema owners, but this is not always the case.   CARLI for example will use Oracle accounts that are not the schema owners of the database -- their policy is that the Oracle account that is the DB schema owner is not for general use.  The result of this is that the table names in all SQL queries must be prefixed by the schema owner account.  Rather than change all the SQL in the Java/NCIP code, their DBA has done some magic on the Oracle side making this unnecessary.  They will document and contribute the documentation from their DBA for how to do this once the dust settles.   I have added an an issue
  * LUIS - ILS DI Discussion this afternoon.  Fields at item level that are common that may get pulled up.  At this point this group did not have use case to make querying by other types of IDs a priority?
  * Randy - take up question of behavior behind Voyager level holds.



**NCIP Meeting 02/2/2011**

Attendees:  John, Owen, Sharmila, Randy

Discussion

  * John checked in LookUp Item Set core code
  * Sharmila reporting Location Bib Description extra classes (left over), John to take a look and fix.
  * LUIS will only deal with Bib ids and full data response.
  * Sharmila to spend next week working on LUIS for Voyager and hope to have first version available next week.
  * LUIS works with Dummy Service now
  * CARLI to test Lookup User, Lookup Item and installation instructions
  * Use Case wiki page started, please add to as you think of more
  * III Connector - LI, Owen creating custom Scheme for Millennium
  * Revisit 2nd class elimination work in a few weeks.  Most likely this will not happen before SR departure.
  * Michelle S. reports success with her resource sharing testing by Relais which is awesome news.  Next stop...**production use**!  We hope.
  * John will work in core code so core can find connector definition of custom schemes from config files
  * Will hold next weeks meeting.  John B. busy mid-week and not attend.  Will largely be XC team call, but full team welcome to attend.
  * John will work on pulling Dummy connector out of core, making it like any other connector where it would have a down-loadable dummy connector in addition to the core.  John will tackle at some point - TBD.


**NCIP Meeting 01/26/2011**

Attendees:  John, Owen, Sharmila, Randy

Discussion

  * John continues to work on LookUpItem Set (LUIS).  Sharmila and Randy provided input over the prior week.
  * Discussion regarding how to model the problem elements.  Decided best approach would be to try to present a problem for a problematic ID at the level of the identifier (e.g. if a bib ID can't be found, then problem report as at the bib level)
  * Nothing can be assumed about the completeness if a message set by ID "Wrapper".  E.g. if multiple item IDs are submitted that pertain to the same bib id, then these can either be wrapped together or sent separately, at the discretion of the Connector writer.  This is also needed due to the Paging feature.
  * No assumptions can be made about order, grouping
  * Will pass complete hierarchy of info each time.  E.g. if items relating to same bib are sent in two different packages, then bib descriptions will be sent each time.
  * John will look into OCLC doing some future "load testing" of the Core Code with Dummy Services
  * Connector based load testing is not planned for at this time and will likely be up to the the individual connector developers.
  * John to send out examples for Sharmila, Owen and Randy to review, then he will code the core.
  * Sharmila is still working toward LUIS based on old XSD and will convert later.
  * Discussed "average library server" - at least dual core, 2-3 MHz processor and 4 Gb RAM.



**NCIP Meeting 01/19/2011**

Attendees:  Pat, John, Owen, Sharmila, Randy

Few misc. things -
  * Rick from ND to join back in group in February 2011 and spend concentrated effort on Aleph connectors.
  * Patrick from CARLI is going to start working on Renew Item for Voyager connector and will use Sharmila's LDAP Authentication work.
  * Lookup User for Voyager is essentially complete, which is awesome news.  In final stages of testing now.
  * Instance directory for dummy - John to send an email to group in next week or so as would like to take a slightly different approach then originally thought.
  * III - Owen - LI and LU - still working on, will look at Voyager authentication code.

Lookup Item set - the new service

  * Lookup by 4 options/choices proposed.  Will only focus on bib id request at this time.
  * Next Item Token - may take insight from OAI PMH resumption token.  Done when responder passes no token.  John and Sharmila to begin work on building the prototype immediately.
  * No compelling need within this group for filtering at this time (e.g. for given ids, return  only some of the items).
  * Sorting - for now, Voyager connector will return items in display order.   Possibly consider a display order extension field if needed in future and use case arises.

Next week

  * Updates
  * Lookup Item Set – review – discuss new the service

**NCIP Meeting 01/12/2011**

Attendees:  Michelle, John,  Sharmila, Randy


General Discussion revolved about Lookup Item Set.   This will be likely be the primary focus for discussion at next week’s meeting as well.

Michelle:

  * Working on other stuff right now.   In future, will return and do bunch of NCIP stuff all at once.  Sharing code with Sirsi customers, spreadsheet, Helper class.
  * Relais has tested LU User successfully, and will test the other 3 next week
  * Will not be here next week

John:

  * Check in LookUp Item set today including new Schema that is to be used.  Sharmila and Randy to review schema, with eye toward what we need to get a functional service in play that meets Drupal Toolkit needs.
  * Bib Description at both a higher level lower  level – for now Randy says that XC will likely proceed with coding only at the higher level.
  * Paging supported and data elements needed to support.  Sharmila to review and we will discuss.  No filtering  provided for.
  * NCIP instance directory – as checked in, no services defined.   John would like dummy services to be functional.     John to add the needed NCIP instances files to Dummy project.

Next meeting

-	Updates
-	Lookup Item Set – review – discuss new the service


**NCIP Meeting 01/05/2011**

Attendees:  Michelle, John, Ben, Owen, Randy

Group Topic Discussion  - Review de-duplication of service class code based on John’s email sent to group on 12/14/2010.  We were going to discuss this at our 12/15/2010 meeting, and due to light attendance we deferred that to an email discussion.   Though no email discussion occurred, we decided to discuss this today and I asked Ben to join the discussion for this portion of the call.  We have decided to proceed as follows:
Proposal – to remove the separate service classes and to use the JaxB generated classes.   Decision was made that we would proceed with doing this.

  * Frequency of NCIP schema updates – the most frequently that this could occur would be every 6 months, but is not likely to even be that often.  The use if diff tools should eliminate this a major obstacle.

  * Pro – eliminate 2nd set of classes, the service classes, and will proceed with use of only JaxB classes.   This will eliminate the hand maintenance of service classes and the errors which would possibly result from hand editing.
  * Con – John will need 2-3 weeks of work and connector developers will need to revisit the already developed connectors.
  * Connector work that occurred during this 2-3 week window will need to be retro-fit as well as already built connectors.  John hopes to make that work minimal for the connector developers.
  * Core Work is minimal, with only Sharmila and John working in Core.  Will check with Sharmila when time comes.  The “Service Class Removal” project (2-3 weeks) should occur when little or no other core work occurs and connector developers will need to proceed knowing some retrofit will be required.
  * Yes – we decided we should do it, and John will raise any newly discovered “costs” with the group as we proceed.
  * We will not set a date for this work yet, but will target about 2 weeks from now (Jan. 19 or 26). We estimate that we need about 2 weeks stabilize the new Lookup Item Set for the use cases already in hand.  This primarily involves John Bodfish, Sharmila and me.

Michelle:

  * Relais to test and give feedback – Michelle has been in contact this week and is hoping to have testing feedback by the end of this week.
  * Discussed how to “share” Michelle’s code for Sirsi with other Sirsi clients.  Discussed Michelle adding a Sirsi page to wiki and adding content, and she will work on as she is able.
  * Goal will be to have all the java code, non proprietary, in NCIP Google project site.  The proprietary parts (e.g. config mappings, Perl scripts) will be obtained from a password protected Sirsi site.
  * Will need to think about and tweak how to load the name mappings from the config file so that a recompile is not needed
  * John suggested a helper method/class for problem element that can be reused by other Connector elements.  John has emailed his structure/approach proposal and Michelle is open to coding this.   This work can occur either before or after the “Service Class Removal” project.  Michelle to let us know based on her availability.
  * Michelle will update Lookup User https://spreadsheets.google.com/ccc?key=0Ah4r3w4XYLVddGNkUEdQWUdRblJua2ZBRUt6Y3cyTFE&hl=en&authkey=CLeiw9wM#gid=0 with Sirsi comments and how she has implemented it.
  * Discussed that Lehigh may look into VUFind, so discussion regarding Lookup Item Set will be of interest her as we move forward with it over the next few weeks.

John:

  * Code check in occurred since last meeting and it included the following:
  * Lookup Item Error codes – general list of both processing errors and messaging errors.  Also, for each service there are some service specific errors.  – Connector writers should revisit the connectors and use the “easier” method provided by the code vs. hard coding problem elements.  Michelle uses some try-catch and problem element and might have to tweak.
  * Translation changes (see prior week’s notes)
  * Auto testing (see prior week’s notes) (junit will be there)
  * Scheme configuration has two parts:
  * Part 1 –  allows for configuration through Spring.  This will allow a site to choose to use a scheme or not (e.g. to enforce it or not) and allow any value to come through.  – Connector writers should obtain the new code, recompile and  revisit the connectors to make sure they still work.
  * Part 2 – TBD – This will allow a site to configure the connector for mappings to a scheme.   Prerequisite conversation with Sharmila.   E.g.   This will allow the Circulation Status scheme to map from what is returned from the ILS to an NCIP scheme.  Need meetings and discussion to proceed with this.  This will be deferred until after Lookup Item Set.

Priorities:

  * Discuss Scheme config with Sharmila (after code check in)
  * Work on Lookup Item Set definition, code check in, then work with Sharmila, Randy and others as needed to code and get base use cases working.
  * Once LookupItemSet is underway, John will do “Service Class Removal” dedup project.
  * Need to discuss the impact of the all NCIP services working with Jaxb to service and the service to Jaxb (both directions work).  Previously had discussed the jaxb to services and the reverse being automated and checked in.  Assume this is no longer on the table given the dedup work.

Sharmila’s work (not discussed, but included by me)

  * Started on Authentication for Lookup User and has made progress with the queries needed to get the data.  I have asked her to implement authentication as we have already built in V1 of toolkit.  Sharmila to work with Owen and (others) that are possibly interested in making the “LDAP” portion common across connectors.
  * LookupItem still needs Circ status work to be done.
Owen -
  * LookupItem and LookupUser are being worked on.
  * Owen unexpectedly dropped off, so we will resume his update next week.

Future Meetings:

-	Next meeting on 1/12/11

Next meeting

-	Updates
-	Problem element work review (result of John’s code check in)
-	Scheme value work review (result of John’s code check in)
-	Lookup Item Set – review – discuss new the service