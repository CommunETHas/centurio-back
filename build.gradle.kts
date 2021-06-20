import io.gitlab.arturbosch.detekt.Detekt

val ktorVersion = "1.6.0"
val exposedVersion = "0.32.1"
val h2Version = "1.4.200"
val hikariCpVersion = "4.0.3"
val flywayVersion = "7.10.0"
val logbackVersion = "1.2.3"
val assertjVersion = "3.19.0"
val restAssuredVersion = "4.4.0"
val junitVersion = "5.7.1"
val koinVersion = "3.1.0"

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

val analysisDir = file(projectDir)
val baselineFile = file("$rootDir/config/detekt/baseline.xml")
val configFile = file("$rootDir/config/detekt/detekt.yml")
val statisticsConfigFile = file("$rootDir/config/detekt/statistics.yml")

val kotlinFiles = "**/*.kt"
val kotlinScriptFiles = "**/*.kts"
val resourceFiles = "**/resources/**"
val buildFiles = "**/build/**"

val detektFormat by tasks.registering(Detekt::class) {
    description = "Formats whole project."
    parallel = true
    disableDefaultRuleSets = true
    buildUponDefaultConfig = true
    autoCorrect = true
    setSource(analysisDir)
    config.setFrom(listOf(statisticsConfigFile, configFile))
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
    baseline.set(baselineFile)
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = false
    }
}

val detektAll by tasks.registering(Detekt::class) {
    description = "Runs the whole project at once."
    parallel = true
    buildUponDefaultConfig = true
    setSource(analysisDir)
    config.setFrom(listOf(statisticsConfigFile, configFile))
    include(kotlinFiles)
    include(kotlinScriptFiles)
    exclude(resourceFiles)
    exclude(buildFiles)
    baseline.set(baselineFile)
    reports {
        xml.enabled = false
        html.enabled = false
        txt.enabled = true
    }
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
        implementation("io.insert-koin:koin-ktor:$koinVersion")
        implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")
        implementation("ch.qos.logback:logback-classic:$logbackVersion")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

project(":nexus-api") {}

project(":centurio-restapi") {}

project(":persistence") {}

// Task for Heroku deployment
tasks.create("stage") {
    dependsOn("centurio-restapi:installDist")
}
