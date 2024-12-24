import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    alias(libs.plugins.shadow)
    alias(libs.plugins.unimined)
}

base {
    archivesName = "${projectId}-forge-${minecraftVersion}"
}

java.toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
java.sourceCompatibility = JavaVersion.toVersion(javaVersion)
java.targetCompatibility = JavaVersion.toVersion(javaVersion)

unimined.minecraft(sourceSets.main.get()) {
    version(minecraftVersion)
    minecraftForge {
        loader(apiVersion)
        mixinConfig("taterlib.mixins.v1_18.forge.json")
    }
    mappings {
        mojmap()
    }
    defaultRemapJar = false
    remap(tasks.shadowJar.get()) {
        prodNamespace("searge")
        mixinRemap {
            disableRefmap()
        }
    }
}

dependencies {
    compileOnly(project(":api"))
    compileOnly(project(":common"))
    compileOnly(project(":tooling:loader"))
    compileOnly(project(":modapi:conditional-mixins"))
    compileOnly(project(":forge:forge-utils-modern"))
    compileOnly(project(":forge:forge-1.18.2"))
    implementation(project(":vanilla:vanilla-1.18"))
}

tasks.named<ShadowJar>("shadowJar") {
    relocate("dev.neuralnexus.taterlib.mixin.v1_18.vanilla", "dev.neuralnexus.taterlib.mixin.v1_18.vanilla.forge")
    relocate("dev.neuralnexus.taterlib.v1_18.vanilla", "dev.neuralnexus.taterlib.v1_18.vanilla.forge")
}

tasks.build {
    dependsOn(tasks.named("remapShadowJar"))
}
