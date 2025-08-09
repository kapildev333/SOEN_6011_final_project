plugins {
    id("java")
    id("application")
    id("checkstyle")
    id("pmd")
}

group = "org.example"
version = "1.0.0" // Semantic Versioning

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
}

application {
    mainClass.set("org.example.PowerCalculatorGUI")
}

tasks.test {
    useJUnitPlatform()
}

// Checkstyle configuration
checkstyle {
    toolVersion = "10.12.5"
    configFile = file("config/checkstyle/checkstyle.xml")
}

// PMD configuration
pmd {
    toolVersion = "6.55.0"
    rulesMinimumPriority = 5
}