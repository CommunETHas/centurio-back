val ktorVersion = "1.6.0"
val exposedVersion = "0.32.1"
val h2Version = "1.4.200"
val hikariCpVersion = "4.0.3"
val flywayVersion = "7.10.0"
val logbackVersion = "1.2.3"
val assertjVersion = "3.20.2"
val restAssuredVersion = "4.4.0"
val junitVersion = "5.7.2"

plugins {
    kotlin("plugin.serialization") version "1.5.10"
    application
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":nexus-api"))
    implementation(project(":persistence"))

    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-websockets:$ktorVersion")

    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")

    testImplementation("org.assertj:assertj-core:$assertjVersion")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("io.ktor:ktor-client-cio:$ktorVersion")
}

application {
    mainClass.set("MainKt")
}
