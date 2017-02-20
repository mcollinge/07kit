package com.kit.gui2.modelviewer

import com.kit.core.Session
import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.embed.swing.SwingFXUtils
import javafx.scene.*
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.DrawMode
import javafx.scene.shape.MeshView
import javafx.scene.shape.TriangleMesh
import javafx.scene.transform.Rotate
import javafx.scene.transform.Translate
import javafx.stage.Stage
import java.io.File
import javax.imageio.ImageIO

/**
 * Created by Matt on 15/02/2017.
 */

class FXGameApp : Application() {

    private val cameraController = CameraController()
    private val playerMesh = TriangleMesh()
    private val playerMeshView = MeshView()
    private val npcMesh = TriangleMesh()
    private val npcMeshView = MeshView()
    private val clientAnimator = ClientAnimator()

    override fun start(primaryStage: Stage?) {
        cameraController.camera.nearClip = 0.1
        cameraController.camera.farClip = 10000.0

        playerMesh.texCoords.setAll(
                0f, 0f,
                0f, 1f,
                1f, 1f
        )
        playerMeshView.mesh = playerMesh
        playerMeshView.material = PhongMaterial(Color.BLUE)
        playerMeshView.drawMode = DrawMode.FILL

        npcMesh.texCoords.setAll(
                0f, 0f,
                0f, 1f,
                1f, 1f
        )
        npcMeshView.mesh = npcMesh
        npcMeshView.material = PhongMaterial(Color.GREEN)
        npcMeshView.drawMode = DrawMode.FILL

        val group = Group(cameraController, npcMeshView, playerMeshView)
        group.isAutoSizeChildren = false
        group.depthTest = DepthTest.ENABLE

        val scene = Scene(group, 765.0, 503.0,true, SceneAntialiasing.BALANCED)
        scene.fill = Color.BLACK
        scene.camera = cameraController.camera

        primaryStage?.scene = scene
        primaryStage?.title = "Runescape"
        primaryStage?.sizeToScene()
        primaryStage?.show()

        clientAnimator.start()

        val texture = RSColour.textureForColours(4, 17, 17, 17, 4, 99, 150, 200, 985, 365, 548, 1223)

        ImageIO.write(SwingFXUtils.fromFXImage(texture.first, null), "png", File("testtexture.png"))

    }

    private inner class ClientAnimator: AnimationTimer() {

        override fun handle(now: Long) {
            if (Session.get() == null || !Session.get().isLoggedIn) return

            updatePlayer()
            updateNpc()
            updateCamera()
        }

        private fun updatePlayer() {
            val triangles = Session.get().player.model?.triangles ?: return

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

            val x = Session.get().player.localX.toDouble()
            val y = Session.get().viewport.getTileHeight(Session.get().player.localX, Session.get().player.localY, Session.get().player.z).toDouble()
            val z = Session.get().player.localY.toDouble()

            playerMeshView.transforms.setAll(
                    Translate(x, y, z)
            )
        }

        private fun updateNpc() {
            val npc = Session.get().npcs.find("Hatius Cosaintus").single() ?: return

            if (npc.model == null)
                return
            val triangles = npc.model.triangles

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

            val x = npc.localX.toDouble()
            val y = Session.get().viewport.getTileHeight(npc.localX, npc.localY, npc.z).toDouble()
            val z = npc.localY.toDouble()

            npcMeshView.transforms.setAll(
                    Translate(x, y, z)
            )
        }

        private fun updateCamera() {
            val camera = Session.get().camera

            cameraController.reset()
            cameraController.setPivot(camera.x.toDouble(), camera.z.toDouble(), camera.y.toDouble())
            cameraController.setRotate(-camera.pitch.toDouble(), -camera.yaw.toDouble(), 0.0)
            cameraController.setTranslate(camera.x.toDouble(), camera.z.toDouble(), camera.y.toDouble())
        }

    }


}