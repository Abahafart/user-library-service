import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.6"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.graalvm.buildtools.native") version "0.9.20"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.8.21"
	kotlin("jvm") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
}

group = "com.abahafart.kotlin.template"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.springframework.data:spring-data-r2dbc")
	implementation("org.springframework.data:spring-data-relational")
	implementation("org.springframework.boot:spring-boot-autoconfigure")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.data:spring-data-jdbc")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// Manage versions of all kotlin components
	implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
	// Use kotlin JDK 8 standard library
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${property("kotlinxCoroutinesVersion")}")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${property("kotlinxCoroutinesVersion")}")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${property("kotlinxCoroutinesVersion")}")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:${property("kotlinxCoroutinesVersion")}")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${property("kotlinxCoroutinesVersion")}")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("com.fasterxml.jackson.core:jackson-databind:${property("jacksonDatabindVersion")}")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.github.microutils:kotlin-logging-jvm:${property("kotlinLoggingJvm")}")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j")

	implementation("org.liquibase:liquibase-core:${property("liquibaseCoreVersion")}")

	implementation("org.mapstruct:mapstruct:${property("mapstructVersion")}")
	implementation("org.mapstruct:mapstruct-processor:${property("mapstructVersion")}")

	implementation("io.github.resilience4j:resilience4j-circuitbreaker:${property("resilience4jCircuitbreaker")}")
	implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j:${property("reactorResilience4jVersion")}")
	implementation("io.github.resilience4j:resilience4j-kotlin:${property("resilience4jKotlin")}")

	implementation("io.swagger.core.v3:swagger-annotations:${property("swaggerAnnotations")}")
	implementation("org.springdoc:springdoc-openapi-ui:${property("openapiVersion")}")
	implementation("org.springdoc:springdoc-openapi-webflux-ui:${property("openapiVersion")}")
	implementation("org.springdoc:springdoc-openapi-kotlin:${property("openapiVersion")}")

	implementation("org.yaml:snakeyaml:${property("snakeYamlVersion")}")

	runtimeOnly("com.mysql:mysql-connector-j:${property("mysqlConnectorVersion")}")
	runtimeOnly("dev.miku:r2dbc-mysql:${property("r2dbcMysqlVersion")}")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:3.0.6")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.testcontainers:mysql:${property("testContainersVersion")}")
	testImplementation("org.testcontainers:junit-jupiter:${property("testContainersVersion")}")
	testImplementation("org.testcontainers:kafka:${property("testContainersVersion")}")
	testImplementation("org.testcontainers:testcontainers:${property("testContainersVersion")}")
	testImplementation("org.testcontainers:mockserver:${property("testContainersVersion")}")
	testImplementation("io.mockk:mockk:${property("mockkVersion")}")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation("org.mock-server:mockserver-client-java:${property("mockserver")}") {
		exclude(group = "javax.servlet", module = "javax.servlet-api")
	}

	testImplementation("com.ninja-squad:springmockk:${property("springmockk")}")
	testImplementation("io.github.serpro69:kotlin-faker:${property("kotlinFaker")}")
	testImplementation("org.awaitility:awaitility:${property("awaitilityVersion")}")
	testImplementation("org.assertj:assertj-core:${property("assertJVersion")}")
	testImplementation("com.tngtech.archunit:archunit-junit5:${property("archunit")}")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
