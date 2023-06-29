plugins {
    id("java")
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "io.github.rephrasing.pearlescent"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar").configure {

    minimize()
    archiveFileName.set("${project.name}-v${project.version}.jar")
    destinationDirectory.set(file("$rootDir/output"))
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}