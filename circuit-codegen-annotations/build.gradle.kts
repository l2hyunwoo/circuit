// Copyright (C) 2022 Slack Technologies, LLC
// SPDX-License-Identifier: Apache-2.0
import org.jetbrains.kotlin.gradle.plugin.PLUGIN_CLASSPATH_CONFIGURATION_NAME

plugins {
  id("com.android.library")
  kotlin("multiplatform")
  alias(libs.plugins.mavenPublish)
}

kotlin {
  // region KMP Targets
  android { publishLibraryVariants("release") }
  jvm()
  // endregion

  sourceSets {
    commonMain { dependencies { api(projects.circuit) } }
    val commonJvm =
      maybeCreate("commonJvm").apply {
        dependencies {
          api(libs.anvil.annotations)
          api(libs.dagger)
        }
      }
    maybeCreate("androidMain").apply { dependsOn(commonJvm) }
    maybeCreate("jvmMain").apply { dependsOn(commonJvm) }
  }
}

android { namespace = "com.slack.circuit.codegen.annotations" }

dependencies { add(PLUGIN_CLASSPATH_CONFIGURATION_NAME, libs.androidx.compose.compiler) }
