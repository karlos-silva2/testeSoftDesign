FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} testesoftdesign.jar
ENTRYPOINT ["java","-jar","/testesoftdesign.jar"]