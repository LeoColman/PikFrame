package br.com.colman.pikframe.color

import androidx.compose.ui.graphics.Color
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.sqrt
import kotlin.random.Random

fun getDominantColor(
  file: File,
  sampleStep: Int = 2,
  k: Int = 10,
  maxIterations: Int = 15
): Color {
  val image = ImageIO.read(file)
  val pixels = mutableListOf<Color>()

  // 1) Collect sample pixels
  for (x in 0 until image.width step sampleStep) {
    for (y in 0 until image.height step sampleStep) {
      val rgb = image.getRGB(x, y)
      val r = (rgb shr 16) and 0xFF
      val g = (rgb shr 8) and 0xFF
      val b = rgb and 0xFF
      pixels.add(Color(r, g, b))
    }
  }
  if (pixels.isEmpty()) throw IllegalStateException("No pixels found in the image.")

  // 2) Randomly initialize k centroids
  val centroids = (1..k).map {
    pixels[Random.nextInt(pixels.size)].toVector()
  }.toMutableList()

  repeat(maxIterations) {
    // Assign each pixel to nearest centroid
    val clusters = List(k) { mutableListOf<Color>() }
    for (px in pixels) {
      val nearestIndex = centroids.indices.minByOrNull { idx ->
        colorDistance(px.toVector(), centroids[idx])
      } ?: 0
      clusters[nearestIndex].add(px)
    }

    // Update each centroid to be the average of its assigned points
    for ((idx, cluster) in clusters.withIndex()) {
      if (cluster.isNotEmpty()) {
        val avg = cluster
          .map { it.toVector() }
          .reduce { acc, v -> acc + v }
          .div(cluster.size.toDouble())
        centroids[idx] = avg
      }
    }
  }

  // 3) After final iteration, pick the largest cluster
  val finalClusters = List(k) { mutableListOf<Color>() }
  for (px in pixels) {
    val nearestIndex = centroids.indices.minByOrNull { idx ->
      colorDistance(px.toVector(), centroids[idx])
    } ?: 0
    finalClusters[nearestIndex].add(px)
  }

  // The dominant cluster is the one with the most points
  val dominantIndex = finalClusters.indices.maxByOrNull { finalClusters[it].size } ?: 0
  val dominantVector = centroids[dominantIndex]

  // 4) Convert centroid to Color
  return Color(
    (dominantVector[0] * 255).coerceIn(0.0, 255.0).toInt(),
    (dominantVector[1] * 255).coerceIn(0.0, 255.0).toInt(),
    (dominantVector[2] * 255).coerceIn(0.0, 255.0).toInt()
  )
}

// Utility extension functions
private fun Color.toVector() = listOf(this.red.toDouble(), this.green.toDouble(), this.blue.toDouble())

private operator fun List<Double>.plus(other: List<Double>) = listOf(
  this[0] + other[0],
  this[1] + other[1],
  this[2] + other[2]
)

private fun List<Double>.div(value: Double) = listOf(
  this[0] / value,
  this[1] / value,
  this[2] / value
)

private fun colorDistance(v1: List<Double>, v2: List<Double>): Double {
  // Euclidean distance in RGB space
  val dr = v1[0] - v2[0]
  val dg = v1[1] - v2[1]
  val db = v1[2] - v2[2]
  return sqrt(dr * dr + dg * dg + db * db)
}
