plugins {
    kotlin("jvm") version "2.2.0"
}

group = "xyz.kandrac"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.ojalgo:ojalgo:56.1.1")
    implementation("org.json:json:20250517")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}