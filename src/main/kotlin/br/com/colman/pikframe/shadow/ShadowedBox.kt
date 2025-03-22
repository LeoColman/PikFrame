package br.com.colman.pikframe.shadow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShadowedBox(
  matteColor: Color = Color.White,
  mattePadding: Dp = 16.dp,
  shadowOffsetPx: Float = 8f,
  shadowColor: Color = Color.Black.copy(alpha = 0.3f),
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) {
  Box(modifier.drawShadowBehind(shadowColor, shadowOffsetPx).drawMatte(matteColor, mattePadding), Alignment.Center) {
    content()
  }
}

private fun Modifier.drawShadowBehind(
  shadowColor: Color,
  shadowOffsetPx: Float
) = drawBehind {
  val w = size.width
  val h = size.height

  drawRect(shadowColor, Offset(shadowOffsetPx, shadowOffsetPx), Size(w, h))
}

private fun Modifier.drawMatte(matteColor: Color, mattePadding: Dp) = background(matteColor).padding(mattePadding)