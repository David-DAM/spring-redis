# Spring Redis Currency Service

Este proyecto es una aplicación de alto rendimiento construida con **Spring Boot 4.0.1** y **Java 25**, diseñada para la
gestión, monitoreo y distribución de tipos de cambio de divisas en tiempo real utilizando **Redis** como motor principal
de datos y mensajería.

## Tecnologías Principales

- **Java 25** (OpenJDK)
- **Spring Boot 4.0.1** (Spring MVC, Data Redis)
- **Jakarta EE** (Ecosistema moderno de Java)
- **Redis**:
    - Almacenamiento en caché (Hash, String).
    - Mensajería reactiva (Pub/Sub).
    - Procesamiento de eventos (Streams).
- **Lombok**: Reducción de código repetitivo.
- **ShedLock**: Gestión de bloqueos distribuidos para tareas programadas.

## Arquitectura

La aplicación sigue una arquitectura limpia (Clean Architecture) dividida en capas:

- **`domain`**: Entidades core (`Currency`), eventos y lógica de negocio.
- **`application`**: Servicios de orquestación que implementan los casos de uso.
- **`infrastructure`**:
    - **API**: Controladores REST y gestión de excepciones.
    - **Cache**: Implementaciones de persistencia en Redis, Repositorios (Pipelines, Rankings) y configuraciones de
      mensajería.
    - **Scheduler**: Tareas automáticas de carga de datos sincronizadas entre múltiples instancias.

## Características Clave

1. **Ingesta de Datos Automatizada**: Un scheduler recupera precios de divisas externamente cada 30 segundos de forma
   resiliente (`@Retryable`).
2. **Sincronización Distribuida**: Uso de **ShedLock** para asegurar que solo una instancia de la aplicación procese los
   datos a la vez en entornos clusterizados.
3. **Eventos en Tiempo Real**:
    - Notificaciones inmediatas vía **Pub/Sub** para cambios críticos de precios (Mínimos/Máximos).
    - Log de auditoría y procesamiento asíncrono robusto mediante **Redis Streams**.
4. **Optimización de Consultas**: Uso de Redis Pipelines y estructuras de Ranking para ofrecer tiempos de respuesta
   sub-milisegundo en la API.

## Instalación y Configuración

### Requisitos previos

- Docker y Docker Compose
- JDK 25

### Ejecución

1. Levantar la infraestructura de Redis:
   ```bash
   docker-compose up -d
   ```

2. Ejecutar la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

## 📖 Documentación de la API

La especificación de los endpoints se encuentra disponible en formato OpenAPI en:
`src/main/resources/openapi.yaml`

---
*Desarrollado con estándares de Jakarta EE y Spring MVC.*