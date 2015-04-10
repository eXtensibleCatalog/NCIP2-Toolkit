This dockerfile serves to easily deploy XCNCIP2Toolkit with Aleph or Koha connector to desired machine on port 8080.

You can access web-app GUI on http://localhost:8080/aleph-web/ (Aleph) or http://localhost:8080/koha-web/ (Koha)

Installation:
---
To install Docker, which will handle this toolkit, visit https://docs.docker.com/installation/#installation

To find out more about Docker, visit https://www.docker.com/whatisdocker/

After Docker is installed, it is enough to deploy the toolkit to Docker by executing install-aleph.sh or install-koha.sh. 

After that is done, you can handle the toolkit with start.sh or stop.sh.

Warning:
----
Standard [docker](http://manpages.ubuntu.com/manpages/natty/man1/docker.1.html) application available in debian repositories found with "aptitude search ^docker" command is not the same as [Docker](https://www.docker.com/), which I'm refering to in here.
