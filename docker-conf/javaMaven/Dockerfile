FROM ubuntu
RUN apt-get -y update
RUN apt-get install -y maven
# RUN apt-get upgrade
RUN apt-get install -y software-properties-common
RUN apt-get install -y vim
RUN apt-get install -y arping 
RUN add-apt-repository ppa:webupd8team/java
RUN apt-get update
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
RUN apt-get install -y oracle-java8-installer
RUN apt-get install -y git

COPY mysql.properties /home/