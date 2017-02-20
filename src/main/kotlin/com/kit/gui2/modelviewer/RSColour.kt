package com.kit.gui2.modelviewer

import javafx.scene.image.Image
import javafx.scene.image.WritableImage

/**
 * Date: 17/02/2017
 * Time: 15:49
 * @author Matt Collinge
 */
object RSColour {

    var colourTable: IntArray = intArrayOf()
        get
        private set

    init {
        colourTable = calculatePalette()
    }

    private fun calculatePalette(): IntArray {
        val colorList = mutableListOf<Int>()

        val brightnessModifier = 0.7
        var index = 0

        for (i in 0 until 512) {
            val hue = (i shr 3) / 64.0f + 0.0078125
            val saturation = (i and 7) / 8.0f + 0.0625

            for (j in 0 until 128) {
                val d = j / 128.0
                var red = d
                var green = d
                var blue = d

                if (saturation != 0.0) {
                    val d1 = if (d < 0.5) (d * (1 + saturation)) else (d + saturation - d * saturation)
                    val d2 = 2f * d - d1

                    var redHue = hue + 0.3333333333333333
                    if (redHue > 1f)
                        --redHue

                    var blueHue = hue - 0.3333333333333333
                    if (blueHue < 0f)
                        ++blueHue

                    if (6 * redHue < 1)
                        red = d2 + (d1 - d2) * 6.0 * redHue
                    else if (2 * redHue < 1)
                        red = d1
                    else if (3 * redHue < 2)
                        red = d2 + (d1 - d2) * (0.6666666666666666f - redHue) * 6f
                    else
                        red = d2

                    if (6 * hue < 1)
                        green = d2 + (d1 - d2) * 6f * hue
                    else if (2 * hue < 1)
                        green = d1
                    else if (3 * hue < 2)
                        green = d2 + (d1 - d2) * (0.6666666666666666f - hue) * 6
                    else
                        green = d2

                    if (6 * blueHue < 1)
                        blue = d2 + (d1 - d2) * 6 * blueHue
                    else if (2 * blueHue < 1)
                        blue = d1
                    else if (3 * blueHue < 2)
                        blue = d2 + (d1 - d2) * (0.6666666666666666f - blueHue) * 6
                    else
                        blue = d2
                }

                val adjustedRed = Math.pow(red, brightnessModifier) * 256.0
                val adjustedGreen = Math.pow(green, brightnessModifier) * 256.0
                val adjustedBlue = Math.pow(blue, brightnessModifier) * 256.0

                val argb = 0xFF000000.toInt() or (adjustedRed.toInt() shl 16) or (adjustedGreen.toInt() shl 8) or (adjustedBlue.toInt())

                colorList.add(index++, argb)
            }
        }

        return colorList.toIntArray()
    }

    fun textureForColours(vararg colours: Int): Triple<Image, FloatArray, Map<Int, FloatArray>> {

        val distinctColours = colours.distinct()
        val textureWidth = distinctColours.size * 4
        val writableImage = WritableImage(textureWidth, 4)
        val pixelWriter = writableImage.pixelWriter
        val map = mutableMapOf<Int, FloatArray>()
        val texCoords = mutableListOf<Float>()

        var x = 0
        for ((i, colour) in distinctColours.withIndex()) {
            for (curX in x..(x + 3)) {
                for (y in 0..3) {
                    pixelWriter.setArgb(curX, y, colourTable[colour])
                }
            }
            val lower = i * 4f
            val upper = ((i + 1f) * 4f) - 1f
            val lowerX = (1f / textureWidth) * lower
            val upperX = (1f / textureWidth) * upper

            val colourCoords = listOf(
                    upperX, 0f,
                    lowerX, 0f,
                    lowerX, 1f
            )
            texCoords.addAll(colourCoords)
            map.put(colour, colourCoords.toFloatArray())

            x += 4
        }
        return Triple(writableImage, texCoords.toFloatArray(), map)
    }

}