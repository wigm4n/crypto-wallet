FROM alpine:3.16

RUN apk add openjdk13
COPY build/libs/crypto-wallet.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]