package com.kit.gui2.modelviewer

import com.kit.game.engine.renderable.entity.INpc
import com.kit.game.engine.renderable.entity.IPlayer
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.embed.swing.JFXPanel
import javafx.geometry.Pos
import javafx.scene.DepthTest
import javafx.scene.Group
import javafx.scene.PerspectiveCamera
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.Box
import javafx.scene.shape.DrawMode
import javafx.scene.shape.MeshView
import javafx.scene.shape.TriangleMesh
import javafx.scene.transform.Rotate
import javafx.scene.transform.Translate
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame

/**
 * Created by Matt on 12/02/2017.
 */
class ModelViewer: JFrame("Model Viewer") {

    private val playerMesh = TriangleMesh()
    private val playerMeshView = MeshView()
    private val npcMesh = TriangleMesh()
    private val npcMeshView = MeshView()
    private val group = Group()
    private val root = StackPane(group)
    private val panel = JFXPanel()
    private val camera = PerspectiveCamera()

    init{
        minimumSize = Dimension(800, 600)
        layout = BorderLayout()
        add(panel, BorderLayout.CENTER)
        initJFX()
        pack()
        isVisible = true
    }

    fun setCamera(cameraX: Double, cameraY: Double, cameraZ: Double, pitch: Double, yaw: Double) {
        Platform.runLater {
            group.transforms.setAll(
                    Rotate(pitch, 0.0, 0.0, 0.0, Rotate.X_AXIS),
                    Rotate(yaw, 0.0, 0.0, 0.0, Rotate.Y_AXIS),
                    Translate(0.0, 0.0, cameraZ)
            )
        }
    }

    fun setPlayer(player: IPlayer) {
        Platform.runLater {

            val triangles = player.model.triangles

            val points = FXCollections.observableFloatArray()
            val faces = FXCollections.observableIntegerArray()

            for ((index, tri) in triangles.withIndex()) {
                points.addAll(tri[0])
                points.addAll(tri[1])
                points.addAll(tri[2])
                faces.addAll((points.size() / 3) - 1, index % 3)
            }

            playerMesh.points.setAll(points)
            playerMesh.faces.setAll(faces)
           /* playerMeshView.transforms.setAll(
                    Translate(player.localX.toDouble(), 0.0, player.localY.toDouble())
            )*/
        }
    }

    fun setNpcs(npcs: INpc, playerX: Float, playerZ: Float) {
        Platform.runLater {
            if (npcs.model == null)
                return@runLater
            val triangles = npcs.model.triangles

            val points = FXCollections.observableFloatArray()
            val faces = FXCollections.observableIntegerArray()

            for ((index, tri) in triangles.withIndex()) {
                points.addAll(tri[0])
                points.addAll(tri[1])
                points.addAll(tri[2])
                faces.addAll((points.size() / 3) - 1, index % 3)
            }

            npcMesh.points.setAll(points)
            npcMesh.faces.setAll(faces)
            npcMeshView.transforms.setAll(
                    Translate((npcs.localX - playerX).toDouble(), 0.0, (npcs.localY.toDouble() - playerZ))
            )


        }
    }

    private fun initJFX() {
        Platform.runLater {

            group.isAutoSizeChildren = false
            group.depthTest = DepthTest.ENABLE
            group.minWidth(765.0)
            group.maxWidth(765.0)
            group.minHeight(503.0)
            group.maxHeight(503.0)

            playerMesh.texCoords.setAll(
                    0f, 0f,
                    0f, 1f,
                    1f, 1f
            )
            playerMeshView.mesh = playerMesh
            playerMeshView.material = PhongMaterial(Color.BLUE)
            playerMeshView.drawMode = DrawMode.FILL
            group.children.add(playerMeshView)

            npcMesh.texCoords.setAll(
                    0f, 0f,
                    0f, 1f,
                    1f, 1f
            )
            npcMeshView.mesh = npcMesh
            npcMeshView.material = PhongMaterial(Color.GREEN)
            npcMeshView.drawMode = DrawMode.FILL
            group.children.add(npcMeshView)

            val box = Box(25.0, 25.0, 25.0)
            box.material = PhongMaterial(Color.RED)
            box.drawMode = DrawMode.FILL


            group.children.add(box)


            val scene = Scene(root, 765.0, 503.0, true)
            scene.camera = camera

            panel.scene = scene
        }
    }
}