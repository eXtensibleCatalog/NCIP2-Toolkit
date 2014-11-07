#!/bin/bash

i=$(docker ps -a | egrep 'xcncip2toolkit/xcncip2toolkit.*Up.*tcp' | cut -d' ' -f1 | xargs docker stop)

if [ $i ]; then
echo ""
echo "Your xcncip2toolkit have successfully stopped."
echo ""
fi
