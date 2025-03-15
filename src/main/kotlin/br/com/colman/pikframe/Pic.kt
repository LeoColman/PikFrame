package br.com.colman.pikframe

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import java.io.File
import kotlin.io.path.createTempFile
import kotlin.io.path.writeBytes

private val CafeStream = object {}::class.java.classLoader.getResourceAsStream("composeResources/drawable/cafe.png")!!

val Cafe = createTempFile("temp", ".png").apply { writeBytes(CafeStream.readBytes()) }.toFile()

@Composable
@Preview
fun PicPreview() {
  Pic(Cafe)
}


@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun Pic(file: File) {
  require(file.extension in listOf("jpg", "jpeg", "png"))
  Image(file.readBytes().decodeToImageBitmap(), null, Modifier.fillMaxSize())
}
