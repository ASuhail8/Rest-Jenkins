FROM openjdk:8u191-jre-alpine3.9
#set working directory
WORKDIR /restapi
#Run and install maven
USER root
RUN apk update && apk add maven
#Add the jar files to container
ADD target/restAssured-0.0.1-SNAPSHOT.jar restAssured-0.0.1-SNAPSHOT.jar
ADD target/restAssured-0.0.1-SNAPSHOT-tests.jar restAssured-0.0.1-SNAPSHOT-tests.jar
ADD target/libs libs

COPY ./testng.xml .

ENTRYPOINT java -cp restAssured-0.0.1-SNAPSHOT.jar:restAssured-0.0.1-SNAPSHOT-tests.jar:libs/* org.testng.TestNG testng.xml