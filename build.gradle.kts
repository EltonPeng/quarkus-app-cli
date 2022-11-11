plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.allopen") version "1.7.20"
    id("io.quarkus")
    jacoco
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:quarkus-amazon-services-bom:${quarkusPlatformVersion}"))

    implementation("io.quarkus:quarkus-picocli")
    implementation("io.quarkus:quarkus-kotlin")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.3")
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jackson")
    implementation("io.quarkiverse.amazonservices:quarkus-amazon-s3:1.3.1")
    implementation("software.amazon.awssdk:url-connection-client")
    testImplementation("io.quarkus:quarkus-jacoco")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.mockk:mockk:1.13.2")
    testImplementation("io.quarkiverse.mockk:quarkus-junit5-mockk:1.1.1")
}

group = "com.zijian"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.javaParameters = true
}

jacoco {
    toolVersion = "0.8.8"
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

tasks.jacocoTestCoverageVerification {
    executionData.setFrom(layout.buildDirectory.file("/jacoco/test.exec"))
    violationRules {
        rule {
            limit {
                counter = "INSTRUCTION"
                minimum = 0.90.toBigDecimal()
            }
            limit {
                counter = "BRANCH"
                minimum = 0.92.toBigDecimal()
            }
        }
    }
}


