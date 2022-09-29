plugins {
    kotlin("plugin.serialization") version "1.7.20"
}

dependencies {
    implementation(project(":centurio-core"))
    implementation("io.ktor:ktor-client-cio:${rootProject.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-client-json:${rootProject.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-client-serialization:${rootProject.extra["ktorVersion"]}")
}

