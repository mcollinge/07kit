package com.kit.gui2.controllers

import com.kit.Application
import com.kit.gui2.views.TitleBarView
import tornadofx.Controller
import java.awt.Frame
import java.awt.Point
import javax.swing.JFrame

/**
 * Date: 10/02/2017
 * Time: 16:26
 * @author Matt Collinge
 */

class TitleBarController: Controller() {

    val view: TitleBarView by inject()

    private var xOffset: Int = 0
    private var yOffset: Int = 0


    fun close() {
        System.exit(1)
    }

    fun maximise() {
        if (Application.FRAME.extendedState != JFrame.MAXIMIZED_BOTH) {
            Application.FRAME.extendedState = JFrame.MAXIMIZED_BOTH
        } else {
            Application.FRAME.extendedState = 0
            Application.FRAME.minimumSize = Application.FRAME.expectedSize
            Application.FRAME.maximumSize = Application.FRAME.expectedSize
            Application.FRAME.preferredSize = Application.FRAME.expectedSize
            Application.FRAME.size = Application.FRAME.expectedSize
        }
    }

    fun minimise() {
        Application.FRAME.state = Frame.ICONIFIED
    }

    fun setWindowOffset(x: Int, y: Int) {
        xOffset = Application.FRAME.x - x
        yOffset = Application.FRAME.y - y
    }

    fun setWindowLocation(x: Int, y: Int) {
        Application.FRAME.location = Point(x + xOffset, y + yOffset)
    }

}