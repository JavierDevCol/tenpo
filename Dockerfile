# Imagen base con Java y Gradle
FROM eclipse-temurin:21-jdk

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar los archivos del proyecto al contenedor
COPY . .

# Dar permisos de ejecución a Gradle Wrapper
RUN chmod +x ./gradlew

# Ejecutar Gradle para compilar el proyecto dentro del contenedor
RUN ./gradlew clean build

# Copiar el JAR generado al contenedor final
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=0 /app/build/libs/tenpo.jar app.jar

# Exponer el puerto de la API
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]