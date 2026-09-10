FROM gradle:8-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

USER root
RUN chmod +x gradlew

USER gradle
RUN ./gradlew build --no-daemon -x test --stacktrace --info

FROM eclipse-temurin:17-jre-alpine
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*[!p][!l][!a][!i][!n].jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
