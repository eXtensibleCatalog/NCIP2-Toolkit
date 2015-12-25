FROM xcncip2toolkit/core

MAINTAINER Jiří Kozlovský <mail@jkozlovsky.cz>

ENV REFRESHED_AT 2015-12-25
ENV LANG C.UTF-8

# Build with bash script because of multiple build errors
ADD maven_build_koha.sh /home/xcncip2toolkit/maven_build_koha.sh
ADD toolkit.properties /home/xcncip2toolkit/xcncip2toolkit/connectors/koha/3.xx/trunk/web/src/main/resources/toolkit.properties

USER root
RUN chown xcncip2toolkit /home/xcncip2toolkit/xcncip2toolkit/connectors/koha/3.xx/trunk/web/src/main/resources/toolkit.properties

USER xcncip2toolkit
RUN /home/xcncip2toolkit/maven_build_koha.sh

# Link built .war to tomcat so that it is all set
USER root
RUN ln -s /home/xcncip2toolkit/xcncip2toolkit/connectors/koha/3.xx/trunk/web/target/koha-web-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/koha-web.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
