plugins {
    id("java")
    id("application")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


application{
    mainClass.set("io.helidon.microprofile.cdi.Main")
}


java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

var helidonVersion = "3.2.6"


dependencies {
    implementation(enforcedPlatform("io.helidon:helidon-dependencies:${helidonVersion}"))

    implementation("io.helidon.microprofile.server:helidon-microprofile-server:3.2.6")
    testImplementation("io.helidon.microprofile.cdi:helidon-microprofile-cdi:3.2.6")
    implementation("jakarta.ws.rs:jakarta.ws.rs-api:3.1.0")
    implementation("io.helidon.microprofile.config:helidon-microprofile-config:3.2.6")
    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")
    implementation("org.postgresql:postgresql:42.7.2")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    compileOnly("jakarta.enterprise:jakarta.enterprise.cdi-api:4.0.1")
    implementation("org.eclipse.microprofile.rest.client:microprofile-rest-client-api:3.0.1")
    implementation("io.helidon.microprofile.openapi:helidon-microprofile-openapi:3.2.6")
    implementation("io.helidon.microprofile.health:helidon-microprofile-health:3.2.6")
    testImplementation("io.helidon.microprofile.cdi:helidon-microprofile-cdi:3.2.6")

}

tasks.test {
    useJUnitPlatform()
}