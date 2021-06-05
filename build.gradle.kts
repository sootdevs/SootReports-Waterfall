import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    idea
    eclipse
    id("com.github.johnrengelman.shadow") version "6.1.0" apply false
}

group = "dev.codeerror"
version = "1.0"

apply(plugin = "com.github.johnrengelman.shadow")

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.github.waterfallmc", "waterfall-api", "1.16-R0.4-SNAPSHOT")
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

eclipse {
    classpath {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
}