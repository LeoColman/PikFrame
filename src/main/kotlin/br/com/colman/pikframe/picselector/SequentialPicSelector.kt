package br.com.colman.pikframe.picselector

import br.com.colman.pikframe.PikFrameConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.io.File

class SequentialPicSelector(directory: File) :  PicSelector(directory) {
  override val pictures = flow {
    var currentIndex = 0
    while (true) {
      delay(PikFrameConfig.picSelector.emitPictureDelay.toMillis())
      emit(children[currentIndex])
      val nextIndex = if (currentIndex == children.lastIndex) 0 else (currentIndex + 1)
      currentIndex = nextIndex
    }
  }
}
