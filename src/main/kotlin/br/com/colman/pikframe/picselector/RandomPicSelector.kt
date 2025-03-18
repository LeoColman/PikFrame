package br.com.colman.pikframe.picselector

import br.com.colman.pikframe.PikFrameConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.io.File

class RandomPicSelector(directory: File) : PicSelector(directory) {
  override val pictures = flow {
    while(true) {
      delay(PikFrameConfig.picSelector.emitPictureDelay.toMillis())
      emit(children.random())
    }
  }
}