#!/bin/bash

cd /home/xcncip2toolkit/xcncip2toolkit/core/trunk 
mvn install
mvn install -Dmaven.test.skip

cd ../../connectors/koha/3.xx/trunk
mvn install -Dmaven.test.skip
