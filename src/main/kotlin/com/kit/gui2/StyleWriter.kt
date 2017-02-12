package com.kit.gui2

import com.kit.gui2.styles.KitStyle
import java.io.File

/**
 * Created by Matt on 12/02/2017.
 */

fun main(args: Array<String>) {
    var kitStyle = KitStyle()

    File("src/main/resources/css/kitstyle.css").printWriter().use { out ->
        kitStyle.selections.forEach {
            out.println(it.render())
        }
    }
}