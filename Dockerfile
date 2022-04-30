FROM openjdk:11

COPY ./target/genesis-1.0.1-RELEASE.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch genesis-1.0.1.jar'

ARG JAR_FILE=target/genesis-1.0.1-RELEASE.jar

ENTRYPOINT ["java", "-jar", "genesis-1.0.1-RELEASE.jar"]