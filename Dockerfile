# Docker multi-stage build

# 1. Building the App with Maven
FROM maven:3-jdk-11

ADD . /root
WORKDIR /root

# Just echo so we can see, if everything is there :)
RUN ls -l

# Run Maven build
RUN mvn clean package


# Just using the build artifact and then removing the build-container
FROM openjdk:11

MAINTAINER Ana-Maria Cusco

VOLUME /tmp

# Add Spring Boot app.jar to Container
COPY --from=0 "/root/target/DS2022_30243_Cusco_Ana_Maria_Assignment_1_Backend-*-SNAPSHOT.jar" app.jar

# Fire up our Spring Boot app by default
CMD [ "sh", "-c", "java -Dserver.port=8082 -Xmx300m -Xss512k -XX:CICompilerCount=2 -Dfile.encoding=UTF-8 -XX:+UseContainerSupport -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]