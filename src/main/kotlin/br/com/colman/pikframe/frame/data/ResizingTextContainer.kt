package br.com.colman.pikframe.frame.data

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit

@Composable
fun ResizingTextContainer(content: @Composable (TextUnit) -> Unit) {
  var containerSize by remember { mutableStateOf(IntSize.Zero) }
  
  Box(Modifier.fillMaxSize().onSizeChanged { containerSize = it }) {
    
    val textSizeSp = with(LocalDensity.current) {
      (containerSize.width * 0.02f).coerceAtLeast(16f).toSp()
    }
    content(textSizeSp)
  }
}