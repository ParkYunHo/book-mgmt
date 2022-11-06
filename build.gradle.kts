import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

group = "com.john"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.querydsl:querydsl-jpa:5.0.0")

    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework:spring-webflux")

    // Cucumber
    testImplementation("io.cucumber:cucumber-java:7.5.0")
    testImplementation("io.cucumber:cucumber-spring:7.5.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.5.0")
    testImplementation("org.junit.platform:junit-platform-suite-api")

    // Graphql
    testImplementation("org.springframework.graphql:spring-graphql-test")

    // Karate
    testImplementation("com.intuit.karate:karate-junit5:1.0.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

// resource directory to upload files karate
sourceSets {
    test {
        resources {
            srcDirs("src/test/kotlin")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
