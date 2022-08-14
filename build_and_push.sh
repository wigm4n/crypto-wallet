./gradlew clean build
docker buildx build --platform linux/amd64 --push -t wigm4n/crypto-wallet:1.0.0 .