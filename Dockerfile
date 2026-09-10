FROM gradle:8-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

USER root
RUN chmod +x gradlew

USER gradle
RUN ./gradlew build --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
