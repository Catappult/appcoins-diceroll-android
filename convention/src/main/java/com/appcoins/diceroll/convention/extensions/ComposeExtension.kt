package com.appcoins.diceroll.convention.extensions

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(
  commonExtension: CommonExtension<*, *, *, *, *>,
) {
  commonExtension.apply {
    buildFeatures {
      compose = true
    }

    composeOptions {
      kotlinCompilerExtensionVersion = version("androidx-compose-compiler")
    }

    dependencies {
      implementation("androidx-compose-runtime")
      implementation("androidx-compose-compiler")
      implementation("androidx-compose-foundation")
      implementation("androidx-compose-accompanist-systemuicontroller")
      implementation("androidx-compose-material3")
    }
  }
}