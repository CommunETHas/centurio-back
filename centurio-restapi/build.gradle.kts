val h2Version = "1.4.200"
val hikariCpVersion = "4.0.3"
val flywayVersion = "7.10.0"
val logbackVersion = "1.2.3"
val assertjVersion = "3.20.2"
val restAssuredVersion = "4.4.0"
val junitVersion = "5.7.2"
val koinVersion = "3.1.0"

plugins {
    kotlin("plugin.serialization") version "1.7.0"
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

    implementation("io.ktor:ktor-server-netty:${rootProject.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-serialization:${rootProject.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-websockets:${rootProject.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-auth:${rootProject.extra["ktorVersion"]}")
    implementation("io.ktor:ktor-auth-jwt:${rootProject.extra["ktorVersion"]}")

    implementation("io.ktor:ktor-client-serialization:${rootProject.extra["ktorVersion"]}")

    implementation("org.jetbrains.exposed:exposed-core:${rootProject.extra["exposedVersion"]}")
    implementation("org.jetbrains.exposed:exposed-dao:${rootProject.extra["exposedVersion"]}")
    implementation("org.web3j:core:${rootProject.extra["web3j"]}")
    implementation("org.web3j:crypto:${rootProject.extra["web3j"]}")
    implementation("org.web3j:utils:${rootProject.extra["web3j"]}")

    testImplementation("org.assertj:assertj-core:${rootProject.extra["assertjVersion"]}")
    testImplementation("io.rest-assured:rest-assured:${rootProject.extra["restAssuredVersion"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${rootProject.extra["junitVersion"]}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${rootProject.extra["junitVersion"]}")
    testImplementation("io.ktor:ktor-client-cio:${rootProject.extra["ktorVersion"]}")
}

application {
    mainClass.set("fr.hadaly.MainKt")
}
