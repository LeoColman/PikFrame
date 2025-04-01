package br.com.colman.pikframe.frame.background

import androidx.compose.ui.graphics.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.pow
import kotlin.math.sqrt

private const val SampleStep = 2
private const val K = 10
private const val MaxIterations = 15

fun File.getDominantColor(): Color {
  val image = ImageIO.read(this) ?: error("Could not read image ${this.absolutePath}")
  val pixels = collectSamplePixels(image)

  val centroids = initializeCentroids(pixels)

  repeat(MaxIterations) {
    iterate(pixels, centroids)
  }

  val clusters = pixels.groupBy { px ->
    centroids.minBy { centroid -> colorDistance(px, centroid) }
  }
  
  val (dominantColor, _) = clusters.maxBy { it.value.size }
  
  return dominantColor
}

private fun collectSamplePixels(image: BufferedImage) = buildList {
  for (x in 0 until image.width step SampleStep) {
    for (y in 0 until image.height step SampleStep) {
      val rgb = image.getRGB(x, y)
      val r = (rgb shr 16) and 0xFF
      val g = (rgb shr 8) and 0xFF
      val b = rgb and 0xFF
      add(Color(r, g, b))
    }
  }
}.ifEmpty { error("No pixels found in the image.") }

private fun initializeCentroids(pixels: List<Color>) = MutableList(K) { pixels.random() }

private fun iterate(pixels: List<Color>, centroids: MutableList<Color>) {
  val clusters = pixels.groupBy { px ->
    centroids.minBy { centroid -> colorDistance(px, centroid) }
  }

  centroids.forEachIndexed { index, oldCentroid ->
    val cluster = clusters.getValue(oldCentroid)
    centroids[index] = averageColor(cluster)
  }
}

fun averageColor(cluster: List<Color>): Color {
  val size = cluster.size
  val sumRed = cluster.sumOf { it.red.toDouble() }
  val sumGreen = cluster.sumOf { it.green.toDouble() }
  val sumBlue = cluster.sumOf { it.blue.toDouble() }

  return Color(
    (sumRed / size).coerceIn(0.0, 255.0).toFloat(),
    (sumGreen / size).coerceIn(0.0, 255.0).toFloat(),
    (sumBlue / size).coerceIn(0.0, 255.0).toFloat()
  )
}

private fun colorDistance(c1: Color, c2: Color): Double {
  val distanceRed = (c1.red - c2.red).toDouble().pow(2)
  val distanceGreen = (c1.green - c2.green).toDouble().pow(2)
  val distanceBlue = (c1.blue - c2.blue).toDouble().pow(2)
  return sqrt(distanceRed + distanceGreen + distanceBlue)
}
