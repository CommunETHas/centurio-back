plugins {
    kotlin("plugin.serialization") version "1.7.10"
}

dependencies {
    implementation(kotlin("stdlib"))
    api(project(":nexus-api"))
    api(project(":ethplorer-api"))
    implementation(project(":centurio-core"))
    implementation("io.ktor:ktor-client-serialization:${rootProject.extra["ktorVersion"]}")
}


