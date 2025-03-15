package br.com.colman.pikframe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File

@Composable
fun App(directory: File) {
  val selector = PicSelector(directory)
  val pictureToDisplay by selector.pictures.collectAsState(Cafe)

  Pic(pictureToDisplay)
}

fun main(args: Array<String>) = application {
  Window(onCloseRequest = ::exitApplication) {
    App(File(args[0]))
  }
}
