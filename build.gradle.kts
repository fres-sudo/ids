plugins {
    id("java")
    id("io.freefair.lombok") version "8.6"
    application
}

group = "it.unicam.cs.ids"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    testCompileOnly("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")
}

application {
    mainClass.set("it.unicam.cs.ids.Main")
}

tasks.test {
    useJUnitPlatform()
}
