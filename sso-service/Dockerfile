FROM eclipse-temurin:21-jre-alpine

# folder working
WORKDIR /app

# copy file jar
COPY ./target/sso-0.0.1-SNAPSHOT.jar sso-service.jar

# create folder volume
VOLUME ["/app/ca"]
# run java
# ENTRYPOINT ["java" ,"-jar" ,"backend.jar"]
ENV SERVICE_NAME="sso-service"
ENV CONSUL_HOST="kk-consul-client"
ENV CONSUL_-PORT="5601"
ENV DATASOURCE_URL="postgres_security"
ENV DATASOURCE_PORT="5432"
ENV REDIS_HOST=""
ENV REDIS_PORT=""
ENV SERVER_PORT=8085
# ENTRYPOINT nên dùng JSON để nhận tín hiệu OS đúng cách
ENTRYPOINT ["java"]
# CMD để dựng phần còn lại, gồm biến JAVA_OPTS động
CMD ["-Djavax.net.ssl.trustStore=/app/ca/truststore.jks", "-Djavax.net.ssl.trustStorePassword=backend", "-Djavax.net.ssl.trustStoreType=PKCS12", "-jar", "sso-service.jar", "--spring.profiles.active=prod"]