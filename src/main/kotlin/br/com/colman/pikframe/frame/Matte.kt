package br.com.colman.pikframe.frame

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.colman.pikframe.shadow.ShadowedBox

@Composable
fun Matte(
  matteColor: Color,
  mattePadding: Dp = 8.dp,
  content: @Composable () -> Unit
) {
  ShadowedBox(matteColor, mattePadding, modifier = Modifier.padding(64.dp)) { content() }
}