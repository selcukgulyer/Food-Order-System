FROM openjdk:8-jdk-alpine
ADD target/demo3-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]