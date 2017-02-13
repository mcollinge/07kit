package com.kit.gui2.modelviewer

import com.kit.api.wrappers.Model
import javafx.animation.AnimationTimer
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.embed.swing.JFXPanel
import javafx.scene.Group
import javafx.scene.PerspectiveCamera
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.DrawMode
import javafx.scene.shape.MeshView
import javafx.scene.shape.TriangleMesh
import javafx.scene.transform.Rotate
import javafx.scene.transform.Translate
import tornadofx.add
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame

/**
 * Created by Matt on 12/02/2017.
 */
class ModelViewer: JFrame("Model Viewer") {

    private val root = Group()
    private val mesh = TriangleMesh()
    private val meshView = MeshView()
    private val panel = JFXPanel()
    private val camera = PerspectiveCamera()
    private val cameraPosition = Translate(0.0, 0.0, 0.0)
    private val cameraXRotate = Rotate(0.0, 0.0, 0.0, 0.0, Rotate.X_AXIS)
    private val cameraYRotate = Rotate(0.0, 0.0, 0.0, 0.0, Rotate.Y_AXIS)
    private val dragStartX = 0.0
    private val dragStartY = 0.0
    private val dragStartRotateX = 0.0
    private val dragStartRotateY = 0.0

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
            val faces = FXCollections.observableIntegerArray()

            for ((index, tri) in triangles.withIndex()) {
                points.addAll(tri[0])
                points.addAll(tri[1])
                points.addAll(tri[2])
                faces.addAll((points.size() / 3) - 1, index % 3)
            }

            mesh.points.setAll(points)
            mesh.faces.setAll(faces)
        }
    }

    fun setCamera(x: Double, y: Double, z: Double, pitch: Double, yaw: Double) {
        Platform.runLater {
            cameraPosition.x = x
            cameraPosition.y = y
            cameraPosition.z = z
            cameraYRotate.angle = yaw
            cameraXRotate.angle = -pitch

        }
    }

    private fun initJFX() {
        Platform.runLater {
            mesh.texCoords.setAll(
                    0f, 0f,
                    0f, 1f,
                    1f, 1f
            )
            meshView.mesh = mesh
            meshView.material = PhongMaterial(Color.BLUE)
            meshView.drawMode = DrawMode.FILL
            val group = Group(meshView)
            root.add(group)
            val scene = Scene(root, 800.0, 600.0)
            camera.transforms.addAll(cameraPosition, cameraXRotate, cameraYRotate)
            scene.camera = camera
            panel.scene = scene
        }
    }
}