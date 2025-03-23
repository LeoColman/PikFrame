package br.com.colman.pikframe

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.ExifSubIFDDirectory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
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
  require(file.extension.lowercase() in listOf("jpg", "jpeg", "png", "bmp", "webp"))
  Box {
    Image(file.readBytes().decodeToImageBitmap(), null)
    ExifData(file)
  }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun BoxScope.ExifData(file: File) {
  val exifDir = ImageMetadataReader.readMetadata(file).getFirstDirectoryOfType(ExifSubIFDDirectory::class.java)
  val date = (exifDir?.dateOriginal ?: Date(file.lastModified())).let {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
    formatter.format(it)
  }
  
  val folderName = file.parentFile!!.name

  ResizingTextContainer { fontSize ->
    Column(Modifier.align(Alignment.BottomStart).padding(16.dp)) {
      date?.let { date ->
        Text(
          text = date,
          modifier = Modifier, fontSize = fontSize,
          style = TextStyle(
            color = Color.White,
            shadow = Shadow(color = Color.Black, offset = Offset(5f, 5f), blurRadius = 2f)
          )
        )
      }

      Text(
        text = folderName,
        modifier = Modifier, fontSize = fontSize,
        style = TextStyle(
          color = Color.White,
          shadow = Shadow(color = Color.Black, offset = Offset(5f, 5f), blurRadius = 2f)
        )
      )
    }
  }
}

@Composable
private fun BoxScope.ResizingTextContainer(content: @Composable (textSize: TextUnit) -> Unit) {
  var containerSize by remember { mutableStateOf(IntSize.Zero) }
  Box(Modifier.matchParentSize().onSizeChanged(onSizeChanged = { containerSize = it })) {
    val textSizeSp = with(LocalDensity.current) {
      (containerSize.width * 0.03f).coerceAtLeast(16f).toSp()
    }
    content(textSizeSp)
  }
}
