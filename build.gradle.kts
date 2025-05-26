plugins {
    id("java")
    id("io.freefair.lombok") version "8.6"
    application
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

group = "it.unicam.cs.ids"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    testCompileOnly("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")

    // Testing
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("it.unicam.cs.ids.Main")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf(
            "-Xlint:unchecked",
            "-Xlint:deprecation",
            "-parameters"  // Better parameter names in reflection
        ))
    }

    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
            showStandardStreams = true
        }
    }
}