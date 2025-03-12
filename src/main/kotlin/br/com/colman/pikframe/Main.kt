package br.com.colman.pikframe

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import org.jetbrains.compose.resources.painterResource
import java.io.File

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App(
    directory: File
) {
    if(!directory.isDirectory) throw IllegalArgumentException("The provided path must be a directory")
    val children = remember(directory) { directory.getAllChildren().filter { it.extension in listOf("jpg", "jpeg", "png") } }
    
    var childToDisplay by remember { mutableStateOf(children.random()) }
    var bitmap by remember(childToDisplay) { mutableStateOf(childToDisplay.readBytes().decodeToImageBitmap())  }
    
    LaunchedEffect(Unit) {
        while(true) {
            delay(2_000)
            childToDisplay = children.random()
            println("Change to $childToDisplay")
        }
    }

    foo(bitmap)
}

@Composable
private fun foo(bitmap: ImageBitmap) {
    Box {
        Image(bitmap, null, modifier = Modifier.fillMaxSize())
    }
}

fun main(args: Array<String>) = application {
    Window(onCloseRequest = ::exitApplication) {
        App(File(args[0]))
    }
}

private fun File.getAllChildren(): List<File> {
    if(this.isDirectory) return listFiles().flatMap { it.getAllChildren() }.distinct()
    return listOf(this)
}
