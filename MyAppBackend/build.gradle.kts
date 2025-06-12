plugins {
	java
	id("org.springframework.boot") version "3.5.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.app"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	implementation("org.springframework.boot:spring-boot-starter-security") // Security

	implementation ("io.jsonwebtoken:jjwt-api:0.12.5") // JWT
	implementation ("io.jsonwebtoken:jjwt-impl:0.12.5")
	implementation ("io.jsonwebtoken:jjwt-jackson:0.12.5")

	implementation ("jakarta.validation:jakarta.validation-api:3.0.2") // VALIDACIONES ENTITYS
	implementation ("org.hibernate.validator:hibernate-validator:8.0.0.Final")

	runtimeOnly("org.postgresql:postgresql")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
