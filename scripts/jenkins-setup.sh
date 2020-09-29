#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
JENKINS_DIR="$DIR/../jenkins"

log () {
  echo "[$1] $2" >> $HOME/jenkins-setup.log
}

log "INFO" "Building jenkins image..."

cd $JENKINS_DIR
sudo docker image build -t bah-jenkins .

if [ $? -ne 0 ]; then
  log "ERROR" "Failure during image. Exiting."
  exit 1
else
  log "INFO" "Image built and available. $( sudo docker ps -qa )"
fi

if [ -f ~/jenkins.env ]; then
  log "INFO" "Moving env variables from ~/jenkins > ~/mlops-repo/jenkins/jenkins.env"
  mv ~/jenkins.env $JENKINS_DIR
fi

log "INFO" "Standing up jenkins service..."
sudo docker-compose up -d

if [ $? -ne 0 ]; then
  log "ERROR" "Failure while spinning up jenkins service"
  exit 1
else
  log "INFO" "Jenkins should be available on 8080"
fi
