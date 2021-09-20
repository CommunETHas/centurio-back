plugins {
    kotlin("plugin.serialization") version "1.5.31"
}

dependencies {
    implementation(project(":centurio-core"))
    implementation("org.web3j:core:5.0.0")
    implementation("io.ktor:ktor-client-cio:${rootProject.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-client-json:${rootProject.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-client-serialization:${rootProject.extra["ktorVersion"]}")
}


