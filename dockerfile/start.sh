#!/bin/bash
# Run tomcat with random PW (to show generated password, use "docker logs CONTAINER_ID")
# All running containers can be listed with "docker ps -a"
i=$(docker run -d -p 8080:8080 xcncip2toolkit/xcncip2toolkit)

# Run xcncip2toolkit on tomcat with custom PW:
# i=$(docker run -d -p 8080:8080 -e TOMCAT_PASS="myCustomPasswordToTomcat" xcncip2toolkit/xcncip2toolkit)

# Tomcat admin panel available at: 
# http://127.0.0.1:8080/manager/html
# http://127.0.0.1:8080/host-manager/html


# My thanks belongs to tutum.co for docker image of tomcat & jamesdbloom for docker image of maven for java7
if [ $i ]; then
echo ""
echo "XCNCIP2Toolkit deployment is done."
echo "You can test on one of these links depending on your connector installed:"
echo "http://localhost:8080/aleph-web/"
echo "http://localhost:8080/koha-web/ (Ctrl + click)"
echo ""
fi
