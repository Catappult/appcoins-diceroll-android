package com.appcoins.diceroll.convention.plugins

import com.android.build.gradle.LibraryExtension
import com.appcoins.diceroll.convention.extensions.configureAndroidCompose
import com.appcoins.diceroll.convention.extensions.get
import com.appcoins.diceroll.convention.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureUiPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply {
        apply<AndroidLibraryPlugin>()
        val extension = extensions.getByType<LibraryExtension>()
        configureAndroidCompose(extension)
      }

      dependencies {
        add("implementation", libs["androidx-compose-lifecycle-viewModel"])
        add("implementation", libs["androidx-compose-lifecycle-runtime"])
        add("implementation", libs["androidx-compose-ui-util"])
        add("implementation", libs["androidx-compose-ui-ui"])
        add("implementation", libs["androidx-compose-ui-tooling-preview"])
        add("implementation", libs["androidx-compose-ui-tooling"])
        add("implementation", libs["androidx-compose-ui-test"])
        add("implementation", libs["androidx-compose-foundation-layout"])
        add("implementation", libs["androidx-compose-activity"])
        add("implementation", libs["androidx-compose-runtime-tracing"])
        add("implementation", libs["androidx-compose-constraintlayout"])
        add("implementation", libs["coil-kt"])
        add("implementation", libs["coil-kt-compose"])
        add("implementation", libs["coil-kt-svg"])
      }
    }
  }
}