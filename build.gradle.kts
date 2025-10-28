// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
}


subprojects {

    plugins.withId("com.android.library") {
        apply(plugin = "maven-publish")
        apply(plugin = "signing")
    }

    afterEvaluate {

        extensions.findByType(org.gradle.api.publish.PublishingExtension::class.java)?.let { publishing ->
            publishing.repositories {

                maven {
                    name = "sonatype"
                    val releasesRepoUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                    val snapshotsRepoUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                    url = uri(if (project.version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)

                    credentials {
                        username = providers.gradleProperty("sonatypeUsername").getOrElse("")
                        password = providers.gradleProperty("sonatypePassword").getOrElse("")
                    }
                }
            }

            val keyId = providers.gradleProperty("signing.keyId").getOrElse("")
            val privateKey = providers.gradleProperty("signing.privateKey").getOrElse("")
            val password = providers.gradleProperty("signing.password").getOrElse("")

            if (keyId.isNotEmpty() && privateKey.isNotEmpty()) {
                extensions.findByType(org.gradle.plugins.signing.SigningExtension::class.java)?.let { signing ->
                    signing.useInMemoryPgpKeys(keyId, privateKey, password)
                    signing.sign(publishing.publications)
                }
            }
        }
    }
}
