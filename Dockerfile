FROM eclipse-temurin:17.0.4_8-jre-alpine
COPY ./target/slutprojekt.jar /application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
