1) Download maven from http://arrchive.apache.org/dist/maven/binaries and install it (per the instructions at http://maven.apache.org/download.html.). (I took the 2.2.0-bin-zip; others may work.)

2) Create a directory for the ncip2toolkit code, let's say "c:\ncip2toolkit".

3) Download the NCIP toolkit code from the Google ncip2toolkit repository to the directory you just created.

4) Open a command shell and navigate to the directory where your downloaded the ncip2toolkit code and type:
	mvn package

This could run for 10-30 minutes while it downloads the necessary jars etc.

If you get "JAXB 2.0 API is being loaded from the bootstrap classloader", see http://blog.spaceprogram.com/2007/05/how-to-fix-linkageerror-when-using-jaxb.html. I downloaded the jaxb-api.jar from http://download.java.net/maven/1/javax.xml.bind/jars/. Be sure to put it in the JRE/lib directory of the JDK if you're using the JDK. Then repeat the "mvn install" command - it'll pick up where it left off, generally.

I don't think you'll get this, but just in case: I got "A required plugin was not found" for org.glassfish.web:jstl-impl:jar:1.2 and javax.servlet.jsp.jstl:jstl-api:jar:1.2. You can download them from http://code.google.com/p/dexter/downloads/detail?name=jstl-impl-1.2.jar&can=2&q= and http://download.java.net/maven/1/jstl/jars/. Install them with these commands (run from the c:\ncip2toolkit, but I don't think that matters).:

	mvn install:install-file -DgroupId=javax.servlet.jsp.jstl -DartifactId=jstl-api -Dversion=1.2 -Dpackaging=jar -Dfile=C:/Downloads/jstl-impl-1.2.jar		mvn install:install-file -DgroupId=org.glassfish.web -DartifactId=jstl-impl -Dversion=1.2 -Dpackaging=jar -Dfile=C:/Downloads/jstl-1.2.jar

Of course, change "C:/Downloads/" to the directory where you save them when detaching them from the email. Once those complete (with a "BUILD SUCCESSFUL" message) repeat the "mvn install" command.

5) Once that completes cleanly, you're ready to deploy the web service.

6) Download Jetty from http://dist.codehaus.org/jetty. (I took the 6.1.25 build; others may work.)

7) Unzip it and test it by opening a command shell in the directory where you unzipped it and typing:
	java -jar start.jar etc/jetty.xml

You should see output that indicates that the Jetty web server is starting up. Once it has started, open the URL http://localhost:8080 and you should see the Jetty welcome page. If you don't, you'll need to figure out why the basic Jetty distribution isn't working on your machine. Once you have it working, you can hit ctrl-c to kill the Jetty process and proceed to the next step.

8) Extract the contents of the ZIP file c:\ncip2toolkit\web\target\web-0.0.1-SNAPSHOT-install.zip into Jetty_home_dir(eg: C:/Jetty-6.1.25) or tomcat_home_dir(C:\Program Files\Apache Software Foundation\Tomcat 6.0 or C:\Program Files\Apache Software Foundation\Tomcat 6.0\bin depending on installtion).

9) Copy WAR file from <Jetty_home_dir>/NCIP-instances/ into the webapps directory in the root of your Jetty installation, and rename it ncipv2.war.

10) Customize NCIPToolkit_core_config.properties(optional - valid properties not defined for core yet) and log4j.config.txt(Change the path to where you want log files to be created).

11) Installing connector
	a) Download driver zip file and extract the contents into  <Jetty_home_dir>/NCIP-instances/ncipv2/driver/
	b) Customize driver_config.properties file according to the connector service implementation classes.If there is no implementation class for a service, then just leave it as blank.
	Also in that properties file you can add connector specific properties to it. 
	c) Rename jar file to driver.jar

9) Start Jetty again as above. This time there should be some output in the command shell window where you started Jetty such as:
INFO: Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@20fa83: defining beans [org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#,org.springframework.context.annotation.internalCommonAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,remoteServiceManager,lookupItemService,ncipservices,ncipMessageHandler,jaxbTranslator,NCIPServlet]; root of factory hierarchy

That indicates that Jetty has properly set-up the ncipv2 war.

10) Open a second command shell, navigate to the same directory (c:\ncip2toolkit, in our example) and type:
	java -cp "binding/target/binding-0.0.1-SNAPSHOT.jar;dummy/target/dummy-0.0.1-SNAPSHOT.jar;examples/target/examples-0.0.1-SNAPSHOT.jar;initiator/target/initiator-0.0.1-SNAPSHOT.jar;service/target/service-0.0.1-SNAPSHOT.jar;transport/target/transport-0.0.1-SNAPSHOT.jar;web/target/web-0.0.1-SNAPSHOT/WEB-INF/lib/commons-lang-2.3.jar" org.extensiblecatalog.ncip.v2.examples.SimpleClient org.extensiblecatalog.ncip.v2.examples.SimpleLookupItemClient http://localhost:8080/ncipv2/NCIPResponder/ 123123

(You can replace "123123" with an item barcode of your own imagining.)
You should see a bunch of messages about what it's "about" to do, and then:
	The Item id is: 123123
	The title is: Of Mice and Men
	The call numbers is: 813.52 St34yV
	The circulation status is: scheme "http://www.niso.org/ncip/v1_0/impl/schemes/circulationstatus/circulationstatus.scm", value "Available On Shelf".

That confirms that the NCIP 2 Core (initiator and responder) is working.

If you get a "ClassNotFoundException: com.sun.xml.bind.v2.ContextFactory" message, I solved this by downloading the jaxb-impl-2.1.5.jar file from http://download.java.net/maven/1/com.sun.xml.bind/jars/	 and putting it in the JRE/lib/endorsed directory as above in step 4. You'll have to restart Jetty as well so it picks up the jar.


John Bodfish
bodfishj@oclc.org
