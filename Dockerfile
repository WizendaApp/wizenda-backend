# === STAGE 1: build com Gradle + JDK 25 ===
FROM gradle:9.1-jdk-25-and-25-corretto AS builder
WORKDIR /app

# Copia arquivos de build do Gradle (ajuste se usar Kotlin DSL, etc)
COPY gradle gradle
COPY gradlew settings.gradle* build.gradle* ./
# Copia código fonte
COPY src src

# (Opcional) dá permissão para gradlew se necessário
RUN chmod +x ./gradlew

# Build da aplicação (skip tests se quiser)
RUN ./gradlew clean build -x test --no-daemon

# === STAGE 2: prepara para packaging com Spring Boot layered jar ===
# Aqui usamos a JRE para extrair camadas
FROM bellsoft/liberica-openjre-debian:25-cds AS extractor
WORKDIR /builder

ARG JAR_FILE=/app/build/libs/*.jar
COPY --from=builder ${JAR_FILE} application.jar

# Extrai camadas (dependencies, spring-boot-loader, etc)
RUN java -Djarmode=tools -jar application.jar extract --layers --destination extracted

# === STAGE 3: runtime leve com JRE 25 + camadas extraídas ===
FROM bellsoft/liberica-openjre-debian:25-cds AS runtime
WORKDIR /app

# Copia camadas para o runtime (cada COPY vira uma layer, bom pro cache)
COPY --from=extractor /builder/extracted/dependencies/ ./
COPY --from=extractor /builder/extracted/spring-boot-loader/ ./
#COPY --from=extractor /builder/extracted/snapshot-dependencies/ ./
COPY --from=extractor /builder/extracted/application/ ./

# (Opcional) usar usuário não-root
RUN adduser spring
USER spring:spring

ENV PORT=8080
EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "application.jar"]
CMD ["--server.port=${PORT}"]
