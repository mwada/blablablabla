FROM maven:3.3.9-jdk-8-alpine

COPY . /usr/src/app

WORKDIR /usr/src/app

RUN mvn install

CMD ["java","-Xmx1G","-jar","target/spotippos.jar"]

EXPOSE 8080
