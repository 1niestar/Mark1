import org.springframework.boot.gradle.tasks.bundling.BootJar

val bootJar: BootJar by tasks
bootJar.enabled = true

plugins {
    id("java")
    id("java-library")
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"

    val kotlinVersion = "1.8.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    idea
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
//        exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j")
    }
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }

}

group = "com.atoncorp.mpki"
version = "1.0-SNAPSHOT"

var lombokVersion = "1.18.20"
var queryDslVersion = "5.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-tomcat")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // WEB
    implementation("org.springframework.boot:spring-boot-starter-web")

    // Log4j2
    implementation("org.bgee.log4jdbc-log4j2:log4jdbc-log4j2-jdbc4.1:1.16")
    implementation ("org.springframework.boot:spring-boot-starter-log4j2")

    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")
    // Internal Tomcat
    implementation("org.springframework.boot:spring-boot-starter-web")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")

    // GSON
    implementation("com.google.code.gson:gson")

    // Apache dependencies
    implementation("org.apache.httpcomponents:httpclient:4.5.14")

    // Java servlet
    implementation("javax.servlet:jstl:1.2")

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:$queryDslVersion")
    implementation("com.querydsl:querydsl-sql:$queryDslVersion")
    kapt("com.querydsl:querydsl-apt:$queryDslVersion:jpa")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.test {
    useJUnitPlatform()
}

// QueryDSL Build Configuration
kotlin.sourceSets.main {
    println("kotlin sourceSets build Dir::$buildDir")
    setBuildDir("$buildDir")
}

idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}