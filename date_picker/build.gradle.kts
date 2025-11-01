import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // Add the maven-publish plugin
    `maven-publish`
//    id("io.github.gradle-nexus.publish-plugin")
}

android {
    namespace = "com.amirmousavi_dev.date_picker"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}


afterEvaluate {

    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "io.github.amirmousavi-dev"
                artifactId = "persian-date-picker"
                version = "1.0.0"

                from(components["release"])

                pom {
                    name.set("Persian Date Picker")
                    description.set("A simple and customizable Persian (Jalali) date picker for Jetpack Compose.")
                    url.set("https://github.com/AmirMousavi-Dev/Persian_Date_Picker")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("AmirMousavi-Dev")
                            name.set("Amir Mousavi")
                            email.set("ah.mousavi06@gmail.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:github.com/AmirMousavi-Dev/Persian_Date_Picker.git")
                        developerConnection.set("scm:git:ssh://github.com/AmirMousavi-Dev/Persian_Date_Picker.git")
                        url.set("https://github.com/AmirMousavi-Dev/Persian_Date_Picker/tree/master")
                    }
                }
            }
        }

        repositories {
            val localProperties = Properties().apply {
                load(rootProject.file("local.properties").inputStream())
            }
            val ossrhUsername = localProperties.getProperty("ossrhUsername")
            val ossrhPassword = localProperties.getProperty("ossrhPassword")
            maven {
                name = "centralPortal"
                url = uri("https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/")
                credentials {
                    username = ossrhUsername
                    password = ossrhPassword
                    println("OSSRH USERNAME = $ossrhUsername")

                }
            }
        }

    }
}
