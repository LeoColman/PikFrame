package br.com.colman.pikframe.picselector

import br.com.colman.pikframe.PikFrameConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

abstract class PicSelector(private val directory: File) {
  protected val children = directory.getAllChildren().filter { it.extension.lowercase() in listOf("jpg", "jpeg", "png", "bmp", "webp") }

  init {
    if(!directory.isDirectory) throw IllegalArgumentException("The provided path must be a directory")
  }
  
  private fun File.getAllChildren(): List<File> {
    if(this.isDirectory) return listFiles().flatMap { it.getAllChildren() }.distinct()
    return listOf(this)
  }
  
  abstract val pictures: Flow<File>
}
