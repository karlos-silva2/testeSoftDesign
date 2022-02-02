FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} testeSoftDesign.jar
ENTRYPOINT ["java","-jar","/testeSoftDesign.jar"]