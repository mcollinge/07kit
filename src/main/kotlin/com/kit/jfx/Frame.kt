package com.kit.jfx

import com.kit.Application
import com.kit.core.Session
import com.kit.core.model.Property
import com.kit.gui.ControllerManager
import com.kit.gui.controller.SidebarController
import com.kit.gui.util.ComponentResizer
import com.kit.jfx.views.TitleBarView
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.text.Font
import tornadofx.*
import java.awt.*
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.*

class Frame: JFrame("07Kit") {

    companion object {
        val WIDTH_PROPERTY_KEY = "client_width"
        val HEIGHT_PROPERTY_KEY = "client_height"
        private val X_PROPERTY_KEY = "client_x"
        private val Y_PROPERTY_KEY = "client_y"
        val STANDARD_WIDTH = 1020
        val STANDARD_HEIGHT = 540
    }

    private val titleBarPanel = JFXPanel()
    private val displayPanel = JPanel()
    private var hasSidebar = false

    init {
        defaultCloseOperation = EXIT_ON_CLOSE

        var widthProperty = Property.get(WIDTH_PROPERTY_KEY)
        if (widthProperty == null) {
            widthProperty = Property(WIDTH_PROPERTY_KEY, STANDARD_WIDTH.toString())
            widthProperty.save()
        }
        val width = widthProperty.value.toInt()

        var heightProperty = Property.get(HEIGHT_PROPERTY_KEY)
        if (heightProperty == null) {
            heightProperty = Property(HEIGHT_PROPERTY_KEY, STANDARD_HEIGHT.toString())
            heightProperty.save()
        }
        val height = heightProperty.value.toInt()

        val size = Dimension(width, height)
        minimumSize = size
        preferredSize = size
        isResizable = true
        isUndecorated = true

        val toolkit = Toolkit.getDefaultToolkit()
        var xProperty = Property.get(X_PROPERTY_KEY)
        if (xProperty == null) {
            xProperty = Property(X_PROPERTY_KEY, (toolkit.screenSize.width / 2 - getWidth() / 2).toString())
            xProperty.save()
        }
        var yProperty = Property.get(Y_PROPERTY_KEY)
        if (yProperty == null) {
            yProperty = Property(Y_PROPERTY_KEY, (toolkit.screenSize.height / 2 - getHeight() / 2).toString())
            yProperty.save()
        }
        location = Point(xProperty.value.toInt(), yProperty.value.toInt())

        addComponentListener(object : ComponentAdapter() {
            override fun componentMoved(e: ComponentEvent?) {
                if (e == null) return
                val x = e.component.location.x.toString()
                val y = e.component.location.y.toString()
                var xProp = Property.get(X_PROPERTY_KEY)
                var yProp = Property.get(Y_PROPERTY_KEY)
                if (xProp == null) {
                    xProp = Property(X_PROPERTY_KEY, x)
                } else {
                    xProp.value = x
                }
                if (yProp == null) {
                    yProp = Property(X_PROPERTY_KEY, y)
                } else {
                    yProp.value = y
                }
                xProp.save()
                yProp.save()
            }

            override fun componentResized(e: ComponentEvent?) {
                if (e == null) return
                val newWidth = e.component.width.toString()
                val newHeight = e.component.height.toString()
                var widthProp = Property.get(WIDTH_PROPERTY_KEY)
                var heightProp = Property.get(HEIGHT_PROPERTY_KEY)

                if (widthProp == null) {
                    widthProp = Property(WIDTH_PROPERTY_KEY, newWidth)
                } else {
                    widthProp.value = newWidth
                }
                if (heightProp == null) {
                    heightProp = Property(HEIGHT_PROPERTY_KEY, newHeight)
                } else {
                    heightProp.value = newHeight
                }
                widthProp.save()
                heightProp.save()
            }
        })

        layout = BorderLayout()

        val resizer = ComponentResizer()
        resizer.dragInsets = Insets(4, 4, 4, 4)
        resizer.registerComponent(this)

        JPopupMenu.setDefaultLightWeightPopupEnabled(false)

        contentPane.add(titleBarPanel, BorderLayout.NORTH)

        displayPanel.background = Application.COLOUR_SCHEME.dark
        displayPanel.preferredSize = Dimension(765, 510)
        displayPanel.minimumSize = Dimension(765, 510)
        displayPanel.layout = BorderLayout()
        displayPanel.border = BorderFactory.createEmptyBorder(0, 5, 5, 5)
        contentPane.add(displayPanel, BorderLayout.CENTER)
        initJfx()
        loadGame()
        pack()
    }

    private fun loadGame() {

        val worker = object : SwingWorker<Any?, Any?>() {
            override fun doInBackground(): Any? {
                try {
                    Session.get().appletLoader.call()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                Session.get().appletView.applet = Session.get().appletLoader.applet
                Session.get().appletLoader.start()
                while (!Session.get().pluginManager.isPluginsStarted) {
                    Thread.sleep(150)
                }
                Session.get().appletView.showApplet()
                return null
            }
        }
        worker.execute()
        displayPanel.add(Session.get().appletView, BorderLayout.CENTER)
        revalidate()
        repaint()
    }

    private fun initJfx() {
        Platform.runLater {
            Font.loadFont(javaClass.classLoader.getResource("fonts/RobotoMono.ttf").toExternalForm(), 0.0)
            //Font.loadFont(javaClass.classLoader.getResource("fonts/wingding.ttf").toExternalForm(), 0.0)
            val titleBarRoot = FX.find(TitleBarView::class.java).root
            val titleBarScene = Scene(titleBarRoot)
            titleBarScene.stylesheets += javaClass.classLoader.getResource("styles/flatlightstyle.css").toExternalForm()
            //titleBarScene.stylesheets += javaClass.classLoader.getResource("styles/flatlightstyle-wingdings.css").toExternalForm()
            titleBarPanel.scene = titleBarScene
        }
    }

    fun toggleSidebar() {
        if (hasSidebar) {
            val width = ControllerManager.get(SidebarController::class.java).component.width
            displayPanel.remove(ControllerManager.get(SidebarController::class.java).component)

            val newMin = Dimension(size)
            newMin.setSize(newMin.width - width, newMin.height)

            displayPanel.minimumSize = newMin
            displayPanel.preferredSize = newMin
            minimumSize = newMin
            preferredSize = newMin

            displayPanel.revalidate()
            pack()
        } else {
            val size = Dimension(size)

            val width = ControllerManager.get(SidebarController::class.java).component.width
            size.width += width

            displayPanel.add(ControllerManager.get(SidebarController::class.java).component, BorderLayout.WEST)
            displayPanel.minimumSize = size
            displayPanel.preferredSize = size
            minimumSize = size
            preferredSize = size

            displayPanel.revalidate()
            pack()
        }
        hasSidebar = !hasSidebar
        revalidate()
        repaint()
    }

    fun present() {
        iconImage = Application.ICON_IMAGE
        isVisible = true
    }

}