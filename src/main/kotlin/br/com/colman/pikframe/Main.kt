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
import androidx.compose.ui.window.application
import br.com.colman.pikframe.utils.getDominantColor
import br.com.colman.pikframe.frame.Background
import br.com.colman.pikframe.frame.ExifData
import br.com.colman.pikframe.frame.Matte
import br.com.colman.pikframe.picselector.RandomPicSelector
import com.sksamuel.hoplite.ConfigLoaderBuilder
import java.io.File


val PikFrameConfig = ConfigLoaderBuilder.default().build().loadConfigOrThrow<Config>("/default-config.yaml")

@Composable
fun PictureFrame(
  image: File,
  matColor: Color = Color.White,
  matPadding: Dp = 8.dp,
) {
  val dominantColor = remember(image) { image.getDominantColor() }
  Box(Modifier.fillMaxSize(), Alignment.Center) {

    Background(image, dominantColor)
    Matte(matColor, matPadding) {
      Pic(image)
    }
    ExifData(image)
  }
}

@Composable
fun App(directory: File) {
  val selector = remember(directory) { RandomPicSelector(directory) }
  val pictureToDisplay by selector.pictures.collectAsState(Cafe)

  Crossfade(pictureToDisplay, animationSpec = tween(PikFrameConfig.picSelector.fadeDuration.toMillis().toInt())) { 
    PictureFrame(it)
  }
}

fun main(args: Array<String>) = application {
  Window(onCloseRequest = ::exitApplication) {
    App(File(args[0]))
  }
}
