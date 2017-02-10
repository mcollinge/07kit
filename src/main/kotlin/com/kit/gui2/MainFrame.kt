package com.kit.gui2

import com.kit.core.model.Property
import com.kit.gui2.views.TitleBarView
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import tornadofx.FX
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Point
import java.awt.Toolkit
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * Date: 10/02/2017
 * Time: 15:12
 * @author Matt Collinge
 */

private const val WIDTH_PROPERTY = "client_width"
private const val HEIGHT_PROPERTY = "client_height"
private const val X_PROEPRTY = "client_x"
private const val Y_PROPERTY = "client_y"
private const val WIDTH = 1020
private const val HEIGHT = 540

class MainFrame : JFrame("07Kit") {

    private val titleBar: JFXPanel = JFXPanel()
    //private val sideBar: JFXPanel
    private val contentPanel: JPanel = JPanel()

    var expectedSize: Dimension = Dimension(WIDTH, HEIGHT)
        get() = Dimension(Property.get(WIDTH_PROPERTY).value.toInt(), Property.get(HEIGHT_PROPERTY).value.toInt())

    init {
        defaultCloseOperation = EXIT_ON_CLOSE

        val widthProp = if (Property.get(WIDTH_PROPERTY) != null) Property.get(WIDTH_PROPERTY) else Property(WIDTH_PROPERTY, WIDTH.toString()).save()
        val width = widthProp.value.toInt()

        val heightProp = if (Property.get(HEIGHT_PROPERTY) != null) Property.get(HEIGHT_PROPERTY) else Property(HEIGHT_PROPERTY, HEIGHT.toString()).save()
        val height = heightProp.value.toInt()

        val toolkit = Toolkit.getDefaultToolkit()
        val xProp = if (Property.get(X_PROEPRTY) != null) Property.get(X_PROEPRTY) else Property(X_PROEPRTY, ((toolkit.screenSize.width / 2) - (width / 2)).toString()).save()
        val x = xProp.value.toInt()

        val yProp = if (Property.get(Y_PROPERTY) != null) Property.get(Y_PROPERTY) else Property(Y_PROPERTY, ((toolkit.screenSize.height / 2) - (height / 2)).toString()).save()
        val y = yProp.value.toInt()

        minimumSize = Dimension(width, height)
        preferredSize = minimumSize

        isResizable = true
        isUndecorated = true

        location = Point(x, y)

        layout = BorderLayout()

        add(titleBar, BorderLayout.NORTH)
        add(contentPanel, BorderLayout.CENTER)
        pack()

        Platform.runLater { initJFX() }
    }

    private fun initJFX() {
        val titleBarRoot = FX.find(TitleBarView::class.java).root
        val titleBarScene = Scene(titleBarRoot)
        titleBarScene.stylesheets += javaClass.classLoader.getResource("css/bootstrap3.css").toExternalForm()
        titleBar.scene = titleBarScene
    }

}
