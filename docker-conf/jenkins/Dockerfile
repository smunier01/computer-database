from jenkins

# Docker installation
USER root
RUN apt-get update
RUN apt-get install curl 
RUN curl -fsSL https://get.docker.com/ | sh

COPY plugins.txt /usr/share/jenkins/plugins.txt
RUN echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt