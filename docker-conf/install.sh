#!/bin/bash

#
# tools to set the computer-database docker environment
#

DOCKER_HUB_JENKINS="jenkins-img"
JENKINS_CONF_FOLDER="/home/$(whoami)/jenkins-conf/"
JENKINS_PORT="8080"
JENKINS_CONTAINER_NAME="jenkins-container"

function build_all {
    docker build -t maven-img ./javaMaven
    docker build -t tomcat-img ./tomcat
    docker build -t mysql-test-img ./mysql
    docker build -t jenkins-img ./jenkins
}

#
# set jenkins config folders
#
function configure_jenkins {

    # 1 - creates folder in ~/jenkins

    # if [ -d "$JENKINS_CONF_FOLDER" ]; then
    #    rm -r $JENKINS_CONF_FOLDER
    # fi

    mkdir -p $JENKINS_CONF_FOLDER

    # 2 - copy config.xml to $CONF/jobs/github-job/

    # mkdir -p $JENKINS_CONF_FOLDER/jobs/github-job/
    # cp config.xml $JENKINS_CONF_FOLDER/jobs/github-job/

    cp -r ./jenkins/jobs $JENKINS_CONF_FOLDER
    
    # cp --parents ./jenkins/jobs/github-job/config.xml $JENKINS_CONF_FOLDER/jobs/github-job
    # cp --parents ./jenkins/jobs/deploy-job/config.xml $JENKINS_CONF_FOLDER/jobs/deploy-job
}

#
# pull & run jenkins
#
function run_jenkins {
    docker rm -f $JENKINS_CONTAINER_NAME
    docker pull $DOCKER_HUB_JENKINS
    docker run --name $JENKINS_CONTAINER_NAME -p $JENKINS_PORT:8080 -p 50000:50000 -v /var/run/docker.sock:/var/run/docker.sock -v $JENKINS_CONF_FOLDER:/var/jenkins_home $DOCKER_HUB_JENKINS
}

#
# stop jenkins
#
function stop_jenkins {
    id=$(docker ps | grep $JENKINS_CONTAINER_NAME | awk '{print $1}')
    if [ ! -z "$id" ]; then
        docker stop $id
    else
        echo 'error: jenkins is not running.'
    fi
}

#
# start jenkins
#
function start_jenkins {
    id=$(docker ps -a | grep $JENKINS_CONTAINER_NAME | awk '{print $1}')
    if [ ! -z "$id" ]; then
        docker start $id
    else
        echo "error: jenkins container doesn't exist. use the run_jenkins command first."
    fi
}

function clean {
    id=$(docker ps -a | grep $JENKINS_CONTAINER_NAME | awk '{print $1}')
    if [ ! -z "$id" ]; then
        docker rm -f $id
    fi

    if [ -d "$JENKINS_CONF_FOLDER" ]; then
        rm -r $JENKINS_CONF_FOLDER
    fi
}

if [ "$1" == "" ]; then
    echo "1 parameter is required"
    exit
fi

case "$1" in
    "build")
        build_all
        ;;
    "conf")
        configure_jenkins
        ;;
    "run")
        run_jenkins
        ;;
    "start")
        start_jenkins
        ;;
    "stop")
        stop_jenkins
        ;;
    "clean")
        clean
        ;;
    *)
        echo "Incorrect command, available commands are : "
        echo "build : build and push images to docker hub."
        echo "conf : configure jenkins environment."
        echo "run : run jenkins."
        ;;
esac



