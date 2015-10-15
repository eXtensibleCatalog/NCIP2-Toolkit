# Introduction #

This section provides and overview for getting started developing an ILS-connector for the NCIP toolkit.

These instructions assume:
  * You have checked-out, built and tested the NCIP toolkit project.
  * You have read the NCIP v2 Toolkit ClassDiagram page.
  * You know the name of the ILS that you’ll be developing a connector for.
  * You know which NCIP services (or at least the first one) you will be implementing, and (roughly) how to perform those services programmatically in the ILS. You don’t in fact need to know all of the details, but these instructions do not have any information about how to perform NCIP services in a particular ILS – your own your own for that.
  * That the directory where you have the root of the project checked-out is named “ncipv2”.


# Details #

1)	Create a new top-level project - typically this would be named for the ILS you're going to connect to, e.g. Voyager, or Aleph\_9.0, or Millennium-Oracle. For this example we’ll pretend we’re developing a connector for Evergreen version 2, so you should then create an “evergreen2” directory within the ncipv2 directory.

2)	Copy the pom.xml (that's a maven configuration file) from the template project to evergreen2 directory. Edit the evergreen2/pom.xml file, changing all instances of “template” to “evergreen2” and “Template” to “Evergreen 2”.

3)	Copy the contents of the template/src/main/java/org/extensiblecatalog/template directory to evergreen2/src/main/java/org/extensiblecatalog/evergreen2 (creating all the necessary directories). Rename the class (and the file) that’s in evergreen2/ src/main/java/org/extensiblecatalog/evergreen2/RemoteServiceManager to evergreen2/ src/main/java/org/extensiblecatalog/evergreen2/Evergreen2ServiceManager (to conform to our naming conventions).

4)	For each service that you want to implement you will need to modify one file, named (in the evergreen2 example) evergreen2/src/main/java/org.extensiblecatalog.evergreen2. NCIP-service-name\_Service.java, where "NCIP-service-name" is LookupItem for the Lookup Item service, RequestItem for the Request Item service, etc.

5)	If you need an object that is shared across every service you implement (e.g. if both Lookup Item and Request Item need to share the same JDBC connection to the database, or you need to cache query results, etc.) you can add those methods to the Evergreen2ServiceManager class, adding any methods you need. The NCIP toolkit framework will pass an instance of that object to each service-specific class when it calls the performService method.

6)	Add the evergreen2 project’s pom to the list of child modules in the top-level pom.xml file (in ncipv2/pom.xml); look for the line that has
```
“<module>template</module>”
```
> duplicate it and change ‘template’ to ‘evergreen2’.

7)	Edit the configuration file located in web/src/main/webapp/WEB-INF/spring/ncipv2-core-context.xml, changing the references to org.extensiblecatalog.ncip.v2.dummy classes to
org.extensiblecatalog.evergreen2 classes. For example, if you implement the RenewItem service, you should have created the following classes in the evergreen2 package:
  * org.extensiblecatalog.ncip.v2.evergreen2.Evergreen2RemoteServiceManager
  * org.extensiblecatalog.ncip.v2.evergreen2.Evergreen2RenewItemService

Your edited ncipv2-core-context.xml file will now look like this:

```
<!-- Copyright 2010 eXtensible Catalog Organization.  All rights reserved. -->
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
                        http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
                        http://www.springframework.org/schema/context
                        http://www.springframework.org/schema/context/spring-context-2.5.xsd">

    <!-- NCIP remote services manager -->
    <bean id="remoteServiceManager" class="org.extensiblecatalog.ncip.v2.evergreen2.Evergreen2RemoteServiceManager"/>

    <!-- NCIP Services -->
    <bean id="lookupItemService" class="org.extensiblecatalog.ncip.v2.evergreen2.Evergreen2RenewItemService"/>

    <bean id="supportedServices" class="org.springframework.beans.factory.config.MapFactoryBean">
      <property name="sourceMap">
         <map>
           <entry key="org.extensiblecatalog.ncip.v2.service.RenewItemInitiationData" value-ref="renewItemService"/>
         </map>
      </property>
    </bean>

    <bean id="messageHandler" class="org.extensiblecatalog.ncip.v2.service.MessageHandler">
      <constructor-arg index="0" ref="supportedServices"/>
      <constructor-arg index="1" ref="remoteServiceManager"/>
    </bean>

    <bean id="translator" class="org.extensiblecatalog.ncip.v2.binding.jaxb.JAXBTranslator"/>

</beans>
```

Notice that the class name used as the key to the supportedServices map must be the “InitiationData” class associated with the NCIP service you’ve implemented, and must be from the org.extensiblecatalog.ncip.v2.service package. The NCIP toolkit’s core framework will be given an instance of its MessageHandler class, with the map of supported services populated as described in this Spring configuration file. The effect of this particular configuration will be that whenever the framework receives a RenewItem message, it will construct an instance of its RenewItemInitiationData class and pass it to the performService method of your Evergreen2RenewItemService, along with an instance of the Evergreen2RemoteServiceManager class.

8)	Build and start the NCIP responder and test your connector by sending it a Renew Item message.