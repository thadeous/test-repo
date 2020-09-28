FROM jenkins/jenkins:lts

# No setup wizard
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

# Jenkins runs groovy files in init.groovy.d
COPY ./init.groovy.d/*.groovy /usr/share/jenkins/ref/init.groovy.d/

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt
