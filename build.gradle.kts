buildscript {
    extra.apply {
        set("lifecycle_version", "2.6.1")
        set("compose_version", "1.1.0")
    }
    dependencies {
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.9.1")
    }
}
plugins {
    id("com.android.application") version "8.0.2" apply false
    id("com.android.library") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
    id("org.sonarqube") version "4.4.1.3373"
}
