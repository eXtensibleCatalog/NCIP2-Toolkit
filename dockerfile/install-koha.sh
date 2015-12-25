#!/bin/bash
# First configure the Koha toolkit connector ..
./connectors/koha/configure.sh

# Build the tomcat & maven (basically pulls the debian )
./maven/docker_build.sh

# Build the xcncip2toolkit core
./core/docker_build.sh

# Build the xcncip2toolkit connector
./connectors/koha/docker_build.sh
