# Устанавливаем базовый образ для сборки приложения
FROM maven:latest AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем pom.xml и загружаем зависимости
COPY pom.xml .

# Собираем зависимости (перед сборкой проекта для ускорения процесса)
RUN mvn dependency:go-offline

# Копируем весь проект в контейнер
COPY src ./src

# Собирать проект в jar файл
RUN mvn clean package -DskipTests

# Используем базовый образ для выполнения
FROM openjdk:17-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar файл из предыдущего этапа
COPY --from=build /app/target/auth-service-0.0.1-SNAPSHOT.jar auth-service.jar


# Устанавливаем переменную окружения для использования в Spring
ENV SPRING_PROFILES_ACTIVE=prod
ENV DB_HOST=host.docker.internal
ENV DB_PORT=5432
ENV DB_USERNAME=postgres
ENV DB_PASSWORD=1006
ENV DB_NAME=auth_service_db

# Открываем порт для приложения
EXPOSE 9090

# Запуск приложения
ENTRYPOINT ["java", "-jar", "auth-service.jar"]
