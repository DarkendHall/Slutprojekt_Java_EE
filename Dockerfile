FROM eclipse-temurin:17.0.2_8-jre-alpine
COPY ./target/slutprojekt.jar /application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]