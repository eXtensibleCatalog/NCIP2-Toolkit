#
# Java 1.7 &amp; Maven Dockerfile
#
# https://github.com/jamesdbloom/docker_java7_maven
#

# pull base image.
FROM tutum/tomcat

# maintainer details
MAINTAINER Jiří Kozlovský "kozlovsky@mzk.cz"

# update packages and install maven
RUN  \
  export DEBIAN_FRONTEND=noninteractive && \
  sed -i 's/# \(.*multiverse$\)/\1/g' /etc/apt/sources.list && \
  apt-get update && \
  apt-get -y upgrade && \
  apt-get install -y vim wget curl git maven

# attach volumes
VOLUME /volume/git

# create working directory
RUN mkdir -p /local/git
WORKDIR /local/git

# run terminal
CMD ["/bin/bash"]
