package br.com.colman.pikframe.frame.data

import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun BoxWithConstraintsScope.calculateDataFontSize() = with(LocalDensity.current) {
  (maxWidth * 0.02f).coerceAtLeast(16.dp).toSp()
}