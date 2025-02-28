FROM openjdk:17
WORKDIR /app
COPY target/chat-service.jar chat-service.jar
ENTRYPOINT ["java", "-jar", "chat-service.jar"]
EXPOSE 8090
