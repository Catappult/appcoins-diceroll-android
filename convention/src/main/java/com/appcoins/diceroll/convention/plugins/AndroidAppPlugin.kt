package com.appcoins.diceroll.convention.plugins

import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.appcoins.diceroll.convention.Config
import com.appcoins.diceroll.convention.extensions.configureAndroidAndKotlin
import com.appcoins.diceroll.convention.extensions.version
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidAppPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("com.android.application")
        apply("kotlin-android")
        apply("kotlin-parcelize")
        apply<HiltPlugin>()
      }

      extensions.configure<BaseAppModuleExtension> {
        configureAndroidAndKotlin(this)
        ndkVersion = Config.android.ndkVersion
        defaultConfig {
          targetSdk = Config.android.targetSdk
          multiDexEnabled = true
        }

        buildTypes {
          debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".dev"
            versionNameSuffix = ".dev"
          }

          release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
          }
        }

        signingConfigs {
          register("release") {
            storeFile = project.property("BDS_WALLET_STORE_FILE")?.let { file(it) }
            storePassword = project.property("BDS_WALLET_STORE_PASSWORD").toString()
            keyAlias = project.property("BDS_WALLET_KEY_ALIAS").toString()
            keyPassword = project.property("BDS_WALLET_KEY_PASSWORD").toString()
          }
        }

        flavorDimensions.add(Config.versionFlavorDimension)
        productFlavors {
          create(Config.googlePlayBillingVersion) {
            dimension = Config.versionFlavorDimension
            applicationIdSuffix = ".gp"
            versionNameSuffix = ".gp"
          }
          create(Config.appcoinsBillingVersion) {
            dimension = Config.versionFlavorDimension
          }
        }

        applicationVariants.all {
          val sep = "_"
          val buildType = buildType.name
          val flavor = flavorName
          val versionName = versionName
          val versionCode = versionCode
          val fileName = "DiceRoll_v$versionName$sep$versionCode$sep$flavor$sep$buildType.apk"
          outputs.all {
            (this as BaseVariantOutputImpl).outputFileName = fileName
          }
        }

        buildFeatures {
          buildConfig = true
          composeOptions {
            kotlinCompilerExtensionVersion = version("androidx-compose-compiler")
          }
          compose = true
        }
      }
    }
  }
}


