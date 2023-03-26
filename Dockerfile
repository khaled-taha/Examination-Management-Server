FROM openjdk:19-alpine
COPY target/product-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
CMD ["java", "-jar", "product-1.0-SNAPSHOT.jar"]