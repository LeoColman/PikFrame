package br.com.colman.pikframe

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import br.com.colman.pikframe.frame.PictureFrame
import br.com.colman.pikframe.frame.picture.Cafe
import br.com.colman.pikframe.frame.background.Background
import br.com.colman.pikframe.frame.data.ExifData
import br.com.colman.pikframe.frame.picture.Matte
import br.com.colman.pikframe.frame.picture.Pic
import br.com.colman.pikframe.picselector.RandomPicSelector
import com.sksamuel.hoplite.ConfigLoaderBuilder
import java.io.File


val PikFrameConfig = ConfigLoaderBuilder.default().build().loadConfigOrThrow<Config>("/default-config.yaml")

@Composable
fun App(directory: File) {
  val selector = remember(directory) { RandomPicSelector(directory) }
  val pictureToDisplay by selector.pictures.collectAsState(Cafe)

  Crossfade(pictureToDisplay, animationSpec = tween(PikFrameConfig.picSelector.fadeDuration.toMillis().toInt())) { 
    PictureFrame(it)
  }
}

fun main(args: Array<String>) = application {
  Window(::exitApplication, rememberWindowState(WindowPlacement.Fullscreen)) {
    App(File(args[0]))
  }
}
