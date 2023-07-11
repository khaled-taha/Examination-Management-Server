FROM openjdk:17-jdk-alpine
COPY target/Exam-app-0.0.1-SNAPSHOT.jar exam-app.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","exam-app.jar"]