val ktorVersion = "1.6.1"

plugins {
    kotlin("plugin.serialization") version "1.5.21"
}

dependencies {
    implementation(project(":centurio-core"))
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
}

