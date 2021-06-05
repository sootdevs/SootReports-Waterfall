import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0" apply false
}

group = "dev.codeerror"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.github.waterfallmc", "waterfall-api", "1.16-R0.4-SNAPSHOT")
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
}