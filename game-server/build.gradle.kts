plugins {
    java
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testCompile("junit", "junit", "4.12")
    testCompile("org.mockito:mockito-core:2.28.2")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}