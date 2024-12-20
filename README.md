# **Notifications Microservice API**

The Notifications Microservice API is a reactive, event-driven component of a distributed system designed to manage email notifications efficiently and at scale. It consumes eligibility events from Kafka topics, dynamically retrieves email templates stored in AWS S3, and sends customized notifications to recipients using Mailgun. Leveraging Reactive Programming with Project Reactor, the service ensures non-blocking processing, enabling high throughput and low latency in handling large volumes of events. Its modular architecture supports extensibility, allowing seamless integration of new event types or notification channels. This microservice acts as the final link in the system, transforming raw events into actionable user communications.

---

## **Key Features**

### **Reactive Programming:**

- **Event-Driven Architecture**: The microservice operates on events published to Kafka topics, using reactive streams to process them asynchronously.
- **Non-Blocking Design**: Built with Project Reactor, enabling seamless handling of multiple events without blocking threads, even during IO operations like fetching templates from S3 or sending emails through Mailgun.
- **Scalable Processing**: Reactive streams process data as it becomes available, allowing the system to handle large volumes of events with minimal latency.

### **Kafka Integration:**

- Listens to Kafka topics for **NotificationEvent** streams, ensuring efficient communication with other microservices.
- Supports reactive bindings for processing incoming events dynamically and publishing confirmation logs or alerts when necessary.

### **Email Notifications:**

- **Dynamic Email Rendering**: Uses Thymeleaf to populate templates with event-specific data, creating personalized, context-rich emails.
- **Mailgun API**: Sends rendered email content to recipients via a reliable email delivery service.

### **AWS S3 Integration:**

- Manages HTML templates stored in S3, providing secure and scalable storage for notification assets.
- Supports uploading, retrieving, and versioning templates directly from S3.

### **Modularity and Extensibility:**

- Clean separation of concerns between event processing, template management, and email delivery, making the system easy to extend with additional notification types or delivery channels.

---

## **Project Structure**

```
src/main/java/com/notificationsmicroservice
├── common
│   ├── constants
│   │   └── ApiPathConstants.java        // Centralized API path constants
│   ├── event
│   │   └── NotificationEvent.java       // Represents notification events
│   ├── properties
│       ├── S3Properties.java            // AWS S3 configuration properties
│       └── MailgunProperties.java       // Mailgun API configuration properties
├── config
│   ├── AmazonS3Config.java              // Configures AWS S3 client
│   ├── EventHandler.java                // Configures Kafka stream bindings
│   └── ThymeleafConfig.java             // Configures Thymeleaf for email templates
├── controller
│   ├── NotificationsApi.java            // Defines API endpoints for notifications
│   └── NotificationController.java      // Implements notification management endpoints
├── processor
│   └── NotificationProcessor.java       // Handles notification event streams
├── service
│   ├── NotificationService.java         // Interface for notification logic
│   ├── S3Service.java                   // Interface for S3 operations
│   └── impl
│       ├── NotificationServiceImpl.java // Implements email sending logic
│       └── S3ServiceImpl.java           // Implements S3 file management logic
└── Application.java                     // Entry point for the microservice

```

---

## **Interaction with Other Microservices**

### **Inputs:**

- *Kafka Topic: `new-notification-event`*Receives **`NotificationEvent`** from the **Eligibility Microservice** when an item (letter or package) is deemed eligible for notification.

### **Outputs:**

- **Mailgun API**Sends dynamically rendered email notifications to the intended recipients.
- **Logs or Alerts**Optionally publishes logs or alerts confirming the email delivery.

---

## **Configuration**

### **Kafka Configuration:**

The Kafka binder and topic bindings are defined in **`application.yaml`**:

```
spring:
  cloud:
    stream:
      kafka:
        binder:
          brokers: localhost:9092
        bindings:
          notificationEvent-in-0:
            destination: new-notification-event
```

### **S3 Configuration:**

S3 settings for storing and fetching email templates:

```
s3:
  accessKey: your-access-key
  secretKey: your-secret-key
  bucket: your-bucket-name
  region: your-region
```

### **Mailgun Configuration:**

API details for email delivery:

```
mailgun:
  url: https://api.mailgun.net/v3/your-domain/messages
  apiKey: your-mailgun-api-key
```

---

> [!Note]
> Make sure to replace the database URL, username, and password with your actual configuration and AWS Cloud configuration.

## **How Reactive Programming is Used**

The Notifications Microservice is built on **Project Reactor** to implement a fully reactive workflow. Here's how it uses Reactive Programming:

1. **Event Handling**:
    - The **`NotificationProcessor`** subscribes to the **`new-notification-event`** Kafka topic.
    - Events are processed reactively as they arrive, avoiding blocking operations.
2. **Template Retrieval**:
    - When an event is received, the template is fetched from AWS S3 reactively, using asynchronous calls to minimize IO wait time.
3. **Template Rendering**:
    - Thymeleaf is integrated into the reactive chain, dynamically populating templates without blocking the processing thread.
4. **Email Delivery**:
    - Emails are sent to Mailgun using non-blocking HTTP requests, ensuring the system remains responsive.
5. **Error Handling**:
    - Errors in any part of the stream are caught and logged reactively, with fallback mechanisms to ensure system reliability.

---

## **Testing the Service**

### **Unit Testing:**

Run all unit tests with:

```
mvn test
```

### **Manual Testing:**

1. Publish a **`NotificationEvent`** to the Kafka topic **`new-notification-event`**:

    ```
    echo '{"receiverEmail": "test@example.com", "message": "Your package is eligible!"}' | kafka-console-producer --broker-list localhost:9092 --topic new-notification-event
    ```

2. Monitor the service logs for email delivery confirmation.

---

## **How to Run the Service**

### **Prerequisites:**

- **Kafka**: Ensure a running Kafka broker (e.g., via Docker).
- **S3 Bucket**: Set up an AWS S3 bucket for templates.
- **Mailgun Account**: Obtain API credentials.

### **Steps:**

1. Build the project:

    ```
    mvn clean install
    ```

2. Start the application:

    ```
    java -jar target/notifications-microservice-api-0.0.1-SNAPSHOT.jar
    ```


---

## **Example Scenarios**

1. **Event Processing**:
    - A **`NotificationEvent`** is published to Kafka when an item is eligible.
    - The service dynamically fetches the appropriate template and sends an email.
2. **Template Upload**:
    - Users upload a new email template to S3 using the API.
    - The service uses the template for future notifications.

---

~~Note~~

~~Make sure to replace the database URL, username, and password with your actual configuration and AWS Cloud configuration.~~

## Related Microservices

The system consists of multiple microservices that work together to provide comprehensive functionality. Below is a list of all the microservices in the system, with links to their respective repositories:

- [**users-service-api**](https://github.com/juansebstt/users-service-api): Handles user management, including registration, profile updates, and account data.
- [**email-kafka-microservice**](https://github.com/juansebstt/email-kafka-microservice): Manages asynchronous email event processing using Kafka for reliable messaging.
- [**notifications-microservice-api**](https://github.com/juansebstt/notifications-microservice-api): Sends notifications based on triggered events from other services.
- [**email-authentication-service-api**](https://github.com/juansebstt/email-authentication-service-api): Manages email-based authentication and verification processes.
- [**email-api-gateway**](https://github.com/juansebstt/email-api-gateway): Serves as the entry point for routing requests to various microservices.
- [**letter-service-api**](https://github.com/juansebstt/letter-service-api): Manages letters, including creation, storage, and retrieval.
- [**packages-service-api**](https://github.com/juansebstt/packages-service-api): Manages package-related operations, including tracking and status updates.

## **Scalability and Extensibility**

- **Scalability**: The reactive, non-blocking design ensures the service can handle high loads without degradation in performance.
- **Extensibility**: Additional notification types or integration points (e.g., SMS notifications) can be added easily.