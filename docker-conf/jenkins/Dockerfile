from jenkins

# Docker installation
USER root
RUN apt-get update
RUN apt-get install curl 
RUN curl -fsSL https://get.docker.com/ | sh

ENV DOCKER_HOST=tcp://172.17.0.2:2375