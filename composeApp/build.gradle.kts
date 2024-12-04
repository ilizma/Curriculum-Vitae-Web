import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import java.util.regex.Pattern

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
    }
}

project.extra.set("buildkonfig.flavor", currentBuildVariant())

private fun Project.currentBuildVariant(): String {
    val variants = setOf("lrhr", "ilizma")
    return System.getenv()["VARIANT"]
        .toString()
        .takeIf { it in variants } ?: "lrhr"
}

buildkonfig {
    packageName = "com.ilizma.curriculumvitaeapp"

    defaultConfigs {
        buildConfigField(STRING, "variant", "lrhr")
        buildConfigField(
            STRING,
            "PLAY_STORE_URL",
            "https://play.google.com/apps/test/RQ-7x0b3lME/ahAO29uNRBjCM8-W5gk08eSvmDzoIiFKjHg6muf6IeLNhb7lVWOsCNQixcUZBokEjo4mRU40b6uceRc6mCIFvg353A"
        )
    }

    defaultConfigs("lrhr") {
        buildConfigField(STRING, "variant", "lrhr")
        buildConfigField(
            STRING,
            "PLAY_STORE_URL",
            "https://play.google.com/apps/test/RQ-7x0b3lME/ahAO29uNRBjCM8-W5gk08eSvmDzoIiFKjHg6muf6IeLNhb7lVWOsCNQixcUZBokEjo4mRU40b6uceRc6mCIFvg353A"
        )
    }
    defaultConfigs("ilizma") {
        buildConfigField(STRING, "variant", "ilizma")
        buildConfigField(
            STRING,
            "PLAY_STORE_URL",
            ""
        )
    }
}


