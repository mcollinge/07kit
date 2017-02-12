package com.kit.gui2

import com.kit.core.Session
import com.kit.core.model.Property
import com.kit.gui2.views.LoginView
import com.kit.gui2.views.TitleBarView
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.text.Font
import tornadofx.FX
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Point
import java.awt.Toolkit
import java.util.concurrent.Executors
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities

/**
 * Date: 10/02/2017
 * Time: 15:12
 * @author Matt Collinge
 */

private const val WIDTH_PROPERTY = "client_width"
private const val HEIGHT_PROPERTY = "client_height"
private const val X_PROEPRTY = "client_x"
private const val Y_PROPERTY = "client_y"
private const val CLIENT_WIDTH = 1020
private const val CLIENT_HEIGHT = 533

class MainFrame : JFrame("07Kit") {

    private val titleBar = JFXPanel()
    //private val sideBar: JFXPanel
    private val contentPanel = JPanel()
    private var contentJFXPanel: JFXPanel? = JFXPanel()

    var expectedSize: Dimension = Dimension(WIDTH, HEIGHT)
        get() = Dimension(Property.get(WIDTH_PROPERTY).value.toInt(), Property.get(HEIGHT_PROPERTY).value.toInt())

    init {
        defaultCloseOperation = EXIT_ON_CLOSE

        val widthProp = if (Property.get(WIDTH_PROPERTY) != null) Property.get(WIDTH_PROPERTY) else Property(WIDTH_PROPERTY, CLIENT_WIDTH.toString()).save()
        val width = widthProp.value.toInt()

        val heightProp = if (Property.get(HEIGHT_PROPERTY) != null) Property.get(HEIGHT_PROPERTY) else Property(HEIGHT_PROPERTY, CLIENT_HEIGHT.toString()).save()
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
        contentPanel.layout = BorderLayout()
        contentPanel.add(contentJFXPanel, BorderLayout.CENTER)
        add(contentPanel, BorderLayout.CENTER)
        pack()

        Platform.runLater { initJFX() }
    }

    fun setFXContent(parent: Parent) {
        Platform.runLater {
            val newScene = Scene(parent)
            newScene.stylesheets += javaClass.classLoader.getResource("css/kitstyle.css").toExternalForm()
            contentJFXPanel?.scene = newScene
        }
    }

    fun loadGame() {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                Session.get().appletLoader.call()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            SwingUtilities.invokeAndWait {
                contentPanel.remove(contentJFXPanel)
                contentPanel.add(Session.get().appletLoader.applet, BorderLayout.CENTER)
                contentJFXPanel = null
                contentPanel.repaint()
                contentPanel.revalidate()
            }
            Session.get().appletLoader.start()
        }
    }

    private fun initJFX() {
        Font.loadFont(javaClass.classLoader.getResource("fonts/RobotoMono.ttf").toExternalForm(), 0.0)
        val titleBarRoot = FX.find(TitleBarView::class.java).root
        val titleBarScene = Scene(titleBarRoot)
        titleBarScene.stylesheets += javaClass.classLoader.getResource("css/kitstyle.css").toExternalForm()
        titleBar.scene = titleBarScene

        val loginView = FX.find(LoginView::class.java)
        val loginScene = Scene(loginView.root)
        loginScene.stylesheets += javaClass.classLoader.getResource("css/kitstyle.css").toExternalForm()
        contentJFXPanel?.scene = loginScene
        loginView.controller.start()

    }

}
