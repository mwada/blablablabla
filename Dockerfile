FROM maven:3.3.9-jdk-8-alpine

COPY . /usr/src/app

WORKDIR /usr/src/app

RUN mvn install -DskipTests

CMD java $JAVA_OPTS -jar target/spotippos.jar
