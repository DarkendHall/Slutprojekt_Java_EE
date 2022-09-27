FROM eclipse-temurin:18.0.2_9-jre-alpine
COPY ./target/slutprojekt.jar /application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
