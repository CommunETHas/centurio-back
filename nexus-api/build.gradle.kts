val ktorVersion = "1.6.0"

plugins {
    kotlin("plugin.serialization") version "1.5.10"
}

dependencies {
    implementation("org.web3j:core:4.8.4")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
}
