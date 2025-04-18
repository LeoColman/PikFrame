package br.com.colman.pikframe.picselector

import java.io.File
import java.time.Duration
import java.util.Locale

data class PicSelectorConfig(
  val displayDuration: Duration,
  val fadeDuration: Duration,
  val dateFormat: String,
  val locale: Locale,
  val pictureDirectory: File,
)