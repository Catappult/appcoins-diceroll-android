package com.appcoins.wallet.convention.plugins

import com.appcoins.wallet.convention.extensions.get
import com.appcoins.wallet.convention.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureDataPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply {
        apply<AndroidLibraryPlugin>()
      }

      dependencies {
        add("implementation", libs["network-retrofit"])
        add("implementation", libs["network-retrofit-converter-gson"])
        add("implementation", libs["network-okhttp"])
        add("implementation", libs["network-okhttp-loginterceptor"])
        add("implementation", libs["google-gson"])

        add("implementation", libs["kotlin-coroutines"])
        add("implementation", libs["kotlin-coroutines-test"])
        add("implementation", libs["test-mockk"])
        add("implementation", libs["test-junit"])
      }
    }
  }
}