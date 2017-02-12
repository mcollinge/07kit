package com.kit.gui2.views

import com.kit.gui2.controllers.TitleBarController
import com.kit.gui2.styles.KitStyle
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import tornadofx.*

/**
 * Date: 10/02/2017
 * Time: 16:04
 * @author Matt Collinge
 */
class TitleBarView : View("Title Bar") {

    override val root = borderpane()

    val controller: TitleBarController by inject()

    init {
        with(root) {
            left {
                hbox {
                    button {
                        addClass(KitStyle.red, KitStyle.medium)
                        val icon = FontAwesomeIconView(FontAwesomeIcon.WINDOW_CLOSE)
                        icon.fill = KitStyle.foreground0
                        graphic = icon
                        setOnAction { controller.close() }
                    }
                    button {
                        addClass(KitStyle.yellow, KitStyle.medium)
                        val icon = FontAwesomeIconView(FontAwesomeIcon.WINDOW_MAXIMIZE)
                        icon.fill = KitStyle.foreground0
                        graphic = icon
                        setOnAction { controller.maximise() }
                    }
                    button {
                        addClass(KitStyle.aqua, KitStyle.medium)
                        val icon = FontAwesomeIconView(FontAwesomeIcon.WINDOW_MINIMIZE)
                        icon.fill = KitStyle.foreground0
                        graphic = icon
                        setOnAction { controller.minimise() }
                    }
                }
            }
            center{
                label("07 Kit") {
                    addClass(KitStyle.medium)
                }
            }
            right {
                hbox {
                    button {
                        addClass(KitStyle.purple, KitStyle.medium)
                        val icon = FontAwesomeIconView(FontAwesomeIcon.CAMERA)
                        icon.fill = KitStyle.foreground0
                        graphic = icon
                        setOnAction {
                            //controller.screenshot()
                        }
                    }
                    button {
                        addClass(KitStyle.purple, KitStyle.medium)
                        val icon = FontAwesomeIconView(FontAwesomeIcon.PICTURE_ALT)
                        icon.fill = KitStyle.foreground0
                        graphic = icon
                        setOnAction {
                            //controller.showGallery()
                        }
                    }
                    button {
                        addClass(KitStyle.purple, KitStyle.medium)
                        val icon = FontAwesomeIconView(FontAwesomeIcon.TELEVISION)
                        icon.fill = KitStyle.foreground0
                        graphic = icon
                        setOnAction {
                            //controller.showWatcher(
                        }
                    }
                    button {
                        addClass(KitStyle.purple, KitStyle.medium)
                        val icon = FontAwesomeIconView(FontAwesomeIcon.COG)
                        icon.fill = KitStyle.foreground0
                        graphic = icon
                        setOnAction {
                            //controller.showSettings()
                        }
                    }
                    button {
                        addClass(KitStyle.purple, KitStyle.medium)
                        val icon = FontAwesomeIconView(FontAwesomeIcon.COLUMNS)
                        icon.fill = KitStyle.foreground0
                        graphic = icon
                        setOnAction {
                            //controller.toggleSideBar()
                        }
                    }
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
