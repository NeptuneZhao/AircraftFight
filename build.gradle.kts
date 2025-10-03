
import java.util.*


plugins {
    id("java")
}

group = "com.halfcooler"

val versionFile = file("version.properties")
val props = Properties().apply { load(versionFile.inputStream()) }
version = "${props["major"]}.${props["minor"]}.${props["patch"]}.${props["build"]}"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
