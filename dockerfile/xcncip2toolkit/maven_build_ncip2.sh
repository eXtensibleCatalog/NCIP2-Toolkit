#!/bin/bash
cd /home/xcncip2toolkit/xcncip2toolkit/core/trunk 
mvn install
mvn install -Dmaven.test.skip

cd ../../connectors/aleph/20/trunk
mvn install -Dmaven.test.skip
