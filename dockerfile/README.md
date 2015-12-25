This dockerfile serves to easily deploy XCNCIP2Toolkit with Aleph or Koha connector to desired machine on port 8080.

You can then access web-app 'GUI' on http://localhost:8080/koha-web/ (for Koha)

Installation:
---
To install Docker, which will handle this toolkit, visit https://docs.docker.com/installation/#installation

To find out more about Docker, visit https://www.docker.com/whatisdocker/

After Docker is installed, it is enough to deploy the toolkit to Docker by executing `./install-koha.sh`, which will install the Koha connector. If you would like to implement dockerfile for another ILS, please create an issue or just copy the logic from koha & create pull request :)

After that is done, you can handle the toolkit with `./start.sh` or `./stop.sh`.

Warning:
----
Standard [docker](http://manpages.ubuntu.com/manpages/natty/man1/docker.1.html) application available in debian repositories found with "aptitude search ^docker" command is not the same as [Docker](https://www.docker.com/), which I'm refering to in here.
