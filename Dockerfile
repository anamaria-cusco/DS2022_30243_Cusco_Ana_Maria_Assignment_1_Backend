FROM openjdk:11
ADD target/DS2022_30243_Cusco_Ana_Maria_Assignment_1_Backend-0.0.1-SNAPSHOT.jar DS2022_30243_Cusco_Ana_Maria_Assignment_1_Backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","DS2022_30243_Cusco_Ana_Maria_Assignment_1_Backend-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080