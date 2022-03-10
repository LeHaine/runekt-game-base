buildscript {
    repositories {
        maven(url ="https://s01.oss.sonatype.org/content/repositories/snapshots/")
        gradlePluginPortal()
        google()
        mavenCentral()
        mavenLocal()
    }
    dependencies {
        classpath(libs.bundles.plugins)
    }
}

allprojects {
    repositories {
        maven(url ="https://s01.oss.sonatype.org/content/repositories/snapshots/")
        google()
        mavenCentral()
        mavenLocal()
    }

    group = "com.lehaine.game"
    version = "1.0"
}