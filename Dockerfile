FROM openjdk:14
ADD target/sessions.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]