FROM gradle:jdk17 AS build
ENV APP_HOME=/app/

WORKDIR $APP_HOME
COPY ./build.gradle.kts ./settings.gradle.kts $APP_HOME

COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root

RUN chown -R gradle /home/gradle/src

RUN gradle build || return 0
COPY . .

RUN gradle clean build

FROM openjdk:17 AS app

ENV ARTIFACT_NAME=record-backend-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/app/

WORKDIR $APP_HOME
COPY --from=build $APP_HOME/build/libs/$ARTIFACT_NAME .

EXPOSE 8080
ENTRYPOINT exec java -jar ${ARTIFACT_NAME}