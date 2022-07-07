plugins {
    kotlin("plugin.serialization") version "1.7.10"
}

dependencies {
    implementation(project(":centurio-core"))
//    implementation(project(":ethplorer-api"))
    implementation("io.ktor:ktor-serialization:${rootProject.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-client-serialization:${rootProject.extra["ktorVersion"]}")

    implementation("com.h2database:h2:${rootProject.extra["h2Version"]}")
    implementation("org.postgresql:postgresql:${rootProject.extra["postgreVersion"]}")
    implementation("org.jetbrains.exposed:exposed-core:${rootProject.extra["exposedVersion"]}")
    implementation("org.jetbrains.exposed:exposed-dao:${rootProject.extra["exposedVersion"]}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${rootProject.extra["exposedVersion"]}")
    implementation("org.jetbrains.exposed:exposed-java-time:${rootProject.extra["exposedVersion"]}")
    implementation("com.zaxxer:HikariCP:${rootProject.extra["hikariCpVersion"]}")
    implementation("org.flywaydb:flyway-core:${rootProject.extra["flywayVersion"]}")
}
