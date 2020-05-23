import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.0.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("com.commercehub.gradle.plugin.avro") version "0.19.1"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
}

group = "com.rafaelcam"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven (url = "https://packages.confluent.io/maven/")
}

dependencies {
	implementation("org.apache.avro:avro:1.9.2")
	implementation("io.confluent:kafka-schema-registry-client:5.5.0")
	implementation("io.confluent:kafka-avro-serializer:5.5.0")
	implementation("io.confluent:kafka-streams-avro-serde:5.5.0") {
		exclude(group = "org.slf4j", module = "slf4j-log4j12")
	}
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("io.github.microutils:kotlin-logging:1.7.7")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

avro {
	isCreateSetters.set(true)
	isCreateOptionalGetters.set(false)
	isGettersReturnOptional.set(false)
	fieldVisibility.set("PUBLIC_DEPRECATED")
	outputCharacterEncoding.set("UTF-8")
	stringType.set("String")
	templateDirectory.set(null as String?)
	isEnableDecimalLogicalType.set(true)
	dateTimeLogicalType.set("JSR310")
}