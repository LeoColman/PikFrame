import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "br.com.colman.pikframe"
version = "0.1.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation(compose.desktop.linux_arm64)
    implementation(compose.components.resources)
    
    implementation("com.sksamuel.hoplite:hoplite-core:2.9.0")
    implementation("com.sksamuel.hoplite:hoplite-yaml:2.9.0")
    implementation("com.drewnoakes:metadata-extractor:2.19.0")
}

compose.resources {
    customDirectory("main", provider { layout.projectDirectory.dir("src/main/resources/composeResources") })
}
compose.desktop {
    application {
        mainClass = "br.com.colman.pikframe.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.AppImage)
            packageName = "PikFrame"
            packageVersion = "1.0.0"
        }
    }
}

tasks.named<ShadowJar>("shadowJar") {
    mergeServiceFiles()

    archiveVersion.set("$version-desktop-shadow")

    destinationDirectory.set(layout.buildDirectory.dir("libs"))

    manifest {
        attributes["Main-Class"] = "br.com.colman.pikframe.MainKt"
    }
}

