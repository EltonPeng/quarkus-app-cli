plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.allopen") version "1.7.20"
    id("io.quarkus")
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
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
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.0")
    implementation("io.quarkiverse.amazonservices:quarkus-amazon-s3:1.3.1")
    implementation("software.amazon.awssdk:url-connection-client")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")

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


tasks.test {

}

kover {
    // true to disable instrumentation and all Kover tasks in this project
    isDisabled.set(false)

    // to change engine, use kotlinx.kover.api.IntellijEngine("xxx") or kotlinx.kover.api.JacocoEngine("xxx")
    engine.set(kotlinx.kover.api.DefaultIntellijEngine)

    // common filters for all default Kover tasks
    filters {
        // common class filter for all default Kover tasks in this project
        classes {
            // class inclusion rules
            includes += "com.zijian.*"
        }
    }

    instrumentation {
        // set of test tasks names to exclude from instrumentation. The results of their execution will not be presented in the report
        excludeTasks += "dummy-tests"
    }

    htmlReport {
        // set to true to run koverHtmlReport task during the execution of the check task (if it exists) of the current project
        onCheck.set(false)

        // change report directory
        reportDir.set(layout.buildDirectory.dir("reports/kover-html-result"))
        overrideFilters {
            // override common class filter
            classes {
                // class inclusion rules
                includes += "com.zijian.*"
            }
        }
    }
}


