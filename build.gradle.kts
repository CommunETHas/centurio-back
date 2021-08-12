import io.gitlab.arturbosch.detekt.Detekt

val logbackVersion = "1.2.5"
val koinVersion = "3.1.2"
val ktorVersion = "1.6.2"
val arrowVersion = "0.13.2"

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
    }
}

dependencies {
    swaggerUI("org.webjars:swagger-ui:3.51.2")
}

plugins {
    id("io.gitlab.arturbosch.detekt") version "1.18.0"
    id("org.hidetake.swagger.generator") version "2.18.2"
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

    extra["arrowVersion"] = "0.13.2"
    extra["assertjVersion"] = "3.20.2"
    extra["exposedVersion"] = "0.32.1"
    extra["flywayVersion"] = "7.11.2"
    extra["h2Version"] = "1.4.200"
    extra["hikariCpVersion"] = "4.0.3"
    extra["junitVersion"] = "5.7.2"
    extra["koinVersion"] = "3.1.2"
    extra["kotlinVersion"] = "1.5.21"
    extra["ktorVersion"] = "1.6.1"
    extra["logbackVersion"] = "1.2.3"
    extra["postgreVersion"] = "42.2.23"
    extra["restAssuredVersion"] = "4.4.0"
    extra["web3j"] = "5.0.0"

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
        implementation("io.arrow-kt:arrow-core:$arrowVersion")
        testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
        testImplementation("org.jetbrains.kotlin:kotlin-test:1.5.21")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}


swaggerSources {
    create("centurio") {
        val restApiDir = project(":centurio-restapi").projectDir
        setInputFile(file(restApiDir.resolve("src/main/resources/centurio.yaml")))
        ui.outputDir = file(restApiDir.resolve("src/main/resources/swagger"))
        code.language = "html"
    }
}

// Task for Heroku deployment
tasks.create("stage") {
    dependsOn("centurio-restapi:installDist")
}

tasks.withType<Detekt>().configureEach {
    // Target version of the generated JVM bytecode. It is used for type resolution.
    this.jvmTarget = "1.8"
}
