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


## 📌 Notas
- Asegúrate de tener Docker instalado y corriendo.
- La base de datos PostgreSQL debe estar disponible para que la API funcione correctamente.
- La API necesita los puertos 8080 y 5432 libres para funcionar.

## 🛠️ Instalación
### 1. Clonar el repositorio
```sh
    git clone https://github.com/JavierDevCol/tenpo.git
    cd tenpo
```

### 2. Levantar los servicios con Docker
```sh
    docker-compose up -d --build
```
Esto iniciará una instancia de **PostgreSQL y de la API ** en `localhost:5432 y localhost:8080`.

### 3. Construir y ejecutar la API
```sh
    ./gradlew clean build
    ./gradlew bootRun
```
La API estará disponible en `http://localhost:8080`

## 🚀 Instrucciones para el Consumo de la API

### 1️⃣ Acceder a Swagger
Swagger permite probar los endpoints de la API de manera interactiva.

📌 **URL de Swagger:**
```
http://localhost:8080/swagger-ui.html
```

### 2️⃣ Endpoints Disponibles
| Método | Endpoint | Descripción |
|--------|---------|-------------|
| `GET` | `/api/calculate?num1={valor1}&num2={valor2}` | Calcula un porcentaje sobre dos números |
| `GET` | `/api/history?page=0&size=10` | Obtiene el historial de llamadas |



Ejemplo de uso en Swagger:
1. Iniciar la API.
2. Ir a `http://localhost:8080/swagger-ui.html`.
3. Probar los endpoints enviando parámetros.

---

## 🐳 Descarga y Ejecución con Docker Hub

### 1️⃣ Descargar la imagen de Docker Hub
Ejecuta el siguiente comando para descargar la imagen desde Docker Hub:
```sh
    docker pull onad/tenpo-api:latest
```

### 2️⃣ Ejecutar el contenedor
Si ya tienes PostgreSQL corriendo, puedes ejecutar la API con:
```sh
    docker run -d \
      --name tenpo_api \
      -e SPRING_DATASOURCE_URL=jdbc:postgresql://tenpo_postgres:5432/tenpo_db \
      -e SPRING_DATASOURCE_USERNAME=tenpo_user \
      -e SPRING_DATASOURCE_PASSWORD=tenpo_pass \
      -p 8080:8080 \
      onad/tenpo-api:latest
```

Si necesitas levantar PostgreSQL junto con la API, usa Docker Compose.

### 3️⃣ Verificar que la API esté corriendo
Revisar los contenedores activos:
```sh
    docker ps
```
Debes de ver algo como :

| CONTAINER ID |   IMAGE   |                COMMAND    |              CREATED  |        STATUS  |        PORTS   |                 NAMES |
|---------------|------------------------|-------------------------|------------------|-----------------|-------------------------|------
| 932b0be05fb5 |  onad/tenpo-api:latest |  "java -jar app.jar"   |   44 minutes ago |  Up 44 minutes  | 0.0.0.0:8080->8080/tcp   |tenpo_api |
| 948d1c4a81f4  | postgres:15      |       "docker-entrypoint.s…"  | 44 minutes ago  | Up 44 minutes |  0.0.0.0:5432->5432/tcp  | tenpo_postgres |
Si todo está bien, accede a Swagger en `http://localhost:8080/swagger-ui.html`.

---

### 🔹 Límite de tasa
Si se hacen más de 3 solicitudes por minuto, la API responderá con:
```http
    HTTP 429 Too Many Requests
```

## 🧪 Pruebas
Ejecutar las pruebas unitarias con:
```sh
    ./gradlew test
```


