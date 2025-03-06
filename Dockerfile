# Usa uma imagem base do OpenJDK 17
FROM openjdk:21-jdk
# Define o diretório de trabalho dentro do container
WORKDIR /app
# Copia o arquivo JAR gerado pelo Maven para o container
COPY target/accountspayable-0.0.1-SNAPSHOT.jar app.jar
# Expõe a porta em que a aplicação Spring Boot roda
EXPOSE 8080
# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]