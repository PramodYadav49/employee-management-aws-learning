# syntax=docker/dockerfile:1

# ---------- build stage ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /build

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ---------- runtime stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

RUN groupadd --system app && useradd --system --gid app app

COPY --from=build /build/target/employee-management-0.0.1-SNAPSHOT.jar app.jar

USER app
EXPOSE 9090
ENV JAVA_OPTS=""
ENV APP_PORT=9090

HEALTHCHECK --interval=30s --timeout=3s --start-period=20s --retries=3 \
  CMD ["sh", "-c", "curl -fsS http://localhost:${APP_PORT:-9090}/api/employees || exit 1"]

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
