FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} networking.jar
ENTRYPOINT ["java","-jar","/networking.jar"]