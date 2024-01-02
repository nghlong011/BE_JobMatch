FROM openjdk:17-jdk-slim
EXPOSE 8081
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} jobmatch.jar
ENTRYPOINT ["java","-jar","/jobmatch.jar"]