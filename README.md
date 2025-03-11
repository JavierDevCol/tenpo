# Challenge Backend 2025

Este proyecto es una API REST desarrollada con **Spring Boot 3**, **Java 21** y **WebFlux** para calcular un resultado con un porcentaje dinámico obtenido de un servicio externo.

## 🚀 Características
- **Cálculo con porcentaje dinámico** usando una API externa simulada.
- **Caché en memoria** con Caffeine (válido por 30 minutos).
- **Reintentos automáticos** en caso de fallo del servicio externo.
- **Historial de llamadas** almacenado en PostgreSQL.
- **Rate Limiting** de 3 solicitudes por minuto con Bucket4j.
- **Contenedores Docker** para la API y la base de datos.
- **Pruebas unitarias y de integración** con JUnit y WebTestClient.

## 🛠️ Instalación
### 1. Clonar el repositorio
```sh
    git clone https://github.com/tu-usuario/challenge-backend-2025.git
    cd challenge-backend-2025
```

### 2. Levantar los servicios con Docker
```sh
    docker-compose up -d
```
Esto iniciará una instancia de **PostgreSQL** en `localhost:5432`.

### 3. Construir y ejecutar la API
```sh
    mvn clean install
    mvn spring-boot:run
```
La API estará disponible en `http://localhost:8080`

## 📌 Endpoints principales
### 🔹 Cálculo con porcentaje dinámico
```http
    GET /api/calculate?num1=5&num2=5
```
**Respuesta:**
```json
    {
      "result": 11
    }
```

### 🔹 Obtener historial de llamadas
```http
    GET /api/history
```
**Respuesta:**
```json
    [
      {
        "timestamp": "2025-03-10T12:00:00",
        "endpoint": "/api/calculate",
        "requestParams": "num1=5&num2=5",
        "response": "11"
      }
    ]
```

### 🔹 Límite de tasa
Si se hacen más de 3 solicitudes por minuto, la API responderá con:
```http
    HTTP 429 Too Many Requests
```

## 🧪 Pruebas
Ejecutar las pruebas unitarias con:
```sh
    mvn test
```

## 📦 Docker
Para construir y ejecutar la imagen de la API:
```sh
    docker build -t challenge-backend-2025 .
    docker run -p 8080:8080 challenge-backend-2025
```


