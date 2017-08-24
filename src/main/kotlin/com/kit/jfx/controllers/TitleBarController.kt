package com.kit.jfx.controllers

import com.kit.core.Session
import com.kit.core.model.Property
import com.kit.gui.ControllerManager
import com.kit.gui.controller.GalleryController
import com.kit.gui.controller.SettingsController
import com.kit.jfx.Frame
import com.kit.jfx.views.TitleBarView
import tornadofx.*
import java.awt.Dimension
import java.awt.Point
import javax.swing.JFrame

class TitleBarController: Controller() {

    val view: TitleBarView by inject()

    private var xOffset: Int = 0
    private var yOffset: Int = 0


    fun close() {
        System.exit(1)
    }

    fun maximise() {
        if (Session.get().frame.extendedState != JFrame.MAXIMIZED_BOTH) {
            Session.get().frame.extendedState = JFrame.MAXIMIZED_BOTH
        } else {
            Session.get().frame.extendedState = 0

            var widthProperty = Property.get(Frame.WIDTH_PROPERTY_KEY)
            if (widthProperty == null) {
                widthProperty = Property(Frame.WIDTH_PROPERTY_KEY, Frame.STANDARD_WIDTH.toString())
                widthProperty.save()
            }
            val width = widthProperty.value.toInt()

            var heightProperty = Property.get(Frame.HEIGHT_PROPERTY_KEY)
            if (heightProperty == null) {
                heightProperty = Property(Frame.HEIGHT_PROPERTY_KEY, Frame.STANDARD_HEIGHT.toString())
                heightProperty.save()
            }
            val height = heightProperty.value.toInt()

            val dimensions = Dimension(width, height)
            Session.get().frame.minimumSize = dimensions
            Session.get().frame.maximumSize = dimensions
            Session.get().frame.preferredSize = dimensions
            Session.get().frame.size = dimensions
        }
    }

    fun minimise() {
        Session.get().frame.state = java.awt.Frame.ICONIFIED
    }

    fun setWindowOffset(x: Int, y: Int) {
        xOffset = Session.get().frame.x - x
        yOffset = Session.get().frame.y - y
    }

    fun setWindowLocation(x: Int, y: Int) {
        Session.get().frame.location = Point(x + xOffset, y + yOffset)
    }

    fun toggleSidebar() {
        Session.get().frame.toggleSidebar()
    }

    fun screenshot() {
        Session.get().screen.capture("Screenshot", true)
    }

    fun showGallery() {
        ControllerManager.get(GalleryController::class.java).show()
    }

    fun showSettings() {
        ControllerManager.get(SettingsController::class.java).show()
    }

}