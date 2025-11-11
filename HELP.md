## Guides configuration [Consul](https://docs.spring.io/spring-cloud-consul/docs/current/reference/html/#quick-start) and Config Key/Value
`<consul.version>4.3.0</consul.version>`
1. Add library for pom.xml
    ```xml
   <dependencies>
      <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-consul-discovery</artifactId>
            <version>${consul.version}</version>
       </dependency>
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-consul-config</artifactId>
       </dependency>
   </dependencies>
    ```
2. Edit application.yml
```yml
spring:
   config:
      import: optional:consul:${CONSUL_HOST:localhost}:${CONSUL_PORT:8500}
   cloud:
      consul:
         host: ${CONSUL_HOST:localhost}
         port: ${CONSUL_PORT:8500}
         discovery:
            register: true
            prefer-ip-address: true
            health-check-path: ${management.server.servlet.context-path}/actuator/health
            health-check-interval: 10s
            health-check-url: https://0.0.0.0:8080/actuator/health
            service-name: ${spring.application.name}
            # Connection Pooling
            catalog-services-watch-delay: 1000
            catalog-services-watch-timeout: 2
            acl-token: token-consul
            health-check-headers:
               X-Config-Token: token-consul
            query-passing: true
            heartbeat:
               enabled: true
            instance-id: ${spring.application.name}:${vcap.application.instance_id:${spring.application.instance_id:${random.value}}}
         config:
            enabled: true
            name: ${spring.application.name}
            format: yaml
            data-key: ${CONSUL_DATA_KEY:data}
            acl-token: token-consul
```
3. Configuration Key/Value in web consul
   - Create folder config (required)
   ![img.png](/docker-compose/images/consul/img.png)
   - Create folder with same service-name
   ![img.png](/docker-compose/images/consul/img_1.png)
   - Create file suffix with format yaml
   ![img.png](/docker-compose/images/consul/img_2.png)
   ![img.png](/docker-compose/images/consul/img_3.png)

### Attention
* Some variable don't support refresh changes so you have to restart the system
* Create token master `docker exec -it kk-consul-server consul acl bootstrap`
---