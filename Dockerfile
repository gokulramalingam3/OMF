FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/*.jar omf.jar
ENTRYPOINT ["sh", "-c", "java -jar /omf.jar"]