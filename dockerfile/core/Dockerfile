FROM tomcat:8-jre7

MAINTAINER Jiří Kozlovský <mail@jkozlovsky.cz>

ENV REFRESHED_AT 2015-12-24
ENV LANG C.UTF-8

# Add user to manage home dir
RUN adduser --disabled-password --gecos "" xcncip2toolkit

# Install maven ..
RUN wget -O /tmp/apache-tomcat.tar.gz http://apache.lauf-forum.at/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
RUN cd /opt && tar -xf /tmp/apache-tomcat.tar.gz && ln -s /opt/apache-maven-3.3.9/bin/mvn /usr/local/bin/

# openjdk-7-jdk needed for javac in order to run mvn install ..
RUN apt-get update && apt-get upgrade -y && apt-get install -y openjdk-7-jdk vim git

USER xcncip2toolkit

# Clone xcncip2toolkit from github - eXtensibleCatalog
RUN git clone https://github.com/eXtensibleCatalog/xcncip2toolkit.git /home/xcncip2toolkit/xcncip2toolkit

# Build with bash script because of multiple build errors
ADD maven_build_core.sh /home/xcncip2toolkit/maven_build_core.sh
RUN /home/xcncip2toolkit/maven_build_core.sh

# run terminal
CMD ["/bin/bash"]
