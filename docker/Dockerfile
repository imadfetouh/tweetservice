FROM openjdk:11
EXPOSE 8082
ARG JAR_FILE=/tweetservice/target/tweetservice-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]