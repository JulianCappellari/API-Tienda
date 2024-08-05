# Utiliza una imagen base con OpenJDK
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR desde tu máquina local al contenedor
COPY target/tu-aplicacion.jar app.jar

# Expone el puerto en el que tu aplicación se ejecutará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
