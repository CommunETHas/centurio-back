plugins {
    kotlin("plugin.serialization") version "1.8.10"
}

dependencies {
    implementation(project(":centurio-core"))
    implementation("org.web3j:core:${rootProject.extra["web3j"]}")
    implementation("io.ktor:ktor-client-cio:${rootProject.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-client-json:${rootProject.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-client-serialization:${rootProject.extra["ktorVersion"]}")
}
