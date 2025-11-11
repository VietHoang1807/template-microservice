FROM eclipse-temurin:21-jre-alpine

# folder working
WORKDIR /app

# copy file jar
COPY ./target/gateway-0.0.1-SNAPSHOT.jar gateway.jar

# create folder volume
VOLUME ["/app/ca"]
# run java
# ENTRYPOINT ["java" ,"-jar" ,"backend.jar"]
ENV SERVICE_NAME="api-gateway"
ENV CONSUL_HOST="kk-consul-client"
ENV CONSUL_PORT=8500
ENV CONSUL_ACL_TOKEN="f18cfb7d-5a36-2a24-7c15-d3316e450d07"
ENV ORDER_SERVICE_ROUTE="lb://order-service"
ENV PAYMENT_SERVICE_ROUTE="lb://payment-service"
ENV CATEGORY_SERVICE_ROUTE="lb://category-service"
ENV SSO_SERVICE_ROUTE="lb://sso-service"
ENV ZIPKIN_SERVER="http://zipkin:9411/api/v2/spans"
ENV CONSUL_DATA_KEY="data"
ENV REDIS_HOST=""
ENV REDIS_PORT=6379
ENV SERVER_PORT=8080
ENV HOSTNAME="host.docker.internal"
# ENTRYPOINT nên dùng JSON để nhận tín hiệu OS đúng cách
ENTRYPOINT ["java"]
# CMD để dựng phần còn lại, gồm biến JAVA_OPTS động
CMD ["-Djavax.net.ssl.trustStore=/app/ca/truststore.jks", "-Djavax.net.ssl.trustStorePassword=backend", "-Djavax.net.ssl.trustStoreType=PKCS12", "-jar", "gateway.jar", "--spring.profiles.active=prod"]