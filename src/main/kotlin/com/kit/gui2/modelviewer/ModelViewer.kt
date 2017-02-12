package com.kit.gui2.modelviewer

import com.kit.api.wrappers.Model
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableFloatArray
import javafx.embed.swing.JFXPanel
import javafx.scene.AmbientLight
import javafx.scene.Group
import javafx.scene.PerspectiveCamera
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.DrawMode
import javafx.scene.shape.MeshView
import javafx.scene.shape.TriangleMesh
import tornadofx.add
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame

/**
 * Created by Matt on 12/02/2017.
 */
class ModelViewer: JFrame("Model Viewer") {

    private val root = Group()
    private val meshView = MeshView()
    private val panel = JFXPanel()

    init{
        minimumSize = Dimension(800, 600)
        layout = BorderLayout()
        add(panel, BorderLayout.CENTER)
        initJFX()
        pack()
        isVisible = true
    }

    fun setModel(model: Model) {
        Platform.runLater {
            val triangles = model.triangles

            val points = FXCollections.observableFloatArray()

            for (tri in triangles) {
                points.addAll(tri[0])
                points.addAll(tri[1])
                points.addAll(tri[2])

            }

            val mesh = TriangleMesh()
            mesh.points.addAll(points)
            meshView.mesh = mesh
            meshView.drawMode = DrawMode.LINE
        }
    }

    private fun initJFX() {
        Platform.runLater {
            val material = PhongMaterial(Color.BLUE)
            meshView.material = material
            root.add(meshView)
            val light = AmbientLight(Color.WHITE)
            root.add(light)
            root.add(PerspectiveCamera())
            val scene = Scene(root, 800.0, 600.0)
            panel.scene = scene
        }
    }
}