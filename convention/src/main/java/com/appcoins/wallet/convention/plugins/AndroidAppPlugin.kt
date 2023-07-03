package com.appcoins.wallet.convention.plugins

import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.appcoins.wallet.convention.Config
import com.appcoins.wallet.convention.extensions.BuildConfigType
import com.appcoins.wallet.convention.extensions.buildConfigFields
import com.appcoins.wallet.convention.extensions.configureAndroidAndKotlin
import com.appcoins.wallet.convention.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import kotlin.collections.set

class AndroidAppPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.application")
        apply("kotlin-android")
        apply("kotlin-parcelize")
        apply("kotlin-kapt")
        apply<HiltPlugin>()
        apply<RoomPlugin>()
      }

      extensions.configure<BaseAppModuleExtension> {
        configureAndroidAndKotlin(this)
        buildToolsVersion = Config.android.buildToolsVersion
        ndkVersion = Config.android.ndkVersion
        defaultConfig {
          targetSdk = Config.android.targetSdk
          multiDexEnabled = true
          javaCompileOptions {
            annotationProcessorOptions {
              annotationProcessorOptions.arguments["room.schemaLocation"] =
                "${project.projectDir}/schemas"
            }
          }
        }

        buildTypes {
          debug {
            isMinifyEnabled = false
            enableUnitTestCoverage = true
            applicationIdSuffix = ".dev"
            versionNameSuffix = ".dev"
            buildConfigFields(project, BuildConfigType.DEBUG)
          }

          release {
            isMinifyEnabled = true
            buildConfigFields(project, BuildConfigType.RELEASE)
          }
        }

        applicationVariants.all {
          val sep = "_"
          val buildType = buildType.name
          val versionName = versionName
          val versionCode = versionCode
          val fileName = "AppCoins-DiceRoll_v$versionName$sep$versionCode$sep$buildType.apk"
          outputs.all {
            (this as BaseVariantOutputImpl).outputFileName = fileName
          }
        }

        buildFeatures {
          buildConfig = true
          viewBinding {
            enable = true
          }
          composeOptions {
            kotlinCompilerExtensionVersion = "1.4.4"
          }  // "1.1.0"
          compose = true
        }
      }
    }
  }
}


