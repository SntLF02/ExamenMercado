# ========================================
# ETAPA 1: BUILD (Compilación)
# ========================================
FROM alpine:latest as build

# Actualizar e instalar JDK 17
RUN apk update
RUN apk add openjdk17

# Copiar código fuente
COPY . .

# Permisos para gradlew
RUN chmod +x ./gradlew

# Generar el JAR (omitiendo tests para acelerar deploy en Render)
RUN ./gradlew bootJar -x test --no-daemon

# ========================================
# ETAPA 2: RUNTIME (Ejecución)
# ========================================
#  eclipse-temurin en lugar de openjdk
FROM eclipse-temurin:17-jre-alpine

# Documentar puerto
EXPOSE 8080

# Usamos *.jar para que funcione sin importar el nombre exacto de la versión
COPY --from=build ./build/libs/*.jar ./app.jar

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]