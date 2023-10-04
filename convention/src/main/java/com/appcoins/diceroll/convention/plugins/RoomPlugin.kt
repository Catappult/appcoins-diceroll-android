package com.appcoins.diceroll.convention.plugins

import com.appcoins.diceroll.convention.extensions.get
import com.appcoins.diceroll.convention.extensions.libs
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.process.CommandLineArgumentProvider
import java.io.File

class RoomPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply("com.google.devtools.ksp")
      extensions.configure<KspExtension> {
        // The schemas directory contains a schema file for each version of the Room database.
        // This is required to enable Room auto migrations.
        // See https://developer.android.com/reference/kotlin/androidx/room/AutoMigration
        arg(RoomSchemaArgProvider(File(projectDir, "schemas")))
      }
      dependencies {
        add("implementation", libs["androidx.room.runtime"])
        add("implementation", libs["androidx.room.ktx"])
        add("ksp", libs["androidx.room.compiler"])
      }
    }
  }

  class RoomSchemaArgProvider(
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    val schemaDir: File,
  ) : CommandLineArgumentProvider {
    override fun asArguments() = listOf("room.schemaLocation=${schemaDir.path}")
  }
}