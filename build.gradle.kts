buildscript {
    repositories {
        mavenLocal()
        maven(url ="https://s01.oss.sonatype.org/content/repositories/snapshots/")
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.bundles.plugins)
    }
}

allprojects {
    repositories {
        mavenLocal()
        maven(url ="https://s01.oss.sonatype.org/content/repositories/snapshots/")
        google()
        mavenCentral()
    }

    group = "com.lehaine.game"
    version = "1.0"
}

plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        yarnLockMismatchReport = org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport.WARNING
    }
}
