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

  val (red, green, blue) = centroids.maxBy<List<Double>, Int> { it.size }

  return Color(
    (red * 255).coerceIn(0.0, 255.0).toInt(),
    (green * 255).coerceIn(0.0, 255.0).toInt(),
    (blue * 255).coerceIn(0.0, 255.0).toInt()
  )
}

private fun collectSamplePixels(image: BufferedImage): List<Color> {
  val pixels = mutableListOf<Color>()
  for (x in 0 until image.width step SampleStep) {
    for (y in 0 until image.height step SampleStep) {
      val rgb = image.getRGB(x, y)
      val r = (rgb shr 16) and 0xFF
      val g = (rgb shr 8) and 0xFF
      val b = rgb and 0xFF
      pixels += Color(r, g, b)
    }
  }
  return pixels.ifEmpty { error("No pixels found in the image.") }
}

private fun initializeCentroids(pixels: List<Color>) = MutableList(K) { pixels.random().toVector() }

private fun iterate(pixels: List<Color>, centroids: MutableList<List<Double>>) {
  val clusters = pixels.groupBy { px ->
    centroids.minBy { centroid -> colorDistance(px.toVector(), centroid) }
  }

  centroids.forEachIndexed { index, oldCentroid ->
    val cluster = clusters.getValue(oldCentroid)
    centroids[index] = cluster.map(Color::toVector).reduce { acc, v -> acc + v }.div(cluster.size)
  }
}


private fun Color.toVector() = listOf(red.toDouble(), green.toDouble(), blue.toDouble())

private operator fun List<Double>.plus(other: List<Double>) = listOf(
  this[0] + other[0],
  this[1] + other[1],
  this[2] + other[2]
)

private fun List<Double>.div(value: Int) = listOf(
  this[0] / value,
  this[1] / value,
  this[2] / value
)

private fun colorDistance(vector1: List<Double>, vector2: List<Double>): Double {
  val (red1, green1, blue1) = vector1
  val (red2, green2, blue2) = vector2

  val distanceRedSquared = (red1 - red2).pow(2)
  val distanceGreenSquared = (green1 - green2).pow(2)
  val distanceBlueSquared = (blue1 - blue2).pow(2)

  return sqrt(distanceRedSquared + distanceGreenSquared + distanceBlueSquared)
}
