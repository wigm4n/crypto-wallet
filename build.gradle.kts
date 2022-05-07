plugins {
    java
    id("org.springframework.boot") version "2.6.7"
}

group = "ru.wallet"
version = "1.0-SNAPSHOT"

apply(plugin = "io.spring.dependency-management")

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.bitcoinj:bitcoinj-core:0.16.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}