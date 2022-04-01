plugins {
    kotlin("plugin.serialization") version "1.6.20"
}

dependencies {
    implementation(kotlin("stdlib"))
//    api(project(":nexus-api"))
    implementation("io.ktor:ktor-client-serialization:${rootProject.extra["ktorVersion"]}")
}
