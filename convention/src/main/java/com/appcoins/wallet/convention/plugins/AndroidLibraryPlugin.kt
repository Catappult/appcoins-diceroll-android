package com.appcoins.wallet.convention.plugins

import com.android.build.gradle.LibraryExtension
import com.appcoins.wallet.convention.Config
import com.appcoins.wallet.convention.extensions.configureAndroidAndKotlin
import com.appcoins.wallet.convention.extensions.get
import com.appcoins.wallet.convention.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.library")
        apply("kotlin-android")
        apply("kotlin-kapt")
        apply<HiltPlugin>()
      }

      extensions.configure<LibraryExtension> {
        configureAndroidAndKotlin(this)
        buildToolsVersion = Config.android.buildToolsVersion
        defaultConfig.targetSdk = Config.android.targetSdk

        flavorDimensions.add(Config.versionFlavorDimension)
        productFlavors {
          create(Config.googlePlayBillingVersion) {
            dimension = Config.versionFlavorDimension
          }
          create(Config.appcoinsBillingVersion) {
            dimension = Config.versionFlavorDimension
          }
        }
      }

      dependencies {
        add("implementation", libs["kotlin.stdlib"])
      }
    }
  }
}