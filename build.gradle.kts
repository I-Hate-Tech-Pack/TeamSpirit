import java.util.Date

plugins {
    `java-library`
    `maven-publish`
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.18.2")
        jvmArgs("-Xms2G", "-Xmx2G")
    }

    processResources {
        val props = mapOf("version" to version, "description" to project.description)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }

    register<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        manifest {
            attributes(
                "Specification-Title" to "TeamSpirit",
                "Specification-Vendor" to "TeamSpirit",
                "Specification-Version" to "1",
                "Implementation-Title" to "TeamSpirit",
                "Implementation-Version" to "${version}",
                "Implementation-Vendor" to "TeamSpirit",
                "Implementation-Timestamp" to Date().format("yyyy-MM-dd'T'HH:mm:ssZ")
            )
        }
        from(sourceSets.main.get().allJava)
    }
}

publishing {
    publications {
        create("mavenJava", MavenPublication::class.java) {
            artifact(tasks.named<Jar>("jar"))
            artifact(tasks.named<Jar>("sourcesJar"))
        }
    }
    repositories {
        maven {
            name = "HowXu"
            url = uri("https://maven.howxu.cn/")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
}
