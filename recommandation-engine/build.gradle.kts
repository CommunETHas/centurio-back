val ktorVersion = "1.6.0"

plugins {
    kotlin("plugin.serialization") version "1.5.20"
}

dependencies {
    implementation(kotlin("stdlib"))
    api(project(":nexus-api"))
    api(project(":ethplorer-api"))
    implementation(project(":centurio-core"))
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
}


