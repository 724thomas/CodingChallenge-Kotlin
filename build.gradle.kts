plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "demo"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // 핵심: 웹 스타터 추가
    implementation("org.springframework.boot:spring-boot-starter-web")

    // 필요하면 actuator 등 추가 가능
    // implementation("org.springframework.boot:spring-boot-starter-actuator")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
    
    sourceSets {
        main {
            kotlin.srcDirs("src/main/kotlin", "src/main/Practice")
            // 각 Practice 파일은 별도 패키지로 분리되어 있어 개별 실행 가능
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
