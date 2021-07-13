val ktorVersion = "1.6.1"
val exposedVersion = "0.32.1"
val h2Version = "1.4.200"
val hikariCpVersion = "4.0.3"
val flywayVersion = "7.10.0"
val logbackVersion = "1.2.3"
val assertjVersion = "3.20.2"
val restAssuredVersion = "4.4.0"
val junitVersion = "5.7.2"
val koinVersion = "3.1.0"

plugins {
    kotlin("plugin.serialization") version "1.5.21"
    application
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":nexus-api"))
    implementation(project(":ethplorer-api"))
    implementation(project(":persistence"))
    implementation(project(":recommandation-engine"))
    implementation(project(":centurio-core"))
    implementation(project(":notification"))

    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-websockets:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")

    implementation("io.ktor:ktor-client-serialization:$ktorVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.web3j:core:5.0.0")
    implementation("org.web3j:crypto:5.0.0")
    implementation("org.web3j:utils:5.0.0")

    testImplementation("org.assertj:assertj-core:$assertjVersion")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("io.ktor:ktor-client-cio:$ktorVersion")
}

application {
    mainClass.set("fr.hadaly.MainKt")
}
