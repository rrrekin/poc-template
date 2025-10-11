plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kotlin.spring)
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
	alias(libs.plugins.gradle.versions)
	alias(libs.plugins.version.catalog.update)
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "POC Template Project"

// Version Catalog Update Plugin Configuration
versionCatalogUpdate {
	sortByKey = true
	keep {
		keepUnusedVersions = true
	}
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot
	implementation(libs.bundles.spring.boot)
	developmentOnly(libs.spring.boot.devtools)

	// Kotlin
	implementation(libs.bundles.kotlin)

	// AI & ML
	implementation(libs.bundles.ai)

	// Apache (Excel & CSV)
	implementation(libs.bundles.apache)

	// WebJars (Frontend Libraries)
	implementation(libs.bundles.webjars)

	// Exposed (Kotlin SQL Framework) + SQLite
	implementation(libs.bundles.exposed)
	implementation(libs.sqlite.jdbc)

	// Testing
	testImplementation(libs.bundles.testing)
	testRuntimeOnly(libs.junit.platform.launcher)
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
