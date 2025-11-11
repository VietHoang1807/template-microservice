## CREATE FILE SAN.CNF

### TẠO CERTIFICATE BACKEND
```openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout backend.key -out backend.crt -config openssl-backend-san.cnf```


### Tự sinh SSL Self Person (HTTPS)
```shell
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout backend.key -out backend.crt
```
```shell
keytool -importcert -file backend.crt -alias backend -keystore truststore.jks -storepass backend -noprompt
```