plugins {
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.allopen") version "1.8.22"
    id("io.quarkus")
    id("org.jetbrains.kotlinx.kover") version "0.7.2"
}

repositories {
    maven {
        setUrl("https://maven.aliyun.com/repository/public/")
    }
    mavenLocal()
    mavenCentral()
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
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jackson")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.2")
    implementation("io.quarkiverse.amazonservices:quarkus-amazon-s3:2.3.0")
    implementation("software.amazon.awssdk:url-connection-client")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")

    testImplementation("io.quarkus:quarkus-jacoco")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("io.quarkiverse.mockk:quarkus-junit5-mockk:2.0.0")
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


tasks.test {

}

kover {
    isDisabled.set(false)

    engine.set(kotlinx.kover.api.DefaultIntellijEngine)

    filters {
       classes {
            includes += "com.zijian.*"
        }
    }

    instrumentation {
        excludeTasks += "dummy-tests"
    }

    htmlReport {
        onCheck.set(false)

        reportDir.set(layout.buildDirectory.dir("reports/kover-html-result"))
        overrideFilters {
            classes {
                includes += "com.zijian.*"
            }
        }
    }
}


