package com.kit.gui2.views

import com.kit.gui2.controllers.TitleBarController
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import javafx.geometry.Pos
import tornadofx.*

/**
 * Date: 10/02/2017
 * Time: 16:04
 * @author Matt Collinge
 */
class TitleBarView : View() {
    override val root = stackpane()

    val controller: TitleBarController by inject()


    init {
        with(root) {
            label("07 Kit") {
                alignment = Pos.CENTER
            }
            hbox {
                alignment = Pos.CENTER_LEFT
                button {
                    addClass("danger", "first", "xs")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.WINDOW_CLOSE)
                    setOnAction { controller.close() }
                }
                button {
                    addClass("warning", "middle", "xs")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.WINDOW_MAXIMIZE)
                    setOnAction { controller.maximise() }
                }
                button {
                    addClass("success", "last", "xs")
                    graphic = FontAwesomeIconView(FontAwesomeIcon.WINDOW_MINIMIZE)
                    setOnAction { controller.minimise() }
                }
            }
            setOnMousePressed {
                controller.setWindowOffset(it.screenX.toInt(), it.screenY.toInt())
            }
            setOnMouseDragged {
                controller.setWindowLocation(it.screenX.toInt(), it.screenY.toInt())
            }
        }
    }
}
