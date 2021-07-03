import org.jetbrains.kotlin.daemon.common.isDaemonEnabled

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val h2Version: String by project
val exposedVersion: String by project
val hikariCpVersion: String by project
val flywayVersion: String by project

plugins {
    kotlin("plugin.serialization") version "1.5.20"
}

dependencies {
    implementation(project(":centurio-core"))
    implementation(project(":ethplorer-api"))
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")

    implementation("com.h2database:h2:$h2Version")
    implementation("org.postgresql:postgresql:42.2.22")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    implementation("com.zaxxer:HikariCP:$hikariCpVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
}
