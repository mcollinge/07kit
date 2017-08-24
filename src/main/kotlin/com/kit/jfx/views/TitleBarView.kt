package com.kit.jfx.views

import com.kit.jfx.controllers.TitleBarController
import com.kit.jfx.styles.FlatLightStyle
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import tornadofx.*

class TitleBarView : View("Title Bar") {

    override val root = borderpane()

    val controller: TitleBarController by inject()

    init {
        with(root) {
            addClass(FlatLightStyle.wrapperDark)
            right {
                hbox {
                    button {
                        addClass(FlatLightStyle.aqua, FlatLightStyle.medium)

                        val icon = FontAwesomeIconView(FontAwesomeIcon.WINDOW_MINIMIZE)
                        icon.fill = FlatLightStyle.background0
                        graphic = icon
                        setOnAction { controller.minimise() }
                    }
                    button {
                        addClass(FlatLightStyle.yellow, FlatLightStyle.medium)
                        val icon = FontAwesomeIconView(FontAwesomeIcon.WINDOW_MAXIMIZE)
                        icon.fill = FlatLightStyle.background0
                        graphic = icon
                        setOnAction { controller.maximise() }
                    }
                    button {
                        addClass(FlatLightStyle.red, FlatLightStyle.medium)
                        val icon = FontAwesomeIconView(FontAwesomeIcon.WINDOW_CLOSE)
                        icon.fill = FlatLightStyle.background0
                        graphic = icon
                        setOnAction { controller.close() }
                    }
                }
            }
            center{
                label("07 Kit") {
                    addClass(FlatLightStyle.medium)
                }
            }
            left {
                hbox {
                    button {
                        addClass(FlatLightStyle.blue, FlatLightStyle.medium)
                        val icon = FontAwesomeIconView(FontAwesomeIcon.CAMERA)
                        icon.fill = FlatLightStyle.background0
                        graphic = icon
                        setOnAction {
                            controller.screenshot()
                        }
                    }
                    button {
                        addClass(FlatLightStyle.blue, FlatLightStyle.medium)
                        val icon = FontAwesomeIconView(FontAwesomeIcon.PICTURE_ALT)
                        icon.fill = FlatLightStyle.background0
                        graphic = icon
                        setOnAction {
                            controller.showGallery()
                        }
                    }
                    button {
                        addClass(FlatLightStyle.blue, FlatLightStyle.medium)
                        val icon = FontAwesomeIconView(FontAwesomeIcon.TELEVISION)
                        icon.fill = FlatLightStyle.background0
                        graphic = icon
                        setOnAction {
                            //controller.showWatcher(
                        }
                    }
                    button {
                        addClass(FlatLightStyle.blue, FlatLightStyle.medium)
                        val icon = FontAwesomeIconView(FontAwesomeIcon.COG)
                        icon.fill = FlatLightStyle.background0
                        graphic = icon
                        setOnAction {
                            controller.showSettings()
                        }
                    }
                    button {
                        addClass(FlatLightStyle.blue, FlatLightStyle.medium)
                        val icon = FontAwesomeIconView(FontAwesomeIcon.COLUMNS)
                        icon.fill = FlatLightStyle.background0
                        graphic = icon
                        setOnAction {
                            controller.toggleSidebar()
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