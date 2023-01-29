FROM openjdk:17
EXPOSE 8083
ADD Deploy-Jar-Ubuntu/Project-2-0.0.1-SNAPSHOT.jar Project-2-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","Project-2-0.0.1-SNAPSHOT.jar"]