plugins {
    alias(libs.plugins.neogradle)
}

base {
    archivesName = "${projectId}-neoforge-${minecraftVersion}"
}

java.toolchain.languageVersion = JavaLanguageVersion.of(javaVersion)
java.sourceCompatibility = JavaVersion.toVersion(javaVersion)
java.targetCompatibility = JavaVersion.toVersion(javaVersion)

dependencies {
    compileOnly("net.neoforged:neoforge:${apiVersion}")
    compileOnly(project(":api"))
    compileOnly(project(":common"))
    compileOnly(project(":loader"))
    compileOnly(project(":modapi:muxins"))
    compileOnly(project(":vanilla:vanilla-1.20"))
}