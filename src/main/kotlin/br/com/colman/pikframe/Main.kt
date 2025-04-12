package br.com.colman.pikframe

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import br.com.colman.pikframe.frame.PictureFrame
import br.com.colman.pikframe.frame.picture.Cafe
import br.com.colman.pikframe.picselector.RandomPicSelector
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.ExperimentalHoplite


lateinit var PikFrameConfig: Config

@Composable
fun App() {
  val selector = remember(PikFrameConfig.picSelector.pictureDirectory) { RandomPicSelector(PikFrameConfig.picSelector.pictureDirectory) }
  val pictureToDisplay by selector.pictures.collectAsState(Cafe)

  Crossfade(pictureToDisplay, animationSpec = tween(PikFrameConfig.picSelector.fadeDuration.toMillis().toInt())) { 
    PictureFrame(it)
  }
}

fun main(args: Array<String>) = application {
  loadConfig(args)
  Window(::exitApplication, rememberWindowState(WindowPlacement.Fullscreen)) {
    App()
  }
}

@OptIn(ExperimentalHoplite::class)
private fun loadConfig(args: Array<String>) {
  val configPath = args.firstOrNull { it.startsWith("--config") }?.removePrefix("--config=") ?: "/default-config.yaml"
  PikFrameConfig = ConfigLoaderBuilder.default().withExplicitSealedTypes().build().loadConfigOrThrow<Config>(configPath, "/default-config.yaml")
}
