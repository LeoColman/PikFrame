package br.com.colman.pikframe.picselector

import br.com.colman.pikframe.PikFrameConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.io.File
import kotlin.random.Random

class RandomPicSelector(directory: File) : PicSelector(directory) {
  private var values = children.toMutableList()
  
  
  override val pictures = flow { 
    while (true) {
      delay(PikFrameConfig.picSelector.emitPictureDelay.toMillis())
      emit(values.removeAt(Random.nextInt(values.size)))
      if(values.isEmpty()) values = children.toMutableList()
    }
  }
}