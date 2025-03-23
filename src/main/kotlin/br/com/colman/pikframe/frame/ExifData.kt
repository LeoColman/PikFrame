package br.com.colman.pikframe.frame

import androidx.compose.foundation.layout.BoxScope
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
import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.ExifSubIFDDirectory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId.systemDefault
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Date

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BoxScope.ExifData(file: File) {
  val date = file.readCreationDate().format(ofPattern("LLL, yyyy"))
  val folderName = file.parentFile!!.name
  
  ResizingTextContainer { fontSize ->
    Text(
      text = "$date - $folderName",
      modifier = Modifier.padding(16.dp).align(Alignment.BottomStart),
      fontSize = fontSize,
      style = TextStyle(
        color = Color.White,
        shadow = Shadow(color = Color.Black, offset = Offset(4f, 4f), blurRadius = 2f)
      )
    )
  }
}

private fun File.readCreationDate(): LocalDateTime {
  val exifDir = ImageMetadataReader.readMetadata(this).getFirstDirectoryOfType(ExifSubIFDDirectory::class.java)
  return exifDir?.dateOriginal?.toLocalDateTime() ?: creationDate()
}

private fun Date.toLocalDateTime() = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), systemDefault())

private fun File.creationDate(): LocalDateTime {
  val attributes = Files.readAttributes(toPath(), BasicFileAttributes::class.java)
  val fileTime = attributes.creationTime()
  
  return fileTime.toInstant().atZone(systemDefault()).toLocalDateTime()
}