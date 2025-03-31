package br.com.colman.pikframe.frame

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.colman.pikframe.frame.background.Background
import br.com.colman.pikframe.frame.data.ExifData
import br.com.colman.pikframe.frame.picture.Matte
import br.com.colman.pikframe.frame.picture.Pic
import java.io.File

@Composable
fun PictureFrame(image: File) {
  Box(Modifier.fillMaxSize(), Alignment.Center) {

    Background(image)
    Matte(Color.White, 8.dp) {
      Pic(image)
    }
    ExifData(image)
  }
}