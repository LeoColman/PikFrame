package br.com.colman.pikframe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import br.com.colman.pikframe.color.getDominantColor
import br.com.colman.pikframe.picselector.RandomPicSelector
import br.com.colman.pikframe.pikframe.generated.resources.Res
import br.com.colman.pikframe.pikframe.generated.resources.texture
import br.com.colman.pikframe.shadow.ShadowedBox
import com.sksamuel.hoplite.ConfigLoaderBuilder
import org.jetbrains.compose.resources.painterResource
import java.io.File


val PikFrameConfig = ConfigLoaderBuilder.default().build().loadConfigOrThrow<Config>("/default-config.yaml")

@Composable
fun PictureFrame(
  image: File,
  matColor: Color = Color.White,
  matPadding: Dp = 8.dp,
) {
  val dominantColor = remember(image) { getDominantColor(image) }
  Box(Modifier.fillMaxSize(), Alignment.Center) {

    Image(
      painterResource(Res.drawable.texture),
      null,
      Modifier.fillMaxSize(),
      contentScale = androidx.compose.ui.layout.ContentScale.Crop
    )

    Box(Modifier.fillMaxSize().background(dominantColor.copy(alpha = 0.5f)))

    ShadowedBox(matColor, matPadding, modifier = Modifier.padding(64.dp)) {
      Pic(image)
    }
  }
}

@Composable
fun App(directory: File) {
  val selector = remember(directory) { RandomPicSelector(directory) }
  val pictureToDisplay by selector.pictures.collectAsState(Cafe)

  PictureFrame(pictureToDisplay)
}

fun main(args: Array<String>) = application {
  Window(onCloseRequest = ::exitApplication) {
    App(File(args[0]))
  }
}
