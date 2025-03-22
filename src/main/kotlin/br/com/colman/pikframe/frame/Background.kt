package br.com.colman.pikframe.frame

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import br.com.colman.pikframe.pikframe.generated.resources.Res
import br.com.colman.pikframe.pikframe.generated.resources.texture
import org.jetbrains.compose.resources.painterResource

@Composable
fun Background(dominantColor: Color) {
  BackgroundTexture()
  BackgroundColor(dominantColor)
}

@Composable
private fun BackgroundColor(dominantColor: Color) {
  Box(Modifier.fillMaxSize().background(dominantColor.copy(alpha = 0.5f)))
}

@Composable
private fun BackgroundTexture() {
  Image(
    painterResource(Res.drawable.texture),
    null,
    Modifier.fillMaxSize(),
    contentScale = ContentScale.Crop
  )
}