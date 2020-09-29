**1. How to create encrypted password**
```
java -cp ~/.m2/repository/org/jasypt/jasypt/1.9.2/jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="sinux123" password=dev-env-secret algorithm=PBEWITHMD5ANDDES
```
**2. Start nats**
```
docker run -d --name nats-main -p 4222:4222 -p 6222:6222 -p 8222:8222 nats
```