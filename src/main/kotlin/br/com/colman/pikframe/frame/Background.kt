package br.com.colman.pikframe.frame

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import br.com.colman.pikframe.pikframe.generated.resources.Res
import br.com.colman.pikframe.pikframe.generated.resources.background_texture
import org.jetbrains.compose.resources.painterResource

@Composable
fun Background(color: Color) {
  Box {
    BackgroundTexture()
    BackgroundColor(color)
  }
}

@Composable
private fun BackgroundTexture() {
  Image(painterResource(Res.drawable.background_texture), null, Modifier.fillMaxSize())
}

@Composable
private fun BackgroundColor(color: Color) {
  Box(Modifier.fillMaxSize().background(color.copy(alpha = 0.5f)))
}
