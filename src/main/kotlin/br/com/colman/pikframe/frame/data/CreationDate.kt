package br.com.colman.pikframe.frame.data

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.ExifSubIFDDirectory
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId.systemDefault
import java.util.Date

val File.creationDate: LocalDateTime
  get() {
    val exifDir = ImageMetadataReader.readMetadata(this).getFirstDirectoryOfType(ExifSubIFDDirectory::class.java)
    return exifDir?.dateOriginal?.toLocalDateTime() ?: exifDir?.dateDigitized?.toLocalDateTime() ?: creationDate()
  }

private fun Date.toLocalDateTime() = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), systemDefault())

private fun File.creationDate(): LocalDateTime {
  val attributes = Files.readAttributes(toPath(), BasicFileAttributes::class.java)
  val fileTime = attributes.creationTime()

  return fileTime.toInstant().atZone(systemDefault()).toLocalDateTime()
}
