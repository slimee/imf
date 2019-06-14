FROM java:8-jdk-alpine

COPY ./target/imf-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch imf-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","imf-0.0.1-SNAPSHOT.jar"]