FROM openjdk:17-jdk-alpine
MAINTAINER reva1707
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]