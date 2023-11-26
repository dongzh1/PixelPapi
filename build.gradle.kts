plugins {
    java
    id("com.github.johnrengelman.shadow") version ("7.1.2")
    id("com.xbaimiao.easylib") version ("1.1.1")
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    kotlin("jvm") version "1.7.10"
}

group = "com.xbaimiao.template"
version = "1.0.0"

easylib {
    version = "3.1.2"
    nbt = true
    hikariCP = false
    ormlite = false
    userMinecraftLib = true
    minecraftVersion = "1.12.2"
    isPaper = false
}

bukkit {
    name = project.name
    main = "${project.group}.${project.name}"
    version = project.version.toString()
    authors = listOf(
        "xbaimiao"
    )
    apiVersion = "1.13"
    foliaSupported = true
}

repositories {
    mavenLocal()
    mavenCentral()
}

subprojects {
    apply {
        plugin("java")
        plugin("kotlin")
    }

    dependencies {
        compileOnly(kotlin("stdlib-jdk8"))
        compileOnly(fileTree("../libs"))
    }

}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":project-api"))
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
//    implementation("com.xbaimiao.ktor:ktor-plugins-bukkit:1.0.8")
//    implementation("redis.clients:jedis:5.0.1")
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.shadowJar {
    dependencies {
        exclude(dependency("org.slf4j:"))
        exclude(dependency("org.jetbrains:annotations:"))
        exclude(dependency("com.google.code.gson:gson:"))
//            exclude(dependency("org.jetbrains.kotlin:"))
//            exclude(dependency("org.jetbrains:"))
    }

    exclude("LICENSE")
    exclude("META-INF/*.SF")
    archiveClassifier.set("")

    relocate("com.xbaimiao.easylib", "${project.group}.shadow.easylib")
    relocate("com.zaxxer.hikari", "${project.group}.shadow.hikari")
    relocate("com.j256.ormlite", "${project.group}.shadow.ormlite")
    relocate("de.tr7zw.changeme.nbtapi", "${project.group}.shadow.itemnbtapi")
    relocate("kotlin", "${project.group}.shadow.kotlin")
    relocate("kotlinx", "${project.group}.shadow.kotlinx")
    relocate("redis", "${project.group}.shadow.redis")
    relocate("com.xbaimiao.ktor", "${project.group}.shadow.ktor")
    minimize()
}
