plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("plugin.jpa") version "1.9.24"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
	jacoco
}

group = "ru.apsenty"
version = "1.0.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	group = "verification"
	useJUnitPlatform()
	defaultCharacterEncoding = "UTF-8"
	finalizedBy("jacocoTestReport")
}

tasks.named("build") {
	dependsOn("createDocumentationPage")
}

tasks.register<Exec>("bundleYamlFile") {
	group = "documentation"
	description = "merge all standalone files into a single definition file"
	workingDir = file("src/main/resources/documentation")
	commandLine("cmd", "/c", "npx @redocly/cli bundle openapi.yaml --output bundled.yaml")
}

tasks.register<Exec>("lintBundledFile") {
	group = "documentation"
	description = "validate bundled.yaml and generate lint-ignore file"
	workingDir = file("src/main/resources/documentation")
	commandLine("cmd", "/c", "npx @redocly/cli lint --generate-ignore-file bundled.yaml")
	dependsOn("bundleYamlFile")
}

tasks.register<Delete>("deletePathsDir") {
	group = "documentation"
	description = "delete paths directory before creating new"

	delete(file("src/main/resources/documentation/paths"))
	dependsOn("lintBundledFile")
}

tasks.register<Exec>("splitBundledFile") {
	group = "documentation"
	description = "divide the large API definition file into different parts"
	workingDir = file("src/main/resources/documentation")
	commandLine("cmd", "/c", "npx @redocly/cli split bundled.yaml --outDir .")
	dependsOn("deletePathsDir")
}

tasks.register<Exec>("previewDocFile") {
	group = "documentation"
	description = "generate link for preview documentation"
	workingDir = file("src/main/resources/documentation")
	commandLine("cmd", "/c", "npx @redocly/cli preview-docs bundled.yaml")
	dependsOn("splitBundledFile")
}

tasks.register<Exec>("createDocumentationPage") {
	group = "documentation"
	description = "create json-file and update index page with api-documentation"
	workingDir = file("docs")
	val openApiPath = "src/main/resources/documentation/openapi.yaml"
	commandLine("cmd", "/c", "npx @redocly/cli bundle ../$openApiPath -o dist.json")
	dependsOn("splitBundledFile")
}