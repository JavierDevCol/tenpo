# Usar imagen base de OpenJDK 21 con JDK y JRE
FROM eclipse-temurin:21-jdk

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR de la aplicación
COPY target/challenge-backend-2025-1.0.0.jar app.jar

# Exponer el puerto de la API
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
