# Stage 1: build with Maven
FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /app

# only copy what's needed to compile
COPY pom.xml .
COPY src ./src

# compile & package
RUN mvn clean package -DskipTests \
  && ls -l target

# Stage 2: runtime
FROM eclipse-temurin:17-jre
WORKDIR /app

# create non-root user for running the app
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser \
    && mkdir -p /app/logs && chown -R appuser:appgroup /app
USER appuser

# grab whatever JAR Maven produced, rename to app.jar
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
HEALTHCHECK --interval=30s --timeout=5s --start-period=20s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1
