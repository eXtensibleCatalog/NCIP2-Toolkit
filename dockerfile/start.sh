#!/bin/bash

# Run tomcat with random PW (to show generated password, use "docker logs CONTAINER_ID")
# All running containers can be listed with "docker ps -a"
docker run -d -p 8080:8080 xcncip2toolkit/xcncip2toolkit

# Run xcncip2toolkit on tomcat with custom PW:
# docker run -d -p 8080:8080 -e TOMCAT_PASS="myCustomPasswordToTomcat" xcncip2toolkit/xcncip2toolkit

# Tomcat admin panel available at: 
# http://127.0.0.1:8080/manager/html
# http://127.0.0.1:8080/host-manager/html


# My thanks belongs to tutum.co for docker image of tomcat & jamesdbloom for docker image of maven for java7
