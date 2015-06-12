Please note that this binding project was developed under OpenJDK 7 with Apache Maven v3.0.4 on Apache Tomcat 8.

Project cannot process LookupItemSet message if you run tomcat on java 8. All other services should work as expected.

Tomcat 8 - https://tomcat.apache.org/download-80.cgi
Maven 3.0.4 - http://archive.apache.org/dist/maven/maven-3/3.0.4/binaries/apache-maven-3.0.4-bin.tar.gz
OpenJDK - http://openjdk.java.net/install/

Please build this project with skipped tests as it doesn't pass yet, but works in connector: 'mvn install -Dmaven.test.skip'

You can inspire yourself with setting up your toolkit.properties in your connector from the testing one: src/test/resources/toolkit.properties

If you are familiar with Czech language, you can proceed to the document describing how to implement this binding here: https://docs.google.com/document/d/1d1ebJcxp7FOsJi_vIfWI1nX3wLwrBjUCun-w7c4R3CY/edit?usp=sharing
