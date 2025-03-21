package br.com.colman.pikframe.picselector

import br.com.colman.pikframe.PikFrameConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.io.File
import kotlin.random.Random

class RandomPicSelector(private val directory: File) {
  private var children = directory.getAllImages().toMutableList()

  init {
    if (!directory.isDirectory) throw IllegalArgumentException("The provided path must be a directory")
  }

  val pictures = flow {
    while (true) {
      delay(PikFrameConfig.picSelector.emitPictureDelay.toMillis())
      emit(children.removeAt(Random.nextInt(children.size)))
      if (children.isEmpty()) children = directory.getAllImages().toMutableList()
    }
  }
}

private fun File.getAllImages(): List<File> {
  if (this.isDirectory) return listFiles().flatMap { it.getAllImages() }.distinct()
  return listOf(this).filter(File::isImage)
}

private fun File.isImage() = extension.lowercase() in listOf("jpg", "jpeg", "png", "bmp", "webp")
