FROM xcncip2toolkit/maven

MAINTAINER Jiří Kozlovský <mail@jkozlovsky.cz>

ENV REFRESHED_AT 2015-12-24
ENV LANG C.UTF-8

# Add user to manage home dir
RUN adduser --disabled-password --gecos "" xcncip2toolkit
USER xcncip2toolkit

# Clone xcncip2toolkit from github - moravianlibrary
RUN git clone https://github.com/eXtensibleCatalog/xcncip2toolkit.git /home/xcncip2toolkit/xcncip2toolkit

# Build with bash script because of multiple build errors
ADD maven_build_ncip2.sh /home/xcncip2toolkit/initialization.sh
ADD toolkit.properties /home/xcncip2toolkit/xcncip2toolkit/connectors/koha/3.xx/trunk/web/src/main/resources/toolkit.properties
USER root
RUN chown xcncip2toolkit /home/xcncip2toolkit/xcncip2toolkit/connectors/koha/3.xx/trunk/web/src/main/resources/toolkit.properties
USER xcncip2toolkit
RUN /home/xcncip2toolkit/initialization.sh

# Link built .war to tomcat so that it is all set
USER root
RUN ln -s /home/xcncip2toolkit/xcncip2toolkit/connectors/aleph/22/trunk/web/target/aleph-web-0.0.1-SNAPSHOT.war /tomcat/webapps/aleph-web.war
RUN ln -s /home/xcncip2toolkit/xcncip2toolkit/connectors/koha/3.xx/trunk/web/target/koha-web-0.0.1-SNAPSHOT.war /tomcat/webapps/koha-web.war

EXPOSE 8080
CMD ["/run.sh"]
