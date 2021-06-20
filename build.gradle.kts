val ktorVersion = "1.6.0"
val exposedVersion = "0.32.1"
val h2Version = "1.4.200"
val hikariCpVersion = "4.0.3"
val flywayVersion = "7.10.0"
val logbackVersion = "1.2.3"
val assertjVersion = "3.19.0"
val restAssuredVersion = "4.4.0"
val junitVersion = "5.7.1"

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version("1.17.1")
    java
}

allprojects {
    group = "fr.hadaly"
    version = "0.0.1"

    apply {
        plugin("org.jetbrains.kotlin.jvm")
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("ch.qos.logback:logback-classic:$logbackVersion")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

project(":nexus-api") {}

project(":centurio-restapi") {}

project(":persistence") {}

tasks.create("stage") {
    dependsOn("installDist")
}
