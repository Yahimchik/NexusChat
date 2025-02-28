# NexusChat

🚀 **Масштабируемый сервис чатов с поддержкой WebSockets и Kafka**

## 📖 Описание проекта
NexusChat – это микросервисное приложение для общения, поддерживающее **приватные и групповые чаты**, а также **реальное время** благодаря WebSockets и Kafka. Основная цель – обеспечить **отказоустойчивый и быстрый обмен сообщениями**.

## 🔧 Используемые технологии
- **Java + Spring Boot** – основной стек
- **Spring Cloud Gateway** – API-шлюз
- **Kafka** – брокер сообщений для чатов
- **PostgreSQL** – хранение метаданных чатов
- **MongoDB (шардирование)** – хранение сообщений
- **WebSockets** – для real-time общения (в разработке)
- **Liquibase** – управление миграциями
- **Docker – контейнеризация

## 🛠 Функциональность
✅ Авторизация через **Auth Service** (JWT)
✅ **Создание/удаление** приватных и групповых чатов
✅ **Отправка/получение** сообщений в реальном времени
✅ **Логическое удаление** сообщений
✅ **Отображение статусов** (прочитано/непрочитано)
✅ **Поиск сообщений** по содержимому
✅ **Шардирование сообщений** для масштабируемости
✅ **Kafka Consumer Groups** для распределения нагрузки

## 🏗 Архитектура
**NexusChat** использует **микросервисный подход**:
1. **Chat Service** – управление чатами (PostgreSQL)
2. **Message Service** – обработка сообщений (MongoDB + Kafka)
3. **Auth Service** – аутентификация (JWT)
4. **Gateway Service** – API-шлюз (Spring Cloud Gateway)
5. **WebSockets Service** – обработка WebSocket-соединений (в разработке)

📌 **Хранение данных:**
- **PostgreSQL** – хранит чаты и участников
- **MongoDB (шардирование по chat_id)** – хранит сообщения
- **Kafka** – транспорт сообщений

## ⚙️ Установка и запуск

### 🔹 Локальный запуск
```bash
git clone https://github.com/your-repo/nexuschat.git
cd nexuschat
docker-compose up -d
```

### 🔹 Запуск через Maven
```bash
mvn clean install
java -jar target/nexuschat.jar
```

## 📡 API и WebSockets
API включает:
- **POST /chats** – создание чата
- **GET /chats** – список чатов
- **POST /messages** – отправка сообщения
- **GET /messages/{chatId}** – получение истории чата
- **PUT /messages/{id}/read** – пометка как "прочитано"

📡 **WebSockets (в разработке)**
- **Отправка сообщений**: JSON `{chatId, senderId, text}`
- **Подписка**: обновления приходят в real-time

## 💡 Планы на будущее
- 🔹 Реализация WebSockets
- 🔹 Реакции на сообщения
- 🔹 Поддержка вложений (файлы, изображения)
- 🔹 Уведомления через WebSockets
- 🔹 Автоматическое удаление неактивных чатов

---
**📬 Контакты**: _[указать контактные данные]_

