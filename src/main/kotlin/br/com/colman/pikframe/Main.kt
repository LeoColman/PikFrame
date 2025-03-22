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
import br.com.colman.pikframe.picselector.RandomPicSelector
import br.com.colman.pikframe.pikframe.generated.resources.Res
import br.com.colman.pikframe.pikframe.generated.resources.texture
import com.sksamuel.hoplite.ConfigLoaderBuilder
import org.jetbrains.compose.resources.painterResource
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


val PikFrameConfig = ConfigLoaderBuilder.default().build().loadConfigOrThrow<Config>("/default-config.yaml")

@Composable
fun PictureFrame(
  image: File,
  matColor: Color = Color.White,
  matPadding: Dp = 32.dp,
) {
  val dominantColor = remember(image) { getDominantColor(image) }
  Box(Modifier.fillMaxSize(), Alignment.Center) {
    
    Image(painterResource(Res.drawable.texture), null, Modifier.fillMaxSize(), contentScale = androidx.compose.ui.layout.ContentScale.Crop)

    Box(Modifier.fillMaxSize().background(dominantColor.copy(alpha = 0.6f)))

    Box(Modifier.padding(matPadding).background(matColor).padding(matPadding)) {
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


fun getDominantColor(file: File, sampleStep: Int = 5): Color {
  val image: BufferedImage = ImageIO.read(file)
  val colorMap = mutableMapOf<Int, Int>()

  for (x in 0 until image.width step sampleStep) {
    for (y in 0 until image.height step sampleStep) {
      val rgb = image.getRGB(x, y)

      // Reduce color depth to group similar colors
      val r = (rgb shr 16) and 0xFF shr 3
      val g = (rgb shr 8) and 0xFF shr 3
      val b = rgb and 0xFF shr 3

      val quantized = (r shl 10) or (g shl 5) or b
      colorMap[quantized] = colorMap.getOrDefault(quantized, 0) + 1
    }
  }

  val dominant = colorMap.maxByOrNull { it.value }?.key ?: 0
  val r = (dominant shr 10) and 0x1F shl 3
  val g = (dominant shr 5) and 0x1F shl 3
  val b = dominant and 0x1F shl 3

  return Color(r.coerceIn(0, 255), g.coerceIn(0, 255), b.coerceIn(0, 255))
}