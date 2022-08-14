plugins {
    java
    id("org.springframework.boot") version "2.6.7"
    id("io.freefair.lombok") version "6.4.3"
}

group = "org.wigm4n"

apply(plugin = "io.spring.dependency-management")

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.apache.httpcomponents:httpclient")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.liquibase:liquibase-core:4.15.0")
    runtimeOnly("org.postgresql:postgresql")

    implementation("javax.validation:validation-api:2.0.1.Final")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}