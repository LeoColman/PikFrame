package br.com.colman.pikframe.frame.data

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import br.com.colman.pikframe.PikFrameConfig
import java.io.File
import java.time.format.DateTimeFormatter.ofPattern

private val dateFormat = PikFrameConfig.picSelector.dateFormat
private val locale = PikFrameConfig.picSelector.locale

@Composable
fun ExifData(file: File) {
  val creationDate = file.creationDateTime.format(ofPattern(dateFormat, locale))
  val folderName = file.parentFile!!.name

  BoxWithConstraints(Modifier.fillMaxSize()) {
    Text(
      text = "$creationDate - $folderName",
      modifier = Modifier.padding(32.dp).align(Alignment.BottomStart),
      fontSize = calculateDataFontSize(),
      style = TextStyle(
        color = Color.White,
        shadow = Shadow(color = Color.Black, offset = Offset(3f, 3f), blurRadius = 1f)
      )
    )
  }
}
