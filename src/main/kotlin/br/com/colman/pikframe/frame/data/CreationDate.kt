package br.com.colman.pikframe.frame.data

import com.drew.imaging.ImageMetadataReader.readMetadata
import com.drew.metadata.exif.ExifSubIFDDirectory
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId.systemDefault
import java.util.Date

val File.creationDateTime: LocalDateTime
  get() = exifDateTime() ?: fileSystemCreationDateTime()

private fun File.exifDateTime() :LocalDateTime? {
  val exifDir = readMetadata(this).getFirstDirectoryOfType(ExifSubIFDDirectory::class.java)

  return (exifDir?.dateOriginal ?: exifDir?.dateDigitized)?.toLocalDateTime()
}

private fun File.fileSystemCreationDateTime(): LocalDateTime {
  val attributes = Files.readAttributes(toPath(), BasicFileAttributes::class.java)

  return attributes.creationTime().toInstant().atZone(systemDefault()).toLocalDateTime()
}

private fun Date.toLocalDateTime() = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), systemDefault())

