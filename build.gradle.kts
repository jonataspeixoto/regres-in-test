plugins {
    id("java")
    id("io.qameta.allure") version "2.11.2"
    id("io.freefair.lombok") version "8.1.0"
    id("io.qameta.allure-download") version "2.11.2"
    id("io.qameta.allure-adapter-base") version "2.11.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

allure {
    version.set("2.23.1")

    adapter {
        frameworks {
            junit5 {
                autoconfigureListeners.set(true)
                adapterVersion.set("2.23.0")
                enabled.set(true)
            }
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.slf4j:slf4j-simple:2.0.7")
    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("io.qameta.allure:allure-junit5:2.22.2")
    testImplementation("io.qameta.allure:allure-junit-platform:2.22.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}


tasks.test {
    useJUnitPlatform()
}
