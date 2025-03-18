package br.com.colman.pikframe

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import br.com.colman.pikframe.picselector.PicSelector
import br.com.colman.pikframe.picselector.SequentialPicSelector
import com.sksamuel.hoplite.ConfigLoaderBuilder
import java.io.File

val PikFrameConfig = ConfigLoaderBuilder.default().build().loadConfigOrThrow<Config>("/default-config.yaml")

@Composable
fun App(directory: File) {
  val selector = remember(directory) { SequentialPicSelector(directory) }
  val pictureToDisplay by selector.pictures.collectAsState(Cafe)

  Pic(pictureToDisplay)
}

fun main(args: Array<String>) = application {
  Window(onCloseRequest = ::exitApplication) {
    App(File(args[0]))
  }
}
