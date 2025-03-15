package br.com.colman.pikframe

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.io.File

// TODO extract to some central config
private const val EmitPictureDelay = 2_000L

class PicSelector(private val directory: File) {
  
  private val children = directory.getAllChildren().filter { it.extension in listOf("jpg", "jpeg", "png") }
  
  init {
    if(!directory.isDirectory) throw IllegalArgumentException("The provided path must be a directory")
  }
  
  val pictures = flow { 
    while(true) {
      delay(EmitPictureDelay)
      emit(children.random())
    }
  }

  private fun File.getAllChildren(): List<File> {
    if(this.isDirectory) return listFiles().flatMap { it.getAllChildren() }.distinct()
    return listOf(this)
  }
}